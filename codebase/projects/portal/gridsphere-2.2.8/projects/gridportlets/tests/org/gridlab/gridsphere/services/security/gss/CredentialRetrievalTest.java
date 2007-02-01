/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: CredentialRetrievalTest.java,v 1.1.1.1 2007-02-01 20:42:32 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.services.core.user.SetupRootUserTest;

public class CredentialRetrievalTest extends SetupRootUserTest {


    protected CredentialRetrievalService credService = null;

    public CredentialRetrievalTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(CredentialRetrievalTest.class);
    }

    protected void setUp() {
        super.setUp();
        try {
            super.testLoginRootUser();
            credService = (CredentialRetrievalService) factory.createUserPortletService(CredentialRetrievalService.class, rootUser, config.getServletContext(), true);
        } catch (PortletServiceException e) {
            fail("Unable to initialize cred retrieval  services");
        }
    }

    public void testRetrieveCredential() {

    }

    protected void tearDown() {

    }

}
