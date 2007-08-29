package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import javax.persistence.Entity;
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
public class ContactMechanism extends AbstractMutableDomainObject {

	private ContactMechanismType type;
	private String value;
		
	public ContactMechanismType getType() {
		return type;
	}
	
	public void setType(ContactMechanismType type) {
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
 
}
 