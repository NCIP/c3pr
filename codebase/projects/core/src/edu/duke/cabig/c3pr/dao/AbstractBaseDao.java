package edu.duke.cabig.c3pr.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.duke.cabig.c3pr.domain.DomainObject;
/**
 * Abstract BaseDao implementing BaseDao. Provides convenient methods for
 * saving a domain object [ save() ] and getById() methods.Override domainClass()
 * by providing your implemented domain object class for the corresponding dao
 *
 * @author Priyatam
 */
public abstract class AbstractBaseDao<T extends DomainObject> extends HibernateDaoSupport implements BaseDao{
    
	/*
	 * Get Object by Id (based on domain class)
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.BaseDao#getById(int)
	 */
	public T getById(int id) {
        return (T) getHibernateTemplate().get(domainClass(), id);
    }
	 
	/* Must override in subclass to return the domain class reference in the dao
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.BaseDao#domainClass()
	 */
    public abstract Class<T> domainClass();
  
    /*
     * Saves a domain object
     * @param domainObject the domain object to save
     */
	public void save(DomainObject domainObject) {
		 getHibernateTemplate().saveOrUpdate(domainObject);		
	}
}
