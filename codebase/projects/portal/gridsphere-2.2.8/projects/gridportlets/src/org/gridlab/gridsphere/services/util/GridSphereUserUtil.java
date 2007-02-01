package org.gridlab.gridsphere.services.util;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.core.user.LoginService;
import org.gridlab.gridsphere.services.core.user.UserManagerService;

import java.util.Locale;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridSphereUserUtil.java,v 1.1.1.1 2007-02-01 20:42:23 kherm Exp $
 * This service is provided for JSR web applications. Since Grid Portlets
 * requires the gridsphere user objects, this service provides a method
 * that returns a user object given a user name.
 */

public class GridSphereUserUtil {

    private static PortletLog log = SportletLog.getInstance(GridSphereUserUtil.class);

    public static User getUserByOid(String userOid) {
        // Get gridsphere user manager service
        UserManagerService userManagerService = getUserManagerService();
        if (userManagerService == null) {
            log.error("Unable to get user manager service!");
            return null;
        }
        // Get user with given oid
        return userManagerService.getUser(userOid);
    }

    public static User getUserByUserName(String userName) {
        // Get gridsphere user manager service
        UserManagerService userManagerService = getUserManagerService();
        if (userManagerService == null) {
            log.error("Unable to get user manager service!");
            return null;
        }
        // Get user with given name
        User user = userManagerService.getUserByUserName(userName);
        // If no user exists with given name, create one
        //if (user == null) {
        //    User newUser = userManagerService.createUser();
        //    newUser.setUserName(userName);
        //    userManagerService.saveUser(newUser);
        //    user = newUser;
        //}
        return user;
    }

    public static UserManagerService getUserManagerService() {
        return (UserManagerService) getPortletService(UserManagerService.class, "UserManagerService");
    }

    public static LoginService getLoginService() {
        return (LoginService) getPortletService(LoginService.class, "LoginService");
    }

    public static PortletService getPortletService(Class portletServiceClass, String portletServiceId) {
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        PortletService portletService = null;
        if (isCoreSpringEnabled()) {
            portletService = (PortletService) factory.createSpringService(portletServiceId);
        } else {
            try {
                portletService = factory.createPortletService(portletServiceClass, null, true);
            } catch (Exception e) {
                log.error("Unable to get portlet service " + portletServiceClass.getName(), e);
            }
        }
        return portletService;
    }

    public static boolean isCoreSpringEnabled() {
        String isSpringEnabled = GridPortletsResourceBundle.getResourceString(Locale.getDefault(), "gridsphere.spring.enabled", "false");
        return Boolean.valueOf(isSpringEnabled).booleanValue();
    }
}
