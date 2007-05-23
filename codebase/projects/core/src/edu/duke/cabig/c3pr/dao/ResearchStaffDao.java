package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.List;

import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Hibernate implementation of StudySiteDao
 * @see edu.duke.cabig.c3pr.dao.StudySiteDao
 * @author Priyatam
 */
public class ResearchStaffDao extends AbstractBaseDao<ResearchStaff> {
	
	private static final List<String> SUBSTRING_MATCH_PROPERTIES	
		= Arrays.asList("firstName", "lastName");
	private static final List<String> EXACT_MATCH_PROPERTIES
		= Collections.emptyList();
	private static final List<Object> EXTRA_PARAMS
		= Collections.emptyList();
		
	@Override
	public Class<ResearchStaff> domainClass() {
		return ResearchStaff.class;
	 }
	
	/*
	 * Returns all ResearchStaff objects
	 */
	 public List<ResearchStaff> getAll() {
		 return getHibernateTemplate().find("from ResearchStaff");
	 }
	 
	 public List<ResearchStaff> getBySubnames(String[] subnames, int healthcareSite) {
	        return findBySubname(subnames,"o.healthcareSite.id = '"+healthcareSite+"'",EXTRA_PARAMS,
	            SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
	 }	
	 	 
}
