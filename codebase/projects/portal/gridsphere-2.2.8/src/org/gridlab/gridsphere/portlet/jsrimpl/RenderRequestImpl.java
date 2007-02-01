/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: RenderRequestImpl.java,v 1.1.1.1 2007-02-01 20:50:17 kherm Exp $
 */
package org.gridlab.gridsphere.portlet.jsrimpl;

import org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Supports;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;


/**
 * The <CODE>RenderRequest</CODE> represents the request sent to the portlet
 * to handle a render.
 * It extends the PortletRequest interface to provide render request
 * information to portlets.<br>
 * The portlet container creates a <CODE>RenderRequest</CODE> object and
 * passes it as argument to the portlet's <CODE>render</CODE> method.
 * 
 * @see PortletRequest
 * @see ActionRequest
 */
public class RenderRequestImpl extends PortletRequestImpl implements RenderRequest {

    /**
     * Constructor creates a proxy for a HttpServletRequest
     * All PortletRequest objects come from request or session attributes
     *
     * @param req the HttpServletRequest
     */
    public RenderRequestImpl(HttpServletRequest req, PortalContext portalContext, PortletContext portletContext, Supports[] supports) {
        super(req, portalContext, portletContext, supports);
    }


}