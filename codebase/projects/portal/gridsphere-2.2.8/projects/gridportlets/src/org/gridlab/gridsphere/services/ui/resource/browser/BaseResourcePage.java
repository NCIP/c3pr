/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseResourcePage.java,v 1.1.1.1 2007-02-01 20:42:11 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.resource.ResourceEventListener;
import org.gridlab.gridsphere.services.resource.ResourceEvent;
import org.gridlab.gridsphere.services.core.utils.DateUtil;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import javax.portlet.PortletException;
import java.util.*;
import java.text.DateFormat;

public class BaseResourcePage extends ResourceComponent implements ResourceEventListener {

    public static final String LAST_UPDATED = "LAST_UPDATED";
    private transient static PortletLog log = SportletLog.getInstance(BaseResourcePage.class);
    private boolean refreshContent = true;
    private List resourceEventNameList = new ArrayList(1);
    private String lastUpdated = "";

    public BaseResourcePage(ActionComponentFrame container, String compName) {
        super(container, compName);
        // Listen for resource registry events if no provider service
        if (resourceRegistryService != null) {
            resourceRegistryService.addResourceEventListener(this);
        }
        addResourceEventName(ResourceEvent.NAME);
    }

    public void onStore() throws PortletException {
        setPageAttribute(LAST_UPDATED, lastUpdated);
    }

    protected boolean getRefreshContent() {
        return refreshContent;
    }

    protected void setRefreshContent(boolean flag) {
        refreshContent  = flag;
    }

    protected void addResourceEventName(String name) {
        resourceEventNameList.add(name);
    }

    public void resourceEventOccured(ResourceEvent event) {
        log.debug("Resource event occured " + getClass().getName());
        if (resourceEventNameList.contains(event.getName())) {
            refreshContent = true;
            Date dateUpated = new Date();
            lastUpdated = DateUtil.getLocalizedDate(Locale.getDefault(), TimeZone.getDefault(), dateUpated.getTime(), DateFormat.LONG, DateFormat.LONG);
            if (!lastUpdated.equals("")) lastUpdated = "(Last updated: " + lastUpdated + ")";
        }
    }
}
