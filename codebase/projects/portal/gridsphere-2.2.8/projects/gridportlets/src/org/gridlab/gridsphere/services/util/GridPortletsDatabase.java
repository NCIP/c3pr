/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridPortletsDatabase.java,v 1.1.1.1 2007-02-01 20:42:23 kherm Exp $
 */
package org.gridlab.gridsphere.services.util;

import org.gridlab.gridsphere.core.persistence.*;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;
import java.util.Iterator;

public class GridPortletsDatabase  {

    private static PortletLog log = SportletLog.getInstance(GridPortletsDatabase.class);

    public static final String DATABASE_NAME = "gridportlets";
    private static GridPortletsDatabase INSTANCE = null;
    private PersistenceManagerRdbms database = null;
    private Session session = null;
    private static final Integer lock = new Integer("1");

    private GridPortletsDatabase() {
        database = PersistenceManagerFactory.createPersistenceManagerRdbms("gridportlets");
        try {
            session = database.getSession();
        } catch (PersistenceManagerException e) {
            log.error("Unable to open gridportlets database session", e);
        }
    }

    public static GridPortletsDatabase getInstance() {
        synchronized(lock) {
            if (INSTANCE == null) {
                INSTANCE = new GridPortletsDatabase();
            }
            return INSTANCE;
        }
    }

    public void destroy() throws PersistenceManagerException {
        synchronized(lock) {
            session.close();
            database.destroy();
        }
    }

    public void resetDatabase(String connURL) {
        synchronized (lock) {
            try {
                session.close();
                database.resetDatabase(connURL);
                session = database.getSession();
            } catch (PersistenceManagerException e) {
                log.error("Error resetting database", e);
            }
        }
    }

    public Session getSession() throws PersistenceManagerException {
        synchronized (lock) {
            return session;
        }
    }

    public Object restore(String query) throws PersistenceManagerException {
        synchronized (lock) {
//            return database.restore(query);
            Transaction tx = session.beginTransaction();
            //Session session = database.getSession();
            Object object = session.restore(query);
            tx.commit();
            //database.closeSession();
            return object;
        }
    }

    public List restoreList(String query) throws PersistenceManagerException {
        synchronized (lock) {
//            return database.restoreList(query);
            Transaction tx = session.beginTransaction();
            //Session session = database.getSession();
            List object = session.restoreList(query);
            tx.commit();
            //database.closeSession();
            return object;
        }
    }

    /*
    public List restoreList(String query, QueryFilter queryFilter) throws PersistenceManagerException {
	synchronized (lock) {
            Transaction tx = session.beginTransaction();
            //Session session = database.getSession();
            List object = session.restoreList(query, queryFilter);
            tx.commit();
            //database.closeSession();
            return object;
        }
    }
 */

    public void create(Object object) throws PersistenceManagerException {
        synchronized (lock) {
//            database.create(object);
            Transaction tx = session.beginTransaction();
            //Session session = database.getSession();
            session.create(object);
            tx.commit();
            //database.closeSession();
        }
    }

    public void saveOrUpdate(Object object) throws PersistenceManagerException {
        synchronized (lock) {
//            database.saveOrUpdate(object);
            Transaction tx = session.beginTransaction();
            //Session session = database.getSession();
            session.createOrUpdate(object);
            tx.commit();
            //database.closeSession();
        }
    }

	public void update(Object object) throws PersistenceManagerException {
        synchronized (lock) {
//            database.update(object);
            Transaction tx = session.beginTransaction();
            //Session session = database.getSession();
            session.update(object);
            tx.commit();
            //database.closeSession();
        }
    }

	public void delete(Object object) throws PersistenceManagerException {
        synchronized (lock) {
//            database.delete(object);
            Transaction tx = session.beginTransaction();
            //Session session = database.getSession();
            session.delete(object);
            tx.commit();
            //database.closeSession();
        }
    }

    public void deleteList(String query) throws PersistenceManagerException {
        synchronized (lock) {
//            return database.restoreList(query);
            Transaction tx = session.beginTransaction();
            //Session session = database.getSession();
            List object = session.restoreList(query);
            for (Iterator iterator = object.iterator(); iterator.hasNext();) {
                Object o = iterator.next();
                session.delete(o);
            }
            tx.commit();
            //database.closeSession();
        }
//        synchronized (lock) {
////            database.deleteList(query);
//            Transaction tx = session.beginTransaction();
//            //Session session = database.getSession();
//            session.delete("delete " + query);
//            tx.commit();
//            //database.closeSession();
//        }
    }

    public void closeSession() throws PersistenceManagerException {
        synchronized (lock) {
            //database.closeSession();
            session.close();
        }
    }

    public void beginTransaction() throws PersistenceManagerException {
        synchronized (lock) {
            database.beginTransaction();
        }
    }

    public void commitTransaction() throws PersistenceManagerException {
        synchronized (lock) {
            database.commitTransaction();
        }
    }

    public void rollbackTransaction() throws PersistenceManagerException {
        synchronized (lock) {
            database.rollbackTransaction();
        }
    }
}
