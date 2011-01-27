/**
 * 
 */
package edu.duke.cabig.c3pr.domain;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.semanticbits.coppa.domain.annotations.RemoteProperty;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.constants.ContactMechanismUse;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * This class defines fields and methods common to all person implementations.  
 * @author dkrylov
 * 
 */
@MappedSuperclass
public abstract class PersonBase extends
		AbstractMutableDeletableDomainObject {
	
    private String firstName;

    private String lastName;

    private String maidenName;

    private String middleName;

    

    protected Set<ContactMechanism> contactMechanisms = new LinkedHashSet<ContactMechanism>();

    @Transient
	public String getEmail(){
		return getEmailContactMechanism()!=null?getEmailContactMechanism().getValue():null;
	}
	
	@Transient
	public String getPhone(){
		return getPhoneContactMechanism()!=null?getPhoneContactMechanism().getValue():null;
	}
	
	@Transient
	public String getFax(){
		return getFaxContactMechanism()!=null?getFaxContactMechanism().getValue():null;
	}
	
	@Transient
	public String getOther(){
		return getOtherContactMechanism()!=null?getOtherContactMechanism().getValue():null;
	}
	
	private void setContactMechanism(String value, ContactMechanismType contactMechanismType, boolean local){
		setContactMechanism(value, contactMechanismType, null, local);
	}
	
	private void setContactMechanism(String value, ContactMechanismType contactMechanismType, List<ContactMechanismUse> uses, boolean local){
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
		if(contactMechanismType == ContactMechanismType.PHONE && !StringUtils.isValidPhone(value)){
			throw new IllegalArgumentException("Invalid phone number");
		}
		if(contactMechanismType == ContactMechanismType.Fax && !StringUtils.isValidFax(value)){
			throw new IllegalArgumentException("Invalid fax number");
		}
		if(contactMechanism == null){
			if(local){
				contactMechanism = new LocalContactMechanism();
			}else{
				contactMechanism = new RemoteContactMechanism();
			}
			contactMechanism.setType(contactMechanismType);
		} else {
			contactMechanisms.remove(contactMechanism);
		}
		contactMechanism.setValue(value);
		contactMechanisms.add(contactMechanism);
		if(uses != null){
			contactMechanism.getContactMechanismUseAssociation().clear();
			for(ContactMechanismUse use : uses){
				contactMechanism.getContactMechanismUseAssociation().add(new ContactMechanismUseAssociation(use));
			}
		}
	}
	
	public void setEmail(String email){
		setEmail(email, null);
	}
	
	public void setPhone(String phone){
		setPhone(phone, null);
	}
	
	public void setFax(String fax){
		setFax(fax, null);
	}
	
	public void setOther(String other){
		setOther(other, null);
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

    

    public void setContactMechanisms(Set<ContactMechanism> contactMechanisms) {
        this.contactMechanisms = contactMechanisms;
    }

    @Transient
    public Set<ContactMechanism> getContactMechanisms() {
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
        
        final PersonBase other = (PersonBase) obj;
        
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

    public void setEmail(String email, List<ContactMechanismUse> uses){
		setContactMechanism(email, ContactMechanismType.EMAIL, uses, true);
	}
	
	public void setPhone(String phone, List<ContactMechanismUse> uses){
		setContactMechanism(phone, ContactMechanismType.PHONE, uses, true);
	}
	
	public void setFax(String fax, List<ContactMechanismUse> uses){
		setContactMechanism(fax, ContactMechanismType.Fax, uses, true);
	}
	
	public void setOther(String other, List<ContactMechanismUse> uses){
		setContactMechanism(other, ContactMechanismType.OTHER, uses, true);
	}
    
	@Transient
	public ContactMechanism getEmailContactMechanism(){
		for(ContactMechanism contactMechanism: getContactMechanisms()){
			if(contactMechanism.getType()==ContactMechanismType.EMAIL){
				return contactMechanism;
			}
		}
		return null;
	}
	
	@Transient
	public ContactMechanism getPhoneContactMechanism(){
		for(ContactMechanism contactMechanism: getContactMechanisms()){
			if(contactMechanism.getType()==ContactMechanismType.PHONE){
				return contactMechanism;
			}
		}
		return null;
	}
	
	@Transient
	public ContactMechanism getFaxContactMechanism(){
		for(ContactMechanism contactMechanism: getContactMechanisms()){
			if(contactMechanism.getType()==ContactMechanismType.Fax){
				return contactMechanism;
			}
		}
		return null;
	}
	
	@Transient
	public ContactMechanism getOtherContactMechanism(){
		for(ContactMechanism contactMechanism: getContactMechanisms()){
			if(contactMechanism.getType()==ContactMechanismType.OTHER){
				return contactMechanism;
			}
		}
		return null;
	}
	
	public void addContactMechanism(ContactMechanism contactMechanism){
		getContactMechanisms().add(contactMechanism);
	}
}
