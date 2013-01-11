/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 23, 2007 Time: 1:20:32 PM To change this template
 * use File | Settings | File Templates.
 */
public class C3PRBaseRuntimeException extends NestedRuntimeException {

    public C3PRBaseRuntimeException(String string) {
        super(string);
    }

    public C3PRBaseRuntimeException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public Throwable getRootCause() {
        return super.getRootCause() == null ? getCause() : super.getRootCause();
    }
}
