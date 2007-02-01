/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletResponseImpl.java,v 1.1.1.1 2007-02-01 20:50:16 kherm Exp $
 */
package org.gridlab.gridsphere.portlet.jsrimpl;

import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import javax.portlet.PortalContext;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The <CODE>PortletResponse</CODE> defines the base interface to assist a
 * portlet in creating and sending a response to the client.
 * The portlet container uses two specialized versions of this interface
 * when invoking a portlet, <CODE>ActionResponse</CODE> and
 * <CODE>RenderResponse</CODE>. The  portlet container creates these
 * objects and passes them as arguments to the portlet's <CODE>processAction</CODE>
 * and <CODE>render</CODE> methods.
 *
 * @see javax.portlet.ActionResponse
 * @see javax.portlet.RenderResponse
 */
public abstract class PortletResponseImpl extends HttpServletResponseWrapper implements PortletResponse {
    protected HttpServletRequest req = null;

    protected PortalContext portalContext = null;

    /**
     * Constructs an instance of SportletResponse using an
     * <code>HttpServletResponse</code> as a proxy
     *
     * @param res the <code>HttpServletRequest</code>
     */
    public PortletResponseImpl(HttpServletRequest req, HttpServletResponse res, PortalContext portalContext) {
        super(res);
        this.req = req;
        this.portalContext = portalContext;
        Map map = (Map)req.getAttribute(SportletProperties.PORTAL_PROPERTIES);
        if (map == null) {
            req.setAttribute(SportletProperties.PORTAL_PROPERTIES, new HashMap());
        }
    }

    /**
     * Adds a String property to an existing key to be returned to the portal.
     * <p/>
     * This method allows response properties to have multiple values.
     * <p/>
     * Properties can be used by portlets to provide vendor specific
     * information to the portal.
     *
     * @param key   the key of the property to be returned to the portal
     * @param value the value of the property to be returned to the portal
     * @throws java.lang.IllegalArgumentException
     *          if key is <code>null</code>.
     */
    public void addProperty(String key, String value) {
        if (key == null) throw new IllegalArgumentException("key is NULL");
        Map properties = (Map)req.getAttribute(SportletProperties.PORTAL_PROPERTIES);
        List vals = (List) properties.get(key);
        if (vals == null) {
            vals = new ArrayList();
        }
        vals.add(value);
        properties.put(key, vals);
    }

    /**
     * Sets a String property to be returned to the portal.
     * <p/>
     * Properties can be used by portlets to provide vendor specific
     * information to the portal.
     * <p/>
     * This method resets all properties previously added with the same key.
     *
     * @param key   the key of the property to be returned to the portal
     * @param value the value of the property to be returned to the portal
     * @throws java.lang.IllegalArgumentException
     *          if key is <code>null</code>.
     */
    public void setProperty(String key, String value) {
        if (key == null) throw new IllegalArgumentException("key is NULL");
        Map properties = (Map)req.getAttribute(SportletProperties.PORTAL_PROPERTIES);
        List vals = new ArrayList();
        vals.add(value);
        properties.put(key, vals);
    }


    /**
     * Returns the encoded URL of the resource, like servlets,
     * JSPs, images and other static files, at the given path.
     * <p/>
     * Some portal/portlet-container implementation may require
     * those URLs to contain implementation specific data encoded
     * in it. Because of that, portlets should use this method to
     * create such URLs.
     * <p/>
     * The <code>encodeURL</code> method may include the session ID
     * and other portal/portlet-container specific information into the URL.
     * If encoding is not needed, it returns the URL unchanged.
     *
     * @param path the URI path to the resource. This must be either
     *             an absolute URL (e.g.
     *             <code>http://my.co/myportal/mywebap/myfolder/myresource.gif</code>)
     *             or a full path URI (e.g. <code>/myportal/mywebap/myfolder/myresource.gif</code>).
     * @return the encoded resource URL as string
     * @throws java.lang.IllegalArgumentException
     *          if path doesn't have a leading slash or is not an absolute URL
     */
    public String encodeURL(String path) {
        if (path.indexOf("://") == -1 && !path.startsWith("/")) {
            throw new IllegalArgumentException("only absolute URLs or full path URIs are allowed");
        }
        return this.getHttpServletResponse().encodeURL(path);
    }


    protected HttpServletResponse getHttpServletResponse() {
        return (HttpServletResponse) super.getResponse();
    }

}


