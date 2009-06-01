package edu.duke.cabig.c3pr.domain;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Transient;

import edu.duke.cabig.c3pr.utils.DateUtil;


public class Summary3Report extends AbstractMutableDeletableDomainObject{
	
	private HealthcareSite reportingOrganization;
	private Date startDate;
	private Date endDate;
	private String reportingSource;
	private Map<AnatomicSite,Map<String,Object>> reportData = new LinkedHashMap<AnatomicSite,Map<String,Object>>();

	public Summary3Report() {
		super();
	}

	public Map<AnatomicSite, Map<String, Object>> getReportData() {
		return reportData;
	}

	public void setReportData(Map<AnatomicSite, Map<String, Object>> reportData) {
		this.reportData = reportData;
	}

	public Summary3Report(HealthcareSite reportingOrganization, Date startDate, Date endDate){
		this.reportingOrganization = reportingOrganization;
		this.startDate =startDate;
		this.endDate = endDate;
	}
	
	public HealthcareSite getReportingOrganization() {
		return reportingOrganization;
	}
	public void setReportingOrganization(HealthcareSite reportingOrganization) {
		this.reportingOrganization = reportingOrganization;
	}
	public Date getStartDate() {
		return startDate;
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
	public String getReportingSource() {
		return reportingSource;
	}
	public void setReportingSource(String reportingSource) {
		this.reportingSource = reportingSource;
	}
	
	/**
	 * Gets the start date str.
	 * 
	 * @return the start date str
	 */
	@Transient
	public String getStartDateStr() {
		if(startDate!=null){
			try {
				return DateUtil.formatDate(startDate, "MM/dd/yyyy");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	
	/**
	 * Gets the end date str.
	 * 
	 * @return the end date str
	 */
	@Transient
	public String getEndDateStr() {
		if(endDate!=null){
			try {
				return DateUtil.formatDate(endDate, "MM/dd/yyyy");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

}
