package edu.duke.cabig.c3pr.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.CalloutRandomization;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PhoneCallRandomization;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyAmendment;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * Services for Study related domain object
 * 
 * @author Priyatam
 * @see edu.duke.cabig.c3pr.service.StudyService
 */
public class StudyServiceImpl extends CCTSWorkflowServiceImpl implements StudyService {

    private StudyDao studyDao;

    private StudySubjectDao studySubjectDao;

    private HealthcareSiteDao healthcareSiteDao;

    private Logger log = Logger.getLogger(StudyService.class);

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }


    /**
     * Search using a sample populate Study object
     * 
     * @param study
     *                the study object
     * @return List of Study objects based on the sample study object
     * @throws Exception
     *                 runtime exception object
     */
    public List<Study> search(Study study) throws Exception {
        return studyDao.searchByExample(study, true);
    }

    /**
     * Saves a study object
     * 
     * @param study
     *                the study object
     * @throws Exception
     *                 runtime exception object
     */
    public void save(Study study) throws C3PRCodedException {
        // TODO call esb to broadcast protocol, POC
        studyDao.save(study);
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    public StudySubjectDao getStudyParticipantDao() {
        return studySubjectDao;
    }

    public void setStudyParticipantDao(StudySubjectDao studyParticipantDao) {
        this.studySubjectDao = studyParticipantDao;
    }

    /**
     * Assigns a Participant to a Study at a particular Site. The Study and Site must already exist
     * and be associated.
     * 
     * @param study
     * @param participant
     * @param site
     * @return StudySubject for the Participant
     */
    
    //TODO The following commented out method is not being used and it will be removed soon
   /* public StudySubject assignParticipant(Study study, Participant participant,
                    HealthcareSite site, Date enrollmentDate) {
        // new assignment
        StudySubject assignment = new StudySubject();

        // study shld exist
        Study assignedStudy = studyDao.getByGridId(study.getGridId());

        assignment.setParticipant(participant);
        assignment.setStartDate(enrollmentDate);
        studySubjectDao.save(assignment);

        return assignment;
    }*/


    public Study merge(Study study) {
        return studyDao.merge(study);
    }

    public StudySubjectDao getStudySubjectDao() {
        return studySubjectDao;
    }

    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

    public Study reassociate(Study study) {
        studyDao.reassociate(study);
        return study;
    }

    public Study refresh(Study study) {
        studyDao.refresh(study);
        return study;
    }

    //TODO move to StudyRepository 
   /* public List<Study> searchByCoOrdinatingCenterId(OrganizationAssignedIdentifier identifier)
                    throws C3PRCodedException {
        HealthcareSite healthcareSite = this.healthcareSiteDao.getByNciInstituteCode(identifier
                        .getHealthcareSite().getNciInstituteCode());
        if (healthcareSite == null) {
            throw getExceptionHelper()
                            .getException(
                                            getCode("C3PR.EXCEPTION.REGISTRATION.INVALID.HEALTHCARESITE_IDENTIFIER.CODE"),
                                            new String[] {
                                                    identifier.getHealthcareSite()
                                                                    .getNciInstituteCode(),
                                                    identifier.getType() });
        }
        identifier.setHealthcareSite(healthcareSite);
        return studyDao.searchByOrgIdentifier(identifier);
    }*/
}
