/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: DefaultHelpComp.java,v 1.1.1.1 2007-02-01 20:41:58 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;

import javax.portlet.PortletException;
import java.util.Map;

public class DefaultHelpComp extends ActionComponent {

    private static PortletLog log = SportletLog.getInstance(DefaultHelpComp.class);

    public DefaultHelpComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        setDefaultAction("doView");
        setDefaultJspPage("/jsp/action/DefaultHelpComp.jsp");
    }

    public void doView(Map parameters) throws PortletException {
        setNextState(defaultJspPage);
    }
}
