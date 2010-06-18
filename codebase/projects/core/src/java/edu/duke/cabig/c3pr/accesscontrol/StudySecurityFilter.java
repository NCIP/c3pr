package edu.duke.cabig.c3pr.accesscontrol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.RoleTypes;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.utils.SecurityUtils;

/**
 * The Class StudySecurityFilter.
 */
public class StudySecurityFilter implements DomainObjectSecurityFilterer{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(StudySecurityFilter.class);

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
		logger.debug("Authorizing the user and filtering studies.");
		//get research staff from username
		String userName = SecurityUtils.getUserName(authentication);
		ResearchStaff researchStaff = (ResearchStaff)csmUserRepository.getUserByName(userName);
		
		//check the type of filterer
		if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
			Iterator collectionIter = returnObject.iterator();
			while (collectionIter.hasNext()) {
	        	Study study = (Study)collectionIter.next();
	        	if(SecurityUtils.hasRole(authentication, Arrays.asList(new RoleTypes[]{RoleTypes.SITE_COORDINATOR})) &&
	        			!hasSiteLevelAccessPermission(researchStaff, study)){
	        		returnObject.remove(study);
	        		
	        	}
	        	if(SecurityUtils.hasRole(authentication, Arrays.asList(new RoleTypes[]{RoleTypes.STUDY_COORDINATOR, RoleTypes.REGISTRAR})) &&
	        			!hasStudyLevelAccessPermission(researchStaff, study)){
	        		returnObject.remove(study);
	        	}
			}
		}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
			Study study = (Study)returnObject.getFilteredObject();
			if(SecurityUtils.hasRole(authentication, Arrays.asList(new RoleTypes[]{RoleTypes.SITE_COORDINATOR})) &&
        			!hasSiteLevelAccessPermission(researchStaff, study)){
        		returnObject.remove(study);
        		
        	}
        	if(SecurityUtils.hasRole(authentication, Arrays.asList(new RoleTypes[]{RoleTypes.STUDY_COORDINATOR, RoleTypes.REGISTRAR})) &&
        			!hasStudyLevelAccessPermission(researchStaff, study)){
        		returnObject.remove(study);
        	}
		}else{
			logger.debug("Filterer instance does not match any of CollectionFilterer, ArrayFilterer or AbstractMutableDomainObjectFilterer. Skipping authorization.");
		}
		return returnObject.getFilteredObject();
	}

	private boolean hasSiteLevelAccessPermission(ResearchStaff researchStaff , Study study){
		List<HealthcareSite> healthcareSiteList = new ArrayList<HealthcareSite>();
		for(StudySite studySite:study.getStudySites()){
			healthcareSiteList.add(studySite.getHealthcareSite());
		}
		for(HealthcareSite hcSite : researchStaff.getHealthcareSites()){
    		if(healthcareSiteList.contains(hcSite) || hcSite.equals(study.getStudyCoordinatingCenter().getHealthcareSite())){
        		return true;
        	}
    	}
    	return false;
	}
	
	private boolean hasStudyLevelAccessPermission(ResearchStaff researchStaff , Study study){
		return study.isAssignedAndActivePersonnel(researchStaff);
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
