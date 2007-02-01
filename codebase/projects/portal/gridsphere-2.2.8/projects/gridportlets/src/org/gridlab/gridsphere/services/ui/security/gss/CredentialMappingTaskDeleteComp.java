/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingTaskDeleteComp.java,v 1.1.1.1 2007-02-01 20:42:18 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.security.gss;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingService;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingTask;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;

public class CredentialMappingTaskDeleteComp extends CredentialMappingTaskViewComp {

    private transient static PortletLog log = SportletLog.getInstance(CredentialMappingTaskDeleteComp.class);

    protected CredentialMappingService credentialMappingService = null;

    public CredentialMappingTaskDeleteComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        setDefaultJspPage("/jsp/security/gss/CredentialMappingTaskDeleteComp.jsp");
    }

    public void doView(Map parameters) throws PortletException {
        super.doView(parameters);
        setPageAttribute("deletedFlag", Boolean.FALSE);
    }

    public void doApply(Map parameters) throws PortletException {
        log.error("doApply");
        setNextState(defaultJspPage);
        CredentialMappingTask cmt = getCredentialMappingTask(parameters);
        if (cmt == null) {
            messageBox.appendText("No credential mapping task parameter was provided");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return;
        }
        try {
            credentialMappingService.deleteTask(cmt);
            setPageAttribute("deletedFlag", Boolean.TRUE);
            messageBox.appendText("CredentialMappingTask was successfully deleted");
            messageBox.setMessageType(TextBean.MSG_SUCCESS);
        } catch (Exception e) {
            setPageAttribute("deletedFlag", Boolean.FALSE);
            messageBox.appendText("No credential mapping task parameter was provided!");
            messageBox.setMessageType(TextBean.MSG_ERROR);
        }
    }

    public void doCancel(Map parameters) throws PortletException {
        log.error("doCancel");
        CredentialMappingTask cmt = getCredentialMappingTask(parameters);
        setNextState(CredentialMappingTaskViewComp.class, DEFAULT_ACTION, parameters);
    }
}
