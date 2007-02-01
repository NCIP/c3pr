/*
 * @version: $Id: RequestServiceImpl.java 4810 2006-05-18 17:53:08Z novotny $
 */
package org.gridlab.gridsphere.services.core.request.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.request.GenericRequest;
import org.gridlab.gridsphere.services.core.request.RequestService;

import java.util.*;

public class RequestServiceImpl implements RequestService, PortletServiceProvider {

    private static final long REQUEST_SWEEP_FREQUENCY =  5000 * 60; // 5 minute intervals

    private PersistenceManagerRdbms pm = null;
    private static PortletLog log = SportletLog.getInstance(RequestServiceImpl.class);
    private Timer timer = null;

    private class RequestSweeperTask extends TimerTask {

        public void run() {
            clearExpiredEntries();
        }
    }

    public void init(PortletServiceConfig config) {
        if (pm == null) pm = PersistenceManagerFactory.createGridSphereRdbms();
        timer = new Timer(true);
        timer.schedule(new RequestSweeperTask(),  Calendar.getInstance().getTime(), REQUEST_SWEEP_FREQUENCY );
    }

    public void destroy() {
        timer.cancel();
        timer = null;
    }

    public GenericRequest getRequest(String requestId, String label) {
        GenericRequest request = null;
        String query = "select gsreq from "
                + GenericRequest.class.getName()
                + " gsreq where gsreq.oid='" + requestId + "' and gsreq.label='"+ label + "'";
        try {
            request = (GenericRequest) this.pm.restore(query);
        } catch (PersistenceManagerException e) {
            log.error("Unable to retrieve request: " + requestId, e);
        }
        return request;
    }

    public GenericRequest createRequest(String label) {
        GenericRequest request = new GenericRequest();
        request.setLabel(label);
        saveRequest(request);
        return request;
    }

    public void saveRequest(GenericRequest request) {
        try {
            if (request.getOid() != null) {
                pm.update(request);
            } else {
                pm.create(request);
            }
        } catch (PersistenceManagerException e) {
            log.error("Unable to create or update password for user", e);
        }
    }

    protected void clearExpiredEntries() {
        //log.debug("Updating generic requests");
        Date date = Calendar.getInstance().getTime();
        List reqs = getAllRequests();
        Iterator it = reqs.iterator();
        while (it.hasNext()) {
            GenericRequest req = (GenericRequest)it.next();
            if (req.getLifetime() == null) {
                log.debug("deleting request with no lifetime specified " + req.getOid());
                deleteRequest(req);
            } else if (date.compareTo(req.getLifetime()) >= 0) {
                log.debug("deleting request " + req.getOid());
                deleteRequest(req);
            }
        }
    }

    public void deleteRequest(GenericRequest request) {
        try {
            pm.delete(request);
        } catch (PersistenceManagerException e) {
            log.error("Unable to delete request: " + request.getOid(), e);
        }

    }

    public List getAllRequests() {
        String oql = "select gsreq from "
                + GenericRequest.class.getName()
                + " gsreq ";
        try {
            return pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving requests";
            log.error(msg, e);
            return new Vector();
        }

    }
}