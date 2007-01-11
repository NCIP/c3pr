package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Kulasekaran
 * @version 1.0 
 * 
 */

@Entity 
@Table (name = "participant_identifiers")
@GenericGenerator (name="id-generator", strategy = "native",
		parameters = {
			@Parameter(name="sequence", value="participant_identifiers_id_seq")
		}
)
public class ParticipantIdentifier extends AbstractDomainObject
{			
	private String medicalRecordNumber;	
	//private Participant participant;	
	private HealthcareSite healthcareSite;
		
	public ParticipantIdentifier()
    {
    	
    }    
    
	public String getMedicalRecordNumber() {
		return medicalRecordNumber;
	}

	public void setMedicalRecordNumber(String medicalRecordNumber) {
		this.medicalRecordNumber = medicalRecordNumber;	
	}
	
	@ManyToOne( cascade = {CascadeType.ALL} )
	@JoinColumn(name = "HCS_ID")		
	public HealthcareSite getHealthcareSite() {
		return healthcareSite;
	}

	public void setHealthcareSite(HealthcareSite healthcareSite) {
		this.healthcareSite = healthcareSite;
	}
	
	/*@ManyToOne( cascade = {CascadeType.ALL}, optional = true )
	@JoinColumn(name = "PRT_ID")		
	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}    */
}
