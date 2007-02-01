/**
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: HardwareResourceImpl.java,v 1.1.1.1 2007-02-01 20:41:01 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource.impl;

import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;

public class HardwareResourceImpl extends BaseHostResource implements HardwareResource {

    public static final String IMAGE1_URL = "HardwareResoruceImage1Url";
    public static final String IMAGE1_LABEL = "HardwareResoruceImage1Label";
    public static final String HTML = "HardwareResourceHtml";
    public static final String HTML_URL = "HardwareResourceHtmlUrl";

    private static PortletLog log = SportletLog.getInstance(BaseResource.class);

    public HardwareResourceImpl() {
        super();
        setResourceType(HardwareResourceType.INSTANCE);
    }

    public String getImage1Url() {
        return getResourceAttributeValue(IMAGE1_URL);
    }

    public void setImage1Url(String hostImage1Url) {
        log.debug("Setting image1 url " + hostImage1Url);
        putResourceAttribute(IMAGE1_URL, hostImage1Url);
    }

    public String getImage1Label() {
        return getResourceAttributeValue(IMAGE1_LABEL);
    }

    public void setImage1Label(String hostImage1Label) {
        log.debug("Setting image1 label " + hostImage1Label);
        putResourceAttribute(IMAGE1_LABEL, hostImage1Label);
    }

    public String getHtml() {
        return getResourceAttributeValue(HTML);
    }

    public void setHtml(String html) {
        log.debug("Setting html " + html);
        putResourceAttribute(HTML, html);
    }

    public String getHtmlUrl() {
        return getResourceAttributeValue(HTML_URL);
    }

    public void setHtmlUrl(String htmlUrl) {
        log.debug("Setting html file" + htmlUrl);
        putResourceAttribute(HTML_URL, htmlUrl);
    }

   public void copy(Resource resource) {
        super.copy(resource);
    }
}
