package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.FamilialRelationshipName;
import edu.duke.cabig.c3pr.constants.RelationshipCategory;

@Entity
@Table(name="relationships")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "relationships_id_seq") })
public class Relationship extends AbstractMutableDeletableDomainObject{
	
	private RelationshipCategory category;
	private FamilialRelationshipName name;
	private Participant primaryParticipant;
	private Participant secondaryParticipant;
	private String otherName;
	
	public Relationship() {
		super();
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	@ManyToOne
	@JoinColumn(name = "pri_prt_id")
	@Cascade(CascadeType.LOCK)
	public Participant getPrimaryParticipant() {
		return primaryParticipant;
	}

	public void setPrimaryParticipant(Participant primaryParticipant) {
		this.primaryParticipant = primaryParticipant;
	}

	@ManyToOne
	@JoinColumn(name = "sec_prt_id")
	@Cascade(CascadeType.LOCK)
	public Participant getSecondaryParticipant() {
		return secondaryParticipant;
	}

	public void setSecondaryParticipant(Participant secondaryParticipant) {
		this.secondaryParticipant = secondaryParticipant;
	}

	@Enumerated(EnumType.STRING)
	public RelationshipCategory getCategory() {
		return category;
	}
	
	public void setCategory(RelationshipCategory category) {
		this.category = category;
	}
	
	@Enumerated(EnumType.STRING)
	public FamilialRelationshipName getName() {
		return name;
	}
	
	public void setName(FamilialRelationshipName name) {
		this.name = name;
	}

}
