    package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.WorkFlowStatusType;
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
    
    public void setStudyFactory(StudyFactory studyFactory) {
		this.studyFactory = studyFactory;
	}

    public void setStudySiteDao(StudySiteDao studySiteDao) {
        this.studySiteDao = studySiteDao;
    }

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

            if ((study.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.OPEN)) {
                throw new StudyValidationException("Study cannot be imported in ACTIVE status");
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

    public List<Study> searchByExample(Study study, int maxResults) {
        return studyDao.searchByExample(study, maxResults);
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
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        if (!studySite.getHostedMode()
                        && !study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())
                        && studySite.getSiteStudyStatus()!=SiteStudyStatus.APPROVED_FOR_ACTIVTION) {
            throw c3PRExceptionHelper.getRuntimeException(
                            getCode("C3PR.EXCEPTION.STUDYSITE.NEED_APPROVAL_FROM_CO_CENTER.CODE"));
        }
        studySite.activate();
        if (study.canMultisiteBroadcast() && !studySite.getStudy().getStudyCoordinatingCenter().getHostedMode()
                        && !study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())) {
            List<AbstractMutableDomainObject> domainObjects = new ArrayList<AbstractMutableDomainObject>();
            domainObjects.addAll(studyIdentifiers);
            domainObjects.add(study.getStudySite(nciInstituteCode));
            studyService.handleCoordinatingCenterBroadcast(study, APIName.ACTIVATE_STUDY_SITE,
                            domainObjects);
            EndPoint endpoint=study.getStudyCoordinatingCenter().getEndPoint(ServiceName.STUDY, APIName.ACTIVATE_STUDY_SITE);
            if(endpoint!=null && endpoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                studySite=studySiteDao.merge(studySite);
            }else{
                throw c3PRExceptionHelper.getRuntimeException(endpoint.getLastAttemptError());
            }
        }
        studySite=studySiteDao.merge(studySite);
        return studySite;
    }

    @Transactional
    public Study amendStudy(List<Identifier> studyIdentifiers, Study amendedStudyDetails){
        throw new RuntimeException("Not yet implemented");
    }

    @Transactional
    public StudySite approveStudySiteForActivation(List<Identifier> studyIdentifiers,
                    String nciInstituteCode){
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        studySite.approveForActivation();
        if(study.canMultisiteBroadcast() && !studySite.getHostedMode() && !studyService.isStudyOrganizationLocal(nciInstituteCode) && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
            List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
            domainObjects.addAll(studyIdentifiers);
            domainObjects.add(study.getStudySite(nciInstituteCode).getHealthcareSite());
            studyService.handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.APPROVE_STUDY_SITE_FOR_ACTIVATION, domainObjects);
            EndPoint endpoint=studySite.getEndPoint(ServiceName.STUDY, APIName.APPROVE_STUDY_SITE_FOR_ACTIVATION);
            if(endpoint!=null && endpoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                studySite=studySiteDao.merge(studySite);
            }else{
                throw c3PRExceptionHelper.getRuntimeException(endpoint.getLastAttemptError());
            }
        }else{
            studySite=studySiteDao.merge(studySite);
        }
        return studySite;
    }

    @Transactional
    public Study closeStudy(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        study.closeToAccrual();
        study=this.merge(study);
        if(study.canMultisiteBroadcast()){
            if(study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
                studyService.closeStudyAtAffiliates(studyIdentifiers);
            }else{
                study.getStudySite(studyService.getLocalNCIInstituteCode()).setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
                study=this.merge(study);
            }
        }
        return study;
    }

    @Transactional
    public Study closeStudyToAccrualAndTreatment(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        study.closeToAccrualAndTreatment();
        study=this.merge(study);
        if(study.canMultisiteBroadcast()){
            if(study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
                studyService.closeStudyAtAffiliates(studyIdentifiers);
            }else{
                study.getStudySite(studyService.getLocalNCIInstituteCode()).setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
                study=this.merge(study);
            }
        }
        return study;
    }

    @Transactional
    public Study temporarilyCloseStudy(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        study.temporarilyCloseToAccrual();
        study=this.merge(study);
        if(study.canMultisiteBroadcast()){
            if(study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
                studyService.closeStudyAtAffiliates(studyIdentifiers);
            }else{
                study.getStudySite(studyService.getLocalNCIInstituteCode()).setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
                study=this.merge(study);
            }
        }
        return study;
    }

    @Transactional
    public Study temporarilyCloseStudyToAccrualAndTreatment(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        study.temporarilyCloseToAccrualAndTreatment();
        study=this.merge(study);
        if(study.canMultisiteBroadcast()){
            if(study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
                studyService.closeStudyAtAffiliates(studyIdentifiers);
            }else{
                study.getStudySite(studyService.getLocalNCIInstituteCode()).setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
                study=this.merge(study);
            }
        }
        return study;
    }
    
    @Transactional
    public Study updateStudyStatus(List<Identifier> studyIdentifiers,
                    CoordinatingCenterStudyStatus status){
        throw new RuntimeException("Not Implemented");
    }

    @Transactional
    public StudySite closeStudySite(List<Identifier> studyIdentifiers, String nciInstituteCode){
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        studySite.closeToAccrual();
        if(study.canMultisiteBroadcast() && !studySite.getHostedMode() && !studyService.isStudyOrganizationLocal(nciInstituteCode) && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
            List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
            domainObjects.addAll(studyIdentifiers);
            domainObjects.add(study.getStudySite(nciInstituteCode).getHealthcareSite());
            studyService.handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.CLOSE_STUDY_SITE, domainObjects);
            EndPoint endpoint=studySite.getEndPoint(ServiceName.STUDY, APIName.CLOSE_STUDY_SITE);
            if(endpoint!=null && endpoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                studySite=studySiteDao.merge(studySite);
            }else{
                throw c3PRExceptionHelper.getRuntimeException(endpoint.getLastAttemptError());
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
        if(study.canMultisiteBroadcast() && !studySite.getHostedMode() && !studyService.isStudyOrganizationLocal(nciInstituteCode) && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
            List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
            domainObjects.addAll(studyIdentifiers);
            domainObjects.add(study.getStudySite(nciInstituteCode).getHealthcareSite());
            studyService.handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.CLOSE_STUDY_SITE, domainObjects);
            EndPoint endpoint=studySite.getEndPoint(ServiceName.STUDY, APIName.CLOSE_STUDY_SITE);
            if(endpoint!=null && endpoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                studySite=studySiteDao.merge(studySite);
            }else{
                throw c3PRExceptionHelper.getRuntimeException(endpoint.getLastAttemptError());
            }
        }else{
            studySite=studySiteDao.merge(studySite);
        }
        return studySite;
    }

    @Transactional
    public StudySite temporarilyCloseStudySite(List<Identifier> studyIdentifiers,
                    String nciInstituteCode) {
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        studySite.temporarilyCloseToAccrual();
        if(study.canMultisiteBroadcast() && !studySite.getHostedMode() && !studyService.isStudyOrganizationLocal(nciInstituteCode) && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
            List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
            domainObjects.addAll(studyIdentifiers);
            domainObjects.add(study.getStudySite(nciInstituteCode).getHealthcareSite());
            studyService.handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.CLOSE_STUDY_SITE, domainObjects);
            EndPoint endpoint=studySite.getEndPoint(ServiceName.STUDY, APIName.CLOSE_STUDY_SITE);
            if(endpoint!=null && endpoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                studySite=studySiteDao.merge(studySite);
            }else{
                throw c3PRExceptionHelper.getRuntimeException(endpoint.getLastAttemptError());
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
        if(study.canMultisiteBroadcast() && !studySite.getHostedMode() && !studyService.isStudyOrganizationLocal(nciInstituteCode) && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
            List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
            domainObjects.addAll(studyIdentifiers);
            domainObjects.add(study.getStudySite(nciInstituteCode).getHealthcareSite());
            studyService.handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.CLOSE_STUDY_SITE, domainObjects);
            EndPoint endpoint=studySite.getEndPoint(ServiceName.STUDY, APIName.CLOSE_STUDY_SITE);
            if(endpoint!=null && endpoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                studySite=studySiteDao.merge(studySite);
            }else{
                throw c3PRExceptionHelper.getRuntimeException(endpoint.getLastAttemptError());
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
//        if(study.canMultisiteBroadcast() && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
//            studyService.handleAffiliateSitesBroadcast(study, APIName.CLOSE_STUDY_SITES, studyIdentifiers);
//        }
        if(study.canMultisiteBroadcast() && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
            studyService.closeStudyAtAffiliates(studyIdentifiers);
        }
        return study.getStudySites();
    }

    @Transactional
    public Study createStudy(Study study) {
        study.readyToOpen();
        study=this.merge(study);
//        if(study.canMultisiteBroadcast() && study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
//            List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
//            domainObjects.add(study);
//            studyService.handleAffiliateSitesBroadcast(study, APIName.CREATE_STUDY, domainObjects);
//        }
        if(study.canMultisiteBroadcast()){
            if(study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
                studyService.createStudyAtAffiliates(study.getIdentifiers());
            }else{
                try{
                	StudySite studySite = study.getStudySite(studyService.getLocalNCIInstituteCode());
                	studySite.setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
                }catch(C3PRCodedRuntimeException ex){
                	// catching exception
                }
                study=this.merge(study);
            }
        }
        return study;
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
        study= this.merge(study);
        if(study.canMultisiteBroadcast()){
            if(study.isCoOrdinatingCenter(studyService.getLocalNCIInstituteCode())){
                studyService.openStudyAtAffiliates(studyIdentifiers);
            }else{
            	try{
                	StudySite studySite = study.getStudySite(studyService.getLocalNCIInstituteCode());
                	studySite.setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
                }catch(C3PRCodedRuntimeException ex){
                	// catching exception
                }
                study=this.merge(study);
            }
        }
        return study;
    }

    @Transactional
    public StudySite updateStudySiteProtocolVersion(List<Identifier> studyIdentifiers,
                    String nciInstituteCode, String version) {
        throw new RuntimeException("Not yet implemented");

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
