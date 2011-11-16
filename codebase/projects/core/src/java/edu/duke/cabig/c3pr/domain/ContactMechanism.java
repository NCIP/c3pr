package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.constants.ContactMechanismUse;
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
    
    private Set<ContactMechanismUseAssociation> contactMechanismUseAssociation = new LinkedHashSet<ContactMechanismUseAssociation>();
    
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
		// setting hash code to a plain constant value as a safety measure related to CPR-2142.
		// In Participant, bags were changed to sets. 
		return 1;
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

	@OneToMany
	@Fetch(FetchMode.SUBSELECT)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name="cntct_id")
    @OrderBy("id")
	public Set<ContactMechanismUseAssociation> getContactMechanismUseAssociation() {
		return contactMechanismUseAssociation;
	}


	public void setContactMechanismUseAssociation(
			Set<ContactMechanismUseAssociation> contactMechanismUseAssociation) {
		this.contactMechanismUseAssociation = contactMechanismUseAssociation;
	}
	
	@Transient
	public List<ContactMechanismUse> getContactUses(){
		List<ContactMechanismUse> uses = new ArrayList<ContactMechanismUse>();
		for(ContactMechanismUseAssociation contactMechanismUseAssociation : getContactMechanismUseAssociation()){
			uses.add(contactMechanismUseAssociation.getUse());
		}
		return uses;
	}

}
