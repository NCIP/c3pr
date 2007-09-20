package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.DateUtil;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

@Entity
@Table(name = "study_amendments")
@GenericGenerator(name = "id-generator",
		strategy = "native",
		parameters = { @Parameter(name = "sequence", value = "STUDY_AMENDMENTS_ID_SEQ") }
)

public class StudyAmendment extends AbstractMutableDomainObject{

	private Integer amendmentVersion;
	private Date amendmentDate;
	private String comments;
	private Date irbApprovalDate;
	private Date amendmentDateStr;
	private Date irbApprovalDateStr;
		
	private Boolean epochAndArmsChangedIndicator; 
	private Boolean stratificationChangedIndicator;
	private Boolean consentChangedIndicator;
	private Boolean eligibilityChangedIndicator;
	private Boolean diseasesChangedIndicator;
	private Boolean principalInvestigatorChangedIndicator;
	

	/// Mutators
	public Integer getAmendmentVersion() {
		return amendmentVersion;
	}
	public void setAmendmentVersion(Integer amendmentVersion) {
		this.amendmentVersion = amendmentVersion;
	}
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
	public Boolean getEpochAndArmsChangedIndicator() {
		return epochAndArmsChangedIndicator;
	}
	public void setEpochAndArmsChangedIndicator(Boolean epochAndArmsChangedIndicator) {
		this.epochAndArmsChangedIndicator = epochAndArmsChangedIndicator;
	}
	public Boolean getStratificationChangedIndicator() {
		return stratificationChangedIndicator;
	}
	public void setStratificationChangedIndicator(
			Boolean stratificationChangedIndicator) {
		this.stratificationChangedIndicator = stratificationChangedIndicator;
	}
	public Boolean getPrincipalInvestigatorChangedIndicator() {
		return principalInvestigatorChangedIndicator;
	}
	public void setPrincipalInvestigatorChangedIndicator(
			Boolean principalInvestigatorChangedIndicator) {
		this.principalInvestigatorChangedIndicator = principalInvestigatorChangedIndicator;
	}


}
