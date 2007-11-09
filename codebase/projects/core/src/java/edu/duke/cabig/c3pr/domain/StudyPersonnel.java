package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Priyatam
 */
@Entity
@Table (name="study_personnel")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="study_personnel_id_seq")
    }
)
public class StudyPersonnel extends AbstractMutableDeletableDomainObject implements Comparable<StudyPersonnel>{
		
	private ResearchStaff researchStaff;
	private StudyOrganization studyOrganization;
	private String roleCode;
	private String statusCode;
	private Date startDate;
	private Date endDate;
			
	@ManyToOne
    @JoinColumn(name = "research_staff_id")
	public ResearchStaff getResearchStaff() {
		return researchStaff;
	}
	
	public void setResearchStaff(ResearchStaff researchStaff) {
		this.researchStaff = researchStaff;
	}
		
	@ManyToOne
    @JoinColumn(name = "sto_id")    	
	public StudyOrganization getStudyOrganization() {
		return studyOrganization;
	}

	public void setStudyOrganization(StudyOrganization studyOrganization) {
		this.studyOrganization = studyOrganization;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public int compareTo(StudyPersonnel o) {
		if(this.equals(o))
			return 0;
		return 1;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((researchStaff == null) ? 0 : researchStaff.hashCode());
		result = PRIME * result + ((roleCode == null) ? 0 : roleCode.hashCode());
		result = PRIME * result + ((studyOrganization == null) ? 0 : studyOrganization.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		final StudyPersonnel other = (StudyPersonnel) obj;
		if (researchStaff == null) {
			if (other.researchStaff != null)
				return false;
		} else if (!researchStaff.equals(other.researchStaff))
			return false;
		if (roleCode == null) {
			if (other.roleCode != null)
				return false;
		} else if (!roleCode.equals(other.roleCode))
			return false;
		if (studyOrganization == null) {
			if (other.studyOrganization != null)
				return false;
		} else if (!studyOrganization.equals(other.studyOrganization))
			return false;
		return true;
	}
}