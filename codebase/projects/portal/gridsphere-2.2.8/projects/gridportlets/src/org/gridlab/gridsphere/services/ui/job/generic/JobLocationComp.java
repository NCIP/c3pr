package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.JobComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.job.Job;
import org.gridlab.gridsphere.services.resources.map.MapResource;
import org.gridlab.gridsphere.services.resources.map.MapResourceType;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.ImageBean;

import java.util.Map;
import java.util.List;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobLocationComp.java,v 1.1.1.1 2007-02-01 20:42:08 kherm Exp $
 */

public class JobLocationComp extends JobComponent {

    private transient static PortletLog log = SportletLog.getInstance(JobLocationComp.class);

    protected ImageBean jobLocationImage = null;
    protected boolean isLoaded = false;

    public JobLocationComp(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);
        jobLocationImage = createImageBean("jobLocationImage");
        setDefaultAction("doJobLocationView");
        setDefaultJspPage("/jsp/job/generic/JobLocationComp.jsp");
    }

    public void doJobLocationView(Map parameters) throws PortletException {
        // Set next state
        setNextState(defaultJspPage);
        // Get job parameter
        Job job = getJob(parameters);
        if (job == null) {
            messageBox.appendText("No job parameter was provided!");
            messageBox.setMessageType(TextBean.MSG_ERROR);
        } else {
            loadJobLocation(job);
        }
    }

    public void onLoad() throws PortletException {
        isLoaded = false;
    }

    public void onStore() throws PortletException {
        super.onStore();

        if (!isLoaded) {
            // Get job parameter
            if (jobOid == null) {
                messageBox.appendText("No job parameter was provided!");
                messageBox.setMessageType(TextBean.MSG_ERROR);
            } else {
                Job job = jobSubmissionService.getJob(jobOid);
                loadJobLocation(job);
            }
        }
    }

    public void loadJobLocation(Job job) throws PortletException {

        isLoaded = true;
        ResourceRegistryService resourceRegistryService
                = (ResourceRegistryService) getPortletService(ResourceRegistryService.class);
        List mapResources = resourceRegistryService.getResources(MapResourceType.INSTANCE);
        log.debug("Found " + mapResources.size() + " map resources");
        if (mapResources.size() > 0)  {
            MapResource mapResource = (MapResource)mapResources.get(0);
            String host = (job == null) ? null : job.getHostName();
            String mapUrl = mapResource.getMapUrl(host);
            log.debug("Setting map url to " +  mapUrl);
            jobLocationImage.setSrc(mapUrl);
        }
    }
}
