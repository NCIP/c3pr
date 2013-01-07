package com.semanticbits.prs.selenium.tests;

import com.semanticbits.saucelabs.AbstractSauceOnDemandTestCase;


public class VerifyPasswordPolicyPageLoadTest extends AbstractSauceOnDemandTestCase {

	public void testVerifyPasswordPolicyPageLoadTest() throws Exception {
		selenium.setContext("sauce:job-name="+VerifyPasswordPolicyPageLoadTest.class.getSimpleName());
		selenium.open("/c3pr/public/login");
		selenium.waitForPageToLoad("60000");
		selenium.type("name=j_username", "c3pr_admin");
		selenium.type("name=j_password", "c3pr_admin");
		selenium.click("id=power_btn");
		selenium.waitForPageToLoad("60000");
		assertTrue(selenium.isTextPresent("Welcome |"));
		selenium.click("id=firstlevelnav_administration");
		selenium.waitForPageToLoad("60000");
		assertTrue(selenium.isTextPresent("Notifications"));
		selenium.click("link=Configure Password Policy");
		selenium.waitForPageToLoad("60000");
		assertTrue(selenium.isTextPresent("Password Policy Configuration"));
		selenium.click("link=Log out");
		selenium.waitForPageToLoad("60000");
	}
}
