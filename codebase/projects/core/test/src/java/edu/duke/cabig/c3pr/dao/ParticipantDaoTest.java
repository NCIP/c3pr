package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for ParticipantDao
 * @author Priyatam
 * @testType unit
 */
public class ParticipantDaoTest extends DaoTestCase {
    private ParticipantDao dao = (ParticipantDao) getApplicationContext().getBean("participantDao");
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
}