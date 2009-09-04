package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.CommonUtils;

@Entity
@Table(name = "study_subject_consents")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_SUBJECT_CONSENTS_ID_SEQ") })
public class StudySubjectConsentVersion extends AbstractMutableDeletableDomainObject{

	private Consent consent;
	private Date informedConsentSignedDate ;
	private StudySubjectStudyVersion studySubjectStudyVersion;

	@OneToOne
    @JoinColumn(name="consent_id", nullable=false)
    @Cascade( { CascadeType.LOCK})
	public Consent getConsent() {
		return consent;
	}

	public void setConsent(Consent consent) {
		this.consent = consent;
	}

	public void setInformedConsentSignedDate(Date informedConsentSignedDate) {
		this.informedConsentSignedDate = informedConsentSignedDate;
	}

	public Date getInformedConsentSignedDate() {
		return informedConsentSignedDate;
	}

	@Transient
	public String getInformedConsentSignedDateStr() {
		return CommonUtils.getDateString(informedConsentSignedDate);
	}

	@ManyToOne
	@JoinColumn(name="study_subject_ver_id", nullable=false)
    @Cascade( { CascadeType.LOCK})
	public StudySubjectStudyVersion getStudySubjectStudyVersion() {
		return studySubjectStudyVersion;
	}

	public void setStudySubjectStudyVersion(
			StudySubjectStudyVersion studySubjectStudyVersion) {
		this.studySubjectStudyVersion = studySubjectStudyVersion;
	}

}
