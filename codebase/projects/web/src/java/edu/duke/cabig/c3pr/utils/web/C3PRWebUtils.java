/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import edu.duke.cabig.c3pr.constants.ConsentingMethod;

public class C3PRWebUtils {
	public static boolean contains(List list, Object o) {
	      return list.contains(o);
	}
	
	public static boolean containsConsentingMethod(List list, String consentMethod) {
		ConsentingMethod consentingMethod = ConsentingMethod.getByCode(consentMethod);
	      return list.contains(consentingMethod);
	}
	
	/**
	 * Substring method for the UI.
	 *
	 * @param stringToBeManipulated the string to be manipulated
	 * @param startIndex the start index
	 * @param endIndex the end index
	 * @return the string
	 */
	public static String substring(String stringToBeManipulated, int beginIndex, int endIndex){
		if(!StringUtils.isBlank(stringToBeManipulated)){
			return stringToBeManipulated.substring(beginIndex, endIndex);
		}
		return stringToBeManipulated;
	}
	
	public static int indexOf(String stringToBeManipulated, char token){
		return stringToBeManipulated.indexOf(token);
	}

}
