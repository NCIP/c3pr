package edu.duke.cabig.c3pr.utils.web;

import gov.nih.nci.cabig.ctms.domain.CodedEnum;

import java.util.LinkedHashMap;
import java.util.Map;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;

public class WebUtils {

	/**
	* This method will create, options from an enumeration.
	* @param codedEnum
	* @param blankValueLabel
	* @return
	*/
	public static Map<Object, Object> collectOptions(CodedEnum<? extends Object>[] codedEnumValues, String blankValueLabel){
		Map<Object, Object> options = new LinkedHashMap<Object, Object>();
		if (blankValueLabel != null) options.put("", blankValueLabel);
		for (int i = 0; i < codedEnumValues.length; i++){
			options.put(((Enum)codedEnumValues[i]).name(), codedEnumValues[i].getDisplayName());
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


}
