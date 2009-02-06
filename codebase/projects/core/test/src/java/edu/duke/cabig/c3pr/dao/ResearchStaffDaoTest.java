package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.SEARCH_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.VERIFY_SUBJECT;
import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.dao.query.ResearchStaffQuery;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for ResearchStaffDao
 * 
 * @author Priyatam
 * @testType integration
 */
@C3PRUseCases( { VERIFY_SUBJECT, SEARCH_SUBJECT })
public class ResearchStaffDaoTest extends ContextDaoTestCase<ResearchStaffDao> {

    private HealthcareSiteDao healthcareSiteDao;

    
    /**
     * Instantiates a new research staff dao test.
     */
    public ResearchStaffDaoTest() {
    	healthcareSiteDao = (HealthcareSiteDao) getApplicationContext().getBean("healthcareSiteDao");
    }
    
    
    /**
	 * Test domain class.
	 */
	public void testDomainClass() {
		assertEquals("Wrong Domain Class", ResearchStaff.class, getDao().domainClass());
	}
    
    /**
     * Test for loading an a Research Staff by Id
     * 
     * @throws Exception
     */
    public void testGetById() throws Exception {
        ResearchStaff staff = getDao().getById(1000);
        assertEquals("Research Bill", staff.getFirstName());
    }
    
    /**
     * Test for loading an a Research Staff by Id
     * 
     * @throws Exception
     */
    public void testGetByIdForException() throws Exception {
        ResearchStaff staff = getDao().getByNciIdentifier("9999");
        assertNull(staff);
    }

    public void testFailureAddingSameResearchStaffMemberTwice() {

        HealthcareSite site = healthcareSiteDao.getById(1000);
        ResearchStaff rs1 = new LocalResearchStaff();
        rs1.setFirstName("Brad");
        rs1.setLastName("Johnson");
        rs1.setMaidenName("Bradster");
        rs1.setNciIdentifier("NCI-123");
        rs1.setHealthcareSite(site);
        getDao().save(rs1);

        interruptSession();

        int savedId1 = rs1.getId();
        ResearchStaff loadedRS1 = getDao().getById(savedId1);
        assertNotNull("Unable to save research staff member with" + savedId1, loadedRS1);

        ResearchStaff rs2 = new LocalResearchStaff();
        rs2.setFirstName("Brad");
        rs2.setLastName("Johnson");
        rs2.setMaidenName("Bradster");
        rs2.setNciIdentifier("NCI-123");
        rs2.setHealthcareSite(site);

        try {
            getDao().save(rs2);
            interruptSessionForceNewSession();
            fail("it should fail because of duplicate data entry");
        }
        catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * Test for loading all ResearchStaffs
     * 
     * @throws Exception
     */
    public void testGetAll() throws Exception {
        List<ResearchStaff> actual = getDao().getAll();
        assertEquals(5, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong ResearchStaff found", ids, 1000);
        assertContains("Wrong ResearchStaff found", ids, 1001);
        assertContains("Wrong ResearchStaff found", ids, 1001);
        assertContains("Wrong ResearchStaff found", ids, 1001);
    }

    /**
     * Test for loading of Research Staff based on mathing pattern on Staff name
     * 
     * @throws Exception
     */
    public void testGetBySubnameMatchesShortTitle() throws Exception {
        List<ResearchStaff> actual = getDao().getBySubnames(new String[] { "Bi" }, 1000);
        assertEquals("Wrong number of matches", 1, actual.size());
        assertEquals("Wrong match", 1000, (int) actual.get(0).getId());
    }

    /**
     * Test for loading of Site Investigators based on matching pattern on Investigator name
     * 
     * @throws Exception
     */
    public void testGetBySubnameMatchesIntersectionOfSubnames() throws Exception {
        List<ResearchStaff> actual = getDao().getBySubnames(new String[] { "Resea", "Geo" }, 1000);
        assertEquals("Wrong number of matches", 1, actual.size());
        assertEquals("Wrong match", 1001, (int) actual.get(0).getId());
    }
    
    /**
     * Test for loading of Site Investigators based on matching pattern on Investigator email
     * 
     * @throws Exception
     */
    public void testGetBySubnameMatchesIntersectionOfSubnamesAndSubemail() throws Exception {
        List<ResearchStaff> actual = getDao().getBySubNameAndSubEmail(new String[] { "test" }, "code");
        assertEquals("Wrong number of matches", 1, actual.size());
        assertEquals("Wrong match", 1000, (int) actual.get(0).getId());
    }
    
    
    /**
     * Test search by example with wildcard.
     * 
     * @throws Exception the exception
     */
    public void testSearchByExampleWithWildcard() throws Exception{
    	ResearchStaff researchStaff = new LocalResearchStaff();
    	researchStaff.setFirstName("Research");
    	List<ResearchStaff> researchStaffList = getDao().searchByExample(researchStaff, true);
    	assertEquals("Incorrect Size of retrieved list",4,researchStaffList.size());
    }
    
    /**
     * Test search by example without wildcard.
     * 
     * @throws Exception the exception
     */
    public void testSearchByExampleWithoutWildcard() throws Exception{
    	ResearchStaff researchStaff = new LocalResearchStaff();
    	researchStaff.setFirstName("Research Bill");
    	List<ResearchStaff> researchStaffList = getDao().searchByExample(researchStaff, true);
    	assertEquals("Incorrect Size of retrieved list", researchStaffList.size(), 1);
    }
    
    /**
     * Test get by email address.
     * 
     * @throws Exception the exception
     */
    public void testGetByEmailAddress() throws Exception {
    	List <ResearchStaff> researchStaffList = getDao().getByEmailAddress("test@mail.com");
    	assertEquals(researchStaffList.size(), 1);
    	assertEquals("Incorrect staff retrieved", researchStaffList.get(0).getFirstName(), "Research Bill");
    }
    
    /**
     * Test get by nci id.
     * 
     * @throws Exception the exception
     */
    public void testGetByNciId() throws Exception {
        ResearchStaff staff = getDao().getByNciIdentifier("rjfk_1003");
        assertEquals("Research Bill", staff.getFirstName());
    }
    
    
    /**
     * Test search research staff by query.
     * 
     * @throws Exception the exception
     */
    public void testSearchResearchStaffByQuery() throws Exception{
    	ResearchStaffQuery researchStaffQuery = new ResearchStaffQuery();
        researchStaffQuery.filterByEmailAddress("test@mail.com");
        List<ResearchStaff> researchStaffList = getDao().searchResearchStaff(researchStaffQuery);
        assertEquals("Incorrect size", 1, researchStaffList.size());
    }
    
    /**
     * Test search research staff by query.
     * 
     * @throws Exception the exception
     */
    public void testLoadRemoteResearchStaffByUniqueIdentifier() throws Exception{
    	List<ResearchStaff> researchStaffList = new ArrayList<ResearchStaff>();
    	researchStaffList = getDao().getByUniqueIdentifier("bob@gmail.com");
        assertEquals("Incorrect size", 1, researchStaffList.size());
     }
    
    /**
     * Test search research staff by query.
     * 
     * @throws Exception the exception
     */
    public void testSearchRemoteResearchStaffByOrganizationInstituteCode() throws Exception{
    	HealthcareSite healthcareSite = new HealthcareSite();
    	healthcareSite.setNciInstituteCode("code");
    	List<ResearchStaff> researchStaffList = new ArrayList<ResearchStaff>();
    	researchStaffList = getDao().getResearchStaffByOrganizationNCIInstituteCode(healthcareSite);
        assertEquals("Incorrect size", 5, researchStaffList.size());
    }

}