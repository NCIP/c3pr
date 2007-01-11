package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for EpochDao
 * @author Priyatam
 * @testType unit
 */
public class EpochDaoTest extends ContextDaoTestCase<EpochDao> {

	/**
	 * Test for loading an Epoch by Id 
	 * @throws Exception
	 * @testType unit
	 */
    public void testGetById() throws Exception {
        Epoch loaded = getDao().getById(1000);
        assertEquals("Wrong name", "Treatment", loaded.getName());
        assertEquals("Wrong number of arms", 2, loaded.getArms().size());     
    }
    
    /**
	  * Test for loading all Epochs
	  * @throws Exception
	  */
    public void testGetAll() throws Exception {
        List<Epoch> actual = getDao().getAll();
        assertEquals(4, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong epoch found", ids, 1000);
        assertContains("Wrong epoch found", ids, 1001);
        assertContains("Wrong epoch found", ids, 1002);
        assertContains("Wrong epoch found", ids, 1003);
    }
    
    /**
     * Test for loading all the Arms associated with this Epoch
     * @throws Exception
     */
    public void testGetArms() throws Exception {
    	Epoch epoch = getDao().getById(1000);
    	List<Arm> arms = epoch.getArms();
        assertEquals("Wrong number of Arms", 2, arms.size());
        List<Integer> ids = collectIds(arms);

        assertContains("Missing expected Arm", ids, 1000);
        assertContains("Missing expected Arm", ids, 1001);
    }
}
