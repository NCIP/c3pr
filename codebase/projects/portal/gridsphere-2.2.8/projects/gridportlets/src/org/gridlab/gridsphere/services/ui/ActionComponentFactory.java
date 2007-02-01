/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionComponentFactory.java,v 1.1.1.1 2007-02-01 20:41:56 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui;

import org.gridlab.gridsphere.portlet.service.PortletService;

import javax.portlet.PortletException;
import javax.servlet.ServletContext;
import java.util.List;

public interface ActionComponentFactory extends PortletService {

    /**
     * Returns the action component classes supported by this factory
     * @return The servlet context
     */
    public ServletContext getServletContext();

    /**
     * Returns the action component classes supported by this factory
     * @return The supported classes
     */
    public List getSupportedClasses();

    /**
     * Returns the action component classes supported by this factory
     * @return The action component type
     */
    public ActionComponentType getActionComponentType(String compClass) throws PortletException;

    /**
     * Creates an action component of the given class assigned to the given name.
     * @param container The containing action component frame
     * @param compClass The action component class
     * @param compName The action component name
     * @return The action component
     * @throws PortletException If the action component cannot be created.
     */
    public ActionComponent createActionComponent(ActionComponentFrame container,
                                                 Class compClass,
                                                 String compName) throws PortletException;
}
