/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletTabEventImpl.java,v 1.1.1.1 2007-02-01 20:49:56 kherm Exp $
 */
package org.gridlab.gridsphere.layout.event.impl;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.PortletTab;
import org.gridlab.gridsphere.layout.event.ComponentAction;
import org.gridlab.gridsphere.layout.event.PortletTabEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * A <code>PortletTabEventImpl</code> is the concrete implementation of
 * <code>PortletTabEvent</code>
 */
public class PortletTabEventImpl implements PortletTabEvent {

    private ComponentAction action;
    private PortletTab portletTab;
    private int id;
    private PortletRequest request;

    private PortletTabEventImpl() {
    }

    /**
     * Constructs an instance of PortletTabEventImpl from a portlet tab, a
     * tab event action and the portlet tab component id
     *
     * @param portletTab the portlet tab
     * @param action     the portlet tab event action
     * @param id         the portlet component id
     * @see PortletTab
     */
    public PortletTabEventImpl(PortletTab portletTab, PortletRequest request, ComponentAction action, int id) {
        this.action = action;
        this.portletTab = portletTab;
        this.id = id;
        this.request = request;
    }

    public PortletRequest getRequest() {
        return request;
    }

    /**
     * Returns true if this event actually triggered an action
     *
     * @return true if this event actually triggered an action
     */
    public boolean hasAction() {
        return (action != null);
    }

    /**
     * Returns the portlet tab event action
     *
     * @return the portlet tab event action
     */
    public ComponentAction getAction() {
        return action;
    }

    /**
     * Returns the PortletTab that was selected
     *
     * @return the PortletTab that was selcted
     */
    public PortletComponent getPortletComponent() {
        return portletTab;
    }

    /**
     * Returns the component id of the portlet tab
     *
     * @return the component id of the portlet tab
     */
    public int getID() {
        return id;
    }

}
