package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.ScheduledNotification;

/**
 * Hibernate implementation of ScheduledNotificationDao.
 * 
 * @author Vinay Gangoli
 */
public class ScheduledNotificationDao extends GridIdentifiableDao<ScheduledNotification> {

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    @Override
    public Class<ScheduledNotification> domainClass() {
        return ScheduledNotification.class;
    }
    
}
