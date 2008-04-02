package edu.duke.cabig.c3pr.domain.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.easymock.classextension.EasyMock;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StratumGroupDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.NonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.factory.StudySubjectFactory;
import edu.duke.cabig.c3pr.domain.repository.impl.StudySubjectRepositoryImpl;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.utils.StudySubjectCreatorHelper;

public class StudySubjectRepositoryTestCase extends AbstractTestCase {
    private StudySubjectDao studySubjectDao;

    private ParticipantDao participantDao;

    private EpochDao epochDao;

    private StratumGroupDao stratumGroupDao;
    
    private C3PRExceptionHelper exceptionHelper;

    private MessageSource c3prErrorMessages;
    
    private StudySubjectFactory studySubjectFactory;
    
    private StudySubjectRepository studySubjectRepository;
    
    private StudySubject studySubject;
    
    private StudySubjectCreatorHelper studySubjectCreatorHelper;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        studySubjectDao=registerDaoMockFor(StudySubjectDao.class);
        participantDao=registerDaoMockFor(ParticipantDao.class);
        epochDao=registerDaoMockFor(EpochDao.class);
        stratumGroupDao=registerDaoMockFor(StratumGroupDao.class);
        studySubjectFactory=registerMockFor(StudySubjectFactory.class);
        exceptionHelper=registerMockFor(C3PRExceptionHelper.class);
        c3prErrorMessages=registerMockFor(MessageSource.class);
        StudySubjectRepositoryImpl studySubjectRepositoryImpl=new StudySubjectRepositoryImpl();
        studySubjectRepositoryImpl.setC3prErrorMessages(c3prErrorMessages);
        studySubjectRepositoryImpl.setEpochDao(epochDao);
        studySubjectRepositoryImpl.setExceptionHelper(exceptionHelper);
        studySubjectRepositoryImpl.setParticipantDao(participantDao);
        studySubjectRepositoryImpl.setStratumGroupDao(stratumGroupDao);
        studySubjectRepositoryImpl.setStudySubjectDao(studySubjectDao);
        studySubjectRepositoryImpl.setStudySubjectFactory(studySubjectFactory);
        studySubjectRepository=studySubjectRepositoryImpl;
        studySubject=new StudySubject();
        studySubjectCreatorHelper=new StudySubjectCreatorHelper();
    }
    
    public void testAssignC3DIdentifier(){
        studySubject.setGridId("test grid");
        EasyMock.expect(studySubjectDao.getByGridId("test grid")).andReturn(studySubject);
        studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.assignC3DIdentifier(studySubject, "test c3d identifier");
        verifyMocks();
        assertEquals("Wrong C3D Identifier", "test c3d identifier", studySubject.getC3DIdentifier());
    }
    
    public void testAssignCoOrdinatingCenterIdentifier(){
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
        studySubject.setGridId("test grid");
        EasyMock.expect(studySubjectDao.getByGridId("test grid")).andReturn(studySubject);
        studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.assignCoOrdinatingCenterIdentifier(studySubject, "test co-ordinating center identifier");
        verifyMocks();
        assertEquals("Wrong C3D Identifier", "test co-ordinating center identifier", studySubject.getCoOrdinatingCenterIdentifier());
    }
    
    public void testIsEpochAccrualCeilingReachedNonReserving(){
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(false, true, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().getEpoch().setId(1);
        EasyMock.expect(epochDao.getById(1)).andReturn(studySubject.getScheduledEpoch().getEpoch());
        replayMocks();
        assertEquals("Wrong accrual ceiling reached indicator", false, studySubjectRepository.isEpochAccrualCeilingReached(1));
        verifyMocks();
    }
    
    
    /**
     * Non Treatment Reserving Epoch
     * Accrual count 22
     * registrations 0
     */
    public void testIsEpochAccrualCeilingReachedReservingCase0(){
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(true, true, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().getEpoch().setId(1);
        ((NonTreatmentEpoch)studySubject.getScheduledEpoch().getEpoch()).setAccrualCeiling(22);
        EasyMock.expect(epochDao.getById(1)).andReturn(studySubject.getScheduledEpoch().getEpoch());
        EasyMock.expect(studySubjectDao.searchByScheduledEpoch(EasyMock.isA(ScheduledNonTreatmentEpoch.class))).andReturn(new ArrayList<StudySubject>());
        replayMocks();
        assertEquals("Wrong accrual ceiling reached indicator", false, studySubjectRepository.isEpochAccrualCeilingReached(1));
        verifyMocks();
    }
    
    /**
     * Non Treatment Reserving Epoch
     * Accrual count 22
     * registrations 22
     */
    public void testIsEpochAccrualCeilingReachedReservingCase1(){
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(true, true, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().getEpoch().setId(1);
        ((NonTreatmentEpoch)studySubject.getScheduledEpoch().getEpoch()).setAccrualCeiling(22);
        StudySubject[] subs=new StudySubject[22];
        EasyMock.expect(epochDao.getById(1)).andReturn(studySubject.getScheduledEpoch().getEpoch());
        EasyMock.expect(studySubjectDao.searchByScheduledEpoch(EasyMock.isA(ScheduledNonTreatmentEpoch.class))).andReturn(Arrays.asList(subs));
        replayMocks();
        assertEquals("Wrong accrual ceiling reached indicator", true, studySubjectRepository.isEpochAccrualCeilingReached(1));
        verifyMocks();
    }
    
    /**
     * Non Treatment Reserving Epoch
     * Accrual count 22
     * registrations >22
     */
    public void testIsEpochAccrualCeilingReachedReservingCase2(){
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(true, true, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().getEpoch().setId(1);
        ((NonTreatmentEpoch)studySubject.getScheduledEpoch().getEpoch()).setAccrualCeiling(22);
        StudySubject[] subs=new StudySubject[40];
        EasyMock.expect(epochDao.getById(1)).andReturn(studySubject.getScheduledEpoch().getEpoch());
        EasyMock.expect(studySubjectDao.searchByScheduledEpoch(EasyMock.isA(ScheduledNonTreatmentEpoch.class))).andReturn(Arrays.asList(subs));
        replayMocks();
        assertEquals("Wrong accrual ceiling reached indicator", true, studySubjectRepository.isEpochAccrualCeilingReached(1));
        verifyMocks();
    }
    
    
    public void testSaveWithID() throws Exception{
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubject.setId(1);
        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
        replayMocks();
        studySubjectRepository.save(studySubject);
        verifyMocks();
    }
    
    public void testSaveWithoutID() throws Exception{
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.save(studySubject);
        verifyMocks();
    }
    
    public void testDoLocalRegistrationDataIncompleteCase0() throws Exception{
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.INCOMPLETE);
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(true, true, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.INCOMPLETE);
        replayMocks();
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.UNAPPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testDoLocalRegistrationDataIncompleteCase1() throws Exception{
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.INCOMPLETE);
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(true, true, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        replayMocks();
        studySubjectRepository.doLocalRegistration(studySubject);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.UNAPPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testDoLocalRegistrationNonRndomizedNonTreatmentStudy() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(true, true, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.doLocalRegistration(studySubject);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testDoLocalRegistrationNonRndomizedTreatmentStudyWithArm() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedTreatmentWithArmStudySite(false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        ScheduledArm scheduledArm = new ScheduledArm();
        scheduledArm.setArm(new Arm());
        ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch()).addScheduledArm(scheduledArm);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.doLocalRegistration(studySubject);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testDoLocalRegistrationRndomizedStudyArmNotAssigned() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.PHONE_CALL, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.REGISTRATION.CANNOT_ASSIGN_ARM.CODE", null, null)).andReturn("1");
        EasyMock.expect(exceptionHelper.getException(1)).andReturn(new C3PRCodedException(1,"Arm not assigned"));
        replayMocks();
        try {
            studySubjectRepository.doLocalRegistration(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertTrue("Wrong Exception", e instanceof C3PRCodedException);
            assertEquals("Wrong exception mesage", "Arm not assigned", ((C3PRCodedException)e).getCodedExceptionMesssage());
            verifyMocks();
            return;
        }
        assertFalse("Should have thrown exception",true);
        verifyMocks();
    }
    
    public void testDoLocalRegistrationRndomizedStudyArmAssignedPhoneCall() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.PHONE_CALL, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        studySubjectCreatorHelper.bindRandomization(studySubject);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubjectDao.save(studySubject);
        replayMocks();
        try {
            studySubjectRepository.doLocalRegistration(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertFalse("Shouldnt have thrown exception",true);
            verifyMocks();
            return;
        }
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testDoLocalRegistrationRndomizedStudyBookStratumGroupAbsent() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.BOOK, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratificationInvalid(studySubject);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.REGISTRATION.RANDOMIZATION.CODE", null, null)).andReturn("1");
        EasyMock.expect(exceptionHelper.getException(EasyMock.eq(1),EasyMock.isA(Exception.class))).andReturn(new C3PRCodedException(1,"Stratum Grop Absent"));
        replayMocks();
        try {
            studySubjectRepository.doLocalRegistration(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertTrue("Wrong Exception", e instanceof C3PRCodedException);
            assertEquals("Wrong exception mesage", "Stratum Grop Absent", ((C3PRCodedException)e).getCodedExceptionMesssage());
            verifyMocks();
            return;
        }
        assertFalse("Should have thrown exception",true);
        verifyMocks();
    }
    
    public void testDoLocalRegistrationRndomizedStudyBookStratumGroupPresent() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.BOOK, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubjectDao.save(studySubject);
        EasyMock.expect(stratumGroupDao.merge(EasyMock.isA(StratumGroup.class))).andReturn(new StratumGroup());
        replayMocks();
        try {
            studySubjectRepository.doLocalRegistration(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertFalse("Shouldnt have thrown exception",true);
            verifyMocks();
            return;
        }
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testDoLocalRegistrationRndomizedStudyBookStratumGroupNumberPresent() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.BOOK, false));
        studySubjectCreatorHelper.addScheduledEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratificationInvalid(studySubject);
        studySubject.setStratumGroupNumber(0);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubjectDao.save(studySubject);
        EasyMock.expect(stratumGroupDao.merge(EasyMock.isA(StratumGroup.class))).andReturn(new StratumGroup());
        replayMocks();
        try {
            studySubjectRepository.doLocalRegistration(studySubject);
        }
        catch (Exception e){
            e.printStackTrace();
            assertFalse("Shouldnt have thrown exception",true);
            verifyMocks();
            return;
        }
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.APPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
}
