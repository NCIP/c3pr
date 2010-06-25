package edu.duke.cabig.c3pr.accesscontrol;


import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.dao.RolePrivilegeDao;
import edu.duke.cabig.c3pr.domain.RolePrivilege;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSessionFactory;
import gov.nih.nci.security.acegi.csm.authorization.CSMUserDetailsService;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	
	RolePrivilegeDao rolePrivilegeDao;
	
	private static final Log logger = LogFactory.getLog(C3prUserDetailsService.class);
	
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.security.acegi.csm.authorization.CSMUserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
					throws UsernameNotFoundException, DataAccessException {

		UserDetails userDetails = super.loadUserByUsername(username);
		
		gov.nih.nci.security.authorization.domainobjects.User csmUser = 
			getCsmUserProvisioningManager().getUser(userDetails.getUsername());
		AuthorizedUser authorizedUser= new AuthorizedUser(userDetails.getUsername(), userDetails.getPassword(), true, true, true, true, 
						userDetails.getAuthorities(), provisioningSessionFactory. createSession(csmUser.getUserId()), getAllRolePrivileges(userDetails.getAuthorities()));
		return authorizedUser;
	}
	
	/**
	 * Gets the all role privileges.
	 *
	 * @param grantedAuthorities the granted authorities
	 * @return the all role privileges
	 */
	private RolePrivilege[] getAllRolePrivileges(GrantedAuthority[] grantedAuthorities) {
		List<C3PRUserGroupType> c3prUserGroupTypes = SecurityUtils.getC3PRUserRoleTypes(grantedAuthorities);
		List<RolePrivilege> rolePrivilegeList = new ArrayList<RolePrivilege>();
		String roleName;
		for(C3PRUserGroupType group: c3prUserGroupTypes){
			roleName = group.getCode();
			rolePrivilegeList.addAll(rolePrivilegeDao.getAllPrivilegesForRole(roleName));
		}
		return rolePrivilegeList.toArray(new RolePrivilege[rolePrivilegeList.size()]);
	}

	public ProvisioningSessionFactory getProvisioningSessionFactory() {
		return provisioningSessionFactory;
	}

	public void setProvisioningSessionFactory(
			ProvisioningSessionFactory provisioningSessionFactory) {
		this.provisioningSessionFactory = provisioningSessionFactory;
	}

	public RolePrivilegeDao getRolePrivilegeDao() {
		return rolePrivilegeDao;
	}

	public void setRolePrivilegeDao(RolePrivilegeDao rolePrivilegeDao) {
		this.rolePrivilegeDao = rolePrivilegeDao;
	}

}
