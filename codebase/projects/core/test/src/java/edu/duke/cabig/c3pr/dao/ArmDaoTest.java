/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for ArmDao
 * 
 * @author Priyatam
 * @testType unit
 */
public class ArmDaoTest extends ContextDaoTestCase<ArmDao> {

    private EpochDao epochDao = (EpochDao) getApplicationContext().getBean("epochDao");

    /**
     * Test for loading an Arm by Id
     * 
     * @throws Exception
     */
    public void testGetById() throws Exception {
        Arm arm = getDao().getById(1000);
        assertEquals("Arm 1000", arm.getName());
        assertEquals("Wrong Target Accrual Number", "Arm 1000 desc", arm.getDescriptionText());
    }

    /**
     * Test for loading all Arms
     * 
     * @throws Exception
     */
    public void testGetAll() throws Exception {
        List<Arm> actual = getDao().getAll();
        assertEquals(8, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong arm found", ids, 1000);
        assertContains("Wrong arm found", ids, 1001);
        assertContains("Wrong arm found", ids, 1002);
        assertContains("Wrong arm found", ids, 1003);
        assertContains("Wrong arm found", ids, 1004);
        assertContains("Wrong arm found", ids, 1005);
        assertContains("Wrong arm found", ids, 1006);
        assertContains("Wrong arm found", ids, 1007);
    }

    public void testFailureSavingTwoArmsWithSameNameInOneEpoch() throws Exception {

        Epoch loadedEpoch = epochDao.getById(1000);
        Arm arm = new Arm();
        arm.setName("Arm 1000");
        try {
            loadedEpoch.addArm(arm);
            fail("add should fail");
        }
        catch (RuntimeException e) {

        }

    }
}
