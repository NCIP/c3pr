/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: MessageEvent.java,v 1.1.1.1 2007-02-01 20:49:47 kherm Exp $
 */
package org.gridlab.gridsphere.event;

import org.gridlab.gridsphere.portlet.PortletMessage;
import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * A <code>MessageEvent</code> is sent by the portlet container if one portlets send a message to another.
 */
public interface MessageEvent extends Event {

    /**
     * Event identifier indicating that a message has been received.
     * <p/>
     * An event with this id is fired when one portlet (the source) sends a
     * message to another portlet (the target). The target portlet is specified by name.
     */
    public static final int MESSAGE_RECEIVED = 0;

    /**
     * Returns the message that this event carries.
     * The message format is entirely open and has to be defined and documented by the source portlet.
     *
     * @return the portlet message
     */
    public PortletMessage getMessage();

    /**
     * Returns the portlet request that has caused this event. If this event is not triggered by a request,
     * this methods returns null
     *
     * @return the portlet request
     */
    public PortletRequest getPortletRequest();

}
