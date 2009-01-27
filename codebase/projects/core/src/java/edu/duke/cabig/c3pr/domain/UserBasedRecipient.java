package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@DiscriminatorValue(value = "ER")
public class UserBasedRecipient extends Recipient {

    private LocalResearchStaff localResearchStaff;
    private Investigator investigator;
    
    private String emailAddress;
    

	@ManyToOne
    @JoinColumn(name = "investigators_id")
    @Cascade(value = { CascadeType.LOCK })
	public Investigator getInvestigator() {
		return investigator;
	}

	public void setInvestigator(Investigator investigator) {
		this.investigator = investigator;
	}

	@ManyToOne
    @JoinColumn(name = "research_staff_id")
    @Cascade(value = { CascadeType.LOCK })
	public LocalResearchStaff getLocalResearchStaff() {
		return localResearchStaff;
	}

	public void setLocalResearchStaff(LocalResearchStaff LocalResearchStaff) {
		this.localResearchStaff = localResearchStaff;
	}
    
    @Transient
	public String getEmailAddress(){		
    	if(this.emailAddress != null && this.emailAddress!= "" ){
    		return this.emailAddress;
    	}
		List<ContactMechanism> cmList = null;		
		if(localResearchStaff != null){
			cmList = localResearchStaff.getContactMechanisms();
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
    
    public void setEmailAddress(String emailAddress){
    	this.emailAddress = emailAddress;
    }
    
    @Transient
	public String getFullName(){		
 
		String fullName = "";		
		if(localResearchStaff != null){
			fullName = localResearchStaff.getFirstName() +" "+ localResearchStaff.getLastName();
		} else {
			if(investigator != null){
				fullName = investigator.getFirstName() +" "+ investigator.getLastName();
			}
		}
		return fullName;
	}
	   
    @Override
    @Transient
    public void setRetiredIndicatorAsTrue() {
        super.setRetiredIndicatorAsTrue();
    }

}
