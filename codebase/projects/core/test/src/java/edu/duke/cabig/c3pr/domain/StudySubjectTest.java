/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.ConsentRequired;
import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.EpochType;
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
	
	StudyOrganization studyOrganization;
	
	StudySubjectDemographics studySubjectDemographics;

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
	
	StudySubjectConsentVersion studySubjectConsentVersion;
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		study = registerMockFor(Study.class);
		epoch = registerMockFor(Epoch.class);
		studySite = registerMockFor(StudySite.class);
		studyOrganization = registerMockFor(StudyOrganization.class);
		scheduledEpoch = registerMockFor(ScheduledEpoch.class);
		studySubject = new StudySubject();
		participant = registerMockFor(Participant.class);
		studySubjectDemographics = registerMockFor(StudySubjectDemographics.class);
		studySubject.setStudySubjectDemographics(studySubjectDemographics);
		c3prExceptionHelper = registerMockFor(C3PRExceptionHelper.class);
		studySiteStudyVersion = registerMockFor(StudySiteStudyVersion.class);
		studySubjectStudyVersion = registerMockFor(StudySubjectStudyVersion.class);
		studyVersion = registerMockFor(StudyVersion.class);
		studySubjectConsentVersion = registerMockFor(StudySubjectConsentVersion.class);
		
	}

    /** The study subject creator helper. */
    private StudySubjectCreatorHelper studySubjectCreatorHelper=new StudySubjectCreatorHelper();

    /**
     * Registration Data Entry Status test blank Study Subject.
     *
     * @throws Exception the exception
     */
    public void testEvaluateRegistrationDataEntryStatusComplete() throws Exception {
    	studySubjectStudyVersion.setStudySubject(studySubject);

    	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(study.getConsentRequired()).andReturn(ConsentRequired.AS_MARKED_BELOW);
    	
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch);
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);
    	
    	List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
    	EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
    	
    	replayMocks();
    	studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
        assertEquals("Wrong Registration Data Entry Status",
                        RegistrationDataEntryStatus.COMPLETE, studySubject.evaluateRegistrationDataEntryStatus());
        
        verifyMocks();
    }

    /**
     * Registration Data Entry Status test InformedConsent Date Filled InformedConsent Version
     * Filled.
     */
    public void testEvaluateRegistrationDataEntryStatusComplete1() {
    	studySubjectStudyVersion.setStudySubject(studySubject);
    	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(study.getConsentRequired()).andReturn(ConsentRequired.AS_MARKED_BELOW);
    	
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch);
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);
    	Consent consent = registerMockFor(Consent.class);
    	List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
    	EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
    	
    	replayMocks();
    	
    	studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
        assertEquals("Wrong Registration Data Entry Status", RegistrationDataEntryStatus.COMPLETE,
                        studySubject.evaluateRegistrationDataEntryStatus());
        verifyMocks();
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
        
        Study study1 = new LocalStudy();
        study1.setMultiInstitutionIndicator(true);
    	StudySite studySite1 = new StudySite();
    	study1.addStudySite(studySite1);
    	
        studySubject.setStudySite(studySite1);
        assertEquals("Wrong requiresCoordinatingCenterApproval return", true,studySubject.requiresCoordinatingCenterApproval());
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
        Study study1 = new LocalStudy();
        study1.setMultiInstitutionIndicator(true);
    	StudySite studySite1 = new StudySite();
    	study1.addStudySite(studySite1);
    	
        studySubject.setStudySite(studySite1);
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
        Study study1 = new LocalStudy();
        study1.setMultiInstitutionIndicator(false);
    	StudySite studySite1 = new StudySite();
    	study1.addStudySite(studySite1);
    	
        studySubject.setStudySite(studySite1);
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

    	assertNotNull("Identifiers is null, may be not initialized", studySubject.getIdentifiers());

    }

    /**
     * Test equals.
     *
     * @throws Exception the exception
     */
    public void testEquals1() throws Exception{
    	StudySubject studySubject1 = new StudySubject();
    	StudySubject studySubject2 = new StudySubject();
    	
    	Study study1 = new LocalStudy();
    	StudySite studySite1 = new StudySite();
    	
    	study1.addStudySite(studySite1);
    	
    	studySubject1.setStudySite(studySite1);
    	studySubject2.setStudySite(studySite1);

    	Participant participant1 = new Participant();
    	participant1.setId(1);
    	studySubject1.setStudySubjectDemographics(participant1.createStudySubjectDemographics());
    	studySubject2.setStudySubjectDemographics(participant1.createStudySubjectDemographics());
    	Date startDate = new Date();
    	studySubject1.setStartDate(startDate);
    	studySubject2.setStartDate(startDate);
    	assertFalse("The two study subjects should not have been equal",studySubject1.equals(null));
    	assertTrue("The two study subjects should have been equal",studySubject1.equals(studySubject2));

    	// Creating a new Participant with different Id and setting the second study subject to this participant
    	Participant participant2 = new Participant();
    	participant2.setId(2);
    	studySubject2.setStudySubjectDemographics(participant2.createStudySubjectDemographics());
    	assertFalse("The two study subjects should not have been equal",studySubject1.equals(studySubject2));
    }

    /**
     * Test equals2.
     *
     * @throws Exception the exception
     */
    public void testEquals2() throws Exception{
    	Participant participant = new Participant();
    	StudySubject studySubject1 = new StudySubject();
    	studySubject1.setStudySubjectDemographics(participant.createStudySubjectDemographics());
    	assertFalse("Should not have been equal",studySubject1.equals(new StudySite()));
    	
    	
    	StudySubject studySubject2 = new StudySubject();
    	studySubject2.setStudySubjectDemographics(participant.createStudySubjectDemographics());
  
    	Study study1 = new LocalStudy();
    	StudySite studySite1 = new StudySite();
    	
    	study1.addStudySite(studySite1);

    	studySubject1.setStudySite(studySite1);
    	studySubject2.setStudySite(studySite1);

    	Date startDate = new Date();
    	studySubject1.setStartDate(null);
    	studySubject2.setStartDate(startDate);
    	
    	
    	assertFalse("Study subject should not be equal to null",studySubject1.equals(null));

    	studySubject1.setStartDate(startDate);
    	
    	
    	
    	assertTrue("The two study subjects should have been equal",studySubject1.equals(studySubject2));

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

    	Study study1 = new LocalStudy();
    	StudySite studySite1 = new StudySite();
    	
    	study1.addStudySite(studySite1);
    	
    	studySubject1.setStudySite(null);
    	studySubject2.setStudySite(studySite1);

    	assertFalse("The two study subjects should not have been equal",studySubject1.equals(null));

    	StudySite studySite2 = new StudySite();
    	study1.addStudySite(studySite2);
    	
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
    	int prime = 29;
    	StudySubject studySubject1 = new StudySubject();
    	Participant participant = new Participant();
    	studySubject1.setStudySubjectDemographics(participant.createStudySubjectDemographics());

    	assertEquals("Wrong hash code",prime*prime + participant.hashCode(), studySubject1.hashCode());

    	Study study1 = new LocalStudy();
    	StudySite studySite1 = new StudySite();
    	
    	study1.addStudySite(studySite1);

    	studySubject1.setStudySite(studySite1);
    	assertEquals("Wrong hash code",(prime*(prime+studySite1.hashCode())) + participant.hashCode(), studySubject1.hashCode());
    }

    /**
     * Test informed consent signed date str.
     *
     * @throws Exception the exception
     */
    public void testGetInformedConsentSignedDateStr() throws Exception{
    	StudySubject studySubject1 = new StudySubject();
    	assertEquals("Unexpected consent signed date",null, studySubject1.getStudySubjectStudyVersion()
    	 		.getStudySubjectConsentVersions().get(0).getInformedConsentSignedDateStr());
    	Date informedConsentSignedDate = new Date();
    	 studySubject1.getStudySubjectStudyVersion()
 		.getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(informedConsentSignedDate);
    	assertEquals("Wrong consent date",DateUtil.formatDate(informedConsentSignedDate,"MM/dd/yyyy"), studySubject1.getStudySubjectStudyVersion()
    	 		.getStudySubjectConsentVersions().get(0).getInformedConsentSignedDateStr());
    }

    /**
     * Test get off study date.
     *
     * @throws Exception the exception
     */
    public void testGetOffStudyDateNull() throws Exception{
    	assertNull(studySubject.getOffStudyDate());
    }
    
    /**
     * Test get off study date.
     *
     * @throws Exception the exception
     */
    public void testGetOffStudyDateNotNull() throws Exception{
    	Date date = new Date();
    	studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.OFF_STUDY);
    	EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(scheduledEpoch.getOffEpochDate()).andReturn(date);
  	  	replayMocks();
  	  	studySubject.setStudySite(studySite);
  	  	studySubject.addScheduledEpoch(scheduledEpoch);
    	assertEquals(date, studySubject.getOffStudyDate());
    }
    /**
     * Test get off study date.
     *
     * @throws Exception the exception
     */
    public void testGetOffStudyDateStrNull() throws Exception{
    	assertEquals("",studySubject.getOffStudyDateStr());
    }
    
    /**
     * Test get off study date.
     *
     * @throws Exception the exception
     */
    public void testGetOffStudyDateStrNotNull() throws Exception{
    	Date date = new Date();
    	studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.OFF_STUDY);
    	EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(scheduledEpoch.getOffEpochDate()).andReturn(date);
  	  	replayMocks();
  	  	studySubject.setStudySite(studySite);
  	  	studySubject.addScheduledEpoch(scheduledEpoch);
  	  	assertEquals("Wrong off study date or in wrong format",DateUtil.formatDate(date, "MM/dd/yyyy"),studySubject.getOffStudyDateStr());
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
    	studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);

    	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(study.getConsentRequired()).andReturn(ConsentRequired.AS_MARKED_BELOW);

		CompanionStudyAssociation companionStudyAssociation = registerMockFor(CompanionStudyAssociation.class);
		ArrayList<CompanionStudyAssociation> companionStudyAssociations = new ArrayList<CompanionStudyAssociation>();
		companionStudyAssociations.add(companionStudyAssociation);
		EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);
		StudySubjectConsentVersion studySubjectConsentVersion = registerMockFor(StudySubjectConsentVersion.class);
		List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
		studySubjectConsentVersions.add(studySubjectConsentVersion);
		EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
		
		EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch);
		Consent consent = registerMockFor(Consent.class);
		EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent).times(2);
		EasyMock.expect(consent.getMandatoryIndicator()).andReturn(true);
		EasyMock.expect(consent.getName()).andReturn("General");
		EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDateStr()).andReturn(null);
		
		replayMocks();
		List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		studySubjectStudyVersions.add(studySubjectStudyVersion);
		studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);

		studySubject.setStudySite(studySite);
		

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
    	//EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
    	EasyMock.expect(studySiteStudyVersion.getStudyVersion()).andReturn(studyVersion);
    	
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(study.getConsentRequired()).andReturn(ConsentRequired.AS_MARKED_BELOW);


		EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch).times(1);
		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(true).times(1);
		//EasyMock.expect(studySite.getStudy()).andReturn(study);

		CompanionStudyAssociation companionStudyAssociation = registerMockFor(CompanionStudyAssociation.class);
		ArrayList<CompanionStudyAssociation> companionStudyAssociations = new ArrayList<CompanionStudyAssociation>();
		companionStudyAssociations.add(companionStudyAssociation);

		//EasyMock.expect(study.getCompanionStudyAssociations()).andReturn(companionStudyAssociations).times(1);
		EasyMock.expect(studyVersion.getCompanionStudyAssociations()).andReturn(companionStudyAssociations).times(1);
		EasyMock.expect(companionStudyAssociation.getMandatoryIndicator()).andReturn(true).times(1);

    	replayMocks();

    	studySubject.setStudySite(studySite);
		studySubject.addScheduledEpoch(scheduledEpoch);

    	studySubject.evaluateRegistrationDataEntryStatus(errors);
    	assertEquals("Wrong number of errors",1,errors.size());
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
    	studySubjectStudyVersion.setStudySubject(studySubject);
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(1);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

    	studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
    	EasyMock.expect(scheduledEpoch.getScEpochDataEntryStatus()).andReturn(ScheduledEpochDataEntryStatus.COMPLETE);
    	replayMocks();
    	studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
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
    	EasyMock.expect(studySubjectDemographics.getMRN()).andReturn(mrn).times(2);
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

    /**
     * Test can reserve.
     *
     * @throws Exception the exception
     */
    public void testCanReserve() throws Exception{
    	studySubjectStudyVersion.setStudySubject(studySubject);
    	
    	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(study.getConsentRequired()).andReturn(ConsentRequired.AS_MARKED_BELOW);
    	
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(3);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);
    	List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
    	studySubjectConsentVersions.add(studySubjectConsentVersion);
    	EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
    	Consent consent = registerMockFor(Consent.class);
    	EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent);
    	EasyMock.expect(consent.getMandatoryIndicator()).andReturn(true);
    	EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDateStr()).andReturn("02/11/1912").times(1);

	    List<Error> errors = new ArrayList<Error>();
		EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);
		EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.INCOMPLETE);
		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.INCOMPLETE);
		replayMocks();
		studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
		studySubject.addScheduledEpoch(scheduledEpoch);
		errors = studySubject.canReserve();

		assertEquals("Expected errors",0,errors.size());
		verifyMocks();
    }

    /**
     * Test register1.
     *
     * @throws Exception the exception
     */
    public void testRegister1() throws Exception{
    	studySubjectStudyVersion.setStudySubject(studySubject);
        EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(1);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

    	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.ON_EPOCH);
    	EasyMock.expect(epoch.getName()).andReturn("treatment");

    	replayMocks();
    	studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
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
    	studySubjectStudyVersion.setStudySubject(studySubject);
    	
    	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(study.getConsentRequired()).andReturn(ConsentRequired.AS_MARKED_BELOW);
     	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(4);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

    	StudySubject parentStudySubject = registerMockFor(StudySubject.class);
    	studySubject.setParentStudySubject(parentStudySubject);
    	Consent consent = registerMockFor(Consent.class);
    	
    	List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
    	studySubjectConsentVersions.add(studySubjectConsentVersion);
    	EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
    	EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent);
    	EasyMock.expect(consent.getMandatoryIndicator()).andReturn(true);
    	EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDateStr()).andReturn("01/11/1980");

    	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
 		EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.COMPLETE);

 		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);

    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getRandomizedIndicator()).andReturn(false);

 		EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false);

 		scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);
 		scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);

    	replayMocks();
    	studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
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
    	studySubjectStudyVersion.setStudySubject(studySubject);
    	
    	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(study.getConsentRequired()).andReturn(ConsentRequired.AS_MARKED_BELOW);
    	
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch).times(1);
    	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
 		EasyMock.expect(epoch.getRandomizedIndicator()).andReturn(false);
    	EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.COMPLETE);
 		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
 		
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(4);
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false).times(2);
    	
    	Consent consent = registerMockFor(Consent.class);
    	List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
    	studySubjectConsentVersions.add(studySubjectConsentVersion);
    	EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
    	EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent);
    	EasyMock.expect(consent.getMandatoryIndicator()).andReturn(true);
    	EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDateStr()).andReturn("01/11/1980");
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);
    	scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);
    	replayMocks();
    	studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
		studySubject.addScheduledEpoch(scheduledEpoch);
		studySubject.register();
		
    	verifyMocks();
    }

    /**
     * Test reserve1.
     *
     * @throws Exception the exception
     */
    public void testReserve1() throws Exception{
    	studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING_ON_STUDY);
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
    	studySubjectStudyVersion.setStudySubject(studySubject);
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(1);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getType()).andReturn(EpochType.SCREENING);
    	replayMocks();
    	studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
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
    	studySubjectStudyVersion.setStudySubject(studySubject);
    	
    	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(study.getConsentRequired()).andReturn(ConsentRequired.AS_MARKED_BELOW);
    	
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch).times(1);
 		EasyMock.expect(epoch.getType()).andReturn(EpochType.RESERVING);
    	EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.COMPLETE);
 		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(4);
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false).times(1);
    	
    	
    	Consent consent = registerMockFor(Consent.class);
    	List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
    	studySubjectConsentVersions.add(studySubjectConsentVersion);
    	EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
    	EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent);
    	EasyMock.expect(consent.getMandatoryIndicator()).andReturn(true);
    	EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDateStr()).andReturn("01/11/1980");
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);
    	scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);
    	replayMocks();
    	studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubject.addScheduledEpoch(scheduledEpoch);
    	studySubject.reserve();
    	verifyMocks();
    }

    /**
     * Test reserve4.
     *
     * @throws Exception the exception
     */
    public void testReserve4() throws Exception{
    	studySubjectStudyVersion.setStudySubject(studySubject);
    	
    	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(study.getConsentRequired()).andReturn(ConsentRequired.AS_MARKED_BELOW);
    	
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch).times(1);
 		EasyMock.expect(epoch.getType()).andReturn(EpochType.RESERVING);
    	EasyMock.expect(scheduledEpoch.evaluateScheduledEpochDataEntryStatus((List<Error>)EasyMock.anyObject())).andReturn(ScheduledEpochDataEntryStatus.COMPLETE);
 		scheduledEpoch.setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
 		
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(4);
    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(false).times(1);
    	
    	
    	Consent consent = registerMockFor(Consent.class);
    	List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
    	studySubjectConsentVersions.add(studySubjectConsentVersion);
    	EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
    	EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent);
    	EasyMock.expect(consent.getMandatoryIndicator()).andReturn(true);
    	EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDateStr()).andReturn("01/11/1980");
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);
    	scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);

    	replayMocks();
    	studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubject.addScheduledEpoch(scheduledEpoch);
    	studySubject.reserve();
    	verifyMocks();
    }

    /**
     * Test is randomized on scheduled epoch.
     *
     * @throws Exception the exception
     */
    public void testIsRandomizedOnScheduledEpoch() throws Exception{
    	
    	studySubjectStudyVersion.setStudySubject(studySubject);
    	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(1);
    	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);

    	EasyMock.expect(scheduledEpoch.getScheduledArm()).andReturn(null);
    	replayMocks();
    	studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
    	studySubject.addScheduledEpoch(scheduledEpoch);
    	assertFalse("Wrong randomization status on scheduled epoch",studySubject.isRandomizedOnScheduledEpoch());
    	verifyMocks();
    }

    /**
     * Test do book randomization no arm available.
     *
     * @throws Exception the exception
     */
	public void testDoBookRandomizationNoArmAvailable() throws Exception {
		EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);

    	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    	EasyMock.expect(epoch.getStratificationIndicator()).andReturn(true);

    	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_RANDOMIZATION_ON_EPOCH).times(1);

    	EasyMock.expect(studySite.getStudy()).andReturn(study);
    	EasyMock.expect(study.getRandomizationType()).andReturn(RandomizationType.BOOK);

    	StratumGroup stratumGroup = registerMockFor(StratumGroup.class);
    	int code =  studySubject.getCode("C3PR.EXCEPTION.REGISTRATION.NO.ARM.AVAILABLE.BOOK.EXHAUSTED.CODE");
    	EasyMock.expect(scheduledEpoch.getStratumGroup()).andReturn(stratumGroup);
    	EasyMock.expect(stratumGroup.getNextArm()).andThrow(new C3PRCodedRuntimeException(code, "next arm not available"));

    	replayMocks();
		try {
			studySubject.addScheduledEpoch(scheduledEpoch);
			studySubject.setStudySite(studySite);
			studySubject.doLocalEnrollment();
			fail("Should have thrown exception");
		} catch (C3PRBaseRuntimeException ex) {

		}
		assertEquals("Wrong registration status", RegistrationWorkFlowStatus.PENDING, studySubject.getRegWorkflowStatus());
		verifyMocks();
	}

  /**
   * Test do phone call randomization.
   *
   * @throws Exception the exception
   */
  public void testDoPhoneCallRandomization() throws Exception{
	EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
    EasyMock.expect(epoch.getName()).andReturn("treating epoch");
	EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
	EasyMock.expect(study.getRandomizationType()).andReturn(RandomizationType.PHONE_CALL);
	EasyMock.expect(scheduledEpoch.getScheduledArm()).andReturn(null);
	EasyMock.expect(study.getBlindedIndicator()).andReturn(false);

	EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_RANDOMIZATION_ON_EPOCH).times(1);

  	EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(1);
	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(2);

	replayMocks();
	try {
		studySubject.addScheduledEpoch(scheduledEpoch);
		studySubject.setStudySite(studySite);
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
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING_ON_STUDY);
	  try{
		  studySubject.takeSubjectOffStudy(new ArrayList<OffEpochReason>(), new Date());
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
	  Date offStudyDate = new Date();
	  List<OffEpochReason> offStudyReasons = new ArrayList<OffEpochReason>();
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
	  EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
  	  scheduledEpoch.takeSubjectOffEpoch(offStudyReasons, offStudyDate);
	  replayMocks();
	  studySubject.setStudySite(studySite);
	  studySubject.addScheduledEpoch(scheduledEpoch);
	  studySubject.takeSubjectOffStudy(offStudyReasons, offStudyDate);

	  assertTrue("Unable to take study subject off study",studySubject.getRegWorkflowStatus()==RegistrationWorkFlowStatus.OFF_STUDY);
	  verifyMocks();
  }

  /**
   * Test transfer fail when not enrolled.
   *
   * @throws Exception the exception
   */
  public void testTransferFailWhenNotEnrolled() throws Exception{
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING_ON_STUDY);
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
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
	  try{
		  studySubject.prepareForTransfer();
		  fail("Transfer should have failed");
	  } catch(C3PRBaseRuntimeException ex){
	  }
  }

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
		  studySubject.addScheduledEpoch(scheduledEpoch);
		  studySubject.prepareForEnrollment();
		  fail("Should have thrown exception");
	  } catch(Exception ex){
		  assertEquals("Wrong exception throws", true,  ex instanceof C3PRBaseRuntimeException);
	  }
	  verifyMocks();
  }

  /**
   * Test prepare for enrollment2.
   *
   * @throws Exception the exception
   */
	public void testPrepareForEnrollment2() throws Exception {
    	EasyMock.expect(study.getStandaloneIndicator()).andReturn(false);
		EasyMock.expect(studySite.getStudy()).andReturn(study);
	    EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	    EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(true);
		EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	    EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
    	EasyMock.expect(studySiteStudyVersion.getStudyVersion()).andReturn(studyVersion);

		List<CompanionStudyAssociation> compStudyAssociations = new ArrayList<CompanionStudyAssociation>();
		CompanionStudyAssociation compStudyAssociation = registerMockFor(CompanionStudyAssociation.class);
		compStudyAssociations.add(compStudyAssociation);

		EasyMock.expect(studyVersion.getCompanionStudyAssociations()).andReturn(compStudyAssociations).times(1);
		EasyMock.expect(compStudyAssociation.getMandatoryIndicator()).andReturn(true);

		replayMocks();
		try {
			studySubject.setStudySite(studySite);
			studySubject.addScheduledEpoch(scheduledEpoch);
			studySubject.prepareForEnrollment();
			fail("Should have thrown exception");
		} catch (Exception ex) {
			assertEquals("Wrong exception throws", true,  ex instanceof C3PRBaseRuntimeException);
		}

		verifyMocks();
	}

  /**
   * Test prepare for enrollment2.
   *
   * @throws Exception the exception
   */
  public void testPrepareForEnrollment3() throws Exception{
	  EasyMock.expect(studySite.getStudy()).andReturn(study);
	  EasyMock.expect(study.getStandaloneIndicator()).andReturn(false);

	  List<CompanionStudyAssociation> compStudyAssociations = new ArrayList<CompanionStudyAssociation>();
	  CompanionStudyAssociation compStudyAssociation = registerMockFor(CompanionStudyAssociation.class);
	  compStudyAssociations.add(compStudyAssociation);

	  EasyMock.expect(compStudyAssociation.getMandatoryIndicator()).andReturn(true);

	  EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	  EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
	  EasyMock.expect(studySiteStudyVersion.getStudyVersion()).andReturn(studyVersion);

	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(epoch.getEnrollmentIndicator()).andReturn(true);

	  EasyMock.expect(studyVersion.getCompanionStudyAssociations()).andReturn(compStudyAssociations).times(1);

	  replayMocks();
	  try{
		  studySubject.setStudySite(studySite);
		  studySubject.addScheduledEpoch(scheduledEpoch);
		  studySubject.prepareForEnrollment();
		  fail("Should have thrown exception");
	  } catch(Exception ex){
		  assertEquals("Wrong exception throws", true,  ex instanceof C3PRBaseRuntimeException);
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
   * Test do local enrollment when already registered on scheduled epoch.
   */
	public void testDoLocalEnrollmentWhenAlreadyRegisteredOnScheduledEpoch() {
		EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(
				ScheduledEpochWorkFlowStatus.ON_EPOCH);
		EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(
				studySiteStudyVersion);
		assertEquals("Wrong initial registration status",
				RegistrationWorkFlowStatus.PENDING, studySubject
						.getRegWorkflowStatus());

		replayMocks();
		studySubject.addScheduledEpoch(scheduledEpoch);
		studySubject.setStudySite(studySite);
		studySubject.doLocalEnrollment();
		assertEquals("Wrong registration status",
				RegistrationWorkFlowStatus.ON_STUDY, studySubject
						.getRegWorkflowStatus());

		verifyMocks();
	}

  /**
   * Test do multi site enrollment.
   *
   * @throws Exception the exception
   */
  public void testDoMultiSiteEnrollment() throws Exception{
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);

	  EasyMock.expect(scheduledEpoch.getRequiresArm()).andReturn(true);
	  Arm arm = registerMockFor(Arm.class);
	  ScheduledArm scheduledArm = registerMockFor(ScheduledArm.class);
	  EasyMock.expect(epoch.getArmByName("armA")).andReturn(arm);

	  ScheduledEpoch coordCentReturnedScheduledEpoch = registerMockFor(ScheduledEpoch.class);
	  Epoch epochInCoordCentReturnedScheduledEpoch = registerMockFor(Epoch.class);
	  ScheduledArm scheduledArmInCoordCentReturnedScheduledEpoch = registerMockFor(ScheduledArm.class);
	  Arm coorCenArm = registerMockFor(Arm.class);

	  EasyMock.expect(coordCentReturnedScheduledEpoch.getScheduledArm()).andReturn(scheduledArmInCoordCentReturnedScheduledEpoch);
	  EasyMock.expect(scheduledArmInCoordCentReturnedScheduledEpoch.getArm()).andReturn(coorCenArm);
	  EasyMock.expect(coorCenArm.getName()).andReturn("armA");

	  EasyMock.expect(scheduledEpoch.getScheduledArm()).andReturn(scheduledArm).times(2);
	  scheduledArm.setArm(arm);

	  EasyMock.expect(coordCentReturnedScheduledEpoch.getEpoch()).andReturn(epochInCoordCentReturnedScheduledEpoch);

	  StudyCoordinatingCenter studyCoordCetner = registerMockFor(StudyCoordinatingCenter.class);
	  EasyMock.expect(study.getStudyCoordinatingCenter()).andReturn(studyCoordCetner);
	  HealthcareSite coordCenterOrganization = registerMockFor(HealthcareSite.class);

	  OrganizationAssignedIdentifier coordinatingCenterAssignedIdentifier = registerMockFor(OrganizationAssignedIdentifier.class);
	  EasyMock.expect(studyCoordCetner.getHealthcareSite()).andReturn(coordCenterOrganization);

	  EasyMock.expect(coordinatingCenterAssignedIdentifier.getGridId()).andReturn("grid Id");
	  EasyMock.expect(coordinatingCenterAssignedIdentifier.getType()).andReturn(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER);
	  EasyMock.expect(coordinatingCenterAssignedIdentifier.getValue()).andReturn("identifier value");

	  EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	  studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);
	  studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);
	  EasyMock.expect(studySubjectStudyVersion.getScheduledEpoch(epochInCoordCentReturnedScheduledEpoch)).andReturn(scheduledEpoch);
	  EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	  EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
	  EasyMock.expect(studySite.getStudy()).andReturn(study);

	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(1);
	  scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);


	 replayMocks();
	 studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	 studySubject.addScheduledEpoch(scheduledEpoch);
	 studySubject.setStudySite(studySite);
	 studySubject.doMutiSiteEnrollment(coordCentReturnedScheduledEpoch, coordinatingCenterAssignedIdentifier);
	 assertEquals("Wrong registration status",RegistrationWorkFlowStatus.ON_STUDY,studySubject.getRegWorkflowStatus());
	 assertEquals("Wrong identifier value","identifier value",studySubject.getCoOrdinatingCenterIdentifier().getValue());

	 verifyMocks();

  }

  /**
   * Test do multi site transfer.
   *
   * @throws Exception the exception
   */
  public void testDoMultiSiteTransfer() throws Exception{
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch).times(1);
	  Arm arm = registerMockFor(Arm.class);
	  ScheduledArm scheduledArm = registerMockFor(ScheduledArm.class);

	  ScheduledEpoch coordCentReturnedScheduledEpoch = registerMockFor(ScheduledEpoch.class);
	  Epoch epochInCoordCentReturnedScheduledEpoch = registerMockFor(Epoch.class);
	  ScheduledArm scheduledArmInCoordCentReturnedScheduledEpoch = registerMockFor(ScheduledArm.class);
	  Arm coorCenArm = registerMockFor(Arm.class);

	  EasyMock.expect(epoch.getArmByName("armA")).andReturn(arm);
	  EasyMock.expect(scheduledEpoch.getScheduledArm()).andReturn(scheduledArm).times(2);

	  EasyMock.expect(scheduledEpoch.getRequiresArm()).andReturn(true);
	  EasyMock.expect(coordCentReturnedScheduledEpoch.getEpoch()).andReturn(epochInCoordCentReturnedScheduledEpoch);
	  EasyMock.expect(coordCentReturnedScheduledEpoch.getScheduledArm()).andReturn(scheduledArmInCoordCentReturnedScheduledEpoch);

	  studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);
	  studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);
	  EasyMock.expect(studySubjectStudyVersion.getScheduledEpoch(epochInCoordCentReturnedScheduledEpoch)).andReturn(scheduledEpoch);


	  scheduledArm.setArm(arm);
	  scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);


	  EasyMock.expect(scheduledArmInCoordCentReturnedScheduledEpoch.getArm()).andReturn(coorCenArm);
	  EasyMock.expect(coorCenArm.getName()).andReturn("armA");

	  EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(1);

	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  studySubject.addScheduledEpoch(scheduledEpoch);
	  studySubject.setStudySite(studySite);
	  studySubject.doMutiSiteTransfer(coordCentReturnedScheduledEpoch);

	  verifyMocks();

  }

  /**
   * Test do local transfer.
   *
   * @throws Exception the exception
   */
  public void testDoLocalTransfer() throws Exception{
	studySubjectStudyVersion.setStudySubject(studySubject);
	EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(3);
	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);
	studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);


	 EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_RANDOMIZATION_ON_EPOCH).times(2);
	 scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);
	 EasyMock.expect(studySite.getStudy()).andReturn(study);
	 EasyMock.expect(study.getRandomizationType()).andReturn(RandomizationType.CALL_OUT);
	 EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
 	 EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(1);

	 replayMocks();
	 studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
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
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
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
  
  public void testIsAssignedAndActivePersonnel1(){
	  	studySubjectStudyVersion.setStudySubject(studySubject);
		StudySite studyOrganization = new StudySite();
		StudyPersonnel studyPersonnel = studyOrganization.getStudyPersonnel().get(0);
		PersonUser researchStaff = new LocalPersonUser();
		researchStaff.setAssignedIdentifier("test1");
		studyPersonnel.setStatusCode("Active");
		studyPersonnel.setPersonUser(researchStaff);
		studyPersonnel = studyOrganization.getStudyPersonnel().get(1);
		researchStaff = new LocalPersonUser();
		researchStaff.setAssignedIdentifier("test2");
		studyPersonnel.setStatusCode("Inactive");
		studyPersonnel.setPersonUser(researchStaff);
		EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studyOrganization);
	  	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	  	replayMocks();
	  	studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
		researchStaff = new LocalPersonUser();
		researchStaff.setAssignedIdentifier("test1");
		assertTrue(studySubject.isAssignedAndActivePersonnel(researchStaff));
		verifyMocks();
	}
  
  public void testIsAssignedAndActivePersonnel2(){
	  	studySubjectStudyVersion.setStudySubject(studySubject);
	  	
		StudySite dummyStudySite = new StudySite();
		StudyPersonnel studyPersonnel = dummyStudySite.getStudyPersonnel().get(0);
		PersonUser researchStaff = new LocalPersonUser();
		researchStaff.setAssignedIdentifier("test1");
		studyPersonnel.setStatusCode("Active");
		studyPersonnel.setPersonUser(researchStaff);
		
		studyPersonnel = dummyStudySite.getStudyPersonnel().get(1);
		researchStaff = new LocalPersonUser();
		researchStaff.setAssignedIdentifier("test2");
		studyPersonnel.setStatusCode("Inactive");
		studyPersonnel.setPersonUser(researchStaff);
		
		EasyMock.expect(studySite.getIsCoordinatingCenter()).andReturn(false);
		EasyMock.expect(studySite.getActiveStudyPersonnel()).andReturn(new ArrayList<StudyPersonnel>());
		EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
		EasyMock.expect(study.getStudySites()).andReturn(new ArrayList<StudySite>());
		EasyMock.expect(study.getStudyCoordinatingCenter()).andReturn(dummyStudySite);
		EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(4);
	  	EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(4);
	  	
	  	replayMocks();
	  	studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
		researchStaff = new LocalPersonUser();
		researchStaff.setAssignedIdentifier("test2");
		assertFalse(studySubject.isAssignedAndActivePersonnel(researchStaff));
		verifyMocks();
  }
  
  public void testIsCurrentEpochWorkflowCompleteTrue1(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.ON_EPOCH);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertTrue(studySubject.isCurrentEpochWorkflowComplete());
	  verifyMocks();
  }
  
  public void testIsCurrentEpochWorkflowCompleteTrue2(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(2);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.OFF_EPOCH).times(2);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertTrue(studySubject.isCurrentEpochWorkflowComplete());
	  verifyMocks();
  }
  
  public void testIsCurrentEpochWorkflowCompleteFalse1(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(2);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_RANDOMIZATION_ON_EPOCH).times(2);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertFalse(studySubject.isCurrentEpochWorkflowComplete());
	  verifyMocks();
  }
  
  public void testIsCurrentEpochWorkflowCompleteFalse2(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(2);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH).times(2);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertFalse(studySubject.isCurrentEpochWorkflowComplete());
	  verifyMocks();
  }
  
  public void testCanChangeEpochTrue1(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(2);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.ON_EPOCH);
	  EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(2);
	  EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(2);
	  EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
	  EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(study.getIfHigherOrderEpochExists(epoch)).andReturn(true);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertTrue(studySubject.canChangeEpoch());
	  verifyMocks();
  }
  
  public void testCanChangeEpochTrue2(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING_ON_STUDY);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(3);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.OFF_EPOCH).times(2);
	  EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(2);
	  EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(2);
	  EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
	  EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(study.getIfHigherOrderEpochExists(epoch)).andReturn(true);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertTrue(studySubject.canChangeEpoch());
	  verifyMocks();
  }
  
  public void testCanChangeEpochFalse1(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(2);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH).times(2);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertFalse(studySubject.canChangeEpoch());
	  verifyMocks();
  }
  
  public void testCanChangeEpochFalse2(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.ON_EPOCH);
	  EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	  EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
	  EasyMock.expect(studySite.getStudy()).andReturn(study);
	  EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.PENDING);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertFalse(studySubject.canChangeEpoch());
	  verifyMocks();
  }
  
  public void testCanChangeEpochFalse3(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(2);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.ON_EPOCH);
	  EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(2);
	  EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(2);
	  EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
	  EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(study.getIfHigherOrderEpochExists(epoch)).andReturn(false);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertFalse(studySubject.canChangeEpoch());
	  verifyMocks();
  }
  
  public void testCanChangeEpochFalse4(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.OFF_STUDY);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(3);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.OFF_EPOCH).times(2);
	  EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(2);
	  EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(2);
	  EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
	  EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(study.getIfHigherOrderEpochExists(epoch)).andReturn(true);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertFalse(studySubject.canChangeEpoch());
	  verifyMocks();
  }
  
  public void testCanChangeEpochFalse5(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.NOT_REGISTERED);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(3);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.OFF_EPOCH).times(2);
	  EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(2);
	  EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(2);
	  EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
	  EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.OPEN);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(study.getIfHigherOrderEpochExists(epoch)).andReturn(true);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertFalse(studySubject.canChangeEpoch());
	  verifyMocks();
  }
  
  public void testCanTakeSubjectOffStudyTrue(){
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
	  replayMocks();
	  assertTrue(studySubject.canTakeSubjectOffStudy());
	  verifyMocks();
  }
  
  public void testCanTakeSubjectOffStudyFalse1(){
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.OFF_STUDY);
	  replayMocks();
	  assertFalse(studySubject.canTakeSubjectOffStudy());
	  verifyMocks();
  }
  
  public void testCanTakeSubjectOffStudyFalse2(){
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING_ON_STUDY);
	  replayMocks();
	  assertFalse(studySubject.canTakeSubjectOffStudy());
	  verifyMocks();
  }
  
  public void testCanFailScreeningTrue(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(2);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(epoch.getType()).andReturn(EpochType.SCREENING);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.ON_EPOCH);
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertTrue(studySubject.canFailScreening());
	  verifyMocks();
  }
  
  public void testCanFailScreeningFalse1(){
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
	  replayMocks();
	  assertFalse(studySubject.canFailScreening());
	  verifyMocks();
  }
  
  public void testCanFailScreeningFalse2(){
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.OFF_STUDY);
	  replayMocks();
	  assertFalse(studySubject.canFailScreening());
	  verifyMocks();
  }
  
  public void testCanFailScreeningFalse3(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(epoch.getType()).andReturn(EpochType.TREATMENT);
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertFalse(studySubject.canFailScreening());
	  verifyMocks();
  }
  
  public void testCanFailScreeningFalse4(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(2);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(epoch.getType()).andReturn(EpochType.SCREENING);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertFalse(studySubject.canFailScreening());
	  verifyMocks();
  }
  
  public void testCanAllowEligibilityWaiver1(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.ON_EPOCH);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertFalse(studySubject.canAllowEligibilityWaiver());
	  verifyMocks();
  }
  
  public void testCanAllowEligibilityWaiver2(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(2);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
	  EasyMock.expect(scheduledEpoch.hasWaivableEligibilityAnswers()).andReturn(false);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertFalse(studySubject.canAllowEligibilityWaiver());
	  verifyMocks();
  }
  
  public void testCanAllowEligibilityWaiver3(){
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(2);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
	  EasyMock.expect(scheduledEpoch.hasWaivableEligibilityAnswers()).andReturn(true);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  assertTrue(studySubject.canAllowEligibilityWaiver());
	  verifyMocks();
  }
  
  public void testAllowEligibilityWaiver(){
	  InclusionEligibilityCriteria inc1 = new InclusionEligibilityCriteria();
	  inc1.setQuestionText("ABC");
	  ExclusionEligibilityCriteria exc1 = new ExclusionEligibilityCriteria();
	  exc1.setQuestionText("DEF");
	  ExclusionEligibilityCriteria exc3 = new ExclusionEligibilityCriteria();
	  exc3.setQuestionText("XYZ");
	  SubjectEligibilityAnswer subjectEligibilityAnswer1 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer1.setEligibilityCriteria(inc1);
	  SubjectEligibilityAnswer subjectEligibilityAnswer2 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer2.setEligibilityCriteria(exc1);
	  subjectEligibilityAnswer2.setAnswerText("yes");
	  SubjectEligibilityAnswer subjectEligibilityAnswer3 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer3.setEligibilityCriteria(exc3);
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers.add(subjectEligibilityAnswer1);
	  subjectEligibilityAnswers.add(subjectEligibilityAnswer2);
	  subjectEligibilityAnswers.add(subjectEligibilityAnswer3);
	  
	  InclusionEligibilityCriteria inc2 = new InclusionEligibilityCriteria();
	  inc2.setQuestionText("PQR");
	  ExclusionEligibilityCriteria exc2 = new ExclusionEligibilityCriteria();
	  exc2.setQuestionText("DEF");
	  List<EligibilityCriteria> eligibilityCriteriaList = new ArrayList<EligibilityCriteria>();
	  eligibilityCriteriaList.add(inc2);
	  eligibilityCriteriaList.add(exc2);
	  
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(3);
	  EasyMock.expect(scheduledEpoch.getSubjectEligibilityAnswers()).andReturn(subjectEligibilityAnswers);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
	  EasyMock.expect(scheduledEpoch.hasWaivableEligibilityAnswers()).andReturn(true);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  PersonUser researchStaff = new LocalPersonUser();
	  studySubject.allowEligibilityWaiver(eligibilityCriteriaList, researchStaff);
	  assertFalse(subjectEligibilityAnswer1.getAllowWaiver());
	  assertTrue(subjectEligibilityAnswer2.getAllowWaiver());
	  assertTrue(subjectEligibilityAnswer2.getWaivedBy() == researchStaff);
	  assertFalse(subjectEligibilityAnswer3.getAllowWaiver());
	  verifyMocks();
  }
  
  public void testAllowEligibilityWaiverException1(){
	  InclusionEligibilityCriteria inc1 = new InclusionEligibilityCriteria();
	  inc1.setQuestionText("ABC");
	  SubjectEligibilityAnswer subjectEligibilityAnswer1 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer1.setEligibilityCriteria(inc1);
	  subjectEligibilityAnswer1.setAnswerText("yes");
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers.add(subjectEligibilityAnswer1);
	  
	  InclusionEligibilityCriteria inc2 = new InclusionEligibilityCriteria();
	  inc2.setQuestionText("ABC");
	  List<EligibilityCriteria> eligibilityCriteriaList = new ArrayList<EligibilityCriteria>();
	  eligibilityCriteriaList.add(inc2);
	  
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(3);
	  EasyMock.expect(scheduledEpoch.getSubjectEligibilityAnswers()).andReturn(subjectEligibilityAnswers);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
	  EasyMock.expect(scheduledEpoch.hasWaivableEligibilityAnswers()).andReturn(true);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  try {
		  PersonUser researchStaff = new LocalPersonUser();
		  studySubject.allowEligibilityWaiver(eligibilityCriteriaList, researchStaff);
		  fail("Should have thrown exception");
	}catch (C3PRBaseRuntimeException e) {
		e.printStackTrace();
	}catch (RuntimeException e) {
		e.printStackTrace();
		fail("wrong exception");
	}finally{
	  verifyMocks();
	}
  }
  
  public void testAllowEligibilityWaiverException2(){
	  ExclusionEligibilityCriteria exc1 = new ExclusionEligibilityCriteria();
	  exc1.setQuestionText("DEF");
	  SubjectEligibilityAnswer subjectEligibilityAnswer2 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer2.setEligibilityCriteria(exc1);
	  subjectEligibilityAnswer2.setAnswerText("no");
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers.add(subjectEligibilityAnswer2);
	  
	  ExclusionEligibilityCriteria exc2 = new ExclusionEligibilityCriteria();
	  exc2.setQuestionText("DEF");
	  List<EligibilityCriteria> eligibilityCriteriaList = new ArrayList<EligibilityCriteria>();
	  eligibilityCriteriaList.add(exc2);
	  
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(3);
	  EasyMock.expect(scheduledEpoch.getSubjectEligibilityAnswers()).andReturn(subjectEligibilityAnswers);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
	  EasyMock.expect(scheduledEpoch.hasWaivableEligibilityAnswers()).andReturn(true);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  try {
		  PersonUser researchStaff = new LocalPersonUser();
		  studySubject.allowEligibilityWaiver(eligibilityCriteriaList, researchStaff);
		  fail("Should have thrown exception");
	}catch (C3PRBaseRuntimeException e) {
		e.printStackTrace();
	}catch (RuntimeException e) {
		e.printStackTrace();
		fail("wrong exception");
	}finally{
	  verifyMocks();
	}
  }
  
  public void testAllowEligibilityWaiverException3(){
	  ExclusionEligibilityCriteria exc3 = new ExclusionEligibilityCriteria();
	  exc3.setQuestionText("XYZ");
	  SubjectEligibilityAnswer subjectEligibilityAnswer3 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer3.setEligibilityCriteria(exc3);
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers.add(subjectEligibilityAnswer3);
	  
	  ExclusionEligibilityCriteria exc4 = new ExclusionEligibilityCriteria();
	  exc4.setQuestionText("XYZ");
	  List<EligibilityCriteria> eligibilityCriteriaList = new ArrayList<EligibilityCriteria>();
	  eligibilityCriteriaList.add(exc4);
	  
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(3);
	  EasyMock.expect(scheduledEpoch.getSubjectEligibilityAnswers()).andReturn(subjectEligibilityAnswers);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
	  EasyMock.expect(scheduledEpoch.hasWaivableEligibilityAnswers()).andReturn(true);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  try {
		  PersonUser researchStaff = new LocalPersonUser();
		  studySubject.allowEligibilityWaiver(eligibilityCriteriaList, researchStaff);
		  fail("Should have thrown exception");
	}catch (C3PRBaseRuntimeException e) {
		e.printStackTrace();
	}catch (RuntimeException e) {
		e.printStackTrace();
		fail("wrong exception");
	}finally{
	  verifyMocks();
	}
  }
  
  public void testAllowEligibilityWaiverException4(){
	  ExclusionEligibilityCriteria exc3 = new ExclusionEligibilityCriteria();
	  exc3.setQuestionText("XYZ");
	  SubjectEligibilityAnswer subjectEligibilityAnswer3 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer3.setEligibilityCriteria(exc3);
	  subjectEligibilityAnswer3.setAnswerText("yes");
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers.add(subjectEligibilityAnswer3);
	  
	  ExclusionEligibilityCriteria exc4 = new ExclusionEligibilityCriteria();
	  exc4.setQuestionText("XYZ");
	  List<EligibilityCriteria> eligibilityCriteriaList = new ArrayList<EligibilityCriteria>();
	  eligibilityCriteriaList.add(exc4);
	  
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(2);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
	  EasyMock.expect(scheduledEpoch.hasWaivableEligibilityAnswers()).andReturn(true);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  try {
		  PersonUser researchStaff = null;
		  studySubject.allowEligibilityWaiver(eligibilityCriteriaList, researchStaff);
		  fail("Should have thrown exception");
	}catch (C3PRBaseRuntimeException e) {
		e.printStackTrace();
	}catch (RuntimeException e) {
		e.printStackTrace();
		fail("wrong exception");
	}finally{
	  verifyMocks();
	}
  }
  
  public void testAllowEligibilityWaiverException5(){
	  InclusionEligibilityCriteria inc1 = new InclusionEligibilityCriteria();
	  inc1.setQuestionText("ABC");
	  ExclusionEligibilityCriteria exc1 = new ExclusionEligibilityCriteria();
	  exc1.setQuestionText("DEF");
	  ExclusionEligibilityCriteria exc3 = new ExclusionEligibilityCriteria();
	  exc3.setQuestionText("XYZ");
	  SubjectEligibilityAnswer subjectEligibilityAnswer1 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer1.setEligibilityCriteria(inc1);
	  SubjectEligibilityAnswer subjectEligibilityAnswer2 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer2.setEligibilityCriteria(exc1);
	  subjectEligibilityAnswer2.setAnswerText("yes");
	  SubjectEligibilityAnswer subjectEligibilityAnswer3 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer3.setEligibilityCriteria(exc3);
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers.add(subjectEligibilityAnswer1);
	  subjectEligibilityAnswers.add(subjectEligibilityAnswer2);
	  subjectEligibilityAnswers.add(subjectEligibilityAnswer3);
	  
	  InclusionEligibilityCriteria inc2 = new InclusionEligibilityCriteria();
	  inc2.setQuestionText("PQR");
	  ExclusionEligibilityCriteria exc2 = new ExclusionEligibilityCriteria();
	  exc2.setQuestionText("DEF");
	  List<EligibilityCriteria> eligibilityCriteriaList = new ArrayList<EligibilityCriteria>();
	  eligibilityCriteriaList.add(inc2);
	  eligibilityCriteriaList.add(exc2);
	  
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch).times(2);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
	  EasyMock.expect(scheduledEpoch.hasWaivableEligibilityAnswers()).andReturn(false);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  try {
		  PersonUser researchStaff = null;
		  studySubject.allowEligibilityWaiver(eligibilityCriteriaList, researchStaff);
		  fail("Should have thrown exception");
	}catch (C3PRBaseRuntimeException e) {
		e.printStackTrace();
	}catch (RuntimeException e) {
		e.printStackTrace();
		fail("wrong exception");
	}finally{
	  verifyMocks();
	}
  }
  
  public void testWaiveEligibilityException1(){
	  InclusionEligibilityCriteria inc1 = new InclusionEligibilityCriteria();
	  inc1.setQuestionText("ABC");
	  ExclusionEligibilityCriteria exc1 = new ExclusionEligibilityCriteria();
	  exc1.setQuestionText("DEF");
	  SubjectEligibilityAnswer subjectEligibilityAnswer1 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer1.setEligibilityCriteria(inc1);
	  SubjectEligibilityAnswer subjectEligibilityAnswer2 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer2.setEligibilityCriteria(exc1);
	  subjectEligibilityAnswer2.setAllowWaiver(true);
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers1 = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers1.add(subjectEligibilityAnswer1);
	  subjectEligibilityAnswers1.add(subjectEligibilityAnswer2);
	  
	  InclusionEligibilityCriteria inc2 = new InclusionEligibilityCriteria();
	  inc2.setQuestionText("ABC");
	  ExclusionEligibilityCriteria exc2 = new ExclusionEligibilityCriteria();
	  exc2.setQuestionText("DEF");
	  SubjectEligibilityAnswer subjectEligibilityAnswer3 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer3.setEligibilityCriteria(inc2);
	  SubjectEligibilityAnswer subjectEligibilityAnswer4 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer4.setEligibilityCriteria(exc2);
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers2 = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers2.add(subjectEligibilityAnswer3);
	  subjectEligibilityAnswers2.add(subjectEligibilityAnswer4);
	  
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch);
	  EasyMock.expect(scheduledEpoch.getSubjectEligibilityAnswers()).andReturn(subjectEligibilityAnswers1);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  try {
		studySubject.waiveEligibility(subjectEligibilityAnswers2);
		fail("Should have thrown exception");
	}catch (C3PRBaseRuntimeException e) {
		e.printStackTrace();
	} catch (RuntimeException e) {
		e.printStackTrace();
		fail("Wrong Exception");
	}finally{
	  verifyMocks();
	}
  }
  
  public void testWaiveEligibilityException2(){
	  InclusionEligibilityCriteria inc1 = new InclusionEligibilityCriteria();
	  inc1.setQuestionText("ABC");
	  ExclusionEligibilityCriteria exc1 = new ExclusionEligibilityCriteria();
	  exc1.setQuestionText("DEF");
	  SubjectEligibilityAnswer subjectEligibilityAnswer1 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer1.setEligibilityCriteria(inc1);
	  subjectEligibilityAnswer1.setAllowWaiver(true);
	  SubjectEligibilityAnswer subjectEligibilityAnswer2 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer2.setEligibilityCriteria(exc1);
	  subjectEligibilityAnswer2.setAllowWaiver(true);
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers1 = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers1.add(subjectEligibilityAnswer1);
	  subjectEligibilityAnswers1.add(subjectEligibilityAnswer2);
	  
	  InclusionEligibilityCriteria inc2 = new InclusionEligibilityCriteria();
	  inc2.setQuestionText("ABC");
	  ExclusionEligibilityCriteria exc2 = new ExclusionEligibilityCriteria();
	  exc2.setQuestionText("DEF");
	  SubjectEligibilityAnswer subjectEligibilityAnswer3 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer3.setEligibilityCriteria(inc2);
	  subjectEligibilityAnswer3.setWaiverId("123");
	  SubjectEligibilityAnswer subjectEligibilityAnswer4 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer4.setEligibilityCriteria(exc2);
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers2 = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers2.add(subjectEligibilityAnswer3);
	  subjectEligibilityAnswers2.add(subjectEligibilityAnswer4);
	  
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch);
	  EasyMock.expect(scheduledEpoch.getSubjectEligibilityAnswers()).andReturn(subjectEligibilityAnswers1);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  try {
		studySubject.waiveEligibility(subjectEligibilityAnswers2);
		fail("Should have thrown exception");
	}catch (C3PRBaseRuntimeException e) {
		e.printStackTrace();
	} catch (RuntimeException e) {
		e.printStackTrace();
		fail("Wrong Exception");
	}finally{
	  verifyMocks();
	}
  }
  
  public void testWaiveEligibilityException3(){
	  InclusionEligibilityCriteria inc1 = new InclusionEligibilityCriteria();
	  inc1.setQuestionText("ABC");
	  ExclusionEligibilityCriteria exc1 = new ExclusionEligibilityCriteria();
	  exc1.setQuestionText("DEF");
	  SubjectEligibilityAnswer subjectEligibilityAnswer1 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer1.setEligibilityCriteria(inc1);
	  subjectEligibilityAnswer1.setAllowWaiver(true);
	  SubjectEligibilityAnswer subjectEligibilityAnswer2 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer2.setEligibilityCriteria(exc1);
	  subjectEligibilityAnswer2.setAllowWaiver(true);
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers1 = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers1.add(subjectEligibilityAnswer1);
	  subjectEligibilityAnswers1.add(subjectEligibilityAnswer2);
	  
	  InclusionEligibilityCriteria inc2 = new InclusionEligibilityCriteria();
	  inc2.setQuestionText("ABC");
	  ExclusionEligibilityCriteria exc2 = new ExclusionEligibilityCriteria();
	  exc2.setQuestionText("DEF");
	  SubjectEligibilityAnswer subjectEligibilityAnswer3 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer3.setEligibilityCriteria(inc2);
	  subjectEligibilityAnswer3.setWaiverId("123");
	  subjectEligibilityAnswer3.setWaiverReason("some reason 1");
	  SubjectEligibilityAnswer subjectEligibilityAnswer4 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer4.setEligibilityCriteria(exc2);
	  subjectEligibilityAnswer4.setWaiverId("123");
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers2 = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers2.add(subjectEligibilityAnswer3);
	  subjectEligibilityAnswers2.add(subjectEligibilityAnswer4);
	  
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch);
	  EasyMock.expect(scheduledEpoch.getSubjectEligibilityAnswers()).andReturn(subjectEligibilityAnswers1);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  try {
		studySubject.waiveEligibility(subjectEligibilityAnswers2);
		fail("Should have thrown exception");
	}catch (C3PRBaseRuntimeException e) {
		e.printStackTrace();
	} catch (RuntimeException e) {
		e.printStackTrace();
		fail("Wrong Exception");
	}finally{
	  verifyMocks();
	}
  }
  
  public void testWaiveEligibility(){
	  InclusionEligibilityCriteria inc1 = new InclusionEligibilityCriteria();
	  inc1.setQuestionText("ABC");
	  ExclusionEligibilityCriteria exc1 = new ExclusionEligibilityCriteria();
	  exc1.setQuestionText("DEF");
	  SubjectEligibilityAnswer subjectEligibilityAnswer1 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer1.setEligibilityCriteria(inc1);
	  subjectEligibilityAnswer1.setAllowWaiver(true);
	  SubjectEligibilityAnswer subjectEligibilityAnswer2 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer2.setEligibilityCriteria(exc1);
	  subjectEligibilityAnswer2.setAllowWaiver(true);
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers1 = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers1.add(subjectEligibilityAnswer1);
	  subjectEligibilityAnswers1.add(subjectEligibilityAnswer2);
	  
	  InclusionEligibilityCriteria inc2 = new InclusionEligibilityCriteria();
	  inc2.setQuestionText("ABC");
	  ExclusionEligibilityCriteria exc2 = new ExclusionEligibilityCriteria();
	  exc2.setQuestionText("DEF");
	  SubjectEligibilityAnswer subjectEligibilityAnswer3 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer3.setEligibilityCriteria(inc2);
	  subjectEligibilityAnswer3.setWaiverId("123");
	  subjectEligibilityAnswer3.setWaiverReason("some reason 1");
	  SubjectEligibilityAnswer subjectEligibilityAnswer4 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer4.setEligibilityCriteria(exc2);
	  subjectEligibilityAnswer4.setWaiverId("123");
	  subjectEligibilityAnswer4.setWaiverReason("some reason 2");
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers2 = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers2.add(subjectEligibilityAnswer3);
	  subjectEligibilityAnswers2.add(subjectEligibilityAnswer4);
	  
	  studySubjectStudyVersion.setStudySubject(studySubject);
	  EasyMock.expect(studySubjectStudyVersion.getCurrentScheduledEpoch()).andReturn(scheduledEpoch);
	  EasyMock.expect(scheduledEpoch.getSubjectEligibilityAnswers()).andReturn(subjectEligibilityAnswers1);
	  replayMocks();
	  studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
	  studySubject.waiveEligibility(subjectEligibilityAnswers2);
	  assertEquals("123", subjectEligibilityAnswer1.getWaiverId());
	  assertEquals("123", subjectEligibilityAnswer2.getWaiverId());
	  assertEquals("some reason 1", subjectEligibilityAnswer1.getWaiverReason());
	  assertEquals("some reason 2", subjectEligibilityAnswer2.getWaiverReason());
	  verifyMocks();
  }
  
  public void testFailScreeningSuccessfull(){
	  Date failScreeningDate = new Date();
	  List<OffEpochReason> offScreeningReasons = new ArrayList<OffEpochReason>();
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING);
	  EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.ON_EPOCH);
	  EasyMock.expect(epoch.getType()).andReturn(EpochType.SCREENING);
  	  scheduledEpoch.takeSubjectOffEpoch(offScreeningReasons, failScreeningDate);
	  replayMocks();
	  studySubject.setStudySite(studySite);
	  studySubject.addScheduledEpoch(scheduledEpoch);
	  studySubject.failScreening(offScreeningReasons, failScreeningDate);

	  assertEquals(RegistrationWorkFlowStatus.NOT_REGISTERED,studySubject.getRegWorkflowStatus());
	  verifyMocks();
  }
  
  public void testFailScreeningFail1(){
	  Date failScreeningDate = new Date();
	  List<OffEpochReason> offScreeningReasons = new ArrayList<OffEpochReason>();
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING);
	  EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(epoch.getType()).andReturn(EpochType.TREATMENT);
	  replayMocks();
	  studySubject.setStudySite(studySite);
	  studySubject.addScheduledEpoch(scheduledEpoch);
	  try {
			studySubject.failScreening(offScreeningReasons, failScreeningDate);
			fail();
		} catch (C3PRBaseRuntimeException e) {
			e.printStackTrace();
		} finally{
			verifyMocks();
		}
  }
  
  public void testFailScreeningFail2(){
	  Date failScreeningDate = new Date();
	  List<OffEpochReason> offScreeningReasons = new ArrayList<OffEpochReason>();
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING);
	  EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	  EasyMock.expect(scheduledEpoch.getEpoch()).andReturn(epoch);
	  EasyMock.expect(scheduledEpoch.getScEpochWorkflowStatus()).andReturn(ScheduledEpochWorkFlowStatus.OFF_EPOCH);
	  EasyMock.expect(epoch.getType()).andReturn(EpochType.SCREENING);
	  replayMocks();
	  studySubject.setStudySite(studySite);
	  studySubject.addScheduledEpoch(scheduledEpoch);
	  try {
			studySubject.failScreening(offScreeningReasons, failScreeningDate);
			fail();
		} catch (C3PRBaseRuntimeException e) {
			e.printStackTrace();
		} finally{
			verifyMocks();
		}
  }
  
  public void testFailScreeningFail3(){
	  Date failScreeningDate = new Date();
	  List<OffEpochReason> offScreeningReasons = new ArrayList<OffEpochReason>();
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
	  EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	  replayMocks();
	  studySubject.setStudySite(studySite);
	  studySubject.addScheduledEpoch(scheduledEpoch);
	  try {
			studySubject.failScreening(offScreeningReasons, failScreeningDate);
			fail();
		} catch (C3PRBaseRuntimeException e) {
			e.printStackTrace();
		} finally{
			verifyMocks();
		}
  }
  
  public void testTakeSubjectOffStudySuccessfull(){
	  Date offStudyDate = new Date();
	  List<OffEpochReason> offStudyReasons = new ArrayList<OffEpochReason>();
	  OffEpochReason offEpochReason = registerMockFor(OffEpochReason.class);
	  offStudyReasons.add(offEpochReason);
	  EasyMock.expect(offEpochReason.getReason()).andReturn(new OffStudyReason());
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
	  EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
	  scheduledEpoch.takeSubjectOffEpoch(offStudyReasons, offStudyDate);
	  replayMocks();
	  studySubject.setStudySite(studySite);
	  studySubject.addScheduledEpoch(scheduledEpoch);
	  studySubject.takeSubjectOffStudy(offStudyReasons, offStudyDate);

	  assertEquals(RegistrationWorkFlowStatus.OFF_STUDY,studySubject.getRegWorkflowStatus());
	  verifyMocks();
  }
  
  public void testTakeSubjectOffStudyFail1(){
	  Date offStudyDate = new Date();
	  List<OffEpochReason> offStudyReasons = new ArrayList<OffEpochReason>();
	  OffEpochReason offEpochReason = registerMockFor(OffEpochReason.class);
	  offStudyReasons.add(offEpochReason);
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING);
	  replayMocks();
	  try {
		studySubject.takeSubjectOffStudy(offStudyReasons, offStudyDate);
		fail();
	} catch (C3PRBaseRuntimeException e) {
		e.printStackTrace();
	}finally{
	  verifyMocks();
	}
  }
  
  public void testTakeSubjectOffStudyFail2(){
	  Date offStudyDate = new Date();
	  List<OffEpochReason> offStudyReasons = new ArrayList<OffEpochReason>();
	  OffEpochReason offEpochReason = registerMockFor(OffEpochReason.class);
	  offStudyReasons.add(offEpochReason);
	  EasyMock.expect(offEpochReason.getReason()).andReturn(new OffScreeningReason()).times(2);
	  studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
	  replayMocks();
	  try {
		studySubject.takeSubjectOffStudy(offStudyReasons, offStudyDate);
		fail();
	} catch (C3PRBaseRuntimeException e) {
		e.printStackTrace();
	}finally{
	  verifyMocks();
	}
  }
  
  public void testGetLastConsentedStudyVersion(){
	  
  }
  
 public void testGetAllSignedConsents(){
	 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
	 studySubjectStudyVersions.add(studySubjectStudyVersion);
	 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
	 
	 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
 		studySubjectConsentVersions.add(studySubjectConsentVersion);
 		
	 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
	 EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDate()).andReturn(new Date());
	 replayMocks();
	 
	 assertEquals("Wrong number of study subject consents",1,studySubject.getAllSignedConsents().size());
  }
 
 public void testGetAllConsents(){
	 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
	 studySubjectStudyVersions.add(studySubjectStudyVersion);
	 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
	 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
 		studySubjectConsentVersions.add(studySubjectConsentVersion);
	 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
	 replayMocks();
	 
	 assertEquals("Wrong number of study subject consents",1,studySubject.getAllConsents().size());
  }
 
  public void testGetFirstConsentedStudyVersion(){
	 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
	 studySubjectStudyVersions.add(studySubjectStudyVersion);
	 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
	 Consent consent = registerMockFor(Consent.class);
	 Consent consent2 = registerMockFor(Consent.class);
	 
	 StudyVersion studyVersion = EasyMock.createNiceMock(StudyVersion.class);
	 StudyVersion studyVersion2 = EasyMock.createNiceMock(StudyVersion.class);
	 
	 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
 		studySubjectConsentVersions.add(studySubjectConsentVersion);
 		
 	 StudySubjectConsentVersion studySubjectConsentVersion2 = registerMockFor(StudySubjectConsentVersion.class);
 	 studySubjectConsentVersions.add(studySubjectConsentVersion2);
 		
	 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
	 EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent);
	 EasyMock.expect(consent.getStudyVersion()).andReturn(studyVersion);
	 EasyMock.expect(studySubjectConsentVersion2.getConsent()).andReturn(consent2);
	 EasyMock.expect(consent2.getStudyVersion()).andReturn(studyVersion2);
	 EasyMock.expect(((StudyVersion) studyVersion2).compareTo(registerMockFor(StudyVersion.class))).andReturn(1);
	 replayMocks();
	 
	 assertEquals("Wrong study version retrieved",studyVersion,studySubject.getFirstConsentedStudyVersion());
	 verifyMocks();
  }
  
  public void testGetOriginalSignedConsents(){
		 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		 studySubjectStudyVersions.add(studySubjectStudyVersion);
		 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
		 Consent consent = registerMockFor(Consent.class);
		 StudyVersion studyVersion = registerMockFor(StudyVersion.class);
		 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
	 		studySubjectConsentVersions.add(studySubjectConsentVersion);
	 		
		 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions).times(2);
		 EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent).times(2);
		 EasyMock.expect(consent.getStudyVersion()).andReturn(studyVersion).times(2);
		 EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDate()).andReturn(new Date());
		 replayMocks();
		 
		 assertEquals("Wrong consents returned",studySubjectConsentVersions,studySubject.getOriginalSignedConsents()); 
		 verifyMocks();
	  }

 
	public void testGetOriginalConsents(){
		 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		 studySubjectStudyVersions.add(studySubjectStudyVersion);
		 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
		 Consent consent = registerMockFor(Consent.class);
		 StudyVersion studyVersion = registerMockFor(StudyVersion.class);
		 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
	 		studySubjectConsentVersions.add(studySubjectConsentVersion);
	 		
		 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions).times(2);
		 EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent).times(2);
		 EasyMock.expect(consent.getStudyVersion()).andReturn(studyVersion).times(2);
		 replayMocks();
		 
		 assertEquals("Wrong consents returned",studySubjectConsentVersions,studySubject.getOriginalConsents()); 
		 verifyMocks();
	  }

	public void testGetLatestConsents(){
		 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		 studySubjectStudyVersions.add(studySubjectStudyVersion);
		 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
		 Consent consent = registerMockFor(Consent.class);
		 StudyVersion studyVersion = registerMockFor(StudyVersion.class);
		 
		 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
			studySubjectConsentVersions.add(studySubjectConsentVersion);
			
		 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions).times(2);
		 EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent).times(2);
		 EasyMock.expect(consent.getStudyVersion()).andReturn(studyVersion).times(2);
		 replayMocks();
		 
		 assertEquals("Wrong consents returned",studySubjectConsentVersions,studySubject.getLatestConsents()); 
		 verifyMocks();
	}
	
	public void testGetLatestSignedConsents(){
		 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		 studySubjectStudyVersions.add(studySubjectStudyVersion);
		 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
		 Consent consent = registerMockFor(Consent.class);
		 StudyVersion studyVersion = registerMockFor(StudyVersion.class);
		 
		 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
			studySubjectConsentVersions.add(studySubjectConsentVersion);
			
		 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions).times(2);
		 EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent).times(2);
		 EasyMock.expect(consent.getStudyVersion()).andReturn(studyVersion).times(2);
		 EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDate()).andReturn(new Date());
		 replayMocks();
		 
		 assertEquals("Wrong consents returned",studySubjectConsentVersions,studySubject.getLatestSignedConsents()); 
		 verifyMocks();
}
	
	public void testGetSignedConsents(){
		 String studyVersionName = "Test Study Version 2";
		 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		 studySubjectStudyVersions.add(studySubjectStudyVersion);
		 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
		 Consent consent = registerMockFor(Consent.class);
		 List<Consent> consents = new ArrayList<Consent>();
		 consents.add(consent);
		 StudyVersion studyVersion = registerMockFor(StudyVersion.class);
		 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
			studySubjectConsentVersions.add(studySubjectConsentVersion);
		 studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);
		 
		 EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
		 List<StudyVersion> studyVersions = new ArrayList<StudyVersion>();
		 studyVersions.add(studyVersion);
		 EasyMock.expect(studySite.getStudy()).andReturn(study);
		 EasyMock.expect(study.getStudyVersion(studyVersionName)).andReturn(studyVersion);
		 EasyMock.expect(studyVersion.getConsents()).andReturn(consents);
		 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
		 EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
		 EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
		 EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent);
		 EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDate()).andReturn(new Date());
		 replayMocks();
		 
		 studySubject.setStudySite(studySite);
		 assertEquals("Wrong consents returned",studySubjectConsentVersions,studySubject.getSignedConsents(studyVersionName)); 
		 verifyMocks();
	}

	public void testGetConsents(){
		 String studyVersionName = "Test Study Version 2";
		 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		 studySubjectStudyVersions.add(studySubjectStudyVersion);
		 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
		 Consent consent = registerMockFor(Consent.class);
		 List<Consent> consents = new ArrayList<Consent>();
		 consents.add(consent);
		 StudyVersion studyVersion = registerMockFor(StudyVersion.class);
		 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
			studySubjectConsentVersions.add(studySubjectConsentVersion);
		 studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);
		 
		 EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
		 List<StudyVersion> studyVersions = new ArrayList<StudyVersion>();
		 studyVersions.add(studyVersion);
		 EasyMock.expect(studySite.getStudy()).andReturn(study);
		 EasyMock.expect(study.getStudyVersion(studyVersionName)).andReturn(studyVersion);
		 EasyMock.expect(studyVersion.getConsents()).andReturn(consents);
		 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
		 EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
		 EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
		 EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent);
		 replayMocks();
		 
		 studySubject.setStudySite(studySite);
		 assertEquals("Wrong consents returned",studySubjectConsentVersions,studySubject.getConsents(studyVersionName)); 
		 verifyMocks();
	}
	
	public void testHasSignedConsents(){
		 String studyVersionName = "Test Study Version 2";
		 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		 studySubjectStudyVersions.add(studySubjectStudyVersion);
		 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
		 Consent consent = registerMockFor(Consent.class);
		 List<Consent> consents = new ArrayList<Consent>();
		 consents.add(consent);
		 StudyVersion studyVersion = registerMockFor(StudyVersion.class);
		 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
			studySubjectConsentVersions.add(studySubjectConsentVersion);
		 studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);
		 
		 EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
		 List<StudyVersion> studyVersions = new ArrayList<StudyVersion>();
		 studyVersions.add(studyVersion);
		 EasyMock.expect(studySite.getStudy()).andReturn(study);
		 EasyMock.expect(study.getStudyVersion(studyVersionName)).andReturn(studyVersion);
		 EasyMock.expect(studyVersion.getConsents()).andReturn(consents);
		 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
		 EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
		 EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
		 EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDate()).andReturn(null);
		 replayMocks();
		 
		 studySubject.setStudySite(studySite);
		 assertFalse("Did not expect signed consents",studySubject.hasSignedConsents(studyVersionName)); 
		 verifyMocks();
	}
	
	public void testHasSignedConsents1(){
		 String studyVersionName = "Test Study Version 2";
		 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		 studySubjectStudyVersions.add(studySubjectStudyVersion);
		 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
		 Consent consent = registerMockFor(Consent.class);
		 List<Consent> consents = new ArrayList<Consent>();
		 consents.add(consent);
		 StudyVersion studyVersion = registerMockFor(StudyVersion.class);
		 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
			studySubjectConsentVersions.add(studySubjectConsentVersion);
		 studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);
		 
		 EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
		 List<StudyVersion> studyVersions = new ArrayList<StudyVersion>();
		 studyVersions.add(studyVersion);
		 EasyMock.expect(studySite.getStudy()).andReturn(study);
		 EasyMock.expect(study.getStudyVersion(studyVersionName)).andReturn(studyVersion);
		 EasyMock.expect(studyVersion.getConsents()).andReturn(consents);
		 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions);
		 EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
		 EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);
		 EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent);
		 EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDate()).andReturn(new Date());
		 replayMocks();
		 
		 studySubject.setStudySite(studySite);
		 assertTrue("Expected signed consent",studySubject.hasSignedConsents(studyVersionName)); 
		 verifyMocks();
	}
	
	public void testCanReConsent(){
		 String studyVersionName = "Test Study Version 2";
		 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		 studySubjectStudyVersions.add(studySubjectStudyVersion);
		 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
		 Consent consent = registerMockFor(Consent.class);
		 List<Consent> consents = new ArrayList<Consent>();
		 consents.add(consent);
		 StudyVersion studyVersion = registerMockFor(StudyVersion.class);
		 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
			studySubjectConsentVersions.add(studySubjectConsentVersion);
		 studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);
		 
		 EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
		 List<StudyVersion> studyVersions = new ArrayList<StudyVersion>();
		 studyVersions.add(studyVersion);
		 EasyMock.expect(studySite.getStudy()).andReturn(study).times(2);
		 EasyMock.expect(study.getStudyVersion(studyVersionName)).andReturn(studyVersion).times(2);
		 EasyMock.expect(studyVersion.getConsents()).andReturn(consents);
		 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions).times(2);
		 EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(2);
		 EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(2);
		 EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent);
		 EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDate()).andReturn(new Date()).times(2);
		 replayMocks();
		 
		 studySubject.setStudySite(studySite);
		 studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
		 
		assertFalse("Should not be able to reconsent as there is already a signed consent",studySubject.canReConsent(studyVersionName));
		 verifyMocks();
	}
	
	public void testCanReConsent1(){
		 String studyVersionName = "Test Study Version 2";
		 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		 studySubjectStudyVersions.add(studySubjectStudyVersion);
		 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
		 Consent consent = registerMockFor(Consent.class);
		 Consent consent1 = registerMockFor(Consent.class);
		 List<Consent> consents = new ArrayList<Consent>();
		 consents.add(consent);
		 List<Consent> consents1 = new ArrayList<Consent>();
		 consents1.add(consent1);
		 StudyVersion studyVersion = registerMockFor(StudyVersion.class);
		 StudyVersion studyVersion1 = registerMockFor(StudyVersion.class);
		 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
			studySubjectConsentVersions.add(studySubjectConsentVersion);
		 studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);
		 
		 EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
		 List<StudyVersion> studyVersions = new ArrayList<StudyVersion>();
		 studyVersions.add(studyVersion);
		 studyVersions.add(studyVersion1);
		 EasyMock.expect(study.getStudyVersions()).andReturn(studyVersions);
		 EasyMock.expect(studySite.getStudy()).andReturn(study).times(3);
		 EasyMock.expect(study.getStudyVersion(studyVersionName)).andReturn(studyVersion1).times(2);
	//	 EasyMock.expect(studyVersion.getConsents()).andReturn(consents);
		 EasyMock.expect(studyVersion1.getConsents()).andReturn(consents1);
		 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions).times(2);
		 EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(3);
		 EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(3);
		 EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDate()).andReturn(new Date()).times(2);
		 EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent);
		 Date version1Date = new Date();
		 version1Date.setYear(version1Date.getYear()-1);
		 EasyMock.expect(studyVersion.getVersionDate()).andReturn(version1Date);
		 EasyMock.expect(studyVersion1.getVersionDate()).andReturn(new Date()).times(3);
		 replayMocks();
		 
		 studySubject.setStudySite(studySite);
		 studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING_ON_STUDY);
		 
		assertTrue("Should be able to reconsent ",studySubject.canReConsent(studyVersionName));
		 verifyMocks();
	}
	
	public void testReConsent(){
		 String studyVersionName = "Test Study Version 2";
		 List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		 studySubjectStudyVersions.add(studySubjectStudyVersion);
		 studySubject.setStudySubjectStudyVersions(studySubjectStudyVersions);
		 Consent consent = registerMockFor(Consent.class);
		 List<Consent> consents = new ArrayList<Consent>();
		 consents.add(consent);
		 Consent consent1 = registerMockFor(Consent.class);
		 List<Consent> consents1 = new ArrayList<Consent>();
		 consents1.add(consent1);
		 StudyVersion studyVersion = registerMockFor(StudyVersion.class);
		 StudyVersion studyVersion1 = registerMockFor(StudyVersion.class);
		 EasyMock.expect(studyVersion1.getConsents()).andReturn(consents1).times(2);
		
		 List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
			studySubjectConsentVersions.add(studySubjectConsentVersion);
		 
		 EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(1);
		 List<StudyVersion> studyVersions = new ArrayList<StudyVersion>();
		 studyVersions.add(studyVersion);
		 EasyMock.expect(study.getStudyVersions()).andReturn(studyVersions);
		 EasyMock.expect(studySite.getStudy()).andReturn(study).times(4);
		 EasyMock.expect(study.getStudyVersion(studyVersionName)).andReturn(studyVersion1).times(3);
		 EasyMock.expect(studySubjectStudyVersion.getStudySubjectConsentVersions()).andReturn(studySubjectConsentVersions).times(2);
		 EasyMock.expect(studySubjectStudyVersion.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion).times(6);
		 EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite).times(5);
		 Date consentDate = new Date();
		 consentDate.setMonth(consentDate.getMonth()-1);
		 EasyMock.expect(studySubjectConsentVersion.getInformedConsentSignedDate()).andReturn(consentDate).times(2);
		 EasyMock.expect(studySubjectConsentVersion.getConsent()).andReturn(consent);
		 List<ConsentingMethod> consentingMethods = new ArrayList<ConsentingMethod>();
		 consentingMethods.add(ConsentingMethod.VERBAL);
		 
		 EasyMock.expect(studySubjectStudyVersion.hasSignedConsent(consent1)).andReturn(true);
		 studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);
		 EasyMock.expect(studySite.getStudySiteStudyVersionGivenStudyVersionName(studyVersionName)).andReturn(studySiteStudyVersion);
		 Date version1Date = new Date();
		 version1Date.setYear(version1Date.getYear()-1);
		 EasyMock.expect(studyVersion.getVersionDate()).andReturn(version1Date);
		 EasyMock.expect(studyVersion1.getVersionDate()).andReturn(new Date()).times(1);
		 EasyMock.expect(studyVersion1.getConsentByName("New consent")).andReturn(consent1);
		 EasyMock.expect(studySiteStudyVersion.getStudyVersion()).andReturn(studyVersion1);
		 EasyMock.expect(studyVersion1.getName()).andReturn(studyVersionName).times(2);
		 EasyMock.expect(consent1.getMandatoryIndicator()).andReturn(true);
		 
		 
		 replayMocks();
		 
		 studySubject.setStudySite(studySite);
		 studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING_ON_STUDY);
		 
		 List<StudySubjectConsentVersion> studySubjectConsentVersionHolder = new ArrayList<StudySubjectConsentVersion>();
		 StudySubjectConsentVersion studySubjectConsentVersion1 = new StudySubjectConsentVersion();
		 Consent consent1copy = new Consent();
		 consent1copy.setName("New consent");
		 studySubjectConsentVersion1.setConsent(consent1copy);
		 
		 studySubjectConsentVersionHolder.add(studySubjectConsentVersion1);
		
		try {
			studySubject.reConsent(studyVersionName,studySubjectConsentVersionHolder);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		 verifyMocks();
	}
	
	public void testUpdateRegistryStatusInvalidStatusCodeException(){
	  	PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus1 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
	  	RegistryStatus registryStatus1 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus2 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        RegistryStatus registryStatus2 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus3 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        RegistryStatus registryStatus3 = registerMockFor(RegistryStatus.class);
        
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus4 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        RegistryStatus registryStatus4 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus5 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        RegistryStatus registryStatus5 = registerMockFor(RegistryStatus.class);
        
        EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);

		EasyMock.expect(studySite.getStudy()).andReturn(study);
		EasyMock.expect(study.getPermissibleStudySubjectRegistryStatuses()).andReturn(Arrays.asList(new PermissibleStudySubjectRegistryStatus[]{permissibleStudySubjectRegistryStatus1,
				permissibleStudySubjectRegistryStatus2, permissibleStudySubjectRegistryStatus3, permissibleStudySubjectRegistryStatus4, permissibleStudySubjectRegistryStatus5}));
		EasyMock.expect(permissibleStudySubjectRegistryStatus1.getRegistryStatus()).andReturn(registryStatus1);
		EasyMock.expect(registryStatus1.getCode()).andReturn("Pre-Enrolled");
		EasyMock.expect(permissibleStudySubjectRegistryStatus2.getRegistryStatus()).andReturn(registryStatus2);
		EasyMock.expect(registryStatus2.getCode()).andReturn("Enrolled");
		EasyMock.expect(permissibleStudySubjectRegistryStatus3.getRegistryStatus()).andReturn(registryStatus3);
		EasyMock.expect(registryStatus3.getCode()).andReturn("Screen Failed");
		EasyMock.expect(permissibleStudySubjectRegistryStatus4.getRegistryStatus()).andReturn(registryStatus4);
		EasyMock.expect(registryStatus4.getCode()).andReturn("Accrued");
		EasyMock.expect(permissibleStudySubjectRegistryStatus5.getRegistryStatus()).andReturn(registryStatus5);
		EasyMock.expect(registryStatus5.getCode()).andReturn("Consent Withdrawn");
		
	  	replayMocks();
	  	studySubject.setStudySite(studySite);
	  	Date date = new Date();
	  	try {
			studySubject.updateRegistryStatus("XYZ", date, null, Arrays.asList(new RegistryStatusReason[]{new RegistryStatusReason("A","A",null,true)}));
			fail();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(600, e.getExceptionCode());
		}finally{
			verifyMocks();
		}
  }
	
	public void testUpdateRegistryStatusPreEnrolled(){
	  	PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus1 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
	  	RegistryStatus registryStatus1 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus2 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus3 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus4 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus5 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        
        EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);

		EasyMock.expect(studySite.getStudy()).andReturn(study);
		EasyMock.expect(study.getPermissibleStudySubjectRegistryStatuses()).andReturn(Arrays.asList(new PermissibleStudySubjectRegistryStatus[]{permissibleStudySubjectRegistryStatus1,
				permissibleStudySubjectRegistryStatus2, permissibleStudySubjectRegistryStatus3, permissibleStudySubjectRegistryStatus4, permissibleStudySubjectRegistryStatus5}));
		EasyMock.expect(permissibleStudySubjectRegistryStatus1.getRegistryStatus()).andReturn(registryStatus1);
		EasyMock.expect(registryStatus1.getCode()).andReturn("Pre-Enrolled");
	  	replayMocks();
	  	studySubject.setStudySite(studySite);
	  	Date date = new Date();
	  	studySubject.updateRegistryStatus("Pre-Enrolled", date, "some comment", new ArrayList<RegistryStatusReason>());
		assertEquals(1, studySubject.getStudySubjectRegistryStatusHistoryInternal().size());
		assertEquals(date, studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getEffectiveDate());
		assertEquals("some comment", studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getCommentText());
		assertEquals(permissibleStudySubjectRegistryStatus1, studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getPermissibleStudySubjectRegistryStatus());
		verifyMocks();
  }
	
	public void testUpdateRegistryStatusEnrolled(){
	  	PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus1 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
	  	RegistryStatus registryStatus1 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus2 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        RegistryStatus registryStatus2 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus3 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus4 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus5 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        
        EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);

		EasyMock.expect(studySite.getStudy()).andReturn(study);
		EasyMock.expect(study.getPermissibleStudySubjectRegistryStatuses()).andReturn(Arrays.asList(new PermissibleStudySubjectRegistryStatus[]{permissibleStudySubjectRegistryStatus1,
				permissibleStudySubjectRegistryStatus2, permissibleStudySubjectRegistryStatus3, permissibleStudySubjectRegistryStatus4, permissibleStudySubjectRegistryStatus5}));
		EasyMock.expect(permissibleStudySubjectRegistryStatus1.getRegistryStatus()).andReturn(registryStatus1);
		EasyMock.expect(registryStatus1.getCode()).andReturn("Pre-Enrolled");
		EasyMock.expect(permissibleStudySubjectRegistryStatus2.getRegistryStatus()).andReturn(registryStatus2);
		EasyMock.expect(registryStatus2.getCode()).andReturn("Enrolled");
	  	replayMocks();
	  	studySubject.setStudySite(studySite);
	  	Date date = new Date();
	  	studySubject.updateRegistryStatus("Enrolled", date, null, null);
		assertEquals(1, studySubject.getStudySubjectRegistryStatusHistoryInternal().size());
		assertEquals(date, studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getEffectiveDate());
		assertEquals(permissibleStudySubjectRegistryStatus2, studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getPermissibleStudySubjectRegistryStatus());
		verifyMocks();
  }
	
	public void testUpdateRegistryStatusScreenFailureWithInvalidPrimaryReason(){
	  	PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus1 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
	  	RegistryStatus registryStatus1 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus2 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        RegistryStatus registryStatus2 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus3 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        RegistryStatus registryStatus3 = registerMockFor(RegistryStatus.class);
        
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus4 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus5 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        
        EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);

		EasyMock.expect(studySite.getStudy()).andReturn(study);
		EasyMock.expect(study.getPermissibleStudySubjectRegistryStatuses()).andReturn(Arrays.asList(new PermissibleStudySubjectRegistryStatus[]{permissibleStudySubjectRegistryStatus1,
				permissibleStudySubjectRegistryStatus2, permissibleStudySubjectRegistryStatus3, permissibleStudySubjectRegistryStatus4, permissibleStudySubjectRegistryStatus5}));
		EasyMock.expect(permissibleStudySubjectRegistryStatus1.getRegistryStatus()).andReturn(registryStatus1);
		EasyMock.expect(registryStatus1.getCode()).andReturn("Pre-Enrolled");
		EasyMock.expect(permissibleStudySubjectRegistryStatus2.getRegistryStatus()).andReturn(registryStatus2);
		EasyMock.expect(registryStatus2.getCode()).andReturn("Enrolled");
		EasyMock.expect(permissibleStudySubjectRegistryStatus3.getRegistryStatus()).andReturn(registryStatus3).times(2);
		EasyMock.expect(registryStatus3.getCode()).andReturn("Screen Failed");
		EasyMock.expect(registryStatus3.getPrimaryReasons()).andReturn(Arrays.asList(new RegistryStatusReason[]{new RegistryStatusReason("A","A",null,true), 
																					new RegistryStatusReason("B","B",null,true)}));
	  	replayMocks();
	  	studySubject.setStudySite(studySite);
	  	Date date = new Date();
	  	try {
	  		studySubject.updateRegistryStatus("Screen Failed", date, null, Arrays.asList(new RegistryStatusReason[]{new RegistryStatusReason("C","C",null,true)}));
	  		fail();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(601, e.getExceptionCode());
		}finally{
		 verifyMocks();
		}
  }
	
	public void testUpdateRegistryStatusScreenFailureWithPrimaryReason(){
	  	PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus1 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
	  	RegistryStatus registryStatus1 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus2 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        RegistryStatus registryStatus2 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus3 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        RegistryStatus registryStatus3 = registerMockFor(RegistryStatus.class);
        
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus4 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus5 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        
        EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);

		EasyMock.expect(studySite.getStudy()).andReturn(study);
		EasyMock.expect(study.getPermissibleStudySubjectRegistryStatuses()).andReturn(Arrays.asList(new PermissibleStudySubjectRegistryStatus[]{permissibleStudySubjectRegistryStatus1,
				permissibleStudySubjectRegistryStatus2, permissibleStudySubjectRegistryStatus3, permissibleStudySubjectRegistryStatus4, permissibleStudySubjectRegistryStatus5}));
		EasyMock.expect(permissibleStudySubjectRegistryStatus1.getRegistryStatus()).andReturn(registryStatus1);
		EasyMock.expect(registryStatus1.getCode()).andReturn("Pre-Enrolled");
		EasyMock.expect(permissibleStudySubjectRegistryStatus2.getRegistryStatus()).andReturn(registryStatus2);
		EasyMock.expect(registryStatus2.getCode()).andReturn("Enrolled");
		EasyMock.expect(permissibleStudySubjectRegistryStatus3.getRegistryStatus()).andReturn(registryStatus3).times(2);
		EasyMock.expect(registryStatus3.getCode()).andReturn("Screen Failed");
		EasyMock.expect(registryStatus3.getPrimaryReasons()).andReturn(Arrays.asList(new RegistryStatusReason[]{new RegistryStatusReason("A","A",null,true), 
																					new RegistryStatusReason("B","B",null,true)}));
	  	replayMocks();
	  	studySubject.setStudySite(studySite);
	  	Date date = new Date();
	  	studySubject.updateRegistryStatus("Screen Failed", date, null, Arrays.asList(new RegistryStatusReason[]{new RegistryStatusReason("B","B",null,true)}));
		assertEquals(1, studySubject.getStudySubjectRegistryStatusHistoryInternal().size());
		assertEquals(date, studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getEffectiveDate());
		assertEquals(permissibleStudySubjectRegistryStatus3, studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getPermissibleStudySubjectRegistryStatus());
		assertEquals(1, studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getReasons().size());
		assertEquals("B", studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getReasons().get(0).getCode());
		verifyMocks();
  }
	
	public void testUpdateRegistryStatusScreenFailureWithInvalidSecondaryReason(){
	  	PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus1 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
	  	RegistryStatus registryStatus1 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus2 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        RegistryStatus registryStatus2 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus3 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        RegistryStatus registryStatus3 = registerMockFor(RegistryStatus.class);
        
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus4 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus5 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        
        EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);

		EasyMock.expect(studySite.getStudy()).andReturn(study);
		EasyMock.expect(study.getPermissibleStudySubjectRegistryStatuses()).andReturn(Arrays.asList(new PermissibleStudySubjectRegistryStatus[]{permissibleStudySubjectRegistryStatus1,
				permissibleStudySubjectRegistryStatus2, permissibleStudySubjectRegistryStatus3, permissibleStudySubjectRegistryStatus4, permissibleStudySubjectRegistryStatus5}));
		EasyMock.expect(permissibleStudySubjectRegistryStatus1.getRegistryStatus()).andReturn(registryStatus1);
		EasyMock.expect(registryStatus1.getCode()).andReturn("Pre-Enrolled");
		EasyMock.expect(permissibleStudySubjectRegistryStatus2.getRegistryStatus()).andReturn(registryStatus2);
		EasyMock.expect(registryStatus2.getCode()).andReturn("Enrolled");
		EasyMock.expect(permissibleStudySubjectRegistryStatus3.getRegistryStatus()).andReturn(registryStatus3).times(2);
		EasyMock.expect(registryStatus3.getCode()).andReturn("Screen Failed");
		EasyMock.expect(registryStatus3.getPrimaryReasons()).andReturn(Arrays.asList(new RegistryStatusReason[]{new RegistryStatusReason("A","A",null,true), 
																					new RegistryStatusReason("B","B",null,true)}));
		EasyMock.expect(permissibleStudySubjectRegistryStatus3.getSecondaryReasons()).andReturn(Arrays.asList(new RegistryStatusReason[]{new RegistryStatusReason("P","P",null,false), 
				new RegistryStatusReason("Q","Q",null,false)}));
	  	replayMocks();
	  	studySubject.setStudySite(studySite);
	  	Date date = new Date();
	  	try {
	  		studySubject.updateRegistryStatus("Screen Failed", date, null, Arrays.asList(new RegistryStatusReason[]{new RegistryStatusReason("A","A",null,true), 
	  																											new RegistryStatusReason("C","C",null,false)}));
	  		fail();
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(601, e.getExceptionCode());
		}finally{
		 verifyMocks();
		}
  }
	
	public void testUpdateRegistryStatusScreenFailureWithSecondaryReason(){
	  	PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus1 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
	  	RegistryStatus registryStatus1 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus2 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        RegistryStatus registryStatus2 = registerMockFor(RegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus3 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        RegistryStatus registryStatus3 = registerMockFor(RegistryStatus.class);
        
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus4 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus5 = registerMockFor(PermissibleStudySubjectRegistryStatus.class);
        
        EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
    	EasyMock.expect(studySiteStudyVersion.getStudySite()).andReturn(studySite);

		EasyMock.expect(studySite.getStudy()).andReturn(study);
		EasyMock.expect(study.getPermissibleStudySubjectRegistryStatuses()).andReturn(Arrays.asList(new PermissibleStudySubjectRegistryStatus[]{permissibleStudySubjectRegistryStatus1,
				permissibleStudySubjectRegistryStatus2, permissibleStudySubjectRegistryStatus3, permissibleStudySubjectRegistryStatus4, permissibleStudySubjectRegistryStatus5}));
		EasyMock.expect(permissibleStudySubjectRegistryStatus1.getRegistryStatus()).andReturn(registryStatus1);
		EasyMock.expect(registryStatus1.getCode()).andReturn("Pre-Enrolled");
		EasyMock.expect(permissibleStudySubjectRegistryStatus2.getRegistryStatus()).andReturn(registryStatus2);
		EasyMock.expect(registryStatus2.getCode()).andReturn("Enrolled");
		EasyMock.expect(permissibleStudySubjectRegistryStatus3.getRegistryStatus()).andReturn(registryStatus3).times(2);
		EasyMock.expect(registryStatus3.getCode()).andReturn("Screen Failed");
		EasyMock.expect(registryStatus3.getPrimaryReasons()).andReturn(Arrays.asList(new RegistryStatusReason[]{new RegistryStatusReason("A","A",null,true), 
																					new RegistryStatusReason("B","B",null,true)}));
		EasyMock.expect(permissibleStudySubjectRegistryStatus3.getSecondaryReasons()).andReturn(Arrays.asList(new RegistryStatusReason[]{new RegistryStatusReason("P","P",null,false), 
				new RegistryStatusReason("Q","Q",null,false)}));
		replayMocks();
	  	studySubject.setStudySite(studySite);
	  	Date date = new Date();
	  	studySubject.updateRegistryStatus("Screen Failed", date, null, Arrays.asList(new RegistryStatusReason[]{new RegistryStatusReason("A","A",null,true), 
					new RegistryStatusReason("Q","Q",null,false)}));
		assertEquals(1, studySubject.getStudySubjectRegistryStatusHistoryInternal().size());
		assertEquals(date, studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getEffectiveDate());
		assertEquals(permissibleStudySubjectRegistryStatus3, studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getPermissibleStudySubjectRegistryStatus());
		assertEquals(2, studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getReasons().size());
		assertEquals("A", studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getReasons().get(0).getCode());
		assertEquals("Q", studySubject.getStudySubjectRegistryStatusHistoryInternal().get(0).getReasons().get(1).getCode());
		verifyMocks();
  }
	
	public void testGetStudySubjectRegistryStatusHistory(){
        Date date5= new GregorianCalendar(2010, 01, 30).getTime();
        Date date4= new GregorianCalendar(2009, 01, 30).getTime();
        Date date3= new GregorianCalendar(2008, 01, 30).getTime();
        Date date2= new GregorianCalendar(2007, 01, 30).getTime();
        Date date1= new GregorianCalendar(2006, 01, 30).getTime();
        StudySubjectRegistryStatus studySubjectRegistryStatus = new StudySubjectRegistryStatus(date2,null, null);
        studySubject.getStudySubjectRegistryStatusHistoryInternal().add(studySubjectRegistryStatus);
        studySubjectRegistryStatus = new StudySubjectRegistryStatus(date4,null, null);
        studySubject.getStudySubjectRegistryStatusHistoryInternal().add(studySubjectRegistryStatus);
        studySubjectRegistryStatus = new StudySubjectRegistryStatus(date3,null, null);
        studySubject.getStudySubjectRegistryStatusHistoryInternal().add(studySubjectRegistryStatus);
        studySubjectRegistryStatus = new StudySubjectRegistryStatus(date5,null, null);
        studySubject.getStudySubjectRegistryStatusHistoryInternal().add(studySubjectRegistryStatus);
        studySubjectRegistryStatus = new StudySubjectRegistryStatus(date1,null, null);
        studySubject.getStudySubjectRegistryStatusHistoryInternal().add(studySubjectRegistryStatus);
        
        List<StudySubjectRegistryStatus> history = studySubject.getStudySubjectRegistryStatusHistory();
        assertEquals(date5, history.get(0).getEffectiveDate());
        assertEquals(date4, history.get(1).getEffectiveDate());
        assertEquals(date3, history.get(2).getEffectiveDate());
        assertEquals(date2, history.get(3).getEffectiveDate());
        assertEquals(date1, history.get(4).getEffectiveDate());
        
        List<StudySubjectRegistryStatus> stored = studySubject.getStudySubjectRegistryStatusHistoryInternal();
        assertEquals(date2, stored.get(0).getEffectiveDate());
        assertEquals(date4, stored.get(1).getEffectiveDate());
        assertEquals(date3, stored.get(2).getEffectiveDate());
        assertEquals(date5, stored.get(3).getEffectiveDate());
        assertEquals(date1, stored.get(4).getEffectiveDate());
  }
	
	public void testGetStudySubjectRegistryStatus(){
        Date date5= new GregorianCalendar(2010, 01, 30).getTime();
        Date date4= new GregorianCalendar(2009, 01, 30).getTime();
        Date date3= new GregorianCalendar(2008, 01, 30).getTime();
        Date date2= new GregorianCalendar(2007, 01, 30).getTime();
        Date date1= new GregorianCalendar(2006, 01, 30).getTime();
        StudySubjectRegistryStatus studySubjectRegistryStatus = new StudySubjectRegistryStatus(date2,null, null);
        studySubject.getStudySubjectRegistryStatusHistoryInternal().add(studySubjectRegistryStatus);
        studySubjectRegistryStatus = new StudySubjectRegistryStatus(date4,null, null);
        studySubject.getStudySubjectRegistryStatusHistoryInternal().add(studySubjectRegistryStatus);
        studySubjectRegistryStatus = new StudySubjectRegistryStatus(date3,null, null);
        studySubject.getStudySubjectRegistryStatusHistoryInternal().add(studySubjectRegistryStatus);
        studySubjectRegistryStatus = new StudySubjectRegistryStatus(date5,null, null);
        studySubject.getStudySubjectRegistryStatusHistoryInternal().add(studySubjectRegistryStatus);
        studySubjectRegistryStatus = new StudySubjectRegistryStatus(date1,null, null);
        studySubject.getStudySubjectRegistryStatusHistoryInternal().add(studySubjectRegistryStatus);
        
        assertEquals(date5, studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
  }
}
