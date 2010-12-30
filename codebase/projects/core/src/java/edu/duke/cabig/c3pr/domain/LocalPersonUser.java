package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import edu.duke.cabig.c3pr.constants.PersonUserType;


@Entity
@DiscriminatorValue("Local")
public class LocalPersonUser extends PersonUser{

    
	/**
	 * Instantiates a new LocalPersonUser.
	 */
	public LocalPersonUser() {
		super();
	}
	
	/**
	 * Instantiates a new LocalPersonUser for a given PersonUserType.
	 */
	public LocalPersonUser(PersonUserType personUserType) {
		super();
		setPersonUserType(personUserType);
	}
	
}
