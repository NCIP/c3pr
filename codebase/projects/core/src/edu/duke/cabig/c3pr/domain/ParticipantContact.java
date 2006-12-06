/*
 * Created Thu Apr 20 17:45:27 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;
import java.util.Collection;


/**
 * A class that represents a row in the 'PARTY_ADDRESS' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class ParticipantContact implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    private Integer id;
	private String preferredFlag;
	
	private Participant participant;
	private Contact contact;
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
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
	
}