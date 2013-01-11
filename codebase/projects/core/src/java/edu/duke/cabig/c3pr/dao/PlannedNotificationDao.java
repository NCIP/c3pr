/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

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
