package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "investigators")
@GenericGenerator(name = "id-generator", strategy = "sequence", parameters = { @Parameter(name = "sequence", value = "investigators_id_seq") })
public class BaseInvestigatorDataContainer extends AbstractMutableDeletableDomainObject{

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMaidenName() {
		return maidenName;
	}
	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}
	public String getDtype() {
		return dtype;
	}
	public void setDtype(String dtype) {
		this.dtype = dtype;
	}
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String maidenName;
	private String assignedIdentifier;
	private String dtype;
	

	public String getAssignedIdentifier() {
		return assignedIdentifier;
	}
	public void setAssignedIdentifier(String assignedIdentifier) {
		this.assignedIdentifier = assignedIdentifier;
	}
	
}
