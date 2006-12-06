/*
 * Created Thu Apr 20 17:45:27 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;


import java.io.Serializable;
import java.util.Collection;

/**
 * A class that represents a row in the 'ADDRESS' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Address implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    private Integer id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String stateProvince;
    private String zipPostalCode;
    private String countryCode;
	private AddressType addressType;
	
	/*TODO - Add in data model*/
	private String phone;
	private String fax;
	
	
    /**
     * Simple constructor of Address instances.
     */
    public Address()
    {
    }
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public AddressType getAddressType() {
		return addressType;
	}
	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public int getHashValue() {
		return hashValue;
	}
	public void setHashValue(int hashValue) {
		this.hashValue = hashValue;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStateProvince() {
		return stateProvince;
	}
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}
	public String getZipPostalCode() {
		return zipPostalCode;
	}
	public void setZipPostalCode(String zipPostalCode) {
		this.zipPostalCode = zipPostalCode;
	}

	public String getAddressTypeDesc(){
		if(getAddressType()==null) return null;
		return getAddressType().getDescription();
	}
	
	public String getAddressTypeCode(){
		if(getAddressType()==null) return null;
		return getAddressType().getCode();
	}
}
