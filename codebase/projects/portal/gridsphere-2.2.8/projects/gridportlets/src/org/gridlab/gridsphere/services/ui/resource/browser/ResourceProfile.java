/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceProfile.java,v 1.1.1.1 2007-02-01 20:42:13 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.util.StringAttribute;

import java.util.List;
import java.util.ArrayList;

public class ResourceProfile {

    private static PortletLog log = SportletLog.getInstance(ResourceProfile.class);

    public static final ResourceProfile INSTANCE = new ResourceProfile();

    protected ResourceType resourceType = ResourceType.INSTANCE;
    protected Class resourceListViewClass = BaseResourceListViewPage.class;
    protected Class resourceViewClass = BaseResourceViewPage.class;
    protected ArrayList resourceEditClassList = new ArrayList();

    protected String resourceClassName = ResourceType.class.getName();
    protected String key = "org.gridlab.gridsphere.services.ui.resource";
    protected String description = "Resource";
    protected int order = 0;
    protected int listOrder = 0;
    protected String resourceListViewClassName = BaseResourceListViewPage.class.getName();
    protected String resourceViewClassName = BaseResourceViewPage.class.getName();
    protected ArrayList resourceEditClassNameList = new ArrayList();
    protected ArrayList profileAttributes = null;
    protected ArrayList resourceProfiles = null;

    private ArrayList requiredResourceList = new ArrayList();
    private ArrayList requiredResourceClassNameList = new ArrayList();

    public ResourceProfile() {
        log.debug("ResourceProfile() Creating new instance");
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
        resourceClassName = resourceType.getID();
    }

    public Class getResourceListViewClass() {
        return resourceListViewClass;
    }

    public Class getResourceViewClass() {
        return resourceViewClass;
    }

    public Class getResourceEditClass() {
        return resourceViewClass;
    }

    public List getResourceEditClassList() {
        log.debug("getResourceSpecEditPageClassList()");
        if (resourceEditClassList.size() == 0) {
            log.debug("Creating classes from class name list " + resourceEditClassNameList.size());
            for (int ii = 0; ii < resourceEditClassNameList.size(); ++ii)  {
                String nextPageClassName = (String)resourceEditClassNameList.get(ii);
                Class nextPageClass = getClass(nextPageClassName);
                if (nextPageClass != null) {
                    resourceEditClassList.add(nextPageClass);
                }
            }
        }
        log.debug("Returning class list of size " + resourceEditClassList.size());
        return resourceEditClassList;
    }

    public String getResourceClassName() {
        return resourceClassName;
    }

    public void setResourceClassName(String resourceClassName) {
        this.resourceClassName = resourceClassName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getListOrder() {
        return listOrder;
    }

    public void setListOrder(int listOrder) {
        this.listOrder = listOrder;
    }

    public String getResourceListViewClassName() {
        return resourceListViewClassName;
    }

    public void setResourceListViewClassName(String resourceListViewClassName) {
        this.resourceListViewClassName = resourceListViewClassName;
        this.resourceListViewClass = getClass(resourceListViewClassName);
    }

    public String getResourceViewClassName() {
        return resourceViewClassName;
    }

    public void setResourceViewClassName(String resourceViewClassName) {
        this.resourceViewClassName = resourceViewClassName;
        this.resourceViewClass = getClass(resourceViewClassName);
    }

    public ArrayList getResourceEditClassNameList() {
        log.debug("getResourceEditClassNameList()");
        log.debug("Returning class name list of size " + resourceEditClassNameList.size());
        return resourceEditClassNameList;
    }

    public void setResourceEditClassNameList(ArrayList resourceEditClassNameList) {
        log.debug("setResourceEditClassNameList()");
        this.resourceEditClassNameList = resourceEditClassNameList;
        resourceEditClassList.clear();
        log.debug("Creating classes from class name list " + resourceEditClassNameList.size());
        for (int ii = 0; ii < resourceEditClassNameList.size(); ++ii)  {
            String nextPageClassName = (String)resourceEditClassNameList.get(ii);
            Class nextPageClass = getClass(nextPageClassName);
            if (nextPageClass != null) {
                resourceEditClassList.add(nextPageClass);
            }
        }
    }

    protected static Class getClass(String className) {
        try {
            return Class.forName(className, true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("Action component class not found: " + className, e);
            return null;
        }
    }

    public List getProfileAttributes() {
        return profileAttributes;
    }

    public void setProfileAttributes(ArrayList profileAttributes) {
        this.profileAttributes = profileAttributes;
    }

    public StringAttribute getProfileAttribute(String name) {
        int position = findProfileAttribute(name);
        if (position > -1) {
            return (StringAttribute) profileAttributes.get(position);
        }
        return null;
    }

    private int findProfileAttribute(String name) {
        for (int ii = 0; ii < profileAttributes.size(); ++ii) {
            StringAttribute var = (StringAttribute) profileAttributes.get(ii);
            if (var.getName().equals(name)) {
                return ii;
            }
        }
        return -1;
    }

    public List getResourceProfiles() {
        return resourceProfiles;
    }

    public void setResourceProfiles(ArrayList resourceProfiles) {
        this.resourceProfiles = resourceProfiles;
    }

    public ResourceProfile getResourceProfile(String key) {
        int position = findResourceProfile(key);
        if (position > -1) {
            return (ResourceProfile) resourceProfiles.get(position);
        }
        return null;
    }

    private int findResourceProfile(String key) {
        for (int ii = 0; ii < resourceProfiles.size(); ++ii) {
            ResourceProfile var = (ResourceProfile) resourceProfiles.get(ii);
            if (var.getKey().equals(key)) {
                return ii;
            }
        }
        return -1;
    }

    public boolean equals(ResourceProfile profile) {
        return key.equals(profile.key);
    }

    public ArrayList getRequiredResourceList() {
        if (requiredResourceList.size() == 0) {
            for (int ii = 0; ii < requiredResourceClassNameList.size(); ++ii)  {
                String nextClassName = (String)requiredResourceClassNameList.get(ii);
                Class nextResourceClass = getClass(nextClassName);
                if (nextResourceClass != null) {
                    requiredResourceList.add(nextResourceClass);
                }
            }
        }
        return requiredResourceList;
    }

    public ArrayList getRequiredResourceClassNameList() {
        return requiredResourceClassNameList;
    }

    public void setRequiredResourceClassNameList(ArrayList requiredResourceClassNameList) {
        this.requiredResourceClassNameList = requiredResourceClassNameList;
        requiredResourceList.clear();
        log.debug("Creating classes from class name list " + resourceEditClassNameList.size());
        for (int ii = 0; ii < requiredResourceClassNameList.size(); ++ii)  {
            String nextClassName = (String)requiredResourceClassNameList.get(ii);
            Class nextResourceClass = getClass(nextClassName);
            if (nextResourceClass != null) {
                requiredResourceList.add(nextResourceClass);
            }
        }
    }
}
