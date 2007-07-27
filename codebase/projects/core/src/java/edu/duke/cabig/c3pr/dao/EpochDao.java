package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.hibernate.LockMode;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * Hibernate implementation of EpochDao
 * @see edu.duke.cabig.c3pr.dao.EpochDao
 * @author Priyatam
 */
public class EpochDao extends GridIdentifiableDao<Epoch> {

	@Override
	public Class<Epoch> domainClass() {
		return Epoch.class;
	 }
	
	/*
	 * Returns all Epoch objects
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.Epoch#getAll()
	 */
	 public List<Epoch> getAll(){
		 return getHibernateTemplate().find("from Epoch");
	 }
		public void reassociate(Epoch epoch) {
	        getHibernateTemplate().lock(epoch,LockMode.NONE);
	     }

}
