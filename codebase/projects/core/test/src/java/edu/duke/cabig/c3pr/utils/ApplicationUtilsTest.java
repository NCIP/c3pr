package edu.duke.cabig.c3pr.utils;

import junit.framework.TestCase;

public class ApplicationUtilsTest extends TestCase{

	public void testIsUnix(){
		String str = System.getProperty("os.name");
		int i = str.indexOf("Win");
		if (i != -1) {
			assertFalse("Unix environment is not present", ApplicationUtils.isUnix());
		}else{
			assertTrue("Winows environment is not present", ApplicationUtils.isUnix());
		}
	}
	  
}
