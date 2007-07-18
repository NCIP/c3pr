package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Kruttik
 */
@Entity
@DiscriminatorValue(value = "I")
public class InclusionEligibilityCriteria extends EligibilityCriteria {
}