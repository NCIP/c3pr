package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.portlet.PortletLog;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.file.FileListComp;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileType;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;

import java.net.MalformedURLException;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileSaveComp.java,v 1.1.1.1 2007-02-01 20:42:01 kherm Exp $
 * <p>
 * Specifies a transfer operation.
 */

public class FileSaveComp extends FileListComp {

    private transient static PortletLog log = SportletLog.getInstance(FileSaveComp.class);

    private TextFieldBean fileSaveAsField = null;

    public FileSaveComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        getFileHostList().setSize(10);
        fileSaveAsField = createTextFieldBean("fileSaveAsField");
        setDefaultJspPage("/jsp/file/FileSaveComp.jsp");
    }

    public FileLocation getSaveAsFile() {
        try {
            // Start with current path
            FileLocation fileLocation = new FileLocation(getCurrentPathField().getValue(), FileType.DIRECTORY);
            // Set file name
            String fileName = fileSaveAsField.getValue();
            if (fileName == null || fileName.equals("")) {
                log.error("No file name provided");
                return null;
            }
            fileLocation.setFileName(fileName);
            // Set host name
            FileLocation fileResourceLoc = getFileResourceLoc();
            if (fileResourceLoc == null) {
            } else {
                String host = fileResourceLoc.getHost();
                fileLocation.setHost(host);
            }
            log.debug("Returning save file as " + fileLocation.getUrl());
            // Return handle
            return fileLocation;
        } catch (MalformedURLException e) {
            log.error("Unable to get file path", e);
            return null;
        }
    }
}
