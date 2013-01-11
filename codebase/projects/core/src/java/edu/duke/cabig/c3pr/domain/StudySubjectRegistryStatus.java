/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "STU_SUB_REG_STATUSES")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STU_SUB_REG_STATUSES_id_seq") })
public class StudySubjectRegistryStatus extends AbstractMutableDeletableDomainObject{

	private Date effectiveDate;
	
	private PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus;
	
	private List<RegistryStatusReason> reasons= new ArrayList<RegistryStatusReason>();
	
	private String commentText;
	
	public StudySubjectRegistryStatus() {
		super();
	}

	public StudySubjectRegistryStatus(
			Date effectiveDate,
			PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus, String comment,
			List<RegistryStatusReason> reasons) {
		this(effectiveDate, permissibleStudySubjectRegistryStatus, comment);
		this.reasons = reasons;
	}

	public StudySubjectRegistryStatus(
			Date effectiveDate,
			PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus, String comment) {
		super();
		this.effectiveDate = effectiveDate;
		this.permissibleStudySubjectRegistryStatus = permissibleStudySubjectRegistryStatus;
		this.commentText = comment;
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

	@ManyToMany
	@Fetch(FetchMode.SUBSELECT)
	@Cascade( { CascadeType.LOCK })
	@JoinTable(
        name="REGISTRY_REASONS_ASSN",
        joinColumns=@JoinColumn(name="STU_SUB_REG_ST_ID"),
        inverseJoinColumns=@JoinColumn(name="REASON_ID")
    )
	public List<RegistryStatusReason> getReasons() {
		return reasons;
	}

	public void setReasons(List<RegistryStatusReason> reasons) {
		this.reasons = reasons;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String comment) {
		this.commentText = comment;
	}

}
