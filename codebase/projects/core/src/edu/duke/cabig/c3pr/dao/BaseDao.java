/**
 * 
 */
package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.DomainObject;

/**
 * @author Priyatam
 *
 */
public interface BaseDao<T extends DomainObject> {
	
	 public T getById(int id); 
	 
     public Class<T> domainClass();

}
