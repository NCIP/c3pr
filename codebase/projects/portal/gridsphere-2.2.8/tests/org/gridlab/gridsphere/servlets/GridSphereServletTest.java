/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @version $Id: GridSphereServletTest.java,v 1.1.1.1 2007-02-01 20:51:17 kherm Exp $
*/
package org.gridlab.gridsphere.servlets;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import org.gridlab.gridsphere.portlet.PortletContext;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;

/**
 * This is the base fixture for service testing. Provides a service factory and the
 * properties file.
 */
public class GridSphereServletTest extends ServletTestCase {

    protected static SportletServiceFactory factory = null;
    protected static PortletLog log = SportletLog.getInstance(GridSphereServletTest.class);
    protected PortletContext context = null;
    protected static GridSphereServlet gsServlet = null;
    private static boolean bInited = false;

    public GridSphereServletTest(String name) {
        super(name);
    }

    protected void setUp() {
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public void testInitGridSphere() {
        if (!bInited ){
            gsServlet = new GridSphereServlet();
            assertNotNull(gsServlet);
            try {
                gsServlet.init(config);
            } catch (Exception e) {
                fail("Unable to perform init() of GridSphereServlet!");
            }
            try {
                factory = SportletServiceFactory.getInstance();
                factory.init();
            } catch (Exception e) {
                fail("Unable to init the SportletServicefactory");
            }
            try {
                gsServlet.initializeServices();
            } catch (Exception e) {
                fail("Unable to initialize GridSphere Portlet services!");
            }

            bInited = true;
        }
    }

    public static Test suite() {
        return new TestSuite(GridSphereServletTest.class);
    }

    protected void tearDown() {
    }
}
