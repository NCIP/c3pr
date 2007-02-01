/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridPortletsResourceBundle.java,v 1.1.1.1 2007-02-01 20:42:23 kherm Exp $
 */
package org.gridlab.gridsphere.services.util;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.ResourceBundle;
import java.util.Locale;

public class GridPortletsResourceBundle {

    public static String BASE_NAME = "gridportlets.Portlet";
    private static PortletLog log = SportletLog.getInstance(GridPortletsResourceBundle.class);
    private static Locale DEFAULT_LOCALE = Locale.ENGLISH;

    public static String getResourceString(Locale locale, String key) {
        try {
            ResourceBundle bundle = getResourceBundle(locale);
            // Use default locale if no bundle returned
            if (bundle == null) {
                bundle = getResourceBundle(DEFAULT_LOCALE);
            }
            return bundle.getString(key);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return key;
        }
    }

    public static String getResourceString(Locale locale, String key, String defaultValue) {
        String value = null;
        try {
            ResourceBundle bundle = getResourceBundle(locale);
            // Use default locale if no bundle returned
            if (bundle == null) {
                bundle = getResourceBundle(DEFAULT_LOCALE);
            }
            value = bundle.getString(key);
            if (value == null) {
                value = defaultValue;
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return value;
    }

    public static ResourceBundle getResourceBundle(Locale locale) {
        log.debug("Getting Grid Portlets resource bundle for language " + locale.getLanguage());
        return ResourceBundle.getBundle(BASE_NAME, locale);
    }
}
