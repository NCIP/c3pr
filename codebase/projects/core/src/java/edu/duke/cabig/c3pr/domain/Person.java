package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.semanticbits.coppa.domain.annotations.RemoteProperty;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * Base Class for Person
 * 
 * @author Priyatam
 */

@MappedSuperclass
public abstract class Person extends AbstractMutableDeletableDomainObject {
    private String firstName;

    private String lastName;

    private String maidenName;

    private String middleName;

    private Address address;

    protected List<ContactMechanism> contactMechanisms = new ArrayList<ContactMechanism>();

    public void addContactMechanism(ContactMechanism contactMechanism) {
        contactMechanisms.add(contactMechanism);
    }

    public void removeContactMechanism(ContactMechanism contactMechanism) {
        contactMechanisms.remove(contactMechanism);
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
	
	public void setEmail(String email, ContactMechanism emailContactMechanism){
		if(emailContactMechanism == null){
			emailContactMechanism = new LocalContactMechanism();
		}
		emailContactMechanism.setType(ContactMechanismType.EMAIL);
		emailContactMechanism.setValue(email);
		this.addContactMechanism(emailContactMechanism);
	}
	
	public void setPhone(String phone, ContactMechanism phoneContactMechanism){
		if(phoneContactMechanism == null){
			phoneContactMechanism = new LocalContactMechanism();
		}
		phoneContactMechanism.setType(ContactMechanismType.PHONE);
		phoneContactMechanism.setValue(phone);
		this.addContactMechanism(phoneContactMechanism);
	}
	
	public void setFax(String fax, ContactMechanism faxContactMechanism){
		if(faxContactMechanism == null){
			faxContactMechanism = new LocalContactMechanism();
		}
		faxContactMechanism.setType(ContactMechanismType.Fax);
		faxContactMechanism.setValue(fax);
		this.addContactMechanism(faxContactMechanism);
	}
	
	public void setEmail(String email){
		setEmail(email, new LocalContactMechanism());
	}
	
	public void setPhone(String phone){
		setPhone(phone, new LocalContactMechanism());
	}
	
	public void setFax(String fax){
		setFax(fax, new LocalContactMechanism());
	}
	
	public void setRemoteEmail(String email){
		setEmail(email, new RemoteContactMechanism());
	}
	
	public void setRemotePhone(String phone){
		setPhone(phone, new RemoteContactMechanism());
	}
	
	public void setRemoteFax(String fax){
		setFax(fax, new RemoteContactMechanism());
	}
    
    @RemoteProperty
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    @RemoteProperty
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Transient
    public Address getAddress() {
        if (this.address == null) {
            this.address = new Address();
        }
        return this.address;
    }

    @OneToOne
    @Cascade(value = { CascadeType.ALL })
    @JoinColumn(name = "ADD_ID", nullable = true)
    public Address getAddressInternal() {

        if (this.getAddress().isBlank()) return null;
        return this.address;

    }
    
    public void setAddressInternal(Address address){
    	this.address = address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setContactMechanisms(List<ContactMechanism> contactMechanisms) {
        this.contactMechanisms = contactMechanisms;
    }

    @Transient
    public List<ContactMechanism> getContactMechanisms() {
        return contactMechanisms;
    }
    
    @RemoteProperty
    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }
    
    @RemoteProperty
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
        }
        else if (!firstName.equals(other.firstName)) 
        	return false;
        
        if (lastName == null) {
            if (other.lastName != null) 
           	return false;
        }
        else if(!lastName.equals(other.lastName)) 
       		return false;
        
        if (middleName == null) {
            if (other.middleName != null) 
            	return false;
        }
        else if(!middleName.equals(other.middleName)) 
        		return false;
        
        if (maidenName == null) {
            if (other.maidenName != null) 
            	return false;
        }
        else if(!maidenName.equals(other.maidenName)) 
        		return false;
        
        return true;
    }

}