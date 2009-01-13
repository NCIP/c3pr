package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.Investigator;
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

    /**
     * Test for loading all Investigators
     * 
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
    
    public void testGetByEmail() throws Exception{
    	ContactMechanism contactMechanism = new ContactMechanism();
    	contactMechanism.setType(ContactMechanismType.EMAIL);
    	List<Investigator> investigators = new ArrayList<Investigator>();
    	investigators = getDao().getByEmailAddress("test@mail.com");
    	assertEquals("Wrong number of investigators",1, investigators.size());
    }
    
    public void testMerge() throws Exception{
    	Investigator investigator = getDao().getLoadedInvestigatorById(1000);
    	investigator.getHealthcareSiteInvestigators().get(0).getStudyInvestigators().get(0).setStatusCode("Inactive");
    	getDao().merge(investigator);
    	assertEquals("Wrong status code for the study investigator","Inactive", investigator.getHealthcareSiteInvestigators().get(0).getStudyInvestigators().get(0).getStatusCode());
    }
    
    public void testGetBySubNamesWithWildCard() throws Exception{
    	Investigator investigator = new Investigator();
    	investigator.setNciIdentifier("NCI");
    	List<Investigator> investigators = getDao().searchByExample(investigator,true);
    	assertEquals("Wrong numbers of investigators retrieved",4, investigators.size());
    }
    
    public void testGetBySubNames() throws Exception{
    	Investigator investigator = new Investigator();
    	investigator.setNciIdentifier("NCI_1232");
    	List<Investigator> investigators = getDao().searchByExample(investigator,false);
    	assertEquals("Wrong numbers of investigators retrieved",1, investigators.size());
    }
    
    public void testGetByNCIIdentifier() throws Exception{
    	Investigator investigator = new Investigator();
    	investigator.setNciIdentifier("NCI_1232");
    	List<Investigator> investigators = getDao().searchByExample(investigator,false);
    	assertEquals("Wrong numbers of investigators retrieved",1, investigators.size());
    }
}