/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import org.acegisecurity.Authentication;

import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.security.authorization.C3PRObjectPrivilegeCSMAuthorizationCheck;

/**
 * User: Vinay Gangoli
 * Date: Jul 3, 2008
 * Time: 3:14:12 PM
 */

public class SubjectAuthorizationCheck extends C3PRObjectPrivilegeCSMAuthorizationCheck{

   @Override
	public boolean checkAuthorization(Authentication authentication, String privilege,
			Object object) {
	   
	   boolean authorize = false;
	   Participant participant = (Participant) object;
	   for(OrganizationAssignedIdentifier organizationAssignedIdentifier : participant.getOrganizationAssignedIdentifiers()){ 
		   authorize = super.checkAuthorization(authentication, privilege,organizationAssignedIdentifier.getHealthcareSite());
		   if(authorize) return true;
	   }
	   return false;
	}
   
}

