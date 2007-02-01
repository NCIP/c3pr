package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.services.ui.wizard.ActionPage;
import org.gridlab.gridsphere.services.ui.wizard.ActionPage;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.generic.GenericApplicationSpecEditPage;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.job.JobSpec;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GenericJobSpecConfirmPage.java,v 1.1.1.1 2007-02-01 20:42:07 kherm Exp $
 */

public class GenericJobSpecConfirmPage extends JobSpecComp implements ActionPage{

    private transient static PortletLog log = SportletLog.getInstance(GenericApplicationSpecEditPage.class);

    public GenericJobSpecConfirmPage(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);

        // Init job spec beans
        setIsReadOnly(true);
        initJobSpec();

        // Set default action and page
        setDefaultAction("doJobSpecConfirm");
        setDefaultJspPage("/jsp/job/generic/JobSpecConfirmComp.jsp");
    }

    public void doJobSpecConfirm(Map parameters) throws PortletException {
        log.error("doJobSpecConfirm");

        // Set next state
        setNextState(defaultJspPage);

        // Get job spec parameter
        JobSpec jobSpec = getJobSpec(parameters);
        // Verify job spec parameter
        if (jobSpec == null) {
            messageBox.appendText("No job specification was provided!");
        }

        // Load job spec beans
        loadJobSpec(jobSpec);
    }

    public void doDisplayPage(Map parameters) throws PortletException {
        log.error("doDisplayPage");
        setNextState(defaultJspPage);
    }

    public boolean validatePage(Map parameters) throws PortletException {
        log.error("validatePage()");
        return true;
    }
}
