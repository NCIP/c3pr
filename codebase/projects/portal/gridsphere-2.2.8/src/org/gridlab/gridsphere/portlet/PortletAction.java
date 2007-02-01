/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletAction.java,v 1.1.1.1 2007-02-01 20:50:04 kherm Exp $
 */
package org.gridlab.gridsphere.portlet;


/**
 * The <code>PortletAction</code> can be implemented to define portlet-specific
 * actions that need to be executed for specific URIs.
 * <p/>
 * A portlet action can carry any information. It should however not store a
 * request, response, or session object. This information is part of the action
 * event that will be sent to the registered action listener(s).
 */
public interface PortletAction {

}
