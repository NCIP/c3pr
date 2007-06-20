package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.HealthcareSite;

/**
 * @author Vinay Gangoli
 * @version 1.0
 */
public class OrganizationDao extends GridIdentifiableDao<HealthcareSite> {
	
	public Class<HealthcareSite> domainClass() {
	        return HealthcareSite.class;
	 }
	
	/*
	 * Returns all Organizations objects
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.OrganizationDao#getAll()
	 */
	 public List<HealthcareSite> getAll() {
		 return getHibernateTemplate().find("from HealthcareSite");
	 }
	 
}


