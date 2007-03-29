package edu.duke.cabig.c3pr.domain;

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
@Table (name="study_personnels")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="study_personnels_id_seq")
    }
)
public class StudyPersonnel extends AbstractDomainObject {
		
	private ResearchStaff researchStaff;
	private StudySite studySite;
	private String roleCode;
	private String statusCode;
	private Date startDate;
	private Date endDate;
			
	@ManyToOne
    @JoinColumn(name = "research_staffs_id")
	public ResearchStaff getResearchStaff() {
		return researchStaff;
	}
	
	public void setResearchStaff(ResearchStaff researchStaff) {
		this.researchStaff = researchStaff;
	}
		
	@ManyToOne
    @JoinColumn(name = "sts_id")    	
	public StudySite getStudySite() {
		return studySite;
	}

	public void setStudySite(StudySite studySite) {
		this.studySite = studySite;
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
}
