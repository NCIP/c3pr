package edu.duke.cabig.c3pr.dao;

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
		  
	/*
	 * Get Object by Id (based on domain class)
	 * @see edu.duke.cabig.c3pr.dao.BaseDao#getById(int)
	 */
	public T getById(int id) {
        return (T) getHibernateTemplate().get(domainClass(), id);
    }
	 
	/* Must override in subclass to return the domain class reference in the dao
	 * @see edu.duke.cabig.c3pr.dao.BaseDao#domainClass()
	 */
    public abstract Class<T> domainClass();
  
	/*
     * Saves a domain object
     * @param domainObject the domain object to save
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
