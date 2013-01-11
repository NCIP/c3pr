/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.exception;

import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;

/**
 * Indicates a problem with converting an object with one type into another object with a different type.
 * @author dkrylov
 *
 */
public class ConversionException extends C3PRCodedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8565266078236353813L;

	public ConversionException(int exceptionCode, String exceptionMessage,
			Throwable throwable) {
		super(exceptionCode, exceptionMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	public ConversionException(int exceptionCode, String exceptionMessage) {
		super(exceptionCode, exceptionMessage);
		// TODO Auto-generated constructor stub
	}
	
	

}
