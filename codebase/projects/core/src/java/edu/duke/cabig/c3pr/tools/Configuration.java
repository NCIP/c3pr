/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.tools;

import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationEntry;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperty;
import gov.nih.nci.cabig.ctms.tools.configuration.DatabaseBackedConfiguration;
import gov.nih.nci.cabig.ctms.tools.configuration.DefaultConfigurationProperties;
import gov.nih.nci.cabig.ctms.tools.configuration.DefaultConfigurationProperty;

import org.springframework.core.io.ClassPathResource;

/**
 * @author Rhett Sutphin
 */
public class Configuration extends DatabaseBackedConfiguration {
    private static final DefaultConfigurationProperties PROPERTIES = new DefaultConfigurationProperties(
                    new ClassPathResource("details.properties"));

    public static final ConfigurationProperty<String> PSC_BASE_URL = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("pscBaseUrl"));

    public static final ConfigurationProperty<String> SMOKE_TEST_URL = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("smokeTestURL"));

    public static final ConfigurationProperty<String> CAAERS_BASE_URL = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("caaersBaseUrl"));

    public static final ConfigurationProperty<String> C3D_BASE_URL = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("c3dViewerBaseUrl"));

    public static final ConfigurationProperty<String> ESB_URL = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("esbUrl"));

    public static final ConfigurationProperty<String> MULTISITE_ESB_URL = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("jms.brokerUrl"));

    public static final ConfigurationProperty<String> ESB_ENABLE = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("esbEnable"));

    public static final ConfigurationProperty<String> MULTISITE_ENABLE = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("multisiteEnable"));

    public static final ConfigurationProperty<String> CCTS_WEBSSO_BASE_URL = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("ccts.websso.base_url"));

    public static final ConfigurationProperty<String> CCTS_WEBSSO_CERT_FILE = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("ccts.websso.cert_file"));

    public static final ConfigurationProperty<String> HOST_CERT = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("hostCertificate"));

    public static final ConfigurationProperty<String> HOST_KEY = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("hostKey"));

    public static final ConfigurationProperty<String> C3PR_WEBAPP_URL = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("c3pr.webapp.url"));

    public static final ConfigurationProperty<String> AUTHENTICATION_MODEL = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("authenticationMode"));

    public static final ConfigurationProperty<String> AUTHORIZATION_ENABLE = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("authorizationSwitch"));

    public static final ConfigurationProperty<String> LOCAL_NCI_INSTITUTE_CODE = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("localNciInstituteCode"));

    public static final ConfigurationProperty<String> OUTGOING_MAIL_SERVER = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("outgoingMailServer"));
    
    public static final ConfigurationProperty<String> OUTGOING_MAIL_SERVER_PORT = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("outgoingMailServerPort"));

    public static final ConfigurationProperty<String> OUTGOING_MAIL_USERNAME = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("outgoingMailUsername"));

    public static final ConfigurationProperty<String> OUTGOING_MAIL_PASSWORD = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("outgoingMailPassword"));

    public static final ConfigurationProperty<String> OUTGOING_MAIL_AUTH = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("outgoingMailAuth"));

    public static final ConfigurationProperty<String> SKIN_PATH = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("skinPath"));

    public static final ConfigurationProperty<String> SITE_NAME = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("siteName"));

    public static final ConfigurationProperty<String> PSC_WINDOW_NAME = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("psc_window_name"));

    public static final ConfigurationProperty<String> CAAERS_WINDOW_NAME = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("caaers_window_name"));

    public static final ConfigurationProperty<String> C3D_WINDOW_NAME = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("c3d_window_name"));

    public static final ConfigurationProperty<String> SMTP_SSL_AUTH = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("smtpSSLAuth"));

    public static final ConfigurationProperty<String> SMTP_PROTOCOL = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("smtpProtocol"));

    public static final ConfigurationProperty<String> CAS_BASE_URL = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("cas.base_url"));

    public static final ConfigurationProperty<String> CAS_CERT_FILE = PROPERTIES
                    .add(new DefaultConfigurationProperty.Text("cas.cert_file"));
    
    public static final ConfigurationProperty<String> IDP_URL = PROPERTIES
    				.add(new DefaultConfigurationProperty.Text("idp.url"));

    public static final ConfigurationProperty<String> IFS_URL = PROPERTIES
        			.add(new DefaultConfigurationProperty.Text("ifs.url"));
    
    public static final ConfigurationProperty<String> NOTIFICATION_LINK_BACK = PROPERTIES
    				.add(new DefaultConfigurationProperty.Text("notification.link_back"));

    public static final ConfigurationProperty<String> ESB_TIME_OUT = PROPERTIES
					.add(new DefaultConfigurationProperty.Text("esb.timeout"));
    
    public static final ConfigurationProperty<String> COPPA_ENABLE = PROPERTIES
    .add(new DefaultConfigurationProperty.Text("coppaEnable"));
    
    
    protected Class<? extends ConfigurationEntry> getConfigurationEntryClass() {
        return C3prConfigurationEntry.class;
    }

    public DefaultConfigurationProperties getProperties() {
        return PROPERTIES;
    }
}
