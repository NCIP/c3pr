/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: SnipStorageServiceImpl.java,v 1.1.1.1 2007-02-01 20:08:39 kherm Exp $
 */
package org.gridlab.gridsphere.extras.services.wiki.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.extras.services.wiki.SnipStorageService;
import org.gridlab.gridsphere.extras.services.wiki.Snip;

import java.util.List;
import java.util.ArrayList;

/**
 *
 */
public class SnipStorageServiceImpl implements SnipStorageService, PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(SnipStorageServiceImpl.class);

    PersistenceManagerRdbms pm = null;


    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        this.pm = PersistenceManagerFactory.createPersistenceManagerRdbms("extras");
    }

    public void destroy() {
        try {
            pm.destroy();
        } catch (PersistenceManagerException e) {
            log.info("Problems shutting down WikiService.");
        }
    }


    public Snip getSnipByOid(String Oid) {
        Snip result = new Snip();
        try {
            result = (Snip) pm.restore("from " + Snip.class.getName() + " as note where note.oid='" + Oid + "'");
        } catch (PersistenceManagerException e) {
            log.error("Could not retrieve note by oid "+Oid+" :"+e);
        }
        if (result==null) result = new Snip();
        return result;
    }

    public boolean snipExists(String name) {
        Snip result = null;
        try {
            result = (Snip) pm.restore("from " + Snip.class.getName() + " as snip where snip.name='" + name + "'");
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
        return (result != null);
    }

    public String createSnip(String username, String name, String text) {
        if (!snipExists(name)) {
            Snip snip = new Snip();
            snip.setName(name);
            snip.setContent(text);
            snip.setUserid(username);
            try {
                pm.create(snip);
                return "";
            } catch (PersistenceManagerException e) {
                e.printStackTrace();
                return "NOTEPAD_DBERROR";
            }
        }
        return "NOTEPAD_NOTEEXISTS";
    }

    public void deleteSnip(Snip snip) {
        try {
            pm.delete(snip);
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
    }

    public void deleteSnip(String snipname) {
        Snip snip = getSnip(snipname);
        deleteSnip(snip);
    }

    public List getSnips(String username) {
        List result = new ArrayList();
        try {
            String oql = "from " + Snip.class.getName() + " as note where note.Userid='" + username + "'";
            log.debug("Query with " + oql);
            result = pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List searchSnips(String searchstring) {
        List result = new ArrayList();
        try {
            String oql =
                    "from " + Snip.class.getName() + " as note where (note.content like '%" + searchstring + "%' or note.name like '%" + searchstring + "%')";
            log.debug("Query with " + oql);
            result = pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String update(Snip snip) {
        try {
            pm.update(snip);
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
            return "NOTEPAD_DBERROR";
        }
        return "";
    }

    public Snip getSnip(String snipname) {
        System.out.println("Trying to get Snip with name "+snipname);
        Snip result = new Snip();
        try {
            result = (Snip) pm.restore("from " + Snip.class.getName() + " as note where note.name='" + snipname + "'");
        } catch (PersistenceManagerException e) {
            System.out.println("Getting snip "+snipname+" failed.");
        }

        if (result==null)  result = new Snip();


        return result;
    }
}
