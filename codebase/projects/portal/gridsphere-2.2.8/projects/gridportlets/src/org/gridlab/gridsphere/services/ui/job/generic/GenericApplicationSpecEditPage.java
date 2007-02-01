package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.portlet.PortletLog;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponentState;
import org.gridlab.gridsphere.services.ui.file.FileChooserDialog;
import org.gridlab.gridsphere.services.ui.file.FileSaveDialog;
import org.gridlab.gridsphere.services.ui.file.FileChooserDialog;
import org.gridlab.gridsphere.services.ui.file.FileSaveDialog;
import org.gridlab.gridsphere.services.ui.wizard.ActionPage;
import org.gridlab.gridsphere.services.ui.ActionComponentState;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.job.JobSpec;
import org.gridlab.gridsphere.services.file.FileLocation;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GenericApplicationSpecEditPage.java,v 1.1.1.1 2007-02-01 20:42:07 kherm Exp $
 */

public class GenericApplicationSpecEditPage
        extends JobSpecComp implements ActionPage {

    private transient static PortletLog log = SportletLog.getInstance(GenericApplicationSpecEditPage.class);

    public static final String MESSAGE_DIRECTORY_SELECT
            = "Use the file chooser dialog to browse remote file systems for the" +
              " <b>directory</b> in which to run this application.";
    public static final String MESSAGE_EXEC_FILE_SELECT
            = "Use the file chooser dialog to browse remote file systems for the <b>executable</b>." +
              "<br>The file you choose will be staged to the computer used to run this application.";
    public static final String MESSAGE_STDOUT_FILE_SELECT
            = "Use the file save dialog to browse remote file systems for a location in which" +
              " to redirect <b>stdout</b>.";
    public static final String MESSAGE_STDERR_FILE_SELECT
            = "Use the file save dialog to browse remote file systems for a location in which" +
              " to redirect <b>stderr</b>.";

    public GenericApplicationSpecEditPage(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);

        // Init application spec beans
        setIsReadOnly(false);
        initGeneralSpec();
        initApplicationSpec();

        // Set default action and page
        setDefaultAction("doApplicationSpecEdit");
        setDefaultJspPage("/jsp/job/generic/ApplicationSpecEditComp.jsp");
    }

    public void doApplicationSpecEdit(Map parameters) throws PortletException {
        log.error("doApplicationSpecEdit");

        // Set next state
        setNextState(defaultJspPage);

        // Get job spec parameter
        JobSpec jobSpec = getJobSpec(parameters);
        // Verify job spec parameter
        if (jobSpec == null) {
            messageBox.appendText("No job specification was provided!");
        }

        // Load application spec beans
        loadGeneralSpec(jobSpec);
        loadApplicationSpec(jobSpec);
    }

    public void doDisplayPage(Map parameters) throws PortletException {
        log.error("doDisplayPage");
        setNextState(defaultJspPage);
    }

    public boolean validatePage(Map parameters) throws PortletException {
        log.error("validatePage()");

        // Get job spec parameter
        JobSpec jobSpec = getJobSpec(parameters);

        // Unload job spec
        boolean isValid = unloadGeneralSpec(jobSpec);
        isValid = isValid && unloadApplicationSpec(jobSpec);
        return isValid;
    }

    public void doDirectoryBrowse(Map parameters) throws PortletException {
        FileChooserDialog dialog
                = (FileChooserDialog)getActionDialog(FileChooserDialog.class);
        dialog.setMessageValue(MESSAGE_DIRECTORY_SELECT);
        dialog.setIsDirectoryOnly(true);
        dialog.showOkCancelDialog(new ActionComponentState(getClass(), "doDirectorySelect"),
                                  new ActionComponentState(getClass(), RENDER_ACTION));
    }

    public void doDirectorySelect(Map parameters) throws PortletException {
        FileChooserDialog dialog
                = (FileChooserDialog)getActionDialog(FileChooserDialog.class);
        FileLocation fileLocation = dialog.getSelectedFileLocation();
        if (fileLocation != null) {
            directoryField.setValue(fileLocation.getUrl());
        }
        setNextState(defaultJspPage);
    }

    public void doExecutableFileBrowse(Map parameters) throws PortletException {
        FileChooserDialog dialog
                = (FileChooserDialog)getActionDialog(FileChooserDialog.class);
        dialog.setMessageValue(MESSAGE_EXEC_FILE_SELECT);
        dialog.setIsFileOnly(true);
        dialog.showOkCancelDialog(new ActionComponentState(getClass(), "doExecutableFileSelect"),
                                  new ActionComponentState(getClass(), RENDER_ACTION));
    }

    public void doExecutableFileSelect(Map parameters) throws PortletException {
        FileChooserDialog dialog
                = (FileChooserDialog)getActionDialog(FileChooserDialog.class);
        FileLocation fileLocation = dialog.getSelectedFileLocation();
        if (fileLocation != null) {
            executableField.setValue(fileLocation.getUrl());
        }
        setNextState(defaultJspPage);
    }


    public void doStdoutFileBrowse(Map parameters) throws PortletException {
        FileSaveDialog dialog
                = (FileSaveDialog)getActionDialog(FileSaveDialog.class);
        dialog.setMessageValue(MESSAGE_STDOUT_FILE_SELECT);
        dialog.showOkCancelDialog(new ActionComponentState(getClass(), "doStdoutFileSelect"),
                                  new ActionComponentState(getClass(), RENDER_ACTION));
    }

    public void doStdoutFileSelect(Map parameters) throws PortletException {
        FileSaveDialog dialog
                = (FileSaveDialog)getActionDialog(FileSaveDialog.class);
        FileLocation fileLocation = dialog.getSaveAsFile();
        if (fileLocation != null) {
            stdoutField.setValue(fileLocation.getUrl());
        }
        setNextState(defaultJspPage);
    }

    public void doStderrFileBrowse(Map parameters) throws PortletException {
        FileSaveDialog dialog
                = (FileSaveDialog)getActionDialog(FileSaveDialog.class);
        dialog.setMessageValue(MESSAGE_STDERR_FILE_SELECT);
        dialog.showOkCancelDialog(new ActionComponentState(getClass(), "doStderrFileSelect"),
                                  new ActionComponentState(getClass(), RENDER_ACTION));
    }

    public void doStderrFileSelect(Map parameters) throws PortletException {
        FileSaveDialog dialog
                = (FileSaveDialog)getActionDialog(FileSaveDialog.class);
        FileLocation fileLocation = dialog.getSaveAsFile();
        if (fileLocation != null) {
            stderrField.setValue(fileLocation.getUrl());
        }
        setNextState(defaultJspPage);
    }
}
