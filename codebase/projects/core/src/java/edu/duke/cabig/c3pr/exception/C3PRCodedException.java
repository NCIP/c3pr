package edu.duke.cabig.c3pr.exception;

import gov.nih.nci.cabig.ctms.domain.CodedEnum;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Aug 23, 2007
 * Time: 1:45:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class C3PRCodedException extends C3PRBaseException {
	
	private int exceptionCode;
	private String exceptionMesssage;
	
    public C3PRCodedException(int exceptionCode, String exceptionMessage, String string, Throwable throwable) {
        super(string, throwable);
        this.exceptionCode=exceptionCode;
        this.exceptionMesssage=exceptionMessage;
    }
    
    public C3PRCodedException(int exceptionCode, String exceptionMessage, String string) {
        super(string);
        this.exceptionCode=exceptionCode;
        this.exceptionMesssage=exceptionMessage;
    }
    
    public C3PRCodedException(int exceptionCode, String exceptionMessage) {
        super("");
        this.exceptionCode=exceptionCode;
        this.exceptionMesssage=exceptionMessage;
    }

    public int getExceptionCode() {
		return exceptionCode;
	}
	
	public String getExceptionMesssage() {
		return exceptionMesssage;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return getExceptionCode()+":"+this.exceptionMesssage+"<"+super.getMessage()+">";
	}
}
