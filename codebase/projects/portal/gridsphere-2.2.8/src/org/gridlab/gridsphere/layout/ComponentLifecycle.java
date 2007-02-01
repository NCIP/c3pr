/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ComponentLifecycle.java,v 1.1.1.1 2007-02-01 20:49:49 kherm Exp $
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.util.List;

/**
 * The <code>ComponentLifecycle</code> represents the lifecycle methods required by any
 * PortletComponent.
 */
public interface ComponentLifecycle extends ComponentRender {

    /**
     * Initializes the portlet component. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see ComponentIdentifier
     */
    public List init(PortletRequest req, List list);

    /**
     * Performs an action on this portlet component
     *
     * @param event a gridsphere event
     */
    public void actionPerformed(GridSphereEvent event);

    /**
     * Destroys this portlet component
     */
    public void destroy();

    /**
     * Returns the associated portlet component id
     *
     * @return the portlet component id
     */
    public int getComponentID();

}
