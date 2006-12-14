package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * A systematic evaluation of an observation or an
 * intervention (for example, treatment, drug, device, procedure or system) in one
 * or more subjects. Frequently this is a test of a particular hypothesis about
 * the treatment, drug, device, procedure or system. [CDAM]  A study can be either
 * primary or correlative. A study is considered a primary study if it has one or
 * more correlative studies. A correlative study extends the objectives or
 * observations of a primary study, enrolling the same, or a subset of the same,
 * subjects as the primary study. A Clinical Trial is a Study with type=
 * "intervention" with subjects of type="human". 
 * 
 * @author priyatam
 * @version 1.0
 */

@Entity
@Table (name = "STUDIES")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="C3PR_GENERAL_SEQ")
    }
)
public class Study extends AbstractDomainObject implements Comparable<Study>, Serializable{
	
	@Column(name = "BLINDED_INDICATOR", length = 4, nullable = false)	  
	private String blindedIndicator;
	
	@Column(name = "DESCRIPTION_TEXT", length = 50, nullable = false)	   
	private java.lang.String descriptionText;
	
	@Column(name = "DISEASE_CODE", length = 20, nullable = false)	   
	private java.lang.String diseaseCode;	

	@Column(name = "DISEASE_CODE_LONG_TITLE_TEXT", length = 50, nullable = false)	   
	private java.lang.String longTitleText;	
	
	@Column(name = "MONITOR_CODE", length = 20, nullable = false)	   
	private java.lang.String monitorCode;
	
	@Column(name = "MULTI_INSTITUTION_INDICATOR", length = 4, nullable = false)	   
	private String multiInstitutionIndicator;
	
	@Column(name = "NCI_IDENTIFIER", length = 4, nullable = false)	   
	private java.lang.String nciIdentifier;
	
	@Column(name = "PHASE_CODE", length = 20, nullable = false)	   	
	private java.lang.String phaseCode;
	
	@Column(name = "PRECIS_TEXT", length = 30, nullable = false)	   			
	private java.lang.String precisText;
	
	@Column(name = "RANDOMIZED_INDICATOR", length = 20, nullable = false)	   	
	private String randomizedIndicator;
	
	@Column(name = "SHORT_TITLE_TEXT", length = 30, nullable = false)	   	
	private java.lang.String shortTitleText;
	
	@Column(name = "SPONSOR_CODE", length = 20, nullable = false)			
	private java.lang.String sponsorCode;
	
	@Column(name = "STATUS_CODE", length = 20, nullable = false)	   		
	private java.lang.String status;
	
	@Column(name = "TARGET_ACCRUAL_NUMBER", length = 10, nullable = false)	   		
	private int targetAccrualNumber;
	
	@Column(name = "TYPE_CODE", length = 20, nullable = false)	   		
	private java.lang.String type;
//	public EligibilityCriteria eligibilityCriteriaCollection;
//	public StudyInvestigator studyInvestigatorCollection;
//	public Amendment amendmentCollection;
//	public StudySite studySiteCollection;
//	public Study study;

	public Study(){

	}
	
    public int compareTo(Study o) {
     //TODO
    	return 1;
    }
//	public Amendment getAmendmentCollection() {
//		return amendmentCollection;
//	}
//
//	public void setAmendmentCollection(Amendment amendmentCollection) {
//		this.amendmentCollection = amendmentCollection;
//	}

	public String getBlindedIndicator() {
		return blindedIndicator;
	}

	public void setBlindedIndicator(String blindedIndicator) {
		this.blindedIndicator = blindedIndicator;
	}

	public java.lang.String getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(java.lang.String descriptionText) {
		this.descriptionText = descriptionText;
	}

	public java.lang.String getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(java.lang.String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}

//	public EligibilityCriteria getEligibilityCriteriaCollection() {
//		return eligibilityCriteriaCollection;
//	}
//
//	public void setEligibilityCriteriaCollection(
//			EligibilityCriteria eligibilityCriteriaCollection) {
//		this.eligibilityCriteriaCollection = eligibilityCriteriaCollection;
//	}



	public java.lang.String getLongTitleText() {
		return longTitleText;
	}

	public void setLongTitleText(java.lang.String longTitleText) {
		this.longTitleText = longTitleText;
	}

	public java.lang.String getMonitorCode() {
		return monitorCode;
	}

	public void setMonitorCode(java.lang.String monitorCode) {
		this.monitorCode = monitorCode;
	}

	public String getMultiInstitutionIndicator() {
		return multiInstitutionIndicator;
	}

	public void setMultiInstitutionIndicator(String multiInstitutionIndicator) {
		this.multiInstitutionIndicator = multiInstitutionIndicator;
	}

	public java.lang.String getNciIdentifier() {
		return nciIdentifier;
	}

	public void setNciIdentifier(java.lang.String nciIdentifier) {
		this.nciIdentifier = nciIdentifier;
	}

	public java.lang.String getPhaseCode() {
		return phaseCode;
	}

	public void setPhaseCode(java.lang.String phaseCode) {
		this.phaseCode = phaseCode;
	}

	public java.lang.String getPrecisText() {
		return precisText;
	}

	public void setPrecisText(java.lang.String precisText) {
		this.precisText = precisText;
	}

	public String getRandomizedIndicator() {
		return randomizedIndicator;
	}

	public void setRandomizedIndicator(String randomizedIndicator) {
		this.randomizedIndicator = randomizedIndicator;
	}

	public java.lang.String getShortTitleText() {
		return shortTitleText;
	}

	public void setShortTitleText(java.lang.String shortTitleText) {
		this.shortTitleText = shortTitleText;
	}

	public java.lang.String getSponsorCode() {
		return sponsorCode;
	}

	public void setSponsorCode(java.lang.String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

//	public Study getStudy() {
//		return study;
//	}
//
//	public void setStudy(Study study) {
//		this.study = study;
//	}

//	public StudyInvestigator getStudyInvestigatorCollection() {
//		return studyInvestigatorCollection;
//	}
//
//	public void setStudyInvestigatorCollection(
//			StudyInvestigator studyInvestigatorCollection) {
//		this.studyInvestigatorCollection = studyInvestigatorCollection;
//	}

//	public StudySite getStudySiteCollection() {
//		return studySiteCollection;
//	}
//
//	public void setStudySiteCollection(StudySite studySiteCollection) {
//		this.studySiteCollection = studySiteCollection;
//	}

	public int getTargetAccrualNumber() {
		return targetAccrualNumber;
	}

	public void setTargetAccrualNumber(int targetAccrualNumber) {
		this.targetAccrualNumber = targetAccrualNumber;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public void finalize() throws Throwable {

	}

}