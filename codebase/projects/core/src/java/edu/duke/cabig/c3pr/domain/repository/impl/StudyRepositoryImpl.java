    package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.ServiceName;
import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.Error;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.factory.StudyFactory;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.StudyValidationException;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.service.impl.StudyXMLImporterServiceImpl;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

@Transactional
public class StudyRepositoryImpl implements StudyRepository {

    private StudyDao studyDao;

    private HealthcareSiteDao healthcareSiteDao;

    private Logger log = Logger.getLogger(StudyXMLImporterServiceImpl.class.getName());

    private C3PRExceptionHelper c3PRExceptionHelper;

    private MessageSource c3prErrorMessages;

    private StudySiteDao studySiteDao;

    private StudyService studyService;
    
    private StudyFactory studyFactory;
    
    public void save(Study study) throws C3PRCodedException {
        // TODO call ESB to broadcast protocol, POC
        studyDao.save(study);
    }

    public Study merge(Study study) {
        return studyDao.merge(study);
    }

    @Transactional(readOnly = false)
    public void buildAndSave(Study study) throws C3PRCodedException {

        study = studyFactory.buildStudy(study);

        studyDao.save(study);
        log.debug("Study saved with grid ID" + study.getGridId());
    }

    /**
     * Validate a study against a set of validation rules
     * 
     * @param study
     * @throws StudyValidationException
     * @throws C3PRCodedException
     */
    public void validate(Study study) throws StudyValidationException, C3PRCodedException {

        try {
            if ((study.getCoordinatingCenterAssignedIdentifier() == null)) {
                throw new StudyValidationException("Coordinating Center identifier is required");
            }
            else if (searchByCoOrdinatingCenterId(study.getCoordinatingCenterAssignedIdentifier())
                            .size() > 0) {
                throw new StudyValidationException("Study exists");
            }

            for (StudyOrganization organization : study.getStudyOrganizations()) {
                if (healthcareSiteDao.getByNciInstituteCode(organization.getHealthcareSite()
                                .getNciInstituteCode()) == null) {
                    throw new StudyValidationException(
                                    "Could not find Organization with NCI Institute code:"
                                                    + organization.getHealthcareSite()
                                                                    .getNciInstituteCode());
                }
            }
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            throw new StudyValidationException("Error when validating study : " + e.getMessage());
        }
    }

    public List<Study> searchByCoOrdinatingCenterId(OrganizationAssignedIdentifier identifier)
                    throws C3PRCodedException {
        HealthcareSite healthcareSite = this.healthcareSiteDao.getByNciInstituteCode(identifier
                        .getHealthcareSite().getNciInstituteCode());
        if (healthcareSite == null) {
            throw c3PRExceptionHelper.getException(
                            getCode("C3PR.EXCEPTION.STUDY.INVALID.HEALTHCARESITE_IDENTIFIER.CODE"),
                            new String[] { identifier.getHealthcareSite().getNciInstituteCode(),
                                    identifier.getType() });
        }
        identifier.setHealthcareSite(healthcareSite);
        return studyDao.searchByOrgIdentifier(identifier);
    }

    public int countAcrrualsByDate(Study study, Date startDate, Date endDate) {
        return studyDao.countAcrrualsByDate(study, startDate, endDate);
    }

    public List<Study> searchByExample(Study study, boolean isWildCard, int maxResults,
                    String order, String orderBy) {
        return studyDao.searchByExample(study, isWildCard, maxResults, order, orderBy);
    }

    @Transactional
    public StudySite activateStudySite(List<Identifier> studyIdentifiers, String nciInstituteCode){
        Study study = getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(nciInstituteCode);
        return activateStudySite(studyIdentifiers, studySite);
    }

    @Transactional
    public Study amendStudy(List<Identifier> studyIdentifiers, Study amendedStudyDetails){
        throw new RuntimeException("Not yet implemented");
    }

//    @Transactional
//    public StudySite approveStudySiteForActivation(List<Identifier> studyIdentifiers,
//                    String nciInstituteCode){
//        Study study=getUniqueStudy(studyIdentifiers);
//        StudySite studySite = study.getStudySite(nciInstituteCode);
//        return approveStudySiteForActivation(studyIdentifiers, studySite);
//    }

    @Transactional
    public Study closeStudyToAccrual(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        study.closeToAccrual();
        if(study.isMultisite() && studyService.isMultisiteEnable()){
            if(study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
                closeStudyToAccrualAtAffiliates(studyIdentifiers);
            }else{
                study.getStudySite(studyService.getLocalNCIInstituteCode()).setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
            }
        }
        return this.merge(study);
    }

    @Transactional
    public Study closeStudyToAccrualAndTreatment(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        study.closeToAccrualAndTreatment();
        if(study.isMultisite() && studyService.isMultisiteEnable()){
            if(study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
                closeStudyToAccrualAtAffiliates(studyIdentifiers);
            }else{
                study.getStudySite(studyService.getLocalNCIInstituteCode()).setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
            }
        }
        return this.merge(study);
    }

    @Transactional
    public Study temporarilyCloseStudy(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        study.temporarilyCloseToAccrual();
        if(study.isMultisite() && studyService.isMultisiteEnable()){
            if(study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
                closeStudyToAccrualAtAffiliates(studyIdentifiers);
            }else{
                study.getStudySite(studyService.getLocalNCIInstituteCode()).setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
            }
        }
        return this.merge(study);
    }

    @Transactional
    public Study temporarilyCloseStudyToAccrualAndTreatment(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        study.temporarilyCloseToAccrualAndTreatment();
        if(study.isMultisite() && studyService.isMultisiteEnable()){
            if(study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
                closeStudyToAccrualAtAffiliates(studyIdentifiers);
            }else{
                study.getStudySite(studyService.getLocalNCIInstituteCode()).setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
            }
        }
        return this.merge(study);
    }
    
    @Transactional
    public Study updateStudyStatus(List<Identifier> studyIdentifiers,
                    CoordinatingCenterStudyStatus status){
        throw new RuntimeException("Not Implemented");
    }

    @Transactional
    public StudySite closeStudySiteToAccrual(List<Identifier> studyIdentifiers, String nciInstituteCode){
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        studySite.closeToAccrual();
        if(study.isMultisite() && !studySite.getHostedMode() && !studyService.isStudyOrganizationLocal(nciInstituteCode) && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
            List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
            domainObjects.addAll(studyIdentifiers);
            domainObjects.add(study.getStudySite(nciInstituteCode).getHealthcareSite());
            EndPoint endpoint=handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.CLOSE_STUDY_SITE_TO_ACCRUAL, domainObjects);
            if(endpoint!=null && endpoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                studySite=studySiteDao.merge(studySite);
            }else{
                throw c3PRExceptionHelper.getMultisiteException(endpoint.getLastAttemptError());
            }
        }else{
            studySite=studySiteDao.merge(studySite);
        }
        return studySite;
    }

    @Transactional
    public StudySite closeStudySiteToAccrualAndTreatment(List<Identifier> studyIdentifiers,
                    String nciInstituteCode) {
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        studySite.closeToAccrualAndTreatment();
        if(study.isMultisite() && !studySite.getHostedMode() && !studyService.isStudyOrganizationLocal(nciInstituteCode) && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
            List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
            domainObjects.addAll(studyIdentifiers);
            domainObjects.add(study.getStudySite(nciInstituteCode).getHealthcareSite());
            handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.CLOSE_STUDY_SITE_TO_ACCRUAL, domainObjects);
            EndPoint endpoint=studySite.getEndPoint(ServiceName.STUDY, APIName.CLOSE_STUDY_SITE_TO_ACCRUAL);
            if(endpoint!=null && endpoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                studySite=studySiteDao.merge(studySite);
            }else{
                throw c3PRExceptionHelper.getMultisiteException(endpoint.getLastAttemptError());
            }
        }else{
            studySite=studySiteDao.merge(studySite);
        }
        return studySite;
    }

    @Transactional
    public StudySite temporarilyCloseStudySiteToAccrual(List<Identifier> studyIdentifiers,
                    String nciInstituteCode) {
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        studySite.temporarilyCloseToAccrual();
        if(study.isMultisite() && !studySite.getHostedMode() && !studyService.isStudyOrganizationLocal(nciInstituteCode) && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
            List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
            domainObjects.addAll(studyIdentifiers);
            domainObjects.add(study.getStudySite(nciInstituteCode).getHealthcareSite());
            handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.CLOSE_STUDY_SITE_TO_ACCRUAL, domainObjects);
            EndPoint endpoint=studySite.getEndPoint(ServiceName.STUDY, APIName.CLOSE_STUDY_SITE_TO_ACCRUAL);
            if(endpoint!=null && endpoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                studySite=studySiteDao.merge(studySite);
            }else{
                throw c3PRExceptionHelper.getMultisiteException(endpoint.getLastAttemptError());
            }
        }else{
            studySite=studySiteDao.merge(studySite);
        }
        return studySite;
    }

    @Transactional
    public StudySite temporarilyCloseStudySiteToAccrualAndTreatment(
                    List<Identifier> studyIdentifiers, String nciInstituteCode) {
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        studySite.temporarilyCloseToAccrualAndTreatment();
        if(study.isMultisite() && !studySite.getHostedMode() && !studyService.isStudyOrganizationLocal(nciInstituteCode) && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
            List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
            domainObjects.addAll(studyIdentifiers);
            domainObjects.add(study.getStudySite(nciInstituteCode).getHealthcareSite());
            handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.CLOSE_STUDY_SITE_TO_ACCRUAL, domainObjects);
            EndPoint endpoint=studySite.getEndPoint(ServiceName.STUDY, APIName.CLOSE_STUDY_SITE_TO_ACCRUAL);
            if(endpoint!=null && endpoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                studySite=studySiteDao.merge(studySite);
            }else{
                throw c3PRExceptionHelper.getMultisiteException(endpoint.getLastAttemptError());
            }
        }else{
            studySite=studySiteDao.merge(studySite);
        }

        return studySite;
    }
    
    @Transactional
    public List<StudySite> closeStudySites(List<Identifier> studyIdentifiers){
        Study study = getUniqueStudy(studyIdentifiers);
        for (StudySite studySite : study.getStudySites()) {
            studySite.closeToAccrual();
            studySiteDao.save(studySite);
        }
//        if(study.studyService.canMultisiteBroadcast() && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
//            studyService.handleAffiliateSitesBroadcast(study, APIName.CLOSE_STUDY_SITES, studyIdentifiers);
//        }
        if(study.isMultisite() && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
            closeStudyToAccrualAtAffiliates(studyIdentifiers);
        }
        return study.getStudySites();
    }

    @Transactional
    public Study createStudy(Study study) {
    	
        study.readyToOpen();
        if(study.isMultisite() && studyService.isMultisiteEnable()){
        	//if C3PR instance is at Coordinating Center
            if(study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
                createStudyAtAffiliates(study.getIdentifiers());
            //if C3PR instance is running at one of the affiliate sites.
            }else{
                try{
                	StudySite studySite = study.getStudySite(studyService.getLocalNCIInstituteCode());
                	studySite.setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
                }catch(C3PRCodedRuntimeException ex){
                	// catching exception
                }
            }
        }
        return this.merge(study);
    }
    
    @Transactional
    public Study createAndOpenStudy(Study study) {
        study.readyToOpen();
        study.open();
        if(study.isMultisite() && studyService.isMultisiteEnable()){
        	//if C3PR instance is at Coordinating Center
            if(study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
                createAndOpenStudyAtAffiliates(study.getIdentifiers());
            //if C3PR instance is running at one of the affiliate sites.
            }else{
                try{
                	StudySite studySite = study.getStudySite(studyService.getLocalNCIInstituteCode());
                	studySite.setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
                }catch(C3PRCodedRuntimeException ex){
                	// catching exception
                }
            }
        }
        return this.merge(study);
    }
    
    @Transactional
    public Study createStudy(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        return createStudy(study);
    }

    @Transactional
    public Study openStudy(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        if(study.getCoordinatingCenterStudyStatus()==CoordinatingCenterStudyStatus.PENDING)
            study=this.createStudy(studyIdentifiers);
        study.open();
        if(study.isMultisite() && studyService.isMultisiteEnable()){
            if(study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
                openStudyAtAffiliates(studyIdentifiers);
            }else{
            	try{
                	StudySite studySite = study.getStudySite(studyService.getLocalNCIInstituteCode());
                	studySite.setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
                }catch(C3PRCodedRuntimeException ex){
                	// catching exception in case the study site doesnt exist
                }
            }
        }
        return this.merge(study);
    }

    @Transactional
    public StudySite updateStudySiteProtocolVersion(List<Identifier> studyIdentifiers,
                    String nciInstituteCode, String version) {
        throw new RuntimeException("Not yet implemented");

    }

    
//    @Transactional
//    public StudySite approveStudySiteForActivation(List<Identifier> studyIdentifiers, StudySite studySiteObj){
//        Study study=getUniqueStudy(studyIdentifiers);
//        String nciInstituteCode = studySiteObj.getHealthcareSite().getNciInstituteCode();
//        StudySite studySite;
//        CompanionStudyAssociation companionStudyAssociaton = studySiteObj.getCompanionStudyAssociation() ;
//        if(companionStudyAssociaton != null ){
//        	studySite = study.getCompanionStudySite(nciInstituteCode);
//        }else{
//        	studySite = study.getStudySite(nciInstituteCode);
//        }
//        studySite.approveForActivation();
//        if(study.isMultisite() && !studySite.getHostedMode() && !studyService.isStudyOrganizationLocal(nciInstituteCode) && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
//            List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
//            domainObjects.addAll(studyIdentifiers);
//            domainObjects.add(studySite.getHealthcareSite());
//            EndPoint endpoint=null;
//            //EndPoint endpoint=handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.APPROVE_STUDY_SITE_FOR_ACTIVATION, domainObjects);
//            if(endpoint.getStatus()!=WorkFlowStatusType.MESSAGE_SEND_CONFIRMED)
//                throw c3PRExceptionHelper.getMultisiteException(endpoint.getLastAttemptError());
//        }
//        return studySiteDao.merge(studySite);
//    }

    public StudySite activateStudySite(List<Identifier> studyIdentifiers, StudySite studySiteObj){
        Study study = getUniqueStudy(studyIdentifiers);
        String nciInstituteCode = studySiteObj.getHealthcareSite().getNciInstituteCode();
        StudySite studySite;
        CompanionStudyAssociation companionStudyAssociaton = studySiteObj.getCompanionStudyAssociation() ;
        if(companionStudyAssociaton != null ){
        	studySite = study.getCompanionStudySite(nciInstituteCode);
        }else{
        	studySite = study.getStudySite(nciInstituteCode);
        }
//        if (!studySite.getHostedMode()
//                        && !study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())
//                        && studySite.getSiteStudyStatus()!=SiteStudyStatus.APPROVED_FOR_ACTIVTION) {
//            throw c3PRExceptionHelper.getRuntimeException(
//                            getCode("C3PR.EXCEPTION.STUDYSITE.NEED_APPROVAL_FROM_CO_CENTER.CODE"));
//        }
        studySite.activate();
        if (study.isMultisite() && !studySite.getStudy().getStudyCoordinatingCenter().getHostedMode()
                        && !study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())) {
            List<AbstractMutableDomainObject> domainObjects = new ArrayList<AbstractMutableDomainObject>();
            domainObjects.addAll(studyIdentifiers);
            domainObjects.add(studySite);
            EndPoint endpoint=handleCoordinatingCenterBroadcast(study, APIName.ACTIVATE_STUDY_SITE,
                            domainObjects);
            if(endpoint.getStatus()!=WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                throw c3PRExceptionHelper.getMultisiteException(endpoint.getLastAttemptError());
            }
        }
        studySite=studySiteDao.merge(studySite);
        return studySite;
    }
    
    public void createStudyAtAffiliates(List<Identifier> studyIdentifiers) {
		Study study = getUniqueStudy(studyIdentifiers);
		for (StudySite studySite : study.getStudySites()) {
			createStudyAtAffiliate(studyIdentifiers, studySite
					.getHealthcareSite().getNciInstituteCode());
		}
	}

	public EndPoint createStudyAtAffiliate(List<Identifier> studyIdentifiers,
			String nciInstituteCode) {
		Study study = getUniqueStudy(studyIdentifiers);
		StudySite studySite = study.getStudySite(nciInstituteCode);
		EndPoint endPoint=null;
		if (study.isMultisite()
				&& study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())
				&& studyService.canMultisiteBroadcast(studySite)) {
			List<AbstractMutableDomainObject> domainObjects = new ArrayList<AbstractMutableDomainObject>();
			domainObjects.add(study);
			endPoint=handleAffiliateSiteBroadcast(studySite.getHealthcareSite().getNciInstituteCode(), study,  APIName.CREATE_STUDY_DEFINITION,
					domainObjects);
			updateCoordinatingCenterStatusForStudySite(studySite,
					APIName.CREATE_STUDY_DEFINITION,
					CoordinatingCenterStudyStatus.READY_TO_OPEN);
		}
		return endPoint;
	}
	
	public void createAndOpenStudyAtAffiliates(List<Identifier> studyIdentifiers) {
		Study study = getUniqueStudy(studyIdentifiers);
		for (StudySite studySite : study.getStudySites()) {
			createAndOpenStudyAtAffiliate(studyIdentifiers, studySite
					.getHealthcareSite().getNciInstituteCode());
		}
	}
	
	public EndPoint createAndOpenStudyAtAffiliate(List<Identifier> studyIdentifiers,
			String nciInstituteCode) {
		Study study = getUniqueStudy(studyIdentifiers);
		StudySite studySite = study.getStudySite(nciInstituteCode);
		EndPoint endPoint=null;
		if (study.isMultisite()
				&& study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())
				&& studyService.canMultisiteBroadcast(studySite)) {
			List<AbstractMutableDomainObject> domainObjects = new ArrayList<AbstractMutableDomainObject>();
			domainObjects.add(study);
			endPoint=handleAffiliateSiteBroadcast(studySite.getHealthcareSite().getNciInstituteCode(), study,  APIName.CREATE_AND_OPEN_STUDY,
					domainObjects);
			updateCoordinatingCenterStatusForStudySite(studySite,
					APIName.CREATE_AND_OPEN_STUDY, CoordinatingCenterStudyStatus.OPEN);
		}
		return endPoint;
	}

	public void openStudyAtAffiliates(List<Identifier> studyIdentifiers) {
		Study study = getUniqueStudy(studyIdentifiers);
		for (StudySite studySite : study.getStudySites()) {
			openStudyAtAffiliate(studyIdentifiers, studySite
					.getHealthcareSite().getNciInstituteCode());
		}
	}

	public EndPoint openStudyAtAffiliate(List<Identifier> studyIdentifiers,
			String nciInstituteCode) {
		Study study = getUniqueStudy(studyIdentifiers);
		StudySite studySite = study.getStudySite(nciInstituteCode);
		EndPoint endPoint=null;
		if (study.isMultisite()
				&& study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())
				&& studyService.canMultisiteBroadcast(studySite)) {
			endPoint=handleAffiliateSiteBroadcast(nciInstituteCode, study,
					APIName.OPEN_STUDY, studyIdentifiers);
			updateCoordinatingCenterStatusForStudySite(studySite,
					APIName.OPEN_STUDY, CoordinatingCenterStudyStatus.OPEN);
		}
		return endPoint;
	}

	public void amendStudyAtAffiliates(List<Identifier> studyIdentifiers,
			Study amendedStudyDetails) {
		// TODO Auto-generated method stub

	}

	public EndPoint closeStudyToAccrualAtAffiliate(List<Identifier> studyIdentifiers,
			String nciInstituteCode) {
		Study study = getUniqueStudy(studyIdentifiers);
		StudySite studySite = study.getStudySite(nciInstituteCode);
		EndPoint endPoint=null;
		if (study.isMultisite()
				&& study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())
				&& studyService.canMultisiteBroadcast(studySite)) {
			endPoint=handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.CLOSE_STUDY_TO_ACCRUAL,
					studyIdentifiers);
			updateCoordinatingCenterStatusForStudySite(studySite,
					APIName.CLOSE_STUDY_TO_ACCRUAL,
					CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
		}
		return endPoint;
	}

	public void closeStudyToAccrualAtAffiliates(List<Identifier> studyIdentifiers) {
		Study study = getUniqueStudy(studyIdentifiers);
		for (StudySite studySite : study.getStudySites()) {
			closeStudyToAccrualAtAffiliate(studyIdentifiers, studySite
					.getHealthcareSite().getNciInstituteCode());
		}
	}
	
	public EndPoint closeStudyToAccrualAndTreatmentAtAffiliate(List<Identifier> studyIdentifiers,
			String nciInstituteCode) {
		Study study = getUniqueStudy(studyIdentifiers);
		StudySite studySite = study.getStudySite(nciInstituteCode);
		EndPoint endPoint=null;
		if (study.isMultisite()
				&& study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())
				&& studyService.canMultisiteBroadcast(studySite)) {
			endPoint=handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.CLOSE_STUDY_TO_ACCRUAL_AND_TREATMENT,
					studyIdentifiers);
			updateCoordinatingCenterStatusForStudySite(studySite,
					APIName.CLOSE_STUDY_TO_ACCRUAL_AND_TREATMENT,
					CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		}
		return endPoint;
	}

	public void closeStudyToAccrualAndTreatmentAtAffiliates(List<Identifier> studyIdentifiers) {
		Study study = getUniqueStudy(studyIdentifiers);
		for (StudySite studySite : study.getStudySites()) {
			closeStudyToAccrualAndTreatmentAtAffiliate(studyIdentifiers, studySite
					.getHealthcareSite().getNciInstituteCode());
		}
	}
	
	public EndPoint temporarilyCloseStudyToAccrualAtAffiliate(List<Identifier> studyIdentifiers,
			String nciInstituteCode) {
		Study study = getUniqueStudy(studyIdentifiers);
		StudySite studySite = study.getStudySite(nciInstituteCode);
		EndPoint endPoint=null;
		if (study.isMultisite()
				&& study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())
				&& studyService.canMultisiteBroadcast(studySite)) {
			endPoint=handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.TEMPORARILY_CLOSE_STUDY_TO_ACCRUAL,
					studyIdentifiers);
			updateCoordinatingCenterStatusForStudySite(studySite,
					APIName.TEMPORARILY_CLOSE_STUDY_TO_ACCRUAL,
					CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL);
		}
		return endPoint;
	}

	public void temporarilyCloseStudyToAccrualAtAffiliates(List<Identifier> studyIdentifiers) {
		Study study = getUniqueStudy(studyIdentifiers);
		for (StudySite studySite : study.getStudySites()) {
			temporarilyCloseStudyToAccrualAtAffiliate(studyIdentifiers, studySite
					.getHealthcareSite().getNciInstituteCode());
		}
	}
	
	public EndPoint temporarilyCloseStudyToAccrualAndTreatmentAtAffiliate(List<Identifier> studyIdentifiers,
			String nciInstituteCode) {
		Study study = getUniqueStudy(studyIdentifiers);
		StudySite studySite = study.getStudySite(nciInstituteCode);
		EndPoint endPoint=null;
		if (study.isMultisite()
				&& study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())
				&& studyService.canMultisiteBroadcast(studySite)) {
			endPoint=handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.TEMPORARILY_CLOSE_STUDY_TO_ACCRUAL_AND_TREATMENT,
					studyIdentifiers);
			updateCoordinatingCenterStatusForStudySite(studySite,
					APIName.TEMPORARILY_CLOSE_STUDY_TO_ACCRUAL_AND_TREATMENT,
					CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);
		}
		return endPoint;
	}

	public void temporarilyCloseStudyToAccrualAndTreatmentAtAffiliates(List<Identifier> studyIdentifiers) {
		Study study = getUniqueStudy(studyIdentifiers);
		for (StudySite studySite : study.getStudySites()) {
			temporarilyCloseStudyToAccrualAndTreatmentAtAffiliate(studyIdentifiers, studySite
					.getHealthcareSite().getNciInstituteCode());
		}
	}

	public void updateAffliateProtocolVersion(
			List<Identifier> studyIdentifiers, String nciInstituteCode,
			String version) {
		// TODO Auto-generated method stub

	}

	public void updateStudyStatusAtAffiliates(
			List<Identifier> studyIdentifiers,
			CoordinatingCenterStudyStatus status) {
		// TODO Auto-generated method stub

	}

	public void updateCoordinatingCenterStatusForStudySite(StudySite studySite,
			APIName apiName, CoordinatingCenterStudyStatus status) {
		if (!studySite.getHostedMode()) {
			EndPoint endPoint = studySite.getEndPoint(
					ServiceName.STUDY, apiName);
			if (endPoint != null
					&& endPoint.getStatus() == WorkFlowStatusType.MESSAGE_SEND_CONFIRMED) {
				studySite.setCoordinatingCenterStudyStatus(status);
			}
		}
	}
    
    public EndPoint handleAffiliateSiteBroadcast(String nciInstituteCode,
			Study study, APIName multisiteAPIName, List domainObjects) {
        for(EndPoint endPoint: study.getStudySite(nciInstituteCode).getEndpoints()){
        	endPoint.getErrors().size();
        	studyDao.evict(endPoint);
        }
		return studyService.handleMultiSiteBroadcast(study.getStudySite(nciInstituteCode),
				ServiceName.STUDY, multisiteAPIName, domainObjects);
	}

	public void handleAffiliateSitesBroadcast(Study study,
			APIName multisiteAPIName, List domainObjects) {
		for (StudyOrganization studyOrganization : study
				.getAffiliateStudySites()) {
			for(EndPoint endPoint: studyOrganization.getEndpoints()){
				endPoint.getErrors().size();
	        	studyDao.evict(endPoint);
	        }
			studyService.handleMultiSiteBroadcast(studyOrganization,
					ServiceName.STUDY, multisiteAPIName, domainObjects);
		}
	}

	public EndPoint handleCoordinatingCenterBroadcast(Study study,
			APIName multisiteAPIName, List domainObjects) {
		for(EndPoint endPoint: study.getStudyCoordinatingCenters().get(0).getEndpoints()){
			endPoint.getErrors().size();
        	studyDao.evict(endPoint);
        }
		return studyService.handleMultiSiteBroadcast(study.getStudyCoordinatingCenters()
				.get(0), ServiceName.STUDY, multisiteAPIName,
				domainObjects);
	}

    public void setStudyFactory(StudyFactory studyFactory) {
		this.studyFactory = studyFactory;
	}

    public void setStudySiteDao(StudySiteDao studySiteDao) {
        this.studySiteDao = studySiteDao;
    }
    public Study getUniqueStudy(List<Identifier> studyIdentifiers) {
	        List<Study> studies = studyDao.getByIdentifiers(studyIdentifiers);
        if (studies.size() == 0) {
            throw this.c3PRExceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.NOT_FOUND_GIVEN_IDENTIFIERS.CODE"));
        }
        else if (studies.size() > 1) {
            throw this.c3PRExceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.MULTIPLE_STUDIES_FOUND.CODE"));
        }
        return studies.get(0);
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    @Transient
    public int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }

    public void reassociate(Study study) {
        studyDao.reassociate(study);
    }

    public void refresh(Study study) {
        studyDao.refresh(study);
    }

    public void clear() {
        studyDao.clear();
    }

    public void setStudyService(StudyService studyService) {
        this.studyService = studyService;
    }

    public void setC3PRExceptionHelper(C3PRExceptionHelper exceptionHelper) {
        c3PRExceptionHelper = exceptionHelper;
    }

    public void setC3prErrorMessages(MessageSource errorMessages) {
        c3prErrorMessages = errorMessages;
    }
    

}
