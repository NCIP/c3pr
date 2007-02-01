/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingTaskListDeleteComp.java,v 1.1.1.1 2007-02-01 20:42:18 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.security.gss;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingService;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingTask;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Iterator;

public class CredentialMappingTaskListDeleteComp extends CredentialMappingTaskComp {

    private transient static PortletLog log = SportletLog.getInstance(CredentialMappingTaskListDeleteComp.class);
    protected CredentialMappingService credentialMappingService = null;

    protected List cmtOidList = null;
    protected boolean isStoredCredentialMappingTaskList = false;

    public CredentialMappingTaskListDeleteComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        // Default action and page
        setDefaultAction("doView");
        setDefaultJspPage("/jsp/security/gss/CredentialMappingTaskListDeleteComp.jsp");
    }

    public void onInit() throws PortletException {
        super.onInit();
        // Register action links
        container.registerActionLink(this, "doCredentialMappingTaskListView", CredentialMappingTaskListViewComp.class, DEFAULT_ACTION);
        container.registerActionLink(this, "doCancel", CredentialMappingTaskListViewComp.class, DEFAULT_ACTION);
    }

    public void onLoad() throws PortletException {
        super.onInit();
        isStoredCredentialMappingTaskList = false;
    }

    public void onStore() throws PortletException {
        if (!isStoredCredentialMappingTaskList) {
            List cmtList = new Vector();
            for (int ii = 0; ii < cmtOidList.size(); ++ii) {
                String cmtOid = (String)cmtOidList.get(ii);
                CredentialMappingTask cmt = (CredentialMappingTask)credentialMappingService.getTask(cmtOid);
                if (cmt != null) {
                    cmtList.add(cmt);
                }
            }
            setPageAttribute("cmtList", cmtList);
            isStoredCredentialMappingTaskList = true;
        }
    }

    public void doView(Map parameters) throws PortletException {
        // Set next state
        setNextState(defaultJspPage);

        cmtOidList = (List)parameters.get("cmtOidList");
        if (cmtOidList == null) {
            messageBox.appendText("No cmts were specified for deletion");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            cmtOidList = new Vector();
        }
    }

    public void doApply(Map parameters) throws PortletException {

        // Set next state
        setNextState(defaultJspPage);

        List cmtList = new Vector();
        log.debug("Deleting " + cmtList.size() + " cmts");
        Iterator cmtOidIterator = cmtOidList.iterator();
        while (cmtOidIterator.hasNext()) {
            String cmtOid = (String)cmtOidIterator.next();
            CredentialMappingTask cmt = (CredentialMappingTask)credentialMappingService.getTask(cmtOid);
            if (cmt != null) {
                try {
                    log.debug("Deleting cmt " + cmtOid);
                    credentialMappingService.deleteTask(cmt);
                    log.debug("Deleted cmt " + cmtOid);
                    cmtList.add(cmt);
                } catch (Exception e) {
                    log.error("Error deleting cmt " + cmtOid, e);
                }
            }
        }
        setPageAttribute("cmtList", cmtList);
        isStoredCredentialMappingTaskList = true;
        messageBox.appendText("The following cmts were successfully deleted");
        messageBox.setMessageType(TextBean.MSG_SUCCESS);
        setPageAttribute("deletedFlag", Boolean.TRUE);
    }

    public void doCancel(Map parameters) throws PortletException {
        setNextState(CredentialMappingTaskListViewComp.class, DEFAULT_ACTION, parameters);
    }
}
