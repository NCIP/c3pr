/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceComponentFrame.java,v 1.1.1.1 2007-02-01 20:42:12 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentState;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;

public class ResourceComponentFrame extends ActionComponentFrame {

    private static PortletLog log = SportletLog.getInstance(ResourceComponentFrame.class);

    private ResourceProfile resourceProfile = ResourceProfile.INSTANCE;

    /**
     * Constructs default include bean
     */
    public ResourceComponentFrame() {
        super();
    }

    /**
     * Constructs default include bean
     */
    public ResourceComponentFrame(PortletRequest request, String beanId) {
        super(request, beanId);
    }

    /**
     * Constructs an include bean
     */
    public ResourceComponentFrame(ActionComponent comp, String beanId) {
        super(comp, beanId);
    }

    public ActionComponent getActionComponent(ActionComponentState state)
            throws PortletException {
        Class compClass = state.getComponent();
        log.debug("Getting action component " + compId + "%" + compClass.getName());

        // Get real component class
        Class realCompClass = compClass;
        if (ResourceListViewPage.class.equals(compClass)) {
            realCompClass = getResourceProfile().getResourceListViewClass();
            log.debug("Action component class " + compClass.getName() + " translated to " + realCompClass.getName());
        } else if (ResourceViewPage.class.equals(compClass)) {
            realCompClass = getResourceProfile().getResourceViewClass();
            log.debug("Action component class " + compClass.getName() + " translated to " + realCompClass.getName());
        } else if (ResourceEditPage.class.equals(compClass)) {
            realCompClass = getResourceProfile().getResourceEditClass();
            log.debug("Action component class " + compClass.getName() + " translated to " + realCompClass.getName());
        }
        state.setComponent(realCompClass);
        return super.getActionComponent(state);
    }

    public ResourceProfile getResourceProfile() {
        return resourceProfile;
    }

    public void setResourceProfile(ResourceProfile resourceProfile) {
        this.resourceProfile = resourceProfile;
    }
}
