package edu.duke.cabig.c3pr.accesscontrol;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
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
		
		logger.debug("Authorizing the user and filtering studies.");
		ResearchStaff researchStaff;
		//check the type of filterer
		if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
			Iterator collectionIter = returnObject.iterator();
			while (collectionIter.hasNext()) {
				researchStaff = (ResearchStaff)collectionIter.next();
				//If logged-in staff does not have site-level access, filter out the result.
	        	if(!hasSiteAndStudyLevelAccess(researchStaff)){
	        		returnObject.remove(researchStaff);
	        	}
			}
		}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
			researchStaff = (ResearchStaff)returnObject.getFilteredObject();
			//If logged-in staff does not have site-level access, filter out the result.
			if(!hasSiteAndStudyLevelAccess(researchStaff)){
        		returnObject.remove(researchStaff);
        	}
		}else{
			logger.debug("Filterer instance does not match any of CollectionFilterer, ArrayFilterer or AbstractMutableDomainObjectFilterer. Skipping authorization.");
		}
		return returnObject.getFilteredObject();
	}
	
	
	private boolean hasSiteAndStudyLevelAccess(ResearchStaff researchStaff){
		//load all the roles the user has with the specified privilege
		Set<C3PRUserGroupType> userRoles = SecurityUtils.getUserRoles(UserPrivilegeType.STUDY_READ);
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
						.buildUserAccessibleOrganizationIdsList(role), researchStaff)){
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
	 * @param researchStaff the study
	 * @return true, if successful
	 */
	private boolean hasSiteLevelAccessPermission(List<String> userAccessibleOrganizationIdsList , ResearchStaff researchStaff){
		for(HealthcareSite healthcareSite: researchStaff.getHealthcareSites()){
			if(userAccessibleOrganizationIdsList.contains(healthcareSite.getPrimaryIdentifier())){
				return true;
			}
		}
		return false;
	}

}
