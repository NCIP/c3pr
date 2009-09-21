package edu.duke.cabig.c3pr.domain;

import java.util.Date;
import java.util.List;

import org.easymock.classextension.EasyMock;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
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




}

class SubStudySite extends StudySite{
	
}