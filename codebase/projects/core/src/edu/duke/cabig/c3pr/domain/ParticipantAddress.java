/*
 * Created Thu Apr 20 17:45:27 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.constants.ReferenceDataConstants;

import java.io.Serializable;
import java.util.Collection;

/**
 * A class that represents a row in the 'PARTY_ADDRESS' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class ParticipantAddress implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    private Integer id;
	private String preferredFlag;
	private String activeFlag;
	
	private Participant participant;
	private Address address;
	
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public int getHashValue() {
		return hashValue;
	}
	public void setHashValue(int hashValue) {
		this.hashValue = hashValue;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Participant getParticipant() {
		return participant;
	}
	public void setParticipant(Participant participant) {
		this.participant = participant;
	}
	public String getPreferredFlag() {
		return preferredFlag;
	}
	public void setPreferredFlag(String preferredFlag) {
		this.preferredFlag = preferredFlag;
	}
	public String getIdAsString(){
		if(getId()==null)
			return "";
		
		return getId().toString();
	}

	public String getActiveFlagDesc() {
		if(getActiveFlag() != null && ReferenceDataConstants.ACTIVE.equals(getActiveFlag())){
			return "Active";
		}else{
			return "Inactive";
		}
	}
}