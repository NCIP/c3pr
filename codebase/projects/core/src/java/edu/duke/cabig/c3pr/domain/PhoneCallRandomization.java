/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * The Class PhoneCallRandomization.
 * Type of randomization. Simply contains the phone number to call to get
 * the arm assignment.
 */
@Entity
@DiscriminatorValue(value = "PR")
public class PhoneCallRandomization extends Randomization {

    /** The phone number. */
    private String phoneNumber;

    /**
     * Gets the phone number.
     * 
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     * 
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Gets the phone number string.
     * 
     * @return the phone number string
     */
    @Transient
    public String getPhoneNumberString() {
    	if(!StringUtils.isBlank(this.phoneNumber)){
	        String tempPhoneNumber = this.phoneNumber.replaceAll("-", "");
	        String phoneNumberString = tempPhoneNumber.substring(0, 3) + "-" +tempPhoneNumber.substring(3, 6) + "-" + tempPhoneNumber.substring(6);
	    	return phoneNumberString;
    	}else{
    		return this.phoneNumber;
    	}
    }

}
