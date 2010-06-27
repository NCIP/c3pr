package edu.duke.cabig.c3pr.accesscontrol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.utils.SecurityUtils;

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
		
		//Use the provisioningSession to determine hasAllSiteAccess and if not then build userAccessibleOrganizationIdsList.
		List<String> userAccessibleOrganizationIdsList = null;
		boolean hasAllSiteAccess = SecurityUtils.hasAllSiteAccess();
		if(!hasAllSiteAccess){
			userAccessibleOrganizationIdsList = SecurityUtils.buildUserAccessibleOrganizationIdsList(UserPrivilegeType.RESEARCHSTAFF_READ);
		}
		
		logger.debug("Authorizing the user and filtering studies.");
		//Ensure user has proper privilege
		if(!hasAllSiteAccess) {
			//Process further if user does not have all site access.
			ResearchStaff researchStaff;
			//check the type of filterer
			if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
				Iterator collectionIter = returnObject.iterator();
				while (collectionIter.hasNext()) {
					researchStaff = (ResearchStaff)collectionIter.next();
					//If logged-in staff does not have site-level access, filter out the result.
		        	if(!hasSiteLevelAccessPermission(userAccessibleOrganizationIdsList, researchStaff)){
		        		returnObject.remove(researchStaff);
		        	}
				}
			}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
				researchStaff = (ResearchStaff)returnObject.getFilteredObject();
				//If logged-in staff does not have site-level access, filter out the result.
				if(!hasSiteLevelAccessPermission(userAccessibleOrganizationIdsList, researchStaff)){
	        		returnObject.remove(researchStaff);
	        	}
			}else{
				logger.debug("Filterer instance does not match any of CollectionFilterer, ArrayFilterer or AbstractMutableDomainObjectFilterer. Skipping authorization.");
			}
		}
		return returnObject.getFilteredObject();
	}
	

	/**
	 * Removes all objects from the returnList.
	 *
	 * @param returnObject the return object
	 */
	private void removeAll(Filterer returnObject) {
		if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
			Iterator collectionIter = returnObject.iterator();
			List<ResearchStaff> staffList = new ArrayList<ResearchStaff>();
			while (collectionIter.hasNext()) {
        		staffList.add((ResearchStaff)collectionIter.next());
			}
			for(ResearchStaff researchStaff: staffList){
				returnObject.remove(researchStaff);
			}
		}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
    		returnObject.remove((ResearchStaff)returnObject.getFilteredObject());
		}
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
