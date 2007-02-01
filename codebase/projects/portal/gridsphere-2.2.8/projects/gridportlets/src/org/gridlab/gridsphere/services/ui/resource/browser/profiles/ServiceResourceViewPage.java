/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ServiceResourceViewPage.java,v 1.1.1.1 2007-02-01 20:42:15 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser.profiles;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.resource.browser.BaseResourceViewPage;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import javax.portlet.PortletException;

public class ServiceResourceViewPage extends BaseResourceViewPage {

    private transient static PortletLog log = SportletLog.getInstance(ServiceResourceViewPage.class);

    protected TextBean hostNameText = null;
    protected TextBean hostLabelText = null;
    protected TextBean protocolText = null;
    protected TextBean portText = null;
    protected TextBean servicePathText = null;

    public ServiceResourceViewPage(ActionComponentFrame container, String compName) {
        super(container, compName);
        log.debug("ServiceResourceViewPage()");
        
        // Resource type
        setResourceType(ServiceResourceType.INSTANCE);

        // Host resource attributes
        hostNameText = createTextBean("hostNameText");
        hostLabelText = createTextBean("hostLabelText");

        // Service resource attributes
        protocolText = createTextBean("osNameText");
        portText = createTextBean("osVersionText");
        servicePathText = createTextBean("osReleaseText");

        // Default page
        setDefaultJspPage("/jsp/resource/browser/profiles/ServiceResourceViewPage.jsp");
    }

    protected void loadResource(Resource resource) throws PortletException {

        super.loadResource(resource);

        ServiceResource serviceResource = (ServiceResource)resource;

        String hostName = serviceResource.getHostName();
        hostNameText.setValue(hostName);

        String hostLabel = serviceResource.getHardwareResource().getLabel();
        hostLabelText.setValue(hostLabel);

        String protocol = serviceResource.getProtocol();
        protocolText.setValue(protocol);

        String port = serviceResource.getPort();
        portText.setValue(port);

        String servicePath = serviceResource.getServicePath();
        servicePathText.setValue(servicePath);
    }
}
