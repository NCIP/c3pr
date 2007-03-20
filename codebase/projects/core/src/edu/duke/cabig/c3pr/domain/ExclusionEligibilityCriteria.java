package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Kruttik
 */
@Entity
@DiscriminatorValue(value="E") 
public abstract class ExclusionEligibilityCriteria extends EligibilityCriteria
{
	 
}