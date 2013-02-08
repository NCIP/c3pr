/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
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

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "contact_mechanisms")
@GenericGenerator(name = "id-generator", strategy = "sequence", parameters = { @Parameter(name = "sequence", value = "CONTACT_MECHANISMS_ID_SEQ") })
public class BaseContactMechanismDataContainer extends AbstractMutableDeletableDomainObject{

    /** The type. */
    private ContactMechanismType type;

    /** The value. */
    private String value;
    
    private String dtype;
    
    
    public String getDtype() {
		return dtype;
	}
	public void setDtype(String dtype) {
		this.dtype = dtype;
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

}
