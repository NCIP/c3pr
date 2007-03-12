package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for ParticipantDao
 * @author Priyatam
 * @testType unit
 */
public class ParticipantDaoTest extends DaoTestCase {
    private ParticipantDao dao = (ParticipantDao) getApplicationContext().getBean("participantDao");
    private StudySiteDao studySiteDao = (StudySiteDao) getApplicationContext().getBean("studySiteDao");

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
        assertEquals(3, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong Participant found", ids, 1000);
        assertContains("Wrong Participant found", ids, 1001);
        assertContains("Wrong Participant found", ids, 1002);
    }
    
    /**
     * Test Saving of a Participant with all associations present
     * @throws Exception
     */
    public void testSaveNewParticipantWithAllAssociations() throws Exception {
        Integer savedId;
        {
            Participant participant = dao.getById(1000);
            StudyParticipantAssignment studyParticipantAssignment=new StudyParticipantAssignment();
            
            StudySite studySite= studySiteDao.getById(1000);
            ScheduledArm scheduledArm=new ScheduledArm();
            Arm arm=studySite.getStudy().getEpochs().get(0).getArms().get(0);
            scheduledArm.setArm(arm);
            scheduledArm.setEligibilityIndicator("true");
            scheduledArm.setStartDate(new Date());
            scheduledArm.setStudyParticipantAssignment(studyParticipantAssignment);
            studyParticipantAssignment.addScheduledArm(scheduledArm);
            studyParticipantAssignment.setStudySite(studySite);
            studyParticipantAssignment.setStudyParticipantIdentifier("1000");
            studyParticipantAssignment.setStartDate(new Date());
            studyParticipantAssignment.setEligibilityIndicator(true);
            participant.addStudyParticipantAssignment(studyParticipantAssignment);            
            studyParticipantAssignment.setParticipant(participant);
            dao.save(participant);
            savedId = participant.getId();
            assertNotNull("The participant didn't get an id", savedId);
        }

        interruptSession();
        {
        	Participant loaded = dao.getById(savedId);
            assertNotNull("Could not reload study with id " + savedId, loaded);         
        }
    }
    
    /**
     * Test for retrieving all participant identifiers associated with this Participant
     * @throws Exception
     */
//    public void testGetParticipantIdentifiers() throws Exception {
//    	Participant participant = dao.getById(1000);
//    	List<ParticipantIdentifier> partIds = participant.getParticipantIdentifiers();
//        assertEquals("Wrong number of Participant Indentifiers", 1, partIds.size());
//        List<Integer> ids = collectIds(partIds);
//
//        assertContains("Missing expected ParticipantIdentifier", ids, 1000);
//     }  
    
    /**
     * Test for retrieving all Participant Assignments associated with this Participant 
     * @throws Exception
     */
    public void testGetStudyParticipantAssignments() throws Exception {
    	Participant participant = dao.getById(1000);
    	List<StudyParticipantAssignment> studyPartIds = participant.getStudyParticipantAssignments();
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

}