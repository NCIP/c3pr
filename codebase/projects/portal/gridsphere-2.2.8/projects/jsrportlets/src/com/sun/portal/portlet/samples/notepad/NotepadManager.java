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

public class NotepadManager extends NoteCategoryManager {

    private String category;
    private int currentPage;
    private ArrayList titleList;
    private ArrayList bodyList;

    public NotepadManager( PortletPreferences pref, String category, int page ) {
        super( pref );
        try {
            this.category = getRealCategoryName(category);
        } catch( Exception e ) {
            this.category = getDefaultCategory();
        }

        if( this.category != null ) {
            titleList = new ArrayList();
            bodyList = new ArrayList();
            String[] titles = this.pref.getValues( TITLE_PREFIX + this.category, EMPTY_STRING_LIST );
            for( int i =0; i < titles.length; i++ ) {
                titleList.add(titles[i]);
            }
            String[] bodies = this.pref.getValues( BODY_PREFIX + this.category, EMPTY_STRING_LIST );
            for( int i =0; i < bodies.length; i++ ) {
                bodyList.add(bodies[i]);
            }

            int maxPage = (int)Math.ceil( (float)getNoteCount() / (float)displayMax )-1; 
            currentPage = Math.min( page, maxPage );
            currentPage = Math.max( currentPage, 0 );
        }
    }

    public String getCategory() {
        return category;
    }

    public int getNoteCount( ) {
        return titleList.size();
    }

    public int getPage() {
        return currentPage;
    }

    public int getStartIndex() {
        return currentPage * displayMax;
    }

    public int getEndIndex() {
        return Math.min( (currentPage+1)*displayMax, getNoteCount() );
    }

    public boolean hasNextPage() {
        return ( (currentPage+1)*displayMax < getNoteCount() );
    }

    public boolean hasPreviousPage() {
        return currentPage > 0;
    }

    public void store() {
        String[] titles = (String[])titleList.toArray( EMPTY_STRING_LIST );
        String[] bodies = (String[])bodyList.toArray( EMPTY_STRING_LIST );
        try {
           pref.setValues( TITLE_PREFIX + category, titles );
           pref.setValues( BODY_PREFIX + category, bodies );
           pref.store();
        } catch( Exception e ) {
        }
    }

    public void addNote( String title, String body ) {
        titleList.add( title );
        bodyList.add( body );
    }

    public void deleteNote( int index ) {
        titleList.remove( index );
        bodyList.remove( index );
    }

    public String getTitle( int index ) {
        return (String)titleList.get( index );
    }

    public String getNote( int index ) {
        return (String)bodyList.get( index );
    }

    public void changeTitle( int index, String title ) {
        titleList.set( index, title );
    }

    public void changeNote( int index, String note ) {
        bodyList.set( index, note );
    }

    private String getDefaultCategory() {
        if( categories!=null && categories.length>0 ) {
           return categories[0];
        } else {
           return null;
        }
    }

}
