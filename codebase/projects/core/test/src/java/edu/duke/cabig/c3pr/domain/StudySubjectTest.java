package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.NotificationEmailSubstitutionVariablesEnum;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRInvalidDataEntryException;
import edu.duke.cabig.c3pr.utils.DateUtil;
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
		studySubject.addScheduledEpoch(scheduledEpoch);
		studySubject.setStudySite(studySite);
		participant = registerMockFor(Participant.class);
		studySubject.setParticipant(participant);
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
    	studySubject.getScheduledEpochs().remove(0);
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
    	studySubject.getScheduledEpochs().remove(0);
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
    	studySubject.getScheduledEpochs().remove(0);
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
    	studySubject.getScheduledEpochs().remove(0);
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
    	studySubject.getScheduledEpochs().remove(0);
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
    	// remove the mock scheduled epoch first so that we don't have to expect on equals of the mock epoch object
    	studySubject.getScheduledEpochs().remove(0);
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
    	// remove the mock scheduled epoch first so that we don't have to expect on equals of the mock epoch object
    	studySubject.getScheduledEpochs().remove(0);
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
    	// remove the mock scheduled epoch first so that we don't have to expect on equals of the mock epoch object
    	studySubject.getScheduledEpochs().remove(0);
        StudySubject studySubject = new StudySubject();
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
    	// remove the mock scheduled epoch first so that we don't have to expect on equals of the mock epoch object
    	studySubject.getScheduledEpochs().remove(0);
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
    public void testEquals() throws Exception{
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
     * Test get informed consent signed date str.
     * 
     * @throws Exception the exception
     */
    public void testGetInformedConsentSignedDateStr() throws Exception{
    	assertEquals("Unexpected Informed Consent Signed Date","",studySubject.getInformedConsentSignedDateStr());
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
    	assertNotSame("Unexpected start date","",studySubject.getStartDateStr());
    	
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
		orgIdentifier1.setType("MRN");
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
    public void testEvaluateRegistrationDataEntryStatus() throws Exception{
    	StudySubject childStudySubject = registerMockFor(StudySubject.class);
    	studySubject.addChildStudySubject(childStudySubject);
    	EasyMock.expect(childStudySubject.evaluateRegistrationDataEntryStatus()).andReturn(RegistrationDataEntryStatus.COMPLETE);
    	
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(true);
    	EasyMock.expect(study.getCompanionStudyAssociations()).andReturn(new ArrayList<CompanionStudyAssociation>());
    	EasyMock.expect(childStudySubject.getDataEntryStatus()).andReturn(false);
    	
    	replayMocks();
    	
    	assertEquals("Wrong registration status",RegistrationDataEntryStatus.INCOMPLETE,studySubject.evaluateRegistrationDataEntryStatus());
    	
    	verifyMocks();
    	
    }
    
    /**
     * Test evaluate registration data entry status with errors.
     * 
     * @throws Exception the exception
     */
    public void testEvaluateRegistrationDataEntryStatusWithErrors() throws Exception{
    	
    	List<Error> errors = new ArrayList<Error>();
    	StudySubject childStudySubject = registerMockFor(StudySubject.class);
    	studySubject.addChildStudySubject(childStudySubject);
    	childStudySubject.evaluateRegistrationDataEntryStatus(errors);
    	
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(true);
    	EasyMock.expect(study.getCompanionStudyAssociations()).andReturn(new ArrayList<CompanionStudyAssociation>());
    	EasyMock.expect(childStudySubject.getDataEntryStatus()).andReturn(false);
    	
    	replayMocks();
    	
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
    	EasyMock.expect(localHealthcareSite.getNciInstituteCode()).andReturn("NCI100");
    	
    	replayMocks();
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
    	assertTrue("Expected data entry to be complete",studySubject.isDataEntryComplete());
    	
    	verifyMocks();
    	
    	resetMocks();
    	
    	EasyMock.expect(scheduledEpoch.getScEpochDataEntryStatus()).andReturn(ScheduledEpochDataEntryStatus.INCOMPLETE);
    	replayMocks();
    	assertFalse("Expected data entry to be incomplete",studySubject.isDataEntryComplete());
    	
    	verifyMocks();
    }
    
    /**
     * Test ready for randomization.
     * 
     * @throws Exception the exception
     */
    public void testReadyForRandomization() throws Exception{
    	studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
    	EasyMock.expect(scheduledEpoch.getScEpochDataEntryStatus()).andReturn(ScheduledEpochDataEntryStatus.COMPLETE);
    	replayMocks();
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
    	
    	replayMocks();
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
    	EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
    	EasyMock.expect(study.getShortTitleText()).andReturn("Short_title_text").times(2);
    	EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
    	EasyMock.expect(study.getId()).andReturn(1).times(2);
    	EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
    	EasyMock.expect(study.getTargetAccrualNumber()).andReturn(5).times(2);
    	EasyMock.expect(studySite.getTargetAccrualNumber()).andReturn(4).times(2);
    	EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
    	EasyMock.expect(study.getCurrentAccrualCount()).andReturn(3).times(2);
    	EasyMock.expect(studySite.getCurrentAccrualCount()).andReturn(2);
    	
    	replayMocks();
    	Map<Object, Object> map = studySubject.buildMapForNotification();
    	assertEquals("Wrong number of entries in the notificaiton map",8,map.size());
    	
    	assertEquals("Wrong entry in map","mrnValue",map.get(NotificationEmailSubstitutionVariablesEnum.PARTICIPANT_MRN
				.toString()));
    	assertEquals("Wrong entry in map","Short_title_text",map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SHORT_TITLE
				.toString()));
    	assertEquals("Wrong entry in map",5,map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_ACCRUAL_THRESHOLD
				.toString()));
    	assertEquals("Wrong entry in map",4,map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_ACCRUAL_THRESHOLD
				.toString()));
    	assertEquals("Wrong entry in map",3,map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_CURRENT_ACCRUAL
				.toString()));
    	assertEquals("Wrong entry in map",2,map.get(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_CURRENT_ACCRUAL
				.toString()));
    	
    	verifyMocks();
    }
    
    
    /**
     * Test create scheduled epoch.
     * 
     * @throws Exception the exception
     */
    public void testCreateScheduledEpoch() throws Exception{
    	ScheduledEpoch scheduledEpoch1 = studySubject.createScheduledEpoch(Epoch.createEpoch("treatment epoch"));
    	assertNotNull("Error in creating scheduled epoch",scheduledEpoch1);
    	assertEquals("Scheduled epoch created with wrong epoch name","treatment epoch",scheduledEpoch1.getEpoch().getName());
    }
    
    /**
     * Test if scheduled epoch created for this epoch.
     * 
     * @throws Exception the exception
     */
    public void testIfScheduledEpochCreatedForThisEpoch() throws Exception{
    	// remove the mock scheduled epoch first so that we don't have to expect on equals of the mock epoch object
    	
    	assertFalse("Unexpected scheduled epoch",studySubject.ifScheduledEpochCreatedForThisEpoch(new Epoch()));
    	studySubject.getScheduledEpochs().remove(0);
    	Epoch epoch1 = Epoch.createEpoch("treatment epoch");
    	studySubject.addScheduledEpoch(studySubject.createScheduledEpoch(epoch1));
    	
    	assertTrue("Scheduled epoch was expected with given epoch:" + epoch1.getName(),studySubject.ifScheduledEpochCreatedForThisEpoch(epoch1));
    }
    
    /**
     * Test get matching scheduled epoch.
     * 
     * @throws Exception the exception
     */
    public void testGetMatchingScheduledEpoch() throws Exception{
    	// remove the mock scheduled epoch first so that we don't have to expect on equals of the mock epoch object
    	studySubject.getScheduledEpochs().remove(0);
    	Epoch epoch1 = Epoch.createEpoch("treatment epoch");
    	assertNull("Unexpected scheduled epoch",studySubject.getMatchingScheduledEpoch(epoch1));
    	studySubject.addScheduledEpoch(studySubject.createScheduledEpoch(epoch1));
    	
    	assertNotNull("Scheduled epoch was expected with given epoch:", studySubject.getMatchingScheduledEpoch(epoch1));
    }
    
    /**
     * Test can reserve.
     * 
     * @throws Exception the exception
     */
    public void testCanReserve() throws Exception{
    
	    List<Error> errors = new ArrayList<Error>();
		EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);
		EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.INCOMPLETE);
		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.INCOMPLETE);
		replayMocks();
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
    	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.REGISTERED);
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getName()).andReturn("treatment");
    	replayMocks();
    	
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
    	
    	StudySubject parentStudySubject = registerMockFor(StudySubject.class);
    	studySubject.setParentStudySubject(parentStudySubject);
    	studySubject.setInformedConsentSignedDate(new Date());
    	studySubject.setInformedConsentVersion("1");
    	
    	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING);
 		EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.COMPLETE);
 		
 		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
    	
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getRandomizedIndicator()).andReturn(false);
    	
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
 		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);
 		
 		scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
 		scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
    	
    	replayMocks();
    	
    	studySubject.register();
    	verifyMocks();
    }
    
    /**
     * Test register3.
     * 
     * @throws Exception the exception
     */
    public void testRegister3() throws Exception{
    	
    	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING);
 		EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.INCOMPLETE);
 		
 		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.INCOMPLETE);
    	
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
 		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);
 		
    	replayMocks();
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
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getReservationIndicator()).andReturn(false);
    	replayMocks();
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
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getReservationIndicator()).andReturn(true);
    	
    	List<Error> errors = new ArrayList<Error>();
		EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);
		EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.INCOMPLETE);
		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.INCOMPLETE);
    	
    	replayMocks();
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
    	EasyMock.expect(scheduledEpoch.getScheduledArm()).andReturn(null);
    	replayMocks();
    	assertFalse("Wrong randomization status on scheduled epoch",studySubject.isRandomizedOnScheduledEpoch());
    	verifyMocks();
    }
    
    /*public void testDoBookRandomization() throws Exception{
    	
    	StratumGroup stratumGroup = registerMockFor(StratumGroup.class);
    	Arm arm = registerMockFor(Arm.class);
    	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.REGISTERED_BUT_NOT_RANDOMIZED).times(2);
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getStratificationIndicator()).andReturn(true);
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(study.getRandomizationType()).andReturn(RandomizationType.BOOK);
    	EasyMock.expect(scheduledEpoch.getStratumGroup()).andReturn(stratumGroup);
    	EasyMock.expect(stratumGroup.getNextArm()).andReturn(arm);
    	
    	scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
    	replayMocks();
    	studySubject.doLocalEnrollment();
    	assertEquals("Wrong registration status",RegistrationWorkFlowStatus.ENROLLED,studySubject.getRegWorkflowStatus());
    	verifyMocks();
    }*/
    
}
