/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.userdetails.UserDetails;

import edu.duke.cabig.c3pr.accesscontrol.C3prUserDetailsService;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

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
    
    
	
	public static boolean containsElementWithId(List<? extends AbstractMutableDomainObject> domainObjects, int id){
		for(AbstractMutableDomainObject domainObject : domainObjects){
			if(domainObject.getId().equals(id)){
				return true;
			}
		}
		return false;
	}
}
