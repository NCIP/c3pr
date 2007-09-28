package edu.duke.cabig.c3pr.accesscontrol;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;
import gov.nih.nci.security.acegi.csm.authorization.CSMAuthorizationCheck;
import org.acegisecurity.*;
import org.acegisecurity.afterinvocation.AfterInvocationProvider;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

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

    private String accessPrivilege = "Access";

    private String processConfigAttribute;
    private LinkedHashMap domainObjectSiteSecurityAuhthorizationCheckProvidersMap;
    private Class processDomainObjectClass = AbstractMutableDomainObject.class;

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
            if (log.isDebugEnabled()) {
                log.debug("Return object is not a collection, skipping");
            }
            return returnedObject;
        }
        // Locate unauthorised Collection elements
        Iterator collectionIter = filterer.iterator();

        log.debug("### Intercepting collection for Site Security check");

        while (collectionIter.hasNext()) {
            Object domainObject = collectionIter.next();

            boolean hasPermission = false;

            if (domainObject == null  || !getProcessDomainObjectClass().isAssignableFrom(returnedObject.getClass())) {
                hasPermission = true;
            }
            else{
                CSMAuthorizationCheck auth =  (CSMAuthorizationCheck)domainObjectSiteSecurityAuhthorizationCheckProvidersMap.get(domainObject.getClass().getName());
                hasPermission = auth.checkAuthorization(authentication,accessPrivilege,domainObject);
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

    public Class getProcessDomainObjectClass(){
        return processDomainObjectClass;
    }


    public String getAccessPrivilege() {
        return accessPrivilege;
    }

    public void setAccessPrivilege(String accessPrivilege) {
        this.accessPrivilege = accessPrivilege;
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

    public void setDomainObjectSiteSecurityAuhthorizationCheckProvidersMap(LinkedHashMap domainObjectSiteSecurityAuhthorizationCheckProvidersMap) {
        this.domainObjectSiteSecurityAuhthorizationCheckProvidersMap = domainObjectSiteSecurityAuhthorizationCheckProvidersMap;
    }
}
