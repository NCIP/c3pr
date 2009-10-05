package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.BookRandomizationEntry;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.PhoneCallRandomization;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionAnswerCombination;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudySite;

public class StudyCreationHelper {

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
        addDefaultConsentToStudy(study);
        return study;
    }
    
    public Study addDefaultConsentToStudy(Study study){
    	Consent consent = new Consent();
    	consent.setName("default consent");
    	study.getStudyVersion().addConsent(consent);
    	return study;
    }

    public Study getLocalStudyWith1stEpochRandomized2ndNonRandomized(RandomizationType randomizationType) throws Exception {
        Study study = buildBasicStudy(false, randomizationType);
        Epoch epoch = getTreatmentEpochWithArm();
        addRandomization(randomizationType, epoch);
        epoch.setEpochOrder(1);
        study.addEpoch(epoch);
        Epoch epoch1 = getNonRandomizedNonStratifiedEnrollingEpochWithoutArm();
        epoch1.setEpochOrder(2);
        study.addEpoch(epoch1);
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
        ArrayList<Arm> aList = new ArrayList<Arm>();
        aList.add(armA);
        epoch.getArms().addAll(aList);
        epoch.setName("epoch1");
        epoch.setRandomizedIndicator(false);
        epoch.setEnrollmentIndicator(true);
        return epoch;
    }

    private Epoch getNonRandomizedNonStratifiedEnrollingEpochWithoutArm() {

        Epoch epoch = new Epoch();
        epoch.setName("epoch2");
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

    public Study buildBasicStudy(Boolean multiSite, RandomizationType randomizationType) {
        Study study = new LocalStudy();
        study.setPrecisText("Study with randomization");
        study.setShortTitleText("ShortTitleText1");
        study.setLongTitleText("LongTitleText1");
        study.setPhaseCode("Phase I Trial");
        study.setStratificationIndicator(true);
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
        study.setTargetAccrualNumber(150);
        study.setType("Diagnostic");
        study.setDescriptionText("Description Text");
        study.setOriginalIndicator(true);
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
        else if (randomizationType == RandomizationType.PHONE_CALL) addPhoneCallRandomization(epoch);
        else {
            throw new Exception("Invalid Randomization Type");
        }
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

        Study study = new LocalStudy();
        study.setPrecisText("New study");
        study.setShortTitleText("ShortTitleText");
        study.setLongTitleText("LongTitleText");
        study.setPhaseCode("PhaseCode");
        study.setRandomizedIndicator(new Boolean(false));
        study.setStratificationIndicator(false);
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
        study.setDataEntryStatus(StudyDataEntryStatus.INCOMPLETE);
        study.setTargetAccrualNumber(150);
        study.setType("Type");
        study.setMultiInstitutionIndicator(Boolean.TRUE);
        study.setStratificationIndicator(Boolean.FALSE);
        study.setOriginalIndicator(true);

        study.getStudyVersion().setName("name");
        study.getStudyVersion().setVersionDate(new Date());

        return study;
    }

    public Study addStudySiteAndEnrollingEpochToBasicStudy(Study study) {
    	return addStudySiteAndEnrollingEpochToBasicStudy(study,"Name");
    }

	public Study addStudySiteAndEnrollingEpochToBasicStudy(Study study,String name) {
		EligibilityCriteria criteria = new InclusionEligibilityCriteria();
		StudySite studySite = new StudySite();
		HealthcareSite hcs = new LocalHealthcareSite();
		hcs.setNCICode("NCI_CODE");
		studySite.setHealthcareSite(hcs);
		study.addStudySite(studySite);
		Epoch epoch = new Epoch();
		epoch.setName(name);
		epoch.addEligibilityCriterion(criteria);
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

    public Study addStudySiteRandomizedEnrollingTreatmentEpochWith2ArmsAndStratumGroupsToBasicStudy(
                    Study study) {
    	StudySite studySite = new StudySite();
    	studySite.setIrbApprovalDate(Calendar.getInstance().getTime());
    	studySite.getStudySiteStudyVersion().setStartDate(new Date());
        study.addStudySite(studySite);
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

    public Study addStudySiteRandomizedTreatmentEpochWith2ArmsStratumGroupsAndRandomizationToBasicStudy(
                    Study study) {

        addStudySiteRandomizedEnrollingTreatmentEpochWith2ArmsAndStratumGroupsToBasicStudy(study);
        addPhoneCallRandomization(study.getEpochs().get(0));
        return study;
    }

    public void addStudySiteAsCooordinatingCenter(Study study) {
        StudySite studySite=study.getStudySites().get(0);
        StudyCoordinatingCenter studyCoordinatingCenter = study.getStudyCoordinatingCenters()
                        .get(0);
        studyCoordinatingCenter.setHealthcareSite(studySite.getHealthcareSite());
        studyCoordinatingCenter.setStudy(study);
    }

	public Study createBasicStudyObject(){
 		Study study = new LocalStudy(false);
        study.setPrecisText("New study");
        study.setShortTitleText("ShortTitleText");
        study.setLongTitleText("LongTitleText");
        study.setPhaseCode("PhaseCode");
        study.setRandomizedIndicator(new Boolean(false));
        study.setStratificationIndicator(false);
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
        study.setDataEntryStatus(StudyDataEntryStatus.INCOMPLETE);
        study.setTargetAccrualNumber(150);
        study.setType("Type");
        study.setMultiInstitutionIndicator(Boolean.TRUE);
        study.setStratificationIndicator(Boolean.FALSE);
        return study;
	}

	public Study addOrganizationAssignedIdentifier(Study study, OrganizationIdentifierTypeEnum type, String value){
		OrganizationAssignedIdentifier orgIdentifier =  new OrganizationAssignedIdentifier();
		orgIdentifier.setType(type);
		orgIdentifier.setValue(value);
		orgIdentifier.setHealthcareSite(new LocalHealthcareSite());
		study.getOrganizationAssignedIdentifiers().add(orgIdentifier);
		return study;
	}

	public Study addOrganizationAssignedIdentifierNonPrimary(Study study, OrganizationIdentifierTypeEnum type, String value){
		OrganizationAssignedIdentifier orgIdentifier =  new OrganizationAssignedIdentifier();
		orgIdentifier.setType(type);
		orgIdentifier.setValue(value);
		orgIdentifier.setHealthcareSite(new LocalHealthcareSite());
		orgIdentifier.setPrimaryIndicator(false);
		study.getOrganizationAssignedIdentifiers().add(orgIdentifier);
		return study;
	}

	 public Study addNonEnrollingEpochToBasicStudy(Study study) {
	        return addNonEnrollingEpochToBasicStudy(study, "Name");
	    }

	 public Study addNonEnrollingEpochToBasicStudy(Study study, String name) {
	        Epoch epoch = new Epoch();
	        epoch.setName(name);
	        epoch.setEnrollmentIndicator(false);
	        study.addEpoch(epoch);
	        return study;
	    }

	 public Study addStratifiedEpochToBasicStudy(Study study, String name) {
	        Epoch epoch = new Epoch();
	        epoch.setName(name);
	        epoch.setStratificationIndicator(true);
	        study.addEpoch(epoch);
	        return study;
	    }

	 public Study addParentStudyAssociation(Study parent, Study child){
		 parent.setCompanionIndicator(false);
		 child.setCompanionIndicator(true);
		 CompanionStudyAssociation association = new CompanionStudyAssociation();
		 association.setId(1);
		 association.setParentStudyVersion(parent.getStudyVersion());
		 association.setCompanionStudy(child);

		 child.getParentStudyAssociations().add(association);
		 return child;
	 }

	 public Study addParentStudyAssociationWithSite(Study parent, Study child){
		 parent.setCompanionIndicator(false);
		 child.setCompanionIndicator(true);
		 CompanionStudyAssociation association = new CompanionStudyAssociation();
		 association.setId(1);
		 association.setParentStudyVersion(parent.getStudyVersion());
		 association.setCompanionStudy(child);

		 StudySite site = new StudySite();
		 HealthcareSite hcs = new LocalHealthcareSite();
		 hcs.setNCICode("NCI_CODE");
		 hcs.getOrganizationAssignedIdentifiers().get(0).setPrimaryIndicator(true);
		 site.setHealthcareSite(hcs);

		 association.addStudySite(site);

		 child.getParentStudyAssociations().add(association);
		 return child;
	 }

	 public Epoch createEpochWithArms(String epochName,
				String... armNames) {
			Epoch epoch = new Epoch();
			epoch.setName(epochName);
			if (armNames.length == 0) {
				addNewArm(epoch,epochName);
			} else {
				for (String armName : armNames) {
					addNewArm(epoch, armName);
				}
			}
			return epoch;
		}

		public Epoch createEpoch(String epochName) {
			Epoch epoch = new Epoch();
			epoch.setName(epochName);
			return epoch;
		}

		public void addNewArm(Epoch epoch, String armName) {
			Arm arm = new Arm();
			arm.setName(armName);
			epoch.addArm(arm);
		}

		public Consent createConsent(String consentName) {
			Consent consent  = new Consent();
			consent.setName(consentName);
			return consent;
		}

		public Study addConsent(Study study, String name) {
			Consent consent = new Consent();
			consent.setName(name);
			study.addConsent(consent);
			return study;
		}

}
