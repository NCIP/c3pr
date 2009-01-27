package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Priyatam
 */
@MappedSuperclass
public abstract class ResearchStaff extends User {

    private List<StudyPersonnel> studyPersonnels = new ArrayList<StudyPersonnel>();

    private String nciIdentifier;
    
    private String fullName;

    private HealthcareSite healthcareSite;
    
    private List<UserBasedRecipient> userBasedRecipient;
    
	public ResearchStaff() {
		super();
	}
	
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

    public void addStudyPersonnel(StudyPersonnel studyPersonnel) {
        getStudyPersonnels().add(studyPersonnel);
    }

    // / BEAN METHODS

    @OneToMany(mappedBy = "localResearchStaff")
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<StudyPersonnel> getStudyPersonnels() {
        return studyPersonnels;
    }

    public void setStudyPersonnels(List<StudyPersonnel> studyPersonnels) {
        this.studyPersonnels = studyPersonnels;
    }

  

    @ManyToOne
    @JoinColumn(name = "HCS_ID")
    public HealthcareSite getHealthcareSite() {
        return healthcareSite;
    }

    public void setHealthcareSite(HealthcareSite healthcareSite) {
        this.healthcareSite = healthcareSite;
    }

    public int compareTo(Object o) {
        if (this.equals((ResearchStaff) o)) return 0;
        else return 1;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((getEmailAsString() == null) ? 0 : getEmailAsString().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        final ResearchStaff other = (ResearchStaff) obj;
        if (getEmailAsString() == null) {
            if (other.getEmailAsString() != null) return false;
        }
        else if (!getEmailAsString().equalsIgnoreCase(other.getEmailAsString())) return false;
        return true;
    }
    
    @Transient
    public String getNciIdentifier() {
        return nciIdentifier;
    }

    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }

    @OneToMany
    @Cascade(value = { CascadeType.LOCK})
    @JoinColumn(name = "research_staff_id")
	public List<UserBasedRecipient> getUserBasedRecipient() {
		return userBasedRecipient;
	}

	public void setUserBasedRecipient(List<UserBasedRecipient> userBasedRecipient) {
		this.userBasedRecipient = userBasedRecipient;
	}

}