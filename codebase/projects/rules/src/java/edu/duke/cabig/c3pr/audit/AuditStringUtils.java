/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.audit;

public class AuditStringUtils {

    public static boolean isEmpty(String str) {
        boolean test = true;
        if ((str == null) || (str.length() == 0)) {
            test = true;
        } else {
            test = false;
        }
        return test;
    }

}
