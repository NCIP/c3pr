/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseActionComponentFactory.java,v 1.1.1.1 2007-02-01 20:42:03 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.impl;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.*;

import javax.servlet.ServletContext;
import javax.portlet.PortletException;
import java.lang.reflect.Constructor;
import java.util.*;

public class BaseActionComponentFactory
        implements ActionComponentFactory, PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(BaseActionComponentFactory.class);

    protected ActionComponentTypeRegistry components = null;
    protected Map componentClassMap = new HashMap();
    protected ServletContext servletContext = null;
    protected List supportedClasses = new Vector();
    protected boolean inited = false;

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("init()");

        if (inited) return;

        log.info("Initing action component factory " + getClass());

        inited = true;
        servletContext = config.getServletContext();
        ServletContext gpContext = servletContext.getContext("/gridportlets");

        // get known resources
        String componentsPath = servletContext.getRealPath("/WEB-INF/ActionComponentTypes.xml");
        String mappingPath = servletContext.getRealPath("/WEB-INF/mapping/ActionComponentType.xml");

        try {
            components = ActionComponentTypeRegistry.open(componentsPath, mappingPath, Thread.currentThread().getContextClassLoader());
        } catch (Exception e) {
            log.error("FATAL PMXML: Unable to deserialize ActionComponents.xml", e);
            throw new PortletServiceUnavailableException("FATAL PMXML: Unable to deserialize ActionComponentTypes.xml");
        }
        Iterator types = components.getComponentTypeList().iterator();
        while (types.hasNext()) {
            ActionComponentType type = (ActionComponentType)types.next();
            Class compClass = type.getComponentClass();
            if (compClass != null) {
                log.info("Adding action component " + compClass.getName());
                supportedClasses.add(compClass);
                componentClassMap.put(compClass.getName(), type);
            }
        }
        PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
        ActionComponentBroker broker = null;
        try {
            log.info("Getting instance of action component broker");
            broker  = (ActionComponentBroker)
                    serviceFactory.createPortletService(ActionComponentFactory.class, null, true);
            log.info("Registering action componet types for factory" + getClass().getName());
            broker.addActionComponentFactory(this);
        } catch (PortletServiceNotFoundException e) {
            log.error("Action component broker not found", e);
        }
    }

    public void destroy() {
    }

    public ActionComponentType getActionComponentType(String compClass) throws PortletException {
        return (ActionComponentType)componentClassMap.get(compClass);
    }

    public ActionComponent createActionComponent(ActionComponentFrame container,
                                                 Class compClass,
                                                 String compName) throws PortletException {
        ActionComponentType type = (ActionComponentType)componentClassMap.get(compClass.getName());
        if (type == null) {
            throw new PortletException("Action component not supported... " + compClass.getName());
        }
        ActionComponent comp = null;
        try {
            Class[] parameterTypes
                    = new Class[] { ActionComponentFrame.class,
                                    String.class };
            Object[] arguments = new Object[] { container, compName };
            Constructor constructor = compClass.getConstructor(parameterTypes);
            comp = (ActionComponent)constructor.newInstance(arguments);
            // Default action
            String defaultAction = type.getDefaultAction();
            if (defaultAction != null) {
                log.debug("Setting default action " + compName + '.' + defaultAction);
                comp.setDefaultAction(defaultAction);
            }
            // Render action
            String renderAction = type.getRenderAction();
            if (renderAction != null) {
                log.debug("Setting render action " + compName + '.' + renderAction);
                comp.setRenderAction(renderAction);
            }
            // Default jsp page
            String defaultJspPage = type.getDefaultJspPage();
            if (defaultJspPage != null) {
                log.debug("Setting default jsp page " + compName + '.' + defaultJspPage);
                comp.setDefaultJspPage(defaultJspPage);
            }
        } catch (Exception e) {
            String msg = "Unable to create action component " + compClass.getName() + ", " + compName;
            log.error(msg, e);
            throw new PortletException(msg);
        }
        if (type.getHasServletContext()) {
            comp.setServletContext(getServletContext());
        } else {
            String extendsClass = type.getExtendsClass();
            if (extendsClass != null) {
            }
        }
        return comp;
    }

    public List getSupportedClasses() {
        return supportedClasses;
    }
}
