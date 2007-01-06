package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.util.ContextDaoTestCase;

/**
 * @author Priyatam
 */
public class EpochDaoTest extends ContextDaoTestCase<EpochDao> {

    public void testGetById() throws Exception {
        Epoch loaded = getDao().getById(1000);
        assertEquals("Wrong name", "Treatment", loaded.getName());
        assertEquals("Wrong number of arms", 2, loaded.getArms().size());
        assertEquals("Wrong 0th arm", "Arm 1000", loaded.getArms().get(0).getName());
        assertEquals("Wrong 1st arm", "Arm 1001", loaded.getArms().get(1).getName());
    }
    
    public void testGetAll() throws Exception {
        List<Epoch> actual = getDao().getAll();
        assertEquals(4, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong epoch found", ids, 1000);
        assertContains("Wrong epoch found", ids, 1001);
        assertContains("Wrong epoch found", ids, 1002);
        assertContains("Wrong epoch found", ids, 1003);
    }
    
    public void testGetArms() throws Exception {
    	Epoch epoch = getDao().getById(1000);
    	List<Arm> arms = epoch.getArms();
        assertEquals("Wrong number of Arms", 2, arms.size());
        List<Integer> ids = collectIds(arms);

        assertContains("Missing expected Arm", ids, 1000);
        assertContains("Missing expected Arm", ids, 1001);
    }
}
