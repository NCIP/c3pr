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

// TODO: Auto-generated Javadoc
/**
 * The Class StudySiteStudyVersion.
 */
@Entity
@Table(name = "study_site_versions")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_SITE_VERSIONS_ID_SEQ") })
public class StudySiteStudyVersion extends AbstractMutableDeletableDomainObject implements Comparable<StudySiteStudyVersion>{

	/** The irb approval date. */
	private Date irbApprovalDate;
	
	/** The start date. */
	private Date startDate;
	
	/** The end date. */
	private Date endDate;
	
	/** The target accrual. */
	private Integer targetAccrual;
	
	/** The study subject study versions. */
	private List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
	
	/** The study site. */
	private StudySite studySite;
	
	/** The study version. */
	private StudyVersion studyVersion ;
    
    /**
     * Gets the end date.
     * 
     * @return the end date
     */
    public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date.
	 * 
	 * @param endDate the new end date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the study site.
	 * 
	 * @return the study site
	 */
	@ManyToOne
    @JoinColumn(name = "sto_id", nullable=false)
    @Cascade( { CascadeType.LOCK})
	public StudySite getStudySite() {
		return studySite;
	}
	
	/**
	 * Sets the study site.
	 * 
	 * @param studySite the new study site
	 */
	public void setStudySite(StudySite studySite) {
		this.studySite = studySite;
	}

	/**
	 * Gets the study version.
	 * 
	 * @return the study version
	 */
	@ManyToOne
    @JoinColumn(name = "stu_version_id")
    @Cascade( { CascadeType.LOCK})
	public StudyVersion getStudyVersion() {
		return studyVersion;
	}
	
	/**
	 * Sets the study version.
	 * 
	 * @param studyVersion the new study version
	 */
	public void setStudyVersion(StudyVersion studyVersion) {
		this.studyVersion = studyVersion;
	}
	
	/**
	 * Gets the irb approval date.
	 * 
	 * @return the irb approval date
	 */
	public Date getIrbApprovalDate() {
		return irbApprovalDate;
	}
	
	/**
	 * Sets the irb approval date.
	 * 
	 * @param irbApprovalDate the new irb approval date
	 */
	public void setIrbApprovalDate(Date irbApprovalDate) {
		this.irbApprovalDate = irbApprovalDate;
	}
	
	/**
	 * Gets the start date.
	 * 
	 * @return the start date
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * Sets the start date.
	 * 
	 * @param startDate the new start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Gets the target accrual.
	 * 
	 * @return the target accrual
	 */
	public Integer getTargetAccrual() {
		return targetAccrual;
	}

	/**
	 * Sets the target accrual.
	 * 
	 * @param targetAccrual the new target accrual
	 */
	public void setTargetAccrual(Integer targetAccrual) {
		this.targetAccrual = targetAccrual;
	}

	/**
	 * Gets the study subject study versions.
	 * 
	 * @return the study subject study versions
	 */
	@OneToMany(mappedBy = "studySiteStudyVersion")
	@Cascade(value = { CascadeType.LOCK, CascadeType.DELETE_ORPHAN })
	public List<StudySubjectStudyVersion> getStudySubjectStudyVersions() {
		return studySubjectStudyVersions;
	}
	
	/**
	 * Sets the study subject study versions.
	 * 
	 * @param studySubjectStudyVersions the new study subject study versions
	 */
	public void setStudySubjectStudyVersions(
			List<StudySubjectStudyVersion> studySubjectStudyVersions) {
		this.studySubjectStudyVersions = studySubjectStudyVersions;
	}

	/**
	 * Adds the study subject study version.
	 * 
	 * @param studySubjectStudyVersion the study subject study version
	 */
	public void addStudySubjectStudyVersion(StudySubjectStudyVersion studySubjectStudyVersion) {
		this.getStudySubjectStudyVersions().add(studySubjectStudyVersion);
		studySubjectStudyVersion.setStudySiteStudyVersion(this);
	}


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(StudySiteStudyVersion studySiteStudyVersion) {
		return this.irbApprovalDate.compareTo(studySiteStudyVersion.getIrbApprovalDate());
	}

}
