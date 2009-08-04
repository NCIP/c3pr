package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.SiteStudyStatus;

@Entity
@Table(name = "study_site_versions")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_SITE_VERSION_ID_SEQ") })
public class StudySiteStudyVersion extends AbstractMutableDeletableDomainObject {

	private Date irbApprovalDate;
	private Date startDate;
	private Integer targetAccrual;
	private List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
	private StudySite studySite;
	private StudyVersion studyVersion ;
    /** The site study status. */
    private SiteStudyStatus siteStudyStatus=SiteStudyStatus.PENDING;

    /**
     * Gets the site study status.
     *
     * @return the site study status
     */
    @Enumerated(EnumType.STRING)
    public SiteStudyStatus getSiteStudyStatus() {
        return siteStudyStatus;
    }
    
    /**
     * Sets the site study status.
     *
     * @param siteStudyStatus the new site study status
     */
    public void setSiteStudyStatus(SiteStudyStatus siteStudyStatus) {
        this.siteStudyStatus = siteStudyStatus;
    }
    
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

	@OneToMany(mappedBy = "studySiteStudyVersion")
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudySubjectStudyVersion> getStudySubjectStudyVersions() {
		return studySubjectStudyVersions;
	}
	public void setStudySubjectStudyVersions(
			List<StudySubjectStudyVersion> studySubjectStudyVersions) {
		this.studySubjectStudyVersions = studySubjectStudyVersions;
	}

}
