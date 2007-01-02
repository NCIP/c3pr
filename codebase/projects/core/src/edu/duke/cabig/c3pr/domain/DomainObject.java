package edu.duke.cabig.c3pr.domain;


/**
 * Generic Interface for every domain object in C3PR. 
 * @author Priyatam
 */
public interface DomainObject {
	
	/**
     * @return the internal database identifier for this object
     */
    Integer getId();
    /**
     * Set the internal database identifier for this object.  In practice this should not be
     * called by application code -- just the persistence mechanism.
     * @param id
     */
    void setId(Integer id);
    
    /**
     * @return the optimistic locking version value for this object.
     */
    Integer getVersion();
    
    /**
     * Set the optimistic locking version value for this object.  In practice this should not be
     * called by application code -- just the persistence mechanism.
     * @param version
     */
    void setVersion(Integer version);
    
    /**
     * Set name for domain object
     * @return the domain object name
     */
    String getName();
    
    /**
     * Useful to instantiate a domain object by name 
     * @param name name of the domain object
     */
    void setName(String name);  
}
