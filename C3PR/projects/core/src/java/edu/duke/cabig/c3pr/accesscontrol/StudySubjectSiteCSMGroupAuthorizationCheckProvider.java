package edu.duke.cabig.c3pr.accesscontrol;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import gov.nih.nci.security.acegi.csm.authorization.CSMAuthorizationCheck;
import gov.nih.nci.security.acegi.csm.authorization.CSMGroupAuthorizationCheck;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;

import org.acegisecurity.Authentication;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 20, 2007 Time: 12:36:31 PM To change this
 * template use File | Settings | File Templates.
 */
public class StudySubjectSiteCSMGroupAuthorizationCheckProvider implements CSMAuthorizationCheck {

    private CSMObjectIdGenerator siteObjectIdGenerator;

    private CSMGroupAuthorizationCheck csmGroupAuthorizationCheck;

    private Logger log = Logger.getLogger(StudySubjectSiteCSMGroupAuthorizationCheckProvider.class);

    public boolean checkAuthorization(Authentication authentication, String permission,
                    Object domainObject) {
        boolean hasPermission = false;
        log.debug("Invoking checkPermission on StudySubjectSiteCSMGroupAuthorizationCheckProvider");

        if (domainObject instanceof StudySubject) {
            StudySubject studySubject = (StudySubject) domainObject;
            HealthcareSite hcs = studySubject.getStudySite().getHealthcareSite();
            HealthcareSite coordinatingCenterOfStudySubject = 
            	studySubject.getStudySite().getStudy().getStudyCoordinatingCenter().getHealthcareSite();
            log.debug("### Checking permission for user on coordinating center of studySubject:" + coordinatingCenterOfStudySubject.getPrimaryIdentifier());
            if(csmGroupAuthorizationCheck.checkAuthorizationForObjectId(authentication,
                    permission, siteObjectIdGenerator.generateId(coordinatingCenterOfStudySubject))){
            	return true;
            }
            log.debug("### Checking permission for user on site:" + hcs.getPrimaryIdentifier());
            return csmGroupAuthorizationCheck.checkAuthorizationForObjectId(authentication,
                            permission, siteObjectIdGenerator.generateId(hcs));

        }
        else {
            log
                            .debug("Unsupported object sent to StudySubjectSiteCSMGroupAuthorizationCheckProvider. Expecting StudySubject object found "
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
