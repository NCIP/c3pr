package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.DefaultCCTSMessageWorkflowCallbackFactory;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Services for Study related domain object
 *
 * @author Priyatam
 * @see edu.duke.cabig.c3pr.service.StudyService
 */
public class StudyServiceImpl implements StudyService {

    private C3PRExceptionHelper exceptionHelper;
    private MessageSource c3prErrorMessages;

    private edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster messageBroadcaster;
    private XmlMarshaller xmlUtility;
    private DefaultCCTSMessageWorkflowCallbackFactory cctsMessageWorkflowCallbackFactory;

    private Logger log = Logger.getLogger(StudyService.class);

    public void setC3prErrorMessages(MessageSource errorMessages) {
        c3prErrorMessages = errorMessages;
    }

    public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
        this.exceptionHelper = exceptionHelper;
    }

    public void setSiteStudyStatuses(Study study) throws Exception {
        for (int i = 0; i < study.getStudySites().size(); i++) {
            study.getStudySites().get(i).setSiteStudyStatus(
                    evaluateSiteStudyStatus(study.getStudySites().get(i)));
        }

    }

    StudyDao studyDao;

    StudySubjectDao studySubjectDao;

    private HealthcareSiteDao healthcareSiteDao;

    // TODO hook esb call
    // ProtocolBroadcastService esbCreateProtocol;

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

    public void setDataEntryStatus(Study study, boolean throwException) throws Exception {

        if (throwException == false) {
            try {
                study.setDataEntryStatus(evaluateDataEntryStatus(study));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //	e.printStackTrace();
            }
        } else {
            study.setDataEntryStatus(evaluateDataEntryStatus(study));
        }
    }

    public CCTSWorkflowStatusType getCCTSWofkflowStatus(Study study) {
        // study shld exist
        Study loadedStudy = studyDao.getByGridId(study.getGridId());
        return loadedStudy.getCctsWorkflowStatus();

    }


    public void broadcastStudyMessage(Study study) throws C3PRBaseException {
        messageBroadcaster.setNotificationHandler(cctsMessageWorkflowCallbackFactory.createWorkflowCallback(study));
        try {
            messageBroadcaster.broadcast(xmlUtility.toXML(study), study.getGridId());
        } catch (Exception e) {
            log.error("Could not send message to ESB", e);
            throw new C3PRBaseException(e.getMessage());
        }
    }

    public CoordinatingCenterStudyStatus evaluateCoordinatingCenterStudyStatus(
            Study study) throws Exception {
        if (evaluateDataEntryStatus(study) != StudyDataEntryStatus.COMPLETE) {
            return CoordinatingCenterStudyStatus.PENDING;
        }
        if (study.getId() != null) {
            if (evaluateAmendmentStatus(study) != StudyDataEntryStatus.COMPLETE) {
                return CoordinatingCenterStudyStatus.AMENDMENT_PENDING;
            }
        }

        return CoordinatingCenterStudyStatus.ACTIVE;
    }

    public StudyDataEntryStatus evaluateTreatmentEpochDataEntryStatus(
            Study study) throws Exception {
        if (study.getTreatmentEpochs().size() > 0) {
            for (TreatmentEpoch treatmentEpoch : study.getTreatmentEpochs()) {
                if (treatmentEpoch.getRandomizedIndicator()) {
                    if ((treatmentEpoch.getArms().size() < 2) || (!treatmentEpoch.hasStratumGroups()) || (treatmentEpoch.getRandomization() == null)) {
                        if (treatmentEpoch.getArms().size() < 2) {
                            throw new Exception("There should be at least 2 arms for randomization for treatment epoch: " + treatmentEpoch.getName());
                        }
                        if (!treatmentEpoch.hasStratumGroups()) {
                            throw new Exception("There are no stratum groups for treatment epoch: " + treatmentEpoch.getName());
                        }
                        if (treatmentEpoch.getRandomization() == null) {
                            throw new Exception("There are no radomization entries for treatment epoch: " + treatmentEpoch.getName());
                        }
                        return StudyDataEntryStatus.INCOMPLETE;
                    }
                }
            }
        }
        return StudyDataEntryStatus.COMPLETE;
    }

    public StudyDataEntryStatus evaluateStratificationDataEntryStatus(
            Study study) throws Exception {
        if (study.hasStratification()) {
            for (TreatmentEpoch treatmentEpoch : study.getTreatmentEpochs()) {
                if (treatmentEpoch.hasStratification()) {
                    if (!treatmentEpoch.hasStratumGroups()) {
                        throw new Exception("There are no stratum groups for treatment epoch: " + treatmentEpoch.getName());
                    }
                }
            }
        }

        for (TreatmentEpoch treatmentEpoch : study.getTreatmentEpochs()) {
            if (treatmentEpoch.getRandomizedIndicator() == Boolean.TRUE) {
                if (!treatmentEpoch.hasStratification() || !treatmentEpoch.hasStratumGroups()) {
                    throw new Exception("Stratification or/and stratum groups are missing for treatment epoch: " + treatmentEpoch.getName());
                }
            }
        }

        return StudyDataEntryStatus.COMPLETE;
    }

    public StudyDataEntryStatus evaluateRandomizationDataEntryStatus(Study study)
            throws Exception {

        if (study.getRandomizationType() == (RandomizationType.BOOK)) {
            for (TreatmentEpoch treatmentEpoch : study.getTreatmentEpochs()) {
                if (treatmentEpoch.hasBookRandomizationEntry()) {
                    if (!treatmentEpoch.hasStratumGroups()) {
                        throw new Exception("Stratum groups are missing for treatment epoch: " + treatmentEpoch.getName());
                    }
                }
            }
            return StudyDataEntryStatus.COMPLETE;
        }

        if (study.getRandomizationType() == (RandomizationType.PHONE_CALL)) {
            for (TreatmentEpoch treatmentEpoch : study.getTreatmentEpochs()) {
                Randomization randomization = treatmentEpoch.getRandomization();
                if (randomization instanceof PhonecallRandomization) {
                    if (StringUtils
                            .isBlank(((PhonecallRandomization) randomization)
                                    .getPhoneNumber())) {
                        throw new Exception("Treatment epoch: " + treatmentEpoch.getName() + " needs phone number for randomization");
                    }
                }
            }
        }

        if (study.getRandomizationType() == (RandomizationType.CALL_OUT)) {
            for (TreatmentEpoch treatmentEpoch : study.getTreatmentEpochs()) {
                Randomization randomization = treatmentEpoch.getRandomization();
                if (randomization instanceof CalloutRandomization) {
                    if (StringUtils
                            .isBlank(((CalloutRandomization) randomization)
                                    .getCalloutUrl())) {
                        throw new Exception("Treatment epoch: " + treatmentEpoch.getName() + " needs call out URL for randomization");
                    }
                }
            }
        }
        return StudyDataEntryStatus.COMPLETE;
    }

    public StudyDataEntryStatus evaluateEligibilityDataEntryStatus(Study study)
            throws Exception {

        //TODO  //Disabled unless more information is obtained on managing the eligibility criteria for all epochs

        /*if (study.hasElligibility()){
              return StudyDataEntryStatus.COMPLETE;
          } else {
              if (study.getId()!=null){
                  throw new Exception("Study needs eligibility criteria");
                  }
              return StudyDataEntryStatus.INCOMPLETE;
          }*/
        return StudyDataEntryStatus.COMPLETE;

    }

    public StudyDataEntryStatus evaluateAmendmentStatus(Study study)
            throws Exception {

        if (study.getStudyAmendments().size() > 0) {
            StudyAmendment latestAmendment = study.getStudyAmendments().get(
                    study.getStudyAmendments().size() - 1);

            if ((latestAmendment.getAmendmentDate() == null)
                    || (latestAmendment.getVersion() == null)) {
                if ((study.getId() != null && (latestAmendment.getAmendmentDate() == null))) {
                    throw new Exception("The latest amendment needs amendment date");
                }
                if ((study.getId() != null && (latestAmendment.getVersion() == null))) {
                    throw new Exception("The latest amendment needs version");
                }
                return StudyDataEntryStatus.INCOMPLETE;
            }
            if ((latestAmendment.getAmendmentDate() != null)
                    && (latestAmendment.getAmendmentDate().after(new Date()))) {
                if ((study.getId() != null)) {
                    throw new Exception("The latest amendment does not have an amendment date or is invalid");
                }
                return StudyDataEntryStatus.INCOMPLETE;
            }
            if ((latestAmendment.getConsentChangedIndicator())
                    || (latestAmendment.getDiseasesChangedIndicator())
                    || (latestAmendment.getEligibilityChangedIndicator())
                    || (latestAmendment.getEaChangedIndicator())
                    || (latestAmendment.getStratChangedIndicator())
                    || (latestAmendment.getPiChangedIndicator())
                    || (latestAmendment.getRandomizationChangedIndicator())) {
                return StudyDataEntryStatus.COMPLETE;
            } else {
                if ((study.getId() != null)) {
                    throw new Exception("At least one item should be marked as changeable for amendment");
                }
                return StudyDataEntryStatus.INCOMPLETE;
            }

        }
        return StudyDataEntryStatus.COMPLETE;
    }

    public StudyDataEntryStatus evaluateDataEntryStatus(Study study)
            throws Exception {

        if ((study.getStudySites().size() == 0) || (!study.hasEnrollingEpoch())) {
            if ((study.getStudySites().size() == 0)) {
                throw new Exception("Study does not have a study site");
            }
            if ((!study.hasEnrollingEpoch())) {
                throw new Exception("Study needs an enrolling epoch");
            }
            return StudyDataEntryStatus.INCOMPLETE;
        }

        if (evaluateEligibilityDataEntryStatus(study) != StudyDataEntryStatus.COMPLETE)
            return StudyDataEntryStatus.INCOMPLETE;
        if (study.hasStratification()) {
            if (evaluateStratificationDataEntryStatus(study) != StudyDataEntryStatus.COMPLETE)
                return StudyDataEntryStatus.INCOMPLETE;
        }
        if (study.getRandomizedIndicator()) {
            if (evaluateRandomizationDataEntryStatus(study) != StudyDataEntryStatus.COMPLETE) {
                return StudyDataEntryStatus.INCOMPLETE;
            }
        }
        if (study.getTreatmentEpochs().size() > 0) {
            if (evaluateTreatmentEpochDataEntryStatus(study) != StudyDataEntryStatus.COMPLETE)
                return StudyDataEntryStatus.INCOMPLETE;
        }

        return StudyDataEntryStatus.COMPLETE;
    }

    public SiteStudyStatus evaluateSiteStudyStatus(StudySite studySite) throws Exception {

        if (studySite.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL) {
            return SiteStudyStatus.CLOSED_TO_ACCRUAL;
        }
        if (studySite.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
            return SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT;
        }

        if (studySite.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL) {
            return SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL;
        }
        if (studySite.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT) {
            return SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT;
        }

        if ((studySite.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.AMENDMENT_PENDING)
                && (studySite.getSiteStudyStatus() == SiteStudyStatus.ACTIVE)) {
            return SiteStudyStatus.AMENDMENT_PENDING;
        }
        if (studySite.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.ACTIVE) {

            Date currentDate = new Date();
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(currentDate);
            calendar.add(calendar.YEAR, -1);
            if (((studySite.getIrbApprovalDate()) == null)
                    || (studySite.getIrbApprovalDate().after(currentDate)) || (studySite.getIrbApprovalDate().before(calendar.getTime()))) {
                if ((studySite.getId() != null)) {
                    throw new Exception("Study site: " + studySite.getHealthcareSite().getName() + " does not have an IRB approval date or is invalid");
                }
                return SiteStudyStatus.PENDING;
            }
            if ((studySite.getStartDate() == null)
                    || (studySite.getStartDate().after(currentDate))) {
                if ((studySite.getId() != null)) {
                    throw new Exception("Study site: " + studySite.getHealthcareSite().getName() + " does not have a start date or is invalid");
                }
                return SiteStudyStatus.PENDING;
            }
            return SiteStudyStatus.ACTIVE;
        }

        return SiteStudyStatus.PENDING;
    }

    /**
     * Search using a sample populate Study object
     *
     * @param study the study object
     * @return List of Study objects based on the sample study object
     * @throws Exception runtime exception object
     */
    public List<Study> search(Study study) throws Exception {
        return studyDao.searchByExample(study, true);
    }

    /**
     * Saves a study object
     *
     * @param study the study object
     * @throws Exception runtime exception object
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
     * Assigns a Participant to a Study at a particular Site. The Study and Site
     * must already exist and be associated.
     *
     * @param study
     * @param participant
     * @param site
     * @return StudySubject for the Participant
     */
    public StudySubject assignParticipant(Study study, Participant participant,
                                          HealthcareSite site, Date enrollmentDate) {
        // new assignment
        StudySubject assignment = new StudySubject();

        // study shld exist
        Study assignedStudy = studyDao.getByGridId(study.getGridId());

        assignment.setParticipant(participant);
        assignment.setStartDate(enrollmentDate);
        studySubjectDao.save(assignment);

        return assignment;
    }

    public Study setStatuses(Study study, boolean throwException) throws Exception {
        if (!throwException) {
            try {
                study.setDataEntryStatus(evaluateDataEntryStatus(study));
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                // e1.printStackTrace();
            }
        } else {
            study.setDataEntryStatus(evaluateDataEntryStatus(study));
        }

        // For a new study, the coordingating center status should be set to
        // Pending.
        if (study.getId() == null) {
            study
                    .setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
        } else {
            if (!throwException) {
                try {
                    study.setCoordinatingCenterStudyStatus(evaluateCoordinatingCenterStudyStatus(study));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                study.setCoordinatingCenterStudyStatus(evaluateCoordinatingCenterStudyStatus(study));
            }
        }

        if (!throwException) {
            try {
                for (int i = 0; i < study.getStudySites().size(); i++) {
                    study.getStudySites().get(i).setSiteStudyStatus(
                            evaluateSiteStudyStatus(study.getStudySites().get(i)));
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < study.getStudySites().size(); i++) {
                study.getStudySites().get(i).setSiteStudyStatus(
                        evaluateSiteStudyStatus(study.getStudySites().get(i)));
            }
        }

        return study;
    }

    public Study setStatuses(Study study,
                             CoordinatingCenterStudyStatus targetStatus) throws Exception {

        study.setDataEntryStatus(evaluateDataEntryStatus(study));
        CoordinatingCenterStudyStatus oldStatus = study
                .getCoordinatingCenterStudyStatus();

        // For a new study, the coordingating center status should be set to
        // Pending.
        if (study.getId() == null) {
            study
                    .setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
        } else {
            if (statusSettable(study, targetStatus)) {
                study.setCoordinatingCenterStudyStatus(targetStatus);
            } else {
                study.setCoordinatingCenterStudyStatus(oldStatus);
            }
        }
        return study;
    }

    public boolean statusSettable(Study study,
                                  CoordinatingCenterStudyStatus newCoordinatingCenterStatus) throws Exception {

        // For a new study, the coordingating center status should not be
        // settable.
        if (study.getId() == null) {
            return false;
        }

        CoordinatingCenterStudyStatus evaluatedStatus = evaluateCoordinatingCenterStudyStatus(study);
        CoordinatingCenterStudyStatus currentStatus = study
                .getCoordinatingCenterStudyStatus();
        if (newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.ACTIVE) {

            if (((evaluatedStatus) == (CoordinatingCenterStudyStatus.PENDING))
                    || ((evaluatedStatus) == (CoordinatingCenterStudyStatus.AMENDMENT_PENDING))
                    || ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL))
                    || ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
                {
                    if ((study.getId() != null)) {
                        throw new Exception("Cannot set status to Active from " + currentStatus.getDisplayName());
                    }
                    return false;
                }
            }
        }

        if ((newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL)
                || (newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
            if (((currentStatus) == (CoordinatingCenterStudyStatus.PENDING))
                    || ((currentStatus) == (CoordinatingCenterStudyStatus.AMENDMENT_PENDING))
                    || ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL))
                    || ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
                if ((study.getId() != null)) {
                    throw new Exception("The study status has to be in Active status before setting it to: " + newCoordinatingCenterStatus.getDisplayName());
                }
                return false;
            }
        }

        if ((newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL)
                || (newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
            if (((currentStatus) == (CoordinatingCenterStudyStatus.PENDING))
                    || ((currentStatus) == (CoordinatingCenterStudyStatus.AMENDMENT_PENDING))
                    || ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
                if ((study.getId() != null)) {
                    throw new Exception("The Study status has to be Active or Temporarily Closed before setting it to: " + newCoordinatingCenterStatus.getDisplayName());
                }
                return false;
            }
        }

        return true;
    }

    public Study setSiteStudyStatus(Study study, StudySite studySite,
                                    SiteStudyStatus status) throws Exception {
        SiteStudyStatus currentSiteStatus = studySite.getSiteStudyStatus();
        if ((status == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL)
                || (status == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
            if (((currentSiteStatus) == (SiteStudyStatus.ACTIVE))
                    || ((currentSiteStatus) == (SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL))) {
                studySite.setSiteStudyStatus(status);
            }
        } else if ((status == SiteStudyStatus.CLOSED_TO_ACCRUAL)
                || (status == SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
            if (((currentSiteStatus) == (SiteStudyStatus.PENDING))
                    || ((currentSiteStatus) == (SiteStudyStatus.AMENDMENT_PENDING))
                    || ((currentSiteStatus) == (SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
                if ((study.getId() != null)) {
                    throw new Exception("The Site Study status has to be Active before setting it to: " + status.getDisplayName());
                }
                return study;
            } else
                studySite.setSiteStudyStatus(status);
        } else {
            if (status == evaluateSiteStudyStatus(studySite)) {
                studySite.setSiteStudyStatus(status);
            } else {
                if ((study.getId() != null)) {
                    throw new Exception("The site study status cannot be set to: " + status.getDisplayName() + " when the coordinating center status is: " + study.getCoordinatingCenterStudyStatus().getDisplayName());
                }
            }
        }
        return study;
    }

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

    public List<Study> searchByCoOrdinatingCenterId(OrganizationAssignedIdentifier identifier) throws C3PRCodedException {
        HealthcareSite healthcareSite = this.healthcareSiteDao.getByNciInstituteCode(identifier.getHealthcareSite().getNciInstituteCode());
        if (healthcareSite == null) {
            throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.INVALID.HEALTHCARESITE_IDENTIFIER.CODE")
                    , new String[]{identifier.getHealthcareSite().getNciInstituteCode(), identifier.getType()});
        }
        identifier.setHealthcareSite(healthcareSite);
        return studyDao.searchByOrgIdentifier(identifier);
    }

    private int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }


    public CCTSMessageBroadcaster getMessageBroadcaster() {
        return messageBroadcaster;
    }

    public void setMessageBroadcaster(CCTSMessageBroadcaster messageBroadcaster) {
        this.messageBroadcaster = messageBroadcaster;
    }

    public XmlMarshaller getXmlUtility() {
        return xmlUtility;
    }

    public void setXmlUtility(XmlMarshaller xmlUtility) {
        this.xmlUtility = xmlUtility;
    }


    public DefaultCCTSMessageWorkflowCallbackFactory getCctsMessageWorkflowCallbackFactory() {
        return cctsMessageWorkflowCallbackFactory;
    }

    public void setCctsMessageWorkflowCallbackFactory(DefaultCCTSMessageWorkflowCallbackFactory cctsMessageWorkflowCallbackFactory) {
        this.cctsMessageWorkflowCallbackFactory = cctsMessageWorkflowCallbackFactory;
    }
}


