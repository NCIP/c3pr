package edu.duke.cabig.c3pr.domain.repository;

import java.util.Date;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.NonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
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
        assertEquals("Wrong C3D Identifier", "test co-ordinating center identifier", studySubject.getCoOrdinatingCenterIdentifier());
    }
    
    public void testIsEpochAccrualCeilingReachedNonReserving(){
        studySubject=persistedStudySubjectCreator.getPersistedLocalNonRandomizedStudySubject(false, true, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
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
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        ((NonTreatmentEpoch)studySubject.getScheduledEpoch().getEpoch()).setAccrualCeiling(1);
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
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        ((NonTreatmentEpoch)studySubject.getScheduledEpoch().getEpoch()).setAccrualCeiling(22);
        studySubjectDao.save(studySubject);
        interruptSession();
        assertEquals("Wrong accrual ceiling reached indicator", false, studySubjectRepository.isEpochAccrualCeilingReached(studySubject.getScheduledEpoch().getEpoch().getId()));
    }
    
    
    public void testSaveWithID() throws Exception{
        studySubject=studySubjectDao.getById(1100);
        studySubject.setInformedConsentVersion("1.4");
        Integer id=studySubjectRepository.save(studySubject).getId();
        assertNotNull("saved id is null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertEquals("Wrong Consent version", "1.4", studySubject.getInformedConsentVersion());
    }
    
    public void testSaveWithoutID() throws Exception{
        studySubject=new StudySubject();
        studySubject.setStudySite(persistedStudySubjectCreator.getLocalNonRandomizedStudySite(false, true, false));
        persistedStudySubjectCreator.prepareToPersistNewStudySubject(studySubject);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        Integer id=studySubjectRepository.save(studySubject).getId();
        assertNotNull("saved id is null", id);
        interruptSession();
        studySubject=studySubjectDao.getById(id);
        assertNotNull("saved id is null", studySubject);
    }
    
    public void testDoLocalRegistrationNonRandomizedNonTreatmentStudy() throws Exception{
        studySubject = persistedStudySubjectCreator.getLocalNonRandomizedStudySubject(true, true, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectRepository.doLocalRegistration(studySubject);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
    
    public void testDoLocalRegistrationNonRandomizedTreatmentStudyWithArm() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalNonRandomizedWithArmStudySubject(false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratification(studySubject);
        persistedStudySubjectCreator.bindArm(studySubject);
        studySubjectRepository.doLocalRegistration(studySubject);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
    
    public void testDoLocalRegistrationRandomizedStudyArmNotAssigned() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.PHONE_CALL, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratification(studySubject);
        try {
            studySubjectRepository.doLocalRegistration(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertTrue("Wrong Exception", e instanceof C3PRCodedException);
            assertEquals("Wrong exception mesage", 200, ((C3PRCodedException)e).getExceptionCode());
            return;
        }
        assertFalse("Should have thrown exception",true);
    }
    
    public void testDoLocalRegistrationRandomizedStudyArmAssignedPhoneCall() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.PHONE_CALL, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
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
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
    
    public void testDoLocalRegistrationRandomizedStudyBookStratumGroupAbsent() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.BOOK, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratificationInvalid(studySubject);
        try {
            studySubjectRepository.doLocalRegistration(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertTrue("Wrong Exception", e instanceof C3PRCodedException);
            assertEquals("Wrong exception mesage", 202, ((C3PRCodedException)e).getExceptionCode());
            return;
        }
        assertFalse("Should have thrown exception",true);
    }
    
    public void testDoLocalRegistrationRandomizedStudyBookStratumGroupPresent() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.BOOK, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
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
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
    
    public void testDoLocalRegistrationRandomizedStudyBookStratumGroupNumberPresent() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.BOOK, false);
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratificationInvalid(studySubject);
        studySubject.setStratumGroupNumber(0);
        try {
            studySubjectRepository.doLocalRegistration(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertFalse("Shouldnt have thrown exception",true);
            return;
        }
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
}
