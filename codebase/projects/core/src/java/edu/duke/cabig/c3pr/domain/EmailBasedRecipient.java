package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value = "ER")
public class EmailBasedRecipient extends Recipient {

	private String emailAddress;

	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	@Transient
	public void setRetiredIndicatorAsTrue(){
		super.setRetiredIndicatorAsTrue();
	}
}
