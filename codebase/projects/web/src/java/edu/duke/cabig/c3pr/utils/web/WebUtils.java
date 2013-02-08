/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils.web;

import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class WebUtils extends org.springframework.web.util.WebUtils{

	/**
	 * This method will create, options from an enumeration.
	 * 
	 * @param codedEnum
	 * @param blankValueLabel
	 * @return
	 */
	public static Map<Object, Object> collectOptions(
			CodedEnum<? extends Object>[] codedEnumValues,
			String blankValueLabel) {
		Map<Object, Object> options = new LinkedHashMap<Object, Object>();
		if (blankValueLabel != null)
			options.put("", blankValueLabel);
		for (int i = 0; i < codedEnumValues.length; i++) {
			options.put(((Enum) codedEnumValues[i]).name(), codedEnumValues[i]
					.getDisplayName());
		}
		return options;
	}
	
	public static List<Object> collectOptions(CodedEnum<? extends Object>[] codedEnumValues) {
		List<Object> options = new ArrayList<Object>();
		for (int i = 0; i < codedEnumValues.length; i++) {
			options.add(((Enum) codedEnumValues[i]));
		}
		return options;
	}

	/**
	 * Returns the previous page, based on the request parameter _page
	 * 
	 * @param request
	 * @return
	 */
	public static int getPreviousPage(HttpServletRequest request) {
		String pg = request.getParameter("_page");
		if (StringUtils.isEmpty(pg))
			return -1;
		return Integer.parseInt(pg);
	}

	public static int getTargetPage(HttpServletRequest request) {
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if (paramName.startsWith("_target")) {
				return Integer.parseInt(paramName.substring(7));
			}
		}
		return -1;
	}
	
	public static List<String> removeEmptyStrings(List<String> collection){
		List<String> newList = new ArrayList<String>();
		for(String str : collection){
			if(!StringUtils.isBlank(str)){
				newList.add(str);
			}
		}
		return newList ;
	}

}
