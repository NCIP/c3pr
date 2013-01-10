/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;
import gov.nih.nci.cabig.ctms.suite.authorization.csmext.FasterAuthorizationDao;
import gov.nih.nci.security.authorization.domainobjects.User;

/**
 * JUnit Tests for UserDao.
 * 
 * @author Priyatam
 * @testType integration
 */
public class C3PRAuthorizationDaoTest extends ContextDaoTestCase<HibernateDaoSupport> {
    
	/** The c3pr authorization dao. */
	FasterAuthorizationDao c3prAuthorizationDao;
	
	/**
	 * Instantiates a new c3 pr authorization dao test.
	 */
	public C3PRAuthorizationDaoTest(){
		c3prAuthorizationDao = (FasterAuthorizationDao) getApplicationContext().getBean("csmAuthorizationDao");
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

