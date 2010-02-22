package edu.duke.cabig.c3pr.utils;

import org.springframework.context.ApplicationContext;


/**
 * Base Dao Test Case for all security test cases
 * 
 * @author Kruttik Aggarwal
 */
public abstract class DaoSecuredTestCase extends DaoTestCase {
	
	@Override
	public ApplicationContext getApplicationContext() {
		return ApplicationTestCase.getDeployedApplicationContext();
	}
}
