/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingTaskViewComp.java,v 1.1.1.1 2007-02-01 20:42:19 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.security.gss;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingService;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingTask;
import org.gridlab.gridsphere.services.core.utils.DateUtil;
import org.gridlab.gridsphere.services.task.TaskStatus;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;
import java.util.List;
import java.text.DateFormat;

public class CredentialMappingTaskViewComp extends CredentialMappingTaskComp {

    private transient static PortletLog log = SportletLog.getInstance(CredentialMappingTaskViewComp.class);

    protected CredentialMappingService credentialMappingService = null;
    protected TextBean cmtOidText = null;
    protected TextBean cmtStatusText = null;
    protected TextBean cmtStartedText = null;
    protected TextBean cmtEndedText = null;

    public CredentialMappingTaskViewComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        // Create action beans
        cmtOidText = createTextBean("cmtOidText");
        cmtStatusText = createTextBean("cmtStatusText");
        cmtStartedText = createTextBean("cmtStartedText");
        cmtEndedText = createTextBean("cmtEndedText");
        // Default action and page
        setDefaultAction("doView");
        setDefaultJspPage("/jsp/security/gss/CredentialMappingTaskViewComp.jsp");
    }

    public void onInit() throws PortletException {
        super.onInit();
        // Register action links
        container.registerActionLink(this, "doNewTask", CredentialMappingTaskEditComp.class);
        // Register credential actions
        container.addActionFilter(this,  "doNewTask", ActiveCredentialFilter.getInstance());
//        container.registerCredentialAction(this, "doNewTask");
    }

    public void doView(Map parameters) throws PortletException {
        // Set next state
        setNextState(defaultJspPage);
        // Get job from parameters
        CredentialMappingTask cmt = getCredentialMappingTask(parameters);
        if (cmt == null) {
            messageBox.appendText("No credential mapping task parameter was provided");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return;
        }
        // Job oid
        cmtOidText.setValue(cmt.getOid());
        // Job status
        TaskStatus cmtStatus = cmt.getTaskStatus();
        String statusMessage = cmt.getTaskStatusMessage();
        if (statusMessage == null) {
            cmtStatusText.setValue(cmtStatus.getName());
        } else {
            cmtStatusText.setValue(statusMessage);
        }
        long timeStarted = cmt.getDateTaskStarted().getTime();
        long timeEnded = cmt.getDateTaskEnded().getTime();
        User user = getUser();
        String textStarted = DateUtil.getLocalizedDate(user,
                                                       portletRequest.getLocale(),
                                                       timeStarted,
                                                       DateFormat.FULL,
                                                       DateFormat.FULL);
        cmtStartedText.setValue(textStarted);
        String textEnded = DateUtil.getLocalizedDate(user,
                                                     portletRequest.getLocale(),
                                                     timeEnded,
                                                     DateFormat.FULL,
                                                     DateFormat.FULL);
        cmtEndedText.setValue(textStarted);
        // Get cmt list
        List cmtTestList = cmt.getCredentialMappingTests();
        setPageAttribute("cmtTestList", cmtTestList);
    }
}
