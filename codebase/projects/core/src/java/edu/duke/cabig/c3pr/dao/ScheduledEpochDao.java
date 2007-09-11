package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.hibernate.LockMode;

import edu.duke.cabig.c3pr.domain.ScheduledEpoch;

/**
 * Hibernate implementation of EpochDao
 * @see edu.duke.cabig.c3pr.dao.ScheduledEpochDao
 * @author Priyatam
 */
public class ScheduledEpochDao extends GridIdentifiableDao<ScheduledEpoch> {

	@Override
	public Class<ScheduledEpoch> domainClass() {
		return ScheduledEpoch.class;
	 }
	
	/*
	 * Returns all Epoch objects
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.Epoch#getAll()
	 */
	 public List<ScheduledEpoch> getAll(){
		 return getHibernateTemplate().find("from ScheduledEpoch");
	 }
	 
	 public void reassociate(ScheduledEpoch sch) {
        getHibernateTemplate().lock(sch,LockMode.NONE);
     }
}
