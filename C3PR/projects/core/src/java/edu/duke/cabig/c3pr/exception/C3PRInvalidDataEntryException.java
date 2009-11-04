package edu.duke.cabig.c3pr.exception;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Error;

public class C3PRInvalidDataEntryException extends C3PRBaseErrorsException {
	

	public C3PRInvalidDataEntryException(String string, List<Error> errors) {
		super(string,errors);
		// TODO Auto-generated constructor stub
	}

	public C3PRInvalidDataEntryException(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

	public C3PRInvalidDataEntryException(String string, Throwable throwable) {
		super(string, throwable);
		// TODO Auto-generated constructor stub
	}

}
