/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

public class ApplicationUtils {

    public static boolean isUnix() {
        boolean test = false;
        String str = System.getProperty("os.name");
        int i = str.indexOf("Win");
        if (i != -1) {
            test = false;
        }
        else {
            test = true;
        }
        return test;
    }
}
