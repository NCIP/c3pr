package edu.duke.cabig.c3pr.accesscontrol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;

/**
 * The Class SubjectSecurityFilter.
 */
public class SubjectSecurityFilter implements DomainObjectSecurityFilterer{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SubjectSecurityFilter.class);

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.accesscontrol.DomainObjectSecurityFilterer#filter(org.acegisecurity.Authentication, java.lang.String, edu.duke.cabig.c3pr.accesscontrol.Filterer)
	 */
	public Object filter(Authentication authentication, String permission,
			Filterer returnObject) {
		
		logger.debug("Authorizing the user and filtering subjects.");
		if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
			Iterator collectionIter = returnObject.iterator();
			while (collectionIter.hasNext()) {
	        	Participant participant = (Participant)collectionIter.next();
	        	if(!hasSiteAndStudyLevelAccess(participant)){
	        		returnObject.remove(participant);
	        	}
			}
		}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
			Participant participant = (Participant)returnObject.getFilteredObject();
			if(!hasSiteAndStudyLevelAccess(participant)){
        		returnObject.remove(participant);
        	}
		}else{
			logger.debug("Filterer instance does not match any of CollectionFilterer, ArrayFilterer or AbstractMutableDomainObjectFilterer. Skipping authorization.");
		}
		return returnObject.getFilteredObject();
	}
	
	
	private boolean hasSiteAndStudyLevelAccess(Participant participant){
		//load all the roles the user has with the specified privilege
		Set<C3PRUserGroupType> userRoles = SecurityUtils.getUserRoles(UserPrivilegeType.SUBJECT_READ);
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
						.buildUserAccessibleOrganizationIdsList(role), participant)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * Checks if user has permission to access subject based on the org to which the subject belongs and
	 * to which the user has been granted access.
	 *
	 * @param userAccessibleOrganizationIdsList the user accessible organization ids list
	 * @param participant the participant
	 * @return true, if successful
	 */
	private boolean hasSiteLevelAccessPermission(List<String> userAccessibleOrganizationIdsList, Participant participant){
		List<HealthcareSite> participantOrganizations = new ArrayList<HealthcareSite>();
		participantOrganizations.addAll(participant.getHealthcareSites());
    	for(OrganizationAssignedIdentifier identifier : participant.getOrganizationAssignedIdentifiers()){
    		participantOrganizations.add(identifier.getHealthcareSite());
    	}
    	for(HealthcareSite participantOrganization: participantOrganizations){
    		if(userAccessibleOrganizationIdsList.contains(participantOrganization.getPrimaryIdentifier())){
        		return true;    			
    		}
    	}
    	return false;
	}

}
