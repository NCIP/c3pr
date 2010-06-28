package edu.duke.cabig.c3pr.accesscontrol;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;

/**
 * The Class StudySecurityFilter.
 */
public class InvestigatorSecurityFilter implements DomainObjectSecurityFilterer{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InvestigatorSecurityFilter.class);

	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.accesscontrol.DomainObjectSecurityFilterer#filter(org.acegisecurity.Authentication, java.lang.String, edu.duke.cabig.c3pr.accesscontrol.Filterer)
	 */
	public Object filter(Authentication authentication, String permission,
			Filterer returnObject) {
		
		logger.debug("Authorizing the user and filtering studies.");
		//Process further if user does not have all site access.
		Investigator investigator;
		//check the type of filterer
		if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
			Iterator collectionIter = returnObject.iterator();
			while (collectionIter.hasNext()) {
				investigator = (Investigator)collectionIter.next();
				//If logged-in staff does not have site-level access, filter out the result.
	        	if(!hasSiteAndStudyLevelAccess(investigator)){
	        		returnObject.remove(investigator);
	        	}
			}
		}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
			investigator = (Investigator)returnObject.getFilteredObject();
			//If logged-in staff does not have site-level access, filter out the result.
			if(!hasSiteAndStudyLevelAccess(investigator)){
        		returnObject.remove(investigator);
        	}
		}else{
			logger.debug("Filterer instance does not match any of CollectionFilterer, ArrayFilterer or AbstractMutableDomainObjectFilterer. Skipping authorization.");
		}
		return returnObject.getFilteredObject();
	}

	
	private boolean hasSiteAndStudyLevelAccess(Investigator investigator){
		//load all the roles the user has with the specified privilege
		Set<C3PRUserGroupType> userRoles = SecurityUtils.getUserRoles(UserPrivilegeType.INVESTIGATOR_READ);
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
						.buildUserAccessibleOrganizationIdsList(role), investigator)){
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
	private boolean hasSiteLevelAccessPermission(List<String> userAccessibleOrganizationIdsList , Investigator investigator){
		for(HealthcareSiteInvestigator hcsi : investigator.getHealthcareSiteInvestigators()){
			if(userAccessibleOrganizationIdsList.contains(hcsi.getHealthcareSite().getPrimaryIdentifier())){
				return true;
			}
		}
		return false;
	}


}
