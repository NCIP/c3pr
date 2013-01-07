package edu.duke.cabig.c3pr.security.authorization;

import edu.duke.cabig.c3pr.accesscontrol.UserPrivilege;
import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.security.acegi.csm.authorization.AbstractObjectPrivilegeCSMAuthorizationCheck;

import org.acegisecurity.Authentication;

/**
 * The Class C3PRObjectPrivilegeCSMAuthorizationCheck.
 * Replaces the DelegatingObjectPrivilegeCSMAuthorizationCheck which used to call
 * 	getCsmAuthorizationCheck().checkAuthorizationForObjectId(authentication, privilege, objectId)
 * Now we load the privileges from the RolePrivilege table instead. Hence the Dao.
 * 
 * @author Vinay Gangoli
 */
public class C3PRObjectPrivilegeCheck extends
									AbstractObjectPrivilegeCSMAuthorizationCheck{
	
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
		if(StringUtils.getBlankIfNull(privilege).equals("") && StringUtils.getBlankIfNull(objectId).equals("")){
			return true;
		}
//		UserPrivilegeType userPrivilegeType = UserPrivilegeType.getByCode(objectId+":"+privilege);
		UserPrivilege userPrivilege = new UserPrivilege(objectId, privilege);
//		UserPrivilegeType userPrivilegeType = UserPrivilegeType.valueOf(objectId);
//		if(userPrivilegeType == null){
//			userPrivilegeType = UserPrivilegeType.getByCode(privilege);
//		}
//		if(userPrivilegeType == null){
//			userPrivilegeType = UserPrivilegeType.getByCode(objectId+":"+privilege);
//		}
		if(SecurityUtils.hasPrivilege(authentication, userPrivilege)){
			return true;
		}else{
			return false;
		}
	}
}
