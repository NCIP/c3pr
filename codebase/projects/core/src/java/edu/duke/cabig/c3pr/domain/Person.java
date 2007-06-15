package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

/**
 * Base Class for Person
 * @author Priyatam
 */

@MappedSuperclass
public abstract class Person extends AbstractMutableDomainObject
{			
	private String firstName;
	private String lastName;
	private String maidenName;
	private Address address;  
	protected List<ContactMechanism> contactMechanisms = new ArrayList<ContactMechanism>();
	
	public void addContactMechanism(ContactMechanism contactMechanism)
	{
		contactMechanisms.add(contactMechanism);
	}
	
	public void removeContactMechanism(ContactMechanism contactMechanism)
	{
		contactMechanisms.remove(contactMechanism);
	}
	
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
	
	public void setContactMechanisms(List<ContactMechanism> contactMechanisms)
	{
		this.contactMechanisms = contactMechanisms;
	}
	
	@Transient
	public List<ContactMechanism> getContactMechanisms()
	{
		return contactMechanisms;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}
	
}