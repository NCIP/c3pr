/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardwareAccountViewPage.java,v 1.1.1.1 2007-02-01 20:42:14 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser.profiles;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.resource.browser.BaseResourceViewPage;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxItemBean;

import javax.portlet.PortletException;
import java.util.List;
import java.util.Iterator;

public class HardwareAccountViewPage extends BaseResourceViewPage {

    private transient static PortletLog log = SportletLog.getInstance(HardwareAccountViewPage.class);

    protected TextBean hostNameText = null;
    protected TextBean hostLabelText = null;
    protected TextBean userIdText = null;
    protected TextBean homeDirText = null;
    protected ListBoxBean userDnListBox = null;

    public HardwareAccountViewPage(ActionComponentFrame container, String compName) {
        super(container, compName);
        log.debug("HardwareAccountViewPage()");

        // Resource type
        setResourceType(HardwareAccountType.INSTANCE);

        // Hardware account resource attributes
        hostNameText = createTextBean("hostNameText");
        hostLabelText = createTextBean("hostLabelText");
        userIdText = createTextBean("hostDescriptionText");
        homeDirText = createTextBean("osNameText");
        userDnListBox = createListBoxBean("userDnListBox");
        userDnListBox.setReadOnly(true);
        userDnListBox.setDisabled(true);

        // Default page
        setDefaultJspPage("/jsp/resource/browser/profiles/HardwareAccountViewPage.jsp");
    }

    protected void loadResource(Resource resource) throws PortletException {

        super.loadResource(resource);

        HardwareAccount hardwareAccountResource = (HardwareAccount)resource;

        String hostName = hardwareAccountResource.getHostName();
        hostNameText.setValue(hostName);

        String hostLabel = hardwareAccountResource.getHostResource().getLabel();
        hostLabelText.setValue(hostLabel);

        String userId = hardwareAccountResource.getUserId();
        userIdText.setValue(userId);

        String homeDir = hardwareAccountResource.getHomeDir();
        homeDirText.setValue(homeDir);

        userDnListBox.clear();
        List userDnList = hardwareAccountResource.getUserDns();
        for (Iterator userDns = userDnList.iterator(); userDns.hasNext();) {
            String userDn = (String) userDns.next();
            ListBoxItemBean userDnItem = new ListBoxItemBean();
            userDnItem.setName(userDn);
            userDnItem.setValue(userDn);
            userDnListBox.addBean(userDnItem);
        }
    }
}
