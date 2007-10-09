package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.DateUtil;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

@Entity
@Table(name = "study_amendments")
@GenericGenerator(name = "id-generator",
		strategy = "native",
		parameters = { @Parameter(name = "sequence", value = "STUDY_AMENDMENTS_ID_SEQ") }
)

public class StudyAmendment extends AbstractMutableDeletableDomainObject{

	private String amendmentVersion;
	private Date amendmentDate;
	private String comments;
	private Date irbApprovalDate;
	private Date amendmentDateStr;
	private Date irbApprovalDateStr;
		
	private Boolean eaChangedIndicator = false; 
	private Boolean stratChangedIndicator = false;
	private Boolean consentChangedIndicator = false;
	private Boolean eligibilityChangedIndicator = false;
	private Boolean diseasesChangedIndicator = false;
	private Boolean piChangedIndicator =false;
	private Boolean randomizationChangedIndicator =false;
	

	/// Mutators
	public Date getAmendmentDate() {
		return amendmentDate;
	}
	public void setAmendmentDate(Date amendmentDate) {
		this.amendmentDate = amendmentDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getIrbApprovalDate() {
		return irbApprovalDate;
	}
	public void setIrbApprovalDate(Date irbApprovalDate) {
		this.irbApprovalDate = irbApprovalDate;
	}
	
	 @Transient
	    public String getAmendmentDateStr() {
	        try {
	            return DateUtil.formatDate(amendmentDate, "MM/dd/yyyy");
	        }
	        catch(Exception e){
	            //do nothing
	        }
	        return "";
	    }
	 
	 @Transient
	    public String getIrbApprovalDateStr() {
	        try {
	            return DateUtil.formatDate(irbApprovalDate, "MM/dd/yyyy");
	        }
	        catch(Exception e){
	            //do nothing
	        }
	        return "";
	    }
/*	 @Transient
	    public StudyDataEntryStatus getAmendmentDataEntryStatus() {
		 if((this.getIrbApprovalDate()==null)||(this.getVersion()==null) ){
			 return StudyDataEntryStatus.INCOMPLETE;
		 }
	        if ((this.getConsentChangedIndicator()==true)||(this.getDiseasesChangedIndicator()==true)||(this.getEligibilityChangedIndicator()==true)||(this.getEpochAndArmsChangedIndicator()==true)
	        		||(this.getStratificationChangedIndicator()==true)||(this.getPrincipalInvestigatorChangedIndicator()==true))
	        return StudyDataEntryStatus.COMPLETE;
	        
	        return StudyDataEntryStatus.COMPLETE;
	    }*/

	public Boolean getConsentChangedIndicator() {
		return consentChangedIndicator;
	}
	public void setConsentChangedIndicator(Boolean consentChangedIndicator) {
		this.consentChangedIndicator = consentChangedIndicator;
	}
	public Boolean getDiseasesChangedIndicator() {
		return diseasesChangedIndicator;
	}
	public void setDiseasesChangedIndicator(Boolean diseasesChangedIndicator) {
		this.diseasesChangedIndicator = diseasesChangedIndicator;
	}
	public Boolean getEligibilityChangedIndicator() {
		return eligibilityChangedIndicator;
	}
	public void setEligibilityChangedIndicator(Boolean eligibilityChangedIndicator) {
		this.eligibilityChangedIndicator = eligibilityChangedIndicator;
	}
	public Boolean getRandomizationChangedIndicator() {
		return randomizationChangedIndicator;
	}
	public void setRandomizationChangedIndicator(
			Boolean randomizationChangedIndicator) {
		this.randomizationChangedIndicator = randomizationChangedIndicator;
	}
	public void setAmendmentVersion(String amendmentVersion) {
		this.amendmentVersion = amendmentVersion;
	}
	public String getAmendmentVersion() {
		return amendmentVersion;
	}
	public Boolean getEaChangedIndicator() {
		return eaChangedIndicator;
	}
	public void setEaChangedIndicator(Boolean eaChangedIndicator) {
		this.eaChangedIndicator = eaChangedIndicator;
	}
	public Boolean getPiChangedIndicator() {
		return piChangedIndicator;
	}
	public void setPiChangedIndicator(Boolean piChangedIndicator) {
		this.piChangedIndicator = piChangedIndicator;
	}
	public Boolean getStratChangedIndicator() {
		return stratChangedIndicator;
	}
	public void setStratChangedIndicator(Boolean stratChangedIndicator) {
		this.stratChangedIndicator = stratChangedIndicator;
	}


}