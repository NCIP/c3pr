/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import java.util.List;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import gov.nih.nci.cabig.ctms.acegi.csm.authorization.CSMAuthorizationCheck;
import gov.nih.nci.cabig.ctms.acegi.csm.authorization.CSMGroupAuthorizationCheck;
import gov.nih.nci.cabig.ctms.acegi.csm.authorization.CSMObjectIdGenerator;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 20, 2007 Time: 12:36:31 PM To change this
 * template use File | Settings | File Templates.
 */
public class ParticipantSiteCSMGroupAuthorizationCheckProvider implements CSMAuthorizationCheck {

    private CSMObjectIdGenerator siteObjectIdGenerator;

    private CSMGroupAuthorizationCheck csmGroupAuthorizationCheck;

    private Logger log = Logger.getLogger(ParticipantSiteCSMGroupAuthorizationCheckProvider.class);

    public boolean checkAuthorization(Authentication authentication, String permission,
                    Object domainObject) {
        boolean hasPermission = false;
        log.debug("Invoking checkPermission on ParticipantSiteCSMGroupAuthorizationCheckProvider");

        if (domainObject instanceof Participant) {
        	Participant participant = (Participant) domainObject;
        	
        	List<HealthcareSite> hcsList = participant.getHealthcareSites();
            for(HealthcareSite hcs: hcsList){
            	log.debug("### Checking permission for user on site:" + hcs.getPrimaryIdentifier());
            	hasPermission = hasPermission || csmGroupAuthorizationCheck.checkAuthorizationForObjectId(authentication,
                        permission, siteObjectIdGenerator.generateId(hcs));
            }
            
            List<OrganizationAssignedIdentifier> oaiList = participant.getOrganizationAssignedIdentifiers();
        	for(OrganizationAssignedIdentifier oai: oaiList){
        		log.debug("### Checking permission for user on site:" + oai.getHealthcareSite().getPrimaryIdentifier());
            	hasPermission = hasPermission || csmGroupAuthorizationCheck.checkAuthorizationForObjectId(authentication,
                        permission, siteObjectIdGenerator.generateId(oai.getHealthcareSite()));
        	}
            
            return hasPermission;
        }
        else {
            log.debug("Unsupported object sent to ParticipantSiteCSMGroupAuthorizationCheckProvider. Expecting StudySubject object found "
                                            + domainObject.getClass().getName());
            hasPermission = true;
        }

        return hasPermission;
    }

    public boolean checkAuthorizationForObjectId(Authentication authentication, String permission,
                    String objectId) {
        return csmGroupAuthorizationCheck.checkAuthorizationForObjectId(authentication, permission,
                        objectId);

    }

    public boolean checkAuthorizationForObjectIds(Authentication authentication, String permission,
                    String[] objectIds) {
        return csmGroupAuthorizationCheck.checkAuthorizationForObjectIds(authentication,
                        permission, objectIds);
    }

    public CSMObjectIdGenerator getSiteObjectIdGenerator() {
        return siteObjectIdGenerator;
    }

    public void setSiteObjectIdGenerator(CSMObjectIdGenerator siteObjectIdGenerator) {
        this.siteObjectIdGenerator = siteObjectIdGenerator;
    }

    public CSMGroupAuthorizationCheck getCsmGroupAuthorizationCheck() {
        return csmGroupAuthorizationCheck;
    }

    public void setCsmGroupAuthorizationCheck(CSMGroupAuthorizationCheck csmGroupAuthorizationCheck) {
        this.csmGroupAuthorizationCheck = csmGroupAuthorizationCheck;
    }
}
