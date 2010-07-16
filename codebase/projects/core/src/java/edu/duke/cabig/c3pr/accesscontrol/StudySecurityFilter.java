package edu.duke.cabig.c3pr.accesscontrol;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;

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
		
		logger.debug("Authorizing the user and filtering studies.");
		//check the type of filterer
		if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
			Iterator collectionIter = returnObject.iterator();
			while (collectionIter.hasNext()) {
	        	Study study = (Study)collectionIter.next();
	        	if(!hasSiteAndStudyLevelAccess(study)){
	        		returnObject.remove(study);
	        	}
			}
		}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
			Study study = (Study)returnObject.getFilteredObject();
			if(!hasSiteAndStudyLevelAccess(study)){
        		returnObject.remove(study);
        	}
		}else{
			logger.debug("Filterer instance does not match any of CollectionFilterer, ArrayFilterer or AbstractMutableDomainObjectFilterer. Skipping authorization.");
		}
		
		return returnObject.getFilteredObject();
	}

	
	private boolean hasSiteAndStudyLevelAccess(Study study){
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
						.buildUserAccessibleOrganizationIdsList(role), study)){
					if(!suiteRole.isStudyScoped()){
						//user is only scoped by site
						return true;
					} else {
						//user is both site and study scoped
						if(SecurityUtils.hasAllStudyAccess(role) || hasStudyLevelAccessPermission(SecurityUtils
								.buildUserAccessibleStudyIdsList(role), study)){
				    		return true;
				    	}
					}
				}
			}
		}
		return false;
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
		//some Coppa studies do not have CoordinatingCenterAssignedIdentifiers, so grant everyone access to such studies.
		if(study.getCoordinatingCenterAssignedIdentifier() == null){
			return true;
		}
		return userAccessibleStudyIdsList.contains(study.getCoordinatingCenterAssignedIdentifier().getValue());
	}

}
