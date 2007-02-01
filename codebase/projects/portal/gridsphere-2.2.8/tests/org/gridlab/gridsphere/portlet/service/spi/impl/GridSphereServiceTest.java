/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: GridSphereServiceTest.java,v 1.1.1.1 2007-02-01 20:51:16 kherm Exp $
 */
package org.gridlab.gridsphere.portlet.service.spi.impl;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.servlets.GridSphereServletTest;
import org.gridlab.gridsphere.servlets.GridSphereServletTest;

/**
 * Simple class to build a TestSuite out of the individual test classes.
 */
public class GridSphereServiceTest extends GridSphereServletTest {

    public GridSphereServiceTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.main(new String[] {GridSphereServiceTest.class.getName()});
    }

    public static Test suite() {
        return new TestSuite(GridSphereServiceTest.class);
    }

    protected void setUp() {
        super.setUp();
        super.testInitGridSphere();
    }

    public void testServiceFactoryCreate() {
        factory = SportletServiceFactory.getInstance();
        assertNotNull(factory);
    }
}

