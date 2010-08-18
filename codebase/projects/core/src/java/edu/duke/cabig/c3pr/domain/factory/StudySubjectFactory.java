package edu.duke.cabig.c3pr.domain.factory;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ICD9DiseaseSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.SubjectConsentQuestionAnswer;
import edu.duke.cabig.c3pr.domain.repository.ParticipantRepository;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.ParticipantService;

public class StudySubjectFactory {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(StudySubjectFactory.class);

    private C3PRExceptionHelper exceptionHelper;

    private MessageSource c3prErrorMessages;

    private final String prtIdentifierTypeValueStr = "MRN";

    private ParticipantService participantService;

    private StudyRepository studyRepository;

    private StudySubjectDao studySubjectDao;

    private ParticipantDao participantDao;
    
    private ICD9DiseaseSiteDao icd9DiseaseSiteDao;
    
    private HealthcareSiteDao healthcareSiteDao;
    
    private ParticipantRepository participantRepository;

    public void setParticipantRepository(ParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public ICD9DiseaseSiteDao getIcd9DiseaseSiteDao() {
		return icd9DiseaseSiteDao;
	}

	public void setIcd9DiseaseSiteDao(ICD9DiseaseSiteDao icd9DiseaseSiteDao) {
		this.icd9DiseaseSiteDao = icd9DiseaseSiteDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

    public StudySubject buildStudySubject(StudySubject deserializedStudySubject)
                    throws C3PRCodedException {
        StudySubject built = new StudySubject();
        StudySite studySite = buildStudySite(deserializedStudySubject.getStudySite(),
                        buildStudy(deserializedStudySubject.getStudySite().getStudy()));
        Participant participant = findMasterParticipant(deserializedStudySubject.getStudySubjectDemographics());
        if (participant == null){
        	participant = new Participant();
        	participant.synchronizeWithStudySubjectDemographics(deserializedStudySubject.getStudySubjectDemographics());
        	participant.addStudySubjectDemographics(deserializedStudySubject.getStudySubjectDemographics());
        }
        if (participant.getId() != null) {	
            List<StudySubject> registrations = studySubjectDao
                            .searchBySubjectAndStudyIdentifiers(participant.getPrimaryIdentifier(),
                            		studySite.getStudy().getCoordinatingCenterAssignedIdentifier());
            if (registrations.size() > 0) {
                throw this.exceptionHelper
                                .getException(getCode("C3PR.EXCEPTION.REGISTRATION.STUDYSUBJECTS_ALREADY_EXISTS.CODE"));
            }
        }
        else {
            if (participant.validateParticipant())
            	{ participantDao.save(participant);
            }
            else {
                throw this.exceptionHelper
                                .getException(getCode("C3PR.EXCEPTION.REGISTRATION.SUBJECTS_INVALID_DETAILS.CODE"));
            }
        }
        buildAndAddStudySubjectConsentVersions(built, studySite);
        built.setStudySite(studySite);
    	studySite.getStudySiteStudyVersion().addStudySubjectStudyVersion(built.getStudySubjectStudyVersion());
        built.setStudySubjectDemographics(participant.createStudySubjectDemographics());
        Epoch epoch = buildEpoch(studySite.getStudy().getEpochs(), deserializedStudySubject
                        .getScheduledEpoch());
        ScheduledEpoch scheduledEpoch = buildScheduledEpoch(deserializedStudySubject
                        .getScheduledEpoch(), epoch);
        built.getScheduledEpochs().add(0, scheduledEpoch);
        fillStudySubjectDetails(built, deserializedStudySubject);
        return built;
    }
    
    public void buildAndAddStudySubjectConsentVersions(StudySubject built,StudySite studySite){
    	for (Consent consent :studySite.getStudySiteStudyVersion().getStudyVersion().getConsents()){
    		StudySubjectConsentVersion studySubjectConsentVersion = new StudySubjectConsentVersion();
    		studySubjectConsentVersion.setConsent(consent);
    		for(ConsentQuestion question:consent.getQuestions()){
    			SubjectConsentQuestionAnswer subjectConsentQuestionAnswer = new SubjectConsentQuestionAnswer();
    			subjectConsentQuestionAnswer.setConsentQuestion(question);
    			studySubjectConsentVersion.addSubjectConsentAnswer(subjectConsentQuestionAnswer);
    		}
    		built.getStudySubjectStudyVersion().addStudySubjectConsentVersion(studySubjectConsentVersion);
    	}
    }

    public StudySubject buildReferencedStudySubject(StudySubject deserializedStudySubject)
                    throws C3PRCodedException {
        StudySubject built = new StudySubject();
        StudySite studySite = buildStudySite(deserializedStudySubject.getStudySite(),
                        buildStudy(deserializedStudySubject.getStudySite().getStudy()));
        Participant participant = findMasterParticipant(deserializedStudySubject.getStudySubjectDemographics());
        built.setStudySite(studySite);
        built.setStudySubjectDemographics(participant.createStudySubjectDemographics());
        Epoch epoch = buildEpoch(studySite.getStudy().getEpochs(), deserializedStudySubject
                        .getScheduledEpoch());
        ScheduledEpoch scheduledEpoch = buildScheduledEpoch(deserializedStudySubject
                        .getScheduledEpoch(), epoch);
        built.getScheduledEpochs().add(0, scheduledEpoch);
        fillStudySubjectDetails(built, deserializedStudySubject);
        return built;
    }

    public Participant findMasterParticipant(StudySubjectDemographics studySubjectDemographics) throws C3PRCodedException {
    	Participant participant = null;
        if (studySubjectDemographics.getIdentifiers() == null || studySubjectDemographics.getIdentifiers().size() == 0) {
            throw exceptionHelper
                            .getException(getCode("C3PR.EXCEPTION.REGISTRATION.MISSING.SUBJECT_IDENTIFIER.CODE"));
        }
        for (OrganizationAssignedIdentifier organizationAssignedIdentifier : studySubjectDemographics
                        .getOrganizationAssignedIdentifiers()) {
            if (organizationAssignedIdentifier.getType().getName().equals(this.prtIdentifierTypeValueStr)) {
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
                    participant = paList.get(0);
                }
            }
        }
        for (OrganizationAssignedIdentifier organizationAssignedIdentifier : studySubjectDemographics
                .getOrganizationAssignedIdentifiers()) {
        	HealthcareSite healthcareSite= healthcareSiteDao.getByPrimaryIdentifier(organizationAssignedIdentifier.getHealthcareSite().getPrimaryIdentifier());
        	organizationAssignedIdentifier.setHealthcareSite(healthcareSite);
        }
//        addContactsToParticipant(participant);
        return participant;
    }

    public Study buildStudy(Study study) throws C3PRCodedException {
        if (study.getIdentifiers() == null || study.getIdentifiers().size() == 0) {
            throw exceptionHelper
                            .getException(getCode("C3PR.EXCEPTION.REGISTRATION.MISSING.STUDY_IDENTIFIER.CODE"));
        }
        List<Study> studies = null;
        OrganizationAssignedIdentifier identifier = null;
        for (OrganizationAssignedIdentifier organizationAssignedIdentifier : study
                        .getOrganizationAssignedIdentifiers()) {
            if (organizationAssignedIdentifier.getType().equals(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER)) {
                identifier = organizationAssignedIdentifier;
                studies = studyRepository
                                .searchByCoOrdinatingCenterId(organizationAssignedIdentifier);
                break;
            }
        }
        if (identifier == null) {
            throw exceptionHelper
                            .getException(getCode("C3PR.EXCEPTION.REGISTRATION.MISSING.STUDY_COORDINATING_IDENTIFIER.CODE"));
        }
        if (studies == null) {
            throw exceptionHelper
                            .getException(
                                            getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.STUDY_WITH_IDENTIFIER.CODE"),
                                            new String[] {
                                                    identifier.getValue(),
                                                    OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER.getCode() });
        }
        if (studies.size() == 0) {
            throw exceptionHelper
                            .getException(
                                            getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.STUDY_WITH_IDENTIFIER.CODE"),
                                            new String[] {
                                                    identifier.getValue(),
                                                    OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER.getCode() });
        }
        if (studies.size() > 1) {
            throw exceptionHelper
                            .getException(
                                            getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE.STUDY_SAME_CO_IDENTIFIER.CODE"),
                                            new String[] {
                                                    identifier.getValue(),
                                                    OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER.getCode() });
        }
        if (studies.get(0).getCoordinatingCenterStudyStatus() != CoordinatingCenterStudyStatus.OPEN) {
            throw exceptionHelper.getException(
                            getCode("C3PR.EXCEPTION.REGISTRATION.STUDY_NOT_ACTIVE"), new String[] {
                                    identifier.getHealthcareSite().getPrimaryIdentifier(),
                                    identifier.getValue() });
        }
        return studies.get(0);
    }

    public StudySite buildStudySite(StudySite studySite, Study study) throws C3PRCodedException {
        for (StudySite temp : study.getStudySites()) {
            if (temp.getHealthcareSite().getPrimaryIdentifier().equals(
                            studySite.getHealthcareSite().getPrimaryIdentifier())) {
                if (temp.getSiteStudyStatus() != SiteStudyStatus.ACTIVE) {
                    throw exceptionHelper
                                    .getException(
                                                    getCode("C3PR.EXCEPTION.REGISTRATION.STUDYSITE_NOT_ACTIVE"),
                                                    new String[] { temp.getHealthcareSite()
                                                                    .getPrimaryIdentifier() });
                }
                return temp;
            }
        }
        throw exceptionHelper
                        .getException(
                                        getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.STUDYSITE_WITH_NCICODE.CODE"),
                                        new String[] { studySite.getHealthcareSite()
                                                        .getPrimaryIdentifier() });
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
        ScheduledEpoch scheduledEpochSource = source;
        scheduledEpoch = new ScheduledEpoch();
        ScheduledEpoch scheduledTreatmentEpoch = scheduledEpoch;
        scheduledTreatmentEpoch.setEligibilityIndicator(true);
        if (scheduledEpochSource.getScheduledArm() != null
                        && scheduledEpochSource.getScheduledArm().getArm() != null
                        && scheduledEpochSource.getScheduledArm().getArm()
                                        .getName() != null) {
            Arm arm = null;
            for (Arm a : (epoch).getArms()) {
                if (a.getName().equals(
                		scheduledEpochSource.getScheduledArm().getArm()
                                                .getName())) {
                    arm = a;
                }
            }
            if (arm == null) {
                throw exceptionHelper
                                .getException(
                                                getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.ARM_NAME.CODE"),
                                                new String[] {
                                                	scheduledEpochSource
                                                                        .getScheduledArm()
                                                                        .getArm().getName(),
                                                                        scheduledEpochSource
                                                                        .getEpoch()
                                                                        .getName() });
            }
            scheduledTreatmentEpoch.getScheduledArms().get(0).setArm(arm);

        }
        scheduledEpoch.setEpoch(epoch);
        scheduledEpoch.setScEpochWorkflowStatus(source.getScEpochWorkflowStatus());
        scheduledEpoch.setStratumGroupNumber(source.getStratumGroupNumber());
        return scheduledEpoch;
    }

    private void fillStudySubjectDetails(StudySubject studySubject, StudySubject source){
    	
    	for (int i=0;i<source.getStudySubjectStudyVersion().getStudySubjectConsentVersions().size();i++){
    		studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(i).
    		setInformedConsentSignedDate(source.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(i).getInformedConsentSignedDate());
    	}
        studySubject.setStartDate(source.getStartDate());
        studySubject.setPaymentMethod(source.getPaymentMethod());
        studySubject.getIdentifiers().addAll(source.getIdentifiers());
        for (OrganizationAssignedIdentifier organizationAssignedIdentifier : studySubject
                .getOrganizationAssignedIdentifiers()) {
        	HealthcareSite healthcareSite= healthcareSiteDao.getByPrimaryIdentifier(organizationAssignedIdentifier.getHealthcareSite().getPrimaryIdentifier());
        	organizationAssignedIdentifier.setHealthcareSite(healthcareSite);
        }
        if(source.getTreatingPhysician()!=null){
        	for(StudyInvestigator studyInvestigator:studySubject.getStudySite().getStudyInvestigators()){
        		if(studyInvestigator.getHealthcareSiteInvestigator().getInvestigator().getAssignedIdentifier().equals(source.getTreatingPhysician().getHealthcareSiteInvestigator().getInvestigator().getAssignedIdentifier())){
        			studySubject.setTreatingPhysician(studyInvestigator);
        			break;
        		}
        	}
        }
        if(source.getDiseaseHistory()!=null){
        	if(source.getDiseaseHistory().getStudyDisease()!=null){
        		for(StudyDisease studyDisease: studySubject.getStudySite().getStudy().getStudyDiseases()){
        			if(studyDisease.getDiseaseTerm().getCtepTerm().equals(source.getDiseaseHistory().getStudyDisease().getDiseaseTerm().getCtepTerm())){
        				studySubject.getDiseaseHistory().setStudyDisease(studyDisease);
        				break;
        			}
        		}
        	}
        	if(source.getDiseaseHistory().getIcd9DiseaseSite()!=null){
        		ICD9DiseaseSite icd9DiseaseSite= icd9DiseaseSiteDao.getByCode(source.getDiseaseHistory().getIcd9DiseaseSite().getCode());
        		if(icd9DiseaseSite == null){
        			studySubject.getDiseaseHistory().setOtherPrimaryDiseaseSiteCode(source.getDiseaseHistory().getIcd9DiseaseSite().getName());
        			studySubject.getDiseaseHistory().setIcd9DiseaseSite(null);
        		}else{
        			studySubject.getDiseaseHistory().setIcd9DiseaseSite(icd9DiseaseSite);
        		}
        	}
        }
        /*studySubject.setCctsWorkflowStatus(source.getCctsWorkflowStatus());
        studySubject.setMultisiteWorkflowStatus(source.getMultisiteWorkflowStatus());*/
    }
    
//    private Participant addContactsToParticipant(Participant participant) {
//
//        ContactMechanism contactMechanismEmail = new LocalContactMechanism();
//        ContactMechanism contactMechanismPhone = new LocalContactMechanism();
//        ContactMechanism contactMechanismFax = new LocalContactMechanism();
//        contactMechanismEmail.setType(ContactMechanismType.EMAIL);
//        contactMechanismPhone.setType(ContactMechanismType.PHONE);
//        contactMechanismFax.setType(ContactMechanismType.Fax);
//        participant.addContactMechanism(contactMechanismEmail);
//        participant.addContactMechanism(contactMechanismPhone);
//        participant.addContactMechanism(contactMechanismFax);
//        return participant;
//    }

    private int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }

    public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
        this.exceptionHelper = exceptionHelper;
    }

    public void setC3prErrorMessages(MessageSource errorMessages) {
        c3prErrorMessages = errorMessages;
    }

    public void setParticipantService(ParticipantService participantService) {
        this.participantService = participantService;
    }

    public void setStudyRepository(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }
    
    
}
