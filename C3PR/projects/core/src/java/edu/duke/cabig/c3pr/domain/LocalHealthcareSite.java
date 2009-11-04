package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Ram Chilukuri Kulasekaran
 */
@Entity
@DiscriminatorValue("Local")
public class LocalHealthcareSite extends HealthcareSite {
	
	
}