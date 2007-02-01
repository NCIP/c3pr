/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletFrameEventImpl.java,v 1.1.1.1 2007-02-01 20:49:56 kherm Exp $
 */
package org.gridlab.gridsphere.layout.event.impl;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.PortletFrame;
import org.gridlab.gridsphere.layout.event.ComponentAction;
import org.gridlab.gridsphere.layout.event.PortletFrameEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * A <code>PortletFrameEventImpl</code> is the concrete implementation of
 * <code>PortletFrameEvent</code>
 */
public class PortletFrameEventImpl implements PortletFrameEvent {

    private ComponentAction action;
    private int id;
    private String width = "";
    private PortletFrame frame = null;
    private PortletRequest request = null;

    /**
     * Constructs an instance of PortletFrameEventImpl from an action
     * and the component id of teh PortletFrame
     *
     * @param action a window action
     * @param id     the component id of the PortletFrame
     */
    public PortletFrameEventImpl(PortletFrame frame, PortletRequest request, ComponentAction action, int id) {
        this.frame = frame;
        this.action = action;
        this.id = id;
        this.request = request;
    }

    public boolean hasAction() {
        return (action != null);
    }

    public PortletRequest getRequest() {
        return request;
    }

    /**
     * Returns the type of PortletFrame action received
     *
     * @return the window action
     */
    public ComponentAction getAction() {
        return action;
    }

    public PortletComponent getPortletComponent() {
        return frame;
    }

    /**
     * Returns the component id of  the PortletFrame that triggered the event
     *
     * @return the component id of  the PortletFrame
     */
    public int getID() {
        return id;
    }

    /**
     * Used in the case of portlet frame resize when it needs to know the original width of the frame
     *
     * @param originalWidth the portlet frame original width
     */
    public void setOriginalWidth(String originalWidth) {
        this.width = originalWidth;
    }

    /**
     * Used in the case of portlet frame resize when it needs to know the original width of the frame
     *
     * @return the portlet frame original width
     */
    public String getOriginalWidth() {
        return width;
    }
}
