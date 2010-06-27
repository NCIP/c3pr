package edu.duke.cabig.c3pr.accesscontrol;

import java.util.Iterator;
import java.util.List;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.utils.SecurityUtils;

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
		
		//Use the provisioningSession to determine hasAllSiteAccess and if not then build userAccessibleOrganizationIdsList.
		List<String> userAccessibleOrganizationIdsList = null;
		boolean hasAllSiteAccess = SecurityUtils.hasAllSiteAccess();
		if(!hasAllSiteAccess){
			userAccessibleOrganizationIdsList = SecurityUtils.buildUserAccessibleOrganizationIdsList(UserPrivilegeType.SUBJECT_READ);
		}
		
		logger.debug("Authorizing the user and filtering subjects.");
		if(!hasAllSiteAccess){
			if(returnObject instanceof CollectionFilterer || returnObject instanceof ArrayFilterer){
				Iterator collectionIter = returnObject.iterator();
				while (collectionIter.hasNext()) {
		        	Participant participant = (Participant)collectionIter.next();
		        	if(!hasPermission(userAccessibleOrganizationIdsList, participant)){
		        		returnObject.remove(participant);
		        	}
				}
			}else if(returnObject instanceof AbstractMutableDomainObjectFilterer){
				Participant participant = (Participant)returnObject.getFilteredObject();
				if(!hasPermission(userAccessibleOrganizationIdsList, participant)){
	        		returnObject.remove(participant);
	        	}
			}else{
				logger.debug("Filterer instance does not match any of CollectionFilterer, ArrayFilterer or AbstractMutableDomainObjectFilterer. Skipping authorization.");
			}
		}
		return returnObject.getFilteredObject();
	}
	
	/**
	 * Checks if user has permission to access subject based on the org to which the subject belongs and
	 * to which the user has been granted access.
	 *
	 * @param userAccessibleOrganizationIdsList the user accessible organization ids list
	 * @param participant the participant
	 * @return true, if successful
	 */
	private boolean hasPermission(List<String> userAccessibleOrganizationIdsList, Participant participant){
		List<HealthcareSite> hcsList = participant.getHealthcareSites();
    	for(OrganizationAssignedIdentifier identifier : participant.getOrganizationAssignedIdentifiers()){
    		hcsList.add(identifier.getHealthcareSite());
    	}
    	for(HealthcareSite hcs: hcsList){
    		if(userAccessibleOrganizationIdsList.contains(hcs.getPrimaryIdentifier())){
        		return true;    			
    		}
    	}
    	return false;
	}

}
