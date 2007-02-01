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


package com.sun.portal.portlet.samples.notepad;

import com.sun.portal.portlet.samples.jspportlet.JSPPortlet;

import javax.portlet.GenericPortlet;
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

import java.io.IOException;

public class NotepadPortlet extends JSPPortlet {

    public static final String ERR_TITLE_EMPTY = "1";
    public static final String ERR_BODY_EMPTY = "2";
    public static final String ERR_CATEGORY_EMPTY = "3";
    public static final String ERR_CATEGORY_EXIST = "4";
    public static final String ERR_DISPLAY_MAX = "5";
    public static final String ERR_UNKNOWN = "6";

    //parameters
    public static final String ERROR_CODE = "errorCode";
    public static final String PAGE_NO = "pageNumber";
    public static final String MODE = "mode";
    public static final String NOTE_INDEX = "location";
    public static final String CATEGORY = "category";

    //field names from form
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String LOCATION = "location";

    public static final String CATEGORY_NAME = "categoryName";
    public static final String DISPLAY_MAX = "displayMax";

    // Button name
    public static final String ADD_CATEGORY = "addCategory";
    public static final String DELETE_CATEGORY = "deleteCategory";
    public static final String FINISHED_EDIT = "finished";
    public static final String CANCEL_EDIT = "cancel";

    public static final String ADD_NOTE = "addNote";
    public static final String DELETE_NOTE = "deleteNote";

    public static final String ADD = "add";
    public static final String SAVE = "save";
    public static final String CANCEL_NOTE = "cancel";
   
    public void processAction (ActionRequest request, ActionResponse actionResponse)
        throws PortletException, java.io.IOException {

        PortletPreferences pref = request.getPreferences();
        if( request.getPortletMode().equals( PortletMode.EDIT ) ) {
            NoteCategoryManager mgr = new NoteCategoryManager( pref );
            if( isAction( request, ADD_CATEGORY ) ) {
                String catName = request.getParameter( CATEGORY_NAME );
                if( catName != null && catName.length() > 0 ) {
                    if( !mgr.isValidCategory( catName ) ) {
                        mgr.addCategory( catName );
                        System.err.println("adding " + catName);
                        mgr.store();
                    } else {
                        actionResponse.setRenderParameter( ERROR_CODE, ERR_CATEGORY_EXIST );
                    }
                } else {
                    actionResponse.setRenderParameter( ERROR_CODE, ERR_CATEGORY_EMPTY );
                }
            } else if( isAction( request, FINISHED_EDIT ) ) {
                String displayMaxStr = request.getParameter( DISPLAY_MAX );
                try {
                    int displayMax = Integer.parseInt( displayMaxStr );
                    if( displayMax > 0 ) {
                        mgr.setDisplayMax( displayMax );
                        mgr.store();
                    } else {
                        actionResponse.setRenderParameter( ERROR_CODE, ERR_DISPLAY_MAX );
                    }
                } catch (Exception e ) {
                    actionResponse.setRenderParameter( ERROR_CODE, ERR_DISPLAY_MAX );
                }
            } else if( isAction( request, DELETE_CATEGORY ) ) {
                String[] locations = request.getParameterValues( LOCATION );
                if( locations != null ) {
                    for( int i = locations.length-1; i >= 0; i-- ) {
                        mgr.removeCategory( locations[i] );
                    }
                    mgr.store();
                }
            }

            if( isAction( request, CANCEL_EDIT ) || isAction( request, FINISHED_EDIT )) {
                actionResponse.setPortletMode( PortletMode.VIEW );
            } else {
                actionResponse.setPortletMode( PortletMode.EDIT );
            }
        } else {
            String category = request.getParameter( CATEGORY );
            NotepadManager mgr = new NotepadManager( pref, category, 0 );
            if( isAction( request, ADD ) || isAction( request, SAVE ) ) {
                String title = request.getParameter( TITLE );
                String body = request.getParameter( BODY );
                if( title == null || title.length() == 0 ) {
                    actionResponse.setRenderParameter( ERROR_CODE, ERR_TITLE_EMPTY );
                } else if( body == null || body.length() == 0 ) {
                    actionResponse.setRenderParameter( ERROR_CODE, ERR_BODY_EMPTY );
                } else {
                   if( isAction( request, ADD ) ) {
                       mgr.addNote( title, body );
                   } else {
                       String location = request.getParameter( NOTE_INDEX );
                       int loc = 0;
                       try {
                           loc = Integer.parseInt( location );
                           mgr.changeTitle( loc, title );
                           mgr.changeNote( loc, body );
                       } catch( NumberFormatException e ) {
                           actionResponse.setRenderParameter( ERROR_CODE, ERR_UNKNOWN);
                       }
                   }
                   mgr.store();
                }
            } else if( isAction( request, ADD_NOTE )) {
                actionResponse.setRenderParameter( MODE, "Add" );
            } else if( isAction( request, DELETE_NOTE )) {
                String[] locations = request.getParameterValues( LOCATION );
                if( locations != null ) {
                    for( int i = locations.length-1; i >= 0; i-- ) {
                        int loc = 0;
                        try {
                            loc = Integer.parseInt( locations[i] );
                            mgr.deleteNote( loc );
                        } catch( NumberFormatException e ) {
                            actionResponse.setRenderParameter( ERROR_CODE, ERR_UNKNOWN);
                        }
                    }
                    mgr.store();
                }
            }

            if( isAction( request, ADD_NOTE ) ) {
                actionResponse.setWindowState( WindowState.MAXIMIZED );
            } else {
                actionResponse.setWindowState( WindowState.NORMAL );
            }

            actionResponse.setRenderParameter( CATEGORY, category );

            // go to the last page for adding note, so user can see the newly added
            // note.
            if( isAction( request, ADD ) ) {
               actionResponse.setRenderParameter( PAGE_NO, Integer.toString(Integer.MAX_VALUE));
            } else {
               String pageNo = request.getParameter( PAGE_NO );
               actionResponse.setRenderParameter( PAGE_NO, pageNo );
            }
        }
    }

    private boolean isAction(ActionRequest request, String action)
    {
        return (request.getParameter( action ) != null); 
    }
}
