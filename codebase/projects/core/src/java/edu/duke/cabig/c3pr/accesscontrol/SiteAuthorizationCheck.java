package edu.duke.cabig.c3pr.accesscontrol;

import org.acegisecurity.Authentication;

import edu.duke.cabig.c3pr.security.authorization.C3PRObjectPrivilegeCSMAuthorizationCheck;

/**
 * User: Vinay Gangoli
 * Date: Jul 3, 2008
 * Time: 3:14:12 PM
 */

public class SiteAuthorizationCheck extends C3PRObjectPrivilegeCSMAuthorizationCheck{

   @Override
	public boolean checkAuthorization(Authentication authentication, String privilege,
			Object object) {
	   
	   return super.checkAuthorization(authentication,privilege, object);
	}
   
   
	/**
	 * Gets the role privilege object id from object id.
	 *
	 * @param objectId the object id
	 * @return the role privilege object id from object id
	 */
   @Override
   protected String getRolePrivilegeObjectIdFromObjectId(String objectId) {
		return objectId.substring(0, objectId.lastIndexOf("."));
	}
}

