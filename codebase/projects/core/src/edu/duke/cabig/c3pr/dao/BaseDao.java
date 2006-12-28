package edu.duke.cabig.c3pr.dao;

import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.domain.DomainObject;

/**
 * Base Dao Interface for all Daos in C3PR
 * 
 * @see edu.duke.cabig.c3pr.dao.AbstractBaseDao for a default implementation
 * @author Priyatam
 */
public interface BaseDao<T extends DomainObject> {
	
	 /**
	  * Get Object by Id (based on domain class)
	  * @param id identifieer
	  * @return domain object 
	  */
	 public T getById(int id); 
	 
	 /**
	  * Return the domain class reference in the dao
	  * @return
	  */
     public Class<T> domainClass();
     
     /**
      * Interface to save a domain object
      * @param domainObject the domain object
      * @throws DataAccessException runtime exception
      */
     public void save(DomainObject domainObject);    		

}
