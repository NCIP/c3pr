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

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import java.util.ArrayList;

public class NoteCategoryManager {

    protected static final String[] EMPTY_STRING_LIST = new String[0];
    protected static String CATEGORY = "categories";
    protected static String DISPLAY_MAX = "displayMax";

    protected static String TITLE_PREFIX = "$$TITLE$$";
    protected static String BODY_PREFIX = "$$BODY$$";


    protected PortletPreferences pref;
    protected String[] categories;
    protected int displayMax;

    public NoteCategoryManager( PortletPreferences pref ) {
        this.pref = pref;
        categories = pref.getValues( CATEGORY, EMPTY_STRING_LIST );

        try {
            String strMax = pref.getValue( DISPLAY_MAX, "5" );
            displayMax = Integer.parseInt( strMax );
        } catch( Exception e ) {
            displayMax = 5;
        }

    }

    public String[] getAllCategories() {
        return categories;
    }

    public int getNoteCount( String category ) {
        try {
           String[] titles = pref.getValues( TITLE_PREFIX + category, EMPTY_STRING_LIST );
           return titles.length;
        } catch( Exception e ) {
           return 0;
        }
    }

    public void addCategory( String category ) {
        if( !isValidCategory(category) ) {
            categories = appendToArray( categories, category );
            try {
                pref.setValues( CATEGORY, categories );
            } catch( Exception ue ) {
            }
        }
    }

    public void removeCategory( String category ) {
        try {
            category = getRealCategoryName( category );
            categories = removeFromArray( categories, category );
            pref.setValues( CATEGORY, categories );
            pref.reset( TITLE_PREFIX + category );
            pref.reset( BODY_PREFIX + category );
        } catch( Exception e ) {
        }
    }

    public int getDisplayMax() {
        return displayMax;
    }

    public void setDisplayMax( int max ) {
        displayMax = max;
        try {
            pref.setValue( DISPLAY_MAX, Integer.toString(max) );
        } catch( Exception e ) {
        }
    }

    public void store() {
        try {
            pref.store();
        } catch( Exception e ) {
        }
    }

    protected String[] appendToArray( String[] original, String element ) {
        ArrayList list = new ArrayList();
        for( int i = 0; i < original.length; i++ ) {
            list.add( original[i] );
        }
        list.add( element );
        return (String[])list.toArray( EMPTY_STRING_LIST );
    }

    protected String[] removeFromArray( String[] original, String element ) {
        ArrayList list = new ArrayList();
        for( int i = 0; i < original.length; i++ ) {
            if( !original[i].equals(element) ) {
                list.add( original[i] );
            }
        }
        return (String[])list.toArray( EMPTY_STRING_LIST );
    }

    public boolean isValidCategory( String category ) {
        if( categories != null ) {
            for( int i = 0; i < categories.length; i++ ) {
                if( category.equals( categories[i] ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    protected String getRealCategoryName( String category ) throws Exception {

        // Determine what is the real category we are dealing with.
        // If it is a valid category, no doubt about it.
        // Otherwise, check whether the category is a valid index to the
        // category list.  Finally use the default.

        String catName = category;

        if( category == null || category.length() == 0 ) {
            throw new Exception();
        } else {
            if( !isValidCategory( category ) ) {
                int intCategory = Integer.parseInt( category );
                if( intCategory < categories.length ) {
                    catName = categories[intCategory];
                } else {
                    throw new Exception();
                }
            }
        }
        return catName;
    }
}
