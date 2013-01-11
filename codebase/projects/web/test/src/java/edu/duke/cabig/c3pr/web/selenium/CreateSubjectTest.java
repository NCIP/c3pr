/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.selenium;

import java.util.regex.Pattern;

import org.springframework.beans.factory.config.PropertiesFactoryBean;

import java.util.Properties;



import com.thoughtworks.selenium.SeleneseTestCase;
import org.springframework.core.io.*;

public class CreateSubjectTest extends AbstractSeleniumTestCase {

	public void testCreateSubject() throws Exception {

		ajaxWidgets.login();

		selenium
				.open("/c3pr/pages/personAndOrganization/participant/createParticipant");
		c3prFixtures.populateSubjectDetails("John", "Smith", "132452461891GYAY");
		ajaxWidgets.clickNext("flow-next");
		ajaxWidgets.clickNext("flow-next");
//		ajaxWidgets.clickNext("createSubject");
//		assertTrue(selenium.isTextPresent("Subject successfully created."));
	}

}
