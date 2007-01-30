package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Kulasekaran, Priyatam
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
	private List<Identifier> identifiers = new ArrayList<Identifier>();
	private List<StudyParticipantAssignment> studyParticipantAssignments= new ArrayList<StudyParticipantAssignment>();
		
	private String primaryIdentifier;
	
    @OneToMany(fetch=FetchType.LAZY)
    @Cascade({CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "PRT_ID")
    public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(
			List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}
	
	public void addIdentifier(Identifier identifier)
	{		
		identifiers.add(identifier);		
	}
	
	public void removeIdentifier(Identifier identifier)
	{
		identifiers.remove(identifier);
	}
	
	@OneToMany (mappedBy="participant", fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})
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
		result = PRIME * result + ((identifiers == null) ? 0 : identifiers.hashCode());
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
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
			return false;
		if (studyParticipantAssignments == null) {
			if (other.studyParticipantAssignments != null)
				return false;
		} else if (!studyParticipantAssignments.equals(other.studyParticipantAssignments))
			return false;
		return true;
	}
	
	@Transient
	public String getPrimaryIdentifier() {		
		for (Identifier identifier : identifiers) {
			if(identifier.getPrimaryIndicator().booleanValue() == true)
			{
				return identifier.getValue();
			}
		}
			
		return primaryIdentifier;		
	}
}