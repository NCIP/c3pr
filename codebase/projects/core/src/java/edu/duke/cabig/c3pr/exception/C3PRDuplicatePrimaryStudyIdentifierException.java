package edu.duke.cabig.c3pr.exception;

public class C3PRDuplicatePrimaryStudyIdentifierException extends C3PRCodedRuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public C3PRDuplicatePrimaryStudyIdentifierException(int exceptionCode,
			String exceptionMessage) {
		super(exceptionCode, exceptionMessage);
	}

}
