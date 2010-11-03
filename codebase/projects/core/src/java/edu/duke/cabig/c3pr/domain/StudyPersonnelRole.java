package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "stu_prsnl_role")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "stu_prsnl_role_id_seq") })
public class StudyPersonnelRole extends AbstractMutableDeletableDomainObject {
	
	public StudyPersonnelRole(){
	}
	
	public StudyPersonnelRole(String role){
		this.role = role;
	}
	
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// setting hash code to a plain constant value as a safety measure related to CPR-2142.
		// In Participant, bags were changed to sets. 
		return 1;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof StudyPersonnelRole)) {
			return false;
		}
		StudyPersonnelRole other = (StudyPersonnelRole) obj;
		if (role != other.role) {
			return false;
		}
		return true;
	}

}
