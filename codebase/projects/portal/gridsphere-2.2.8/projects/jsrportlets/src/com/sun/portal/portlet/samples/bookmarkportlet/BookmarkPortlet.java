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

package com.sun.portal.portlet.samples.bookmarkportlet;

import java.io.IOException;
import java.util.Vector;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.ValidatorException;

import com.sun.portal.portlet.samples.jspportlet.JSPPortlet;

public class BookmarkPortlet extends JSPPortlet {

    // file names     
    public static final String CANCEL_EDIT = "Cancel";
    public static final String FINISHED_EDIT = "Finished";
    public static final String ADD_RESOURCE = "Add Resource";
    public static final String RESOURCE_COUNT = "resourceCount";
    public static final String RESOURCE_NAME = "resourceName";
    public static final String RESOURCE_URL = "resourceURL";
    public static final String WINDOW_PREF = "windowPref";

    // name of JSP pages 
    public static final String BOOKMARK_CONTENT_PAGE =
        "bookmarkContentPage";
    public static final String BOOKMARK_EDIT_PAGE =
        "bookmarkEditPage";
    public static final String BOOKMARK_HELP_PAGE =
        "bookmarkHelpPage";    

    public static final String TARGETS = "targets";
    public static final String CONTENT_TEMPLATE = 
        "display.template";
    public static final String URL_WRAP_TEMPLATE = 
        "urlWrapper.template";
    public static final String IDENTIFIER = 
        "BookmarkPortlet";    
    public static final String ENCODER_NAME_HTML = 
        "com.sun.portal.portlet.samples.bookmarkportlet.HTMLEncoder";
    public static final String ENCODER_NAME_FORMNAME = 
        "com.sun.portal.portlet.samples.bookmarkportlet.FormNameEncoder";
    public static TypeEncoder HTML_ENCODER = null;
    public static TypeEncoder FORMNAME_ENCODER = null;
    public static final String OWN_WINDOW = 
        "Open each page in its own window.";
    public static final String SINGLE_WINDOW =
        "Open all pages in a single browser window.";
    public static final String MAIN_WINDOW =
        "Open all pages in the main desktop window.";
    public static final String NON_ASCII_URL = 
        "Resource URL must be ASCII characters!";
    public static final String EMPTY_URL = 
        "ERROR: Please enter a value for the URL: ";
    public static final String EMPTY_NAME = 
        "ERROR: Please enter a value for the Bookmark Name: ";
    public static final String EMPTY_NAME_URL = 
        "ERROR: Please enter a value for the Bookmark Name and URL";
    public static final String REMOVE_ERROR = 
        "ERROR: Please remove a bookmark by selecting the remove check box";
    public static final String RENDER_PARAM_ERROR =
        "render_param_error";
    
    public void init(PortletConfig config) throws PortletException {    
	super.init(config);
        
	try {            
	    if (HTML_ENCODER == null) {
		HTML_ENCODER = 
		    (TypeEncoder)Class.forName(ENCODER_NAME_HTML)
		    .newInstance(); 
	    }
	    if (FORMNAME_ENCODER == null) {
		FORMNAME_ENCODER = 
		    (TypeEncoder)Class.forName(ENCODER_NAME_FORMNAME)
		    .newInstance();
	    }
	} catch (ClassNotFoundException cnfe) {
	    throw new PortletException("BookmarkPortlet static initializer",
				       cnfe);
	} catch (InstantiationException ie) {
	    throw new PortletException("BookmarkPortlet static initializer",
				       ie);
	} catch (IllegalAccessException iae) {
	    throw new PortletException("BookmarkPortlet static initializer",
				       iae);
	}
    }
    
    public void processAction(ActionRequest request, 
                              ActionResponse response) 
        throws PortletException {            
      
        boolean errorFlag = false;
	Vector targets = new Vector();

        if (isAction(request, CANCEL_EDIT)) {
            response.setPortletMode(PortletMode.VIEW);     
        } else {
	    // find out which book marks were checked to remove
	    // 
	    String rc = request.getParameter(RESOURCE_COUNT);
        
	    int resourceCount = Integer.parseInt(rc);

	    for (int i = 0; i < resourceCount; i++) {            
		String checkBoxValue = request.getParameter("remove"+i);
		Integer removeFlag = 
		    (checkBoxValue == null) ? 
		    Integers.get(0) : Integer.valueOf(checkBoxValue);	    
		//
		// if the box was NOT check then we will keep it
		// in the bookmark list. Build the name and url 
		// vectors to store in the hashtable
		//
		if (removeFlag.intValue() != 1) {
		    String nameString = "name"+i;
		    String urlString = "url"+i; 
		    
		    String name = request.getParameter(nameString);
		    String url = request.getParameter(urlString);
		    
		    // Error handle when one of the existing bookmarks
                    // is changed
                    if (!isParamValid(name) &&
                        !isParamValid(url)) {
			response.setRenderParameter(RENDER_PARAM_ERROR, 
						    REMOVE_ERROR);
			errorFlag = true;
		    } else if (!isParamValid(name)) {
			response.setRenderParameter(RENDER_PARAM_ERROR, 
						    EMPTY_NAME+url);
			errorFlag = true;
		    } else if (!isParamValid(url)) {
			response.setRenderParameter(RENDER_PARAM_ERROR, 
						    EMPTY_URL+name);
			errorFlag = true;
		    }		    
		    if (!I18n.isAscii(url)) {
			response.setRenderParameter(RENDER_PARAM_ERROR, 
						    NON_ASCII_URL);	
			errorFlag = true;
		    }
		    
		    // if input error is detected, return to the edit page 
		    // with error message
		    if(errorFlag) {
			response.setPortletMode(PortletMode.EDIT);
		    } else {		    
			Target target = new Target(name, url);
			
			if ((target.getValue().indexOf("://") == -1) &&
			    (!target.getValue().startsWith("/")) ) {
			    // add http if there's no protocol specificed
			    // and it doesn't start with a '/'
			    target.setValue("http://"+target.getValue());
			}
			targets.add(target.toString());
		    }
		}		
	    }
	    
	    if (!errorFlag) {
		//
		// get the new bookmark information
		//
		String newName = request.getParameter(RESOURCE_NAME);
		String newURL = request.getParameter(RESOURCE_URL);
		
		if (isAction(request, ADD_RESOURCE)) {
		    response.setPortletMode(PortletMode.EDIT);            
		}
		
                if (isParamValid(newName) &&
                    isParamValid(newURL)) {                    
		    
		    if (!I18n.isAscii(newURL)) {
			response.setRenderParameter(RENDER_PARAM_ERROR, 
						    NON_ASCII_URL);
			errorFlag = true;
		    } else {		    
			Target target = new Target(newName, newURL);
			
			if ((target.getValue().indexOf("://") == -1) &&
			    (!target.getValue().startsWith("/")) ) {
			    
			    // add http if there's no protocol specificed
			    // and it doesn't start with a '/'
			    target.setValue("http://" + target.getValue());
			}
			
			//
			// add the new target to the vector of all targets
			//
			targets.add(target.toString());
		    }
		} else {

                    if (!isParamValid(newName) &&
                        !isParamValid(newURL)) {
			if(isAction(request, ADD_RESOURCE)) { 
			    response.setRenderParameter(RENDER_PARAM_ERROR,
							EMPTY_NAME_URL); 
			    errorFlag = true;
			}
		    } else if (!isParamValid(newName)) {
			response.setRenderParameter(RENDER_PARAM_ERROR,
						    EMPTY_NAME+newURL);    
			errorFlag = true;
		    } else {
			response.setRenderParameter(RENDER_PARAM_ERROR,
						    EMPTY_URL+newName);    
			errorFlag = true;
		    } 
		}
		
		if (errorFlag) {

		    // do necessary settings and return
		    response.setPortletMode(PortletMode.EDIT);    
		    response.setRenderParameter(RESOURCE_NAME, newName);
		    response.setRenderParameter(RESOURCE_URL, newURL);        
		} else {
                    if (isAction(request, FINISHED_EDIT)) {
			response.setPortletMode(PortletMode.VIEW);
		    } 

		    PortletPreferences pref = request.getPreferences();
		    //
		    // put the targets
		    //
		    String[] targetElements = new String[targets.size()];
		    pref.setValues(TARGETS, 
				   (String [])targets.toArray(targetElements)); 
		    //Window Preference
		    String storedWindowPref = pref.getValue(WINDOW_PREF, "");
		    String windowPref = request.getParameter(WINDOW_PREF);
		    
		    if ((windowPref != null) && 
			(!windowPref.equals(storedWindowPref))) {
			pref.setValue(WINDOW_PREF, windowPref);
		    }
		    
		    // storing the preferences
		    try {            
			pref.store();
		    } catch (ValidatorException ve) {
			throw new PortletException("BookmarkPortlet.processAction",
						   ve);
		    } catch (IOException ioe) {
			throw new PortletException("BookmarkPortlet.processAction",
						   ioe);            
		    }                
		}
	    }
	}
    }

    private boolean isAction(ActionRequest request, String action)
    {

        return (request.getParameter( action ) != null); 
    }

    private boolean isParamValid(String paramValue)
    {
        return ((paramValue != null) &&
                (!paramValue.trim().equals("")));        
    }

    protected String getContentJSP(RenderRequest request) 
	throws PortletException {

        PortletPreferences pref = request.getPreferences();
        String contentPage = pref.getValue(BOOKMARK_CONTENT_PAGE,"");
        return getLocalizedJSP(request.getLocale(), contentPage);
    }
    
    protected String getEditJSP(RenderRequest request) 
	throws PortletException {

        PortletPreferences pref = request.getPreferences();
        String editPage = pref.getValue(BOOKMARK_EDIT_PAGE,"");
        return getLocalizedJSP(request.getLocale(), editPage);
    }

    protected String getHelpJSP(RenderRequest request) 
	throws PortletException {

        PortletPreferences pref = request.getPreferences();
        String helpPage = pref.getValue(BOOKMARK_HELP_PAGE,"");
	return getLocalizedJSP(request.getLocale(), helpPage);
    }
}
