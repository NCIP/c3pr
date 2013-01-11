/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.selenium;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

public class CreateResearchStaffTest extends AbstractSeleniumTestCase {
	
	public void testCreateResearchStaff() throws Exception {
		
		
		
		selenium.open("/c3pr/pages/personAndOrganization/researchStaff/createResearchStaff");
		selenium.waitForPageToLoad("30000");
		selenium.click("healthcareSite-input");
		selenium.typeKeys("healthcareSite-input", "NC010");
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (selenium.isElementPresent("//div[@id='healthcareSite-choices']/ul/li/strong")) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

		selenium.click("//div[@id='healthcareSite-choices']/ul/li/strong");
		selenium.click("healthcareSite-input");
		selenium.type("firstName", "duke");
		selenium.type("lastName", "admin");
		selenium.type("assignedIdentifier", "nco#44");
		selenium.type("contactMechanisms[0].value", "duke@admin.com");
		selenium.click("groups_0");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
	}
}
