/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletRoleCollection.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridlab.gridsphere.services.core.security.role.impl.descriptor;

import java.util.List;
import java.util.Vector;

/**
 * The <code>PortletRoleCollection</code> provides a list of
 * <code>PortletRoleDescription</code> entries.
 */
public class PortletRoleCollection {

    private List rolesList = new Vector();

    /**
     * Sets the list of portlet role descriptions
     *
     * @param rolesList a <code>Vector</code> containing portlet role descriptions
     *
     * @see PortletRoleDescription
     */
    public void setPortletRolesList(List rolesList) {
        this.rolesList = rolesList;
    }

    /**
     * Returns the list of portlet role descriptions
     *
     * @return a list containing the portlet role descriptions
     * 
     * @see PortletRoleDescription
     */
    public List getPortletRolesList() {
        return rolesList;
    }

}
