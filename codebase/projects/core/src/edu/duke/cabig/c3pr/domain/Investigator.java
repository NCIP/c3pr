package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Priyatam
 */
@Entity
@Table (name="investigators")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="investigators_id_seq")
    }
)
public class Investigator extends AbstractDomainObject {
	
	private String firstName;
    private String lastName;
    private String nciIdentifier;
    private List<HealthcareSiteInvestigator> healthcareSiteInvestigators 
    	= new ArrayList<HealthcareSiteInvestigator>(); 
    
    private String fullName;
    
    // business methods
    	   	    
    @Transient
    public String getLastFirst() {
        StringBuilder name = new StringBuilder();
        boolean hasFirstName = getFirstName() != null;
        if (getLastName() != null) {
            name.append(getLastName());
            if (hasFirstName) name.append(", ");
        }
        if (hasFirstName) {
            name.append(getFirstName());
        }
        return name.toString();
    }  
    
    @Transient
    public String getFullName() {
        StringBuilder name = new StringBuilder();
        boolean hasLastName = getLastName() != null;
        if (getFirstName() != null) {
            name.append(getFirstName());
            if (hasLastName) name.append(' ');
        }
        if (hasLastName) {
            name.append(getLastName());
        }
        return name.toString();
    }
    
    public void addHealthcareSiteInvestigator(HealthcareSiteInvestigator hcsi) {
    	healthcareSiteInvestigators.add(hcsi);    
        hcsi.setInvestigator(this);
    }    
    	
	@OneToMany (mappedBy = "investigator")    
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<HealthcareSiteInvestigator> getHealthcareSiteInvestigators() {
		return healthcareSiteInvestigators;
	}

	public void setHealthcareSiteInvestigators(List<HealthcareSiteInvestigator> 
		healthcareSiteInvestigators) {
		this.healthcareSiteInvestigators = healthcareSiteInvestigators;
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

	public String getNciIdentifier() {
		return nciIdentifier;
	}

	public void setNciIdentifier(String nciIdentifier) {
		this.nciIdentifier = nciIdentifier;
	}
	       
	
	
   
}
