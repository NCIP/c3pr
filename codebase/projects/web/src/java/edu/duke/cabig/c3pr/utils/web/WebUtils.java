package edu.duke.cabig.c3pr.utils.web;

import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;

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

	public static Boolean isAdmin() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		if (auth != null) {
			GrantedAuthority[] groups = auth.getAuthorities();
			for (GrantedAuthority ga : groups) {
				if (ga.getAuthority().endsWith("admin")) {
					return new Boolean(true);
				}
			}
		}
		return new Boolean(false);
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

}
