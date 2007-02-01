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

import java.util.*;

public class Target {    
    
    //TBD: "=" should be replaced...
    private static final char DELIMITER = '=';
    
    private static final int NAMES = 0;
    private static final int VALUES = 1;
    
    public static final int INDEX_FIRST = 0;
    public static final int INDEX_LAST = 1;

    private String name = null;
    private String value = null;

    public Target(String t, char sep, int indexType) {
	int index;
	if (indexType == INDEX_FIRST) {
	    index = t.indexOf(sep);
	} else {
	    index = t.lastIndexOf(sep);
	}

	if (index != -1) {
	    name  = t.substring(0,index);
	    value = t.substring(index+1);
	}
    }

    //TBD: "=" should be replaced...
    public Target(String t) {
	this(t, DELIMITER, INDEX_LAST);
    }
    
    public Target(String n, String v) {
	name = n;
	value = v;
    }

    //TBD: "=" should be replaced...
    public String toString() {
	return name + DELIMITER + value;
    }

    public String getName() {
	return name;
    }

    public String getValue() {
	return value;
    }

    public void setName(String n) {
	name = n;
    }

    public void setValue(String v) {
	value = v;
    }

    private static List breakTargetList(List targetStrings, int type) {
	List result = new ArrayList();
	
	Iterator i = targetStrings.iterator();
	while (i.hasNext()) {
	    String s = (String)i.next();
	    Target t = new Target(s);

	    String item = null;
	    if (type == NAMES) {
		item = t.getName();
	    } else {
		item = t.getValue();
	    }
	    result.add(item);
	}

	return result;
    }

    private static Vector breakTargetVector(Vector targetStrings, int type) {
	Vector result = new Vector();
	
	Enumeration e = targetStrings.elements();
	while (e.hasMoreElements()) {
	    String s = (String)e.nextElement();
	    Target t = new Target(s);

	    String item = null;
	    if (type == NAMES) {
		item = t.getName();
	    } else {
		item = t.getValue();
	    }
	    result.add(item);
	}

	return result;
    }  

    public static List getNames(List targetStrings) {
	List result = breakTargetList(targetStrings, NAMES);
	return result;
    }

    public static Vector getNamesVector(Vector targetStrings) {
	Vector result = breakTargetVector(targetStrings, NAMES);
	return result;
    }

    public static List getValues(List targetStrings) {
	List result = breakTargetList(targetStrings, VALUES);
	return result;
    }

    public static Vector getValuesVector(Vector targetStrings) {
	Vector result = breakTargetVector(targetStrings, VALUES);
	return result;
    }      
    
    
}
