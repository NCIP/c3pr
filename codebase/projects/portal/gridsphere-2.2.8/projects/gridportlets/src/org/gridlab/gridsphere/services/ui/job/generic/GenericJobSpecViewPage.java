package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.services.ui.job.JobSpecViewPage;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.job.JobSpec;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GenericJobSpecViewPage.java,v 1.1.1.1 2007-02-01 20:42:07 kherm Exp $
 */

public class GenericJobSpecViewPage extends JobSpecComp implements JobSpecViewPage {

    private transient static PortletLog log = SportletLog.getInstance(GenericJobSpecViewPage.class);

    public GenericJobSpecViewPage(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);

        // Init job spec beans
        setIsReadOnly(true);
        initJobSpec();

        // Default action and page
        setDefaultAction("doJobSpecView");
        setDefaultJspPage("/jsp/job/generic/JobSpecViewComp.jsp");
    }

    public void doJobSpecView(Map parameters) throws PortletException {
        log.debug("doJobSpecView()");

        // Set next state
        setNextState(defaultJspPage);

        // Get job spec parameter
        JobSpec jobSpec = getJobSpec(parameters);

        log.debug("Job spec has oid " + jobSpec.getOid());

        // Verify job spec parameter
        if (jobSpec == null) {
            messageBox.appendText("No job specification was provided!");
        }

        // Load job spec
        loadJobSpec(jobSpec);
    }

    public void doDisplayPage(Map parameters) throws PortletException {
        log.error("doDisplayPage");
        setNextState(defaultJspPage);
    }

    public boolean validatePage(Map parameters) throws PortletException {
        log.error("validatePage");
        return true;
    }
}
