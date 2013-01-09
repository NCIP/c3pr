/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;

import edu.duke.cabig.c3pr.constants.ICD9DiseaseSiteCodeDepth;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.nwu.bioinformatics.commons.CollectionUtils;

// TODO: Auto-generated Javadoc
/*
 * @author kulasekaran
 * 
 */
/**
 * The Class AnatomicSiteDao.
 */
public class ICD9DiseaseSiteDao extends GridIdentifiableDao<ICD9DiseaseSite> {

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
    public Class<ICD9DiseaseSite> domainClass() {
        return ICD9DiseaseSite.class;
    }

    /**
     * Gets the anatomic sites by subnames.
     * 
     * @param subnames the subnames
     * 
     * @return the by subnames
     */
    public List<ICD9DiseaseSite> getBySubnames(String[] subnames) {
        return findBySubname(subnames, null, EXTRA_PARAMS, SUBSTRING_MATCH_PROPERTIES,
                        EXACT_MATCH_PROPERTIES);
    }
    
    /**
     * get by name
     * 
     * @param name the name
     * 
     * @return the list< icd9 disease sites>
     */
    public List<ICD9DiseaseSite> getByName(String name){
    	return (List<ICD9DiseaseSite>)(getHibernateTemplate().find("from ICD9DiseaseSite where name=?",new Object[]{name}));
    }
    
    /**
     * get selectables 
     * 
     *  * @return the list< icd9 disease sites>
     */
    public List<ICD9DiseaseSite> getSelectableSites(){
    	return (List<ICD9DiseaseSite>)(getHibernateTemplate().find("from ICD9DiseaseSite where selectable='1'"));
    }
    
    /**
     * get non selectables 
     * 
     *  * @return the list< icd9 disease sites>
     */
    public List<ICD9DiseaseSite> getNonSelectableSites(){
    	return (List<ICD9DiseaseSite>)(getHibernateTemplate().find("from ICD9DiseaseSite where selectable='0'"));
    }
    
    /**
     * get by level
     * 
     * @param level the level
     *      * 
     * @return the list< icd9 disease sites>
     */
    public List<ICD9DiseaseSite> getByLevel(ICD9DiseaseSiteCodeDepth level){
    	return (List<ICD9DiseaseSite>)(getHibernateTemplate().find("from ICD9DiseaseSite where level=?",new Object[]{level}));
    }
    
    
    /**
     * get by code
     * 
     * @param code the code
     * 
     * @return the list< icd9 disease sites>
     */
    public ICD9DiseaseSite getByCode(String code){
    	return CollectionUtils.firstElement((List<ICD9DiseaseSite>)(getHibernateTemplate().find("from ICD9DiseaseSite where code=?",new Object[]{code})));
    }
    
    public List<ICD9DiseaseSite> getAllOrderedByName(){
    	Criteria icdDiseaseSiteCriteria =getSession().createCriteria(ICD9DiseaseSite.class);
    	icdDiseaseSiteCriteria.addOrder(Order.asc("name"));
    	icdDiseaseSiteCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    	return icdDiseaseSiteCriteria.list();
    }
    
}
