/*
 * Created Thu Apr 20 17:45:29 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;



import java.io.Serializable;
import java.util.Date;

/**
 * A class that represents a row in the 'ARM_STATUS' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class ArmStatus   implements Serializable
{
	private Integer id;
	private Date effectiveDate;
	private ProtocolStatusCode protocolStatusCode;

	/**
     * Simple constructor of ArmStatus instances.
     */
    public ArmStatus()
    {
    }

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProtocolStatusCode getProtocolStatusCode() {
		return protocolStatusCode;
	}

	public void setProtocolStatusCode(ProtocolStatusCode protocolStatusCode) {
		this.protocolStatusCode = protocolStatusCode;
	}

}
