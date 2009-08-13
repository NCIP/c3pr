package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.NotificationEmailSubstitutionVariablesEnum;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;
import edu.duke.cabig.c3pr.domain.customfield.BooleanCustomField;
import edu.duke.cabig.c3pr.domain.customfield.CustomField;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.C3PRInvalidDataEntryException;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;
import edu.duke.cabig.c3pr.utils.StudySubjectCreatorHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class StudySubjectTest.
 */
public class StudySubjectTest extends AbstractTestCase {

	/** The study site. */
	StudySite studySite;

	/** The participant. */
	Participant participant;

	/** The study subject. */
	StudySubject studySubject;

	/** The scheduled epoch. */
	ScheduledEpoch scheduledEpoch;

	/** The epoch. */
	Epoch epoch;

	/** The study. */
	Study study;

	StudyCreationHelper studyCreationHelper = new StudyCreationHelper();

	/** The c3pr exception helper. */
	C3PRExceptionHelper c3prExceptionHelper;

	StudySiteStudyVersion studySiteStudyVersion;

	StudySubjectStudyVersion studySubjectStudyVersion;
	StudyVersion studyVersion ;

	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		study = registerMockFor(Study.class);
		epoch = registerMockFor(Epoch.class);
		studySite = registerMockFor(StudySite.class);
		scheduledEpoch = registerMockFor(ScheduledEpoch.class);
		studySubject = new StudySubject();
//		studySubject.addScheduledEpoch(scheduledEpoch);
		participant = registerMockFor(Participant.class);
		studySubject.setParticipant(participant);
		c3prExceptionHelper = registerMockFor(C3PRExceptionHelper.class);
		studySiteStudyVersion = registerMockFor(StudySiteStudyVersion.class);
		studySubjectStudyVersion = registerMockFor(StudySubjectStudyVersion.class);
		studyVersion = registerMockFor(StudyVersion.class);
	}

    /** The study subject creator helper. */
    private StudySubjectCreatorHelper studySubjectCreatorHelper=new StudySubjectCreatorHelper();

    /**
     * Registration Data Entry Status test blank Study Subject.
     *
     * @throws Exception the exception
     */
    public void testEvaluateRegistrationDataEntryStatusInComplete() throws Exception {
        assertEquals("Wrong Registration Data Entry Status",
                        RegistrationDataEntryStatus.INCOMPLETE, studySubject
                                        .evaluateRegistrationDataEntryStatus());
    }

    /**
     * Registration Data Entry Status test InformedConsent Date Filled InformedConsent Version
     * Filled.
     */
    public void testEvaluateRegistrationDataEntryStatusComplete() {
        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        try {
			studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySiteWith2EnrollingEpochs(RandomizationType.PHONE_CALL, true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        assertEquals("Wrong Registration Data Entry Status", RegistrationDataEntryStatus.COMPLETE,
                        studySubject.evaluateRegistrationDataEntryStatus());
    }

    /**
     * Epoch Data Entry Status test Randomized Treatment Epoch Blank Scheduled Epoch.
     *
     * @throws Exception the exception
     */
    public void testEvaluateEpochDataEntryStatusInComplete1() throws Exception {
    	// remove the mock scheduled epoch first so that we don't have to expect on equals of the mock epoch object
        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        assertEquals("Wrong Epoch Data Entry Status", ScheduledEpochDataEntryStatus.INCOMPLETE,
                        studySubject.evaluateScheduledEpochDataEntryStatus((List)new ArrayList<Error>()));
    }

    /**
     * Epoch Data Entry Status test Randomized Treatment Epoch Eligibility Done.
     *
     * @throws Exception the exception
     */
    public void testEvaluateEpochDataEntryStatusInComplete2() throws Exception {
    	// remove the mock scheduled epoch first so that we don't have to expect on equals of the mock epoch object
        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        (scheduledEpochFirst.getEpoch()).getArms().size();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        assertEquals("Wrong Epoch Data Entry Status", ScheduledEpochDataEntryStatus.COMPLETE, studySubject.evaluateScheduledEpochDataEntryStatus((List)new ArrayList<Error>()));
    }

    /**
     * Epoch Data Entry Status test Non Randomized Treatment Epoch with Arms Eligibility Done
     * Stratification Done Arm not assigned.
     *
     * @throws Exception the exception
     */
    public void testEvaluateEpochDataEntryStatusInComplete3() throws Exception {
    	// remove the mock scheduled epoch first so that we don't have to expect on equals of the mock epoch object
        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(false));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        assertEquals("Wrong Epoch Data Entry Status", ScheduledEpochDataEntryStatus.INCOMPLETE,
                        studySubject.evaluateScheduledEpochDataEntryStatus((List)new ArrayList<Error>()));
    }

    /**
     * Epoch Data Entry Status test Randomized Treatment Epoch Eligibility Done Stratification Done.
     *
     * @throws Exception the exception
     */
    public void testEvaluateEpochDataEntryStatusComplete1() throws Exception {
    	// remove the mock scheduled epoch first so that we don't have to expect on equals of the mock epoch object
        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        assertEquals("Wrong Epoch Data Entry Status", ScheduledEpochDataEntryStatus.COMPLETE,
                        studySubject.evaluateScheduledEpochDataEntryStatus((List)new ArrayList<Error>()));
    }

    /**
     * Epoch Data Entry Status test Non Randomized Treatment Epoch Eligibility Done Stratification
     * Done Assigned Arm.
     *
     * @throws Exception the exception
     */
    public void testEvaluateEpochDataEntryStatusComplete2() throws Exception {
    	// remove the mock scheduled epoch first so that we don't have to expect on equals of the mock epoch object
        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
        ScheduledArm scheduledArm = new ScheduledArm();
        scheduledArm.setArm(new Arm());
        (scheduledEpochFirst).addScheduledArm(scheduledArm);
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(false));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        assertEquals("Wrong Epoch Data Entry Status", ScheduledEpochDataEntryStatus.COMPLETE,
                        studySubject.evaluateScheduledEpochDataEntryStatus((List)new ArrayList<Error>()));
    }

    /**
     * Epoch Data Entry Status test Non Treatment Epoch.
     *
     * @throws Exception the exception
     */
    public void testEvaluateEpochDataEntryStatusComplete3() throws Exception {
        ScheduledEpoch scheduledEpoch = new ScheduledEpoch();
        Epoch epoch = new Epoch();
        scheduledEpoch.setEpoch(epoch);
        scheduledEpoch.setEligibilityIndicator(true);
        studySubject.addScheduledEpoch(scheduledEpoch);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        assertEquals("Wrong Epoch Data Entry Status", ScheduledEpochDataEntryStatus.COMPLETE,
                        studySubject.evaluateScheduledEpochDataEntryStatus((List)new ArrayList<Error>()));
    }

//    public void testIsRegisterableTrue(){
//        StudySubject studySubject = new StudySubject();
//        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
//        ScheduledEpoch sc=new ScheduledEpoch();
//        Epoch nt=new Epoch();
//        nt.setEnrollmentIndicator(true);
//        sc.setEpoch(nt);
//        studySubject.addScheduledEpoch(sc);
//        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
//
//        assertEquals("Wrong isRegisterable return", true,
//                        studySubject.getIsRegisterable());
//    }
//
//    public void testIsRegisterableFalse(){
//        StudySubject studySubject = new StudySubject();
//        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
//        ScheduledEpoch sc=new ScheduledEpoch();
//        sc.setEpoch(new Epoch());
//        studySubject.addScheduledEpoch(sc);
//        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
//        assertEquals("Wrong isRegisterable return", false,
//                        studySubject.getIsRegisterable());
//    }

    /**
 * Test requires coordinating center approval true.
 */
public void testRequiresCoordinatingCenterApprovalTrue(){
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        ScheduledEpoch sc=new ScheduledEpoch();
        Epoch nt=new Epoch();
        nt.setEnrollmentIndicator(true);
        sc.setEpoch(nt);
        studySubject.addScheduledEpoch(sc);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setStudySite(new StudySite());
        studySubject.getStudySite().setStudy(new Study());
        studySubject.getStudySite().getStudy().setMultiInstitutionIndicator(true);
        assertEquals("Wrong requiresCoordinatingCenterApproval return", true,
                        studySubject.requiresCoordinatingCenterApproval());
    }

    /**
     * Test requires coordinating center approval false0.
     */
    public void testRequiresCoordinatingCenterApprovalFalse0(){
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        ScheduledEpoch sc=new ScheduledEpoch();
        Epoch nt=new Epoch();
        nt.setEnrollmentIndicator(false);
        sc.setEpoch(nt);
        studySubject.addScheduledEpoch(sc);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setStudySite(new StudySite());
        studySubject.getStudySite().setStudy(new Study());
        studySubject.getStudySite().getStudy().setMultiInstitutionIndicator(true);
        assertEquals("Wrong requiresCoordinatingCenterApproval return", false,
                        studySubject.requiresCoordinatingCenterApproval());
    }

    /**
     * Test requires coordinating center approval false1.
     */
    public void testRequiresCoordinatingCenterApprovalFalse1(){
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        ScheduledEpoch sc=new ScheduledEpoch();
        Epoch nt=new Epoch();
        nt.setEnrollmentIndicator(true);
        sc.setEpoch(nt);
        studySubject.addScheduledEpoch(sc);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setStudySite(new StudySite());
        studySubject.getStudySite().setStudy(new Study());
        studySubject.getStudySite().getStudy().setMultiInstitutionIndicator(false);
        assertEquals("Wrong requiresCoordinatingCenterApproval return", false,
                        studySubject.requiresCoordinatingCenterApproval());
    }

    /**
     * Test constructor.
     *
     * @throws Exception the exception
     */
    public void testConstructor() throws Exception{
    	StudySubject studySubject = new StudySubject(false);

    	assertNotNull("Start Date is null, may be not initialized", studySubject.getStartDate());

    }

    /**
     * Test equals.
     *
     * @throws Exception the exception
     */
    public void testEquals1() throws Exception{
    	StudySubject studySubject1 = new StudySubject();
    	StudySubject studySubject2 = new StudySubject();

    	StudySite studySite1 = new StudySite();

    	studySubject1.setStudySite(studySite1);
    	studySubject2.setStudySite(studySite1);

    	Participant participant1 = new Participant();
    	participant1.setId(1);
    	studySubject1.setParticipant(participant1);
    	studySubject2.setParticipant(participant1);
    	Date startDate = new Date();
    	studySubject1.setStartDate(startDate);
    	studySubject2.setStartDate(startDate);
    	assertFalse("The two study subjects should not have been equal",studySubject1.equals(null));
    	assertTrue("The two study subjects should have been equal",studySubject1.equals(studySubject2));

    	// Creating a new Participant with different Id and setting the second study subject to this participant
    	Participant participant2 = new Participant();
    	participant2.setId(2);
    	studySubject2.setParticipant(participant2);
    	assertFalse("The two study subjects should not have been equal",studySubject1.equals(studySubject2));
    }

    /**
     * Test equals2.
     *
     * @throws Exception the exception
     */
    public void testEquals2() throws Exception{
    	StudySubject studySubject1 = new StudySubject();
    	assertFalse("Should not have been equal",studySubject1.equals(new StudySite()));
    	StudySubject studySubject2 = new StudySubject();

    	StudySite studySite1 = new StudySite();

    	studySubject1.setStudySite(studySite1);
    	studySubject2.setStudySite(studySite1);

    	Date startDate = new Date();
    	studySubject1.setStartDate(null);
    	studySubject2.setStartDate(startDate);
    	assertFalse("The two study subjects should not have been equal",studySubject1.equals(null));

    	studySubject1.setStartDate(startDate);
    	assertTrue("The two study subjects should have been equal",studySubject1.equals(studySubject2));

    	GregorianCalendar calendar = new GregorianCalendar();
    	calendar.setTime(startDate);
    	calendar.add(calendar.YEAR, -1);

    	studySubject2.setStartDate(calendar.getTime());
    	assertFalse("The two study subjects should not have been equal",studySubject1.equals(studySubject2));
    }

    /**
     * Test equals3.
     *
     * @throws Exception the exception
     */
    public void testEquals3() throws Exception{
    	StudySubject studySubject1 = new StudySubject();
    	assertFalse("Should not have been equal",studySubject1.equals(new StudySite()));
    	StudySubject studySubject2 = new StudySubject();

    	StudySite studySite1 = new StudySite();

    	studySubject1.setStudySite(null);
    	studySubject2.setStudySite(studySite1);

    	assertFalse("The two study subjects should not have been equal",studySubject1.equals(null));

    	StudySite studySite2 = new StudySite();
    	studySite2.setHealthcareSite(new LocalHealthcareSite());
    	studySubject1.setStudySite(studySite1);
    	studySubject2.setStudySite(studySite2);

    	assertFalse("The two study subjects should not have been equal",studySubject1.equals(studySubject2));
    }

    /**
     * Test hash code1.
     *
     * @throws Exception the exception
     */
    public void testHashCode1() throws Exception{
    	/*EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
    	replayMocks();*/
    	int prime = 29;
    	StudySubject studySubject1 = new StudySubject();

    	assertEquals("Wrong hash code",prime*prime*prime + studySubject1.getStartDate().hashCode(), studySubject1.hashCode());

    	StudySite studySite1 = new StudySite();

    	studySubject1.setStudySite(studySite1);
    	assertEquals("Wrong hash code",prime*prime*(prime+studySite1.hashCode()) + studySubject1.getStartDate().hashCode(), studySubject1.hashCode());
    //	verifyMocks();
    }

    /**
     * Test informed consent signed date str.
     *
     * @throws Exception the exception
     */
    public void testGetInformedConsentSignedDateStr() throws Exception{
    	StudySubject studySubject1 = new StudySubject();
    	assertEquals("Unexpected consent signed date","", studySubject1.getInformedConsentSignedDateStr());
    	Date informedConsentSignedDate = new Date();
    	studySubject1.setInformedConsentSignedDate(informedConsentSignedDate);
    	assertEquals("Wrong consent date",DateUtil.formatDate(informedConsentSignedDate,"MM/dd/yyyy"), studySubject1.getInformedConsentSignedDateStr());
    }

    /**
     * Test get off study date str.
     *
     * @throws Exception the exception
     */
    public void testGetOffStudyDateStr() throws Exception{
    	assertEquals("Unexpected off study date","",studySubject.getOffStudyDateStr());

    	Date offStudyDate = new Date();
    	studySubject.setOffStudyDate(offStudyDate);
    	assertEquals("Wrong off study date or in wrong format",DateUtil.formatDate(offStudyDate, "MM/dd/yyyy"),studySubject.getOffStudyDateStr());
    }

    /**
     * Test get start date str.
     *
     * @throws Exception the exception
     */
    public void testGetStartDateStr() throws Exception{
    	StudySubject studySubject = new StudySubject();
    	studySubject.setStartDate(null);
    	assertEquals("Wrong start date","",studySubject.getStartDateStr());

    	Date startDate = new Date();
    	studySubject.setStartDate(startDate);
    	assertEquals("Wrong start date or in wrong format",DateUtil.formatDate(startDate, "MM/dd/yyyy"),studySubject.getStartDateStr());
    }

    /**
     * Test remove identifier.
     *
     * @throws Exception the exception
     */
    public void testRemoveIdentifier() throws Exception{
    	OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
		orgIdentifier.setHealthcareSite(new LocalHealthcareSite());
		orgIdentifier.setValue("NCI_1232");

		SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
		sysIdentifier.setSystemName("Sys");
		sysIdentifier.setValue("sys_123");

		studySubject.addIdentifier(orgIdentifier);
		studySubject.addIdentifier(sysIdentifier);

		assertEquals("Wrong number of identifiers",2,studySubject.getIdentifiers().size());
		studySubject.removeIdentifier(orgIdentifier);
		assertEquals("Wrong number of identifiers: identifiers was not removed",1,studySubject.getIdentifiers().size());
		assertEquals("Wrong identifier removed",sysIdentifier,studySubject.getIdentifiers().get(0));
    }

    /**
     * Test get primary identifier.
     *
     * @throws Exception the exception
     */
    public void testGetPrimaryIdentifier() throws Exception{
		StudySubject studySubject = new StudySubject();
		assertEquals("Wrong number of identifiers",0,studySubject.getIdentifiers().size());
		OrganizationAssignedIdentifier orgIdentifier1 = new OrganizationAssignedIdentifier();
		orgIdentifier1.setPrimaryIndicator(true);
		orgIdentifier1.setType(OrganizationIdentifierTypeEnum.MRN);
		orgIdentifier1.setValue("1232");
		studySubject.addIdentifier(orgIdentifier1);

		assertEquals("Wrong primary identifier","1232",studySubject.getPrimaryIdentifier());

		orgIdentifier1.setPrimaryIndicator(false);
		assertNull("Unexpected primary identifier",studySubject.getPrimaryIdentifier());
	}

    /**
     * Test get treating physician full name.
     *
     * @throws Exception the exception
     */
    public void testGetTreatingPhysicianFullName() throws Exception{
    	studySubject.setOtherTreatingPhysician("Steven Chang");

    	assertEquals("Wrong other treating physician","Steven Chang",studySubject.getTreatingPhysicianFullName());

    	Investigator investigator = registerMockFor(Investigator.class);
    	HealthcareSiteInvestigator healthcareSiteInvestigator = registerMockFor(HealthcareSiteInvestigator.class);
    	healthcareSiteInvestigator.setInvestigator(investigator);
    	StudyInvestigator treatingPhysician = registerMockFor(StudyInvestigator.class);
    	treatingPhysician.setHealthcareSiteInvestigator(healthcareSiteInvestigator);
    	studySubject.setTreatingPhysician(treatingPhysician);

    	EasyMock.expect(treatingPhysician.getHealthcareSiteInvestigator()).andReturn(healthcareSiteInvestigator);
    	EasyMock.expect(healthcareSiteInvestigator.getInvestigator()).andReturn(investigator);
    	EasyMock.expect(investigator.getFullName()).andReturn("Dr. Richard Baker");

    	replayMocks();
    	assertEquals("Wrong treating physician","Dr. Richard Baker",studySubject.getTreatingPhysicianFullName());

    }

    /**
     * Test get data entry status string.
     *
     * @throws Exception the exception
     */
    public void testGetDataEntryStatusString() throws Exception{
    	assertEquals("Wrong data entry status ","Incomplete",studySubject.getDataEntryStatusString());
    }

    /**
     * Test get data entry status.
     *
     * @throws Exception the exception
     */
    public void testGetDataEntryStatus() throws Exception{
    	assertFalse("Wrong data entry status ",studySubject.getDataEntryStatus());
    }

    /**
     * Test get coordinating center identifier.
     *
     * @throws Exception the exception
     */
    public void testGetCoordinatingCenterIdentifier() throws Exception{
    	assertNull("Unexpected coordinating center identifier",studySubject.getCoOrdinatingCenterIdentifier());
    }

    /**
     * Test get c3 d identifier.
     *
     * @throws Exception the exception
     */
    public void testGetC3DIdentifier() throws Exception{
    	assertNull("Unexpected C3D identifier",studySubject.getC3DIdentifier());
    }

    /**
     * Test evaluate registration data entry status.
     *
     * @throws Exception the exception
     */
	public void testEvaluateRegistrationDataEntryStatus() throws Exception {
		EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(1);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);


		EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch).times(1);
		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(true).times(1);
		EasyMock.expect(studySite.getStudy()).andReturn(study).times(1);

		CompanionStudyAssociation companionStudyAssociation = registerMockFor(CompanionStudyAssociation.class);
		ArrayList<CompanionStudyAssociation> companionStudyAssociations = new ArrayList<CompanionStudyAssociation>();
		companionStudyAssociations.add(companionStudyAssociation);

		EasyMock.expect(study.getCompanionStudyAssociations()).andReturn(companionStudyAssociations).times(1);
		EasyMock.expect(companionStudyAssociation.getMandatoryIndicator()).andReturn(true).times(1);

		replayMocks();

		studySubject.setInformedConsentSignedDate(new Date());
    	studySubject.setInformedConsentVersion("1");

		studySubject.setStudySite(studySite);
		studySubject.addScheduledEpoch(scheduledEpoch);

		assertEquals("wrong registration status", RegistrationDataEntryStatus.INCOMPLETE, studySubject.evaluateRegistrationDataEntryStatus());

		verifyMocks();

	}

    /**
     * Test evaluate registration data entry status with errors.
     *
     * @throws Exception the exception
     */
    public void testEvaluateRegistrationDataEntryStatusWithErrors() throws Exception{
    	List<Error> errors = new ArrayList<Error>();
    	EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(1);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);


		EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch).times(1);
		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(true).times(1);
		EasyMock.expect(studySite.getStudy()).andReturn(study).times(1);

		CompanionStudyAssociation companionStudyAssociation = registerMockFor(CompanionStudyAssociation.class);
		ArrayList<CompanionStudyAssociation> companionStudyAssociations = new ArrayList<CompanionStudyAssociation>();
		companionStudyAssociations.add(companionStudyAssociation);

		EasyMock.expect(study.getCompanionStudyAssociations()).andReturn(companionStudyAssociations).times(1);
		EasyMock.expect(companionStudyAssociation.getMandatoryIndicator()).andReturn(true).times(1);

    	replayMocks();

    	studySubject.setStudySite(studySite);
		studySubject.addScheduledEpoch(scheduledEpoch);

    	studySubject.evaluateRegistrationDataEntryStatus(errors);
    	assertEquals("Wrong number of errors",3,errors.size());
    	verifyMocks();
    }

    /**
     * Test is study site.
     *
     * @throws Exception the exception
     */
    public void testIsStudySite() throws Exception{
    	HealthcareSite localHealthcareSite = registerMockFor(HealthcareSite.class);
    	EasyMock.expect(studySite.getHealthcareSite()).andReturn(localHealthcareSite);
    	EasyMock.expect(localHealthcareSite.getPrimaryIdentifier()).andReturn("NCI100");
    	EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);

    	replayMocks();
    	studySubject.setStudySite(studySite);
    	assertTrue("Expected to be a study site",studySubject.isStudySite("NCI100"));

    	verifyMocks();
    }

    /**
     * Test is data entry complete.
     *
     * @throws Exception the exception
     */
    public void testIsDataEntryComplete() throws Exception{
    	studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
    	EasyMock.expect(scheduledEpoch.getScEpochDataEntryStatus()).andReturn(ScheduledEpochDataEntryStatus.COMPLETE);
    	replayMocks();
    	studySubject.addScheduledEpoch(scheduledEpoch);
    	assertTrue("Expected data entry to be complete",studySubject.isDataEntryComplete());

    	verifyMocks();

    }


    /**
     * Test is data entry complete.
     *
     * @throws Exception the exception
     */
    public void testIsDataEntryComplete1() throws Exception{
    	studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
    	EasyMock.expect(scheduledEpoch.getScEpochDataEntryStatus()).andReturn(ScheduledEpochDataEntryStatus.INCOMPLETE);
    	replayMocks();
    	studySubject.addScheduledEpoch(scheduledEpoch);
    	assertFalse("Expected data entry to be incomplete",studySubject.isDataEntryComplete());
    	verifyMocks();
    }

    /**
     * Test ready for randomization.
     *
     * @throws Exception the exception
     */
    public void testReadyForRandomization() throws Exception{
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(1);
    	studySubject.setStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

    	studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
    	EasyMock.expect(scheduledEpoch.getScEpochDataEntryStatus()).andReturn(ScheduledEpochDataEntryStatus.COMPLETE);
    	replayMocks();
    	studySubject.addScheduledEpoch(scheduledEpoch);
    	assertTrue("Expected to be ready for randomization",studySubject.readyForRandomization());

    	verifyMocks();
    }

    /**
     * Test is coordinating center.
     *
     * @throws Exception the exception
     */
    public void testIsCoordinatingCenter() throws Exception{
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(study.isCoOrdinatingCenter("nci_code")).andReturn(true);
    	EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);

    	replayMocks();
    	studySubject.setStudySite(studySite);
    	assertTrue("Expected to be a coordinating center",studySubject.isCoOrdinatingCenter("nci_code"));

    	verifyMocks();
    }


    /**
     * Test required affiliate site response.
     *
     * @throws Exception the exception
     */
    public void testRequiredAffiliateSiteResponse() throws Exception{

    	GridEndPoint gridEndPoint = new GridEndPoint();
    	gridEndPoint.setStatus(WorkFlowStatusType.MESSAGE_RECIEVED);
    	studySubject.addEndPoint(gridEndPoint);
    	assertTrue("Expected to require affiliate site response",studySubject.requiresAffiliateSiteResponse());
    }

    /**
     * Test required affiliate site response.
     *
     * @throws Exception the exception
     */
    public void testRequiredAffiliateSiteResponseNegative() throws Exception{

    	GridEndPoint gridEndPoint = new GridEndPoint();
    	gridEndPoint.setStatus(WorkFlowStatusType.MESSAGE_REPLY_CONFIRMED);
    	studySubject.addEndPoint(gridEndPoint);
    	assertFalse("Response not expected",studySubject.requiresAffiliateSiteResponse());
    }

    /**
     * Test add and remove child study subject.
     *
     * @throws Exception the exception
     */
    public void testAddAndRemoveChildStudySubject() throws Exception{
    	StudySubject childStudySubject = new StudySubject();
    	studySubject.addChildStudySubject(childStudySubject);
    	assertEquals("Wrong number of study subjects",1,studySubject.getChildStudySubjects().size());
    	studySubject.removeChildStudySubject(childStudySubject);
    	assertEquals("Expected no child study subject,should have been removed",0,studySubject.getChildStudySubjects().size());
    }

    /**
     * Test build map for notification.
     *
     * @throws Exception the exception
     */
    public void testBuildMapForNotification() throws Exception{
    	OrganizationAssignedIdentifier mrn = registerMockFor(OrganizationAssignedIdentifier.class);
    	EasyMock.expect(participant.getMRN()).andReturn(mrn).times(2);
    	EasyMock.expect(mrn.getValue()).andReturn("mrnValue").times(2);
    	EasyMock.expect(studySite.getStudy()).andReturn(study).times(1);
    	EasyMock.expect(study.getShortTitleText()).andReturn("Short_title_text").times(2);
    	EasyMock.expect(study.getId()).andReturn(1).times(2);
    	EasyMock.expect(study.getTargetAccrualNumber()).andReturn(5).times(2);
    	EasyMock.expect(studySite.getTargetAccrualNumber()).andReturn(4).times(2);
    	EasyMock.expect(study.getCurrentAccrualCount()).andReturn(3).times(2);
    	EasyMock.expect(studySite.getCurrentAccrualCount()).andReturn(2);

    	EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);

    	replayMocks();
    	studySubject.setStudySite(studySite);
    	studySubject.addScheduledEpoch(scheduledEpoch);
    	Map<Object, Object> map = studySubject.buildMapForNotification();
    	assertEquals("Wrong number of entries in the notificaiton map",8,map.size());

    	assertEquals("Wrong entry in map","mrnValue",map.get(NotificationEmailSubstitutionVariablesEnum.PARTICIPANT_MRN.toString()));
    	assertEquals("Wrong entry in map","Short_title_text",map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SHORT_TITLE.toString()));
    	assertEquals("Wrong entry in map",5,map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_ACCRUAL_THRESHOLD.toString()));
    	assertEquals("Wrong entry in map",4,map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_ACCRUAL_THRESHOLD.toString()));
    	assertEquals("Wrong entry in map",3,map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_CURRENT_ACCRUAL.toString()));
    	assertEquals("Wrong entry in map",2,map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_CURRENT_ACCRUAL.toString()));

    	verifyMocks();
    }


    /**
     * Test create scheduled epoch.
     *
     * @throws Exception the exception
     */
    public void testCreateScheduledEpoch() throws Exception{
    	ScheduledEpoch scheduledEpoch1 = studySubject.createScheduledEpoch(studyCreationHelper.createEpoch("treatment epoch"));
    	assertNotNull("Error in creating scheduled epoch",scheduledEpoch1);
    	assertEquals("Scheduled epoch created with wrong epoch name","treatment epoch",scheduledEpoch1.getEpoch().getName());
    }

    /**
     * Test if scheduled epoch created for this epoch.
     *
     * @throws Exception the exception
     */
    public void testIfScheduledEpochCreatedForThisEpoch() throws Exception{
    	assertFalse("Unexpected scheduled epoch",studySubject.hasScheduledEpoch(new Epoch()));
    	Epoch epoch1 = studyCreationHelper.createEpoch("treatment epoch");
    	studySubject.addScheduledEpoch(studySubject.createScheduledEpoch(epoch1));

    	assertTrue("Scheduled epoch was expected with given epoch:" + epoch1.getName(),studySubject.hasScheduledEpoch(epoch1));
    }

//    /**
//     * Test get matching scheduled epoch.
//     *
//     * @throws Exception the exception
//     */
//    public void testGetMatchingScheduledEpoch() throws Exception{
//    	// remove the mock scheduled epoch first so that we don't have to expect on equals of the mock epoch object
//    	studySubject.getScheduledEpochs().remove(0);
//    	Epoch epoch1 = Epoch.createEpoch("treatment epoch");
//    	assertNull("Unexpected scheduled epoch",studySubject.getMatchingScheduledEpoch(epoch1));
//    	studySubject.addScheduledEpoch(studySubject.createScheduledEpoch(epoch1));
//
//    	assertNotNull("Scheduled epoch was expected with given epoch:", studySubject.getMatchingScheduledEpoch(epoch1));
//    }

    /**
     * Test can reserve.
     *
     * @throws Exception the exception
     */
    public void testCanReserve() throws Exception{
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(3);
    	studySubject.setStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

	    List<Error> errors = new ArrayList<Error>();
		EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);
		EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.INCOMPLETE);
		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.INCOMPLETE);
		replayMocks();
		studySubject.addScheduledEpoch(scheduledEpoch);
		errors = studySubject.canReserve();

		assertEquals("Expected errors",2,errors.size());
		verifyMocks();
    }

    /**
     * Test register1.
     *
     * @throws Exception the exception
     */
    public void testRegister1() throws Exception{
        EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(1);
    	studySubject.setStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

    	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.REGISTERED);
    	EasyMock.expect(epoch.getName()).andReturn("treatment");

    	replayMocks();

    	studySubject.addScheduledEpoch(scheduledEpoch);



    	try{
    		studySubject.register();
    		fail("Should have thrown exception");
    	} catch(C3PRBaseRuntimeException ex){

    	}
    	verifyMocks();
    }

    /**
     * Test register2.
     *
     * @throws Exception the exception
     */
    public void testRegister2() throws Exception{

     	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(4);
    	studySubject.setStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

    	StudySubject parentStudySubject = registerMockFor(StudySubject.class);
    	studySubject.setParentStudySubject(parentStudySubject);
    	studySubject.setInformedConsentSignedDate(new Date());
    	studySubject.setInformedConsentVersion("1");

    	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING);
 		EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.COMPLETE);

 		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);

    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getRandomizedIndicator()).andReturn(false);

 		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);

 		scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
 		scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);

    	replayMocks();
    	studySubject.addScheduledEpoch(scheduledEpoch);
    	studySubject.register();
    	verifyMocks();
    }

    /**
     * Test register3.
     *
     * @throws Exception the exception
     */
    public void testRegister3() throws Exception{

    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(4);
    	studySubject.setStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch).times(2);
    	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING);
    	EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.COMPLETE);

 		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);
 		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.INCOMPLETE);
    	replayMocks();

    	studySubject.addScheduledEpoch(scheduledEpoch);
    	try{
    		studySubject.register();
    		fail("Should have thrown an invalid data entry exception");
    	}catch(C3PRInvalidDataEntryException ex){

    	}
    	verifyMocks();
    }

    /**
     * Test reserve1.
     *
     * @throws Exception the exception
     */
    public void testReserve1() throws Exception{
    	studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED);
    	try{
    		studySubject.reserve();
    		fail("Should have thrown an invalid data entry exception");
    	}catch(C3PRBaseRuntimeException ex){

    	}
    }

    /**
     * Test reserve2.
     *
     * @throws Exception the exception
     */
    public void testReserve2() throws Exception{
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(1);
    	studySubject.setStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getReservationIndicator()).andReturn(false);
    	replayMocks();
    	studySubject.addScheduledEpoch(scheduledEpoch);
    	try{
    		studySubject.reserve();
    		fail("Should have thrown an invalid data entry exception");
    	}catch(C3PRBaseRuntimeException ex){

    	}
    	verifyMocks();
    }

    /**
     * Test reserve3.
     *
     * @throws Exception the exception
     */
    public void testReserve3() throws Exception{
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(4);
    	studySubject.setStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getReservationIndicator()).andReturn(true);

    	List<Error> errors = new ArrayList<Error>();
		EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);
		EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.INCOMPLETE);
		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.INCOMPLETE);

    	replayMocks();
    	studySubject.addScheduledEpoch(scheduledEpoch);
    	try{
    		studySubject.reserve();
    		fail("Should have thrown an invalid data entry exception");
    	}catch(C3PRBaseRuntimeException ex){

    	}
    	verifyMocks();
    }

    /**
     * Test reserve4.
     *
     * @throws Exception the exception
     */
    public void testReserve4() throws Exception{

    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(4);
    	studySubject.setStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

    	studySubject.setInformedConsentSignedDate(new Date());
    	studySubject.setInformedConsentVersion("1");

    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getReservationIndicator()).andReturn(true);

    	List<Error> errors = new ArrayList<Error>();
		EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);
		EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.COMPLETE);
		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
		scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);

    	replayMocks();
    	studySubject.addScheduledEpoch(scheduledEpoch);
    	try{
    		studySubject.reserve();
    	}catch(C3PRBaseRuntimeException ex){
    		fail("Should have thrown an invalid data entry exception");
    	}
    	verifyMocks();
    }

    /**
     * Test is randomized on scheduled epoch.
     *
     * @throws Exception the exception
     */
    public void testIsRandomizedOnScheduledEpoch() throws Exception{
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(1);
    	studySubject.setStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

    	EasyMock.expect(scheduledEpoch.getScheduledArm()).andReturn(null);
    	replayMocks();
    	studySubject.addScheduledEpoch(scheduledEpoch);
    	assertFalse("Wrong randomization status on scheduled epoch",studySubject.isRandomizedOnScheduledEpoch());
    	verifyMocks();
    }

    /**
     * Test do book randomization no arm available.
     *
     * @throws Exception the exception
     */
    public void testDoBookRandomizationNoArmAvailable() throws Exception{
    	StratumGroup stratumGroup = registerMockFor(StratumGroup.class);
    	Arm arm = registerMockFor(Arm.class);
    	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.REGISTERED_BUT_NOT_RANDOMIZED).times(2);
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getStratificationIndicator()).andReturn(true);
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(study.getRandomizationType()).andReturn(RandomizationType.BOOK);
    	EasyMock.expect(scheduledEpoch.getStratumGroup()).andReturn(stratumGroup);
    	int code =  studySubject.getCode("C3PR.EXCEPTION.REGISTRATION.NO.ARM.AVAILABLE.BOOK.EXHAUSTED.CODE");
    	EasyMock.expect(stratumGroup.getNextArm()).andThrow(new C3PRCodedRuntimeException(code, "next arm not available"));
    	EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);
    	studySubject.setStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);
    	studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(5);
    	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	replayMocks();
    	try {
    		studySubject.addScheduledEpoch(scheduledEpoch);
    		studySubject.setStudySite(studySite);
    		studySubject.doLocalEnrollment();
    		fail("Should have thrown exception");
    	}catch(C3PRBaseRuntimeException ex){

    	}
    	assertEquals("Wrong registration status",RegistrationWorkFlowStatus.PENDING,studySubject.getRegWorkflowStatus());
    	verifyMocks();
    }

  /**
   * Test do phone call randomization.
   *
   * @throws Exception the exception
   */
  public void testDoPhoneCallRandomization() throws Exception{

    	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.REGISTERED_BUT_NOT_RANDOMIZED).times(2);
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getName()).andReturn("treating epoch");
    	EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
    	EasyMock.expect(study.getRandomizationType()).andReturn(RandomizationType.PHONE_CALL);
    	EasyMock.expect(scheduledEpoch.getScheduledArm()).andReturn(null);
    	EasyMock.expect(study.getBlindedIndicator()).andReturn(false);

    	EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(1);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(2);
    	studySubject.setStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);
    	studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(3);
    	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(2);


    	replayMocks();
    	try {
    		studySubject.setStudySite(studySite);
    		studySubject.addScheduledEpoch(scheduledEpoch);
    		studySubject.doLocalEnrollment();
    		fail("Should not have thrown exception");
    	}catch(C3PRBaseRuntimeException ex){

    	}
    	assertEquals("Wrong registration status",RegistrationWorkFlowStatus.PENDING,studySubject.getRegWorkflowStatus());
    	verifyMocks();
    }

  /**
   * Test take subject off study fail when not enrolled.
   *
   * @throws Exception the exception
   */
  public void testTakeSubjectOffStudyFailWhenNotEnrolled() throws Exception{
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED);
	  try{
		  studySubject.takeSubjectOffStudy("Subject being taken off study", new Date());
		  fail("Should have thrown exception");
	  }catch(C3PRBaseRuntimeException ex){

	  }

	  assertFalse("There should have been and error in taking study subject off study",studySubject.getRegWorkflowStatus()==RegistrationWorkFlowStatus.OFF_STUDY);
  }

  /**
   * Take subject off study successful.
   *
   * @throws Exception the exception
   */
  public void testTakeSubjectOffStudySuccessful() throws Exception{
	  String offStudyReasonText = "Subject being taken off study";
	  Date offStudyDate = new Date();
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
	  try{
		  studySubject.takeSubjectOffStudy(offStudyReasonText, offStudyDate);
	  }catch(C3PRBaseRuntimeException ex){
		  fail("Should not have thrown exception");
	  }

	  assertTrue("Unable to take study subject off study",studySubject.getRegWorkflowStatus()==RegistrationWorkFlowStatus.OFF_STUDY);
	  assertEquals("Wrong offStudy reason",offStudyReasonText,studySubject.getOffStudyReasonText());
	  assertEquals("Wrong offStudy date",offStudyDate,studySubject.getOffStudyDate());
  }

  /**
   * Test transfer fail when not enrolled.
   *
   * @throws Exception the exception
   */
  public void testTransferFailWhenNotEnrolled() throws Exception{
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED);
	  try{
		  studySubject.transfer();
		  fail("Transfer should have failed");
	  } catch(C3PRBaseRuntimeException ex){
		  assertTrue("Wrong failed message",ex.getMessage().contains("The subject has to be enrolled before being transferred"));
	  }
  }

  /**
   * Test transfer fail when not transferrable1.
   *
   * @throws Exception the exception
   */
  public void testTransferFailWhenNotTransferrable1() throws Exception{
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
	  try{
		  studySubject.prepareForTransfer();
		  fail("Transfer should have failed");
	  } catch(C3PRBaseRuntimeException ex){
	  }
  }

  /**
   * Test transfer fail to a lower order epoch.
   *
   * @throws Exception the exception
   */
 /* public void testTransferFailToALowerOrderEpoch() throws Exception{
	//  Collections collections  = registerMockFor(Collections.class,Collections.class.getMethod("sort", null));
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
	  ScheduledEpoch scheduledEpoch1 = registerMockFor(ScheduledEpoch.class);
	  studySubject.addScheduledEpoch(scheduledEpoch1);
	  Epoch epoch1 = registerMockFor(Epoch.class);
	//  List<ScheduledEpoch> tempList = new ArrayList<ScheduledEpoch>();
	//  tempList.addAll(studySubject.getScheduledEpochs());
	  EasyMock.expect(((ScheduledEpoch)scheduledEpoch).compareTo((ScheduledEpoch)scheduledEpoch1)).andReturn(1);
	  EasyMock.expect(((ScheduledEpoch)(EasyMock.anyObject())).compareTo(((ScheduledEpoch)EasyMock.anyObject()))).andReturn(1);
	//  collections.sort(tempList);
	//  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(epoch.getEpochOrder()).andReturn(2);
	  EasyMock.expect(scheduledEpoch1.getEpoch()).andReturn(epoch1);
	  EasyMock.expect(epoch1.getEpochOrder()).andReturn(1);
	  int code = studySubject.getCode("C3PR.EXCEPTION.REGISTRATION.TRANSFER.CANNOT_TO_LOWER_ORDER_EPOCH.CODE");
	  EasyMock.expect(c3prExceptionHelper.getRuntimeException(code)).andReturn(new C3PRCodedRuntimeException(code,""));
	  replayMocks();
	  try{
		  studySubject.transfer();
		  fail("Transfer should have failed");
	  } catch(C3PRBaseRuntimeException ex){
	  }
  }*/

  /**
   * Test get code.
   *
   * @throws Exception the exception
   */
 public void testGetCode() throws Exception{
	  assertEquals("Wrong code",233,studySubject.getCode("C3PR.EXCEPTION.REGISTRATION.TRANSFER.CANNOT_TO_LOWER_ORDER_EPOCH.CODE"));
  }

  /**
   * Test prepare for enrollment1.
   *
   * @throws Exception the exception
   */
  public void testPrepareForEnrollment1() throws Exception{
	  EasyMock.expect(studySite.getStudy()).andReturn(study);
	  EasyMock.expect(study.getStandaloneIndicator()).andReturn(false);
	  EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
  	  EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);
	  StudySubject parentStudySubject = registerMockFor(StudySubject.class);
	  studySubject.setParentStudySubject(parentStudySubject);

	  replayMocks();
	  try{
		  studySubject.setStudySite(studySite);
		  studySubject.prepareForEnrollment();
		  fail("Should have thrown exception");
	  } catch(Exception ex){

	  }
	  verifyMocks();
  }

  /**
   * Test prepare for enrollment2.
   *
   * @throws Exception the exception
   */
	public void testPrepareForEnrollment2() throws Exception {
		  EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
		  EasyMock.expect(study.getStandaloneIndicator()).andReturn(false);

		  StudySubject childStudySubject = registerMockFor(StudySubject.class);
		  studySubject.addChildStudySubject(childStudySubject);

		  Study companionStudy = registerMockFor(Study.class);
		  List<CompanionStudyAssociation> compStudyAssociations = new ArrayList<CompanionStudyAssociation>();
		  CompanionStudyAssociation compStudyAssociation = registerMockFor(CompanionStudyAssociation.class);
		  compStudyAssociations.add(compStudyAssociation);

		  EasyMock.expect(study.getCompanionStudyAssociations()).andReturn(compStudyAssociations);
		  EasyMock.expect(compStudyAssociation.getMandatoryIndicator()).andReturn(true);
		  EasyMock.expect(childStudySubject.getStudySite()).andReturn(studySite);
		  EasyMock.expect(studySite.getStudy()).andReturn(companionStudy);
		  EasyMock.expect(childStudySubject.getDataEntryStatus()).andReturn(true);

		  EasyMock.expect(studySite.getStudy()).andReturn(study);
		  EasyMock.expect(study.getCompanionStudyAssociations()).andReturn(compStudyAssociations);
		  EasyMock.expect(compStudyAssociation.getCompanionStudy()).andReturn(companionStudy);
		  EasyMock.expect(childStudySubject.getStudySite()).andReturn(studySite);
		  EasyMock.expect(studySite.getStudy()).andReturn(companionStudy);

		  EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(2);
	  	  EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);

		  EasyMock.expect(childStudySubject.getRegWorkflowStatus()).andReturn(RegistrationWorkFlowStatus.PENDING);

		  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch).times(1);

		  EasyMock.expect(compStudyAssociation.getCompanionStudy()).andReturn(companionStudy);
		  EasyMock.expect(compStudyAssociation.getMandatoryIndicator()).andReturn(true);


		  Epoch companionStudyEpoch = registerMockFor(Epoch.class);
		  ScheduledEpoch childScheduledEpoch = registerMockFor(ScheduledEpoch.class);
		  EasyMock.expect(childScheduledEpoch.getEpoch()).andReturn(companionStudyEpoch).times(1);


		  EasyMock.expect(childStudySubject.getScheduledEpoch()).andReturn(childScheduledEpoch);
		  EasyMock.expect(companionStudyEpoch.getEnrollmentIndicator()).andReturn(false);

		replayMocks();
		try {
			studySubject.setStudySite(studySite);
			studySubject.prepareForEnrollment();
			fail("Should have thrown exception");
		} catch (Exception ex) {

		}

		verifyMocks();
	}

  /**
   * Test prepare for enrollment2.
   *
   * @throws Exception the exception
   */
  public void testPrepareForEnrollment3() throws Exception{
	  EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
	  EasyMock.expect(study.getStandaloneIndicator()).andReturn(false);

	  StudySubject childStudySubject = registerMockFor(StudySubject.class);
	  studySubject.addChildStudySubject(childStudySubject);

	  Study companionStudy = registerMockFor(Study.class);
	  List<CompanionStudyAssociation> compStudyAssociations = new ArrayList<CompanionStudyAssociation>();
	  CompanionStudyAssociation compStudyAssociation = registerMockFor(CompanionStudyAssociation.class);
	  compStudyAssociations.add(compStudyAssociation);

	  StudyVersion studyVersion = registerMockFor(StudyVersion.class);
	  EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion);
	  EasyMock.expect(studyVersion.getCompanionStudyAssociations()).andReturn(compStudyAssociations);
	  EasyMock.expect(compStudyAssociation.getMandatoryIndicator()).andReturn(true);
	  EasyMock.expect(childStudySubject.getStudySite()).andReturn(studySite);
	  EasyMock.expect(studySite.getStudy()).andReturn(companionStudy);
	  EasyMock.expect(childStudySubject.getDataEntryStatus()).andReturn(true);

	  EasyMock.expect(studySite.getStudy()).andReturn(study);
	  StudyVersion studyVersion1 = registerMockFor(StudyVersion.class);
	  EasyMock.expect(study.getStudyVersion()).andReturn(studyVersion1);
	  EasyMock.expect(studyVersion1.getCompanionStudyAssociations()).andReturn(compStudyAssociations);
	  EasyMock.expect(compStudyAssociation.getCompanionStudy()).andReturn(companionStudy);
	  EasyMock.expect(childStudySubject.getStudySite()).andReturn(studySite);
	  EasyMock.expect(studySite.getStudy()).andReturn(companionStudy);

	  EasyMock.expect(childStudySubject.getRegWorkflowStatus()).andReturn(RegistrationWorkFlowStatus.PENDING);

	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch).times(1);
	  EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(true);

	  EasyMock.expect(compStudyAssociation.getCompanionStudy()).andReturn(companionStudy);
	  EasyMock.expect(compStudyAssociation.getMandatoryIndicator()).andReturn(true);


	  Epoch companionStudyEpoch = registerMockFor(Epoch.class);
	  ScheduledEpoch childScheduledEpoch = registerMockFor(ScheduledEpoch.class);
	  EasyMock.expect(childScheduledEpoch.getEpoch()).andReturn(companionStudyEpoch).times(1);


	  EasyMock.expect(childStudySubject.getScheduledEpoch()).andReturn(childScheduledEpoch);
	  EasyMock.expect(companionStudyEpoch.getEnrollmentIndicator()).andReturn(true);

	  EasyMock.expect(childStudySubject.getRegWorkflowStatus()).andReturn(RegistrationWorkFlowStatus.PENDING);

	  replayMocks();
	  try{
		  studySubject.prepareForEnrollment();
		  fail("Should have thrown exception");
	  } catch(Exception ex){

	  }

	  verifyMocks();
  }


  /**
   * Test add custom fields.
   *
   * @throws Exception the exception
   */
  public void testAddCustomFields() throws Exception{
		StudySubject studySubject = new StudySubject();
		CustomField customField = new BooleanCustomField();
		assertEquals("Unexpected customfields",0,studySubject.getCustomFields().size());

		studySubject.addCustomField(customField);
		assertEquals("Wrong number of customfields",1,studySubject.getCustomFields().size());
	}

  /**
   * Test can enroll.
   *
   * @throws Exception the exception
   */
  public void testCanEnroll() throws Exception{
	  	List<Error> errors = new ArrayList<Error>();
	  	StudySubject childStudySubject = registerMockFor(StudySubject.class);
	  	studySubject.addChildStudySubject(childStudySubject);

	  	CompanionStudyAssociation compStudyAssociation = registerMockFor(CompanionStudyAssociation.class);
	  	Study companionStudy = registerMockFor(Study.class);
	  	List<CompanionStudyAssociation> companionStudyAssociations = new ArrayList<CompanionStudyAssociation>();
	  	companionStudyAssociations.add(compStudyAssociation);

	  	EasyMock.expect(studySite.getStudy()).andReturn(study);
		EasyMock.expect(study.getCompanionStudyAssociations()).andReturn(companionStudyAssociations);
	  	EasyMock.expect(compStudyAssociation.getCompanionStudy()).andReturn(companionStudy);

	  	EasyMock.expect(childStudySubject.getStudySite()).andReturn(studySite);
	  	EasyMock.expect(studySite.getStudy()).andReturn(companionStudy);
	    EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	  	  EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);

	  	EasyMock.expect(compStudyAssociation.getMandatoryIndicator()).andReturn(true);
	  	childStudySubject.evaluateRegistrationDataEntryStatus(errors);
	  	EasyMock.expect(childStudySubject.evaluateScheduledEpochDataEntryStatus(errors)).andReturn(ScheduledEpochDataEntryStatus.COMPLETE);
	  	replayMocks();
	  	studySubject.setStudySite(studySite);
	  	studySubject.addScheduledEpoch(scheduledEpoch);
		studySubject.canEnroll(errors);
		verifyMocks();
	}

  /**
   * Test get scheduled epoch by epoch name.
   *
   * @throws Exception the exception
   */
  public void testGetScheduledEpochByEpochName() throws Exception{
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch).times(2);
	  EasyMock.expect(epoch.getName()).andReturn("Treatment").times(2);

	  replayMocks();
	  studySubject.addScheduledEpoch(scheduledEpoch);
	  assertNull("Unexpected Scheduled Epoch",studySubject.getScheduledEpochByName("Arbitrary Epoch Name"));
	  assertEquals("Wrong Scheduled Epoch",scheduledEpoch,studySubject.getScheduledEpochByName("Treatment"));

	  verifyMocks();
  }

  /**
   * Test do local enrollment when already registered on scheduled epoch.
   */
  public void testDoLocalEnrollmentWhenAlreadyRegisteredOnScheduledEpoch(){
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.REGISTERED);
	  assertEquals("Wrong initial registration status",RegistrationWorkFlowStatus.PENDING,studySubject.getRegWorkflowStatus());
	  replayMocks();

	  studySubject.doLocalEnrollment();
	  assertEquals("Wrong registration status",RegistrationWorkFlowStatus.ENROLLED,studySubject.getRegWorkflowStatus());

	  verifyMocks();
  }


  /**
   * Test do multi site enrollment.
   *
   * @throws Exception the exception
   */
  public void testDoMultiSiteEnrollment() throws Exception{
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(epoch.getName()).andReturn("Treatment");
	  ScheduledEpoch coordCentReturnedScheduledEpoch = registerMockFor(ScheduledEpoch.class);
	  Epoch epochInCoordCentReturnedScheduledEpoch = registerMockFor(Epoch.class);
	  ScheduledArm scheduledArmInCoordCentReturnedScheduledEpoch = registerMockFor(ScheduledArm.class);
	  Arm coorCenArm = registerMockFor(Arm.class);

	  EasyMock.expect(coordCentReturnedScheduledEpoch.getEpoch()).andReturn(epochInCoordCentReturnedScheduledEpoch);
	  EasyMock.expect(epochInCoordCentReturnedScheduledEpoch.getName()).andReturn("Treatment");

	  EasyMock.expect(scheduledEpoch.getRequiresArm()).andReturn(true);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  Arm arm = registerMockFor(Arm.class);
	  ScheduledArm scheduledArm = registerMockFor(ScheduledArm.class);
	  EasyMock.expect(epoch.getArmByName("armA")).andReturn(arm);

	  EasyMock.expect(coordCentReturnedScheduledEpoch.getScheduledArm()).andReturn(scheduledArmInCoordCentReturnedScheduledEpoch);
	  EasyMock.expect(scheduledArmInCoordCentReturnedScheduledEpoch.getArm()).andReturn(coorCenArm);
	  EasyMock.expect(coorCenArm.getName()).andReturn("armA");

	  EasyMock.expect(scheduledEpoch.getScheduledArm()).andReturn(scheduledArm).times(2);
	  scheduledArm.setArm(arm);

	  EasyMock.expect(studySite.getStudy()).andReturn(study);
	  StudyCoordinatingCenter studyCoordCetner = registerMockFor(StudyCoordinatingCenter.class);
	  EasyMock.expect(study.getStudyCoordinatingCenter()).andReturn(studyCoordCetner);
	  HealthcareSite coordCenterOrganization = registerMockFor(HealthcareSite.class);
	  OrganizationAssignedIdentifier coordinatingCenterAssignedIdentifier = registerMockFor(OrganizationAssignedIdentifier.class);
	  EasyMock.expect(studyCoordCetner.getHealthcareSite()).andReturn(coordCenterOrganization);

	  EasyMock.expect(coordinatingCenterAssignedIdentifier.getGridId()).andReturn("grid Id");
	  EasyMock.expect(coordinatingCenterAssignedIdentifier.getType()).andReturn(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER);
	  EasyMock.expect(coordinatingCenterAssignedIdentifier.getValue()).andReturn("identifier value");
	  scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
	  EasyMock.expect(studySubjectStudyVersion.getScheduledEpoch(epochInCoordCentReturnedScheduledEpoch)).andReturn(scheduledEpoch);
	  studySubjectStudyVersion.setStudySubject(studySubject);

	  replayMocks();

	 studySubject.addStudySubjectStudyVersion(studySubjectStudyVersion);
	 studySubject.doMutiSiteEnrollment(coordCentReturnedScheduledEpoch, coordinatingCenterAssignedIdentifier);
	 assertEquals("Wrong registration status",RegistrationWorkFlowStatus.ENROLLED,studySubject.getRegWorkflowStatus());
	 assertEquals("Wrong identifier value","identifier value",studySubject.getCoOrdinatingCenterIdentifier().getValue());

	 verifyMocks();

  }

  /**
   * Test do multi site transfer.
   *
   * @throws Exception the exception
   */
  public void testDoMultiSiteTransfer() throws Exception{

	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(epoch.getName()).andReturn("Treatment");
	  EasyMock.expect(scheduledEpoch.getRequiresArm()).andReturn(true);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  Arm arm = registerMockFor(Arm.class);
	  ScheduledArm scheduledArm = registerMockFor(ScheduledArm.class);
	  EasyMock.expect(epoch.getArmByName("armA")).andReturn(arm);
	  EasyMock.expect(scheduledEpoch.getScheduledArm()).andReturn(scheduledArm).times(2);
	  scheduledArm.setArm(arm);
	  scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);

	  studySubject.setStudySubjectStudyVersion(studySubjectStudyVersion);

	  ScheduledEpoch coordCentReturnedScheduledEpoch = registerMockFor(ScheduledEpoch.class);
	  Epoch epochInCoordCentReturnedScheduledEpoch = registerMockFor(Epoch.class);
	  ScheduledArm scheduledArmInCoordCentReturnedScheduledEpoch = registerMockFor(ScheduledArm.class);
	  Arm coorCenArm = registerMockFor(Arm.class);

	  EasyMock.expect(coordCentReturnedScheduledEpoch.getEpoch()).andReturn(epochInCoordCentReturnedScheduledEpoch);
	  EasyMock.expect(epochInCoordCentReturnedScheduledEpoch.getName()).andReturn("Treatment");

	  EasyMock.expect(coordCentReturnedScheduledEpoch.getScheduledArm()).andReturn(scheduledArmInCoordCentReturnedScheduledEpoch);
	  EasyMock.expect(scheduledArmInCoordCentReturnedScheduledEpoch.getArm()).andReturn(coorCenArm);
	  EasyMock.expect(coorCenArm.getName()).andReturn("armA");

	  replayMocks();

	  studySubject.doMutiSiteTransfer(coordCentReturnedScheduledEpoch);

	  verifyMocks();

  }

  /**
   * Test do local transfer.
   *
   * @throws Exception the exception
   */
  public void testDoLocalTransfer() throws Exception{
	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(3);
	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	studySubject.setStudySubjectStudyVersion(studySubjectStudyVersion);
	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);
	studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);


	 EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.REGISTERED_BUT_NOT_RANDOMIZED).times(2);
	 scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
	 EasyMock.expect(studySite.getStudy()).andReturn(study);
	 EasyMock.expect(study.getRandomizationType()).andReturn(RandomizationType.CALL_OUT);
	 EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
 	 EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);

	 replayMocks();
	 studySubject.setStudySite(studySite);
	 studySubject.addScheduledEpoch(scheduledEpoch);
	 studySubject.doLocalTransfer();
	 verifyMocks();
  }

  /**
   * Test prepare for transfer1.
   *
   * @throws Exception the exception
   */
  public void testPrepareForTransfer1() throws Exception{
	  try{
		  studySubject.prepareForTransfer();
		  fail("Should have thrown exception");
	  } catch(Exception ex){
		assertTrue("Wrong exception message",ex.getMessage().contains("The subject has to be enrolled before being transferred"));
	  }
  }

  /**
   * Test prepare for transfer2.
   *
   * @throws Exception the exception
   */
  public void testPrepareForTransfer2() throws Exception{
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
	  try{
		  studySubject.prepareForTransfer();
		  fail("Should have thrown exception");
	  } catch(Exception ex){
		assertTrue("Wrong exception message",ex.getMessage().contains("The subject can only be transferred to an Epoch with an or higher order"));
	  }
  }

  /**
   * Test has mandatory companions true.
   *
   * @throws Exception the exception
   */
  public void testHasMandatoryCompanionsTrue() throws Exception{
	  StudySubject childStudySubject = registerMockFor(StudySubject.class);
	  studySubject.addChildStudySubject(childStudySubject);

	  CompanionStudyAssociation compStudyAssociation = registerMockFor(CompanionStudyAssociation.class);
	  List<CompanionStudyAssociation> companionStudyAssociations = new ArrayList<CompanionStudyAssociation>();
	  companionStudyAssociations.add(compStudyAssociation);

	  EasyMock.expect(studySite.getStudy()).andReturn(study);
	  EasyMock.expect(study.getCompanionStudyAssociations()).andReturn(companionStudyAssociations);
	  EasyMock.expect(compStudyAssociation.getMandatoryIndicator()).andReturn(true);
	  EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
  	  EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);

	  replayMocks();
	  studySubject.setStudySite(studySite);
	  assertTrue("Wrong result in evaluating if study subject has mandatory companins", studySubject.hasMandatoryCompanions());
	  verifyMocks();
  }

  /**
   * Test has mandatory companions false.
   *
   * @throws Exception the exception
   */
  public void testHasMandatoryCompanionsFalse() throws Exception{
	  StudySubject childStudySubject = registerMockFor(StudySubject.class);
	  studySubject.addChildStudySubject(childStudySubject);

	  CompanionStudyAssociation compStudyAssociation = registerMockFor(CompanionStudyAssociation.class);

	  List<CompanionStudyAssociation> companionStudyAssociations = new ArrayList<CompanionStudyAssociation>();
	  companionStudyAssociations.add(compStudyAssociation);

	  EasyMock.expect(studySite.getStudy()).andReturn(study);
	  EasyMock.expect(study.getCompanionStudyAssociations()).andReturn(companionStudyAssociations);

	  EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
  	  EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);

	  EasyMock.expect(compStudyAssociation.getMandatoryIndicator()).andReturn(false);

	  replayMocks();
	  studySubject.setStudySite(studySite);
	  assertFalse("Wrong result in evaluating if study subject has mandatory companins", studySubject.hasMandatoryCompanions());
	  verifyMocks();
  }
}
