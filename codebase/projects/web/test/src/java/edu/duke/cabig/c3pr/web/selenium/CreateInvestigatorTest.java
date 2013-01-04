/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.selenium;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

public class CreateInvestigatorTest extends AbstractSeleniumTestCase {
	
	public void testCreateInvestigator() throws Exception {
		ajaxWidgets.login();
		selenium.open("/c3pr/pages/personAndOrganization/investigator/createInvestigator");
		ajaxWidgets.typeAutosuggest("healthcareSite0-input", "duke", "healthcareSite0-choices");
		selenium.select("healthcareSiteInvestigators[0].statusCode", "label=Active");
		selenium.type("firstName", "Duke");
		selenium.type("lastName", "Invest");
		selenium.type("assignedIdentifier", "nci4r53");
		selenium.type("contactMechanisms[0].value", "Duke@inv.com");
		ajaxWidgets.clickNext("flow-next");
		
	}
	
	}
