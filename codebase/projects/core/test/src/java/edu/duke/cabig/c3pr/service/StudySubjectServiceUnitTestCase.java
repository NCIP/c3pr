package edu.duke.cabig.c3pr.service;

import java.util.Date;

import org.easymock.classextension.EasyMock;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
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
import edu.duke.cabig.c3pr.domain.factory.StudySubjectFactory;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.esb.MessageBroadcastService;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.impl.StudySubjectServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StudySubjectCreatorHelper;
import edu.duke.cabig.c3pr.utils.StudyTargetAccrualNotificationEmail;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

public class StudySubjectServiceUnitTestCase extends AbstractTestCase {
    
    private StudyTargetAccrualNotificationEmail notificationEmailer;
    private StudySubjectRepository studySubjectRepository;
    private StudySubjectService studySubjectService;
    private StudySubjectCreatorHelper studySubjectCreatorHelper;
    private Configuration configuration;
    private StudySubject studySubject;
    private StudySubjectFactory studySubjectFactory;
    private MessageBroadcastService jmsAffiliateSiteBroadcaster;
    private MessageBroadcastService jmsCoBroadcaster;
    private MessageSource c3prErrorMessages;
    private C3PRExceptionHelper exceptionHelper;
    private XmlMarshaller xmlMarshaller;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        StudySubjectServiceImpl studySubjectServiceImpl=new StudySubjectServiceImpl();
        studySubject = new StudySubject();
        studySubjectCreatorHelper=new StudySubjectCreatorHelper();
        studySubjectRepository=registerMockFor(StudySubjectRepository.class);
        notificationEmailer=registerMockFor(StudyTargetAccrualNotificationEmail.class);
        configuration=registerMockFor(Configuration.class);
        jmsAffiliateSiteBroadcaster=registerMockFor(MessageBroadcastService.class);
        jmsCoBroadcaster=registerMockFor(MessageBroadcastService.class);
        studySubjectFactory=registerMockFor(StudySubjectFactory.class);
        c3prErrorMessages=registerMockFor(MessageSource.class);
        exceptionHelper=registerMockFor(C3PRExceptionHelper.class);
        xmlMarshaller=registerMockFor(XmlMarshaller.class);
        studySubjectServiceImpl.setNotificationEmailer(notificationEmailer);
        studySubjectServiceImpl.setStudySubjectRepository(studySubjectRepository);
        studySubjectServiceImpl.setConfiguration(configuration);
        studySubjectServiceImpl.setStudySubjectFactory(studySubjectFactory);
        studySubjectServiceImpl.setJmsAffiliateSiteBroadcaster(jmsAffiliateSiteBroadcaster);
        studySubjectServiceImpl.setC3prErrorMessages(c3prErrorMessages);
        studySubjectServiceImpl.setExceptionHelper(exceptionHelper);
        studySubjectServiceImpl.setXmlUtility(xmlMarshaller);
        studySubjectServiceImpl.setJmsCoOrdinatingCenterBroadcaster(jmsCoBroadcaster);
        studySubjectService=studySubjectServiceImpl;
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
        EasyMock.expect(studySubjectRepository.save(studySubject)).andReturn(studySubject);
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
        EasyMock.expect(studySubjectRepository.save(studySubject)).andReturn(studySubject);
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
        EasyMock.expect(studySubjectRepository.save(studySubject)).andReturn(studySubject);
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
        EasyMock.expect(studySubjectRepository.save(studySubject)).andReturn(studySubject);
        EasyMock.expect(xmlMarshaller.toXML(studySubject)).andReturn("Some Co XML");
        jmsCoBroadcaster.broadcast("Some Co XML");
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
        EasyMock.expect(studySubjectRepository.save(studySubject)).andReturn(studySubject);
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
        EasyMock.expect(studySubjectRepository.save(studySubject)).andReturn(studySubject);
        replayMocks();
        studySubjectService.register(studySubject);
        verifyMocks();
        assertEquals("Wrong Registration Worflow Entry Status", 
                        RegistrationWorkFlowStatus.REGISTERED, studySubject.getRegWorkflowStatus());
    }
    
    public void testProcessAffiliateSiteRequestRegDataEntryIncomplete() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(false, false, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        EasyMock.expect(studySubjectFactory.buildStudySubject(studySubject)).andReturn(studySubject);
        jmsAffiliateSiteBroadcaster.broadcast("Some xml");
        EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.REGISTRATION.DATA_ENTRY_INCOMPLETE.CODE", null, null)).andReturn("1");
        EasyMock.expect(exceptionHelper.getException(1)).andReturn(new C3PRCodedException(1, "Error"));
        EasyMock.expect(xmlMarshaller.toXML(studySubject)).andReturn("Some xml");
        EasyMock.expect(configuration.get(Configuration.MULTISITE_ENABLE)).andReturn("true");
        replayMocks();
        studySubjectService.processAffliateSiteRegistrationRequest(studySubject);
        assertEquals("Wrong workflow status", RegistrationWorkFlowStatus.DISAPPROVED, studySubject.getRegWorkflowStatus());
        assertEquals("Wrong disapproval reason text", "Error", studySubject.getDisapprovalReasonText());
        verifyMocks();
    }
    
    public void testProcessAffiliateSiteRequestSchDataEntryIncomplete() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(false, false, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
        studySubject.addScheduledEpoch(new ScheduledTreatmentEpoch());
        EasyMock.expect(studySubjectFactory.buildStudySubject(studySubject)).andReturn(studySubject);
        jmsAffiliateSiteBroadcaster.broadcast("Some xml");
        EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.REGISTRATION.SCHEDULEDEPOCH.DATA_ENTRY_INCOMPLETE.CODE", null, null)).andReturn("1");
        EasyMock.expect(exceptionHelper.getException(1)).andReturn(new C3PRCodedException(1, "Error"));
        EasyMock.expect(xmlMarshaller.toXML(studySubject)).andReturn("Some xml");
        EasyMock.expect(configuration.get(Configuration.MULTISITE_ENABLE)).andReturn("true");
        replayMocks();
        studySubjectService.processAffliateSiteRegistrationRequest(studySubject);
        assertEquals("Wrong workflow status", RegistrationWorkFlowStatus.DISAPPROVED, studySubject.getRegWorkflowStatus());
        assertEquals("Wrong disapproval reason text", "Error", studySubject.getDisapprovalReasonText());
        verifyMocks();
    }
    
    public void testProcessAffiliateSiteRequestPhoneRandomization() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.PHONE_CALL, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        EasyMock.expect(studySubjectFactory.buildStudySubject(studySubject)).andReturn(studySubject);
        EasyMock.expect(studySubjectRepository.save(studySubject)).andReturn(studySubject);
        replayMocks();
        studySubjectService.processAffliateSiteRegistrationRequest(studySubject);
        verifyMocks();
    }
    
    public void testProcessAffiliateSiteRequestBookRandomization() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.BOOK, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        StudySubject studySubject1=new StudySubject();
        studySubject1.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.BOOK, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject1);
        studySubject1.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject1.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject1.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
        EasyMock.expect(studySubjectFactory.buildStudySubject(studySubject)).andReturn(studySubject);
        EasyMock.expect(studySubjectRepository.save(studySubject)).andReturn(studySubject).times(2);
        EasyMock.expect(studySubjectRepository.save(studySubject1)).andReturn(studySubject1);
        EasyMock.expect(studySubjectRepository.doLocalRegistration(studySubject)).andReturn(studySubject1);
        notificationEmailer.sendEmail(studySubject1);
        EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.REGISTRATION.BROADCAST.DISABLED", null, null)).andReturn("0");
        EasyMock.expect(exceptionHelper.getException(0)).andReturn(new C3PRCodedException(0, "Message Broadcast Disabled"));
        EasyMock.expect(configuration.get(Configuration.ESB_ENABLE)).andReturn("false");
        replayMocks();
        studySubjectService.processAffliateSiteRegistrationRequest(studySubject);
        assertEquals("Wrong Registration Workflow status", RegistrationWorkFlowStatus.REGISTERED, studySubject1.getRegWorkflowStatus());
        assertEquals("Wrong CCTS Workflow status", CCTSWorkflowStatusType.MESSAGE_SEND_FAILED, studySubject1.getCctsWorkflowStatus());
        verifyMocks();
    }
    
    
}
