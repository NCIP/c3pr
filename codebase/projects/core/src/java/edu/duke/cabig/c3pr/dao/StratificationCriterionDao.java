package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.StratificationCriterion;

/**
 * Hibernate implementation of StudySiteDao
 * @see edu.duke.cabig.c3pr.dao.StudySiteDao
 * @author Priyatam
 */
public class StratificationCriterionDao extends GridIdentifiableDao<StratificationCriterion> {
		
	@Override
	public Class<StratificationCriterion> domainClass() {
		return StratificationCriterion.class;
	 }
	
	/*
	 * Returns all StratificationCriterion objects
	 */
	 public List<StratificationCriterion> getAll() {
		 return getHibernateTemplate().find("from StratificationCriterion");
	 }
	 	 	

}
