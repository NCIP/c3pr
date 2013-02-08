/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.exception;

import org.springframework.core.NestedCheckedException;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 23, 2007 Time: 1:45:43 PM To change this template
 * use File | Settings | File Templates.
 */
public class C3PRBaseException extends NestedCheckedException {

    public C3PRBaseException(String string) {
        super(string);
    }

    public C3PRBaseException(String string, Throwable throwable) {
        super(string, throwable);
    }

}
