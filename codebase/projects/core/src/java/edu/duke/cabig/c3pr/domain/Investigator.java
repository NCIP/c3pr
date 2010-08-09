package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.semanticbits.coppa.domain.annotations.RemoteProperty;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * The Class Investigator.
 * 
 * @author Priyatam
 */
@Entity
@Table(name = "investigators")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "investigators_id_seq") })
public abstract class Investigator extends C3PRUser {
    
    /** The assigned identifier. */
    private String assignedIdentifier;

    /** The lazy list helper. */
    private LazyListHelper lazyListHelper;

    /** The user based recipient. */
    private List<UserBasedRecipient> userBasedRecipients;
    
    /** The external investigators. */
    protected List<Investigator> externalInvestigators = new ArrayList<Investigator>();

    /**
     * Gets the external investigators.
     * 
     * @return the external investigators
     */
    @Transient
    public List<Investigator> getExternalInvestigators() {
		return externalInvestigators;
	}

	/**
	 * Sets the external investigators.
	 * 
	 * @param externalInvestigators the new external investigators
	 */
	public void setExternalInvestigators(List<Investigator> externalInvestigators) {
		this.externalInvestigators = externalInvestigators;
	}
	
	/**
	 * Adds the external investigator.
	 * 
	 * @param externalInvestigator the external investigator
	 */
	public void addExternalInvestigator(Investigator externalInvestigator) {
		this.getExternalInvestigators().add(externalInvestigator);
	}

	/**
	 * Instantiates a new investigator.
	 */
	public Investigator() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(HealthcareSiteInvestigator.class,
                        new BiDirectionalInstantiateFactory<HealthcareSiteInvestigator>(
                            HealthcareSiteInvestigator.class, this, "Investigator", new Class[] { Investigator.class }));
    }

    /**
     * Gets the last first.
     * 
     * @return the last first
     */
    @Transient
    public String getLastFirst() {
        StringBuilder name = new StringBuilder();
        boolean hasFirstName = getFirstName() != null;
        if (getLastName() != null) {
            name.append(getLastName());
            if (hasFirstName) name.append(", ");
        }
        if (hasFirstName) {
            name.append(getFirstName());
        }
        return name.toString();
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Person#getFullName()
     */
    @Transient
    public String getFullName() {
        StringBuilder name = new StringBuilder();
        boolean hasLastName = getLastName() != null;
        if (getFirstName() != null) {
            name.append(getFirstName());
            if (hasLastName) name.append(' ');
        }
        if (hasLastName) {
            name.append(getLastName());
        }
        return name.toString();
    }

    /**
     * Adds the healthcare site investigator.
     * 
     * @param hcsi the hcsi
     */
    public void addHealthcareSiteInvestigator(HealthcareSiteInvestigator hcsi) {
        hcsi.setInvestigator(this);
        lazyListHelper.getLazyList(HealthcareSiteInvestigator.class).add(hcsi);
    }
    
    /**
     * Gets the healthcare site investigators internal.
     * 
     * @return the healthcare site investigators internal
     */
    @OneToMany(mappedBy = "investigator")
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<HealthcareSiteInvestigator> getHealthcareSiteInvestigatorsInternal() {
        return lazyListHelper.getInternalList(HealthcareSiteInvestigator.class);
    }

    /**
     * Gets the healthcare site investigators.
     * 
     * @return the healthcare site investigators
     */
    @Transient
    public List<HealthcareSiteInvestigator> getHealthcareSiteInvestigators() {
        return lazyListHelper.getLazyList(HealthcareSiteInvestigator.class);
    }

    /**
     * Sets the healthcare site investigators.
     * 
     * @param healthcareSiteInvestigators the new healthcare site investigators
     */
    public void setHealthcareSiteInvestigators(
                    List<HealthcareSiteInvestigator> healthcareSiteInvestigators) {
    }

    /**
     * Sets the healthcare site investigators internal.
     * 
     * @param healthcareSiteInvestigators the new healthcare site investigators internal
     */
    public void setHealthcareSiteInvestigatorsInternal(
                    List<HealthcareSiteInvestigator> healthcareSiteInvestigators) {
        lazyListHelper.setInternalList(HealthcareSiteInvestigator.class,
                        healthcareSiteInvestigators);
    }

    /**
     * Gets the assigned identifier.
     * 
     * @return the assigned identifier
     */
    public String getAssignedIdentifier() {
        return assignedIdentifier;
    }

    /**
     * Sets the assigned identifier.
     * 
     * @param assignedIdentifier the new assigned identifier
     */
    public void setAssignedIdentifier(String assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Person#getContactMechanisms()
     */
    @OneToMany
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "INV_ID")
    @OrderBy("id")
    @RemoteProperty
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
     * Compare to.
     * 
     * @param o the o
     * 
     * @return the int
     */
    public int compareTo(Object o) {
        if (this.equals((Investigator) o)) return 0;
        else return 1;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Person#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Person#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        final Investigator other = (Investigator) obj;
        if (getAssignedIdentifier() == null) {
            if (other.getAssignedIdentifier() != null) return false;
        }
        else if (!getAssignedIdentifier().equalsIgnoreCase(other.getAssignedIdentifier())) return false;
        return true;
    }

    /**
     * Gets the user based recipient.
     * 
     * @return the user based recipient
     */
    @OneToMany
    @Cascade(value = { CascadeType.LOCK})
    @JoinColumn(name = "investigators_id")
	public List<UserBasedRecipient> getUserBasedRecipients() {
		return userBasedRecipients;
	}

	/**
	 * Sets the user based recipient.
	 * 
	 * @param userBasedRecipients the new user based recipient
	 */
	public void setUserBasedRecipients(List<UserBasedRecipient> userBasedRecipients) {
		this.userBasedRecipients = userBasedRecipients;
	}
	
	@Transient
	public List<HealthcareSite> getHealthcareSites(){
		List<HealthcareSite> healthcareSites = new ArrayList<HealthcareSite>();
		for(HealthcareSiteInvestigator hcsInvestigator : getHealthcareSiteInvestigators()){
			healthcareSites.add(hcsInvestigator.getHealthcareSite());
		}
		
		return healthcareSites;
	}

}