/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialContextEditComp.java,v 1.1.1.1 2007-02-01 20:42:17 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.security.gss;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.security.gss.*;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.ietf.jgss.GSSCredential;

import java.util.Map;

public class CredentialContextEditComp extends CredentialContextComp {

    private transient static PortletLog log = SportletLog.getInstance(CredentialContextEditComp.class);

    protected TextBean dnText = null;
    protected TextFieldBean userNameField = null;
    protected TextFieldBean credentialNameField = null;
    protected TextFieldBean credentialLabelField = null;
    protected TextFieldBean lifetimeField = null;
    protected PasswordBean passphraseBean = null;

    public CredentialContextEditComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        // Create action beans
        dnText = createTextBean("dnText");
        userNameField = createTextFieldBean("userNameField");
        credentialNameField = createTextFieldBean("credentialNameField");
        credentialLabelField = createTextFieldBean("credentialLabelField");
        lifetimeField = createTextFieldBean("lifetimeField");
        passphraseBean = createPasswordBean("passphrase");
        // Set default action
        setDefaultAction("doCredentialNew");
        setDefaultJspPage("/jsp/security/gss/CredentialContextNewComp.jsp");
    }


    public void doCredentialNew(Map parameters) throws PortletException {
        // Display credential edit form
        setNextState(defaultJspPage);
        // Load credentialContext repository
        loadCredentialRepository();
        // Load credentialContext edit
        loadCredentialEdit(null, null);
    }

    public void doCredentialNewApply(Map parameters) throws PortletException {

        // Default to credential edit form
        setNextState(defaultJspPage);

        // Validate input
        String userName = userNameField.getValue();
        String credentialName = credentialNameField.getValue();
        String credentialLabel = credentialLabelField.getValue();
        String passphrase = passphraseBean.getValue();
        String slifetime = lifetimeField.getValue();

        StringBuffer errorMessage = new StringBuffer();
        if (userName == null || userName.equals("")) {
            errorMessage.append("Please provide a value for user name.");
        }
        if (credentialLabel == null || credentialLabel.equals("")) {
            errorMessage.append("\nPlease provide a value for credential label.");
        }
        if (passphrase == null || passphrase.equals("")) {
            errorMessage.append("\nPlease provide a passphrase.");
        }
        int lifetime = -1;
        if (slifetime != null && !slifetime.equals("")) {
            try {
                lifetime = Integer.parseInt(slifetime);
            } catch (Exception e) {
                lifetime = -1;
            }
        }

        // If error, display messages and return to edit form
        if (errorMessage.length() > 0) {
            messageBox.setMessageType(TextBean.MSG_ERROR);
            messageBox.setValue(errorMessage.toString());
        } else {

            try {
                // Create retrieval context
                User user = getUser();
                CredentialRetrievalContext retrievalContext
                        = credentialRetrievalService.createCredentialRetrievalContext(user);
                retrievalContext.setUserName(userName);
                retrievalContext.setCredentialName(credentialName);
                retrievalContext.setCredentialLifetime(lifetime);
                GSSCredential credential
                    = credentialRetrievalService.saveCredentialRetrievalContext(retrievalContext, passphrase);

                // Get credentialContext dn
                String dn = retrievalContext.getDn();

                // Create credentialContext context
                CredentialContext credentialContext
                        = credentialManagerService.createCredentialContext(user, dn);
                credentialContext.setLabel(credentialLabel);
                credentialManagerService.saveCredentialContext(credentialContext);

                // Activate the context
                credentialManagerService.activateCredentialContext(credential);

                // Attempt to map credentials to resources
//                CredentialMappingService credentialMapper = getCredentialMapper();
//                CredentialMappingSpec cmtSpec = (CredentialMappingSpec)
//                        credentialMapper.createTaskSpec(CredentialMappingTaskType.INSTANCE);
//                cmtSpec.setUser(user);
//                cmtSpec.setCredentialDn(dn);
//                cmtSpec.setTestAllResources(true);
//                credentialMapper.submitTask(cmtSpec);

                // Load credential context view
                parameters.put(CREDENTIAL_DN_PARAM, dn);
                parameters.put(CREDENTIAL_CONTEXT_PARAM, credentialContext);
                parameters.put(RETRIEVAL_CONTEXT_PARAM, retrievalContext);
                setNextState(CredentialContextViewComp.class, DEFAULT_ACTION, parameters);

            } catch (CredentialException e) {

                errorMessage.append(e.getMessage());
                messageBox.setMessageType(TextBean.MSG_ERROR);
                messageBox.setValue(errorMessage.toString());
            }
        }
    }

    public void doCredentialEdit(Map parameters) throws PortletException {

        // Default to credential edit form
        setNextState("/jsp/security/gss/CredentialContextEditComp.jsp");

        // Unload dn
        String dn = getCredentialDnParam(parameters);
        log.debug("Editing credential context with dn " + dn);

        // Load credentialContext repository
        loadCredentialRepository();

        // Credential context info
        CredentialContext credentialContext = credentialManagerService.getCredentialContextByDn(dn);

        // Retrieval context info
        CredentialRetrievalContext retrievalContext = credentialRetrievalService.getCredentialRetrievalContextByDn(dn);

        // Set dn text
        dnText.setValue(dn);

        // Load credential context edit
        loadCredentialEdit(credentialContext, retrievalContext);
    }

    public void doCredentialEditApply(Map parameters) throws PortletException {

        // Default to credential edit form
        setNextState("/jsp/security/gss/CredentialContextEditComp.jsp");

        String dn = getCredentialDnParam(parameters);
        log.debug("Applying edits to credential context with dn " + dn);

        // Validate input
        String userName = userNameField.getValue();
        String credentialName = credentialNameField.getValue();
        String credentialLabel = credentialLabelField.getValue();
        String passphrase = passphraseBean.getValue();
        String slifetime = lifetimeField.getValue();

        StringBuffer errorMessage = new StringBuffer();
        if (userName == null || userName.equals("")) {
            errorMessage.append(getResourceString(MESSAGE_USERNAME));
        }
        if (credentialLabel == null || credentialLabel.equals("")) {
            errorMessage.append('\n' + getResourceString(MESSAGE_LABEL));
        }
        if (passphrase == null || passphrase.equals("")) {
            errorMessage.append('\n' + getResourceString(MESSAGE_PASSPHRASE));
        }
        int lifetime = -1;
        if (slifetime != null && !slifetime.equals("")) {
            try {
                lifetime = Integer.parseInt(slifetime);
            } catch (Exception e) {
                lifetime = -1;
            }
        }

        // If error, display messages and return to edit form
        if (errorMessage.length() > 0) {
            messageBox.setMessageType(TextBean.MSG_ERROR);
            messageBox.setValue(errorMessage.toString());
        } else {

            try {

                // Apply changes to retrieval context
                CredentialRetrievalContext retrievalContext = credentialRetrievalService.getCredentialRetrievalContextByDn(dn);
                User user = getUser();
                if (retrievalContext == null) {
                    log.debug("Creating new retrieval context for dn " + dn);
                    // Create new context if one doesn't exist
                    retrievalContext = credentialRetrievalService.createCredentialRetrievalContext(user);
                }
                retrievalContext.setUserName(userName);
                retrievalContext.setCredentialName(credentialName);
                retrievalContext.setCredentialLifetime(lifetime);
                GSSCredential credential
                    = credentialRetrievalService.saveCredentialRetrievalContext(retrievalContext, passphrase);

                // Apply changes to credentialContext context
                CredentialContext credentialContext = credentialManagerService.getCredentialContextByDn(dn);
                if (credentialContext == null) {
                    log.debug("Creating new credentialContext context for dn " + dn);
                    credentialContext = credentialManagerService.createCredentialContext(user, dn);
                }
                credentialContext.setLabel(credentialLabel);
                credentialManagerService.saveCredentialContext(credentialContext);

                // Re-activate credentialContext context
                credentialManagerService.activateCredentialContext(credential);

                // Load credential context view
                parameters.put(CREDENTIAL_DN_PARAM, dn);
                parameters.put(CREDENTIAL_CONTEXT_PARAM, credentialContext);
                parameters.put(RETRIEVAL_CONTEXT_PARAM, retrievalContext);
                setNextState(CredentialContextViewComp.class, DEFAULT_ACTION, parameters);

            } catch (CredentialException e) {

                errorMessage.append(e.getMessage());
                messageBox.setMessageType(TextBean.MSG_ERROR);
                messageBox.setValue(errorMessage.toString());
            }
        }
    }

    public void doCancel(Map parameters) throws PortletException {
        // Unload dn
        String dn = getCredentialDnParam(parameters);
        if (dn == null || dn.equals("")) {
            setNextState(CredentialContextListViewComp.class, DEFAULT_ACTION, parameters);
        } else {
            setNextState(CredentialContextViewComp.class, DEFAULT_ACTION, parameters);
        }
    }

    private void loadCredentialEdit(CredentialContext credentialContext, CredentialRetrievalContext retrievalContext) {
        // Retrieval context
        if (retrievalContext == null) {
            // User name
            User user = getUser();
            userNameField.setValue(user.getUserName());
            // Lifetime
            int lifetime = credentialRetrievalService.getDefaultCredentialLifetime();
            lifetimeField.setValue(String.valueOf(lifetime));
        } else {
            // User name
            userNameField.setValue(retrievalContext.getUserName());
            // Credential name
            credentialNameField.setValue(retrievalContext.getCredentialName());
            // Lifetime
            int lifetime = retrievalContext.getCredentialLifetime();
            if (lifetime <= 0) lifetime = credentialRetrievalService.getDefaultCredentialLifetime();
            lifetimeField.setValue(String.valueOf(lifetime));
        }
        // Credential context
        if (credentialContext != null) {
            // Credential label
            credentialLabelField.setValue(credentialContext.getLabel());
        }
    }
}
