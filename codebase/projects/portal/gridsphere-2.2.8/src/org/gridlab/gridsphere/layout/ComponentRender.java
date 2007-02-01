/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ComponentRender.java,v 1.1.1.1 2007-02-01 20:49:49 kherm Exp $
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

/**
 * The <code>ComponentRender</code> interface required by all PortletComponets to be displayed
 */
public interface ComponentRender extends Cloneable {

    /**
     * Renders the portlet component
     *
     * @param event a gridsphere event
     */
    public void doRender(GridSphereEvent event);

    public Object clone() throws CloneNotSupportedException;
}
