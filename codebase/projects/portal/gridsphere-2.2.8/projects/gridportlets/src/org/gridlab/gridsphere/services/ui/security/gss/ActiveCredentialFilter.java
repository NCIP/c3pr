/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActiveCredentialFilter.java,v 1.1.1.1 2007-02-01 20:42:16 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.security.gss;

import org.gridlab.gridsphere.services.ui.ActionFilter;
import org.gridlab.gridsphere.services.ui.ActionFilterException;
import org.gridlab.gridsphere.services.ui.ActionComponent;
import org.gridlab.gridsphere.services.security.gss.CredentialManagerService;
import org.gridlab.gridsphere.services.util.GridPortletsResourceBundle;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.MessageBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;
import java.util.Locale;

/**
 * Throws an action filter exception if the user invoking the action does not have active credentials.
 */
public class ActiveCredentialFilter implements ActionFilter {

    public static final String MESSAGE_CRED_ERR = "portlets.security.gss.message_action_filter_error";
    public static final String MESSAGE_CRED_REQ = "portlets.security.gss.message_credential_required";

    private transient static PortletLog log = SportletLog.getInstance(ActionComponent.class);

    private static ActiveCredentialFilter instance = null;

    public static ActiveCredentialFilter getInstance() {
        synchronized(MESSAGE_CRED_ERR) {
            if (instance == null) {
                instance = new ActiveCredentialFilter();
            }
        }
        return instance;
    }

    public void filter(ActionComponent component, String method, Map parameters)
            throws ActionFilterException {
        int numCredentials = 0;
        try {
            CredentialManagerService credentialManagerService =
                    (CredentialManagerService)component.getPortletService(CredentialManagerService.class);
            numCredentials = (credentialManagerService.getActiveCredentials(component.getUser()).size());
        } catch(Exception e) {
            log.error("Unable to get credential manager service", e);
            Locale locale = component.getLocale();
            MessageBoxBean messageBox = component.getMessageBox();
            messageBox.setValue(GridPortletsResourceBundle.getResourceString(locale, MESSAGE_CRED_ERR));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            throw new ActionFilterException();
        }
        if (numCredentials == 0) {
            Locale locale = component.getLocale();
            MessageBoxBean messageBox = component.getMessageBox();
            messageBox.setValue(GridPortletsResourceBundle.getResourceString(locale, MESSAGE_CRED_REQ));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            throw new ActionFilterException();
        }
    }
}
