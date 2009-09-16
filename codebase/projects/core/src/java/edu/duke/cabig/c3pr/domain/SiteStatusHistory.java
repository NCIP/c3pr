package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.utils.CommonUtils;

/**
 * @author Himanshu
 */

@Entity
@Table(name = "STU_SITE_STATUS_HISTORY")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STU_SITE_STATUS_HISTORY_ID_SEQ") })
public class SiteStatusHistory extends AbstractMutableDeletableDomainObject implements Comparable<SiteStatusHistory>{
	
	private Date startDate;
	private Date endDate;
	private SiteStudyStatus siteStudyStatus ;
	
	public Date getStartDate() {
		return startDate;
	}
	
	@Transient
	public String getStartDateStr() {
		return CommonUtils.getDateString(startDate);
	}
	
	@Transient
	public String getEndDateStr() {
		return CommonUtils.getDateString(endDate);
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Enumerated(EnumType.STRING)
	public SiteStudyStatus getSiteStudyStatus() {
		return siteStudyStatus;
	}
	public void setSiteStudyStatus(SiteStudyStatus siteStudyStatus) {
		this.siteStudyStatus = siteStudyStatus;
	}
	
	public int compareTo(SiteStatusHistory siteStatusHistory) {
    	if(this.startDate == null && siteStatusHistory.getStartDate() == null){
    		return 0;
    	}else if(this.startDate == null && siteStatusHistory.getStartDate() != null){
    		return 1;
    	}else if(this.startDate != null && siteStatusHistory.getStartDate() == null){
    		return -1;
    	}else{
    		return this.startDate.compareTo(siteStatusHistory.getStartDate());
    	}
   	}
	
	public SiteStatusHistory(){
	}

	
}
