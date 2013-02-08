/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
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
