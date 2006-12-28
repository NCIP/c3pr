package edu.duke.cabig.c3pr.dao.hibernate;

import java.util.List;

import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.dao.AbstractBaseDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.StudySite;

/**
 * Hibernate implementation of StudySiteDao
 * @see edu.duke.cabig.c3pr.dao.StudySiteDao
 * @author Priyatam
 */
public class StudySiteDaoHibernate extends AbstractBaseDao<StudySite> implements StudySiteDao{

	@Override
	public Class<StudySite> domainClass() {
		return StudySite.class;
	 }
	
	/*
	 * Returns all StudySite objects
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.StudySite#getAll()
	 */
	 public List<StudySite> getAll() {
		 return getHibernateTemplate().find("from StudySite");
	 }
}
