/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ActionFormEventImpl.java,v 1.1.1.1 2007-02-01 20:51:00 kherm Exp $
 */
package org.gridlab.gridsphere.provider.event.jsr.impl;

import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.event.impl.BaseFormEventImpl;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.util.HashMap;

/**
 * An <code>ActionEvent</code> is sent by the portlet container when an HTTP request is
 * received that is associated with an action.
 */
public class ActionFormEventImpl extends BaseFormEventImpl implements ActionFormEvent {

    private DefaultPortletAction action;
    private ActionRequest request;
    private ActionResponse response;

    /**
     * Constructs an instance of ActionEventImpl given an action, request and response
     *
     * @param action   a <code>DefaultPortletAction</code>
     * @param request  the <code>PortletRequest</code>
     * @param response the <code>PortletResponse</code>
     */
    public ActionFormEventImpl(DefaultPortletAction action, ActionRequest request, ActionResponse response) {
        super(request, response);
        this.action = action;
        this.request = request;
        this.response = response;
        // Unless tagBeans is null, don't recreate them
        if (tagBeans == null) {
            tagBeans = new HashMap();
            createTagBeans(request);
        }
        //logRequestParameters();
        //logTagBeans();
    }

    /**
     * Returns the action that this action event carries.
     *
     * @return the portlet action
     */
    public DefaultPortletAction getAction() {
        return action;
    }

    /**
     * Returns the action that this action event carries.
     *
     * @return the portlet action
     */
    public String getActionString() {
        return action.getName();
    }

    /**
     * Return the action request associated with this action event
     *
     * @return the <code>PortletRequest</code>
     */
    public ActionRequest getActionRequest() {
        return request;
    }

    /**
     * Return the action response associated with this action event
     *
     * @return the <code>PortletResponse</code>
     */
    public ActionResponse getActionResponse() {
        return response;
    }

}
