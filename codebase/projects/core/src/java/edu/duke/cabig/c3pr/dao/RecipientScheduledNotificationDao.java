package edu.duke.cabig.c3pr.dao;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;

/**
 * Hibernate implementation of RecipientScheduledNotificationDao
 * 
 * @see edu.duke.cabig.c3pr.dao.RecipientScheduledNotificationDao
 * @author Vinay Gangoli
 */
public class RecipientScheduledNotificationDao extends GridIdentifiableDao<RecipientScheduledNotification> {

    @Override
    public Class<RecipientScheduledNotification> domainClass() {
        return RecipientScheduledNotification.class;
    }

    /**
     * Save or update.
     * 
     * @param recipientScheduledNotification the recipient scheduled notification
     */
<<<<<<< .mine
=======
    // For Oracle test-build, the readOnly has been changed from true to false;
    @Transactional(readOnly=false)
    public List<RecipientScheduledNotification> getAll() {
        return getHibernateTemplate().find("from RecipientScheduledNotification");
    }
    
    //readOnly was chnaged from true to false to get notifications to work on ORacle 
>>>>>>> .r7323
    @Transactional(readOnly=false)
    public void saveOrUpdate(RecipientScheduledNotification recipientScheduledNotification){
    	
    	getHibernateTemplate().saveOrUpdate(recipientScheduledNotification);
    	
    	//Do not remove the flush...imperative for the notifications flow.
    	getHibernateTemplate().flush();
    }
}
