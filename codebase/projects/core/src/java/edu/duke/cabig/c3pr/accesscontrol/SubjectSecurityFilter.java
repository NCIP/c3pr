package edu.duke.cabig.c3pr.accesscontrol;

import java.util.Iterator;
import java.util.List;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.RoleTypes;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.utils.SecurityUtils;

/**
 * The Class SubjectSecurityFilter.
 */
public class SubjectSecurityFilter implements DomainObjectSecurityFilterer{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(SubjectSecurityFilter.class);

	/** The csm user repository. */
	private CSMUserRepository csmUserRepository;
	
	/** The roles to exclude. */
	private List<RoleTypes> rolesToExclude;
	
	
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.accesscontrol.DomainObjectSecurityFilterer#filter(org.acegisecurity.Authentication, java.lang.String, edu.duke.cabig.c3pr.accesscontrol.Filterer)
	 */
	public Object filter(Authentication authentication, String permission,
			Filterer returnObject) {
		
		//no filtering if super user or if user has an exclude role
		if (SecurityUtils.isSuperUser(authentication) || SecurityUtils.hasRole(authentication, rolesToExclude)) {
			logger.debug("User is either a super user or is part of the authorization exclusion list. Skiping authrization.");
    		return returnObject.getFilteredObject();
		}
		logger.debug("Authorizing the user and filtering subjects.");
		//get research staff from username
		String userName = SecurityUtils.getUserName(authentication);
		ResearchStaff researchStaff = (ResearchStaff)csmUserRepository.getUserByName(userName);
		
		//check the type of filterer
		if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
			Iterator collectionIter = returnObject.iterator();
			while (collectionIter.hasNext()) {
	        	Participant participant = (Participant)collectionIter.next();
	        	if(!hasPermission(researchStaff, participant)){
	        		returnObject.remove(participant);
	        	}
			}
		}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
			Participant participant = (Participant)returnObject.getFilteredObject();
			if(!hasPermission(researchStaff, participant)){
        		returnObject.remove(participant);
        	}
		}else{
			logger.debug("Filterer instance does not match any of CollectionFilterer, ArrayFilterer or AbstractMutableDomainObjectFilterer. Skipping authorization.");
		}
		return returnObject.getFilteredObject();
	}
	
	private boolean hasPermission(ResearchStaff researchStaff , Participant participant){
		List<HealthcareSite> hcsList = participant.getHealthcareSites();
    	for(OrganizationAssignedIdentifier identifier : participant.getOrganizationAssignedIdentifiers()){
    		hcsList.add(identifier.getHealthcareSite());
    	}
    	for(HealthcareSite hcSite : researchStaff.getHealthcareSites()){
    		if(hcsList.contains(hcSite)){
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

}
