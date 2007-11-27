package edu.duke.cabig.c3pr.tools;

import org.springframework.core.io.ClassPathResource;

import gov.nih.nci.cabig.ctms.tools.configuration.DatabaseBackedConfiguration;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperties;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperty;

/**
 * @author Rhett Sutphin
 */
public class Configuration extends DatabaseBackedConfiguration {
    private static final ConfigurationProperties PROPERTIES
        = new ConfigurationProperties(new ClassPathResource("details.properties"));

    public static final ConfigurationProperty<String> PSC_BASE_URL
        = PROPERTIES.add(new ConfigurationProperty.Text("pscBaseUrl"));
    public static final ConfigurationProperty<String> CAAERS_BASE_URL
        = PROPERTIES.add(new ConfigurationProperty.Text("caaersBaseUrl"));
    public static final ConfigurationProperty<String> C3D_BASE_URL
    	= PROPERTIES.add(new ConfigurationProperty.Text("c3dViewerBaseUrl"));
    public static final ConfigurationProperty<String> ESB_URL
        = PROPERTIES.add(new ConfigurationProperty.Text("esbUrl"));
    public static final ConfigurationProperty<String> ESB_ENABLE
    	= PROPERTIES.add(new ConfigurationProperty.Text("esbEnable"));

    public static final ConfigurationProperty<String> GRID_IDP_URL
    	= PROPERTIES.add(new ConfigurationProperty.Text("gridIdpUrl"));
    public static final ConfigurationProperty<String> GRID_IFS_URL
		= PROPERTIES.add(new ConfigurationProperty.Text("gridIfsUrl"));
    public static final ConfigurationProperty<String> AUTHORIZATION_ENABLE
		= PROPERTIES.add(new ConfigurationProperty.Text("authorizationEnable"));
    public static final ConfigurationProperty<String> LOCAL_NCI_INSTITUTE_CODE
		= PROPERTIES.add(new ConfigurationProperty.Text("localNciInstituteCode"));

    
    public static final ConfigurationProperty<String> SMTP_ADDRESS
        = PROPERTIES.add(new ConfigurationProperty.Text("smtpAddress"));
    public static final ConfigurationProperty<Integer> SMTP_PORT
        = PROPERTIES.add(new ConfigurationProperty.Int("smtpPort"));
    public static final ConfigurationProperty<String> SMTP_USER
        = PROPERTIES.add(new ConfigurationProperty.Text("smtpUser"));
    public static final ConfigurationProperty<String> SMTP_PASSWORD
        = PROPERTIES.add(new ConfigurationProperty.Text("smtpPassword"));
    public static final ConfigurationProperty<String> SYSTEM_FROM_EMAIL
        = PROPERTIES.add(new ConfigurationProperty.Text("systemFromEmail"));

    @Override
    public ConfigurationProperties getProperties() {
        return PROPERTIES;
    }
}
