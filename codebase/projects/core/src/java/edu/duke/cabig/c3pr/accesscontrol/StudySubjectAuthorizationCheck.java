/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.security.acegi.csm.authorization.CSMAuthorizationCheck;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.Authentication;

/**
 * User: Vinay Gangoli
 * Date: Jul 3, 2008
 * Time: 3:14:12 PM
 */

public class StudySubjectAuthorizationCheck implements CSMAuthorizationCheck{
	
	
	public boolean checkAuthorization(Authentication authentication, String privilege,
			Object object) {
	   
		UserPrivilegeType userPrivilegeType = UserPrivilegeType.valueOf(privilege);
		Study study = ((StudySubject)object).getStudySite().getStudy();
		
		//load all the roles the user has with the specified privilege
		Set<C3PRUserGroupType> userRoles = SecurityUtils.getRolesForLoggedInUser(userPrivilegeType);
		Iterator<C3PRUserGroupType> iter = userRoles.iterator();

		C3PRUserGroupType role;
		SuiteRole suiteRole;
		while(iter.hasNext()){
			role = iter.next();
			suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(role);
			//grant access if role is unscoped
			if(!suiteRole.isScoped()){
				return true;
			} else {
				if(SecurityUtils.hasAllSiteAccess(role) || hasSiteLevelAccessPermission(SecurityUtils
						.buildAccessibleOrganizationIdsListForLoggedInUser(role), study)){
					if(!suiteRole.isStudyScoped()){
						//user is only scoped by site
						return true;
					} else {
						//user is both site and study scoped
						if(SecurityUtils.hasAllStudyAccess(role) || hasStudyLevelAccessPermission(SecurityUtils
								.buildAccessibleStudyIdsListForLoggedInUser(role), study)){
				    		return true;
				    	}
					}
				}
			}
		}
		return false;
	}
	
	
	private boolean hasSiteLevelAccessPermission(List<String> userAccessibleOrganizationIdsList , Study study){
		for(StudyOrganization studyOrganization:study.getStudyOrganizations()){
			if(userAccessibleOrganizationIdsList.contains(studyOrganization.getHealthcareSite().getPrimaryIdentifier())){
				return true;
			}
		}
		return false;
	}
	
	private boolean hasStudyLevelAccessPermission(List<String> userAccessibleStudyIdsList, Study study){
		return userAccessibleStudyIdsList.contains(study.getCoordinatingCenterAssignedIdentifier().getValue());
	}
	

	public boolean checkAuthorizationForObjectId(Authentication arg0, String arg1,
			String arg2) {
		return false;
	}
	
	public boolean checkAuthorizationForObjectIds(Authentication arg0, String arg1,
			String[] arg2) {
		return false;
	}
   
}

