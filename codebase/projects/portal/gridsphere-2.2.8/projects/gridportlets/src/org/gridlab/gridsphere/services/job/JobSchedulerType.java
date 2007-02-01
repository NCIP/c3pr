package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.job.impl.BaseJobScheduler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobSchedulerType.java,v 1.1.1.1 2007-02-01 20:40:41 kherm Exp $
 * <p>
 */

public class JobSchedulerType extends ResourceType {

    private static HashMap constants = new HashMap(5);

    public static final JobSchedulerType INSTANCE = new JobSchedulerType("default", true);
    public static final JobSchedulerType FORK = new JobSchedulerType("fork", true);
    public static final JobSchedulerType LSF = new JobSchedulerType("lsf", true);
    public static final JobSchedulerType PBS = new JobSchedulerType("pbs", true);
    public static final JobSchedulerType CONDOR = new JobSchedulerType("condor", true);

    private String name = "default";

    private JobSchedulerType(String name, boolean addToConstants) {
        super("JobScheduler", JobScheduler.class, BaseJobScheduler.class);
        this.name = name;
        if (addToConstants) {
            constants.put(name, this);
        }
    }

    protected JobSchedulerType(String id, Class resourceClass) {
        super(id, resourceClass);
    }

    public String getName() {
       return name;
    }

   public boolean equals(String name) {
        return (this.name.equalsIgnoreCase(name));
    }

    public boolean equals(JobSchedulerType type) {
        return (this.name.equalsIgnoreCase(type.name));
    }

    public boolean isaConstant() {
        return constants.containsKey(name);
    }

    public static Iterator iterateConstants() {
        return constants.values().iterator();
    }

    public static JobSchedulerType toJobSchedulerType(String name) {
        JobSchedulerType constant =
                (JobSchedulerType) constants.get(name);
        if (constant == null) {
            return new JobSchedulerType(name, false);
        }
        return constant;
    }

    public static JobScheduler getJobScheduler(HardwareResource hardwareResource, JobSchedulerType type) {
        List currentJobSchedulerList = hardwareResource.getChildResources(JobSchedulerType.INSTANCE);
        for (Iterator currentJobSchedulers = currentJobSchedulerList.iterator(); currentJobSchedulers.hasNext();) {
            JobScheduler nextJobScheduler =  (JobScheduler)currentJobSchedulers.next();
            if (nextJobScheduler.getJobSchedulerType().equals(type)) {
                return nextJobScheduler;
            }
        }
        return null;
    }
}
