package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Kruttik
 */
@Entity
@DiscriminatorValue(value = "E")
public class ExclusionEligibilityCriteria extends EligibilityCriteria {
private Study study;
	
	@ManyToOne
	@JoinColumn(name = "stu_id", updatable = false)
	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

}