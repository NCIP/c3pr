package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import org.easymock.classextension.EasyMock;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.customfield.CustomField;
import edu.duke.cabig.c3pr.domain.customfield.CustomFieldDefinition;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class StudyTestCase.
 */
public class StudyTestCase extends AbstractTestCase{
	
	/** The simple study. */
	private Study simpleStudy ;
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		basicStudy = studyCreationHelper.createBasicStudy();
		basicStudy.setC3prErrorMessages(c3prErrorMessages);
		basicStudy.setC3PRExceptionHelper(c3prExceptionHelper);
		simpleStudy = new Study();
	}
	
	/** The c3pr exception helper. */
	C3PRExceptionHelper c3prExceptionHelper = registerMockFor(C3PRExceptionHelper.class);
	
	/** The c3pr error messages. */
	MessageSource c3prErrorMessages = registerMockFor(MessageSource.class);
	
	/** The study creation helper. */
	private StudyCreationHelper studyCreationHelper = new StudyCreationHelper() ;
	
	/** The basic study. */
	private Study basicStudy;
	
	/**
	 * Sets the study creation helper.
	 * 
	 * @param studyCreationHelper the new study creation helper
	 */
	public void setStudyCreationHelper(StudyCreationHelper studyCreationHelper) {
		this.studyCreationHelper = studyCreationHelper;
	}

	/**
	 * Test data entry status incomplete case1.
	 * 
	 * @throws Exception the exception
	 */
	public void testDataEntryStatusIncompleteCase1() throws Exception {
		EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ENROLLING_EPOCH.CODE", null, null)).andReturn("300");
		EasyMock.expect(c3prExceptionHelper.getRuntimeException(300)).andReturn(new C3PRCodedRuntimeException(300, "exception message"));
		replayMocks();
		try {
			List<Error> errors = new ArrayList<Error>();
			basicStudy.evaluateDataEntryStatus(errors);
			//fail("Should have thrown C3PRCodedException");
		} catch (C3PRCodedRuntimeException e) {
			assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException); 
		}
		verifyMocks();
	}
	
	/**
	 * Test data entry status incomplete case2.
	 * 
	 * @throws Exception the exception
	 */
	public void testDataEntryStatusIncompleteCase2() throws Exception {
		basicStudy.addStudySite(new StudySite());
		EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ENROLLING_EPOCH.CODE", null, null)).andReturn("300");
		EasyMock.expect(c3prExceptionHelper.getRuntimeException(300)).andReturn(new C3PRCodedRuntimeException(300, "exception message"));
		replayMocks();
		try {
			List<Error> errors = new ArrayList<Error>();
			basicStudy.evaluateDataEntryStatus(errors);
//			fail("Should have thrown C3PRCodedException");
		} catch (C3PRCodedRuntimeException e) {
			assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException); 
		}
		verifyMocks();
		
	}
	
	/**
	 * Test data entry status incomplete case3.
	 * 
	 * @throws Exception the exception
	 */
	public void testDataEntryStatusIncompleteCase3() throws Exception {
		basicStudy.addStudySite(new StudySite());
		Epoch nonTreatmentEpoch = new Epoch();
		basicStudy.addEpoch(nonTreatmentEpoch);
		EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ENROLLING_EPOCH.CODE", null, null)).andReturn("302");
		EasyMock.expect(c3prExceptionHelper.getRuntimeException(302)).andReturn(new C3PRCodedRuntimeException(302, "exception message"));
		replayMocks();
		try {
			List<Error> errors = new ArrayList<Error>();
			basicStudy.evaluateDataEntryStatus(errors);
//			fail("Should have thrown C3PRCodedException");
		} catch (C3PRCodedRuntimeException e) {
			assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException); 
		}
		verifyMocks();
	}
	
	/**
	 * Test data entry status incomplete case4.
	 * 
	 * @throws Exception the exception
	 */
	public void testDataEntryStatusIncompleteCase4() throws Exception {
		studyCreationHelper.addStudySiteAndRandomizedTreatmentEpochToBasicStudy(basicStudy);
		basicStudy.getEpochs().get(0).setExceptionHelper(c3prExceptionHelper);
		basicStudy.getEpochs().get(0).setC3prErrorMessages(c3prErrorMessages);
		basicStudy.setStratificationIndicator(false);
		EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ATLEAST_2_ARMS_FOR_RANDOMIZED_EPOCH.CODE", null, null)).andReturn("306");
		EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(306),EasyMock.aryEq(new String[]{"Treatment Epoch1"}))).andReturn(new C3PRCodedRuntimeException(306, "exception message"));
		replayMocks();	
		try {
			List<Error> errors = new ArrayList<Error>();
			basicStudy.evaluateDataEntryStatus(errors);
//			fail("Should have thrown C3PRCodedRuntimeException");
		} catch (C3PRCodedRuntimeException e) {
			assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException); 
		}
		verifyMocks();
	}
	
	/**
	 * Test data entry status incomplete case5.
	 * 
	 * @throws Exception the exception
	 */
	public void testDataEntryStatusIncompleteCase5() throws Exception {
		studyCreationHelper.addStudySiteAndRandomizedTreatmentEpochWith2ArmsToBasicStudy(basicStudy);
		basicStudy.getEpochs().get(0).setExceptionHelper(c3prExceptionHelper);
		basicStudy.getEpochs().get(0).setC3prErrorMessages(c3prErrorMessages);
		EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.STRATIFICATION_CRITERIA_OR_STRATUM_GROUPS_FOR_RANDOMIZED_EPOCH.CODE", null, null)).andReturn("304");
		EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(304),EasyMock.aryEq(new String[]{"Treatment Epoch1"}))).andReturn(new C3PRCodedRuntimeException(304, "exception message"));
		basicStudy.setStratificationIndicator(true);
		basicStudy.getEpochs().get(0).setStratificationIndicator(true);
		replayMocks();	
		try {
			List<Error> errors = new ArrayList<Error>();
			basicStudy.evaluateDataEntryStatus(errors);
//			fail("Should have thrown C3PRCodedRuntimeException");
		} catch (C3PRCodedRuntimeException e) {
			assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException); 
		}
		verifyMocks();
	}
	
	/**
	 * Test data entry status incomplete case6.
	 * 
	 * @throws Exception the exception
	 */
	public void testDataEntryStatusIncompleteCase6() throws Exception {
		studyCreationHelper.addStudySiteRandomizedEnrollingTreatmentEpochWith2ArmsAndStratumGroupsToBasicStudy(basicStudy);
		basicStudy.getEpochs().get(0).setExceptionHelper(c3prExceptionHelper);
		basicStudy.getEpochs().get(0).setC3prErrorMessages(c3prErrorMessages);
		basicStudy.setStratificationIndicator(true);
		basicStudy.setRandomizedIndicator(true);
		basicStudy.setRandomizationType(RandomizationType.PHONE_CALL);
		basicStudy.getEpochs().get(0).setStratificationIndicator(true);
		basicStudy.getEpochs().get(0).setRandomization(new PhoneCallRandomization());
		EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.PHONE_NUMBER_FOR_PHONE_CALL_RANDOMIZED_EPOCH.CODE", null, null)).andReturn("308");
		EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(308),EasyMock.aryEq(new String[]{"Treatment Epoch1"}))).andReturn(new C3PRCodedRuntimeException(308, "exception message"));
		replayMocks();	
		List<Error> errors = new ArrayList<Error>();
		basicStudy.evaluateDataEntryStatus(errors);
		assertEquals("Wrong number of errors returned ",1, errors.size()); 
		verifyMocks();
	}
	
	/**
	 * Test data entry status incomplete case7.
	 * 
	 * @throws Exception the exception
	 */
	public void testDataEntryStatusIncompleteCase7() throws Exception {
		Study study = studyCreationHelper.createBasicStudyObject();
		studyCreationHelper.addStudySiteRandomizedEnrollingTreatmentEpochWith2ArmsAndStratumGroupsToBasicStudy(study);
		study.getEpochs().get(0).setExceptionHelper(c3prExceptionHelper);
		study.getEpochs().get(0).setC3prErrorMessages(c3prErrorMessages);
		study.setStratificationIndicator(true);
		study.setRandomizedIndicator(true);
		study.setRandomizationType(RandomizationType.PHONE_CALL);
		study.getEpochs().get(0).setStratificationIndicator(true);
		study.getEpochs().get(0).setRandomization(new PhoneCallRandomization());
		EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.PHONE_NUMBER_FOR_PHONE_CALL_RANDOMIZED_EPOCH.CODE", null, null)).andReturn("308");
		EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(308),EasyMock.aryEq(new String[]{"Treatment Epoch1"}))).andReturn(new C3PRCodedRuntimeException(308, "exception message"));
		replayMocks();	
		List<Error> errors = new ArrayList<Error>();
		study.evaluateDataEntryStatus(errors);
		assertEquals("Wrong number of errors returned ",1, errors.size()); 
		verifyMocks();
	}
	
	/**
	 * Test data entry status complete case1.
	 * 
	 * @throws Exception the exception
	 */
	public void testDataEntryStatusCompleteCase1() throws Exception {
		studyCreationHelper.addStudySiteAndEnrollingEpochToBasicStudy(basicStudy);
		basicStudy.setStratificationIndicator(false);
		replayMocks();
			List<Error> errors = new ArrayList<Error>();
			assertEquals("Data Entry Status should evaluate to Complete",StudyDataEntryStatus.COMPLETE,basicStudy.evaluateDataEntryStatus(errors));
			verifyMocks();
	}
	
	/**
	 * Test data entry status complete case2.
	 * 
	 * @throws Exception the exception
	 */
	public void testDataEntryStatusCompleteCase2() throws Exception {
		studyCreationHelper.addStudySiteRandomizedTreatmentEpochWith2ArmsStratumGroupsAndRandomizationToBasicStudy(basicStudy);
		basicStudy.setStratificationIndicator(true);
		basicStudy.getEpochs().get(0).setStratificationIndicator(true);
		replayMocks();	
		List<Error> errors = new ArrayList<Error>();
		assertEquals("Wrong Data Entry Status",StudyDataEntryStatus.COMPLETE,basicStudy.evaluateDataEntryStatus(errors));
		verifyMocks();
	}
	
	/**
	 * Test site study status pending case1.
	 * 
	 * @throws Exception the exception
	 */
	public void testSiteStudyStatusPendingCase1() throws Exception {
		studyCreationHelper.addStudySiteAndEnrollingEpochToBasicStudy(basicStudy);
		basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
		assertEquals("Wrong Site study status",SiteStudyStatus.PENDING,basicStudy.getStudySites().get(0).evaluateSiteStudyStatus());
		
	}
	
	/**
	 * Test site study status active case1.
	 * 
	 * @throws Exception the exception
	 */
	public void testSiteStudyStatusActiveCase1() throws Exception {
		studyCreationHelper.addStudySiteRandomizedTreatmentEpochWith2ArmsStratumGroupsAndRandomizationToBasicStudy(basicStudy);
		basicStudy.setStratificationIndicator(false);
		basicStudy.setCoordinatingCenterStudyStatus(basicStudy.evaluateCoordinatingCenterStudyStatus());
		assertEquals("Study status should evaluate to Active",CoordinatingCenterStudyStatus.OPEN,basicStudy.getCoordinatingCenterStudyStatus());
		assertEquals("Site Study status should evaluate to Open",SiteStudyStatus.ACTIVE,basicStudy.getStudySites().get(0).evaluateSiteStudyStatus());
	}
	
	/**
	 * Test get local identifiers
	 * this method tests if study doesnt have local identifier, it should return empty arraylist
	 */
	public void testGetLocalIdentifiers1(){
		List<Identifier> identifiers = basicStudy.getLocalIdentifiers();
		assertEquals("No local identifierfound", 0 , identifiers.size());
	}
	

	/**
	 * Test get local identifiers
	 * this method tests if study has Protocol Authority Identifier it shd return empty arraylist
	 */
	public void testGetLocalIdentifiers2(){
		Identifier identifier  = registerMockFor(Identifier.class);
		simpleStudy.getIdentifiers().add(identifier);
		EasyMock.expect(identifier.getType()).andReturn("Protocol Authority Identifier");
		replayMocks();
		List<Identifier> identifiers = simpleStudy.getLocalIdentifiers();
		assertEquals("No local identifierfound", 0 , identifiers.size());
		verifyMocks();
	}
	
	/**
	 * Test get local identifiers
	 * this method tests if study has coordinating center identifier it shd return empty arraylist
	 */
	public void testGetLocalIdentifiers3(){
		Identifier identifier  = registerMockFor(Identifier.class);
		simpleStudy.getIdentifiers().add(identifier);
		EasyMock.expect(identifier.getType()).andReturn("Coordinating Center Identifier").times(2);
		replayMocks();
		List<Identifier> identifiers = simpleStudy.getLocalIdentifiers();
		assertEquals("No local identifierfound", 0 , identifiers.size());
		verifyMocks();
	}
	
	/**
	 * Test get local identifiers
	 * this method tests if study has identifiers.
	 */
	public void testGetLocalIdentifiers4(){
		Identifier identifier  = registerMockFor(Identifier.class);
		Identifier identifier1 = registerMockFor(Identifier.class);
		Identifier identifier2  = registerMockFor(Identifier.class);
		simpleStudy.getIdentifiers().add(identifier);
		simpleStudy.getIdentifiers().add(identifier1);
		simpleStudy.getIdentifiers().add(identifier2);
		
		EasyMock.expect(identifier.getType()).andReturn("Coordinating Center Identifier").times(2);
		EasyMock.expect(identifier1.getType()).andReturn("C3D").times(2) ;
		EasyMock.expect(identifier2.getType()).andReturn("C3PR").times(2) ;
		replayMocks();
		List<Identifier> identifiers = simpleStudy.getLocalIdentifiers();
		assertEquals("Local Idnetifier C3D and C3PR Present", 2 , identifiers.size());
		verifyMocks();
	}
	
	/**
	 * Test remove study organization
	 * 
	 */
	public void testRemoveStudyOrganization(){
		StudyOrganization organization  = registerMockFor(StudyOrganization.class);
		organization.setStudy(simpleStudy);
		
		StudyOrganization organization2  = registerMockFor(StudyOrganization.class);
		organization2.setStudy(simpleStudy);
		
		replayMocks();
		simpleStudy.addStudyOrganization(organization);
		simpleStudy.addStudyOrganization(organization2);
		
		assertEquals("Study should have 2 study organization", 2 , simpleStudy.getStudyOrganizations().size());
		simpleStudy.removeStudyOrganization(organization2);
		assertEquals("Study should have only 1 study organization", 1 , simpleStudy.getStudyOrganizations().size());
		verifyMocks();
	}
	
	/**
	 * Test remove epoch
	 * 
	 */
	public void testRemoveEpoch(){
		Epoch epoch  = registerMockFor(Epoch.class);
		epoch.setStudy(simpleStudy);

		Epoch epoch1  = registerMockFor(Epoch.class);
		epoch1.setStudy(simpleStudy);

		replayMocks();

		simpleStudy.addEpoch(epoch1);
		simpleStudy.addEpoch(epoch);

		assertEquals("Study should have 2 epochs ", 2 , simpleStudy.getEpochs().size());
		simpleStudy.removeEpoch(epoch);
		assertEquals("Study should have 1 epoch ", 1 , simpleStudy.getEpochs().size());
		verifyMocks();
	}
	
	/**
	 * Test remove study disease
	 * 
	 */
	public void testRemoveStudyDisease(){
		StudyDisease disease  = registerMockFor(StudyDisease.class);
		disease.setStudy(simpleStudy);
		StudyDisease disease2  = registerMockFor(StudyDisease.class);
		disease2.setStudy(simpleStudy);
		replayMocks();
		simpleStudy.addStudyDisease(disease2);
		simpleStudy.addStudyDisease(disease);
		assertEquals("Study should have 2 study disease", 2 , simpleStudy.getStudyDiseases().size());
		simpleStudy.removeStudyDisease(disease2);
		assertEquals("Study should have only 1 study disease", 1 , simpleStudy.getStudyDiseases().size());
		verifyMocks();
	}
	
	/**
	 * Test remove all study disease
	 * 
	 */
	
	public void testRemoveAllStudyDisease(){
		StudyDisease disease  = registerMockFor(StudyDisease.class);
		disease.setStudy(simpleStudy);
		
		StudyDisease disease2  = registerMockFor(StudyDisease.class);
		disease2.setStudy(simpleStudy);
		
		replayMocks();
		
		simpleStudy.addStudyDisease(disease2);
		simpleStudy.addStudyDisease(disease);
		
		assertEquals("Study should have 2 study disease", 2 , simpleStudy.getStudyDiseases().size());
		simpleStudy.removeAllStudyDisease();
		assertEquals("Study should have 0 study disease", 0 , simpleStudy.getStudyDiseases().size());
		verifyMocks();
	}
	

	/**
	 * Test remove study site
	 * 
	 */
	public void testRemoveStudySite(){
		StudySite site  = registerMockFor(StudySite.class);
		site.setStudy(simpleStudy);
		
		StudySite site2  = registerMockFor(StudySite.class);
		site2.setStudy(simpleStudy);
		
		replayMocks();
		
		simpleStudy.addStudySite(site2);
		simpleStudy.addStudySite(site);
		
		assertEquals("Study should have 2 study sites", 2 , simpleStudy.getStudyOrganizations().size());
		
		simpleStudy.removeStudySite(site2);
		assertEquals("Study should have only 1 study site", 1 , simpleStudy.getStudyOrganizations().size());
		verifyMocks();
	}
	
	/**
	 * Test remove identifiers
	 * 
	 */
	public void testRemoveIdentifiers(){
		Identifier identifier  = registerMockFor(Identifier.class);
		Identifier identifier1  = registerMockFor(Identifier.class);
		
		replayMocks();
		
		simpleStudy.addIdentifier(identifier1);
		simpleStudy.addIdentifier(identifier);
		
		assertEquals("Study should have 2 identifiers", 2 , simpleStudy.getIdentifiers().size());
		
		simpleStudy.removeIdentifier(identifier);
		assertEquals("Study should have 1 identifier", 1 , simpleStudy.getIdentifiers().size());
		verifyMocks();
	}
	
	/**
	 * Test get latest consent version
	 * 
	 */
	public void testGetLatestConsentVersion(){
		simpleStudy.setConsentVersion("10/08/1981");
		assertEquals("Latest consent version should be 10/08/1981", "10/08/1981" , simpleStudy.getLatestConsentVersion());
	}
	
	/**
	 * Test get latest consent version
	 * 
	 */
	public void testGetLatestConsentVersion1(){
		simpleStudy.setConsentVersion("10/08/1970");
		StudyAmendment amendment  = registerMockFor(StudyAmendment.class);
		EasyMock.expect(amendment.getConsentVersion()).andReturn("10/08/1981");
		EasyMock.expect(amendment.getConsentChangedIndicator()).andReturn(true);
		replayMocks();
		simpleStudy.getStudyAmendments().add(amendment);
		
		assertEquals("Latest consent version should be 10/08/1981", "10/08/1981" , simpleStudy.getLatestConsentVersion());
		}
	
	/**
	 * Test get companion indicator display value
	 * 
	 */
	public void testGetCompanionIndicatorDisplayValue(){
		simpleStudy.setCompanionIndicator(false);
		assertEquals("Companion indicator display value should be No", "No", simpleStudy.getCompanionIndicatorDisplayValue());
	}
	
	/**
	 * Test get companion indicator display value
	 * 
	 */
	public void testGetCompanionIndicatorDisplayValue1(){
		simpleStudy.setCompanionIndicator(true);
		assertEquals("Companion indicator display value should be Yes", "Yes", simpleStudy.getCompanionIndicatorDisplayValue());
	}
	
	/**
	 * Test add custom field
	 * 
	 */
	public void testAddCustomField(){
		CustomField customField  = registerMockFor(CustomField.class);
		customField.setStudy(simpleStudy);
		CustomField customField1  = registerMockFor(CustomField.class);
		customField1.setStudy(simpleStudy);
		replayMocks();
		simpleStudy.addCustomField(customField);
		simpleStudy.addCustomField(customField1);
		
		assertEquals("Study should have 2 custom fields", 2 , simpleStudy.getCustomFields().size());
		verifyMocks();
	}
	
	/**
	 * Test add custom field definition
	 * 
	 */
	public void testAddCustomFieldDefinition(){
		CustomFieldDefinition customFieldDefinition  = registerMockFor(CustomFieldDefinition.class);
		customFieldDefinition.setStudy(simpleStudy);
		CustomFieldDefinition customFieldDefinition1  = registerMockFor(CustomFieldDefinition.class);
		customFieldDefinition1.setStudy(simpleStudy);
		replayMocks();
		simpleStudy.addCustomFieldDefinition(customFieldDefinition);
		simpleStudy.addCustomFieldDefinition(customFieldDefinition1);
		
		assertEquals("Study should have 2 custom fields", 2 , simpleStudy.getCustomFieldDefinitions().size());
		verifyMocks();
	}

	
	/**
	 * Test get companion study site by nci identifier
	 * 
	 */
	public void testGetCompanionStudySite(){
		simpleStudy.setCompanionIndicator(false);
		CompanionStudyAssociation association = registerMockFor(CompanionStudyAssociation.class);
		association.setParentStudy(simpleStudy);
		
		List<StudySite> listStudySite = new ArrayList<StudySite>();
		StudySite studySite = registerMockFor(StudySite.class);
		HealthcareSite healthcareSite = registerMockFor(HealthcareSite.class);
		listStudySite.add(studySite);
		
		EasyMock.expect(association.getStudySites()).andReturn(listStudySite);
		EasyMock.expect(studySite.getHealthcareSite()).andReturn(healthcareSite);
		EasyMock.expect(healthcareSite.getNciInstituteCode()).andReturn("NC010");
		
		replayMocks();

		simpleStudy.addCompanionStudyAssociation(association);
		
		assertNotNull("study site found with nc010", simpleStudy.getCompanionStudySite("NC010"));
		verifyMocks();
	}

	/**
	 * Test get companion study site by nci identifier
	 * 
	 */
	public void testGetCompanionStudySite1(){
//		simpleStudy.setCompanionIndicator(false);
//		Study companion = new Study();
//		companion.setCompanionIndicator(true);
//		
//		CompanionStudyAssociation association = registerMockFor(CompanionStudyAssociation.class);
//		association.setParentStudy(simpleStudy);
//		List<StudySite> listStudySite = new ArrayList<StudySite>();
//		StudySite studySite = registerMockFor(StudySite.class);
//		listStudySite.add(studySite);
//
//		HealthcareSite healthcareSite = registerMockFor(HealthcareSite.class);
//
//		EasyMock.expect(association.getStudySites()).andReturn(listStudySite);
//		EasyMock.expect(studySite.getHealthcareSite()).andReturn(healthcareSite);
//		EasyMock.expect(healthcareSite.getNciInstituteCode()).andReturn("NC010");
//		
//		replayMocks();
//
//		companion.addCompanionStudyAssociation(association);
//		assertNotNull("study site found with nc010", companion.getCompanionStudySite("NC010"));
//		verifyMocks();
	}
	
	/**
	 * Test get companion study site by nci identifier
	 * 
	 */
	public void testGetCompanionStudySite2(){
		simpleStudy.setCompanionIndicator(false);
		CompanionStudyAssociation association = registerMockFor(CompanionStudyAssociation.class);
		association.setParentStudy(simpleStudy);
		
		List<StudySite> listStudySite = new ArrayList<StudySite>();
		StudySite studySite = registerMockFor(StudySite.class);
		HealthcareSite healthcareSite = registerMockFor(HealthcareSite.class);
		listStudySite.add(studySite);
		
		EasyMock.expect(association.getStudySites()).andReturn(listStudySite);
		EasyMock.expect(studySite.getHealthcareSite()).andReturn(healthcareSite);
		EasyMock.expect(healthcareSite.getNciInstituteCode()).andReturn("NC011");
		
		replayMocks();

		simpleStudy.addCompanionStudyAssociation(association);
		
		assertNull("study site not found with nc010", simpleStudy.getCompanionStudySite("NC010"));
		verifyMocks();
	}
	
	
	/**
	 * Test get study organization by nci identifier
	 * 
	 */
	public void testGetStudyOrganization(){
		StudyOrganization organization = registerMockFor(StudyOrganization.class);
		organization.setStudy(simpleStudy);
		HealthcareSite healthcareSite = registerMockFor(HealthcareSite.class);
		EasyMock.expect(organization.getHealthcareSite()).andReturn(healthcareSite);
		EasyMock.expect(healthcareSite.getNciInstituteCode()).andReturn("NC010");
		
		replayMocks();
		
		simpleStudy.addStudyOrganization(organization);

		assertNotNull("study site found with nc010", simpleStudy.getStudyOrganization("NC010"));
		
		verifyMocks();
	}
	

	/**
	 * Test get study organization by nci identifier
	 * 
	 */
	public void testGetStudyOrganization1(){
		try {
			simpleStudy.getStudyOrganization("NC010");
		} catch (C3PRCodedRuntimeException e) {
			assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException); 
		}
	}
	
	/**
	 * Test get affiliate study sites 
	 * 
	 */
	public void testGetAffiliateStudySites(){
		HealthcareSite healthcareSite = registerMockFor(HealthcareSite.class);
		HealthcareSite healthcareSite1 = registerMockFor(HealthcareSite.class);
		
		StudySite studySite = new StudySite();
		studySite.setStudy(simpleStudy);
		studySite.setHealthcareSite(healthcareSite);
		
		StudyCoordinatingCenter coordinatingCenter = new StudyCoordinatingCenter();
		coordinatingCenter.setStudy(simpleStudy);
		coordinatingCenter.setHealthcareSite(healthcareSite1);
		
		EasyMock.expect(healthcareSite.getNciInstituteCode()).andReturn("NC010");
		EasyMock.expect(healthcareSite1.getNciInstituteCode()).andReturn("NC011");
		
		replayMocks();
		
		simpleStudy.addStudySite(studySite);
		simpleStudy.addStudyOrganization(coordinatingCenter);

		assertEquals("one affiliate study site present", 1 ,simpleStudy.getAffiliateStudySites().size());
		
		verifyMocks();
	}
	
	/**
	 * Test isMultisite
	 * 
	 */
	public void testIsMultisite(){
		simpleStudy.setMultiInstitutionIndicator(true);
		simpleStudy.setCompanionIndicator(true);
		assertFalse("This is not a multisite study", simpleStudy.isMultisite());
	}
	
	/**
	 * Test isMultisite
	 * 
	 */
	public void testIsMultisite1(){
		simpleStudy.setMultiInstitutionIndicator(true);
		simpleStudy.setCompanionIndicator(false);
		assertTrue("This is  a multisite study", simpleStudy.isMultisite());
	}
	
	
	/**
	 * test get accrual count
	 */
	public void testGetAccrualCount(){
		
		// will do it later
		// might need method level mocking for classes
	}
	
	/**
	 * test buildMapForNotification
	 */
	
	public void testBuildMapForNotification() {
		
//		OrganizationAssignedIdentifier mrn = registerMockFor(OrganizationAssignedIdentifier.class);
//		EasyMock.expect(participant.getMRN()).andReturn(mrn).times(2);
//		EasyMock.expect(mrn.getValue()).andReturn("mrnValue").times(2);
//		EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
//		EasyMock.expect(study.getShortTitleText()).andReturn("Short_title_text").times(2);
//		EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
//		EasyMock.expect(study.getId()).andReturn(1).times(2);
//		EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
//		EasyMock.expect(study.getTargetAccrualNumber()).andReturn(5).times(2);
//		EasyMock.expect(studySite.getTargetAccrualNumber()).andReturn(4).times(2);
//		EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
//		EasyMock.expect(study.getCurrentAccrualCount()).andReturn(3).times(2);
//		EasyMock.expect(studySite.getCurrentAccrualCount()).andReturn(2);
//
//		replayMocks();
//		Map<Object, Object> map = studySubject.buildMapForNotification();
//		assertEquals("Wrong number of entries in the notificaiton map",8,map.size());
//
//		assertEquals("Wrong entry in map","mrnValue",map.get(NotificationEmailSubstitutionVariablesEnum.PARTICIPANT_MRN
//		.toString()));
//		assertEquals("Wrong entry in map","Short_title_text",map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SHORT_TITLE
//		.toString()));
//		assertEquals("Wrong entry in map",5,map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_ACCRUAL_THRESHOLD
//		.toString()));
//		assertEquals("Wrong entry in map",4,map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_ACCRUAL_THRESHOLD
//		.toString()));
//		assertEquals("Wrong entry in map",3,map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_CURRENT_ACCRUAL
//		.toString()));
//		assertEquals("Wrong entry in map",2,map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_CURRENT_ACCRUAL
//		.toString()));
//
//		verifyMocks();
		
		// write is later.
	}
	
	/**
	 * test get principal study investigator 
	 */
	public void testGetPricipalStudyInvestigator(){
		StudyOrganization organization = registerMockFor(StudyOrganization.class);
		StudyInvestigator studyInv = registerMockFor(StudyInvestigator.class);
		List<StudyInvestigator> studyInvs = new ArrayList<StudyInvestigator>();
		studyInvs.add(studyInv);
		
		EasyMock.expect(organization.getStudyInvestigators()).andReturn(studyInvs);
		EasyMock.expect(studyInv.getRoleCode()).andReturn("Principal Investigator");
		organization.setStudy(simpleStudy);
		
		replayMocks();
		
		simpleStudy.addStudyOrganization(organization);
		
		assertNotNull("principal study investigator found", simpleStudy.getPrincipalStudyInvestigator());
		verifyMocks();
	}
	
	/**
	 * test get principal study investigator 
	 */
	public void testGetPricipalStudyInvestigator1(){
		StudyOrganization organization = registerMockFor(StudyOrganization.class);
		StudyInvestigator studyInv = registerMockFor(StudyInvestigator.class);
		List<StudyInvestigator> studyInvs = new ArrayList<StudyInvestigator>();
		studyInvs.add(studyInv);
		
		EasyMock.expect(organization.getStudyInvestigators()).andReturn(studyInvs);
		EasyMock.expect(studyInv.getRoleCode()).andReturn("Study Investigator");
		organization.setStudy(simpleStudy);
		
		replayMocks();
		
		simpleStudy.addStudyOrganization(organization);
		
		assertNull("principal study investigator not found", simpleStudy.getPrincipalStudyInvestigator());
		verifyMocks();
	}
	

	/**
	 * test get principal  investigator 
	 */
	public void testGetPricipalInvestigator(){
		StudyOrganization organization = registerMockFor(StudyOrganization.class);
		StudyInvestigator studyInv = registerMockFor(StudyInvestigator.class);	
		HealthcareSiteInvestigator healthcareSiteInv = registerMockFor(HealthcareSiteInvestigator.class);
		List<StudyInvestigator> studyInvs = new ArrayList<StudyInvestigator>();
		studyInvs.add(studyInv);
		
		EasyMock.expect(organization.getStudyInvestigators()).andReturn(studyInvs);
		EasyMock.expect(studyInv.getRoleCode()).andReturn("Principal Investigator");
		EasyMock.expect(studyInv.getHealthcareSiteInvestigator()).andReturn(healthcareSiteInv);
		organization.setStudy(simpleStudy);
		
		replayMocks();
		
		simpleStudy.addStudyOrganization(organization);
		
		assertNotNull("principal investigator  found", simpleStudy.getPrincipalInvestigator());
		verifyMocks();
	}
	
	/**
	 * test get principal  investigator 
	 */
	public void testGetPricipalInvestigator1(){
		StudyOrganization organization = registerMockFor(StudyOrganization.class);
		StudyInvestigator studyInv = registerMockFor(StudyInvestigator.class);	
		List<StudyInvestigator> studyInvs = new ArrayList<StudyInvestigator>();
		studyInvs.add(studyInv);
		
		EasyMock.expect(organization.getStudyInvestigators()).andReturn(studyInvs);
		EasyMock.expect(studyInv.getRoleCode()).andReturn("Study Investigator");
		organization.setStudy(simpleStudy);
		
		replayMocks();
		
		simpleStudy.addStudyOrganization(organization);
		
		assertNull("principal investigator not found", simpleStudy.getPrincipalInvestigator());
		verifyMocks();
	}
	
	/**
	 * test get principal  investigator full name
	 */
	public void testGetPricipalInvestigatorFullName(){
		StudyOrganization organization = registerMockFor(StudyOrganization.class);
		Investigator inv = registerMockFor(Investigator.class);
		HealthcareSiteInvestigator healthcareSiteInv = registerMockFor(HealthcareSiteInvestigator.class);
		StudyInvestigator studyInv = registerMockFor(StudyInvestigator.class);	
		List<StudyInvestigator> studyInvs = new ArrayList<StudyInvestigator>();
		studyInvs.add(studyInv);
		
		EasyMock.expect(organization.getStudyInvestigators()).andReturn(studyInvs);
		EasyMock.expect(studyInv.getRoleCode()).andReturn("Principal Investigator");
		EasyMock.expect(inv.getFullName()).andReturn("Ronald Regan");
		EasyMock.expect(studyInv.getHealthcareSiteInvestigator()).andReturn(healthcareSiteInv);
		EasyMock.expect(healthcareSiteInv.getInvestigator()).andReturn(inv);
		
		organization.setStudy(simpleStudy);
		
		replayMocks();
		
		simpleStudy.addStudyOrganization(organization);
		
		assertEquals("principal investigator full name is Ronald Regan", "Ronald Regan",simpleStudy.getPrincipalInvestigatorFullName());
		verifyMocks();
	}
	
	/**
	 * test get principal  investigator Full Name
	 */
	public void testGetPricipalInvestigatorFullName1(){
		StudyOrganization organization = registerMockFor(StudyOrganization.class);
		StudyInvestigator studyInv = registerMockFor(StudyInvestigator.class);	
		List<StudyInvestigator> studyInvs = new ArrayList<StudyInvestigator>();
		studyInvs.add(studyInv);
		
		EasyMock.expect(organization.getStudyInvestigators()).andReturn(studyInvs);
		EasyMock.expect(studyInv.getRoleCode()).andReturn("Study Investigator");
		organization.setStudy(simpleStudy);
		
		replayMocks();
		
		simpleStudy.addStudyOrganization(organization);
		
		assertNull("principal investigator not found", simpleStudy.getPrincipalInvestigatorFullName());
		verifyMocks();
	}
	
	/**
	 * test get principal investigator's study organization 
	 */	
	public void testGetPricipalInvestigatorStudyOrg(){
		StudyOrganization organization = registerMockFor(StudyOrganization.class);
		StudyInvestigator studyInv = registerMockFor(StudyInvestigator.class);
		List<StudyInvestigator> studyInvs = new ArrayList<StudyInvestigator>();
		studyInvs.add(studyInv);
		
		EasyMock.expect(organization.getStudyInvestigators()).andReturn(studyInvs);
		EasyMock.expect(studyInv.getRoleCode()).andReturn("Principal Investigator");
		EasyMock.expect(studyInv.getStudyOrganization()).andReturn(organization);
		organization.setStudy(simpleStudy);
		
		replayMocks();
		
		simpleStudy.addStudyOrganization(organization);
		
		assertNotNull("principal investigator's study site found", simpleStudy.getPrincipalInvestigatorStudyOrganization());
		verifyMocks();
	}
	
	/**
	 * test get principal investigator's study organization 
	 */
	public void testGetPricipalInvestigatorStudyOrg1(){
		StudyOrganization organization = registerMockFor(StudyOrganization.class);
		StudyInvestigator studyInv = registerMockFor(StudyInvestigator.class);
		List<StudyInvestigator> studyInvs = new ArrayList<StudyInvestigator>();
		studyInvs.add(studyInv);
		
		EasyMock.expect(organization.getStudyInvestigators()).andReturn(studyInvs);
		EasyMock.expect(studyInv.getRoleCode()).andReturn("Study Investigator");
		organization.setStudy(simpleStudy);
		
		replayMocks();
		
		simpleStudy.addStudyOrganization(organization);
		
		assertNull("principal investigator's study site not found", simpleStudy.getPrincipalInvestigatorStudyOrganization());
		verifyMocks();
	}
	
	/**
	 * test set epochs
	 */
	public void testSetEpochs(){
		Epoch epoch = registerMockFor(Epoch.class);
		List<Epoch> epochs = new ArrayList<Epoch>();
		epochs.add(epoch);
		replayMocks();
		simpleStudy.setEpochs(epochs);
		assertEquals("study has 1 epoch",1, simpleStudy.getEpochs().size());
		verifyMocks();
		
	}
	

	/**
	 * test set Amendments
	 */
	public void testSetAmendments(){
		StudyAmendment amendment = registerMockFor(StudyAmendment.class);
		List<StudyAmendment> amendments = new ArrayList<StudyAmendment>();
		amendments.add(amendment);
		replayMocks();
		simpleStudy.setStudyAmendments(amendments);
		assertEquals("study has 1 amenment",1, simpleStudy.getStudyAmendments().size());
		verifyMocks();
		
	}
	
	/**
	 * test get current study amendment
	 */
	public void testGetCurrentStudyAmendment(){
		simpleStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
		assertNull("no current amendment pending for study", simpleStudy.getCurrentStudyAmendment());
	}
	

	/**
	 * test get current study amendment
	 */
	public void testGetCurrentStudyAmendment1(){
		StudyAmendment amendment = registerMockFor(StudyAmendment.class);
		List<StudyAmendment> amendments = new ArrayList<StudyAmendment>();
		amendments.add(amendment);
		replayMocks();
		simpleStudy.setStudyAmendments(amendments);
		
		simpleStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.AMENDMENT_PENDING);
		assertNotNull("current amendment found for study", simpleStudy.getCurrentStudyAmendment());
		
		verifyMocks();
	}
	
	/**
	 * test get previous study amendment
	 */
	public void testGetPreviousStudyAmendment(){
		StudyAmendment amendment = registerMockFor(StudyAmendment.class);
		List<StudyAmendment> amendments = new ArrayList<StudyAmendment>();
		amendments.add(amendment);
		replayMocks();
		simpleStudy.setStudyAmendments(amendments);
		
		simpleStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
		assertEquals("one previous amendment found for study", 1,  simpleStudy.getPreviousStudyAmendments().size());
		
		verifyMocks();
	}
	
	/**
	 * test get previous study amendment
	 */
	public void testGetPreviousStudyAmendment1(){
		StudyAmendment amendment = registerMockFor(StudyAmendment.class);
		List<StudyAmendment> amendments = new ArrayList<StudyAmendment>();
		amendments.add(amendment);
		replayMocks();
		simpleStudy.setStudyAmendments(amendments);
		
		simpleStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.AMENDMENT_PENDING);
		assertNull("no previous amendment found for study", simpleStudy.getPreviousStudyAmendments());
		
		verifyMocks();
	}
	
	/**
	 * test get previous study amendment
	 */
	public void testGetPreviousStudyAmendment2(){
		StudyAmendment amendment = registerMockFor(StudyAmendment.class);
		StudyAmendment amendment1 = registerMockFor(StudyAmendment.class);
		List<StudyAmendment> amendments = new ArrayList<StudyAmendment>();
		amendments.add(amendment);
		amendments.add(amendment1);
		replayMocks();
		simpleStudy.setStudyAmendments(amendments);
		
		simpleStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.AMENDMENT_PENDING);
		assertEquals("one previous amendment found for study", 1 , simpleStudy.getPreviousStudyAmendments().size());
		
		verifyMocks();
	}
	
	/**
	 * test compare to
	 */
	public void testCompareTo(){
		Study study1 = new Study();
		Study study2 = study1;
		assertEquals("condition should come out as false", 0 ,study1.compareTo(study2));
	}
	
	/**
	 * test equals, if objects are of different class
	 */
	public void testEquals(){
		Study study1 = new Study();
		Epoch epoch = new Epoch();
		assertFalse("objects are from differnt class", study1.equals(epoch));
	}
	
	/**
	 * test equals, if one study object has null coordinating center identifier
	 */
	public void testEquals1(){
		basicStudy = studyCreationHelper.addCoordinationCenterIdentifier(basicStudy, "Coordinating Center Identifier", "value");
		Study study = new Study();
		assertFalse("both objects has null coordinating center identifer  hence unequal", basicStudy.equals(study));
	}
	
	/**
	 * test equals, if  coordinating center identifier value is different
	 */
	public void testEquals2(){
		basicStudy = studyCreationHelper.addCoordinationCenterIdentifier(basicStudy, "Coordinating Center Identifier", "value");
		Study study = new Study();
		study = studyCreationHelper.addCoordinationCenterIdentifier(study, "Coordinating Center Identifier", "testValue");
		assertFalse("coordinating center identifer values are different hence unequal", basicStudy.equals(study));
	}
	
	/**
	 * test equals, if  coordinating center identifier value are same
	 */
	public void testEquals3(){
		basicStudy = studyCreationHelper.addCoordinationCenterIdentifier(basicStudy, "Coordinating Center Identifier", "value");
		Study study = new Study();
		study = studyCreationHelper.addCoordinationCenterIdentifier(study, "Coordinating Center Identifier", "value");
		assertTrue("coordinating center identifer values are same hence equal", basicStudy.equals(study));
	}


	/**
	 * Test get trimmed short title text.
	 */
	public void testGetTrimmedShortTitleText(){
		simpleStudy.setShortTitleText("Duke Hematology/Oncology at Raleigh Community Hospital Study");
		assertEquals("Trimmed short title should be Duke Hematology/Oncology at Raleigh Com...","Duke Hematology/Oncology at Raleigh Com...", simpleStudy.getTrimmedShortTitleText());
	}


/**
 * Test get primary indicator
 */
public void testGetPrimaryIndicator(){
	basicStudy = studyCreationHelper.addCoordinationCenterIdentifier(basicStudy, "Coordinating Center Identifier", "TestValue");
	assertEquals("Primary identifier value is Test Value","TestValue", basicStudy.getPrimaryIdentifier());
}

}