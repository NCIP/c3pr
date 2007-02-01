/**
 * Copyright 2003 IBM Corporation and Sun Microsystems, Inc.
 * All rights reserved.
 * Use is subject to license terms.
 */

package javax.portlet;


/**
 * The <CODE>ActionResponse</CODE> interface represents the portlet
 * response to an action request.
 * It extends the <CODE>PortletResponse</CODE> interface to provide specific
 * action response functionality to portlets.<br>
 * The portlet container creates an <CODE>ActionResponse</CODE> object and
 * passes it as argument to the portlet's <CODE>processAction</CODE> method.
 *
 * @see ActionRequest
 * @see PortletResponse
 */
public interface ActionResponse extends PortletResponse {

    /**
     * Sets the window state of a portlet to the given window state.
     * <p/>
     * Possible values are the standard window states and any custom
     * window states supported by the portal and the portlet.
     * Standard window states are:
     * <ul>
     * <li>MINIMIZED
     * <li>NORMAL
     * <li>MAXIMIZED
     * </ul>
     *
     * @param windowState the new portlet window state
     * @throws WindowStateException if the portlet cannot switch to the specified window state.
     *                              To avoid this exception the portlet can check the allowed
     *                              window states with <code>Request.isWindowStateAllowed()</code>.
     * @throws java.lang.IllegalStateException
     *                              if the method is invoked after <code>sendRedirect</code> has been called.
     * @see WindowState
     */

    public void setWindowState(WindowState windowState)
            throws WindowStateException;


    /**
     * Sets the portlet mode of a portlet to the given portlet mode.
     * <p/>
     * Possible values are the standard portlet modes and any custom
     * portlet modes supported by the portal and the portlet. Portlets
     * must declare in the deployment descriptor the portlet modes they
     * support for each markup type.
     * Standard portlet modes are:
     * <ul>
     * <li>EDIT
     * <li>HELP
     * <li>VIEW
     * </ul>
     * <p/>
     * Note: The portlet may still be called in a different window
     * state in the next render call, depending on the portlet container / portal.
     *
     * @param portletMode the new portlet mode
     * @throws PortletModeException if the portlet cannot switch to this portlet mode,
     *                              because the portlet or portal does not support it for this markup,
     *                              or the current user is not allowed to switch to this portlet mode.
     *                              To avoid this exception the portlet can check the allowed
     *                              portlet modes with <code>Request.isPortletModeAllowed()</code>.
     * @throws java.lang.IllegalStateException
     *                              if the method is invoked after <code>sendRedirect</code> has been called.
     */

    public void setPortletMode(PortletMode portletMode)
            throws PortletModeException;


    /**
     * Instructs the portlet container to send a redirect response
     * to the client using the specified redirect location URL.
     * <p/>
     * This method only accepts an absolute URL (e.g.
     * <code>http://my.co/myportal/mywebap/myfolder/myresource.gif</code>)
     * or a full path URI (e.g. <code>/myportal/mywebap/myfolder/myresource.gif</code>).
     * If required,
     * the portlet container may encode the given URL before the
     * redirection is issued to the client.
     * <p/>
     * The sendRedirect method can not be invoked after any of the
     * following methods of the ActionResponse interface has been called:
     * <ul>
     * <li>setPortletMode
     * <li>setWindowState
     * <li>setRenderParameter
     * <li>setRenderParameters
     * </ul>
     *
     * @throws java.lang.IllegalStateException
     *          if the method is invoked after any of above mentioned methods of
     *          the ActionResponse interface has been called.
     * @param		location	the redirect location URL
     * @exception	java.io.IOException if an input or output exception occurs.
     * @exception	java.lang.IllegalArgumentException if a relative path URL is given
     */

    public void sendRedirect(String location)
            throws java.io.IOException;


    /**
     * Sets a parameter map for the render request.
     * <p/>
     * All previously set render parameters are cleared.
     * <p/>
     * These parameters will be accessible in all
     * sub-sequent render calls via the
     * <code>PortletRequest.getParameter</code> call until
     * a new request is targeted to the portlet.
     * <p/>
     * The given parameters do not need to be encoded
     * prior to calling this method.
     *
     * @param parameters Map containing parameter names for
     *                   the render phase as
     *                   keys and parameter values as map
     *                   values. The keys in the parameter
     *                   map must be of type String. The values
     *                   in the parameter map must be of type
     *                   String array (<code>String[]</code>).
     * @throws java.lang.IllegalStateException
     *          if the method is invoked after <code>sendRedirect</code> has been called.
     * @exception	java.lang.IllegalArgumentException if parameters is <code>null</code>, if
     * any of the key/values in the Map are <code>null</code>,
     * if any of the keys is not a String, or if any of
     * the values is not a String array.
     */

    public void setRenderParameters(java.util.Map parameters);


    /**
     * Sets a String parameter for the render request.
     * <p/>
     * These parameters will be accessible in all
     * sub-sequent render calls via the
     * <code>PortletRequest.getParameter</code> call until
     * a request is targeted to the portlet.
     * <p/>
     * This method replaces all parameters with the given key.
     * <p/>
     * The given parameter do not need to be encoded
     * prior to calling this method.
     *
     * @param key   key of the render parameter
     * @param value value of the render parameter
     * @throws java.lang.IllegalStateException
     *          if the method is invoked after <code>sendRedirect</code> has been called.
     * @exception	java.lang.IllegalArgumentException if key or value are <code>null</code>.
     */

    public void setRenderParameter(String key, String value);


    /**
     * Sets a String array parameter for the render request.
     * <p/>
     * These parameters will be accessible in all
     * sub-sequent render calls via the
     * <code>PortletRequest.getParameter</code> call until
     * a request is targeted to the portlet.
     * <p/>
     * This method replaces all parameters with the given key.
     * <p/>
     * The given parameter do not need to be encoded
     * prior to calling this method.
     *
     * @param key    key of the render parameter
     * @param values values of the render parameter
     * @throws java.lang.IllegalStateException
     *          if the method is invoked after <code>sendRedirect</code> has been called.
     * @exception	java.lang.IllegalArgumentException if key or value are <code>null</code>.
     */

    public void setRenderParameter(String key, String[] values);


}


