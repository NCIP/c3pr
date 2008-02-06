package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * Base Class for Person
 * @author Priyatam
 */

@MappedSuperclass
public abstract class Person extends AbstractMutableDeletableDomainObject
{			
	private String firstName;
	private String lastName;
	private String maidenName;
	private String middleName;
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
	
	@Transient
	public Address getAddress() {
		if (this.address==null){
		this.address=new Address();
		}
		return this.address;
	}
	
	@OneToOne
	@Cascade(value = { CascadeType.ALL})
    @JoinColumn(name="ADD_ID" ,nullable=true)
	public Address getAddressInternal(){
		
		if(this.getAddress().isBlank()) return null;
		return this.address;
		
	}
	
	private void setAddressInternal(Address address){
		this.address=address;
	}
		
	public void setAddress(Address address) {
        this.address = address;
    }
		
	@Transient
	public void fillAddress(Address address){
		getAddress().setStreetAddress(address.getStreetAddress());
		getAddress().setCity(address.getCity());
		getAddress().setStateCode(address.getStateCode());
		getAddress().setCountryCode(address.getCountryCode());
		getAddress().setPostalCode(address.getPostalCode());
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	@Transient
	public String getFullName(){
		return this.getLastName()+ " "+this.getFirstName()+", "+StringUtils.getBlankIfNull(this.getMiddleName());
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = PRIME * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = PRIME * result + ((maidenName == null) ? 0 : maidenName.hashCode());
		result = PRIME * result + ((middleName == null) ? 0 : middleName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		final Person other = (Person) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (maidenName == null) {
			if (other.maidenName != null)
				return false;
		} else if (!maidenName.equals(other.maidenName))
			return false;
		if (middleName == null) {
			if (other.middleName != null)
				return false;
		} else if (!middleName.equals(other.middleName))
			return false;
		return true;
	}

	
}