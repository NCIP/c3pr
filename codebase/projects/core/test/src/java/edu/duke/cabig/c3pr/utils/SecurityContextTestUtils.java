package edu.duke.cabig.c3pr.utils;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.TestingAuthenticationToken;

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
                        "c3pr.user") };
        Authentication auth = new TestingAuthenticationToken(username, "does_not_matter",
                        authorities);
        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
