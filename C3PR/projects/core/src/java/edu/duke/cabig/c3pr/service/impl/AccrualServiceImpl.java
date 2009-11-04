package edu.duke.cabig.c3pr.service.impl;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.accrual.AccrualDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.accrual.Accrual;
import edu.duke.cabig.c3pr.domain.accrual.DiseaseSiteAccrualReport;
import edu.duke.cabig.c3pr.domain.accrual.SiteAccrualReport;
import edu.duke.cabig.c3pr.domain.accrual.StudyAccrualReport;
import edu.duke.cabig.c3pr.service.AccrualService;
import edu.duke.cabig.c3pr.tools.Configuration;

// TODO: Auto-generated Javadoc
/**
 * The Class AccrualServiceImpl.
 */
public class AccrualServiceImpl implements AccrualService{
	
	/** The accrual dao. */
	private AccrualDao accrualDao;
	
	/** The configuration. */
	private Configuration configuration;
	
	/** The healthcare site dao. */
	private HealthcareSiteDao healthcareSiteDao;
	
	/** The local nci identifier. */
	private String localNCIIdentifier;

	/**
	 * Gets the local nci identifier.
	 * 
	 * @return the local nci identifier
	 */
	public String getLocalNCIIdentifier() {
		return localNCIIdentifier;
	}

	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.service.AccrualService#setLocalNCIIdentifier(java.lang.String)
	 */
	public void setLocalNCIIdentifier(String localNCIIdentifier) {
		this.localNCIIdentifier = localNCIIdentifier;
	}

	/**
	 * Sets the healthcare site dao.
	 * 
	 * @param healthcareSiteDao the new healthcare site dao
	 */
	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	/**
	 * Sets the configuration.
	 * 
	 * @param configuration the new configuration
	 */
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Sets the accrual dao.
	 * 
	 * @param accrualDao the new accrual dao
	 */
	public void setAccrualDao(AccrualDao accrualDao) {
		this.accrualDao = accrualDao;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.service.AccrualService#getAccrual(edu.duke.cabig.c3pr.domain.accrual.DiseaseSiteAccrualReport, edu.duke.cabig.c3pr.domain.accrual.StudyAccrualReport, java.util.Date, java.util.Date)
	 */
	public SiteAccrualReport getSiteAccrualReport(
			String diseaseSiteName,
			String studyShortTitleText, Date startDate, Date endDate) {
		SiteAccrualReport siteAccrualReport = buildSiteAccrualReport(getSiteCtepId(),studyShortTitleText);
		Accrual accrual = new Accrual();
		accrual.setValue(accrualDao.getSiteAccrual(siteAccrualReport, diseaseSiteName, studyShortTitleText, startDate, endDate));
		siteAccrualReport.setAccrual(accrual);
		return siteAccrualReport;
			
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.service.AccrualService#getDiseaseSiteAccrualReports()
	 */
	public List<DiseaseSiteAccrualReport> getDiseaseSiteAccrualReports() {
		String nciInstituteCode = getSiteCtepId();
		
		return accrualDao.getAllDiseaseSiteAccrualReports(nciInstituteCode);
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.service.AccrualService#getSiteCtepId()
	 */
	public String getSiteCtepId() {
		if(this.localNCIIdentifier != null){
			return this.localNCIIdentifier;
		}
		return this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.service.AccrualService#getStudyAccrualReports()
	 */
	public List<StudyAccrualReport> getStudyAccrualReports() {
		return accrualDao.getStudyAccrualReports(getSiteCtepId(),null);
	}
	
	/**
	 * Builds the site accrual report.
	 * 
	 * @param nciInstituteCode the nci institute code
	 * @param shortTitleText the short title text
	 * 
	 * @return the site accrual report
	 */
	public SiteAccrualReport buildSiteAccrualReport(String nciInstituteCode, String shortTitleText){
		
		HealthcareSite localHealthcareSite = healthcareSiteDao.getByPrimaryIdentifier(nciInstituteCode);
		SiteAccrualReport siteAccrualReport =new SiteAccrualReport();
		siteAccrualReport.setName(localHealthcareSite.getName());
		siteAccrualReport.setCtepId(nciInstituteCode);
		siteAccrualReport.setStreetAddress(localHealthcareSite.getAddress().getStreetAddress());
		siteAccrualReport.setCity(localHealthcareSite.getAddress().getCity());
		siteAccrualReport.setStateCode(localHealthcareSite.getAddress().getStateCode());
		siteAccrualReport.setPostalCode(localHealthcareSite.getAddress().getPostalCode());
		siteAccrualReport.setCountryCode(localHealthcareSite.getAddress().getCountryCode());
		siteAccrualReport.setStudyAccrualReports(accrualDao.getStudyAccrualReports(nciInstituteCode,shortTitleText));
		
		return siteAccrualReport;
		
	}
}
