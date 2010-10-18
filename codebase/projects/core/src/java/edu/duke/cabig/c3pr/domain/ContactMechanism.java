package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * The Class ContactMechanism.
 * 
 * @author Ramakrishna
 */
@Entity
@Table(name = "contact_mechanisms")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CONTACT_MECHANISMS_ID_SEQ") })
public class ContactMechanism extends AbstractMutableDeletableDomainObject {

    /** The type. */
    private ContactMechanismType type;

    /** The value. */
    private String value;
    
    public ContactMechanism() {}
    

    public ContactMechanism(ContactMechanismType type, String value) {
		super();
		this.type = type;
		this.value = value;
	}


	/**
     * Gets the type.
     * 
     * @return the type
     */
    @Enumerated(EnumType.STRING)
    public ContactMechanismType getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type the new type
     */
    public void setType(ContactMechanismType type) {
        this.type = type;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Gets the value string.
     * 
     * @return the value string
     */
    @Transient
    public String getValueString() {
    	if(this.getType() != ContactMechanismType.EMAIL && !StringUtils.isBlank(this.value)){
    		String tempPhoneNumber = this.value.replaceAll("-", "");
            String phoneNumberString = tempPhoneNumber.substring(0, 3) + "-" +tempPhoneNumber.substring(3, 6) + "-" + tempPhoneNumber.substring(6);
        	return phoneNumberString;
    	}else{
    		return this.value ;
    	}
        
    }


    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value) {
        this.value = value;
    }


	


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ContactMechanism)) {
			return false;
		}
		ContactMechanism other = (ContactMechanism) obj;
		if (type != other.type) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ContactMechanism [type=" + type + ", value=" + value + "]";
	}
	
	

}
