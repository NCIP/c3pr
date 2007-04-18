package edu.duke.cabig.c3pr.domain;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Base Class for Person
 * @author Priyatam
 */

@MappedSuperclass
public abstract class Person extends AbstractGridIdentifiableDomainObject
{			
	private String firstName;
	private String lastName;
	private Address address;  
	
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
	
	@OneToOne
	@Cascade(value = { CascadeType.ALL})
    @JoinColumn(name="ADD_ID" ,nullable=true)
    public Address getAddress() {
        return address;
    }
		
	public void setAddress(Address address) {
        this.address = address;
    }
	
}