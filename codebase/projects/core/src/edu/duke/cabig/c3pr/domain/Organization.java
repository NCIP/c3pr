package edu.duke.cabig.c3pr.domain;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;


/**
 * @author Ram Chilukuri
 *         Kulasekaran
 */
@MappedSuperclass
public abstract class Organization extends AbstractDomainObject {
		
    private String name;
		
    private String descriptionText;

    private Address address;
	
    public Organization() {
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

    @OneToOne(cascade={CascadeType.ALL}, optional=false)
    @JoinColumn(name="ADDRESS_ID" ,nullable=false)
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    } 
}
