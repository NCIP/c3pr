/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingTaskEditComp.java,v 1.1.1.1 2007-02-01 20:42:18 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.security.gss;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.security.gss.*;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxItemBean;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class CredentialMappingTaskEditComp extends CredentialMappingTaskComp {

    private transient static PortletLog log = SportletLog.getInstance(CredentialMappingTaskEditComp.class);

    protected CredentialMappingService credentialMappingService = null;
    protected CredentialMappingSpec credentialMappingSpec = null;
    protected ListBoxBean credentialDnListBox = null;
    protected ListBoxBean resourceDnListBox = null;

    public CredentialMappingTaskEditComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        // Create action beans
        credentialDnListBox = createListBoxBean("credentialDnListBox");
        resourceDnListBox = createListBoxBean("resourceDnListBox");
        // Set default action
        setDefaultAction("doEdit");
        setDefaultJspPage("/jsp/security/gss/CredentialMappingTaskEditComp.jsp");
    }

    public void doEdit(Map parameters) throws PortletException {
        setNextState(defaultJspPage);
        // Credential dn list
        User user = getUser();
        Iterator credentialContexts = credentialManagerService.getCredentialContexts(user).iterator();
        while (credentialContexts.hasNext()) {
            CredentialContext context = (CredentialContext)credentialContexts.next();
            String dn = context.getDn();
            ListBoxItemBean item = new ListBoxItemBean();
            item.setName(dn);
            item.setValue(dn);
            resourceDnListBox.addBean(item);
        }
        // Resource dn list
        ResourceRegistryService registry
                = (ResourceRegistryService)getPortletService(ResourceRegistryService.class);
        Iterator gssResources = registry.getResources(GssEnabledResourceType.INSTANCE).iterator();
        while (gssResources.hasNext()) {
            GssEnabledResource gssResource = (GssEnabledResource)gssResources.next();
            String resourceDn = gssResource.getDn();
            ListBoxItemBean item = new ListBoxItemBean();
            item.setName(resourceDn);
            item.setValue(resourceDn);
            resourceDnListBox.addBean(item);
        }
    }

    public void doApply(Map parameters) throws PortletException {
        setNextState(defaultJspPage);
        try {
            // Create spec
            CredentialMappingSpec cmtSpec = (CredentialMappingSpec)
                    credentialMappingService.createTaskSpec(CredentialMappingTaskType.INSTANCE);
            User user = getUser();
            cmtSpec.setUser(user);
            // Credential dn
            String credentialDn = credentialDnListBox.getSelectedName();
            cmtSpec.setCredentialDn(credentialDn);
            // Resource dns
            ResourceRegistryService registry
                    = (ResourceRegistryService)getPortletService(ResourceRegistryService.class);
            List resourceDnList = resourceDnListBox.getSelectedNames();
            cmtSpec.setResourceDns(resourceDnList);
            // Submit task
            CredentialMappingTask cmt = (CredentialMappingTask)
                    credentialMappingService.submitTask(cmtSpec);
            // Pass parameters...
            parameters.put(MESSAGE_BOX_TEXT_PARAM, "This task is being submitted...");
            parameters.put(MESSAGE_BOX_TYPE_PARAM, TextBean.MSG_SUCCESS);
            parameters.put(CMT_PARAM, cmt);
            setNextState(CredentialMappingTaskViewComp.class, DEFAULT_ACTION, parameters);
        } catch(Exception e) {
            // Display error messsage
            StringBuffer errorBuffer = new StringBuffer();
            errorBuffer.append("Unable to submit credential mapping task.<br>");
            errorBuffer.append(e.getMessage());
            errorBuffer.append("<br>");
            messageBox.appendText(errorBuffer.toString());
            messageBox.setMessageType(TextBean.MSG_ERROR);
        }
    }

    public void doCancel(Map parameters) throws PortletException {
        setNextState(CredentialMappingTaskListViewComp.class, DEFAULT_ACTION, parameters);
    }
}
