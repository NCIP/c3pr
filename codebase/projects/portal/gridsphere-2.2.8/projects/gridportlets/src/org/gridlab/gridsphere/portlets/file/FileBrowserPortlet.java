package org.gridlab.gridsphere.portlets.file;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.services.ui.file.FileBrowserComp;
import org.gridlab.gridsphere.portlets.ActionComponentPortlet;
import org.gridlab.gridsphere.portlets.ActionComponentPortlet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;

/**
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Nov 24, 2003
 * Time: 5:11:48 PM
 * To change this template use Options | File Templates.
 */
public class FileBrowserPortlet extends ActionComponentPortlet {

    private transient static PortletLog log = SportletLog.getInstance(FileBrowserPortlet.class);

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        setViewModeComp(FileBrowserComp.class);
    }
}
