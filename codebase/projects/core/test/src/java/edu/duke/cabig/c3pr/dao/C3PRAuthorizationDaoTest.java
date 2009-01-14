package edu.duke.cabig.c3pr.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;
import gov.nih.nci.security.authorization.domainobjects.User;

/**
 * JUnit Tests for UserDao.
 * 
 * @author Priyatam
 * @testType integration
 */
public class C3PRAuthorizationDaoTest extends ContextDaoTestCase<HibernateDaoSupport> {
    
	C3PRAuthorizationDao c3prAuthorizationDao;
	
	public C3PRAuthorizationDaoTest(){
		c3prAuthorizationDao = (C3PRAuthorizationDao) getApplicationContext().getBean("csmAuthorizationDao");
	}
    
    
    /**
     * Test get by email address.
     * 
     * @throws Exception the exception
     */
    public void testGetUser() throws Exception {
    	User user = c3prAuthorizationDao.getUser("c3pr_admin");
    	assertNotNull(user);
    	assertEquals("Incorrect user retrieved", user.getFirstName(), "c3pr");
    }


}

