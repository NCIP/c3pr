package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for HealthcareSiteInvestigatorDao
 * @author Priyatam
 * @testType unit
 */
public class HealthcareSiteInvestigatorDaoTest extends 
	ContextDaoTestCase<HealthcareSiteInvestigatorDao>{

	private HealthcareSiteInvestigatorDao dao = 
		(HealthcareSiteInvestigatorDao) getApplicationContext()
		.getBean("healthcareSiteInvestigatorDao");
	
	/**
	 * Test for loading a Site Investigator by Id 	
	 * @throws Exception
	 */
	public void testGetById() throws Exception {
		HealthcareSiteInvestigator loaded = getDao().getById(1000);
        assertNotNull("HealthcareSiteInvestigatorDao not found", loaded);
        assertEquals("ACTIVE", loaded.getStatusCode());
    }
    
   /**
	 * Test for loading all Site Investigators 
	 * @throws Exception
	 */
    public void testGetAll() throws Exception {
        List<HealthcareSiteInvestigator> actual = dao.getAll();
        assertEquals(4, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong study found", ids, 1000);
        assertContains("Wrong study found", ids, 1001);
        assertContains("Wrong study found", ids, 1002);
        assertContains("Wrong study found", ids, 1003);        
    }

    /**
	 * Test for loading of Site Investigators based on mathing pattern on Investigator name 
	 * @throws Exception
	 */
	public void testGetBySubnameMatchesShortTitle() throws Exception {
        List<HealthcareSiteInvestigator> actual = getDao().getBySubnames(new String[] { "Bi" },1000);
        assertEquals("Wrong number of matches", 1, actual.size());
        assertEquals("Wrong match", 1000, (int) actual.get(0).getId());
    }
 
	/**
	 * Test for loading of Site Investigators based on mathing pattern on Investigator name 
	 * @throws Exception
	 */
    public void testGetBySubnameMatchesIntersectionOfSubnames() throws Exception {
        List<HealthcareSiteInvestigator> actual = getDao().getBySubnames(new String[] { "Investig", "Geo" },1000);
        assertEquals("Wrong number of matches", 1, actual.size());
        assertEquals("Wrong match", 1001, (int) actual.get(0).getId());
    }
    
}