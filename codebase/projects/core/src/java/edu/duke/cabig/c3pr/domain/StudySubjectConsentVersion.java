package edu.duke.cabig.c3pr.domain;

import java.text.ParseException;
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

import edu.duke.cabig.c3pr.utils.DateUtil;

@Entity
@Table(name = "stu_sub_cosnt_vers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STU_SUB_COSNT_VERS_ID_SEQ") })
public class StudySubjectConsentVersion extends AbstractMutableDeletableDomainObject{

	private ConsentVersion consentVersion;
	private Date informedConsentSignedDate ;
	private StudySubject studySubject ;

	@OneToOne
    @JoinColumn(name="consent_version_id", nullable=false)
    @Cascade( { CascadeType.LOCK})
	public ConsentVersion getConsentVersion() {
		return consentVersion;
	}

	public void setConsentVersion(ConsentVersion consentVersion) {
		this.consentVersion = consentVersion;
	}

	public void setInformedConsentSignedDate(Date informedConsentSignedDate) {
		this.informedConsentSignedDate = informedConsentSignedDate;
	}

	public Date getInformedConsentSignedDate() {
		return informedConsentSignedDate;
	}
	
	@ManyToOne
	@JoinColumn(name="stu_sub_id", nullable=false)
    @Cascade( { CascadeType.LOCK})
	public StudySubject getStudySubject() {
		return studySubject;
	}

	public void setStudySubject(StudySubject studySubject) {
		this.studySubject = studySubject;
	}
	
	@Transient
	public String getInformedConsentSignedDateStr() {
		if (informedConsentSignedDate != null) {
		try {
				return DateUtil.formatDate(informedConsentSignedDate, "MM/dd/yyyy");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}