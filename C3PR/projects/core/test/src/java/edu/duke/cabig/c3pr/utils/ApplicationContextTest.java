package edu.duke.cabig.c3pr.utils;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;


/**
 * Base Dao Test Case for all dao level test cases
 * 
 * @author Rhett Sutphin, Priyatam
 */
public abstract class ApplicationContextTest extends TestCase {
 

    public ApplicationContext getApplicationContext() {
        return ApplicationTestCase.getDeployedCoreApplicationContext();
    }


}
