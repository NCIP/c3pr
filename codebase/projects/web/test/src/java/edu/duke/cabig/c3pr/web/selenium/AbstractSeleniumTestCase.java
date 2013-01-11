/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.selenium;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.apache.tools.ant.AntClassLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

public class AbstractSeleniumTestCase extends SeleneseTestCase {

	protected AjaxWidgets ajaxWidgets;
	protected C3PRFlowFixtures c3prFixtures;
	private static Logger log = Logger.getLogger(CreateSubjectTest.class);
	private static RuntimeException acLoadFailure = null;
	private static ApplicationContext applicationContext = null;
	String seleniumServerURL = null;
	String seleniumServerPort = null;
	String seleniumBrowser = null;
	String c3prURL = null;
	String seleniumSpeed = null;
	String seleniumRulesDir = null;
	static String waitTime = null;
	private SeleniumProperties seleniumProperties;

	protected static ApplicationContext getDeployedApplicationContext() throws IOException {
		if (acLoadFailure == null && applicationContext == null) {
			try {
				SimpleNamingContextBuilder.emptyActivatedContextBuilder();
			} catch (NamingException e) {
				throw new RuntimeException("", e);
			}
	
			try {
				log.debug("Initializing test version of deployed application context");
				applicationContext = new ClassPathXmlApplicationContext(getConfigLocations());
	
			} catch (RuntimeException e) {
				acLoadFailure = e;
				throw e;
			}
		} else if (acLoadFailure != null) {
			throw new RuntimeException("Application context loading already failed.  Will not retry. Original cause attached.", acLoadFailure);
		}
		return applicationContext;
	}

	public static final String[] getConfigLocations() {
		return new String[] { "classpath*:edu/duke/cabig/c3pr/web/selenium/applicationContext-selenium.xml","classpath*:edu/duke/cabig/c3pr/applicationContext-config.xml" };
	}

	public void setUp() throws Exception {
			try {
	
				seleniumProperties=(SeleniumProperties)getDeployedApplicationContext().getBean("seleniumProperties");
				seleniumServerURL = seleniumProperties.getServerHost();
				seleniumServerPort = seleniumProperties.getServerPort();
				seleniumBrowser =seleniumProperties.getBrowser();
				c3prURL = seleniumProperties.getBaseUrl();
				waitTime = seleniumProperties.getWaitTime();
				selenium = new DefaultSelenium(seleniumServerURL,Integer.parseInt(seleniumServerPort), seleniumBrowser, c3prURL);
				selenium.start();
				 ajaxWidgets= new AjaxWidgets(selenium);
				selenium.setTimeout(waitTime);
				selenium.setBrowserLogLevel("info");
			} catch (Exception e) {
				e.printStackTrace();
				fail(" failed in setUp method");
			}
	
			c3prFixtures = new C3PRFlowFixtures(selenium, ajaxWidgets);
	
		}

	public AbstractSeleniumTestCase() {
		super();
	}

	public AbstractSeleniumTestCase(String name) {
		super(name);
	}

	public String getClasspathString() {
		StringBuffer classpath = new StringBuffer();
		ClassLoader applicationClassLoader = this.getClass().getClassLoader();
	
		if (applicationClassLoader == null) {
			applicationClassLoader = ClassLoader.getSystemClassLoader();
		}
		if (applicationClassLoader instanceof URLClassLoader) {
			URL[] urls = ((URLClassLoader) applicationClassLoader).getURLs();
			for (int i = 0; i < urls.length; i++) {
	
				classpath.append(urls[i].getFile()).append("\r\n");
			}
			return classpath.toString();
		} else {
			return ((AntClassLoader) applicationClassLoader).getClasspath();
		}
	
	}

}
