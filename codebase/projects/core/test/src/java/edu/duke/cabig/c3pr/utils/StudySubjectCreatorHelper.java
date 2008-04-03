package edu.duke.cabig.c3pr.utils;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PhoneCallRandomization;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;

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
        if (studySubject.getIfTreatmentScheduledEpoch()) {
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) studySubject
                            .getScheduledEpoch();
            List criterias = scheduledTreatmentEpoch.getTreatmentEpoch()
                            .getInclusionEligibilityCriteria();
            for (int i = 0; i < criterias.size(); i++) {
                SubjectEligibilityAnswer subjectEligibilityAnswer = new SubjectEligibilityAnswer();
                subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria) criterias
                                .get(i));
                scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
            }
            criterias = scheduledTreatmentEpoch.getTreatmentEpoch()
                            .getExclusionEligibilityCriteria();
            for (int i = 0; i < criterias.size(); i++) {
                SubjectEligibilityAnswer subjectEligibilityAnswer = new SubjectEligibilityAnswer();
                subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria) criterias
                                .get(i));
                scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
            }
            List<StratificationCriterion> stratifications = scheduledTreatmentEpoch
                            .getTreatmentEpoch().getStratificationCriteria();
            for (StratificationCriterion stratificationCriterion : stratifications) {
                stratificationCriterion.getPermissibleAnswers().size();
                SubjectStratificationAnswer subjectStratificationAnswer = new SubjectStratificationAnswer();
                subjectStratificationAnswer.setStratificationCriterion(stratificationCriterion);
                scheduledTreatmentEpoch
                                .addSubjectStratificationAnswers(subjectStratificationAnswer);
            }
        }
    }

    public void bindEligibility(Object command) {
        StudySubject studySubject = (StudySubject) command;
        if(!studySubject.getIfTreatmentScheduledEpoch()) return;
        List<SubjectEligibilityAnswer> subList = ((ScheduledTreatmentEpoch) studySubject
                        .getScheduledEpoch()).getSubjectEligibilityAnswers();
        for (SubjectEligibilityAnswer subjectEligibilityAnswer : subList) {
            if (subjectEligibilityAnswer.getEligibilityCriteria() instanceof InclusionEligibilityCriteria) {
                subjectEligibilityAnswer.setAnswerText("yes");
            }
            else {
                subjectEligibilityAnswer.setAnswerText("no");
            }
        }
        ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch())
                        .setEligibilityIndicator(evaluateEligibilityIndicator(studySubject));
    }

    public void bindStratification(Object command) {
        StudySubject studySubject = (StudySubject) command;
        if(!studySubject.getIfTreatmentScheduledEpoch()) return;
        List<SubjectStratificationAnswer> subList1 = ((ScheduledTreatmentEpoch) studySubject
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
        if(!studySubject.getIfTreatmentScheduledEpoch()) return;
        List<SubjectStratificationAnswer> subList1 = ((ScheduledTreatmentEpoch) studySubject
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
            ScheduledTreatmentEpoch scheduledEpoch = ((ScheduledTreatmentEpoch) studySubject
                            .getScheduledEpoch());
            scheduledEpoch.addScheduledArm(new ScheduledArm());
            ScheduledArm scheduledArm = scheduledEpoch.getScheduledArm();
            scheduledArm.setArm(((TreatmentEpoch) scheduledEpoch.getTreatmentEpoch()).getArms()
                            .get(0));
        }
    }

    public void bindArm(StudySubject studySubject){
        if(!studySubject.getScheduledEpoch().getRequiresArm())
            return;
        ScheduledArm scheduledArm = new ScheduledArm();
        scheduledArm.setArm(((TreatmentEpoch)studySubject.getScheduledEpoch().getEpoch()).getArms().get(0));
        ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch()).addScheduledArm(scheduledArm);
    }
    public boolean evaluateEligibilityIndicator(StudySubject studySubject) {
        boolean flag = true;
        List<SubjectEligibilityAnswer> answers = ((ScheduledTreatmentEpoch) studySubject
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
            answers = ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch())
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
        TreatmentEpoch epoch = new TreatmentEpoch();
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

    public void addScheduledEpochFromStudyEpochs(StudySubject studySubject) {
        boolean isTreatment=studySubject.getStudySite().getStudy().getEpochs().get(0) instanceof TreatmentEpoch?true:false;
        ScheduledEpoch scheduledEpoch = isTreatment ? new ScheduledTreatmentEpoch()
                        : new ScheduledNonTreatmentEpoch();
        scheduledEpoch.setEpoch(studySubject.getStudySite().getStudy().getEpochs().get(0));
        studySubject.addScheduledEpoch(scheduledEpoch);
    }
    
    public void addDummyScheduledEpoch(StudySubject studySubject, boolean isTreatment){
        ScheduledEpoch scheduledEpoch=isTreatment?new ScheduledTreatmentEpoch():new ScheduledNonTreatmentEpoch();
        studySubject.addScheduledEpoch(scheduledEpoch);
    }

    public void addEnrollmentDetails(StudySubject studySubject) {
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubject.setOtherTreatingPhysician("Other T P");
    }

   
    public void addStudySiteAndCoCenter(Study study, boolean makeStudysiteCoCenter) {
        StudySite studySite = new StudySite();
        
        HealthcareSite healthcaresite = new HealthcareSite();
        Address address = new Address();
        address.setCity("Chicago");
        address.setCountryCode("USA");
        address.setPostalCode("83929");
        address.setStateCode("IL");
        address.setStreetAddress("123 Lake Shore Dr");
        healthcaresite.setAddress(address);
        healthcaresite.setName("Northwestern Memorial Hospital");
        healthcaresite.setDescriptionText("NU healthcare");
        healthcaresite.setNciInstituteCode("NU healthcare");
        studySite.setHealthcareSite(healthcaresite);
        studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);
        studySite.setStudy(study);
        study.getStudySites().add(studySite);

        StudyCoordinatingCenter stC = study.getStudyCoordinatingCenters().get(0);
        stC.setStudy(study);
        if (makeStudysiteCoCenter){
            stC.setHealthcareSite(healthcaresite);
        }else{
            healthcaresite = new HealthcareSite();
            address = new Address();
            address.setCity("Charlotte");
            address.setCountryCode("USA");
            address.setPostalCode("12123");
            address.setStateCode("NC");
            address.setStreetAddress("A imperial building");
    
            healthcaresite.setAddress(address);
            healthcaresite.setName("Duke");
            healthcaresite.setDescriptionText("DUKE healthcare");
            healthcaresite.setNciInstituteCode("DUKE NCI");
            stC.setHealthcareSite(healthcaresite);
        }
        addIdentifierToStudy(study);
    }
    
    public void completeRegistrationDataEntry(StudySubject studySubject){
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
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
        ScheduledTreatmentEpoch scheduledEpoch = ((ScheduledTreatmentEpoch) studySubject
                        .getScheduledEpoch());
        scheduledEpoch.addScheduledArm(new ScheduledArm());
        ScheduledArm scheduledArm = scheduledEpoch.getScheduledArm();
        scheduledArm.setArm(((TreatmentEpoch) scheduledEpoch.getTreatmentEpoch()).getArms().get(0));
    }
    
    public void addIdentifierToStudy(Study study){
        List<OrganizationAssignedIdentifier> studyIdentifiers=study.getOrganizationAssignedIdentifiers();
        OrganizationAssignedIdentifier identifier=studyIdentifiers.get(studyIdentifiers.size());
        identifier.setHealthcareSite(study.getStudyCoordinatingCenters().get(0).getHealthcareSite());
        identifier.setValue("test id");
        identifier.setType("Coordinating Center Identifier");
        identifier.setPrimaryIndicator(true);
    }
    
    public void addMRNIdentifierToSubject(Participant participant, HealthcareSite healthcareSite){
        List<OrganizationAssignedIdentifier> prtIdentifiers=participant.getOrganizationAssignedIdentifiers();
        OrganizationAssignedIdentifier identifier=prtIdentifiers.get(prtIdentifiers.size());
        identifier.setHealthcareSite(healthcareSite);
        identifier.setValue("test id"+Math.random());
        identifier.setType("MRN");
        identifier.setPrimaryIndicator(true);
    }
}
