/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.selenium;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

public class CreateRegTest extends AbstractSeleniumTestCase {
	
	public void testCreateReg() throws Exception {
		
		ajaxWidgets.login();
		
		
		selenium.open("/c3pr/pages/registration/createRegistration");
		selenium.waitForPageToLoad("30000");
		selenium.type("searchText", "study-03-24-08");
		selenium.click("//button[@type='button'  and @onclick='searchStudy();']");
		Thread.sleep(4000);
		selenium.click("//tr[@id='row0']/td[3]");
		selenium.click("//tr[@id='row100']/td[1]");
		Thread.sleep(4000);
		selenium.click("//div/div/div/table/tbody/tr/td/table/tbody/tr[1]/td[1]");
		
		selenium.type("firstName", "Richard");
		selenium.type("lastName", "Wilson");
		selenium.select("administrativeGenderCode", "label=Unknown");
		selenium.type("birthDate", "11/11/1898");
		selenium.select("ethnicGroupCode", "label=Unknown");
		selenium.click("raceCodes7");
		selenium.click("mrnOrganization-input");
		selenium.typeKeys("mrnOrganization-input", "duke");
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (selenium.isElementPresent("//div[@id='mrnOrganization-choices']/ul/li[1]")) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

		selenium.click("//div[@id='mrnOrganization-choices']/ul/li[1]");
		selenium.click("mrnOrganization-input");
		selenium.type("organizationAssignedIdentifiers[0].value", "mrn-03-07");
		selenium.click("//button[@type='button' and @onclick='document.createSubForm.submit();']");
		Thread.sleep(4000);
		
		
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.type("studySubject.informedConsentSignedDate", "01/01/2009");
		selenium.click("studySubject.currentVersionIndicator");
		/*
		 selenium.select("studyDiseaseSelect", "label=Ewing sarcoma/Peripheral PNET");
		selenium.type("diseaseSite-input", "skin");
		selenium.click("//div[@id='diseaseSite-choices']/ul/li");
		selenium.select("paymentMethod", "label=Self Pay (No Insurance)");
		selenium.select("treatingPhysician", "label=Duke Investigator");
	
		 */
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.select("studySubject.scheduledEpoch.scheduledArms[0].arm", "label=Arm A");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		//Thread.sleep(4000);
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		
		
		//transfer
		
		selenium.click("link=Manage this registration");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Change Epoch");
		selenium.click("//input[@id='flow' and @value='126']");
		selenium.click("//button[@type='button' and @onclick='transfer();']");
		Thread.sleep(4000);
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		Thread.sleep(4000);
		selenium.select("studySubject.scheduledEpoch.subjectStratificationAnswers[0].stratificationCriterionAnswer", "label=r2");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.select("studySubject.scheduledEpoch.scheduledArms[0].arm", "label=Arm A");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
	}
	
	public void testCreateRegWithCompanion() throws Exception {
		ajaxWidgets.login();
		
		selenium.open("/c3pr/pages/registration/createRegistration");
		selenium.waitForPageToLoad("30000");
		
		selenium.type("searchText", "duke#ee4e");
		selenium.click("//button[@type='button'  and @onclick='searchStudy();']");
		Thread.sleep(4000);
		selenium.click("//tr[@id='row0']/td[3]");
		selenium.click("//tr[@id='row100']/td[1]");
		Thread.sleep(4000);
		selenium.click("//div/div/div/table/tbody/tr/td/table/tbody/tr[1]/td[1]");
		
		selenium.type("firstName", "Richard");
		selenium.type("lastName", "Wilson");
		selenium.select("administrativeGenderCode", "label=Unknown");
		selenium.type("birthDate", "11/11/1898");
		selenium.select("ethnicGroupCode", "label=Unknown");
		selenium.click("raceCodes7");
		selenium.click("mrnOrganization-input");
		selenium.typeKeys("mrnOrganization-input", "duke");
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (selenium.isElementPresent("//div[@id='mrnOrganization-choices']/ul/li[1]")) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

		selenium.click("//div[@id='mrnOrganization-choices']/ul/li[1]");
		selenium.click("mrnOrganization-input");
		selenium.type("organizationAssignedIdentifiers[0].value", "mrn-03-16");
		selenium.click("//button[@type='button' and @onclick='document.createSubForm.submit();']");
		Thread.sleep(4000);
		
		
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.type("studySubject.informedConsentSignedDate", "01/01/2009");
		selenium.click("studySubject.currentVersionIndicator");
		selenium.select("treatingPhysician", "label=Duke Investigator");
		/*
		 selenium.select("studyDiseaseSelect", "label=Ewing sarcoma/Peripheral PNET");
		selenium.type("diseaseSite-input", "skin");
		selenium.click("//div[@id='diseaseSite-choices']/ul/li");
		selenium.select("paymentMethod", "label=Self Pay (No Insurance)");
		
	
		 */
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.select("studySubject.scheduledEpoch.scheduledArms[0].arm", "label=Arm A");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		//Thread.sleep(4000);

		//compANION REG
		selenium.click("registerCompanionStudy");
		Thread.sleep(4000);
		selenium.selectFrame("relative=up");
		selenium.click("//input[@value='233']");
		//selenium.selectFrame("index=1");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.click("studySubject.currentVersionIndicator");
		selenium.select("studyDiseaseSelect", "label=Diffuse brainstem glioma");
		selenium.type("diseaseSite-input", "skin");
		selenium.click("//div[@id='diseaseSite-choices']/ul/li");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.select("scheduledEpoch.subjectEligibilityAnswers[0].answerText", "label=Yes");
		selenium.select("scheduledEpoch.subjectEligibilityAnswers[1].answerText", "label=No");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.select("studySubject.scheduledEpoch.scheduledArms[0].arm", "label=Arm A");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.click("flow-next");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		
		
		
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		selenium.click("flow-next");
		selenium.waitForPageToLoad("30000");
		
	
	}

}
