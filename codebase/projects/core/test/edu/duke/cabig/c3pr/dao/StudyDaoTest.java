package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.DomainObject;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.util.DaoTestCase;

/**
 * 
 * @author Priyatam
 */
public class StudyDaoTest extends DaoTestCase {
    private StudyDao dao = (StudyDao) getApplicationContext().getBean("studyDao");

    public void testGetById() throws Exception {
        Study study = dao.getById(1000);
        assertNotNull("Study 1 not found", study);
        assertEquals("Wrong name", "precis_text", study.getPrecisText());
    }

    public void testGetAll() throws Exception {
        List<Study> actual = dao.getAll();
        assertEquals(3, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong study found", ids, 1000);
        assertContains("Wrong study found", ids, 1001);
        assertContains("Wrong study found", ids, 1002);
    }

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
    
    public void testGetEpochs() throws Exception {
    	Study study = dao.getById(1000);
    	List<Epoch> epochs = study.getEpochs();
        assertEquals("Wrong number of Epochs", 2, epochs.size());
        List<Integer> ids = collectIds(epochs);

        assertContains("Missing expected Epoch", ids, 1000);
        assertContains("Missing expected Epoch", ids, 1001);        
    }

    public void testGetArms() throws Exception {
//        List<Arm> actual = dao.getArmsForStudy(1000);
//        assertEquals("Wrong number of assigments", 8, actual.size());
//        List<Integer> ids = collectIds(actual);
//
//        assertContains("Missing expected Arm", ids, 1000);
//        assertContains("Missing expected Arm", ids, 1001);
//        assertContains("Missing expected Arm", ids, 1002);
//        assertContains("Missing expected Arm", ids, 1003);
//        assertContains("Missing expected Arm", ids, 1004);
//        assertContains("Missing expected Arm", ids, 1005);
//        assertContains("Missing expected Arm", ids, 1006);
//        assertContains("Missing expected Arm", ids, 1007);
    }
    
    
    public void testSearchStudySimple()
    {    
    	  Study studySearchCriteria = new Study();
    	  studySearchCriteria.setShortTitleText("short_title_text");
          List<Study> results = dao.searchByExample(studySearchCriteria);
          assertEquals("Wrong number of Studies", 2, results.size());
          assertEquals("short_title_text", results.get(0).getShortTitleText());
          assertEquals("short_title_text", results.get(1).getShortTitleText());      
    }
   
    public void testSearchStudyByWildCards()
    {    
//    	  Study studySearchCriteria = new Study();
//    	  studySearchCriteria.setShortTitleText("*_title_*");
//          List<Study> results = dao.searchByExample(studySearchCriteria);
//          assertEquals("Wrong number of Studies", 3, results.size());
//          assertEquals("short_title_text", results.get(0).getShortTitleText());
//          assertEquals("short_title_text2", results.get(1).getShortTitleText());   
//          assertEquals("short_title_text", results.get(1).getShortTitleText());
    }
}