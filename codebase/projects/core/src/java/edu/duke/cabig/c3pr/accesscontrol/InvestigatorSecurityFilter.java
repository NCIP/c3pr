package edu.duke.cabig.c3pr.accesscontrol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.RoleTypes;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.utils.SecurityUtils;

/**
 * The Class StudySecurityFilter.
 */
public class InvestigatorSecurityFilter implements DomainObjectSecurityFilterer{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(InvestigatorSecurityFilter.class);

	/** The csm user repository. */
	private CSMUserRepository csmUserRepository;
	
	/** The roles to exclude. */
	private List<RoleTypes> rolesToExclude;
	
	/** The roles that can access investigators. */
	private List<String> rolesThatCanAccessInvestigators;
	
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.accesscontrol.DomainObjectSecurityFilterer#filter(org.acegisecurity.Authentication, java.lang.String, edu.duke.cabig.c3pr.accesscontrol.Filterer)
	 */
	public Object filter(Authentication authentication, String permission,
			Filterer returnObject) {
		
		//No filtering if user has access to all sites or if user has an exclude role
		if (SecurityUtils.hasAllSiteAccess(authentication) || SecurityUtils.hasRole(authentication, rolesToExclude)) {
			logger.debug("User is either a all-site user or is part of the authorization exclusion list. Skipping authorization.");
    		return returnObject.getFilteredObject();
		}
		
		logger.debug("Authorizing the user and filtering studies.");
		//If logged-in staff does not have necessary role, filter out all the results.
		//Note: We get rolesThatCanAccessStaff from the app-core-security.xml, instead should this be from csm by looking at the edu.duke.cabig.c3pr.Investigator:READ privilege?
		if(!SecurityUtils.hasRole(authentication, SecurityUtils.getRoleTypesFromCodeList(getRolesThatCanAccessInvestigators()))){
			removeAll(returnObject);
		}
		
		//get logged-in research staff from authentication
		String userName = SecurityUtils.getUserName(authentication);
		ResearchStaff loggedIdResearchStaff = (ResearchStaff)csmUserRepository.getUserByName(userName);
		Investigator investigator;
		
		//check the type of filterer
		if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
			Iterator collectionIter = returnObject.iterator();
			while (collectionIter.hasNext()) {
				investigator = (Investigator)collectionIter.next();
				//If logged-in staff does not have site-level access, filter out the result.
	        	if(!hasSiteLevelAccessPermission(loggedIdResearchStaff, investigator)){
	        		returnObject.remove(investigator);
	        	}
			}
		}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
			investigator = (Investigator)returnObject.getFilteredObject();
			//If logged-in staff does not have site-level access, filter out the result.
			if(!hasSiteLevelAccessPermission(loggedIdResearchStaff, investigator)){
        		returnObject.remove(investigator);
        	}
		}else{
			logger.debug("Filterer instance does not match any of CollectionFilterer, ArrayFilterer or AbstractMutableDomainObjectFilterer. Skipping authorization.");
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
	 * @param researchStaff the research staff
	 * @param study the study
	 * @return true, if successful
	 */
	private boolean hasSiteLevelAccessPermission(ResearchStaff researchStaff, Investigator investigator){
		List<HealthcareSite> healthcareSites = new ArrayList<HealthcareSite>();
		for(HealthcareSiteInvestigator hcsi : investigator.getHealthcareSiteInvestigators()){
			healthcareSites.add(hcsi.getHealthcareSite());
		}
		for(HealthcareSite hcSite : researchStaff.getHealthcareSites()){
			if(healthcareSites.contains(hcSite)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Sets the csm user repository.
	 * 
	 * @param csmUserRepository the new csm user repository
	 */
	public void setCsmUserRepository(CSMUserRepository csmUserRepository) {
		this.csmUserRepository = csmUserRepository;
	}

	/**
	 * Sets the roles to exclude.
	 * 
	 * @param rolesToExclude the new roles to exclude
	 */
	public void setRolesToExclude(List<RoleTypes> rolesToExclude) {
		this.rolesToExclude = rolesToExclude;
	}

	public List<String> getRolesThatCanAccessInvestigators() {
		return rolesThatCanAccessInvestigators;
	}

	public void setRolesThatCanAccessInvestigators(
			List<String> rolesThatCanAccessInvestigators) {
		this.rolesThatCanAccessInvestigators = rolesThatCanAccessInvestigators;
	}

}
