/*
 * Created Thu Apr 20 17:45:32 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

/**
 * A class that represents a row in the 'CODE_REGISTRATION_ROLE' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class RegistrationRole implements Serializable
{
    private String id;
    private String code;
    private String description;

    /**
     * Simple constructor of RegistrationRole instances.
     */
    public RegistrationRole()
    {
    }

    public RegistrationRole(String id)
    {
    	this.id=id;
    	this.code = id;
    }
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
