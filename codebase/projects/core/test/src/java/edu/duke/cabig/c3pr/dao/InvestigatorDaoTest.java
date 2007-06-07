package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for InvestigatorDao
 * @author Priyatam
 * @testType unit
 */
public class InvestigatorDaoTest extends ContextDaoTestCase<InvestigatorDao> {

	/**
	 * Test for loading an Investigator by Id 
	 * @throws Exception
	 */
	 public void testGetById() throws Exception {
	        Investigator inv = getDao().getById(1000);
	        assertEquals("Investigator Bill", inv.getFirstName());
	    }
	    
	 /**
	  * Test for loading all Investigators
	  * @throws Exception
	  */
	 public void testGetAll() throws Exception {
        List<Investigator> actual = getDao().getAll();
        assertEquals(4, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong investigator found", ids, 1000);
        assertContains("Wrong investigator found", ids, 1001);
        assertContains("Wrong investigator found", ids, 1001);
        assertContains("Wrong investigator found", ids, 1001);        
      }	    
}