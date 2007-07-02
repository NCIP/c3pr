package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

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
//@AssociationOverride( name="contactMechanisms", joinColumns= @JoinColumn(name="INV_ID") )
public class Investigator extends Person {
    private String nciIdentifier;
    private LazyListHelper lazyListHelper;
    private String fullName;
    
    // business methods
    	   	    
    public Investigator() {
    	lazyListHelper=new LazyListHelper();
    	lazyListHelper.add(HealthcareSiteInvestigator.class, new BiDirectionalInstantiateFactory<HealthcareSiteInvestigator>(HealthcareSiteInvestigator.class,this));
	}
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
    	hcsi.setInvestigator(this);
    	lazyListHelper.getLazyList(HealthcareSiteInvestigator.class).add(hcsi);
    }    
    	
	@OneToMany (mappedBy = "investigator")    
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<HealthcareSiteInvestigator> getHealthcareSiteInvestigatorsInternal() {
		return lazyListHelper.getInternalList(HealthcareSiteInvestigator.class);
	}

	@Transient
	public List<HealthcareSiteInvestigator> getHealthcareSiteInvestigators() {
		return lazyListHelper.getLazyList(HealthcareSiteInvestigator.class);
	}
	
	public void setHealthcareSiteInvestigators(List<HealthcareSiteInvestigator>	healthcareSiteInvestigators) {
	}
	public void setHealthcareSiteInvestigatorsInternal(List<HealthcareSiteInvestigator> 
		healthcareSiteInvestigators) {
		lazyListHelper.setInternalList(HealthcareSiteInvestigator.class, healthcareSiteInvestigators);
	}

	public String getNciIdentifier() {
		return nciIdentifier;
	}

	public void setNciIdentifier(String nciIdentifier) {
		this.nciIdentifier = nciIdentifier;
	}	
	
	@OneToMany
	@Cascade(value={CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	@JoinColumn(name="INV_ID")
	public List<ContactMechanism> getContactMechanisms()
	{
		return contactMechanisms;
	}
	
	public void setContactMechanisms(List<ContactMechanism> contactMechanisms)
	{
		this.contactMechanisms = contactMechanisms;
	}
   
}