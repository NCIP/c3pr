package edu.duke.cabig.c3pr.domain;

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

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RaceCode;
import edu.duke.cabig.c3pr.domain.customfield.BooleanCustomField;
import edu.duke.cabig.c3pr.domain.customfield.CustomField;
import edu.duke.cabig.c3pr.domain.customfield.Customizable;
import edu.duke.cabig.c3pr.domain.customfield.DateCustomField;
import edu.duke.cabig.c3pr.domain.customfield.IntegerCustomField;
import edu.duke.cabig.c3pr.domain.customfield.StringCustomField;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedBiDirectionalInstantiateFactory;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedInstantiateFactory;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.ProjectedList;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class Participant.
 * 
 * @author Priyatam
 */
@Entity
@Table(name = "participants")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "participants_id_seq") })
public class Participant extends Person implements Comparable<Participant> , Customizable{

	/** The birth date. */
	private Date birthDate;


	/** The administrative gender code. */
	private String administrativeGenderCode;

	/** The ethnic group code. */
	private String ethnicGroupCode;

	//private String raceCode;
	/** The healthcare sites. */
	private List<HealthcareSite> healthcareSites;

	/** The race codes. */
	private List<RaceCode> raceCodes;

	/** The marital status code. */
	private String maritalStatusCode;

	/** The study subjects. */
//	private List<StudySubject> studySubjects = new ArrayList<StudySubject>();

	/** The lazy list helper. */
	private LazyListHelper lazyListHelper;

	/** The identifiers. */
	private List<Identifier> identifiers;
	
	private List<StudySubjectDemographics> studySubjectDemographics = new ArrayList<StudySubjectDemographics>();
	
	/**
	 * Instantiates a new participant.
	 */
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
		healthcareSites = new ArrayList<HealthcareSite>();
		lazyListHelper.add(CustomField.class,new ParameterizedBiDirectionalInstantiateFactory<CustomField>(CustomField.class, this));
	}

	
	@OneToMany(mappedBy = "masterSubject")
	@Cascade(value = {CascadeType.LOCK})
	@OrderBy("id")
	public List<StudySubjectDemographics> getStudySubjectDemographics() {
		return studySubjectDemographics;
	}

	public void setStudySubjectDemographics(
			List<StudySubjectDemographics> studySubjectDemographics) {
		this.studySubjectDemographics = studySubjectDemographics;
	}
	
	
	public void addStudySubjectDemographics(StudySubjectDemographics studySubjectDemographics){
		this.getStudySubjectDemographics().add(studySubjectDemographics);
		studySubjectDemographics.setMasterSubject(this);
	}

	/**
	 * Adds the identifier.
	 * 
	 * @param identifier the identifier
	 */
	public void addIdentifier(Identifier identifier) {
		getIdentifiers().add(identifier);
	}

	/**
	 * Removes the identifier.
	 * 
	 * @param identifier the identifier
	 */
	public void removeIdentifier(Identifier identifier) {
		getIdentifiers().remove(identifier);
	}

	/**
	 * Gets the identifiers.
	 * 
	 * @return the identifiers
	 */
	@OneToMany(fetch = FetchType.LAZY)
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "PRT_ID")
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	/**
	 * Sets the identifiers.
	 * 
	 * @param identifiers the new identifiers
	 */
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

	/**
	 * Gets the system assigned identifiers.
	 * 
	 * @return the system assigned identifiers
	 */
	@Transient
	public List<SystemAssignedIdentifier> getSystemAssignedIdentifiers() {
		return lazyListHelper.getLazyList(SystemAssignedIdentifier.class);
	}

	/**
	 * Sets the system assigned identifiers.
	 * 
	 * @param systemAssignedIdentifiers the new system assigned identifiers
	 */
	public void setSystemAssignedIdentifiers(
			List<SystemAssignedIdentifier> systemAssignedIdentifiers) {
		// do nothing
	}

	/**
	 * Gets the organization assigned identifiers.
	 * 
	 * @return the organization assigned identifiers
	 */
	@Transient
	public List<OrganizationAssignedIdentifier> getOrganizationAssignedIdentifiers() {
		return lazyListHelper.getLazyList(OrganizationAssignedIdentifier.class);
	}

	/**
	 * Sets the organization assigned identifiers.
	 * 
	 * @param organizationAssignedIdentifiers the new organization assigned identifiers
	 */
	public void setOrganizationAssignedIdentifiers(
			List<OrganizationAssignedIdentifier> organizationAssignedIdentifiers) {
		// do nothing
	}

	/**
	 * Gets the study subjects.
	 * 
	 * @return the study subjects
	 */
	/*@OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Where(clause = "reg_workflow_status  != 'INVALID'")
	public List<StudySubject> getStudySubjects() {
		return studySubjects;
	}
*/
	@Transient
	public List<StudySubject> getStudySubjects(){
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    	
    	for(StudySubjectDemographics studySubjectDemographic : this.getStudySubjectDemographics()){
    		studySubjects.addAll(studySubjectDemographic.getRegistrations());
    	}
		return studySubjects;
	}
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.Person#getContactMechanisms()
	 */
	@OneToMany
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "PRT_ID")
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
	
	public void addContactMechanism(ContactMechanism contactMechanism){
		getContactMechanisms().add(contactMechanism);
	}
	
	/**
	 * Gets the birth date.
	 * 
	 * @return the birth date
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Sets the birth date.
	 * 
	 * @param birthDate the new birth date
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Gets the administrative gender code.
	 * 
	 * @return the administrative gender code
	 */
	public String getAdministrativeGenderCode() {
		return administrativeGenderCode;
	}

	/**
	 * Sets the administrative gender code.
	 * 
	 * @param administritativeGenderCode the new administrative gender code
	 */
	public void setAdministrativeGenderCode(String administritativeGenderCode) {
		this.administrativeGenderCode = administritativeGenderCode;
	}

	/**
	 * Gets the ethnic group code.
	 * 
	 * @return the ethnic group code
	 */
	public String getEthnicGroupCode() {
		return ethnicGroupCode;
	}

	/**
	 * Sets the ethnic group code.
	 * 
	 * @param ethnicGroupCode the new ethnic group code
	 */
	public void setEthnicGroupCode(String ethnicGroupCode) {
		this.ethnicGroupCode = ethnicGroupCode;
	}

	/**
	 * Gets the race code.
	 * 
	 * @return the race code
	 */
	public String getRaceCode() {
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

	/**
	 * Sets the race code.
	 * 
	 * @param raceCode the new race code
	 */
	public void setRaceCode(String raceCode) {
		raceCodes = new ArrayList<RaceCode>();
		if (!StringUtils.isBlank(raceCode)) {
			StringTokenizer tokenizer = new StringTokenizer(raceCode, " : ");
			while (tokenizer.hasMoreTokens()) {
				RaceCode r = (RaceCode) Enum.valueOf(RaceCode.class, tokenizer
						.nextToken());
				raceCodes.add(r);
			};
		}
	}

	/**
	 * Gets the race codes.
	 * 
	 * @return the race codes
	 */
	@Transient
	public List<RaceCode> getRaceCodes() {
		return raceCodes;
	}

	/**
	 * Sets the race codes.
	 * 
	 * @param raceCodes the new race codes
	 */
	public void setRaceCodes(List<RaceCode> raceCodes) {
		this.raceCodes = raceCodes;
	}

	/**
	 * Gets the birth date str.
	 * 
	 * @return the birth date str
	 */
	@Transient
	public String getBirthDateStr() {
		if (birthDate == null ||birthDate.equals("") ) {
			return "";
		}
		return DateUtil.formatDate(birthDate, "MM/dd/yyyy");
	}

	/**
	 * Gets the mRN.
	 * 
	 * @return the mRN
	 */
	@Transient
	public OrganizationAssignedIdentifier getMRN() {
		for (OrganizationAssignedIdentifier orgIdentifier : this
				.getOrganizationAssignedIdentifiers()) {
			// null check on the orgIdentifier is required as sometimes the projected list may contain null objects in the middle of the list.
			if (orgIdentifier!=null && orgIdentifier.getType() != null && orgIdentifier.getType().equals(OrganizationIdentifierTypeEnum.MRN)) 
				return orgIdentifier;
		}
		return null;
	}

	/**
	 * Gets the marital status code.
	 * 
	 * @return the marital status code
	 */
	public String getMaritalStatusCode() {
		return maritalStatusCode;
	}

	/**
	 * Sets the marital status code.
	 * 
	 * @param maritalStatusCode the new marital status code
	 */
	public void setMaritalStatusCode(String maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}

	/**
	 * Sets the study subjects.
	 * 
	 * @param studySubjects the new study subjects
	 */
	/*public void setStudySubjects(List<StudySubject> studySubjects) {
		this.studySubjects = studySubjects;
	}*/

	/**
	 * Adds the study subject.
	 * 
	 * @param studySubject the study subject
	 */
	/*public void addStudySubject(StudySubject studySubject) {
		studySubjects.add(studySubject);
	}*/

	/**
	 * Removes the study subject.
	 * 
	 * @param studySubject the study subject
	 */
	/*public void removeStudySubject(StudySubject studySubject) {
		studySubjects.remove(studySubject);
	}*/

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Participant o) {
		if (this.equals(o)) return 0;
		return 1;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.Person#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((getMRN() == null) ? 0 : getMRN().hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.Person#equals(java.lang.Object)
	 */
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

	/**
	 * Gets the primary identifier value.
	 * 
	 * @return the primary identifier value
	 */
	@Transient
	public String getPrimaryIdentifierValue() {
		return getPrimaryIdentifier()==null ? null : getPrimaryIdentifier().getValue();
	}
	
	/**
	 * Gets the primary identifier value.
	 * 
	 * @return the primary identifier value
	 */
	@Transient
	public Identifier getPrimaryIdentifier() {
		for (Identifier identifier : getIdentifiers()) {
			if (identifier.getPrimaryIndicator()) {
				return identifier;
			}
		}
		return null;
	}

	/**
	 * Sets the primary identifier.
	 * 
	 * @param primaryIdentifier the new primary identifier
	 */
	public void setPrimaryIdentifier(String primaryIdentifier) {
	}

	/**
	 * Runs basic validation on the Participant object.
	 * Moved here from the service during the refactoring effort.
	 * 
	 * @return true, if validate participant
	 */
	public boolean validateParticipant() {
		if (StringUtils.getBlankIfNull(this.getFirstName()).equals("")
				|| StringUtils.getBlankIfNull(this.getLastName()).equals("")
				|| this.getBirthDate() == null
				|| StringUtils.getBlankIfNull(this.getAdministrativeGenderCode())
				.equals("")) return false;
		return true;
	}

	/**
	 * Gets the healthcare sites.
	 * 
	 * @return the healthcare sites
	 */
	@ManyToMany
	@Cascade(value = {CascadeType.LOCK})
    @JoinTable(name="prt_org_associations",
        joinColumns = @JoinColumn(name="prt_id"),
        inverseJoinColumns = @JoinColumn(name="org_id")
    )
    public List<HealthcareSite> getHealthcareSites() {
        return healthcareSites;
    }

    /**
     * Sets the healthcare sites.
     * 
     * @param healthcareSites the new healthcare sites
     */
    public void setHealthcareSites(List<HealthcareSite> healthcareSites) {
    	this.healthcareSites = healthcareSites;
    }
    
    /**
     * Gets the custom fields internal.
     * 
     * @return the custom fields internal
     */
    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<CustomField> getCustomFieldsInternal() {
		return lazyListHelper.getInternalList(CustomField.class);
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.customfield.Customizable#getCustomFields()
	 */
	@Transient
	public List<CustomField> getCustomFields() {
		return lazyListHelper.getLazyList(CustomField.class);
	}

	/**
	 * Sets the custom fields internal.
	 * 
	 * @param customFields the new custom fields internal
	 */
	public void setCustomFieldsInternal(List<CustomField> customFields) {
		lazyListHelper.setInternalList(CustomField.class,customFields);
	}

	/**
	 * Adds the custom field.
	 * 
	 * @param customField the custom field
	 */
	public void addCustomField(CustomField customField) {
		this.getCustomFields().add(customField);
		customField.setParticipant(this);
	}
	
	public StudySubjectDemographics createStudySubjectDemographics(){
		StudySubjectDemographics studySubjectDemographics = new StudySubjectDemographics();
		
		// copy basic details
		studySubjectDemographics.setFirstName(this.getFirstName());
		studySubjectDemographics.setLastName(this.getLastName());
		studySubjectDemographics.setMiddleName(this.getMiddleName());
		studySubjectDemographics.setMaidenName(this.getMaidenName());
		studySubjectDemographics.setAdministrativeGenderCode(this.getAdministrativeGenderCode());
		studySubjectDemographics.setEthnicGroupCode(this.getEthnicGroupCode());
		studySubjectDemographics.setRaceCode(this.getRaceCode());
		studySubjectDemographics.setBirthDate(this.getBirthDate());
		
		// copy address
		
		Address addressCopy = new Address();
		addressCopy.setStreetAddress(this.getAddress().getStreetAddress());
		addressCopy.setCity(this.getAddress().getCity());
		addressCopy.setStateCode(this.getAddress().getStateCode());
		addressCopy.setCountryCode(this.getAddress().getCountryCode());
		addressCopy.setPostalCode(this.getAddress().getPostalCode());
		
		studySubjectDemographics.setAddress(addressCopy);
		
		// copy contact mechanisms
		
		for(ContactMechanism contactMechanism : this.getContactMechanisms()){
			ContactMechanism contactMechanismCopy = new ContactMechanism();
			contactMechanismCopy.setType(contactMechanism.getType());
			contactMechanismCopy.setValue(contactMechanism.getValue());
			
			studySubjectDemographics.addContactMechanism(contactMechanismCopy);
		}
		
		// copy identifiers
		
		for(Identifier identifier : this.getIdentifiers()){
			if(identifier instanceof OrganizationAssignedIdentifier){
				OrganizationAssignedIdentifier orgIdentifier = (OrganizationAssignedIdentifier) identifier;
				OrganizationAssignedIdentifier orgIdentifierCopy = new OrganizationAssignedIdentifier();
				orgIdentifierCopy.setHealthcareSite(orgIdentifier.getHealthcareSite());
				orgIdentifierCopy.setType(orgIdentifier.getType());
				orgIdentifierCopy.setValue(orgIdentifier.getValue());
				orgIdentifierCopy.setPrimaryIndicator(orgIdentifier.getPrimaryIndicator());
				
				studySubjectDemographics.addIdentifier(orgIdentifierCopy);
				
			}else if (identifier instanceof SystemAssignedIdentifier){
				SystemAssignedIdentifier sysIdentifier = (SystemAssignedIdentifier) identifier;
				SystemAssignedIdentifier sysIdentifierCopy = new SystemAssignedIdentifier();
				sysIdentifierCopy.setSystemName(sysIdentifier.getSystemName());
				sysIdentifierCopy.setType(sysIdentifier.getType());
				sysIdentifierCopy.setValue(sysIdentifier.getValue());
				sysIdentifierCopy.setPrimaryIndicator(sysIdentifier.getPrimaryIndicator());
				
				studySubjectDemographics.addIdentifier(sysIdentifierCopy);
			}
		}
		
		// copy custom fields
		
		for(CustomField customField:this.getCustomFields()){
			if(customField instanceof BooleanCustomField){
				BooleanCustomField booleanCustomField = (BooleanCustomField) customField;
				BooleanCustomField booleanCustomFieldCopy = new BooleanCustomField();
				booleanCustomFieldCopy.setCustomFieldDefinition(booleanCustomField.getCustomFieldDefinition());
				booleanCustomFieldCopy.setValue(booleanCustomField.getValue());
				studySubjectDemographics.addCustomField(booleanCustomFieldCopy);
			}else if(customField instanceof DateCustomField){
				DateCustomField dateCustomField = (DateCustomField) customField;
				DateCustomField dateCustomFieldCopy = new DateCustomField();
				dateCustomFieldCopy.setCustomFieldDefinition(dateCustomField.getCustomFieldDefinition());
				dateCustomFieldCopy.setValue(dateCustomField.getValue());
				studySubjectDemographics.addCustomField(dateCustomFieldCopy);
			} else if(customField instanceof IntegerCustomField){
				IntegerCustomField integerCustomField = (IntegerCustomField) customField;
				IntegerCustomField integerCustomFieldCopy = new IntegerCustomField();
				integerCustomFieldCopy.setCustomFieldDefinition(integerCustomField.getCustomFieldDefinition());
				integerCustomFieldCopy.setValue(integerCustomField.getValue());
				studySubjectDemographics.addCustomField(integerCustomFieldCopy);
			}else if(customField instanceof StringCustomField){
				StringCustomField stringCustomField = (StringCustomField) customField;
				StringCustomField stringCustomFieldCopy = new StringCustomField();
				stringCustomField.setCustomFieldDefinition(stringCustomField.getCustomFieldDefinition());
				stringCustomField.setValue(stringCustomField.getValue());
				studySubjectDemographics.addCustomField(stringCustomFieldCopy);
			}
		}
		
		// add to Participant and set association both ways
		
		this.addStudySubjectDemographics(studySubjectDemographics);
		
		return studySubjectDemographics;
	}
	
	
	public void synchronizeWithStudySubjectDemographics(StudySubjectDemographics studySubjectDemographics){
		// copy basic details
		this.setFirstName(studySubjectDemographics.getFirstName());
		this.setLastName(studySubjectDemographics.getLastName());
		this.setMiddleName(studySubjectDemographics.getMiddleName());
		this.setMaidenName(studySubjectDemographics.getMaidenName());
		this.setAdministrativeGenderCode(studySubjectDemographics.getAdministrativeGenderCode());
		this.setEthnicGroupCode(studySubjectDemographics.getEthnicGroupCode());
		this.setRaceCode(studySubjectDemographics.getRaceCode());
		this.setBirthDate(studySubjectDemographics.getBirthDate());
		
		// copy address
		Address addressCopy = new Address();
		addressCopy.setStreetAddress(studySubjectDemographics.getAddress().getStreetAddress());
		addressCopy.setCity(studySubjectDemographics.getAddress().getCity());
		addressCopy.setStateCode(studySubjectDemographics.getAddress().getStateCode());
		addressCopy.setCountryCode(studySubjectDemographics.getAddress().getCountryCode());
		addressCopy.setPostalCode(studySubjectDemographics.getAddress().getPostalCode());
		
		this.setAddress(addressCopy);
		
		// copy the contact mechanisms 
		this.setEmail(studySubjectDemographics.getEmail());
		this.setPhone(studySubjectDemographics.getPhone());
		this.setFax(studySubjectDemographics.getFax());
		
		// reset the existing identifiers of the participant by setting it to an empty list
		this.identifiers = new ArrayList<Identifier>();
		
		// copy identifiers from passed in demographics
		
		for(Identifier identifier : studySubjectDemographics.getIdentifiers()){
			if(identifier instanceof OrganizationAssignedIdentifier){
				OrganizationAssignedIdentifier orgIdentifier = (OrganizationAssignedIdentifier) identifier;
				OrganizationAssignedIdentifier orgIdentifierCopy = new OrganizationAssignedIdentifier();
				orgIdentifierCopy.setHealthcareSite(orgIdentifier.getHealthcareSite());
				orgIdentifierCopy.setType(orgIdentifier.getType());
				orgIdentifierCopy.setValue(orgIdentifier.getValue());
				orgIdentifierCopy.setPrimaryIndicator(orgIdentifier.getPrimaryIndicator());
				
				this.addIdentifier(orgIdentifierCopy);
				
			}else if (identifier instanceof SystemAssignedIdentifier){
				SystemAssignedIdentifier sysIdentifier = (SystemAssignedIdentifier) identifier;
				SystemAssignedIdentifier sysIdentifierCopy = new SystemAssignedIdentifier();
				sysIdentifierCopy.setSystemName(sysIdentifier.getSystemName());
				sysIdentifierCopy.setType(sysIdentifier.getType());
				sysIdentifierCopy.setValue(sysIdentifier.getValue());
				sysIdentifierCopy.setPrimaryIndicator(sysIdentifier.getPrimaryIndicator());
				
				this.addIdentifier(sysIdentifierCopy);
			}
		}
	}
	
}