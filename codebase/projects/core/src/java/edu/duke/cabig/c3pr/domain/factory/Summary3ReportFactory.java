package edu.duke.cabig.c3pr.domain.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.dao.AnatomicSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.Summary3ReportDao;
import edu.duke.cabig.c3pr.domain.AnatomicSite;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Summary3Report;
import edu.duke.cabig.c3pr.tools.Configuration;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperty;

public class Summary3ReportFactory {
	
	private Summary3ReportDao summary3ReportDao;
	
	private HealthcareSiteDao healthcareSiteDao;
	
	private Configuration configuration;
	
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public AnatomicSiteDao getAnatomicSiteDao() {
		return anatomicSiteDao;
	}

	public void setAnatomicSiteDao(AnatomicSiteDao anatomicSiteDao) {
		this.anatomicSiteDao = anatomicSiteDao;
	}

	private AnatomicSiteDao anatomicSiteDao;

	public Summary3ReportDao getSummary3ReportDao() {
		return summary3ReportDao;
	}

	public void setSummary3ReportDao(Summary3ReportDao summary3ReportDao) {
		this.summary3ReportDao = summary3ReportDao;
	}
	
	public void buildSummary3Report(Summary3Report summary3Report){
		
		if(summary3Report.getReportingSource()== null){
			String reportingSourceNCICode =	this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);;
			HealthcareSite reportingOrganization = healthcareSiteDao.getByNciInstituteCode(reportingSourceNCICode);
			summary3Report.setReportingSource(reportingOrganization.getName());
		}
		Date reportStartDate = summary3Report.getStartDate();
		Date reportEndDate = summary3Report.getEndDate();
		HealthcareSite hcs = summary3Report.getReportingOrganization();
		List<AnatomicSite> diseaseSites = new ArrayList<AnatomicSite>();
		diseaseSites = anatomicSiteDao.getAllOrderedByName();
		
		Integer therapeuticAnatomicSiteRegistrationsCount =0;
		Integer anatomicSiteRegistrationsCount =0;
		
		for(AnatomicSite anatomicSite:diseaseSites){
			Map registrationsForDiseaseSite = new HashMap<String,Object>();
			Integer newlyEnrolledTherapeuticPatientsForGivenDiseaseSite = summary3ReportDao.getNewlyEnrolledTherapeuticStudyPatientsForGivenAnatomicSite(anatomicSite, hcs, reportStartDate, reportEndDate);
			therapeuticAnatomicSiteRegistrationsCount = therapeuticAnatomicSiteRegistrationsCount+newlyEnrolledTherapeuticPatientsForGivenDiseaseSite;
			Integer newlyRegisteredPatientsForGivenAnatomicSite = summary3ReportDao.getNewlyRegisteredPatientsForGivenAnatomicSite(anatomicSite, hcs, reportStartDate, reportEndDate);
			anatomicSiteRegistrationsCount = anatomicSiteRegistrationsCount + newlyRegisteredPatientsForGivenAnatomicSite;
			registrationsForDiseaseSite.put("newlyEnrolledTherapeuticPatients", newlyEnrolledTherapeuticPatientsForGivenDiseaseSite);
			registrationsForDiseaseSite.put("newlyRegisteredPatients", " - ");
			summary3Report.getReportData().put(anatomicSite,registrationsForDiseaseSite);
		}
		
		// creating a dummy anatomic site which has total as name for reporting purpose.
		//TODO find a better way to implement this		
		
		Map totalRegistrationCounts = new HashMap<String,Object>();
	
		AnatomicSite totalAnatomicSite = new AnatomicSite();
		Integer newlyEnrolledTotalTherapeuticPatients = summary3ReportDao.getNewlyEnrolledTherapeuticStudyPatients( hcs, reportStartDate, reportEndDate);
		Integer newlyRegisteredTotalPatients = summary3ReportDao.getNewlyRegisteredPatients(hcs, reportStartDate, reportEndDate);
		totalRegistrationCounts.put("newlyEnrolledTherapeuticPatients", newlyEnrolledTotalTherapeuticPatients);
		totalRegistrationCounts.put("newlyRegisteredPatients", " - ");
		
		// creating a dummy anatomic site which has UnknwonSites Sites as name for reporting purpose.
		//TODO find a better way to implement this		
	
		Map unknownAnatomicSiteSiteRegistrationCounts = new HashMap<String,Object>();
		
		AnatomicSite unKnownAnatomicSite = new AnatomicSite();
		Integer unKnownDiseaseSiteNewTherapeuticRegistrations = newlyEnrolledTotalTherapeuticPatients - therapeuticAnatomicSiteRegistrationsCount;
		Integer unKnownDiseaseSiteNewRegistrations = newlyRegisteredTotalPatients - anatomicSiteRegistrationsCount;
		unknownAnatomicSiteSiteRegistrationCounts.put("newlyEnrolledTherapeuticPatients", unKnownDiseaseSiteNewTherapeuticRegistrations < 0 ? 0 :unKnownDiseaseSiteNewTherapeuticRegistrations);
		unknownAnatomicSiteSiteRegistrationCounts.put("newlyRegisteredPatients", " - ");
	
		unKnownAnatomicSite.setName("Unknown Sites");
		summary3Report.getReportData().put(unKnownAnatomicSite, unknownAnatomicSiteSiteRegistrationCounts);
		
		totalAnatomicSite.setName("TOTAL:");
		summary3Report.getReportData().put(totalAnatomicSite, totalRegistrationCounts);
	
	}

}
