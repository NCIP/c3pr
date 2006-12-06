/*
 * Created Thu Apr 20 17:45:40 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

/**
 * A class that represents a row in the 'PROTOCOL_ARM' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class ProtocolArm implements Serializable
{
	private Integer id;
	private String code;
	private String description;
	private String accrualCeiling;
	
    /**
     * Simple constructor of ProtocolArm instances.
     */
    public ProtocolArm()
    {
    }

	public String getAccrualCeiling() {
		return accrualCeiling;
	}

	public void setAccrualCeiling(String accrualCeiling) {
		this.accrualCeiling = accrualCeiling;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


}
