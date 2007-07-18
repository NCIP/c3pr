package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for ParticipantDao
 * @author Priyatam
 * @testType unit
 */
public class StudySubjectDaoTest extends DaoTestCase {
    private ParticipantDao dao = (ParticipantDao) getApplicationContext().getBean("participantDao");
    private StudySiteDao studySiteDao = (StudySiteDao) getApplicationContext().getBean("studySiteDao");
    private EpochDao epochDao = (EpochDao) getApplicationContext().getBean("epochDao");
    private AnatomicSiteDao anatomicSiteDao = (AnatomicSiteDao) getApplicationContext().getBean("anatomicSiteDao");
    private StudySubjectDao studySubjectDao = (StudySubjectDao) getApplicationContext().getBean("studySubjectDao");
    /**
	 * Test for loading a Participant by Id 
	 * @throws Exception
	 */
    public void testGetById() throws Exception {
    	Participant participant = dao.getById(1000);
        assertNotNull("Participant 1 not found", participant);    
        assertEquals("Wrong last name", "Clooney", participant.getLastName());
    }
    
    /**
	  * Test for loading all Participants
	  * @throws Exception
	  */
    public void testGetAll() throws Exception {
        List<Participant> actual = dao.getAll();
        assertEquals(4, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong Participant found", ids, 1000);
        assertContains("Wrong Participant found", ids, 1001);
        assertContains("Wrong Participant found", ids, 1002);
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
    
    /**
     * Test for searching Participants without a wildcard 
     * @throws Exception
     */
    public void testSearchParticipantSimple() throws Exception
    {    
    	  Participant searchCriteria = new Participant();
    	  searchCriteria.setLastName("Clooney");
          List<Participant> results = dao.searchByExample(searchCriteria);
          assertEquals("Wrong number of Participants", 1, results.size());
     }
    
    /**
     * Test for searching Participants using wildcards 
     * @throws Exception
     */
    public void testSearchParticipantSimpleByWildCards() throws Exception
    {    
    	Participant searchCriteria = new Participant();
  	  searchCriteria.setLastName("Clo%ey");
        List<Participant> results = dao.searchByExample(searchCriteria, true);
        assertEquals("Wrong number of Participants", 1, results.size());
    }
    
    public void testCreateRegistrationWithAllAssociations() throws Exception {
        Integer savedId;
        {
    		StudySubject studySubject=new StudySubject();  
        	Participant participant = dao.getById(1010);
            StudySite studySite= studySiteDao.getById(1010);
            studySubject.setParticipant(participant);
            studySubject.setStudySite(studySite);
            participant.addStudySubject(studySubject);
            studySite.addStudySubject(studySubject);
            studySubject.addScheduledEpoch(new ScheduledTreatmentEpoch());
            studySubject.getScheduledEpoch().setEpoch(epochDao.getById(1000));
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
					subjectEligibilityAnswer.setAnswerText("true");
				}else{
					subjectEligibilityAnswer.setAnswerText("false");
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
			studySubject.setRegistrationStatus(evaluateStatus(studySubject));
//			dao.save(studySubject.getParticipant());
			studySubjectDao.save(studySubject);

            savedId= studySubject.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);         
        }
    }
    /**
     * Test Saving of a Participant with all associations present
     * @throws Exception
     */
/*    public void testCreateRegistrationWithAllAssociationsWeb() throws Exception {
        Integer savedId;
        {
        	//formbackingobject
            Object command =formBackingObject();
            
            //binding process
            Object afterBind=bind(command);
            
            //onBindNewForm
            Object onBindFormObject=onBindOnNewForm(afterBind);
            
            //enrollment details
            Object afterEnroll= bindEnrollmentDetails(onBindFormObject);
            
            //disease details
            Object afterDisease= bindDiseaseDetails(afterEnroll);
            
            //eligibility details
            Object afterElig= bindEligibility(afterDisease);
            
            //startification details
            Object afterStrat= bindStratification(afterElig);

            //randomization details
            Object afterRan= bindRandomization(afterStrat);

            //reviewsave details
            Object saved= reviewAndSave(afterRan);
            
            StudySubject studySubject=(StudySubject)saved;
            
            savedId= studySubject.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
        	StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);         
        }
    }
    
*/	private Object formBackingObject() {
		// TODO Auto-generated method stub
		StudySubject studySubject=new StudySubject();
		return studySubject;
	}
	
	private Object bind(Object command){
		StudySubject studySubject=(StudySubject)command;
    	Participant participant = dao.getById(1010);
        StudySite studySite= studySiteDao.getById(1010);
        studySubject.setParticipant(participant);
        studySubject.setStudySite(studySite);
        participant.addStudySubject(studySubject);
        studySite.addStudySubject(studySubject);
        studySubject.addScheduledEpoch(new ScheduledTreatmentEpoch());
        studySubject.getScheduledEpoch().setEpoch(epochDao.getById(1000));
        return studySubject;
	}
	
	protected StudySubject onBindOnNewForm(Object command) {
		return buildCommandObject((StudySubject)command);
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
				subjectEligibilityAnswer.setAnswerText("true");
			}else{
				subjectEligibilityAnswer.setAnswerText("false");
			}
		}
		((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).setEligibilityIndicator(evaluateEligibilityIndicator(studySubject));
		return studySubject;
	}
	
	private Object bindStratification(Object command){
		StudySubject studySubject=(StudySubject)command;
		List<SubjectStratificationAnswer> subList=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getSubjectStratificationAnswers();
		for(SubjectStratificationAnswer subjectStratificationAnswer: subList){
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
		studySubject.setRegistrationStatus(evaluateStatus(studySubject));
		ScheduledEpoch current=studySubject.getScheduledEpoch();
		if (current instanceof ScheduledTreatmentEpoch) {
			ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) current;
			if(scheduledTreatmentEpoch.getScheduledArm().getArm()==null){
				scheduledTreatmentEpoch.removeScheduledArm();
			}
		}
//		dao.save(studySubject.getParticipant());
		studySubjectDao.save(studySubject);
		return studySubject;
	}
	
	public static String evaluateStatus(StudySubject studySubject){
		String status="Complete";
		ScheduledEpoch current=studySubject.getScheduledEpoch();
		if(studySubject.getInformedConsentSignedDateStr().equals("")){
			return "Incomplete";
		}else if(studySubject.getTreatingPhysician()==null){
			return "Incomplete";
		}else if(!evaluateStratificationIndicator(studySubject)){
			return "Incomplete";
		}else if (current instanceof ScheduledTreatmentEpoch) {
			ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) current; 
			if(!scheduledTreatmentEpoch.getEligibilityIndicator()){
				return "Incomplete";
			}else if(scheduledTreatmentEpoch.getScheduledArm()==null || scheduledTreatmentEpoch.getScheduledArm()==null){
				return "Incomplete";
			}
		}
		return status;
	}
	private static boolean evaluateStratificationIndicator(StudySubject studySubject){
		ScheduledEpoch current=studySubject.getScheduledEpoch();
		if (current instanceof ScheduledTreatmentEpoch) {
			ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) current; 
			List<SubjectStratificationAnswer> answers=scheduledTreatmentEpoch.getSubjectStratificationAnswers();
			for(SubjectStratificationAnswer subjectStratificationAnswer:answers){
				if(subjectStratificationAnswer.getStratificationCriterionAnswer()==null){
					return false;
				}
			}
		}
		return true;
	}
	
}