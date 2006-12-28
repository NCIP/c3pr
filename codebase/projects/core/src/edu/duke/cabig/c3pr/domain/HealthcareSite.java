package edu.duke.cabig.c3pr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * @author Ram Chilukuri
 *         Kulasekaran
 */
 
 @Entity
 @Table (name = "healthcare_sites")
 @GenericGenerator(name="id-generator", strategy = "native",
     parameters = {
         @Parameter(name="sequence", value="healthcare_sites_id_seq")
     }
 )
public class HealthcareSite extends Organization {
		 
    private String nciIdentifier;
	
    public HealthcareSite() {
    }

    @Column(name = "NCI_IDENTIFIER", length = 20, nullable = false)
    public String getNciIdentifier() {
        return nciIdentifier;
    }
    
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }    
}
