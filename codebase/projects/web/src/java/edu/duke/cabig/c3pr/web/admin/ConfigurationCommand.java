/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.admin;

import java.util.Map;
import java.util.TreeMap;

import edu.duke.cabig.c3pr.tools.Configuration;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperty;

/**
 * @author Rhett Sutphin
 */
public class ConfigurationCommand {
    private Configuration configuration;

    private Map<String, BoundProperty<?>> conf;

    public ConfigurationCommand(Configuration configuration) {
        this.configuration = configuration;
        conf = new TreeMap<String, BoundProperty<?>>();
        for (ConfigurationProperty<?> property : configuration.getProperties().getAll()) {
            conf.put(property.getKey(), new BoundProperty(property));
        }
    }

    public Map<String, BoundProperty<?>> getConf() {
        return conf;
    }

    public final class BoundProperty<V> {
        private ConfigurationProperty<V> property;

        public BoundProperty(ConfigurationProperty<V> property) {
            this.property = property;
        }

        public ConfigurationProperty<V> getProperty() {
            return property;
        }

        public V getValue() {
            return configuration.get(property);
        }

        public void setValue(V value) {
            configuration.set(property, value);
        }

        public V getDefault() {
            return property.getDefault();
        }
    }
}
