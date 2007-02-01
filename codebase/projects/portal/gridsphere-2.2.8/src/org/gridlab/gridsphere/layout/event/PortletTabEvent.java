/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletTabEvent.java,v 1.1.1.1 2007-02-01 20:49:55 kherm Exp $
 */
package org.gridlab.gridsphere.layout.event;


/**
 * A <code>PortletTabEvent</code> is triggered by a <code>PortletTab</code>
 * when a tab has been selected.
 */
public interface PortletTabEvent extends PortletComponentEvent {

    /**
     * An Action defines a single tab event action:
     * <p/>
     * <ul><li>TAB_SELECTED</li></ul>
     */
    public static final class TabAction implements ComponentAction {

        public static final TabAction TAB_SELECTED = new TabAction(1);

        private int action = 0;

        /**
         * Action is immutable and cannot be created outside of this class
         *
         * @param action a unique integer id
         */
        private TabAction(int action) {
            this.action = action;
        }

        public int getID() {
            return action;
        }
    }

    /**
     * Returns the component id of the portlet tab
     *
     * @return the component id of the portlet tab
     */
    public int getID();

}
