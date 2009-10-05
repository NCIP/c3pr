package edu.duke.cabig.c3pr.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.User;

import edu.duke.cabig.c3pr.domain.Error;


public class CommonUtils {

	public static String getLoggedInUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if (auth != null) {
            return ((User)auth.getPrincipal()).getUsername();
        }
        return "";
    }
	
    public static String getDateString(Date date){
      if (date != null) {
    	  		return DateUtil.formatDate(date, "MM/dd/yyyy");
		}
		return "";
    }


    public static Date getOldDate(Date date, int days){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();

    }

    public static String listErrors(List<Error> errors){
    	
    	StringBuffer sb = new StringBuffer();
    	for(int i=0;i< errors.size();i++){
    		sb.append(i + "." + " " + errors.get(i).getErrorMessage());
    	}
    	return sb.toString();
    }


}
