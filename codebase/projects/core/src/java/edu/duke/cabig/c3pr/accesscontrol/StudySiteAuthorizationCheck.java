package edu.duke.cabig.c3pr.accesscontrol;

import org.acegisecurity.Authentication;

import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
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
	   
	   StudySite studySite = null;
	   if(object instanceof StudySite){
		   studySite = (StudySite) object;
		   studyCoordinatingCenter = studySite.getStudy().getStudyCoordinatingCenter();
	   }
	    
	   //super.checkAuthorization will enforce that the logged in user only sees his organization.
	   //however if the logged in user is a Coordinating center for the study being viewed the he 
	   //shud be able to see all sites
	   if(studyCoordinatingCenter != null){
		   authorize = super.checkAuthorization(authentication, privilege,studyCoordinatingCenter);
	   }
	   
	   return authorize?authorize:super.checkAuthorization(authentication,privilege, object);
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

