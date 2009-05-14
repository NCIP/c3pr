package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * @author Ramakrishna
 */
@Entity
@Table(name = "contact_mechanisms")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CONTACT_MECHANISMS_ID_SEQ") })
public class ContactMechanism extends AbstractMutableDeletableDomainObject {

    private ContactMechanismType type;

    private String value;

    @Enumerated(EnumType.STRING)
    public ContactMechanismType getType() {
        return type;
    }

    public void setType(ContactMechanismType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }
    
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


    public void setValue(String value) {
        this.value = value;
    }

}
