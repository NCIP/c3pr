package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.StatusType;
import edu.duke.cabig.c3pr.constants.StudyPart;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;
import edu.duke.cabig.c3pr.utils.StudyVersionCreationHelper;

public class StudyVersionTestCase extends AbstractTestCase {
	/** The simple study. */
	private StudyVersion basicStudyVersion ;
	private StudyVersion simpleStudyVersion ;
	private StudyVersionCreationHelper studyVersionCreationHelper = new StudyVersionCreationHelper() ;
	private StudyCreationHelper studyCreationHelper = new StudyCreationHelper() ;
	C3PRExceptionHelper c3prExceptionHelper = registerMockFor(C3PRExceptionHelper.class);
	MessageSource c3prErrorMessages = registerMockFor(MessageSource.class);


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		basicStudyVersion = studyVersionCreationHelper.buildBasicStudyVersion();
		basicStudyVersion.setC3prErrorMessages(c3prErrorMessages);
		basicStudyVersion.setC3PRExceptionHelper(c3prExceptionHelper);
		simpleStudyVersion = new StudyVersion();
	}

	public void testAddEpoch1() throws Exception{
		Epoch epoch = studyCreationHelper.createEpoch("Epoch1");
		Epoch epoch1 = studyCreationHelper.createEpoch("Epoch2");
		basicStudyVersion.addEpoch(epoch);
		basicStudyVersion.addEpoch(epoch1);
		assertEquals("2 epochs present", 2, basicStudyVersion.getEpochs().size());
	}

	public void testAddEpoch2() throws Exception{
		Epoch epoch = studyCreationHelper.createEpoch("Epoch1");
		Epoch epoch1 = studyCreationHelper.createEpoch("Epoch1");
		try{
			basicStudyVersion.addEpoch(epoch);
			basicStudyVersion.addEpoch(epoch1);
		}catch (RuntimeException e) {
			assertEquals("epoch with same name already exists in study","epoch with same name already exists in study", e.getMessage());
		}
		assertEquals("1 epochs present", 1, basicStudyVersion.getEpochs().size());
	}

	public void testGetEpochByName() throws Exception{
		Epoch epoch = studyCreationHelper.createEpoch("Epoch1");
		Epoch epoch1 = studyCreationHelper.createEpoch("Epoch2");
		basicStudyVersion.addEpoch(epoch);
		basicStudyVersion.addEpoch(epoch1);
		assertEquals("2 epochs present", 2, basicStudyVersion.getEpochs().size());

		Epoch epochSearched = basicStudyVersion.getEpochByName("Epoch1");
		assertNotNull("Epoch is present", epochSearched);
	}

	public void testGetEpochByName1() throws Exception{
		Epoch epoch = studyCreationHelper.createEpoch("Epoch1");
		Epoch epoch1 = studyCreationHelper.createEpoch("Epoch2");
		basicStudyVersion.addEpoch(epoch);
		basicStudyVersion.addEpoch(epoch1);
		assertEquals("2 epochs present", 2, basicStudyVersion.getEpochs().size());
		Epoch epochSearched = basicStudyVersion.getEpochByName("Epoch3");
		assertNull("Epoch not present", epochSearched);
	}

	public void testAddConsent1() throws Exception{
		Consent consent1 = studyCreationHelper.createConsent("Consent 1");
		Consent consent2 = studyCreationHelper.createConsent("Consent 2");
		basicStudyVersion.addConsent(consent1);
		basicStudyVersion.addConsent(consent2);
		assertEquals("2 consents present", 2, basicStudyVersion.getConsents().size());
	}

	public void testAddConsent2() throws Exception{
		Consent consent1 = studyCreationHelper.createConsent("Consent 1");
		Consent consent2 = studyCreationHelper.createConsent("Consent 1");
		try{
			basicStudyVersion.addConsent(consent1);
			basicStudyVersion.addConsent(consent2);
		}catch (RuntimeException e) {
			assertEquals("Consent with same name already exists in study","Consent with same name already exists in study", e.getMessage());
		}
		assertEquals("1 consent present", 1, basicStudyVersion.getConsents().size());
	}

	public void testAddStudyDisease() throws Exception{
		basicStudyVersion.addStudyDisease(new StudyDisease());
		basicStudyVersion.addStudyDisease(new StudyDisease());
		assertEquals("2 study disease present", 2, basicStudyVersion.getStudyDiseases().size());
	}

	public void testRemoveStudyDisease() throws Exception{
		StudyDisease disease = new StudyDisease();
		basicStudyVersion.addStudyDisease(disease);
		basicStudyVersion.addStudyDisease(new StudyDisease());
		assertEquals("2 study disease present", 2, basicStudyVersion.getStudyDiseases().size());
		basicStudyVersion.removeStudyDisease(disease);
		assertEquals("1 study disease present", 1, basicStudyVersion.getStudyDiseases().size());
	}

	public void testRemoveAllStudyDisease() throws Exception{
		StudyDisease disease = new StudyDisease();
		basicStudyVersion.addStudyDisease(disease);
		basicStudyVersion.addStudyDisease(new StudyDisease());

		assertEquals("2 study disease present", 2, basicStudyVersion.getStudyDiseases().size());
		basicStudyVersion.removeAllStudyDisease();
		assertEquals("no study disease present", 0, basicStudyVersion.getStudyDiseases().size());
	}

	public void testAddCompanionStudyAssociation() throws Exception{
		basicStudyVersion.addCompanionStudyAssociation(new CompanionStudyAssociation());
		basicStudyVersion.addCompanionStudyAssociation(new CompanionStudyAssociation());
		assertEquals("2 companion study association present", 2, basicStudyVersion.getCompanionStudyAssociations().size());
	}

	public void testAddStudySiteStudyVersion() throws Exception{
		basicStudyVersion.addStudySiteStudyVersion(new StudySiteStudyVersion());
		basicStudyVersion.addStudySiteStudyVersion(new StudySiteStudyVersion());
		assertEquals("2 StudySiteStudyVersion present", 2, basicStudyVersion.getStudySiteStudyVersions().size());
	}

	public void testEquals1() throws Exception{
		assertTrue("Same study version", basicStudyVersion.equals(basicStudyVersion));
	}

	public void testEquals2() throws Exception{
		assertFalse("study versions not equal because they are different domain objects", basicStudyVersion.equals(new Study()));
	}

	public void testEquals3() throws Exception{
		simpleStudyVersion.setStudy(new Study());
		assertFalse("study versions not equal because they are associated with different studies", basicStudyVersion.equals(simpleStudyVersion));
	}

	public void testEquals4() throws Exception{
		simpleStudyVersion.setStudy(basicStudyVersion.getStudy());
		simpleStudyVersion.setName("Test Name");
		assertFalse("study versions not equal because they have different names", basicStudyVersion.equals(simpleStudyVersion));
	}

	public void testEquals5() throws Exception{
		simpleStudyVersion.setStudy(basicStudyVersion.getStudy());
		simpleStudyVersion.setName(basicStudyVersion.getName());
		assertTrue("study versions are equal", basicStudyVersion.equals(simpleStudyVersion));
	}

	public void testAddAmendmentReason() throws Exception{
		assertEquals("no amendment reason present", 0, basicStudyVersion.getAmendmentReasons().size());
		basicStudyVersion.addAmendmentReason(StudyPart.COMPANION);
		basicStudyVersion.addAmendmentReason(StudyPart.DESIGN);
		assertEquals("2 amendment reason present", 2, basicStudyVersion.getAmendmentReasons().size());
	}

	public void testGetAmendmentReasonInternal() throws Exception{
		assertEquals("no amendment reason present", 0, basicStudyVersion.getAmendmentReasons().size());
		basicStudyVersion.addAmendmentReason(StudyPart.COMPANION);
		basicStudyVersion.addAmendmentReason(StudyPart.DESIGN);
		assertEquals("COMPANION : DESIGN", basicStudyVersion.getAmendmentReasonInternal());
	}

	public void testSetAmendmentReason() throws Exception{
		List<StudyPart> list = new ArrayList<StudyPart>();
		list.add(StudyPart.COMPANION);
		basicStudyVersion.setAmendmentReasons(list);
		assertEquals("1 amendment reason present", 1, basicStudyVersion.getAmendmentReasons().size());
	}

	public void testSetAmendmentReasonInternal() throws Exception{
		assertEquals("no amendment reason present", 0, basicStudyVersion.getAmendmentReasons().size());
		basicStudyVersion.setAmendmentReasonInternal("COMPANION : DESIGN");
		assertEquals("2 amendment reason present", 2, basicStudyVersion.getAmendmentReasons().size());
	}

	public void testSetAmendmentReasonInternal1() throws Exception{
		assertEquals("no amendment reason present", 0, basicStudyVersion.getAmendmentReasons().size());
		basicStudyVersion.setAmendmentReasonInternal("");
		assertEquals("no amendment reason present", 0, basicStudyVersion.getAmendmentReasons().size());
	}

	public void testGetVersionDateStr() throws Exception{
		basicStudyVersion.setVersionDate(new Date());
		assertEquals( DateUtil.formatDate(new Date(), "MM/dd/yyyy"), basicStudyVersion.getVersionDateStr());
	}

	public void testCompareTo1() throws Exception{
		assertEquals("both study version equals because version date is null", 0,  basicStudyVersion.compareTo(simpleStudyVersion));
	}

	public void testCompareTo2() throws Exception{
		basicStudyVersion.setVersionDate(new Date());
		assertEquals("simpleStudyVersion is latest then basicStudyversion because version date is null in simpleStudyVersion", 1, simpleStudyVersion.compareTo(basicStudyVersion));
	}

	public void testCompareTo3() throws Exception{
		basicStudyVersion.setVersionDate(new Date());
		assertEquals("basicStudyVersion is latest then simpleStudyversion because version date is null in simpleStudyVersion", -1, basicStudyVersion.compareTo(simpleStudyVersion));
	}

	public void testCompareTo4() throws Exception{
		basicStudyVersion.setVersionDate(new Date(2000, 10, 12));
		simpleStudyVersion.setVersionDate(new Date(1982,11,10));
		assertEquals("simpleStudyVersion is latest then basicStudyversion", 1, basicStudyVersion.compareTo(simpleStudyVersion));
	}

	public void testGetC3PRErrorMessages() throws Exception{
		assertEquals("c3pr error message is instance of ResourceBundleMessageSource", true,  simpleStudyVersion.getC3prErrorMessages() instanceof ResourceBundleMessageSource) ;
	}

	public void testGetComments() throws Exception{
		assertEquals("comment is basic study", "basic study", basicStudyVersion.getComments());
	}

	public void testGetVersionStatus() throws Exception{
		assertEquals("version status is active", StatusType.AC, basicStudyVersion.getVersionStatus());
	}

	public void testHasRandomizedEpoch() throws Exception{
		assertFalse("No Epoch present, hence not randomized epoch", basicStudyVersion.hasRandomizedEpoch());
	}

	public void testHasRandomizedEpoch1() throws Exception{
		Epoch epoch = registerMockFor(Epoch.class);
		EasyMock.expect(epoch.getRandomizedIndicator()).andReturn(false);
		basicStudyVersion.addEpoch(epoch);
		replayMocks();
		assertFalse("no randomized epoch", basicStudyVersion.hasRandomizedEpoch());
		verifyMocks();
	}

	public void testHasRandomizedEpoch2() throws Exception{
		Epoch epoch = registerMockFor(Epoch.class);
		EasyMock.expect(epoch.getRandomizedIndicator()).andReturn(true);
		basicStudyVersion.addEpoch(epoch);
		replayMocks();
		assertTrue("randomized epoch present", basicStudyVersion.hasRandomizedEpoch());
		verifyMocks();
	}

	public void testHasEnrollingEpoch() throws Exception{
		assertFalse("No Epoch present, hence not Enrolling epoch", basicStudyVersion.hasEnrollingEpoch());
	}

	public void testHasEnrollingEpoch1() throws Exception{
		Epoch epoch = registerMockFor(Epoch.class);
		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);
		basicStudyVersion.addEpoch(epoch);
		replayMocks();
		assertFalse("no Enrolling epoch", basicStudyVersion.hasEnrollingEpoch());
		verifyMocks();
	}

	public void testHasEnrollingEpoch2() throws Exception{
		Epoch epoch = registerMockFor(Epoch.class);
		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(true);
		basicStudyVersion.addEpoch(epoch);
		replayMocks();
		assertTrue("Enrolling epoch present", basicStudyVersion.hasEnrollingEpoch());
		verifyMocks();
	}

	public void testHasStratifiedEpoch() throws Exception{
		assertFalse("No Epoch present, hence not Stratified epoch", basicStudyVersion.hasStratifiedEpoch());
	}

	public void testHasStratifiedEpoch1() throws Exception{
		Epoch epoch = registerMockFor(Epoch.class);
		EasyMock.expect(epoch.getStratificationIndicator()).andReturn(false);
		basicStudyVersion.addEpoch(epoch);
		replayMocks();
		assertFalse("no Stratified epoch", basicStudyVersion.hasStratifiedEpoch());
		verifyMocks();
	}

	public void testHasStratifiedEpoch2() throws Exception{
		Epoch epoch = registerMockFor(Epoch.class);
		EasyMock.expect(epoch.getStratificationIndicator()).andReturn(true);
		basicStudyVersion.addEpoch(epoch);
		replayMocks();
		assertTrue("Stratified epoch present", basicStudyVersion.hasStratifiedEpoch());
		verifyMocks();
	}


}
