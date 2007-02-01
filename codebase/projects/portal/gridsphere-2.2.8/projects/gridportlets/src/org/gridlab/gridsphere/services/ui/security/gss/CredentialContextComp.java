/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialContextComp.java,v 1.1.1.1 2007-02-01 20:42:17 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.security.gss;

import org.gridlab.gridsphere.services.ui.ActionComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.security.gss.*;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;

public class CredentialContextComp extends ActionComponent {

    public static final String MESSAGE_CANT_RETRIEVE = "portlets.security.gss.message_retrieve_unable";
    public static final String MESSAGE_RETRIEVE_CXT = "portlets.security.gss.message_retrieval_context";
    public static final String MESSAGE_DEACTIVATE_CRED = "portlets.security.gss.message_credential_deactivated";
    public static final String MESSAGE_CANT_DEACTIVATE_CRED = "portlets.security.gss.message_credential_deactivate_unable";
    public static final String MESSAGE_USERNAME = "portlets.security.gss.message_username";
    public static final String MESSAGE_LABEL = "portlets.security.gss.message_label";
    public static final String MESSAGE_PASSPHRASE = "portlets.security.gss.message_passphrase";

    public final static String CREDENTIAL_CONTEXT_PARAM = "credentialContextParam";
    public final static String RETRIEVAL_CONTEXT_PARAM = "retrievalContextParam";
    public final static String CREDENTIAL_DN_PARAM = "credentialDnParam";
    public final static String CREDENTIAL_DN_LIST_PARAM = "credentialContextParam";

    private transient static PortletLog log = SportletLog.getInstance(CredentialContextComp.class);

    protected CredentialManagerService credentialManagerService = null;
    protected CredentialRetrievalService credentialRetrievalService = null;
    protected TextBean credentialRepositoryText = null;
    protected String credentialDn = null;

    public CredentialContextComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        credentialRepositoryText = createTextBean("credentialRepositoryText");
    }

    public void onInit()
            throws PortletException {
        super.onInit();
        credentialManagerService = (CredentialManagerService)
                getPortletService(CredentialManagerService.class);
        credentialRetrievalService = (CredentialRetrievalService)
                getPortletService(CredentialRetrievalService.class);
    }

    public void onStore() throws PortletException {
        setPageAttribute("credentialDn", credentialDn);
    }

    protected String getCredentialDnParam(Map parameters) {
        String value = (String)parameters.get(CREDENTIAL_DN_PARAM);
        if (value != null) {
            credentialDn = value;
        }
        return value;
    }

    public CredentialContext getCredentialContext(Map parameters) {
        CredentialContext cc = getCredentialContext(credentialManagerService, parameters);
        if (cc != null) {
            // Save job oid and job type for use in jsps
            credentialDn = cc.getDn();
        }
        return cc;
    }

    public static CredentialContext getCredentialContext(CredentialManagerService credentialManagerService, Map parameters) {
        // Get cmt parameter
        CredentialContext cc = (CredentialContext)parameters.get(CREDENTIAL_CONTEXT_PARAM);
        if (cc == null) {
            // Get cmt oid parameter...
            String dnParam = (String)parameters.get(CREDENTIAL_DN_PARAM);
            if (cc != null) {
                cc = (CredentialContext)
                        credentialManagerService.getCredentialContextByDn(dnParam);
                parameters.put(CREDENTIAL_CONTEXT_PARAM, cc);
            }
        }
        return cc;
    }

    public CredentialRetrievalContext getRetrievalContext(Map parameters) {
        CredentialRetrievalContext rc = getRetrievalContext(credentialRetrievalService, parameters);
        if (rc != null) {
            // Save job oid and job type for use in jsps
            credentialDn = rc.getDn();
        }
        return rc;
    }

    public static CredentialRetrievalContext getRetrievalContext(CredentialRetrievalService credentialRetrievalService, Map parameters) {
        // Get cmt parameter
        CredentialRetrievalContext rc = (CredentialRetrievalContext)parameters.get(CREDENTIAL_CONTEXT_PARAM);
        if (rc == null) {
            // Get cmt oid parameter...
            String dnParam = (String)parameters.get(CREDENTIAL_DN_PARAM);
            if (rc != null) {
                rc = (CredentialRetrievalContext)
                        credentialRetrievalService.getCredentialRetrievalContextByDn(dnParam);
                parameters.put(RETRIEVAL_CONTEXT_PARAM, rc);
            }
        }
        return rc;
    }

    protected void loadCredentialRepository() {
        CredentialRepository credentialRepository = credentialRetrievalService.getActiveCredentialRepository();
        if (credentialRepository == null) {
            credentialRepositoryText.setValue("&lt;MyProxy not yet configured!&gt;");
            credentialRepositoryText.setStyle(TextBean.MSG_ERROR);
        } else {
            credentialRepositoryText.setValue(credentialRepository.getHardwareResource().getHostName());
        }
    }
}
