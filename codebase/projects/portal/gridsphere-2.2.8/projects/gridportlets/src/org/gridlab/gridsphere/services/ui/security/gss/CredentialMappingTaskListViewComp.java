/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingTaskListViewComp.java,v 1.1.1.1 2007-02-01 20:42:18 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.security.gss;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingService;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingTaskType;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.CheckBoxBean;
import org.gridlab.gridsphere.portlet.User;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class CredentialMappingTaskListViewComp extends CredentialMappingTaskComp {

    protected CredentialMappingService credentialMappingService = null;
    protected CheckBoxBean cmtOidCheckBox = null;

    public CredentialMappingTaskListViewComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        setDefaultAction("doViewTaskList");
        setRenderAction("doViewTaskList");
        setDefaultJspPage("/jsp/security/gss/CredentialMappingListViewComp.jsp");
    }

    public void onInit()
            throws PortletException {
        super.onInit();
        // Get credential mapping service
        credentialMappingService = (CredentialMappingService)
                getPortletService(CredentialMappingService.class);
        // Create new oid check box
        cmtOidCheckBox = createCheckBoxBean("cmtOidCheckBox");
        // Register action links
        container.registerActionLink(this, "doViewTask", CredentialMappingTaskViewComp.class);
        container.registerActionLink(this, "doNewTask", CredentialMappingTaskEditComp.class);
        // Register credential actions
        container.addActionFilter(this,  "doNewTask", ActiveCredentialFilter.getInstance());
//        container.registerCredentialAction(this, "doNewTask");
    }

    public void onStore() throws PortletException {
        // Get cmt list
        User user = getUser();
        List cmtList = credentialMappingService.getTasks(user, CredentialMappingTaskType.INSTANCE);
        setPageAttribute("cmtList", cmtList);
    }

    public void doViewTaskList(Map parameters) throws PortletException {
        // Set next state
        setNextState(defaultJspPage);
    }

    public void doDeleteTaskList(Map parameters) throws PortletException {
        List values = new Vector(cmtOidCheckBox.getSelectedValues());
        if (values.size() == 0) {
            messageBox.appendText("Please select which task records you want to delete");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
        } else {
            parameters.put(CMT_OID_LIST_PARAM, values);
            setNextState(CredentialMappingTaskListDeleteComp.class, DEFAULT_ACTION, parameters);
        }
    }
}
