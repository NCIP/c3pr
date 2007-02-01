/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardwareResourceViewPage.java,v 1.1.1.1 2007-02-01 20:42:14 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser.profiles;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.HardwareResourceType;
import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.resource.browser.BaseResourceViewPage;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.ImageBean;

import javax.portlet.PortletException;

public class HardwareResourceViewPage extends BaseResourceViewPage {

    public static final String DEFAULT_IMAGE1_SRC = "/gridportlets/media/HardwareResourceImage1.png";
    public static final String DEFAULT_IMAGE1_LABEL = "Hardware Resource";

    private transient static PortletLog log = SportletLog.getInstance(HardwareResourceViewPage.class);

    protected TextBean hostNameText = null;
    protected TextBean hostLabelText = null;
    protected TextBean hostDescriptionText = null;
    protected ImageBean hostImage1 = null;
    protected TextBean hostImage1Label = null;
    protected TextBean hostHtmlText = null;
    protected String hostHtmlUrl = null;

    public HardwareResourceViewPage(ActionComponentFrame container, String compName) {
        super(container, compName);
        log.debug("HardwareResourceViewPage()");
        
        // Hardware resource attributes
        hostNameText = createTextBean("hostNameText");
        hostLabelText = createTextBean("hostLabelText");
        hostDescriptionText = createTextBean("hostDescriptionText");
        hostImage1 = createImageBean("hostImage1");
        hostImage1Label = createTextBean("hostImage1Label");
        hostHtmlText = createTextBean("hostHtmlText");

        // Resource type
        setResourceType(HardwareResourceType.INSTANCE);
        // Default page
        setDefaultJspPage("/jsp/resource/browser/profiles/HardwareResourceViewPage.jsp");
    }

    public void initTagBeans() {
        super.initTagBeans();
        hostNameText.setValue("");
        hostLabelText.setValue("");
        hostDescriptionText.setValue("");
        hostImage1.setSrc(DEFAULT_IMAGE1_SRC);
        hostImage1Label.setValue(DEFAULT_IMAGE1_LABEL);
        hostHtmlText.setValue("");
    }

    public void onStore() throws PortletException {
        setPageAttribute("hostHtmlUrl", hostHtmlUrl);
    }

    protected void loadResource(Resource resource) throws PortletException {

        HardwareResource hardwareResource = (HardwareResource)resource;

        String hostName = hardwareResource.getHostName();
        hostNameText.setValue(hostName);

        String hostLabel = hardwareResource.getLabel();
        if (hostLabel == null) hostLabel = hostName;
        hostLabelText.setValue(hostLabel);

        String hostDescription = hardwareResource.getDescription();
        if (hostDescription == null) hostDescription = "";
        hostDescriptionText.setValue(hostDescription);

        String hostImage1Url = hardwareResource.getImage1Url();
        if (hostImage1Url == null) hostImage1Url = DEFAULT_IMAGE1_SRC;
        hostImage1.setSrc(hostImage1Url);

        String hostImage1LabelStr = hardwareResource.getImage1Label();
        if (hostImage1LabelStr == null) hostImage1LabelStr = DEFAULT_IMAGE1_LABEL;
        hostImage1Label.setValue(hostImage1LabelStr);

        String hostHtml = hardwareResource.getHtml();
        if (hostHtml == null) hostHtml = "";
        hostHtmlText.setValue(hostHtml);

        if (hostHtml.equals("")) {
            hostHtmlUrl = hardwareResource.getHtmlUrl();
        }
   }
}
