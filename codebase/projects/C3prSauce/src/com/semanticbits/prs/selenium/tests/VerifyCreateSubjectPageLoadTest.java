package com.semanticbits.prs.selenium.tests;

import com.semanticbits.saucelabs.AbstractSauceOnDemandTestCase;


public class VerifyCreateSubjectPageLoadTest extends AbstractSauceOnDemandTestCase {
	
	public void testVerifyCreateSubjectPageLoadTest() throws Exception {
		selenium.setContext("sauce:job-name="+VerifyCreateSubjectPageLoadTest.class.getSimpleName());
		selenium.open("/c3pr/public/login");
		selenium.waitForPageToLoad("60000");
		selenium.type("name=j_username", "c3pr_admin");
		selenium.type("name=j_password", "c3pr_admin");
		selenium.click("id=power_btn");
		selenium.waitForPageToLoad("60000");
		selenium.click("id=firstlevelnav_personOrganizations");
		selenium.waitForPageToLoad("60000");
		assertTrue(selenium.isTextPresent("Search"));
		selenium.click("link=Create Subject");
		selenium.waitForPageToLoad("60000");
		assertTrue(selenium.isTextPresent("Details"));
		selenium.click("link=Log out");
		selenium.waitForPageToLoad("60000");
	}
}
