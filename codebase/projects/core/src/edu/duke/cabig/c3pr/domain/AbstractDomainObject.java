package edu.duke.cabig.c3pr.domain;


/**
 * @author Priyatam
 */
public abstract class AbstractDomainObject implements DomainObject {
    private Integer id;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static <T extends DomainObject> boolean equalById(T t1, T t2) {
        if (t1 == t2) return true;
        if (t1 == null) {
            // t2 must be non-null, so
            return false;
        } else if (t2 == null) {
            // ditto
            return false;
        } else {
            return t1.getId() == null
                ? t2.getId() == null
                : t1.getId().equals(t2.getId());
        }
    }
}
