package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.HealthcareSite;

/**
 * @author Ramakrishna
 * @version 1.0
 */
public class HealthcareSiteDao extends AbstractBaseDao<HealthcareSite> {
	
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


