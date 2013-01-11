/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.LocalInvestigator;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for InvestigatorDao
 * 
 * @author Priyatam
 * @testType unit
 */
public class InvestigatorDaoTest extends ContextDaoTestCase<InvestigatorDao> {

    /**
     * Test for loading an Investigator by Id
     * 
     * @throws Exception
     */
    public void testGetById() throws Exception {
        Investigator inv = getDao().getById(1000);
        assertEquals("Investigator Bill", inv.getFirstName());
    }

    /**Note:  this test requires that the inv-resolver be overridden to return a hard-coded investigator
     * by using the following:
     * 
     * 	RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
		remoteInvestigator.setFirstName("remote fname");
		remoteInvestigator.setLastName("remote lname");
		remoteInvestigator.setExternalId(externalId);
		RemoteContactMechanism contactMechanism = new RemoteContactMechanism();
		contactMechanism.setType(ContactMechanismType.PHONE);
		contactMechanism.setValue("9727401169");
		
		remoteInvestigator.getContactMechanisms().add(contactMechanism);
		return remoteInvestigator;

     * Test for loading an Investigator by Id.
     * Also test out the collection handling by the interceptor/object populator
     * 
     * @throws Exception
     
    public void testGetRemoteById() throws Exception {
    	{
	    	Investigator inv = getDao().getById(1005);
	        assertEquals("9727401169", inv.getContactMechanisms().get(0).getValue());
	        
	        ContactMechanism contactMechanism = new LocalContactMechanism();
			contactMechanism.setType(ContactMechanismType.Fax);
			contactMechanism.setValue("7036239046");
			inv.getContactMechanisms().add(contactMechanism);
			
	        getDao().save(inv);
	    }
    	interruptSession();
    	{
    		Investigator inv = getDao().getById(1005);
    		Iterator iter = inv.getContactMechanisms().iterator();
    		while(iter.hasNext()){
    			ContactMechanism contactMechanism = (ContactMechanism)iter.next();
    			if(contactMechanism.getType().equals(ContactMechanismType.Fax)){
    				assertEquals("7036239046", contactMechanism.getValue());
    			}
    			if(contactMechanism.getType().equals(ContactMechanismType.PHONE)){
    				assertEquals("9727401169", contactMechanism.getValue());
    			}
    		}
    	}
    }*/

    
    /**
     * Test for loading all Investigators
     * 
     * @throws Exception
     */
    public void testGetAll() throws Exception {
        List<Investigator> actual = getDao().getAll();
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong investigator found", ids, 1000);
        assertContains("Wrong investigator found", ids, 1001);
        assertContains("Wrong investigator found", ids, 1002);
        assertContains("Wrong investigator found", ids, 1003);
        assertContains("Wrong investigator found", ids, 1005);
    }
    
    public void testDomainClass() throws Exception{
    	assertEquals("Wrong domain class",Investigator.class, getDao().domainClass());
    }
    
    public void testForLazyInvestigator() throws Exception{
    	Investigator investigator = getDao().getById(1000);
    	interruptSession();
    	try{
    		investigator.getHealthcareSiteInvestigators().get(0).getSiteInvestigatorGroupAffiliations().get(0);
    		fail("Test should not have reached this line");
    	}catch (org.hibernate.LazyInitializationException ex){
    		
    	}
    }
    
    public void testLoadedInvestigator() throws Exception{
    	Investigator investigator = getDao().getLoadedInvestigatorById(1000);
    	interruptSession();
    	try{
    		investigator.getHealthcareSiteInvestigators().get(0).getSiteInvestigatorGroupAffiliations().get(0);
    		investigator.getHealthcareSiteInvestigators().get(0).getStudyInvestigators().get(0);
    	}catch (org.hibernate.LazyInitializationException ex){
    		fail("Should not have thrown lazy initialization");
    	}
    	assertEquals("Wrong number of group affiliations",1, investigator.getHealthcareSiteInvestigators().get(0).getSiteInvestigatorGroupAffiliations().size());
    	assertEquals("Wrong number of study investigators",1, investigator.getHealthcareSiteInvestigators().get(0).getStudyInvestigators().size());
    }
    
    /**
     * Test get by name for remote.
     * 
     * @throws Exception the exception
     
    public void testGetByNameForRemote() throws Exception{
    	RemoteInvestigator remoteInvestigator = getSampleRemoteInvestigatorWithName();
		List objList = getDao().searchByExample(remoteInvestigator, true);
		
		assertNotNull(objList);
		assertTrue(objList.size() > 0);
    }*/
    
	/**
	 * Gets the sample remote Investigator with Name.
	 * 
	 * @return the sample remote Investigator
	 
	private RemoteInvestigator getSampleRemoteInvestigatorWithName() {
		RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
		remoteInvestigator.setFirstName("Jam");
		remoteInvestigator.setLastName("long");
		Address address = new Address();
		address.setCity("");
		address.setCountryCode("");
		remoteInvestigator.setAddress(address);
		return remoteInvestigator;
	}*/
    
    public void testMerge() throws Exception{
    	Investigator investigator = getDao().getLoadedInvestigatorById(1000);
    	investigator.getHealthcareSiteInvestigators().get(0).getStudyInvestigators().get(0).setStatusCode(InvestigatorStatusCodeEnum.IN);
    	getDao().merge(investigator);
    	assertEquals("Wrong status code for the study investigator",InvestigatorStatusCodeEnum.IN, investigator.getHealthcareSiteInvestigators().get(0).getStudyInvestigators().get(0).getStatusCode());
    }
    
    public void testSearchByExampleWithWildCard() throws Exception{
    	Investigator investigator = new LocalInvestigator();
    	investigator.setFirstName("Investig");
    	List<Investigator> investigators = getDao().searchByExample(investigator,true);
    	assertEquals("Wrong numbers of investigators retrieved",5, investigators.size());
    }
    
    
    public void testSearchByExampleWithAssignedIdentifier() throws Exception{
    	Investigator investigator = new LocalInvestigator();
    	investigator.setAssignedIdentifier("NCIID_1232");
    	List<Investigator> investigators = getDao().searchByExample(investigator,false);
    	assertEquals("Wrong numbers of investigators retrieved",1, investigators.size());
    }
    
    public void testGetBySubnamesOnAssignedIdentifier() throws Exception{
    	String[] subnames = new String[]{"NCIID_1232"};
    	List<Investigator> investigators = getDao().getBySubnames(subnames);
    	assertEquals("Wrong numbers of investigators retrieved",2, investigators.size());
    }
    
    public void testGetBySubnamesOnFirstName1() throws Exception{
    	String[] subnames = new String[]{"Brady"};
    	List<Investigator> investigators = getDao().getBySubnames(subnames);
    	assertEquals("Wrong numbers of investigators retrieved",1, investigators.size());
    }
    
    public void testGetBySubnamesOnFirstName2() throws Exception{
    	String[] subnames = new String[]{"Investigator"};
    	List<Investigator> investigators = getDao().getBySubnames(subnames);
    	assertEquals("Wrong numbers of investigators retrieved",5, investigators.size());
    }
    
    public void testGetBySubnamesOnLastName() throws Exception{
    	String[] subnames = new String[]{"Staff3"};
    	List<Investigator> investigators = getDao().getBySubnames(subnames);
    	assertEquals("Wrong numbers of investigators retrieved",1, investigators.size());
    }
}
