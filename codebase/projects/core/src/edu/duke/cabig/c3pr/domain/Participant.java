package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Participant extends Person implements Comparable<Participant>
{	
	private List<ParticipantIdentifier> participantIdentifiers = new ArrayList<ParticipantIdentifier>();
	private List<StudyParticipantAssignment> studyParticipantAssignments= new ArrayList<StudyParticipantAssignment>();
	
    @OneToMany (mappedBy="participant", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public List<ParticipantIdentifier> getParticipantIdentifiers() {
		return participantIdentifiers;
	}

	public void setParticipantIdentifiers(
			List<ParticipantIdentifier> participantIdentifiers) {
		this.participantIdentifiers = participantIdentifiers;
	}
	
	public void addParticipantIdentifier(ParticipantIdentifier partId)
	{		
		participantIdentifiers.add(partId);
		partId.setParticipant(this);
	}
	
	public void removeParticipantIdentifier(ParticipantIdentifier partId)
	{
		participantIdentifiers.remove(partId);
	}
	
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
	
	public int compareTo(Participant o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((participantIdentifiers == null) ? 0 : participantIdentifiers.hashCode());
		result = PRIME * result + ((studyParticipantAssignments == null) ? 0 : studyParticipantAssignments.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Participant other = (Participant) obj;
		if (participantIdentifiers == null) {
			if (other.participantIdentifiers != null)
				return false;
		} else if (!participantIdentifiers.equals(other.participantIdentifiers))
			return false;
		if (studyParticipantAssignments == null) {
			if (other.studyParticipantAssignments != null)
				return false;
		} else if (!studyParticipantAssignments.equals(other.studyParticipantAssignments))
			return false;
		return true;
	}
}