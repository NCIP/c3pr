/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: WindowEventImpl.java,v 1.1.1.1 2007-02-01 20:49:48 kherm Exp $
 */
package org.gridlab.gridsphere.event.impl;

import org.gridlab.gridsphere.event.WindowEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;

import java.util.EventObject;

/**
 * A <code>WindowEventImpl</code> is the <code>WindowEvent</code> implementation
 * that is responsible for handling window action events e.g. <code>MINIMIZED</code>,
 * <code>MAXIMIZED</code> or <code>RESTORED</code>.
 */
public class WindowEventImpl extends EventObject implements WindowEvent {

    private int event = -1;
    private PortletRequest req;

    /**
     * Constructs an instance of <code>WindowEventImpl</code> with a provided
     * portlet request and response
     *
     * @param req         the <code>PortletRequests</code>
     * @param windowEvent the window event id
     */
    public WindowEventImpl(PortletRequest req, int windowEvent) {
        super(req);
        this.req = req;
        this.event = windowEvent;
    }

    /**
     * Returns the identifier of the current window event
     *
     * @return the window event identifier
     */
    public int getEventId() {
        return event;
    }

    /**
     * Returns the portlet request that has caused this event. If this event is not triggered by a request,
     * this methods returns null
     *
     * @return the <code>PortletRequest</code>
     */
    public PortletRequest getPortletRequest() {
        return req;
    }

}
