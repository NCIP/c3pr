package edu.duke.cabig.c3pr.utils;

import java.util.Arrays;
import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.User;

import edu.duke.cabig.c3pr.constants.RoleTypes;

/**
 * The Class SecurityUtils.
 */
public class SecurityUtils {

	/**
	 * Check if user is a super user based on ROLE_c3pr_admin.
	 * 
	 * @param authentication the authentication
	 * 
	 * @return true, if checks if is super user
	 */
	public static boolean isSuperUser(Authentication authentication) {
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
        	if (grantedAuthority.getAuthority().equals(RoleTypes.C3PR_ADMIN.getCode())) {
        			return true;
        	}       
        }		
		return false;
	}
	
	
	/**
	 * Gets the user name from the authentication object.
	 * 
	 * @param authentication the authentication
	 * 
	 * @return the user name
	 */
	public static String getUserName(Authentication authentication) {
		return ((User)authentication.getPrincipal()).getUsername();
	}
	
	/**
	 * Checks if user has any of the provided roles.
	 * 
	 * @param authentication the authentication
	 * @param roleTypes the role types
	 * 
	 * @return true, if successful
	 */
	public static boolean hasRole(Authentication authentication, List<RoleTypes> roleTypes){
		List<GrantedAuthority> grantedAuthorities = Arrays.asList(authentication.getAuthorities());
		for(GrantedAuthority grantedAuthority : grantedAuthorities){
			if(roleTypes.contains(RoleTypes.getByCode(grantedAuthority.getAuthority()))){
				return true;
			}
		}
		return false;
	}
	
}
