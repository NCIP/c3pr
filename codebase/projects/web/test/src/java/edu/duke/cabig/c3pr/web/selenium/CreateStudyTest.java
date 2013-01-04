/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.selenium;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

public class CreateStudyTest extends AbstractSeleniumTestCase {
	
	public void testCreateStudy() throws Exception {
		
		ajaxWidgets.login();
		selenium.open("/c3pr/pages/study/createStudy");
		
		c3prFixtures.populateStudyDetails("Create PhoneCall Study", "study-03-31");
		ajaxWidgets.clickNext("flow-next");
		
		c3prFixtures.populateStudyEpochAndArm();
		ajaxWidgets.clickNext("flow-next");
		
		c3prFixtures.populateStudyEligibility();
		ajaxWidgets.clickNext("flow-next");
		
		c3prFixtures.populateStudyStratification();
		ajaxWidgets.clickNext("flow-next");
		
		c3prFixtures.populateStudyRandomization();
		ajaxWidgets.clickNext("flow-next");
		
		c3prFixtures.populateStudyDiseases();
		ajaxWidgets.clickNext("flow-next");
		
		c3prFixtures.populateStudyCompanionStudies();
		ajaxWidgets.clickNext("flow-next");
		
		ajaxWidgets.clickNext("open");
		
		ajaxWidgets.clickNext("manageStudy");
		
		c3prFixtures.populateStudySites();
		
		c3prFixtures.populateStudyManageSites();
		
		c3prFixtures.populateStudyInvestigators();
		
		c3prFixtures.populateStudyNotifications();
		
		
		}
   
}
