package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Vinay Gangoli 
 */
@Entity
@DiscriminatorValue("Local")
public class LocalStudy extends Study {
	
	public LocalStudy(){
		super();
	}
	
	public LocalStudy(boolean forSearchByExample){
		super(forSearchByExample);
	}
}
