/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import org.acegisecurity.context.SecurityContextHolder;
import org.springframework.context.ApplicationContext;


/**
 * Base Dao Test Case for all security test cases
 * 
 * @author Kruttik Aggarwal
 */
public abstract class DaoSecuredTestCase extends AuthenticationProviderDaoTestCase {
	
	@Override
	public ApplicationContext getApplicationContext() {
		return ApplicationTestCase.getDeployedApplicationContext();
	}
	
	
    public void setUser(String username) {
    	SecurityContextHolder.getContext().setAuthentication(setUserInSecurityContext(username));
    }

}
