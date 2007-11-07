package edu.duke.cabig.c3pr.service;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.NonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.PhonecallRandomization;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 4, 2007
 * Time: 2:33:16 PM
 * To change this template use File | Settings | File Templates.
 */

public class StudySubjectServiceTestCase extends DaoTestCase{

    private StudySubjectService studySubjectService;
    private StudyCreationHelper studyCreationHelper=new StudyCreationHelper();
    private StudyDao dao = (StudyDao) getApplicationContext().getBean("studyDao");
    private HealthcareSiteDao healthcareSitedao = (HealthcareSiteDao) getApplicationContext().getBean("healthcareSiteDao");
    private ParticipantDao participantDao = (ParticipantDao) getApplicationContext().getBean("participantDao");
    private StudySubjectDao studySubjectDao=(StudySubjectDao) getApplicationContext().getBean("studySubjectDao");
    protected void setUp() throws Exception {
        super.setUp();    
        studySubjectService = (StudySubjectService) getApplicationContext().getBean("studySubjectService");
    }
    
	/**	Registration Data Entry Status test
	 * blank Study Subject
	*/	
	public void testEvaluateRegistrationDataEntryStatusInComplete() throws Exception{
		StudySubject studySubject=new StudySubject();
        assertEquals("Wrong Registration Data Entry Status",RegistrationDataEntryStatus.INCOMPLETE, studySubjectService.evaluateRegistrationDataEntryStatus(studySubject));
	}

	/**	Registration Data Entry Status test
	 *	InformedConsent Date Filled
	 *	InformedConsent Version Filled
	*/	
	public void testEvaluateRegistrationDataEntryStatusComplete(){
		StudySubject studySubject=new StudySubject();
		studySubject.setInformedConsentSignedDate(new Date());
		studySubject.setInformedConsentVersion("1.0");
        assertEquals("Wrong Registration Data Entry Status",RegistrationDataEntryStatus.COMPLETE, studySubjectService.evaluateRegistrationDataEntryStatus(studySubject));
	}

	/**	Epoch Data Entry Status test
	 * Randomized Treatment Epoch
	 * Blank Scheduled Epoch
	*/	
	public void testEvaluateEpochDataEntryStatusInComplete1() throws Exception{
		StudySubject studySubject=new StudySubject();
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        buildCommandObject(studySubject);
        assertEquals("Wrong Epoch Data Entry Status",ScheduledEpochDataEntryStatus.INCOMPLETE, studySubjectService.evaluateScheduledEpochDataEntryStatus(studySubject));
	}

	/**	Epoch Data Entry Status test
	 * Randomized Treatment Epoch
	 * Eligibility Done
	*/	
	public void testEvaluateEpochDataEntryStatusInComplete2() throws Exception{
		StudySubject studySubject=new StudySubject();
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(createTestTreatmentEpoch(true));
        ((TreatmentEpoch)scheduledEpochFirst.getEpoch()).getArms().size();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        buildCommandObject(studySubject);
        bindEligibility(studySubject);
        assertEquals("Wrong Epoch Data Entry Status",ScheduledEpochDataEntryStatus.INCOMPLETE, studySubjectService.evaluateScheduledEpochDataEntryStatus(studySubject));
	}

	/**	Epoch Data Entry Status test
	 * Non Randomized Treatment Epoch with Arms
	 * Eligibility Done	 
	 * Stratification Done
	 * Arm not assigned
	*/	
	public void testEvaluateEpochDataEntryStatusInComplete3() throws Exception{
		StudySubject studySubject=new StudySubject();
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(createTestTreatmentEpoch(false));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        buildCommandObject(studySubject);
        bindEligibility(studySubject);
        bindStratification(studySubject);
        assertEquals("Wrong Epoch Data Entry Status",ScheduledEpochDataEntryStatus.INCOMPLETE, studySubjectService.evaluateScheduledEpochDataEntryStatus(studySubject));
	}

	/**	Epoch Data Entry Status test
	 * Randomized Treatment Epoch
	 * Eligibility Done	 
	 * Stratification Done
	*/	
	public void testEvaluateEpochDataEntryStatusComplete1() throws Exception{
		StudySubject studySubject=new StudySubject();
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        buildCommandObject(studySubject);
        bindEligibility(studySubject);
        bindStratification(studySubject);
        assertEquals("Wrong Epoch Data Entry Status",ScheduledEpochDataEntryStatus.COMPLETE, studySubjectService.evaluateScheduledEpochDataEntryStatus(studySubject));
	}

	/**	Epoch Data Entry Status test
	 * Non Randomized Treatment Epoch
	 * Eligibility Done	 
	 * Stratification Done
	 * Assigned Arm
	*/	
	public void testEvaluateEpochDataEntryStatusComplete2() throws Exception{
		StudySubject studySubject=new StudySubject();
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        ScheduledArm scheduledArm=new ScheduledArm();
        scheduledArm.setArm(new Arm());
        ((ScheduledTreatmentEpoch)scheduledEpochFirst).addScheduledArm(scheduledArm);
        scheduledEpochFirst.setEpoch(createTestTreatmentEpoch(false));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        buildCommandObject(studySubject);
        bindEligibility(studySubject);
        bindStratification(studySubject);
        assertEquals("Wrong Epoch Data Entry Status",ScheduledEpochDataEntryStatus.COMPLETE, studySubjectService.evaluateScheduledEpochDataEntryStatus(studySubject));
	}

	/**	Epoch Data Entry Status test
	 * Non Treatment Epoch
	*/	
	public void testEvaluateEpochDataEntryStatusComplete3() throws Exception{
		StudySubject studySubject=new StudySubject();
        studySubject.addScheduledEpoch(new ScheduledNonTreatmentEpoch());
        buildCommandObject(studySubject);
        assertEquals("Wrong Epoch Data Entry Status",ScheduledEpochDataEntryStatus.COMPLETE, studySubjectService.evaluateScheduledEpochDataEntryStatus(studySubject));
	}

	/**	Epoch Workflow Status test
	 * Not Multi Site Trial
	 * Non Treatment Epoch
	 * Epoch Data Entry Status: Incomplete 	 
	 * Registration Data Entry Status: Complete
	*/	
	public void testManageSchEpochWorkFlowStatusIfUnAppCase0() throws Exception{
		StudySubject studySubject=new StudySubject();
		StudySite site=new StudySite();
		Study study=new Study();
		study.setMultiInstitutionIndicator(Boolean.TRUE);
		site.setStudy(study);
        studySubject.setStudySite(site);
        studySubject.addScheduledEpoch(new ScheduledNonTreatmentEpoch());
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.INCOMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubjectService.manageSchEpochWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status",ScheduledEpochWorkFlowStatus.UNAPPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
	}

	/**	Epoch Workflow Status test
	 * Not Multi Site Trial
	 * Non Treatment Epoch
	 * Epoch Data Entry Status: Complete 	 
	 * Registration Data Entry Status: Complete
	*/	
	public void testManageSchEpochWorkFlowStatusIfUnAppCase1()throws Exception{
		StudySubject studySubject=new StudySubject();
		StudySite site=new StudySite();
		Study study=new Study();
		study.setMultiInstitutionIndicator(Boolean.FALSE);
		site.setStudy(study);
        studySubject.setStudySite(site);
        studySubject.addScheduledEpoch(new ScheduledNonTreatmentEpoch());
        studySubject.getScheduledEpoch().setEpoch(new NonTreatmentEpoch());
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubjectService.manageSchEpochWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status",ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
	}
	
	/**	Epoch Workflow Status test
	 * Not Multi Site Trial
	 * Randomized Treatment Epoch
	 * Epoch Data Entry Status: Complete 	 
	 * Registration Data Entry Status: Complete
	 * not assigned an Arm
	*/	
	public void testManageSchEpochWorkFlowStatusIfUnAppCase2()throws Exception{
		StudySubject studySubject=new StudySubject();
		StudySite site=new StudySite();
		Study study=new Study();
		study.setMultiInstitutionIndicator(Boolean.FALSE);
		site.setStudy(study);
        studySubject.setStudySite(site);
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        try {
			studySubjectService.manageSchEpochWorkFlow(studySubject);
		} catch (Exception e) {
		}
        assertEquals("Wrong Epoch WorkFlow Status",ScheduledEpochWorkFlowStatus.UNAPPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
	}

	/**	Epoch Workflow Status test
	 * Not Multi Site Trial
	 * Randomized Treatment Epoch
	 * Epoch Data Entry Status: Complete 	 
	 * Registration Data Entry Status: Complete
	 * Arm assigned
	*/	
	public void testManageSchEpochWorkFlowStatusIfUnAppCase3()throws Exception{
		StudySubject studySubject=new StudySubject();
		StudySite site=new StudySite();
		Study study=new Study();
		study.setMultiInstitutionIndicator(Boolean.FALSE);
		study.setRandomizationType(RandomizationType.PHONE_CALL);
		site.setStudy(study);
        studySubject.setStudySite(site);
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        bindRandomization(studySubject,RandomizationType.PHONE_CALL);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubjectService.manageSchEpochWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status",ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
	}

	/**	Epoch Workflow Status test
	 * Not Multi Site Trial
	 * Non Randomized Treatment Epoch
	 * Epoch Data Entry Status: Complete 	 
	 * Registration Data Entry Status: Complete
	*/	
	public void testManageSchEpochWorkFlowStatusIfUnAppCase4()throws Exception{
		StudySubject studySubject=new StudySubject();
		StudySite site=new StudySite();
		Study study=new Study();
		study.setMultiInstitutionIndicator(Boolean.FALSE);
		site.setStudy(study);
        studySubject.setStudySite(site);
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(createTestTreatmentEpoch(false));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubjectService.manageSchEpochWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status",ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
	}

	/**	Epoch Workflow Status test
	 * Multi Site Trial
	 * Randomized Treatment Epoch
	 * not Hosted Mode
	 * Epoch Data Entry Status: Complete 	 
	 * Registration Data Entry Status: Complete
	*/	
	public void testManageSchEpochWorkFlowStatusIfUnAppCase5()throws Exception{
		StudySubject studySubject=new StudySubject();
		StudySite site=new StudySite();
		Study study=new Study();
		StudyCoordinatingCenter stC=study.getStudyCoordinatingCenters().get(0);
		stC.setStudy(study);
		HealthcareSite healthcareSite=new HealthcareSite();
		healthcareSite.setName("test name");
		healthcareSite.setNciInstituteCode("test code");
		stC.setHealthcareSite(healthcareSite);
		study.setMultiInstitutionIndicator(Boolean.TRUE);
		site.setStudy(study);
        studySubject.setStudySite(site);
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        try {
        	studySubjectService.setHostedMode(false);
			studySubjectService.manageSchEpochWorkFlow(studySubject);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Wrong Epoch WorkFlow Status",ScheduledEpochWorkFlowStatus.DISAPPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
		}
        assertEquals("Wrong Epoch WorkFlow Status",ScheduledEpochWorkFlowStatus.PENDING, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
	}

	/**	Epoch Workflow Status test
	 * Multi Site Trial
	 * Hosted Mode
	 * Randomized Treatment Epoch
	 * Epoch Data Entry Status: Complete 	 
	 * Registration Data Entry Status: Complete
	*/	
	public void testManageSchEpochWorkFlowStatusIfUnAppCase6()throws Exception{
		StudySubject studySubject=new StudySubject();
		StudySite site=new StudySite();
		Study study=new Study();
		study.setMultiInstitutionIndicator(Boolean.TRUE);
		study.setRandomizationType(RandomizationType.PHONE_CALL);
		site.setStudy(study);
        studySubject.setStudySite(site);
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        bindRandomization(studySubject,RandomizationType.PHONE_CALL);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        try {
        	studySubjectService.setHostedMode(true);
			studySubjectService.manageSchEpochWorkFlow(studySubject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertEquals("Wrong Epoch WorkFlow Status",ScheduledEpochWorkFlowStatus.DISAPPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
		}
		assertEquals("Wrong Epoch WorkFlow Status",ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
	}
	
	/**	Regisrtation Workflow Status test
	 * Epoch Workflow Status: Unapproved 	 
	 * Registration Data Entry Status: Incomplete
	*/	
	public void testManageRegWorkFlowIfUnRegCase0()throws Exception{
		StudySubject studySubject=new StudySubject();
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.INCOMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status",RegistrationWorkFlowStatus.UNREGISTERED, studySubject.getRegWorkflowStatus());
	}
	
	/**	Regisrtation Workflow Status test
	 * Epoch Workflow Status: Unapproved 	 
	 * Registration Data Entry Status: Complete
	*/	
	public void testManageRegWorkFlowIfUnRegCase1()throws Exception{
		StudySubject studySubject=new StudySubject();
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status",RegistrationWorkFlowStatus.UNREGISTERED, studySubject.getRegWorkflowStatus());
	}

	/**	Regisrtation Workflow Status test
	 * Epoch Workflow Status: Disaaproved 	 
	 * Registration Data Entry Status: Complete
	*/	
	public void testManageRegWorkFlowIfUnRegCase2()throws Exception{
		StudySubject studySubject=new StudySubject();
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.DISAPPROVED);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status",RegistrationWorkFlowStatus.DISAPPROVED, studySubject.getRegWorkflowStatus());
	}

	/**	Regisrtation Workflow Status test
	 * Epoch Workflow Status: Pending 	 
	 * Registration Data Entry Status: Complete
	*/	
	public void testManageRegWorkFlowIfUnRegCase3()throws Exception{
		StudySubject studySubject=new StudySubject();
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status",RegistrationWorkFlowStatus.PENDING, studySubject.getRegWorkflowStatus());
	}

	/**	Regisrtation Workflow Status test
	 * Epoch Workflow Status: Approved 	 
	 * Treatment Epoch
	 * Registration Data Entry Status: Complete
	*/	
	public void testManageRegWorkFlowIfUnRegCase4()throws Exception{
		StudySubject studySubject=new StudySubject();
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
        studySubject.getScheduledEpoch().setEpoch(new TreatmentEpoch());
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status",RegistrationWorkFlowStatus.REGISTERED, studySubject.getRegWorkflowStatus());
	}

	/**	Regisrtation Workflow Status test
	 * Reserving Non Treatment Epoch
	 * Epoch Workflow Status: Unapproved 	 
	 * Registration Data Entry Status: Complete
	*/	
	public void testManageRegWorkFlowIfUnRegCase5()throws Exception{
		StudySubject studySubject=new StudySubject();
        ScheduledNonTreatmentEpoch scheduledEpochFirst=new ScheduledNonTreatmentEpoch();
        NonTreatmentEpoch nonTreatmentEpoch=new NonTreatmentEpoch();
        nonTreatmentEpoch.setReservationIndicator(Boolean.TRUE);
        scheduledEpochFirst.setEpoch(nonTreatmentEpoch);
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status",RegistrationWorkFlowStatus.RESERVED, studySubject.getRegWorkflowStatus());
	}

	/**	Regisrtation Workflow Status test
	 * Non Reserving Non Enrolling Non Treatment Epoch
	 * Epoch Workflow Status: Approved 	 
	 * Registration Data Entry Status: Complete
	*/	
	public void testManageRegWorkFlowIfUnRegCase6()throws Exception{
		StudySubject studySubject=new StudySubject();
        ScheduledNonTreatmentEpoch scheduledEpochFirst=new ScheduledNonTreatmentEpoch();
        NonTreatmentEpoch nonTreatmentEpoch=new NonTreatmentEpoch();
        nonTreatmentEpoch.setReservationIndicator(Boolean.FALSE);
        scheduledEpochFirst.setEpoch(nonTreatmentEpoch);
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status",RegistrationWorkFlowStatus.UNREGISTERED, studySubject.getRegWorkflowStatus());
	}

	/**	Regisrtation Workflow Status test
	 * Non Reserving Enrolling Non Treatment Epoch
	 * Epoch Workflow Status: Approved 	 
	 * Registration Data Entry Status: Complete
	*/	
	public void testManageRegWorkFlowIfUnRegCase7()throws Exception{
		StudySubject studySubject=new StudySubject();
        ScheduledNonTreatmentEpoch scheduledEpochFirst=new ScheduledNonTreatmentEpoch();
        NonTreatmentEpoch nonTreatmentEpoch=new NonTreatmentEpoch();
        nonTreatmentEpoch.setReservationIndicator(Boolean.FALSE);
        nonTreatmentEpoch.setEnrollmentIndicator(Boolean.TRUE);
        scheduledEpochFirst.setEpoch(nonTreatmentEpoch);
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status",RegistrationWorkFlowStatus.REGISTERED, studySubject.getRegWorkflowStatus());
	}

	/* Test Cases for createRegistration
	 * Multi Site Trial
	 * Multi Site Mode
	 * Treatment Epoch
	 * Book Randomization
	 */
	public void testCreateRegistrationCase0()throws Exception{
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getMultiSiteRandomizedStudy(RandomizationType.BOOK, false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
		studySubjectService.setHostedMode(false);
        Integer savedId;
        {
            addScheduledEpoch(studySubject,true);
            buildCommandObject(studySubject);
            addEnrollmentDetails(studySubject);
            bindEligibility(studySubject);
            bindStratification(studySubject);
            bindRandomization(studySubject,RandomizationType.BOOK);
            StudySubject saved= studySubjectService.registerSubject(studySubject);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 1, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded.getIfTreatmentScheduledEpoch());
        	ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)loaded.getScheduledEpoch();
        	assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch.getEligibilityIndicator().booleanValue());
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());        	
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.PENDING, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.PENDING, loaded.getScheduledEpoch().getScEpochWorkflowStatus());        	
        }
        interruptSession();

	}

	/* Test Cases for createRegistration
	 * Multi Site Trial
	 * Multi Site Mode
	 * Treatment Epoch
	 * Callout Randomization
	 */
	public void testCreateRegistrationCase1()throws Exception{
		studySubjectService.setHostedMode(false);
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getMultiSiteRandomizedStudy(RandomizationType.CALL_OUT, false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            addScheduledEpoch(studySubject,true);
            buildCommandObject(studySubject);
            addEnrollmentDetails(studySubject);
            bindEligibility(studySubject);
            bindStratification(studySubject);
            bindRandomization(studySubject,RandomizationType.CALL_OUT);
            StudySubject saved= studySubjectService.registerSubject(studySubject);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 1, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded.getIfTreatmentScheduledEpoch());
        	ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)loaded.getScheduledEpoch();
        	assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch.getEligibilityIndicator().booleanValue());
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());        	
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.PENDING, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.PENDING, loaded.getScheduledEpoch().getScEpochWorkflowStatus());        	
        }
        interruptSession();

	}

	/* Test Cases for createRegistration
	 * Multi Site Trial
	 * Multi Site Mode
	 * Non Treatment Epoch, Non Reserving, Non Registering
	 * Non Randomized
	 */
	public void testCreateRegistrationCase2()throws Exception{
		studySubjectService.setHostedMode(false);
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getMultiSiteNonRandomizedStudy(false, false, false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            addScheduledEpoch(studySubject,false);
            buildCommandObject(studySubject);
            addEnrollmentDetails(studySubject);
            StudySubject saved= studySubjectService.registerSubject(studySubject);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 0, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded.getIfTreatmentScheduledEpoch());
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());        	
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.UNREGISTERED, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());        	
        }
        interruptSession();

	}

	/* Test Cases for createRegistration
	 * Local Trial
	 * Treatment Epoch
	 * Phone Call Randomization
	 */
	public void testCreateRegistrationCase3()throws Exception{
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getLocalRandomizedStudy(RandomizationType.PHONE_CALL, false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            addScheduledEpoch(studySubject,true);
            buildCommandObject(studySubject);
            addEnrollmentDetails(studySubject);
            bindEligibility(studySubject);
            bindStratification(studySubject);
            OrganizationAssignedIdentifier id=studySubject.getOrganizationAssignedIdentifiers().get(0);
            id.setHealthcareSite(healthcareSitedao.getById(1002));
            id.setType("Test1");
            id.setValue("Test1");
            StudySubject saved=studySubjectDao.merge(studySubject);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            OrganizationAssignedIdentifier id=loaded.getOrganizationAssignedIdentifiers().get(1);
            id.setHealthcareSite(healthcareSitedao.getById(1002));
            id.setType("Test2");
            id.setValue("Test2");
            //studySubjectDao.reassociate(loaded);
            loaded=studySubjectDao.merge(loaded);
            savedId= loaded.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
            bindRandomization(loaded,RandomizationType.PHONE_CALL);
            StudySubject saved= studySubjectService.registerSubject(loaded);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 1, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded.getIfTreatmentScheduledEpoch());
        	ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)loaded.getScheduledEpoch();
        	assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch.getEligibilityIndicator().booleanValue());
        	assertEquals("Wrong number of identifier", 2, loaded.getIdentifiers().size());        	
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());        	
        }
	}

	/* Test Cases for createRegistration
	 * Local Trial
	 * Treatment Epoch
	 * Book Randomization
	 */
	public void testCreateRegistrationCase4()throws Exception{
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getLocalRandomizedStudy(RandomizationType.BOOK, false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            addScheduledEpoch(studySubject,true);
            buildCommandObject(studySubject);
            addEnrollmentDetails(studySubject);
            bindEligibility(studySubject);
            bindStratification(studySubject);
            StudySubject saved=studySubjectDao.merge(studySubject);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
            bindRandomization(loaded,RandomizationType.BOOK);
            StudySubject saved= studySubjectService.registerSubject(loaded);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 1, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded.getIfTreatmentScheduledEpoch());
        	ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)loaded.getScheduledEpoch();
        	assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch.getEligibilityIndicator().booleanValue());
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());        	
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

        XmlMarshaller marshaller = new XmlMarshaller("ccts-registration-castor-mapping.xml");
        StudySubject loaded = studySubjectDao.getById(savedId);
        SystemAssignedIdentifier systemAssignedIdentifier=new SystemAssignedIdentifier();
        systemAssignedIdentifier.setType("MRN");
        systemAssignedIdentifier.setValue("MRN-12A!2121");
        systemAssignedIdentifier.setSystemName("C3PR");
        loaded.getParticipant().getSystemAssignedIdentifiers().add(systemAssignedIdentifier);
        String xml = marshaller.toXML(loaded);
        System.out.println(xml);
        assertNotNull(xml);
    }

    /* Test Cases for createRegistration
	 * Local Trial
	 * Non Randomized Treatment Epoch
	 */
	public void testCreateRegistrationCase5()throws Exception{
		//interruptSession();
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getLocalNonRandomizedWithArmStudy(false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        addScheduledEpoch(studySubject,true);
        buildCommandObject(studySubject);
        addEnrollmentDetails(studySubject);
        bindEligibility(studySubject);
        bindStratification(studySubject);

        {
            StudySubject saved= studySubjectService.registerSubject(studySubject);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 1, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded.getIfTreatmentScheduledEpoch());
        	ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)loaded.getScheduledEpoch();
        	assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch.getEligibilityIndicator().booleanValue());
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.INCOMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());        	
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.UNREGISTERED, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.UNAPPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

	}

	/* Test Cases for createRegistration
	 * Local Trial
	 * NonTreatment Epoch Reserving
	 * Non Randomized
	 */
	public void testCreateRegistrationCase6()throws Exception{
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getLocalNonRandomizedStudy(true, false, false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        addScheduledEpoch(studySubject,false);
        addEnrollmentDetails(studySubject);
        buildCommandObject(studySubject);
        {
	        StudySubject saved= studySubjectService.registerSubject(studySubject);
	        savedId= saved.getId().intValue();
	        assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 0, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded.getIfTreatmentScheduledEpoch());
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());        	
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.RESERVED, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());        	
        }
        interruptSession();

	}

	/* Test Cases for createRegistration
	 * Local Trial
	 * NonTreatment Epoch Enrolling
	 * Non Randomized
	 */
	public void testCreateRegistrationCase7()throws Exception{
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getLocalNonRandomizedStudy(false, true, false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        addScheduledEpoch(studySubject,false);
        addEnrollmentDetails(studySubject);
        buildCommandObject(studySubject);
        {
	        StudySubject saved= studySubjectService.registerSubject(studySubject);
	        savedId= saved.getId().intValue();
	        assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 0, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded.getIfTreatmentScheduledEpoch());
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());        	
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());        	
        }
        interruptSession();

	}

	/* Test Cases for createRegistration
	 * Local Trial
	 * NonTreatment Epoch Non Enrolling, Non Reserving
	 * Non Randomized
	 */
	public void testCreateRegistrationCase8()throws Exception{
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getLocalNonRandomizedStudy(false, false, false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        addScheduledEpoch(studySubject,false);
        addEnrollmentDetails(studySubject);
        buildCommandObject(studySubject);
        {
	        StudySubject saved= studySubjectService.registerSubject(studySubject);
	        savedId= saved.getId().intValue();
	        assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 0, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded.getIfTreatmentScheduledEpoch());
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());        	
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.UNREGISTERED, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());        	
        }
        interruptSession();

	}

	/* Test Cases for createRegistration
	 * Multi Site Trial
	 * Multi Site Mode
	 * Non Treatment Epoch, Reserving, Non Registering
	 * Non Randomized
	 */
	public void testCreateRegistrationCase9()throws Exception{
		studySubjectService.setHostedMode(false);
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getMultiSiteNonRandomizedStudy(true, false, false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            addScheduledEpoch(studySubject,false);
            buildCommandObject(studySubject);
            addEnrollmentDetails(studySubject);
            StudySubject saved= studySubjectService.registerSubject(studySubject);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 0, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded.getIfTreatmentScheduledEpoch());
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());        	
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.RESERVED, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());        	
        }
        interruptSession();

	}

	/* Test Cases for createRegistration
	 * Multi Site Trial
	 * Hosted Mode
	 * Treatment Epoch
	 * Book Randomization
	 */
	public void testCreateRegistrationCase10()throws Exception{
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getMultiSiteRandomizedStudy(RandomizationType.BOOK, false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
		studySubjectService.setHostedMode(true);
        Integer savedId;
        {
            addScheduledEpoch(studySubject,true);
            buildCommandObject(studySubject);
            addEnrollmentDetails(studySubject);
            bindEligibility(studySubject);
            bindStratification(studySubject);
            bindRandomization(studySubject,RandomizationType.BOOK);
            StudySubject saved= studySubjectService.registerSubject(studySubject);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 1, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded.getIfTreatmentScheduledEpoch());
        	ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)loaded.getScheduledEpoch();
        	assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch.getEligibilityIndicator().booleanValue());
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());        	
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());        	
        }
        interruptSession();

	}

	/* Test Cases for createRegistration
	 * Multi Site Trial
	 * Hosted Mode
	 * Non Treatment Epoch, Non Reserving, Non Registering
	 * Non Randomized
	 */
	public void testCreateRegistrationCase11()throws Exception{
		studySubjectService.setHostedMode(true);
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getMultiSiteNonRandomizedStudy(false, false, false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            addScheduledEpoch(studySubject,false);
            buildCommandObject(studySubject);
            addEnrollmentDetails(studySubject);
            StudySubject saved= studySubjectService.registerSubject(studySubject);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 0, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded.getIfTreatmentScheduledEpoch());
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());        	
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.UNREGISTERED, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());        	
        }
        interruptSession();

	}

	/* Test Cases for createRegistration
	 * Multi Site Trial
	 * Hosted Mode
	 * Non Treatment Epoch, Reserving, Non Registering
	 * Non Randomized
	 */
	public void testCreateRegistrationCase12()throws Exception{
		studySubjectService.setHostedMode(true);
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getMultiSiteNonRandomizedStudy(true, false, false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            addScheduledEpoch(studySubject,false);
            buildCommandObject(studySubject);
            addEnrollmentDetails(studySubject);
            StudySubject saved= studySubjectService.registerSubject(studySubject);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 0, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded.getIfTreatmentScheduledEpoch());
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());        	
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.RESERVED, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());        	
        }
        interruptSession();

	}
	
	/* Test Cases for createRegistration
	 * Multi Site Trial
	 * Multi Site Mode
	 * Treatment Epoch
	 * Book Randomization
	 * Study Site is Co Ordinating Center
	 */
	public void testCreateRegistrationCase13()throws Exception{
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getMultiSiteRandomizedStudy(RandomizationType.BOOK, true).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            addScheduledEpoch(studySubject,true);
            buildCommandObject(studySubject);
            addEnrollmentDetails(studySubject);
            bindEligibility(studySubject);
            bindStratification(studySubject);
            StudySubject saved=studySubjectDao.merge(studySubject);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
            bindRandomization(loaded,RandomizationType.BOOK);
            StudySubject saved= studySubjectService.registerSubject(loaded);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 1, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded.getIfTreatmentScheduledEpoch());
        	ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)loaded.getScheduledEpoch();
        	assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch.getEligibilityIndicator().booleanValue());
        	assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
        	assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());        	
        	assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
        	assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

	}
	
	/* Test Cases for invalid stratum group
	 */
	public void testInvalidStratumGroup()throws Exception{
		StudySubject studySubject=new StudySubject();
		studySubject.setStudySite(getLocalRandomizedStudy(RandomizationType.BOOK, false).getStudySites().get(0));
		studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            addScheduledEpoch(studySubject,true);
            buildCommandObject(studySubject);
            addEnrollmentDetails(studySubject);
            bindEligibility(studySubject);
            bindStratificationInvalid(studySubject);
            StudySubject saved=studySubjectDao.merge(studySubject);
            savedId= saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
            bindRandomization(loaded,RandomizationType.BOOK);
            StudySubject saved=null;
			try {
				saved = studySubjectService.registerSubject(loaded);
			} catch (C3PRBaseException e) {
		//		assertEquals("Wrong Exception ", "No startum group found. Maybe the answer combination does not have a valid startum group", e.getMessage());
				return;
			}
			assertNull(saved);
        }
        interruptSession();
	}

	private Study getMultiSiteRandomizedStudy(RandomizationType randomizationType, boolean makeStudysiteCoCenter)throws Exception{
		Study study=studyCreationHelper.getMultiSiteRandomizedStudy(randomizationType);
		return addStudySiteCoCenterAndSave(study, makeStudysiteCoCenter);
	}
	
	private Study getMultiSiteNonRandomizedStudy(Boolean reserving, Boolean enrolling, boolean makeStudysiteCoCenter){
		Study study=studyCreationHelper.getMultiSiteNonRandomizedStudy(reserving, enrolling);
		return addStudySiteCoCenterAndSave(study, makeStudysiteCoCenter);
	}
	
	private Study getMultiSiteNonRandomizedWithArmStudy(boolean makeStudysiteCoCenter){
		Study study=studyCreationHelper.getMultiSiteNonRandomizedWithArmStudy();
		return addStudySiteCoCenterAndSave(study, makeStudysiteCoCenter);
	}
	
	private Study getLocalRandomizedStudy(RandomizationType randomizationType, boolean makeStudysiteCoCenter)throws Exception{
		Study study=studyCreationHelper.getLocalRandomizedStudy(randomizationType);
		return addStudySiteCoCenterAndSave(study, makeStudysiteCoCenter);
	}
	private Study getLocalNonRandomizedStudy(Boolean reserving, Boolean enrolling, boolean makeStudysiteCoCenter){
		Study study=studyCreationHelper.getLocalNonRandomizedStudy(reserving, enrolling);
		return addStudySiteCoCenterAndSave(study, makeStudysiteCoCenter);
	}
	private Study getLocalNonRandomizedWithArmStudy(boolean makeStudysiteCoCenter){
		Study study=studyCreationHelper.getLocalNonRandomizedWithArmStudy();
		return addStudySiteCoCenterAndSave(study, makeStudysiteCoCenter);
	}

	private void buildCommandObject(StudySubject studySubject){
		if(studySubject.getIfTreatmentScheduledEpoch()){
			ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)studySubject.getScheduledEpoch();
			List criterias=scheduledTreatmentEpoch.getTreatmentEpoch().getInclusionEligibilityCriteria();
			for(int i=0 ; i<criterias.size() ; i++){
				SubjectEligibilityAnswer subjectEligibilityAnswer=new SubjectEligibilityAnswer();
				subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria)criterias.get(i));
				scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
			}
			criterias=scheduledTreatmentEpoch.getTreatmentEpoch().getExclusionEligibilityCriteria();
			for(int i=0 ; i<criterias.size() ; i++){
				SubjectEligibilityAnswer subjectEligibilityAnswer=new SubjectEligibilityAnswer();
				subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria)criterias.get(i));
				scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
			}
			List<StratificationCriterion> stratifications=scheduledTreatmentEpoch.getTreatmentEpoch().getStratificationCriteria();
			for(StratificationCriterion stratificationCriterion : stratifications){
				stratificationCriterion.getPermissibleAnswers().size();
				SubjectStratificationAnswer subjectStratificationAnswer=new SubjectStratificationAnswer();
				subjectStratificationAnswer.setStratificationCriterion(stratificationCriterion);
				scheduledTreatmentEpoch.addSubjectStratificationAnswers(subjectStratificationAnswer);
			}
		}
	}
	private void bindEligibility(Object command){
		StudySubject studySubject=(StudySubject)command;
		List<SubjectEligibilityAnswer> subList=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getSubjectEligibilityAnswers();
		for(SubjectEligibilityAnswer subjectEligibilityAnswer: subList){
			if (subjectEligibilityAnswer.getEligibilityCriteria() instanceof InclusionEligibilityCriteria) {
				subjectEligibilityAnswer.setAnswerText("yes");
			}else{
				subjectEligibilityAnswer.setAnswerText("no");
			}
		}
		((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).setEligibilityIndicator(evaluateEligibilityIndicator(studySubject));
	}
	
	private void bindStratification(Object command){
		StudySubject studySubject=(StudySubject)command;
		List<SubjectStratificationAnswer> subList1=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getSubjectStratificationAnswers();
		for(SubjectStratificationAnswer subjectStratificationAnswer: subList1){
			subjectStratificationAnswer.setStratificationCriterionAnswer(subjectStratificationAnswer.getStratificationCriterion().getPermissibleAnswers().get(0));
		}
	}
	private void bindStratificationInvalid(Object command){
		StudySubject studySubject=(StudySubject)command;
		List<SubjectStratificationAnswer> subList1=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getSubjectStratificationAnswers();
		for(SubjectStratificationAnswer subjectStratificationAnswer: subList1){
			subjectStratificationAnswer.setStratificationCriterionAnswer(subjectStratificationAnswer.getStratificationCriterion().getPermissibleAnswers().get(subjectStratificationAnswer.getStratificationCriterion().getPermissibleAnswers().size()-1));
		}
	}

	private void bindRandomization(Object command, RandomizationType randomizationType){
		if(randomizationType==RandomizationType.PHONE_CALL){
			StudySubject studySubject=(StudySubject)command;
			ScheduledTreatmentEpoch scheduledEpoch=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch());
			scheduledEpoch.addScheduledArm(new ScheduledArm());
			ScheduledArm scheduledArm=scheduledEpoch.getScheduledArm();
			scheduledArm.setArm(((TreatmentEpoch)scheduledEpoch.getTreatmentEpoch()).getArms().get(0));
		}
	}

	private boolean evaluateEligibilityIndicator(StudySubject studySubject){
		boolean flag=true;
		List<SubjectEligibilityAnswer> answers=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getInclusionEligibilityAnswers();
		for(SubjectEligibilityAnswer subjectEligibilityAnswer:answers){
			String answerText=subjectEligibilityAnswer.getAnswerText();
			if(answerText==null||answerText.equalsIgnoreCase("")||(!answerText.equalsIgnoreCase("Yes")&&!answerText.equalsIgnoreCase("NA"))){
				flag=false;
				break;
			}
		}
		if(flag){
			answers=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getExclusionEligibilityAnswers();
			for(SubjectEligibilityAnswer subjectEligibilityAnswer:answers){
				String answerText=subjectEligibilityAnswer.getAnswerText();
				if(answerText==null||answerText.equalsIgnoreCase("")||(!answerText.equalsIgnoreCase("No")&&!answerText.equalsIgnoreCase("NA"))){
					flag=false;
					break;
				}
			}
		}
		return flag;
	}

	private Epoch createTestTreatmentEpoch(boolean randomized){
		TreatmentEpoch epoch =new TreatmentEpoch();
		epoch.addEligibilityCriterion(new InclusionEligibilityCriteria());
		epoch.addEligibilityCriterion(new ExclusionEligibilityCriteria());
		StratificationCriterion stratificationCriterion=new StratificationCriterion();
		stratificationCriterion.addPermissibleAnswer(new StratificationCriterionPermissibleAnswer());
		epoch.addStratificationCriterion(stratificationCriterion);
		epoch.addArm(new Arm());
		epoch.setRandomizedIndicator(randomized);
		if(randomized){
			epoch.setRandomization(new PhonecallRandomization());
		}
		return epoch;
	}

	protected void addScheduledEpoch(StudySubject studySubject,boolean isTreatment) {
        ScheduledEpoch scheduledEpoch=isTreatment?new ScheduledTreatmentEpoch():new ScheduledNonTreatmentEpoch();
        scheduledEpoch.setEpoch(studySubject.getStudySite().getStudy().getEpochs().get(0));
        studySubject.addScheduledEpoch(scheduledEpoch);
	}
	
	private void addEnrollmentDetails(StudySubject studySubject){
		studySubject.setInformedConsentSignedDate(new Date());
		studySubject.setInformedConsentVersion("1.0");
		studySubject.setOtherTreatingPhysician("Other T P");
	}
	
	private Study addStudySiteCoCenterAndSave(Study study, boolean makeStudysiteCoCenter){
		StudySite studySite=new StudySite();
		studySite.setHealthcareSite(healthcareSitedao.getById(1000));
		studySite.setStudy(study);
		study.getStudySites().add(studySite);
		Integer id;
		{
			HealthcareSite healthcaresite = new HealthcareSite();
			Address address = new Address();
			address.setCity("Chicago");
			address.setCountryCode("USA");
			address.setPostalCode("83929");
			address.setStateCode("IL");
			address.setStreetAddress("123 Lake Shore Dr");
	
			healthcaresite.setAddress(address);
			healthcaresite.setName("Northwestern Memorial Hospital");
			healthcaresite.setDescriptionText("NU healthcare");
			if(makeStudysiteCoCenter)
				healthcaresite.setNciInstituteCode(studySite.getHealthcareSite().getNciInstituteCode());
			else
				healthcaresite.setNciInstituteCode("NCI northwestern");
			healthcareSitedao.save(healthcaresite);
			id = healthcaresite.getId();
		}
		interruptSession();
		assertNotNull("The saved organization didn't get an id", id);
		HealthcareSite healthcareSite=healthcareSitedao.getById(id);
		StudyCoordinatingCenter stC=study.getStudyCoordinatingCenters().get(0);
		stC.setStudy(study);
		stC.setHealthcareSite(healthcareSite);
		Integer savedId;
		{
			study = dao.merge(study);

            savedId = study.getId();
        }
		interruptSession();
        {
            Study loaded = dao.getById(savedId);
            return loaded;
        }
	}
	
}
