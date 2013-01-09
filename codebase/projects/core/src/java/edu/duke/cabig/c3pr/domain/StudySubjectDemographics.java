/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.domain.customfield.CustomField;
import edu.duke.cabig.c3pr.domain.customfield.Customizable;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedBiDirectionalInstantiateFactory;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedInstantiateFactory;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.ProjectedList;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@Table(name = "stu_sub_demographics")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "stu_sub_demographics_id_seq") })
public class StudySubjectDemographics extends PersonBase implements Customizable,IdentifiableObject{
	
	
	@OneToMany
	@Fetch(FetchMode.SUBSELECT)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "stu_sub_demographics_id")
	@OrderBy("id")
	public Set<Address> getAddresses() {
		if (this.address!=null && !this.address.isBlank() && !addresses.contains(this.address)) {
			addresses.add(this.address);
		}
		return addresses;
	}

	/**
	 * <b>Avoid calling this method directly from your code because it might
	 * lead to problems with Hibernate upon saving due to usage of
	 * CascadeType.DELETE_ORPHAN in {@link #getAddresses()}. See <a
	 * href="http://www.sleberknight.com/blog/sleberkn/entry/20070329"
	 * >http://www.sleberknight.com/blog/sleberkn/entry/20070329</a>. </b> <br>
	 * <br>
	 * Use {@link #replaceAddresses(Set)} instead.
	 * 
	 * @param addresses
	 */
	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}
	
	/**
	 * Erases all current addresses and inserts new ones. 
	 * @param addresses
	 */
	public void replaceAddresses(Set<Address> addresses) {
		this.addresses.clear();
		this.addresses.addAll(addresses);
	}
	
	public void addAddress(Address newAddress){
		getAddresses().add(newAddress);
	}
	
	// The following set of methods is kept for backward compatibility only.
	// See http://jira.semanticbits.com/browse/CPR-2098.
	// Methods will be removed eventually.
	// -- dkrylov

	@Transient
	@Deprecated
	public Address getAddress() {
		if (this.address == null) {
			if (CollectionUtils.isNotEmpty(addresses)) {
				this.address = addresses.iterator().next();
			} else {			
				this.address = new Address();
			}
		}
		return this.address;
	}

	@Transient
	@Deprecated
	public Address getAddressInternal() {

		if (this.getAddress().isBlank())
			return null;
		return this.address;

	}
	
	@Transient
	@Deprecated
	public void setAddressInternal(Address address) {
		this.address = address;
	}

	@Transient
	@Deprecated	
	public void setAddress(Address address) {
		this.address = address;
	}
	
    private Set<Address> addresses = new HashSet<Address>();

	private Address address;

	/** The birth date. */
	private Date birthDate;

	/** The administrative gender code. */
	private String administrativeGenderCode;

	/** The ethnic group code. */
	private String ethnicGroupCode;

	/** The race codes. */
	private List<RaceCodeAssociation> raceCodeAssociations;

	/** The marital status code. */
	private String maritalStatusCode;

	/** The registrations. */
	private List<StudySubject> registrations = new ArrayList<StudySubject>();
	
	private Participant masterSubject;


	/** The lazy list helper. */
	private LazyListHelper lazyListHelper;

	/** The identifiers. */
	private List<Identifier> identifiers;
	
	
	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	// flag that indicates whether this demographics record is 'valid' i.e. is this demographics record (or snapshot) 
	// is created officially during a business functionality like screening, reserving, enrolling , off-study or  
	// is created solely for technical purpose when it takes the value 'invalid'.
	private Boolean valid = false;
	
	/**
	 * Instantiates a new demographics.
	 */
	public StudySubjectDemographics()  {
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(OrganizationAssignedIdentifier.class,
				new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(
						OrganizationAssignedIdentifier.class));
		lazyListHelper.add(SystemAssignedIdentifier.class,
				new ParameterizedInstantiateFactory<SystemAssignedIdentifier>(
						SystemAssignedIdentifier.class));
		// mandatory, so that the lazy-projected list is managed properly.
		setIdentifiers(new ArrayList<Identifier>());
		raceCodeAssociations =  new ArrayList<RaceCodeAssociation>();
		lazyListHelper.add(CustomField.class,new ParameterizedBiDirectionalInstantiateFactory<CustomField>(CustomField.class, this));
	}
	
	@OneToMany(mappedBy = "studySubjectDemographics")
	@Cascade (CascadeType.LOCK)
	public List<StudySubject> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<StudySubject> registrations) {
		this.registrations = registrations;
	}
	
	public void addRegistration(StudySubject registration){
		getRegistrations().add(registration);
		registration.setStudySubjectDemographics(this);
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "prt_id", nullable = false)
		public Participant getMasterSubject() {
		return masterSubject;
	}

	public void setMasterSubject(Participant masterSubject) {
		this.masterSubject = masterSubject;
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
	@OneToMany
	@Fetch(FetchMode.SUBSELECT)
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "stu_sub_dmgphcs_id")
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

//	/**
//	 * Gets the race code.
//	 * 
//	 * @return the race code
//	 */
//	public String getRaceCode() {
//		String rCode = "" ;
//		for(RaceCode r : raceCodes){
//			if( r != null ){
//				if(rCode != "" ){
//					rCode =  rCode + " : " + r.getName();
//				}else{
//					rCode = rCode + r.getName();
//				}
//			}
//		}
//		return rCode;
//	}
//
//	/**
//	 * Sets the race code.
//	 * 
//	 * @param raceCode the new race code
//	 */
//	public void setRaceCode(String raceCode) {
//		raceCodes = new ArrayList<RaceCode>();
//		if (!StringUtils.isBlank(raceCode)) {
//			StringTokenizer tokenizer = new StringTokenizer(raceCode, " : ");
//			while (tokenizer.hasMoreTokens()) {
//				RaceCode r = (RaceCode) Enum.valueOf(RaceCode.class, tokenizer
//						.nextToken());
//				raceCodes.add(r);
//			};
//		}
//	}

	/**
	 * Gets the race codes.
	 * 
	 * @return the race codes
	 */
//	  @OneToMany
//	  @JoinTable(name = "race_code_assocn",
//	    joinColumns = {
//	      @JoinColumn(name="stu_sub_dmgphcs_id")           
//	    }
//	  )
//	@ManyToMany
//	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
//	@JoinTable(name = "race_code_assocn", joinColumns = @JoinColumn(name = "stu_sub_dmgphcs_id"), inverseJoinColumns = @JoinColumn(name = "race_code_id")) 
	@OneToMany
	@Fetch(FetchMode.SUBSELECT)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name="stu_sub_dmgphcs_id")
	public List<RaceCodeAssociation> getRaceCodeAssociations() {
		if(raceCodeAssociations == null){
			raceCodeAssociations = new ArrayList<RaceCodeAssociation>();
		}
		return raceCodeAssociations;
	}
	  
	public void addRaceCodeAssociation(RaceCodeAssociation raceCodeAssociation) {
		getRaceCodeAssociations().add(raceCodeAssociation);
	}
	
	@Transient
	public List<RaceCodeEnum> getRaceCodes(){
		List<RaceCodeEnum> raceCodes = new ArrayList<RaceCodeEnum>();
		for(RaceCodeAssociation raceCodeAssociation : getRaceCodeAssociations()){
			raceCodes.add(raceCodeAssociation.getRaceCode());
		}
		return raceCodes;
	}


	/**
	 * Sets the race codes.
	 * 
	 * @param raceCodes the new race codes
	 */
	public void setRaceCodeAssociations(List<RaceCodeAssociation> raceCodeAssociations) {
		this.raceCodeAssociations = raceCodeAssociations;
	}

	/**
	 * @param list
	 */
	@Transient
	public void setRaceCodes(List<RaceCodeEnum> list) {
		this.getRaceCodeAssociations().clear();
		for (RaceCodeEnum raceCodeEnum : list) {
			RaceCodeAssociation association  = new RaceCodeAssociation();
			association.setRaceCode(raceCodeEnum);
			addRaceCodeAssociation(association);			
		}
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
     * Gets the custom fields internal.
     * 
     * @return the custom fields internal
     */
    @OneToMany(mappedBy = "studySubjectDemographics")
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<CustomField> getCustomFieldsInternal() {
		return lazyListHelper.getInternalList(CustomField.class);
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.customfield.Customizable#getCustomFields()*/
	 
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
		customField.setStudySubjectDemographics(this);
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.Person#getContactMechanisms()
	 */
	@OneToMany
	@Fetch(FetchMode.SUBSELECT)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "stu_sub_dmgphcs_id")
	@OrderBy("id")
	public Set<ContactMechanism> getContactMechanisms() {
		return contactMechanisms;
	}
}
