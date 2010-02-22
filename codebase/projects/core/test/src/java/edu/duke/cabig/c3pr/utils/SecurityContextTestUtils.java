package edu.duke.cabig.c3pr.utils;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.TestingAuthenticationToken;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.userdetails.User;

import edu.duke.cabig.c3pr.constants.RoleTypes;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 23, 2007 Time: 11:49:09 AM To change this
 * template use File | Settings | File Templates.
 */
public class SecurityContextTestUtils {

    /**
     * Switch to c3pr_admin user. THis data is provisioned during migration
     */
    public static void switchToSuperuser() {
        switchUser("c3pr_admin");
    }

    /**
     * Switch to a dummy user with no authority. Use this to test if security interceptors are
     * working
     */
    public static void switchToNobody() {
        switchUser(null);
    }

    private static void switchUser(String username) {

        GrantedAuthority[] authorities = new GrantedAuthority[] { new GrantedAuthorityImpl(
                        "ROLE_c3pr_admin"), new GrantedAuthorityImpl("c3pr_admin"), new GrantedAuthorityImpl("study_coordinator") };
        Authentication auth = new TestingAuthenticationToken(username, "does_not_matter",
                        authorities);
        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    
    public static Authentication setUser(String username, RoleTypes roleTypes) {
    	GrantedAuthority[] authorities = new GrantedAuthority[] { new GrantedAuthorityImpl(roleTypes.getCode()) };
    	org.acegisecurity.userdetails.User user = new User(username, "does_not_matter",true,authorities);
    	Authentication auth = new UsernamePasswordAuthenticationToken(user, null,authorities);
    	SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }
}
