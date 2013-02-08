/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.utils.CommonUtils;
import edu.duke.cabig.c3pr.utils.DateUtil;

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
	private StudySite studySite;
	
	public Date getStartDate() {
		if(startDate != null){
			return DateUtil.getUtilDateFromString(DateUtil.formatDate(startDate, "MM/dd/yyyy"), "MM/dd/yyyy");
		}else{
			return startDate ;
		}
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
		if(endDate != null){
			return DateUtil.getUtilDateFromString(DateUtil.formatDate(endDate, "MM/dd/yyyy"), "MM/dd/yyyy");
		}else{
			return endDate ;
		}
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

	public void setStudySite(StudySite studySite) {
		this.studySite = studySite;
	}
	
	@ManyToOne
    @JoinColumn(name = "sto_id", nullable = false)
    @Cascade( { CascadeType.LOCK })
	public StudySite getStudySite() {
		return studySite;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final SiteStatusHistory other = (SiteStatusHistory) obj;
        
        if (siteStudyStatus == null) {
            if (other.siteStudyStatus != null) return false;
        }
        else if (!siteStudyStatus.equals(other.siteStudyStatus)) return false;
        
        if (startDate == null) {
            if (other.startDate != null) return false;
        }
        else if (!startDate.equals(other.startDate)) return false;
        
        if (endDate == null) {
            if (other.endDate != null) return false;
        }
        else if (!endDate.equals(other.endDate)) return false;
        
        if (studySite == null) {
            if (other.studySite != null) return false;
        }
        else if (!studySite.equals(other.studySite)) return false;
        
        return true;
	}

	
}
