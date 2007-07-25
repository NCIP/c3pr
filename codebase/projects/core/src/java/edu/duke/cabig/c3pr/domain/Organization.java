package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

//import javax.persistence.CascadeType;
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
public abstract class Organization extends AbstractMutableDomainObject {
		
    private String name;
		
    private String descriptionText;

    private Address address;
    
    private String trimmedName;
    
    private List<StudyOrganization> studyOrganizations = new ArrayList<StudyOrganization>();
    
    @OneToMany(mappedBy = "healthcareSite", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<StudyOrganization> getStudyOrganizations() {
		return studyOrganizations;
	}

	public void setStudyOrganizations(List<StudyOrganization> studyOrganizations) {
		this.studyOrganizations = studyOrganizations;
	}
	
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

}