package edu.duke.cabig.c3pr.service;

import java.util.Date;

import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
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
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.impl.StudySubjectServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.PersistedStudySubjectCreator;
import edu.duke.cabig.c3pr.utils.StudySubjectCreatorHelper;
import edu.duke.cabig.c3pr.utils.StudyTargetAccrualNotificationEmail;

public class StudySubjectServiceIntegrationTestCase extends DaoTestCase {
    
    private StudySubjectService studySubjectService;
    private PersistedStudySubjectCreator persistedStudySubjectCreator;
    private StudySubject studySubject;
    private StudySubjectDao studySubjectDao;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        persistedStudySubjectCreator=new PersistedStudySubjectCreator(getApplicationContext());
        studySubjectDao=(StudySubjectDao)getApplicationContext().getBean("studySubjectDao");
        studySubjectService=(StudySubjectService)getApplicationContext().getBean("studySubjectService");
    }
    
    /**
     * Epoch Workflow Status test 
     * Not Multi Site Trial 
     * Treatment Epoch 
     * Epoch Data Entry Status:Incomplete 
     * Registration Data Entry Status: Complete
     */
    public void testRegisterIncompleteDataEntry() throws Exception {
        studySubject=persistedStudySubjectCreator.getLocalNonRandomizedWithArmStudySubject(false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        Integer id=studySubjectService.register(studySubject).getId();
        assertNotNull("Id should not be null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Epoch WorkFlow Status", ScheduledEpochWorkFlowStatus.UNAPPROVED,
                        studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration WorkFlow Status", RegistrationWorkFlowStatus.UNREGISTERED,
                        studySubject.getRegWorkflowStatus());
    }
    
  //---------------------Local study Registration Tests-----------------------------
    public void testRegisterLocalRegistrationNonRandomizedNonTreatmentStudy() throws Exception{
        studySubject = persistedStudySubjectCreator.getLocalNonRandomizedStudySubject(true, false, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        Integer id=studySubjectService.register(studySubject).getId();
        assertNotNull("Id should not be null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration WorkFlow Status", RegistrationWorkFlowStatus.RESERVED,
                        studySubject.getRegWorkflowStatus());
    }
    
    public void testRegisterLocalRegistrationNonRandomizedTreatmentStudyWithArm() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalNonRandomizedWithArmStudySubject(false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        Integer id=studySubjectService.register(studySubject).getId();
        assertNotNull("Id should not be null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration WorkFlow Status", RegistrationWorkFlowStatus.REGISTERED,
                        studySubject.getRegWorkflowStatus());
    }
    
    public void testRegisterLocalRegistrationRandomizedStudyArmNotAssigned() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.PHONE_CALL, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratification(studySubject);
        try {
            studySubjectService.register(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertTrue("Wrong Exception", e.getCause() instanceof C3PRCodedException);
            assertEquals("Wrong exception mesage", 200, ((C3PRCodedException)e.getCause()).getExceptionCode());
            return;
        }
        assertFalse("Should have thrown exception",true);
    }
    
    public void testRegisterLocalRegistrationRandomizedStudyArmAssignedPhoneCall() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.PHONE_CALL, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        Integer id=studySubjectService.register(studySubject).getId();
        assertNotNull("Id should not be null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration WorkFlow Status", RegistrationWorkFlowStatus.REGISTERED,
                        studySubject.getRegWorkflowStatus());
    }
    
    public void testRegisterLocalRegistrationRandomizedStudyBookStratumGroupAbsent() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.BOOK, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratificationInvalid(studySubject);
        try {
            studySubjectService.register(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertTrue("Wrong Exception", e.getCause() instanceof C3PRCodedException);
            assertEquals("Wrong exception mesage", 202, ((C3PRCodedException)e.getCause()).getExceptionCode());
            return;
        }
        assertFalse("Should have thrown exception",true);
    }
    
    public void testRegisterLocalRegistrationRandomizedStudyBookStratumGroupPresent() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.BOOK, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        Integer id=studySubjectService.register(studySubject).getId();
        assertNotNull("Id should not be null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration WorkFlow Status", RegistrationWorkFlowStatus.REGISTERED,
                        studySubject.getRegWorkflowStatus());
    }
    
    public void testRegisterLocalRegistrationRandomizedStudyBookStratumGroupNumberPresent() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.BOOK, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        studySubject.setStratumGroupNumber(0);
        Integer id=studySubjectService.register(studySubject).getId();
        assertNotNull("Id should not be null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration WorkFlow Status", RegistrationWorkFlowStatus.REGISTERED,
                        studySubject.getRegWorkflowStatus());
    }
    
    //---------------------MultiSite Study Registration Tests-----------------------------
    
    public void testRegisterMultiSiteRegistrationNonRandomizedNonTreatmentStudy() throws Exception{
        studySubjectService.setHostedMode(false);
        studySubject = persistedStudySubjectCreator.getMultiSiteNonRandomizedStudySubject(false, true, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        Integer id=studySubjectService.register(studySubject).getId();
        assertNotNull("Id should not be null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.PENDING, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration WorkFlow Status", RegistrationWorkFlowStatus.PENDING,
                        studySubject.getRegWorkflowStatus());
    }
    
    public void testRegisterMultiSitestrationNonRandomizedTreatmentStudyWithArm() throws Exception{
        studySubjectService.setHostedMode(false);
        studySubject=persistedStudySubjectCreator.getMultiSiteNonRandomizedWithArmStudySubject(false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        Integer id=studySubjectService.register(studySubject).getId();
        assertNotNull("Id should not be null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.PENDING, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration WorkFlow Status", RegistrationWorkFlowStatus.PENDING,
                        studySubject.getRegWorkflowStatus());
    }
    
    public void testRegisterMultiSiteRegistrationRandomizedStudyArmNotAssigned() throws Exception{
        studySubjectService.setHostedMode(false);
        studySubject=persistedStudySubjectCreator.getMultiSiteRandomizedStudySubject(RandomizationType.PHONE_CALL, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratification(studySubject);
        Integer id=studySubjectService.register(studySubject).getId();
        assertNotNull("Id should not be null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.PENDING, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration WorkFlow Status", RegistrationWorkFlowStatus.PENDING,
                        studySubject.getRegWorkflowStatus());
    }
    
    public void testRegisterMultiSiteRegistrationRandomizedStudyArmAssignedPhoneCall() throws Exception{
        studySubjectService.setHostedMode(false);
        studySubject=persistedStudySubjectCreator.getMultiSiteRandomizedStudySubject(RandomizationType.PHONE_CALL, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        studySubjectDao.save(studySubject);
        Integer id=studySubjectService.register(studySubject).getId();
        assertNotNull("Id should not be null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.PENDING, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration WorkFlow Status", RegistrationWorkFlowStatus.PENDING,
                        studySubject.getRegWorkflowStatus());
    }
    
    public void testRegisterMultiSiteRegistrationRandomizedStudyBookStratumGroupAbsent() throws Exception{
        studySubjectService.setHostedMode(false);
        studySubject=persistedStudySubjectCreator.getMultiSiteRandomizedStudySubject(RandomizationType.BOOK, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratificationInvalid(studySubject);
        Integer id=studySubjectService.register(studySubject).getId();
        assertNotNull("Id should not be null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.PENDING, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration WorkFlow Status", RegistrationWorkFlowStatus.PENDING,
                        studySubject.getRegWorkflowStatus());
    }
    
    public void testRegisterMultiSiteRegistrationRandomizedStudyBookStratumGroupPresent() throws Exception{
        studySubjectService.setHostedMode(false);
        studySubject=persistedStudySubjectCreator.getMultiSiteRandomizedStudySubject(RandomizationType.BOOK, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        Integer id=studySubjectService.register(studySubject).getId();
        assertNotNull("Id should not be null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.PENDING, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration WorkFlow Status", RegistrationWorkFlowStatus.PENDING,
                        studySubject.getRegWorkflowStatus());
    }
    
    public void testRegisterMultiSiteRegistrationRandomizedStudyBookStratumGroupNumberPresent() throws Exception{
        studySubjectService.setHostedMode(false);
        studySubject=persistedStudySubjectCreator.getMultiSiteRandomizedStudySubject(RandomizationType.BOOK, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        studySubject.setStratumGroupNumber(0);
        Integer id=studySubjectService.register(studySubject).getId();
        assertNotNull("Id should not be null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.PENDING, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration WorkFlow Status", RegistrationWorkFlowStatus.PENDING,
                        studySubject.getRegWorkflowStatus());
    }

}
