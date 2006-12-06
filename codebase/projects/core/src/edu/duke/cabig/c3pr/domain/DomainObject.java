package edu.duke.cabig.c3pr.domain;

import java.util.Comparator;

/**
 * @author Priyatam
 */
public interface DomainObject {
    public Integer getId();
    public void setId(Integer id);

    class ById<T extends DomainObject> implements Comparator<T> {
        public int compare(T o1, T o2) {
            //return ComparisonUtils.nullSafeCompare(o1.getId(), o2.getId());
        	//TODO remove
        	return 1;
        }
    }
}
