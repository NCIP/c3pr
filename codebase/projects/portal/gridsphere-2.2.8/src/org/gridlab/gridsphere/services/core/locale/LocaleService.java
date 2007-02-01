package org.gridlab.gridsphere.services.core.locale;

import org.gridlab.gridsphere.portlet.service.PortletService;

import java.util.Locale;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: LocaleService.java 4496 2006-02-08 20:27:04Z wehrens $
 */
public interface LocaleService extends PortletService {

    public Locale[] getSupportedLocales();

}
