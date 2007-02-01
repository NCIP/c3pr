/*
 *
 * @version: $Id: UserManagerServiceImpl.java 4856 2006-06-12 04:53:29Z novotny $
 */
package org.gridlab.gridsphere.services.core.user.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.QueryFilter;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;

import java.util.*;

public class UserManagerServiceImpl implements PortletServiceProvider, UserManagerService {

    private static PortletLog log = SportletLog.getInstance(UserManagerServiceImpl.class);

    private static PersistenceManagerRdbms pm = null;

    private String jdoUser = SportletUserImpl.class.getName();

    private String accountMappingPath = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/account-mapping.xml");
    private String accountDescPath = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/account.xml");

    public UserManagerServiceImpl() {
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        pm = PersistenceManagerFactory.createGridSphereRdbms();
        /*try {
            userAccount = new UserAccountDescriptor(accountDescPath, accountMappingPath);
        } catch (Exception e) {
            throw new PortletServiceUnavailableException("Unable to load account.xml from " + accountDescPath);
        }*/
    }

    public void destroy() {
        log.info("Calling destroy()");
    }

    /**
     * Creates a new user
     *
     * @return a blank user
     */
    public User createUser() {
        SportletUserImpl user = new SportletUserImpl();
        saveSportletUserImpl(user);
        return user;
    }

    /**
     * Adds a user
     *
     * @param user a supplied User object
     */
    public void saveUser(User user) {
        if (user instanceof SportletUserImpl) {
            SportletUserImpl impl = (SportletUserImpl) user;
            saveSportletUserImpl(impl);
        }

    }


    public void deleteUser(User user) {
        if (user instanceof SportletUserImpl) {
            // delete user object
            deleteSportletUserImpl((SportletUserImpl) user);
            // Send message if not null
        }
    }

    public int getNumUsers() {
        try {
            String oql = "select count(UserID) from "
                + this.jdoUser
                + " uzer ";
            return pm.count(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving num users";
            log.error(msg, e);
            return 0;
        }
    }

    public List selectUsers(String criteria, QueryFilter queryFilter) {
        String oql = "select uzer from "
                + this.jdoUser
                + " uzer "
                + criteria;
        try {
            return pm.restoreList(oql, queryFilter);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving users with criteria " + criteria;
            log.error(msg, e);
            return new ArrayList();
        }
    }

    public List getUsersByUserName(QueryFilter queryFilter) {
        return selectUsers("order by uzer.UserID", queryFilter);
    }

    public List getUsersByOrganization(QueryFilter queryFilter) {
        return selectUsers("order by uzer.Organization", queryFilter);
    }

    public List getUsersByFullName(QueryFilter queryFilter) {
        return selectUsers("order by uzer.FullName", queryFilter);
    }

    public List getUsersByEmail(QueryFilter queryFilter) {
        return selectUsers("order by uzer.EmailAddress", queryFilter);
    }

    public List getUsers(QueryFilter queryFilter) {
        return selectUsers("", queryFilter);
    }

    public List getUsers() {
        return selectUsers("", null);
    }

    public User getUser(String id) {
        return getSportletUserImpl(id);
    }

    public User getLoggedInUser(String loginName) {
        SportletUserImpl user = getSportletUserImplByLoginName(loginName);
        if (user != null) {
            long now = Calendar.getInstance().getTime().getTime();
            String lastlogin = (String) user.getAttribute("lastlogin");
            if (lastlogin != null) {
                user.setLastLoginTime(Long.parseLong(lastlogin));
            } else {
                user.setLastLoginTime(now);
            }
            user.setAttribute("lastlogin", Long.toString(now));
            saveSportletUserImpl(user);
        }
        return user;
    }

    public User getUserByUserName(String loginName) {
        return getSportletUserImplByLoginName(loginName);
    }


    /**
     * Retrieves a user object with the given email from this service.
     *
     * @param email the user's email address
     */
    public User getUserByEmail(String email) {
        return selectSportletUserImpl("where uzer.EmailAddress='" + email + "'");
    }

    /**
     * Retrieves users based on attribute criteria
     *
     * @param attrName the attribute name
     * @param attrValue the attribute value
     */
    public List getUsersByAttribute(String attrName, String attrValue, QueryFilter queryFilter) {
        String criteria = "where uzer.attributes['user." + attrName + "'] = '" + attrValue + "'";
        return selectUsers(criteria, queryFilter);
    }

    private SportletUserImpl getSportletUserImpl(String id) {
        return selectSportletUserImpl("where uzer.oid='" + id + "'");
    }

    private SportletUserImpl getSportletUserImplByLoginName(String loginName) {
        return selectSportletUserImpl("where uzer.UserID='" + loginName + "'");
    }

    private SportletUserImpl selectSportletUserImpl(String criteria) {
        String oql = "select uzer from "
                + jdoUser
                + " uzer "
                + criteria;
        try {
            return (SportletUserImpl) pm.restore(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving user with criteria " + criteria;
            log.error(msg, e);
            return null;
        }
    }

    private void saveSportletUserImpl(SportletUserImpl user) {
        // Create or update user
        try {
            pm.saveOrUpdate(user);
        } catch (PersistenceManagerException e) {
            String msg = "Error updating user";
            log.error(msg, e);
        }

    }

    private void deleteSportletUserImpl(SportletUserImpl user) {
        try {
            pm.delete(user);
        } catch (PersistenceManagerException e) {
            String msg = "Error deleting user";
            log.error(msg, e);
        }
    }

    public boolean existsUserWithID(String userID) {
        String criteria = "where uzer.oid='" + userID + "'";
        return existsSportletUserImpl(criteria);
    }

    public boolean existsUserName(String loginName) {
        String criteria = "where uzer.UserID='" + loginName + "'";
        return existsSportletUserImpl(criteria);
    }

    private boolean existsSportletUserImpl(String criteria) {
        String oql = "select uzer from "
                + jdoUser
                + " uzer "
                + criteria;
        try {
            SportletUserImpl sui = (SportletUserImpl) pm.restore(oql);
            if (sui == null) {
                log.debug("User does not exist!");
            }
            return (sui != null);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving account request";
            log.error(msg, e);
        }
        return false;
    }

}
