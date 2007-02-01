/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceRegistryComp.java,v 1.1.1.1 2007-02-01 20:42:10 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource;

import org.gridlab.gridsphere.services.ui.ActionComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.gridlab.gridsphere.services.resource.ResourceRegistry;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.services.file.io.FileReaderUtil;
import org.gridlab.gridsphere.services.file.io.FileWriterUtil;
import org.gridlab.gridsphere.services.core.utils.DateUtil;
import org.gridlab.gridsphere.portlet.PortletLog;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextAreaBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;
import java.io.IOException;
import java.io.File;
import java.text.DateFormat;

/**
 * Resource registry component allows users to edit Resources.xml.
 */
public class ResourceRegistryComp extends ActionComponent {

    public static final String SAVE_MESSAGE = "New resource descriptions have been successfully saved to the resource registry";

    public static final String DEFAULT_WEBAPP_TEST_PATH = "/WEB-INF/ResourcesTest.xml";
    public static final String DEFAULT_WEBAPP_SAVE_PATH = "/WEB-INF/ResourcesSave.xml";

    public static final String VIEW_PAGE = "/jsp/resource/registry/ResourceRegistryViewPage.jsp";
    public static final String EDIT_PAGE = "/jsp/resource/registry/ResourceRegistryEditPage.jsp";

    private static PortletLog log = SportletLog.getInstance(ResourceRegistryComp.class);

    protected ResourceRegistryService resourceRegistryService = null;
    protected TextAreaBean resourceRegistryArea = null;
    protected TextBean filePathText = null;
    protected TextBean mappingFilePathText = null;
    protected TextBean lastModifiedText = null;
    protected boolean isEditMode = false;

    public ResourceRegistryComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        resourceRegistryArea = createTextAreaBean("resourceRegistryArea");
        filePathText = createTextBean("filePathText");
        mappingFilePathText = createTextBean("mappingFilePathText");
        lastModifiedText = createTextBean("lastModifiedText");
        setDefaultAction("doView");
        setDefaultJspPage(VIEW_PAGE);
    }

    public void onInit()
            throws PortletException {
        super.onInit();
        resourceRegistryService = (ResourceRegistryService)
                getPortletService(ResourceRegistryService.class);
    }

    public void reRender(Map parameters) throws PortletException {
        if (!isEditMode) {
            doView(parameters);
        }
    }

    public void doView(Map parameters) throws PortletException {
        isEditMode = false;
        setNextState(VIEW_PAGE);
        ResourceRegistry resourceRegistry = resourceRegistryService.getResourceRegistry();
        loadResourceRegistryTextInfo(resourceRegistry);
        loadResourceRegistryTextArea(resourceRegistry);
        resourceRegistryArea.setReadOnly(true);
        resourceRegistryArea.setDisabled(true);
    }

    public void doEdit(Map parameters) throws PortletException {
        isEditMode = true;
        setNextState(EDIT_PAGE);
        ResourceRegistry resourceRegistry = resourceRegistryService.getResourceRegistry();
        loadResourceRegistryTextInfo(resourceRegistry);
        loadResourceRegistryTextArea(resourceRegistry);
        resourceRegistryArea.setReadOnly(false);
        resourceRegistryArea.setDisabled(false);
    }

    private void loadResourceRegistryTextInfo(ResourceRegistry resourceRegistry) {
        filePathText.setValue(ResourceRegistry.DEFAULT_WEBAPP_FILE_PATH);
        String dateValue = DateUtil.getLocalizedDate(getUser(),
                                                     portletRequest.getLocale(),
                                                     resourceRegistry.getLastModified(),
                                                     DateFormat.MEDIUM,
                                                     DateFormat.MEDIUM);
        lastModifiedText.setValue(dateValue);
        mappingFilePathText.setValue(ResourceRegistry.DEFAULT_WEBAPP_MAPPING_FILE_PATH);
    }

    private void loadResourceRegistryTextArea(ResourceRegistry resourceRegistry) {
        String filePath = resourceRegistry.getFilePath();
        log.debug("Reading resource descriptions from " + filePath);
        String fileContents = null;
        try {
            fileContents = FileReaderUtil.toString(filePath);
        } catch (IOException e) {
            log.error("Unable to read resource descriptions from " + filePath, e);
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return;
        }
        resourceRegistryArea.setValue(fileContents);
    }

    public void doSave(Map parameters) throws PortletException {
        setNextState(EDIT_PAGE);
        String filePath = getServletContext().getRealPath(ResourceRegistry.DEFAULT_WEBAPP_FILE_PATH);
        String mappingFilePath = getServletContext().getRealPath(ResourceRegistry.DEFAULT_WEBAPP_MAPPING_FILE_PATH);
        String testFilePath = getServletContext().getRealPath(DEFAULT_WEBAPP_TEST_PATH);
        String saveFilePath = getServletContext().getRealPath(DEFAULT_WEBAPP_SAVE_PATH);
        log.debug("Wrting resource entries to " + filePath);
        String fileContents = resourceRegistryArea.getValue();
        // First test resource descriptions
        try {
            File testFile = new File(testFilePath);
            testFile.delete();
            if (!testFile.createNewFile()) {
                throw new IOException("Unable to create new file " + testFilePath);
            }
            FileWriterUtil.toFile(fileContents, testFile);
        } catch (IOException e) {
            log.error("Unable to write resource descriptions to " + testFilePath, e);
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return;
        }
        try {
            ResourceRegistry.load(testFilePath, mappingFilePath);
        } catch (IOException e) {
            log.error("Unable to read resource descriptions from " + testFilePath, e);
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return;
        }
        // Then save old resource descriptions
        String saveFileContents = null;
        try {
            saveFileContents = FileReaderUtil.toString(filePath);
        } catch (IOException e) {
            log.error("Unable to read resource descriptions from " + filePath, e);
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return;
        }
        try {
            File saveFile = new File(saveFilePath);
            saveFile.delete();
            if (!saveFile.createNewFile()) {
                throw new IOException("Unable to create new file " + saveFilePath);
            }
            FileWriterUtil.toFile(saveFileContents, saveFile);
        } catch (IOException e) {
            log.error("Unable to write resource descriptions to " + saveFilePath, e);
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return;
        }
        // Finally save new resource descriptions
        try {
            File file = new File(filePath);
            file.delete();
            if (!file.createNewFile()) {
                throw new IOException("Unable to create new file " + filePath);
            }
            FileWriterUtil.toFile(fileContents, file);
        } catch (IOException e) {
            log.error("Unable to write resource descriptions to " + filePath, e);
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return;
        }
        ResourceRegistry resourceRegistry = null;
        try {
            resourceRegistry = ResourceRegistry.load(filePath, mappingFilePath);
            resourceRegistryService.setResourceRegistry(resourceRegistry);
        } catch (IOException e) {
            log.error("Unable to read resource descriptions from " + filePath, e);
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return;
        } catch (ResourceException e) {
            log.error("An error occured whiling saving resource descriptions from " + filePath, e);
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return;
        }
        setNextState(VIEW_PAGE);
        messageBox.appendText(SAVE_MESSAGE);
        messageBox.setMessageType(TextBean.MSG_SUCCESS);
        loadResourceRegistryTextInfo(resourceRegistry);
        resourceRegistryArea.setReadOnly(true);
        resourceRegistryArea.setDisabled(true);
        isEditMode = false;
    }
}
