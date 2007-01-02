package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.dao.AbstractBaseDao;


/**
 * @author Priyatam
 */
public class TestObject extends AbstractDomainObject { 
    public TestObject() { }

    public TestObject(int id) { setId(id); }

    public static class MockableDao extends AbstractBaseDao<TestObject> {
        public Class<TestObject> domainClass() {
            return TestObject.class;
        }
    }
}
