package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author Ram Chilukuri
 *         Kulasekaran
 */
 
 @Entity
 @Table (name = "healthcare_sites")
 @SequenceGenerator(name="id-generator",sequenceName="healthcare_sites_id_seq")
 /*@GenericGenerator(name="id-generator", strategy = "native",
     parameters = {
         @Parameter(name="sequence", value="healthcare_sites_id_seq")
     }
 )*/
public class HealthcareSite extends Organization {
		 
    private String nciIdentifier;
	
    public HealthcareSite() 
    {}
    
    public HealthcareSite(boolean initialise) {
    	super(true);
    }

    public String getNciIdentifier() {
        return nciIdentifier;
    }
    
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }    
}
