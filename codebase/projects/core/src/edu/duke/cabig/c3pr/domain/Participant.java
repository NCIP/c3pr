package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Kulasekaran
 * @version 1.0 
 * 
 */
@Entity 
@Table (name = "participants")
@GenericGenerator (name="id-generator", strategy = "native",
		parameters = {
			@Parameter(name="sequence", value="participants_id_seq")
		}
)
public class Participant extends Person implements Serializable
{	
	private List<ParticipantIdentifier> participantIdentifiers;
	
    public Participant()
    {
    	
    }

	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn(name="PRT_ID")
	public List<ParticipantIdentifier> getParticipantIdentifiers() {
		return participantIdentifiers;
	}

	public void setParticipantIdentifiers(
			List<ParticipantIdentifier> participantIdentifiers) {
		this.participantIdentifiers = participantIdentifiers;
	}  	 
}

