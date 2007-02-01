package org.gridlab.gridsphere.services.ui.job;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentState;
import org.gridlab.gridsphere.services.ui.job.generic.GenericJobProfile;
import org.gridlab.gridsphere.services.job.JobSubmissionService;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobComponentFrame.java,v 1.1.1.1 2007-02-01 20:42:04 kherm Exp $
 */

public class JobComponentFrame extends ActionComponentFrame {

    private static PortletLog log = SportletLog.getInstance(JobComponentFrame.class);

    protected JobProfile jobProfile = GenericJobProfile.INSTANCE;

    /**
     * Constructs default include bean
     */
    public JobComponentFrame() {
        super();
    }

    /**
     * Constructs default include bean
     */
    public JobComponentFrame(PortletRequest request, String beanId) {
        super(request, beanId);
    }

    /**
     * Constructs an include bean
     */
    public JobComponentFrame(ActionComponent comp, String beanId) {
        super(comp, beanId);
    }

    public ActionComponent getActionComponent(ActionComponentState state)
            throws PortletException {
        Class compClass = state.getComponent();
        log.debug("Getting job component " + compId + "%" + compClass.getName());

        // Get real component class
        Class realCompClass = compClass;
        if (JobViewPage.class.isAssignableFrom(compClass)) {
            realCompClass = getJobProfile(state.getParameters()).getJobViewPageClass();
        //} else if (JobSpecEditPage.class.isAssignableFrom(compClass)) {
        //    realCompClass = getJobProfile(state.getParameters()).getJobSpecEditPageClass();
        } else if (JobSpecViewPage.class.isAssignableFrom(compClass)) {
            realCompClass = getJobProfile(state.getParameters()).getJobSpecViewPageClass();
        } else if (SubmittedJobViewPage.class.isAssignableFrom(compClass)) {
            realCompClass = getJobProfile(state.getParameters()).getSubmittedJobViewPageClass();
        } else if (DeletedJobViewPage.class.isAssignableFrom(compClass)) {
            realCompClass = getJobProfile(state.getParameters()).getDeletedJobViewPageClass();
        } else if (CanceledJobViewPage.class.isAssignableFrom(compClass)) {
            realCompClass = getJobProfile(state.getParameters()).getCanceledJobViewPageClass();
        } else if (MigratedJobViewPage.class.isAssignableFrom(compClass)) {
            realCompClass = getJobProfile(state.getParameters()).getMigratedJobViewPageClass();
        } else if (JobListViewPage.class.isAssignableFrom(compClass)) {
            realCompClass = getJobProfile(state.getParameters()).getJobListViewPageClass();
        } else if (DeletedJobListViewPage.class.isAssignableFrom(compClass)) {
            realCompClass = getJobProfile(state.getParameters()).getDeletedJobListViewPageClass();
        }

        log.debug("Job component class " + compClass.getName() + " translated to " + realCompClass.getName());

        state.setComponent(realCompClass);
        return super.getActionComponent(state);
    }

    public JobProfile getJobProfile(Map parameters) throws PortletException {
        PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
        JobSubmissionService service  = null;
        try {
            service  = (JobSubmissionService)
                serviceFactory.createPortletService(JobSubmissionService.class, null, true);
        } catch (PortletServiceException e) {
            log.error("Unable to get job submission service", e);
            throw new PortletException(e);
        }
        JobProfile nextProfile = JobComponent.getJobProfile(service, parameters);
        if (nextProfile != null) {
            log.debug("New job component type " + nextProfile.getDescription());
            jobProfile = nextProfile;
        }
        log.debug("Using job component type " + jobProfile.getKey());
        return jobProfile;
    }
}
