package edu.duke.cabig.c3pr.domain.repository;

import java.util.Date;

import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.PersistedStudySubjectCreator;

public class StudySubjectRepositoryIntegrationTestCase extends DaoTestCase {
    private StudySubjectDao studySubjectDao;

    private StudySubjectRepository studySubjectRepository;
    
    private StudySubject studySubject;
    
    private PersistedStudySubjectCreator persistedStudySubjectCreator;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        studySubjectRepository=(StudySubjectRepository)getApplicationContext().getBean("studySubjectRepository");
        studySubjectDao=(StudySubjectDao)getApplicationContext().getBean("studySubjectDao");
        persistedStudySubjectCreator=new PersistedStudySubjectCreator(getApplicationContext());
    }
    
    public void testAssignC3DIdentifier(){
        studySubject=studySubjectDao.getById(1100);
        studySubjectRepository.assignC3DIdentifier(studySubject, "test c3d identifier");
        interruptSession();
        studySubject=studySubjectDao.getById(1100);
        assertEquals("Wrong C3D Identifier", "test c3d identifier", studySubject.getC3DIdentifier());
    }
    
    public void testAssignCoOrdinatingCenterIdentifier(){
        studySubject=studySubjectDao.getById(1100);
        studySubjectRepository.assignCoOrdinatingCenterIdentifier(studySubject, "test co-ordinating center identifier");
        interruptSession();
        studySubject=studySubjectDao.getById(1100);
        assertEquals("Wrong C3D Identifier", "test co-ordinating center identifier", studySubject.getCoOrdinatingCenterIdentifier().getValue());
    }
    
    public void testIsEpochAccrualCeilingReachedNonReserving(){
        studySubject=persistedStudySubjectCreator.getPersistedLocalNonRandomizedStudySubject(false, true, false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubjectDao.save(studySubject);
        interruptSession();
        assertEquals("Wrong accrual ceiling reached indicator", false, studySubjectRepository.isEpochAccrualCeilingReached(studySubject.getScheduledEpoch().getEpoch().getId()));
    }
    
    
    /**
     * Non Treatment Reserving Epoch
     * Accrual count 1
     * registrations 1
     */
    public void testIsEpochAccrualCeilingReachedReservingCase0(){
        studySubject=persistedStudySubjectCreator.getPersistedLocalNonRandomizedStudySubject(true, true, false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        (studySubject.getScheduledEpoch().getEpoch()).setAccrualCeiling(1);
        studySubjectDao.save(studySubject);
        interruptSession();
        assertEquals("Wrong accrual ceiling reached indicator", true, studySubjectRepository.isEpochAccrualCeilingReached(studySubject.getScheduledEpoch().getEpoch().getId()));
    }
    
    /**
     * Non Treatment Reserving Epoch
     * Accrual count 22
     * registrations 1
     */
    public void testIsEpochAccrualCeilingReachedReservingCase1(){
        studySubject=persistedStudySubjectCreator.getPersistedLocalNonRandomizedStudySubject(true, true, false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        (studySubject.getScheduledEpoch().getEpoch()).setAccrualCeiling(22);
        studySubjectDao.save(studySubject);
        interruptSession();
        assertEquals("Wrong accrual ceiling reached indicator", false, studySubjectRepository.isEpochAccrualCeilingReached(studySubject.getScheduledEpoch().getEpoch().getId()));
    }
    
    
    public void testSaveWithID() throws Exception{
        studySubject=studySubjectDao.getById(1100);
        studySubject.setOtherTreatingPhysician("other PI");
        Integer id=studySubjectRepository.save(studySubject).getId();
        assertNotNull("saved id is null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong PI found", "other PI", studySubject.getOtherTreatingPhysician());
    }
    
    public void testSaveWithoutID() throws Exception{
        studySubject=new StudySubject();
        studySubject.setStudySite(persistedStudySubjectCreator.getLocalNonRandomizedStudySite(false, true, false));
        persistedStudySubjectCreator.prepareToPersistNewStudySubject(studySubject);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
      //  studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
        Integer id=studySubjectRepository.save(studySubject).getId();
        assertNotNull("saved id is null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertNotNull("saved id is null", studySubject);
    }
    
    public void testDoLocalRegistrationNonRandomizedNonTreatmentStudy() throws Exception{
        studySubject = persistedStudySubjectCreator.getLocalNonRandomizedStudySubject(true, true, false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochWithEligibilityFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        studySubjectRepository.doLocalRegistration(studySubject);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
    
    public void testDoLocalRegistrationNonRandomizedTreatmentStudyWithArm() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalNonRandomizedTrestmentWithArmStudySubject(false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratification(studySubject);
        persistedStudySubjectCreator.bindArm(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        studySubjectRepository.doLocalRegistration(studySubject);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
    
    public void testDoLocalRegistrationRandomizedStudyArmNotAssigned() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.PHONE_CALL, false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratification(studySubject);
        try {
            studySubjectRepository.register(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertTrue("Wrong Exception", e instanceof C3PRCodedException);
            assertEquals("Wrong exception mesage", 200, ((C3PRCodedException)e).getExceptionCode());
            return;
        }
        
        assertEquals("Wrong registration status",RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED,studySubject.getRegWorkflowStatus());
        assertEquals("Wrong scheduled epoch status",ScheduledEpochWorkFlowStatus.REGISTERED_BUT_NOT_RANDOMIZED,studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
    
    public void testDoLocalRegistrationRandomizedStudyArmAssignedPhoneCall() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.PHONE_CALL, false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratification(studySubject);
        persistedStudySubjectCreator.bindRandomization(studySubject);
        studySubjectDao.save(studySubject);
        try {
            studySubjectRepository.doLocalRegistration(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertFalse("Shouldnt have thrown exception",true);
            return;
        }
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
    
    public void testDoLocalRegistrationStraightRandomizedBookStudyStratumGroupAbsent() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.BOOK, false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratificationInvalid(studySubject);
        try {
            studySubjectRepository.enroll(studySubject);
        }
        catch (Exception e){
           e.printStackTrace();
        }
        assertEquals("Wrong registration status",RegistrationWorkFlowStatus.ENROLLED,studySubject.getRegWorkflowStatus());
        assertEquals("Wrong scheduled epoch status",ScheduledEpochWorkFlowStatus.REGISTERED,studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
    
    public void testDoLocalRegistrationRandomizedStudyBookStratumGroupPresent() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.BOOK, false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratification(studySubject);
        try {
            studySubjectRepository.doLocalRegistration(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertFalse("Shouldnt have thrown exception",true);
            return;
        }
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
    
    public void testDoLocalRegistrationRandomizedStudyBookStratumGroupNumberPresent() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.BOOK, false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratificationInvalid(studySubject);
        try {
            studySubjectRepository.doLocalRegistration(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertFalse("Shouldnt have thrown exception",true);
            return;
        }
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
}