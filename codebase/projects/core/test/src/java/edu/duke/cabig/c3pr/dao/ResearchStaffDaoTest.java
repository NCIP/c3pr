package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.SEARCH_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.VERIFY_SUBJECT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.dao.query.ResearchStaffQuery;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalResearchStaff;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.ProtectionGroupRoleContext;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;

/**
 * JUnit Tests for ResearchStaffDao
 * 
 * @author Priyatam, Vinay G
 * @testType integration
 */
@C3PRUseCases( { VERIFY_SUBJECT, SEARCH_SUBJECT })
public class ResearchStaffDaoTest extends ContextDaoTestCase<ResearchStaffDao> {

    private HealthcareSiteDao healthcareSiteDao;
	private UserProvisioningManager userProvisioningManager;
    
    /**
     * Instantiates a new research staff dao test.
     */
    public ResearchStaffDaoTest() {
    	healthcareSiteDao = (HealthcareSiteDao) getApplicationContext().getBean("healthcareSiteDao");
    	userProvisioningManager = (UserProvisioningManager) getApplicationContext().getBean("csmUserProvisioningManager");
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
        ResearchStaff staff = getDao().getByAssignedIdentifier("9999");
        assertNull(staff);
    }

    public void testFailureAddingSameResearchStaffMemberTwice() {

        HealthcareSite site = healthcareSiteDao.getById(1000);
        ResearchStaff rs1 = new LocalResearchStaff();
        rs1.setFirstName("Brad");
        rs1.setLastName("Johnson");
        rs1.setMaidenName("Bradster");
        rs1.setAssignedIdentifier("NCI-123");
        rs1.addHealthcareSite(site);
        getDao().save(rs1);

        interruptSession();

        int savedId1 = rs1.getId();
        ResearchStaff loadedRS1 = getDao().getById(savedId1);
        assertNotNull("Unable to save research staff member with" + savedId1, loadedRS1);

        ResearchStaff rs2 = new LocalResearchStaff();
        rs2.setFirstName("Brad");
        rs2.setLastName("Johnson");
        rs2.setMaidenName("Bradster");
        rs2.setAssignedIdentifier("NCI-123");
        rs2.addHealthcareSite(site);

        try {
            getDao().save(rs2);
            interruptSessionForceNewSession();
            fail("it should fail because nci id is a unique constraint");
        }
        catch (Exception e) {
        	assertTrue(true);
        }
    }

    
    /**
     * Test for loading of Site Investigators based on matching pattern on Investigator email
     * 
     * @throws Exception
     */
    public void testGetBySubnameMatchesIntersectionOfSubnamesAndSubemail() throws Exception {
        List<ResearchStaff> actual = getDao().getBySubNameAndSubEmail(new String[] { "test" });
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
    	researchStaff.addHealthcareSite(healthcareSite);
    	List<ResearchStaff> researchStaffList = getDao().searchByExample(researchStaff, true);
    	assertEquals("Incorrect Size of retrieved list",researchStaffList.size(), 4);
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
     * Test get by nci id.
     * 
     * @throws Exception the exception
     */
    public void testGetByNciId() throws Exception {
        HealthcareSite healthcareSite = healthcareSiteDao.getById(1000);
        RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
		remoteResearchStaff.setFirstName("LArry");
		remoteResearchStaff.setLastName("Page");
		remoteResearchStaff.setAssignedIdentifier("NCI_101");
		remoteResearchStaff.addHealthcareSite(healthcareSite);
		getDao().save(remoteResearchStaff);
		ResearchStaff staff = getDao().getByAssignedIdentifier("NCI_101");
        assertNotNull(remoteResearchStaff);
		//assertEquals("Research Bill", staff.getFirstName());
    }
    
    public void testGetResearchStaffByOrganizationNCIInstituteCode (){
    	HealthcareSite healthcareSite = healthcareSiteDao.getById(1001);
    	List<ResearchStaff> rsList = getDao().getResearchStaffByOrganizationCtepCodeFromLocal(healthcareSite);
    	assertEquals(1, rsList.size());
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
    
    //newly added
	public void testCreateOrModifyResearchStaff(ResearchStaff researchStaff,
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap, boolean hasAccessToAllSites) throws C3PRBaseException {
	}
	
	public void testCreateCSMUser(ResearchStaff researchStaff, String username, boolean hasAccessToAllSites) throws C3PRBaseException{
	}

	public void testCreateCSMUserAndAssignRoles(){
		ResearchStaff staff = getDao().getById(1001);
		Map<HealthcareSite, List<C3PRUserGroupType>> associationMap = new HashMap<HealthcareSite, List<C3PRUserGroupType>>();
		HealthcareSite hcs1 = healthcareSiteDao.getById(1001);
		
		List<C3PRUserGroupType> roleList = new ArrayList<C3PRUserGroupType>();
		roleList.add(C3PRUserGroupType.STUDY_TEAM_ADMINISTRATOR);
		roleList.add(C3PRUserGroupType.STUDY_CREATOR);
		associationMap.put(hcs1, roleList);
		try {
			getDao().createOrModifyResearchStaff(staff, true, "somename", associationMap , false);
			ResearchStaff reloadedStaff = getDao().getById(1001);
			User user = getDao().getCSMUser((C3PRUser)reloadedStaff);

			Set<Group> groups = userProvisioningManager.getGroups(user.getUserId().toString());
			assertEquals("wrong number of groups", groups.size(), 2);
			Set<ProtectionGroupRoleContext> pgrcList = userProvisioningManager.getProtectionGroupRoleContextForUser(user.getUserId().toString());
    		
	    	Iterator<Group> iter = groups.iterator();
	    	Group group;
	    	String groupName;
	    	while(iter.hasNext()){
	    		group = (Group)iter.next();
	    		groupName = group.getGroupName();
	    		Iterator<ProtectionGroupRoleContext> iter2 = pgrcList.iterator();
	    		while(iter2.hasNext()){
	    			if(iter2.next().getProtectionGroup().getProtectionGroupName().equals("HealthcareSite."+hcs1.getPrimaryIdentifier())){
			    		if(!groupName.equals(C3PRUserGroupType.STUDY_TEAM_ADMINISTRATOR.getCode()) &&
								!groupName.equals(C3PRUserGroupType.STUDY_CREATOR.getCode())){
							fail("Wrong groups associated to the organization");
						}
		    		} else {
		    			fail("Wrong protection group associated to the organization");
		    		}	
	    		}
	    	}
		} catch (C3PRBaseException e) {
			e.printStackTrace();
		} catch (CSObjectNotFoundException e) {
			e.printStackTrace();
		} 
	}

	
	public void testCreateResearchStaff(ResearchStaff researchStaff) throws C3PRBaseException {
	}

	public void testCreateResearchStaffWithCSMUser(ResearchStaff researchStaff, String username, boolean hasAccessToAllSites) 
			throws C3PRBaseException {
	}

	public void testCreateResearchStaffWithCSMUserAndAssignRoles(ResearchStaff researchStaff, String username,
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap, boolean hasAccessToAllSites) throws C3PRBaseException {
	}

	public void testCreateSuperUser(ResearchStaff researchStaff, String username,
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap) throws C3PRBaseException {
	}

	public void testCreateResearchStaff(ResearchStaff researchStaff, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap)  throws C3PRBaseException {
	}
    //newly added

	public UserProvisioningManager getUserProvisioningManager() {
		return userProvisioningManager;
	}


	public void setUserProvisioningManager(
			UserProvisioningManager userProvisioningManager) {
		this.userProvisioningManager = userProvisioningManager;
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



