package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.BookRandomizationEntry;
import edu.duke.cabig.c3pr.domain.CalloutRandomization;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.PhoneCallRandomization;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionAnswerCombination;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudySite;

public class StudyCreationHelper {
	private  HealthcareSiteDao healthcareSitedao;
    public Study getMultiSiteRandomizedStudy(RandomizationType randomizationType) throws Exception {
        Study study = buildBasicStudy(true, randomizationType);
        Epoch epoch = getTreatmentEpochWithArm();
        addRandomization(randomizationType, epoch);
        study.addEpoch(epoch);
        return study;
    }

    public Study getMultiSiteNonRandomizedStudy(Boolean reserving, Boolean enrolling) {
        Study study = buildBasicStudy(true, null);
       Epoch epoch = new Epoch();
        epoch.setName("screening");
        epoch.setReservationIndicator(reserving);
        epoch.setEnrollmentIndicator(enrolling);
        study.addEpoch(epoch);
        return study;
    }

    public Study getMultiSiteNonRandomizedWithArmStudy() {
        Study study = buildBasicStudy(true, null);
        Epoch epoch = getTreatmentEpochWithArm();
        study.addEpoch(epoch);
        return study;
    }

    public Study getLocalRandomizedStudy(RandomizationType randomizationType) throws Exception {
        Study study = buildBasicStudy(false, randomizationType);
        Epoch epoch = getTreatmentEpochWithArm();
        addRandomization(randomizationType, epoch);
        study.addEpoch(epoch);
        return study;
    }

    public Study getLocalNonRandomizedStudy(Boolean reserving, Boolean enrolling) {
        Study study = buildBasicStudy(false, null);
        Epoch epoch = new Epoch();
        epoch.setName("screening");
        epoch.setReservationIndicator(reserving);
        epoch.setEnrollmentIndicator(enrolling);
        study.addEpoch(epoch);
        return study;
    }

    public Study getLocalNonRandomizedTratmentWithArmStudy() {
        Study study = buildBasicStudy(false, null);
        Epoch epoch = getTreatmentEpochWithArm();
        epoch.setRandomizedIndicator(false);
        study.addEpoch(epoch);
        return study;
    }
    
    public Study getLocalNonRandomizedTratmentWithoutArmStudy() {
        Study study = buildBasicStudy(false, null);
        Epoch epoch = new Epoch();
        epoch.setName("epoch1");
        study.addEpoch(epoch);
        epoch.setEnrollmentIndicator(true);
        return study;
    }

    private Epoch getTreatmentEpochWithArm() {
        Arm armA = new Arm();
        armA.setName("A");

        Epoch epoch = new Epoch();
        armA.setEpoch(epoch);
        ArrayList<Arm> aList = new ArrayList<Arm>();
        aList.add(armA);
        epoch.getArms().addAll(aList);
        epoch.setName("epoch1");
        epoch.setRandomizedIndicator(false);
        epoch.setEnrollmentIndicator(true);
        return epoch;
    }

    private ArrayList<StratumGroup> buildStratumGroupWithScac() {
        StratificationCriterionAnswerCombination scac = new StratificationCriterionAnswerCombination();
        List<StratificationCriterionAnswerCombination> scacList = new ArrayList<StratificationCriterionAnswerCombination>();
        scacList.add(scac);

        StratumGroup stratumGroup = new StratumGroup();
        stratumGroup.getStratificationCriterionAnswerCombination().addAll(scacList);
        stratumGroup.setCurrentPosition(1);
        stratumGroup.setStratumGroupNumber(2);
        ArrayList<StratumGroup> sgList = new ArrayList<StratumGroup>();
        sgList.add(stratumGroup);
        return sgList;
    }

    private void addStratumGroupToEpoch(Epoch epoch1) {
        StratificationCriterion sc = new StratificationCriterion();
        sc.setQuestionText("will I work?");
        StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
        scpa1.setPermissibleAnswer("lets find out1");
        StratificationCriterionPermissibleAnswer scpa2 = new StratificationCriterionPermissibleAnswer();
        scpa2.setPermissibleAnswer("lets find out2");
        ArrayList scpaList = new ArrayList();
        scpaList.add(scpa1);
        scpaList.add(scpa2);
        sc.getPermissibleAnswers().addAll(scpaList);
        ArrayList scList = new ArrayList();
        scList.add(sc);
        epoch1.getStratificationCriteria().addAll(scList);

        StratificationCriterionAnswerCombination scac1 = new StratificationCriterionAnswerCombination();
        scac1.setStratificationCriterion(sc);
        scac1.setStratificationCriterionPermissibleAnswer(scpa1);
        StratificationCriterionAnswerCombination scac2 = new StratificationCriterionAnswerCombination();
        scac2.setStratificationCriterion(sc);
        scac2.setStratificationCriterionPermissibleAnswer(scpa2);
        List<StratificationCriterionAnswerCombination> scacList = new ArrayList<StratificationCriterionAnswerCombination>();
        scacList.add(scac1);

        StratumGroup stratumGroup = new StratumGroup();
        stratumGroup.getStratificationCriterionAnswerCombination().addAll(scacList);
        stratumGroup.setCurrentPosition(0);
        stratumGroup.setStratumGroupNumber(0);
        ArrayList<StratumGroup> sgList = new ArrayList<StratumGroup>();
        sgList.add(stratumGroup);

        epoch1.getStratumGroups().addAll(sgList);
    }

    private Study buildBasicStudy(Boolean multiSite, RandomizationType randomizationType) {
        Study study = new Study();
        study.setPrecisText("Study with randomization");
        study.setShortTitleText("ShortTitleText1");
        study.setLongTitleText("LongTitleText1");
        study.setPhaseCode("PhaseCode1");
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
        study.setTargetAccrualNumber(150);
        study.setType("Type");
        study.setMultiInstitutionIndicator(multiSite);
        if (randomizationType != null) {
            study.setRandomizedIndicator(Boolean.TRUE);
            study.setRandomizationType(randomizationType);
        }
        else {
            study.setRandomizedIndicator(Boolean.FALSE);
        }
        return study;
    }

    private void addRandomization(RandomizationType randomizationType, Epoch epoch)
                    throws Exception {
        epoch.setRandomizedIndicator(true);
        if (randomizationType == RandomizationType.BOOK) addBookRandomization(epoch);
        else if (randomizationType == RandomizationType.CALL_OUT) addCalloutRandomization(epoch);
        else if (randomizationType == RandomizationType.PHONE_CALL) addPhoneCallRandomization(epoch);
        else {
            throw new Exception("Invalid Randomization Type");
        }
    }

    private void addCalloutRandomization(Epoch epoch) {
        Randomization cRandomization = new CalloutRandomization();
        ((CalloutRandomization) cRandomization).setCalloutUrl("trialUrl.com");
        epoch.setRandomization(cRandomization);
    }

    private void addBookRandomization(Epoch epoch) {
        BookRandomization bRandomization = new BookRandomization();
        BookRandomizationEntry bre = new BookRandomizationEntry();
        bre.setPosition(0);
        //
        if (epoch != null) {
            List<Arm> armList = epoch.getArms();
            for (Arm individualArm : armList) {
                if (individualArm.getName().equals("A")) {
                    bre.setArm(individualArm);
                }
            }
        }
        addStratumGroupToEpoch(epoch);
        bre.setStratumGroup(epoch.getStratumGroups().get(0));
        epoch.getStratumGroups().get(0).getBookRandomizationEntry().add(bre);

        List<BookRandomizationEntry> breList = new ArrayList<BookRandomizationEntry>();
        breList.add(bre);

        bRandomization.getBookRandomizationEntry().addAll(breList);
        epoch.setRandomization(bRandomization);
    }

    private void addPhoneCallRandomization(Epoch epoch) {
        Randomization pRandomization = new PhoneCallRandomization();
        ((PhoneCallRandomization) pRandomization).setPhoneNumber("777 777 7777");
        epoch.setRandomization(pRandomization);
    }
    
    public Study createBasicStudy() {
    	
    	Study study = new Study();
        study.setPrecisText("New study");
        study.setShortTitleText("ShortTitleText");
        study.setLongTitleText("LongTitleText");
        study.setPhaseCode("PhaseCode");
        study.setRandomizedIndicator(new Boolean(false));
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
        study.setDataEntryStatus(StudyDataEntryStatus.INCOMPLETE);
        study.setTargetAccrualNumber(150);
        study.setType("Type");
        study.setMultiInstitutionIndicator(Boolean.TRUE);

        return study;
    }
    
 public Study addStudySiteAndEnrollingEpochToBasicStudy(Study study) {
    	
        study.addStudySite(new StudySite());
        Epoch epoch = new Epoch();
        epoch.setEnrollmentIndicator(new Boolean(true));
        study.addEpoch(epoch);
        
        return study;
    }
 
 public Study addStudySiteAndRandomizedTreatmentEpochToBasicStudy(Study study) {
 	
     study.addStudySite(new StudySite());
     Epoch treatmentEpoch = new Epoch();
     treatmentEpoch.setName("Treatment Epoch1");
     treatmentEpoch.setRandomizedIndicator(new Boolean(true));
     treatmentEpoch.setEnrollmentIndicator(new Boolean(true));
     study.addEpoch(treatmentEpoch);
     
     return study;
 }
 
 public Study addStudySiteAndRandomizedTreatmentEpochWith2ArmsToBasicStudy(Study study) {
	 	
     study.addStudySite(new StudySite());
     Epoch treatmentEpoch = new Epoch();
     treatmentEpoch.setName("Treatment Epoch1");
     Arm arm1 = new Arm();
     arm1.setName("arm1");
     treatmentEpoch.addArm(arm1);
     Arm arm2 = new Arm();
     arm2.setName("arm2");
     treatmentEpoch.addArm(arm2);
     treatmentEpoch.setRandomizedIndicator(new Boolean(true));
     treatmentEpoch.setEnrollmentIndicator(new Boolean(true));
     study.addEpoch(treatmentEpoch);
     
     return study;
 }
 
 public Study addStudySiteRandomizedEnrollingTreatmentEpochWith2ArmsAndStratumGroupsToBasicStudy(Study study) {
	 	
     study.addStudySite(new StudySite());
     Epoch treatmentEpoch = new Epoch();
     treatmentEpoch.setName("Treatment Epoch1");
     Arm arm1 = new Arm();
     arm1.setName("arm1");
     treatmentEpoch.addArm(arm1);
     Arm arm2 = new Arm();
     arm2.setName("arm2");
     treatmentEpoch.addArm(arm2);
     addStratumGroupToEpoch(treatmentEpoch);
     treatmentEpoch.setRandomizedIndicator(new Boolean(true));
     treatmentEpoch.setEnrollmentIndicator(new Boolean(true));
     study.addEpoch(treatmentEpoch);
     
     return study;
 }
 
 public Study addStudySiteRandomizedTreatmentEpochWith2ArmsStratumGroupsAndRandomizationToBasicStudy(Study study){
	 
	 addStudySiteRandomizedEnrollingTreatmentEpochWith2ArmsAndStratumGroupsToBasicStudy(study);
	 addPhoneCallRandomization(study.getEpochs().get(0));
     return study;
 }

	public void setHealthcareSitedao(HealthcareSiteDao healthcareSitedao) {
		this.healthcareSitedao = healthcareSitedao;
	}
}
