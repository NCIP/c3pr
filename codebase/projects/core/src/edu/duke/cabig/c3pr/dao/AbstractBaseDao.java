package edu.duke.cabig.c3pr.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.duke.cabig.c3pr.domain.DomainObject;
import edu.duke.cabig.c3pr.utils.GridIdentifierCreator;

/**
 * Abstract BaseDao implementing BaseDao. Provides convenient methods for
 * saving a base Dao
 *
 * @author Priyatam
 */
public abstract class AbstractBaseDao<T extends DomainObject> extends HibernateDaoSupport
	implements BaseDao{   
	
	//private GridIdentifierCreator gridIdentifierCreator;
	   
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
//		
//		String bigid = updateGridIdentifier(domainObject, domainObject.getClass().toString()
//    		+domainObject.getId().toString() );
//       	domainObject.setGridId(bigid);     
//       	getHibernateTemplate().saveOrUpdate(domainObject);			
//           	
	//	postProcessSave();
	}
	
//	private String updateGridIdentifier(DomainObject domainObject, String uniqueObjectId){
//    	String bigId = gridIdentifierCreator.getGridIdentifier(
//        domainObject.getClass()+domainObject.getId().toString());	
//        return bigId;           	
//    }
		
//    public GridIdentifierCreator getGridIdentifierCreator() {
//		return gridIdentifierCreator;
//	}
//
//	public void setGridIdentifierCreator(GridIdentifierCreator gridIdentifierCreator) {
//		this.gridIdentifierCreator = gridIdentifierCreator;
//	}

	/**
	 * To be implemented by subclasses for custom extension of save
	 */
	protected void postProcessSave(){
		//default is empty implementation
	}	
}
