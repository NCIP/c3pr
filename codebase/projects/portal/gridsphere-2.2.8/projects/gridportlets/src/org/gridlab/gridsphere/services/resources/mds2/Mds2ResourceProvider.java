/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: Mds2ResourceProvider.java,v 1.1.1.1 2007-02-01 20:41:20 kherm Exp $
 */

package org.gridlab.gridsphere.services.resources.mds2;

import org.gridlab.gridsphere.services.resource.ResourceAttribute;
import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import javax.naming.directory.*;
import javax.naming.NamingException;
import javax.naming.NamingEnumeration;
import javax.naming.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.Hashtable;

/**
 * Provides a base class for mds2 resource information providers.
 */
public abstract class Mds2ResourceProvider {

    private static PortletLog log = SportletLog.getInstance(Mds2ResourceProvider.class);

    protected ResourceRegistryService resourceRegistry = null;
    protected Mds2ResourceProvider parentProvider = null;
    protected HashMap mds2ToResourceAttributeMap = new HashMap();
    protected List childResourceProviderList = new Vector();

    protected Resource resource = null;
    protected String resourceDn = null;
    protected String resourceName = null;

    private boolean isActive = true;

    public Mds2ResourceProvider(ResourceRegistryService resourceRegistry) {
        this.resourceRegistry = resourceRegistry;
    }

    public void destroy() {
        deactivate();
        resource = null;
        parentProvider = null;
        childResourceProviderList.clear();
    }

    public boolean getIsActive() {
        synchronized(this) {
            return isActive;
        }
    }


    public void deactivate() {
        log.debug("Deactivating " + getClass().getName());
        synchronized(this) {
            if (isActive) {
                isActive = false;
                for (int ii = 0; ii < childResourceProviderList.size(); ++ii) {
                    Mds2ResourceProvider resourceProvider = (Mds2ResourceProvider)childResourceProviderList.get(ii);
                    resourceProvider.deactivate();
                }
            }
        }
    }

    public Mds2ResourceProvider getParentProvider() {
        return parentProvider;
    }

    public void setParentProvider(Mds2ResourceProvider parentProvider) {
        this.parentProvider = parentProvider;
    }

    public void addResourceAttribute(String mds2Attribute, String resourceAttribute) {
        mds2ToResourceAttributeMap.put(mds2Attribute, resourceAttribute);
    }

    public void addChildResourceProvider(Mds2ResourceProvider resourceProvider) {
        resourceProvider.setParentProvider(this);
        childResourceProviderList.add(resourceProvider);
    }

    public String getResourceDn() {
        return resourceDn;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Resource getResource() {
        //if (resource == null) {
            //log.debug("Creating resource for object " + resourceDn);
            resource = createResource();
        //}
        return resource;
    }

    public abstract Resource createResource();

    public abstract String getResourceSearchDn();

    public abstract String getResourceSearchFilter();

    public abstract int getResourceSearchScope();

    public void updateResources(Mds2Resource infoResource)
            throws NamingException {
        NamingEnumeration resourceObjects = getResourceObjects(infoResource);
        while (resourceObjects.hasMoreElements()) {
            if (!getIsActive()) {
                break;
            }
            SearchResult resourceObject = (SearchResult) resourceObjects.next();
            //log.debug("Updating resource " + resourceObject.getName());
            updateResource(resourceObject);
            updateChildResources(infoResource);
        }
        resourceObjects.close();
    }

    public void updateChildResources(Mds2Resource infoResource)
            throws NamingException {
        for (int ii = 0; ii < childResourceProviderList.size(); ++ii) {
            if (!getIsActive()) {
                return;
            }
            Mds2ResourceProvider resourceProvider
                    = (Mds2ResourceProvider)childResourceProviderList.get(ii);
            resourceProvider.updateResources(infoResource);
        }
    }

    public NamingEnumeration getResourceObjects(Mds2Resource infoResource)
            throws NamingException {
        String host = infoResource.getHostName();
        String port = infoResource.getPort();
        String ldapUrl = "ldap://" + host + ':' + port;
        String baseDn = getResourceSearchDn();
        String filter = getResourceSearchFilter();
        int scope = getResourceSearchScope();
        String authentication = infoResource.getAuthentication();
        String timeout = infoResource.getTimeout();
        log.debug("Using authentication " + authentication);
        log.debug("Using timeout " + timeout);
        NamingEnumeration resourceObjects = null;
        //log.debug("Searching for " + filter + "  with base dn " + baseDn + " on "+ ldapUrl);
        try {
            Hashtable env = new Hashtable(4);
            env.put(Context.PROVIDER_URL, ldapUrl);
            env.put(Context.SECURITY_AUTHENTICATION, authentication);
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put("com.sun.jndi.ldap.connect.timeout", timeout);
            DirContext ctx = new InitialDirContext(env);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(scope);
            resourceObjects = ctx.search(baseDn, filter, controls);
            ctx.close();
        } catch (NamingException e) {
            log.error("Unable to perform ldap query", e);
            throw e;
        }
        //log.debug("Searched " + filter + "  with base dn " + baseDn + " on "+ ldapUrl);
        return resourceObjects;
    }

    public void updateResource(SearchResult resourceObject)
            throws NamingException {

        String resourceObjectName = resourceObject.getName();
        resourceDn = resourceObjectName + ',' + getResourceSearchDn();
        int index = resourceObjectName.indexOf("=");
        resourceName = resourceObjectName.substring(index+1);

        resource = getResource();
        if (resource != null) {
            updateResourceAttributes(resource, resourceObject);
            try {
                resourceRegistry.saveResource(resource);
            } catch (ResourceException e) {
                log.error("Unable to save resource ", e);
            }
        }
    }

    public void updateResourceAttributes(Resource resource, SearchResult resourceObject)
            throws NamingException {
        NamingEnumeration mds2Attributes = resourceObject.getAttributes().getAll();
        // Iterate through the mds attr names
        while (mds2Attributes.hasMoreElements()) {

            // Get the next mds attr name
            BasicAttribute mds2Attribute = (BasicAttribute)mds2Attributes.nextElement();
            String mds2AttrName = mds2Attribute.getID();
            //log.debug("Next mds attribute " + mds2AttrName);
            // Get the resource attribute to which this mds2 attribute maps
            String resourceAttrName = getResourceAttributeName(mds2AttrName);
            if (resourceAttrName != null) {
                //log.debug("Maps to resource attribute " + resourceAttrName);
                ResourceAttribute resourceAttribute = resource.getResourceAttribute(resourceAttrName);
                if (resourceAttribute == null) {
                    //log.debug("Creating resource attribute " + resourceAttrName);
                    resourceAttribute = new ResourceAttribute(resourceAttrName, null);
                    resource.putResourceAttribute(resourceAttribute);
                }
                // Get the values for this attribute
                NamingEnumeration mds2AttrValues = mds2Attribute.getAll();
                if (mds2AttrValues.hasMoreElements()) {
                    // Get next value
                    String mds2AttrValue = mds2AttrValues.nextElement().toString();
                    //log.debug("Mds attribute value " + mds2AttrValue);
                    if (mds2AttrValue.equals("NULL")) {
                        mds2AttrValue = null;
                    }
                    resourceAttribute.setValue(mds2AttrValue);
                }
            }
        }
    }

    public String getResourceAttributeName(String mds2Attribute) {
        return (String)mds2ToResourceAttributeMap.get(mds2Attribute);
    }
}
