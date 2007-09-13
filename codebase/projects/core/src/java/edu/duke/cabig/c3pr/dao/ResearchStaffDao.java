package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.dao.DataAccessResourceFailureException;

import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Hibernate implementation of StudySiteDao
 * @see edu.duke.cabig.c3pr.dao.StudySiteDao
 * @author Priyatam
 */
public class ResearchStaffDao extends GridIdentifiableDao<ResearchStaff> {
	
	private static Log log = LogFactory.getLog(InvestigatorDao.class);
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
	 	 
	 public List<ResearchStaff> searchByExample(ResearchStaff inv, boolean isWildCard) {
	        List<ResearchStaff> result = new ArrayList<ResearchStaff>();

	        Example example = Example.create(inv).excludeZeroes().ignoreCase();
	        try {
	            Criteria criteria = getSession().createCriteria(ResearchStaff.class);
	            criteria.addOrder(Order.asc("nciIdentifier"));
	            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	            
	            if (isWildCard)
	            {
	                example.enableLike(MatchMode.ANYWHERE);
	                criteria.add(example);
	                result =  criteria.list();
	            }else{
	            	result =  criteria.add(example).list();
	            }
	            
	        } catch (DataAccessResourceFailureException e) {
	            log.error(e.getMessage());
	        } catch (IllegalStateException e) {
	            e.printStackTrace(); 
	        } catch (HibernateException e) {
	            log.error(e.getMessage());
	        }
	        return result;
	    }
}
