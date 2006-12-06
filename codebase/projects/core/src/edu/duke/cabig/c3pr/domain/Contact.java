/*
 * Created Thu Apr 20 17:45:34 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;


import java.io.Serializable;

/**
 * A class that represents a row in the 'CONTACT_MECHANISM' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Contact implements Serializable
{
    private Integer id;
    private String value;
    private ContactType type;

    /**
     * Simple constructor of Contact instances.
     */
    public Contact()
    {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ContactType getType() {
		return type;
	}

	public void setType(ContactType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getTypeDesc(){
		return getType().getDescription();
	}
	
	public String getTypeCode(){
		return getType().getCode();
	}

}
