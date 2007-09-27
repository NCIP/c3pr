package edu.duke.cabig.c3pr.accesscontrol;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.StudySubject;
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
 * Time: 12:36:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudySubjectSiteCSMGroupAuthorizationCheckProvider implements CSMAuthorizationCheck   {


    private CSMObjectIdGenerator siteObjectIdGenerator;
    private CSMGroupAuthorizationCheck csmGroupAuthorizationCheck;

    private Logger log = Logger.getLogger(StudySubjectSiteCSMGroupAuthorizationCheckProvider.class);


    public boolean checkAuthorization(Authentication authentication, String permission, Object domainObject) {
        if(domainObject instanceof StudySubject) {
            StudySubject subject = (StudySubject)domainObject;
            HealthcareSite hcs = subject.getStudySite().getHealthcareSite();
            log.debug("### Checking permission for user on site:" + hcs.getNciInstituteCode());
            return csmGroupAuthorizationCheck.checkAuthorizationForObjectId(authentication,"ACCESS",siteObjectIdGenerator.generateId(hcs));

        }
        return false;
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
