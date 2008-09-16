package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;

/**
 * Hibernate implementation of ArmDao
 * 
 * @see edu.duke.cabig.c3pr.dao.RecipientScheduledNotificationDao
 * @author Priyatam
 */
public class RecipientScheduledNotificationDao extends GridIdentifiableDao<RecipientScheduledNotification> {

    @Override
    public Class<RecipientScheduledNotification> domainClass() {
        return RecipientScheduledNotification.class;
    }

    /*
     * Returns all Arm objects (non-Javadoc)
     * 
     * @see edu.duke.cabig.c3pr.dao.Arm#getAll()
     */
    public List<RecipientScheduledNotification> getAll() {
        return getHibernateTemplate().find("from RecipientScheduledNotification");
    }
    

    public RecipientScheduledNotification getInitializedRecipientScheduledNotificationById(int id){
    	RecipientScheduledNotification recipientScheduledNotification = getById(id);
    	return recipientScheduledNotification;
    }
}
