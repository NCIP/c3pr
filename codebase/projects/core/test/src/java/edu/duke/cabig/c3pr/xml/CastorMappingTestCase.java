package edu.duke.cabig.c3pr.xml;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.StatusType;
import edu.duke.cabig.c3pr.constants.StudyPart;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PhoneCallRandomization;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;

public class CastorMappingTestCase extends AbstractTestCase{

	private XmlMarshaller marshaller;
	
	private XMLParser xmlParser;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		marshaller= new XmlMarshaller("c3pr-study-xml-castor-mapping.xml");
		xmlParser= new XMLParser("c3pr-domain.xsd");
	}
	
	public Study buildStudy(){
		Study study= DomainObjectCreationHelper.getStudyWithDetails(RandomizationType.BOOK);
		DomainObjectCreationHelper.addConsent(study);
		DomainObjectCreationHelper.addStudyDesign(study);
		Epoch epoch= study.getEpochs().get(0);
		DomainObjectCreationHelper.addEligibility(epoch);
		DomainObjectCreationHelper.addStratification(epoch);
		DomainObjectCreationHelper.addRandomization(study, epoch);
		DomainObjectCreationHelper.addCompanions(study);
		DomainObjectCreationHelper.addDisease(study);
		DomainObjectCreationHelper.addStudySites(study);
		DomainObjectCreationHelper.addInvestigators(study.getStudyCoordinatingCenter());
		return study;
	}
	
	public StudyVersion buildStudyVersion(){
		Study study= buildStudy();
		StudyVersion studyVersion=study.getStudyVersion();
		studyVersion.setVersionStatus(StatusType.IN);
		studyVersion.setName("1.1");
		studyVersion.setVersionDate(new Date());
		List<StudyPart> studyParts= new ArrayList<StudyPart>();
		studyParts.add(StudyPart.COMPANION);
		studyParts.add(StudyPart.CONSENT);
		studyParts.add(StudyPart.DESIGN);
		studyParts.add(StudyPart.DETAIL);
		studyParts.add(StudyPart.DISEASE);
		studyParts.add(StudyPart.ELIGIBILITY);
		studyParts.add(StudyPart.RANDOMIZATION);
		studyParts.add(StudyPart.STRATIFICATION);
		studyVersion.setAmendmentReasons(studyParts);
		return studyVersion;
	}
	
	public void testStudyMarshalling() throws Exception{
		Study study= buildStudy();
		String xml= marshaller.toXML(study);
		System.out.println(xml);
		xmlParser.validate(xml.getBytes());
		assertNotNull(xml);
	}
	
	public void testStudyAmendmentMarshalling() throws Exception{
		StudyVersion studyVersion= buildStudyVersion();
		String xml= marshaller.toXML(studyVersion);
		System.out.println(xml);
		xmlParser.validate(xml.getBytes());
		assertNotNull(xml);
	}
	
	public void testParticipantMarshalling() throws Exception{
		Participant participant= DomainObjectCreationHelper.getParticipantWithAddress();
		String xml= marshaller.toXML(participant);
		System.out.println(xml);
		xmlParser.validate(xml.getBytes());
		assertNotNull(xml);
	}
	
	public void testStudyUnMarshalling() throws Exception{
		Study serializable= buildStudy();
		String xml= marshaller.toXML(serializable);
		System.out.println(xml);
		Study study= (Study)marshaller.fromXML(new StringReader(xml));
		assertNotNull(study);
		assertStudy(study);
	}
	
	public void testStudyAmendmentUnMarshalling() throws Exception{
		StudyVersion expected= buildStudyVersion();
		String xml= marshaller.toXML(expected);
		System.out.println(xml);
		StudyVersion actual= (StudyVersion)marshaller.fromXML(new StringReader(xml));
		assertNotNull(actual);
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getVersionDateStr(), actual.getVersionDateStr());
		assertEquals(expected.getAmendmentReasonInternal(), actual.getAmendmentReasonInternal());
		assertIdentifiers(expected.getStudy().getIdentifiers(), actual.getStudy().getIdentifiers());
	}
	
	public void testParticipantUnMarshalling() throws Exception{
		Participant expected= DomainObjectCreationHelper.getParticipantWithAddress();
		String xml= marshaller.toXML(expected);
		System.out.println(xml);
		Participant actual=(Participant)marshaller.fromXML(new StringReader(xml));
		assertNotNull(actual);
		assertEquals(expected.getFullName(), actual.getFullName());
		assertEquals(expected.getAdministrativeGenderCode(), actual.getAdministrativeGenderCode());
		assertEquals(expected.getBirthDateStr(), actual.getBirthDateStr());
		assertEquals(expected.getRaceCode(), actual.getRaceCode());
		assertEquals(expected.getEthnicGroupCode(), actual.getEthnicGroupCode());
		assertAddress(expected.getAddress(), actual.getAddress());
		assertIdentifiers(expected.getIdentifiers(), actual.getIdentifiers());
	}
	
	public void assertStudy(Study study){
		Study expectedStudy= buildStudy();
		assertStudy(expectedStudy, study);
	}
	
	public void assertStudy(Study expected, Study actual){
		assertStudyDetails(expected, actual);
		assertConsents(expected.getConsents(), actual.getConsents());
		assertStudyDesign(expected, actual);
		assertCompanions(expected, actual);
		assertStudyDiseases(expected.getStudyDiseases(), actual.getStudyDiseases());
	}
	
	public void assertStudyDetails(Study expected, Study actual){
		assertEquals(expected.getBlindedIndicator(), actual.getBlindedIndicator());
		assertEquals(expected.getMultiInstitutionIndicator(), actual.getMultiInstitutionIndicator());
		assertEquals(expected.getRandomizedIndicator(), actual.getRandomizedIndicator());
		assertEquals(expected.getRandomizationType(), actual.getRandomizationType());
		assertEquals(expected.getStratificationIndicator(), actual.getStratificationIndicator());
		assertEquals(expected.getStandaloneIndicator(), actual.getStandaloneIndicator());
		assertEquals(expected.getCompanionIndicator(), actual.getCompanionIndicator());
		assertEquals(expected.getShortTitleText(), actual.getShortTitleText());
		assertEquals(expected.getLongTitleText(), actual.getLongTitleText());
		assertEquals(expected.getDescriptionText(), actual.getDescriptionText());
		assertEquals(expected.getPrecisText(), actual.getPrecisText());
		assertEquals(expected.getPhaseCode(), actual.getPhaseCode());
		assertEquals(expected.getCoordinatingCenterStudyStatus(), actual.getCoordinatingCenterStudyStatus());
		assertEquals(expected.getType(), actual.getType());
		assertEquals(expected.getTargetAccrualNumber(), actual.getTargetAccrualNumber());
		assertIdentifiers(expected.getIdentifiers(), actual.getIdentifiers());
		assertStudyOrganizations(expected.getStudyOrganizations(), actual.getStudyOrganizations());
	}
	
	public void assertConsents(List<Consent> expected, List<Consent> actual){
		assertEquals(expected.size(), actual.size());
		for(int i=0 ; i<expected.size() ; i++){
			assertConsent(expected.get(i), actual.get(i));
		}
	}
	
	public void assertConsent(Consent expected, Consent actual){
		assertEquals(expected.getName(), actual.getName());
		for(int i=0 ; i<expected.getConsentVersions().size() ; i++){
			assertEquals(expected.getConsentVersions().get(i).getName(), actual.getConsentVersions().get(i).getName());
			assertEquals(expected.getConsentVersions().get(i).getEffectiveDateStr(), actual.getConsentVersions().get(i).getEffectiveDateStr());
		}
	}
	
	public void assertStudyDesign(Study expected, Study actual){
		assertEquals(expected.getEpochs().size(), actual.getEpochs().size());
		for(int i=0 ; i<expected.getEpochs().size() ; i++){
			assertEpoch(expected.getEpochs().get(i), actual.getEpochs().get(i));
		}
	}
	
	public void assertCompanions(Study expected, Study actual){
		assertEquals(expected.getCompanionStudyAssociations().size(), actual.getCompanionStudyAssociations().size());
		for(int i=0 ; i<expected.getCompanionStudyAssociations().size() ; i++){
			assertStudy(expected.getCompanionStudyAssociations().get(i).getCompanionStudy(), actual.getCompanionStudyAssociations().get(i).getCompanionStudy());
		}
	}
	
	public void assertStudyDiseases(List<StudyDisease> expected, List<StudyDisease> actual){
		assertEquals(expected.size(), actual.size());
		for(int i=0 ; i<expected.size() ; i++){
			assertStudyDisease(expected.get(i), actual.get(i));
		}
	}
	
	public void assertStudyDisease(StudyDisease expected, StudyDisease actual){
		assertEquals(expected.getLeadDisease(), actual.getLeadDisease());
		assertEquals(expected.getDiseaseTerm().getMedraCode(), actual.getDiseaseTerm().getMedraCode());
		assertEquals(expected.getDiseaseTerm().getCtepTerm(), actual.getDiseaseTerm().getCtepTerm());
		assertEquals(expected.getDiseaseTerm().getTerm(), actual.getDiseaseTerm().getTerm());
	}
	
	public void assertEpoch(Epoch expected, Epoch actual){
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getEpochOrder(), actual.getEpochOrder());
		assertEquals(expected.getDescriptionText(), actual.getDescriptionText());
		assertEquals(expected.getAccrualCeiling(), actual.getAccrualCeiling());
		assertEquals(expected.getStratificationIndicator(), actual.getStratificationIndicator());
		assertEquals(expected.getRandomizedIndicator(), actual.getRandomizedIndicator());
		assertEquals(expected.getTreatmentIndicator(), actual.getTreatmentIndicator());
		assertEquals(expected.getReservationIndicator(), actual.getReservationIndicator());
		assertEquals(expected.getEnrollmentIndicator(), actual.getEnrollmentIndicator());
		assertArms(expected.getArms(), actual.getArms());
		assertEligibilityCriteria(expected.getEligibilityCriteria(), actual.getEligibilityCriteria());
		assertStratificationCriteria(expected.getStratificationCriteria(), actual.getStratificationCriteria());
		if(expected.getRandomizedIndicator()){
			assertEquals(expected.getRandomization().getClass(), actual.getRandomization().getClass());
			if (expected.getRandomization() instanceof PhoneCallRandomization) {
				assertEquals(((PhoneCallRandomization) expected.getRandomization()).getPhoneNumber(), ((PhoneCallRandomization) actual.getRandomization()).getPhoneNumber());
			}
		}
	}
	
	public void assertStratificationCriteria(List<StratificationCriterion> expected, List<StratificationCriterion> actual){
		assertEquals(expected.size(), actual.size());
		for(int i=0 ; i<expected.size() ; i++){
			assertStratificationCriterion(expected.get(i), actual.get(i));
		}
	}
	
	public void assertStratificationCriterion(StratificationCriterion expected, StratificationCriterion actual){
		assertEquals(expected.getClass(), actual.getClass());
		assertEquals(expected.getQuestionText(), actual.getQuestionText());
		assertEquals(expected.getQuestionNumber(), actual.getQuestionNumber());
		for(int i=0 ; i<expected.getPermissibleAnswers().size() ; i++){
			assertEquals(expected.getPermissibleAnswers().get(i).getPermissibleAnswer(), actual.getPermissibleAnswers().get(i).getPermissibleAnswer());
		}
	}
	
	public void assertEligibilityCriteria(List<EligibilityCriteria> expected, List<EligibilityCriteria> actual){
		assertEquals(expected.size(), actual.size());
		for(int i=0 ; i<expected.size() ; i++){
			assertEligibilityCriterion(expected.get(i), actual.get(i));
		}
	}
	
	public void assertEligibilityCriterion(EligibilityCriteria expected, EligibilityCriteria actual){
		assertEquals(expected.getClass(), actual.getClass());
		assertEquals(expected.getQuestionText(), actual.getQuestionText());
		assertEquals(expected.getQuestionNumber(), actual.getQuestionNumber());
	}
	
	public void assertArms(List<Arm> expected, List<Arm> actual){
		assertEquals(expected.size(), actual.size());
		for(int i=0 ; i<expected.size() ; i++){
			assertArm(expected.get(i), actual.get(i));
		}
	}
	
	public void assertArm(Arm expected, Arm actual){
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescriptionText(), actual.getDescriptionText());
		assertEquals(expected.getTargetAccrualNumber(), actual.getTargetAccrualNumber());
	}
	
	public void assertIdentifiers(List<Identifier> expected, List<Identifier> actual){
		assertEquals(expected.size(), actual.size());
		for(int i=0 ; i<expected.size() ; i++){
			assertIdentifier(expected.get(i), actual.get(i));
		}
	}
	
	public void assertIdentifier(Identifier expected, Identifier actual){
		assertEquals(expected.getTypeInternal(), actual.getTypeInternal());
		assertEquals(expected.getValue(), actual.getValue());
		if (expected instanceof OrganizationAssignedIdentifier) {
			if (actual instanceof SystemAssignedIdentifier) {
				fail();
			}
			OrganizationAssignedIdentifier expectedId = (OrganizationAssignedIdentifier) expected;
			OrganizationAssignedIdentifier actualId = (OrganizationAssignedIdentifier) actual;
			if(expectedId.getHealthcareSite()!=null){
				assertEquals(expectedId.getHealthcareSite().getIdentifiersAssignedToOrganization().get(0).getValue(), actualId.getHealthcareSite().getIdentifiersAssignedToOrganization().get(0).getValue());
			}
		}else if (actual instanceof OrganizationAssignedIdentifier) {
				fail();
		}
	}
	
	public void assertStudyOrganizations(List<StudyOrganization> expected, List<StudyOrganization> actual){
		assertEquals(expected.size(), actual.size());
		for(int i=0 ; i<expected.size() ; i++){
			assertStudyOrganization(expected.get(i), actual.get(i));
		}
	}
	public void assertStudyOrganization(StudyOrganization expected, StudyOrganization actual){
		assertHealthcareSite(expected.getHealthcareSite(), actual.getHealthcareSite());
		assertStudyInvestigators(expected.getStudyInvestigators(), actual.getStudyInvestigators());
		if (expected instanceof StudySite) {
			StudySite expectedSite = (StudySite) expected;
			StudySite actualSite= (StudySite) actual;
			assertEquals(expectedSite.getIrbApprovalDateStr(), actualSite.getIrbApprovalDateStr());
			assertEquals(expectedSite.getStartDateStr(), actualSite.getStartDateStr());
			assertEquals(expectedSite.getRoleCode(), actualSite.getRoleCode());
			assertEquals(expectedSite.getSiteStudyStatus(), actualSite.getSiteStudyStatus());
		}
	}
	
	public void assertHealthcareSite(HealthcareSite expected, HealthcareSite actual){
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescriptionText(), actual.getDescriptionText());
		assertAddress(expected.getAddress(), actual.getAddress());
		assertIdentifiers(expected.getIdentifiersAssignedToOrganization(), actual.getIdentifiersAssignedToOrganization());
	}
	
	public void assertAddress(Address expected, Address actual){
		assertEquals(expected.getAddressString(), actual.getAddressString());
	}
	
	public void assertStudyInvestigators(List<StudyInvestigator> expected, List<StudyInvestigator> actual){
		assertEquals(expected.size(), actual.size());
		for(int i=0 ; i<expected.size() ; i++){
			assertStudyInvestigator(expected.get(i), actual.get(i));
		}
	}
	
	public void assertStudyInvestigator(StudyInvestigator expected, StudyInvestigator actual){
		assertEquals(expected.getRoleCode(), actual.getRoleCode());
		assertEquals(expected.getStatusCode(), actual.getStatusCode());
		assertEquals(expected.getStartDate(), actual.getStartDate());
		assertEquals(expected.getEndDate(), actual.getEndDate());
		assertHealthcareSiteInvestigator(expected.getHealthcareSiteInvestigator(), actual.getHealthcareSiteInvestigator());
	}
	
	public void assertHealthcareSiteInvestigator(HealthcareSiteInvestigator expected, HealthcareSiteInvestigator actual){
		assertEquals(expected.getInvestigator().getNciIdentifier(), actual.getInvestigator().getNciIdentifier());
	}
	
}
