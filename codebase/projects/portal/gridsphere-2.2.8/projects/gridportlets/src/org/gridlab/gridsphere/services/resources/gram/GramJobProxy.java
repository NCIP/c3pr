package org.gridlab.gridsphere.services.resources.gram;

import org.globus.gram.GramJobListener;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.services.util.GridPortletsDatabase;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GramJobProxy.java,v 1.1.1.1 2007-02-01 20:41:07 kherm Exp $
 */
public class GramJobProxy implements GramJobListener {

    private static PortletLog log = SportletLog.getInstance(GramJobProxy.class);
    private GridPortletsDatabase pm = null;
    private String gramJobOid = null;

    public GramJobProxy(String gramJobOid) {
        log.debug("Creating new monitor instance");
        pm = GridPortletsDatabase.getInstance();
        this.gramJobOid = gramJobOid;
    }

    private GramJob getGramJob(String oid) {
        GramJob job = null;
        try {
            job = (GramJob) pm.restore("from " + GramJob.class.getName()
                                  + " as j where j.oid='" + oid + "'");
        } catch (PersistenceManagerException e) {
            log.error("FATAL: could not retrieve job by oid " + oid + "!\n" + e.getMessage(), e);
        }
        return job;
    }

    public void statusChanged(org.globus.gram.GramJob globusJob) {
        GramJob gramJob = getGramJob(gramJobOid);

        if (gramJob == null) {
            log.warn("Gram job " + gramJobOid + " has been deleted");
            return;
        }

        gramJob.statusChanged(globusJob);
    }
}
