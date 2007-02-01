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

import java.io.ByteArrayOutputStream;
import java.util.BitSet;

/**
 * The class contains a utility method for converting a
 * <code>String</code> into a MIME format called
 * "<code>x-www-form-urlencoded</code>" format.
 * <p>
 * To convert a <code>String</code>, each character is examined in turn:
 * <ul>
 * <li>The ASCII characters '<code>a</code>' through '<code>z</code>',
 *     '<code>A</code>' through '<code>Z</code>', and '<code>0</code>'
 *     through '<code>9</code>' remain the same.
 * <li>The space character '<code>&nbsp;</code>' is converted into a
 *     plus sign '<code>+</code>'.
 * <li>All other characters are converted into the 3-character string
 *     "<code>%<i>xy</i></code>", where <i>xy</i> is the two-digit
 *     hexadecimal representation of the lower 8-bits of the character.
 * </ul>
 *
 * Fix for OutputStreamEncoding problem, code reworked to not use 
 * OutputStreamWriter by Tom Mueller, 7/24/01
 *
 * @author  Herb Jellinek
 * @version 1.15, 06/29/98
 * @since   JDK1.0
 */

public class IURLEncoder {
    static BitSet dontNeedEncoding;
    static final int caseDiff = ('a' - 'A');

    /* The list of characters that are not encoded have been determined by
       referencing O'Reilly's "HTML: The Definitive Guide" (page 164). */

    static {
	dontNeedEncoding = new BitSet(256);
	int i;
	for (i = 'a'; i <= 'z'; i++) {
	    dontNeedEncoding.set(i);
	}
	for (i = 'A'; i <= 'Z'; i++) {
	    dontNeedEncoding.set(i);
	}
	for (i = '0'; i <= '9'; i++) {
	    dontNeedEncoding.set(i);
	}
	dontNeedEncoding.set(' '); /* encoding a space to a + is done in the encode() method */
	dontNeedEncoding.set('-');
	dontNeedEncoding.set('_');
	dontNeedEncoding.set('.');
	dontNeedEncoding.set('*');
	dontNeedEncoding.set('+');
    }

    /**
     * Translates a string into <code>x-www-form-urlencoded</code> format.
     *
     * @param   s   <code>String</code> to be translated.
     * @return  the translated <code>String</code>.
     */

    public static String encode(String s) {
        StringBuffer out = new StringBuffer(s.length());

	for (int i = 0; i < s.length(); i++) {
	    int c = (int)s.charAt(i);
	    if (dontNeedEncoding.get(c)) {
		if (c == ' ') {
		    c = '+';
		}
		out.append((char)c);
	    } else {
		int lowbyte = (c & 0xff);
		out.append('%');
		char ch = Character.forDigit((lowbyte >> 4) & 0xF, 16);
		// converting to use uppercase letter as part of
		// the hex value if ch is a letter.
		if (Character.isLetter(ch)) {
		    ch -= caseDiff;
		}
		out.append(ch);
		ch = Character.forDigit(lowbyte & 0xF, 16);
		if (Character.isLetter(ch)) {
		    ch -= caseDiff;
		}
		out.append(ch);
	    }
	}
	
	return out.toString();
    }   
}
