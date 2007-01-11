package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for StudyDao
 * @author Priyatam
 * @testType unit
 */
public class StudyDaoTest extends DaoTestCase {
    private StudyDao dao = (StudyDao) getApplicationContext().getBean("studyDao");

    /**
	 * Test for loading a Study by Id 
	 * @throws Exception
	 */
    public void testGetById() throws Exception {
        System.out.println("***********************1231312");
    	Study study = dao.getById(1000);
        assertNotNull("Study 1 not found", study);
        assertEquals("Wrong name", "precis_text", study.getPrecisText());
    }

    /**
	 * Test for loading all Studies 
	 * @throws Exception
	 */
    public void testGetAll() throws Exception {
        List<Study> actual = dao.getAll();
        assertEquals(3, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong study found", ids, 1000);
        assertContains("Wrong study found", ids, 1001);
        assertContains("Wrong study found", ids, 1002);
    }

    /**
     * Test Saving of a Study with all associations present
     * @throws Exception
     */
    public void testSaveNewStudy() throws Exception {
        Integer savedId;
        {
            Study study = new Study();
            study.setPrecisText("New study");            
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setSponsorCode("SponsorCode");
            study.setStatus("Status");
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator("No");
            dao.save(study);
            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);
        }

        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            assertNotNull("Could not reload study with id " + savedId, loaded);
            assertEquals("Wrong name", "New study", loaded.getPrecisText());
        }
    }
    
    /**
     * Test for retrieving all Epochs associated with this Study 
     * @throws Exception
     */
    public void testGetEpochs() throws Exception {
    	Study study = dao.getById(210);
    	List<Epoch> epochs = study.getEpochs();
        assertEquals("Wrong number of Epochs", 2, epochs.size());
        List<Integer> ids = collectIds(epochs);

        assertContains("Missing expected Epoch", ids, 204);
        assertContains("Missing expected Epoch", ids, 205);        
    }

    /**
     * Test for retrieving all Arms associated with this Studies' epochs 
     * @throws Exception
     */
    public void testGetArms() throws Exception {
        List<Arm> actual = dao.getArmsForStudy(1000);
       
        assertEquals("Wrong number of assigments", 4, actual.size());
        List<Integer> ids = collectIds(actual);

        assertContains("Missing expected Arm", ids, 1000);
        assertContains("Missing expected Arm", ids, 1001);
        assertContains("Missing expected Arm", ids, 1002);
        assertContains("Missing expected Arm", ids, 1003);
      
    }
    
    /**
     * Test for searching Studies without wildcards 
     * @throws Exception
     */
    public void testSearchStudySimple()
    {    
    	  Study studySearchCriteria = new Study();
    	  studySearchCriteria.setShortTitleText("short_TITLE_text");
          List<Study> results = dao.searchByExample(studySearchCriteria);
          assertEquals("Wrong number of Studies", 2, results.size());
          assertEquals("short_title_text", results.get(0).getShortTitleText());
          assertEquals("short_title_text", results.get(1).getShortTitleText());      
    }
    
    /**
     * Test for searching Studies using wildcards 
     * @throws Exception
     */   
    public void testSearchStudyByWildCards() throws Exception
    {    
    	  Study studySearchCriteria = new Study();
    	  studySearchCriteria.setShortTitleText("ti%e");
          List<Study> results = dao.searchByExample(studySearchCriteria, true);
          assertEquals("Wrong number of Studies", 3, results.size());          
    }
}