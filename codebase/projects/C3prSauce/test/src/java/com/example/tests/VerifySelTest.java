package com.example.tests;

import com.thoughtworks.selenium.SeleneseTestCase;

public class VerifySelTest extends SeleneseTestCase {
	
	public void setUp() throws Exception {
		setUp("https://dev.semanticbits.com/", "{\"username\": \"monishd\",\"access-key\":\"a7234082-1048-46ef-9ee8-4e6430806e98\",\"browser\": \"firefox\",\"browser-version\":\"3.0\",\"job-name\":\"Untitled\",\"max-duration\":1800,\"record-video\":true,\"user-extensions-url\":\"\",\"os\":\"Linux\"}");
	}
	public void testVerifySelTest() throws Exception {
		selenium.open("/c3pr/public/login");
		selenium.waitForPageToLoad("60000");
		selenium.type("name=j_username", "c3pr_admin");
		selenium.type("name=j_password", "c3pr_admin");
		selenium.click("id=power_btn");
		selenium.waitForPageToLoad("60000");
		selenium.click("id=firstlevelnav_personOrganizations");
		selenium.waitForPageToLoad("60000");
		verifyTrue(selenium.isTextPresent("Search"));
		selenium.click("link=Create Subject");
		selenium.waitForPageToLoad("60000");
		verifyTrue(selenium.isTextPresent("Details"));
		selenium.click("link=Log out");
		selenium.waitForPageToLoad("60000");
	}
}