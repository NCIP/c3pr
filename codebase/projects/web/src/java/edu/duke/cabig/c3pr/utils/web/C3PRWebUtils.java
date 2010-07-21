package edu.duke.cabig.c3pr.utils.web;

import java.util.List;

import edu.duke.cabig.c3pr.constants.ConsentingMethod;

public class C3PRWebUtils {
	public static boolean contains(List list, Object o) {
	      return list.contains(o);
	   }
	
	public static boolean containsConsentingMethod(List list, String consentMethod) {
		ConsentingMethod consentingMethod = ConsentingMethod.getByCode(consentMethod);
	      return list.contains(consentingMethod);
	   }

}
