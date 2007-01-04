package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
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
	private List<StudyParticipantAssignment> studyParticipantAssignments= new ArrayList<StudyParticipantAssignment>();
	
    @OneToMany (mappedBy="participant", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	public List<StudyParticipantAssignment> getStudyParticipantAssignments() {
		return studyParticipantAssignments;
	}

	public void setStudyParticipantAssignments(
			List<StudyParticipantAssignment> studyParticipantAssignments) {
		this.studyParticipantAssignments = studyParticipantAssignments;
	}

    public void addStudyParticipantAssignment(StudyParticipantAssignment studyParticipantAssignment){
    	studyParticipantAssignments.add(studyParticipantAssignment);
    }

    public void removeStudyParticipantAssignment(StudyParticipantAssignment studyParticipantAssignment){
    	studyParticipantAssignments.remove(studyParticipantAssignment);
    }

	
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

