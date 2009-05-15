package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

// TODO: Auto-generated Javadoc
/**
 * The Class OrganizationAssignedIdentifier.
 */
@Entity
@DiscriminatorValue("OAI")
public class OrganizationAssignedIdentifier extends Identifier implements
                Comparable<OrganizationAssignedIdentifier> {

    /** The healthcare site. */
    private HealthcareSite healthcareSite;

    /**
     * Gets the healthcare site.
     * 
     * @return the healthcare site
     */
    @ManyToOne
    @JoinColumn(name = "hcs_id")
    public HealthcareSite getHealthcareSite() {
        return healthcareSite;
    }

    /**
     * Sets the healthcare site.
     * 
     * @param healthcareSite the new healthcare site
     */
    public void setHealthcareSite(HealthcareSite healthcareSite) {
        this.healthcareSite = healthcareSite;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(OrganizationAssignedIdentifier o) {
        if (this.equals(o)) return 0;
        else return 1;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Identifier#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + ((healthcareSite == null) ? 0 : healthcareSite.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Identifier#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        final OrganizationAssignedIdentifier other = (OrganizationAssignedIdentifier) obj;
        if (healthcareSite == null) {
            if (other.healthcareSite != null) return false;
        }
        else if (!healthcareSite.equals(other.healthcareSite)) return false;
        return true;
    }

}
