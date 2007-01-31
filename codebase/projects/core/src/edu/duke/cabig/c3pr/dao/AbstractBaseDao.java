package edu.duke.cabig.c3pr.dao;

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
}
