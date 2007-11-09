package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Entity
@Table(name = "research_staff")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "research_staff_id_seq") })
// @AssociationOverride( name="contactMechanisms", joinColumns=
// @JoinColumn(name="RS_ID") )
public class ResearchStaff extends C3PRUser {

	private List<StudyPersonnel> studyPersonnels = new ArrayList<StudyPersonnel>();

	private String nciIdentifier;
	private String fullName;

	private HealthcareSite healthcareSite;

	// / LOGIC ~

	@Transient
	public String getLastFirst() {
		StringBuilder name = new StringBuilder();
		boolean hasFirstName = getFirstName() != null;
		if (getLastName() != null) {
			name.append(getLastName());
			if (hasFirstName)
				name.append(", ");
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
			if (hasLastName)
				name.append(' ');
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

	@OneToMany(mappedBy = "researchStaff")
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudyPersonnel> getStudyPersonnels() {
		return studyPersonnels;
	}

	public void setStudyPersonnels(List<StudyPersonnel> studyPersonnels) {
		this.studyPersonnels = studyPersonnels;
	}

	@OneToMany
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "RS_ID")
	public List<ContactMechanism> getContactMechanisms() {
		return contactMechanisms;
	}

	public void setContactMechanisms(List<ContactMechanism> contactMechanisms) {
		this.contactMechanisms = contactMechanisms;
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
		if (this.equals((ResearchStaff)o )) return 0;
		else return 1;
	}


	@Override
	public int hashCode() {
		final int PRIME = 31;
	//	int result = super.hashCode();
		int result = 1 ;
		result = PRIME * result + ((nciIdentifier == null) ? 0 : nciIdentifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		/*if (!super.equals(obj))
			return false;*/
		if (getClass() != obj.getClass())
			return false;
		final ResearchStaff other = (ResearchStaff) obj;
		if (nciIdentifier == null) {
			if (other.nciIdentifier != null)
				return false;
		} else if (!nciIdentifier.equalsIgnoreCase(other.nciIdentifier))
			return false;
		return true;
	}

	public String getNciIdentifier() {
		return nciIdentifier;
	}

	public void setNciIdentifier(String nciIdentifier) {
		this.nciIdentifier = nciIdentifier;
	}

}