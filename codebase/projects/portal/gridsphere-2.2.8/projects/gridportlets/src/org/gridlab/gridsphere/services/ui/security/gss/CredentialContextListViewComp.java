/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialContextListViewComp.java,v 1.1.1.1 2007-02-01 20:42:17 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.security.gss;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.security.gss.*;

import javax.portlet.PortletException;

import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.ietf.jgss.GSSCredential;

import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class CredentialContextListViewComp extends CredentialContextComp {

    private transient static PortletLog log = SportletLog.getInstance(CredentialContextListViewComp.class);

    protected CheckBoxBean credentialDnCheckBox = null;
    protected PasswordBean passphraseBean = null;

    public CredentialContextListViewComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        passphraseBean = createPasswordBean("passphrase");
        setDefaultAction("doCredentialList");
        setRenderAction("doCredentialList");
        setDefaultJspPage("/jsp/security/gss/CredentialContextListViewComp.jsp");
    }

    public void onInit()
            throws PortletException {
        super.onInit();
        // Create new oid check box
        credentialDnCheckBox = createCheckBoxBean("credentialDnCheckBox");
        // Register action links
        container.registerActionLink(this, "doCredentialView", CredentialContextViewComp.class);
        container.registerActionLink(this, "doCredentialNew", CredentialContextEditComp.class);
    }

    public void onStore() throws PortletException {
        // Get credential context list
        List credentialContextList = credentialManagerService.getCredentialContexts(getUser());
        setPageAttribute("credentialContextList", credentialContextList);
    }

    public void doCredentialList(Map parameters) throws PortletException {
        // Load credential repository
        loadCredentialRepository();
        // Set next state
        setNextState(defaultJspPage);
    }

    public void doCredentialListRetrieve(Map parameters) throws PortletException {
        // Set next page
        setNextState(defaultJspPage);
        // Load credential repository
        loadCredentialRepository();
        // Retrieve credentials  for which there exist retrieval contexts
        User user = getUser();
        List credentialContextList = credentialManagerService.getCredentialContexts(user);
        StringBuffer errorMessage = new StringBuffer();
        String passphrase = passphraseBean.getValue();
        // Make sure passphrase was provided
        if (passphrase == null || passphrase.equals("")) {
            errorMessage.append(getResourceString(MESSAGE_PASSPHRASE));
        } else {

            for (Iterator credentialContexts = credentialContextList.iterator(); credentialContexts.hasNext();) {
                CredentialContext credentialContext = (CredentialContext) credentialContexts.next();
                String dn = credentialContext.getDn();

                CredentialRetrievalContext retrievalContext = credentialRetrievalService.getCredentialRetrievalContextByDn(dn);
                if (retrievalContext != null) {
                    GSSCredential credential = null;
                    try {
                        credential = credentialRetrievalService.retrieveCredential(retrievalContext, passphrase);
                        credentialManagerService.activateCredentialContext(credential);
                    } catch (CredentialException e) {
                        errorMessage.append(getResourceString(MESSAGE_CANT_RETRIEVE) + " ["
                                          + e.getMessage() + "]");
                    }
                } else {
                    errorMessage.append(getResourceString(MESSAGE_RETRIEVE_CXT) + " ["
                                      + credentialContext.getLabel() + "]");
                }
            }
        }

        // If errors, display messages
        if (errorMessage.length() > 0) {
            messageBox.setMessageType(TextBean.MSG_ERROR);
            messageBox.setValue(errorMessage.toString());
        }
        // Set credentialContext context list
        setPageAttribute("credentialContextList", credentialContextList);
    }

    public void doCredentialDeactivate(Map parameters) throws PortletException {

        // Load credential repository
        loadCredentialRepository();

        // Unlooad dn
        String dn = getCredentialDnParam(parameters);
        log.debug("Retrieving credential  with dn " + dn);

        CredentialContext credentialContext = credentialManagerService.getCredentialContextByDn(dn);
        if (credentialContext == null) {
            messageBox.setValue(getResourceString(MESSAGE_CANT_DEACTIVATE_CRED));
            messageBox.setMessageType(TextBean.MSG_ERROR);

        } else {
            credentialContext.deactivate();
            messageBox.setValue(getResourceString(MESSAGE_DEACTIVATE_CRED));
            messageBox.setMessageType(TextBean.MSG_ERROR);
        }

        // Set next state
        setNextState(defaultJspPage);
    }
}
