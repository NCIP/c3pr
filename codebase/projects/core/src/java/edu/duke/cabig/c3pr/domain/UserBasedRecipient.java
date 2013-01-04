/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * The Class UserBasedRecipient. 
 * This is used by the notification framework to send notifications to recipients who are users in 
 * the C3PR system. Hence this class has links to the personUser and investigators
 */
@Entity
@DiscriminatorValue(value = "ER")
public class UserBasedRecipient extends Recipient {

    /** The person user. */
    private PersonUser personUser;
    
    /** The investigator. */
    private Investigator investigator;
    
    /** The email address. */
    private String emailAddress;
    

	/**
	 * Gets the investigator.
	 * 
	 * @return the investigator
	 */
	@ManyToOne
    @JoinColumn(name = "investigators_id")
//    @Cascade(value = { CascadeType.LOCK })
	public Investigator getInvestigator() {
		return investigator;
	}

	/**
	 * Sets the investigator.
	 * 
	 * @param investigator the new investigator
	 */
	public void setInvestigator(Investigator investigator) {
		this.investigator = investigator;
	}


	/**
	 * Gets the person user.
	 *
	 * @return the person user
	 */
	@ManyToOne
    @JoinColumn(name = "research_staff_id")
//    @Cascade(value = { CascadeType.LOCK })
	public PersonUser getPersonUser() {
		return personUser;
	}


	/**
	 * Sets the person user.
	 *
	 * @param personUser the new person user
	 */
	public void setPersonUser(PersonUser personUser) {
		this.personUser = personUser;
	}
	
    /**
     * Sets the email address.
     * 
     * @param emailAddress the new email address
     */
    public void setEmailAddress(String emailAddress){
    	this.emailAddress = emailAddress;
    }
    
    /**
     * Gets the email address.
     * 
     * @return the email address
     */
    @Transient
	public String getEmailAddress(){		
    	if(this.emailAddress != null && this.emailAddress!= "" ){
    		return this.emailAddress;
    	}
		List<ContactMechanism> cmList = null;		
		if(personUser != null){
			//cmList = researchStaff.getContactMechanisms();
			return personUser.getEmail();
		} else {
			if(investigator != null){
//				cmList = investigator.getContactMechanisms();
				return investigator.getEmail();
			}
		}
		
//		if(cmList != null){
//			for(ContactMechanism cm: cmList){
//				if(cm.getType().equals(ContactMechanismType.EMAIL)){
//					return cm.getValue();
//				}
//			}
//		}			
		return "";
	}
    
    /**
     * Gets the full name.
     * 
     * @return the full name
     */
    @Transient
	public String getFullName(){		
		String fullName = "";		
		if(personUser != null){
			fullName = personUser.getFirstName() +" "+ personUser.getLastName();
		} else {
			if(investigator != null){
				fullName = investigator.getFirstName() +" "+ investigator.getLastName();
			}
		}
		return fullName;
	}
	   
    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#setRetiredIndicatorAsTrue()
     */
    @Override
    @Transient
    public void setRetiredIndicatorAsTrue() {
        super.setRetiredIndicatorAsTrue();
    }

}
