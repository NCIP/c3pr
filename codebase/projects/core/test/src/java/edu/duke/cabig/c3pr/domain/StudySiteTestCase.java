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
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.DateUtil;

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
	
	public void testTemporarilyCloseToAccrualAlreadyTemporarilyClosedToAccrualAndTreament() {
		studySite.handleStudySiteStatusChange(new Date(),SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.STATUS_ALREADY_TEMPORARY_CLOSED_TO_ACCRUAL_AND_TREATMENT.CODE",null, null)).andReturn("1");
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
	
	/**
	 * Test get possible transition. study.coordinatingCenterStudyStatus:
	 * ReadyToOpen study.coordinatingCenterStudyStatus: Pending
	 */
	public void testGetPossibleTransition1() {
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.READY_TO_OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames = studySite.getPossibleTransitions();
		assertEquals(1, apiNames.size());
		assertEquals(APIName.CREATE_STUDY_DEFINITION, apiNames.get(0));
		verifyMocks();
	}

	/**
	 * Test get possible transition. study.coordinatingCenterStudyStatus:
	 * ReadyToOpen studySite.coordinatingCenterStudyStatus: Not Pending
	 */
	public void testGetPossibleTransition2() {
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.READY_TO_OPEN).times(2);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.CORRUPT.STATE.CODE", null,null)).andReturn("1");
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
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test get possible transition. study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Pending
	 */
	public void testGetPossibleTransition3() {
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.PENDING);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames = studySite.getPossibleTransitions();
		assertEquals(1, apiNames.size());
		assertEquals(APIName.CREATE_AND_OPEN_STUDY, apiNames.get(0));
		verifyMocks();
	}

	/**
	 * Test get possible transition. study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: ReadyToOpen
	 */
	public void testGetPossibleTransition4() {
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.READY_TO_OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames = studySite.getPossibleTransitions();
		assertEquals(1, apiNames.size());
		assertEquals(APIName.OPEN_STUDY, apiNames.get(0));
		verifyMocks();
	}

	/**
	 * Test get possible transition. study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: ClosedToAccrual
	 */
	public void testGetPossibleTransition5() {
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.CORRUPT.STATE.CODE", null,null)).andReturn("1");
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
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}

	/**
	 * Test get possible transition. study.coordinatingCenterStudyStatus: Not
	 * Open studySite.coordinatingCenterStudyStatus: Not Open
	 * study.coordinatingCenterStudyStatus=
	 * studySite.coordinatingCenterStudyStatus
	 */
	public void testGetPossibleTransition6() {
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.PENDING).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames = studySite.getPossibleTransitions();
		assertEquals(0, apiNames.size());
		verifyMocks();
	}

	/**
	 * Test get possible transition. study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Open studySite.siteStudyStatus:
	 * Pending
	 */
	public void testGetPossibleTransition7() {
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.PENDING);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames = studySite.getPossibleTransitions();
		assertEquals(1, apiNames.size());
		assertEquals(APIName.ACTIVATE_STUDY_SITE, apiNames.get(0));
		verifyMocks();
	}

	/**
	 * Test get possible transition. study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Open studySite.siteStudyStatus:
	 * Active
	 */
	public void testGetPossibleTransition8() {
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.ACTIVE);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames = studySite.getPossibleTransitions();
		assertEquals(4, apiNames.size());
		assertEquals(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL, apiNames.get(0));
		assertEquals(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT,apiNames.get(1));
		assertEquals(APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL, apiNames.get(2));
		assertEquals(APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT,apiNames.get(3));
		verifyMocks();
	}

	/**
	 * Test get possible transition. study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Open studySite.siteStudyStatus:
	 * TemoporarilyCloseToAccrual
	 */
	public void testGetPossibleTransition9() {
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames = studySite.getPossibleTransitions();
		assertEquals(4, apiNames.size());
		assertEquals(APIName.ACTIVATE_STUDY_SITE, apiNames.get(0));
		assertEquals(APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT,apiNames.get(1));
		assertEquals(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL, apiNames.get(2));
		assertEquals(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT,apiNames.get(3));
		verifyMocks();
	}

	/**
	 * Test get possible transition. study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Open studySite.siteStudyStatus:
	 * TemoporarilyCloseToAccrualAndTreatment
	 */
	public void testGetPossibleTransition10() {
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames = studySite.getPossibleTransitions();
		assertEquals(3, apiNames.size());
		assertEquals(APIName.ACTIVATE_STUDY_SITE, apiNames.get(0));
		assertEquals(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL, apiNames.get(1));
		assertEquals(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT,apiNames.get(2));
		verifyMocks();
	}

	/**
	 * Test get possible transition. study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Open studySite.siteStudyStatus:
	 * CloseToAccrual
	 */
	public void testGetPossibleTransition11() {
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.CLOSED_TO_ACCRUAL);
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.setStudy(study);
		List<APIName> apiNames = studySite.getPossibleTransitions();
		assertEquals(1, apiNames.size());
		assertEquals(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT,apiNames.get(0));
		verifyMocks();
	}

	/**
	 * Test get possible transition. study.coordinatingCenterStudyStatus: Open
	 * studySite.coordinatingCenterStudyStatus: Open studySite.siteStudyStatus:
	 * CloseToAccrualAndTreatment
	 */
	public void testGetPossibleTransition12() {
		Date date = new Date();
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(2);
		replayMocks();
		studySite.handleStudySiteStatusChange(date,SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		studySite.setStudy(study);
		List<APIName> apiNames = studySite.getPossibleTransitions();
		assertEquals(0, apiNames.size());
		verifyMocks();
	}

	/**
	 * Test build map for notification.
	 */
	public void testBuildMapForNotification() {
		EasyMock.expect(healthcareSite.getName()).andReturn("testHCS").times(2);
		EasyMock.expect(study.getShortTitleText()).andReturn("testShortTitle").times(2);
		EasyMock.expect(study.getCurrentAccrualCount()).andReturn(1).times(2);
		EasyMock.expect(study.getTargetAccrualNumber()).andReturn(1).times(2);
		replayMocks();
		studySite.setStudy(study);
		Map<Object, Object> map = studySite.buildMapForNotification();
		assertEquals(SiteStudyStatus.PENDING.getDisplayName(),map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_STATUS.toString()));
		assertEquals("testHCS", map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_ID.toString()));
		assertEquals("testShortTitle",map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SHORT_TITLE.toString()));
		assertEquals("1",map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_CURRENT_ACCRUAL.toString()));
		assertEquals("1",map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_ACCRUAL_THRESHOLD.toString()));
		verifyMocks();
	}
	
	public void testAddSiteStatusHistory(){
		assertEquals("Incorrect status", SiteStudyStatus.PENDING, studySite.getSiteStudyStatus());
		assertEquals("Incorrect number of status object found",1,  studySite.getSiteStatusHistory().size());
		siteStatusHistory.setStudySite(studySite);
		replayMocks();
		studySite.addSiteStatusHistory(siteStatusHistory);
		assertEquals("Incorrect number of status object found",2,  studySite.getSiteStatusHistory().size());
		verifyMocks();
	}
	
	public void testHandleStudySiteStatusChange(){
		EasyMock.expect(siteStatusHistory.getStartDate()).andReturn(null);
		siteStatusHistory.setStudySite(studySite);
		EasyMock.expect(siteStatusHistory.getSiteStudyStatus()).andReturn(SiteStudyStatus.PENDING);
		
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.STATUS_HISTORY.NO.START_DATE.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.addSiteStatusHistory(siteStatusHistory);
		try {
			studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.ACTIVE);
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}
	
	public void testHandleStudySiteStatusChange1(){
		EasyMock.expect(siteStatusHistory.getStartDate()).andReturn(new Date()).times(4);
		siteStatusHistory.setStudySite(studySite);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.STATUS_HISTORY.INVALID.EFFECTIVE_DATE.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);

		replayMocks();
		
		Date currentDate = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(currentDate);
        calendar.add(calendar.DATE, -1);
        
		studySite.addSiteStatusHistory(siteStatusHistory);
		try {
			studySite.handleStudySiteStatusChange(calendar.getTime(), SiteStudyStatus.ACTIVE);
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}
	
	public void testHandleStudySiteStatusChange2(){
		Date currentDate = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(currentDate);
        calendar.add(calendar.DATE, -1);
        
		EasyMock.expect(siteStatusHistory.getStartDate()).andReturn(calendar.getTime()).times(3);
		EasyMock.expect(siteStatusHistory.getEndDate()).andReturn(calendar.getTime()).times(1);
		EasyMock.expect(siteStatusHistory.getSiteStudyStatus()).andReturn(SiteStudyStatus.PENDING);

		siteStatusHistory.setStudySite(studySite);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.STATUS_HISTORY.END_DATE_PRESENT.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
	    
		studySite.addSiteStatusHistory(siteStatusHistory);
		try {
			studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.ACTIVE);
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}
//	FIXME
//	public void testHandleStudySiteStatusChange3(){
//		Date currentDate = new Date();
//        GregorianCalendar calendar = new GregorianCalendar();
//        calendar.setTime(currentDate);
//        calendar.add(calendar.DATE, -100);
//        
//		Date oldDate = new Date();
//        GregorianCalendar oldCal = new GregorianCalendar();
//        oldCal.setTime(oldDate);
//        oldCal.add(calendar.DATE, -1);
//        
//		EasyMock.expect(siteStatusHistory.getStartDate()).andReturn(calendar.getTime()).times(3);
//		EasyMock.expect(siteStatusHistory.getEndDate()).andReturn(null).times(1);
//		EasyMock.expect(healthcareSite.getName()).andReturn("Duke");
//		siteStatusHistory.setStudySite(studySite);
//		siteStatusHistory.setEndDate(oldCal.getTime());
//		
//		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
//		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDY.STUDYSITE.STATUS_HISTORY.END_DATE_PRESENT.CODE",null, null)).andReturn("1");
//		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
//		siteStatusHistory.compareTo(siteStatusHistory);
//		replayMocks();
//	    
//		studySite.addSiteStatusHistory(siteStatusHistory);
//		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.ACTIVE);
//		assertEquals("Incorrect status", SiteStudyStatus.ACTIVE, studySite.getSiteStudyStatus());
//		assertEquals("Incorrect size", 2,studySite.getSiteStatusHistory().size() );
//		verifyMocks();
//	}
//	
	public void testGetIRBApprovalDate(){
		Date date = new Date();
		EasyMock.expect(studySiteStudyVersion.getIrbApprovalDate()).andReturn(date);
		studySite.setStudySiteStudyVersion(studySiteStudyVersion);
		replayMocks();
		assertEquals("Incorrect IRB Approval date", date, studySite.getIrbApprovalDate());
		verifyMocks();
	}
	
	public void testGetIRBApprovalDateStr(){
		Date date = new Date();
		String dateStr = DateUtil.formatDate(date, "MM/dd/yyyy");
		EasyMock.expect(studySiteStudyVersion.getIrbApprovalDate()).andReturn(date);
		studySite.setStudySiteStudyVersion(studySiteStudyVersion);
		replayMocks();
		assertEquals("Incorrect IRB Approval date", dateStr, studySite.getIrbApprovalDateStr());
		verifyMocks();
	}

	public void testIsUsed(){
		StudySubject studySubject = registerMockFor(StudySubject.class);
		List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		StudySubjectStudyVersion studySubjectStudyVersion = registerMockFor(StudySubjectStudyVersion.class);
		studySubjectStudyVersions.add(studySubjectStudyVersion);
		
		EasyMock.expect(studySubjectStudyVersion.getStudySubject()).andReturn(studySubject);
		EasyMock.expect(studySiteStudyVersion.getStudySubjectStudyVersions()).andReturn(studySubjectStudyVersions);
		
		List<StudySiteStudyVersion> studySiteStudyVersions = new ArrayList<StudySiteStudyVersion>();
		studySiteStudyVersions.add(studySiteStudyVersion);
		studySite.setStudySiteStudyVersions(studySiteStudyVersions);
		
		replayMocks();
		studySite.setStudy(study);
		assertTrue("Study site is in use", studySite.isUsed());
		verifyMocks();
	}
	
	public void testCanEnroll(){
		assertFalse("Can not enroll", studySite.canEnroll(studyVersion, new Date()));
	}
	
	public void testCanEnroll1(){
		Date date = new Date();
		StudyVersion studyVersion1 = registerMockFor(StudyVersion.class);
		EasyMock.expect(studySiteStudyVersion.isValid(date)).andReturn(true);
		EasyMock.expect(studySiteStudyVersion.getStudyVersion()).andReturn(studyVersion1);
		
		List<StudySiteStudyVersion> studySiteStudyVersions = new ArrayList<StudySiteStudyVersion>();
		studySiteStudyVersions.add(studySiteStudyVersion);
		studySite.setStudySiteStudyVersions(studySiteStudyVersions);
		replayMocks();
		assertFalse("Can not enroll", studySite.canEnroll(studyVersion, date));
		verifyMocks();
	}
	
	public void testCanEnroll2(){
		Date date = new Date();
		EasyMock.expect(studySiteStudyVersion.isValid(date)).andReturn(true);
		EasyMock.expect(studySiteStudyVersion.getStudyVersion()).andReturn(studyVersion);
		
		List<StudySiteStudyVersion> studySiteStudyVersions = new ArrayList<StudySiteStudyVersion>();
		studySiteStudyVersions.add(studySiteStudyVersion);
		studySite.setStudySiteStudyVersions(studySiteStudyVersions);
		replayMocks();
		assertTrue("Can  enroll", studySite.canEnroll(studyVersion, date));
		verifyMocks();
	}
	
	public void testIsNotUsed(){
		assertFalse("Study site is not in use", studySite.isUsed());
	}
	
	public void testGetTargetAccrualNumber(){
		studySite.setTargetAccrualNumber(10);
		assertEquals("Traget accrual number is 10", 10, studySite.getTargetAccrualNumber().intValue());
	}
	
	public void testGetCurrentAccrualCount(){
		StudySubject studySubject = registerMockFor(StudySubject.class);
		List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		StudySubjectStudyVersion studySubjectStudyVersion = registerMockFor(StudySubjectStudyVersion.class);
		studySubjectStudyVersions.add(studySubjectStudyVersion);
		
		List<StudySiteStudyVersion> studySiteStudyVersions = new ArrayList<StudySiteStudyVersion>();
		studySiteStudyVersions.add(studySiteStudyVersion);
		
		EasyMock.expect(studySubjectStudyVersion.getStudySubject()).andReturn(studySubject);
		EasyMock.expect(studySiteStudyVersion.getStudySubjectStudyVersions()).andReturn(studySubjectStudyVersions);
		EasyMock.expect(studySubject.getRegWorkflowStatus()).andReturn(RegistrationWorkFlowStatus.ENROLLED);
		
		studySite.setStudySiteStudyVersions(studySiteStudyVersions);
		replayMocks();
		assertEquals("accrual count is 1 ", 1,  studySite.getCurrentAccrualCount());
		verifyMocks();
	}
	
	public void testGetCurrentAccrualCount1(){
		StudySubject studySubject = registerMockFor(StudySubject.class);
		List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		StudySubjectStudyVersion studySubjectStudyVersion = registerMockFor(StudySubjectStudyVersion.class);
		studySubjectStudyVersions.add(studySubjectStudyVersion);
		
		List<StudySiteStudyVersion> studySiteStudyVersions = new ArrayList<StudySiteStudyVersion>();
		studySiteStudyVersions.add(studySiteStudyVersion);
		
		EasyMock.expect(studySubjectStudyVersion.getStudySubject()).andReturn(studySubject);
		EasyMock.expect(studySiteStudyVersion.getStudySubjectStudyVersions()).andReturn(studySubjectStudyVersions);
		EasyMock.expect(studySubject.getRegWorkflowStatus()).andReturn(RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED).times(2);
		
		studySite.setStudySiteStudyVersions(studySiteStudyVersions);
		replayMocks();
		assertEquals("accrual count is 1 ", 1,  studySite.getCurrentAccrualCount());
		verifyMocks();
	}
	
	public void testGetCurrentAccrualCount2(){
		StudySubject studySubject = registerMockFor(StudySubject.class);
		List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		StudySubjectStudyVersion studySubjectStudyVersion = registerMockFor(StudySubjectStudyVersion.class);
		studySubjectStudyVersions.add(studySubjectStudyVersion);
		
		List<StudySiteStudyVersion> studySiteStudyVersions = new ArrayList<StudySiteStudyVersion>();
		studySiteStudyVersions.add(studySiteStudyVersion);
		
		EasyMock.expect(studySubjectStudyVersion.getStudySubject()).andReturn(studySubject);
		EasyMock.expect(studySiteStudyVersion.getStudySubjectStudyVersions()).andReturn(studySubjectStudyVersions);
		EasyMock.expect(studySubject.getRegWorkflowStatus()).andReturn(RegistrationWorkFlowStatus.RESERVED).times(3);
		
		studySite.setStudySiteStudyVersions(studySiteStudyVersions);
		replayMocks();
		assertEquals("accrual count is 1 ", 1,  studySite.getCurrentAccrualCount());
		verifyMocks();
	}
	
	public void testGetCurrentAccrualCount3(){
		StudySubject studySubject = registerMockFor(StudySubject.class);
		List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		StudySubjectStudyVersion studySubjectStudyVersion = registerMockFor(StudySubjectStudyVersion.class);
		studySubjectStudyVersions.add(studySubjectStudyVersion);
		
		List<StudySiteStudyVersion> studySiteStudyVersions = new ArrayList<StudySiteStudyVersion>();
		studySiteStudyVersions.add(studySiteStudyVersion);
		
		EasyMock.expect(studySubjectStudyVersion.getStudySubject()).andReturn(studySubject);
		EasyMock.expect(studySiteStudyVersion.getStudySubjectStudyVersions()).andReturn(studySubjectStudyVersions);
		EasyMock.expect(studySubject.getRegWorkflowStatus()).andReturn(RegistrationWorkFlowStatus.PENDING).times(3);
		
		studySite.setStudySiteStudyVersions(studySiteStudyVersions);
		replayMocks();
		assertEquals("accrual count is 0 ", 0,  studySite.getCurrentAccrualCount());
		verifyMocks();
	}
	
	public void testGetCurrentAccrualCount4(){
		assertEquals("accrual count is 0 ", 0,  studySite.getCurrentAccrualCount());
	}
	
	public void testActivate(){
		studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.STATUS_CANNOT_SET_STATUS.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		try {
			studySite.activate(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}
	
	public void testActivateStudyNotOpen() throws Exception {
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.PENDING).times(2);
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.SITE.STUDY.STATUS_CANNOT_BE_SET_WITH_CURRENT_COORDINATING_CENTER_STATUS.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		replayMocks();
		studySite.setStudy(study);
		try {
			studySite.activate(new Date());
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}
	
	public void testActivateStudyIsOpen() throws Exception {
		List<StudySiteStudyVersion> studySiteStudyVersions = new ArrayList<StudySiteStudyVersion>();
		studySiteStudyVersions.add(studySiteStudyVersion);
		studySite.setStudySiteStudyVersions(studySiteStudyVersions);
		Date date = new Date();
		studySiteStudyVersion.apply(date);
		EasyMock.expect(studySiteStudyVersion.isValid(date)).andReturn(true);
		
		EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN).times(1);
		replayMocks();
		studySite.setStudy(study);
		studySite.handleStudySiteStatusChange(date, SiteStudyStatus.PENDING);
		studySite.activate(date);
		assertEquals("Study site status is ACTIVE", SiteStudyStatus.ACTIVE, studySite.getSiteStudyStatus());
		verifyMocks();
	}
	
	public void testGetCoordinatingCenterStatus(){
		studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
		assertEquals("Coordinating center status is open" , CoordinatingCenterStudyStatus.OPEN, studySite.getCoordinatingCenterStudyStatus());
	}
	
	public void testGetStudySiteStudyVersion(){
		studySite.setStudySiteStudyVersion(studySiteStudyVersion);
		assertNotNull("study site study version is present", studySite.getStudySiteStudyVersion());
	}
	
	public void testGetStudySiteStudyVersion1(){
		C3PRCodedRuntimeException c3CodedException = new C3PRCodedRuntimeException(1, "test");
		EasyMock.expect(messageSource.getMessage("C3PR.EXCEPTION.STUDYSITE.CORRUPT.STATE.CODE",null, null)).andReturn("1");
		EasyMock.expect(c3PRExceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedException);
		EasyMock.expect(healthcareSite.getName()).andReturn("Duke");
		replayMocks();
		try {
			studySite.getStudySiteStudyVersion();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
			return;
		} finally {
			verifyMocks();
		}
		fail("Should have failed");
	}
	
	public void testGetStudySiteStudyVersion2(){
		List<StudySiteStudyVersion> studySiteStudyVersions = new ArrayList<StudySiteStudyVersion>();
		studySiteStudyVersions.add(studySiteStudyVersion);
		studySite.setStudySiteStudyVersions(studySiteStudyVersions);
		
		//Date date = new Date();
		EasyMock.expect(studySiteStudyVersion.isValid(EasyMock.isA(Date.class))).andReturn(false);
		replayMocks();
		assertNull("No study site study version present", studySite.getStudySiteStudyVersion());
		verifyMocks();
	}
	
	public void testGetStudySiteStudyVersion3(){
		List<StudySiteStudyVersion> studySiteStudyVersions = new ArrayList<StudySiteStudyVersion>();
		studySiteStudyVersions.add(studySiteStudyVersion);
		studySite.setStudySiteStudyVersions(studySiteStudyVersions);
		Date date = new Date();
		EasyMock.expect(studySiteStudyVersion.isValid(date)).andReturn(true);
		replayMocks();
		assertNotNull(" study site study version present", studySite.getStudySiteStudyVersion());
		verifyMocks();
	}
	
//	FIXME
//	public void testGetLatestStudySiteStudyVersion() {
//		StudySiteStudyVersion studySiteStudyVersion1 = registerMockFor(StudySiteStudyVersion.class);
//		List<StudySiteStudyVersion> studySiteStudyVersions = new ArrayList<StudySiteStudyVersion>();
//		
//		studySiteStudyVersions.add(studySiteStudyVersion);
//		studySiteStudyVersions.add(studySiteStudyVersion1);
//		
//		EasyMock.expect(studySiteStudyVersion1.compareTo(studySiteStudyVersion)).andReturn(1);
//		
//		studySite.setStudySiteStudyVersions(studySiteStudyVersions);
//		replayMocks();
//		assertNotNull(" study site study version present", studySite.getLatestStudySiteStudyVersion());
//		assertEquals(" studySiteStudyVersion1 is latest version", studySiteStudyVersion1, studySite.getLatestStudySiteStudyVersion());
//		verifyMocks();
//	}
	
	public void testAddStudySiteStudyVersion(){
		studySiteStudyVersion.setStudySite(studySite);
		studySite.addStudySiteStudyVersion(studySiteStudyVersion);
		assertEquals("1 studysitestudyversion present", 1, studySite.getStudySiteStudyVersions().size());
	}
	
	public void testGetAccruingStudySiteStudyVersion(){
		assertNull("no study site version available for accrual", studySite.getAccruingStudySiteStudyVersion(new Date()));
	}
	
	public void testGetAccruingStudySiteStudyVersion1(){
		Date date = new Date();
		studySite.handleStudySiteStatusChange(date, SiteStudyStatus.ACTIVE);
		assertNull("no study site version available for accrual", studySite.getAccruingStudySiteStudyVersion(date));
	}
	
	public void testGetAccruingStudySiteStudyVersion2(){
		Date date = new Date();
		studySite.handleStudySiteStatusChange(date, SiteStudyStatus.ACTIVE);
		StudyVersion studyVersion1 = registerMockFor(StudyVersion.class);
		EasyMock.expect(studySiteStudyVersion.isValid(date)).andReturn(true);
		
		List<StudySiteStudyVersion> studySiteStudyVersions = new ArrayList<StudySiteStudyVersion>();
		studySiteStudyVersions.add(studySiteStudyVersion);
		studySite.setStudySiteStudyVersions(studySiteStudyVersions);
		replayMocks();
		assertNotNull("one study site version available for accrual", studySite.getAccruingStudySiteStudyVersion(date));
		verifyMocks();
	}
	
}

class SubStudySite extends StudySite{
	
}