package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "comp_stu_associations")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "comp_stu_associations_id_seq") })

public class CompanionStudyAssociation extends AbstractMutableDeletableDomainObject{

	 private LazyListHelper lazyListHelper;
	 
	 private Study parentStudy ;
	 private Study companionStudy ;
	 private Boolean mandatoryIndicator ;
	 
	
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
    @JoinColumn(name = "companion_study_id")
    @Cascade( { CascadeType.MERGE, CascadeType.SAVE_UPDATE, CascadeType.LOCK})
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


}
