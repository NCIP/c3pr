/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import gov.nih.nci.cabig.ctms.acegi.csm.authorization.AuthorizationSwitch;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.afterinvocation.AfterInvocationProvider;
import org.apache.log4j.Logger;

public class C3PRBasedAfterInvocationFilteringProvider implements AfterInvocationProvider{
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger
			.getLogger(C3PRBasedAfterInvocationFilteringProvider.class);

	private AuthorizationSwitch authorizationSwitch;
	
	private String processConfigAttribute;
	
	private LinkedHashMap<Class<? extends AbstractMutableDomainObject> , DomainObjectSecurityFilterer> domainObjectC3PRAuhthorizationCheckProvidersMap;
	
	public Object decide(Authentication authentication, Object object,
            ConfigAttributeDefinition configAttributeDefinition, Object result)
			throws AccessDeniedException {
		if (result == null) {
            log.debug("Return object is null, skipping");
            return null;
        }
		
		if (!authorizationSwitch.isOn()) {
			log.debug("Authrization switch is off, skipping authrization");
        	return result;
        }
		
        Filterer filterer = null;
        Object searchedObject = null;

        if (result instanceof Collection || result.getClass().isArray()) {
	        if (result instanceof Collection) {
	            Collection collection = (Collection) result;
	            filterer = new CollectionFilterer(collection);
	            
	        } else if (result.getClass().isArray()) {
	            Object[] array = (Object[]) result;
	            filterer = new ArrayFilterer(array);
	        }
	        if (!filterer.iterator().hasNext()) {
            	log.debug("Collection is empty, skipping authrization");
            	return result;
            }
	        searchedObject = filterer.iterator().next();
        }else if (result instanceof AbstractMutableDomainObject) {
            filterer = new AbstractMutableDomainObjectFilterer((AbstractMutableDomainObject)result);
            searchedObject = filterer.getFilteredObject();
        }else {
            log.debug("Return object is not a collection or child of AbstractMutableDomainObjectFilterer, skipping");
            return result;
        }
        
        // load objects from domainObjectSiteSecurityAuhthorizationCheckProvidersMap , applicationContext-core-security.xml
        DomainObjectSecurityFilterer auth = null;
        for(Class<? extends AbstractMutableDomainObject> domainObjectClass : domainObjectC3PRAuhthorizationCheckProvidersMap.keySet()){
        	if(domainObjectClass.isAssignableFrom(searchedObject.getClass())){
        		auth = domainObjectC3PRAuhthorizationCheckProvidersMap
                .get(domainObjectClass);
        		log.debug("Found security filter '"+auth.getClass().getSimpleName()+"'for "+searchedObject.getClass().getSimpleName());
        		break;
        	}
        }
        
        //no filtering is required if a filterer is not configured.
        if(auth == null){
        	log.debug("No filter configured for collection of class "+searchedObject.getClass().getSimpleName()+", skipping");
        	return filterer.getFilteredObject();
        }
        
        return auth.filter(authentication, "ACCESS", filterer);
        //return filteredResults.getFilteredObject();
	}

	public boolean supports(ConfigAttribute config) {
		return config.getAttribute().equals(processConfigAttribute);
	}

	public boolean supports(Class returnClass) {
		return true;
	}

	public void setProcessConfigAttribute(String processConfigAttribute) {
		this.processConfigAttribute = processConfigAttribute;
	}

	public void setDomainObjectC3PRAuhthorizationCheckProvidersMap(
			LinkedHashMap<Class<? extends AbstractMutableDomainObject> , DomainObjectSecurityFilterer> domainObjectC3PRAuhthorizationCheckProvidersMap) {
		this.domainObjectC3PRAuhthorizationCheckProvidersMap = domainObjectC3PRAuhthorizationCheckProvidersMap;
	}

	public void setAuthorizationSwitch(AuthorizationSwitch authorizationSwitch) {
		this.authorizationSwitch = authorizationSwitch;
	}


}
