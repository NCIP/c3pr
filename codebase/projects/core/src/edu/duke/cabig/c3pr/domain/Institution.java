/*
 * Created Thu Apr 20 17:45:35 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;


/**
 * A class that represents a row in the 'ORGANIZATION' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Institution implements Serializable
{
    private String id;
    private String code;
    private String description;	
    private String name;
    private String orgTypeCd;
    /**
     * Simple constructor of Institution instances.
     */
    public Institution(String id)
    {
    	this.id=id;
    }
    
    public Institution()
    {
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
	public String getOrgTypeCd() {
		return orgTypeCd;
	}
	public void setOrgTypeCd(String orgTypeCd) {
		this.orgTypeCd = orgTypeCd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getLOVDescription(Object param) {
		return this.getDescription()+"/"+this.getCode();
	}

	public String getLOVId() {
		return this.getId();
	}

	public void setLOVFilter(Object param, Object filter) {
		this.setOrgTypeCd((String)filter);
	}
}
