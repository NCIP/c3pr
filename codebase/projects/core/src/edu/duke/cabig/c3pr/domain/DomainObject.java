package edu.duke.cabig.c3pr.domain;

import java.util.Comparator;
import edu.nwu.bioinformatics.commons.ComparisonUtils;

/**
 * Generic Interface for every domain object in C3PR. 
 * @author Priyatam
 */
public interface DomainObject {
    Integer getId();
    Integer getVersion();
    
    void setId(Integer id);
    void setVersion(Integer version);
    
    class ById<T extends DomainObject> implements Comparator<T> {
    	 public int compare(T o1, T o2) {
             return ComparisonUtils.nullSafeCompare(o1.getId(), o2.getId());
         }
    }
}
