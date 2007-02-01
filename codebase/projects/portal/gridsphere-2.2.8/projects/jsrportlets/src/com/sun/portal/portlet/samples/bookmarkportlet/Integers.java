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

public class Integers {
    private static int SIZE = 10;
    private static int HEXSIZE = 126;
    static Integer[] integers = null;
    static String[] hexInts = null;

    static {
	integers = new Integer[SIZE];

	for (int i = 0; i < SIZE; i++) {
	    integers[i] = new Integer(i);
	}

        hexInts = new String[HEXSIZE];
        for (int i = 0; i < HEXSIZE; i++) {
            hexInts[i] = Integer.toString(i, 16);
        }

    }

    public static Integer get(int i) {
	Integer retValue = null;
	if (i >= SIZE) {
	    retValue = new Integer(i);
	} else {
	     retValue = integers[i];
	}
	return retValue;
    }

    public static String getHex(int i) {
	String retValue = null;
        if (i >= HEXSIZE) {
            retValue = Integer.toString(i, 16);
        } else {
	    retValue = hexInts[i];
	}
	return retValue;
    }

}	    
