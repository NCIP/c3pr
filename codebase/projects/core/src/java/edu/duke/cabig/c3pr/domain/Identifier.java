package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

// TODO: Auto-generated Javadoc
/**
 * The Class Identifier.
 * 
 * @author Priyatam
 */

@Entity
@Table(name = "IDENTIFIERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "IDENTIFIERS_ID_SEQ") })
public abstract class Identifier extends AbstractMutableDeletableDomainObject {

    /** The type. */
    private String type;

    /** The value. */
    private String value;

    /** The primary indicator. */
    private Boolean primaryIndicator = false;

    /**
     * Null-safe conversion from primaryIndicator property to simple boolean. TODO: switch the db
     * field to not-null, default false so this isn't necessary.
     * 
     * @return true, if checks if is primary
     */
    @Transient
    public boolean isPrimary() {
        return getPrimaryIndicator() == null ? false : getPrimaryIndicator();
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the primary indicator.
     * 
     * @return the primary indicator
     */
    public Boolean getPrimaryIndicator() {
        return primaryIndicator;
    }

    /**
     * Sets the primary indicator.
     * 
     * @param primaryIndicator the new primary indicator
     */
    public void setPrimaryIndicator(Boolean primaryIndicator) {
        if (primaryIndicator == null) {
            primaryIndicator = false;
        }
        this.primaryIndicator = primaryIndicator;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Identifier other = (Identifier) obj;
        if (type == null) {
            if (other.type != null) return false;
        }
        else if (!type.equalsIgnoreCase(other.type)) return false;
        return true;
    }

}
