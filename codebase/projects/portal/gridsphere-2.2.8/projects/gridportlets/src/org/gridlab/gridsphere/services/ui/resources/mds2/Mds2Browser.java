/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: Mds2Browser.java,v 1.1.1.1 2007-02-01 20:42:15 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resources.mds2;

import org.gridlab.gridsphere.services.ui.resource.browser.SingleProfileBrowser;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;

public class Mds2Browser extends SingleProfileBrowser {

    public Mds2Browser(ActionComponentFrame actionComponentFrame, String compId) {
        super(actionComponentFrame, compId);
        setResourceProfileGroup("Mds2");
    }
}


