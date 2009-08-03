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
@Table(name = "stu_site_stu_versions")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STU_SITE_STU_VERSION_ID_SEQ") })
public class StudySiteStudyVersion extends AbstractMutableDeletableDomainObject {

	private Date endDate;
	private Date irbApprovalDate;
	private Date startDate;
	private Integer targetAccrual;
	private List<StudySubjectStudyVersion> studySubjectStudyVersion = new ArrayList<StudySubjectStudyVersion>();
	private StudySite studySite;
	private StudyVersion studyVersion ;


	@ManyToOne
    @JoinColumn(name = "stu_site_id", nullable=false)
    @Cascade( { CascadeType.LOCK, CascadeType.SAVE_UPDATE, CascadeType.MERGE})
	public StudySite getStudySite() {
		return studySite;
	}
	public void setStudySite(StudySite studySite) {
		this.studySite = studySite;
	}

	@ManyToOne
    @JoinColumn(name = "stu_version_id", nullable=false)
    @Cascade( { CascadeType.LOCK, CascadeType.SAVE_UPDATE, CascadeType.MERGE})
	public StudyVersion getStudyVersion() {
		return studyVersion;
	}
	public void setStudyVersion(StudyVersion studyVersion) {
		this.studyVersion = studyVersion;
	}
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
	public void setStudySubjectStudyVersion(List<StudySubjectStudyVersion> studySubjectStudyVersion) {
		this.studySubjectStudyVersion = studySubjectStudyVersion;
	}

	@OneToMany(mappedBy = "studySiteStudyVersion")
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudySubjectStudyVersion> getStudySubjectStudyVersion() {
		return studySubjectStudyVersion;
	}

}
