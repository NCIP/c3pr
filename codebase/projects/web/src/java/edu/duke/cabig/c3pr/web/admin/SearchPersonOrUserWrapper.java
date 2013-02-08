/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.admin;


public class SearchPersonOrUserWrapper {
	
	public String firstName;
	
	public String lastName;
	
	public String assignedIdentifier;
	
	//id of the selected hcs
	public String healthcareSite;
	
	public String emailAddress;
	
	public String loginName;


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAssignedIdentifier() {
		return assignedIdentifier;
	}

	public void setAssignedIdentifier(String assignedIdentifier) {
		this.assignedIdentifier = assignedIdentifier;
	}

	public String getHealthcareSite() {
		return healthcareSite;
	}

	public void setHealthcareSite(String healthcareSite) {
		this.healthcareSite = healthcareSite;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
		
}
