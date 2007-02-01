/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletFrameListener.java,v 1.1.1.1 2007-02-01 20:49:55 kherm Exp $
 */
package org.gridlab.gridsphere.layout.event;

/**
 * The <code>PortletFrameListener</code> is an interface for an observer to register to
 * receive notifications of changes to a portlet frame component.
 */
public interface PortletFrameListener {

    /**
     * Gives notification that a portlet frame event has occured
     *
     * @param event the portlet frame event
     */
    public void handleFrameEvent(PortletFrameEvent event);

}
