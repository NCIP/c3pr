package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Epoch;

/**
 * Hibernate implementation of EpochDao
 * @see edu.duke.cabig.c3pr.dao.EpochDao
 * @author Priyatam
 */
public class EpochDao extends AbstractBaseDao<Epoch> {

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
}
