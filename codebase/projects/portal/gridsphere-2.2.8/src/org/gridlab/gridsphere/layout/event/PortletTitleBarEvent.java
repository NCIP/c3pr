/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletTitleBarEvent.java,v 1.1.1.1 2007-02-01 20:49:55 kherm Exp $
 */
package org.gridlab.gridsphere.layout.event;

import org.gridlab.gridsphere.portlet.Portlet;
import org.gridlab.gridsphere.portlet.PortletWindow;

/**
 * A <code>PortletTitleBarEvent</code> is created by a <code>PortletTitleBar</code>
 * when a title bar event has been triggered.
 */
public interface PortletTitleBarEvent extends PortletComponentEvent {

    /**
     * Action is an immutable representing the window state and portlet mode
     * of the portlet title bar.
     */
    public static final class TitleBarAction implements ComponentAction {

        public static final TitleBarAction WINDOW_MODIFY = new TitleBarAction(1);

        public static final TitleBarAction MODE_MODIFY = new TitleBarAction(5);

        private int action = 0;

        /**
         * Action cannot be instantiated outside of this class
         */
        private TitleBarAction(int action) {
            this.action = action;
        }

        public int getID() {
            return action;
        }
    }

    /**
     * Returns the portlet title bar mode
     *
     * @return mode the portlet title bar mode
     */
    public Portlet.Mode getMode();

    /**
     * Returns the portlet title bar window state
     *
     * @return the portlet title bar window state
     */
    public PortletWindow.State getState();

    /**
     * Returns true if this title bar event signals a window state change
     *
     * @return true if this title bar event signals a window state change
     */
    public boolean hasWindowStateAction();

    /**
     * Returns true if this title bar event signals a portlet mode change
     *
     * @return true if this title bar event signals a portlet mode change
     */
    public boolean hasPortletModeAction();

    /**
     * Returns the portlet title bar component id
     *
     * @return the portlet title bar component id
     */
    public int getID();

}
