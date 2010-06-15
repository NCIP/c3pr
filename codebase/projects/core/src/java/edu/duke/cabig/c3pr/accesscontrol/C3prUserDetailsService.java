package edu.duke.cabig.c3pr.accesscontrol;

import java.util.List;

import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSessionFactory;
import gov.nih.nci.security.acegi.csm.authorization.CSMUserDetailsService;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

/**
 * The Class C3prUserDetailsService.
 * Overrides the loadUserByUsername() from CSMUserDetailsService so as to return a AuthorizedUser object.
 * Populates the protectionGroupRoleContextList in the AuthorizedUser so that all filterers and checkers can
 * utilize it instead of going to CSM.
 * 
 * @author Vinay G
 */
public class C3prUserDetailsService extends CSMUserDetailsService{
	
	private ProvisioningSessionFactory provisioningSessionFactory;
	
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.security.acegi.csm.authorization.CSMUserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
					throws UsernameNotFoundException, DataAccessException {

		UserDetails userDetails = super.loadUserByUsername(username);
		
		List<Object> protectionGroupRoleContextList = getProtectionGroupRoleContextList();
		AuthorizedUser authorizedUser= new AuthorizedUser(userDetails.getUsername(), userDetails.getPassword(), true, true, true, true, 
						userDetails.getAuthorities(), protectionGroupRoleContextList);
		return authorizedUser;
	}

	private List<Object> getProtectionGroupRoleContextList() {
		// TODO Auto-generated method stub
		//use the new library to build this list.
		return null;
	}

	public ProvisioningSessionFactory getProvisioningSessionFactory() {
		return provisioningSessionFactory;
	}

	public void setProvisioningSessionFactory(
			ProvisioningSessionFactory provisioningSessionFactory) {
		this.provisioningSessionFactory = provisioningSessionFactory;
	}

}
