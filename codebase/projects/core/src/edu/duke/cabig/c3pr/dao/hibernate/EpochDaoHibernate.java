package edu.duke.cabig.c3pr.dao.hibernate;

import java.util.List;

import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.dao.AbstractBaseDao;
import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.domain.Epoch;

/**
 * Hibernate implementation of EpochDao
 * @see edu.duke.cabig.c3pr.dao.EpochDao
 * @author Priyatam
 */
public class EpochDaoHibernate extends AbstractBaseDao<Epoch> implements EpochDao{

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
