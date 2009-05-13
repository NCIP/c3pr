package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import org.easymock.classextension.EasyMock;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
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
		simpleStudy.getStudyOrganizations().add(organization);
		
		StudyOrganization organization2  = registerMockFor(StudyOrganization.class);
		simpleStudy.getStudyOrganizations().add(organization2);
		replayMocks();
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
		simpleStudy.getEpochs().add(epoch);
		
		Epoch epoch1  = registerMockFor(Epoch.class);
		simpleStudy.getEpochs().add(epoch1);

		replayMocks();
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
		simpleStudy.getStudyDiseases().add(disease);
		
		StudyDisease disease2  = registerMockFor(StudyDisease.class);
		simpleStudy.getStudyDiseases().add(disease2);
		replayMocks();
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
		simpleStudy.getStudyDiseases().add(disease);
		
		StudyDisease disease2  = registerMockFor(StudyDisease.class);
		simpleStudy.getStudyDiseases().add(disease2);
		replayMocks();
		assertEquals("Study should have 2 study disease", 2 , simpleStudy.getStudyDiseases().size());
		simpleStudy.removeAllStudyDisease();
		assertEquals("Study should have 0 study disease", 0 , simpleStudy.getStudyDiseases().size());
		verifyMocks();
	}
}
