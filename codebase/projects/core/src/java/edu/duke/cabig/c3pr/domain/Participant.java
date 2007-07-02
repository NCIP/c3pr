package edu.duke.cabig.c3pr.domain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.DateUtil;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * @author Priyatam
 * 
 */
@Entity
@Table(name = "participants")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "participants_id_seq") })
public class Participant extends Person implements Comparable<Participant> {
	private Date birthDate;
	private String birthDateStr;
	private String administrativeGenderCode;
	private String ethnicGroupCode;
	private String raceCode;
	private String maritalStatusCode;
	private String primaryIdentifier;
	private List<Identifier> identifiers = new ArrayList<Identifier>();
	private List<StudyParticipantAssignment> studyParticipantAssignments = new ArrayList<StudyParticipantAssignment>();
	private LazyListHelper lazyListHelper;
	
	public Participant() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(Identifier.class, new InstantiateFactory<Identifier>(Identifier.class));
    }

	public void addIdentifier(Identifier identifier) {
		identifiers.add(identifier);
	}

	public void removeIdentifier(Identifier identifier) {
		identifiers.remove(identifier);
	}
	
	 @Transient
	    public List<Identifier> getIdentifiers() {
	        return lazyListHelper.getLazyList(Identifier.class);
	    }

	@OneToMany(fetch = FetchType.LAZY)
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "PRT_ID")
	public List<Identifier> getIdentifiersInternal() {
		return lazyListHelper.getInternalList(Identifier.class);
	}
	
    public void setIdentifiersInternal(List<Identifier> identifiers) {
        lazyListHelper.setInternalList(Identifier.class, identifiers);
    }

    public void setIdentifiers(List<Identifier> identifiers) {
        lazyListHelper.setInternalList(Identifier.class, identifiers);
    }

	@OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudyParticipantAssignment> getStudyParticipantAssignments() {
		return studyParticipantAssignments;
	}

	@OneToMany
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "PRT_ID")
	public List<ContactMechanism> getContactMechanisms() {
		return contactMechanisms;
	}

	public void setContactMechanisms(List<ContactMechanism> contactMechanisms) {
		this.contactMechanisms = contactMechanisms;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getAdministrativeGenderCode() {
		return administrativeGenderCode;
	}

	public void setAdministrativeGenderCode(String administritativeGenderCode) {
		this.administrativeGenderCode = administritativeGenderCode;
	}

	public String getEthnicGroupCode() {
		return ethnicGroupCode;
	}

	public void setEthnicGroupCode(String ethnicGroupCode) {
		this.ethnicGroupCode = ethnicGroupCode;
	}

	public String getRaceCode() {
		return raceCode;
	}

	public void setRaceCode(String raceCode) {
		this.raceCode = raceCode;
	}

	@Transient
	public String getBirthDateStr() {
		if (birthDate == null) {
			return "";
		} else if (birthDate.equals("")) {
			return "";
		}
		try {
			return DateUtil.formatDate(birthDate, "MM/dd/yyyy");
		} catch (ParseException e) {
			// do nothing
		}
		return "";
	}

	public String getMaritalStatusCode() {
		return maritalStatusCode;
	}

	public void setMaritalStatusCode(String maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}

	public void setStudyParticipantAssignments(
			List<StudyParticipantAssignment> studyParticipantAssignments) {
		this.studyParticipantAssignments = studyParticipantAssignments;
	}

	public void addStudyParticipantAssignment(
			StudyParticipantAssignment studyParticipantAssignment) {
		studyParticipantAssignments.add(studyParticipantAssignment);
	}

	public void removeStudyParticipantAssignment(
			StudyParticipantAssignment studyParticipantAssignment) {
		studyParticipantAssignments.remove(studyParticipantAssignment);
	}

	public int compareTo(Participant o) {
		if(this.equals((Participant)o))
			return 0;
		return 1;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = PRIME
				* result
				+ ((studyParticipantAssignments == null) ? 0
						: studyParticipantAssignments.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Participant other = (Participant) obj;
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
			return false;
		if (studyParticipantAssignments == null) {
			if (other.studyParticipantAssignments != null)
				return false;
		} else if (!studyParticipantAssignments
				.equals(other.studyParticipantAssignments))
			return false;
		return true;
	}

	@Transient
	public String getPrimaryIdentifier() {
		for (Identifier identifier : identifiers) {
			if (identifier.getPrimaryIndicator().booleanValue() == true) {
				return identifier.getValue();
			}
		}

		return primaryIdentifier;
	}

	public void setPrimaryIdentifier(String primaryIdentifier) {
		this.primaryIdentifier = primaryIdentifier;
	}
}