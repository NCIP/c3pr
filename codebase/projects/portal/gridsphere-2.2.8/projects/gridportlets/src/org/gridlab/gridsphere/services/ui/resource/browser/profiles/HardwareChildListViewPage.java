/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardwareChildListViewPage.java,v 1.1.1.1 2007-02-01 20:42:14 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser.profiles;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.HardwareResourceType;
import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.resource.browser.*;

import javax.portlet.PortletException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;

public class HardwareChildListViewPage extends BaseResourceListViewPage {

    private transient static PortletLog log = SportletLog.getInstance(HardwareChildListViewPage.class);
    private int childLevel = 1;

    public HardwareChildListViewPage(ActionComponentFrame container, String compName) {
         super(container, compName);
        log.debug("HardwareResourceListViewPage()");
        // Default page
        setDefaultJspPage("/jsp/resource/browser/HardwareResourceListViewPage.jsp");
    }

    public Resource getParentResource() throws PortletException {
        ResourceBrowser browser = (ResourceBrowser)container.getParentComponent();
        String parentDn = browser.getHardwareResourceDn();
        setSelectedParentDn(parentDn);
        setSelectedParentType(HardwareResourceType.INSTANCE);
        if (parentDn == null) {
            log.debug("Parent resource dn is null");
            return null;
        } else {
            log.debug("Parent resource dn is " + parentDn);
            return resourceRegistryService.getResourceByDn(parentDn);
        }
    }

    public List getAllChildResourceList() {
        return resourceRegistryService.getResources(getResourceType());
    }

    public List getHardwareChildResourceList(HardwareResource resource) {
        return resourceRegistryService.getChildResources(resource, getResourceType());
    }

    protected List loadResourceList(Resource parentResource, Map parameters) throws PortletException {
        List resourceList = null;
        if (parentResource == null) {
            ResourceProfile ourProfile = ((ResourceComponentFrame)container).getResourceProfile();
            if (ourProfile == null) {
                log.warn("Container does not have resource profile");
                resourceList = getAllChildResourceList();
            } else {
                resourceList = new ArrayList();
                List fullResourceList = resourceRegistryService.getResources(HardwareResourceType.INSTANCE);
                List hardwareResourceList = ResourceProfileRegistryService.getSupportedResources(ourProfile, fullResourceList);
                for (Iterator hardwareResources = hardwareResourceList.iterator(); hardwareResources.hasNext();) {
                    HardwareResource hardwareResource = (HardwareResource) hardwareResources.next();
                    List childResourceList = getHardwareChildResourceList(hardwareResource);
                    resourceList.addAll(childResourceList);
                }
            }
        } else {
            HardwareResource hardwareResource = (HardwareResource)parentResource;
            resourceList = getHardwareChildResourceList(hardwareResource);
        }
        return resourceList;
    }

    public int getChildLevel() {
        return childLevel;
    }

    public void setChildLevel(int childLevel) {
        this.childLevel = childLevel;
    }
}
