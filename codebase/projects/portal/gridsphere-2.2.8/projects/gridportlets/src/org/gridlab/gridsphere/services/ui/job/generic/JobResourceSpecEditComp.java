package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.portlet.PortletLog;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.generic.JobApplicationSpecViewComp;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.job.JobSpec;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobResourceSpecEditComp.java,v 1.1.1.1 2007-02-01 20:42:08 kherm Exp $
 */

public class JobResourceSpecEditComp extends JobSpecComp {

    private transient static PortletLog log = SportletLog.getInstance(JobApplicationSpecViewComp.class);

    public JobResourceSpecEditComp(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);

        initResourceSpec();

        // Set default action and page
        setDefaultAction("doJobResourceSpecEdit");
        setDefaultJspPage("/jsp/job/generic/JobResourceSpecComp.jsp");
    }

    public void doJobResourceSpecEdit(Map parameters) throws PortletException {

        log.error("doJobResourceSpecEdit");

        // Set next state
        setNextState(defaultJspPage);

        // Get job spec parameter
        JobSpec jobSpec = getJobSpec(parameters);
        // Verify job spec parameter
        if (jobSpec == null) {
            messageBox.appendText("No job specification was provided!");
        }

        // Load resource spec beans
        loadResourceSpec(jobSpec);
    }
}
