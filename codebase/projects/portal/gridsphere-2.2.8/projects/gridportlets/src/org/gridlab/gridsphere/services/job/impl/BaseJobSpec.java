package org.gridlab.gridsphere.services.job.impl;

import java.util.*;

import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.job.*;
import org.gridlab.gridsphere.services.task.impl.BaseTaskSpec;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.security.gss.CredentialContext;
import org.gridlab.gridsphere.services.resources.gass.GassManagerService;
import org.gridlab.gridsphere.services.resources.system.LocalHostBrowserService;
import org.ietf.jgss.GSSCredential;
import org.globus.rsl.RslAttributes;
import org.globus.rsl.ParseException;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseJobSpec.java,v 1.1.1.1 2007-02-01 20:40:48 kherm Exp $
 * <p>
 * Base implementation of job specification.
 */

public class BaseJobSpec extends BaseTaskSpec implements JobSpec {

    protected static PortletLog log = SportletLog.getInstance(BaseJobSpec.class);

    protected GassManagerService gassManagerService = null;
    protected LocalHostBrowserService localhostBrowserService = null;

    protected String directory = null;
    protected FileLocation executable = null;
    protected ExecutionMethod executionMethod = null;
    protected String executionMethodName = null;
    protected FileLocation stdout = null;
    protected FileLocation stderr = null;
    protected List arguments = new Vector(2);
    protected List fileStageParameters = new Vector(2);
    protected List environmentVariables = new Vector(0);
    protected List checkpointFileLocations = new Vector(0);
    private String rslString = null;
    private RslAttributes rslAttributes = null;

    /**
     * Default constructor
     */
    public BaseJobSpec() {
        super();
    }

    /**
     * Copies values from the given job spec
     * @param jobSpec The job spec to copy
     */
    public BaseJobSpec(JobSpec jobSpec) {
        super(jobSpec);
        copyApplicationSpec(jobSpec);
        copyResourceSpec(jobSpec);
    }

    /**
     * Copies values from the given job spec
     * @param jobSpec
     */
    public BaseJobSpec(BaseJobSpec jobSpec) {
        super(jobSpec);
        copyApplicationSpec(jobSpec);
        copyResourceSpec(jobSpec);
    }

    public void copy(BaseJobSpec jobSpec) {
        super.copy(jobSpec);
        copyApplicationSpec(jobSpec);
        copyResourceSpec(jobSpec);
    }

    protected void copyApplicationSpec(JobSpec jobSpec) {
        setDirectory(jobSpec.getDirectory());
        setExecutableLocation(jobSpec.getExecutableLocation());
        setExecutionMethod(jobSpec.getExecutionMethod());
        setStdoutLocation(jobSpec.getStdoutLocation());
        setStderrLocation(jobSpec.getStderrLocation());
        copyArguments(jobSpec.getArguments());
        copyFileStageParameters(jobSpec.getFileStageParameters());
        copyCheckpointFileLocations(jobSpec.getCheckpointFileLocations());
        copyEnvironmentVariables(jobSpec.getEnvironmentVariables());
    }

    protected void copyResourceSpec(JobSpec jobSpec) {
        setHostName(jobSpec.getHostName());
        setJobSchedulerName(jobSpec.getJobSchedulerName());
        setJobQueueName(jobSpec.getJobQueueName());
        setCpuCount(jobSpec.getCpuCount());
        setMinMemory(jobSpec.getMinMemory());
        setMaxMemory(jobSpec.getMaxMemory());
        setMaxWallTime(jobSpec.getMaxWallTime());
        setMaxCpuTime(jobSpec.getMaxCpuTime());
    }

    public TaskType getTaskType() {
        return JobType.INSTANCE;
    }

    public JobType getJobType() {
        return JobType.INSTANCE;
    }

    public String getProject() {
        return (String)getRslAttributes().getSingle("project");
    }

    public void setProject(String project) {
        if (project == null) {
            getRslAttributes().remove("project");
        } else {
            getRslAttributes().set("project", project.toString());
        }
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public FileLocation getExecutableLocation() {
        return executable;
    }

    public void setExecutableLocation(FileLocation executable) {
        this.executable = executable;
    }

    public ExecutionMethod getExecutionMethod() {
        return executionMethod;
    }

    public void setExecutionMethod(ExecutionMethod method) {
        if (method == null) {
            executionMethod = null;
            executionMethodName = null;
        } else {
            executionMethod = method;
            executionMethodName = method.getName();
        }
    }

    public String getExecutionMethodName() {
        return executionMethodName;
    }

    public void setExecutionMethodName(String name) {
        if (name == null) {
            executionMethod = null;
            executionMethodName = null;
        } else {
            executionMethodName = name;
            executionMethod = ExecutionMethod.toExecutionMethod(name);
        }
    }

    public FileLocation getStdoutLocation() {
        return stdout;
    }

    public void setStdoutLocation(FileLocation stdout) {
        this.stdout = stdout;
    }

    public FileLocation getStderrLocation() {
        return stderr;
    }

    public void setStderrLocation(FileLocation stderr) {
        this.stderr = stderr;
    }

    public List getArguments() {
        return arguments;
    }

    public void setArguments(List arguments) {
        this.arguments = arguments;
    }

    public void copyArguments(List originalArguments) {
        arguments.clear();
        Iterator iterator = originalArguments.iterator();
        while (iterator.hasNext()) {
            String argument = (String)iterator.next();
            arguments.add(argument);
        }
    }

    public String getArgument(int position) {
        return (String) arguments.get(position);
    }

    public void addArgument(String argument) {
        arguments.add(argument);
    }

    public void addArguments(List arguments) {
        arguments.addAll(arguments);
    }

    public String removeArgument(int position) {
        if (position > arguments.size()) {
            return null;
        }
        String argument = (String)arguments.get(position);
        arguments.remove(position);
        return argument;
    }

    public void clearArguments() {
        arguments.clear();
    }

    public String getArgumentsAsString() {
        StringBuffer buffer = new StringBuffer();
        for (Iterator args = arguments.iterator(); args.hasNext();) {
            String argument = (String) args.next();
            buffer.append(argument);
            buffer.append(" ");
        }
        return buffer.toString();
    }

    public void setArguments(String argumentsStr) {
        if (argumentsStr != null) {
            // Trim input
            argumentsStr.trim();
            // Parse input...
            StringTokenizer argumentTokens = new StringTokenizer(argumentsStr);
            while (argumentTokens.hasMoreTokens()) {
                String value = argumentTokens.nextToken();
                addArgument(value);
            }
        }
    }

    public List getFileStageParameters() {
        return fileStageParameters;
    }

    public void setFileStageParameters(List fileStageParameters) {
        this.fileStageParameters = fileStageParameters;
    }

    public void copyFileStageParameters(List originalParameters) {
        fileStageParameters.clear();
        Iterator iterator = originalParameters.iterator();
        while (iterator.hasNext()) {
            FileStageParameter parameter = (FileStageParameter)iterator.next();
            FileStageParameter copyParameter = new FileStageParameter(parameter);
            fileStageParameters.add(copyParameter);
        }
    }

    public FileStageParameter getFileStageParameter(int position) {
        return (FileStageParameter) fileStageParameters.get(position);
    }

    public FileStageParameter addFileStageParameter(FileLocation fileStageLoc) {
        FileStageParameter param = new FileStageParameter(fileStageLoc);
        fileStageParameters.add(param);
        return param;
    }

    public FileStageParameter addFileStageInParameter(FileLocation fileLoc, String stageName) {
        FileStageParameter param = new FileStageParameter(fileLoc, stageName, FileStageType.IN);
        fileStageParameters.add(param);
        return param;
    }

    public FileStageParameter addFileStageOutParameter(FileLocation fileLoc, String stageName) {
        FileStageParameter param = new FileStageParameter(fileLoc, stageName, FileStageType.OUT);
        fileStageParameters.add(param);
        return param;
    }

    public FileStageParameter removeFileStageParameter(int position) {
        if (position > fileStageParameters.size()) {
            return null;
        }
        FileStageParameter param = (FileStageParameter)fileStageParameters.get(position);
        fileStageParameters.remove(position);
        return param;
    }

    public void clearFileStageParameters() {
        fileStageParameters.clear();
    }

    public List getCheckpointFileLocations() {
        return checkpointFileLocations;
    }

    public void setCheckpointFileLocations(List locations) {
        checkpointFileLocations = locations;
    }

    protected void copyCheckpointFileLocations(List originalLocations) {
        checkpointFileLocations.clear();
        Iterator locations = originalLocations.iterator();
        while (locations.hasNext()) {
            FileLocation location = (FileLocation)locations.next();
            String url = location.getUrl();
            try {
                FileLocation copyLocation = new FileLocation(url);
                checkpointFileLocations.add(copyLocation);
            } catch (Exception e) {
                log.error("Error while copying checkpiont file location", e);
            }
        }
    }

    public FileLocation getCheckpointFileLocation(int position) {
        return (FileLocation) checkpointFileLocations.get(position);
    }

    public void addCheckpointFileLocation(FileLocation location) {
        checkpointFileLocations.add(location);
    }

    public FileLocation removeCheckpointFileLocation(int position) {
        if (position > checkpointFileLocations.size()) {
            return null;
        }
        FileLocation location = (FileLocation)checkpointFileLocations.get(position);
        checkpointFileLocations.remove(position);
        return location;
    }

    public void clearCheckpointFileLocations() {
        checkpointFileLocations.clear();
    }

    public String getCheckpointFileLocationsAsString() {
        StringBuffer buffer = new StringBuffer();
        for (Iterator fileLocations = checkpointFileLocations.iterator(); fileLocations.hasNext();) {
            FileLocation location = (FileLocation) fileLocations.next();
            buffer.append(location.getUrl());
            buffer.append(" ");
        }
        return buffer.toString();
    }

    public List getEnvironmentVariables() {
        return environmentVariables;
    }

    public void setEnvironmentVariables(List variables) {
        environmentVariables = variables;
    }

    protected void copyEnvironmentVariables(List originalVariables) {
        environmentVariables.clear();
        Iterator specVariables = originalVariables.iterator();
        while (specVariables.hasNext()) {
            EnvironmentVariable specVariable = (EnvironmentVariable)specVariables.next();
            EnvironmentVariable variable = new EnvironmentVariable();
            variable.setName(specVariable.getName());
            variable.setValue(specVariable.getValue());
            environmentVariables.add(variable);
        }
    }

    public EnvironmentVariable getEnvironmentVariable(String name) {
        int position = findEnvironmentVariable(name);
        if (position > -1) {
            return (EnvironmentVariable) environmentVariables.get(position);
        }
        return null;
    }

    public EnvironmentVariable putEnvironmentVariable(String name, String value) {
        EnvironmentVariable variable = new EnvironmentVariable(name, value);
        int position = findEnvironmentVariable(name);
        if (position > -1) {
            environmentVariables.set(position, variable);
        } else {
            environmentVariables.add(variable);
        }
        return variable;
    }

    public EnvironmentVariable removeEnvironmentVariable(String name) {
        int position = findEnvironmentVariable(name);
        if (position > -1) {
            EnvironmentVariable var =  (EnvironmentVariable)environmentVariables.get(position);
            environmentVariables.remove(position);
            return var;
        }
        return null;
    }

    private int findEnvironmentVariable(String name) {
        int ii = 0;
        for (Iterator envvars = environmentVariables.iterator(); envvars.hasNext(); ++ii) {
            EnvironmentVariable var = (EnvironmentVariable) envvars.next();
            if (var.getName().equals(name)) {
                return ii;
            }
        }
        return -1;
    }

    public void clearEnvironmentVariables() {
        environmentVariables.clear();
    }

    public String getEnvironmentAsString() {
        StringBuffer buffer = new StringBuffer();
        for (Iterator envvars = environmentVariables.iterator(); envvars.hasNext();) {
            EnvironmentVariable var = (EnvironmentVariable) envvars.next();
            buffer.append(var.getNameEqualsValue());
            buffer.append(" ");
        }
        return buffer.toString();
    }

    /**
     * Used by persistence manager only!.
     * @return The rsl string
     */
    public String getRslString() {
        return rslString;
    }

    /**
     * Used by persistence manager only!
     * @param rslString The rsl string
     */
    public void setRslString(String rslString) {
        this.rslString = rslString;
    }

    public RslAttributes getRslAttributes() {
        if (rslAttributes == null) {
            if (rslString == null) {
                rslAttributes = new RslAttributes();
            } else {
                try {
                    setRslAttributes(new RslAttributes(rslString));
                } catch (ParseException e) {
                    log.error("Unable to parse rsl attributes", e);
                }
            }
        }
        return rslAttributes;
    }

    public void setRslAttributes(RslAttributes rslAttributes) {
        this.rslAttributes = rslAttributes;
    }

    public String getHostName() {
        return (String)getRslAttributes().getSingle("hostName");
    }

    public void setHostName(String hostName) {
        if (hostName == null) {
            getRslAttributes().remove("hostName");
        } else {
            getRslAttributes().set("hostName", hostName);
        }
    }

    public String getJobSchedulerName() {
        return (String)getRslAttributes().getSingle("jobSchedulerName");
    }

    public void setJobSchedulerName(String jobSchedulerName) {
        if (jobSchedulerName == null) {
            getRslAttributes().remove("jobSchedulerName");
        } else {
            getRslAttributes().set("jobSchedulerName", jobSchedulerName);
        }
    }

    public String getJobQueueName() {
        return (String)getRslAttributes().getSingle("jobQueueName");
    }

    public void setJobQueueName(String jobQueueName) {
        if (jobQueueName == null) {
            getRslAttributes().remove("jobQueueName");
        } else {
            getRslAttributes().set("jobQueueName", jobQueueName);
        }
    }

    public Integer getCpuCount() {
        String strValue = (String)getRslAttributes().getSingle("cpuCount");
        if (strValue == null) return null;
        log.debug("getCpuCount " + strValue);
        return new Integer(strValue);
    }

    public void setCpuCount(Integer value) {
        if (value == null) {
            getRslAttributes().remove("cpuCount");
        } else {
            getRslAttributes().set("cpuCount", value.toString());
        }
    }

    public Integer getMinMemory() {
        String strValue = (String)getRslAttributes().getSingle("minMemory");
        if (strValue == null) return null;
        log.debug("getMinMemory " + strValue);
        return new Integer(strValue);
    }

    public void setMinMemory(Integer value) {
        if (value == null) {
            getRslAttributes().remove("minMemory");
        } else {
            getRslAttributes().set("minMemory", value.toString());
        }
    }

    public Integer getMaxMemory() {
        String strValue = (String)getRslAttributes().getSingle("maxMemory");
        if (strValue == null) return null;
        return new Integer(strValue);
    }

    public void setMaxMemory(Integer value) {
        if (value == null) {
            getRslAttributes().remove("maxMemory");
        } else {
            getRslAttributes().set("maxMemory", value.toString());
        }
    }

    public Integer getMaxWallTime() {
        String strValue = (String)getRslAttributes().getSingle("maxMemory");
        if (strValue == null) return null;
        return new Integer(strValue);
    }

    public void setMaxWallTime(Integer value) {
        if (value == null) {
            getRslAttributes().remove("maxWallTime");
        } else {
            getRslAttributes().set("maxWallTime", value.toString());
        }
    }

    public Integer getMaxCpuTime() {
        String strValue = (String)getRslAttributes().getSingle("maxMemory");
        if (strValue == null) return null;
        return new Integer(strValue);
    }

    public void setMaxCpuTime(Integer value) {
        if (value == null) {
            getRslAttributes().remove("maxCpuTime");
        } else {
            getRslAttributes().set("maxCpuTime", value.toString());
        }
    }

    /**
     * Pre-processes the spec before job submission.
     * Applies the user's default credential context.
     * @throws JobException If an error occurs during pre-processing
     */
    public void preProcess() throws JobException {
        CredentialContext context = getCredentialContext();
        if (context== null) {
            context = getDefaultCredentialContext();
            if (context== null) {
                throw new JobException("No active credential available for pre processing");
            }
        }
        preProcess(context);
    }

    /**
     * Pre-processes the spec before job submission with the given credential context.
     * @param context The credential context in which to submit this job
     * @throws JobException If an error occurs during pre-processing
     */
    public void preProcess(CredentialContext context) throws JobException {
        log.debug("Entering preProcess");
        setCredentialContext(context);
        initPortletServices();
        preProcessExecutable();
        preProcessOutput();
        preProcessFileStageParameters();
        setRslString(getRslAttributes().toRSL());
        log.debug("RSL string " + rslString);
        log.debug("RSL tostring " + getRslAttributes().toString());
        log.debug("Exiting postProcess");
    }
  
    protected void initPortletServices() throws JobException {
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        try {
            localhostBrowserService = (LocalHostBrowserService)
                    factory.createPortletService(LocalHostBrowserService.class, null, true);
            gassManagerService = (GassManagerService)
                factory.createPortletService(GassManagerService.class, null, true);
        } catch (PortletException e) {
            log.error("Unable to get instance of gass server manager", e);
            throw new JobException(e.getMessage());
        }
    }

    /**
     * Pre-processes the executable before job submission.
     */
    protected void preProcessExecutable() throws JobException {
        // If gass is required...
        FileLocation executable = getExecutableLocation();
        if (executable != null && isGassRequired(executable)) {
            log.debug("Gass is required for executable");
            // Make available as a gass location
            try {
                GSSCredential credential = credentialContext.getCredential();
                String path = executable.getFilePath();
                executable = gassManagerService.createGassFileLocation(credential, path);
                setExecutableLocation(executable);
            } catch (Exception e) {
                throw new JobException(e.getMessage());             
            }
        }
    }
    
    /**
     * Pre-process job output
     */
    public void preProcessOutput() throws JobException {
        log.debug("Entering preProcessOutput");
        /* Is this an interactive job */
        //boolean isInteractiveJob = (!isBatchJob());
        /* Stdout */
        FileLocation stdout = getStdoutLocation();
        GSSCredential credential = credentialContext.getCredential();
        // Create job output path in case stdout or stdout not set...
        String joboutpath = null;
        if (stdout == null || stderr == null) {
            String time = String.valueOf((new Date()).getTime());
            // Using job description, get rid of blank spaces...
            String jobdesc = null;
            if (description == null || description.equals("")) {
                jobdesc = "job-output";
            } else {
                jobdesc = description.replaceAll(" ", "_");
            }
            try {
                joboutpath = localhostBrowserService.getRealPath(getUser(), "/" + jobdesc + "-" + time);
            } catch (Exception e) {
                throw new JobException(e.getMessage());
            }
        }
        // If no stdout specified...
        if (stdout == null) {
            // If interactive and no stdout specified then we want job output
            //if (isInteractiveJob) {
                log.debug("Creating gass job output for stdout");
                try {
                   stdout = gassManagerService.createGassFileLocation(credential, joboutpath);
                    //stdout = gassManagerService.createGassTempLocation(credential, time, "stdout");
                    setStdoutLocation(stdout);
                } catch (Exception e) {
                    throw new JobException(e.getMessage());             
                }
            //}
        // Otherwise check if should make available as a gass location
        } else if (isGassRequired(stdout)) {
            log.debug("Gass is required for stdout");
            try {
                stdout = gassManagerService.createGassFileLocation(credential, stdout.getFilePath());
                setStdoutLocation(stdout);
            } catch (Exception e) {
                throw new JobException(e.getMessage());             
            }
        }

        /* Stderr */
        FileLocation stderr = getStderrLocation();
        // If no stderr specified...       
        if (stderr == null) {
            // If interactive and no stderr specified then we want job output
            //if (isInteractiveJob) {
                log.debug("Creating gass job output for stderr");
                try {
                    stderr = gassManagerService.createGassFileLocation(credential, joboutpath);
                    //stderr = gassManagerService.createGassTempLocation(credential, time, "stderr");
                    setStderrLocation(stderr);
                } catch (Exception e) {
                    throw new JobException(e.getMessage());             
                }
            //}
        // Otherwise check if should make available as a gass location
        } else if (isGassRequired(stderr)) {
            log.debug("Gass is required for stderr");
            // Make available as a gass location
            try {
                stderr = gassManagerService.createGassFileLocation(credential, stderr.getFilePath());
                setStderrLocation(stderr);
            } catch (Exception e) {
                throw new JobException(e.getMessage());             
            }
        }
        log.debug("Exiting preProcessOutput");
    }

    /**
     * Pre-processes the arguments before job submission
     */
    protected void preProcessFileStageParameters() throws JobException {
        Iterator parameters = fileStageParameters.iterator();
        while (parameters.hasNext()) {
            FileStageParameter parameter = (FileStageParameter)parameters.next();
            log.debug("Pre-processing file stage parameter " + parameter.getFileStageUrl());
            preProcessFileStageParameter(parameter);
        }
    }

    /**
     * Pre-processes the given file stage parameter for the given job spec before job submission.
     * @param parameter The file stage parameter
     */
    protected void preProcessFileStageParameter(FileStageParameter parameter) throws JobException {
        FileLocation location = parameter.getFileLocation();
        // If gass is required...
        if (location != null && isGassRequired(location)) {
            log.debug("Gass required for file stage parameter");
            // Make available as gass location
            FileLocation gassLocation = null;
            try {
                GSSCredential credential = credentialContext.getCredential();
                String filePath = location.getFilePath();
                gassLocation = gassManagerService.createGassFileLocation(credential, filePath);
                parameter.setFileLocation(gassLocation);
            } catch (Exception e) {
                throw new JobException(e.getMessage());
            }
        }
    }

    /**
     * Returns true if given file location has to be made available with gass.
     * @param file The file location in question
     * @return True if gass required, false otherwise.
     */
    protected boolean isGassRequired(FileLocation file) {
        // If gass is enabled for user and file is on local host
        return (file.getProtocol().equalsIgnoreCase("file") && file.isLocalHost());
    }

    /**
     * Subclassed implementations should override this as necessary!
     * @return The string representation of this job spec
     */
    public String marshalToString() {
        return "";
    }

    /**
     * Clones this job spec.
     * @return The cloned job spec
     */
    public Object clone() {
        BaseJobSpec spec = new BaseJobSpec(this);
        return spec;
    }
}
