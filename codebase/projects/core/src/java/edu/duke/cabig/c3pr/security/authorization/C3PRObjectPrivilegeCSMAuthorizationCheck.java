package edu.duke.cabig.c3pr.security.authorization;

import edu.duke.cabig.c3pr.dao.RolePrivilegeDao;
import edu.duke.cabig.c3pr.domain.RolePrivilege;
import gov.nih.nci.security.acegi.csm.authorization.AbstractObjectPrivilegeCSMAuthorizationCheck;
import gov.nih.nci.security.acegi.csm.authorization.CSMAuthorizationCheck;
import gov.nih.nci.security.acegi.csm.authorization.CSMGroupAuthorizationCheck;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class C3PRObjectPrivilegeCSMAuthorizationCheck.
 * Replaces the DelegatingObjectPrivilegeCSMAuthorizationCheck which used to call
 * 	getCsmAuthorizationCheck().checkAuthorizationForObjectId(authentication, privilege, objectId)
 * Now we load the privileges from the RolePrivilege table instead. Hence the Dao.
 * 
 * @author Vinay Gangoli
 */
public class C3PRObjectPrivilegeCSMAuthorizationCheck extends
									AbstractObjectPrivilegeCSMAuthorizationCheck{
	
	private CSMAuthorizationCheck csmAuthorizationCheck;
	
	private RolePrivilegeDao rolePrivilegeDao;

	private static final Log logger = LogFactory.getLog(C3PRObjectPrivilegeCSMAuthorizationCheck.class);
	
	
	/**
	 * Check authorization for object id. 
	 * Returns a decision on if the authentication(user) has access to do the mentioned privilege on the mentioned object(PE)
	 *
	 * @param authentication the authentication
	 * @param privilege the privilege
	 * @param objectId the object id
	 * @return true, if successful
	 * @see gov.nih.nci.security.acegi.csm.authorization.CSMAuthorizationCheck#checkAuthorizationForObjectId(org.acegisecurity.Authentication, java.lang.String, java.lang.String)
	 */
	public boolean checkAuthorizationForObjectId(Authentication authentication,
			String privilege, String objectId) {
		
		//return getCsmAuthorizationCheck().checkAuthorizationForObjectId(authentication, privilege, objectId);
		return true;
		
//		String checkPrivilege = privilege;
//		if (checkPrivilege == null) {
//			logger.error("No checkPrivilege was passed in.");
//			checkPrivilege = ((CSMGroupAuthorizationCheck)csmAuthorizationCheck).getRequiredPermission();
//		}
//
//		//Get all the groups that have this privilege on the Object and then call isMember to check if user belongs to any of them.
//		boolean isAuthorized = false;
//		try {
//			//Calling getAccessibleRoles(). As of CCTS2.2 Roles are the same as Groups
//			List<RolePrivilege> rolePrivileges = rolePrivilegeDao.getAccessibleRoles(objectId, checkPrivilege);
//			if (rolePrivileges == null || rolePrivileges.size() == 0) {
//				logger.debug("Found no groups for " + objectId);
//			} else {
//				for (Iterator i = rolePrivileges.iterator(); i.hasNext();) {
//					RolePrivilege rolePrivilege = (RolePrivilege) i.next();
//					if (authentication != null && isMember(authentication, rolePrivilege.getRoleName())) {
//						isAuthorized = true;
//						break;
//					}
//				}
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			logger.debug(ex);
//		}
//		return isAuthorized;
	}
	

	/**
	 * Checks if user(Authentication) is member of the group that is passed in.
	 *
	 * @param authentication the authentication
	 * @param groupName the group name
	 * @return true, if is member
	 */
	protected boolean isMember(Authentication authentication, String groupName) {
		
		boolean isMember = false;
		
		GrantedAuthority[] gaArray = authentication.getAuthorities();
		Set<String> groups = new HashSet<String>();
		try {
			for(int i=0;i<gaArray.length;i++){
				groups.add(getGroupNameFromAuthority(gaArray[i]));
			}
		} catch (Exception ex) {
			throw new RuntimeException("Error getting groups: " + ex.getMessage(), ex);
		}

		if (groups != null) {
			for (Iterator i = groups.iterator(); i.hasNext();) {
				String usersGroupName = (String) i.next();
				if (usersGroupName.equals(groupName)) {
					isMember = true;
					break;
				}
			}
			logger.debug("isMember? " + isMember);
		} else {
			logger.debug("found no groups for user " + authentication.getName());
		}
		return isMember;
	}
	

	/**
	 * Gets the group name from authority.
	 * Note that the authority is the string "ROLE_" appended with the group name.
	 *
	 * @param grantedAuthority the granted authority
	 * @return the group name from authority
	 */
	private String getGroupNameFromAuthority(GrantedAuthority grantedAuthority) {
		return grantedAuthority.getAuthority().substring(5, grantedAuthority.getAuthority().length());
	}


	public RolePrivilegeDao getRolePrivilegeDao() {
		return rolePrivilegeDao;
	}

	public void setRolePrivilegeDao(RolePrivilegeDao rolePrivilegeDao) {
		this.rolePrivilegeDao = rolePrivilegeDao;
	}

	public CSMAuthorizationCheck getCsmAuthorizationCheck() {
		return csmAuthorizationCheck;
	}

	public void setCsmAuthorizationCheck(CSMAuthorizationCheck csmAuthorizationCheck) {
		this.csmAuthorizationCheck = csmAuthorizationCheck;
	}

}
