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
		System.out.println(acLoadFailure);
		if (acLoadFailure == null && applicationContext == null) {
			try {
				SimpleNamingContextBuilder.emptyActivatedContextBuilder();
			} catch (NamingException e) {
				throw new RuntimeException("", e);
			}
	
			try {
				log
						.debug("Initializing test version of deployed application context");
				applicationContext = new ClassPathXmlApplicationContext(
						getConfigLocations());
	
				/*
				 * Resource resources[] = applicationContext.getResources("*");
				 * for(int i=0;i<resources.length;i++){
				 * System.out.println("\n "+
				 * i+": "+resources[i].getDescription()); }
				 * System.out.println("\n Printing classpath:\n"
				 * +getClasspathString());
				 */
			} catch (RuntimeException e) {
				acLoadFailure = e;
				throw e;
			}
		} else if (acLoadFailure != null) {
			throw new RuntimeException(
					"Application context loading already failed.  Will not retry.  "
							+ "Original cause attached.", acLoadFailure);
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
				//seleniumSpeed = seleniumProperties.getproperties.getProperty("selenium.speed");
				waitTime = seleniumProperties.getWaitTime();
				System.out.println(seleniumServerPort+"url:"+seleniumServerURL);
	
				// setUp("https://oracle.qa.semanticbits.com", "*chrome");
				// selenium = new DefaultSelenium("10.10.10.154", 4444, "*chrome",
				// "https://oracle.qa.semanticbits.com");
	
				selenium = new DefaultSelenium(seleniumServerURL,
						Integer.valueOf(seleniumServerPort), seleniumBrowser, c3prURL);
				selenium.start();
				 ajaxWidgets= new AjaxWidgets(selenium);
	//			selenium.setSpeed(seleniumSpeed);
				selenium.setTimeout(waitTime);
				selenium.setBrowserLogLevel("info");
			} catch (Exception e) {
				// TODO Auto-generated catch block
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