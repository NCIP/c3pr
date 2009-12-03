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

    @Transient
	public String getEmail(){
		for(ContactMechanism contactMechanism: getContactMechanisms()){
			if(contactMechanism.getType()==ContactMechanismType.EMAIL){
				return contactMechanism.getValue();
			}
		}
		return null;
	}
	
	@Transient
	public String getPhone(){
		for(ContactMechanism contactMechanism: getContactMechanisms()){
			if(contactMechanism.getType()==ContactMechanismType.PHONE){
				return contactMechanism.getValue();
			}
		}
		return null;
	}
	
	@Transient
	public String getFax(){
		for(ContactMechanism contactMechanism: getContactMechanisms()){
			if(contactMechanism.getType()==ContactMechanismType.Fax){
				return contactMechanism.getValue();
			}
		}
		return null;
	}
	
	private void setContactMechanism(String value, ContactMechanismType contactMechanismType, boolean local){
		ContactMechanism contactMechanism= getContactMechanism(contactMechanismType);
		if(StringUtils.getBlankIfNull(value).equals("")){
			if(contactMechanism !=null){
				contactMechanisms.remove(contactMechanism);
			}
			return;
		}
		if(contactMechanismType == null){
			throw new NullPointerException("Cannot set empty contact mechanism type");
		}
		if(contactMechanismType == ContactMechanismType.EMAIL && !StringUtils.isValidEmail(value)){
			throw new IllegalArgumentException("Invalid email address");
		}
		if((contactMechanismType == ContactMechanismType.PHONE || contactMechanismType == ContactMechanismType.Fax) && !StringUtils.isValidPhone(value)){
			throw new IllegalArgumentException("Invalid phone number");
		}
		if(contactMechanism == null){
			if(local){
				contactMechanism = new LocalContactMechanism();
			}else{
				contactMechanism = new RemoteContactMechanism();
			}
			contactMechanism.setType(contactMechanismType);
			contactMechanisms.add(contactMechanism);
		}
		contactMechanism.setValue(value);
	}
	
	public void setEmail(String email){
		setContactMechanism(email, ContactMechanismType.EMAIL, true);
	}
	
	public void setPhone(String phone){
		setContactMechanism(phone, ContactMechanismType.PHONE, true);
	}
	
	public void setFax(String fax){
		setContactMechanism(fax, ContactMechanismType.Fax, true);
	}
	
	public void setRemoteEmail(String email){
		setContactMechanism(email, ContactMechanismType.EMAIL, false);
	}
	
	@Transient
    public String getRemoteEmail(){
        ContactMechanism contactMechanism = getContactMechanism(ContactMechanismType.EMAIL);
        if(contactMechanism != null){
                return contactMechanism.getValue();
        }
        return null;
    }

	public void setRemotePhone(String phone){
		setContactMechanism(phone, ContactMechanismType.PHONE, false);
	}
	
	public void setRemoteFax(String fax){
		setContactMechanism(fax, ContactMechanismType.Fax, false);
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
    
    @Transient
    private ContactMechanism getContactMechanism(ContactMechanismType type) {
        for(ContactMechanism contactMechanism: getContactMechanisms()){
        	if(contactMechanism.getType() == type)
        		return contactMechanism;
        }
        return null;
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