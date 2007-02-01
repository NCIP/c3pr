/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SportletServiceConfig.java,v 1.1.1.1 2007-02-01 20:50:19 kherm Exp $
 */
package org.gridlab.gridsphere.portlet.service.spi.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceDefinition;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceDescriptor;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * The <code>SportletServiceConfig</code> provides an implementation
 * of the <code>PortletServiceConfig</code> interface through which
 * portlet services access the configuration settings from the services
 * descriptor file.
 */
public class SportletServiceConfig implements PortletServiceConfig {

    private Properties configProperties;
    private ServletContext servletContext;
    private SportletServiceDefinition def;

    /**
     * Constructor disallows non-argument instantiation
     */
    private SportletServiceConfig() {
    }

    /**
     * Constructs an instance of SportletServiceConfig using the supplied
     * service class, the configuration properties and the  servlet configuration
     *
     * @param def            the sportlet service definition
     * @param servletContext the <code>ServletConfig</code>
     */
    public SportletServiceConfig(SportletServiceDefinition def,
                                 ServletContext servletContext) {
        this.def = def;
        this.configProperties = def.getConfigProperties();
        this.servletContext = servletContext;
    }

    /**
     * Returns the init parameter with the given name.
     *
     * @param name the name of the requested init parameter.
     * @return the init parameter
     */
    public String getInitParameter(String name) {
        return configProperties.getProperty(name);
    }

    /**
     * Returns the init parameter with the given name.
     *
     * @param name  the name of the requested init parameter.
     * @param value the value of the init parameter
     */
    public void setInitParameter(String name, String value) {
        configProperties.setProperty(name, value);
    }

    /**
     * Returns the init parameter with the given name. It returns the given default
     * value if the parameter is not found.
     *
     * @param name         the name of the requested init parameter.
     * @param defaultValue the default value to return.
     * @return the init parameter value if exists, otherwise defaultValue
     */
    public String getInitParameter(String name, String defaultValue) {
        return configProperties.getProperty(name, defaultValue);
    }

    /**
     * Returns an enumeration with the names of all init parameters provided in the portlet service configuration.
     *
     * @return an enumeration of the init parameters
     */
    public Enumeration getInitParameterNames() {
        return configProperties.keys();
    }

    /**
     * Returns the servlet context
     *
     * @return the servlet context
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * Stores the service config settings
     *
     * @throws java.io.IOException if the store failed
     */
    public void store() throws IOException {
        SportletServiceDescriptor desc = def.getServiceDescriptor();
        def.setConfigProperties(configProperties);
        desc.setServiceDefinition(def);
        try {
            desc.save();
        } catch (PersistenceManagerException e) {
            throw new IOException("Unable to serialize portlet service config");
        }
    }

}
