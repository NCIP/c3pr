package edu.duke.cabig.c3pr.dao.hibernate;

import java.util.List;

import edu.duke.cabig.c3pr.dao.AbstractBaseDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;

/**
 * @author Ramakrishna
 * @version 1.0
 */
public class HealthcareSiteDaoHibernate extends AbstractBaseDao<HealthcareSite> implements HealthcareSiteDao {
	
	public Class<HealthcareSite> domainClass() {
	        return HealthcareSite.class;
	 }
	
	/*
	 * Returns all HealthcarSite objects
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.HealthcareSiteDao#getAll()
	 */
	 public List<HealthcareSite> getAll() {
		 return getHibernateTemplate().find("from HealthcareSite");
	 }
	 
}


