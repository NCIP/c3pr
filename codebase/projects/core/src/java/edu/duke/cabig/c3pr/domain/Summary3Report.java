package edu.duke.cabig.c3pr.domain;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Transient;

import edu.duke.cabig.c3pr.utils.DateUtil;


/**
 * The Class Summary3Report.
 */
public class Summary3Report extends AbstractMutableDeletableDomainObject{

	/** The reporting organization. */
	private HealthcareSite reportingOrganization;

	/** The start date. */
	private Date startDate;

	/** The end date. */
	private Date endDate;

	/** The reporting source. */
	private String reportingSource;

	/** The report data. */
	private Map<Summary3ReportDiseaseSite,LinkedHashMap<String,Object>> reportData = new LinkedHashMap<Summary3ReportDiseaseSite,LinkedHashMap<String,Object>>();


	private String grantNumber;


	public String getGrantNumber() {
		return grantNumber;
	}

	public void setGrantNumber(String grantNumber) {
		this.grantNumber = grantNumber;
	}

	/**
	 * Instantiates a new summary3 report.
	 */
	public Summary3Report() {
		super();
	}

	/**
	 * Gets the report data.
	 *
	 * @return the report data
	 */
	public Map<Summary3ReportDiseaseSite, LinkedHashMap<String, Object>> getReportData() {
		return reportData;
	}

	/**
	 * Sets the report data.
	 *
	 * @param reportData the report data
	 */
	public void setReportData(Map<Summary3ReportDiseaseSite, LinkedHashMap<String, Object>> reportData) {
		this.reportData = reportData;
	}

	/**
	 * Instantiates a new summary3 report.
	 *
	 * @param reportingOrganization the reporting organization
	 * @param startDate the start date
	 * @param endDate the end date
	 */
	public Summary3Report(HealthcareSite reportingOrganization, String grantNumber, Date startDate, Date endDate){
		this.reportingOrganization = reportingOrganization;
		this.grantNumber = grantNumber;
		this.startDate =startDate;
		this.endDate = endDate;
	}

	/**
	 * Gets the reporting organization.
	 *
	 * @return the reporting organization
	 */
	public HealthcareSite getReportingOrganization() {
		return reportingOrganization;
	}

	/**
	 * Sets the reporting organization.
	 *
	 * @param reportingOrganization the new reporting organization
	 */
	public void setReportingOrganization(HealthcareSite reportingOrganization) {
		this.reportingOrganization = reportingOrganization;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date.
	 *
	 * @param endDate the new end date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the reporting source.
	 *
	 * @return the reporting source
	 */
	public String getReportingSource() {
		return reportingSource;
	}

	/**
	 * Sets the reporting source.
	 *
	 * @param reportingSource the new reporting source
	 */
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
			return DateUtil.formatDate(startDate, "MM/dd/yyyy");
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
			return DateUtil.formatDate(endDate, "MM/dd/yyyy");
		}
		return "";
	}

}
