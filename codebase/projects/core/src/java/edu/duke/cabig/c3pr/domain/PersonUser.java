/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.PersonUserType;
import edu.duke.cabig.c3pr.constants.StatusType;

/**
 * Originally created as The Class ResearchStaff, now renamed to PersonUser.
 * The intent being to support both users (who aren't necessarily staff) and staff(who aren't necessarily users).
 * The personType enum determines if the instance is a staff or user or both.
 * 
 * @author Priyatam, Vinay G
 */
@Entity
@Table(name = "persons_users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "persons_users_id_seq") })
public abstract class PersonUser extends User {

    /** The study personnels. */
    private List<StudyPersonnel> studyPersonnels = new ArrayList<StudyPersonnel>();

    /** The nci identifier. */
    private String assignedIdentifier;
    
    /** The healthcare site. */
	private List<HealthcareSite> healthcareSites = new ArrayList<HealthcareSite>();
    
	/** The status code. */
    private StatusType statusCode = StatusType.AC;
    
	/** The user based recipient. */
    private List<UserBasedRecipient> userBasedRecipients;
    
    /** The external research staff. */
    protected List<PersonUser> externalResearchStaff = new ArrayList<PersonUser>();
    
    /** The person user type. */
    private PersonUserType personUserType;
    
	/**
	 * Instantiates a new research staff.
	 */
	public PersonUser() {
		super();
	}
	
	/**
	 * Gets the external research staff.
	 * 
	 * @return the external research staff
	 */
	@Transient
	public List<PersonUser> getExternalResearchStaff() {
		return externalResearchStaff;
	}

	/**
	 * Sets the external research staff.
	 * 
	 * @param externalResearchStaff the new external research staff
	 */
	public void setExternalResearchStaff(List<PersonUser> externalResearchStaff) {
		this.externalResearchStaff = externalResearchStaff;
	}
	
	/**
	 * Adds the external research staff.
	 * 
	 * @param externalResearchStaff the external research staff
	 */
	public void addExternalResearchStaff(PersonUser externalResearchStaff){
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


    /**
     * Gets the study personnels.
     * 
     * @return the study personnels
     */
    @OneToMany(mappedBy = "personUser")
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
    public Set<ContactMechanism> getContactMechanisms() {
        return contactMechanisms;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Person#setContactMechanisms(java.util.List)
     */
    public void setContactMechanisms(Set<ContactMechanism> contactMechanisms) {
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
        if (this.equals((PersonUser) o)) return 0;
        else return 1;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Person#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.assignedIdentifier == null) ? 0 : this.assignedIdentifier.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Person#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        final PersonUser other = (PersonUser) obj;
        if(getAssignedIdentifier() == null && other.getAssignedIdentifier() != null){
        	return false;
        }
        if(getAssignedIdentifier() != null && other.getAssignedIdentifier() == null){
        	return false;
        }
        if(getAssignedIdentifier() != null && other.getAssignedIdentifier() != null){
        	if(!getAssignedIdentifier().equalsIgnoreCase(other.getAssignedIdentifier())) return false;
        }
        if(getAssignedIdentifier() == null && other.getAssignedIdentifier() == null){
        	if(getLoginId() == null && other.getLoginId() != null){
            	return false;
            }
            if(getLoginId() != null && other.getLoginId() == null){
            	return false;
            }
            if(getLoginId() != null && other.getLoginId() != null){
            	if (!getLoginId().equalsIgnoreCase(other.getLoginId())) return false;
            }
        }
        return true;
    }

    /**
     * Gets the user based recipient.
     * 
     * @return the user based recipient
     */
    @OneToMany
    @Cascade(value = { CascadeType.LOCK})
    @JoinColumn(name = "research_staff_id")
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
	
	 @Enumerated(EnumType.STRING)
	    public StatusType getStatusCode() {
			return statusCode;
	}

	public void setStatusCode(StatusType statusCode) {
		this.statusCode = statusCode;
	}

	public String getAssignedIdentifier() {
		return assignedIdentifier;
	}

	public void setAssignedIdentifier(String assignedIdentifier) {
		this.assignedIdentifier = assignedIdentifier;
	}
	
	/**
	 * @return the healthcareSites
	 */
    @ManyToMany
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinTable(name = "rs_hc_site_assocn", joinColumns = @JoinColumn(name = "rs_id"), inverseJoinColumns = @JoinColumn(name = "hcs_id"))
	public List<HealthcareSite> getHealthcareSites() { 	
		return healthcareSites;
	}
	public void setHealthcareSites(List<HealthcareSite> healthcareSites) {
		this.healthcareSites = healthcareSites;
	}
	public void addHealthcareSite(HealthcareSite healthcareSite){
    	this.getHealthcareSites().add(healthcareSite);
    } 
	public void removeHealthcareSite(HealthcareSite healthcareSite){
    	this.getHealthcareSites().remove(healthcareSite);
    }
	
	/**
	 * Checks if is staff.
	 *
	 * @return true, if assigned Id is present.
	 */
	@Transient
	public boolean getIsStaffOnly(){
		if(!StringUtils.isBlank(getAssignedIdentifier()) && StringUtils.isBlank(getLoginId())){
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if is user.
	 *
	 * @return true, if loginId is present.
	 */
	@Transient
	public boolean getIsUserOnly(){
		if(StringUtils.isBlank(getAssignedIdentifier()) && !StringUtils.isBlank(getLoginId())){
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if is user.
	 *
	 * @return true, if loginId is present.
	 */
	@Transient
	public boolean getIsBothStaffAndUser(){
		if(!StringUtils.isBlank(getAssignedIdentifier()) && !StringUtils.isBlank(getLoginId())){
			return true;
		}
		return false;
	}

	@Column(name = "person_user_type")
	@Enumerated(EnumType.STRING)
	public PersonUserType getPersonUserType() {
		return personUserType;
	}

	public void setPersonUserType(PersonUserType personUserType) {
		this.personUserType = personUserType;
	}
	
	
}
