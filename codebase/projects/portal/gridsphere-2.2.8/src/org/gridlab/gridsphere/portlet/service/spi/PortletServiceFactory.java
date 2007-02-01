/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletServiceFactory.java,v 1.1.1.1 2007-02-01 20:50:19 kherm Exp $
 */
package org.gridlab.gridsphere.portlet.service.spi;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;

import javax.servlet.ServletContext;

/**
 * The <code>PortletServiceFactory</code> interface must be implemented by
 * all portlet service factory implementations.
 */
public interface PortletServiceFactory {

    /**
     * Returns a Spring (www.springframework.org) service defined in applicationContext.xml by its
     * bean name
     *
     * @param beanName the bean name identifying the spring service
     * @return the Spring service defined in applicationContext.xml or null if none exists
     */
    public Object createSpringService(String beanName);
    
    /**
     * Creates a portlet service and initializes it
     *
     * @param service          the class of the service
     * @param servletContext   the servlet configuration
     * @param useCachedService reuse a previous initialized service if <code>true</code>,
     *                         otherwise create a new service instance if <code>false</code>
     * @return the instantiated portlet service
     * @throws PortletServiceUnavailableException
     *          if the portlet service is unavailable
     * @throws PortletServiceNotFoundException
     *          if the PortletService is not found
     *
     * @deprecated Use createPortletService(Class service, boolean useCachedService) instead
     */
    public PortletService createPortletService(Class service,
                                               ServletContext servletContext,
                                               boolean useCachedService)
            throws PortletServiceUnavailableException, PortletServiceNotFoundException;
    
    /**
     * Creates a portlet service and initializes it
     *
     * @param service          the class of the service
     * @param useCachedService reuse a previous initialized service if <code>true</code>,
     *                         otherwise create a new service instance if <code>false</code>
     * @return the instantiated portlet service
     * @throws PortletServiceUnavailableException
     *          if the portlet service is unavailable
     * @throws PortletServiceNotFoundException
     *          if the PortletService is not found
     *
     */
    public PortletService createPortletService(Class service,
                                               boolean useCachedService)
            throws PortletServiceUnavailableException, PortletServiceNotFoundException;

}
