package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value = "PR")
public class PhoneCallRandomization extends Randomization {

    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Transient
    public String getPhoneNumberString() {
    	if(this.phoneNumber != null){
	        String tempPhoneNumber = this.phoneNumber.replaceAll("-", "");
	        String phoneNumberString = tempPhoneNumber.substring(0, 3) + "-" +tempPhoneNumber.substring(3, 6) + "-" + tempPhoneNumber.substring(6);
	    	return phoneNumberString;
    	}else{
    		return this.phoneNumber;
    	}
    }

    @Override
    @Transient
    public void setRetiredIndicatorAsTrue() {
        super.setRetiredIndicatorAsTrue();
    }
}
