package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.domain.customfield.CustomField;
import edu.duke.cabig.c3pr.domain.customfield.Customizable;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedBiDirectionalInstantiateFactory;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedInstantiateFactory;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.ProjectedList;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@Table(name = "stu_sub_demographics")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "stu_sub_demographics_id_seq") })
public class StudySubjectDemographics extends AbstractMutableDeletableDomainObject implements Customizable,IdentifiableObject{
	
	 private String firstName;

    private String lastName;

    private String maidenName;

    private String middleName;
    

    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	 @Transient
    public String getFullName() {
        String fullName = this.getFirstName() ;
        if(StringUtils.isNotBlank(getMiddleName())){
        	fullName += " "+ this.getMiddleName();
        }
        fullName +=  " " + this.getLastName() ; 
        return fullName ;
    }

	@Transient
	public Address getAddress() {
		if (this.address == null) {
	            this.address = new Address();
	        }
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	@OneToOne
    @Cascade(value = { CascadeType.ALL })
    @JoinColumn(name = "add_id", nullable = true)
    public Address getAddressInternal() {

        if (this.getAddress().isBlank()) return null;
        return this.address;

    }
    
    public void setAddressInternal(Address address){
    	this.address = address;
    }

	private Address address;

    protected List<ContactMechanism> contactMechanisms = new ArrayList<ContactMechanism>();

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
	
	@OneToMany(mappedBy = "studySubjectDemographics",fetch=FetchType.EAGER)
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

	@ManyToOne
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
	@OneToMany(fetch = FetchType.LAZY)
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

	@OneToMany
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "stu_sub_dmgphcs_id")
	@OrderBy("id")
	public List<ContactMechanism> getContactMechanisms() {
		return contactMechanisms;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.Person#setContactMechanisms(java.util.List)*/
	 
	public void setContactMechanisms(List<ContactMechanism> contactMechanisms) {
		this.contactMechanisms = contactMechanisms;
	}
	
	public void addContactMechanism(ContactMechanism contactMechanism){
		getContactMechanisms().add(contactMechanism);
	}
	
	  @Transient
		public String getEmail(){
			for(ContactMechanism contactMechanism: getContactMechanisms()){
				if(contactMechanism.getType()==ContactMechanismType.EMAIL){
					return contactMechanism.getValue();
				}
			}
			return null;
		}
		
		@Transient
		public String getPhone(){
			for(ContactMechanism contactMechanism: getContactMechanisms()){
				if(contactMechanism.getType()==ContactMechanismType.PHONE){
					return contactMechanism.getValue();
				}
			}
			return null;
		}
		
		@Transient
		public String getFax(){
			for(ContactMechanism contactMechanism: getContactMechanisms()){
				if(contactMechanism.getType()==ContactMechanismType.Fax){
					return contactMechanism.getValue();
				}
			}
			return null;
		}
		
		 @Transient
		    private ContactMechanism getContactMechanism(ContactMechanismType type) {
		        for(ContactMechanism contactMechanism: getContactMechanisms()){
		        	if(contactMechanism.getType() == type)
		        		return contactMechanism;
		        }
		        return null;
		    }
		
		private void setContactMechanism(String value, ContactMechanismType contactMechanismType, boolean local){
			ContactMechanism contactMechanism= getContactMechanism(contactMechanismType);
			if(StringUtils.getBlankIfNull(value).equals("")){
				if(contactMechanism !=null){
					contactMechanisms.remove(contactMechanism);
				}
				return;
			}
			if(contactMechanismType == null){
				throw new NullPointerException("Cannot set empty contact mechanism type");
			}
			if(contactMechanismType == ContactMechanismType.EMAIL && !StringUtils.isValidEmail(value)){
				throw new IllegalArgumentException("Invalid email address");
			}
			if(contactMechanismType == ContactMechanismType.PHONE && !StringUtils.isValidPhone(value)){
				throw new IllegalArgumentException("Invalid phone number");
			}
			if(contactMechanismType == ContactMechanismType.Fax && !StringUtils.isValidFax(value)){
				throw new IllegalArgumentException("Invalid fax number");
			}
			if(contactMechanism == null){
				if(local){
					contactMechanism = new LocalContactMechanism();
				}else{
					contactMechanism = new RemoteContactMechanism();
				}
				contactMechanism.setType(contactMechanismType);
				contactMechanisms.add(contactMechanism);
			}
			contactMechanism.setValue(value);
		}
		
		public void setEmail(String email){
			setContactMechanism(email, ContactMechanismType.EMAIL, true);
		}
		
		public void setPhone(String phone){
			setContactMechanism(phone, ContactMechanismType.PHONE, true);
		}
		
		public void setFax(String fax){
			setContactMechanism(fax, ContactMechanismType.Fax, true);
		}
		
		public void setRemoteEmail(String email){
			setContactMechanism(email, ContactMechanismType.EMAIL, false);
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
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name="stu_sub_dmgphcs_id")
	public List<RaceCodeAssociation> getRaceCodeAssociations() {
		return raceCodeAssociations;
	}
	  
	public void addRaceCodeAssociation(RaceCodeAssociation raceCodeAssociation) {
		getRaceCodeAssociations().add(raceCodeAssociation);
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
	
}