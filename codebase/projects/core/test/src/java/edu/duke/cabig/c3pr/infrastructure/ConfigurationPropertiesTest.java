package edu.duke.cabig.c3pr.infrastructure;


import edu.duke.cabig.c3pr.tools.Configuration;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperties;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperty;
import junit.framework.TestCase;

/**
 * @author Ramakrishna
 */
public class ConfigurationPropertiesTest extends TestCase {
    public void testEmptyPropertiesIsEmptyWithoutErrors() throws Exception {
        ConfigurationProperties empty = ConfigurationProperties.empty();
        assertEquals(0, empty.size());
        assertNull(empty.get("anything"));
        assertNull(empty.getNameFor("anything"));
    }
    
    public void testGetCoppaEnableProperty() throws Exception {
    	ConfigurationProperty coppaEnable = Configuration.COPPA_ENABLE;
    	assertNotNull("Missing the property 'COPPA_ENABLE'",coppaEnable);
      //  assertTrue("COPPA_ENABLE should have been true", Boolean.parseBoolean(coppaEnable.getDefault().toString()));
    }
    
    public void testGetProperties() throws Exception {
    	Configuration conf = new Configuration();
    	assertEquals("Property count should be 35", 35, conf.getProperties().size());
    }
    
}
