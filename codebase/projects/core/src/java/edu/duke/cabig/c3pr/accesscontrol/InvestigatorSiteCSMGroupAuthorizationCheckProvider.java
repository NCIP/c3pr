/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import gov.nih.nci.cabig.ctms.acegi.csm.authorization.CSMAuthorizationCheck;
import gov.nih.nci.cabig.ctms.acegi.csm.authorization.CSMGroupAuthorizationCheck;
import gov.nih.nci.cabig.ctms.acegi.csm.authorization.CSMObjectIdGenerator;

/**
 * The Class InvestigatorSiteSecurityCSMGroupAuthorizationCheckProvider.
 * Filters out investigators who do not belong to the logged in users organization.
 * 
 * @author Vinay G
 */
public class InvestigatorSiteCSMGroupAuthorizationCheckProvider implements
                CSMAuthorizationCheck {

    private CSMObjectIdGenerator siteObjectIdGenerator;

    private CSMGroupAuthorizationCheck csmGroupAuthorizationCheck;

    private Logger log = Logger
                    .getLogger(InvestigatorSiteCSMGroupAuthorizationCheckProvider.class);

    public boolean checkAuthorization(Authentication authentication, String permission,
                    Object domainObject) {
        boolean hasPermission = false;
        log.debug("Invoking checkPermission on StudySiteSiteSecurityCSMGroupAuthorizationCheckProvider");

        if (domainObject instanceof Investigator) {
        	Investigator investigator = (Investigator) domainObject;
        	HealthcareSite hcs = null;
        	if (investigator.getHealthcareSiteInvestigators().size() > 0) {
                for (HealthcareSiteInvestigator healthcareSiteInvestigator : investigator.getHealthcareSiteInvestigators()) {
                    hcs = healthcareSiteInvestigator.getHealthcareSite();
                    log.debug("### Checking permission for user on site:"+ hcs.getPrimaryIdentifier());
                    hasPermission = csmGroupAuthorizationCheck.checkAuthorizationForObjectId(
                                    authentication, permission, siteObjectIdGenerator.generateId(hcs));
                    // only needs permission on one of the sites
                    if (hasPermission) break;
                }
            }
            else {
                hasPermission = true;
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
