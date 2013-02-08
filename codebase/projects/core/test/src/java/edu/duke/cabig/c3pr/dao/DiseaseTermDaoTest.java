/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
/**
 * JUnit Tests for DiseaseTermDao
 * 
 * @author Ramakrishna
 * @testType unit
 */
package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * The Class DiseaseTermDaoTest.
 */
public class DiseaseTermDaoTest extends DaoTestCase {

	/** The disease term dao. */
	private DiseaseTermDao diseaseTermDao = (DiseaseTermDao)getApplicationContext().getBean("diseaseTermDao");

    /**
     * Test for loading all Disease Terms.
     * 
     * @throws Exception the exception
     */
    public void testGetByCategoryId() throws Exception {
        List<DiseaseTerm> actual = diseaseTermDao.getByCategoryId(1000);
        assertEquals(1, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong disease term found", ids, 1000);
    }
    
    /**
     * Test domain class.
     * 
     * @throws Exception the exception
     */
    public void testDomainClass() throws Exception{
    	assertEquals("Wrong domain class",DiseaseTerm.class,diseaseTermDao.domainClass());
    }
    
    /**
     * Test get by ctep term.
     * 
     * @throws Exception the exception
     */
    public void testGetByCtepTerm() throws Exception{
    	List<DiseaseTerm> diseaseTerms = diseaseTermDao.getByCtepTerm("AIDS-related anal cancer");
    	assertEquals("Wrong numbers of disease categories retrieved",1, diseaseTerms.size());
    }
}
