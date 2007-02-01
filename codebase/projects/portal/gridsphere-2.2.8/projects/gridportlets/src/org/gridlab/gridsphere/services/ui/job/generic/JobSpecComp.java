package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.portlet.PortletLog;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.JobComponent;
import org.gridlab.gridsphere.services.ui.job.JobDeleteComp;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.job.*;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.provider.portletui.beans.*;

import java.util.*;
import java.net.MalformedURLException;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobSpecComp.java,v 1.1.1.1 2007-02-01 20:42:09 kherm Exp $
 */

public class JobSpecComp extends JobComponent {

    private transient static PortletLog log = SportletLog.getInstance(JobDeleteComp.class);

    public static final String AUTO_VALUE_WEB = "&lt;Automatic&gt;";
    public static final String DEFAULT_VALUE_WEB = "&lt;Default Value&gt;";
    public static final String DEFAULT_VALUE = "Default Value";

    public static final String BLANK_VALUE_WEB = "&lt;Blank&gt;";
    public static final String PORTAL_OUTPUT_WEB = "&lt;Portal&gt;";

    // Portlet services
    protected ResourceRegistryService resourceRegistryService = null;

    // General view beans
    protected TextBean jobTypeText = null;
    protected TextBean descriptionText = null;

    // General edit beans
    protected TextFieldBean descriptionField = null;

    // Application view beans
    protected TextBean directoryText = null;
    protected TextBean executableText = null;
    protected TextBean execMethodText = null;
    protected TextBean argumentsText = null;
    protected TextBean stdoutText = null;
    protected TextBean stderrText = null;
    protected TextBean stageInText = null;
    protected TextBean stageOutText = null;
    protected TextBean environmentText = null;
    protected TextBean checkpointText = null;

    // Application edit beans
    protected TextFieldBean directoryField = null;
    protected TextFieldBean executableField = null;
    protected ListBoxBean execMethodList = null;
    protected TextAreaBean argumentsArea = null;
    protected TextFieldBean stdoutField = null;
    protected TextFieldBean stderrField = null;
    protected TextAreaBean stageInArea = null;
    protected TextAreaBean stageOutArea = null;
    protected TextAreaBean environmentArea = null;
    protected TextAreaBean checkpointArea = null;

    // Resource view beans
    protected TextBean jobResourceText = null;
    protected TextBean jobSchedulerNameText = null;
    protected TextBean jobQueueNameText = null;
    protected TextBean cpuCountText = null;
    protected TextBean minMemoryText = null;

    // Resource edit beans
    protected ListBoxBean jobResourceList = null;
    protected ListBoxBean jobSchedulerNameList = null;
    protected RadioButtonBean jobQueueOption = null;
    protected TextFieldBean jobQueueNameField = null;
    protected TextFieldBean cpuCountField = null;
    protected TextFieldBean minMemoryField = null;

    protected List jobQueueList = new Vector();
    protected Boolean useResourceDiscovery = Boolean.FALSE;
    protected Boolean isReadOnly = Boolean.FALSE;

    public JobSpecComp(ActionComponentFrame container, String compName){
        super(container, compName);
    }

    public boolean getIsReadOnly() {
        return isReadOnly.booleanValue();
    }

    public void setIsReadOnly(boolean readOnly) {
        isReadOnly = Boolean.valueOf(readOnly);
    }

    public void onInit() throws PortletException {
        super.onInit();
        resourceRegistryService = (ResourceRegistryService)
                getPortletService(ResourceRegistryService.class);
    }

    public void onStore() throws PortletException {
        super.onStore();
        setPageAttribute("jobQueueList", jobQueueList);
        setPageAttribute("useResourceDiscovery", useResourceDiscovery);
        setPageAttribute("isReadOnly", isReadOnly);
    }

    public void doViewResource(Map parameters) throws PortletException {
        // Job resource
        String hostName = jobResourceList.getSelectedName();
        if (hostName == null) hostName = "";
        // Clear selected job queue options if user
        // selected a new resource from the list.
        if (!hostName.equals(jobResourceText.getValue())) {
            clearSelectedItems(jobSchedulerNameList);
            jobQueueOption.clearSelectedValues();
            jobQueueNameField.setValue("");
        }
        loadJobResourceText(hostName);
        loadJobQueueList(hostName);
        log.error("Testing selected job queue " + jobQueueOption.getSelectedValue());
    }

    protected void initJobSpec() throws PortletException {

        initGeneralSpec();
        initApplicationSpec();
        initResourceSpec();
    }

    protected void initGeneralSpec() throws PortletException {

        jobTypeText = createTextBean("jobTypeText");
        // Description is inited in both general spec and application spec
        descriptionField = createTextFieldBean("descriptionField");
        descriptionField.setReadOnly(isReadOnly.booleanValue());

    }

    protected void initApplicationSpec() throws PortletException {

        if (isReadOnly.booleanValue()) {
            // Description is inited in both application spec and general spec
            descriptionText = createTextBean("descriptionText");
            directoryText = createTextBean("directoryText");
            executableText = createTextBean("executableText");
            execMethodText = createTextBean("execMethodText");
            argumentsText = createTextBean("argumentsText");
            stdoutText = createTextBean("stdoutText");
            stderrText = createTextBean("stderrText");
            stageInText = createTextBean("stageInText");
            stageOutText = createTextBean("stageOutText");
            environmentText = createTextBean("environmentText");
            checkpointText = createTextBean("checkpointText");
        }
        // Description is inited in both application spec and general spec
        descriptionField = createTextFieldBean("descriptionField");
        descriptionField.setReadOnly(isReadOnly.booleanValue());
        directoryField = createTextFieldBean("directoryField");
        directoryField.setReadOnly(isReadOnly.booleanValue());
        executableField = createTextFieldBean("executableField");
        executableField.setReadOnly(isReadOnly.booleanValue());
        execMethodList = createListBoxBean("execMethodList");
        execMethodList.setReadOnly(isReadOnly.booleanValue());
        execMethodList.setDisabled(isReadOnly.booleanValue());
        argumentsArea = createTextAreaBean("argumentsArea");
        argumentsArea.setReadOnly(isReadOnly.booleanValue());
        stdoutField = createTextFieldBean("stdoutField");
        stdoutField.setReadOnly(isReadOnly.booleanValue());
        stderrField = createTextFieldBean("stderrField");
        stderrField.setReadOnly(isReadOnly.booleanValue());
        stageInArea = createTextAreaBean("stageInArea");
        stageInArea.setReadOnly(isReadOnly.booleanValue());
        stageOutArea = createTextAreaBean("stageOutArea");
        stageOutArea.setReadOnly(isReadOnly.booleanValue());
        environmentArea = createTextAreaBean("environmentArea");
        environmentArea.setReadOnly(isReadOnly.booleanValue());
        checkpointArea = createTextAreaBean("checkpointArea");
        checkpointArea.setReadOnly(isReadOnly.booleanValue());
    }

    protected void initResourceSpec() throws PortletException {

        jobResourceText = createTextBean("jobResourceText");

        if (isReadOnly.booleanValue()) {
            jobSchedulerNameText = createTextBean("jobSchedulerNameText");
            jobQueueNameText = this.createTextBean("jobQueueNameText");
            jobQueueNameText = createTextBean("jobQueueNameText");
            cpuCountText = createTextBean("cpuCountText");
            minMemoryText = createTextBean("minMemoryText");
        }

        jobResourceList = createListBoxBean("jobResourceList");
        jobResourceList.setReadOnly(isReadOnly.booleanValue());
        jobResourceList.setDisabled(isReadOnly.booleanValue());
        jobSchedulerNameList = createListBoxBean("jobSchedulerNameList");
        jobSchedulerNameList.setReadOnly(isReadOnly.booleanValue());
        jobSchedulerNameList.setDisabled(isReadOnly.booleanValue());
        jobQueueOption = this.createRadioButtonBean("jobQueueOption");
        jobQueueOption.setReadOnly(isReadOnly.booleanValue());
        jobQueueOption.setDisabled(isReadOnly.booleanValue());
        jobQueueNameField = createTextFieldBean("jobQueueNameField");
        jobQueueNameField.setReadOnly(isReadOnly.booleanValue());
        cpuCountField = createTextFieldBean("cpuCountField");
        cpuCountField.setReadOnly(isReadOnly.booleanValue());
        minMemoryField = createTextFieldBean("minMemoryField");
        minMemoryField.setReadOnly(isReadOnly.booleanValue());

    }

    protected void clearJobSpec() throws PortletException {

        clearGeneralSpec();
        clearApplicationSpec();
        clearResourceSpec();
    }

    protected void clearGeneralSpec() throws PortletException {

        jobTypeText.setValue("");

        if (isReadOnly.booleanValue()) {
            descriptionText.setValue("");
        }

        descriptionField.setValue("");
    }

    protected void clearApplicationSpec() throws PortletException {

        if (isReadOnly.booleanValue()) {
            directoryText.setValue("");
            executableText.setValue("");
            argumentsText.setValue("");
            stdoutText.setValue(PORTAL_OUTPUT_WEB);
            stderrText.setValue(PORTAL_OUTPUT_WEB);
            stageInText.setValue("");
            stageOutText.setValue("");
            environmentText.setValue("");
            checkpointText.setValue("");
        }

        directoryField.setValue("");
        executableField.setValue("");
        clearSelectedItems(execMethodList);
        argumentsArea.setValue("");
        stdoutField.setValue("");
        stderrField.setValue("");
        stageInArea.setValue("");
        stageOutArea.setValue("");
        environmentArea.setValue("");
        checkpointArea.setValue("");
    }

    protected void clearResourceSpec() throws PortletException {

        jobResourceText.setValue("");

        if (isReadOnly.booleanValue()) {
            jobResourceText.setValue("");
            jobSchedulerNameText.setValue(DEFAULT_VALUE_WEB);
            jobQueueNameText.setValue(DEFAULT_VALUE_WEB);
            cpuCountText.setValue("");
            minMemoryText.setValue("");
        }

        clearSelectedItems(jobResourceList);
        clearSelectedItems(jobSchedulerNameList);
        jobQueueOption.clearSelectedValues();
        jobQueueNameField.setValue("");
        cpuCountField.setValue("");
        minMemoryField.setValue("");

    }

    protected static void clearSelectedItems(ListBoxBean listBox) {
        List selectedItems = listBox.getSelectedItems();
        for (int ii = 0; ii < selectedItems.size(); ++ii) {
            ListBoxItemBean listBoxItem = (ListBoxItemBean)selectedItems.get(ii);
            listBoxItem.setSelected(false);
        }
    }

    protected void loadJobSpec(JobSpec jobSpec) throws PortletException {

        // Fist clear job spec fields
        clearJobSpec();

        // Now load fields from job spec
        try {
            loadGeneralSpec(jobSpec);
        } catch (PortletException e) {
            log.error("Error loading job general spec", e);
        }
        try {
            loadApplicationSpec(jobSpec);
        } catch (PortletException e) {
            log.error("Error loading job application spec", e);
        }
        try {
            loadResourceSpec(jobSpec);
        } catch (PortletException e) {
            log.error("Error loading job resource spec", e);
        }
    }

    protected void loadGeneralSpec(JobSpec jobSpec) throws PortletException {

        if (jobSpec != null) {
            // Job type
            JobType jobType = jobSpec.getJobType();
            jobTypeText.setValue(jobType.getLabel(locale));

            // Description
            String description = jobSpec.getDescription();
            if (description != null) descriptionField.setValue(description);

            if (isReadOnly.booleanValue()) {
                if (description != null) descriptionText.setValue(description);
            }
        }
    }

    protected void loadApplicationSpec(JobSpec jobSpec) throws PortletException {

        if (jobSpec != null) {

            // Directory
            String directory = jobSpec.getDirectory();
            if (directory != null) directoryField.setValue(directory);

            // Executable
            FileLocation executable = jobSpec.getExecutableLocation();
            if (executable != null) executableField.setValue(executable.getUrl());

            // Stdout
            FileLocation stdout = jobSpec.getStdoutLocation();
            if (stdout != null) {
                log.error("DEBUG stdout = " + stdout.getUrl());
                if (!stdout.getProtocol().equals("https")) {
                    stdoutField.setValue(stdout.getUrl());
                }
                //stdoutField.setValue(file.getUrl());
            }

            // Stderr
            //TextBean stderrLabel = event.getTextBean("stderrLabel");
            FileLocation stderr = jobSpec.getStderrLocation();
            if (stderr != null) {
                if (!stderr.getProtocol().equals("https")) {
                    stderrField.setValue(stderr.getUrl());
                }
                //stderrField.setValue(file.getUrl());
            }

            if (isReadOnly.booleanValue()) {
                if (directory != null) directoryText.setValue(directory);
                if (executable != null) executableText.setValue(executable.getUrl());
                if (stdout != null) stdoutText.setValue(stdout.getUrl());
                if (stderr != null) stderrText.setValue(stderr.getUrl());
            }

            /* Arguments */
            loadArgumentSpec(jobSpec);

            /* File Staging */
            loadFileStageSpec(jobSpec);

            /* Environment */
            loadEnvironmentSpec(jobSpec);

            /* Checkpointing */
            loadCheckpointSpec(jobSpec);
        }

        // Execution method
        loadExecMethodList(jobSpec);
    }

    protected void loadExecMethodList(JobSpec jobSpec) {
        // Execution Method
        //TextBean execMethodLabel = event.getTextBean("execMethodLabel");
        execMethodList.clear();

        ExecutionMethod execMethod = null;

        if (jobSpec != null) {
            execMethod = jobSpec.getExecutionMethod();

            if (isReadOnly.booleanValue()) {
                if (execMethod != null) execMethodText.setValue(execMethod.getName());
            }
        }

        //Iterator execMethods = ExecutionMethod.iterateConstants();

        // Execution methods
        List execMethodConstants = new Vector();
        execMethodConstants.add(ExecutionMethod.SINGLE);
        execMethodConstants.add(ExecutionMethod.MPI);
        execMethodConstants.add(ExecutionMethod.MULTIPLE);
        execMethodConstants.add(ExecutionMethod.CONDOR);
        // Build execution method list
        Iterator execMethods = execMethodConstants.iterator();
        while (execMethods.hasNext()) {
            ListBoxItemBean execMethodItem = new ListBoxItemBean();
            ExecutionMethod nextMethod = (ExecutionMethod) execMethods.next();
            execMethodItem.setName(nextMethod.getName());
            execMethodItem.setValue(nextMethod.getName());
            // Set selected if it equals job spec's method
            if (execMethod != null && nextMethod.equals(execMethod)) {
                execMethodItem.setSelected(true);
            }
            execMethodList.addBean(execMethodItem);
        }
    }

    protected void loadArgumentSpec(JobSpec jobSpec) {

        StringBuffer argumentsBuffer = new StringBuffer();
        Iterator arguments = jobSpec.getArguments().iterator();
        while (arguments.hasNext()) {
            String argument = (String) arguments.next();
            argumentsBuffer.append(argument);
            argumentsBuffer.append(' ');
        }
        argumentsArea.setValue(argumentsBuffer.toString());

        if (isReadOnly.booleanValue()) {
            argumentsText.setValue(argumentsBuffer.toString());
        }
    }

    protected void loadFileStageSpec(JobSpec jobSpec) {

        StringBuffer stageInBuffer = new StringBuffer();
        StringBuffer stageOutBuffer = new StringBuffer();

        Iterator fileStageLocations = jobSpec.getFileStageParameters().iterator();
        while (fileStageLocations.hasNext()) {
            FileStageParameter location = (FileStageParameter) fileStageLocations.next();
            FileStageType fileStageType = location.getFileStageType();
            if (fileStageType.equals(FileStageType.IN)) {
                stageInBuffer.append(location.getFileStageUrlWithoutQuery());
                stageInBuffer.append('\n');
            } else {
                stageOutBuffer.append(location.getFileStageUrlWithoutQuery());
                stageOutBuffer.append('\n');
            }
        }

        stageInArea.setValue(stageInBuffer.toString());
        stageOutArea.setValue(stageOutBuffer.toString());

        if (isReadOnly.booleanValue()) {
            stageInText.setValue(stageInBuffer.toString());
            stageOutText.setValue(stageOutBuffer.toString());
        }
    }

    protected void loadEnvironmentSpec(JobSpec jobSpec) {

        if (jobSpec.getEnvironmentVariables().size() > 0) {
            environmentArea.setValue(jobSpec.getEnvironmentAsString());

            if (isReadOnly.booleanValue()) {
                environmentText.setValue(jobSpec.getEnvironmentAsString());
            }
        }
    }

    protected void loadCheckpointSpec(JobSpec jobSpec) {

        StringBuffer checkpointBuffer = new StringBuffer();

        Iterator fileLocations = jobSpec.getCheckpointFileLocations().iterator();
        while (fileLocations.hasNext()) {
            FileLocation location = (FileLocation) fileLocations.next();
            checkpointBuffer.append(location.getUrl());
            checkpointBuffer.append(' ');
        }
        checkpointArea.setValue(checkpointBuffer.toString());

        if (isReadOnly.booleanValue()) {
            checkpointText.setValue(checkpointBuffer.toString());
        }
    }

    protected void loadResourceSpec(JobSpec jobSpec) throws PortletException {

        // Job reource
        loadJobResource(jobSpec);

        // Job scheduler name
        loadJobSchedulerList(jobSpec);

        // Job scheduler name
        loadJobQueue(jobSpec);

        // Now the rest...
        if (jobSpec != null) {

            // Cpu count
            //TextBean cpuCountLabel = event.getTextBean("cpuCountLabel");
            Integer cpuCount =  jobSpec.getCpuCount();
            if (cpuCount != null) {
                cpuCountField.setValue(cpuCount.toString());
            }

            // Minimum memory
            //TextBean minMemoryLabel = event.getTextBean("minMemoryLabel");
            Integer minMemory =  jobSpec.getMinMemory();
            if (minMemory != null) {
                minMemoryField.setValue(minMemory.toString());
            }

            if (isReadOnly.booleanValue()) {
                if (cpuCount != null) cpuCountText.setValue(cpuCount.toString());
                if (minMemory != null) minMemoryText.setValue(minMemory.toString());
            }
        }

    }

    protected void loadJobResource(JobSpec jobSpec) throws PortletException {

        // Job reource list
        loadJobResourceList(jobSpec);

        // Job resource text
        loadJobResourceText(jobResourceList.getSelectedName());
    }

    protected void loadJobResourceText(String hostName) {
        if (hostName == null) {
            jobResourceText.setValue("");
            useResourceDiscovery = Boolean.FALSE;
        } else {
            jobResourceText.setValue(hostName);
            if (hostName.equals(DEFAULT_VALUE)) {
                useResourceDiscovery = Boolean.TRUE;
            } else {
                useResourceDiscovery = Boolean.FALSE;
            }
        }
    }

    protected void loadJobResourceList(JobSpec jobSpec)
            throws PortletException {

        // Clear job resource list
        jobResourceList.clear();

        // Is a resource selected
        boolean isSelected = false;
        // Selected job host
        String hostName = null;
        // If job spec not nul....
        if (jobSpec != null) {

            hostName = jobSpec.getHostName();

            // If resource brokering supported
            if (jobSpec.getJobType().getSupportsResourceBrokering()) {
                ListBoxItemBean jobResourceItem = new ListBoxItemBean();
                jobResourceItem.setName(DEFAULT_VALUE);
                jobResourceItem.setValue(AUTO_VALUE_WEB);
                if (hostName == null || hostName.equals("")) {
                    jobResourceItem.setSelected(true);
                    isSelected = true;
                }
                jobResourceList.addBean(jobResourceItem);
            }
        }
        // Make sure hostname not null for comparisons below
        if (hostName == null) hostName = "";

        log.error("DEBUG loadJobResourceList with hostname " + hostName);


        // Get job resources for user
        List jobResources = getJobResourceList();
        int ii = 0;
        for (Iterator resources = jobResources.iterator(); resources.hasNext(); ++ii) {
            JobResource jobResource = (JobResource) resources.next();
            String nextHostName = jobResource.getHostName();
            // Create job resource item
            ListBoxItemBean jobResourceItem = new ListBoxItemBean();
            jobResourceItem.setName(nextHostName);
            jobResourceItem.setValue(nextHostName);
            if (!isSelected) {
                if (ii == 0 && hostName.equals("")) {
                    // Select first item in list by default
                    jobResourceItem.setSelected(true);
                    isSelected = true;
                } else if (nextHostName.equals(hostName)) {
                    // Select this item if hostnames are equal
                    jobResourceItem.setSelected(true);
                    isSelected = true;
                }
            }
            // Add to job resource list
            jobResourceList.addBean(jobResourceItem);
        }
    }

    /**
     * Subclasses should override this method if they want to produce a
     * different list of resources to display to users.
     * @return The list of job resources
     * @see JobResource
     */
    protected List getJobResourceList() {
        return jobSubmissionService.getJobResources(jobType, getUser());
    }

    protected void loadJobSchedulerList(JobSpec jobSpec) {

        // Selected job scheduler
        String jobSchedulerName = null;
        if (jobSpec != null) {
            jobSchedulerName = jobSpec.getJobSchedulerName();
            if (jobSchedulerName == null) {
                jobSchedulerName = "";
            } else {
                if (isReadOnly.booleanValue()) {
                    jobSchedulerNameText.setValue(jobSchedulerName);
                }
            }
        }

        log.error("DEBUG loadJobSchedulerList with scheduler " + jobSchedulerName);

        // Clear job scheduler list
        jobSchedulerNameList.clear();

        // Default value item
        ListBoxItemBean jobSchedulerNameItem = new ListBoxItemBean();
        jobSchedulerNameItem.setName(DEFAULT_VALUE);
        jobSchedulerNameItem.setValue(DEFAULT_VALUE_WEB);
        jobSchedulerNameList.addBean(jobSchedulerNameItem);

        // Job scheduler types
        List jobSchedulerConstants = new Vector();
        jobSchedulerConstants.add(JobSchedulerType.FORK);
        jobSchedulerConstants.add(JobSchedulerType.LSF);
        jobSchedulerConstants.add(JobSchedulerType.PBS);
        jobSchedulerConstants.add(JobSchedulerType.CONDOR);

        // Built scheduler type list
        Iterator jobSchedulerTypes = jobSchedulerConstants.iterator();
        while (jobSchedulerTypes.hasNext()) {
            jobSchedulerNameItem = new ListBoxItemBean();
            JobSchedulerType nextType = (JobSchedulerType) jobSchedulerTypes.next();
            jobSchedulerNameItem.setName(nextType.getName());
            jobSchedulerNameItem.setValue(nextType.getName());
            // Set selected if it equals job spec's method
            if (jobSchedulerName != null && nextType.equals(jobSchedulerName)) {
                jobSchedulerNameItem.setSelected(true);
            }
            jobSchedulerNameList.addBean(jobSchedulerNameItem);
        }
    }

    protected void loadJobQueue(JobSpec jobSpec) {

        // Build job queue list from selected resource
        loadJobQueueList(jobResourceList.getSelectedName());

        // Get job queue information
        String jobSchedulerName = null;
        String jobQueueName = null;
        if (jobSpec != null) {
            jobSchedulerName = jobSpec.getJobSchedulerName();
            jobQueueName = jobSpec.getJobQueueName();

            if (jobQueueName != null) {
                if (isReadOnly.booleanValue()) {
                    jobQueueNameText.setValue(jobQueueName);
                }
            }
        }

        // Set job queue selected option
        jobQueueOption.clearSelectedValues();
        if (jobSchedulerName != null && jobQueueName != null) {
            jobQueueOption.addSelectedValue(jobSchedulerName + ':' + jobQueueName);
        }

        // Set job queue name field
        jobQueueNameField.setValue(jobQueueName);
    }

    public void loadJobQueueList(String hostName) {
        jobQueueList.clear();
        if (hostName != null && !hostName.equals(DEFAULT_VALUE)) {
            // TODO: Move this functionality to job submission service soon.
            HardwareResource jobHardware
                    = resourceRegistryService.getHardwareResourceByHostName(hostName);
            if (jobHardware != null) {
                JobResource jobResource
                        = (JobResource)jobHardware.getChildResource(JobResourceType.INSTANCE);
                if (jobResource != null) {
                    List nextJobSchedulerList = jobResource.getJobSchedulers();
                    Iterator nextJobSchedulerIter = nextJobSchedulerList.iterator();
                    while (nextJobSchedulerIter.hasNext()) {
                        JobScheduler jobScheduler = (JobScheduler)nextJobSchedulerIter.next();
                        List nextJobQueueList = jobScheduler.getJobQueues();
                        jobQueueList.addAll(nextJobQueueList);
                    }
                }
            }
        }
    }

    protected boolean unloadJobSpec(JobSpec jobSpec)
            throws PortletException {

        boolean isValid = true;

        // Invalid if job spec not provided...
        if (jobSpec == null) {
            messageBox.appendText("No job spec was provided!");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            isValid = false;
        } else {
            // Unload appplication spec...
            isValid = isValid && unloadGeneralSpec(jobSpec);
            // Unload appplication spec...
            isValid = isValid && unloadApplicationSpec(jobSpec);
            // Unload appplication spec...
            isValid = isValid && unloadResourceSpec(jobSpec);
        }

        return isValid;
    }

    protected boolean unloadGeneralSpec(JobSpec jobSpec) throws PortletException {

        boolean isValid = true;

        // Description
        String description = descriptionField.getValue();
        if (description == null || description.trim().equals("")) {
            jobSpec.setDescription(null);
            messageBox.appendText("Please provide a description for this job");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            isValid = false;
        } else {
            jobSpec.setDescription(description);
        }

        return isValid;
    }

    protected boolean unloadApplicationSpec(JobSpec jobSpec) throws PortletException {

        boolean isValid = true;

        // Executable, stdout, stderr
        isValid = isValid && unloadExecutableSpec(jobSpec);

        // Arguments
        isValid = isValid && unloadArgumentSpec(jobSpec);

        // File staging
        isValid = isValid && unloadFileStageSpec(jobSpec);

        // File staging
        isValid = isValid && unloadCheckpointSpec(jobSpec);

        // Environment
        isValid = isValid && unloadEnvironmentSpec(jobSpec);

        return isValid;
    }

    protected boolean unloadExecutableSpec(JobSpec jobSpec)
            throws PortletException {
        log.error("DEBUG Entering unloadExecutableSpec");

        boolean isValid = true;

        // Directory
        String directory = directoryField.getValue();
        if (directory == null || directory.trim().equals("")) {
            jobSpec.setDirectory(null);
        } else {
            jobSpec.setDirectory(directory);
        }

        // Executable
        String executableUrl = executableField.getValue();
        if (executableUrl == null || executableUrl.trim().equals("")) {
            messageBox.appendText("Executable must be provided.<br>");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            isValid = false;
        } else {
            try {
                FileLocation executable = new FileLocation(executableUrl);
                jobSpec.setExecutableLocation(executable);
            } catch (MalformedURLException e) {
                messageBox.appendText("Executable location is not a valid url.<br>");
                messageBox.setMessageType(TextBean.MSG_ERROR);
                isValid = false;
            }
        }

        // Execution Method
        String execMethodValue = execMethodList.getSelectedName();
        log.error("DEBUG Selected execution method = " + execMethodValue);
        ExecutionMethod execMethod = ExecutionMethod.toExecutionMethod(execMethodValue);
        jobSpec.setExecutionMethod(execMethod);

        // Stdout
        String stdoutUrl = stdoutField.getValue();
        if (stdoutUrl == null || stdoutUrl.trim().equals("")) {
            jobSpec.setStdoutLocation(null);
        } else {
            try {
                FileLocation stdout = new FileLocation(stdoutUrl);
                jobSpec.setStdoutLocation(stdout);
            } catch (MalformedURLException e) {
                messageBox.appendText("Stdout location is not a valid url.<br>");
                messageBox.setMessageType(TextBean.MSG_ERROR);
                isValid = false;
            }
        }

        // Stderr
        String stderrUrl = stderrField.getValue();
        if (stderrUrl == null || stderrUrl.trim().equals("")) {
            jobSpec.setStderrLocation(null);
        } else {
            try {
                FileLocation stderr = new FileLocation(stderrUrl);
                jobSpec.setStderrLocation(stderr);
            } catch (MalformedURLException e) {
                messageBox.appendText("Stderr location is not a valid url.<br>");
                messageBox.setMessageType(TextBean.MSG_ERROR);
                isValid = false;
            }
        }

        // Display errors if any...
        //throwErrorMessage(errorBuffer, event);

        log.error("DEBUG Exiting unloadExecutableSpec");

        return isValid;
    }

    protected boolean unloadArgumentSpec(JobSpec jobSpec)
            throws PortletException {
        log.error("DEBUG Entering unloadArgumentSpec");

        boolean isValid = true;

        // Clear current arguments
        jobSpec.clearArguments();
        //TextBean argumentsLabel = event.getTextBean("argumentsLabel");
        String argumentsStr = argumentsArea.getValue();
        log.debug("DEBUG argumentsArea = " + argumentsStr);
        if (argumentsStr != null) {
            // Trim input
            argumentsStr.trim();
            // Parse input...
            StringTokenizer argumentTokens = new StringTokenizer(argumentsStr);
            while (argumentTokens.hasMoreTokens()) {
                String value = argumentTokens.nextToken();
                jobSpec.addArgument(value);
            }
        }

        log.debug("DEBUG Exiting unloadArgumentSpec");

        return isValid;
    }

    protected boolean unloadFileStageSpec(JobSpec jobSpec)
            throws PortletException {
        log.error("DEBUG Entering unloadFileStageSpec");

        boolean isValid = true;

        // Clear current parameters
        jobSpec.clearFileStageParameters();
        // (Re)set stage in parameters
        isValid = isValid && unloadFileStageIn(jobSpec);
        // (Re)set stage out parameters
        isValid = isValid && unloadFileStageOut(jobSpec);

        log.error("DEBUG Exiting unloadFileStageSpec");

        return isValid;
    }

    protected boolean unloadFileStageIn(JobSpec jobSpec)
            throws PortletException {
        log.error("DEBUG Entering unloadFileStageIn");

        boolean isValid = true;

        //TextBean stageInLabel = event.getTextBean("stageInLabel");
        String stageInStr = stageInArea.getValue();
        log.error("DEBUG stageInArea = " + stageInStr);
        if (stageInStr != null) {
            // Trim input
            stageInStr.trim();
            // Parse input...
            StringTokenizer argumentTokens = new StringTokenizer(stageInStr);
            while (argumentTokens.hasMoreTokens()) {
                String value = argumentTokens.nextToken();
                try {
                    FileLocation fileLocation = new FileLocation(value);
                    jobSpec.addFileStageParameter(fileLocation);
                } catch (MalformedURLException e) {
                    messageBox.appendText("Stage-in contains a file location with an invalid url.<br>");
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                    isValid = false;
                }
            }
        }
        log.error("DEBUG Exiting unloadFileStageIn");

        return isValid;
    }

    protected boolean unloadFileStageOut(JobSpec jobSpec)
            throws PortletException {
        log.error("DEBUG Entering unloadFileStageOut");

        boolean isValid = true;

        //TextBean stageOutLabel = event.getTextBean("stageOutLabel");
        String stageOutStr = stageOutArea.getValue();
        log.error("DEBUG stageOutArea = " + stageOutStr);
        if (stageOutStr != null) {
            // Trim input
            stageOutStr.trim();
            // Parse input...
            StringTokenizer argumentTokens = new StringTokenizer(stageOutStr);
            while (argumentTokens.hasMoreTokens()) {
                String value = argumentTokens.nextToken();
                try {
                    FileLocation fileLocation = new FileLocation(value);
                    FileStageParameter parameter = jobSpec.addFileStageParameter(fileLocation);
                    parameter.setFileStageType(FileStageType.OUT);
                } catch (MalformedURLException e) {
                    messageBox.appendText("Stage-out contains a file location with an invalid url.<br>");
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                    isValid = false;
                }
            }
        }

        // Display errors if any...
        //throwErrorMessage(errorBuffer, event);

        log.error("DEBUG Exiting unloadFileStageOut");

        return isValid;
    }

    protected boolean unloadEnvironmentSpec(JobSpec jobSpec)
            throws PortletException {
        log.error("DEBUG Entering unloadEnvironmentSpec");

        boolean isValid = true;

        // Clear current environment
        jobSpec.clearEnvironmentVariables();
        // Environment fields
        String environmentStr = environmentArea.getValue();
        log.error("DEBUG environmentArea = " + environmentStr);
        if (environmentStr != null) {
            // Trim input
            environmentStr.trim();
            // Parse input
            StringTokenizer environmentTokens = new StringTokenizer(environmentStr);
            while (environmentTokens.hasMoreTokens()) {
                String variableText = environmentTokens.nextToken();
                StringTokenizer variableTokens = new StringTokenizer(variableText, "=");
                if (variableTokens.countTokens() == 0) {
                    messageBox.appendText("Environment variable ["
                                            + variableText
                                            + "] not properly assigned!");
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                    isValid = false;
                } else {
                    String name = variableTokens.nextToken();
                    String value = variableTokens.nextToken();
                    jobSpec.putEnvironmentVariable(name, value);
                }
            }
        }
        // Display errors if any...
        //throwErrorMessage(errorBuffer, event);

        log.error("DEBUG Exiting unloadEnvironmentSpec");

        return isValid;
    }

    protected boolean unloadCheckpointSpec(JobSpec jobSpec)
            throws PortletException {
        log.error("DEBUG Entering unloadCheckpointSpec");

        boolean isValid = true;

        // Clear current parameters
        jobSpec.clearCheckpointFileLocations();

        //TextBean stageOutLabel = event.getTextBean("checkpointLabel");
        String checkpointStr = checkpointArea.getValue();
        if (checkpointStr != null) {
            // Trim input
            checkpointStr.trim();
            // Parse input...
            StringTokenizer argumentTokens = new StringTokenizer(checkpointStr);
            while (argumentTokens.hasMoreTokens()) {
                String value = argumentTokens.nextToken();
                try {
                    FileLocation fileLocation = new FileLocation(value);
                    jobSpec.addCheckpointFileLocation(fileLocation);
                } catch (MalformedURLException e) {
                    messageBox.appendText("Stage-out contains a file location with an invalid url.<br>");
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                    isValid = false;
                }
            }
        }

        // Display errors if any...
        //throwErrorMessage(errorBuffer, event);

        log.error("DEBUG Exiting unloadCheckpointSpec");

        return isValid;
    }

    protected boolean unloadResourceSpec(JobSpec jobSpec)
            throws PortletException {
        log.error("DEBUG Entering unloadResourceSpec");

        boolean isValid = true;

        if (jobSpec == null) {

            isValid = false;

        } else {

            // Job resource
            String hostName = jobResourceList.getSelectedName();
            log.error("DEBUG Selected job resource = " + hostName);
            // Clear job scheduler if default value selected
            if (hostName == null || hostName.equals(DEFAULT_VALUE)) {
                if (jobSpec.getJobType().getSupportsResourceBrokering()) {
                    jobSpec.setHostName(null);
                } else {
                    messageBox.appendText("Please specify a resource for this job");
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                    isValid = false;
                }
            } else {
                jobSpec.setHostName(hostName);
            }
           // Job scheduler name
            String jobSchedulerName = null;
            // Job queue name
            String jobQueueName = null;
            // Get job queue option
            String jobQueueOptionValue = jobQueueOption.getSelectedValue();
            log.error("DEBUG Selected job scheduler = " + jobSchedulerName);
            // If job queue option was not used...
            if (jobQueueOptionValue == null) {
                // Get job scheduler name from list box
                jobSchedulerName = jobSchedulerNameList.getSelectedName();
                // Get job queue name from text field
                jobQueueName = jobQueueNameField.getValue();
            } else {
                // Get job scheduler and queue name from selected option
                int index = jobQueueOptionValue.indexOf(":");
                jobSchedulerName = jobQueueOptionValue.substring(0, index);
                jobQueueName = jobQueueOptionValue.substring(index+1);
            }
            log.error("DEBUG Selected job scheduler = " + jobSchedulerName);
            // Clear job scheduler if default value selected
            if (jobSchedulerName == null || jobSchedulerName.equals(DEFAULT_VALUE)) {
                jobSpec.setJobSchedulerName(null);
            } else {
                jobSpec.setJobSchedulerName(jobSchedulerName);
            }
            log.error("DEBUG Selected job queue = " + jobQueueName);
            // Clear job queue if default value selected
            if (jobQueueName == null || jobQueueName.equals("")) {
                jobSpec.setJobQueueName(null);
            } else {
                jobSpec.setJobQueueName(jobQueueName);
            }

            // Cpu count
            String cpuCountStr = cpuCountField.getValue();
            if (cpuCountStr == null || cpuCountStr.trim().equals("")) {
                jobSpec.setCpuCount(null);
            } else {
                Integer value = null;
                try {
                    value = new Integer(cpuCountStr);
                    if (value.intValue() < 1) {
                        value = null;
                        messageBox.appendText("Cpu count must be a number greater than or equal to 1.<br>");
                        messageBox.setMessageType(TextBean.MSG_ERROR);
                        isValid = false;
                    }
                } catch (NumberFormatException e) {
                    messageBox.appendText("Cpu count must be a number greater than or equal to 1.<br>");
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                    isValid = false;
                }
                jobSpec.setCpuCount(value);
            }

            // Minimum memory
            String minMemoryStr = minMemoryField.getValue();
            if (minMemoryStr == null || minMemoryStr.trim().equals("")) {
                jobSpec.setMinMemory(null);
            } else {
                Integer value = null;
                try {
                    value = new Integer(minMemoryStr);
                    if (value.intValue() < 1) {
                        value = null;
                        messageBox.appendText("Minimum memory must be a number greater than or equal to 0.<br>");
                        messageBox.setMessageType(TextBean.MSG_ERROR);
                        isValid = false;
                    }
                } catch (NumberFormatException e) {
                    messageBox.appendText("Minimum memory must be a number greater than or equal to 0.<br>");
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                    isValid = false;
                }
                jobSpec.setMinMemory(value);
            }

            // Display errors if any...
            //throwErrorMessage(errorBuffer, event);

            log.error("DEBUG Exiting unloadResourceSpec");
        }

        return isValid;
    }
}
