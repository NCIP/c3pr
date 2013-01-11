/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.AddressUse;
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
    
    private Set<AddressUseAssociation> addressUseAssociation = new LinkedHashSet<AddressUseAssociation>();

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result
				+ ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result
				+ ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result
				+ ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result
				+ ((streetAddress == null) ? 0 : streetAddress.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Address)) {
			return false;
		}
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (countryCode == null) {
			if (other.countryCode != null) {
				return false;
			}
		} else if (!countryCode.equals(other.countryCode)) {
			return false;
		}
		if (postalCode == null) {
			if (other.postalCode != null) {
				return false;
			}
		} else if (!postalCode.equals(other.postalCode)) {
			return false;
		}
		if (stateCode == null) {
			if (other.stateCode != null) {
				return false;
			}
		} else if (!stateCode.equals(other.stateCode)) {
			return false;
		}
		if (streetAddress == null) {
			if (other.streetAddress != null) {
				return false;
			}
		} else if (!streetAddress.equals(other.streetAddress)) {
			return false;
		}
		return true;
	}

	@OneToMany
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name="add_id")
    @OrderBy("id")
	public Set<AddressUseAssociation> getAddressUseAssociation() {
		return addressUseAssociation;
	}

	public void setAddressUseAssociation(
			Set<AddressUseAssociation> addressUseAssociation) {
		this.addressUseAssociation = addressUseAssociation;
	}
	
	@Transient
	public List<AddressUse> getAddressUses(){
		List<AddressUse> uses = new ArrayList<AddressUse>();
		for(AddressUseAssociation addressUseAssociation : getAddressUseAssociation()){
			uses.add(addressUseAssociation.getUse());
		}
		return uses;
	}
	
	public void setAddressUses(List<AddressUse> uses){
		addressUseAssociation.clear();
		for(AddressUse use: uses){
			addressUseAssociation.add(new AddressUseAssociation(use));
		}
	}
	
	public void addAddressUse(AddressUse use){
		addressUseAssociation.add(new AddressUseAssociation(use));
	}
}
