package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.domain.factory.StudyFactory;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.StudyValidationException;
import edu.duke.cabig.c3pr.service.impl.StudyXMLImporterServiceImpl;

@Transactional
public class StudyRepositoryImpl implements StudyRepository {

    private StudyDao studyDao;

    private HealthcareSiteDao healthcareSiteDao;

    private Logger log = Logger.getLogger(StudyXMLImporterServiceImpl.class.getName());

    private C3PRExceptionHelper c3PRExceptionHelper;

    private MessageSource c3prErrorMessages;

    private StudySiteDao studySiteDao;

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
                throw new StudyValidationException("Study already exists.");
            }

            for (StudyOrganization organization : study.getStudyOrganizations()) {
                if (healthcareSiteDao.getByPrimaryIdentifier(organization.getHealthcareSite()
                                .getPrimaryIdentifier()) == null) {
                    throw new StudyValidationException(
                                    "Could not find Organization with NCI Institute code:"
                                                    + organization.getHealthcareSite()
                                                                    .getPrimaryIdentifier());
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
        HealthcareSite healthcareSite = this.healthcareSiteDao.getByPrimaryIdentifier(identifier
                        .getHealthcareSite().getPrimaryIdentifier());
        if (healthcareSite == null) {
            throw c3PRExceptionHelper.getException(
                            getCode("C3PR.EXCEPTION.STUDY.INVALID.HEALTHCARESITE_IDENTIFIER.CODE"),
                            new String[] { identifier.getHealthcareSite().getPrimaryIdentifier(),
                                    identifier.getType().getName() });
        }
        identifier.setHealthcareSite(healthcareSite);
        return studyDao.searchByOrgIdentifier(identifier);
    }

    @Transactional
    public StudySite activateStudySite(List<Identifier> studyIdentifiers, String ctepCode, Date effectiveDate){
        Study study = getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(ctepCode);
        return activateStudySite(studyIdentifiers, studySite, effectiveDate);
    }

    @Transactional
    public Study amendStudy(List<Identifier> studyIdentifiers, Study amendedStudyDetails){
        throw new RuntimeException("Not yet implemented");
    }

    @Transactional
    public Study closeStudyToAccrual(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        study.closeToAccrual();
        return this.merge(study);
    }

    @Transactional
    public Study closeStudyToAccrualAndTreatment(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        study.closeToAccrualAndTreatment();
        return this.merge(study);
    }

    @Transactional
    public Study temporarilyCloseStudy(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        study.temporarilyCloseToAccrual();
        return this.merge(study);
    }

    @Transactional
    public Study temporarilyCloseStudyToAccrualAndTreatment(List<Identifier> studyIdentifiers) {
        Study study = getUniqueStudy(studyIdentifiers);
        study.temporarilyCloseToAccrualAndTreatment();
        return this.merge(study);
    }

    @Transactional
    public Study updateStudyStatus(List<Identifier> studyIdentifiers,
                    CoordinatingCenterStudyStatus status){
        throw new RuntimeException("Not Implemented");
    }

    @Transactional
    public StudySite closeStudySiteToAccrual(List<Identifier> studyIdentifiers, String nciInstituteCode, Date effectiveDate){
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        studySite.closeToAccrual(effectiveDate);
        studySite=studySiteDao.merge(studySite);
        return studySite;
    }

    @Transactional
    public StudySite closeStudySiteToAccrualAndTreatment(List<Identifier> studyIdentifiers, String nciInstituteCode, Date effectiveDate) {
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        studySite.closeToAccrualAndTreatment(effectiveDate);
        studySite=studySiteDao.merge(studySite);
        return studySite;
    }

    @Transactional
    public StudySite temporarilyCloseStudySiteToAccrual(List<Identifier> studyIdentifiers, String nciInstituteCode, Date effectiveDate) {
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        studySite.temporarilyCloseToAccrual(effectiveDate);
        studySite=studySiteDao.merge(studySite);
        return studySite;
    }

    @Transactional
    public StudySite temporarilyCloseStudySiteToAccrualAndTreatment(List<Identifier> studyIdentifiers, String nciInstituteCode, Date effectiveDate) {
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(nciInstituteCode);
        studySite.temporarilyCloseToAccrualAndTreatment(effectiveDate);
        studySite=studySiteDao.merge(studySite);
        return studySite;
    }

    @Transactional
    public List<StudySite> closeStudySites(List<Identifier> studyIdentifiers, Date effectiveDate){
        Study study = getUniqueStudy(studyIdentifiers);
        for (StudySite studySite : study.getStudySites()) {
            studySite.closeToAccrual(effectiveDate);
            studySiteDao.save(studySite);
        }
        return study.getStudySites();
    }

    @Transactional
    public Study createStudy(Study study) {

        study.readyToOpen();
        return this.merge(study);
    }

    @Transactional
    public Study createAndOpenStudy(Study study) {
        study.readyToOpen();
        study.open();
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
        return this.merge(study);
    }

    @Transactional
    public StudySite updateStudySiteProtocolVersion(List<Identifier> studyIdentifiers,
                    String nciInstituteCode, String version) {
        throw new RuntimeException("Not yet implemented");

    }

    public StudySite activateStudySite(List<Identifier> studyIdentifiers, StudySite studySiteObj, Date effectiveDate){
        Study study = getUniqueStudy(studyIdentifiers);
        String nciInstituteCode = studySiteObj.getHealthcareSite().getPrimaryIdentifier();
        StudySite studySite;
        CompanionStudyAssociation companionStudyAssociaton = studySiteObj.getCompanionStudyAssociation() ;
        if(companionStudyAssociaton != null ){
        	studySite = study.getCompanionStudySite(nciInstituteCode);
        }else{
        	studySite = study.getStudySite(nciInstituteCode);
        }
        studySite.activate(effectiveDate);
        studySite=studySiteDao.merge(studySite);
        return studySite;
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

    public void setC3PRExceptionHelper(C3PRExceptionHelper exceptionHelper) {
        c3PRExceptionHelper = exceptionHelper;
    }

    public void setC3prErrorMessages(MessageSource errorMessages) {
        c3prErrorMessages = errorMessages;
    }

    public Study createAmendment(List<Identifier> identifiers){
		Study study = getUniqueStudy(identifiers);
		study.createAmendment();
		return study;
    }

	public Study applyAmendment(List<Identifier> identifiers) {
		Study study = getUniqueStudy(identifiers);
		study.applyAmendment();
		return this.merge(study);
	}

	public Study applyAmendment(List<Identifier> identifiers, StudyVersion studyVersion) {
		Study study = getUniqueStudy(identifiers);
		study.applyAmendment(studyVersion);
		return this.merge(study);
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.repository.StudyRepository#getByIdentifiers(java.util.List)
	 */
	public List<Study> getByIdentifiers(List<Identifier> studyIdentifiers) {
		return studyDao.getByIdentifiers(studyIdentifiers); 
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.repository.StudyRepository#search(java.util.List)
	 */
	public List<Study> search(
			List<AdvancedSearchCriteriaParameter> searchParameters) {
		return studyDao.search(searchParameters);
	}

}
