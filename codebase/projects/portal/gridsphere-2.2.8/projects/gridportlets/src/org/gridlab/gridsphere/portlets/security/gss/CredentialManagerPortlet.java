/**
 * @author <a href="mailto:rmichael.ussell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialManagerPortlet.java,v 1.1.1.1 2007-02-01 20:39:47 kherm Exp $
 */
package org.gridlab.gridsphere.portlets.security.gss;

import org.gridlab.gridsphere.portlets.ActionComponentPortlet;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.security.gss.CredentialContextListViewComp;

import javax.portlet.PortletException;
import javax.portlet.PortletConfig;

public class CredentialManagerPortlet extends ActionComponentPortlet {

    private static PortletLog log = SportletLog.getInstance(CredentialManagerPortlet.class);

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        setViewModeComp(CredentialContextListViewComp.class);
    }
}
