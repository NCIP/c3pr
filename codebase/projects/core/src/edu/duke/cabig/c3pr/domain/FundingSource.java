/*
 * Created Thu Apr 20 17:45:32 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

/**
 * A class that represents a row in the 'CODE_FUNDING_SOURCE' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class FundingSource implements Serializable
{
    private String id;
    private String name;
    private String description;

    /**
     * Simple constructor of RegistrationRole instances.
     */
    public FundingSource()
    {
    }


	public FundingSource(String id)
	{
		this.id=id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
