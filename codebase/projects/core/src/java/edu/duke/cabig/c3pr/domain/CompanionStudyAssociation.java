package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@Table(name = "comp_stu_associations")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "comp_stu_associations_id_seq") })

public class CompanionStudyAssociation extends AbstractMutableDeletableDomainObject{

	 private Study parentStudy ;
	 private Study companionStudy ;
	 private Boolean mandatoryIndicator ;
	 private List<StudySite> studySites = new ArrayList<StudySite>();
	 
	
	 @ManyToOne
	 @JoinColumn(name = "parent_study_id")
	@Cascade( { CascadeType.LOCK })
	public Study getParentStudy() {
		return parentStudy;
	}
	public void setParentStudy(Study parentStudy) {
		this.parentStudy = parentStudy;
	}
	
	@ManyToOne
    @JoinColumn(name = "companion_study_id", nullable=false)
    @Cascade( { CascadeType.ALL})
	public Study getCompanionStudy() {
		return companionStudy;
	}
	public void setCompanionStudy(Study companionStudy) {
		this.companionStudy = companionStudy;
	}

	public void setCompanionStudyMandatory(boolean mandatoryIndicator) {
		this.mandatoryIndicator = mandatoryIndicator;
	}
	public Boolean getMandatoryIndicator() {
		return mandatoryIndicator;
	}
	public void setMandatoryIndicator(Boolean mandatoryIndicator) {
		this.mandatoryIndicator = mandatoryIndicator;
	}

	@OneToMany
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "comp_assoc_id")
	public List<StudySite> getStudySites() {
		return studySites;
	}

	public void setStudySites(List<StudySite> studySites) {
		this.studySites = studySites;
	}
	
	public void addStudySite(StudySite studySite) {
		if(studySite != null){
			getStudySites().add(studySite);
		}
	}
	
	@Transient
	public boolean contains(StudySite studySite){
		return this.getStudySites().contains(studySite);
	}
	
	public CompanionStudyAssociation(){
		companionStudy = new Study();
		companionStudy.setCompanionIndicator(true);
	}
}
