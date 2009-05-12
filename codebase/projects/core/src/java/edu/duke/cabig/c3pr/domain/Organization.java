package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;

import com.semanticbits.coppa.domain.annotations.RemoteProperty;

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
    
    private EndPointConnectionProperty studyEndPointProperty;
    
    private EndPointConnectionProperty registrationEndPointProperty;
    
    private LazyListHelper lazyListHelper;
    
    public Organization() {
        address = new Address();
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(PlannedNotification.class, new InstantiateFactory<PlannedNotification>(
        		PlannedNotification.class));
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

    @OneToMany(mappedBy = "healthcareSite", fetch = FetchType.LAZY)
   // @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<OrganizationAssignedIdentifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<OrganizationAssignedIdentifier> identifiers) {
        this.identifiers = identifiers;
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