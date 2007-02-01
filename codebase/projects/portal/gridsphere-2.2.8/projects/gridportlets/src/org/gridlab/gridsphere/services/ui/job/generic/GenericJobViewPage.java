package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.JobViewPage;
import javax.portlet.PortletException;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GenericJobViewPage.java,v 1.1.1.1 2007-02-01 20:42:07 kherm Exp $
 */

public class GenericJobViewPage extends JobViewComp implements JobViewPage {

    public GenericJobViewPage(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);
    }

    public void doDisplayPage(Map parameters) throws PortletException {
    }

    public boolean validatePage(Map parameters) throws PortletException {
        return true;
    }
}
