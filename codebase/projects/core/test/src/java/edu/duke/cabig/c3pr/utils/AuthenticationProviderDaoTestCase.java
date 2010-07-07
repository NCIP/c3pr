package edu.duke.cabig.c3pr.utils;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.userdetails.UserDetails;

import edu.duke.cabig.c3pr.accesscontrol.C3prUserDetailsService;

public class AuthenticationProviderDaoTestCase extends DaoTestCase {

	private C3prUserDetailsService c3prUserDetailsService;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		c3prUserDetailsService = (C3prUserDetailsService) getApplicationContext().getBean("csmUserDetailsService");
	}
	
    public Authentication setUserInSecurityContext(String username) {
    	UserDetails userDetails = c3prUserDetailsService.loadUserByUsername(username);
    	GrantedAuthority[] authorities = userDetails.getAuthorities();
    	Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    	SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }
}
