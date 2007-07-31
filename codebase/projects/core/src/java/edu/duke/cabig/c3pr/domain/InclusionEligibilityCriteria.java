package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Kruttik
 */
@Entity
@DiscriminatorValue(value = "I")
public class InclusionEligibilityCriteria extends EligibilityCriteria {
	public InclusionEligibilityCriteria() {
		setQuestionNumber(new Integer(0));
		// TODO Auto-generated constructor stub
	}
}