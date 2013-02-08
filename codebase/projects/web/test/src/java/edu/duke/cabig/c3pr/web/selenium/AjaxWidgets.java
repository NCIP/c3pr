/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.selenium;

import junit.framework.*;
import com.thoughtworks.selenium.*;

public class AjaxWidgets extends TestCase {
	Selenium selenium;

	public AjaxWidgets(Selenium selenium) {
		super();
		this.selenium = selenium;
	}

	public void clickNext(String nextButton) {
		selenium.click(nextButton);
		selenium.waitForPageToLoad("30000");
	}

	public void confirmOK(String confirmMessage) throws InterruptedException {
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isConfirmationPresent())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		String s = selenium.getConfirmation();
		// System.out.println(s+" confirmMessage: "+confirmMessage);
		assertTrue(s.matches(confirmMessage));
	}

	public void login() throws Exception {
		selenium.open("/c3pr/public/login");
		selenium.type("j_username", "c3pr_admin");
		selenium.type("j_password", "c3pr_admin");
		selenium.click("power_btn");
		selenium.waitForPageToLoad("30000");

	}

	public void typeAutosuggest(String element, String text, String elemPresent)
			throws InterruptedException {
		selenium.click(element);
		selenium.typeKeys(element, "");
		selenium.typeKeys(element, text);
		waitForElementPresent("//div[@id='" + elemPresent + "']/ul/li");

		selenium.click("//div[@id='" + elemPresent + "']/ul/li[1]");
		selenium.click(element);
	}

	public void waitForElementPresent(String xPathElement)
			throws InterruptedException {
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("Timed out waiting for element to be created: "
						+ xPathElement);
			try {
				if (selenium.isElementPresent(xPathElement))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);

		}
	}

	public void waitForElementNotPresent(String xPathElement)
			throws InterruptedException {
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("Timed out waiting for element to be removed: "
						+ xPathElement);
			try {
				if (!selenium.isElementPresent(xPathElement))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
	}

	public void addPanel(String clickableElement, String waitForElementPresent) throws InterruptedException {
		selenium.click(clickableElement);
		waitForElementPresent(waitForElementPresent);
	}
	
	public void removePanel(String clickableElement, String waitForElementNotPresent, String confirmMessage) throws InterruptedException{
	//Set confirmMessage to null if no confirmation dialog will appear
	selenium.click(clickableElement);
	if(confirmMessage!=null)
		confirmOK(confirmMessage);
	waitForElementNotPresent(waitForElementNotPresent);
	}
}
