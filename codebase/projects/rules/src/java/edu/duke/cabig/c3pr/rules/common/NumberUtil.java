/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.rules.common;

public class NumberUtil {
    public static int compare(Integer i1, Integer i2) {
        if (i1 == null) return 1;
        if (i2 == null) return -1;
        return i2.compareTo(i1);
    }
}
