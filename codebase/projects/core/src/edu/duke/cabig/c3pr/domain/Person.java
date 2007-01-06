package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.MappedSuperclass;

/**
 * @author Kulasekaran
 * @version 1.0
 */

@MappedSuperclass
public abstract class Person extends AbstractDomainObject
{			
	private String firstName;
	
	private String lastName;
		
	private Date birthDate;
		
	private String administrativeGenderCode;
	
	private String ethnicGroupCode;
		
	private String raceCode;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getAdministrativeGenderCode() {
		return administrativeGenderCode;
	}

	public void setAdministrativeGenderCode(String administritativeGenderCode) {
		this.administrativeGenderCode = administritativeGenderCode;
	}

	public String getEthnicGroupCode() {
		return ethnicGroupCode;
	}

	public void setEthnicGroupCode(String ethnicGroupCode) {
		this.ethnicGroupCode = ethnicGroupCode;
	}
	
	public String getRaceCode() {
		return raceCode;
	}
	
	public void setRaceCode(String raceCode) {
		this.raceCode = raceCode;
	}		
}
