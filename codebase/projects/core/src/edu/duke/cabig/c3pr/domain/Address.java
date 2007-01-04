package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author Ram Chilukuri
 */
 
 @Entity
 @Table (name = "addresses")
 @SequenceGenerator(name="id-generator",sequenceName="addresses_id_seq")
 /*@GenericGenerator(name="id-generator", strategy = "native",
     parameters = {
         @Parameter(name="sequence", value="addresses_id_seq")
     }
 )*/
public class Address extends AbstractDomainObject {
    private String streetAddress;
    private String city;
    private String stateCode;
    private String postalCode;
    private String countryCode;
    
    public Address() {
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
