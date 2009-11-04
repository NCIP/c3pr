package edu.duke.cabig.c3pr.accesscontrol;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;
import gov.nih.nci.security.acegi.csm.authorization.CSMAuthorizationCheck;
import gov.nih.nci.security.constants.Constants;

import java.util.LinkedHashMap;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.afterinvocation.AfterInvocationProvider;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 24, 2007 Time: 3:27:52 PM To change this template
 * use File | Settings | File Templates.
 */
public class SiteSecurityAfterInvocationBasicAuthorizationCheckProvider implements
                AfterInvocationProvider {

    private String accessPrivilege = Constants.CSM_ACCESS_PRIVILEGE; // default

    private String processConfigAttribute;

    private LinkedHashMap domainObjectSiteSecurityAuhthorizationCheckProvidersMap;

    private Class processDomainObjectClass = AbstractMutableDomainObject.class;

    private Logger log = Logger
                    .getLogger(SiteSecurityAfterInvocationCollectionFilteringProvider.class);

    public Object decide(Authentication authentication, Object object,
                    ConfigAttributeDefinition configAttributeDefinition, Object returnedObject)
                    throws AccessDeniedException {

        if (returnedObject == null) {
            if (log.isDebugEnabled()) {
                log.debug("Return object is null, skipping");
            }

            return null;
        }

        log.debug("Checking authorization on object " + returnedObject.getClass().getName());

        if (!getProcessDomainObjectClass().isAssignableFrom(returnedObject.getClass())) {
            if (log.isDebugEnabled()) {
                log.debug("Return object is not applicable for this provider, skipping");
            }

            return returnedObject;
        }

        if (!domainObjectSiteSecurityAuhthorizationCheckProvidersMap.containsKey(returnedObject
                        .getClass().getName())) {
            log
                            .warn("Slipping Authorization. No appropriate CSMAuthorizationCheck object found for object type: "
                                            + returnedObject.getClass().getName());
            return returnedObject;
        }

        CSMAuthorizationCheck auth = (CSMAuthorizationCheck) domainObjectSiteSecurityAuhthorizationCheckProvidersMap
                        .get(returnedObject.getClass().getName());
        boolean hasPermission = auth.checkAuthorization(authentication, accessPrivilege,
                        returnedObject);

        if (hasPermission) {
            return returnedObject;
        }
        else {
            if (log.isDebugEnabled()) {
                log.debug("Denying access");
            }

            throw new AccessDeniedException(
                            "User does not have permission to view to this Study Site");
        }
    }

    public String getAccessPrivilege() {
        return accessPrivilege;
    }

    public void setAccessPrivilege(String accessPrivilege) {
        this.accessPrivilege = accessPrivilege;
    }

    public Class getProcessDomainObjectClass() {
        return processDomainObjectClass;
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

    public LinkedHashMap getDomainObjectSiteSecurityAuhthorizationCheckProvidersMap() {
        return domainObjectSiteSecurityAuhthorizationCheckProvidersMap;
    }

    public void setDomainObjectSiteSecurityAuhthorizationCheckProvidersMap(
                    LinkedHashMap domainObjectSiteSecurityAuhthorizationCheckProvidersMap) {
        this.domainObjectSiteSecurityAuhthorizationCheckProvidersMap = domainObjectSiteSecurityAuhthorizationCheckProvidersMap;
    }
}
