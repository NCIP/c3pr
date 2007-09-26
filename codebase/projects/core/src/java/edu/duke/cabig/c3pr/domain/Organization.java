package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


/**
 * @author Ram Chilukuri
 *         Kulasekaran
 */
@MappedSuperclass
public abstract class Organization extends AbstractMutableDeletableDomainObject implements Comparable<HealthcareSite> {

    private String name;

    private String descriptionText;

    private Address address;

    private String trimmedName;

    private List<StudyOrganization> studyOrganizations = new ArrayList<StudyOrganization>();
    private List<OrganizationAssignedIdentifier> identifiers = new ArrayList<OrganizationAssignedIdentifier>();



    public Organization() {
        address = new Address();
    }

    public Organization(boolean initialise) {
        address = new Address();
    }

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
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<StudyOrganization> getStudyOrganizations() {
        return studyOrganizations;
    }

    public void setStudyOrganizations(List<StudyOrganization> studyOrganizations) {
        this.studyOrganizations = studyOrganizations;
    }

    @OneToMany(mappedBy = "healthcareSite", fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<OrganizationAssignedIdentifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<OrganizationAssignedIdentifier> identifiers) {
        this.identifiers = identifiers;
    }

    @OneToOne(cascade={javax.persistence.CascadeType.ALL}, optional=false)
    @JoinColumn(name="ADDRESS_ID" ,nullable=false)
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
    
    public int compareTo(HealthcareSite o) {
		if (this.equals((HealthcareSite)o))
			return 0;

		return 1;
	}

/*    public int compareTo(Object o) {
		if (this.equals((Organization)o))
			return 0;

		return 1;
	}*/

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Organization other = (Organization) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}