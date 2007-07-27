package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;

/**
 * Hibernate implementation of StudySiteDao
 * @see edu.duke.cabig.c3pr.dao.StudySiteDao
 * @author Priyatam
 */
public class StratificationCriterionAnswerDao extends GridIdentifiableDao<StratificationCriterionPermissibleAnswer> {
		
	@Override
	public Class<StratificationCriterionPermissibleAnswer> domainClass() {
		return StratificationCriterionPermissibleAnswer.class;
	 }
	
	/*
	 * Returns all StratificationCriterion objects
	 */
	 public List<StratificationCriterionPermissibleAnswer> getAll() {
		 return getHibernateTemplate().find("from StratificationCriterionPermissibleAnswer");
	 }
	 	 	

}
