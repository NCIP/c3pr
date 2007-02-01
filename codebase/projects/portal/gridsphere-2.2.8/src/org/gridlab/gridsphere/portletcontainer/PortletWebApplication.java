/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletWebApplication.java,v 1.1.1.1 2007-02-01 20:50:26 kherm Exp $
 */
package org.gridlab.gridsphere.portletcontainer;

import java.util.Collection;

/**
 * A <code>PortletWebApplication</code> represents a collection of portlets contained in a packaged WAR file. Currently
 * under development is the notion of dynamically managing portlet web applications.
 */
public interface PortletWebApplication {

    /**
     * Under development. A portlet web application can unregister itself from the application server
     */
    public void destroy();

    /**
     * Returns the portlet web application name
     *
     * @return the ui application name
     */
    public String getWebApplicationName();

    /**
     * Returns the portlet web application description
     *
     * @return the portlet web application description
     */
    public String getWebApplicationDescription();

    /**
     * Returns the collection of application portlets contained by this portlet web application
     *
     * @return the collection of application portlets
     */
    public Collection getAllApplicationPortlets();
}
