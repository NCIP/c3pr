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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.ProjectedList;
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
	private List<StudySubject> studySubjects = new ArrayList<StudySubject>();
	private LazyListHelper lazyListHelper;
	private List<Identifier> identifiers;

	
	public Participant() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(OrganizationAssignedIdentifier.class, new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(OrganizationAssignedIdentifier.class));
        lazyListHelper.add(SystemAssignedIdentifier.class, new ParameterizedInstantiateFactory<SystemAssignedIdentifier>(SystemAssignedIdentifier.class));
        //   mandatory, so that the lazy-projected list is managed properly.
        setIdentifiers(new ArrayList<Identifier>());
    }

	public void addIdentifier(Identifier identifier) {
		getIdentifiers().add(identifier);
	}

	public void removeIdentifier(Identifier identifier) {
		getIdentifiers().remove(identifier);
	}

	@OneToMany(fetch = FetchType.LAZY)
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "PRT_ID")
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}
	
    public void setIdentifiers(List<Identifier> identifiers) {
    	this.identifiers = identifiers;
		//initialize projected list for OrganizationAssigned and SystemAssignedIdentifier
		lazyListHelper.setInternalList(OrganizationAssignedIdentifier.class, 
				new ProjectedList<OrganizationAssignedIdentifier>(this.identifiers, OrganizationAssignedIdentifier.class));
		lazyListHelper.setInternalList(SystemAssignedIdentifier.class, 
				new ProjectedList<SystemAssignedIdentifier>(this.identifiers, SystemAssignedIdentifier.class));
    }
  
    @Transient
    public List<SystemAssignedIdentifier> getSystemAssignedIdentifiers() {
        return lazyListHelper.getLazyList(SystemAssignedIdentifier.class);
    }
    
    public void setSystemAssignedIdentifiers(List<SystemAssignedIdentifier> systemAssignedIdentifiers)
    {
    	// do nothing
    }
    
    @Transient
    public List<OrganizationAssignedIdentifier> getOrganizationAssignedIdentifiers() {
        return lazyListHelper.getLazyList(OrganizationAssignedIdentifier.class);
    }
    
    public void setOrganizationAssignedIdentifiers(List<OrganizationAssignedIdentifier> organizationAssignedIdentifiers)
    {
    	// do nothing
    }

	@OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudySubject> getStudySubjects() {
		return studySubjects;
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

	public void setStudySubjects(
			List<StudySubject> studySubjects) {
		this.studySubjects = studySubjects;
	}

	public void addStudySubject(StudySubject studySubject) {
		studySubjects.add(studySubject);
	}

	public void removeStudySubject(
			StudySubject studySubject) {
		studySubjects.remove(studySubject);
	}
	
	public int compareTo(Participant o) {
		if(this.equals(o))
			return 0;
		return 1;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result
				+ ((getIdentifiers() == null) ? 0 : getIdentifiers().hashCode());
		result = PRIME
				* result
				+ ((studySubjects == null) ? 0
						: studySubjects.hashCode());
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
		if (getIdentifiers() == null) {
			if (other.getIdentifiers() != null)
				return false;
		} else if (!getIdentifiers().equals(other.getIdentifiers()))
			return false;
		if (studySubjects == null) {
			if (other.studySubjects != null)
				return false;
		} else if (!studySubjects
				.equals(other.studySubjects))
			return false;
		return true;
	}

	@Transient
	public String getPrimaryIdentifier() {
		for (Identifier identifier : getIdentifiers()) {
			if (identifier.getPrimaryIndicator().booleanValue() == true) {
				return identifier.getValue();
			}
		}
		return "";
	}

	public void setPrimaryIdentifier(String primaryIdentifier) {
	}
}