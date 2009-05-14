package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;

/**
 * The Class UserBasedRecipient. 
 * This is used by the notification framework to send notifications to recipients who are users in 
 * the C3PR system. Hence this class has links to the staff and investigators
 */
@Entity
@DiscriminatorValue(value = "ER")
public class UserBasedRecipient extends Recipient {

    /** The research staff. */
    private ResearchStaff researchStaff;
    
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
	 * Gets the research staff.
	 * 
	 * @return the research staff
	 */
	@ManyToOne
    @JoinColumn(name = "research_staff_id")
//    @Cascade(value = { CascadeType.LOCK })
	public ResearchStaff getResearchStaff() {
		return researchStaff;
	}

	/**
	 * Sets the research staff.
	 * 
	 * @param researchStaff the new research staff
	 */
	public void setResearchStaff(ResearchStaff researchStaff) {
		this.researchStaff = researchStaff;
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
		if(researchStaff != null){
			cmList = researchStaff.getContactMechanisms();
		} else {
			if(investigator != null){
				cmList = investigator.getContactMechanisms();
			}
		}
		
		if(cmList != null){
			for(ContactMechanism cm: cmList){
				if(cm.getType().equals(ContactMechanismType.EMAIL)){
					return cm.getValue();
				}
			}
		}			
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
		if(researchStaff != null){
			fullName = researchStaff.getFirstName() +" "+ researchStaff.getLastName();
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
