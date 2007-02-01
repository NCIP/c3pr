/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialContextViewComp.java,v 1.1.1.1 2007-02-01 20:42:18 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.security.gss;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.security.gss.*;
import org.gridlab.gridsphere.services.core.utils.DateUtil;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.PasswordBean;
import org.ietf.jgss.GSSCredential;

import java.util.Map;
import java.util.Locale;
import java.util.Date;
import java.text.DateFormat;

public class CredentialContextViewComp extends CredentialContextComp {

    private transient static PortletLog log = SportletLog.getInstance(CredentialContextViewComp.class);

    protected CredentialMappingService credentialMappingService = null;

    protected TextBean dnText = null;
    protected TextBean credentialStatusText = null;
    protected TextBean remainingLifetimeText = null;
    protected TextBean dateCreatedText = null;
    protected TextBean dateLastRetrievedText = null;
    protected PasswordBean passphraseBean = null;
    protected TextBean userNameText = null;
    protected TextBean credentialNameText = null;
    protected TextBean lifetimeText = null;
    protected TextBean credentialLabelText = null;

    public CredentialContextViewComp(ActionComponentFrame container, String compName) {
        super(container, compName);

        // Create action beans
        dnText = createTextBean("dnText");

        userNameText = createTextBean("userNameText");
        credentialNameText = createTextBean("credentialNameText");
        credentialLabelText = createTextBean("credentialLabelText");
        lifetimeText = createTextBean("lifetimeText");

        credentialStatusText = createTextBean("credentialStatusText");
        remainingLifetimeText = createTextBean("remainingLifetimeText");
        dateCreatedText = createTextBean("dateCreatedText");
        dateLastRetrievedText = createTextBean("dateLastRetrievedText");
        passphraseBean = createPasswordBean("passphrase");

        // Default action and page
        setDefaultAction("doCredentialView");
        setRenderAction("doCredentialView");
        setDefaultJspPage("/jsp/security/gss/CredentialContextViewComp.jsp");
    }

    public void onInit() throws PortletException {
        super.onInit();
        // Register action links
        container.registerActionLink(this, "doCredentialList", CredentialContextListViewComp.class);
        container.registerActionLink(this, "doCredentialNew", CredentialContextEditComp.class);
        container.registerActionLink(this, "doCredentialEdit", CredentialContextEditComp.class);
        container.registerActionLink(this, "doCredentialDelete", CredentialContextDeleteComp.class);
    }

    public void doCredentialView(Map parameters) throws PortletException {

        log.debug("doCredentialView()");

        // Display credential view form
        setNextState(defaultJspPage);

        // Unlooad dn
        String dn = getCredentialDnParam(parameters);
        if (dn == null) {
            dn = dnText.getValue();
        }
        log.debug("Viewing credential context with dn " + dn);

        // Credential context info
        CredentialContext credentialContext = credentialManagerService.getCredentialContextByDn(dn);
        // Retrieval context info
        CredentialRetrievalContext retrievalContext = credentialRetrievalService.getCredentialRetrievalContextByDn(dn);

        // Set dn text
        dnText.setValue(dn);

        // Load credentialContext view
        loadCredentialView(credentialContext, retrievalContext);
    }

    public void doCredentialRetrieve(Map parameters) throws PortletException {

        // Unlooad dn
        String dn = getCredentialDnParam(parameters);
        log.debug("Retrieving credential  with dn " + dn);

        // Unload password
        String passphrase = passphraseBean.getValue();
        // Credential context info
        CredentialContext credentialContext = credentialManagerService.getCredentialContextByDn(dn);
        // Retrieval context info
        CredentialRetrievalContext retrievalContext = credentialRetrievalService.getCredentialRetrievalContextByDn(dn);
        // Error message
        StringBuffer errorMessage = new StringBuffer();
        // Retrieve credentials
        if (retrievalContext == null) {
            errorMessage.append(getResourceString(MESSAGE_RETRIEVE_CXT));
        } else {
            GSSCredential credential = null;
            try {
                credential = credentialRetrievalService.retrieveCredential(retrievalContext, passphrase);
                credentialManagerService.activateCredentialContext(credential);
            } catch (CredentialException e) {
                errorMessage.append("Unable to retrieve credential ["
                                  + e.getMessage() + "]");
            }
        }
        // If errors, display messages
        if (errorMessage.length() > 0) {
            messageBox.setMessageType(TextBean.MSG_ERROR);
            messageBox.setValue(errorMessage.toString());
        }
        // Load credentialContext view
        loadCredentialView(credentialContext, retrievalContext);
    }

    public void doCredentialDeactivate(Map parameters) throws PortletException {

        // Unlooad dn
        String dn = getCredentialDnParam(parameters);
        log.debug("Retrieving credential  with dn " + dn);

        CredentialContext credentialContext = credentialManagerService.getCredentialContextByDn(dn);
        credentialContext.deactivate();
        if (credentialContext == null) {
            messageBox.setValue(getResourceString(MESSAGE_CANT_DEACTIVATE_CRED));
            messageBox.setMessageType(TextBean.MSG_ERROR);

        } else {
            credentialContext.deactivate();
            messageBox.setValue(getResourceString(MESSAGE_DEACTIVATE_CRED));
            messageBox.setMessageType(TextBean.MSG_ERROR);
        }

        // Retrieval context info
        CredentialRetrievalContext retrievalContext = credentialRetrievalService.getCredentialRetrievalContextByDn(dn);

        // Load credentialContext view
        loadCredentialView(credentialContext, retrievalContext);
    }

    protected void loadCredentialView(CredentialContext credentialContext,
                                   CredentialRetrievalContext retrievalContext) throws PortletException {


        if (credentialContext != null) {
            setPageAttribute("isActive", Boolean.valueOf(credentialContext.isActive()));
        }

        // Load repository
        loadCredentialRepository();
        // Load credentialContext view panel
        loadCredentialInfo(credentialContext, retrievalContext);
        // Load credentialContext status panel
        loadCredentialStatus(credentialContext, retrievalContext);
    }

    protected void loadCredentialInfo(CredentialContext credentialContext, CredentialRetrievalContext retrievalContext) {

        // Retrieval context info
        if (retrievalContext != null) {
            // User name
            userNameText.setValue(retrievalContext.getUserName());
            // Credential name
            credentialNameText.setValue(retrievalContext.getCredentialName());
            // Life time
            int lifetime = retrievalContext.getCredentialLifetime();
            if (lifetime <= 0) lifetime = credentialRetrievalService.getDefaultCredentialLifetime();
            lifetimeText.setValue(String.valueOf(lifetime));
        }
        // Credential context info
        if (credentialContext != null) {
            // Credential label
            credentialLabelText.setValue(credentialContext.getLabel());
        }
    }

    protected void loadCredentialStatus(CredentialContext credentialContext, CredentialRetrievalContext retrievalContext) {

        Locale locale = portletRequest.getLocale();

        // Retrieval context info
        if (retrievalContext != null) {
            // Date last retrieved
            Date dateLastRetrieved = retrievalContext.getDateLastRetrieved();
            long timeLastRetrieved = 0;
            if (dateLastRetrieved != null) {
                timeLastRetrieved = dateLastRetrieved.getTime();
            }
            User user = getUser();
            String textLastRetrieved = DateUtil.getLocalizedDate(user,
                                                                 locale,
                                                                 timeLastRetrieved,
                                                                 DateFormat.FULL,
                                                                 DateFormat.FULL);
            dateLastRetrievedText.setValue(textLastRetrieved);
        }
        // Credential context info
        if (credentialContext != null) {
            // Remaining lifetime
            int lifetime = credentialContext.getRemainingLifetime();
            remainingLifetimeText.setValue(CredentialUtil.getTimeRemainingText(credentialContext));
            // Credential status
            if (lifetime == 0) {
                credentialStatusText.setValue(getResourceString("INACTIVE"));
            } else {
                credentialStatusText.setValue(getResourceString("ACTIVE"));
            }
            // Date context created
            Date dateCreated = credentialContext.getDateCreated();
            long timeCreated = 0;
            if (dateCreated != null) {
                timeCreated = dateCreated.getTime();
            }
            User user = getUser();
            String textCreated = DateUtil.getLocalizedDate(user,
                                                           locale,
                                                           timeCreated,
                                                           DateFormat.FULL,
                                                           DateFormat.FULL);
            dateCreatedText.setValue(textCreated);
        }

    }
}
