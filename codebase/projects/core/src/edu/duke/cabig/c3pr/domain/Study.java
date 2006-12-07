package edu.duke.cabig.c3pr.domain;

/**
 * A systematic evaluation of an observation or an
 * intervention (for example, treatment, drug, device, procedure or system) in one
 * or more subjects. Frequently this is a test of a particular hypothesis about
 * the treatment, drug, device, procedure or system. [CDAM]  A study can be either
 * primary or correlative. A study is considered a primary study if it has one or
 * more correlative studies. A correlative study extends the objectives or
 * observations of a primary study, enrolling the same, or a subset of the same,
 * subjects as the primary study. A Clinical Trial is a Study with type=
 * "intervention" with subjects of type="human". [BRIDG] [End Documentation]
 * @version 1.0
 * @created 06-Dec-2006 12:19:55 AM
 */
public class Study {
	private int id;
	private String blindedIndicator;
	private java.lang.String descriptionText;
	private java.lang.String diseaseCode;	
	private java.lang.String longTitleText;	
	private java.lang.String monitorCode;
	private String multiInstitutionIndicator;
	private java.lang.String nciIdentifier;
	private java.lang.String phaseCode;
	private java.lang.String precisText;
	private String randomizedIndicator;
	private java.lang.String shortTitleText;
	private java.lang.String sponsorCode;
	private java.lang.String status;
	private int targetAccrualNumber;
	private java.lang.String type;
//	public EligibilityCriteria eligibilityCriteriaCollection;
//	public StudyInvestigator studyInvestigatorCollection;
//	public Amendment amendmentCollection;
//	public StudySite studySiteCollection;
//	public Study study;

	public Study(){

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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