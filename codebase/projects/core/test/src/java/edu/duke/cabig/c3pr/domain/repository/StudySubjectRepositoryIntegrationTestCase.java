package edu.duke.cabig.c3pr.domain.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.OffEpochReason;
import edu.duke.cabig.c3pr.domain.OffStudyReason;
import edu.duke.cabig.c3pr.domain.Reason;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.IdentifierGenerator;
import edu.duke.cabig.c3pr.utils.PersistedStudySubjectCreator;

public class StudySubjectRepositoryIntegrationTestCase extends DaoTestCase {
    private StudySubjectDao studySubjectDao;
    
    private ResearchStaffDao researchStaffDao;
    
    private StudySiteDao studySiteDao;

    private StudySubjectRepository studySubjectRepository;
    
    private StudySubject studySubject;
    
    private PersistedStudySubjectCreator persistedStudySubjectCreator;
    
    private IdentifierGenerator identifierGenerator;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        studySubjectRepository=(StudySubjectRepository)getApplicationContext().getBean("studySubjectRepository");
        studySubjectDao=(StudySubjectDao)getApplicationContext().getBean("studySubjectDao");
        researchStaffDao=(ResearchStaffDao)getApplicationContext().getBean("researchStaffDao");
        studySiteDao=(StudySiteDao)getApplicationContext().getBean("studySiteDao");
        identifierGenerator=(IdentifierGenerator)getApplicationContext().getBean("identifierGenerator");
        persistedStudySubjectCreator=new PersistedStudySubjectCreator(getApplicationContext());
    }
    
    public void testAssignC3DIdentifier(){
        studySubject=studySubjectDao.getById(1100);
        studySubjectRepository.assignC3DIdentifier(studySubject, "test c3d identifier");
        interruptSession();
        studySubject=studySubjectDao.getById(1100);
        assertEquals("Wrong C3D Identifier", "test c3d identifier", studySubject.getC3DIdentifier());
    }
    
    public void testAssignMedidataIdentifier(){
        studySubject=studySubjectDao.getById(1100);
        studySubjectRepository.assignMedidataIdentifier(studySubject, "test medidata identifier");
        interruptSession();
        studySubject=studySubjectDao.getById(1100);
        assertEquals("Wrong Medidata Identifier", "test medidata identifier", studySubject.getMedidataIdentifier());
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
        studySubject.getScheduledEpoch().getEpoch().setAccrualCeiling(1);
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
    
//    public void testDoLocalRegistrationNonRandomizedNonTreatmentStudy() throws Exception{
//        studySubject = persistedStudySubjectCreator.getLocalNonRandomizedStudySubject(true, true, false);
//        persistedStudySubjectCreator.addScheduledNonEnrollingEpochWithEligibilityFromStudyEpochs(studySubject);
//        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
//        studySubjectRepository.doLocalRegistration(studySubject);
//        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
//    }
//    
//    public void testDoLocalRegistrationNonRandomizedTreatmentStudyWithArm() throws Exception{
//        studySubject=persistedStudySubjectCreator.getLocalNonRandomizedTrestmentWithArmStudySubject(false);
//        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
//        studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
//        persistedStudySubjectCreator.buildCommandObject(studySubject);
//        persistedStudySubjectCreator.bindEligibility(studySubject);
//        persistedStudySubjectCreator.bindStratification(studySubject);
//        persistedStudySubjectCreator.bindArm(studySubject);
//        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
//        studySubjectRepository.doLocalRegistration(studySubject);
//        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
//    }
    
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
        
        assertEquals("Wrong registration status",RegistrationWorkFlowStatus.PENDING_ON_STUDY,studySubject.getRegWorkflowStatus());
        assertEquals("Wrong scheduled epoch status",ScheduledEpochWorkFlowStatus.PENDING_RANDOMIZATION_ON_EPOCH,studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
    
//    public void testDoLocalRegistrationRandomizedStudyArmAssignedPhoneCall() throws Exception{
//        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.PHONE_CALL, false);
//        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
//        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
//        persistedStudySubjectCreator.buildCommandObject(studySubject);
//        persistedStudySubjectCreator.bindEligibility(studySubject);
//        persistedStudySubjectCreator.bindStratification(studySubject);
//        persistedStudySubjectCreator.bindRandomization(studySubject);
//        studySubjectDao.save(studySubject);
//        try {
//            studySubjectRepository.doLocalRegistration(studySubject);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            assertFalse("Shouldnt have thrown exception",true);
//            return;
//        }
//        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
//    }
    
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
           fail();
        }
        assertEquals("Wrong registration status",RegistrationWorkFlowStatus.ON_STUDY,studySubject.getRegWorkflowStatus());
        assertEquals("Wrong scheduled epoch status",ScheduledEpochWorkFlowStatus.ON_EPOCH,studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
    }
    
//    public void testDoLocalRegistrationRandomizedStudyBookStratumGroupPresent() throws Exception{
//        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.BOOK, false);
//        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
//        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
//        persistedStudySubjectCreator.buildCommandObject(studySubject);
//        persistedStudySubjectCreator.bindEligibility(studySubject);
//        persistedStudySubjectCreator.bindStratification(studySubject);
//        try {
//            studySubjectRepository.doLocalRegistration(studySubject);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            assertFalse("Shouldnt have thrown exception",true);
//            return;
//        }
//        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
//    }
//    
//    public void testDoLocalRegistrationRandomizedStudyBookStratumGroupNumberPresent() throws Exception{
//        studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.BOOK, false);
//        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
//        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
//        persistedStudySubjectCreator.buildCommandObject(studySubject);
//        persistedStudySubjectCreator.bindEligibility(studySubject);
//        persistedStudySubjectCreator.bindStratificationInvalid(studySubject);
//        try {
//            studySubjectRepository.doLocalRegistration(studySubject);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            assertFalse("Shouldnt have thrown exception",true);
//            return;
//        }
//        assertEquals("Wrong Scheduled Epoch Status", ScheduledEpochWorkFlowStatus.REGISTERED, studySubject.getScheduledEpoch().getScEpochWorkflowStatus());
//    }
    
    public void testAllowEligibilityWaiver() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalNonRandomizedTreatmentWithArmEligibityStudySubject(false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        List<SubjectEligibilityAnswer> subAnswwers = studySubject.getScheduledEpoch().getSubjectEligibilityAnswers();
        for(SubjectEligibilityAnswer subjectEligibilityAnswer : subAnswwers){
        	if(subjectEligibilityAnswer.getEligibilityCriteria().getQuestionText().equals("ABC")){
        		subjectEligibilityAnswer.setAnswerText("no");
        	} else if(subjectEligibilityAnswer.getEligibilityCriteria().getQuestionText().equals("DEF")){
        		subjectEligibilityAnswer.setAnswerText("no");
        	} else if(subjectEligibilityAnswer.getEligibilityCriteria().getQuestionText().equals("XYZ")){
        		subjectEligibilityAnswer.setAnswerText("yes");
        	}
        }
        studySubject.addIdentifier(identifierGenerator.generateSystemAssignedIdentifier(studySubject));
        studySubject = studySubjectDao.merge(studySubject);
        ResearchStaff researchStaff = researchStaffDao.getById(1100);
        StudyPersonnel studyPersonnel = studySubject.getStudySite().getStudyPersonnel().get(0);
        studyPersonnel.setResearchStaff(researchStaff);
        studyPersonnel.setStatusCode("Active");
        studySiteDao.save(studySubject.getStudySite());
        interruptSession();
        
        InclusionEligibilityCriteria inc1 = new InclusionEligibilityCriteria();
  	  	inc1.setQuestionText("ABC");
  	  	ExclusionEligibilityCriteria exc1 = new ExclusionEligibilityCriteria();
  	  	exc1.setQuestionText("XYZ");
  	  	List<EligibilityCriteria> eligibilityCriteriaList = new ArrayList<EligibilityCriteria>();
  	  	eligibilityCriteriaList.add(inc1);
  	  	eligibilityCriteriaList.add(exc1);
  	  	studySubjectRepository.allowEligibilityWaiver(studySubject.getUniqueIdentifier(), eligibilityCriteriaList, researchStaff.getAssignedIdentifier());
  	  	interruptSession();
  	  	
  	  	studySubject = studySubjectDao.getById(studySubject.getId());
  	  	for(SubjectEligibilityAnswer subjectEligibilityAnswer : studySubject.getScheduledEpoch().getSubjectEligibilityAnswers()){
	  	  	if(subjectEligibilityAnswer.getEligibilityCriteria().getQuestionText().equals("ABC") ||
	  	  		subjectEligibilityAnswer.getEligibilityCriteria().getQuestionText().equals("XYZ")){
	    		assertTrue(subjectEligibilityAnswer.getAllowWaiver());
	    		assertEquals(subjectEligibilityAnswer.getWaivedBy().getAssignedIdentifier(), researchStaff.getAssignedIdentifier());
	    	} else {
	    		assertFalse(subjectEligibilityAnswer.getAllowWaiver());
	    	}
  	  	}
    }
    
    public void testAllowEligibilityWaiverException() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalNonRandomizedTreatmentWithArmEligibityStudySubject(false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        List<SubjectEligibilityAnswer> subAnswwers = studySubject.getScheduledEpoch().getSubjectEligibilityAnswers();
        for(SubjectEligibilityAnswer subjectEligibilityAnswer : subAnswwers){
        	if(subjectEligibilityAnswer.getEligibilityCriteria().getQuestionText().equals("ABC")){
        		subjectEligibilityAnswer.setAnswerText("no");
        	} else if(subjectEligibilityAnswer.getEligibilityCriteria().getQuestionText().equals("DEF")){
        		subjectEligibilityAnswer.setAnswerText("no");
        	} else if(subjectEligibilityAnswer.getEligibilityCriteria().getQuestionText().equals("XYZ")){
        		subjectEligibilityAnswer.setAnswerText("yes");
        	}
        }
        studySubject.addIdentifier(identifierGenerator.generateSystemAssignedIdentifier(studySubject));
        studySubject = studySubjectDao.merge(studySubject);
        ResearchStaff researchStaff = researchStaffDao.getById(1100);
        StudyPersonnel studyPersonnel = studySubject.getStudySite().getStudyPersonnel().get(0);
        studyPersonnel.setResearchStaff(researchStaff);
        studyPersonnel.setStatusCode("Active");
        studySiteDao.save(studySubject.getStudySite());
        interruptSession();
        
        InclusionEligibilityCriteria inc1 = new InclusionEligibilityCriteria();
  	  	inc1.setQuestionText("ABC");
  	  	ExclusionEligibilityCriteria exc1 = new ExclusionEligibilityCriteria();
  	  	exc1.setQuestionText("XYZ");
  	  	List<EligibilityCriteria> eligibilityCriteriaList = new ArrayList<EligibilityCriteria>();
  	  	eligibilityCriteriaList.add(inc1);
  	  	eligibilityCriteriaList.add(exc1);
  	  	try {
			studySubjectRepository.allowEligibilityWaiver(studySubject.getUniqueIdentifier(), eligibilityCriteriaList, "test");
			fail("Should have thrown Exception");
		} catch (C3PRBaseRuntimeException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
			fail("Wrong Exception");
		}
    }
    
    public void testWaiveEligibility() throws Exception{
        studySubject=persistedStudySubjectCreator.getLocalNonRandomizedTreatmentWithArmEligibityStudySubject(false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        List<SubjectEligibilityAnswer> subAnswwers = studySubject.getScheduledEpoch().getSubjectEligibilityAnswers();
        for(SubjectEligibilityAnswer subjectEligibilityAnswer : subAnswwers){
        	if(subjectEligibilityAnswer.getEligibilityCriteria().getQuestionText().equals("ABC")){
        		subjectEligibilityAnswer.setAnswerText("no");
        	} else if(subjectEligibilityAnswer.getEligibilityCriteria().getQuestionText().equals("DEF")){
        		subjectEligibilityAnswer.setAnswerText("no");
        	} else if(subjectEligibilityAnswer.getEligibilityCriteria().getQuestionText().equals("XYZ")){
        		subjectEligibilityAnswer.setAnswerText("yes");
        	}
        }
        studySubject.addIdentifier(identifierGenerator.generateSystemAssignedIdentifier(studySubject));
        studySubject = studySubjectDao.merge(studySubject);
        ResearchStaff researchStaff = researchStaffDao.getById(1100);
        StudyPersonnel studyPersonnel = studySubject.getStudySite().getStudyPersonnel().get(0);
        studyPersonnel.setResearchStaff(researchStaff);
        studyPersonnel.setStatusCode("Active");
        studySiteDao.save(studySubject.getStudySite());
        interruptSession();
        
        InclusionEligibilityCriteria inc1 = new InclusionEligibilityCriteria();
  	  	inc1.setQuestionText("ABC");
  	  	ExclusionEligibilityCriteria exc1 = new ExclusionEligibilityCriteria();
  	  	exc1.setQuestionText("XYZ");
  	  	List<EligibilityCriteria> eligibilityCriteriaList = new ArrayList<EligibilityCriteria>();
  	  	eligibilityCriteriaList.add(inc1);
  	  	eligibilityCriteriaList.add(exc1);
  	  	studySubjectRepository.allowEligibilityWaiver(studySubject.getUniqueIdentifier(), eligibilityCriteriaList, researchStaff.getAssignedIdentifier());
  	  	interruptSession();
  	  	
  	  	studySubject = studySubjectDao.getById(studySubject.getId());
  	  	
	  SubjectEligibilityAnswer subjectEligibilityAnswer1 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer1.setEligibilityCriteria(inc1);
	  subjectEligibilityAnswer1.setWaiverId("123");
	  subjectEligibilityAnswer1.setWaiverReason("some reason 1");
	  SubjectEligibilityAnswer subjectEligibilityAnswer2 = new SubjectEligibilityAnswer();
	  subjectEligibilityAnswer2.setEligibilityCriteria(exc1);
	  subjectEligibilityAnswer2.setWaiverId("123");
	  subjectEligibilityAnswer2.setWaiverReason("some reason 2");
	  List<SubjectEligibilityAnswer> subjectEligibilityAnswers = new ArrayList<SubjectEligibilityAnswer>();
	  subjectEligibilityAnswers.add(subjectEligibilityAnswer1);
	  subjectEligibilityAnswers.add(subjectEligibilityAnswer2);
  	  	
	  	studySubject = studySubjectRepository.waiveEligibility(studySubject.getUniqueIdentifier(), subjectEligibilityAnswers);
	  	interruptSession();
	  	
	  	studySubject = studySubjectDao.getById(studySubject.getId());
  	  	for(SubjectEligibilityAnswer subjectEligibilityAnswer : studySubject.getScheduledEpoch().getSubjectEligibilityAnswers()){
	  	  	if(subjectEligibilityAnswer.getEligibilityCriteria().getQuestionText().equals("ABC")){
	  	  		assertEquals("123", subjectEligibilityAnswer.getWaiverId());
	  	  		assertEquals("some reason 1", subjectEligibilityAnswer.getWaiverReason());
	    	} else if(subjectEligibilityAnswer.getEligibilityCriteria().getQuestionText().equals("XYZ")){
	  	  		assertEquals("123", subjectEligibilityAnswer.getWaiverId());
	  	  		assertEquals("some reason 2", subjectEligibilityAnswer.getWaiverReason());
	    	} else {
	    		assertFalse(subjectEligibilityAnswer.getAllowWaiver());
	    	}
  	  	}
    }
    
    public void testTakeSubjectOffStudy() throws Exception{
    	studySubject=persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.BOOK, false);
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratificationInvalid(studySubject);
        studySubject.addIdentifier(identifierGenerator.generateSystemAssignedIdentifier(studySubject));
        studySubject = studySubjectRepository.enroll(studySubject);
        interruptSession();
        Date offStudyDate = new Date();
        List<OffEpochReason> offStudyReasons = new ArrayList<OffEpochReason>();
        OffEpochReason offEpochReason1 = new OffEpochReason();
        Reason reason1 = new OffStudyReason();
        reason1.setCode("DEATH_ON_STUDY");
        offEpochReason1.setReason(reason1);
        offEpochReason1.setDescription("died");
        OffEpochReason offEpochReason2 = new OffEpochReason();
        Reason reason2 = new OffStudyReason();
        reason2.setCode("LATE_ADVERSE_EVENT_SIDE_EFFECT");
        offEpochReason2.setReason(reason2);
        offEpochReason2.setDescription("adverse event");
        offStudyReasons.add(offEpochReason1);
        offStudyReasons.add(offEpochReason2);
        studySubject = studySubjectRepository.takeSubjectOffStudy(studySubject.getUniqueIdentifier(), offStudyReasons, offStudyDate);
        interruptSession();
        studySubject = studySubjectDao.getById(studySubject.getId());
        assertEquals(RegistrationWorkFlowStatus.OFF_STUDY, studySubject.getRegWorkflowStatus());
        assertEquals(DateUtil.toString(offStudyDate, "MM/dd/yyyy"), DateUtil.toString(studySubject.getOffStudyDate(),"MM/dd/yyyy"));
        assertEquals(offStudyReasons, studySubject.getOffStudyReasons());
        for(OffEpochReason offStudyReason : studySubject.getOffStudyReasons()){
        	if(offStudyReason.getReason().getCode().equals("DEATH_ON_STUDY")){
        		assertEquals("died", offStudyReason.getDescription());
        	}
        	if(offStudyReason.getReason().getCode().equals("LATE_ADVERSE_EVENT_SIDE_EFFECT")){
        		assertEquals("adverse event", offStudyReason.getDescription());
        	}
        }
    }
    
    public void testStudySubjectReConsent() throws Exception{
    	 studySubject = persistedStudySubjectCreator.getLocalNonRandomizedStudySubject(false, true, true);
    	 SystemAssignedIdentifier studySubjectIdentifier = new SystemAssignedIdentifier();
		 studySubjectIdentifier.setSystemName("C3PR");
		 studySubjectIdentifier.setType("C3PR");
		 studySubjectIdentifier.setValue("id1");
		 studySubject.addIdentifier(studySubjectIdentifier);
		 studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING_ON_STUDY);
		 studySubjectDao.save(studySubject);
		 Integer id = studySubject.getId();
		 interruptSession();
		 
		StudySubject savedStudySubject = studySubjectDao.getById(id);
		
		Consent consent = new Consent();
		consent.setName("Parent Consent");
		StudySubjectConsentVersion studySubjectConsentVersion1 = new StudySubjectConsentVersion();
		studySubjectConsentVersion1.setConsent(consent);
		
		List<StudySubjectConsentVersion> studySubjectConsentVersionsHolder = new ArrayList<StudySubjectConsentVersion>();
		studySubjectConsentVersionsHolder.add(studySubjectConsentVersion1);
		 
		savedStudySubject = studySubjectRepository.reConsent(savedStudySubject.getStudySiteVersion().getStudyVersion().getName(), studySubjectConsentVersionsHolder, studySubjectIdentifier);
    	assertEquals("Re consent should not be successful as informed consent signed date is not given",0,savedStudySubject.getLatestSignedConsents().size());
    	
    	studySubjectConsentVersion1.setInformedConsentSignedDate(new Date());
    	
    	savedStudySubject = studySubjectRepository.reConsent(savedStudySubject.getStudySiteVersion().getStudyVersion().getName(), studySubjectConsentVersionsHolder, studySubjectIdentifier);
    	assertEquals("Wrong number of consents",1,savedStudySubject.getLatestSignedConsents().size());
    	assertEquals("Wrong consent","Parent Consent",savedStudySubject.getLatestSignedConsents().get(0).getConsent().getName());
    	
    	
    	
    }
}
