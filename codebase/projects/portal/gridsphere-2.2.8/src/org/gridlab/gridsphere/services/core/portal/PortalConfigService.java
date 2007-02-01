/*
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortalConfigService.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridlab.gridsphere.services.core.portal;



/**
 * Portal configuration service is used to manage portal administrative settings
 */
public interface PortalConfigService {

    public void savePortalConfigSettings(PortalConfigSettings configSettings);

    public PortalConfigSettings getPortalConfigSettings();

}
