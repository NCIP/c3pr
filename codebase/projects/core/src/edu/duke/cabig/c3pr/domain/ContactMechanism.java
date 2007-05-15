package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Ramakrishna
 */
@Entity
@Table (name="contact_mechanisms")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="CONTACT_MECHANISMS_ID_SEQ")
    }
)
public class ContactMechanism extends AbstractDomainObject {

	private String type;
	private String value;
		
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
 
}
 