package edu.duke.cabig.c3pr.xml;

import java.util.Date;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalInvestigator;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PhoneCallRandomization;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyFundingSponsor;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;

public class DomainObjectCreationHelper {
	
	private static StudyCreationHelper studyCreationHelper= new StudyCreationHelper();
	
	public static Study getStudyWithDetails(RandomizationType randomizationType){
		Study study= studyCreationHelper.buildBasicStudy(true, randomizationType);
		addCooCenterAndIdentifier(study);
		addFundingSponsorAndIdentifier(study);
		addPI(study.getStudyCoordinatingCenter());
		return study;
	}
	
	public static void addCooCenterAndIdentifier(Study study){
		StudyCoordinatingCenter studyCoordinatingCenter= new StudyCoordinatingCenter();
		HealthcareSite healthcareSite= createHealthcareSite(true);
		studyCoordinatingCenter.setHealthcareSite(healthcareSite);
		studyCoordinatingCenter.setGridId("co center");
		OrganizationAssignedIdentifier orgIdentifier= new OrganizationAssignedIdentifier();
		orgIdentifier.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
		orgIdentifier.setHealthcareSite(healthcareSite);
		orgIdentifier.setValue("cocenter id");
		study.addStudyOrganization(studyCoordinatingCenter);
		study.addIdentifier(orgIdentifier);
	}
	
	public static void addFundingSponsorAndIdentifier(Study study){
		StudyFundingSponsor studyFundingSponsor= new StudyFundingSponsor();
		HealthcareSite healthcareSite= createHealthcareSite(true);
		studyFundingSponsor.setHealthcareSite(healthcareSite);
		studyFundingSponsor.setGridId("funding sponsor");
		OrganizationAssignedIdentifier orgIdentifier= new OrganizationAssignedIdentifier();
		orgIdentifier.setType(OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER);
		orgIdentifier.setHealthcareSite(healthcareSite);
		orgIdentifier.setValue("funding sponsor id");
		study.addStudyOrganization(studyFundingSponsor);
		study.addIdentifier(orgIdentifier);
	}
	
	public static void addPI(StudyOrganization studyOrganization){
		StudyInvestigator studyInvestigator1= studyOrganization.getStudyInvestigators().get(0);
		studyInvestigator1.setRoleCode("Principal Investigator");
		HealthcareSiteInvestigator healthcareSiteInvestigator1= new HealthcareSiteInvestigator();
		LocalInvestigator localInvestigator= new LocalInvestigator();
		localInvestigator.setFirstName("first");
		localInvestigator.setLastName("last");
		localInvestigator.setNciIdentifier("ctep_assigned_id");
		healthcareSiteInvestigator1.setInvestigator(localInvestigator);
		studyInvestigator1.setSiteInvestigator(healthcareSiteInvestigator1);
	}
	
	public static void addConsent(Study study){
		Consent consent= study.getStudyVersion().getConsents().get(0);
		consent.setName("Male");
		
		consent= study.getStudyVersion().getConsents().get(1);
		consent.setName("Female");
	}
	
	public static void addStudyDesign(Study study){
		Epoch epoch= study.getEpochs().get(0);
		Arm arm= epoch.getArms().get(0);
		epoch.setAccrualCeiling(10);
		epoch.setDescriptionText("DescriptionText");
		epoch.setEnrollmentIndicator(true);
		epoch.setEpochOrder(1);
		epoch.setGridId("grid");
		epoch.setName("epoch 1");
		if(study.getRandomizedIndicator()){
			epoch.setRandomizedIndicator(true);
		}
		epoch.setReservationIndicator(false);
		epoch.setStratificationIndicator(true);
		epoch.setTreatmentIndicator(true);
		arm.setDescriptionText("DescriptionText1");
		arm.setGridId("grid1");
		arm.setName("arm 1");
		arm.setTargetAccrualNumber(20);
		arm= epoch.getArms().get(1);
		arm.setDescriptionText("DescriptionText2");
		arm.setGridId("grid2");
		arm.setName("arm 2");
		arm.setTargetAccrualNumber(30);
	}
	
	public static void addEligibility(Epoch epoch){
		EligibilityCriteria criteria = new InclusionEligibilityCriteria();
		criteria.setGridId("gridid");
		criteria.setNotApplicableIndicator(true);
		criteria.setQuestionNumber(1);
		criteria.setQuestionText("inc1");
		epoch.addEligibilityCriterion(criteria);
		criteria = new ExclusionEligibilityCriteria();
		criteria.setGridId("gridid");
		criteria.setQuestionNumber(1);
		criteria.setQuestionText("exc1");
		epoch.addEligibilityCriterion(criteria);
	}
	
	public static void addStratification(Epoch epoch){
		StratificationCriterion stratificationCriterion= epoch.getStratificationCriteria().get(0);
		StratificationCriterionPermissibleAnswer stratificationCriterionPermissibleAnswer= stratificationCriterion.getPermissibleAnswers().get(0);
		stratificationCriterion.setQuestionText("q1");
		stratificationCriterion.setQuestionNumber(1);
		stratificationCriterionPermissibleAnswer.setPermissibleAnswer("ans1");
		stratificationCriterionPermissibleAnswer= stratificationCriterion.getPermissibleAnswers().get(1);
		stratificationCriterionPermissibleAnswer.setPermissibleAnswer("ans2");
		stratificationCriterionPermissibleAnswer= stratificationCriterion.getPermissibleAnswers().get(2);
		stratificationCriterionPermissibleAnswer.setPermissibleAnswer("ans3");
		stratificationCriterion= epoch.getStratificationCriteria().get(1);
		stratificationCriterion.setQuestionText("q2");
		stratificationCriterionPermissibleAnswer= stratificationCriterion.getPermissibleAnswers().get(0);
		stratificationCriterionPermissibleAnswer.setPermissibleAnswer("ans1");
		stratificationCriterionPermissibleAnswer= stratificationCriterion.getPermissibleAnswers().get(1);
		stratificationCriterionPermissibleAnswer.setPermissibleAnswer("ans2");
		stratificationCriterionPermissibleAnswer= stratificationCriterion.getPermissibleAnswers().get(2);
		stratificationCriterionPermissibleAnswer.setPermissibleAnswer("ans3");
	}
	
	public static void addRandomization(Study study, Epoch epoch){
		if(!epoch.getRandomizedIndicator()) return;
		if(study.getRandomizationType() == RandomizationType.PHONE_CALL){
			PhoneCallRandomization phoneCallRandomization= new PhoneCallRandomization();
			phoneCallRandomization.setPhoneNumber("000-111-2222");
			epoch.setRandomization(phoneCallRandomization);
		}else{
			epoch.setRandomization(new BookRandomization());
		}
	}
	
	public static void addDisease(Study study){
		StudyDisease studyDisease= new StudyDisease();
		DiseaseTerm diseaseTerm= new DiseaseTerm();
		diseaseTerm.setCtepTerm("ctep");
		diseaseTerm.setMedraCode(12121);
		diseaseTerm.setTerm("term");
		studyDisease.setDiseaseTerm(diseaseTerm);
		studyDisease.setLeadDisease(true);
	}
	
	public static void addStudySites(Study study){
		study.addStudySite(createStudySite(true));
		study.addStudySite(createStudySite(false));
	}
	
	public static void addInvestigators(StudyOrganization studyOrganization){
		StudyInvestigator studyInvestigator1= studyOrganization.getStudyInvestigators().get(0);
		studyInvestigator1.setRoleCode("Site Investigator");
		HealthcareSiteInvestigator healthcareSiteInvestigator1= new HealthcareSiteInvestigator();
		LocalInvestigator localInvestigator= new LocalInvestigator();
		localInvestigator.setFirstName("first");
		localInvestigator.setLastName("last");
		localInvestigator.setNciIdentifier("ctep_assigned_id");
		healthcareSiteInvestigator1.setInvestigator(localInvestigator);
		studyInvestigator1.setSiteInvestigator(healthcareSiteInvestigator1);
		
		StudyInvestigator studyInvestigator2= studyOrganization.getStudyInvestigators().get(1);
		studyInvestigator2.setRoleCode("Site Investigator");
		HealthcareSiteInvestigator healthcareSiteInvestigator2= new HealthcareSiteInvestigator();
		RemoteInvestigator remoteInvestigator= new RemoteInvestigator();
		remoteInvestigator.setFirstName("first");
		remoteInvestigator.setLastName("last");
		remoteInvestigator.setNciIdentifier("remote_ctep_assigned_id");
		remoteInvestigator.setExternalId("external_id");
		healthcareSiteInvestigator2.setInvestigator(remoteInvestigator);
		studyInvestigator2.setSiteInvestigator(healthcareSiteInvestigator2);
	}
	
	public static StudySite createStudySite(boolean local){
		StudySite studySite= new StudySite();
		studySite.setIrbApprovalDate(new Date());
		HealthcareSite healthcareSite= createHealthcareSite(local);
		studySite.setHealthcareSite(healthcareSite);
		return studySite;
	}
	
	public static HealthcareSite createHealthcareSite(boolean local){
		HealthcareSite healthcareSite=null;
		if(local){
			LocalHealthcareSite localHealthcareSite= new LocalHealthcareSite();
			localHealthcareSite.setCtepCode("site_ctep_id");
			healthcareSite= localHealthcareSite;
		}else{
			RemoteHealthcareSite remoteHealthcareSite= new RemoteHealthcareSite();
			remoteHealthcareSite.setCtepCode("remote_ctep_id");
			remoteHealthcareSite.setExternalId("external_id");
			healthcareSite= remoteHealthcareSite;
		}
		return healthcareSite;
	}
	
	public static void addCompanions(Study study){
		Study companion1= getStudyWithDetails(RandomizationType.PHONE_CALL);
		companion1.setStandaloneIndicator(false);
		companion1.setCompanionIndicator(true);
		addConsent(companion1);
		addStudyDesign(companion1);
		Epoch epoch1= companion1.getEpochs().get(0);
		addEligibility(epoch1);
		addStratification(epoch1);
		addRandomization(companion1, epoch1);
		addDisease(companion1);
		addStudySites(companion1);
		addInvestigators(companion1.getStudySites().get(0));
		addInvestigators(companion1.getStudySites().get(1));
		
		CompanionStudyAssociation companionStudyAssociation1= new CompanionStudyAssociation();
		companionStudyAssociation1.setCompanionStudy(companion1);
		companionStudyAssociation1.setMandatoryIndicator(true);
		companionStudyAssociation1.addStudySite(createStudySite(true));
		study.addCompanionStudyAssociation(companionStudyAssociation1);
		
		Study companion2= getStudyWithDetails(null);
		companion2.setStandaloneIndicator(true);
		companion2.setCompanionIndicator(true);
		addConsent(companion2);
		addStudyDesign(companion2);
		Epoch epoch2= companion2.getEpochs().get(0);
		addEligibility(epoch2);
		addStratification(epoch2);
		addDisease(companion2);
		addStudySites(companion2);
		addInvestigators(companion2.getStudySites().get(0));
		addInvestigators(companion2.getStudySites().get(1));
		
		CompanionStudyAssociation companionStudyAssociation2= new CompanionStudyAssociation();
		companionStudyAssociation2.setCompanionStudy(companion2);
		companionStudyAssociation2.setMandatoryIndicator(true);
		companionStudyAssociation2.addStudySite(createStudySite(true));
		study.addCompanionStudyAssociation(companionStudyAssociation2);
	}
	
	public static Participant getParticipantWithAddress(){
		Participant participant= new Participant();
		participant.setFirstName("First");
		participant.setLastName("Last");
		participant.setAdministrativeGenderCode("Male");
		participant.setBirthDate(new Date());
		participant.setEthnicGroupCode("Hispanic or Latino");
		participant.setRaceCode("Asian : Unknown");
		OrganizationAssignedIdentifier organizationAssignedIdentifier= new OrganizationAssignedIdentifier();
		organizationAssignedIdentifier.setType(OrganizationIdentifierTypeEnum.MRN);
		organizationAssignedIdentifier.setValue("org id value");
		participant.addIdentifier(organizationAssignedIdentifier);
		organizationAssignedIdentifier= new OrganizationAssignedIdentifier();
		organizationAssignedIdentifier.setType(OrganizationIdentifierTypeEnum.MRN);
		organizationAssignedIdentifier.setValue("org id value");
		organizationAssignedIdentifier.setHealthcareSite(createHealthcareSite(true));
		participant.addIdentifier(organizationAssignedIdentifier);
		SystemAssignedIdentifier systemAssignedIdentifier= new SystemAssignedIdentifier();
		systemAssignedIdentifier.setType("some_system_id_type");
		systemAssignedIdentifier.setValue("sys id value");
		systemAssignedIdentifier.setSystemName("some_system");
		participant.addIdentifier(systemAssignedIdentifier);
		Address address= new Address();
		address.setCity("Herndon");
		address.setCountryCode("US");
		address.setPostalCode("20170");
		address.setStateCode("VA");
		address.setStreetAddress("1391 Park Center Rd, Ste#420");
		participant.setAddress(address);
		return participant;
	}
}
