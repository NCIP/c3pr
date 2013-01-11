/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.infrastructure;


import edu.duke.cabig.c3pr.tools.Configuration;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperties;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperty;
import gov.nih.nci.cabig.ctms.tools.configuration.DefaultConfigurationProperties;
import junit.framework.TestCase;

/**
 * @author Ramakrishna
 */
public class ConfigurationPropertiesTest extends TestCase {
    public void testEmptyPropertiesIsEmptyWithoutErrors() throws Exception {
        ConfigurationProperties empty = DefaultConfigurationProperties.empty();
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
