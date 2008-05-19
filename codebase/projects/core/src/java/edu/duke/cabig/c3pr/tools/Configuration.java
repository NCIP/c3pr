package edu.duke.cabig.c3pr.tools;

import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperties;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperty;
import gov.nih.nci.cabig.ctms.tools.configuration.DatabaseBackedConfiguration;

import org.springframework.core.io.ClassPathResource;

/**
 * @author Rhett Sutphin
 */
public class Configuration extends DatabaseBackedConfiguration {
    private static final ConfigurationProperties PROPERTIES = new ConfigurationProperties(
                    new ClassPathResource("details.properties"));

    public static final ConfigurationProperty<String> PSC_BASE_URL = PROPERTIES
                    .add(new ConfigurationProperty.Text("pscBaseUrl"));

    public static final ConfigurationProperty<String> CAAERS_BASE_URL = PROPERTIES
                    .add(new ConfigurationProperty.Text("caaersBaseUrl"));

    public static final ConfigurationProperty<String> C3D_BASE_URL = PROPERTIES
                    .add(new ConfigurationProperty.Text("c3dViewerBaseUrl"));

    public static final ConfigurationProperty<String> ESB_URL = PROPERTIES
                    .add(new ConfigurationProperty.Text("esbUrl"));

    public static final ConfigurationProperty<String> ESB_ENABLE = PROPERTIES
                    .add(new ConfigurationProperty.Text("esbEnable"));
    
    public static final ConfigurationProperty<String> MULTISITE_ENABLE = PROPERTIES
                    .add(new ConfigurationProperty.Text("multisiteEnable"));

    public static final ConfigurationProperty<String> CCTS_WEBSSO_BASE_URL = PROPERTIES
                    .add(new ConfigurationProperty.Text("ccts.websso.base_url"));

    public static final ConfigurationProperty<String> CCTS_WEBSSO_CERT_FILE = PROPERTIES
                    .add(new ConfigurationProperty.Text("ccts.websso.cert_file"));

    public static final ConfigurationProperty<String> HOST_CERT = PROPERTIES
                    .add(new ConfigurationProperty.Text("hostCertificate"));

    public static final ConfigurationProperty<String> HOST_KEY = PROPERTIES
                    .add(new ConfigurationProperty.Text("hostKey"));

    public static final ConfigurationProperty<String> C3PR_WEBAPP_URL = PROPERTIES
                    .add(new ConfigurationProperty.Text("c3pr.webapp.url"));

    public static final ConfigurationProperty<String> AUTHENTICATION_MODEL = PROPERTIES
                    .add(new ConfigurationProperty.Text("authenticationMode"));

    public static final ConfigurationProperty<String> HOSTED_MODE = PROPERTIES
                    .add(new ConfigurationProperty.Text("hostedMode"));

    public static final ConfigurationProperty<String> AUTHORIZATION_ENABLE = PROPERTIES
                    .add(new ConfigurationProperty.Text("authorizationSwitch"));

    public static final ConfigurationProperty<String> LOCAL_NCI_INSTITUTE_CODE = PROPERTIES
                    .add(new ConfigurationProperty.Text("localNciInstituteCode"));

    public static final ConfigurationProperty<String> OUTGOING_MAIL_SERVER = PROPERTIES
                    .add(new ConfigurationProperty.Text("outgoingMailServer"));

    public static final ConfigurationProperty<String> OUTGOING_MAIL_SERVER_PORT = PROPERTIES
                    .add(new ConfigurationProperty.Text("outgoingMailServerPort"));

    public static final ConfigurationProperty<String> OUTGOING_MAIL_USERNAME = PROPERTIES
                    .add(new ConfigurationProperty.Text("outgoingMailUsername"));

    public static final ConfigurationProperty<String> OUTGOING_MAIL_PASSWORD = PROPERTIES
                    .add(new ConfigurationProperty.Text("outgoingMailPassword"));

    public static final ConfigurationProperty<String> OUTGOING_MAIL_FROM_ADDRESS = PROPERTIES
                    .add(new ConfigurationProperty.Text("outgoingMailFromAddress"));

    public static final ConfigurationProperty<String> OUTGOING_MAIL_AUTH = PROPERTIES
                    .add(new ConfigurationProperty.Text("outgoingMailAuth"));

    public ConfigurationProperties getProperties() {
        return PROPERTIES;
    }
}
