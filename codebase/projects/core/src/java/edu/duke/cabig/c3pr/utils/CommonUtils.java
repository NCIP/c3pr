package edu.duke.cabig.c3pr.utils;

import java.util.Date;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;

public class CommonUtils {

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


    public static String getDateString(Date date){
      if (date != null) {
			return DateUtil.formatDate(date, "MM/dd/yyyy");
		}
		return "";
    }


}
