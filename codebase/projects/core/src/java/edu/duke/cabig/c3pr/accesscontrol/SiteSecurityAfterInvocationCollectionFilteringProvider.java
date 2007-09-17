package edu.duke.cabig.c3pr.accesscontrol;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import gov.nih.nci.security.acegi.csm.authorization.CSMGroupAuthorizationCheck;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;
import gov.nih.nci.security.acegi.csm.authorization.CSMPrivilegeGenerator;
import org.acegisecurity.*;
import org.acegisecurity.afterinvocation.AfterInvocationProvider;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;

/**
 * Will filter collection of c3pr domain objects
 * based on Site permissions.
 *
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 9, 2007
 * Time: 5:46:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class SiteSecurityAfterInvocationCollectionFilteringProvider implements AfterInvocationProvider {

    private CSMGroupAuthorizationCheck authorizationCheck;
    private String processConfigAttribute;

    private CSMPrivilegeGenerator sitePrivilegeGenerator;
    private CSMObjectIdGenerator siteObjectIdGenerator;

    private Logger log = Logger.getLogger(SiteSecurityAfterInvocationCollectionFilteringProvider.class);

    public Object decide(Authentication authentication, Object object, ConfigAttributeDefinition configAttributeDefinition,
                         Object returnedObject) throws AccessDeniedException {

        if (returnedObject == null) {
            if (log.isDebugEnabled()) {
                log.debug("Return object is null, skipping");
            }

            return null;
        }

        Filterer filterer = null;

        if (returnedObject instanceof Collection) {
            Collection collection = (Collection) returnedObject;
            filterer = new CollectionFilterer(collection);
        } else if (returnedObject.getClass().isArray()) {
            Object[] array = (Object[]) returnedObject;
            filterer = new ArrayFilterer(array);
        } else {
            throw new AuthorizationServiceException("A Collection or an array (or null) was required as the "
                    + "returnedObject, but the returnedObject was: " + returnedObject);
        }
        // Locate unauthorised Collection elements
        Iterator collectionIter = filterer.iterator();

        log.debug("### Intercepting collection for Site Security check");

        while (collectionIter.hasNext()) {
            Object domainObject = collectionIter.next();

            boolean hasPermission = false;

            if (domainObject == null) {
                hasPermission = true;
            }

            else if(domainObject instanceof Study) {
                Study study = (Study)domainObject;
                for(StudySite site:study.getStudySites()){
                    HealthcareSite hcs = site.getHealthcareSite();
                    log.debug("### Checking permission for user on site:" + hcs.getNciInstituteCode());
                    hasPermission = authorizationCheck.checkAuthorizationForObjectId(authentication,"ACCESS",siteObjectIdGenerator.generateId(hcs));
                    //only needs permission on one of the sites
                    if(hasPermission)
                        break;
                }
            }
            else if(domainObject instanceof StudySubject) {
                StudySubject subject = (StudySubject)domainObject;
                HealthcareSite hcs = subject.getStudySite().getHealthcareSite();
                log.debug("### Checking permission for user on site:" + hcs.getNciInstituteCode());
                hasPermission = authorizationCheck.checkAuthorizationForObjectId(authentication,"ACCESS",siteObjectIdGenerator.generateId(hcs));
            }


            if (!hasPermission) {
                filterer.remove(domainObject);

                if (log.isDebugEnabled()) {
                    log.debug("### Principal is NOT authorised for element: " + domainObject);
                }
            }
        }

        return filterer.getFilteredObject();
    }


    public CSMGroupAuthorizationCheck getAuthorizationCheck() {
        return authorizationCheck;
    }

    public void setAuthorizationCheck(CSMGroupAuthorizationCheck authorizationCheck) {
        this.authorizationCheck = authorizationCheck;
    }


    public CSMPrivilegeGenerator getSitePrivilegeGenerator() {
        return sitePrivilegeGenerator;
    }

    public void setSitePrivilegeGenerator(CSMPrivilegeGenerator sitePrivilegeGenerator) {
        this.sitePrivilegeGenerator = sitePrivilegeGenerator;
    }

    public CSMObjectIdGenerator getSiteObjectIdGenerator() {
        return siteObjectIdGenerator;
    }

    public void setSiteObjectIdGenerator(CSMObjectIdGenerator siteObjectIdGenerator) {
        this.siteObjectIdGenerator = siteObjectIdGenerator;
    }

    public boolean supports(ConfigAttribute config) {
        return config.getAttribute().equals(getProcessConfigAttribute());
    }

    public boolean supports(Class aClass) {
        return true;
    }




    public String getProcessConfigAttribute() {
        return processConfigAttribute;
    }

    public void setProcessConfigAttribute(String processConfigAttribute) {
        this.processConfigAttribute = processConfigAttribute;
    }
}
