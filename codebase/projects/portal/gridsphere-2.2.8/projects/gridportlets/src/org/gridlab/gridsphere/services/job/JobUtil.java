package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.services.util.ServiceUtil;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobUtil.java,v 1.1.1.1 2007-02-01 20:40:43 kherm Exp $
 * <p>
 * This utility class simplifies the use of the job package by
 * providing convenient methods for methods commonly used by
 * other portlet services.
 */

public class JobUtil extends ServiceUtil {

    public static Job getJobByJobId(User user, String jobId)
            throws PortletServiceException {
        JobSubmissionService jobSubmissionService = getJobSubmissionService(user);
        return jobSubmissionService.getJobByJobId(jobId);
    }

    public static JobSpec createJobSpec(User user)
            throws PortletServiceException {
        JobSubmissionService jobSubmissionService = getJobSubmissionService(user);
        JobSpec jobSpec = jobSubmissionService.createJobSpec(JobType.INSTANCE);
        jobSpec.setUser(user);
        return jobSpec;
    }

    public static Job submitJob(JobSpec jobSpec)
            throws PortletServiceException {
        User user = jobSpec.getUser();
        JobSubmissionService jobSubmissionService = getJobSubmissionService(user);
        return jobSubmissionService.submitJob(jobSpec);
    }

    public static JobSubmissionService getJobSubmissionService(User user)
            throws PortletServiceException {
        return (JobSubmissionService)getPortletService(user, JobSubmissionService.class);
    }
}
