package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Kruttik
 */
@Entity
@DiscriminatorValue(value="E") 
public class ExclusionEligibilityCriteria extends EligibilityCriteria
{
	 
}