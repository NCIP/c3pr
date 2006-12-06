/*
 * Created Thu Apr 20 17:45:40 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;


/**
 * A class that represents a row in the 'PTP_CONTACT_RELATIONSHIP' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class RelatedParticipant implements Serializable
{
	private Integer id;
	private Participant participant;
	private Participant relatedParticipant;
	private ParticipantRole participantRole;
	private Letter letter;
	
    /**
     * Simple constructor of RelatedParticipant instances.
     */
    public RelatedParticipant()
    {
    }

	public RelatedParticipant(Participant relatedParticipant, ParticipantRole participantRole)
	{
		this.relatedParticipant = relatedParticipant;
		this.participantRole = participantRole;
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

	public ParticipantRole getParticipantRole() {
		return participantRole;
	}

	public void setParticipantRole(ParticipantRole participantRole) {
		this.participantRole = participantRole;
	}

	public Participant getRelatedParticipant() {
		return relatedParticipant;
	}

	public void setRelatedParticipant(Participant relatedParticipant) {
		this.relatedParticipant = relatedParticipant;
	}

	
    /* Add customized code below */
	public String getIdAsString(){
		if(getId() == null)
			return "";
		
		return getId().toString();
	}
 
	public String getRelatedParticipantRoleDesc(){
		if(getParticipantRole()==null)
			return "";
		
		return getParticipantRole().getDescription();
	}

	public String getRelatedParticipantFullName(){
		if(getRelatedParticipant()==null)
			return "";
		
		return getRelatedParticipant().getFullName();
	}
	
	public String getRelatedParticipantFullNameAndSpecialty(){
		if(getRelatedParticipant()==null)
			return "";
		
		return getRelatedParticipant().getFullNameAndSpecialty();
	}

	public Letter getLetter() {
		return letter;
	}

	public void setLetter(Letter letter) {
		this.letter = letter;
	}
}
