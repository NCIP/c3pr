/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SetupRootUserTest.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridlab.gridsphere.services.core.user;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.servlets.GridSphereServletTest;

public class SetupRootUserTest extends GridSphereServletTest {

    protected User rootUser = null;

    public SetupRootUserTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(SetupRootUserTest.class);
    }

    protected void setUp() {
       super.testInitGridSphere();
    }

    public void testLoginRootUser() throws PortletServiceException {

        // create a root user if none available
        UserManagerService userManagerService = (UserManagerService)factory.createPortletService(UserManagerService.class, context, true);
        LoginService loginService = (LoginService) factory.createPortletService(LoginService.class, context, true);

    }

    protected void tearDown() {

    }

}
