package edu.duke.cabig.c3pr.accesscontrol;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.PersonUser;
import gov.nih.nci.security.acegi.csm.authorization.CSMAuthorizationCheck;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;

import java.util.List;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;


/**
 * The Class ResearchStaffSiteSecurityCSMGroupAuthorizationCheckProvider.
 * Filters out staff who do not belong to the logged in users organization.
 * 
 * @author Vinay G
 */
public class ResearchStaffSiteCSMGroupAuthorizationCheckProvider implements
                CSMAuthorizationCheck {

    private CSMObjectIdGenerator siteObjectIdGenerator;

    private CSMAuthorizationCheck csmAuthorizationCheck;

    private Logger log = Logger
                    .getLogger(ResearchStaffSiteCSMGroupAuthorizationCheckProvider.class);

    public boolean checkAuthorization(Authentication authentication, String permission,
                    Object domainObject) {
        boolean hasPermission = false;
        log.debug("Invoking checkPermission on StudySiteSiteSecurityCSMGroupAuthorizationCheckProvider");

        if (domainObject instanceof PersonUser) {
        	PersonUser researchStaff = (PersonUser) domainObject;
        	//get the researchStaff's hcs and see if logged in user has access to that organization.
        	
        	List<HealthcareSite> hcSites = researchStaff.getHealthcareSites();
        	for(HealthcareSite hcSite : hcSites){
            	log.debug("### Checking permission for user on site:"+ hcSite.getPrimaryIdentifier());
                hasPermission = csmAuthorizationCheck.checkAuthorizationForObjectId(authentication, permission, siteObjectIdGenerator.generateId(hcSite));
                if(hasPermission){
                	break ;
                }
            }
        }
        else {
            log.debug("Unsupported object sent to StudySiteSiteSecurityCSMGroupAuthorizationCheckProvider. Expecting Study object found "
                                            + domainObject.getClass().getName());
        }
        return hasPermission;
    }

    public boolean checkAuthorizationForObjectId(Authentication authentication, String permission,
                    String objectId) {
        return csmAuthorizationCheck.checkAuthorizationForObjectId(authentication, permission,
                        objectId);
    }

    public boolean checkAuthorizationForObjectIds(Authentication authentication, String permission,
                    String[] objectIds) {
        return csmAuthorizationCheck.checkAuthorizationForObjectIds(authentication,
                        permission, objectIds);
    }

    public CSMObjectIdGenerator getSiteObjectIdGenerator() {
        return siteObjectIdGenerator;
    }

    public void setSiteObjectIdGenerator(CSMObjectIdGenerator siteObjectIdGenerator) {
        this.siteObjectIdGenerator = siteObjectIdGenerator;
    }

	public void setCsmAuthorizationCheck(CSMAuthorizationCheck csmAuthorizationCheck) {
		this.csmAuthorizationCheck = csmAuthorizationCheck;
	}

}
