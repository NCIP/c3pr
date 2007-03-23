package edu.duke.cabig.c3pr.dao;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.duke.cabig.c3pr.domain.DomainObject;
import edu.nwu.bioinformatics.commons.CollectionUtils;

/**
 * Abstract BaseDao implementing BaseDao. Provides convenient methods for
 * saving a base Dao
 *
 * @author Priyatam
 */
public abstract class AbstractBaseDao<T extends DomainObject> extends HibernateDaoSupport
	implements BaseDao{   
	
	private static Log log = LogFactory.getLog(HealthcareSiteInvestigatorDao.class);
	
		  
	/**
	 * Get Object by Id (based on domain class)
	 * @param id object id
	 * @return loaded persistent object
	 */
	public T getById(int id) {
        return (T) getHibernateTemplate().get(domainClass(), id);
    }
	
	/**
	 * Get Object by Id (based on domain class) with eager fetch of a single
	 * association
	 * @param id object id
	 * @param isEager true if eager fetch is needed
	 * @param associationPath the association 'name' needed to be loaded
	 * @return loaded persistent object
	 */
	public T getById(int id, boolean isEager, String associationPath) {
		if (isEager){
			Criterion idCr = Restrictions.eq("id", id);
			Criteria criteria = getSession().createCriteria(domainClass())
				.setFetchMode(associationPath, FetchMode.JOIN).add(idCr);
			return (T) criteria.list().get(0);
		}
	    return getById(id);
    }
	 
	/* Must override in subclass to return the domain class reference in the dao
	  */
    public abstract Class<T> domainClass();
  
	/*
     * Saves a domain object
      */
	public final void save(DomainObject domainObject) {
		getHibernateTemplate().saveOrUpdate(domainObject);	 	
		postProcessSave();
	}	

	/**
	 * To be implemented by subclasses for custom extension of save
	 */
	protected void postProcessSave(){
		//default is empty implementation
	}
        
    @SuppressWarnings("unchecked")
    public T getByGridId(T template) {
        return (T) CollectionUtils.firstElement(getHibernateTemplate().findByExample(template));
    }
	 
    public T getByGridId(String gridId) {
        StringBuilder query = new StringBuilder("from ")
          .append(domainClass().getName()).append(" o where gridId = ?");
        Object[] params = {gridId};
        return (T) CollectionUtils.firstElement(getHibernateTemplate().find(query.toString(), params));
    }
    
    /**
     * A variation of {@link #findBySubname} that does not allow for extra conditions
     */
    protected List<T> findBySubname(
        String[] subnames, List<String> substringMatchProperties, List<String> exactMatchProperties
    ) {
        return findBySubname(subnames, null, null, substringMatchProperties, exactMatchProperties);
    }

    /**
     * A query builder for use by subclass DAOs.  It makes it easy to match a fragment of a name
     * or identifier against multiple properties.  This is intended for use in implementing
     * user-friendly dynamic searches; e.g., autocompleters.
     *
     * @param subnames the name fragments to search on
     * @param extraConditions custom HQL conditions with which to constrain the fragment matches
     * @param extraParameters parameters for the custom conditions
     * @param substringMatchProperties a list of properties of the implementing object which should
     *          be matched as case-insensitive substrings
     * @param exactMatchProperties a list of properties which should be matched as case-insensitive
     *          full strings
     * @return a list of matching domain object instances
     */
    @SuppressWarnings("unchecked")
    protected List<T> findBySubname(
        String[] subnames, String extraConditions, List<Object> extraParameters,
        List<String> substringMatchProperties, List<String> exactMatchProperties
    ) {
        StringBuilder query = new StringBuilder("from ")
            .append(domainClass().getName()).append(" o where ");
        if (extraConditions != null) query.append(extraConditions).append(" and ");
        List<Object> params = new LinkedList<Object>();
        if (extraParameters != null) params.addAll(extraParameters);

        for (int i = 0; i < subnames.length; i++) {
            buildSubnameQuery(subnames[i], query, params,
                substringMatchProperties, exactMatchProperties);
            if (i < subnames.length - 1) query.append(" and ");
        }
                
        log.debug("query string = " +query);        
        return getHibernateTemplate().find(query.toString(), params.toArray());
    }

    protected void buildSubnameQuery(
        String subname, StringBuilder query, List<Object> params,
        List<String> substringMatchProperties, List<String> exactMatchProperties
    ) {
        query.append('(');
        if (hasAny(substringMatchProperties)) {
            for (Iterator<String> it = substringMatchProperties.iterator(); it.hasNext();) {
                String prop = it.next();
                query.append("LOWER(o.").append(prop).append(") LIKE ?");
                params.add('%' + subname.toLowerCase() + '%');
                if (it.hasNext()) query.append(" or ");
            }
            if (hasAny(exactMatchProperties)) {
                query.append(" or ");
            }
        }
        if (hasAny(exactMatchProperties)) {
            for (Iterator<String> it = exactMatchProperties.iterator(); it.hasNext();) {
                String prop = it.next();
                query.append("LOWER(o.").append(prop).append(") = ?");
                params.add(subname.toLowerCase());
                if (it.hasNext()) query.append(" or ");
            }
        }
        query.append(')');
    }

    private boolean hasAny(List<String> properties) {
        return properties != null && properties.size() > 0;
    }

}