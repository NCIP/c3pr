package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Kulasekaran
 * @version 1.0
 */

@MappedSuperclass
public abstract class Person extends AbstractDomainObject implements Serializable
{			
	private String firstName;
	
	private String lastName;
		
	private Date birthDate;
		
	private String administritativeGenderCode;
	
	private String ethnicGroupCode;
		
	private String raceCode;

	@Column(name = "FIRST_NAME", length = 20, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LAST_NAME", length = 20, nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "BIRTH_DATE", nullable = false)
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = "ADMINISTRITATIVE_GENDER_CODE", length = 20)
	public String getAdministritativeGenderCode() {
		return administritativeGenderCode;
	}

	public void setAdministritativeGenderCode(String administritativeGenderCode) {
		this.administritativeGenderCode = administritativeGenderCode;
	}

	@Column(name = "ETHNIC_GROUP_CODE", length = 20)
	public String getEthnicGroupCode() {
		return ethnicGroupCode;
	}

	public void setEthnicGroupCode(String ethnicGroupCode) {
		this.ethnicGroupCode = ethnicGroupCode;
	}
	
	@Column(name = "RACE_CODE", length = 20)
	public String getRaceCode() {
		return raceCode;
	}
	
	public void setRaceCode(String raceCode) {
		this.raceCode = raceCode;
	}		
}
