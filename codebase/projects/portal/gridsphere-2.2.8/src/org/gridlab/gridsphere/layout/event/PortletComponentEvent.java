/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletComponentEvent.java,v 1.1.1.1 2007-02-01 20:49:55 kherm Exp $
 */
package org.gridlab.gridsphere.layout.event;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * A <code>PortletComponentEvent</code> is a general portlet render event
 */
public interface PortletComponentEvent {

    /**
     * Returns the portlet title bar event action
     *
     * @return the portlet title bar event action
     */
    public ComponentAction getAction();

    public PortletRequest getRequest();

    /**
     * Returns true if this event actually triggered an action
     *
     * @return true if this event actually triggered an action
     */
    public boolean hasAction();

    /**
     * Returns the PortletComponent that was selected
     *
     * @return the PortletComponent that was selcted
     */
    public PortletComponent getPortletComponent();

    /**
     * Returns the component id of the portlet component
     *
     * @return the component id of the portlet component
     */
    public int getID();

}
