package org.gridlab.gridsphere.services.core.messaging.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.messaging.TextMessagingService;
import org.gridlab.gridsphere.services.core.messaging.MessagingID;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridsphere.tmf.TextMessagingException;
import org.gridsphere.tmf.TMFService;
import org.gridsphere.tmf.TMFFactory;
import org.gridsphere.tmf.TextMessagingSession;
import org.gridsphere.tmf.message.GenericMessage;
import org.gridsphere.tmf.message.InstantMessage;
import org.gridsphere.tmf.message.MailMessage;

import java.util.Set;
import java.io.File;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: TextMessagingServiceImpl.java 4496 2006-02-08 20:27:04Z wehrens $
 */

public class TextMessagingServiceImpl implements TextMessagingService, PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(TextMessagingServiceImpl.class);

    TMFService tmfService = null;
    PersistenceManagerRdbms pm = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        String configfile = config.getServletContext().getRealPath("WEB-INF"+
                File.separator+"CustomPortal"+File.separator+"tmf");
        tmfService = TMFFactory.createTMFService(configfile);
        pm = PersistenceManagerFactory.createGridSphereRdbms();
        log.info("Starting up TextMessagingService with config " + configfile);
        tmfService.startup();
    }

    public void destroy() {
        this.shutdown();
    }

    public InstantMessage getInstantMessage() {
        return tmfService.getInstantMessage();
    }

    public MailMessage getMailMessage() {
        return tmfService.getMailMessage();
    }

    public void send(GenericMessage message) throws TextMessagingException {
        tmfService.sendMessage(message);
    }

    public void startup() {
        tmfService.startup();
    }

    public void shutdown() {
        tmfService.shutdown();
    }

    public Set getServices() {
        return tmfService.getServices();
    }

    private MessagingID getMessagingID(String serviceid, String username) {
        MessagingID mid = new MessagingID();
        String oql = "select mid from "+MessagingID.class.getName()+" mid where mid.serviceid='"+serviceid+
                    "' and mid.username='"+username+"'";
        try {
            mid = (MessagingID)pm.restore(oql);
        } catch (PersistenceManagerException e) {
            log.error("Error getting the messagingID for "+serviceid+" and "+username);
        }
        if (mid==null) mid = new MessagingID();
        return mid;
    }

    public String getServiceUserID(String serviceid, String username) {
        MessagingID mid = getMessagingID(serviceid, username);
        return mid.getServiceuserid();
    }

    public void setServiceUserID(String serviceid, String username, String serviceuserid) {
        MessagingID mid = getMessagingID(serviceid, username);
        mid.setUsername(username);
        mid.setServiceid(serviceid);
        mid.setServiceuserid(serviceuserid);
        try {
            pm.saveOrUpdate(mid);
        } catch (PersistenceManagerException e) {
            log.error("Could not save MessagingID for "+serviceid+" and "+username);
        }
    }

    public void addCommands(Set commands) {
        tmfService.addCommands(commands);
    }

    public TextMessagingSession getSession(String serviceid,String userid) {
        return tmfService.getTextMessagingSession(serviceid, userid);
    }
}
