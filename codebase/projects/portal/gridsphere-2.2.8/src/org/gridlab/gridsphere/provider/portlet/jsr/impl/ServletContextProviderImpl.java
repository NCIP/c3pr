package org.gridlab.gridsphere.provider.portlet.jsr.impl;

import org.gridlab.gridsphere.portlet.jsrimpl.PortletContextImpl;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.portals.bridges.common.ServletContextProvider;

/**
 * ServletContextProvider
 * 
 * @author <a href="mailto:novotny@aei.mpg.de>Jason Novotny</a>
 * @version $Id: ServletContextProviderImpl.java,v 1.1.1.1 2007-02-01 20:51:03 kherm Exp $
 */
public class ServletContextProviderImpl implements ServletContextProvider {

    public ServletContext getServletContext(GenericPortlet portlet) {
        PortletContextImpl ctx = (PortletContextImpl)(portlet.getPortletContext());
        return ctx.getServletContext();
    }

    public HttpServletRequest getHttpServletRequest(GenericPortlet portlet, PortletRequest request) {
        return (HttpServletRequest) ((HttpServletRequestWrapper) request).getRequest();
    }

    public HttpServletResponse getHttpServletResponse(GenericPortlet portlet, PortletResponse response) {
        return (HttpServletResponse) ((HttpServletResponseWrapper) response).getResponse();
    }

}
