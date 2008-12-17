package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import org.easymock.classextension.EasyMock;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;

public class StudyTestCase extends AbstractTestCase{
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		basicStudy = studyCreationHelper.createBasicStudy();
		basicStudy.setC3prErrorMessages(c3prErrorMessages);
		basicStudy.setC3PRExceptionHelper(c3prExceptionHelper);
	}
	
	C3PRExceptionHelper c3prExceptionHelper = registerMockFor(C3PRExceptionHelper.class);
	MessageSource c3prErrorMessages = registerMockFor(MessageSource.class);
	
	private StudyCreationHelper studyCreationHelper = new StudyCreationHelper() ;
	private Study basicStudy;
	
	public void setStudyCreationHelper(StudyCreationHelper studyCreationHelper) {
		this.studyCreationHelper = studyCreationHelper;
	}

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
	
	public void testDataEntryStatusIncompleteCase6() throws Exception {
		studyCreationHelper.addStudySiteRandomizedEnrollingTreatmentEpochWith2ArmsAndStratumGroupsToBasicStudy(basicStudy);
		basicStudy.getEpochs().get(0).setExceptionHelper(c3prExceptionHelper);
		basicStudy.getEpochs().get(0).setC3prErrorMessages(c3prErrorMessages);
		basicStudy.setStratificationIndicator(true);
		basicStudy.getEpochs().get(0).setStratificationIndicator(true);
		EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.RANDOMIZATION_FOR_RANDOMIZED_EPOCH.CODE", null, null)).andReturn("307");
		EasyMock.expect(c3prExceptionHelper.getRuntimeException(EasyMock.eq(307),EasyMock.aryEq(new String[]{"Treatment Epoch1"}))).andReturn(new C3PRCodedRuntimeException(307, "exception message"));
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
	
	public void testDataEntryStatusCompleteCase1() throws Exception {
		studyCreationHelper.addStudySiteAndEnrollingEpochToBasicStudy(basicStudy);
		basicStudy.setStratificationIndicator(false);
		replayMocks();
			List<Error> errors = new ArrayList<Error>();
			assertEquals("Data Entry Status should evaluate to Complete",StudyDataEntryStatus.COMPLETE,basicStudy.evaluateDataEntryStatus(errors));
			verifyMocks();
	}
	
	public void testDataEntryStatusCompleteCase2() throws Exception {
		studyCreationHelper.addStudySiteRandomizedTreatmentEpochWith2ArmsStratumGroupsAndRandomizationToBasicStudy(basicStudy);
		basicStudy.setStratificationIndicator(true);
		basicStudy.getEpochs().get(0).setStratificationIndicator(true);
		replayMocks();	
		List<Error> errors = new ArrayList<Error>();
		assertEquals("Wrong Data Entry Status",StudyDataEntryStatus.COMPLETE,basicStudy.evaluateDataEntryStatus(errors));
		verifyMocks();
	}
	
	public void testSiteStudyStatusPendingCase1() throws Exception {
		studyCreationHelper.addStudySiteAndEnrollingEpochToBasicStudy(basicStudy);
		basicStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
		assertEquals("Wrong Site study status",SiteStudyStatus.PENDING,basicStudy.getStudySites().get(0).evaluateSiteStudyStatus());
		
	}
	
	public void testSiteStudyStatusActiveCase1() throws Exception {
		studyCreationHelper.addStudySiteRandomizedTreatmentEpochWith2ArmsStratumGroupsAndRandomizationToBasicStudy(basicStudy);
		basicStudy.setStratificationIndicator(false);
		basicStudy.setCoordinatingCenterStudyStatus(basicStudy.evaluateCoordinatingCenterStudyStatus());
		assertEquals("Study status should evaluate to Active",CoordinatingCenterStudyStatus.OPEN,basicStudy.getCoordinatingCenterStudyStatus());
		assertEquals("Site Study status should evaluate to Open",SiteStudyStatus.ACTIVE,basicStudy.getStudySites().get(0).evaluateSiteStudyStatus());
	}
	
	public void testChangeCoordinatingStatusPendingToActiveCase1() throws Exception {
		Study study = new Study();
		Study study1 = studyCreationHelper.createBasicStudy();
		
	}
	
	public void testChangeCoordinatingStatusPendingToActiveCase2() throws Exception {
		Study study = new Study();
		Study study1 = studyCreationHelper.createBasicStudy();
		
	}
	
	public void testChangeCoordinatingStatusPendingToActiveCase3() throws Exception {
		Study study = new Study();
		Study study1 = studyCreationHelper.createBasicStudy();
		
	}
	
	public void testChangeCoordinatingStatusPendingToActiveCase4() throws Exception {
		Study study = new Study();
		Study study1 = studyCreationHelper.createBasicStudy();
		
	}
	

}
