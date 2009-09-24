package edu.duke.cabig.c3pr.utils;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PhoneCallRandomization;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;

public class StudySubjectCreatorHelper {
    
    private StudyCreationHelper studyCreationHelper = new StudyCreationHelper();
    
    public Participant createNewParticipant(){
        Participant participant=new Participant();
        participant.setFirstName("firstName");
        participant.setLastName("lastName");
        participant.setAdministrativeGenderCode("M");
        participant.setBirthDate(new java.util.Date());
        return participant;
    }
    
    public Study getMultiSiteRandomizedStudy(RandomizationType randomizationType,
                    boolean makeStudysiteCoCenter) throws Exception {
        Study study = studyCreationHelper.getMultiSiteRandomizedStudy(randomizationType);
        addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
        return study;
    }

    public Study getMultiSiteNonRandomizedStudy(Boolean reserving, Boolean enrolling,
                    boolean makeStudysiteCoCenter) {
        Study study = studyCreationHelper.getMultiSiteNonRandomizedStudy(reserving, enrolling);
        addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
        return study;
    }

    public Study getMultiSiteNonRandomizedWithArmStudy(boolean makeStudysiteCoCenter) {
        Study study = studyCreationHelper.getMultiSiteNonRandomizedWithArmStudy();
        addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
        return study;
    }

    public Study getLocalRandomizedStudy(RandomizationType randomizationType,
                    boolean makeStudysiteCoCenter) throws Exception {
        Study study = studyCreationHelper.getLocalRandomizedStudy(randomizationType);
        addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
        return study;
    }

    public Study getLocalNonRandomizedStudy(Boolean reserving, Boolean enrolling,
                    boolean makeStudysiteCoCenter) {
        Study study = studyCreationHelper.getLocalNonRandomizedStudy(reserving, enrolling);
        addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
        return study;
    }

    public Study getLocalNonRandomizedWithArmStudy(boolean makeStudysiteCoCenter) {
        Study study = studyCreationHelper.getLocalNonRandomizedTratmentWithArmStudy();
        addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
        return study;
    }

    public StudySite getMultiSiteRandomizedStudySite(RandomizationType randomizationType,
                    boolean makeStudysiteCoCenter) throws Exception {
        Study study = studyCreationHelper.getMultiSiteRandomizedStudy(randomizationType);
        addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
        return study.getStudySites().get(0);
    }

    public StudySite getMultiSiteNonRandomizedStudySite(Boolean reserving, Boolean enrolling,
                    boolean makeStudysiteCoCenter) {
        Study study = studyCreationHelper.getMultiSiteNonRandomizedStudy(reserving, enrolling);
        addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
        return study.getStudySites().get(0);
    }

    public StudySite getMultiSiteNonRandomizedWithArmStudySite(boolean makeStudysiteCoCenter) {
        Study study = studyCreationHelper.getMultiSiteNonRandomizedWithArmStudy();
        addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
        return study.getStudySites().get(0);
    }

    public StudySite getLocalRandomizedStudySite(RandomizationType randomizationType,
        boolean makeStudysiteCoCenter) throws Exception {
        Study study = studyCreationHelper.getLocalRandomizedStudy(randomizationType);
        addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
        return study.getStudySites().get(0);
    }
    
    public StudySite getLocalRandomizedStudySiteWith2EnrollingEpochs(RandomizationType randomizationType,
	    boolean makeStudysiteCoCenter) throws Exception {
		Study study = studyCreationHelper.getLocalStudyWith1stEpochRandomized2ndNonRandomized(randomizationType);
		addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
		return study.getStudySites().get(0);
	}

    public StudySite getLocalNonRandomizedStudySite(Boolean reserving, Boolean enrolling,
                    boolean makeStudysiteCoCenter) {
        Study study = studyCreationHelper.getLocalNonRandomizedStudy(reserving, enrolling);
        addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
        return study.getStudySites().get(0);
    }

    public StudySite getLocalNonRandomizedTreatmentWithArmStudySite(boolean makeStudysiteCoCenter) {
        Study study = studyCreationHelper.getLocalNonRandomizedTratmentWithArmStudy();
        addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
        return study.getStudySites().get(0);
    }
    
    public StudySite getLocalNonRandomizedTreatmentWithoutArmStudySite(boolean makeStudysiteCoCenter) {
        Study study = studyCreationHelper.getLocalNonRandomizedTratmentWithoutArmStudy();
        addStudySiteAndCoCenter(study, makeStudysiteCoCenter);
        return study.getStudySites().get(0);
    }
    
    public void buildCommandObject(StudySubject studySubject) {
        	ScheduledEpoch scheduledEpoch = studySubject
                            .getScheduledEpoch();
            List criterias = scheduledEpoch.getEpoch()
                            .getInclusionEligibilityCriteria();
            for (int i = 0; i < criterias.size(); i++) {
                SubjectEligibilityAnswer subjectEligibilityAnswer = new SubjectEligibilityAnswer();
                subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria) criterias
                                .get(i));
                scheduledEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
            }
            criterias = scheduledEpoch.getEpoch()
                            .getExclusionEligibilityCriteria();
            for (int i = 0; i < criterias.size(); i++) {
                SubjectEligibilityAnswer subjectEligibilityAnswer = new SubjectEligibilityAnswer();
                subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria) criterias
                                .get(i));
                scheduledEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
            }
            List<StratificationCriterion> stratifications = scheduledEpoch
                            .getEpoch().getStratificationCriteria();
            for (StratificationCriterion stratificationCriterion : stratifications) {
                stratificationCriterion.getPermissibleAnswers().size();
                SubjectStratificationAnswer subjectStratificationAnswer = new SubjectStratificationAnswer();
                subjectStratificationAnswer.setStratificationCriterion(stratificationCriterion);
                scheduledEpoch
                                .addSubjectStratificationAnswers(subjectStratificationAnswer);
            }
    }

    public void bindEligibility(Object command) {
        StudySubject studySubject = (StudySubject) command;
        List<SubjectEligibilityAnswer> subList = (studySubject
                        .getScheduledEpoch()).getSubjectEligibilityAnswers();
        for (SubjectEligibilityAnswer subjectEligibilityAnswer : subList) {
            if (subjectEligibilityAnswer.getEligibilityCriteria() instanceof InclusionEligibilityCriteria) {
                subjectEligibilityAnswer.setAnswerText("yes");
            }
            else {
                subjectEligibilityAnswer.setAnswerText("no");
            }
        }
        (studySubject.getScheduledEpoch())
                        .setEligibilityIndicator(evaluateEligibilityIndicator(studySubject));
    }

    public void bindStratification(Object command) {
        StudySubject studySubject = (StudySubject) command;
        List<SubjectStratificationAnswer> subList1 = (studySubject
                        .getScheduledEpoch()).getSubjectStratificationAnswers();
        for (SubjectStratificationAnswer subjectStratificationAnswer : subList1) {
            subjectStratificationAnswer
                            .setStratificationCriterionAnswer(subjectStratificationAnswer
                                            .getStratificationCriterion().getPermissibleAnswers()
                                            .get(0));
        }
    }

    public void bindStratificationInvalid(Object command) {
        StudySubject studySubject = (StudySubject) command;
        List<SubjectStratificationAnswer> subList1 = (studySubject
                        .getScheduledEpoch()).getSubjectStratificationAnswers();
        for (SubjectStratificationAnswer subjectStratificationAnswer : subList1) {
            subjectStratificationAnswer
                            .setStratificationCriterionAnswer(subjectStratificationAnswer
                                            .getStratificationCriterion()
                                            .getPermissibleAnswers()
                                            .get(
                                                            subjectStratificationAnswer
                                                                            .getStratificationCriterion()
                                                                            .getPermissibleAnswers()
                                                                            .size() - 1));
        }
    }

    public void bindRandomization(Object command) {
        StudySubject studySubject = (StudySubject) command;
        RandomizationType randomizationType=studySubject.getStudySite().getStudy().getRandomizationType();
        if (randomizationType == RandomizationType.PHONE_CALL) {
        	ScheduledEpoch scheduledEpoch = (studySubject
                            .getScheduledEpoch());
            scheduledEpoch.addScheduledArm(new ScheduledArm());
            ScheduledArm scheduledArm = scheduledEpoch.getScheduledArm();
            scheduledArm.setArm(( scheduledEpoch.getEpoch()).getArms()
                            .get(0));
        }
    }

    public void bindArm(StudySubject studySubject){
        if(!studySubject.getScheduledEpoch().getRequiresArm())
            return;
        ScheduledArm scheduledArm = new ScheduledArm();
        scheduledArm.setArm((studySubject.getScheduledEpoch().getEpoch()).getArms().get(0));
        (studySubject.getScheduledEpoch()).addScheduledArm(scheduledArm);
    }
    public boolean evaluateEligibilityIndicator(StudySubject studySubject) {
        boolean flag = true;
        List<SubjectEligibilityAnswer> answers = (studySubject
                        .getScheduledEpoch()).getInclusionEligibilityAnswers();
        for (SubjectEligibilityAnswer subjectEligibilityAnswer : answers) {
            String answerText = subjectEligibilityAnswer.getAnswerText();
            if (answerText == null
                            || answerText.equalsIgnoreCase("")
                            || (!answerText.equalsIgnoreCase("Yes") && !answerText
                                            .equalsIgnoreCase("NA"))) {
                flag = false;
                break;
            }
        }
        if (flag) {
            answers = (studySubject.getScheduledEpoch())
                            .getExclusionEligibilityAnswers();
            for (SubjectEligibilityAnswer subjectEligibilityAnswer : answers) {
                String answerText = subjectEligibilityAnswer.getAnswerText();
                if (answerText == null
                                || answerText.equalsIgnoreCase("")
                                || (!answerText.equalsIgnoreCase("No") && !answerText
                                                .equalsIgnoreCase("NA"))) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public Epoch createTestTreatmentEpoch(boolean randomized) {
        Epoch epoch = new Epoch();
        epoch.setEnrollmentIndicator(true);
        epoch.addEligibilityCriterion(new InclusionEligibilityCriteria());
        epoch.addEligibilityCriterion(new ExclusionEligibilityCriteria());
        StratificationCriterion stratificationCriterion = new StratificationCriterion();
        stratificationCriterion
                        .addPermissibleAnswer(new StratificationCriterionPermissibleAnswer());
        epoch.addStratificationCriterion(stratificationCriterion);
        epoch.addArm(new Arm());
        epoch.setRandomizedIndicator(randomized);
        if (randomized) {
            epoch.setRandomization(new PhoneCallRandomization());
        }
        return epoch;
    }
    
    public Epoch createTestTreatmentEpochWithoutArm(boolean randomized) {
        Epoch epoch = new Epoch();
        epoch.setEnrollmentIndicator(true);
        epoch.addEligibilityCriterion(new InclusionEligibilityCriteria());
        epoch.addEligibilityCriterion(new ExclusionEligibilityCriteria());
        StratificationCriterion stratificationCriterion = new StratificationCriterion();
        stratificationCriterion
                        .addPermissibleAnswer(new StratificationCriterionPermissibleAnswer());
        epoch.addStratificationCriterion(stratificationCriterion);
        epoch.setRandomizedIndicator(randomized);
        if (randomized) {
            epoch.setRandomization(new PhoneCallRandomization());
        }
        return epoch;
    }

    public void addScheduledEnrollingEpochFromStudyEpochs(StudySubject studySubject) {
    	ScheduledEpoch scheduledEpoch = new ScheduledEpoch();
        scheduledEpoch.setEpoch(studySubject.getStudySite().getStudy().getEpochs().get(0));
        studySubject.addScheduledEpoch(scheduledEpoch);
    }
    
    public void addScheduledNonEnrollingEpochFromStudyEpochs(StudySubject studySubject) {
    	ScheduledEpoch scheduledEpoch = new ScheduledEpoch();
        scheduledEpoch.setEpoch(studySubject.getStudySite().getStudy().getEpochs().get(0));
        studySubject.addScheduledEpoch(scheduledEpoch);
    }
    
    public void addScheduled2ndNonRandomizedEnrollingEpochFromStudyEpochs(StudySubject studySubject) {
    	ScheduledEpoch scheduledEpoch = new ScheduledEpoch();
        scheduledEpoch.setEpoch(studySubject.getStudySite().getStudy().getEpochs().get(1));
        studySubject.addScheduledEpoch(scheduledEpoch);
    }
    
    public void addScheduledNonEnrollingEpochWithEligibilityFromStudyEpochs(StudySubject studySubject) {
     	ScheduledEpoch scheduledEpoch = new ScheduledEpoch();
     	scheduledEpoch.setEligibilityIndicator(true);
         scheduledEpoch.setEpoch(studySubject.getStudySite().getStudy().getEpochs().get(0));
         studySubject.addScheduledEpoch(scheduledEpoch);
     }
    
    public void addDummyScheduledEpoch(StudySubject studySubject, boolean isTreatment){
        ScheduledEpoch scheduledEpoch=isTreatment?new ScheduledEpoch():new ScheduledEpoch();
        studySubject.addScheduledEpoch(scheduledEpoch);
    }

    public void addEnrollmentDetails(StudySubject studySubject) {
    	studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
        studySubject.setOtherTreatingPhysician("Other T P");
    }

   
    public void addStudySiteAndCoCenter(Study study, boolean makeStudysiteCoCenter) {
        StudySite studySite = new StudySite();

        Date effectiveDate = new Date();
        effectiveDate.setYear(50);
        
        HealthcareSite healthcaresite = new LocalHealthcareSite();
        Address address = new Address();
        address.setCity("Chicago");
        address.setCountryCode("USA");
        address.setPostalCode("83929");
        address.setStateCode("IL");
        address.setStreetAddress("123 Lake Shore Dr");
        healthcaresite.setAddress(address);
        healthcaresite.setName("Northwestern Memorial Hospital");
        healthcaresite.setDescriptionText("NU healthcare");
        healthcaresite.setCtepCode("NU healthcare");
        healthcaresite.getOrganizationAssignedIdentifiers().get(0).setPrimaryIndicator(true);
        studySite.setHealthcareSite(healthcaresite);
      //TODO fix it later
//        studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);
        study.addStudySite(studySite);
        
        studySite.handleStudySiteStatusChange(effectiveDate, SiteStudyStatus.ACTIVE);

        StudyCoordinatingCenter stC = study.getStudyCoordinatingCenters().get(0);
        stC.setStudy(study);
        if (makeStudysiteCoCenter){
            stC.setHealthcareSite(healthcaresite);
        }else{
            healthcaresite = new LocalHealthcareSite();
            address = new Address();
            address.setCity("Charlotte");
            address.setCountryCode("USA");
            address.setPostalCode("12123");
            address.setStateCode("NC");
            address.setStreetAddress("A imperial building");
    
            healthcaresite.setAddress(address);
            healthcaresite.setName("Duke");
            healthcaresite.setDescriptionText("DUKE healthcare");
            healthcaresite.setCtepCode("DUKE NCI");
            healthcaresite.getOrganizationAssignedIdentifiers().get(0).setPrimaryIndicator(true);
            stC.setHealthcareSite(healthcaresite);
        }
        addIdentifierToStudy(study);
    }
    
    public void completeRegistrationDataEntry(StudySubject studySubject){
    	studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
    	studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setConsent(studySubject.getStudySite()
    			.getStudy().getStudyVersion().getConsents().get(0));
    }
    
    public void completeScheduledEpochDataEntry(StudySubject studySubject){
        buildCommandObject(studySubject);
        bindEligibility(studySubject);
        bindStratification(studySubject);
        if(studySubject.getScheduledEpoch().getRequiresRandomization())
            bindRandomization(studySubject);
        else if(studySubject.getScheduledEpoch().getRequiresArm())
            bindArm(studySubject);
    }
    
    public void forceAssignArm(StudySubject studySubject) {
    	ScheduledEpoch scheduledEpoch = (studySubject
                        .getScheduledEpoch());
        scheduledEpoch.addScheduledArm(new ScheduledArm());
        ScheduledArm scheduledArm = scheduledEpoch.getScheduledArm();
        scheduledArm.setArm(( scheduledEpoch.getEpoch()).getArms().get(0));
    }
    
    public void addIdentifierToStudy(Study study){
        List<OrganizationAssignedIdentifier> studyIdentifiers=study.getOrganizationAssignedIdentifiers();
        OrganizationAssignedIdentifier identifier=studyIdentifiers.get(studyIdentifiers.size());
        identifier.setHealthcareSite(study.getStudyCoordinatingCenters().get(0).getHealthcareSite());
        identifier.setValue("test id");
        identifier.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
        identifier.setPrimaryIndicator(true);
    }
    
    public void addMRNIdentifierToSubject(Participant participant, HealthcareSite healthcareSite){
        List<OrganizationAssignedIdentifier> prtIdentifiers=participant.getOrganizationAssignedIdentifiers();
        OrganizationAssignedIdentifier identifier=prtIdentifiers.get(prtIdentifiers.size());
        identifier.setHealthcareSite(healthcareSite);
        identifier.setValue("test id"+Math.random());
        identifier.setType(OrganizationIdentifierTypeEnum.MRN);
        identifier.setPrimaryIndicator(true);
    }
}
