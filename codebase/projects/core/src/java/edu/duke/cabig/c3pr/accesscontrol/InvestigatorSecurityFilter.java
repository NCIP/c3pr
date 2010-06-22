package edu.duke.cabig.c3pr.accesscontrol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.utils.SecurityUtils;

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
		
		//Use the provisioningSession to determine hasAllSiteAccess and if not then build userAccessibleOrganizationIdsList.
		List<String> userAccessibleOrganizationIdsList = null;
		boolean hasAllSiteAccess = SecurityUtils.hasAllSiteAccess(authentication);
		if(!hasAllSiteAccess){
			userAccessibleOrganizationIdsList = SecurityUtils.buildUserAccessibleOrganizationIdsList(authentication);
		}
		
		logger.debug("Authorizing the user and filtering studies.");
		if(!hasAllSiteAccess) {
			//Process further if user does not have all site access.
			Investigator investigator;
			//check the type of filterer
			if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
				Iterator collectionIter = returnObject.iterator();
				while (collectionIter.hasNext()) {
					investigator = (Investigator)collectionIter.next();
					//If logged-in staff does not have site-level access, filter out the result.
		        	if(!hasSiteLevelAccessPermission(userAccessibleOrganizationIdsList, investigator)){
		        		returnObject.remove(investigator);
		        	}
				}
			}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
				investigator = (Investigator)returnObject.getFilteredObject();
				//If logged-in staff does not have site-level access, filter out the result.
				if(!hasSiteLevelAccessPermission(userAccessibleOrganizationIdsList, investigator)){
	        		returnObject.remove(investigator);
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
			List<Investigator> invList = new ArrayList<Investigator>();
			while (collectionIter.hasNext()) {
        		invList.add((Investigator)collectionIter.next());
			}
			for(Investigator investigator: invList){
				returnObject.remove(investigator);
			}
		}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
    		returnObject.remove((Investigator)returnObject.getFilteredObject());
		}
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
