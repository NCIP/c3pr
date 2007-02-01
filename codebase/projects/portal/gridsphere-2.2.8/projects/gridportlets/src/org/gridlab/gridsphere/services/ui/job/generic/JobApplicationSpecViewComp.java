package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.portlet.PortletLog;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.job.JobSpec;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobApplicationSpecViewComp.java,v 1.1.1.1 2007-02-01 20:42:07 kherm Exp $
 */

public class JobApplicationSpecViewComp extends JobSpecComp {

    private transient static PortletLog log = SportletLog.getInstance(JobApplicationSpecViewComp.class);

    public JobApplicationSpecViewComp(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);

        setIsReadOnly(true);
        initApplicationSpec();

        // Set default action and page
        setDefaultAction("doJobApplicationSpecView");
        setDefaultJspPage("/jsp/job/generic/JobApplicationSpecComp.jsp");
    }

    public void doJobApplicationSpecView(Map parameters) throws PortletException {

        log.error("doJobApplicationSpecView");

        // Set next state
        setNextState(defaultJspPage);

        // Get job spec parameter
        JobSpec jobSpec = getJobSpec(parameters);
        // Verify job spec parameter
        if (jobSpec == null) {
            messageBox.appendText("No job specification was provided!");
        }

        // Load application spec beans
        loadApplicationSpec(jobSpec);
    }
}
