/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
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

@Entity
@Table(name = "comp_stu_associations")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "comp_stu_associations_id_seq") })

public class CompanionStudyAssociation extends AbstractMutableDeletableDomainObject{

	 private StudyVersion parentStudyVersion ;
     private Study companionStudy ;
	 private Boolean mandatoryIndicator ;
	 private List<StudySite> studySites = new ArrayList<StudySite>();
	 
	 @ManyToOne
	 @JoinColumn(name = "parent_version_id")
	 @Cascade( { CascadeType.LOCK })
	 public StudyVersion getParentStudyVersion() {
		 return parentStudyVersion;
	 }

	 public void setParentStudyVersion(StudyVersion parentStudyVersion) {
		 this.parentStudyVersion = parentStudyVersion;
	 }
	
	@ManyToOne
    @JoinColumn(name = "companion_study_id", nullable=false)
    @Cascade( { CascadeType.LOCK, CascadeType.SAVE_UPDATE, CascadeType.MERGE})
	public Study getCompanionStudy() {
		return companionStudy;
	}
	public void setCompanionStudy(Study companionStudy) {
		this.companionStudy = companionStudy;
	}

	public Boolean getMandatoryIndicator() {
		return mandatoryIndicator;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((companionStudy == null) ? 0 : companionStudy.hashCode());
		result = prime
				* result
				+ ((mandatoryIndicator == null) ? 0 : mandatoryIndicator
						.hashCode());
		result = prime * result
				+ ((parentStudyVersion == null) ? 0 : parentStudyVersion.hashCode());
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
		CompanionStudyAssociation other = (CompanionStudyAssociation) obj;
		if (companionStudy == null) {
			if (other.companionStudy != null)
				return false;
		} else if (!companionStudy.equals(other.companionStudy))
			return false;
		if (mandatoryIndicator == null) {
			if (other.mandatoryIndicator != null)
				return false;
		} else if (!mandatoryIndicator.equals(other.mandatoryIndicator))
			return false;
		if (parentStudyVersion == null) {
			if (other.parentStudyVersion != null)
				return false;
		} else if (!parentStudyVersion.equals(other.parentStudyVersion))
			return false;
		return true;
	}
	public void setMandatoryIndicator(Boolean mandatoryIndicator) {
		this.mandatoryIndicator = mandatoryIndicator;
	}

	@OneToMany(orphanRemoval=true)
	@Cascade( { CascadeType.ALL})
	@JoinColumn(name = "comp_assoc_id")
	public List<StudySite> getStudySites() {
		return studySites;
	}

	public void setStudySites(List<StudySite> studySites) {
		this.studySites = studySites;
	}
	
	public void addStudySite(StudySite studySite) {
		if(studySite != null){
			this.getStudySites().add(studySite);
			studySite.setCompanionStudyAssociation(this);
		}
	}
	
	public void removeStudySite(StudySite studySite) {
		if(studySite != null){
			this.getStudySites().remove(studySite);
		}
	}
	
	@Transient
	public boolean contains(StudySite studySite){
		return this.getStudySites().contains(studySite);
	}
	
	public CompanionStudyAssociation(){
		companionStudy = new LocalStudy();
		companionStudy.setCompanionIndicator(true);
	}
	
	@Transient
	public Study getParentStudy(){
		return getParentStudyVersion().getStudy();
	}
}
