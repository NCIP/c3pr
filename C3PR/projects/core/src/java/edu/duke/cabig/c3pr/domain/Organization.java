package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;

import com.semanticbits.coppa.domain.annotations.RemoteProperty;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.constants.EndPointType;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedInstantiateFactory;
import edu.duke.cabig.c3pr.utils.ProjectedList;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * @author Ram Chilukuri Kulasekaran
 */
@MappedSuperclass
public abstract class Organization extends AbstractMutableDeletableDomainObject implements
                Comparable<HealthcareSite> {

    private String name;

    private String descriptionText;

    private Address address;

    private List<StudyOrganization> studyOrganizations = new ArrayList<StudyOrganization>();

    private List<OrganizationAssignedIdentifier> identifiers = new ArrayList<OrganizationAssignedIdentifier>();
    
    private List<Identifier> identifiersAssignedToOrganization = new ArrayList<Identifier>();
    
    private EndPointConnectionProperty studyEndPointProperty;
    
    private EndPointConnectionProperty registrationEndPointProperty;
    
    protected List<ContactMechanism> contactMechanisms = new ArrayList<ContactMechanism>();

    public void addContactMechanism(ContactMechanism contactMechanism) {
        contactMechanisms.add(contactMechanism);
    }

    public void removeContactMechanism(ContactMechanism contactMechanism) {
        contactMechanisms.remove(contactMechanism);
    }
    
    public void setContactMechanisms(List<ContactMechanism> contactMechanisms) {
        this.contactMechanisms = contactMechanisms;
    }

    @OneToMany
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "ORG_ID")
    @OrderBy("id")
    public List<ContactMechanism> getContactMechanisms() {
        return contactMechanisms;
    }
    
    @Transient
	public String getEmailAsString(){
		for(ContactMechanism contactMechanism: getContactMechanisms()){
			if(contactMechanism.getType()==ContactMechanismType.EMAIL){
				return contactMechanism.getValue();
			}
		}
		return null;
	}
	
	@Transient
	public String getPhoneAsString(){
		for(ContactMechanism contactMechanism: getContactMechanisms()){
			if(contactMechanism.getType()==ContactMechanismType.PHONE){
				return contactMechanism.getValue();
			}
		}
		return null;
	}
	
	@Transient
	public String getFaxAsString(){
		for(ContactMechanism contactMechanism: getContactMechanisms()){
			if(contactMechanism.getType()==ContactMechanismType.Fax){
				return contactMechanism.getValue();
			}
		}
		return null;
	}
	
	public void setEmail(String email){
		ContactMechanism emailContactMechanism = new LocalContactMechanism();
		emailContactMechanism.setType(ContactMechanismType.EMAIL);
		emailContactMechanism.setValue(email);
		this.addContactMechanism(emailContactMechanism);
	}
	
	public void setPhone(String phone){
		ContactMechanism phoneContactMechanism = new LocalContactMechanism();
		phoneContactMechanism.setType(ContactMechanismType.PHONE);
		phoneContactMechanism.setValue(phone);
		this.addContactMechanism(phoneContactMechanism);
	}
	
	public void setFax(String fax){
		ContactMechanism faxContactMechanism = new LocalContactMechanism();
		faxContactMechanism.setType(ContactMechanismType.Fax);
		faxContactMechanism.setValue(fax);
		this.addContactMechanism(faxContactMechanism);
	}
	
    
    private LazyListHelper lazyListHelper;
    
    public Organization() {
        address = new Address();
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(PlannedNotification.class, new InstantiateFactory<PlannedNotification>(
        		PlannedNotification.class));
		lazyListHelper.add(OrganizationAssignedIdentifier.class,
				new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(
						OrganizationAssignedIdentifier.class));
		lazyListHelper.add(SystemAssignedIdentifier.class,
				new ParameterizedInstantiateFactory<SystemAssignedIdentifier>(
						SystemAssignedIdentifier.class));
        setIdentifiersAssignedToOrganization(new ArrayList<Identifier>());
    }

    @RemoteProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    @OneToMany(mappedBy = "healthcareSite", fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.DELETE, CascadeType.DELETE_ORPHAN })
    public List<StudyOrganization> getStudyOrganizations() {
        return studyOrganizations;
    }

    public void setStudyOrganizations(List<StudyOrganization> studyOrganizations) {
        this.studyOrganizations = studyOrganizations;
    }

    /**
     * These are the identifiers assigned BY this organization to other entities like 
     * study, person or maybe even organization.
     * @return
     */
    @OneToMany(mappedBy = "healthcareSite", fetch = FetchType.LAZY)
   // @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<OrganizationAssignedIdentifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<OrganizationAssignedIdentifier> identifiers) {
        this.identifiers = identifiers;
    }
    
	
    /**
	 * Gets the identifiers assigned TO the organization.
	 * 
	 * @return the identifiers
	 */
	@OneToMany(fetch = FetchType.LAZY)
	@Cascade({CascadeType.MERGE, CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "ORG_ID")
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy
	public List<Identifier> getIdentifiersAssignedToOrganization() {
		return identifiersAssignedToOrganization;
	}
	
	/**
	 * Adds the identifier to the organization.
	 * 
	 * @return the identifiers
	 */
	public void addIdentifiersAssignedToOrganization(Identifier identifier) {
		identifiersAssignedToOrganization.add(identifier);
	}

	/**
	 * Sets the identifiers assigned TO the organization.
	 * 
	 * @param identifiers the new identifiers
	 */
	private void setIdentifiersAssignedToOrganization(List<Identifier> identifiers) {
		this.identifiersAssignedToOrganization = identifiers;
		// initialize projected list for OrganizationAssigned and
		// SystemAssignedIdentifier
		lazyListHelper.setInternalList(OrganizationAssignedIdentifier.class,
				new ProjectedList<OrganizationAssignedIdentifier>(
					this.identifiersAssignedToOrganization,
						OrganizationAssignedIdentifier.class));
		lazyListHelper.setInternalList(SystemAssignedIdentifier.class,
				new ProjectedList<SystemAssignedIdentifier>(
					this.identifiersAssignedToOrganization,
						SystemAssignedIdentifier.class));
	}

	/**
	 * Gets the system assigned identifiers assigned TO the organization.
	 * 
	 * @return the system assigned identifiers
	 */
	@Transient
	public List<SystemAssignedIdentifier> getSystemAssignedIdentifiers() {
		return lazyListHelper.getLazyList(SystemAssignedIdentifier.class);
	}

	/**
	 * Gets the organization assigned identifiers assigned TO the organization.
	 * 
	 * @return the organization assigned identifiers
	 */
	@Transient
	public List<OrganizationAssignedIdentifier> getOrganizationAssignedIdentifiers() {
		return lazyListHelper.getLazyList(OrganizationAssignedIdentifier.class);
	}
    

    @OneToOne(cascade = { javax.persistence.CascadeType.ALL }, optional = false)
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Transient
    public String getTrimmedName() {
        return StringUtils.getTrimmedText(name, 25);
    }

    public int compareTo(Organization o) {
        if (this.equals((Organization) o)) return 0;

        return 1;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Organization other = (Organization) obj;
        if (name == null) {
            if (other.name != null) return false;
        }
        else if (!name.equals(other.name)) return false;
        return true;
    }

    @OneToMany(mappedBy="healthcareSite", fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @Where(clause="retired_indicator = 'false'")
 	public List<PlannedNotification> getPlannedNotificationsInternal() {
        return lazyListHelper.getInternalList(PlannedNotification.class);
    }

    public void setPlannedNotificationsInternal(List<PlannedNotification> plannedNotifications) {
        lazyListHelper.setInternalList(PlannedNotification.class, plannedNotifications);
    }

    @Transient
    public List<PlannedNotification> getPlannedNotifications() {
        return lazyListHelper.getLazyList(PlannedNotification.class);
    }

    @OneToOne(cascade = { javax.persistence.CascadeType.ALL })
    @JoinColumn(name = "study_endpoint_props_id")
    public EndPointConnectionProperty getStudyEndPointProperty() {
        return studyEndPointProperty;
    }

    public void setStudyEndPointProperty(EndPointConnectionProperty studyEndPointProperty) {
        this.studyEndPointProperty = studyEndPointProperty;
    }

    @OneToOne(cascade = { javax.persistence.CascadeType.ALL })
    @JoinColumn(name = "reg_endpoint_props_id")
    public EndPointConnectionProperty getRegistrationEndPointProperty() {
        return registrationEndPointProperty;
    }

    public void setRegistrationEndPointProperty(EndPointConnectionProperty registrationEndPointProperty) {
        this.registrationEndPointProperty = registrationEndPointProperty;
    }

    @Transient
    public boolean getEndPointAuthenticationRequired(){
        return this.getHasEndpointProperty() && this.studyEndPointProperty.getIsAuthenticationRequired() && this.registrationEndPointProperty.getIsAuthenticationRequired();
    }
    
    @Transient
    public void setEndPointAuthenticationRequired(boolean endPointAuthenticationRequired) {
        this.studyEndPointProperty.setIsAuthenticationRequired(endPointAuthenticationRequired);
        this.registrationEndPointProperty.setIsAuthenticationRequired(endPointAuthenticationRequired);
    }
    
    @Transient
    public boolean getHasEndpointProperty(){
        return studyEndPointProperty!=null && registrationEndPointProperty!=null;
    }
    
    public void initializeEndPointProperties(EndPointType endPointType){
        registrationEndPointProperty=new EndPointConnectionProperty(endPointType);
        studyEndPointProperty=new EndPointConnectionProperty(endPointType);
    }
}