package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.easymock.classextension.EasyMock;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.NotificationEmailSubstitutionVariablesEnum;
import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.extensions.StudySiteSubClass;

// TODO: Auto-generated Javadoc
/**
 * The Class StudySiteTestCase.
 */
public class StudySiteTestCase extends AbstractTestCase{

	/** The study site. */
	private StudySite studySite;

	/** The study. */
	private Study study;

	/** The c3 pr exception helper. */
    private C3PRExceptionHelper c3PRExceptionHelper;

    /** The message source. */
    private MessageSource messageSource;

    /** The healthcare site. */
    private HealthcareSite healthcareSite;
    
    
    private StudyVersion studyVersion;

	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		studySite= new StudySite();
		study= registerMockFor(Study.class);
		studyVersion = registerMockFor(StudyVersion.class);
		c3PRExceptionHelper= registerMockFor(C3PRExceptionHelper.class);
		studySite.setExceptionHelper(c3PRExceptionHelper);
		healthcareSite= registerMockFor(HealthcareSite.class);
		studySite.setHealthcareSite(healthcareSite);
		messageSource= registerMockFor(MessageSource.class);
		studySite.setC3prErrorMessages(messageSource);
	}

	/**
	 * Test compare different types of StudySites.
	 */
	public void testCompareToDifferentType(){
		StudySiteSubClass studySiteSubClass= new StudySiteSubClass();
		assertEquals(1, studySite.compareTo(studySiteSubClass));
	}

	/**
	 * Test compare same reference.
	 */
	public void testCompareToSameReference(){
		StudySite studySite1=studySite;
		assertEquals(0, studySite.compareTo(studySite1));
	}

	/**
	 * Test get irb approval date.
	 */
	public void testGetIrbApprovalDateStr(){
		studySite.setIrbApprovalDate(new Date());
		assertEquals(DateUtil.formatDate(new Date(), "MM/dd/yyyy"), studySite.getIrbApprovalDateStr());
	}

	/**
	 * Test get irb approval date , throws exception.
	 */
	public void testGetIrbApprovalDateException(){
		try {
			assertEquals("",studySite.getIrbApprovalDateStr());
		} catch (C3PRBaseRuntimeException e) {
			e.printStackTrace();
			fail("Shouldn't have failed");
		}
	}

	/**
	 * Test get start date.
	 */
	public void testGetStartDateStr(){
		studySite.setStartDate(new Date());
		assertEquals(DateUtil.formatDate(new Date(), "MM/dd/yyyy"), studySite.getStartDateStr());
	}

	/**
	 * Test get start date, null date.
	 */
	public void testGetStartDateException(){
		try {
			assertEquals("", studySite.getStartDateStr());
		} catch (C3PRBaseRuntimeException e) {
			e.printStackTrace();
			fail("Shouldn't have failed");
		}
	}

//	/**
//	 * Test get current accrual count.
//	 */
//	public void testGetCurrentAccrualCount(){
//		studySite.setStudySubjects(new ArrayList<StudySubject>());
//		StudySubject studySubject= new StudySubject();
//		studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
//		studySite.addStudySubject(studySubject);
//		studySubject= new StudySubject();
//		studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.INVALID);
//		studySite.addStudySubject(studySubject);
//		studySubject= new StudySubject();
//		studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.OFF_STUDY);
//		studySite.addStudySubject(studySubject);
//		studySubject= new StudySubject();
//		studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING);
//		studySite.addStudySubject(studySubject);
//		studySubject= new StudySubject();
//		studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED);
//		studySite.addStudySubject(studySubject);
//		studySubject= new StudySubject();
//		studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.RESERVED);
//		studySite.addStudySubject(studySubject);
//		assertEquals(3, studySite.getCurrentAccrualCount());
//	}


	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: ClosedToAccrual
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus1() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
		replayMocks();
		studySite.setStudy(study);
		assertEquals(SiteStudyStatus.CLOSED_TO_ACCRUAL, studySite.evaluateSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: ClosedToAccrualAndTreatment
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus2() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT).times(2);
		replayMocks();
		studySite.setStudy(study);
		assertEquals(SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT, studySite.evaluateSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: TemporarilyClosedToAccrual
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus3() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL).times(3);
		replayMocks();
		studySite.setStudy(study);
		assertEquals(SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL, studySite.evaluateSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: TemporarilyClosedToAccrualAndTreatment
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus4() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT).times(4);
		replayMocks();
		studySite.setStudy(study);
		assertEquals(SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT, studySite.evaluateSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: AmendmentPending
	 * siteStudyStatus: Active
	 *
	 * @throws Exception the exception
	 */
//	public void testEvaluateSiteStudyStatus5() throws Exception{
//		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.AMENDMENT_PENDING).times(5);
//		studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);
//		replayMocks();
//		assertEquals(SiteStudyStatus.AMENDMENT_PENDING, studySite.evaluateSiteStudyStatus());
//		verifyMocks();
//	}
//
	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: null
	 * id: not null
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus6() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setId(1);
		C3PRCodedException c3CodedException= new C3PRCodedException(1,"test");
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(5);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.IRB_APPROVAL_DATE.CODE", null, null)).andReturn("1");
		//EasyMock.expect(c3PRExceptionHelper.getException(EasyMock.isA(Integer.class),new String[] { EasyMock.isA(String.class) })).andReturn(c3CodedException);
		EasyMock.expect(c3PRExceptionHelper.getException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		EasyMock.expect(healthcareSite.getName()).andReturn("test");
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.evaluateSiteStudyStatus();
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: null
	 * id: null
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus7() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(5);
		replayMocks();
		studySite.setStudy(study);
		assertEquals(SiteStudyStatus.PENDING, studySite.evaluateSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: Future
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus8() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setId(1);
		GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE, 1);
        studySite.setIrbApprovalDate(calendar.getTime());
		C3PRCodedException c3CodedException= new C3PRCodedException(1,"test");
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(5);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.INVALID.IRB_APPROVAL_DATE.CODE", null, null)).andReturn("1");
		EasyMock.expect(healthcareSite.getName()).andReturn("test");
		EasyMock.expect(c3PRExceptionHelper.getException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		//EasyMock.expect(c3PRExceptionHelper.getException(1,new String[]{EasyMock.isA(String.class), EasyMock.isA(String.class)})).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.evaluateSiteStudyStatus();
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: Future
	 * id: null
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus9() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE, 1);
        studySite.setIrbApprovalDate(calendar.getTime());
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(5);
		replayMocks();
		studySite.setStudy(study);
		assertEquals(SiteStudyStatus.PENDING, studySite.evaluateSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: expired
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus10() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setId(1);
		GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.YEAR, -2);
        studySite.setIrbApprovalDate(calendar.getTime());
		C3PRCodedException c3CodedException= new C3PRCodedException(1,"test");
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(5);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.EXPIRED.IRB_APPROVAL_DATE.CODE", null, null)).andReturn("1");
		EasyMock.expect(healthcareSite.getName()).andReturn("test");
		EasyMock.expect(c3PRExceptionHelper.getException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		//EasyMock.expect(c3PRExceptionHelper.getException(1,new String[]{EasyMock.isA(String.class), EasyMock.isA(String.class)})).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.evaluateSiteStudyStatus();
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: expired
	 * id: null
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus11() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.YEAR, -2);
        studySite.setIrbApprovalDate(calendar.getTime());
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(5);
		replayMocks();
		studySite.setStudy(study);
		assertEquals(SiteStudyStatus.PENDING, studySite.evaluateSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: correct
	 * startDate: null
	 * id: not null
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus12() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setId(1);
        studySite.setIrbApprovalDate(new Date());
		C3PRCodedException c3CodedException= new C3PRCodedException(1,"test");
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(5);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.INVALID.START_DATE.CODE", null, null)).andReturn("1");
		EasyMock.expect(healthcareSite.getName()).andReturn("test");
		EasyMock.expect(c3PRExceptionHelper.getException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.evaluateSiteStudyStatus();
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: correct
	 * startDate: Future
	 * id: not null
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus13() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setId(1);
        studySite.setIrbApprovalDate(new Date());
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE, 1);
        studySite.setStartDate(calendar.getTime());
		C3PRCodedException c3CodedException= new C3PRCodedException(1,"test");
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(5);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.INVALID.START_DATE.CODE", null, null)).andReturn("1");
		EasyMock.expect(healthcareSite.getName()).andReturn("test");
		EasyMock.expect(c3PRExceptionHelper.getException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.evaluateSiteStudyStatus();
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: correct
	 * startDate: null
	 * id: null
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus14() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
        studySite.setIrbApprovalDate(new Date());
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(5);
		replayMocks();
		studySite.setStudy(study);
		assertEquals(SiteStudyStatus.PENDING, studySite.evaluateSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: correct
	 * startDate: correct
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus15() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
        studySite.setIrbApprovalDate(new Date());
        studySite.setStartDate(new Date());
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(5);
		replayMocks();
		studySite.setStudy(study);
		assertEquals(SiteStudyStatus.ACTIVE, studySite.evaluateSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: Pending
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus16() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
        studySite.setIrbApprovalDate(new Date());
        studySite.setStartDate(new Date());
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.PENDING).times(5);
		replayMocks();
		studySite.setStudy(study);
		assertEquals(SiteStudyStatus.PENDING, studySite.evaluateSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test evaluate site study status.
	 * study.coordinatingCenterStudyStatus: ReadyToOpen
	 *
	 * @throws Exception the exception
	 */
	public void testEvaluateSiteStudyStatus17() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
        studySite.setIrbApprovalDate(new Date());
        studySite.setStartDate(new Date());
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.READY_TO_OPEN).times(5);
		replayMocks();
		studySite.setStudy(study);
		assertEquals(SiteStudyStatus.PENDING, studySite.evaluateSiteStudyStatus());
		verifyMocks();
	}


	/**
	 * Test activate.
	 * siteStudyStatus: Active
	 */
	public void testActivateAlreadyActiveStudySite(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.STATUS_CANNOT_SET_STATUS.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.activate();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1,e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
	}

	/**
	 * Test activate.CloseToAccrual
	 */
	public void testActivateClosed(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		studySite.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.STATUS_CANNOT_SET_STATUS.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.activate();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1,e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
	}

	/**
	 * Test activate.CloseToAccrualAndTreatment
	 */
	public void testActivateClosedTreatment(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		studySite.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.STATUS_CANNOT_SET_STATUS.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.activate();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1,e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
	}

	/**
	 * Test Activate.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: null
	 *
	 * @throws Exception the exception
	 */
	public void testActivateNullIRB() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.PENDING);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(1);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.IRB_APPROVAL_DATE.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		EasyMock.expect(healthcareSite.getName()).andReturn("test");
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.activate();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test Activate.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: Future
	 *
	 * @throws Exception the exception
	 */
	public void testActivateFutureIRB() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE, 1);
        studySite.setIrbApprovalDate(calendar.getTime());
        C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(1);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.INVALID.IRB_APPROVAL_DATE.CODE", null, null)).andReturn("1");
		EasyMock.expect(healthcareSite.getName()).andReturn("test");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		//EasyMock.expect(c3PRExceptionHelper.getException(1,new String[]{EasyMock.isA(String.class), EasyMock.isA(String.class)})).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.activate();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test Activate.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: expired
	 *
	 * @throws Exception the exception
	 */
	public void testActivateExpiredIRB() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.YEAR, -2);
        studySite.setIrbApprovalDate(calendar.getTime());
        C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(1);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.EXPIRED.IRB_APPROVAL_DATE.CODE", null, null)).andReturn("1");
		EasyMock.expect(healthcareSite.getName()).andReturn("test");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		//EasyMock.expect(c3PRExceptionHelper.getException(1,new String[]{EasyMock.isA(String.class), EasyMock.isA(String.class)})).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.activate();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test activate.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: correct
	 * startDate: null
	 *
	 * @throws Exception the exception
	 */
	public void testActivateNullStartDate() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
        studySite.setIrbApprovalDate(new Date());
        C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(1);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.INVALID.START_DATE.CODE", null, null)).andReturn("1");
		EasyMock.expect(healthcareSite.getName()).andReturn("test");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.activate();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test activate.
	 * study.coordinatingCenterStudyStatus: Open
	 * irbApprovalDate: correct
	 * startDate: Future
	 *
	 * @throws Exception the exception
	 */
	public void testActivateFutureStartDate() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
        studySite.setIrbApprovalDate(new Date());
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE, 1);
        studySite.setStartDate(calendar.getTime());
        C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(1);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.INVALID.START_DATE.CODE", null, null)).andReturn("1");
		EasyMock.expect(healthcareSite.getName()).andReturn("test");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.activate();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test activate.
	 * study.coordinatingCenterStudyStatus: Not Open
	 *
	 * @throws Exception the exception
	 */
	public void testActivateStudyNotOpen() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
        C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.PENDING).times(2);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_CANNOT_BE_SET_WITH_CURRENT_COORDINATING_CENTER_STATUS.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.activate();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test activate.
	 * study.coordinatingCenterStudyStatus: Open
	 * study.companionIndicator: true
	 *
	 * @throws Exception the exception
	 */
	public void testActivate() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCompanionIndicator()).andReturn(true);
		studySite.setIrbApprovalDate(new Date());
		studySite.setStartDate(new Date());
		replayMocks();
		studySite.setStudy(study);
		studySite.activate();
		assertEquals(SiteStudyStatus.ACTIVE, studySite.getSiteStudyStatus());
	}

	/**
	 * Test activate with companions.
	 * study.coordinatingCenterStudyStatus: Open
	 * study.companionIndicator: false
	 *
	 * @throws Exception the exception
	 */
	public void testActivateWithCompanions() throws Exception{
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		List<CompanionStudyAssociation> comList= new ArrayList<CompanionStudyAssociation>();
		CompanionStudyAssociation companionStudyAssociation= registerMockFor(CompanionStudyAssociation.class);
		comList.add(companionStudyAssociation);

		List<StudyVersion> studyVersionList= new ArrayList<StudyVersion>();
		StudyVersion studyVersion = registerMockFor(StudyVersion.class);
		studyVersionList.add(studyVersion);

		List<StudySite> stuList= new ArrayList<StudySite>();
		StudySite companionStudySite1= registerMockFor(StudySite.class);
		stuList.add(companionStudySite1);
		StudySite companionStudySite2= registerMockFor(StudySite.class);
		stuList.add(companionStudySite2);
		StudySite companionStudySite3= registerMockFor(StudySite.class);
		stuList.add(companionStudySite3);
		HealthcareSite healthcareSite1= registerMockFor(HealthcareSite.class);
		HealthcareSite healthcareSite2= registerMockFor(HealthcareSite.class);
		HealthcareSite healthcareSite3= registerMockFor(HealthcareSite.class);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCompanionIndicator()).andReturn(false);
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		EasyMock.expect(study.getStudyVersions()).andReturn(studyVersionList);

		EasyMock.expect(studyVersion.getCompanionStudyAssociations()).andReturn(comList);
		EasyMock.expect(companionStudyAssociation.getStudySites()).andReturn(stuList);
		EasyMock.expect(healthcareSite.getPrimaryIdentifier()).andReturn("test1").times(3);
		EasyMock.expect(companionStudySite1.getHealthcareSite()).andReturn(healthcareSite1);
		EasyMock.expect(healthcareSite1.getPrimaryIdentifier()).andReturn("test1");
		EasyMock.expect(companionStudySite1.getSiteStudyStatus()).andReturn(SiteStudyStatus.PENDING);
		companionStudySite1.activate();
		EasyMock.expect(companionStudySite2.getHealthcareSite()).andReturn(healthcareSite2);
		EasyMock.expect(healthcareSite2.getPrimaryIdentifier()).andReturn("test1");
		EasyMock.expect(companionStudySite2.getSiteStudyStatus()).andReturn(SiteStudyStatus.ACTIVE);
		EasyMock.expect(companionStudySite3.getHealthcareSite()).andReturn(healthcareSite3);
		EasyMock.expect(healthcareSite3.getPrimaryIdentifier()).andReturn("test2");
		studySite.setIrbApprovalDate(new Date());
		studySite.setStartDate(new Date());

		replayMocks();
		studySite.setStudy(study);
		studySite.activate();
		assertEquals(SiteStudyStatus.ACTIVE, studySite.getSiteStudyStatus());
	}

	/**
	 * Test close to accrual.
	 * siteStudyStatus: closeToAccrual
	 */
	public void testCloseToAccrualAlreadyClosed(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.STATUS_ALREADY_CLOSED_TO_ACCRUAL.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt())).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.closeToAccrual();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test close to accrual.
	 * siteStudyStatus: amendmentPending
	 */
	public void testCloseToAccrualAmendmentPending(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.AMENDMENT_PENDING);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.closeToAccrual();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test close to accrual.
	 * siteStudyStatus: pending
	 */
	public void testCloseToAccrualPending(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.PENDING);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.closeToAccrual();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test close to accrual.
	 * siteStudyStatus: active
	 */
	public void testCloseToAccrual(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);
		replayMocks();
		studySite.setStudy(study);
		studySite.closeToAccrual();
		assertEquals(SiteStudyStatus.CLOSED_TO_ACCRUAL, studySite.getSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test close to accrual and treatment.
	 * siteStudyStatus: closeToAccrualAndTreatment
	 */
	public void testCloseToAccrualAndTreatmentAlreadyClosed(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.STATUS_ALREADY_CLOSED_TO_ACCRUAL_AND_TREATMENT.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt())).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.closeToAccrualAndTreatment();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test close to accrual and treatment.
	 * siteStudyStatus: amendmentPending
	 */
	public void testCloseToAccrualAndTreatmentAmendmentPending(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.AMENDMENT_PENDING);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.closeToAccrualAndTreatment();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test close to accrual and treatment.
	 * siteStudyStatus: pending
	 */
	public void testCloseToAccrualAndTreatmentPending(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.PENDING);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.closeToAccrualAndTreatment();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test close to accrual and treatment.
	 * siteStudyStatus: closedToAccrual
	 */
	public void testCloseToAccrualAndTreatmentClosedToAccrual(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.closeToAccrualAndTreatment();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test close to accrual and treatment.
	 * siteStudyStatus: active
	 */
	public void testCloseToAccrualAndTreatment(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);
		replayMocks();
		studySite.setStudy(study);
		studySite.closeToAccrualAndTreatment();
		assertEquals(SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT, studySite.getSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test temporarily close to accrual.
	 * siteStudyStatus: closeToAccrual
	 */
	public void testTemporarilyCloseToAccrualClosedToAccrual(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.temporarilyCloseToAccrual();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test temporarily close to accrual.
	 * siteStudyStatus: closeToAccrualAndTreatment
	 */
	public void testTemporarilyCloseToAccrualClosedToAccrualAndTreatment(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.temporarilyCloseToAccrual();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test temporarily close to accrual.
	 * siteStudyStatus: amendmentPending
	 */
	public void testTemporarilyCloseToAccrualAmendmentPending(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.AMENDMENT_PENDING);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.temporarilyCloseToAccrual();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test temporarily close to accrual.
	 * siteStudyStatus: pending
	 */
	public void testTemporarilyCloseToAccrualPending(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.PENDING);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.temporarilyCloseToAccrual();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test temporarily close to accrual.
	 * siteStudyStatus: active
	 */
	public void testTemporarilyCloseToAccrual(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);
		replayMocks();
		studySite.setStudy(study);
		studySite.temporarilyCloseToAccrual();
		assertEquals(SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL, studySite.getSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test temporarily close to accrual and treatment.
	 * siteStudyStatus: closeToAccrual
	 */
	public void testTemporarilyCloseToAccrualAndTreatmentClosedToAccrual(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.temporarilyCloseToAccrualAndTreatment();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test temporarily close to accrual and treatment.
	 * siteStudyStatus: closeToAccrualAndTreatment
	 */
	public void testTemporarilyCloseToAccrualAndTreatmentClosedToAccrualAndTreatment(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.temporarilyCloseToAccrualAndTreatment();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test temporarily close to accrual and treatment.
	 * siteStudyStatus: amendmentPending
	 */
	public void testTemporarilyCloseToAccrualAndTreatmentAmendmentPending(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.AMENDMENT_PENDING);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.temporarilyCloseToAccrualAndTreatment();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test temporarily close to accrual and treatment.
	 * siteStudyStatus: pending
	 */
	public void testTemporarilyCloseToAccrualAndTreatmentPending(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.PENDING);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE", null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.temporarilyCloseToAccrualAndTreatment();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test temporarily close to accrual and treatment.
	 * siteStudyStatus: active
	 */
	public void testTemporarilyCloseToAccrualAndTreatment(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);
		replayMocks();
		studySite.setStudy(study);
		studySite.temporarilyCloseToAccrualAndTreatment();
		assertEquals(SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT, studySite.getSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test pending amendment.
	 */
	public void testPendingAmendment(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		replayMocks();
		studySite.setStudy(study);
		studySite.pendingAmendment();
		assertEquals(SiteStudyStatus.AMENDMENT_PENDING, studySite.getSiteStudyStatus());
		verifyMocks();
	}

	/**
	 * Test build map for notification.
	 */
	public void testBuildMapForNotification(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		EasyMock.expect(healthcareSite.getName()).andReturn("testHCS").times(2);
		EasyMock.expect(study.getShortTitleText()).andReturn("testShortTitle").times(2);
		EasyMock.expect(study.getCurrentAccrualCount()).andReturn(1).times(2);
		EasyMock.expect(study.getTargetAccrualNumber()).andReturn(1).times(2);
		replayMocks();
		studySite.setStudy(study);
		Map<Object, Object> map= studySite.buildMapForNotification();
		assertEquals(SiteStudyStatus.PENDING.getDisplayName(), map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_STATUS.toString()));
		assertEquals("testHCS", map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_ID.toString()));
		assertEquals("testShortTitle", map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SHORT_TITLE.toString()));
		assertEquals("1", map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_CURRENT_ACCRUAL.toString()));
		assertEquals("1", map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_ACCRUAL_THRESHOLD.toString()));
		verifyMocks();
	}


	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: ReadyToOpen
	 * study.coordinatingCenterStudyStatus: Pending
	 */
	public void testGetPossibleTransition1(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.READY_TO_OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames= studySite.getPossibleTransitions();
		assertEquals(1, apiNames.size());
		assertEquals(APIName.CREATE_STUDY_DEFINITION, apiNames.get(0));
		verifyMocks();
	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: ReadyToOpen
	 * studySite.coordinatingCenterStudyStatus: Not Pending
	 */
	public void testGetPossibleTransition2(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.READY_TO_OPEN).times(2);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.CORRUPT.STATE.CODE", null, null)).andReturn("1");
		EasyMock.expect(healthcareSite.getName()).andReturn("test");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.getPossibleTransitions();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Pending
	 */
	public void testGetPossibleTransition3(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.PENDING);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames= studySite.getPossibleTransitions();
		assertEquals(1, apiNames.size());
		assertEquals(APIName.CREATE_AND_OPEN_STUDY, apiNames.get(0));
		verifyMocks();
	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: ReadyToOpen
	 */
	public void testGetPossibleTransition4(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.READY_TO_OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames= studySite.getPossibleTransitions();
		assertEquals(1, apiNames.size());
		assertEquals(APIName.OPEN_STUDY, apiNames.get(0));
		verifyMocks();
	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: ClosedToAccrual
	 */
	public void testGetPossibleTransition5(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.CORRUPT.STATE.CODE", null, null)).andReturn("1");
		EasyMock.expect(healthcareSite.getName()).andReturn("test");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.getPossibleTransitions();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		}finally{
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: AmendmentPending
	 * studySite.coordinatingCenterStudyStatus: Pending
	 */
//	public void testGetPossibleTransition6(){
//		studySite.setSiteStudyStatus(SiteStudyStatus.PENDING);
//		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.AMENDMENT_PENDING).times(2);
//		replayMocks();
//		List<APIName> apiNames= studySite.getPossibleTransitions();
//		assertEquals(1, apiNames.size());
//		assertEquals(APIName.CREATE_AND_OPEN_STUDY, apiNames.get(0));
//		verifyMocks();
//	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: AmendmentPending
	 * studySite.coordinatingCenterStudyStatus: ReadyToOpen
	 */
//	public void testGetPossibleTransition7(){
//		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.READY_TO_OPEN);
//		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.AMENDMENT_PENDING).times(2);
//		replayMocks();
//		List<APIName> apiNames= studySite.getPossibleTransitions();
//		assertEquals(1, apiNames.size());
//		assertEquals(APIName.OPEN_STUDY, apiNames.get(0));
//		verifyMocks();
//	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: AmendmentPending
	 * studySite.coordinatingCenterStudyStatus: Open
	 */
//	public void testGetPossibleTransition8(){
//		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
//		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.AMENDMENT_PENDING).times(2);
//		replayMocks();
//		List<APIName> apiNames= studySite.getPossibleTransitions();
//		assertEquals(1, apiNames.size());
//		assertEquals(APIName.AMEND_STUDY, apiNames.get(0));
//		verifyMocks();
//	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: AmendmentPending
	 * studySite.coordinatingCenterStudyStatus: ClosedToAccrual
	 */
//	public void testGetPossibleTransition9(){
//		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
//		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.AMENDMENT_PENDING).times(2);
//		C3PRCodedRuntimeException c3CodedException= new C3PRCodedRuntimeException(1,"test");
//		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.CORRUPT.STATE.CODE", null, null)).andReturn("1");
//		EasyMock.expect(healthcareSite.getName()).andReturn("test");
//		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
//		replayMocks();
//		try {
//			studySite.getPossibleTransitions();
//		} catch (C3PRCodedRuntimeException e) {
//			e.printStackTrace();
//			assertEquals(1, e.getExceptionCode());
//			return;
//		}finally{
//			verifyMocks();
//		}
//		fail("Should have failed");
//	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: Not Open
	 * studySite.coordinatingCenterStudyStatus: Not Open
	 * study.coordinatingCenterStudyStatus= studySite.coordinatingCenterStudyStatus
	 */
	public void testGetPossibleTransition10(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.PENDING).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames= studySite.getPossibleTransitions();
		assertEquals(0, apiNames.size());
		verifyMocks();
	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Open
	 * studySite.siteStudyStatus: Pending
	 */
	public void testGetPossibleTransition11(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.PENDING);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames= studySite.getPossibleTransitions();
		assertEquals(1, apiNames.size());
		assertEquals(APIName.ACTIVATE_STUDY_SITE, apiNames.get(0));
		verifyMocks();
	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Open
	 * studySite.siteStudyStatus: AmendmentPending
	 */
	public void testGetPossibleTransition12(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.AMENDMENT_PENDING);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames= studySite.getPossibleTransitions();
		assertEquals(1, apiNames.size());
		assertEquals(APIName.ACTIVATE_STUDY_SITE, apiNames.get(0));
		verifyMocks();
	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Open
	 * studySite.siteStudyStatus: Active
	 */
	public void testGetPossibleTransition13(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames= studySite.getPossibleTransitions();
		assertEquals(4, apiNames.size());
		assertEquals(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL, apiNames.get(0));
		assertEquals(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT, apiNames.get(1));
		assertEquals(APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL, apiNames.get(2));
		assertEquals(APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT, apiNames.get(3));
		verifyMocks();
	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Open
	 * studySite.siteStudyStatus: TemoporarilyCloseToAccrual
	 */
	public void testGetPossibleTransition14(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames= studySite.getPossibleTransitions();
		assertEquals(3, apiNames.size());
		assertEquals(APIName.ACTIVATE_STUDY_SITE, apiNames.get(0));
		assertEquals(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL, apiNames.get(1));
		assertEquals(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT, apiNames.get(2));
		verifyMocks();
	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Open
	 * studySite.siteStudyStatus: TemoporarilyCloseToAccrualAndTreatment
	 */
	public void testGetPossibleTransition15(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames= studySite.getPossibleTransitions();
		assertEquals(3, apiNames.size());
		assertEquals(APIName.ACTIVATE_STUDY_SITE, apiNames.get(0));
		assertEquals(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL, apiNames.get(1));
		assertEquals(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT, apiNames.get(2));
		verifyMocks();
	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Open
	 * studySite.siteStudyStatus: CloseToAccrual
	 */
	public void testGetPossibleTransition16(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames= studySite.getPossibleTransitions();
		assertEquals(0, apiNames.size());
		verifyMocks();
	}

	/**
	 * Test get possible transition.
	 * study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Open
	 * studySite.siteStudyStatus: CloseToAccrualAndTreatment
	 */
	public void testGetPossibleTransition17(){
		EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
		studySite.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames= studySite.getPossibleTransitions();
		assertEquals(0, apiNames.size());
		verifyMocks();
	}

}
