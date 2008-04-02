package edu.duke.cabig.c3pr.service;

import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
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
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.service.impl.StudySubjectServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StudySubjectCreatorHelper;
import edu.duke.cabig.c3pr.utils.StudyTargetAccrualNotificationEmail;

public class StudySubjectServiceUnitTestCase extends AbstractTestCase {
    
    private StudyTargetAccrualNotificationEmail notificationEmailer;
    private StudySubjectRepository studySubjectRepository;
    private StudySubjectService studySubjectService;
    private StudySubjectCreatorHelper studySubjectCreatorHelper;
    private Configuration configuration;
    private StudySubject studySubject;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        StudySubjectServiceImpl studySubjectServiceImpl=new StudySubjectServiceImpl();
        studySubject = new StudySubject();
        studySubjectCreatorHelper=new StudySubjectCreatorHelper();
        studySubjectRepository=registerMockFor(StudySubjectRepository.class);
        notificationEmailer=registerMockFor(StudyTargetAccrualNotificationEmail.class);
        configuration=registerMockFor(Configuration.class);
        studySubjectServiceImpl.setNotificationEmailer(notificationEmailer);
        studySubjectServiceImpl.setStudySubjectRepository(studySubjectRepository);
        studySubjectServiceImpl.setConfiguration(configuration);
        studySubjectService=studySubjectServiceImpl;
        EasyMock.expect(studySubjectRepository.save(studySubject)).andReturn(studySubject);
    }
    
    /**
     * Epoch Workflow Status test 
     * Not Multi Site Trial 
     * Non Treatment Epoch 
     * Epoch Data Entry Status:Incomplete 
     * Registration Data Entry Status: Complete
     */
    public void testRegisterCase0() throws Exception {
        
        StudySite site = new StudySite();
        Study study = new Study();
        study.setMultiInstitutionIndicator(Boolean.TRUE);
        site.setStudy(study);
        studySubject.setStudySite(site);
        studySubject.addScheduledEpoch(new ScheduledNonTreatmentEpoch());
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        ScheduledEpochDataEntryStatus.INCOMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        replayMocks();
        studySubjectService.register(studySubject);
        verifyMocks();
        assertEquals("Wrong Epoch WorkFlow Status", ScheduledEpochWorkFlowStatus.UNAPPROVED,
                        studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
    
    /**
     * Register method control flow test 
     * Treatment Epoch Epoch Data Entry Status: Complete 
     * Registration Data Entry Status: Complete
     * ScheduledWorkflowStatus: Approved
     */
    public void testRegisterCase1() throws Exception {
        
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
        replayMocks();
        studySubjectService.register(studySubject);
        verifyMocks();
    }
    
    /**
     * Epoch Workflow Status test 
     * Not Multi Site Trial 
     * Non Treatment Epoch 
     * Epoch Data Entry Status:Complete
     * Registration Data Entry Status: Complete
     */
    public void testRegisterCase2() throws Exception {
        
        StudySite site = new StudySite();
        Study study = new Study();
        study.setMultiInstitutionIndicator(Boolean.FALSE);
        site.setStudy(study);
        studySubject.setStudySite(site);
        studySubject.addScheduledEpoch(new ScheduledNonTreatmentEpoch());
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        EasyMock.expect(studySubjectRepository.save(studySubject)).andReturn(studySubject);
        EasyMock.expect(studySubjectRepository.doLocalRegistration(studySubject)).andReturn(studySubject);
        replayMocks();
        studySubjectService.register(studySubject);
        verifyMocks();
        assertEquals("Wrong Registration Worflow Entry Status", 
                        RegistrationWorkFlowStatus.UNREGISTERED, studySubject.getRegWorkflowStatus());

    }
    
    /**
     * Registration Workflow Status & Control Flow test 
     * Multi Site Trial Randomized 
     * Treatment Epoch 
     * not Hosted Mode 
     * Epoch Data Entry Status: Complete 
     * Registration Data Entry Status: Complete
     */
    public void testRegisterCase3() throws Exception {
        
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
        studySubjectService.setHostedMode(false);
        EasyMock.expect(studySubjectRepository.save(studySubject)).andReturn(studySubject);
        EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn("test code1");
        replayMocks();
        studySubjectService.register(studySubject);
        verifyMocks();
        assertEquals("Wrong Registration Worflow Entry Status", 
                        RegistrationWorkFlowStatus.PENDING, studySubject.getRegWorkflowStatus());

    }
    
    /**
     * Registration Workflow Status & Control Flow test 
     * Multi Site Trial Randomized 
     * Local Instance Co ordinating center
     * Treatment Epoch 
     * not Hosted Mode 
     * Epoch Data Entry Status: Complete 
     * Registration Data Entry Status: Complete
     */
    public void testRegisterCase4() throws Exception {
        
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
        studySubjectService.setHostedMode(false);
        EasyMock.expect(studySubjectRepository.save(studySubject)).andReturn(studySubject);
        EasyMock.expect(studySubjectRepository.doLocalRegistration(studySubject)).andReturn(studySubject);
        EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn("test code");
        replayMocks();
        studySubjectService.register(studySubject);
        verifyMocks();
        assertEquals("Wrong Registration Worflow Entry Status", 
                        RegistrationWorkFlowStatus.UNREGISTERED, studySubject.getRegWorkflowStatus());
    }
    
    /**
     * Registration Workflow Status test 
     * Not Multi Site Trial 
     * Non Treatment Epoch 
     * Epoch Data Entry Status:Complete
     * Registration Data Entry Status: Complete
     */
    public void testRegisterCase5() throws Exception {
        
        StudySite site = new StudySite();
        Study study = new Study();
        study.setMultiInstitutionIndicator(Boolean.FALSE);
        site.setStudy(study);
        studySubject.setStudySite(site);
        studySubject.addScheduledEpoch(new ScheduledNonTreatmentEpoch());
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED);
        EasyMock.expect(studySubjectRepository.doLocalRegistration(studySubject)).andReturn(studySubject);
        replayMocks();
        studySubjectService.register(studySubject);
        verifyMocks();
        assertEquals("Wrong Registration Worflow Entry Status", 
                        RegistrationWorkFlowStatus.REGISTERED, studySubject.getRegWorkflowStatus());
    }
}
