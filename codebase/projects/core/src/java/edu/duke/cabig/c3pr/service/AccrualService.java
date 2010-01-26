package edu.duke.cabig.c3pr.service;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.domain.accrual.DiseaseSiteAccrualReport;
import edu.duke.cabig.c3pr.domain.accrual.SiteAccrualReport;
import edu.duke.cabig.c3pr.domain.accrual.StudyAccrualReport;

// TODO: Auto-generated Javadoc
/**
 * The Interface AccrualService.
 */
public interface AccrualService {

	/**
	 * Sets the local nci identifier.
	 * 
	 * @param localNCIIdentifier
	 *            the new local nci identifier
	 */
	public void setLocalNCIIdentifier(String localNCIIdentifier);

	/**
	 * Returns the CTEP of the site.
	 * 
	 * @return the site ctep id
	 */
	public String getSiteCtepId();

	/**
	 * Just return study identifier and short title.
	 * 
	 * @return the study accrual reports
	 */
	public List<StudyAccrualReport> getStudyAccrualReports();

	/**
	 * Just returns a list of disease sites.
	 * 
	 * @return the disease site accrual reports
	 */
	public List<DiseaseSiteAccrualReport> getDiseaseSiteAccrualReports();

	/**
	 * Returns SiteAccrualReport in an object graph.
	 * 
	 * @param diseaseSiteAccrualReport
	 *            can be null
	 * @param studyAccrualReport
	 *            can be null
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * 
	 * @return the accrual
	 */

	public SiteAccrualReport getSiteAccrualReport(
			String diseaseSiteName,
			String studyShortTitleText, Date startDate, Date endDate);

}
