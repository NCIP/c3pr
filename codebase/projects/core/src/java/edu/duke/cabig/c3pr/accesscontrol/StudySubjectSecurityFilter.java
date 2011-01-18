package edu.duke.cabig.c3pr.accesscontrol;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;

/**
 * The Class StudySubjectSecurityFilter.
 */
public class StudySubjectSecurityFilter implements DomainObjectSecurityFilterer{

	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(StudySubjectSecurityFilter.class);

	
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
				StudySubject studySubject = (StudySubject)collectionIter.next();
	        	if(!hasSiteAndStudyLevelAccess(studySubject)){
	        		returnObject.remove(studySubject);
	        	}
			}
		}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
			StudySubject studySubject = (StudySubject)returnObject.getFilteredObject();
			if(!hasSiteAndStudyLevelAccess(studySubject)){
        		returnObject.remove(studySubject);
        	}
		}else{
			logger.debug("Filterer instance does not match any of CollectionFilterer, ArrayFilterer or AbstractMutableDomainObjectFilterer. Skipping authorization.");
		}
		return returnObject.getFilteredObject();
	}

	
	private boolean hasSiteAndStudyLevelAccess(StudySubject studySubject){
		//load all the roles the user has with the specified privilege
		Set<C3PRUserGroupType> userRoles = SecurityUtils.getRolesForLoggedInUser(UserPrivilegeType.STUDYSUBJECT_READ);
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
						.buildAccessibleOrganizationIdsListForLoggedInUser(role), studySubject)){
					if(!suiteRole.isStudyScoped()){
						//user is only scoped by site
						return true;
					} else {
						//user is both site and study scoped
						if(SecurityUtils.hasAllStudyAccess(role) || hasStudyLevelAccessPermission(SecurityUtils
								.buildAccessibleStudyIdsListForLoggedInUser(role), studySubject)){
				    		return true;
				    	}
					}
				}
			}
		}
		return false;
	}
	
	private boolean hasSiteLevelAccessPermission(List<String> userAccessibleOrganizationIdsList , StudySubject studySubject){
		return userAccessibleOrganizationIdsList.contains(studySubject.getStudySite().getHealthcareSite().getPrimaryIdentifier()) ||
		userAccessibleOrganizationIdsList.contains(studySubject.getStudySite().getStudy().getStudyCoordinatingCenter().getHealthcareSite().getPrimaryIdentifier());
	}
	
	private boolean hasStudyLevelAccessPermission(List<String> userAccessibleStudyIdsList , StudySubject studySubject){
		return userAccessibleStudyIdsList.contains(studySubject.getStudySite().getStudy().getCoordinatingCenterAssignedIdentifier().getValue());
	}
	

}
