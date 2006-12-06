/*
 * Created Thu Apr 20 17:45:40 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * A class that represents a row in the 'PARTICIPANT_REG_STATUS' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class ParticipantRegistrationStatus implements Serializable
{
	private Integer id;
	private Date effectiveDate;
	private String comments;
	private RegistrationStatus registrationStatus;
	private Reason reason;
	
    /**
     * Simple constructor of ProtocolStatus instances.
     */
    public ParticipantRegistrationStatus()
    {
    }

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}

	public RegistrationStatus getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(RegistrationStatus registrationStatus) {
		this.registrationStatus = registrationStatus;
	}
}
