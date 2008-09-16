package edu.duke.cabig.c3pr.exception;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 23, 2007 Time: 1:45:43 PM To change this template
 * use File | Settings | File Templates.
 */
public class C3PRCodedException extends C3PRBaseException {

    private int exceptionCode;

    private String exceptionMesssage;

    public C3PRCodedException(int exceptionCode, String exceptionMessage, Throwable throwable) {
        super("", throwable);
        this.exceptionCode = exceptionCode;
        this.exceptionMesssage = exceptionMessage;
    }

    public C3PRCodedException(int exceptionCode, String exceptionMessage) {
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
