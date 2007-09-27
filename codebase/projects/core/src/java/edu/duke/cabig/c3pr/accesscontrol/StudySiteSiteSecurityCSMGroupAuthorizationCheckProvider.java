package edu.duke.cabig.c3pr.accesscontrol;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;
import gov.nih.nci.security.acegi.csm.authorization.CSMGroupAuthorizationCheck;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;
import gov.nih.nci.security.acegi.csm.authorization.CSMAuthorizationCheck;
import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 20, 2007
 * Time: 11:35:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class StudySiteSiteSecurityCSMGroupAuthorizationCheckProvider implements CSMAuthorizationCheck {

    private CSMObjectIdGenerator siteObjectIdGenerator;
    private CSMGroupAuthorizationCheck csmGroupAuthorizationCheck;

    private Logger log = Logger.getLogger(StudySiteSiteSecurityCSMGroupAuthorizationCheckProvider.class);


    public boolean checkAuthorization(Authentication authentication, String permission, Object domainObject) {
           boolean hasPermission = false;
        log.debug("Invoking checkPermission on StudySiteSiteSecurityCSMGroupAuthorizationCheckProvider");

        if(domainObject instanceof Study) {
            Study study = (Study)domainObject;
            //if no sites then make it globally accessible
            if(study.getStudySites().size()>0){
                for(StudySite site:study.getStudySites()){
                    HealthcareSite hcs = site.getHealthcareSite();
                    log.debug("### Checking permission for user on site:" + hcs.getNciInstituteCode());
                    hasPermission = csmGroupAuthorizationCheck.checkAuthorizationForObjectId(authentication,permission,siteObjectIdGenerator.generateId(hcs));
                    //only needs permission on one of the sites
                    if(hasPermission)
                        break;
                }
            }else{
                log.debug("Unsupported object sent to StudySiteSiteSecurityCSMGroupAuthorizationCheckProvider. Expecting Study object found " + domainObject.getClass().getName());
                hasPermission = true;
            }

        }
        return hasPermission;
    }

    public boolean checkAuthorizationForObjectId(Authentication authentication, String permission, String objectId) {
        return csmGroupAuthorizationCheck.checkAuthorizationForObjectId(authentication,permission,objectId);

    }

    public boolean checkAuthorizationForObjectIds(Authentication authentication, String permission, String[] objectIds) {
        return csmGroupAuthorizationCheck.checkAuthorizationForObjectIds(authentication,permission,objectIds);
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
