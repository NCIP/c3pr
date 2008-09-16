package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.ScheduledNotification;

/**
 * Hibernate implementation of ArmDao
 * 
 * @see edu.duke.cabig.c3pr.dao.ScheduledNotificationDao
 * @author Priyatam
 */
public class ScheduledNotificationDao extends GridIdentifiableDao<ScheduledNotification> {

    @Override
    public Class<ScheduledNotification> domainClass() {
        return ScheduledNotification.class;
    }

    /*
     * Returns all Arm objects (non-Javadoc)
     * 
     * @see edu.duke.cabig.c3pr.dao.Arm#getAll()
     */
    public List<ScheduledNotification> getAll() {
        return getHibernateTemplate().find("from ScheduledNotification");
    }
    

    public ScheduledNotification getInitializedScheduledNotificationById(int id){
    	ScheduledNotification scheduledNotification = getById(id);
    	return scheduledNotification;
    }
    
    public void reassociate(ScheduledNotification scheduledNotification){
    	getHibernateTemplate().update(scheduledNotification);
    }
}
