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

/**
 * @author Ramakrishna
 */
@Entity
@Table(name = "research_staff")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "research_staff_id_seq") })
public class LocalResearchStaff extends ResearchStaff {
	
	@Override
	public String getFirstName() {
		return super.getFirstName();
	}

	@Override
	public String getLastName() {
		return super.getLastName();
	}

	@Override
	public String getMaidenName() {
		return super.getMaidenName();
	}
	
	@Override
	public String getNciIdentifier() {
		return super.getNciIdentifier();
	}

	@Override
	public String getMiddleName() {
		return super.getMiddleName();
	}
	
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
       return super.equals(obj);
    }
    
    @OneToMany
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "RS_ID")
	public List<ContactMechanism> getContactMechanisms() {
	    return contactMechanisms;
	}
	
	public void setContactMechanisms(List<ContactMechanism> contactMechanisms) {
	    this.contactMechanisms = contactMechanisms;
	}
}