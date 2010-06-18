package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for DiseaseCategoryDao
 * 
 * @author Ramakrishna
 * @testType unit
 */
public class ReasonDaoTest extends ContextDaoTestCase<ReasonDao> {

    public void testGetReasonByCode() throws Exception{
    	assertEquals(1001, getDao().getReasonByCode("ABC").getId().intValue());
    	assertEquals(1000, getDao().getReasonByCode("ABCD").getId().intValue());
    	assertEquals(1002, getDao().getReasonByCode("EFG").getId().intValue());
    	assertEquals(1003, getDao().getReasonByCode("EFGH").getId().intValue());
    }
}