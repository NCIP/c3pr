package edu.duke.cabig.c3pr.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * These tests are intended to verify that the various application contexts will
 * all load when the application is deployed.
 * @author Priyatam
 * @testType unit
 */
public class ApplicationContextInitializationTest extends ApplicationTestCase {

    private MockServletContext servletContext;

    protected void setUp() throws Exception {
        super.setUp();
        servletContext = new MockServletContext("web", new FileSystemResourceLoader());
    }
    
    /**
     * Test for checking  Application Context file configurations
     * @throws Exception
     */
    public void testApplicationContextItself() throws Exception {
        getDeployedApplicationContext();
        // no exceptions
    }

    /**
     * This servlet's configuration can't be loaded in the test environment because of
     * CSM's excessive startup demands.
     */
//    public void testC3PRServletContext() throws Exception {
//         assertDispatcherServletConfigLoads("c3pr");
//    }

    private void assertDispatcherServletConfigLoads(String servletName) {
        try {
            createWebApplicationContextForServlet(servletName);
        } catch (Exception e) {
            fail("Loading the configuration for MVC servlet '" + servletName + "' failed:  " + e.getMessage());
        }
    }

    private XmlWebApplicationContext createWebApplicationContextForServlet(String servletName) {
        ApplicationContext parent = getDeployedApplicationContext();
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setParent(parent);
        context.setServletContext(servletContext);
        context.setConfigLocations(new String[] { "WEB-INF/" + servletName + "-servlet.xml" });
        context.refresh();
        return context;
    }
}
