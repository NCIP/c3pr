/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.Date;
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
	
	public static User getLoggedInUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if (auth != null) {
            return (User)auth.getPrincipal();
        }
        return null;
    }
	
	
    public static String getDateString(Date date){
      if (date != null) {
    	  		return DateUtil.formatDate(date, "MM/dd/yyyy");
		}
		return "";
    }

    public static String listErrors(List<Error> errors){
    	StringBuffer sb = new StringBuffer();
    	for(int i=0;i< errors.size();i++){
    		sb.append("\n");
    		sb.append(i+1 + "." + " " + errors.get(i).getErrorMessage());
    	}
    	return sb.toString();
    }


}
