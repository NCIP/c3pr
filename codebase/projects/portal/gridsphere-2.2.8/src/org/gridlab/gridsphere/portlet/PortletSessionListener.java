/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletSessionListener.java,v 1.1.1.1 2007-02-01 20:50:06 kherm Exp $
 */
package org.gridlab.gridsphere.portlet;

/**
 * The <code>PortletSessionListener</code> provides an interface for performing
 * user lohin and logout functionality. <code>bPortletSessionListener</code> is
 * implemented by the {@link org.gridlab.gridsphere.portlet.PortletAdapter}.
 */
public interface PortletSessionListener {

    /**
     * Called by the portlet container to ask the portlet to initialize a
     * personalized user experience. In addition to initializing the session
     * this method allows the portlet to initialize the concrete portlet
     * instance, for example, to store attributes in the session.
     *
     * @param request the portlet request
     * @throws PortletException if a portlet error occurs during processing
     */
    public void login(PortletRequest request) throws PortletException;

    /**
     * Called by the portlet container to indicate that a concrete portlet instance is being removed.
     * This method gives the concrete portlet instance an opportunity to clean up any resources
     * (for example, memory, file handles, threads), before it is removed.
     * This happens if the user logs out, or decides to remove this portlet from a page.
     *
     * @param session the portlet session
     * @throws PortletException if a portlet error occurs during processing
     */
    public void logout(PortletSession session) throws PortletException;

}
