/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobSpec.java,v 1.1.1.1 2007-02-01 20:40:43 kherm Exp $
 */
package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.task.TaskSpec;

import java.util.List;

/**
 * Specifies a "job" to run an executable on computing
 * resource(s) that match the given requirements.
 */
public interface JobSpec extends TaskSpec {

    /**
     * Returns this job type associated with this spec.
     * @return The job type
     */
    public JobType getJobType();

    /**
     * Returns the project in which to execute the job.
     * This is null by default.
     * @return The job project
     */
    public String getProject();

    /**
     * Sets the project in which to execute the job.
     * @param project
     */
    public void setProject(String project);

    /**
     * Returns the directory in which to execute the job.
     * This is null by default.
     * @return The job directory
     */
    public String getDirectory();

    /**
     * Sets the directory in which to execute the job.
     * This should be set only when a specific job
     * directory is known to be available on the
     * resource(s) described in the resource spec.
     * @param directory
     */
    public void setDirectory(String directory);

    /**
     * Returns the location of the executable.
     * @return The executable location
     */
    public FileLocation getExecutableLocation();

    /**
     * Sets the location of the executable. The
     * executable need not be on the resource if
     * the job submission service supports file
     * staging, which most do.
     * @param executable The executable location
     */
    public void setExecutableLocation(FileLocation executable);

    /**
     * Returns the method for execution.
     * @return The method of execution
     */
    public ExecutionMethod getExecutionMethod();

    /**
     * Sets the method for execution. Two util
     * methods are single and mpi excution.
     * @param method The method of execution
     */
    public void setExecutionMethod(ExecutionMethod method);

    /**
     * Returns the stdout for the job.
     * @return The job stdout location
     */
    public FileLocation getStdoutLocation();

    /**
     * Sets the location for stdout
     * @param stdout The job stdout location
     */
    public void setStdoutLocation(FileLocation stdout);

    /**
     * Returns the stderr for the job.
     * @return The job stderr location
     */
    public FileLocation getStderrLocation();

    /**
     * Sets the location for stderr
     * @param stderr The job stderr location
     */
    public void setStderrLocation(FileLocation stderr);

    /**
     * Returns the list arguments for this job.
     * @return List of (String) arguments
     */
    public List getArguments();

    /**
     * Sets the list arguments for this job.
     * @param arguments List of (String) arguments
     */
    public void setArguments(List arguments);

    /**
     * Returns a string representation of argument list
     * where each argument is separated by a blank space.
     * @return The argument list string
     */
    public String getArgumentsAsString();

    /**
     * Sets the arguments for this job.
     * @param arguments Arguments as written on a command line.
     */
    public void setArguments(String arguments);

    /**
     * Returns the argument at the given position.
     * @param position The argument position
     * @return The argument
     */
    public String getArgument(int position);

    /**
     * Adds the given list of arguments to this list.
     * @param arguments The (String) arguments to add
     */
    public void addArguments(List arguments);

    /**
     * Adds the given argument to the list.
     * @param argument The argument to add
     */
    public void addArgument(String argument);

    /**
     * Removes the given argument from the list.
     * Returns the argument that was removed.
     * @param position The position of the argument to be removed
     */
    public String removeArgument(int position);

    /**
     * Removes all the arguments from the list.
     */
    public void clearArguments();

    /**
     * Returns the list of file stage parameters required for this job.
     * @see FileStageParameter
     * @return The list of file stage parameters
     */
    public List getFileStageParameters();

    /**
     * Sets the list of file stage parameters required for this job.
     * @param parameters The list of file stage parameters
     */
    public void setFileStageParameters(List parameters);

    /**
     * Returns the file stage parameter at the given position.
     * @param position The position of the of the file stage parameter
     * @return The file stage parameter
     */
    public FileStageParameter getFileStageParameter(int position);

    /**
     * Adds a file stage in parameter using the given file stage in location.
     * @param fileStageLocation The file stage location
     * @return The resulting file stage in parameter
     */
    public FileStageParameter addFileStageParameter(FileLocation fileStageLocation);

    /**
     * Adds a file stage in parameter. Returns the file stage parameter that was created.
     * @param fileLocation The location of the file to be staged in.
     * @param stageName The name to stage the file to.
     * @return The resulting file stage in parameter
     */
    public FileStageParameter addFileStageInParameter(FileLocation fileLocation, String stageName);

    /**
     * Adds a file stage out parameter. Returns the file stage parameter that was created.
     * @param fileLocation The location of the file to be staged in.
     * @param stageName The name to stage the file to.
     * @return The resulting file stage in parameter
     */
    public FileStageParameter addFileStageOutParameter(FileLocation fileLocation, String stageName);

    /**
     * Removes the given file stage parameter at the given position.
     * @param position The position of the file stage parameter
     * @return The file stage parameter that was removed
     */
    public FileStageParameter removeFileStageParameter(int position);

    /**
     * Clears the list of stage parameter.
     */
    public void clearFileStageParameters();

    /**
     * Returns the list of checkpoint file locations required for this job.
     * @see FileLocation
     * @return The list of checkpoint file locations
     */
    public List getCheckpointFileLocations();

    /**
     * Sets the list of checkpoint file locations required for this job.
     * @see FileLocation
     * @param locations list of checkpoint file locations
     */
    public void setCheckpointFileLocations(List locations);

    /**
     * Returns the checkpoint file location at the given index
     * in the list of checkpoint file locations specifiecd for
     * this job.
     * @param index The index of the checkpoint file location
     * @return The checkpoint file location
     */
    public FileLocation getCheckpointFileLocation(int index);

    /**
     * Adds the given file location to the list of checkpoint
     * file locations specified for this job.
     * @param loc The checkpoint file location
     */
    public void addCheckpointFileLocation(FileLocation loc);

    /**
     * Removes the checkpoint file location at the given index
     * in the list of checkpoint file locations specified for
     * this job. Returns the file location that was removed.
     * @param index Th index of the checkpoint file location
     */
    public FileLocation removeCheckpointFileLocation(int index);

    /**
     * Clears the list of checkpoint file locations.
     */
    public void clearCheckpointFileLocations();

    /**
     * Returns a string representation of the checkpoint file location list
     * as a set of check file stage locations separated by blank spaces.
     * @return The environment variable list string
     */
    public String getCheckpointFileLocationsAsString();

    /**
     * Returns the list of environment variables required for this job.
     * @see EnvironmentVariable
     * @return The list of environment variables
     */
    public List getEnvironmentVariables();

    /**
     * Sets the list of environment variables required for this job.
     * @see EnvironmentVariable
     * @param variables The list of environment variables
     */
    public void setEnvironmentVariables(List variables);

    /**
     * Returns the environment variable with the given name.
     * Returns null if no variable exists with that name.
     * @return The environment variable with the given name
     */
    public EnvironmentVariable getEnvironmentVariable(String name);

    /**
     * Creates and sets an environment variable with the given values.
     */
    public EnvironmentVariable putEnvironmentVariable(String name, String value);

    /**
     * Removes the given setting from the environment.
     * Returns the environment variable that was removed.
     * @param name
     */
    public EnvironmentVariable removeEnvironmentVariable(String name);

    /**
     * Clears the environment of all its variables.
     */
    public void clearEnvironmentVariables();

    /**
     * Returns a string representation of envirnoment variable list
     * where each environment variable is specified as <NAME>=<VALUE>
     * and is separated by a blank space.
     * @return The environment variable list string
     */
    public String getEnvironmentAsString();

    /**
     * Returns the name of the host to which this job should be submitted.
     * Returns null if this has not been set before.
     * @return The job host name
     */
    public String getHostName();

    /**
     * Sets the name of the host to which this job should be submitted.
     * @param hostName The job host name
     */
    public void setHostName(String hostName);

    /**
     * Returns the name of the scheduler to which this job should be submitted.
     * Returns null if this has not been set before.
     * @return The job scheduler name
     */
    public String getJobSchedulerName();

    /**
     * Sets the name of the scheduler to which this job should be submitted.
     * @param schedulerName The job scheduler name
     */
    public void setJobSchedulerName(String schedulerName);

    /**
     * Returns the name of the queue to which this job should be submitted.
     * Returns null if this has not been set before.
     * @return The job queue name
     */
    public String getJobQueueName();

    /**
     * Sets the name of the queue to which this job should be submitted.
     * @param queueName The job queue name
     */
    public void setJobQueueName(String queueName);

    /**
     * Returns the number of cpus this job requires.
     * Returns null if this has not been set before.
     * @return The number of cpus
     */
    public Integer getCpuCount();

    /**
     * Sets the number of cpus this job requires.
     * @param cpuCount the number of cpus
     */
    public void setCpuCount(Integer cpuCount);

    /**
     * Returns the minimum memory required by this job.
     * Returns null if this has not been set before.
     * @return The minium memory
     */
    public Integer getMinMemory();

    /**
     * Sets the minimum memory required by this job
     * @param memory The minimum memory
     */
    public void setMinMemory(Integer memory);

    /**
     * Returns the maximum memory required by this job.
     * Returns null if this has not been set before.
     * @return The maximum memory
     */
    public Integer getMaxMemory();

    /**
     * Sets the maximum memory required by this job.
     * @param memory The maximum memory
     */
    public void setMaxMemory(Integer memory);

    /**
     * Returns the maxiumum wall time required by this job.
     * Returns null if this has not been set before.
     * @return The minium memory
     */
    public Integer getMaxWallTime();

    /**
     * Sets the maxiumum wall time required by this job
     * @param time The maximum wall time
     */
    public void setMaxWallTime(Integer time);

    /**
     * Returns the maxiumum cpu time required by this job
     * Returns null if this has not been set before.
     * @return The minium memory
     */
    public Integer getMaxCpuTime();

    /**
     * Sets the maxiumum cpu time required by this job
     * @param time The maximum cpu time
     */
    public void setMaxCpuTime(Integer time);

    /**
     * Marshals the job specification into a string represenation.
     * @return The job specification as a string
     */
    public String marshalToString();
}
