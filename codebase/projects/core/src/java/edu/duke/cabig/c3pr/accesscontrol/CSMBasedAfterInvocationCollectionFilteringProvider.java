/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import gov.nih.nci.cabig.ctms.acegi.csm.authorization.CSMAuthorizationCheck;
import gov.nih.nci.cabig.ctms.domain.MutableDomainObject;
import gov.nih.nci.security.constants.Constants;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.afterinvocation.AfterInvocationProvider;
import org.apache.log4j.Logger;

/**
 * Will filter collection of c3pr domain objects based on Site permissions. <p/> <p/> Created by
 * IntelliJ IDEA. User: kherm Date: Sep 9, 2007 Time: 5:46:31 PM To change this template use File |
 * Settings | File Templates.
 */
public class CSMBasedAfterInvocationCollectionFilteringProvider implements
                AfterInvocationProvider {

    private String accessPrivilege = Constants.CSM_ACCESS_PRIVILEGE;

    private String processConfigAttribute;

    private LinkedHashMap domainObjectCSMAuhthorizationCheckProvidersMap;

    private Class processDomainObjectClass = MutableDomainObject.class;

    private Logger log = Logger
                    .getLogger(CSMBasedAfterInvocationCollectionFilteringProvider.class);

    public Object decide(Authentication authentication, Object object,
                    ConfigAttributeDefinition configAttributeDefinition, Object returnedObject)
                    throws AccessDeniedException {

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
        }
        else if (returnedObject.getClass().isArray()) {
            Object[] array = (Object[]) returnedObject;
            filterer = new ArrayFilterer(array);
        }
        else {
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

            if (domainObject == null
                            || !getProcessDomainObjectClass().isAssignableFrom(
                                            domainObject.getClass())) {
                log.debug("Unsupported domain object in collection. Skipping authorization check");
                hasPermission = true;
            }
            else {

                if (!domainObjectCSMAuhthorizationCheckProvidersMap
                                .containsKey(domainObject.getClass().getName())) {
                    log
                                    .warn("Skipping Authorization. No appropriate CSMAuthorizationCheck object found for object type: "
                                                    + returnedObject.getClass().getName());
                    hasPermission = true;
                }
                else {
                    CSMAuthorizationCheck auth = (CSMAuthorizationCheck) domainObjectCSMAuhthorizationCheckProvidersMap
                                    .get(domainObject.getClass().getName());
                    hasPermission = auth.checkAuthorization(authentication, accessPrivilege,
                                    domainObject);
                }
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

    public Class getProcessDomainObjectClass() {
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

	public void setDomainObjectCSMAuhthorizationCheckProvidersMap(
			LinkedHashMap domainObjectCSMAuhthorizationCheckProvidersMap) {
		this.domainObjectCSMAuhthorizationCheckProvidersMap = domainObjectCSMAuhthorizationCheckProvidersMap;
	}

}
