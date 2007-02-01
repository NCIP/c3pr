/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionFilter.java,v 1.1.1.1 2007-02-01 20:41:58 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui;

import java.util.Map;

/**
 * Describes a "filter" method before performing a given action method.
 */
public interface ActionFilter {

    public void filter(ActionComponent component, String method, Map parameters)
            throws ActionFilterException;
}
