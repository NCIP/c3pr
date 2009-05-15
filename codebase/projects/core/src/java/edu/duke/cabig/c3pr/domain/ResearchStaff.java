package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.semanticbits.coppa.domain.annotations.RemoteProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class ResearchStaff.
 * 
 * @author Priyatam
 */
@Entity
@Table(name = "research_staff")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "research_staff_id_seq") })
public abstract class ResearchStaff extends User {

    /** The study personnels. */
    private List<StudyPersonnel> studyPersonnels = new ArrayList<StudyPersonnel>();

    /** The nci identifier. */
    private String nciIdentifier;
    
    /** The healthcare site. */
    private HealthcareSite healthcareSite;
    
    /** The user based recipient. */
    private List<UserBasedRecipient> userBasedRecipient;
    
    /** The external research staff. */
    protected List<ResearchStaff> externalResearchStaff = new ArrayList<ResearchStaff>();
    
	/**
	 * Instantiates a new research staff.
	 */
	public ResearchStaff() {
		super();
	}
	
	/**
	 * Gets the external research staff.
	 * 
	 * @return the external research staff
	 */
	@Transient
	public List<ResearchStaff> getExternalResearchStaff() {
		return externalResearchStaff;
	}

	/**
	 * Sets the external research staff.
	 * 
	 * @param externalResearchStaff the new external research staff
	 */
	public void setExternalResearchStaff(List<ResearchStaff> externalResearchStaff) {
		this.externalResearchStaff = externalResearchStaff;
	}
	
	/**
	 * Adds the external research staff.
	 * 
	 * @param externalResearchStaff the external research staff
	 */
	public void addExternalResearchStaff(ResearchStaff externalResearchStaff){
	    	this.getExternalResearchStaff().add(externalResearchStaff);
	    }


	/**
	 * Adds the study personnel.
	 * 
	 * @param studyPersonnel the study personnel
	 */
	public void addStudyPersonnel(StudyPersonnel studyPersonnel) {
        getStudyPersonnels().add(studyPersonnel);
    }

    // / BEAN METHODS

    /**
     * Gets the study personnels.
     * 
     * @return the study personnels
     */
    @OneToMany(mappedBy = "researchStaff")
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<StudyPersonnel> getStudyPersonnels() {
        return studyPersonnels;
    }

    /**
     * Sets the study personnels.
     * 
     * @param studyPersonnels the new study personnels
     */
    public void setStudyPersonnels(List<StudyPersonnel> studyPersonnels) {
        this.studyPersonnels = studyPersonnels;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Person#getContactMechanisms()
     */
    @OneToMany
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "RS_ID")
    @OrderBy("id")
    public List<ContactMechanism> getContactMechanisms() {
        return contactMechanisms;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Person#setContactMechanisms(java.util.List)
     */
    public void setContactMechanisms(List<ContactMechanism> contactMechanisms) {
        this.contactMechanisms = contactMechanisms;
    }

    /**
     * Gets the healthcare site.
     * 
     * @return the healthcare site
     */
    @ManyToOne
    @JoinColumn(name = "HCS_ID")
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
     * Compare to.
     * 
     * @param o the o
     * 
     * @return the int
     */
    public int compareTo(Object o) {
        if (this.equals((ResearchStaff) o)) return 0;
        else return 1;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Person#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        // int result = super.hashCode();
        int result = 1;
        result = PRIME * result + ((nciIdentifier == null) ? 0 : nciIdentifier.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Person#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        final ResearchStaff other = (ResearchStaff) obj;
        if (nciIdentifier == null) {
            if (other.nciIdentifier != null) return false;
        }
        else if (!nciIdentifier.equalsIgnoreCase(other.nciIdentifier)) return false;
        return true;
    }

    /**
     * Gets the nci identifier.
     * 
     * @return the nci identifier
     */
    @RemoteProperty
    public String getNciIdentifier() {
        return nciIdentifier;
    }

    /**
     * Sets the nci identifier.
     * 
     * @param nciIdentifier the new nci identifier
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }

    /**
     * Gets the user based recipient.
     * 
     * @return the user based recipient
     */
    @OneToMany
    @Cascade(value = { CascadeType.LOCK})
    @JoinColumn(name = "research_staff_id")
	public List<UserBasedRecipient> getUserBasedRecipient() {
		return userBasedRecipient;
	}

	/**
	 * Sets the user based recipient.
	 * 
	 * @param userBasedRecipient the new user based recipient
	 */
	public void setUserBasedRecipient(List<UserBasedRecipient> userBasedRecipient) {
		this.userBasedRecipient = userBasedRecipient;
	}
	
}