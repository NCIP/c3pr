/*
 * Created Thu Apr 20 17:45:39 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.utils.Constants;

import java.io.Serializable;

/**
 * A class that represents a row in the 'PERSON_ORG' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class RegistrationParticipantIdentifier implements Serializable
{
	private Integer id;
	private String identifierCode;
	private String primaryIDFlag;
	private Institution institution;
	private Registration registration;
    /**
     * Simple constructor of PersonOrg instances.
     */
    public RegistrationParticipantIdentifier()
    {
    }
    
    
	public RegistrationParticipantIdentifier(String identifierCode, String primaryIDFlag, Institution institution)
	{
		this.identifierCode = identifierCode;
		this.primaryIDFlag = primaryIDFlag;
		this.institution = institution;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIdentifierCode() {
		return identifierCode;
	}
	public void setIdentifierCode(String identifierCode) {
		this.identifierCode = identifierCode;
	}
	public Institution getInstitution() {
		return institution;
	}
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	public String getPrimaryIDFlag() {
		return primaryIDFlag;
	}
	public void setPrimaryIDFlag(String primaryIDFlag) {
		this.primaryIDFlag = primaryIDFlag;
	}
    public Registration getRegistration() {
		return registration;
	}
	public void setRegistration(Registration registration) {
		this.registration = registration;
	}


	
	public boolean isPrimary() {
    	
    	if (primaryIDFlag != null && primaryIDFlag.length() > 0){
    		if (primaryIDFlag.equalsIgnoreCase(Constants.TRUE))
    			return true;
    	}
    	return false;
    }
}
