package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import org.easymock.classextension.EasyMock;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;

/**
 * The Class StudySiteTestCase.
 */
public class StudySiteTestCase extends AbstractTestCase {

	private StudySite studySite;
	private Study study;
	private C3PRExceptionHelper c3PRExceptionHelper;
	private MessageSource messageSource;
	private HealthcareSite healthcareSite;
	private StudyVersion studyVersion;
	private SiteStatusHistory siteStatusHistory ;
	private StudySiteStudyVersion studySiteStudyVersion ; 

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		studySite = new StudySite();
		study = registerMockFor(Study.class);
		studyVersion = registerMockFor(StudyVersion.class);
		siteStatusHistory = registerMockFor(SiteStatusHistory.class);
		c3PRExceptionHelper = registerMockFor(C3PRExceptionHelper.class);
		studySite.setExceptionHelper(c3PRExceptionHelper);
		healthcareSite = registerMockFor(HealthcareSite.class);
		studySite.setHealthcareSite(healthcareSite);
		messageSource = registerMockFor(MessageSource.class);
		studySite.setC3prErrorMessages(messageSource);
		studySiteStudyVersion = registerMockFor(StudySiteStudyVersion.class);
	}

	public void testCompareToDifferentType() {
		SubStudySite subStudySite = new SubStudySite();
		assertEquals(1, studySite.compareTo(subStudySite));
	}
	
	public void testCompareToSameReference() {
		StudySite studySite1 = studySite;
		assertEquals(0, studySite.compareTo(studySite1));
	}

	public void testChangeStudySiteStatus() {
		assertEquals(studySite.getSiteStudyStatus(), SiteStudyStatus.PENDING);
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.ACTIVE);
		assertEquals(studySite.getSiteStudyStatus(), SiteStudyStatus.ACTIVE);
	}
	
	public void testCloseToAccrualAndTreatmentAlreadyClosedToAccrualAndTreatment() {
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.STATUS_ALREADY_CLOSED_TO_ACCRUAL_AND_TREATMENT.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.closeToAccrualAndTreatment(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}

	public void testCloseToAccrualAndTreatmentPending() {
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.PENDING);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.closeToAccrualAndTreatment(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}

	public void testCloseToAccrualAndTreatment() {
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.ACTIVE);
		studySite.closeToAccrualAndTreatment(new Date());
		assertEquals(SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT, studySite.getSiteStudyStatus());
	}
	
	public void testTemporarilyCloseToAccrualAlreadyTemporarilyClosedToAccrual() {
		studySite.handleStudySiteStatusChange(new Date(),SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.ALREADY.TEMPORARILY_CLOSED_TO_ACCRUAL.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.temporarilyCloseToAccrual(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}


	public void testTemporarilyCloseToAccrualAlreadyClosedToAccrual() {
		studySite.handleStudySiteStatusChange(new Date(),SiteStudyStatus.CLOSED_TO_ACCRUAL);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.ALREADY_CLOSED.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.temporarilyCloseToAccrual(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}

	public void testTemporarilyCloseToAccrualClosedToAccrualAndTreatment() {
		studySite.handleStudySiteStatusChange(new Date(),SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.ALREADY_CLOSED.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.temporarilyCloseToAccrual(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}
	
	public void testTemporarilyCloseToAccrualPending() {
		studySite.handleStudySiteStatusChange(new Date(),SiteStudyStatus.PENDING);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.temporarilyCloseToAccrual(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}

	public void testTemporarilyCloseToAccrual() {
		studySite.handleStudySiteStatusChange(new Date(),SiteStudyStatus.ACTIVE);
		studySite.temporarilyCloseToAccrual(new Date());
		assertEquals(SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL, studySite.getSiteStudyStatus());
	}
	
	public void testTemporarilyCloseToAccrualAndTreatmentClosedToAccrual() {
		studySite.handleStudySiteStatusChange(new Date(),SiteStudyStatus.CLOSED_TO_ACCRUAL);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.ALREADY_CLOSED.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.temporarilyCloseToAccrualAndTreatment(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}

	public void testTemporarilyCloseToAccrualAndTreatmentClosedToAccrualAndTreatment() {
		studySite.handleStudySiteStatusChange(new Date(),SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.ALREADY_CLOSED.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.temporarilyCloseToAccrualAndTreatment(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}
	
	public void testTemporarilyCloseToAccrualAndTreatmentPending() {
		studySite.handleStudySiteStatusChange(new Date(),SiteStudyStatus.PENDING);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.temporarilyCloseToAccrualAndTreatment(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}
	
	public void testTemporarilyCloseToAccrualAndTreatmentAlreadyTemporarilyCloseToAccrualAndTreatment() {
		studySite.handleStudySiteStatusChange(new Date(),SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.STATUS_ALREADY_TEMPORARY_CLOSED_TO_ACCRUAL_AND_TREATMENT.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.temporarilyCloseToAccrualAndTreatment(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}
	
	public void testTemporarilyCloseToAccrualAndTreatmentl() {
		studySite.handleStudySiteStatusChange(new Date(),SiteStudyStatus.ACTIVE);
		studySite.temporarilyCloseToAccrualAndTreatment(new Date());
		assertEquals(SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT, studySite.getSiteStudyStatus());
	}

	public void testCloseToAccrualAlreadyClosedToAccrualAndTreatment() {
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.ALREADY_CLOSED.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.closeToAccrual(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}
	
	public void testCloseToAccrualAlreadyClosedToAccrual() {
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.CLOSED_TO_ACCRUAL);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.STATUS_ALREADY_CLOSED_TO_ACCRUAL.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.closeToAccrual(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}

	public void testCloseToAccrualPending() {
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.PENDING);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.closeToAccrual(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}

	public void testCloseToAccrual() {
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.ACTIVE);
		studySite.closeToAccrual(new Date());
		assertEquals(SiteStudyStatus.CLOSED_TO_ACCRUAL, studySite.getSiteStudyStatus());
	}


}

class SubStudySite extends StudySite{
	
}