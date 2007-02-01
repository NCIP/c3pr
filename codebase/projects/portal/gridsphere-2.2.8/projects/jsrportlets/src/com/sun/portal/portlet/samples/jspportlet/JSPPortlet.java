/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 * OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */


package com.sun.portal.portlet.samples.jspportlet;

import javax.portlet.GenericPortlet;
import javax.portlet.RenderRequest;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletConfig;
import javax.portlet.WindowState;
import javax.portlet.PortletMode;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;

import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;

public class JSPPortlet extends GenericPortlet {

    private PortletContext pContext;

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        pContext = config.getPortletContext();
    }

    public void doView(RenderRequest request,RenderResponse response)
        throws PortletException,IOException {
        String contentPage = getContentJSP(request);

	response.setContentType(request.getResponseContentType());
        if (contentPage != null && contentPage.length() != 0) {
            try {
                PortletRequestDispatcher dispatcher = pContext.getRequestDispatcher(contentPage);
                dispatcher.include(request, response);
            } catch (IOException e) {
                throw new PortletException("JSPPortlet.doView exception", e);
            }
        }

    }

    public void doEdit(RenderRequest request,RenderResponse response)
        throws PortletException {
        String editPage = getEditJSP(request);

	response.setContentType(request.getResponseContentType());
        if (editPage != null && editPage.length() != 0) {
            try {
                PortletRequestDispatcher dispatcher = pContext.getRequestDispatcher(editPage);
                dispatcher.include(request, response);
            } catch (IOException e) {
                throw new PortletException("JSPPortlet.doEdit exception", e);
            }
        }
    }

    public void doHelp(RenderRequest request,RenderResponse response)
        throws PortletException {
        String helpPage = getHelpJSP(request);

	response.setContentType(request.getResponseContentType());
        if (helpPage != null && helpPage.length() != 0) {
            try {
                PortletRequestDispatcher dispatcher = pContext.getRequestDispatcher(helpPage);
                dispatcher.include(request, response);
            } catch (IOException e) {
                throw new PortletException("JSPPortlet.doHelp exception", e);
            }
        }
    }

    public void processAction (ActionRequest request, ActionResponse actionResponse)
    throws PortletException, java.io.IOException {
        actionResponse.setRenderParameters(request.getParameterMap());
    }

    protected String getContentJSP(RenderRequest request) throws PortletException {
        PortletPreferences pref = request.getPreferences();
        String contentPage = pref.getValue("contentPage","");
        return getLocalizedJSP(request.getLocale(), contentPage);
    }

    protected String getEditJSP(RenderRequest request) throws PortletException {
        PortletPreferences pref = request.getPreferences();
        String editPage = pref.getValue("editPage","");
        return getLocalizedJSP(request.getLocale(), editPage);
    }

    protected String getHelpJSP(RenderRequest request) throws PortletException {
        PortletPreferences pref = request.getPreferences();
        String helpPage = pref.getValue("helpPage","");
	return getLocalizedJSP(request.getLocale(), helpPage);
    }
    
    protected String getLocalizedJSP(Locale locale, String jspPath) {
	String realJspPath = jspPath;

	if (locale != null) {
	    int separator = jspPath.lastIndexOf("/");
        System.err.println("jspPath= " + jspPath);
	    String jspBaseDir = jspPath.substring(0, separator);
	    String jspFileName = jspPath.substring(separator+1);
	    PortletContext pContext = getPortletContext();
	    	  	    
	    String searchPath = getJSPPath(jspBaseDir,
					   locale.toString(),
					   jspFileName);

	    // search the requested JSP from the following location:
	    // <ctxt_root>/<portlet_base_dir>_<language>_<country>/<jsp_file_name>
	    if (pContext.getResourceAsStream(searchPath) != null) {
		realJspPath = searchPath;
	    } else {
		// if the country code is not empty, try to search the 
		// requested JSP from the following location:
		// <ctxt_root>/<portlet_base_dir>_<language>/<jsp_file_name>
		if (!locale.getCountry().equals("")) {
		    searchPath = getJSPPath(jspBaseDir, 
					    locale.getLanguage(),
					    jspFileName);

		    if (pContext.getResourceAsStream(searchPath) != null) {
			realJspPath = searchPath;
		    }
		}
	    }	    
	}
	return realJspPath;
    }

    private String getJSPPath(String jspBaseDir,
			      String localeStr,
			      String jspFileName) {
	StringBuffer sb = new StringBuffer();
	sb.append(jspBaseDir)
	    .append("_")
	    .append(localeStr)
	    .append("/")
	    .append(jspFileName);	  		
	return sb.toString();
    }
}
