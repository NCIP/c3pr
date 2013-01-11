/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import org.acegisecurity.Authentication;

import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.security.authorization.C3PRObjectPrivilegeCSMAuthorizationCheck;

/**
 * User: Vinay Gangoli
 * Date: Jul 3, 2008
 * Time: 3:14:12 PM
 */

public class StudySiteAuthorizationCheck extends C3PRObjectPrivilegeCSMAuthorizationCheck{

   @Override
	public boolean checkAuthorization(Authentication authentication, String privilege,
			Object object) {
	   
	   boolean authorize = false;
	   StudyOrganization studyOrganization = null;
	   StudyOrganization studyCoordinatingCenter = null;
	   if(object instanceof StudyOrganization){
		   studyOrganization = (StudyOrganization)object;
		   studyCoordinatingCenter = studyOrganization.getStudy().getStudyCoordinatingCenter(); 
	   }
	    
	   //super.checkAuthorization will enforce that the logged in user only sees his organization.
	   //however if the logged in user is a Coordinating center for the study being viewed the he 
	   //shud be able to see all sites
	   if(studyCoordinatingCenter != null){
		   authorize = super.checkAuthorization(authentication, privilege,studyCoordinatingCenter);
	   }
	   
	   return authorize?authorize:super.checkAuthorization(authentication,privilege, object);
	}
   
}

