package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.dao.C3PRBaseDao;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;


/**
 * @author Priyatam
 */
public class TestObject extends AbstractMutableDomainObject { 
    public TestObject() { }

    public TestObject(int id) { setId(id); }

    public static class MockableDao extends C3PRBaseDao<TestObject> {
        public Class<TestObject> domainClass() {
            return TestObject.class;
        }
    }
}
