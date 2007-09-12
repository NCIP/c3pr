package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.ADD_DISEASE_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.ASSIGN_ARM;
import static edu.duke.cabig.c3pr.C3PRUseCase.ASSIGN_EXISTING_PARTICIPANT;
import static edu.duke.cabig.c3pr.C3PRUseCase.ASSIGN_REGISTERED_PARTICIPANT;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_INCOMPLETE_REGISTERATION;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_LOCAL_REGISTERATION;
import static edu.duke.cabig.c3pr.C3PRUseCase.UPDATE_REGISTERATION_STATUS;
import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * JUnit Tests for ParticipantDao
 * @author Priyatam
 * @testType unit
 */
@C3PRUseCases({ADD_DISEASE_SUBJECT,ASSIGN_EXISTING_PARTICIPANT,ASSIGN_REGISTERED_PARTICIPANT,CREATE_INCOMPLETE_REGISTERATION,CREATE_LOCAL_REGISTERATION,ASSIGN_ARM,UPDATE_REGISTERATION_STATUS})
public class StudySubjectDaoTest extends DaoTestCase {
    private ParticipantDao dao;
    private StudySiteDao studySiteDao;
    private EpochDao epochDao;
    private StudyDao studyDao;
    private AnatomicSiteDao anatomicSiteDao;
    private StudySubjectDao studySubjectDao;
    private ScheduledEpochDao scheduledEpochDao;
    
    @Override
    protected void setUp() throws Exception {
    	// TODO Auto-generated method stub
    	super.setUp();
    	dao = (ParticipantDao) getApplicationContext().getBean("participantDao");
        studySiteDao = (StudySiteDao) getApplicationContext().getBean("studySiteDao");
        epochDao = (EpochDao) getApplicationContext().getBean("epochDao");
        anatomicSiteDao = (AnatomicSiteDao) getApplicationContext().getBean("anatomicSiteDao");
        studySubjectDao = (StudySubjectDao) getApplicationContext().getBean("studySubjectDao");
        scheduledEpochDao= (ScheduledEpochDao) getApplicationContext().getBean("scheduledEpochDao");
        studyDao=(StudyDao) getApplicationContext().getBean("studyDao");
    }
    
	/*
	 * Test for the advanced search for studies(reporting use case).
	 */
	public void testAdvancedStudySearch(){
		List<StudySubject> ssList;
		{
			Study study = new Study();
			study.setShortTitleText("");
			
			SystemAssignedIdentifier id = new SystemAssignedIdentifier();
			id.setValue(null);
		    study.addIdentifier(id);
			
			Participant participant = new Participant();
			id = new SystemAssignedIdentifier();
	        id.setValue(null);
	        participant.addIdentifier(id);
	
			participant.setFirstName("Alfred");
			participant.setLastName("");    
	        
	        StudySite studySite = new StudySite();
	        studySite.setStudy(study);
	        studySite.setHealthcareSite(new HealthcareSite());
	        
	        StudySubject studySubject = new StudySubject();
	        studySubject.setStudySite(studySite);
	        studySubject.setParticipant(participant);	        
			
			ssList = studySubjectDao.advancedStudySearch(studySubject);
		}
		interruptSession();
		{
			//TODO fix test
			/*assertEquals(ssList.isEmpty(), false);
			if(!ssList.isEmpty()){
				StudySubject ss = ssList.get(0);
				assertEquals(ss.getParticipant().getFirstName(), "Alfred");
				assertEquals(ss.getParticipant().getLastName(), "");
				assertEquals(ss.getStudySite().getStudy().getShortTitleText() ,"");
			}*/
		}
	}
	
    public void testCurrentScheduledEpoch() throws Exception{
    	StudySubject studySubject=new StudySubject();
    	ScheduledEpoch scheduledEpoch1=new ScheduledTreatmentEpoch();
    	scheduledEpoch1.setStartDate((new GregorianCalendar(1990,1,2)).getTime());
    	ScheduledEpoch scheduledEpoch2=new ScheduledNonTreatmentEpoch();
    	studySubject.addScheduledEpoch(scheduledEpoch2);
    	studySubject.addScheduledEpoch(scheduledEpoch1);
    	assertEquals("current epoch is wrong",studySubject.getCurrentScheduledEpoch(),scheduledEpoch2); 
    }
    /**
     * Test Saving of a basic Study Subject
     * @throws Exception
     */
    public void testSaveBasicRegistrationWeb() throws Exception {
        Integer savedId;
        {
        	//formbackingobject
            Object command =formBackingObject();
            
            //binding process
            Object afterBind=bind(command);
            
            //select study & subject
            Object onBindFormObject=bindSelectSubjectStudy(afterBind);
            interruptSession();
            
            Object saved= reviewAndSave(currentFormObject(onBindFormObject));
            
            StudySubject studySubject=(StudySubject)saved;
            
            savedId= studySubject.getId().intValue();
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
        	assertEquals("Wrong eligibility indicator", false, scheduledTreatmentEpoch.getEligibilityIndicator().booleanValue());
        	assertEquals("Wrong number of subject eligibility answers", 3, scheduledTreatmentEpoch.getSubjectEligibilityAnswers().size());
        	assertEquals("Wrong number of subject inclusion eligibility answers", 2, scheduledTreatmentEpoch.getInclusionEligibilityAnswers().size());
        	assertEquals("Wrong number of subject exclusion eligibility answers", 1, scheduledTreatmentEpoch.getExclusionEligibilityAnswers().size());
        	assertEquals("Wrong number of subject stratification answers", 1, scheduledTreatmentEpoch.getSubjectStratificationAnswers().size());
        	assertEquals("Wrong registration status", "INCOMPLETE", loaded.getRegDataEntryStatus().getName());
        	assertEquals("Wrong epoch status", "INCOMPLETE", loaded.getScheduledEpoch().getScEpochDataEntryStatus().getName());        	
        }
        interruptSession();
    }

    /**
	 * Test for loading a Study Subject by Id 
	 * @throws Exception
	 */
    public void testGetById() throws Exception {
    	StudySubject studySubject = studySubjectDao.getById(1000);
        assertNotNull("Study Subject 1 not found");    
        assertEquals("Wrong last name", "Clooney", studySubject.getParticipant().getLastName());
    }
    
    /**
	  * Test for loading all Study Subjects
	  * @throws Exception
	  */
   public void testGetAll() throws Exception {
       List<StudySubject> actual = studySubjectDao.getAll();
       assertEquals(2, actual.size());
       List<Integer> ids = collectIds(actual);
       assertContains("Wrong Study Subject found", ids, 1000);
       assertContains("Wrong Study Subject found", ids, 1001);
   }

    /**
     * Test for retrieving all Participant Assignments associated with this Participant 
     * @throws Exception
     */
    public void testGetStudySubjects() throws Exception {
    	Participant participant = dao.getById(1000);
    	List<StudySubject> studyPartIds = participant.getStudySubjects();
        assertEquals("Wrong number of Study Participant Identifiers", 2, studyPartIds.size());
        List<Integer> ids = collectIds(studyPartIds);

        assertContains("Missing expected Study Participant Identifier", ids, 1000);
        assertContains("Missing expected Study Participant Identifier", ids, 1001);        
    }  

    public void testCreateRegistrationWithAllAssociations() throws Exception {
        Integer savedId;
        {
    		StudySubject studySubject=new StudySubject();  
        	Participant participant = dao.getById(1001);
            StudySite studySite= studySiteDao.getById(1001);
            studySubject.setParticipant(participant);
            studySubject.setStudySite(studySite);
            ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
            scheduledEpochFirst.setEpoch(epochDao.getById(1000));
            studySubject.addScheduledEpoch(scheduledEpochFirst);
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
			studySubject.setInformedConsentSignedDate(new Date());
			studySubject.setInformedConsentVersion("1.0");
			studySubject.setTreatingPhysician(studySubject.getStudySite().getStudyInvestigatorsInternal().get(0));
			studySubject.getDiseaseHistory().setAnatomicSite(anatomicSiteDao.getById(1000));
			studySubject.getDiseaseHistory().setOtherPrimaryDiseaseCode("Other Primary Disease Code");
			List<SubjectEligibilityAnswer> subList=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getSubjectEligibilityAnswers();
			for(SubjectEligibilityAnswer subjectEligibilityAnswer: subList){
				if (subjectEligibilityAnswer.getEligibilityCriteria() instanceof InclusionEligibilityCriteria) {
					subjectEligibilityAnswer.setAnswerText("yes");
				}else{
					subjectEligibilityAnswer.setAnswerText("no");
				}
			}
			((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).setEligibilityIndicator(evaluateEligibilityIndicator(studySubject));
		
			List<SubjectStratificationAnswer> subList1=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getSubjectStratificationAnswers();
			for(SubjectStratificationAnswer subjectStratificationAnswer: subList1){
				subjectStratificationAnswer.setStratificationCriterionAnswer(subjectStratificationAnswer.getStratificationCriterion().getPermissibleAnswers().get(0));
			}
			ScheduledTreatmentEpoch scheduledEpoch=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch());
			scheduledEpoch.addScheduledArm(new ScheduledArm());
			ScheduledArm scheduledArm=scheduledEpoch.getScheduledArm();
			scheduledArm.setArm(((TreatmentEpoch)scheduledEpoch.getTreatmentEpoch()).getArms().get(0));
			studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
			
//			dao.save(studySubject.getParticipant());
			studySubject=studySubjectDao.merge(studySubject);

            savedId= studySubject.getId().intValue();
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
        	assertEquals("Wrong number of subject eligibility answers", 3, scheduledTreatmentEpoch.getSubjectEligibilityAnswers().size());
        	assertEquals("Wrong number of subject inclusion eligibility answers", 2, scheduledTreatmentEpoch.getInclusionEligibilityAnswers().size());
        	assertEquals("Wrong number of subject exclusion eligibility answers", 1, scheduledTreatmentEpoch.getExclusionEligibilityAnswers().size());
        	assertEquals("Wrong number of subject stratification answers", 1, scheduledTreatmentEpoch.getSubjectStratificationAnswers().size());
        }
    }

	private Object formBackingObject() {
		// TODO Auto-generated method stub
		StudySubject studySubject=new StudySubject();
		return studySubject;
	}
	
	private Object bind(Object command){
		StudySubject studySubject=(StudySubject)command;
        studySubject.setParticipant(dao.getById(1010));
        studySubject.setStudySite(studySiteDao.getById(1010));
        return studySubject;
	}
	
	protected StudySubject bindSelectSubjectStudy(Object command) {
		StudySubject studySubject=(StudySubject)command;
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(epochDao.getById(1000));
        int a=((TreatmentEpoch)scheduledEpochFirst.getEpoch()).getArms().size();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
		return buildCommandObject(studySubject);
	}
	protected StudySubject bindNewScheduledEpoch(Object command) {
		StudySubject studySubject=(StudySubject)command;
        ScheduledEpoch scheduledEpochFirst=new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(epochDao.getById(1002));
        int a=((TreatmentEpoch)scheduledEpochFirst.getEpoch()).getArms().size();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
		return buildCommandObject(studySubject);
	}

	private StudySubject buildCommandObject(StudySubject studySubject){
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
		return studySubject;
	}
	
	private Object bindEnrollmentDetails(Object command){
		StudySubject studySubject=(StudySubject)command;
		studySubject.setInformedConsentSignedDate(new Date());
		studySubject.setInformedConsentVersion("1.0");
		studySubject.setTreatingPhysician(studySubject.getStudySite().getStudyInvestigatorsInternal().get(0));
		return studySubject;
	}
	
	private Object bindEnrollmentDetailsOtherTreatingPhysician(Object command){
		StudySubject studySubject=(StudySubject)command;
		studySubject.setInformedConsentSignedDate(new Date());
		studySubject.setInformedConsentVersion("1.0");
		studySubject.setOtherTreatingPhysician("other treating physician");
		return studySubject;
	}

	private Object bindDiseaseDetails(Object command){
		StudySubject studySubject=(StudySubject)command;
		studySubject.getDiseaseHistory().setAnatomicSite(anatomicSiteDao.getById(1000));
		studySubject.getDiseaseHistory().setOtherPrimaryDiseaseCode("Other Primary Disease Code");
		return studySubject;
	}
	
	private Object bindEligibility(Object command){
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
		return studySubject;
	}
	
	private Object bindStratification(Object command){
		StudySubject studySubject=(StudySubject)command;
		List<SubjectStratificationAnswer> subList1=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getSubjectStratificationAnswers();
		for(SubjectStratificationAnswer subjectStratificationAnswer: subList1){
			subjectStratificationAnswer.setStratificationCriterionAnswer(subjectStratificationAnswer.getStratificationCriterion().getPermissibleAnswers().get(0));
		}
		return studySubject;
	}
	
	private Object bindRandomization(Object command){
		StudySubject studySubject=(StudySubject)command;
		ScheduledTreatmentEpoch scheduledEpoch=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch());
		scheduledEpoch.addScheduledArm(new ScheduledArm());
		ScheduledArm scheduledArm=scheduledEpoch.getScheduledArm();
		scheduledArm.setArm(((TreatmentEpoch)scheduledEpoch.getTreatmentEpoch()).getArms().get(0));
		return studySubject;
	}
	
	private Object reviewAndSave(Object command){
		StudySubject studySubject=(StudySubject)command;
		return createRegistration(studySubject);
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
	
	private Object createRegistration(StudySubject studySubject) {
		studySubject.setRegDataEntryStatus(evaluateRegistrationDataEntryStatus(studySubject));
		studySubject.getScheduledEpoch().setScEpochDataEntryStatus(evaluateScheduledEpochDataEntryStatus(studySubject));
		ScheduledEpoch current=studySubject.getScheduledEpoch();
		if (current instanceof ScheduledTreatmentEpoch) {
			ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) current;
			if(scheduledTreatmentEpoch.getScheduledArm()!=null&&scheduledTreatmentEpoch.getScheduledArm().getArm()==null){
				scheduledTreatmentEpoch.removeScheduledArm();
			}
		}
		//evaluate status
		if(studySubject.getScheduledEpoch().getScEpochWorkflowStatus()==ScheduledEpochWorkFlowStatus.UNAPPROVED){
			manageSchEpochWorkFlowStatusIfUnApp(studySubject);
		}
		if(studySubject.getRegWorkflowStatus()==RegistrationWorkFlowStatus.UNREGISTERED){
			manageRegWorkFlowIfUnReg(studySubject);
		}
		studySubject=studySubjectDao.merge(studySubject);
		return studySubject;
	}
	
	private static boolean evaluateStratificationIndicator(ScheduledTreatmentEpoch scheduledTreatmentEpoch){
		List<SubjectStratificationAnswer> answers=scheduledTreatmentEpoch.getSubjectStratificationAnswers();
		for(SubjectStratificationAnswer subjectStratificationAnswer:answers){
			if(subjectStratificationAnswer.getStratificationCriterionAnswer()==null){
				return false;
			}
		}
		return true;
	}
	private StudySubject currentFormObject(Object sessionFormObject) throws Exception {
		// TODO Auto-generated method stub
		StudySubject command=(StudySubject) sessionFormObject;
		if (sessionFormObject != null) {
			if(command.getId()!=null){
				return studySubjectDao.merge(command);
			}
			if(command.getParticipant()!=null)
				dao.reassociate(command.getParticipant());
			if(command.getStudySite()!=null){
				studySiteDao.reassociate(command.getStudySite());
				studyDao.reassociate(command.getStudySite().getStudy());
			}
		}
		return command;
	}

	public RegistrationDataEntryStatus evaluateRegistrationDataEntryStatus(StudySubject studySubject){
		if(studySubject.getInformedConsentSignedDateStr().equals(""))
			return RegistrationDataEntryStatus.INCOMPLETE;
		if(StringUtils.getBlankIfNull(studySubject.getInformedConsentVersion()).equals(""))
			return RegistrationDataEntryStatus.INCOMPLETE;
		return RegistrationDataEntryStatus.COMPLETE;
	}
	
	public ScheduledEpochDataEntryStatus evaluateScheduledEpochDataEntryStatus(StudySubject studySubject){
		if(!studySubject.getIfTreatmentScheduledEpoch())
			return ScheduledEpochDataEntryStatus.COMPLETE;
		ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) studySubject.getScheduledEpoch();
		if(!evaluateStratificationIndicator(scheduledTreatmentEpoch)){
			return ScheduledEpochDataEntryStatus.INCOMPLETE;
		}
		if(!scheduledTreatmentEpoch.getEligibilityIndicator()){
			return ScheduledEpochDataEntryStatus.INCOMPLETE;
		}
		return ScheduledEpochDataEntryStatus.COMPLETE;
	}
	
	private void manageSchEpochWorkFlowStatusIfUnApp(StudySubject studySubject){
		ScheduledEpoch scheduledEpoch=studySubject.getScheduledEpoch();
		if(scheduledEpoch.getScEpochDataEntryStatus()==ScheduledEpochDataEntryStatus.COMPLETE &&
				studySubject.getRegDataEntryStatus()==RegistrationDataEntryStatus.COMPLETE){
			if(studySubject.getStudySite().getStudy().getMultiInstitutionIndicator().equalsIgnoreCase("true")){
				//broadcase message to co-ordinating center
				try {
					//sendRegistrationRequest(studySubject);
					scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
				} catch (Exception e) {
					e.printStackTrace();
					scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.DISAPPROVED);
				}
			}else{
				if(studySubject.getScheduledEpoch().getRequiresArm() && ((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getScheduledArm()==null){
					scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
				}else{
					//logic for accrual ceiling check
					scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
				}
			}
		}else{
			scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
		}
	}
	
	private void manageRegWorkFlowIfUnReg(StudySubject studySubject){
		ScheduledEpoch scheduledEpoch=studySubject.getScheduledEpoch();		
		if(scheduledEpoch.getScEpochWorkflowStatus()==ScheduledEpochWorkFlowStatus.DISAPPROVED){
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.DISAPPROVED);
		}else if(scheduledEpoch.getScEpochWorkflowStatus()==ScheduledEpochWorkFlowStatus.PENDING){
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING);
		}else if(scheduledEpoch.getScEpochWorkflowStatus()==ScheduledEpochWorkFlowStatus.APPROVED){
/*				//logic for accrual ceiling at study level
			if(isAccrualCeilingReached()){
				studySubject.setRegistrationWorkFlowStatus(RegistrationWorkFlowStatus.DISAPPROVED);
			}else{
				// continue Here
			}
*/				if(scheduledEpoch.isReserving()){
				studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.RESERVED);
			}else{
				studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED);
			}
		}
	}

	/**
     * Test Saving of a basic Study Subject
     * @throws Exception
     */
    public void testCreateRegistrationOtherTreatingPhysician() throws Exception {
        Integer savedId;
        {
        	//formbackingobject
            Object command =formBackingObject();
            
            //binding process
            Object afterBind=bind(command);
            
            //select study & subject
            Object onBindFormObject=bindSelectSubjectStudy(afterBind);
            interruptSession();
            currentFormObject(onBindFormObject);
            
            Object onEnrollmentBindObject=bindEnrollmentDetailsOtherTreatingPhysician(afterBind);
            interruptSession();
            currentFormObject(onEnrollmentBindObject);
            
            Object saved= reviewAndSave(onEnrollmentBindObject);
            
            StudySubject studySubject=(StudySubject)saved;
            
            savedId= studySubject.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
        	assertNotNull("Could not reload registration with id " + savedId, loaded);
        	assertEquals("Treating Physician not null", null, loaded.getTreatingPhysician());
        	assertEquals("Other Treating Physician wrong", "other treating physician", loaded.getOtherTreatingPhysician());
        	assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
        	assertEquals("Wrong number of scheduled treatment epochs", 1, loaded.getScheduledTreatmentEpochs().size());
        	assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded.getScheduledNonTreatmentEpochs().size());
        	assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded.getIfTreatmentScheduledEpoch());
        	ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)loaded.getScheduledEpoch();
        	assertEquals("Wrong eligibility indicator", false, scheduledTreatmentEpoch.getEligibilityIndicator().booleanValue());
        	assertEquals("Wrong number of subject eligibility answers", 3, scheduledTreatmentEpoch.getSubjectEligibilityAnswers().size());
        	assertEquals("Wrong number of subject inclusion eligibility answers", 2, scheduledTreatmentEpoch.getInclusionEligibilityAnswers().size());
        	assertEquals("Wrong number of subject exclusion eligibility answers", 1, scheduledTreatmentEpoch.getExclusionEligibilityAnswers().size());
        	assertEquals("Wrong number of subject stratification answers", 1, scheduledTreatmentEpoch.getSubjectStratificationAnswers().size());
        	assertEquals("Wrong registration status", "COMPLETE", loaded.getRegDataEntryStatus().getName());
        }
        interruptSession();
    }
	
}