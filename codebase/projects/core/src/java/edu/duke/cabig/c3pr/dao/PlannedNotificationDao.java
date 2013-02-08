/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.domain.PlannedNotification;

/**
 * Hibernate implementation of PlannedNotificationDao
 * 
 * @see edu.duke.cabig.c3pr.dao.PlannedNotificationDao
 * @author Vinay Gangoli
 */
public class PlannedNotificationDao extends GridIdentifiableDao<PlannedNotification> {

    @Override
    public Class<PlannedNotification> domainClass() {
        return PlannedNotification.class;
    }


    /**
     * Gets all the PlannedNotifications.
     * 
     * @return the list of PlannedNotifications
     */
    public List<PlannedNotification> getAll() {
        return getHibernateTemplate().find("from PlannedNotification");
    }
    
    /**
     * Gets all the PlannedNotifications.
     * 
     * @return the list of PlannedNotifications
     */
    public PlannedNotification getByEventName(NotificationEventTypeEnum event) {
    	
    	List<PlannedNotification> plannedNotifications =  (List<PlannedNotification>)getHibernateTemplate().find
		("from PlannedNotification p left join fetch p.userBasedRecipientInternal where p.eventName = ?"  , new Object[]{event});
		return (plannedNotifications.size()>0? plannedNotifications.get(0):null);
    }
    
    /**
	 * Save or update.
	 * 
	 * @param plannedNotification the planned notification
	 */
	@Transactional(readOnly=false)
    public void saveOrUpdate(PlannedNotification plannedNotification){
    	//do not remove the flush...imperative for the notifications flow.
    	getHibernateTemplate().saveOrUpdate(plannedNotification);
    	getHibernateTemplate().flush();
    }
    
}
