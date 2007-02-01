/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletConfig.java,v 1.1.1.1 2007-02-01 20:50:04 kherm Exp $
 */
package org.gridlab.gridsphere.portlet;

import javax.servlet.ServletConfig;

/**
 * The <code>PortletConfig</code> interface provides the portlet with its
 * configuration. The configuration holds information about the portlet that
 * is valid for all users, and is maintained by the administrator. The portlet
 * can therefore only read the configuration. Only when the portlet is in
 * <code>CONFIGURE</code> mode, it has write access to the configuration data
 * (the rest of the configuration is managed by the portlet container directly).
 */
public interface PortletConfig extends ServletConfig {

    /**
     * Returns the portlet context
     *
     * @return the portlet context
     */
    public PortletContext getContext();

    /**
     * Returns the name of the portlet
     *
     * @return the name of the portlet
     */
    public String getName();

    /**
     * Returns whether the portlet supports the given mode for the supplied client
     *
     * @param mode   the portlet mode
     * @param client the Client
     * @return <code>true</code> if the window supports the given state,
     *         <code>false</code> otherwise
     */
    public boolean supports(Portlet.Mode mode, Client client);


    /**
     * Returns whether the portlet window supports the given state
     *
     * @param state the portlet window state
     * @return <code>true</code> if the window supports the given state,
     *         <code>false</code> otherwise
     */
    public boolean supports(PortletWindow.State state);

}