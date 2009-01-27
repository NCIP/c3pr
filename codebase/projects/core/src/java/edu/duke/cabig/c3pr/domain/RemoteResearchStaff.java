package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
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
@Table(name = "remote_research_staff")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "remote_research_staff_id_seq") })
public class RemoteResearchStaff extends ResearchStaff implements RemoteResolvable {
	
	@Transient
	public String getUniqueId() {
		return getEmailAsString();
	}
	
	@OneToMany
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "RRS_ID")
	public List<ContactMechanism> getContactMechanisms() {
	    return contactMechanisms;
	}
	
	public void setContactMechanisms(List<ContactMechanism> contactMechanisms) {
	    this.contactMechanisms = contactMechanisms;
	}
}