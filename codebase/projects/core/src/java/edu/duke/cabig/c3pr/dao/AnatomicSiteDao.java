package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateTemplate;

import edu.duke.cabig.c3pr.domain.AnatomicSite;
import edu.duke.cabig.c3pr.domain.StudySubject;

// TODO: Auto-generated Javadoc
/*
 * @author kulasekaran
 * 
 */
/**
 * The Class AnatomicSiteDao.
 */
public class AnatomicSiteDao extends GridIdentifiableDao<AnatomicSite> {

    /** The Constant SUBSTRING_MATCH_PROPERTIES. */
    private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("name");

    /** The Constant EXACT_MATCH_PROPERTIES. */
    private static final List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    /** The Constant EXTRA_PARAMS. */
    private static final List<Object> EXTRA_PARAMS = Collections.emptyList();

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    @Override
    public Class<AnatomicSite> domainClass() {
        return AnatomicSite.class;
    }

    /**
     * Gets the anatomic sites by subnames.
     * 
     * @param subnames the subnames
     * 
     * @return the by subnames
     */
    public List<AnatomicSite> getBySubnames(String[] subnames) {
        return findBySubname(subnames, null, EXTRA_PARAMS, SUBSTRING_MATCH_PROPERTIES,
                        EXACT_MATCH_PROPERTIES, null);
    }
    
    
    /**
     * Search by example.
     * 
     * @param anatomicSite the anatomic site
     * 
     * @return the list< anatomic site>
     */
    public List<AnatomicSite> searchByExample(AnatomicSite anatomicSite){
    	Example example = Example.create(anatomicSite).excludeZeroes().ignoreCase();
    	Criteria anatomicSiteCriteria = getSession().createCriteria(AnatomicSite.class);
    	anatomicSiteCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    	return (List<AnatomicSite>)anatomicSiteCriteria.add(example).list();
    }
    
    public List<AnatomicSite> getAllOrderedByName(){
    	Criteria anatomicSiteCriteria =getSession().createCriteria(AnatomicSite.class);
    	anatomicSiteCriteria.addOrder(Order.asc("name"));
    	anatomicSiteCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    	return anatomicSiteCriteria.list();
    }
    
}
