package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Kruttik
 */
@Entity
@DiscriminatorValue(value = "E")
public class ExclusionEligibilityCriteria extends EligibilityCriteria {

	public ExclusionEligibilityCriteria() {
		setQuestionNumber(new Integer(0));
		// TODO Auto-generated constructor stub
	}
	
}