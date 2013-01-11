/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.domain.DiseaseCategory;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for DiseaseCategoryDao
 * 
 * @author Ramakrishna
 * @testType unit
 */
public class DiseaseCategoryDaoTest extends ContextDaoTestCase<DiseaseCategoryDao> {


    /**
     * Test for loading all Disease Categories
     * 
     * @throws Exception
     */
    public void testGetAll() throws Exception {
        List<DiseaseCategory> actual = getDao().getAll();
        assertEquals(2, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong investigator found", ids, 1000);
        assertContains("Wrong investigator found", ids, 1001);
    }
    
    public void testDomainClass() throws Exception{
    	assertEquals("Wrong domain class",DiseaseCategory.class,getDao().domainClass());
    }
    
    public void testGetBySubNames() throws Exception{
    	List<DiseaseCategory> diseaseCategories = getDao().getBySubname(new String[]{"le"}, 1000);
    	assertEquals("Wrong numbers of disease categories retrieved",1, diseaseCategories.size());
    }
    
    public void testGetByParentId() throws Exception{
    	List<DiseaseCategory> diseaseCategories = getDao().getByParentId(1000);
    	assertEquals("Wrong numbers of disease categories retrieved",1, diseaseCategories.size());
    	assertEquals("Wrong category retrieved","Leukemia", diseaseCategories.get(0).getName());
    }
}
