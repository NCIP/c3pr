/**
 * @author <a href="mailto:jean-claude.cote@nrc.ca">Jean-Claude Cote</a>
 * @version $Id: GramJobSpec.java,v 1.1.1.1 2007-02-01 20:41:07 kherm Exp $
 */
package org.gridlab.gridsphere.services.resources.gram;

import java.util.List;
import java.util.Iterator;
import java.util.Map;

import org.globus.gram.GramAttributes;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.job.*;
import org.gridlab.gridsphere.services.job.impl.BaseJobSpec;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.task.TaskAttribute;

public class GramJobSpec extends BaseJobSpec {

    private static PortletLog log = SportletLog.getInstance(GramJobSpec.class);

    public GramJobSpec() throws JobException {
        super();
    }

    public GramJobSpec(JobSpec spec) {
        super(spec);
    }

    public GramJobSpec(GramJobSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     * @return
     */
    public TaskType getTaskType() {
        return GramJobType.INSTANCE;
    }

    /**
     * See JobSpec.
     * @return
     */
    public JobType getJobType() {
        return GramJobType.INSTANCE;
    }

    /**
     * See GramJobSpec.
     */
    public String getResourceManagerContact() {
        // Get resource manager contact
        String resourceManagerContact = "";
        String hostName = getHostName();
        if (hostName == null || hostName.equals("")) {
            log.debug("No rmc provided for gram job");
        } else {
            String schedulerName = getJobSchedulerName();
            if (schedulerName == null || schedulerName.trim().equals("")) {
                resourceManagerContact = hostName;
            } else {
                resourceManagerContact = hostName
                                       + "/jobmanager-"
                                       + schedulerName;
            }
        }
        log.debug("Submitting job to rmc " + resourceManagerContact);
        return resourceManagerContact;
    }

    /**
     * See GramJobSpec.
     */
    public boolean isBatchJob() {
        String schedulerName = getJobSchedulerName();
        return (schedulerName != null &&
                (schedulerName.equals("lsf") ||
                 schedulerName.equals("pbs") ||
                 schedulerName.equals("condor")));
    }

    /**
     * Transform this spec into an RSL string for submitting
     * to GRAM.
     */
    public String marshalToString() {

        GramAttributes gramAttributes = new GramAttributes();
        exportApplicationSettings(gramAttributes);
        exportResources(gramAttributes);
        exportArguments(gramAttributes);
        exportFileStageParameters(gramAttributes);
        exportEnvironment(gramAttributes);
        //TODO: Get parameter not supported errors from this
        //exportTaskAttributes(gramAttributes);

        return gramAttributes.toRSL();
    }

    /**
     * Exports this specs values to the given GramAttributes.
     * Exported items are: directory, executable, stdin, stdout.
     * @param gramAttributes
     */
    private void exportApplicationSettings(GramAttributes gramAttributes) {

        /*** Application settings ****/

        // Project
        String project = getProject();
        if (project != null) {
            gramAttributes.setProject(project);
        }

        // Directory
        String directory = getDirectory();
        if (directory != null) {
            gramAttributes.setDirectory(directory);
        }

        // Executable
        FileLocation executable = getExecutableLocation();
        if (executable != null) {
            String execValue = stripOffFileProtocol(executable);
            gramAttributes.setExecutable(execValue);
        }

        // Execution method
        ExecutionMethod execMethod = getExecutionMethod();
        if (execMethod != null) {
            if (execMethod.equals(ExecutionMethod.SINGLE)) {
                gramAttributes.setJobType(GramAttributes.JOBTYPE_SINGLE);
            }
            else if (execMethod.equals(ExecutionMethod.MULTIPLE)) {
                gramAttributes.setJobType(GramAttributes.JOBTYPE_MULTIPLE);
            }
            else if (execMethod.equals(ExecutionMethod.MPI)) {
                gramAttributes.setJobType(GramAttributes.JOBTYPE_MPI);
            }
            else if (execMethod.equals(ExecutionMethod.CONDOR)) {
                gramAttributes.setJobType(GramAttributes.JOBTYPE_CONDOR);
            }
        }

        // Stdout
        FileLocation stdout = getStdoutLocation();
        if (stdout != null) {
            String stdoutValue = stripOffFileProtocol(stdout);
            gramAttributes.setStdout(stdoutValue);
        }

        // Stderr
        FileLocation stderr = getStderrLocation();
        if (stderr != null) {
            String stderrValue = stripOffFileProtocol(stderr);
            gramAttributes.setStderr(stderrValue);
        }
    }

    /**
     * Exports this specs values to the given GramAttributes.
     * @param gramAttributes
     */
    private void exportResources(GramAttributes gramAttributes) {
        // Job queue
        String jobQueue = getJobQueueName();
        if (jobQueue != null) {
            gramAttributes.setQueue(jobQueue);
        }
        // Cpu count
        Integer cpuCount = getCpuCount();
        if (cpuCount != null) {
            gramAttributes.setNumProcs(cpuCount.intValue());
        }
        // Minimum memory
        Integer minMemory = getMinMemory();
        if (minMemory != null) {
            gramAttributes.setMinMemory(minMemory.intValue());
        }
    }

    /**
     * Exports this specs values to the given GramAttributes.
     * @param gramAttributes
     */
    private void exportArguments(GramAttributes gramAttributes) {
        if (arguments != null) {
            for (int ii = 0; ii < arguments.size(); ++ii) {
                String argument = (String) arguments.get(ii);
                gramAttributes.addArgument(argument);
            }
        }
    }

    /**
     * Exports the arguments to the given GramAttributes.
     * @param gramAttributes
     */
    private void exportFileStageParameters(GramAttributes gramAttributes) {

        // Then add file stage locations
        List parameters = getFileStageParameters();
        int numParameters = parameters.size();
        if (numParameters > 0) {
            // We must make sure file stage paths are relative to the
            // job directory. Turns out if a relative directory is
            // specified then if a full path is not specified for the
            // file stage path it will fail!
            String basePath = null;
            // If directory has been specified...
            if (directory != null) {
                // If it is relative, must prepend ${HOME} to file stage path
                if (! (
                       directory.startsWith("/") || // Dir path not full path...
                       directory.startsWith("$") || // Dir path doesn't begin with $HOME/${HOME}/etc...
                       directory.startsWith("~")    // Dir path doesn't begin with ~...
                      )
                   ) {
                    basePath = "${HOME}/" + directory;
                    if (!basePath.endsWith("/")) {
                        basePath = basePath + '/';
                    }
                }
            }

            // Add our file stage parameters to the gram rsl
            for (int ii = 0; ii < parameters.size(); ++ii) {
                FileStageParameter parameter = (FileStageParameter)parameters.get(ii);

                FileLocation location = parameter.getFileLocation();
                String stageName = parameter.getFileStageName();
                FileStageType stageType = parameter.getFileStageType();
                String rslFileUrl = null;
                String rslStageType = null;

                // If this is an input file....
                if (stageType.equals(FileStageType.IN)) {

                    // Set rsl stage type
                    rslStageType = "file_stage_in";

                    // Get url from file location
                    rslFileUrl = stripOffFileProtocol(location);

                    // If we have a base path and stage path is relative
                    // we must prepend the base path...
                    if (basePath != null &&
                        ! (
                           stageName.startsWith("/") || // Stage path not full path...
                           stageName.startsWith("$") || // Stage path doesn't begin with $HOME/${HOME}/etc...
                           stageName.startsWith("~")    // Stage path doesn't begin with ~...
                           )
                        ) {
                        stageName = basePath + stageName;
                    }

                    // Add file stage-in parameter to rsl
                    gramAttributes.addMulti(rslStageType, new String[] { rslFileUrl, stageName });

                } else {
                    // Otherwise if this is an output file...

                    // Set rsl stage type
                    rslStageType = "file_stage_out";

                    // If we have a base path and the file path is relative
                    // we must prepend the base path to the file path...
                    if (basePath != null) {
                        // Check file path...
                        String path = location.getFilePath();
                        if (! (
                               path.startsWith("/") || // File path not full path...
                               path.startsWith("$") || // File path doesn't begin with $HOME/${HOME}/etc...
                               path.startsWith("~")    // File path doesn't begin with ~...
                              )
                            ) {
                            // Prepend base path to file path
                            location.setFilePath(basePath + path);
                        }
                    }

                    // Get url from file location
                    rslFileUrl = stripOffFileProtocol(location);

                    // Add file stage-out parameter to rsl
                    gramAttributes.addMulti(rslStageType, new String[] { stageName, rslFileUrl  });
                }
            }
        }
    }

    /**
     * Exports this spec values to the given GramAttributes.
     * @param gramAttributes
     */
    private void exportEnvironment(GramAttributes gramAttributes) {
        if (environmentVariables != null) {
            for (int ii = 0; ii < environmentVariables.size(); ++ii) {
                EnvironmentVariable envVar = (EnvironmentVariable) environmentVariables.get(ii);
                String envVarName = envVar.getName();
                String envVarValue = envVar.getValue();
                gramAttributes.addEnvVariable(envVarName, envVarValue);
            }
        }
    }

    private void exportTaskAttributes(GramAttributes gramAttributes) {
        log.debug("exportTaskAttributes");
        Map taskAttributeMap = getTaskAttributeMap();
        Iterator taskAttributes = taskAttributeMap.keySet().iterator();
        while (taskAttributes.hasNext()) {
            String key = (String)taskAttributes.next();
            TaskAttribute taskAttribute = (TaskAttribute)taskAttributeMap.get(key);
            gramAttributes.add(key, taskAttribute.getValue());
        }
    }

    /**
     * Strip off any file:// protocol string. If the FileLocation
     * is not of file protocol simply returns the url.
     * @param fileLoc
     * @return
     */
    private static String stripOffFileProtocol(FileLocation fileLoc) {
        String fileValue = null;
        if (fileLoc != null) {
            String fileProtocol = fileLoc.getProtocol();
            if (fileProtocol.equals("file")) {
                fileValue = fileLoc.getFilePath();
            } else {
                fileValue = fileLoc.getUrl();
            }
        }
        return fileValue;
    }

    public Object clone() {
        GramJobSpec spec = new GramJobSpec(this);
        return spec;
    }
}
