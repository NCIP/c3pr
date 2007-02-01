/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionPage.java,v 1.1.1.1 2007-02-01 20:42:19 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.wizard;

import javax.portlet.PortletException;

import java.util.Map;

public interface ActionPage {

    public void doDisplayPage(Map parameters) throws PortletException;

    public boolean validatePage(Map parameters) throws PortletException;
}
