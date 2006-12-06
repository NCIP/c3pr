/*
 * Created on Jan 5, 2006
 */
package edu.duke.cabig.c3pr.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

/**
 * This class loads all configurable values from the table
 * CONFIGURATION_PARAMETERS into the cache.
 * 
 * @author Rangaraju Gadiraju
 */
public class ConfigurationManager {

	// All keys in the CONFIGURATION_PARAMETERS table should be defined in this
	// class as a static
	// String to refer throughout the applicaiton.
	public final static String ACCESSIBILITY_DETAIL_1 = "AccessibilityDetail1";
	public final static String ACCESSIBILITY_DETAIL_2 = "AccessibilityDetail2";
	public final static String ACCESSIBILITY_DETAIL_3 = "AccessibilityDetail3";
	public final static String APPLICATION_ABBR_TITLE = "ApplicationAbbrTitle";
	public final static String APPLICATION_LOGO = "ApplicationLogo";
	public final static String APPLICATION_LONG_TITLE = "ApplicationLongTitle";
	public final static String APPLICATION_SUPPORT_DETAIL = "ApplicationSupportDetail";
	public final static String AUTHENTICATION_TYPE = "AuthenticationType";
	public final static String C3D_URL = "C3DURL";
	public final static String CONTACT_US_DETAIL = "ContactUsDetail";
	public final static String DISCLAIMER_DETAIL_1 = "DisclaimerDetail1";
	public final static String DISCLAIMER_DETAIL_2 = "DisclaimerDetail2";
	public final static String DISCLAIMER_DETAIL_3 = "DisclaimerDetail3";
	public final static String HOME_APPLICATION_DESCRIPTION = "HomeApplicationDescription";
	public final static String HOME_DID_YOU_KNOW = "HomeDidYouKnow";
	public final static String HOME_WHATS_NEW = "HomeWhatsNew";
	public final static String LINKS_PAGE_DETAIL = "LinksPageDetail";
	public final static String NCI_ADOPTER = "NCIAdopter"; 
	public final static String NCI_ADOPTER_LOGO = "NCIAdopterLogo"; //DEPRECATED
	public final static String NCI_ADOPTER_LOGO_TEXT = "NCIAdopterLogoText";
	public final static String NCI_ADOPTER_TAG_LINE = "NCIAdopterTagLine"; //DEPRECATED
	public final static String NCI_ADOPTER_TAG_LINE_TEXT = "NCIAdopterTagLineText";
	public final static String NCI_ADOPTER_URL = "NCIAdopterURL";
	public final static String PRIVACY_DETAIL_1 = "PrivacyDetail1";
	public final static String PRIVACY_DETAIL_2 = "PrivacyDetail2";
	public final static String PRIVACY_DETAIL_3 = "PrivacyDetail3";
	public final static String PRIVACY_DETAIL_4 = "PrivacyDetail4";
	public final static String PRIVACY_DETAIL_5 = "PrivacyDetail5";
	public final static String REPORTS_DESIGNATION = "ReportsDesignation";
	public final static String SHOW_FOOTER = "ShowFooter";	
	public final static String DEFAULT_NCI_LOGO = "\"Images/logotype.gif\"";
	public final static String APPLICATION_TITLE_PREFIX = "ApplicationTitlePrefix";
	public static final String CADSR_URL = "CADSRURL";
	public static final String DATABASE_TYPE = "DBType";
	public static final String HELP_LINK = "HelpLink";
	public static final String HELP_WITHOUT_LINK = "HelpWithoutLink";
	public static final String OPA_DB_SID = "OPADBSID";
	
	
	//Define here if we know the possible values for each key in the CONFIGURATION_PARAMETERS table 
	//String to refer throughout the applicaiton.
	//Possible values for AUTHENTICATION_TYPE
	public final static String AUTHENTICATION_TYPE_CSM = "CSM";
	public final static String AUTHENTICATION_TYPE_NIHSITEMINDER = "NIH-SITEMINDER";
	
	
	//Referral Letter report
	public static final String REFERRAL_LETTER_TEXT_LINE1 = "ReferralLetterTextLine1";
	public static final String REFERRAL_LETTER_TEXT_LINE2 = "ReferralLetterTextLine2";
	public static final String REFERRAL_LETTER_TEXT_LINE3 = "ReferralLetterTextLine3";
	public static final String REFERRAL_LETTER_FROM_NAME = "ReferralLetterFromName";
	public static final String REFERRAL_LETTER_FROM_TITLE = "ReferralLetterFromTitle";
	
	// Please add all private member variables here.
	private Map configurationMap = null;

	public ConfigurationManager() {
	}

	/**
	 * Returns the configurable value of the specified key.
	 * 
	 * @param key -
	 *            key for which the value to be returned.
	 * @return the value of the key passed.
	 */
	public String getValue(String key) {

		if (configurationMap == null)
			setConfigurationMap();

		return (String) configurationMap.get(key);

	}

	/**
	 * Retrieves all keys and respective values from CONFIGURATION_PARAMETERS
	 * table and populates the map with those key, value.
	 */
	private void setConfigurationMap() {

		try {

			Statement stmt = getConnection().createStatement();
			String query = "SELECT * FROM CONFIGURATION_PARAMETERS";
			ResultSet rs = stmt.executeQuery(query);
			configurationMap = new HashMap();
			while (rs.next()) {
				configurationMap.put(rs.getString(2), rs.getString(3));
			}
			
			

		} catch (Exception e) {
			LogWriter.getInstance().log(LogWriter.SEVERE, e.getMessage(), e);
		}

	}

	 public static Connection getConnection() throws Exception
	 {
		    // load the Oracle JDBC Driver
		    Class.forName("oracle.jdbc.driver.OracleDriver");
		    // define database connection parameters
		    return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:database", "userName",
		        "password");
	}
	
}
