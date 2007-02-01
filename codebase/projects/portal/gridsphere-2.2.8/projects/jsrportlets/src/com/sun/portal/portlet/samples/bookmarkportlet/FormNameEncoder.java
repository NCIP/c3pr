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

class FormNameEncoder implements TypeEncoder {

  /** The html object names have to be unique within a document. An 
   *  example where a problem will arise if the above condition is not 
   *  met is, if you have two bookmark channels in the users desktop, 
   *  and if the templates come from the provider directory 
   *  ( i.e. BookmarkProvider) then the html form name is same for both 
   *  the channels and opening a new url by typing in to the channel's 
   *  text field will fail because the javascript target name is ambiguous. 
   *  To avoid this, the html form is  named after the channel name. 
   *  HTML special characters in the channel name is encoded using this 
   *  static method.
   **/

    public String encode(String text) {
        
        StringBuffer escaped = new StringBuffer();
        for (int i=0; i<text.length(); i++) {
            char c = text.charAt(i);
            switch(c) {
            case '!':  
            case '#':  
            case '%':  
	    case '\'':  
	    case ')':  
            case '+':  
            case '-':  
	    case '/':  
	    case ';':  
            case '=':  
            case '?':  
	    case '\\':  
	    case '^':  
            case '`':  
            case '|':  
	    case '~':  
	    case '"':  
            case '$':  
	    case '&':  
	    case '(':  
            case '*':  
            case ',':  
	    case '.':  
	    case ':':  
            case '<':  
            case '>':  
	    case '@':  
	    case '[':  
            case ']':  
            case '{':  
	    case '}':  
	    case ' ':  escaped.append("_"); continue;
            default:   escaped.append(c); continue;
            }
        }
        return escaped.toString();
    }
}
