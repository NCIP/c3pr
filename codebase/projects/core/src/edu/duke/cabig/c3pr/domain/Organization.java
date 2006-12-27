package edu.duke.cabig.c3pr.domain;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;


/**
 * @author Ram Chilukuri
 */

@MappedSuperclass
public abstract class Organization extends AbstractDomainObject {
	
	@Column(name = "NAME", length = 20, nullable = false)
    private String name;
	
	@Column(name = "DESCRIPTION_TEXT", length = 50, nullable = false)
    private String descriptionText;

	@OneToOne(optional=false)
    @JoinColumn(name="ADDRESS_ID")
    private Address address;
	
    public Organization() {
    }

    // Bean Methods
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public Address getAddress() {
        return address;
    }
}
