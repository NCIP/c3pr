/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.Error;
import org.springframework.validation.Errors;


public class ErrorHelper {
	
	public static Errors convertC3PRToSpringErrors(Errors errors, List<Error> c3prErrors){
		 for(Error error :c3prErrors){
			 errors.reject("tempProperty", error.getErrorMessage());
		 }
		
		return errors;
	}

}
