package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

// TODO: Auto-generated Javadoc
/**
 * The Class StudySubjectConsentVersion.
 */
@Entity
@Table(name = "stu_sub_cosnt_vers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STU_SUB_COSNT_VERS_ID_SEQ") })
public class StudySubjectConsentVersion extends AbstractMutableDeletableDomainObject{

	/** The consent version. */
	private ConsentVersion consentVersion;
	
	/** The informed consent signed date. */
	private Date informedConsentSignedDate ;
	
	/** The study subject. */
	private StudySubject studySubject ;
	
    /**
     * Gets the consent version.
     * 
     * @return the consent version
     */
	@OneToOne
    @JoinColumn(name="consent_version_id", nullable=false)
    @Cascade( { CascadeType.LOCK})
	public ConsentVersion getConsentVersion() {
		return consentVersion;
	}

	/**
	 * Sets the consent version.
	 * 
	 * @param consentVersion the new consent version
	 */
	public void setConsentVersion(ConsentVersion consentVersion) {
		this.consentVersion = consentVersion;
	}

	/**
	 * Sets the informed consent signed date.
	 * 
	 * @param informedConsentSignedDate the informedConsentSignedDate to set
	 */
	public void setInformedConsentSignedDate(Date informedConsentSignedDate) {
		this.informedConsentSignedDate = informedConsentSignedDate;
	}

	/**
	 * Gets the informed consent signed date.
	 * 
	 * @return the informedConsentSignedDate
	 */
	public Date getInformedConsentSignedDate() {
		return informedConsentSignedDate;
	}
	
	/**
	 * Gets the study subject.
	 * 
	 * @return the study subject
	 */
	
	@ManyToOne
	@JoinColumn(name="stu_sub_id", nullable=false)
    @Cascade( { CascadeType.LOCK})
	public StudySubject getStudySubject() {
		return studySubject;
	}

	/**
	 * Sets the study subject.
	 * 
	 * @param studySubject the new study subject
	 */
	public void setStudySubject(StudySubject studySubject) {
		this.studySubject = studySubject;
	}

	
}
