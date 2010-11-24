package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;

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
    
    /**
     * Gets the type.
     * 
     * @return the type
     */
    @Transient
    public OrganizationIdentifierTypeEnum getType() {
		String typeInternal = getTypeInternal();
    	
    	return OrganizationAssignedIdentifier.getOrganizationIdentifierEnumByCode(typeInternal);
	}

    public static OrganizationIdentifierTypeEnum getOrganizationIdentifierEnumByCode(String type) {
    	if(OrganizationIdentifierTypeEnum.AI.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.AI;
		}
    	if(OrganizationIdentifierTypeEnum.C3D_IDENTIFIER.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.C3D_IDENTIFIER;
		}
		if(OrganizationIdentifierTypeEnum.C3PR.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.C3PR;
		}
		if(OrganizationIdentifierTypeEnum.CLINICAL_TRIALS_GOV_IDENTIFIER.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.CLINICAL_TRIALS_GOV_IDENTIFIER;
		}
		if(OrganizationIdentifierTypeEnum.COOPERATIVE_GROUP_IDENTIFIER.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.COOPERATIVE_GROUP_IDENTIFIER;
		}
		if(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER;
		}
		if(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER;
		}
		if(OrganizationIdentifierTypeEnum.CTEP.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.CTEP;
		}
		if(OrganizationIdentifierTypeEnum.GRID_IDENTIFIER.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.GRID_IDENTIFIER;
		}
		if(OrganizationIdentifierTypeEnum.LOCAL.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.LOCAL;
		}
		if(OrganizationIdentifierTypeEnum.MRN.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.MRN;
		}
		if(OrganizationIdentifierTypeEnum.NCI.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.NCI;
		}
		if(OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER;
		}
		if(OrganizationIdentifierTypeEnum.SITE_IDENTIFIER.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.SITE_IDENTIFIER;
		}
		if(OrganizationIdentifierTypeEnum.SITE_IRB_IDENTIFIER.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.SITE_IRB_IDENTIFIER;
		}
		if(OrganizationIdentifierTypeEnum.STUDY_FUNDING_SPONSOR.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.STUDY_FUNDING_SPONSOR;
		}
		if(OrganizationIdentifierTypeEnum.STUDY_SUBJECT_IDENTIFIER.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.STUDY_SUBJECT_IDENTIFIER;
		}
		if(OrganizationIdentifierTypeEnum.SUBJECT_IDENTIFIER.getName().equalsIgnoreCase(type)){
			return OrganizationIdentifierTypeEnum.SUBJECT_IDENTIFIER;
		}
    	return null;
	}
    

	/**
	 * Sets the type.
	 * 
	 * @param type the new type
	 */
	public void setType(OrganizationIdentifierTypeEnum type) {
		this.setTypeInternal(type.getName());
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
        if (!this.getValue().equals(other.getValue())) return false;
        return true;
    }
    
    @Override
    public String toString() {
    	 return " Assigning Organization:" + healthcareSite.getName() + " Identifier Type:" + getType() + " Identifier Value:" + getValue();
    }

}
