package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value = "RR")
public class RoleBasedRecipient extends Recipient {

	private String role;

	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	@Transient
	public void setRetiredIndicatorAsTrue(){
		super.setRetiredIndicatorAsTrue();
	}
}
