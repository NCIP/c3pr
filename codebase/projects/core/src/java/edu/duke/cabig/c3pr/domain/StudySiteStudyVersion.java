package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "stu_site_stu_versions")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STU_SITE_STU_VERSION_ID_SEQ") })
public class StudySiteStudyVersion extends AbstractMutableDeletableDomainObject {
	
	private Date endDate;
	private Date irbApprovalDate;
	private Date startDate;
	private Integer targetAccrual;
	
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getIrbApprovalDate() {
		return irbApprovalDate;
	}
	public void setIrbApprovalDate(Date irbApprovalDate) {
		this.irbApprovalDate = irbApprovalDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Integer getTargetAccrual() {
		return targetAccrual;
	}
	public void setTargetAccrual(Integer targetAccrual) {
		this.targetAccrual = targetAccrual;
	}
	
}
