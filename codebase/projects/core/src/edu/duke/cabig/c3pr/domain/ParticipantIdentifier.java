package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table (name = "PARTICIPANT_IDENTIFIERS")
@GenericGenerator (name="id-generator", strategy = "native",
		parameters = {
			@Parameter(name="sequence", value="participant_identifiers_id_seq")
		}
)
public class ParticipantIdentifier extends AbstractDomainObject implements Serializable
{			
//	private String medicalRecordNumber;
	
	private Participant participant;
	private String value;
	private String typeCode;
	
	private HealthcareSite healthcareSite;
	
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ParticipantIdentifier()
    {
    	
    }    
    
	@ManyToOne( cascade = {CascadeType.ALL} )
	@JoinColumn(name = "HCS_ID")		
	public HealthcareSite getHealthcareSite() {
		return healthcareSite;
	}

	public void setHealthcareSite(HealthcareSite healthcareSite) {
		this.healthcareSite = healthcareSite;
	}

//	public String getMedicalRecordNumber() {
//		return medicalRecordNumber;
//	}
//
//	public void setMedicalRecordNumber(String medicalRecordNumber) {
//		this.medicalRecordNumber = medicalRecordNumber;
//	}
	
	@ManyToOne( cascade = {CascadeType.ALL} )
	@JoinColumn(name = "PRT_ID")		
	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}    
}
