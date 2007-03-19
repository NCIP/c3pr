package edu.duke.cabig.c3pr.domain;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import edu.duke.cabig.c3pr.utils.DateUtil;

/**
 * @author Kulasekaran
 * @version 1.0
 */

@MappedSuperclass
public abstract class Person extends AbstractGridIdentifiableDomainObject
{			
	private String firstName;
	
	private String lastName;
		
	private Date birthDate;
	
	private String birthDateStr;
		
	private String administrativeGenderCode;
	
	private String ethnicGroupCode;
		
	private String raceCode;

    private String maritalStatusCode;

    private Address address;
	
	@OneToOne(cascade={CascadeType.ALL}, optional=false)
    @JoinColumn(name="ADD_ID" ,nullable=false)
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
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
	
	@Transient
	public String getBirthDateStr() {
		try {
			return DateUtil.formatDate(birthDate, "MM/dd/yyyy");
		}
		catch(ParseException e){
			//do nothing
		}
		return "";
	}

    public String getMaritalStatusCode() {
        return maritalStatusCode;
    }

    public void setMaritalStatusCode(String maritalStatusCode) {
        this.maritalStatusCode = maritalStatusCode;
    }
}