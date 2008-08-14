package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;

import edu.duke.cabig.c3pr.domain.PlannedNotification;

/**
 * Hibernate implementation of ArmDao
 * 
 * @see edu.duke.cabig.c3pr.dao.PlannedNotificationDao
 * @author Priyatam
 */
public class PlannedNotificationDao extends GridIdentifiableDao<PlannedNotification> {

    @Override
    public Class<PlannedNotification> domainClass() {
        return PlannedNotification.class;
    }

    /*
     * Returns all Arm objects (non-Javadoc)
     * 
     * @see edu.duke.cabig.c3pr.dao.Arm#getAll()
     */
    public List<PlannedNotification> getAll() {
        return getHibernateTemplate().find("from PlannedNotification");
    }
    

    public PlannedNotification getInitializedPlannedNotificationById(int id, ApplicationContext applicationContext){
    	PlannedNotification plannedNotification = getById(id);
    	return plannedNotification;
    }
}
