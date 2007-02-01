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

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class I18n {
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String ASCII_CHARSET = "ISO-8859-1";
    
    static public String print(String s) {

	String retStr = null;
	if (s != null) {	    
	    StringBuffer buf = new StringBuffer();
	    for (int i = 0; i < s.length(); i++) {
		int c = (int)s.charAt(i);
		if (c >= 32 && c < 127) buf.append(s.charAt(i));
		else {
		    buf.append("\\u");
		    String n = "";
		    for (int j = 0; j < 4; j++) {
			n = "" + Character.forDigit(c & 0xf, 16) + n;
			c = c >> 4;
		    }
		    buf.append(n);
		}
	    }
	    retStr = buf.toString();
	}
	return retStr;
    }

    static public String decodeCharset(String s, String charset) {
	String retStr = null;

	if (s != null) {
	    try {
		byte buf[] = s.getBytes(ASCII_CHARSET);
		retStr = new String(buf, 0, buf.length, charset);
	    } catch (UnsupportedEncodingException uee) {
		retStr = s;
	    }
	}
	return retStr;	
    }

    static public String encodeCharset(String s, String charset) {
	String retStr = null;

	if (s != null) {
	    try {
		byte buf[] = s.getBytes(charset);
		retStr = new String(buf, 0, buf.length, ASCII_CHARSET);
	    } catch (UnsupportedEncodingException uee) {
		retStr = s;
	    }
	}
	return retStr;	
    }
   
    static public boolean isAscii(String s) {
	// perform i18n check - the equals test returns false if there are
	// any chars with value > 256, i.e., multi-byte characters.

	boolean retValue = true;

	if (s != null) {
	    try {
		if(!s.equals(new String(s.getBytes(ASCII_CHARSET), 
					ASCII_CHARSET))) {
		    // the name does contain non-latin chars
		    retValue = false;
		}
	    } catch (java.io.UnsupportedEncodingException uee) {
		// we should never reach this.
		retValue = false;
	    }
	}
	return retValue;
    }

    static private String format(MessageFormat mf, Object o) {
	String msg = mf.format(new Object[] { o },
			       new StringBuffer(), 
			       null).toString();

	return msg;
    }
    
    static private String format(MessageFormat mf, Object [] array) {
	String msg = mf.format(array,
			       new StringBuffer(), 
			       null).toString();

	return msg;
    }
    
    static public String format(
	String pattern, Long j, Locale l) {

	MessageFormat mf = new MessageFormat("");
	mf.setLocale(l);
	mf.applyPattern(pattern);
	String msg = format(mf, j);

	return msg;
    }
    
    static public String format(
	String pattern, Integer i, Locale l) {

	MessageFormat mf = new MessageFormat("");
	mf.setLocale(l);
	mf.applyPattern(pattern);
	String msg = format(mf, i);

	return msg;
    }
    
    static public String format(
	String pattern, Date d, TimeZone tz, Locale l) {

	MessageFormat mf = new MessageFormat("");
	mf.setLocale(l);
	mf.applyPattern(pattern);
	((DateFormat)mf.getFormats()[0]).setTimeZone(tz);

	//
	// possibly two formats, one for date and one for time
	// depends on pattern, which is in the 
	//
	DateFormat df1 = ((DateFormat)mf.getFormats()[0]);
	if (df1 != null) {
	    df1.setTimeZone(tz);
	}
	
	DateFormat df2 = ((DateFormat)mf.getFormats()[1]);
	if (df2 != null) {
	    df2.setTimeZone(tz);
	}
	
	return format(mf, d);
    }

    static public String format(
      String pattern, TimeZone tz, Locale l) {

      MessageFormat mf = new MessageFormat("");
      mf.setLocale(l);
      mf.applyPattern(pattern);

      Object [] arguments = {
	  new String(tz.getID()),
	  new String(tz.getDisplayName(false,TimeZone.SHORT,l)),
	  new String(tz.getDisplayName(false,TimeZone.LONG,l))
      };

      return format(mf,arguments);
    }
        /**
     * Replacement URLEncoder.encode() method for i18n purposes
     * Uses our replacement IURLEncoder class due to bug in java URLEncoder class
     * encodes twice because the PAPI decodes once automatically (via the Servlet API) 
     * and then automatically does charset decoding with possibly the wrong charset 
     * so we need to hide behind an extra layer of URLEncoding
     * @param enc string to encode, converts to UTF8 first
     **/
    static public String IURLEncode(String enc) {
	String retValue = null;

	try {
	    retValue = IURLEncoder.encode(IURLEncoder.encode(new String(enc.getBytes(DEFAULT_CHARSET), ASCII_CHARSET)));
	} catch (Exception e) {
	    retValue = enc;
	}
	return retValue;
    }
    
    /**
     * Replacement URLDecoder.decode() method for i18n purposes
     * Uses our replacement IURLDecoder class due to bug in java URLDecoder class
     * @param enc string to decode, converts from UTF8 first
     **/
    static public String IURLDecode(String enc) {
	String retValue = null;
	try {
	    String dc = new String(IURLDecoder.decode(enc));
	    retValue = new String(dc.getBytes(ASCII_CHARSET), DEFAULT_CHARSET);
	} catch (Exception e) {
	    retValue = enc;
	}
	return retValue;	
    }
}
