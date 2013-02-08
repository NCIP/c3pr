/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.List;

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
    
    public List<ScheduledNotification> getByEventId(String eventId){
    	return getHibernateTemplate().find("from ScheduledNotification where eventId = ?",eventId);
    }
    
}
