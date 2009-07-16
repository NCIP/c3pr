package edu.duke.cabig.c3pr.domain;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.easymock.classextension.EasyMock;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
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

//	/**
//	 * Test data entry status incomplete case1.
//	 * 
//	 * @throws Exception the exception
//	 */
//	public void testDataEntryStatusIncompleteCase1() throws Exception {
//		EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ENROLLING_EPOCH.CODE", null, null)).andReturn("300");
//		EasyMock.expect(c3prExceptionHelper.getRuntimeException(300)).andReturn(new C3PRCodedRuntimeException(300, "exception message"));
//		replayMocks();
//		try {
//			List<Error> errors = new ArrayList<Error>();
//			basicStudy.evaluateDataEntryStatus(errors);
//			//fail("Should have thrown C3PRCodedException");
//		} catch (C3PRCodedRuntimeException e) {
//			assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException); 
//		}
//		verifyMocks();
//	}
//	
//	/**
//	 * Test data entry status incomplete case2.
//	 * 
//	 * @throws Exception the exception
//	 */
//	public void testDataEntryStatusIncompleteCase2() throws Exception {
//		basicStudy.addStudySite(new StudySite());
//		EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ENROLLING_EPOCH.CODE", null, null)).andReturn("300");
//		EasyMock.expect(c3prExceptionHelper.getRuntimeException(300)).andReturn(new C3PRCodedRuntimeException(300, "exception message"));
//		replayMocks();
//		try {
//			List<Error> errors = new ArrayList<Error>();
//			basicStudy.evaluateDataEntryStatus(errors);
////			fail("Should have thrown C3PRCodedException");
//		} catch (C3PRCodedRuntimeException e) {
//			assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException); 
//		}
//		verifyMocks();
//		
//	}
//	
//	/**
//	 * Test data entry status incomplete case3.
//	 * 
//	 * @throws Exception the exception
//	 */
//	public void testDataEntryStatusIncompleteCase3() throws Exception {
//		basicStudy.addStudySite(new StudySite());
//		Epoch nonTreatmentEpoch = new Epoch();
//		basicStudy.addEpoch(nonTreatmentEpoch);
//		EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ENROLLING_EPOCH.CODE", null, null)).andReturn("302");
//		EasyMock.expect(c3prExceptionHelper.getRuntimeException(302)).andReturn(new C3PRCodedRuntimeException(302, "exception message"));
//		replayMocks();
//		try {
//			List<Error> errors = new ArrayList<Error>();
//			basicStudy.evaluateDataEntryStatus(errors);
////			fail("Should have thrown C3PRCodedException");
//		} catch (C3PRCodedRuntimeException e) {
//			assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException); 
//		}
//		verifyMocks();
//	}
//	
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
		OrganizationAssignedIdentifier identifier  = registerMockFor(OrganizationAssignedIdentifier.class);
		simpleStudy.getIdentifiers().add(identifier);
		EasyMock.expect(identifier.getTypeInternal()).andReturn(OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER.getName());
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
		OrganizationAssignedIdentifier identifier  = registerMockFor(OrganizationAssignedIdentifier.class);
		simpleStudy.getIdentifiers().add(identifier);
		EasyMock.expect(identifier.getTypeInternal()).andReturn(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER.getName()).times(2);
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
		OrganizationAssignedIdentifier identifier  = registerMockFor(OrganizationAssignedIdentifier.class);
		OrganizationAssignedIdentifier identifier1 = registerMockFor(OrganizationAssignedIdentifier.class);
		OrganizationAssignedIdentifier identifier2  = registerMockFor(OrganizationAssignedIdentifier.class);
		simpleStudy.getIdentifiers().add(identifier);
		simpleStudy.getIdentifiers().add(identifier1);
		simpleStudy.getIdentifiers().add(identifier2);
		
		EasyMock.expect(identifier.getTypeInternal()).andReturn(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER.getName()).times(2);
		EasyMock.expect(identifier1.getTypeInternal()).andReturn(OrganizationIdentifierTypeEnum.C3D_IDENTIFIER.getName()).times(2) ;
		EasyMock.expect(identifier2.getTypeInternal()).andReturn(OrganizationIdentifierTypeEnum.C3PR.getName()).times(2) ;
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
		epoch.setStudyVersion(simpleStudy.getStudyVersion());

		Epoch epoch1  = registerMockFor(Epoch.class);
		epoch1.setStudyVersion(simpleStudy.getStudyVersion());

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
		disease.setStudyVersion(simpleStudy.getStudyVersion());
		StudyDisease disease2  = registerMockFor(StudyDisease.class);
		disease2.setStudyVersion(simpleStudy.getStudyVersion());
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
		disease.setStudyVersion(simpleStudy.getStudyVersion());
		
		StudyDisease disease2  = registerMockFor(StudyDisease.class);
		disease2.setStudyVersion(simpleStudy.getStudyVersion());
		
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
		association.setParentStudyVersion(simpleStudy.getStudyVersion());
		
		List<StudySite> listStudySite = new ArrayList<StudySite>();
		StudySite studySite = registerMockFor(StudySite.class);
		HealthcareSite healthcareSite = registerMockFor(HealthcareSite.class);
		listStudySite.add(studySite);
		
		EasyMock.expect(association.getStudySites()).andReturn(listStudySite);
		EasyMock.expect(studySite.getHealthcareSite()).andReturn(healthcareSite);
		EasyMock.expect(healthcareSite.getPrimaryIdentifier()).andReturn("NC010");
		
		replayMocks();

		simpleStudy.addCompanionStudyAssociation(association);
		
		assertNotNull("study site found with nc010", simpleStudy.getCompanionStudySite("NC010"));
		verifyMocks();
	}

	/**
	 * Test get companion study site by nci identifier
	 * 
	 */
	public void testGetCompanionStudySite2(){
		simpleStudy.setCompanionIndicator(false);
		CompanionStudyAssociation association = registerMockFor(CompanionStudyAssociation.class);
		association.setParentStudyVersion(simpleStudy.getStudyVersion());
		
		List<StudySite> listStudySite = new ArrayList<StudySite>();
		StudySite studySite = registerMockFor(StudySite.class);
		HealthcareSite healthcareSite = registerMockFor(HealthcareSite.class);
		listStudySite.add(studySite);
		
		EasyMock.expect(association.getStudySites()).andReturn(listStudySite);
		EasyMock.expect(studySite.getHealthcareSite()).andReturn(healthcareSite);
		EasyMock.expect(healthcareSite.getPrimaryIdentifier()).andReturn("NC011");
		
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
		EasyMock.expect(healthcareSite.getPrimaryIdentifier()).andReturn("NC010");
		
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
		
		EasyMock.expect(healthcareSite.getPrimaryIdentifier()).andReturn("NC010");
		EasyMock.expect(healthcareSite1.getPrimaryIdentifier()).andReturn("NC011");
		
		replayMocks();
		
		simpleStudy.addStudySite(studySite);
		simpleStudy.addStudyOrganization(coordinatingCenter);

		assertEquals("one affiliate study site present", 1 ,simpleStudy.getAffiliateStudySites().size());
		
		verifyMocks();
	}
	

	/**
	 * Test get affiliate study sites 
	 * 
	 */
	public void testGetAffiliateStudySites1(){
		HealthcareSite healthcareSite = registerMockFor(HealthcareSite.class);
		HealthcareSite healthcareSite1 = registerMockFor(HealthcareSite.class);
		
		StudySite studySite = new StudySite();
		studySite.setStudy(simpleStudy);
		studySite.setHealthcareSite(healthcareSite);
		
		StudyCoordinatingCenter coordinatingCenter = new StudyCoordinatingCenter();
		coordinatingCenter.setStudy(simpleStudy);
		coordinatingCenter.setHealthcareSite(healthcareSite1);
		
		EasyMock.expect(healthcareSite.getPrimaryIdentifier()).andReturn("NC011");
		EasyMock.expect(healthcareSite1.getPrimaryIdentifier()).andReturn("NC011");
		
		replayMocks();
		
		simpleStudy.addStudySite(studySite);
		simpleStudy.addStudyOrganization(coordinatingCenter);

		assertEquals("no affiliate study site present", 0 ,simpleStudy.getAffiliateStudySites().size());
		
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
	 * Test isMultisite
	 * 
	 */
	public void testIsMultisite12(){
		simpleStudy.setMultiInstitutionIndicator(false);
		simpleStudy.setCompanionIndicator(true);
		assertFalse("This is not a multisite study", simpleStudy.isMultisite());
	}
	

	/**
	 * Test isMultisite
	 * 
	 */
	public void testIsMultisite2(){
		simpleStudy.setMultiInstitutionIndicator(false);
		simpleStudy.setCompanionIndicator(false);
		assertFalse("This is not a multisite study", simpleStudy.isMultisite());
	}
	
	
	/**
	 * test get accrual count
	 */
	public void testGetAccrualCount(){
		
		// will do it later
		// might need method level mocking for classes
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
		basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier
							(basicStudy, OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER, "value");
		Study study = new Study();
		assertFalse("both objects has null coordinating center identifer  hence unequal", basicStudy.equals(study));
	}
	
	/**
	 * test equals, if  coordinating center identifier value is different
	 */
	public void testEquals2(){
		basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier
							(basicStudy, OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER, "value");
		Study study = new Study();
		study = studyCreationHelper.addOrganizationAssignedIdentifier
							(study, OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER, "testValue");
		assertFalse("coordinating center identifer values are different hence unequal", basicStudy.equals(study));
	}
	
	/**
	 * test equals, if  coordinating center identifier value are same
	 */
	public void testEquals3(){
		basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER, "value");
		Study study = new Study();
		study = studyCreationHelper.addOrganizationAssignedIdentifier(study, OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER, "value");
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
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER, "TestValue");
	basicStudy.getOrganizationAssignedIdentifiers().get(0).setPrimaryIndicator(true);
	assertEquals("Primary identifier value is Test Value","TestValue", basicStudy.getPrimaryIdentifier());
}


/**
 * Test get primary indicator
 */
public void testGetPrimaryIndicator1(){
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifierNonPrimary(basicStudy, OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER, "TestValue");
	assertNull("Primary identifier not found", basicStudy.getPrimaryIdentifier());
}


/**
 * Test get primary indicator
 */
public void testGetPrimaryIndicator2(){
	assertNull("Primary identifier not found", basicStudy.getPrimaryIdentifier());
}

/**
 * Test get funding sponsor identifier index 
 */
public void testGetFundingSponsorIdentifierIndex(){
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER, "TestValue");
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.C3D_IDENTIFIER, "C3D");
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER, "identifier");
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.C3PR, "C3PR");
	
	assertEquals("Index of funding sponsorer should be 2",2, basicStudy.getFundingSponsorIdentifierIndex());
}


/**
 * Test get funding sponsor identifier index 
 */
public void testGetFundingSponsorIdentifierIndex1(){
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER, "TestValue");
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.C3D_IDENTIFIER, "C3D");
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.C3PR, "C3PR");
	
	assertEquals("Index of funding sponsorer should be -1",-1, basicStudy.getFundingSponsorIdentifierIndex());
}


/**
 * Test get funding sponsor identifier index 
 */
public void testGetFundingSponsorIdentifierIndex2(){
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER, "TestValue");
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.C3D_IDENTIFIER, "C3D");
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER, "identifier");
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.C3PR, "C3PR");
	
	assertEquals("Index of funding sponsorer should be 2",2, basicStudy.getFundingSponsorIdentifierIndex());
}

/**
 * test has eligibility
 */
public void testHasEligibility(){
	basicStudy = studyCreationHelper.addNonEnrollingEpochToBasicStudy(basicStudy, "name1");
	basicStudy = studyCreationHelper.addNonEnrollingEpochToBasicStudy(basicStudy, "name2");
	
	assertFalse("no eligibility for this study",basicStudy.hasElligibility());
	
}


/**
 * test has eligibility
 */
public void testHasEligibility1(){
	basicStudy = studyCreationHelper.addNonEnrollingEpochToBasicStudy(basicStudy, "epoch1");
	basicStudy = studyCreationHelper.addStudySiteAndEnrollingEpochToBasicStudy(basicStudy, "epoch 2");
	
	assertTrue("eligibility present for this study",basicStudy.hasElligibility());
	
}

/**
 * test has randomized epoch
 */
public void testHasRandomizedEpoch(){
	basicStudy = studyCreationHelper.addStudySiteAndRandomizedTreatmentEpochToBasicStudy(basicStudy);
	assertTrue("randomized epoch present for this study",basicStudy.hasRandomizedEpoch());
	
}


/**
 * test has randomized epoch
 */
public void testHasRandomizedEpoch1(){
	assertFalse("randomized epoch present for this study",basicStudy.hasRandomizedEpoch());
}

/**
 * test has randomized epoch
 */
public void testHasRandomizedEpoch2(){
	basicStudy = studyCreationHelper.addNonEnrollingEpochToBasicStudy(basicStudy);
	assertFalse("randomized epoch present for this study",basicStudy.hasRandomizedEpoch());
}

/**
 * test has stratified epoch
 */
public void testHasStratifiedEpoch(){
	basicStudy = studyCreationHelper.addStratifiedEpochToBasicStudy(basicStudy, "epoch 1");
	assertTrue("stratified epoch present for this study",basicStudy.hasStratifiedEpoch());
}

/**
 * test has stratified epoch
 */
public void testHasStratifiedEpoch2(){
	basicStudy = studyCreationHelper.addNonEnrollingEpochToBasicStudy(basicStudy);
	assertFalse("stratified epoch not present for this study",basicStudy.hasStratifiedEpoch());
}


/**
 * test has stratified epoch
 */
public void testHasStratifiedEpoch1(){
	assertFalse("stratified epoch not present for this study",basicStudy.hasStratifiedEpoch());
}


/**
 * test get epoch by name
 */
public void testGetEpochByName(){
	basicStudy = studyCreationHelper.addNonEnrollingEpochToBasicStudy(basicStudy,"epoch 1");
	assertNotNull("epoch present with name 'epoch 1'",basicStudy.getEpochByName("epoch 1"));
}


/**
 * test get epoch by name
 */
public void testGetEpochByName1(){
	basicStudy = studyCreationHelper.addNonEnrollingEpochToBasicStudy(basicStudy,"epoch 2");
	assertNull("no epoch present with name 'epoch 1'",basicStudy.getEpochByName("epoch 1"));
}

/**
 * test get epoch by name
 */
public void testGetEpochByName2(){
	assertNull("no epoch present with name 'epoch 1'",basicStudy.getEpochByName("epoch 1"));
}


///**
// * test update data entry status
// */
//public void testUpdateDataEntryStatus(){
//	basicStudy.setStratificationIndicator(true);
//	basicStudy.setRandomizedIndicator(true);
//	
//	EasyMock.expect(c3prExceptionHelper.getRuntimeException(300)).andReturn(new C3PRCodedRuntimeException(300, "exception message"));
//	EasyMock.expect(c3prExceptionHelper.getRuntimeException(303)).andReturn(new C3PRCodedRuntimeException(303, "exception message"));
//	EasyMock.expect(c3prExceptionHelper.getRuntimeException(328)).andReturn(new C3PRCodedRuntimeException(328, "exception message"));
//	
//	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ENROLLING_EPOCH.CODE", null, null)).andReturn("300");
//	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.RANDOMIZED_EPOCH_FOR_RANDOMIZED_STUDY.CODE", null, null)).andReturn("303");
//	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.STRATIFIED_EPOCH_FOR_STRATIFIED_STUDY.CODE", null, null)).andReturn("328");
//	replayMocks();
//	try{
//		basicStudy.updateDataEntryStatus();
//	}catch(Exception e){
//		assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException); 
//	}
//	verifyMocks();
//}


///**
// * test evaluate coordinating center study status
// */
//public void testEvaluateCoordinatingCenterStudyStatus(){
//	basicStudy.setStratificationIndicator(true);
//	basicStudy.setRandomizedIndicator(true);
//	
//	EasyMock.expect(c3prExceptionHelper.getRuntimeException(300)).andReturn(new C3PRCodedRuntimeException(300, "exception message"));
//	EasyMock.expect(c3prExceptionHelper.getRuntimeException(303)).andReturn(new C3PRCodedRuntimeException(303, "exception message"));
//	EasyMock.expect(c3prExceptionHelper.getRuntimeException(328)).andReturn(new C3PRCodedRuntimeException(328, "exception message"));
//	
//	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ENROLLING_EPOCH.CODE", null, null)).andReturn("300");
//	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.RANDOMIZED_EPOCH_FOR_RANDOMIZED_STUDY.CODE", null, null)).andReturn("303");
//	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.STRATIFIED_EPOCH_FOR_STRATIFIED_STUDY.CODE", null, null)).andReturn("328");
//	replayMocks();
//	CoordinatingCenterStudyStatus status = null ;
//	try{
//		 status = basicStudy.evaluateCoordinatingCenterStudyStatus();
//	}catch(Exception e){
//		assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException);
//	}
//	assertEquals("coordinating center status should be pending",CoordinatingCenterStudyStatus.PENDING, status);
//	verifyMocks();
//}
//

/**
 * test evaluate coordinating center study status
 */
public void testEvaluateCoordinatingCenterStudyStatus1(){
	basicStudy.setStratificationIndicator(false);
	basicStudy.setRandomizedIndicator(false);
	studyCreationHelper.addStudySiteAndEnrollingEpochToBasicStudy(basicStudy);
	CoordinatingCenterStudyStatus status = null;
	try{
		 status = basicStudy.evaluateCoordinatingCenterStudyStatus();
	}catch(Exception e){
		assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException);
	}
	assertEquals("coordinating center status should be pending",CoordinatingCenterStudyStatus.OPEN, status);
}

/**
 * test evaluate coordinating center study status
 */
public void testEvaluateCoordinatingCenterStudyStatus2(){
	basicStudy.setStratificationIndicator(false);
	basicStudy.setCompanionIndicator(true);
	basicStudy.setStandaloneIndicator(false);
	basicStudy.setRandomizedIndicator(false);
	studyCreationHelper.addStudySiteAndEnrollingEpochToBasicStudy(basicStudy);
	CoordinatingCenterStudyStatus status = null;
	try{
		 status = basicStudy.evaluateCoordinatingCenterStudyStatus();
	}catch(Exception e){
		assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException);
	}
	assertEquals("coordinating center status should be pending",CoordinatingCenterStudyStatus.READY_TO_OPEN, status);
}


/**
 * test evaluate coordinating center study status
 */
public void testEvaluateCoordinatingCenterStudyStatus3(){
	//TODO write test case for amendment pending status- some issue in logic
}


/**
 * test evaluate coordinating center study status
 */
public void testEvaluateCoordinatingCenterStudyStatus4(){
	basicStudy.setStratificationIndicator(false);
	basicStudy.setCompanionIndicator(false);
	basicStudy.setStandaloneIndicator(false);
	basicStudy.setRandomizedIndicator(false);
	studyCreationHelper.addStudySiteAndEnrollingEpochToBasicStudy(basicStudy);
	CoordinatingCenterStudyStatus status = null;
	try{
		 status = basicStudy.evaluateCoordinatingCenterStudyStatus();
	}catch(Exception e){
		assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException);
	}
	assertEquals("coordinating center status should be pending",CoordinatingCenterStudyStatus.OPEN, status);
}

/**
 * test evaluate coordinating center study status
 */
public void testEvaluateCoordinatingCenterStudyStatus5(){
	basicStudy.setStratificationIndicator(false);
	basicStudy.setCompanionIndicator(true);
	basicStudy.setStandaloneIndicator(true);
	basicStudy.setRandomizedIndicator(false);
	studyCreationHelper.addStudySiteAndEnrollingEpochToBasicStudy(basicStudy);
	CoordinatingCenterStudyStatus status = null;
	try{
		 status = basicStudy.evaluateCoordinatingCenterStudyStatus();
	}catch(Exception e){
		assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException);
	}
	assertEquals("coordinating center status should be pending",CoordinatingCenterStudyStatus.OPEN, status);
}

/**
 * test evaluate coordinating center study status
 */
public void testEvaluateCoordinatingCenterStudyStatus6(){
	basicStudy.setStratificationIndicator(false);
	basicStudy.setCompanionIndicator(false);
	basicStudy.setStandaloneIndicator(true);
	basicStudy.setRandomizedIndicator(false);
	studyCreationHelper.addStudySiteAndEnrollingEpochToBasicStudy(basicStudy);
	CoordinatingCenterStudyStatus status = null;
	try{
		 status = basicStudy.evaluateCoordinatingCenterStudyStatus();
	}catch(Exception e){
		assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException);
	}
	assertEquals("coordinating center status should be pending",CoordinatingCenterStudyStatus.OPEN, status);
}

/**
 * test get criteria file
 */
public void testGetCriteriaFile(){
	String st = "test string";
	byte[] criteriaFile = st.getBytes();
	simpleStudy.setCriteriaFile(criteriaFile) ;
	assertNotNull("criteriaFile found", simpleStudy.getCriteriaFile());
	assertEquals("criteriaFile found", "test string", new String(simpleStudy.getCriteriaFile()));
}

/**
 * test get criteria reader
 */
public void testGetCriteriaReader(){
	String st = "test string";
	byte[] criteriaFile = st.getBytes();
	simpleStudy.setCriteriaFile(criteriaFile) ;
	assertNotNull("criteriaReader found", simpleStudy.getCriteriaReader());
	assertEquals("criteriaReader instance of Reader", true,  simpleStudy.getCriteriaReader() instanceof Reader) ;
}


/**
 * test get criteria reader
 */
public void testGetCriteriaReader1(){
	assertNull("criteriaReader not found", simpleStudy.getCriteriaReader());
}


/**
 * test get criteria input stream
 */
public void testGetCriteriaInputStream(){
	String st = "test string";
	byte[] criteriaFile = st.getBytes();
	simpleStudy.setCriteriaFile(criteriaFile) ;
	assertNotNull("criteriaInputStream found", simpleStudy.getCriteriaInputStream());
	assertEquals("criteriaInputStream instance of InputStream", true,  simpleStudy.getCriteriaInputStream() instanceof InputStream) ;
}


/**
 * test get criteria input stream
 */
public void testGetCriteriaInputStream1(){
	assertNull("criteriaInputStream not found", simpleStudy.getCriteriaInputStream());
}


/**
 * test set accrual within last week
 */
 public void testGetAcrrualWithinLastWeek(){
	 simpleStudy.setAcrrualsWithinLastWeek(10);
	 assertEquals("accrual in last week is 10", 10 , simpleStudy.getAcrrualsWithinLastWeek());
 }

 /**
  * test get c3pr error messages
  */
public void testGetC3PRErrorMessages(){
	assertEquals("c3pr error message is instance of ResourceBundleMessageSource", true,  simpleStudy.getC3prErrorMessages() instanceof ResourceBundleMessageSource) ;
}

/**
 * test evaluateEpochsDataEntryStatus
 */
public void testEvaluateEpochsDataEntryStatus(){
	basicStudy = studyCreationHelper.addStratifiedEpochToBasicStudy(basicStudy, "Name");
	ArrayList<Error> errors = new ArrayList<Error>();
	basicStudy.evaluateEpochsDataEntryStatus(errors);
	assertEquals("size of errors is 1", 1 , errors.size());
}


/**
 * test evaluateEpochsDataEntryStatus
 */
public void testEvaluateEpochsDataEntryStatus1(){
	basicStudy = studyCreationHelper.addNonEnrollingEpochToBasicStudy(basicStudy);
	ArrayList<Error> errors = new ArrayList<Error>();
		basicStudy.evaluateEpochsDataEntryStatus(errors);
		assertEquals("no error found", 0 , errors.size());
}


/**
 * test evaluateEpochsDataEntryStatus
 */
public void testEvaluateEpochsDataEntryStatus2(){
	ArrayList<Error> errors = new ArrayList<Error>();
	basicStudy.evaluateEpochsDataEntryStatus(errors);
	assertEquals("no error found", 0 , errors.size());
}


/**
 * test pending amendment
 */
public void testPendingAmendment(){
	basicStudy.pendingAmendment();
	assertEquals("study coordinating center status should be amendment pending", CoordinatingCenterStudyStatus.AMENDMENT_PENDING,  basicStudy.getCoordinatingCenterStudyStatus());
}

/**
 * test pending
 */
public void testPending(){
	basicStudy.pending();
	assertEquals("study coordinating center status should be  pending", CoordinatingCenterStudyStatus.PENDING,  basicStudy.getCoordinatingCenterStudyStatus());
}

/**
 * test temporarilyCloseToAccrual
 */
public void testTemporarilyCloseToAccrual(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
	try{
		basicStudy.temporarilyCloseToAccrual();
	}catch (Exception e) {
		assertNull("Error not theorwn", e);
	}
	assertEquals("study coordinating center status should be  pending", CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL,  basicStudy.getCoordinatingCenterStudyStatus());
}


/**
 * test temporarilyCloseToAccrual
 */
public void testTemporarilyCloseToAccrual1(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(320),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(320, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("320");
	
	
	replayMocks();
	try{
		basicStudy.temporarilyCloseToAccrual();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}



/**
 * test temporarilyCloseToAccrual
 */
public void testTemporarilyCloseToAccrual2(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.AMENDMENT_PENDING);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(320),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(320, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("320");
	
	
	replayMocks();
	try{
		basicStudy.temporarilyCloseToAccrual();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}



/**
 * test temporarilyCloseToAccrual
 */
public void testTemporarilyCloseToAccrual3(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(320),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(320, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("320");
	
	
	replayMocks();
	try{
		basicStudy.temporarilyCloseToAccrual();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}



/**
 * test temporarilyCloseToAccrual
 */
public void testTemporarilyCloseToAccrual4(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(320),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(320, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("320");
	
	
	replayMocks();
	try{
		basicStudy.temporarilyCloseToAccrual();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}


/**
 * test temporarilyCloseToAccrualAndTreatment
 */
public void testTemporarilyCloseToAccrualAndTreatment(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
	try{
		basicStudy.temporarilyCloseToAccrualAndTreatment();
	}catch (Exception e) {
		assertNull("Error not thrown", e);
	}
	assertEquals("study coordinating center status should be  temporarily close ato accrual and treatment", CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT,  basicStudy.getCoordinatingCenterStudyStatus());
}


/**
 * test temporarilyCloseToAccrualAndTreatment
 */
public void testTemporarilyCloseToAccrualAndTreatment1(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(320),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(320, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("320");
	
	
	replayMocks();
	try{
		basicStudy.temporarilyCloseToAccrualAndTreatment();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}



/**
 * test temporarilyCloseToAccrualAndTreatment
 */
public void testTemporarilyCloseToAccrualAndTreatment2(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.AMENDMENT_PENDING);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(320),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(320, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("320");
	
	
	replayMocks();
	try{
		basicStudy.temporarilyCloseToAccrualAndTreatment();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}



/**
 * test temporarilyCloseToAccrualAndTreatment
 */
public void testTemporarilyCloseToAccrualAndTreatment3(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(320),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(320, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("320");
	
	replayMocks();
	try{
		basicStudy.temporarilyCloseToAccrualAndTreatment();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}



/**
 * test temporarilyCloseToAccrualAndTreatment
 */
public void testTemporarilyCloseToAccrualAndTreatment4(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);

	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(320),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(320, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("320");
	
	replayMocks();
	try{
		basicStudy.temporarilyCloseToAccrualAndTreatment();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}



/**
 * test closeToAccrual
 */
public void testCloseToAccrual(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
	try{
		basicStudy.closeToAccrual();
	}catch (Exception e) {
		assertNull("Error not theorwn", e);
	}
	assertEquals("study coordinating center status should be close to accrual", CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL,  basicStudy.getCoordinatingCenterStudyStatus());
}


/**
 * test closeToAccrual
 */
public void testCloseToAccrual1(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(320),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(320, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("320");
	
	
	replayMocks();
	try{
		basicStudy.closeToAccrual();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}



/**
 * test closeToAccrual
 */
public void testCloseToAccrual2(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.AMENDMENT_PENDING);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(320),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(320, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("320");
	
	
	replayMocks();
	try{
		basicStudy.closeToAccrual();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}



/**
 * test closeToAccrual
 */
public void testCloseToAccrual3(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
	
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_ALREADY_CLOSED.CODE", null, null)).andReturn("344");
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(344),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(344, "exception message"));
	
	
	replayMocks();
	try{
		basicStudy.closeToAccrual();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}



/**
 * test closeToAccrual
 */
public void testCloseToAccrual4(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(344),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(344, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_ALREADY_CLOSED.CODE", null, null)).andReturn("344");
	
	replayMocks();
	try{
		basicStudy.closeToAccrual();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}


/**
 * test closeToAccrualAndTreatment
 */
public void testCloseToAccrualAndTreatment(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
	try{
		basicStudy.closeToAccrualAndTreatment();
	}catch (Exception e) {
		assertNull("Error not theorwn", e);
	}
	assertEquals("study coordinating center status should be close to accrual and treatment", CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT,  basicStudy.getCoordinatingCenterStudyStatus());
}


/**
 * test closeToAccrualAndTreatment
 */
public void testCloseToAccrualAndTreatment1(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(320),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(320, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("320");
	
	
	replayMocks();
	try{
		basicStudy.closeToAccrualAndTreatment();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}



/**
 * test closeToAccrualAndTreatment
 */
public void testCloseToAccrualAndTreatment2(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.AMENDMENT_PENDING);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(320),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(320, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("320");
	
	
	replayMocks();
	try{
		basicStudy.closeToAccrualAndTreatment();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}

/**
 * test closeToAccrualAndTreatment
 */
public void testCloseToAccrualAndTreatment3(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(344),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(344, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_ALREADY_CLOSED.CODE", null, null)).andReturn("344");
	
	replayMocks();
	try{
		basicStudy.closeToAccrualAndTreatment();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}


/**
 * test closeToAccrualAndTreatment
 */
public void testCloseToAccrualAndTreatment4(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
	
	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(344),EasyMock.aryEq(new String[]{CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT.getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(344, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_ALREADY_CLOSED.CODE", null, null)).andReturn("344");
	
	replayMocks();
	try{
		basicStudy.closeToAccrualAndTreatment();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
}

/**
 * test getFundingSponsorAssignedIdentifier
 */
public void testGetFundingSponsorAssignedIdentifier(){
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER, "Value");
	assertNotNull("Funding Sponsorer Identifier Found", basicStudy.getFundingSponsorAssignedIdentifier());
	
}


/**
 * test getFundingSponsorAssignedIdentifier
 */
public void testGetFundingSponsorAssignedIdentifier1(){
	assertNull("Funding Sponsorer Identifier not Found", basicStudy.getFundingSponsorAssignedIdentifier());
}

/**
 * test getFundingSponsorAssignedIdentifier
 */
public void testGetFundingSponsorAssignedIdentifier2(){
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.C3D_IDENTIFIER , "Value");
	assertNull("Funding Sponsorer Identifier not Found", basicStudy.getFundingSponsorAssignedIdentifier());
}


/**
 * test getFundingSponsorAssignedIdentifier
 */
public void testGetFundingSponsorAssignedIdentifier3(){
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.C3D_IDENTIFIER , "Value");
	assertNull("Funding Sponsorer Identifier not Found", basicStudy.getFundingSponsorAssignedIdentifier());
}



/**
 * test getCoordinatingCenterAssignedIdentifier
 */
public void testGetCoordinatingCenterAssignedIdentifier(){
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER, "Value");
	assertNotNull("Coordinating Center Assigned Identifier Found", basicStudy.getCoordinatingCenterAssignedIdentifier());
	
}


/**
 * test getCoordinatingCenterAssignedIdentifier
 */
public void testGetCoordinatingCenterAssignedIdentifier1(){
	assertNull("Coordinating Center Assigned Identifier not Found", basicStudy.getCoordinatingCenterAssignedIdentifier());
}

/**
 * test getCoordinatingCenterAssignedIdentifier
 */
public void testGetCoordinatingCenterAssignedIdentifier2(){
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.C3D_IDENTIFIER , "Value");
	assertNull("Coordinating Center Assigned Identifier not Found", basicStudy.getCoordinatingCenterAssignedIdentifier());
}


/**
 * test getCoordinatingCenterAssignedIdentifier
 */
public void testGetCoordinatingCenterAssignedIdentifier3(){
	basicStudy = studyCreationHelper.addOrganizationAssignedIdentifier(basicStudy, OrganizationIdentifierTypeEnum.C3D_IDENTIFIER, "Value");
	assertNull("Coordinating Center Assigned Identifier not Found", basicStudy.getCoordinatingCenterAssignedIdentifier());
}

/**
 * Test get study organization by nci identifier
 * 
 */
public void testGetStudyOrganization2(){
	StudyOrganization organization = registerMockFor(StudyOrganization.class);
	organization.setStudy(simpleStudy);
	HealthcareSite healthcareSite = registerMockFor(HealthcareSite.class);
	EasyMock.expect(organization.getHealthcareSite()).andReturn(healthcareSite);
	EasyMock.expect(healthcareSite.getPrimaryIdentifier()).andReturn("NC010");
	
	replayMocks();
	
	simpleStudy.addStudyOrganization(organization);

	try {
		simpleStudy.getStudyOrganization("NC011");
	} catch (C3PRCodedRuntimeException e) {
		assertEquals("Exception should have been of type C3PRCodedRuntimeException",true, e instanceof C3PRCodedRuntimeException); 
	}
	
	verifyMocks();
}

/**
 * test get parent association
 */
public void testGetParentStudyAssociation(){
	basicStudy.setId(1);
	basicStudy.getStudyVersion().setId(1);
	simpleStudy.setId(2);
	simpleStudy.getStudyVersion().setId(2);
	simpleStudy = studyCreationHelper.addParentStudyAssociation(basicStudy, simpleStudy);
	assertNotNull("parent association found for this study", simpleStudy.getParentStudyAssociation(1));
}

/**
 * test get parent association
 */
public void testGetParentStudyAssociation1(){
	basicStudy.setId(1);
	basicStudy.getStudyVersion().setId(1);
	simpleStudy.setId(2);
	simpleStudy.getStudyVersion().setId(2);
	simpleStudy = studyCreationHelper.addParentStudyAssociation(basicStudy, simpleStudy);
	assertNull("parent association found for this study", simpleStudy.getParentStudyAssociation(2));
}

/**
 * test get parent association
 */
public void testGetParentStudyAssociation2(){
	assertNull("parent association found for this study", simpleStudy.getParentStudyAssociation(1));
	
}

/**
 * test get companion study site
 */
public void testGetCompanionStudySite3(){
	basicStudy.setCompanionIndicator(true);
	simpleStudy.setCompanionIndicator(false);
	basicStudy = studyCreationHelper.addParentStudyAssociationWithSite(simpleStudy, basicStudy);
	
	assertNotNull("study site found with NCI_CODE nci identifier", basicStudy.getCompanionStudySite("NCI_CODE"));
	
}


/**
 * test get companion study site
 */
public void testGetCompanionStudySite4(){
	basicStudy.setCompanionIndicator(true);
	simpleStudy.setCompanionIndicator(false);
	basicStudy = studyCreationHelper.addParentStudyAssociationWithSite(simpleStudy, basicStudy);
	assertNull("no study site found with NCI_CODE_1 nci identifier", basicStudy.getCompanionStudySite("NCI_CODE_1"));
}


/**
 * test get companion study site
 */
public void testGetCompanionStudySite5(){
	basicStudy.setCompanionIndicator(true);
	simpleStudy.setCompanionIndicator(false);
	basicStudy = studyCreationHelper.addParentStudyAssociation(simpleStudy, basicStudy);
	assertNull("no study site found with NCI_CODE_1 nci identifier because no study site in association", basicStudy.getCompanionStudySite("NCI_CODE_1"));
}

/**
 * test get companion study site
 */
public void testGetCompanionStudySite6(){
	basicStudy.setCompanionIndicator(true);
	assertNull("no study site found with NCI_CODE_1 nci identifier because no parent association", basicStudy.getCompanionStudySite("NCI_CODE_1"));
}

/**
 * test get possible status transition
 */
public void testGetPossibleStatusTransition(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
	basicStudy.setCompanionIndicator(false);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("1 possible status found", 1 , list.size());
	assertContains(list, CoordinatingCenterStudyStatus.OPEN);
}

/**
 * test get possible status transition
 */
public void testGetPossibleStatusTransition1(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
	basicStudy.setCompanionIndicator(true);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("2 possible status found", 2 , list.size());
	assertContains(list, CoordinatingCenterStudyStatus.OPEN);
	assertContains(list, CoordinatingCenterStudyStatus.READY_TO_OPEN);
}

/**
 * test get possible status transition
 */
public void testGetPossibleStatusTransition2(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
	basicStudy.setCompanionIndicator(true);
	simpleStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
	basicStudy = studyCreationHelper.addParentStudyAssociationWithSite(simpleStudy, basicStudy);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("2 possible status found", 2 , list.size());
	assertContains(list, CoordinatingCenterStudyStatus.OPEN);
	assertContains(list, CoordinatingCenterStudyStatus.READY_TO_OPEN);
}


/**
 * test get possible status transition
 */
public void testGetPossibleStatusTransition3(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
	basicStudy.setCompanionIndicator(true);
	simpleStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
	basicStudy = studyCreationHelper.addParentStudyAssociationWithSite(simpleStudy, basicStudy);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("1 possible status found", 1 , list.size());
	assertContains(list, CoordinatingCenterStudyStatus.READY_TO_OPEN);
}

/**
 * test get possible status transition
 */
public void testGetPossibleStatusTransition4(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.READY_TO_OPEN);
	basicStudy.setCompanionIndicator(false);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("1 possible status found", 1 , list.size());
	assertContains(list, CoordinatingCenterStudyStatus.OPEN);
}


/**
 * test get possible status transition
 */
public void testGetPossibleStatusTransition5(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.READY_TO_OPEN);
	basicStudy.setCompanionIndicator(true);
	basicStudy.setStandaloneIndicator(false);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("1 possible status found", 1 , list.size());
	assertContains(list, CoordinatingCenterStudyStatus.OPEN);
}

/**
 * test get possible status transition
 */
public void testGetPossibleStatusTransition6(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.READY_TO_OPEN);
	basicStudy.setCompanionIndicator(true);
	basicStudy.setCompanionIndicator(true);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("1 possible status found", 1 , list.size());
	assertContains(list, CoordinatingCenterStudyStatus.OPEN);
}

/**
 * test get possible status transition
 */
public void testGetPossibleStatusTransition7(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.READY_TO_OPEN);
	basicStudy.setCompanionIndicator(true);
	basicStudy.setStandaloneIndicator(false);
	simpleStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
	basicStudy = studyCreationHelper.addParentStudyAssociationWithSite(simpleStudy, basicStudy);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("no possible status found", 0 , list.size());
}

/**
 * test get possible status transition
 */
public void testGetPossibleStatusTransition8(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.READY_TO_OPEN);
	basicStudy.setCompanionIndicator(true);
	basicStudy.setStandaloneIndicator(false);
	simpleStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
	basicStudy = studyCreationHelper.addParentStudyAssociationWithSite(simpleStudy, basicStudy);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("1 possible status found", 1 , list.size());
	assertContains(list, CoordinatingCenterStudyStatus.OPEN);
}

public void testGetPossibleStatusTransition9(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("4 possible status found", 4 , list.size());
	assertContains(list, CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
	assertContains(list, CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
	assertContains(list, CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL);
	assertContains(list, CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);
}

/**
 * test get possible status transition
 */
public void testGetPossibleStatusTransition10(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.AMENDMENT_PENDING);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("1 possible status found", 1 , list.size());
	assertContains(list, CoordinatingCenterStudyStatus.OPEN);
}


/**
* test get possible status transition
*/
public void testGetPossibleStatusTransition11(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("3 possible status found", 3 , list.size());
	assertContains(list, CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
	assertContains(list, CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
	assertContains(list, CoordinatingCenterStudyStatus.OPEN);
}


/**
* test get possible status transition
*/
public void testGetPossibleStatusTransition12(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("3 possible status found", 3 , list.size());
	assertContains(list, CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
	assertContains(list, CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
	assertContains(list, CoordinatingCenterStudyStatus.OPEN);
}

/**
 * test get possible status transition
 */
public void testGetPossibleStatusTransitio13(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
	List<CoordinatingCenterStudyStatus> list =  basicStudy.getPossibleStatusTransitions() ;
	assertEquals("no possible status found", 0 , list.size());
}

/**
 * test get current accrual count
 */
public void testGetCurrentAccrualCount(){
	assertEquals("0 accrual count for basic study", new Integer(0), basicStudy.getCurrentAccrualCount());
}


/**
 * test get current accrual count
 */
public void testGetCurrentAccrualCount1(){
	StudySite studySite = new StudySite();
	StudySubject studySubject = new StudySubject();
	studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
	studySite.getStudySubjects().add(studySubject);
	basicStudy.addStudySite(studySite);
	assertEquals("1 accrual count for basic study", new Integer(1), basicStudy.getCurrentAccrualCount());
}

/**
 * test ready to open
 */
public void testReadyToOpen(){
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);

	EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(319),EasyMock.aryEq(new String[]{basicStudy.getCoordinatingCenterStudyStatus().getDisplayName()}))).andReturn(new C3PRCodedRuntimeException(319, "exception message"));
	EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.STATUS_CANNOT_SET_TO_ACTIVE.CODE", null, null)).andReturn("319");
	
	replayMocks();
	try{
		basicStudy.readyToOpen();
	}catch (Exception e) {
		assertEquals("Exception is instance of C3PRCodedRuntimeException", true , e instanceof C3PRCodedRuntimeException);
	}
	verifyMocks();
		
	}

/**
 * test has registered participants
 */
public void testGetHasRegisteredParticipants(){
	assertFalse("no registered participant found", basicStudy.getHasRegisteredParticipants());
}

/**
 * test has registered participants
 */
public void testGetHasRegisteredParticipants1(){
	basicStudy = studyCreationHelper.addStudySiteAndEnrollingEpochToBasicStudy(basicStudy);
	assertFalse("no registered participant found", basicStudy.getHasRegisteredParticipants());
}

/**
 * test has registered participants
 */
public void testGetHasRegisteredParticipants2(){
	basicStudy = studyCreationHelper.addStudySiteAndEnrollingEpochToBasicStudy(basicStudy);
	basicStudy.getStudySites().get(0).getStudySubjects().add(new StudySubject());
	assertTrue("1 registered participant found", basicStudy.getHasRegisteredParticipants());
}

/**
 * test set coordinating center study status
 */
public void testSetCoordinatingCenterStudyStatus(){
	StudySite studySite = new StudySite();
	basicStudy.addStudySite(studySite);
	studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
	basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
	assertEquals("coordinating center status is open ", CoordinatingCenterStudyStatus.OPEN,basicStudy.getCoordinatingCenterStudyStatus());
	assertEquals("study site coordinating center is open ", CoordinatingCenterStudyStatus.OPEN,basicStudy.getStudySites().get(0).getCoordinatingCenterStudyStatus());
}


}