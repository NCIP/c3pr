package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.AddressUse;
import edu.duke.cabig.c3pr.utils.CommonUtils;
import edu.duke.cabig.c3pr.utils.DateUtil;
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

    public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	private String postalCode;

    private String countryCode;
    
    private Date startDate;
    
    private Date endDate;
    
    private boolean primaryIndicator = false;
    
    private String identifier;
    
    public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isPrimaryIndicator() {
		return primaryIndicator;
	}

	public void setPrimaryIndicator(boolean primaryIndicator) {
		this.primaryIndicator = primaryIndicator;
	}

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
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		return false;
	}

	@OneToMany(orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	@Cascade(value = { CascadeType.ALL})
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
	
	@Transient
	public String getStartDateStr(){
		return CommonUtils.getDateString(startDate);
	}
	
	@Transient
	public String getEndDateStr(){
		return CommonUtils.getDateString(endDate);
	}
}
