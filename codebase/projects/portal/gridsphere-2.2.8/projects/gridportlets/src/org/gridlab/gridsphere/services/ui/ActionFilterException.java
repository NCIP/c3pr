/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionFilterException.java,v 1.1.1.1 2007-02-01 20:41:58 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui;

import javax.portlet.PortletException;

/**
 * Thrown when an unauthorized action is invoked.
 */
public class ActionFilterException extends PortletException {

    public ActionFilterException() { super(); }

    public ActionFilterException(String s) { super(s); }

    public ActionFilterException(String s, Throwable throwable) { super(s, throwable); }

    public ActionFilterException(Throwable throwable) { super(throwable); }

}
