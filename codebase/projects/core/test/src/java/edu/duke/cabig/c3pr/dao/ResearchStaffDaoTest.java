package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.SEARCH_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.VERIFY_SUBJECT;
import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;
import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

/**
 * JUnit Tests for ResearchStaffDao
 * @author Priyatam
 * @testType unit
 */
@C3PRUseCases({VERIFY_SUBJECT,SEARCH_SUBJECT})
public class ResearchStaffDaoTest extends ContextDaoTestCase<ResearchStaffDao> {

	/**
	 * Test for loading an a Research Staff by Id 
	 * @throws Exception
	 */
	 public void testGetById() throws Exception {
	        ResearchStaff staff = getDao().getById(1000);
	        assertEquals("Research Bill", staff.getFirstName());
	    }
	    
	 /**
	  * Test for loading all ResearchStaffs
	  * @throws Exception
	  */
	 public void testGetAll() throws Exception {
        List<ResearchStaff> actual = getDao().getAll();
        assertEquals(4, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong ResearchStaff found", ids, 1000);
        assertContains("Wrong ResearchStaff found", ids, 1001);
        assertContains("Wrong ResearchStaff found", ids, 1001);
        assertContains("Wrong ResearchStaff found", ids, 1001);        
      }	    
	 

    /**
	 * Test for loading of Research Staff based on mathing pattern on Staff name 
	 * @throws Exception
	 */
	public void testGetBySubnameMatchesShortTitle() throws Exception {
        List<ResearchStaff> actual = getDao().getBySubnames(new String[] { "Bi" }, 1000);
        assertEquals("Wrong number of matches", 1, actual.size());
        assertEquals("Wrong match", 1000, (int) actual.get(0).getId());
    }
 
	/**
	 * Test for loading of Site Investigators based on mathing pattern on Investigator name 
	 * @throws Exception
	 */
    public void testGetBySubnameMatchesIntersectionOfSubnames() throws Exception {
        List<ResearchStaff> actual = getDao().getBySubnames(new String[] { "Resea", "Geo" }, 1000);
        assertEquals("Wrong number of matches", 1, actual.size());
        assertEquals("Wrong match", 1001, (int) actual.get(0).getId());
    }
}