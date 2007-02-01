package org.gridlab.gridsphere.portlets.file;

import org.gridlab.gridsphere.services.ui.file.FileActivityComp;
import org.gridlab.gridsphere.services.ui.file.FileActivityComp;
import org.gridlab.gridsphere.portlets.ActionComponentPortlet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileActivityPortlet.java,v 1.2 2004/06/12 15:32:32 russell Exp
 * <p>
 * Specifies a transfer operation.
 */

public class FileActivityPortlet extends ActionComponentPortlet {

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        setViewModeComp(FileActivityComp.class);
    }
}
