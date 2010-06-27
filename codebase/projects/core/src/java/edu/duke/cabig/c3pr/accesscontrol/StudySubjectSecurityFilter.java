package edu.duke.cabig.c3pr.accesscontrol;

import java.util.Iterator;
import java.util.List;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.SecurityUtils;

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

		//Use the provisioningSession to determine hasAllStudyAccess and if not then build userAccessibleStudyIdsList.
		List<String> userAccessibleStudyIdsList = null;
		boolean hasAllStudyAccess = SecurityUtils.hasAllStudyAccess();
		if(!hasAllStudyAccess){
			userAccessibleStudyIdsList = SecurityUtils.buildUserAccessibleStudyIdsList();
		}
		
		//Use the provisioningSession to determine hasAllSiteAccess and if not then build userAccessibleOrganizationIdsList.
		List<String> userAccessibleOrganizationIdsList = null;
		boolean hasAllSiteAccess = SecurityUtils.hasAllSiteAccess();
		if(!hasAllSiteAccess){
			userAccessibleOrganizationIdsList = SecurityUtils.buildUserAccessibleOrganizationIdsList(UserPrivilegeType.STUDYSUBJECT_READ);
		}
		
		logger.debug("Authorizing the user and filtering studies.");
		//check the type of filterer
		if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
			Iterator collectionIter = returnObject.iterator();
			while (collectionIter.hasNext()) {
	        	StudySubject studySubject = (StudySubject)collectionIter.next();
	        	if(!hasAllStudyAccess && !hasStudyLevelAccessPermission(userAccessibleStudyIdsList, studySubject)){
	        		returnObject.remove(studySubject);
	        	}
	        	if(!hasAllSiteAccess && !hasSiteLevelAccessPermission(userAccessibleOrganizationIdsList, studySubject)){
	        		returnObject.remove(studySubject);
	        	}
			}
		}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
			StudySubject studySubject = (StudySubject)returnObject.getFilteredObject();
        	if(!hasAllStudyAccess && !hasStudyLevelAccessPermission(userAccessibleStudyIdsList, studySubject)){
        		returnObject.remove(studySubject);
        	}
        	if(!hasAllSiteAccess && !hasSiteLevelAccessPermission(userAccessibleOrganizationIdsList, studySubject)){
        		returnObject.remove(studySubject);
        	}
		}else{
			logger.debug("Filterer instance does not match any of CollectionFilterer, ArrayFilterer or AbstractMutableDomainObjectFilterer. Skipping authorization.");
		}
		return returnObject.getFilteredObject();
	}

	private boolean hasSiteLevelAccessPermission(List<String> userAccessibleOrganizationIdsList , StudySubject studySubject){
		return userAccessibleOrganizationIdsList.contains(studySubject.getStudySite().getHealthcareSite()) ||
		userAccessibleOrganizationIdsList.contains(studySubject.getStudySite().getStudy().getStudyCoordinatingCenter().getHealthcareSite());
	}
	
	private boolean hasStudyLevelAccessPermission(List<String> userAccessibleStudyIdsList , StudySubject studySubject){
		return userAccessibleStudyIdsList.contains(studySubject.getStudySite().getStudy().getCoordinatingCenterAssignedIdentifier().getValue());
	}
	

}
