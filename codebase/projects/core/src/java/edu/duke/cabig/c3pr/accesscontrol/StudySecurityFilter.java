package edu.duke.cabig.c3pr.accesscontrol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.SecurityUtils;

/**
 * The Class StudySecurityFilter.
 */
public class StudySecurityFilter implements DomainObjectSecurityFilterer{

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(StudySecurityFilter.class);

	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.accesscontrol.DomainObjectSecurityFilterer#filter(org.acegisecurity.Authentication, java.lang.String, edu.duke.cabig.c3pr.accesscontrol.Filterer)
	 */
	public Object filter(Authentication authentication, String permission,
			Filterer returnObject) {
		
		//Use the provisioningSession to determine hasAllStudyAccess and if not then build hasAllStudyAccess.
		List<String> userAccessibleStudyIdsList = null;
		boolean hasAllStudyAccess = SecurityUtils.hasAllStudyAccess(authentication);
		if(!hasAllStudyAccess){
			userAccessibleStudyIdsList = SecurityUtils.buildUserAccessibleStudyIdsList(authentication);
		}
		
		//Use the provisioningSession to determine hasAllSiteAccess and if not then build userAccessibleOrganizationIdsList.
		List<String> userAccessibleOrganizationIdsList = null;
		boolean hasAllSiteAccess = SecurityUtils.hasAllSiteAccess(authentication);
		if(!hasAllSiteAccess){
			userAccessibleOrganizationIdsList = SecurityUtils.buildUserAccessibleOrganizationIdsList(authentication);
		}
		
		logger.debug("Authorizing the user and filtering studies.");
		//check the type of filterer
		if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
			Iterator collectionIter = returnObject.iterator();
			while (collectionIter.hasNext()) {
	        	Study study = (Study)collectionIter.next();
	        	if(!hasAllStudyAccess && !hasStudyLevelAccessPermission(userAccessibleStudyIdsList, study)){
	        		returnObject.remove(study);
	        	}
	        	if(!hasAllSiteAccess && !hasSiteLevelAccessPermission(userAccessibleOrganizationIdsList, study)){
	        		returnObject.remove(study);
	        	}
			}
		}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
			Study study = (Study)returnObject.getFilteredObject();
        	if(!hasAllStudyAccess && !hasStudyLevelAccessPermission(userAccessibleStudyIdsList, study)){
        		returnObject.remove(study);
        	}
        	if(!hasAllSiteAccess && !hasSiteLevelAccessPermission(userAccessibleOrganizationIdsList, study)){
        		returnObject.remove(study);
        	}
		}else{
			logger.debug("Filterer instance does not match any of CollectionFilterer, ArrayFilterer or AbstractMutableDomainObjectFilterer. Skipping authorization.");
		}
		
		return returnObject.getFilteredObject();
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

}
