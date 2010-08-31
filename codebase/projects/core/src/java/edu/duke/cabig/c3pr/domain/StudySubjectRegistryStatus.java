package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "STU_SUB_REG_STATUSES")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STU_SUB_REG_STATUSES_id_seq") })
public class StudySubjectRegistryStatus extends AbstractMutableDeletableDomainObject{

	private Date effectiveDate;
	
	private PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus;
	
	private List<RegistryStatusReason> reasons= new ArrayList<RegistryStatusReason>();
	
	public StudySubjectRegistryStatus() {
		super();
	}

	public StudySubjectRegistryStatus(
			Date effectiveDate,
			PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus,
			List<RegistryStatusReason> reasons) {
		super();
		this.effectiveDate = effectiveDate;
		this.permissibleStudySubjectRegistryStatus = permissibleStudySubjectRegistryStatus;
		this.reasons = reasons;
	}

	public StudySubjectRegistryStatus(
			Date effectiveDate,
			PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus) {
		super();
		this.effectiveDate = effectiveDate;
		this.permissibleStudySubjectRegistryStatus = permissibleStudySubjectRegistryStatus;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@ManyToOne
	@Cascade(value = { CascadeType.LOCK })
	@JoinColumn(name = "per_reg_st_id", nullable = false)
	public PermissibleStudySubjectRegistryStatus getPermissibleStudySubjectRegistryStatus() {
		return permissibleStudySubjectRegistryStatus;
	}

	public void setPermissibleStudySubjectRegistryStatus(
			PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus) {
		this.permissibleStudySubjectRegistryStatus = permissibleStudySubjectRegistryStatus;
	}

	@OneToMany
	@Cascade( { CascadeType.LOCK })
	@JoinColumn(name = "stu_sub_reg_st_id")
	public List<RegistryStatusReason> getReasons() {
		return reasons;
	}

	public void setReasons(List<RegistryStatusReason> reasons) {
		this.reasons = reasons;
	}
	
	

}