package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.domain.SiteInvestigatorGroupAffiliation;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for InvestigatorDao
 * 
 * @author Priyatam
 * @testType unit
 */
public class InvestigatorGroupDaoTest extends ContextDaoTestCase<InvestigatorGroupDao> {
	
	private InvestigatorGroupDao investigatorGroupDao = (InvestigatorGroupDao) 
		getApplicationContext().getBean("investigatorGroupDao");

    /**
     * Test for loading all Investigators
     * 
     * @throws Exception
     */
    public void testGetAll() throws Exception {
        List<InvestigatorGroup> actual = investigatorGroupDao.getAll();
        assertEquals(3, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Investigator group not found", ids, 1000);
        assertContains("Investigator group not found", ids, 1001);
        assertContains("Investigator group not found", ids, 1002);
    }
    
    public void testDomainClass() throws Exception{
    	assertEquals("Wrong domain class",InvestigatorGroup.class, investigatorGroupDao.domainClass());
    }
    
    public void testGetAffiliationsByGroupId() throws Exception{
    	List<SiteInvestigatorGroupAffiliation> investigatorAffiliations = investigatorGroupDao.getAffiliationsByGroupId(1000);
    	 assertEquals("Wrong number of affiliations found", 1, investigatorAffiliations.size());
    }
}