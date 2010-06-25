package edu.duke.cabig.c3pr.accesscontrol;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.security.authorization.C3PRObjectPrivilegeCSMAuthorizationCheck;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;

/**
 * User: Vinay Gangoli
 * Date: Jul 3, 2008
 * Time: 3:14:12 PM
 */

public class StudyAuthorizationCheck extends C3PRObjectPrivilegeCSMAuthorizationCheck{

   
   /**
	 * Checks if user(Authentication) is member of the group that is passed in.
	 * This is used for determining study scoped access.
	 * If user is scoped by site then go ahead and grant access (as it is assumed user's org is present in the study in some respect)
	 * else check for the study in the list of study PG's.
	 * 
	 * example objectId: Study.NCI-2009-00001
	 *
	 * @param authentication the authentication
	 * @param groupName the group name
	 * @return true, if is member
	 */
	@Override
	protected boolean isMember(AuthorizedUser authorizedUser, String groupName, String objectId) {
		boolean isMember = false;
		ProvisioningSession provisioningSession = authorizedUser.getProvisioningSession();
		SuiteRole suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(C3PRUserGroupType.getByCode(groupName));
		
		if(!suiteRole.isStudyScoped()){
			isMember = true;
		} else {
			SuiteRoleMembership suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
			String studyId = objectId.substring(objectId.lastIndexOf("."));
			if(suiteRoleMembership.isAllStudies() || suiteRoleMembership.getStudyIdentifiers().contains(studyId)){
				isMember = true;
			}
		}
		return isMember;
	}

   
}

