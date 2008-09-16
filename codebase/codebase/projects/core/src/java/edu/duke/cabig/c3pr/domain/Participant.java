package edu.duke.cabig.c3pr.domain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.ProjectedList;
import edu.duke.cabig.c3pr.utils.StringUtils;
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

//	private String birthDateStr;

	private String administrativeGenderCode;

	private String ethnicGroupCode;

	//private String raceCode;
	private List<HealthcareSite> healthcareSites = new ArrayList<HealthcareSite>();;

	private List<RaceCode> raceCodes;

	private String maritalStatusCode;

	private List<StudySubject> studySubjects = new ArrayList<StudySubject>();

	private LazyListHelper lazyListHelper;

	private List<Identifier> identifiers;
	
	public Participant() {
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(OrganizationAssignedIdentifier.class,
				new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(
						OrganizationAssignedIdentifier.class));
		lazyListHelper.add(SystemAssignedIdentifier.class,
				new ParameterizedInstantiateFactory<SystemAssignedIdentifier>(
						SystemAssignedIdentifier.class));
		// mandatory, so that the lazy-projected list is managed properly.
		setIdentifiers(new ArrayList<Identifier>());
		raceCodes =  new ArrayList<RaceCode>();
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
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
		// initialize projected list for OrganizationAssigned and SystemAssignedIdentifier
		lazyListHelper.setInternalList(OrganizationAssignedIdentifier.class,
				new ProjectedList<OrganizationAssignedIdentifier>(this.identifiers,
						OrganizationAssignedIdentifier.class));
		lazyListHelper.setInternalList(SystemAssignedIdentifier.class,
				new ProjectedList<SystemAssignedIdentifier>(this.identifiers,
						SystemAssignedIdentifier.class));
	}

	@Transient
	public List<SystemAssignedIdentifier> getSystemAssignedIdentifiers() {
		return lazyListHelper.getLazyList(SystemAssignedIdentifier.class);
	}

	public void setSystemAssignedIdentifiers(
			List<SystemAssignedIdentifier> systemAssignedIdentifiers) {
		// do nothing
	}

	@Transient
	public List<OrganizationAssignedIdentifier> getOrganizationAssignedIdentifiers() {
		return lazyListHelper.getLazyList(OrganizationAssignedIdentifier.class);
	}

	public void setOrganizationAssignedIdentifiers(
			List<OrganizationAssignedIdentifier> organizationAssignedIdentifiers) {
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
		if (raceCodes == null){ 
			return null;
		}
		String rCode = "" ;
		for(RaceCode r : raceCodes){
			if( r != null ){
				if(rCode != "" ){
					rCode =  rCode + " : " + r.getName();
				}else{
					rCode = rCode + r.getName();
				}
			}
		}
		return rCode;
	}

	public void setRaceCode(String raceCode) {
		raceCodes = new ArrayList<RaceCode>();
		StringTokenizer tokenizer = new StringTokenizer(raceCode," : ");
		while (tokenizer.hasMoreTokens()) {
			RaceCode r = (RaceCode) Enum.valueOf(RaceCode.class, tokenizer.nextToken());
			raceCodes.add(r);
		};
	}

	@Transient
	public List<RaceCode> getRaceCodes() {
		return raceCodes;
	}

	public void setRaceCodes(List<RaceCode> raceCodes) {
		this.raceCodes = raceCodes;
	}

	@Transient
	public String getBirthDateStr() {
		if (birthDate == null) {
			return "";
		}
		else if (birthDate.equals("")) {
			return "";
		}
		try {
			return DateUtil.formatDate(birthDate, "MM/dd/yyyy");
		}
		catch (ParseException e) {
			// do nothing
		}
		return "";
	}

	@Transient
	public OrganizationAssignedIdentifier getMRN() {
		for (OrganizationAssignedIdentifier orgIdentifier : this
				.getOrganizationAssignedIdentifiers()) {
			if (orgIdentifier.getType() != null && orgIdentifier.getType().equalsIgnoreCase("MRN")) return orgIdentifier;
		}
		return null;
	}

	public String getMaritalStatusCode() {
		return maritalStatusCode;
	}

	public void setMaritalStatusCode(String maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}

	public void setStudySubjects(List<StudySubject> studySubjects) {
		this.studySubjects = studySubjects;
	}

	public void addStudySubject(StudySubject studySubject) {
		studySubjects.add(studySubject);
	}

	public void removeStudySubject(StudySubject studySubject) {
		studySubjects.remove(studySubject);
	}

	public int compareTo(Participant o) {
		if (this.equals(o)) return 0;
		return 1;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((getMRN() == null) ? 0 : getMRN().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		final Participant other = (Participant) obj;
		if ((this.getMRN() == null) || (other.getMRN() == null)) {
			return false;
		}
		else if (!((this.getMRN().getValue()).equalsIgnoreCase(other.getMRN().getValue()))) {
			return false;
		}
		else if (!(this.getMRN().equals(other.getMRN()))) {
			return false;
		}
		return true;
	}

	@Transient
	public String getPrimaryIdentifier() {
		Identifier id = this.getMedicalRecordNumber();
		if (id == null) return "";
		return id.getValue();
	}

	@Transient
	public Identifier getMedicalRecordNumber() {
		for (Identifier identifier : getIdentifiers()) {
			if (identifier.getPrimaryIndicator().booleanValue() == true) {
				return identifier;
			}
		}
		return null;
	}

	public void setPrimaryIdentifier(String primaryIdentifier) {
	}

	/**
	 * Runs basic validation on the Participant object.
	 * Moved here from the service during the refactoring effort.
	 * @param participant
	 * @return
	 */
	public boolean validateParticipant() {
		if (StringUtils.getBlankIfNull(this.getFirstName()).equals("")
				|| StringUtils.getBlankIfNull(this.getLastName()).equals("")
				|| this.getBirthDate() == null
				|| StringUtils.getBlankIfNull(this.getAdministrativeGenderCode())
				.equals("")) return false;
		return true;
	}

	@ManyToMany
	@Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    @JoinTable(name="prt_org_associations",
        joinColumns = @JoinColumn(name="prt_stu_sub_id"),
        inverseJoinColumns = @JoinColumn(name="org_stu_sub_id")
    )
    public List<HealthcareSite> getHealthcareSites() {
        return healthcareSites;
    }

    public void setHealthcareSites(List<HealthcareSite> healthcareSites) {
    	this.healthcareSites = healthcareSites;
    }

}