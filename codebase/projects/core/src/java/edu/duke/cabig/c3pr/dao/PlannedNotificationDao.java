package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

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
    

    public PlannedNotification getInitializedPlannedNotificationById(int id){
    	PlannedNotification plannedNotification = getById(id);
    	return plannedNotification;
    }
    
    public void reassociate(PlannedNotification plannedNotification){
    	getHibernateTemplate().update(plannedNotification);
    }
    
    @Transactional(readOnly=false)
    public void saveOrUpdate(PlannedNotification plannedNotification){
    	//do not remove the flush...imperative for the notifications flow.
    	getHibernateTemplate().saveOrUpdate(plannedNotification);
    	getHibernateTemplate().flush();
    }
}
