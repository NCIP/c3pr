/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.selenium;

import com.thoughtworks.selenium.*;

import java.util.regex.Pattern;

public class C3PRFlowFixtures extends SeleneseTestCase{
	Selenium selenium;
	AjaxWidgets aw;
	public C3PRFlowFixtures(Selenium selenium, AjaxWidgets aw) {
		super();
		this.selenium = selenium;
		this.aw=aw;
	}
	public void populateStudyDetails(String ShortTitle,String CoCenterID) throws InterruptedException{
		selenium.type("_shortTitle", ShortTitle);
		selenium.type("study.longTitleText", "test");
		selenium.type("study.targetAccrualNumber", "100");
		selenium.select("study.type", "label=Genetic Therapeutic");
		selenium.select("study.phaseCode", "label=Phase II Trial");
		selenium.select("study.multiInstitutionIndicator", "label=Yes");
		selenium.type("study.consentVersion", "1");
		selenium.select("study.stratificationIndicator", "label=Yes");
		selenium.select("study.randomizedIndicator", "label=Yes");
		selenium.click("//select[@id='study.randomizedIndicator']/option[3]");
		selenium.select("randomizationType", "label=Phone Call");
		aw.typeAutosuggest("coCenter-input", "duke", "coCenter-choices");
		selenium.type("study.organizationAssignedIdentifiers[0].value", CoCenterID);
		aw.typeAutosuggest("healthcareSite-input", "national cancer inst", "healthcareSite-choices");		
		aw.typeAutosuggest("piCoCenter-input", "duke", "piCoCenter-choices");
		aw.typeAutosuggest("hcsInvestigator-input", "duke", "hcsInvestigator-choices");		
		
	}
	public void populateStudyEpochAndArm() throws InterruptedException{
		selenium.click("//button[@type='button']");
		selenium.type("study.epochs[0].name", "Enrolling");
		selenium.type("study.epochs[0].epochOrder", "1");
		selenium.select("study.epochs[0].type", "label=TREATMENT");
		selenium.select("study.epochs[0].stratificationIndicator", "label=No");
		selenium.click("addArm-0");
		selenium.click("//button[@type='button' and @onclick=\"$('dummy-genericEpoch').innerHTML=$('genericHtml').innerHTML;RowManager.addRow(genericEpochRowInserterProps)\"]");
		selenium.type("study.epochs[1].name", "Randomized");
		selenium.type("study.epochs[1].epochOrder", "3");
		selenium.select("study.epochs[0].type", "label=TREATMENT");
		//selenium.click("addArm-1");
		//selenium.click("addArm-1");
		aw.addPanel("addArm-1", "//input[@name='study.epochs[1].arms[0].name']");
		aw.addPanel("addArm-1", "//input[@name='study.epochs[1].arms[1].name']");
		selenium.click("study.epochs[1].arms[1].name");
		selenium.type("study.epochs[1].arms[1].name", "Arm B");
	}
	public void populateStudyEligibility() throws InterruptedException{
		
	}
	public void populateStudyStratification() throws InterruptedException{
		/*selenium.click("//input[@value='Add Stratification Factor']");
		selenium.type("study.epochs[1].stratificationCriteria[0].questionText", "strat 1");
		selenium.type("study.epochs[1].stratificationCriteria[0].permissibleAnswers[0].permissibleAnswer", "r1");
		selenium.type("study.epochs[1].stratificationCriteria[0].permissibleAnswers[1].permissibleAnswer", "r2");
		aw.addPanel("//input[@value='Generate Stratum Groups']", "sgCombinationsTable_1");*/
		
		selenium.click("//button[@type='button']");
		selenium.type("study.epochs[1].stratificationCriteria[0].questionText", "strat 1");
		selenium.type("study.epochs[1].stratificationCriteria[0].permissibleAnswers[0].permissibleAnswer", "r1");
		selenium.type("study.epochs[1].stratificationCriteria[0].permissibleAnswers[1].permissibleAnswer", "r2");
		//selenium.click("//button[@type='submit']");
		//selenium.waitForPageToLoad("30000");
		aw.addPanel("//button[@type='submit']", "sgCombinationsTable_1");
		
	}
	public void populateStudyRandomization() throws InterruptedException{
		selenium.type("study.epochs[1].randomization.phoneNumber", "1122334455");
	}
	public void populateStudyDiseases() throws InterruptedException{
		aw.typeAutosuggest("disease-input", "bone", "disease-choices");
		Thread.sleep(4000);
		aw.addPanel("//button[@type='button']", "//input[@id='study.studyDiseases[0].leadDisease1']");
	}
	public void populateStudyCompanionStudies() throws InterruptedException{
	
	}
	public void populateStudySites() throws InterruptedException{
		aw.clickNext("link=2. Study Sites");
		aw.addPanel("addSite", "//input[@id='healthcareSite0-input']");
		aw.typeAutosuggest("healthcareSite0-input", "duke","healthcareSite0-choices");
		selenium.type("studySites[0].startDate", "12/17/2008");
		selenium.type("studySites[0].irbApprovalDate", "12/17/2008");
		selenium.type("studySites[0].targetAccrualNumber", "11");
		//aw.clickNext("saveAdvanceConfig");
		aw.addPanel("addSite", "//input[@id='healthcareSite1-input']");
		aw.typeAutosuggest("healthcareSite1-input", "wake medical center","healthcareSite1-choices");
		selenium.type("studySites[1].startDate", "12/17/2008");
		selenium.type("studySites[1].irbApprovalDate", "12/17/2008");
		selenium.type("studySites[1].targetAccrualNumber", "11");
		aw.clickNext("flow-update");
	}
   public void populateStudyManageSites() throws InterruptedException{
	   aw.clickNext("link=3. Manage Sites");
		selenium.select("siteAction-NC010", "label=Activate Study Site");
		selenium.click("go");
		Thread.sleep(4000);
		selenium.select("siteAction-NC008", "label=Activate Study Site");
		selenium.click("go");
		Thread.sleep(4000);

	}
   public void populateStudyIdentifiers() throws InterruptedException{
		
	}
   public void populateStudyInvestigators() throws InterruptedException{
	   aw.clickNext("link=5. Study Investigators");
		selenium.select("site", "label=Duke University Medical Center (Site)");
		selenium.waitForPageToLoad("30000");
		//Thread.sleep(4000);
		aw.addPanel("add", "//select[@id='study.studyOrganizations[2].studyInvestigators[0].statusCode']");
		//aw.clickNext("//input[@value='Add >>']");
		selenium.select("site", "label=Wake Medical Center (Site)");
		selenium.waitForPageToLoad("30000");
		//Thread.sleep(4000);
		aw.addPanel("add", "//select[@id='study.studyOrganizations[3].studyInvestigators[0].statusCode']");
		//aw.clickNext("//input[@value='Add >>']");
		aw.clickNext("flow-update");
	}
   public void populateStudyPersonnel() throws InterruptedException{
		
	}
   public void populateStudyNotifications() throws InterruptedException{
	    aw.clickNext("link=7. Study Notifications");
	    aw.addPanel("addNotification", "//tr[@id='notification-0']");
		selenium.type("study.plannedNotifications[0].studyThreshold", "1");
		aw.addPanel("addEmail", "//tr[@id='table1-0']");
		selenium.click("study.plannedNotifications[0].contactMechanismBasedRecipient[0].contactMechanisms[0].value");
		selenium.type("study.plannedNotifications[0].contactMechanismBasedRecipient[0].contactMechanisms[0].value", "shilpa.alluru@semanticbits.com");
		aw.clickNext("flow-update");
	}
   public void populateSubjectDetails(String firstName,String lastName,String subjectIdentifier) throws InterruptedException{
	   selenium.type("firstName", firstName);
		selenium.type("lastName", lastName);
		selenium.select("administrativeGenderCode", "label=Male");
		selenium.type("birthDate", "10/10/1876");
		selenium.select("ethnicGroupCode", "label=Not Reported");
		selenium.click("raceCodes6");
		aw.typeAutosuggest("mrnOrganization-input", "NC010", "mrnOrganization-choices");
		selenium.type("organizationAssignedIdentifiers[0].value", subjectIdentifier);

   }


}
