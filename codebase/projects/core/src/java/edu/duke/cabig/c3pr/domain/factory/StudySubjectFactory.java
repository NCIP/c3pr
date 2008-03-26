package edu.duke.cabig.c3pr.domain.factory;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.ParticipantService;
import edu.duke.cabig.c3pr.service.StudyService;

public class StudySubjectFactory {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(StudySubjectFactory.class);
    
    private C3PRExceptionHelper exceptionHelper;

    private MessageSource c3prErrorMessages;
    
    private final String identifierTypeValueStr = "Coordinating Center Identifier";

    private final String prtIdentifierTypeValueStr = "MRN";
    
    private StudyService studyService;

    private ParticipantService participantService;

    public StudySubject buildStudySubject(StudySubject deserializedStudySubject)
                    throws C3PRCodedException {
        StudySubject built = new StudySubject();
        Participant participant = buildParticipant(deserializedStudySubject.getParticipant());
        StudySite studySite = buildStudySite(deserializedStudySubject.getStudySite(),
                        buildStudy(deserializedStudySubject.getStudySite().getStudy()));
        built.setStudySite(studySite);
        built.setParticipant(participant);
        Epoch epoch = buildEpoch(studySite.getStudy().getEpochs(), deserializedStudySubject
                        .getScheduledEpoch());
        ScheduledEpoch scheduledEpoch = buildScheduledEpoch(deserializedStudySubject
                        .getScheduledEpoch(), epoch);
        built.getScheduledEpochs().add(0, scheduledEpoch);
        fillStudySubjectDetails(built, deserializedStudySubject);
        return built;
    }

    private Participant buildParticipant(Participant participant) throws C3PRCodedException {
        if (participant.getIdentifiers() == null || participant.getIdentifiers().size() == 0) {
            throw exceptionHelper.getException(
                            getCode("C3PR.EXCEPTION.REGISTRATION.MISSING.SUBJECT_IDENTIFIER.CODE"));
        }
        for (OrganizationAssignedIdentifier organizationAssignedIdentifier : participant
                        .getOrganizationAssignedIdentifiers()) {
            if (organizationAssignedIdentifier.getType().equals(this.prtIdentifierTypeValueStr)) {
                List<Participant> paList = participantService
                                .searchByMRN(organizationAssignedIdentifier);
                if (paList.size() > 1) {
                    throw exceptionHelper
                                    .getException(
                                                    getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE.SUBJECTS_SAME_MRN.CODE"),
                                                    new String[] { organizationAssignedIdentifier
                                                                    .getValue() });
                }
                else if (paList.size() == 1) {
                    if (logger.isDebugEnabled()) {
                        logger
                                        .debug("buildParticipant(Participant) - Participant with the same MRN found in the database");
                    }
                    Participant temp = paList.get(0);
                    if (temp.getFirstName().equals(participant.getFirstName())
                                    && temp.getLastName().equals(participant.getLastName())
                                    && temp.getBirthDate().getTime() == participant.getBirthDate()
                                                    .getTime()) {
                        return temp;
                    }
                    else {
                        throw exceptionHelper
                                        .getException(
                                                        getCode("C3PR.EXCEPTION.REGISTRATION.INVALID.ANOTHER_SUBJECT_SAME_MRN.CODE"),
                                                        new String[] { organizationAssignedIdentifier
                                                                        .getValue() });
                    }
                }
            }
        }
        return participant;
    }

    private Study buildStudy(Study study) throws C3PRCodedException {
        if (study.getIdentifiers() == null || study.getIdentifiers().size() == 0) {
            throw exceptionHelper.getException(
                            getCode("C3PR.EXCEPTION.REGISTRATION.MISSING.STUDY_IDENTIFIER.CODE"));
        }
        List<Study> studies = null;
        OrganizationAssignedIdentifier identifier = null;
        for (OrganizationAssignedIdentifier organizationAssignedIdentifier : study
                        .getOrganizationAssignedIdentifiers()) {
            if (organizationAssignedIdentifier.getType().equals(this.identifierTypeValueStr)) {
                identifier = organizationAssignedIdentifier;
                studies = studyService.searchByCoOrdinatingCenterId(organizationAssignedIdentifier);
                break;
            }
        }
        if (identifier == null) {
            throw exceptionHelper
                            .getException(
                                            getCode("C3PR.EXCEPTION.REGISTRATION.MISSING.STUDY_COORDINATING_IDENTIFIER.CODE"));
        }
        if (studies == null) {
            throw exceptionHelper
                            .getException(
                                            getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.STUDY_WITH_IDENTIFIER.CODE"),
                                            new String[] {
                                                    identifier.getHealthcareSite()
                                                                    .getNciInstituteCode(),
                                                    this.identifierTypeValueStr });
        }
        if (studies.size() == 0) {
            throw exceptionHelper
                            .getException(
                                            getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.STUDY_WITH_IDENTIFIER.CODE"),
                                            new String[] {
                                                    identifier.getHealthcareSite()
                                                                    .getNciInstituteCode(),
                                                    this.identifierTypeValueStr });
        }
        if (studies.size() > 1) {
            throw exceptionHelper
                            .getException(
                                            getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE.STUDY_SAME_CO_IDENTIFIER.CODE"),
                                            new String[] {
                                                    identifier.getHealthcareSite()
                                                                    .getNciInstituteCode(),
                                                    this.identifierTypeValueStr });
        }
        if (studies.get(0).getCoordinatingCenterStudyStatus() != CoordinatingCenterStudyStatus.ACTIVE) {
            throw exceptionHelper.getException(
                            getCode("C3PR.EXCEPTION.REGISTRATION.STUDY_NOT_ACTIVE"),
                            new String[] { identifier.getHealthcareSite().getNciInstituteCode(),
                                    identifier.getValue() });
        }
        return studies.get(0);
    }

    private StudySite buildStudySite(StudySite studySite, Study study) throws C3PRCodedException {
        for (StudySite temp : study.getStudySites()) {
            if (temp.getHealthcareSite().getNciInstituteCode().equals(
                            studySite.getHealthcareSite().getNciInstituteCode())) {
                if (temp.getSiteStudyStatus() != SiteStudyStatus.ACTIVE) {
                    throw exceptionHelper
                                    .getException(
                                                    getCode("C3PR.EXCEPTION.REGISTRATION.STUDYSITE_NOT_ACTIVE"),
                                                    new String[] { temp.getHealthcareSite()
                                                                    .getNciInstituteCode() });
                }
                return temp;
            }
        }
        throw exceptionHelper
                        .getException(
                                        getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.STUDYSITE_WITH_NCICODE.CODE"),
                                        new String[] { studySite.getHealthcareSite()
                                                        .getNciInstituteCode() });
    }

    private Epoch buildEpoch(List<Epoch> epochs, ScheduledEpoch scheduledEpoch)
                    throws C3PRCodedException {
        for (Epoch epochCurr : epochs) {
            if (epochCurr.getName().equalsIgnoreCase(scheduledEpoch.getEpoch().getName())) {
                return epochCurr;
            }
        }
        throw exceptionHelper.getException(
                        getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.EPOCH_NAME.CODE"),
                        new String[] { scheduledEpoch.getEpoch().getName() });
    }

    private ScheduledEpoch buildScheduledEpoch(ScheduledEpoch source, Epoch epoch)
                    throws C3PRCodedException {
        ScheduledEpoch scheduledEpoch = null;
        if (epoch instanceof TreatmentEpoch) {
            ScheduledTreatmentEpoch scheduledTreatmentEpochSource = (ScheduledTreatmentEpoch) source;
            scheduledEpoch = new ScheduledTreatmentEpoch();
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) scheduledEpoch;
            scheduledTreatmentEpoch.setEligibilityIndicator(true);
            if (epoch.getRequiresArm()) {
                if (scheduledTreatmentEpochSource.getScheduledArm() != null
                                && scheduledTreatmentEpochSource.getScheduledArm().getArm() != null
                                && scheduledTreatmentEpochSource.getScheduledArm().getArm()
                                                .getName() != null) {
                    Arm arm = null;
                    for (Arm a : ((TreatmentEpoch) epoch).getArms()) {
                        if (a.getName().equals(
                                        scheduledTreatmentEpochSource.getScheduledArm().getArm()
                                                        .getName())) {
                            arm = a;
                        }
                    }
                    if (arm == null) {
                        throw exceptionHelper
                                        .getException(
                                                        getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.ARM_NAME.CODE"),
                                                        new String[] {
                                                                scheduledTreatmentEpochSource
                                                                                .getScheduledArm()
                                                                                .getArm().getName(),
                                                                scheduledTreatmentEpochSource
                                                                                .getEpoch()
                                                                                .getName() });
                    }
                    scheduledTreatmentEpoch.getScheduledArms().get(0).setArm(arm);

                }
            }
        }
        else {
            scheduledEpoch = new ScheduledNonTreatmentEpoch();
        }
        scheduledEpoch.setEpoch(epoch);
        scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
        return scheduledEpoch;
    }

    private void fillStudySubjectDetails(StudySubject studySubject, StudySubject source) {
        studySubject.setInformedConsentSignedDate(source.getInformedConsentSignedDate());
        studySubject.setInformedConsentVersion(source.getInformedConsentVersion());
        studySubject.setStartDate(source.getStartDate());
        studySubject.setStratumGroupNumber(source.getStratumGroupNumber());
        studySubject.getIdentifiers().addAll(source.getIdentifiers());
    }
    
    private int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }

    public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
        this.exceptionHelper = exceptionHelper;
    }

    public void setC3prErrorMessages(MessageSource errorMessages) {
        c3prErrorMessages = errorMessages;
    }

    public void setStudyService(StudyService studyService) {
        this.studyService = studyService;
    }

    public void setParticipantService(ParticipantService participantService) {
        this.participantService = participantService;
    }
}
