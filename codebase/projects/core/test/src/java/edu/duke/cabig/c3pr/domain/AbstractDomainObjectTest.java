package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.utils.ApplicationTestCase;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;
import gov.nih.nci.cabig.ctms.domain.DomainObjectTools;

/**
 * JUnit Tests for AbstractMutableDomainObject
 * 
 * @author Priyatam
 * @testType unit
 */
public class AbstractDomainObjectTest extends ApplicationTestCase {
    private AbstractMutableDomainObject o1, o2;

    protected void setUp() throws Exception {
        super.setUp();
        o1 = new TestObject(1);
        o2 = new TestObject(2);
    }

    /**
     * Test if domain object is equal by Id with two nulls
     * 
     * @throws Exception
     */
    public void testEqualByIdWithTwoNulls() throws Exception {
        assertTrue(DomainObjectTools.equalById(null, null));
    }

    /**
     * Test if domain object is equal by Id with first null
     * 
     * @throws Exception
     */
    public void testEqualByIdWithFirstNull() throws Exception {
        assertFalse(DomainObjectTools.equalById(null, o1));
    }

    /**
     * Test if domain object is equal by Id with second null
     * 
     * @throws Exception
     */
    public void testEqualByIdWithSecondNull() throws Exception {
        assertFalse(DomainObjectTools.equalById(o1, null));
    }

    /**
     * Test if domain object is equal by Id when they are identically same
     * 
     * @throws Exception
     */
    public void testEqualByIdWhenSame() throws Exception {
        assertTrue(DomainObjectTools.equalById(o1, o1));
    }

    /**
     * Test if domain object is equal by Id when they have same equal references
     * 
     * @throws Exception
     */
    public void testEqualByIdWhenEqual() throws Exception {
        o2.setId(o1.getId());
        assertTrue(DomainObjectTools.equalById(o1, o2));
    }

    /**
     * Test if domain object is equal by Id when they arent equal
     * 
     * @throws Exception
     */
    public void testEqualByIdWhenNotEqual() throws Exception {
        assertFalse(DomainObjectTools.equalById(o1, o2));
    }
}
