package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.PlannedNotification;
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
    @Transactional(readOnly=true)
    public List<RecipientScheduledNotification> getAll() {
        return getHibernateTemplate().find("from RecipientScheduledNotification");
    }
    
    //readOnly was chnaged from true to false to get notifications to work on ORacle 
    @Transactional(readOnly=false)
    public RecipientScheduledNotification getInitializedRecipientScheduledNotificationById(int id){
    	RecipientScheduledNotification recipientScheduledNotification = getById(id);
    	return recipientScheduledNotification;
    }
    
    @Transactional(readOnly=false)
    public void saveOrUpdate(RecipientScheduledNotification recipientScheduledNotification){
    	//do not remove the flush...imperative for the notifications flow.
    	getHibernateTemplate().saveOrUpdate(recipientScheduledNotification);
    	getHibernateTemplate().flush();
    }
}
