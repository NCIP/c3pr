/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.exception;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.Error;

public class C3PRBaseErrorsException extends C3PRBaseRuntimeException {
	
	private List<Error> errors = new ArrayList<Error>();

	public C3PRBaseErrorsException(String string, List<Error> errors) {
		super(string);
		this.errors = errors;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public C3PRBaseErrorsException(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

	public C3PRBaseErrorsException(String string, Throwable throwable) {
		super(string, throwable);
		// TODO Auto-generated constructor stub
	}

}
