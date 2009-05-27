package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Column;
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
import org.hibernate.validator.NotNull;



// TODO: Auto-generated Javadoc
/**
 * The Class ConsentVersion.
 */
@Entity
@Table(name = "consent_versions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CONSENT_VERSIONS_ID_SEQ") })
public class ConsentVersion extends AbstractMutableDeletableDomainObject{

	public ConsentVersion(){
		this.setLatestIndicator(false);
	}
	
	
	/** The consent version name. */
	private String name;
	
	/** The consent date. */
	private Date date;
	
	/** The use latest indicator. */
	private Boolean latestIndicator ;
	
	/** The consent. */
	private Consent consent;
	
	/** The study subject consent version. */
	private StudySubjectConsentVersion studySubjectConsentVersion;
	/**
	 * Gets the consent.
	 * 
	 * @return the consent
	 */
	@ManyToOne
    @JoinColumn(name = "consent_id", nullable = false)
    @Cascade( { CascadeType.LOCK})
	public Consent getConsent() {
		return consent;
	}
	
	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	@NotNull
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	@NotNull
	@Column(name = "date_value")
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 * 
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the latest indicator.
	 * 
	 * @return the latest indicator
	 */
	public Boolean getLatestIndicator() {
		return latestIndicator;
	}

	/**
	 * Sets the latest indicator.
	 * 
	 * @param latestIndicator the new latest indicator
	 */
	public void setLatestIndicator(Boolean latestIndicator) {
		this.latestIndicator = latestIndicator;
	}

	/**
	 * Sets the consent.
	 * 
	 * @param consent the new consent
	 */
	public void setConsent(Consent consent) {
		this.consent = consent;
	}
	
	/**
	 * Sets the study subject consent version.
	 * 
	 * @param studySubjectConsentVersion the new study subject consent version
	 */
	public void setStudySubjectConsentVersion(StudySubjectConsentVersion studySubjectConsentVersion) {
		this.studySubjectConsentVersion = studySubjectConsentVersion;
	}
	
	/**
	 * Gets the study subject consent version.
	 * 
	 * @return the study subject consent version
	 */
	@OneToOne(mappedBy = "consentVersion")
	 @Cascade( { CascadeType.LOCK})
	public StudySubjectConsentVersion getStudySubjectConsentVersion() {
		return studySubjectConsentVersion;
	}
	
	
}
