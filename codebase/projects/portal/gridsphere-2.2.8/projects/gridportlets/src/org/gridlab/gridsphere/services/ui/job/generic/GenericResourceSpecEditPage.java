package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.portlet.PortletLog;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.wizard.ActionPage;
import org.gridlab.gridsphere.services.ui.wizard.ActionPage;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.job.JobSpec;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GenericResourceSpecEditPage.java,v 1.1.1.1 2007-02-01 20:42:07 kherm Exp $
 */

public class GenericResourceSpecEditPage
        extends JobSpecComp implements ActionPage {

    private transient static PortletLog log = SportletLog.getInstance(GenericResourceSpecEditPage.class);

    public GenericResourceSpecEditPage(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);

        setIsReadOnly(false);
        initResourceSpec();
//        jobResourceList.setSize(10);

        // Set default action and page
        setDefaultAction("doResourceSpecEdit");
        setDefaultJspPage("/jsp/job/generic/ResourceSpecEditComp.jsp");
    }

    public void doResourceSpecEdit(Map parameters) throws PortletException {

        log.error("doResourceSpecEdit");

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

    public void doDisplayPage(Map parameters) throws PortletException {
        log.error("doDisplayPage");
        setNextState(defaultJspPage);
    }

    public boolean validatePage(Map parameters) throws PortletException {
        log.error("validatePage()");

        // Get job spec parameter
        JobSpec jobSpec = getJobSpec(parameters);

        // Unload job spec
        return unloadResourceSpec(jobSpec);
    }
}
