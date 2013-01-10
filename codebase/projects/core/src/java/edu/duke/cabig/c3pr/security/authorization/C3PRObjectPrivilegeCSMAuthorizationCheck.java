/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.security.authorization;

import edu.duke.cabig.c3pr.accesscontrol.AuthorizedUser;
import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.RolePrivilege;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;
import gov.nih.nci.security.acegi.csm.authorization.AbstractObjectPrivilegeCSMAuthorizationCheck;

import java.util.List;

import org.acegisecurity.Authentication;
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
		if(authentication == null){
			return true;
		}
		AuthorizedUser authorizedUser = (AuthorizedUser)authentication.getPrincipal();
		UserPrivilegeType userPrivilegeType = UserPrivilegeType.valueOf(privilege);
		if(userPrivilegeType == null){
			userPrivilegeType = UserPrivilegeType.getByCode(privilege);
		}
		
//		if (checkPrivilege == null) {
//			logger.error("No checkPrivilege was passed in.");
//			checkPrivilege = ((CSMGroupAuthorizationCheck)csmAuthorizationCheck).getRequiredPermission();
//		}

		//Get all the groups that have this privilege on the Object and then call isMember to check if user belongs to any of them.
		try {
			//Calling getAccessibleRoles(). As of CCTS2.2 Roles are the same as Groups
			//List<RolePrivilege> rolePrivileges = rolePrivilegeDao.getAccessibleRoles(rolePrivilegeObjectId, checkPrivilege);
			List<RolePrivilege> rolePrivileges = authorizedUser.getRolePrivileges(userPrivilegeType);
			if (rolePrivileges == null || rolePrivileges.size() == 0) {
				logger.debug("Found no groups for " + objectId);
			} else {
				for (RolePrivilege rolePrivilege : rolePrivileges) {
					if (isMember(authorizedUser, rolePrivilege.getRoleName(),objectId)) {
						return true;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug(ex);
		}
		return false;
	}
	

	/**
	 * Checks if user(Authentication) is member of the group that is passed in.
	 *
	 * @param authentication the authentication
	 * @param groupName the group name
	 * @return true, if is member
	 */
	protected boolean isMember(AuthorizedUser authorizedUser, String groupName, String objectId) {
		boolean isMember = false;
		ProvisioningSession provisioningSession = authorizedUser.getProvisioningSession();
		SuiteRole suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(C3PRUserGroupType.getByCode(groupName));
		SuiteRoleMembership suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
		String siteId = objectId.substring(objectId.lastIndexOf(".") + 1);
		if(suiteRoleMembership.isAllSites() || suiteRoleMembership.getSiteIdentifiers().contains(siteId)){
			isMember = true;
		}
		return isMember;
	}

}
