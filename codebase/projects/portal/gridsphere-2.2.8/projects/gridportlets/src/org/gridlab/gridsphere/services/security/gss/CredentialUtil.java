/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialUtil.java,v 1.1.1.1 2007-02-01 20:41:31 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.ietf.jgss.GSSCredential;

import java.util.List;
import java.util.Vector;

/**
 * This utility class simplifies the use of the credential package by
 * providing convenient methods for methods commonly used by
 * other portlet services.
 */
public class CredentialUtil {

    private static PortletLog log = SportletLog.getInstance(CredentialUtil.class);
    private static PortletServiceFactory portletServiceFactory = null;

    /**
     * Returns the time remaining for the given context in a user friendly format.
     * Should be localized however!
     * @param context The credential context
     * @return The type remaining as a string
     */
    public static String getTimeRemainingText(CredentialContext context) {
        // Dumb way to convert seconds to hours + minutes + seconds
        int lifetime = context.getRemainingLifetime();
        int hours = lifetime / 60 / 60;
        int hoursRe = hours * 60 * 60;
        int minutes = (lifetime - hoursRe) / 60;
        int secondsRe = hoursRe + minutes * 60;
        int seconds = lifetime - secondsRe;
        StringBuffer timeRemainingBuffer = new StringBuffer();
        if (minutes > 0) {
            timeRemainingBuffer.append(hours);
            timeRemainingBuffer.append(" hours ");
        }
        if (minutes > 0) {
            timeRemainingBuffer.append(minutes);
            timeRemainingBuffer.append(" minutes ");
        }
        timeRemainingBuffer.append(seconds);
        timeRemainingBuffer.append(" seconds");
        return timeRemainingBuffer.toString();
    }

    /**
     * Returns the credential context belonging to the given user with the given DN.
     * @param user The user
     * @return The credential context
     */
    public static CredentialContext getDefaultCredentialContext(User user) {
        CredentialContext credentialContext = null;
        try {
            credentialContext = getCredentialManagerService(user).getDefaultCredentialContext(user);
        } catch(PortletServiceException e) {
            log.error(e.getMessage());
        }
        return credentialContext;
    }

    /**
     * Returns the credential context belonging to the given user with the given DN.
     * @param user The user
     * @param dn The DN
     * @return The credential context
     */
    public static CredentialContext getCredentialContext(User user, String dn) {
        CredentialContext credentialContext = null;
        try {
            credentialContext = getCredentialManagerService(user).getCredentialContextByDn(dn);
        } catch(PortletServiceException e) {
            log.error(e.getMessage());
        }
        return credentialContext;
    }

    /**
     * Returns the credential belong to the given user with the given DN.
     * @param user The user
     * @param dn The DN
     * @return The credential
     */
    public static GSSCredential getCredential(User user, String dn) {
        GSSCredential credential = null;
        try {
            credential = getCredentialManagerService(user).getCredentialContextByDn(dn).getCredential();
        } catch(PortletServiceException e) {
            log.error(e.getMessage());
        }
        return credential;
    }

    /**
     * Returns the default credential for the given user.
     * @param user The user
     * @return The credential
     */
    public static GSSCredential getDefaultCredential(User user) {
        GSSCredential credential = null;
        try {
            credential = getCredentialManagerService(user).getDefaultCredential(user);
        } catch(PortletServiceException e) {
            log.error(e.getMessage());
        }
        return credential;
    }

    /**
     * Retuns the default credential for the given user and resource.
     * @param user The user
     * @param resource The resource
     * @return The credential
     */
    public static GSSCredential getDefaultCredential(User user, GssEnabledResource resource) {
        GSSCredential credential = null;
        try {
            credential = getCredentialManagerService(user).getDefaultCredential(user, resource);
        } catch(PortletServiceException e) {
            log.error(e.getMessage());
        }
        return credential;
    }

    /**
     * Returns the active credentials for the given user.
     * @param user The user
     * @return The active credentials
     * @see GSSCredential
     */
    public static List getActiveCredentials(User user) {
        List activeCredentials = null;
        try {
            activeCredentials = getCredentialManagerService(user).getActiveCredentials(user);
        } catch(PortletServiceException e) {
            log.error(e.getMessage());
            activeCredentials = new Vector(0);
        }
        return activeCredentials;
    }

    /**
     * Returns the active credentials for the given user and resource.
     * @param user The user
     * @param resource The resource
     * @return The active credentials
     * @see GSSCredential
     */
    public static List getActiveCredentials(User user, GssEnabledResource resource) {
        List activeCredentials = null;
        try {
            activeCredentials = getCredentialManagerService(user).getActiveCredentials(user, resource);
        } catch(PortletServiceException e) {
            log.error(e.getMessage());
            activeCredentials = new Vector(0);
        }
        return activeCredentials;
    }

    /**
     * Returns a handle to the credential manager service.
     * @param user
     * @return The cerdential manager service
     * @throws PortletServiceException
     */
    public static CredentialManagerService getCredentialManagerService(User user)
            throws PortletServiceException {
        if (portletServiceFactory == null) {
            portletServiceFactory = SportletServiceFactory.getInstance();
        }
        return (CredentialManagerService)
                portletServiceFactory.createPortletService(CredentialManagerService.class, null, true);
    }

    public static PortletServiceFactory getPortletServiceFactory() {
        if (portletServiceFactory == null) {
            portletServiceFactory = SportletServiceFactory.getInstance();
        }
        return portletServiceFactory;
    }
}
