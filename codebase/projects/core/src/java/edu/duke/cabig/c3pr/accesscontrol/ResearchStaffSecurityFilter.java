package edu.duke.cabig.c3pr.accesscontrol;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;

/**
 * The Class ResearchStaffSecurityFilter.
 * 
 * @author Vinay G
 */
public class ResearchStaffSecurityFilter implements DomainObjectSecurityFilterer{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ResearchStaffSecurityFilter.class);
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.accesscontrol.DomainObjectSecurityFilterer#filter(org.acegisecurity.Authentication, java.lang.String, edu.duke.cabig.c3pr.accesscontrol.Filterer)
	 */
	public Object filter(Authentication authentication, String permission,
			Filterer returnObject) {
		
		logger.debug("Authorizing the user and filtering Persons.");
		PersonUser personUser;
		//check the type of filterer
		if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
			Iterator collectionIter = returnObject.iterator();
			while (collectionIter.hasNext()) {
				personUser = (PersonUser)collectionIter.next();
				//If logged-in staff does not have site-level access, filter out the result.
	        	if(!hasSiteAndStudyLevelAccess(personUser)){
	        		returnObject.remove(personUser);
	        	}
			}
		}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
			personUser = (PersonUser)returnObject.getFilteredObject();
			//If logged-in staff does not have site-level access, filter out the result.
			if(!hasSiteAndStudyLevelAccess(personUser)){
        		returnObject.remove(personUser);
        	}
		}else{
			logger.debug("Filterer instance does not match any of CollectionFilterer, ArrayFilterer or AbstractMutableDomainObjectFilterer. Skipping authorization.");
		}
		return returnObject.getFilteredObject();
	}
	
	
	/**
	 * Checks for site and study level access.
	 *
	 * @param personUser the person user
	 * @return true, if successful
	 */
	private boolean hasSiteAndStudyLevelAccess(PersonUser personUser){
		//load all the roles the user has with the specified privilege
		Set<C3PRUserGroupType> userRoles = SecurityUtils.getRolesForLoggedInUser(UserPrivilegeType.PERSONUSER_READ);
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
						.buildAccessibleOrganizationIdsListForLoggedInUser(role), personUser)){
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * Checks for site level access permission.
	 *
	 * @param loggedIdResearchStaff the research staff
	 * @param personUser the study
	 * @return true, if successful
	 */
	private boolean hasSiteLevelAccessPermission(List<String> userAccessibleOrganizationIdsList , PersonUser personUser){
		for(HealthcareSite healthcareSite: personUser.getHealthcareSites()){
			if(userAccessibleOrganizationIdsList.contains(healthcareSite.getPrimaryIdentifier())){
				return true;
			}
		}
		return false;
	}

}
