/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.exception;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 23, 2007 Time: 1:45:43 PM To change this template
 * use File | Settings | File Templates.
 */
public class MultisiteException extends C3PRCodedRuntimeException{

	public MultisiteException(int exceptionCode, String exceptionMessage) {
		super(exceptionCode, exceptionMessage);
	}
}
