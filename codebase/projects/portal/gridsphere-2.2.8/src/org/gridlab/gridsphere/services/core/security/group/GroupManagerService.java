/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 31, 2003
 * Time: 4:06:44 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.group;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.services.core.security.group.impl.UserGroup;

import java.util.List;

public interface GroupManagerService extends PortletService {

    public PortletGroup getCoreGroup();

    public List getGroups();

    public void deleteGroup(PortletGroup group);

    public PortletGroup getGroup(String groupName);

    public void saveGroup(PortletGroup portletGroup);

    public void addUserToGroup(User user, PortletGroup group);

    public void deleteUserInGroup(User user, PortletGroup group);

    public void deleteUserGroup(UserGroup userGroup);

    public UserGroup getUserGroup(User user, PortletGroup group);

    public List getGroups(User user);

    public List getUsersInGroup(PortletGroup group);

    public boolean isUserInGroup(User user, PortletGroup group);


    // these methods should not be used publicly anymore
    /**
     * @deprecated
     * @return a list of user group objects
     */
    public List getUserGroups();

    /**
     * @deprecated
     * @param groupEntry a group entry
     */
    public void saveUserGroup(UserGroup groupEntry);

}
