package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.SEARCH_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.VERIFY_SUBJECT;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.dao.query.ResearchStaffQuery;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalResearchStaff;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for ResearchStaffDao
 * 
 * @author Priyatam, Vinay G
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
        List<ResearchStaff> actual = getDao().getBySubNameAndSubEmail(new String[] { "test" }, "NC010");
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
    	
    	HealthcareSite healthcareSite = healthcareSiteDao.getById(1000);
    	researchStaff.setHealthcareSite(healthcareSite);
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
    	ResearchStaff researchStaff = getDao().getByEmailAddress("test@mail.com");
    	assertNotNull(researchStaff);
    	assertEquals("Incorrect staff retrieved", researchStaff.getFirstName(), "Research Bill");
    }
    
    /**
     * Test get by nci id.
     * 
     * @throws Exception the exception
     */
    public void testGetByNciId() throws Exception {
        HealthcareSite healthcareSite = healthcareSiteDao.getById(1000);
        RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
		remoteResearchStaff.setFirstName("LArry");
		remoteResearchStaff.setLastName("Page");
		remoteResearchStaff.setNciIdentifier("NCI_101");
		remoteResearchStaff.setHealthcareSite(healthcareSite);
		getDao().save(remoteResearchStaff);
		ResearchStaff staff = getDao().getByNciIdentifier("NCI_101");
        assertNotNull(remoteResearchStaff);
		//assertEquals("Research Bill", staff.getFirstName());
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
    	researchStaffList = getDao().getByExternalIdentifierFromLocal("bob@gmail.com");
        assertEquals("Incorrect size", 1, researchStaffList.size());
     }
    
    /**
     * Test search Remote research staff by healthcareSite.
     * This should get the new ones and save them to the database too...this includes the associated orgs
     * 
     * @throws Exception the exception
     
    public void testGetRemoteResearchStaffByOrganizationInstituteCodeWithHealthcareSiteSpecified() throws Exception{
    	HealthcareSite healthcareSite = new LocalHealthcareSite();
    	healthcareSite.setNciInstituteCode("RM-TST-ID2");
    	List<ResearchStaff> researchStaffList = new ArrayList<ResearchStaff>();
    	researchStaffList = getDao().getResearchStaffByOrganizationNCIInstituteCode(healthcareSite);
        assertEquals("Incorrect size", 3, researchStaffList.size());
    }*/

    /**
     * Gets all the remoteResearchStaff since the hcs is not specified
     * @throws Exception
     
    public void testGetRemoteResearchStaffFromResolverByExample() throws Exception{
    	RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
    	List<RemoteResearchStaff> remoteResearchStaffList = getDao().getRemoteResearchStaffFromResolverByExample(remoteResearchStaff);
        assertEquals("Incorrect size", 9, remoteResearchStaffList.size());
    }*/
    
    /**
     * Gets all the remoteResearchStaff since the hcs is not specified
     * This one tests to see whether coppa data overrides the db content successfully
     * @throws Exception
     */
    /*public void testUpdateRemoteResearchStaffFromResolverByExample() throws Exception{
    	getDao().getRemoteResearchStaffFromResolverByExample(new RemoteResearchStaff());
    	String ui = "";
    	Integer id = null;
    	
        RemoteResearchStaff remoteResearchStaff = (RemoteResearchStaff)getDao().getByEmailAddress("LPage@nci.org").get(0);
        id = remoteResearchStaff.getId();
        remoteResearchStaff.setFirstName("LarryTemp");
        remoteResearchStaff.setLastName("PageTemp");
        ui = remoteResearchStaff.getUniqueIdentifier();
        remoteResearchStaff.setUniqueIdentifier("temp");
        getDao().mergeResearchStaff(remoteResearchStaff);
        
        interruptSession();
        
        RemoteResearchStaff remoteResearchStaff2 = (RemoteResearchStaff)getDao().getById(id);
        assertEquals(remoteResearchStaff2.getFirstName(), "LarryTemp");
        assertEquals(remoteResearchStaff2.getLastName(), "PageTemp");
        remoteResearchStaff2.setUniqueIdentifier(ui);
        getDao().mergeResearchStaff(remoteResearchStaff2);
        
        interruptSession();
        
        
        RemoteResearchStaff remoteResearchStaff3 = (RemoteResearchStaff)getDao().getByEmailAddress("LPage@nci.org").get(0);
        remoteResearchStaff3.setUniqueIdentifier("temp");
        
        interruptSession();
        
        RemoteResearchStaff remoteResearchStaff5 = (RemoteResearchStaff)getDao().getById(id);
        assertEquals(remoteResearchStaff5.getFirstName(), "Larry");
        assertEquals(remoteResearchStaff5.getLastName(), "Page");
        
    }
*/
}



