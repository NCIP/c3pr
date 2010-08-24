package edu.duke.cabig.c3pr.domain;

import java.util.StringTokenizer;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * @author Ram Chilukuri
 */

@Entity
@Table(name = "addresses")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "addresses_id_seq") })
public class Address extends AbstractMutableDeletableDomainObject {
    private String streetAddress;

    private String city;

    private String stateCode;

    private String postalCode;

    private String countryCode;

    public Address() {
    }

    /**
     * Convenience constructor. Please use with care (watch parameters order).
     * @param streetAddress
     * @param city
     * @param stateCode
     * @param postalCode
     * @param countryCode
     */
    public Address(String streetAddress, String city, String stateCode,
			String postalCode, String countryCode) {
		super();
		this.streetAddress = streetAddress;
		this.city = city;
		this.stateCode = stateCode;
		this.postalCode = postalCode;
		this.countryCode = countryCode;
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

    @Transient
    public boolean isBlank() {

        if (StringUtils.getBlankIfNull(getStreetAddress()).equals("")
                        && StringUtils.getBlankIfNull(getCity()).equals("")
                        && StringUtils.getBlankIfNull(getCountryCode()).equals("")
                        && StringUtils.getBlankIfNull(getPostalCode()).equals("")
                        && StringUtils.getBlankIfNull(getStateCode()).equals("")) {
            return true;
        }
        return false;

    }
    
    @Transient
    public String getAddressString(){
    	String addressStr = getStreetAddress() + "," + getCity()+ "," + getStateCode()+ "," + getPostalCode()+  "," + getCountryCode();
    	addressStr = formatAddress(addressStr)  ;
    	return addressStr ;
    }

	private String formatAddress(String addressStr) {
		String address = "" ;
		StringTokenizer st = new StringTokenizer(addressStr, ",");
		int count = 0 ;
	     while (st.hasMoreTokens()) {
	    	 String token = st.nextToken() ;
	    	 if(!"null".equalsIgnoreCase(token)){
		    	 if(count == 0){
		    		 address +=  token;
		    	 }else{
		    		 address += ", " + token ;
		    	 }
		    	 count ++ ;
	    	 }
	     }
		return address ;
	}
}
