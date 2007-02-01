package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionDialog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionDialog;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserService;
import org.gridlab.gridsphere.services.file.logical.LogicalFileBrowserService;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileDialog.java,v 1.1.1.1 2007-02-01 20:42:00 kherm Exp $
 */

public class FileDialog extends ActionDialog {

    private transient static PortletLog log = SportletLog.getInstance(FileDialog.class);

    // Portlet services
    protected FileBrowserService fileBrowserService = null;
    protected Boolean isLogical = Boolean.FALSE;

    public FileDialog(ActionComponentFrame container, String compName) {
        super(container, compName);
    }

    public void onInit()
            throws PortletException {
        super.onInit();
        if (isLogical.booleanValue()) {
            log.debug("Getting instance of logical file browser service");
            fileBrowserService = (LogicalFileBrowserService)
                    getPortletService(LogicalFileBrowserService.class);
        } else {
            log.debug("Getting instance of physical file browser service");
            fileBrowserService = (FileBrowserService)
                    getPortletService(FileBrowserService.class);
        }
    }

    public Boolean getIsLogicalFileMode()
            throws PortletException {
        return isLogical;
    }

    public void setIsLogicalFileMode(Boolean flag)
            throws PortletException {
        isLogical = flag;
        if (isLogical.booleanValue()) {
            log.debug("Getting instance of logical file browser service");
            fileBrowserService = (LogicalFileBrowserService)
                    getPortletService(LogicalFileBrowserService.class);
        } else {
            log.debug("Getting instance of physical file browser service");
            fileBrowserService = (FileBrowserService)
                    getPortletService(FileBrowserService.class);
        }
    }
}
