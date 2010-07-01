package edu.duke.cabig.c3pr.domain;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.utils.CommonUtils;
import edu.duke.cabig.c3pr.utils.DateUtil;

@Entity
@Table(name = "study_subject_consents")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_SUBJECT_CONSENTS_ID_SEQ") })
public class StudySubjectConsentVersion extends AbstractMutableDeletableDomainObject{

	private Consent consent;
	private Date consentDeliveryDate;
	private ConsentingMethod consentingMethod;
	private String consentPresenter;
	private Date informedConsentSignedTimestamp;
	
	
	@Transient
	public Time getInformedConsentSignedTime() {
		return informedConsentSignedTimestamp!=null?DateUtil.getTime(informedConsentSignedTimestamp):null;
	}

	public void setInformedConsentSignedTime(Time informedConsentSignedTime) {
		Calendar cal = new GregorianCalendar();
		if(informedConsentSignedTime==null){
			informedConsentSignedTimestamp = new Date();
		}
		cal.setTime(informedConsentSignedTimestamp);
		cal.set(Calendar.HOUR, informedConsentSignedTime.getHours());
		cal.set(Calendar.MINUTE, informedConsentSignedTime.getMinutes());
		cal.set(Calendar.SECOND,informedConsentSignedTime.getSeconds());
		this.setInformedConsentSignedTimestamp(cal.getTime());
	}

	private List<SubjectConsentQuestionAnswer> subjectConsentAnswers = new ArrayList<SubjectConsentQuestionAnswer>();

	public void setInformedConsentSignedDate(Date informedConsentSignedDate) {
		Calendar cal = new GregorianCalendar(0,0,0,0,0,0);
		if(informedConsentSignedTimestamp!=null){
			cal.setTime(informedConsentSignedTimestamp);
		}
		cal.set(Calendar.YEAR, informedConsentSignedDate.getYear() +1900);
		cal.set(Calendar.MONTH, informedConsentSignedDate.getMonth());
		cal.set(Calendar.DATE,informedConsentSignedDate.getDate());
		this.setInformedConsentSignedTimestamp(cal.getTime());
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="informed_consent_signed_tstamp")
	public Date getInformedConsentSignedTimestamp() {
		return informedConsentSignedTimestamp;
	}

	public void setInformedConsentSignedTimestamp(
			Date informedConsentSignedTimestamp) {
		this.informedConsentSignedTimestamp = informedConsentSignedTimestamp;
	}

	@OneToOne
    @JoinColumn(name="consent_id", nullable=false)
    @Cascade( { CascadeType.LOCK})
	public Consent getConsent() {
		return consent;
	}
	
	public Date getConsentDeliveryDate() {
		return consentDeliveryDate;
	}

	public void setConsentDeliveryDate(Date consentDeliveryDate) {
		this.consentDeliveryDate = consentDeliveryDate;
	}

	@Enumerated(EnumType.STRING)
	public ConsentingMethod getConsentingMethod() {
		return consentingMethod;
	}

	public void setConsentingMethod(ConsentingMethod consentingMethod) {
		this.consentingMethod = consentingMethod;
	}

	public String getConsentPresenter() {
		return consentPresenter;
	}

	public void setConsentPresenter(String consentPresenter) {
		this.consentPresenter = consentPresenter;
	}


	@OneToMany
	@JoinColumn(name="stu_sub_con_ver_id",nullable=false)
	@Cascade(value={CascadeType.ALL,CascadeType.DELETE_ORPHAN})
	public List<SubjectConsentQuestionAnswer> getSubjectConsentAnswers() {
		return subjectConsentAnswers;
	}

	public void setSubjectConsentAnswers(
			List<SubjectConsentQuestionAnswer> subjectConsentAnswers) {
		this.subjectConsentAnswers = subjectConsentAnswers;
	}
	
	public void addSubjectConsentAnswer(SubjectConsentQuestionAnswer subjectConsentQuestionAnswer){
		this.getSubjectConsentAnswers().add(subjectConsentQuestionAnswer);
	}

	public void setConsent(Consent consent) {
		this.consent = consent;
	}

	@Transient
	public Date getInformedConsentSignedDate() {
		return informedConsentSignedTimestamp!=null?DateUtil.getDate("mm/dd/yyyy", informedConsentSignedTimestamp):null;
	}

	@Transient
	public String getInformedConsentSignedDateStr() {
		return informedConsentSignedTimestamp!=null?CommonUtils.getDateString(getInformedConsentSignedDate()):null;
	}
	
	@Transient
	public String getConsentDeliveryDateStr() {
		return consentDeliveryDate!=null?CommonUtils.getDateString(consentDeliveryDate):null;
	}

}
