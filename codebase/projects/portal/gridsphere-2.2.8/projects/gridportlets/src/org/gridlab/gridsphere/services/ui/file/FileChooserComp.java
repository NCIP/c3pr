package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.portlet.PortletLog;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.file.FileLocation;

import java.net.MalformedURLException;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileChooserComp.java,v 1.1.1.1 2007-02-01 20:41:59 kherm Exp $
 * <p>
 * Specifies a transfer operation.
 */

public class FileChooserComp extends FileListComp {

    private transient static PortletLog log = SportletLog.getInstance(FileChooserComp.class);

    public FileChooserComp(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);
        getFileHostList().setSize(10);
        setDefaultJspPage("/jsp/file/FileChooserComp.jsp");
    }

    public FileLocation getSelectedFileLocation() {
        try {
            FileLocation fileLocation = unloadFilePathLocation(getFilePathList(), getCurrentPathField());
            FileLocation fileResourceLoc = getFileResourceLoc();
            if (fileResourceLoc == null) {
                return null;
            }
            String host = fileResourceLoc.getHost();
            fileLocation.setHost(host);
            log.debug("Selected file location is " + fileLocation.getUrl());
            return fileLocation;
        } catch (MalformedURLException e) {
            log.error("Unable to get file path", e);
            return null;
        }
    }
}
