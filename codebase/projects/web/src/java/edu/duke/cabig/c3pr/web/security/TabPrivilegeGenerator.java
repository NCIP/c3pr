package edu.duke.cabig.c3pr.web.security;

import gov.nih.nci.cabig.ctms.acegi.csm.authorization.AbstractPrivilegeAndObjectIdGenerator;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Apr 24, 2007 Time: 11:28:36 AM To change this
 * template use File | Settings | File Templates.
 */
public class TabPrivilegeGenerator extends AbstractPrivilegeAndObjectIdGenerator {

    protected String getKeyValue(Object object) {
        return object.getClass().getName();
    }

    protected boolean supports(Object object) {
        return object instanceof Tab;
    }

}
