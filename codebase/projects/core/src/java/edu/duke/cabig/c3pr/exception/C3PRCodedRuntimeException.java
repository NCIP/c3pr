/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.exception;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 23, 2007 Time: 1:45:43 PM To change this template
 * use File | Settings | File Templates.
 */
public class C3PRCodedRuntimeException extends C3PRBaseRuntimeException {

    private int exceptionCode;

    private String exceptionMesssage;

    public C3PRCodedRuntimeException(int exceptionCode, String exceptionMessage, Throwable throwable) {
        super("", throwable);
        this.exceptionCode = exceptionCode;
        this.exceptionMesssage = exceptionMessage;
    }

    public C3PRCodedRuntimeException(int exceptionCode, String exceptionMessage) {
        super("");
        this.exceptionCode = exceptionCode;
        this.exceptionMesssage = exceptionMessage;
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public String getCodedExceptionMesssage() {
        return exceptionMesssage;
    }

    @Override
    public String getMessage() {
        return getExceptionCode() + ":" + this.exceptionMesssage + "<" + super.getMessage() + ">";
    }
}
