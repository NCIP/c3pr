package edu.duke.cabig.c3pr.domain.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.easymock.classextension.EasyMock;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StratumGroupDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.factory.StudySubjectFactory;
import edu.duke.cabig.c3pr.domain.repository.impl.StudySubjectRepositoryImpl;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.utils.StudySubjectCreatorHelper;
import edu.duke.cabig.c3pr.utils.StudyTargetAccrualNotificationEmail;

public class StudySubjectRepositoryTestCase extends AbstractTestCase {
    private StudySubjectDao studySubjectDao;

    private EpochDao epochDao;
    
    private ParticipantDao participantDao;

    private StratumGroupDao stratumGroupDao;
    
    private C3PRExceptionHelper exceptionHelper;

    private MessageSource c3prErrorMessages;
    
    private StudySubjectFactory studySubjectFactory;
    
    private StudySubjectRepository studySubjectRepository;
    
    private StudySubject studySubject;
    
    private StudySubjectService studySubjectService;
    
    private StudySubjectCreatorHelper studySubjectCreatorHelper;
    
    private StudyTargetAccrualNotificationEmail notificationEmailer;
    
    private Logger log = Logger.getLogger(StudySubjectRepositoryTestCase.class.getName());
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        studySubjectDao=registerDaoMockFor(StudySubjectDao.class);
        epochDao=registerDaoMockFor(EpochDao.class);
        stratumGroupDao=registerDaoMockFor(StratumGroupDao.class);
        participantDao=registerDaoMockFor(ParticipantDao.class); 
        studySubjectFactory=registerMockFor(StudySubjectFactory.class);
        studySubjectService=registerMockFor(StudySubjectService.class);
        notificationEmailer=registerMockFor(StudyTargetAccrualNotificationEmail.class);
        exceptionHelper=registerMockFor(C3PRExceptionHelper.class);
        c3prErrorMessages=registerMockFor(MessageSource.class);
        StudySubjectRepositoryImpl studySubjectRepositoryImpl=new StudySubjectRepositoryImpl();
        studySubjectRepositoryImpl.setC3prErrorMessages(c3prErrorMessages);
        studySubjectRepositoryImpl.setEpochDao(epochDao);
        studySubjectRepositoryImpl.setExceptionHelper(exceptionHelper);
        studySubjectRepositoryImpl.setStratumGroupDao(stratumGroupDao);
        studySubjectRepositoryImpl.setStudySubjectDao(studySubjectDao);
        studySubjectRepositoryImpl.setStudySubjectFactory(studySubjectFactory);
        studySubjectRepositoryImpl.setParticipantDao(participantDao);
        studySubjectRepositoryImpl.setStudySubjectService(studySubjectService);
        studySubjectRepositoryImpl.setNotificationEmailer(notificationEmailer);
        studySubjectRepository=studySubjectRepositoryImpl;
        studySubject=new StudySubject();
        studySubjectCreatorHelper=new StudySubjectCreatorHelper();
        studySubject.setParticipant(studySubjectCreatorHelper.createNewParticipant());
    }
    
   /* public void testAssignC3DIdentifier(){
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
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().getEpoch().setId(1);
        EasyMock.expect(epochDao.getById(1)).andReturn(studySubject.getScheduledEpoch().getEpoch());
        replayMocks();
        assertEquals("Wrong accrual ceiling reached indicator", false, studySubjectRepository.isEpochAccrualCeilingReached(1));
        verifyMocks();
    }
    
    
    *//**
     * Non Reserving Epoch
     * Accrual count 22
     * registrations 0
     *//*
    public void testIsEpochAccrualCeilingReachedReservingCase0(){
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(true, true, false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().getEpoch().setId(1);
        (studySubject.getScheduledEpoch().getEpoch()).setAccrualCeiling(22);
        EasyMock.expect(epochDao.getById(1)).andReturn(studySubject.getScheduledEpoch().getEpoch());
        EasyMock.expect(studySubjectDao.searchByScheduledEpoch(EasyMock.isA(ScheduledEpoch.class))).andReturn(new ArrayList<StudySubject>());
        replayMocks();
        assertEquals("Wrong accrual ceiling reached indicator", false, studySubjectRepository.isEpochAccrualCeilingReached(1));
        verifyMocks();
    }
    
    *//**
     * Reserving Epoch
     * Accrual count 22
     * registrations 22
     *//*
    public void testIsEpochAccrualCeilingReachedReservingCase1(){
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(true, true, false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().getEpoch().setId(1);
        (studySubject.getScheduledEpoch().getEpoch()).setAccrualCeiling(22);
        StudySubject[] subs=new StudySubject[22];
        EasyMock.expect(epochDao.getById(1)).andReturn(studySubject.getScheduledEpoch().getEpoch());
        EasyMock.expect(studySubjectDao.searchByScheduledEpoch(EasyMock.isA(ScheduledEpoch.class))).andReturn(Arrays.asList(subs));
        replayMocks();
        assertEquals("Wrong accrual ceiling reached indicator", true, studySubjectRepository.isEpochAccrualCeilingReached(1));
        verifyMocks();
    }
    
    *//**
     * Reserving Epoch
     * Accrual count 22
     * registrations >22
     *//*
    public void testIsEpochAccrualCeilingReachedReservingCase2(){
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(true, true, false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().getEpoch().setId(1);
        (studySubject.getScheduledEpoch().getEpoch()).setAccrualCeiling(22);
        StudySubject[] subs=new StudySubject[40];
        EasyMock.expect(epochDao.getById(1)).andReturn(studySubject.getScheduledEpoch().getEpoch());
        EasyMock.expect(studySubjectDao.searchByScheduledEpoch(EasyMock.isA(ScheduledEpoch.class))).andReturn(Arrays.asList(subs));
        replayMocks();
        assertEquals("Wrong accrual ceiling reached indicator", true, studySubjectRepository.isEpochAccrualCeilingReached(1));
        verifyMocks();
    }
    
    
    public void testSaveWithID() throws Exception{
        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
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
        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
        //studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.save(studySubject);
        verifyMocks();
    }
    
    public void testDoLocalRegistrationDataIncompleteCase0() throws Exception{
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.INCOMPLETE);
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(true, true, false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.INCOMPLETE);
        replayMocks();
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.PENDING, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testDoLocalRegistrationDataIncompleteCase1() throws Exception{
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.INCOMPLETE);
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(true, true, false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        replayMocks();
        studySubjectRepository.doLocalRegistration(studySubject);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.PENDING, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testDoLocalRegistrationNonRndomizedNonTreatmentStudy() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedStudySite(true, true, false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.getScheduledEpoch().setEligibilityIndicator(true);
        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
       // studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.doLocalRegistration(studySubject);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testDoLocalRegistrationNonRndomizedTreatmentStudyWithArm() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedTreatmentWithArmStudySite(false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        ScheduledArm scheduledArm = new ScheduledArm();
        scheduledArm.setArm(new Arm());
        ((ScheduledEpoch) studySubject.getScheduledEpoch()).addScheduledArm(scheduledArm);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
        //studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.doLocalRegistration(studySubject);
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testDoLocalRegistrationRndomizedStudyArmNotAssigned() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.PHONE_CALL, false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
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
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        studySubjectCreatorHelper.bindRandomization(studySubject);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
        // studySubjectDao.save(studySubject);
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
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testDoLocalRegistrationRndomizedStudyBookStratumGroupAbsent() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.BOOK, false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
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
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
        //studySubjectDao.save(studySubject);
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
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testDoLocalRegistrationRndomizedStudyBookStratumGroupNumberPresent() throws Exception{
        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.BOOK, false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratificationInvalid(studySubject);
        studySubject.setStratumGroupNumber(0);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
        //studySubjectDao.save(studySubject);
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
        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testImportStudySubjectLocalNonRandomizedWithArm() {
    	StudySubject deserializedStudySubject = new StudySubject();    	
    	deserializedStudySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedTreatmentWithArmStudySite(true));
    	deserializedStudySubject.setParticipant(buildParticipant());
    	deserializedStudySubject.getScheduledEpochs().add(buildScheduledEpoch());
    	deserializedStudySubject.setInformedConsentSignedDate(new Date());
    	deserializedStudySubject.setInformedConsentVersion("ver-001");
    	deserializedStudySubject.setStratumGroupNumber(10);
    	
    	try  {
    		EasyMock.expect(studySubjectFactory.buildStudySubject(deserializedStudySubject)).andReturn(deserializedStudySubject);
    	} catch(C3PRCodedException cce){
    		log.error("studySubjectFactory.buildStudySubject() threw exception");
    	}
//        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite(new StudySubject(true))).andReturn(new ArrayList<StudySubject>());
        participantDao.save(deserializedStudySubject.getParticipant());
        studySubjectDao.save(deserializedStudySubject);
        replayMocks();
        
        try{
        	StudySubject studySubject = studySubjectRepository.importStudySubject(deserializedStudySubject);            
        } catch(C3PRCodedException e){
        	assertFalse("C3PRCodedException thrown", false);
        }
        verifyMocks();
    }
    
    public void testImportStudySubjectMultiSiteNonRandomizedWithArm() {
    	StudySubject deserializedStudySubject = new StudySubject();    	
    	deserializedStudySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedWithArmStudySite(true));
    	deserializedStudySubject.setParticipant(buildParticipant());
    	deserializedStudySubject.getScheduledEpochs().add(buildScheduledEpoch());
    	deserializedStudySubject.setInformedConsentSignedDate(new Date());
    	deserializedStudySubject.setInformedConsentVersion("ver-001");
    	deserializedStudySubject.setStratumGroupNumber(11);
    	
    	try  {
    		EasyMock.expect(studySubjectFactory.buildStudySubject(deserializedStudySubject)).andReturn(deserializedStudySubject);
    	} catch(C3PRCodedException cce){
    		log.error("studySubjectFactory.buildStudySubject() threw exception");
    	}
        participantDao.save(deserializedStudySubject.getParticipant());
        studySubjectDao.save(deserializedStudySubject);
        replayMocks();
        try{
        	StudySubject studySubject = studySubjectRepository.importStudySubject(deserializedStudySubject);            
        } catch(C3PRCodedException e){
        	assertFalse("C3PRCodedException thrown", false);
        }
        verifyMocks();
    }
    
    public void testUpdateLocalRegistrationNoParticipantFound() {
        StudySubject deserializedStudySubject = new StudySubject();     
        deserializedStudySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedWithArmStudySite(true));
        deserializedStudySubject.setParticipant(buildParticipant());
        deserializedStudySubject.getScheduledEpochs().add(buildScheduledEpoch());
        System.out.println("Calling...");
        try  {
                EasyMock.expect(studySubjectFactory.buildReferencedStudySubject(deserializedStudySubject)).andReturn(deserializedStudySubject);
        } catch(C3PRCodedException cce){
                log.error("studySubjectFactory.buildStudySubject() threw exception");
        }
        replayMocks();
        try{
            studySubjectRepository.updateLocalRegistration(deserializedStudySubject);            
        } catch(RuntimeException e){
            e.printStackTrace();
            assertTrue(true);
            verifyMocks();
            return;
        }
        assertTrue("Exception expected", false);
    }
    
    public void testUpdateLocalRegistrationNoRegistrationFound() {
        StudySubject deserializedStudySubject = new StudySubject();     
        deserializedStudySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedWithArmStudySite(true));
        deserializedStudySubject.setParticipant(buildParticipant());
        deserializedStudySubject.getParticipant().setId(0);
        deserializedStudySubject.getScheduledEpochs().add(buildScheduledEpoch());
        try  {
                EasyMock.expect(studySubjectFactory.buildReferencedStudySubject(deserializedStudySubject)).andReturn(deserializedStudySubject);
        } catch(C3PRCodedException cce){
                log.error("studySubjectFactory.buildStudySubject() threw exception");
        }
        StudySubject exampleSS = new StudySubject(true);
        exampleSS.setParticipant(deserializedStudySubject.getParticipant());
        exampleSS.setStudySite(deserializedStudySubject.getStudySite());
        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite(exampleSS)).andReturn(new ArrayList<StudySubject>());
        replayMocks();
        try{
            studySubjectRepository.updateLocalRegistration(deserializedStudySubject);            
        } catch(RuntimeException e){
            e.printStackTrace();
            assertTrue(true);
            verifyMocks();
            return;
        }
        assertTrue("Exception expected", false);
    }
    
    public void testUpdateLocalRegistrationMultipleRegistrationsFound() {
        StudySubject deserializedStudySubject = new StudySubject();     
        deserializedStudySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedWithArmStudySite(true));
        deserializedStudySubject.setParticipant(buildParticipant());
        deserializedStudySubject.getParticipant().setId(0);
        deserializedStudySubject.getScheduledEpochs().add(buildScheduledEpoch());
        try  {
                EasyMock.expect(studySubjectFactory.buildReferencedStudySubject(deserializedStudySubject)).andReturn(deserializedStudySubject);
        } catch(C3PRCodedException cce){
                log.error("studySubjectFactory.buildStudySubject() threw exception");
        }
        StudySubject exampleSS = new StudySubject(true);
        exampleSS.setParticipant(deserializedStudySubject.getParticipant());
        exampleSS.setStudySite(deserializedStudySubject.getStudySite());
        List<StudySubject> regs=new ArrayList<StudySubject>();
        regs.add(new StudySubject());
        regs.add(new StudySubject());
        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite(exampleSS)).andReturn(regs);
        replayMocks();
        try{
            studySubjectRepository.updateLocalRegistration(deserializedStudySubject);            
        } catch(RuntimeException e){
            e.printStackTrace();
            assertTrue(true);
            verifyMocks();
            return;
        }
        assertTrue("Exception expected", false);
    }
    
    public void testUpdateLocalRegistrationErrorBuildingRegistration() {
        StudySubject deserializedStudySubject = new StudySubject();     
        deserializedStudySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedWithArmStudySite(true));
        deserializedStudySubject.setParticipant(buildParticipant());
        deserializedStudySubject.getParticipant().setId(0);
        deserializedStudySubject.getScheduledEpochs().add(buildScheduledEpoch());
        try  {
                EasyMock.expect(studySubjectFactory.buildReferencedStudySubject(deserializedStudySubject)).andThrow(new C3PRCodedException(0,"Error"));
        } catch(C3PRCodedException cce){
                log.error("studySubjectFactory.buildStudySubject() threw exception");
        }
        replayMocks();
        try{
            studySubjectRepository.updateLocalRegistration(deserializedStudySubject);            
        } catch(RuntimeException e){
            e.printStackTrace();
            assertTrue(true);
            verifyMocks();
            return;
        }
        assertTrue("Exception expected", false);
    }
    
    public void testUpdateLocalRegistrationNotPendingRegistration() {
        StudySubject deserializedStudySubject = new StudySubject();     
        deserializedStudySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedWithArmStudySite(true));
        deserializedStudySubject.setParticipant(buildParticipant());
        deserializedStudySubject.getParticipant().setId(0);
        deserializedStudySubject.getScheduledEpochs().add(buildScheduledEpoch());
        deserializedStudySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
        try  {
                EasyMock.expect(studySubjectFactory.buildReferencedStudySubject(deserializedStudySubject)).andReturn(deserializedStudySubject);
        } catch(C3PRCodedException cce){
                log.error("studySubjectFactory.buildStudySubject() threw exception");
        }
        StudySubject exampleSS = new StudySubject(true);
        exampleSS.setParticipant(deserializedStudySubject.getParticipant());
        exampleSS.setStudySite(deserializedStudySubject.getStudySite());
        List<StudySubject> regs=new ArrayList<StudySubject>();
        regs.add(deserializedStudySubject);
        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite(exampleSS)).andReturn(regs);
        replayMocks();
        try{
            studySubjectRepository.updateLocalRegistration(deserializedStudySubject);            
        } catch(RuntimeException e){
            e.printStackTrace();
            assertTrue(true);
            verifyMocks();
            return;
        }
        assertTrue("Exception expected", false);
    }
    
    public void testUpdateLocalRegistrationUnapprovedSchEpoch() {
        StudySubject deserializedStudySubject = new StudySubject();
        deserializedStudySubject.addScheduledEpoch(new ScheduledEpoch());
        deserializedStudySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
        deserializedStudySubject.setParticipant(buildParticipant());
        deserializedStudySubject.getParticipant().setId(0);
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedWithArmStudySite(true));
        studySubject.setParticipant(buildParticipant());
        studySubject.getParticipant().setId(0);
        studySubject.getScheduledEpochs().add(buildScheduledEpoch());
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
        try  {
                EasyMock.expect(studySubjectFactory.buildReferencedStudySubject(deserializedStudySubject)).andReturn(deserializedStudySubject);
        } catch(C3PRCodedException cce){
                log.error("studySubjectFactory.buildStudySubject() threw exception");
        }
        StudySubject exampleSS = new StudySubject(true);
        exampleSS.setParticipant(deserializedStudySubject.getParticipant());
        exampleSS.setStudySite(deserializedStudySubject.getStudySite());
        List<StudySubject> regs=new ArrayList<StudySubject>();
        regs.add(studySubject);
        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite(exampleSS)).andReturn(regs);
        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
        //studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.updateLocalRegistration(deserializedStudySubject);            
        assertEquals("Wrong SchduledEpoch Workflow status", ScheduledEpochWorkFlowStatus.DISAPPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration Workflow status", RegistrationWorkFlowStatus.DISAPPROVED, studySubject.getRegWorkflowStatus());
        assertEquals("Wrong error message","Registration was not approved by co-ordinating center. No error message was provided.", studySubject.getScheduledEpoch().getDisapprovalReasonText());
        verifyMocks();
    }
    
    public void testUpdateLocalRegistrationUnapprovedSchEpochErrorProvided() {
        StudySubject deserializedStudySubject = new StudySubject();
        deserializedStudySubject.addScheduledEpoch(new ScheduledEpoch());
        deserializedStudySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
        deserializedStudySubject.setParticipant(buildParticipant());
        deserializedStudySubject.getParticipant().setId(0);
        deserializedStudySubject.getScheduledEpoch().setDisapprovalReasonText("Error provided by Co-ordinating center.");
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedWithArmStudySite(true));
        studySubject.setParticipant(buildParticipant());
        studySubject.getParticipant().setId(0);
        studySubject.getScheduledEpochs().add(buildScheduledEpoch());
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
        try  {
                EasyMock.expect(studySubjectFactory.buildReferencedStudySubject(deserializedStudySubject)).andReturn(deserializedStudySubject);
        } catch(C3PRCodedException cce){
                log.error("studySubjectFactory.buildStudySubject() threw exception");
        }
        StudySubject exampleSS = new StudySubject(true);
        exampleSS.setParticipant(deserializedStudySubject.getParticipant());
        exampleSS.setStudySite(deserializedStudySubject.getStudySite());
        List<StudySubject> regs=new ArrayList<StudySubject>();
        regs.add(studySubject);
        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite(exampleSS)).andReturn(regs);
        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
        //studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.updateLocalRegistration(deserializedStudySubject);            
        assertEquals("Wrong SchduledEpoch Workflow status", ScheduledEpochWorkFlowStatus.DISAPPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration Workflow status", RegistrationWorkFlowStatus.DISAPPROVED, studySubject.getRegWorkflowStatus());
        System.out.println(studySubject.getScheduledEpoch().getDisapprovalReasonText());
        verifyMocks();
    }
    
    public void testUpdateLocalRegistrationUnapprovedSchEpochErrorNotProvided() {
        StudySubject deserializedStudySubject = new StudySubject();
        deserializedStudySubject.addScheduledEpoch(new ScheduledEpoch());
        deserializedStudySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
        deserializedStudySubject.setParticipant(buildParticipant());
        deserializedStudySubject.getParticipant().setId(0);
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedWithArmStudySite(true));
        studySubject.setParticipant(buildParticipant());
        studySubject.getParticipant().setId(0);
        studySubject.getScheduledEpochs().add(buildScheduledEpoch());
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
        try  {
                EasyMock.expect(studySubjectFactory.buildReferencedStudySubject(deserializedStudySubject)).andReturn(deserializedStudySubject);
        } catch(C3PRCodedException cce){
                log.error("studySubjectFactory.buildStudySubject() threw exception");
        }
        StudySubject exampleSS = new StudySubject(true);
        exampleSS.setParticipant(deserializedStudySubject.getParticipant());
        exampleSS.setStudySite(deserializedStudySubject.getStudySite());
        List<StudySubject> regs=new ArrayList<StudySubject>();
        regs.add(studySubject);
        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite(exampleSS)).andReturn(regs);
        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
        //studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.updateLocalRegistration(deserializedStudySubject);            
        assertEquals("Wrong SchduledEpoch Workflow status", ScheduledEpochWorkFlowStatus.DISAPPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration Workflow status", RegistrationWorkFlowStatus.DISAPPROVED, studySubject.getRegWorkflowStatus());
        System.out.println(studySubject.getScheduledEpoch().getDisapprovalReasonText());
        verifyMocks();
    }
    
    public void testUpdateLocalRegistrationApprovedRandomizedArmNotProvided() throws Exception{
        StudySubject deserializedStudySubject = new StudySubject();
        deserializedStudySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteRandomizedStudySite(RandomizationType.BOOK, false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(deserializedStudySubject);
        deserializedStudySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
        deserializedStudySubject.setParticipant(buildParticipant());
        deserializedStudySubject.getParticipant().setId(0);
        studySubject.setParticipant(buildParticipant());
        studySubject.getParticipant().setId(0);
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteRandomizedStudySite(RandomizationType.BOOK, false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
        try  {
                EasyMock.expect(studySubjectFactory.buildReferencedStudySubject(deserializedStudySubject)).andReturn(deserializedStudySubject);
        } catch(C3PRCodedException cce){
                log.error("studySubjectFactory.buildStudySubject() threw exception");
        }
        StudySubject exampleSS = new StudySubject(true);
        exampleSS.setParticipant(deserializedStudySubject.getParticipant());
        exampleSS.setStudySite(deserializedStudySubject.getStudySite());
        List<StudySubject> regs=new ArrayList<StudySubject>();
        regs.add(studySubject);
        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite(exampleSS)).andReturn(regs);
        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
        //studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.updateLocalRegistration(deserializedStudySubject);            
        assertEquals("Wrong SchduledEpoch Workflow status", ScheduledEpochWorkFlowStatus.DISAPPROVED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        assertEquals("Wrong Registration Workflow status", RegistrationWorkFlowStatus.DISAPPROVED, studySubject.getRegWorkflowStatus());
        System.out.println(studySubject.getScheduledEpoch().getDisapprovalReasonText());
        verifyMocks();
    }
    
    public void testUpdateLocalRegistrationApprovedRandomizedArmAssigned() throws Exception{
        StudySubject deserializedStudySubject = new StudySubject();
        deserializedStudySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteRandomizedStudySite(RandomizationType.PHONE_CALL, false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(deserializedStudySubject);
        deserializedStudySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
        deserializedStudySubject.setParticipant(buildParticipant());
        deserializedStudySubject.getParticipant().setId(0);
        studySubjectCreatorHelper.bindRandomization(deserializedStudySubject);
        studySubject.setParticipant(buildParticipant());
        studySubject.getParticipant().setId(0);
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteRandomizedStudySite(RandomizationType.BOOK, false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
        try  {
                EasyMock.expect(studySubjectFactory.buildReferencedStudySubject(deserializedStudySubject)).andReturn(deserializedStudySubject);
        } catch(C3PRCodedException cce){
                log.error("studySubjectFactory.buildStudySubject() threw exception");
        }
        StudySubject exampleSS = new StudySubject(true);
        exampleSS.setParticipant(deserializedStudySubject.getParticipant());
        exampleSS.setStudySite(deserializedStudySubject.getStudySite());
        List<StudySubject> regs=new ArrayList<StudySubject>();
        regs.add(studySubject);
        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite(exampleSS)).andReturn(regs);
        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
        //studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.updateLocalRegistration(deserializedStudySubject);            
        assertEquals("Wrong SchduledEpoch Workflow status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }
    
    public void testUpdateLocalRegistrationApprovedNonRandomized() throws Exception{
        StudySubject deserializedStudySubject = new StudySubject();
        deserializedStudySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedWithArmStudySite(false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(deserializedStudySubject);
        deserializedStudySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
        deserializedStudySubject.setParticipant(buildParticipant());
        deserializedStudySubject.getParticipant().setId(0);
        studySubject.setParticipant(buildParticipant());
        studySubject.getParticipant().setId(0);
        studySubject.setStudySite(studySubjectCreatorHelper.getMultiSiteNonRandomizedWithArmStudySite(false));
        studySubjectCreatorHelper.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        studySubjectCreatorHelper.bindArm(studySubject);
        studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
        try  {
                EasyMock.expect(studySubjectFactory.buildReferencedStudySubject(deserializedStudySubject)).andReturn(deserializedStudySubject);
        } catch(C3PRCodedException cce){
                log.error("studySubjectFactory.buildStudySubject() threw exception");
        }
        StudySubject exampleSS = new StudySubject(true);
        exampleSS.setParticipant(deserializedStudySubject.getParticipant());
        exampleSS.setStudySite(deserializedStudySubject.getStudySite());
        List<StudySubject> regs=new ArrayList<StudySubject>();
        regs.add(studySubject);
        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite(exampleSS)).andReturn(regs);
        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
       // studySubjectDao.save(studySubject);
        replayMocks();
        studySubjectRepository.updateLocalRegistration(deserializedStudySubject);            
        assertEquals("Wrong SchduledEpoch Workflow status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
        verifyMocks();
    }*/
    
    public ScheduledEpoch buildScheduledEpoch(){
    	ScheduledEpoch scheduledTreatmentEpoch = new ScheduledEpoch();
    	ScheduledArm scheduledArm = new ScheduledArm();
    	Epoch treatmentEpoch = new Epoch();
    	treatmentEpoch.setName("Treatmen Epoch 1");
    	Arm arm = new Arm();
    	arm.setId(001);
    	arm.setEpoch(treatmentEpoch);
    	scheduledArm.setArm(arm);
    	
    	scheduledTreatmentEpoch.getScheduledArms().add(scheduledArm);
    	scheduledTreatmentEpoch.setEpoch(treatmentEpoch);
    	scheduledTreatmentEpoch.setEligibilityIndicator(true);
    	
    	return scheduledTreatmentEpoch;
    }

    public Participant buildParticipant(){
    	Participant participant = new Participant();
    	participant.setFirstName("Johnny");
    	participant.setLastName("Cash");
    	participant.setBirthDate(new Date());
    	participant.setAdministrativeGenderCode("Male");
    	return participant;    	
    }

	public StudySubjectCreatorHelper getStudySubjectCreatorHelper() {
		return studySubjectCreatorHelper;
	}

	public void setStudySubjectCreatorHelper(
			StudySubjectCreatorHelper studySubjectCreatorHelper) {
		this.studySubjectCreatorHelper = studySubjectCreatorHelper;
	}
	
	  public void testCreate() throws Exception{
	        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
	        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedTreatmentWithArmStudySite(true));
	        studySubject.addScheduledEpoch(scheduledEpochFirst);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.setInformedConsentSignedDate(new Date());
	        studySubject.setInformedConsentVersion("1.0");
	        studySubject.setId(1);
	        OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
	        orgIdentifier.setHealthcareSite(studySubject.getStudySite().getHealthcareSite());
	        orgIdentifier.setValue("MRN Value");
	        orgIdentifier.setType("MRN");
	        studySubject.getParticipant().addIdentifier(orgIdentifier);
	        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());
	        replayMocks();
	        studySubjectRepository.create(studySubject);
	        assertSame("The study subject should have only 1 system assigned Identifier",1, studySubject.getSystemAssignedIdentifiers().size());
	        verifyMocks();
	    }
	  public void testCreateWithC3PRSystemIdentifier() throws Exception{
	        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
	        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedTreatmentWithArmStudySite(true));
	        studySubject.addScheduledEpoch(scheduledEpochFirst);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.setInformedConsentSignedDate(new Date());
	        studySubject.setInformedConsentVersion("1.0");
	        studySubject.setId(1);
	        SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
			sysIdentifier.setSystemName("C3PR");
			sysIdentifier.setType("Study Subject Identifier1");
			sysIdentifier.setValue(studySubject.getStudySite().getStudy().getCoordinatingCenterAssignedIdentifier().getValue() + "_" +studySubject.getParticipant().getPrimaryIdentifier());
			studySubject.addIdentifier(sysIdentifier);
	        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());
	        replayMocks();
	        studySubjectRepository.create(studySubject);
	        assertSame("The study subject should have only 1 system assigned Identifier",1, studySubject.getSystemAssignedIdentifiers().size());
	        verifyMocks();
	    }
	  
	  public void testEnrollOnNonRandomizedEpochWithoutArm() throws Exception{
	        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
	        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpochWithoutArm(false));
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedTreatmentWithArmStudySite(true));
	        studySubject.addScheduledEpoch(scheduledEpochFirst);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.setInformedConsentSignedDate(new Date());
	        studySubject.setInformedConsentVersion("1.0");
	        studySubject.setId(1);
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());
	        
	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);
	        
	        replayMocks();
	        studySubjectRepository.enroll(studySubject);
	        verifyMocks();
	    }
	  public void testEnrollOnNonRandomizedEpochWithArm() throws Exception{
	        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
	        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(false));
	        ScheduledArm scheduledArm = new ScheduledArm();
	        scheduledArm.setArm(studySubjectCreatorHelper.createTestTreatmentEpoch(false).getArms().get(0));
	        scheduledEpochFirst.addScheduledArm(scheduledArm);
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedTreatmentWithArmStudySite(true));
	        studySubject.addScheduledEpoch(scheduledEpochFirst);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.setInformedConsentSignedDate(new Date());
	        studySubject.setInformedConsentVersion("1.0");
	        studySubject.setId(1);
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());

	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);
	        
	        replayMocks();
	        studySubjectRepository.enroll(studySubject);
	        verifyMocks();
	    }
	  
	  public void testEnrollOnPhoneCallRandomizedEpoch() throws Exception{
	        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
	        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
	        ScheduledArm scheduledArm = new ScheduledArm();
	        scheduledArm.setArm(studySubjectCreatorHelper.createTestTreatmentEpoch(false).getArms().get(0));
	        scheduledEpochFirst.addScheduledArm(scheduledArm);
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.PHONE_CALL,true));
	        studySubject.addScheduledEpoch(scheduledEpochFirst);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.setInformedConsentSignedDate(new Date());
	        studySubject.setInformedConsentVersion("1.0");
	        studySubject.setId(1);
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());

	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);
	        
	        replayMocks();
	        studySubjectRepository.enroll(studySubject);
	        verifyMocks();
	    }
	  public void testEnrollOnBookRandomizedEpoch() throws Exception{
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.BOOK,true));
	        studySubjectCreatorHelper.addScheduledEnrollingEpochFromStudyEpochs(studySubject);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.setInformedConsentSignedDate(new Date());
	        studySubject.setInformedConsentVersion("1.0");
	        studySubject.setId(1);
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());

	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);
	        
	        replayMocks();
	        studySubjectRepository.enroll(studySubject);
	        verifyMocks();
	    }
	  public void testTransferEpoch() throws Exception{
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.BOOK,true));
	        studySubjectCreatorHelper.addScheduledEnrollingEpochFromStudyEpochs(studySubject);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.setInformedConsentSignedDate(new Date());
	        studySubject.setInformedConsentVersion("1.0");
	        studySubject.setId(1);
	        EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.REGISTRATION.NOT_FOUND_GIVEN_IDENTIFIERS.CODE",null,null)).andReturn("1");
	        EasyMock.expect(exceptionHelper.getRuntimeException(1)).andReturn(new C3PRCodedRuntimeException(1,"Cannot find a registration with the given identifier(s)"));
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());
	        EasyMock.expect(studySubjectDao.getByIdentifiers((List<Identifier>)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());

	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);
	        
	        replayMocks();
	        studySubject = studySubjectRepository.enroll(studySubject);
	        try{
	        	studySubjectRepository.transferSubject(studySubject.getIdentifiers());
	        	}
	        catch(Exception ex){
	        	 log.error("studySubjectRepositoryImpl.getUniqueStudySubjects() threw exception");
	        	}
	        verifyMocks();
	    }
	  
	  public void testTransferEpoch1() throws Exception{
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.BOOK,true));
	        studySubjectCreatorHelper.addScheduledEnrollingEpochFromStudyEpochs(studySubject);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.setInformedConsentSignedDate(new Date());
	        studySubject.setInformedConsentVersion("1.0");
	        studySubject.setId(1);
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());
	        List<StudySubject> studySubjects = new ArrayList<StudySubject>();
	        studySubjects.add(studySubject);
	        EasyMock.expect(studySubjectDao.getByIdentifiers((List<Identifier>)EasyMock.anyObject())).andReturn(studySubjects);

	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);
	        
	        replayMocks();
	        studySubject = studySubjectRepository.enroll(studySubject);
	        try{
	        	studySubjectRepository.transferSubject(studySubject.getIdentifiers());
	        	}
	        catch(Exception ex){
	        	 log.error("StudySubject.transfer() threw exception");
	        	}
	        verifyMocks();
	    }
	  
	  public void testTransferEpoch2() throws Exception{
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySiteWith2EnrollingEpochs(RandomizationType.BOOK,true));
	        studySubjectCreatorHelper.addScheduledEnrollingEpochFromStudyEpochs(studySubject);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.setInformedConsentSignedDate(new Date());
	        studySubject.setInformedConsentVersion("1.0");
	        studySubject.setId(1);
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());
	        List<StudySubject> studySubjects = new ArrayList<StudySubject>();
	        studySubjects.add(studySubject);
	        EasyMock.expect(studySubjectDao.getByIdentifiers((List<Identifier>)EasyMock.anyObject())).andReturn(studySubjects);

	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);
	        
	        replayMocks();
	        studySubject = studySubjectRepository.enroll(studySubject);
	        studySubjectCreatorHelper.addScheduled2ndNonRandomizedEnrollingEpochFromStudyEpochs(studySubject);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubjectRepository.transferSubject(studySubject.getIdentifiers());
	        assertSame("The subject should have been successfully transferred",ScheduledEpochWorkFlowStatus.REGISTERED,studySubject.getScheduledEpochs().get(1).getScEpochWorkflowStatus());
	        verifyMocks();
	    }

	public StudySubjectService getStudySubjectService() {
		return studySubjectService;
	}

	public void setStudySubjectService(StudySubjectService studySubjectService) {
		this.studySubjectService = studySubjectService;
	}

	public StudyTargetAccrualNotificationEmail getNotificationEmailer() {
		return notificationEmailer;
	}

	public void setNotificationEmailer(
			StudyTargetAccrualNotificationEmail notificationEmailer) {
		this.notificationEmailer = notificationEmailer;
	}
}
