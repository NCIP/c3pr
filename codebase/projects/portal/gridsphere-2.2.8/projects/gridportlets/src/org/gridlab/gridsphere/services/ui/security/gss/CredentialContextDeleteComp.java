/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialContextDeleteComp.java,v 1.1.1.1 2007-02-01 20:42:17 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.security.gss;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingService;
import org.gridlab.gridsphere.services.security.gss.CredentialRetrievalContext;
import org.gridlab.gridsphere.services.security.gss.CredentialContext;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.Map;

public class CredentialContextDeleteComp extends CredentialContextViewComp {

    private transient static PortletLog log = SportletLog.getInstance(CredentialContextDeleteComp.class);

    protected CredentialMappingService credentialMappingService = null;

    public CredentialContextDeleteComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        setDefaultAction("doCredentialDelete");
        setDefaultJspPage("/jsp/security/gss/CredentialContextDeleteComp.jsp");
    }

    public void onInit()
            throws PortletException {
        super.onInit();
        // Register action links
        container.registerActionLink(this, "doCredentialList", CredentialContextListViewComp.class);
    }

    public void doCredentialDelete(Map parameters) throws PortletException {
        setNextState("/jsp/security/gss/CredentialContextDeleteComp.jsp");
        setPageAttribute("deletedFlag", Boolean.FALSE);

        super.doCredentialView(parameters);
    }

    public void doApply(Map parameters) throws PortletException {

        setNextState("/jsp/security/gss/CredentialContextDeleteComp.jsp");
        setPageAttribute("deletedFlag", Boolean.FALSE);

        // Unload dn
        String dn = getCredentialDnParam(parameters);
        log.debug("Deleting credential context with dn " + dn);

        // Retrieval context info
        CredentialRetrievalContext retrievalContext = credentialRetrievalService.getCredentialRetrievalContextByDn(dn);
        // Credential context info
        CredentialContext credentialContext = credentialManagerService.getCredentialContextByDn(dn);
        // Load credential view panel
        loadCredentialInfo(credentialContext, retrievalContext);
        // Load credential status panel
        loadCredentialStatus(credentialContext, retrievalContext);
        // Delete retrieval context
        if (retrievalContext != null) {
            credentialRetrievalService.deleteCredentialRetrievalContext(retrievalContext);
        }
        // Delete credential context
        if (credentialContext != null) {
            credentialManagerService.deleteCredentialContext(credentialContext);
        }

        setPageAttribute("deletedFlag", Boolean.TRUE);
    }

    public void doCancel(Map parameters) throws PortletException {
        log.error("doCancel");
        setNextState(CredentialContextViewComp.class, DEFAULT_ACTION, parameters);
    }
}
