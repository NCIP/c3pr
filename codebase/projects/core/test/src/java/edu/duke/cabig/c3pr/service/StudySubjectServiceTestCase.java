package edu.duke.cabig.c3pr.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.NonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.StudySubjectCreatorHelper;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 4, 2007 Time: 2:33:16 PM To change this template
 * use File | Settings | File Templates.
 */

public class StudySubjectServiceTestCase extends DaoTestCase {

    private StudySubjectService studySubjectService;

    private StudySubjectCreatorHelper studySubjectCreatorHelper;

    private HealthcareSiteDao healthcareSitedao = (HealthcareSiteDao) getApplicationContext()
                    .getBean("healthcareSiteDao");

    private ParticipantDao participantDao = (ParticipantDao) getApplicationContext().getBean(
                    "participantDao");

    private StudySubjectDao studySubjectDao = (StudySubjectDao) getApplicationContext().getBean(
                    "studySubjectDao");
    
    private StudySubjectRepository studySubjectRepository = (StudySubjectRepository) getApplicationContext().getBean(
    "studySubjectRepository");

    protected void setUp() throws Exception {
        super.setUp();
        studySubjectCreatorHelper=new StudySubjectCreatorHelper();
        studySubjectService = (StudySubjectService) getApplicationContext().getBean(
                        "studySubjectService");
    }

    

    /**
     * Epoch Workflow Status test Not Multi Site Trial Non Treatment Epoch Epoch Data Entry Status:
     * Incomplete Registration Data Entry Status: Complete
     */
    public void testManageSchEpochWorkFlowStatusIfUnAppCase0() throws Exception {
        StudySubject studySubject = new StudySubject();
        StudySite site = new StudySite();
        Study study = new Study();
        study.setMultiInstitutionIndicator(Boolean.TRUE);
        site.setStudy(study);
        studySubject.setStudySite(site);
        studySubject.addScheduledEpoch(new ScheduledNonTreatmentEpoch());
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        ScheduledEpochDataEntryStatus.INCOMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubjectService.manageSchEpochWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status", ScheduledEpochWorkFlowStatus.UNAPPROVED,
                        studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }

    /**
     * Epoch Workflow Status test Not Multi Site Trial Non Treatment Epoch Epoch Data Entry Status:
     * Complete Registration Data Entry Status: Complete
     */
    public void testManageSchEpochWorkFlowStatusIfUnAppCase1() throws Exception {
        StudySubject studySubject = new StudySubject();
        StudySite site = new StudySite();
        Study study = new Study();
        study.setMultiInstitutionIndicator(Boolean.FALSE);
        site.setStudy(study);
        studySubject.setStudySite(site);
        studySubject.addScheduledEpoch(new ScheduledNonTreatmentEpoch());
        studySubject.getScheduledEpoch().setEpoch(new NonTreatmentEpoch());
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubjectService.manageSchEpochWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status", ScheduledEpochWorkFlowStatus.APPROVED,
                        studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }

    /**
     * Epoch Workflow Status test Not Multi Site Trial Randomized Treatment Epoch Epoch Data Entry
     * Status: Complete Registration Data Entry Status: Complete not assigned an Arm
     */
    public void testManageSchEpochWorkFlowStatusIfUnAppCase2() throws Exception {
        StudySubject studySubject = new StudySubject();
        StudySite site = new StudySite();
        Study study = new Study();
        study.setMultiInstitutionIndicator(Boolean.FALSE);
        site.setStudy(study);
        studySubject.setStudySite(site);
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        try {
            studySubjectService.manageSchEpochWorkFlow(studySubject);
        }
        catch (Exception e) {
        }
        assertEquals("Wrong Epoch WorkFlow Status", ScheduledEpochWorkFlowStatus.UNAPPROVED,
                        studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }

    /**
     * Epoch Workflow Status test Not Multi Site Trial Randomized Treatment Epoch Epoch Data Entry
     * Status: Complete Registration Data Entry Status: Complete Arm assigned
     */
    public void testManageSchEpochWorkFlowStatusIfUnAppCase3() throws Exception {
        StudySubject studySubject = new StudySubject();
        StudySite site = new StudySite();
        Study study = new Study();
        study.setMultiInstitutionIndicator(Boolean.FALSE);
        study.setRandomizationType(RandomizationType.PHONE_CALL);
        site.setStudy(study);
        studySubject.setStudySite(site);
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.bindRandomization(studySubject, RandomizationType.PHONE_CALL);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubjectService.manageSchEpochWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status", ScheduledEpochWorkFlowStatus.APPROVED,
                        studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }

    /**
     * Epoch Workflow Status test Not Multi Site Trial Non Randomized Treatment Epoch Epoch Data
     * Entry Status: Complete Registration Data Entry Status: Complete
     */
    public void testManageSchEpochWorkFlowStatusIfUnAppCase4() throws Exception {
        StudySubject studySubject = new StudySubject();
        StudySite site = new StudySite();
        Study study = new Study();
        study.setMultiInstitutionIndicator(Boolean.FALSE);
        site.setStudy(study);
        studySubject.setStudySite(site);
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(false));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubjectService.manageSchEpochWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status", ScheduledEpochWorkFlowStatus.APPROVED,
                        studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }

    /**
     * Epoch Workflow Status test Multi Site Trial Randomized Treatment Epoch not Hosted Mode Epoch
     * Data Entry Status: Complete Registration Data Entry Status: Complete
     */
    public void testManageSchEpochWorkFlowStatusIfUnAppCase5() throws Exception {
        StudySubject studySubject = new StudySubject();
        StudySite site = new StudySite();
        Study study = new Study();
        StudyCoordinatingCenter stC = study.getStudyCoordinatingCenters().get(0);
        stC.setStudy(study);
        HealthcareSite healthcareSite = new HealthcareSite();
        healthcareSite.setName("test name");
        healthcareSite.setNciInstituteCode("test code");
        stC.setHealthcareSite(healthcareSite);
        study.setMultiInstitutionIndicator(Boolean.TRUE);
        site.setStudy(study);
        studySubject.setStudySite(site);
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        try {
            studySubjectService.setHostedMode(false);
            studySubjectService.manageSchEpochWorkFlow(studySubject);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertEquals("Wrong Epoch WorkFlow Status", ScheduledEpochWorkFlowStatus.DISAPPROVED,
                            studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        assertEquals("Wrong Epoch WorkFlow Status", ScheduledEpochWorkFlowStatus.PENDING,
                        studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }

    /**
     * Epoch Workflow Status test Multi Site Trial Hosted Mode Randomized Treatment Epoch Epoch Data
     * Entry Status: Complete Registration Data Entry Status: Complete
     */
    public void testManageSchEpochWorkFlowStatusIfUnAppCase6() throws Exception {
        StudySubject studySubject = new StudySubject();
        StudySite site = new StudySite();
        Study study = new Study();
        study.setMultiInstitutionIndicator(Boolean.TRUE);
        study.setRandomizationType(RandomizationType.PHONE_CALL);
        site.setStudy(study);
        studySubject.setStudySite(site);
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.bindRandomization(studySubject, RandomizationType.PHONE_CALL);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        try {
            studySubjectService.setHostedMode(true);
            studySubjectService.manageSchEpochWorkFlow(studySubject);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertEquals("Wrong Epoch WorkFlow Status", ScheduledEpochWorkFlowStatus.DISAPPROVED,
                            studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        assertEquals("Wrong Epoch WorkFlow Status", ScheduledEpochWorkFlowStatus.APPROVED,
                        studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }

    /**
     * Regisrtation Workflow Status test Epoch Workflow Status: Unapproved Registration Data Entry
     * Status: Incomplete
     */
    public void testManageRegWorkFlowIfUnRegCase0() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.INCOMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(
                        ScheduledEpochWorkFlowStatus.UNAPPROVED);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status", RegistrationWorkFlowStatus.UNREGISTERED,
                        studySubject.getRegWorkflowStatus());
    }

    /**
     * Regisrtation Workflow Status test Epoch Workflow Status: Unapproved Registration Data Entry
     * Status: Complete
     */
    public void testManageRegWorkFlowIfUnRegCase1() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(
                        ScheduledEpochWorkFlowStatus.UNAPPROVED);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status", RegistrationWorkFlowStatus.UNREGISTERED,
                        studySubject.getRegWorkflowStatus());
    }

    /**
     * Regisrtation Workflow Status test Epoch Workflow Status: Disaaproved Registration Data Entry
     * Status: Complete
     */
    public void testManageRegWorkFlowIfUnRegCase2() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(
                        ScheduledEpochWorkFlowStatus.DISAPPROVED);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status", RegistrationWorkFlowStatus.DISAPPROVED,
                        studySubject.getRegWorkflowStatus());
    }

    /**
     * Regisrtation Workflow Status test Epoch Workflow Status: Pending Registration Data Entry
     * Status: Complete
     */
    public void testManageRegWorkFlowIfUnRegCase3() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(
                        ScheduledEpochWorkFlowStatus.PENDING);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status", RegistrationWorkFlowStatus.PENDING,
                        studySubject.getRegWorkflowStatus());
    }

    /**
     * Regisrtation Workflow Status test Epoch Workflow Status: Approved Treatment Epoch
     * Registration Data Entry Status: Complete
     */
    public void testManageRegWorkFlowIfUnRegCase4() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(
                        ScheduledEpochWorkFlowStatus.APPROVED);
        studySubject.getScheduledEpoch().setEpoch(new TreatmentEpoch());
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status", RegistrationWorkFlowStatus.REGISTERED,
                        studySubject.getRegWorkflowStatus());
    }

    /**
     * Regisrtation Workflow Status test Reserving Non Treatment Epoch Epoch Workflow Status:
     * Unapproved Registration Data Entry Status: Complete
     */
    public void testManageRegWorkFlowIfUnRegCase5() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledNonTreatmentEpoch scheduledEpochFirst = new ScheduledNonTreatmentEpoch();
        NonTreatmentEpoch nonTreatmentEpoch = new NonTreatmentEpoch();
        nonTreatmentEpoch.setReservationIndicator(Boolean.TRUE);
        scheduledEpochFirst.setEpoch(nonTreatmentEpoch);
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(
                        ScheduledEpochWorkFlowStatus.APPROVED);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status", RegistrationWorkFlowStatus.RESERVED,
                        studySubject.getRegWorkflowStatus());
    }

    /**
     * Regisrtation Workflow Status test Non Reserving Non Enrolling Non Treatment Epoch Epoch
     * Workflow Status: Approved Registration Data Entry Status: Complete
     */
    public void testManageRegWorkFlowIfUnRegCase6() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledNonTreatmentEpoch scheduledEpochFirst = new ScheduledNonTreatmentEpoch();
        NonTreatmentEpoch nonTreatmentEpoch = new NonTreatmentEpoch();
        nonTreatmentEpoch.setReservationIndicator(Boolean.FALSE);
        scheduledEpochFirst.setEpoch(nonTreatmentEpoch);
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(
                        ScheduledEpochWorkFlowStatus.APPROVED);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status", RegistrationWorkFlowStatus.UNREGISTERED,
                        studySubject.getRegWorkflowStatus());
    }

    /**
     * Regisrtation Workflow Status test Non Reserving Enrolling Non Treatment Epoch Epoch Workflow
     * Status: Approved Registration Data Entry Status: Complete
     */
    public void testManageRegWorkFlowIfUnRegCase7() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledNonTreatmentEpoch scheduledEpochFirst = new ScheduledNonTreatmentEpoch();
        NonTreatmentEpoch nonTreatmentEpoch = new NonTreatmentEpoch();
        nonTreatmentEpoch.setReservationIndicator(Boolean.FALSE);
        nonTreatmentEpoch.setEnrollmentIndicator(Boolean.TRUE);
        scheduledEpochFirst.setEpoch(nonTreatmentEpoch);
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(
                        ScheduledEpochWorkFlowStatus.APPROVED);
        studySubjectService.manageRegWorkFlow(studySubject);
        assertEquals("Wrong Epoch WorkFlow Status", RegistrationWorkFlowStatus.REGISTERED,
                        studySubject.getRegWorkflowStatus());
    }

    /*
     * Test Cases for createRegistration Multi Site Trial Multi Site Mode Treatment Epoch Book
     * Randomization
     */
    public void testCreateRegistrationCase0() throws Exception {
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteRandomizedStudy(RandomizationType.BOOK, false)
                        .getStudySites().get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        studySubjectService.setHostedMode(false);
        Integer savedId;
        {
            studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
            studySubjectCreatorHelper.buildCommandObject(studySubject);
            studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
            studySubjectCreatorHelper.bindEligibility(studySubject);
            studySubjectCreatorHelper.bindStratification(studySubject);
            studySubjectCreatorHelper.bindRandomization(studySubject, RandomizationType.BOOK);
            StudySubject saved = studySubjectRepository.register(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded
                            .getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded
                            .getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.PENDING,
                            loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.PENDING,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for createRegistration Multi Site Trial Multi Site Mode Treatment Epoch Callout
     * Randomization
     */
    public void testCreateRegistrationCase1() throws Exception {
        studySubjectService.setHostedMode(false);
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteRandomizedStudy(RandomizationType.CALL_OUT, false)
                        .getStudySites().get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
            studySubjectCreatorHelper.buildCommandObject(studySubject);
            studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
            studySubjectCreatorHelper.bindEligibility(studySubject);
            studySubjectCreatorHelper.bindStratification(studySubject);
            studySubjectCreatorHelper.bindRandomization(studySubject, RandomizationType.CALL_OUT);
            StudySubject saved = studySubjectRepository.register(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded
                            .getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded
                            .getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.PENDING,
                            loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.PENDING,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for createRegistration Multi Site Trial Multi Site Mode Non Treatment Epoch, Non
     * Reserving, Non Registering Non Randomized
     */
    public void testCreateRegistrationCase2() throws Exception {
        studySubjectService.setHostedMode(false);
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedStudy(false, false, false)
                        .getStudySites().get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
            studySubjectCreatorHelper.buildCommandObject(studySubject);
            studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
            StudySubject saved = studySubjectRepository.register(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded
                            .getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.UNREGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for createRegistration Local Trial Treatment Epoch Phone Call Randomization
     */
    public void testCreateRegistrationCase3() throws Exception {
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudy(RandomizationType.PHONE_CALL, false)
                        .getStudySites().get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
            studySubjectCreatorHelper.buildCommandObject(studySubject);
            studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
            studySubjectCreatorHelper.bindEligibility(studySubject);
            studySubjectCreatorHelper.bindStratification(studySubject);
            OrganizationAssignedIdentifier id = studySubject.getOrganizationAssignedIdentifiers()
                            .get(0);
            id.setHealthcareSite(healthcareSitedao.getById(1002));
            id.setType("Test1");
            id.setValue("Test1");
            StudySubject saved = studySubjectDao.merge(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            OrganizationAssignedIdentifier id = loaded.getOrganizationAssignedIdentifiers().get(1);
            id.setHealthcareSite(healthcareSitedao.getById(1002));
            id.setType("Test2");
            id.setValue("Test2");
            loaded = studySubjectDao.merge(loaded);
            savedId = loaded.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            studySubjectCreatorHelper.bindRandomization(loaded, RandomizationType.PHONE_CALL);
            StudySubject saved = studySubjectRepository.register(loaded);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded
                            .getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded
                            .getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong number of identifier", 2, loaded.getIdentifiers().size());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
    }

    /*
     * Test Cases for createRegistration Local Trial Treatment Epoch Book Randomization
     */
    public void testCreateRegistrationCase4() throws Exception {
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudy(RandomizationType.BOOK, false)
                        .getStudySites().get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
            studySubjectCreatorHelper.buildCommandObject(studySubject);
            studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
            studySubjectCreatorHelper.bindEligibility(studySubject);
            studySubjectCreatorHelper.bindStratification(studySubject);
            StudySubject saved = studySubjectDao.merge(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            studySubjectCreatorHelper.bindRandomization(loaded, RandomizationType.BOOK);
            StudySubject saved = studySubjectRepository.register(loaded);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded
                            .getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded
                            .getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

        XmlMarshaller marshaller = new XmlMarshaller((String) getApplicationContext().getBean(
                        "ccts-registration-castorMapping"));
        StudySubject loaded = studySubjectDao.getById(savedId);
        SystemAssignedIdentifier systemAssignedIdentifier = new SystemAssignedIdentifier();
        systemAssignedIdentifier.setType("MRN");
        systemAssignedIdentifier.setValue("MRN-12A!2121");
        systemAssignedIdentifier.setSystemName("C3PR");
        loaded.getParticipant().getSystemAssignedIdentifiers().add(systemAssignedIdentifier);
        String xml = marshaller.toXML(loaded);
        System.out.println(xml);
        assertNotNull(xml);
    }

    /*
     * Test Cases for createRegistration Local Trial Non Randomized Treatment Epoch
     */
    public void testCreateRegistrationCase5() throws Exception {
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedWithArmStudy(false).getStudySites().get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);

        {
            StudySubject saved = studySubjectRepository.register(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded
                            .getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded
                            .getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.INCOMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.UNREGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.UNAPPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for createRegistration Local Trial NonTreatment Epoch Reserving Non Randomized
     */
    public void testCreateRegistrationCase6() throws Exception {
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudy(true, false, false).getStudySites()
                        .get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        {
            StudySubject saved = studySubjectRepository.register(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded
                            .getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.RESERVED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for createRegistration Local Trial NonTreatment Epoch Enrolling Non Randomized
     */
    public void testCreateRegistrationCase7() throws Exception {
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudy(false, true, false).getStudySites()
                        .get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        {
            StudySubject saved = studySubjectRepository.register(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded
                            .getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for createRegistration Local Trial NonTreatment Epoch Non Enrolling, Non Reserving
     * Non Randomized
     */
    public void testCreateRegistrationCase8() throws Exception {
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudy(false, false, false).getStudySites()
                        .get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        {
            StudySubject saved = studySubjectRepository.register(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded
                            .getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.UNREGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for createRegistration Multi Site Trial Multi Site Mode Non Treatment Epoch,
     * Reserving, Non Registering Non Randomized
     */
    public void testCreateRegistrationCase9() throws Exception {
        studySubjectService.setHostedMode(false);
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedStudy(true, false, false)
                        .getStudySites().get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
            studySubjectCreatorHelper.buildCommandObject(studySubject);
            studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
            StudySubject saved = studySubjectRepository.register(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded
                            .getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.RESERVED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for createRegistration Multi Site Trial Hosted Mode Treatment Epoch Book
     * Randomization
     */
    public void testCreateRegistrationCase10() throws Exception {
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteRandomizedStudy(RandomizationType.BOOK, false)
                        .getStudySites().get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        studySubjectService.setHostedMode(true);
        Integer savedId;
        {
            studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
            studySubjectCreatorHelper.buildCommandObject(studySubject);
            studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
            studySubjectCreatorHelper.bindEligibility(studySubject);
            studySubjectCreatorHelper.bindStratification(studySubject);
            studySubjectCreatorHelper.bindRandomization(studySubject, RandomizationType.BOOK);
            StudySubject saved = studySubjectRepository.register(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded
                            .getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded
                            .getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for createRegistration Multi Site Trial Hosted Mode Non Treatment Epoch, Non
     * Reserving, Non Registering Non Randomized
     */
    public void testCreateRegistrationCase11() throws Exception {
        studySubjectService.setHostedMode(true);
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedStudy(false, false, false)
                        .getStudySites().get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
            studySubjectCreatorHelper.buildCommandObject(studySubject);
            studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
            StudySubject saved = studySubjectRepository.register(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded
                            .getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.UNREGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for createRegistration Multi Site Trial Hosted Mode Non Treatment Epoch,
     * Reserving, Non Registering Non Randomized
     */
    public void testCreateRegistrationCase12() throws Exception {
        studySubjectService.setHostedMode(true);
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedStudy(true, false, false)
                        .getStudySites().get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
            studySubjectCreatorHelper.buildCommandObject(studySubject);
            studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
            StudySubject saved = studySubjectRepository.register(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded
                            .getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.RESERVED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for createRegistration Multi Site Trial Multi Site Mode Treatment Epoch Book
     * Randomization Study Site is Co Ordinating Center
     */
    public void testCreateRegistrationCase13() throws Exception {
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteRandomizedStudy(RandomizationType.BOOK, true)
                        .getStudySites().get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
            studySubjectCreatorHelper.buildCommandObject(studySubject);
            studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
            studySubjectCreatorHelper.bindEligibility(studySubject);
            studySubjectCreatorHelper.bindStratification(studySubject);
            StudySubject saved = studySubjectDao.merge(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            studySubjectCreatorHelper.bindRandomization(loaded, RandomizationType.BOOK);
            StudySubject saved = studySubjectRepository.register(loaded);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded
                            .getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded
                            .getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for invalid stratum group
     */
    public void testInvalidStratumGroup() throws Exception {
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudy(RandomizationType.BOOK, false)
                        .getStudySites().get(0));
        studySubject.setParticipant(participantDao.getById(1000));
        Integer savedId;
        {
            studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
            studySubjectCreatorHelper.buildCommandObject(studySubject);
            studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
            studySubjectCreatorHelper.bindEligibility(studySubject);
            studySubjectCreatorHelper.bindStratificationInvalid(studySubject);
            StudySubject saved = studySubjectDao.merge(studySubject);
            savedId = saved.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            studySubjectCreatorHelper.bindRandomization(loaded, RandomizationType.BOOK);
            StudySubject saved = null;
            try {
                saved = studySubjectRepository.register(loaded);
            }
            catch (C3PRBaseException e) {
                // assertEquals("Wrong Exception ", "No startum group found. Maybe the answer
                // combination does not have a valid startum group", e.getMessage());
                return;
            }
            assertNull(saved);
        }
        interruptSession();
    }

    /**
     * @param filePath
     *                the name of the file to open. Not sure if it can accept URLs or just
     *                filenames. Path handling could be better, and buffer sizes are hardcoded
     */
    private String readFileAsString(String filePath) throws java.io.IOException {
        String fileData = "";
        File f = new File(filePath);
        System.out.println(f.getAbsolutePath());
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            fileData += line;
        }
        reader.close();
        return fileData;
    }

}
