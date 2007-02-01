/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ApplicationPortlet.java,v 1.1.1.1 2007-02-01 20:50:21 kherm Exp $
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;

import java.util.List;

/**
 * An <code>ApplicationPortlet</code> represents the portlet application instance
 * defined in the portlet descriptor file.
 *
 * @see <code>org.gridlab.gridsphere.portletcontainer.gs.descriptor.ApplicationSportletConfig</code>
 */
public interface ApplicationPortlet {

    /**
     * Returns the concrete portlets associated with this application portlet
     *
     * @return the <code>ConcretePortlet</code>s
     */
    public List getConcretePortlets();

    /**
     * Returns the <code>ConcretePortlet</code> associated with this application
     * portlet
     *
     * @param concretePortletID the concrete portlet ID associated with this
     *                          <code>ApplicationPortlet</code>
     * @return the <code>ConcretePortlet</code> associated with this
     *         application portlet
     */
    public ConcretePortlet getConcretePortlet(String concretePortletID);

    /**
     * Returns the web application name associated with this application portlet
     *
     * @return the web application name
     */
    public String getWebApplicationName();

    /**
     * Returns the id of a PortletApplication
     *
     * @return the id of the PortletApplication
     */
    public String getApplicationPortletID();

    /**
     * Returns the name of a PortletApplication
     *
     * @return name of the PortletApplication
     */
    public String getApplicationPortletName();

    /**
     * Returns the portlet application class
     *
     * @return classname of the portlet
     */
    public String getApplicationPortletClassName();

    /**
     * Returns a PortletDispatcher for this ApplicationPortlet
     *
     * @return PortletDispatcher the proxy portlet for this ApplicationPortlet
     */
    public PortletDispatcher getPortletDispatcher(PortletRequest req, PortletResponse res);

    /**
     * Return the PortletApplication, the portlet descriptor class that defines the portlet application
     *
     * @return the PortletApplication
     */
    public ApplicationPortletConfig getApplicationPortletConfig();

    public String toString();

}
