/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionComponentBroker.java,v 1.1.1.1 2007-02-01 20:42:02 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.impl;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFactory;
import org.gridlab.gridsphere.services.ui.ActionComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponentType;

import javax.portlet.PortletException;
import javax.servlet.ServletContext;
import java.util.*;

public class ActionComponentBroker
        implements ActionComponentFactory, PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(ActionComponentBroker.class);
    private List supportedClasses = new Vector();
    private HashMap supportedClassesMap = new HashMap();
    private ServletContext servletContext = null;
    private boolean inited = false;

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {

        if (inited) return;

        log.info("Initing action component broker " + getClass());

        inited = true;
        servletContext = config.getServletContext();

        PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
        Enumeration paramNames = config.getInitParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            String serviceName = config.getInitParameter(paramName);
            Class serviceClass = null;
            try {
                serviceClass = Class.forName(serviceName);
            } catch (ClassNotFoundException e) {
                log.error("Action component factory class not found: " + serviceName);
            } finally {
                log.info("Loading action component factory " + serviceName);
                ActionComponentFactory service = null;
                try {
                    service  = (ActionComponentFactory)
                            serviceFactory.createPortletService(serviceClass, null, true);
                    addActionComponentFactory(service);
                } catch (PortletServiceNotFoundException e) {
                    log.error("Action component factory service not found: " + serviceName);
                }
            }
        }
    }

    public void destroy() {
    }

    public List getSupportedClasses() {
        return supportedClasses;
    }

    public ActionComponentType getActionComponentType(String compClass) throws PortletException {

        ActionComponentFactory factory = (ActionComponentFactory)supportedClassesMap.get(compClass);
        if (factory == null) {
            throw new PortletException("No factory found for action component " + compClass);
        }

        return (ActionComponentType)factory.getActionComponentType(compClass);
    }

    public ActionComponent createActionComponent(ActionComponentFrame container,
                                                 Class compClass,
                                                 String compName) throws PortletException {
        ActionComponentFactory factory = (ActionComponentFactory)supportedClassesMap.get(compClass.getName());
        if (factory == null) {
            throw new PortletException("No factory found for action component " + compClass.getName());
        }
        ActionComponent component = factory.createActionComponent(container, compClass, compName);
        // Set servlet context if this class extends another and uses its servlet context
        ActionComponentType type = factory.getActionComponentType(compClass.getName());
        if (!type.getHasServletContext()) {
            ServletContext servletContext = getServletContext(type);
            if (servletContext != null) {
                component.setServletContext(servletContext);
            }
        }
        return component;
    }

    protected ServletContext getServletContext(ActionComponentType type) throws PortletException {

        ServletContext servletContext = null;

        String extendsClass = type.getExtendsClass();
        if (extendsClass != null) {
            ActionComponentFactory factory = (ActionComponentFactory)supportedClassesMap.get(extendsClass);
            if (factory == null) {
                throw new PortletException("No factory found for action component " + extendsClass);
            }
            ActionComponentType extendsType = factory.getActionComponentType(extendsClass);
            if (extendsType.getHasServletContext()) {
                servletContext = factory.getServletContext();
            } else {
                servletContext = getServletContext(extendsType);
            }
        }

        return servletContext;
    }

    public void addActionComponentFactory(ActionComponentFactory service) {
        List supportedClasses = service.getSupportedClasses();
        for (int ii = 0; ii < supportedClasses.size(); ++ii) {
            Class actionClass = (Class)supportedClasses.get(ii);
            log.debug("Adding class " + actionClass.getName() + " with factory  " +  service.getClass().getName());
            supportedClassesMap.put(actionClass.getName(), service);
        }
        this.supportedClasses.addAll(supportedClasses);
     }
}
