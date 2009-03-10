package edu.duke.cabig.c3pr.accesscontrol;

import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import gov.nih.nci.security.acegi.csm.authorization.DelegatingObjectPrivilegeCSMAuthorizationCheck;

import org.acegisecurity.Authentication;

/**
 * User: Vinay Gangoli
 * Date: Jul 3, 2008
 * Time: 3:14:12 PM
 */

public class StudySiteAuthorizationCheck extends DelegatingObjectPrivilegeCSMAuthorizationCheck{

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
}

