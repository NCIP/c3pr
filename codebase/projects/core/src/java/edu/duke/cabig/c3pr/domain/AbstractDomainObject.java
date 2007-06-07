package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Abstract Domain class implementing Domain object
 * @see edu.duke.cabig.c3pr.domain.DomainObject
 * @author Priyatam
 */

@MappedSuperclass
public abstract class AbstractDomainObject implements DomainObject, Serializable {
    
	private Integer id;	
	private Integer version;
	private String name;

	@Id @GeneratedValue(generator = "id-generator")	   	
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    @Version
	@Column(name = "VERSION")	
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
    
    
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.DomainObject#getName()
	 */
    @Transient
	public String getName() {		
		return name;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.DomainObject#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name=name;
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractDomainObject other = (AbstractDomainObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
}
