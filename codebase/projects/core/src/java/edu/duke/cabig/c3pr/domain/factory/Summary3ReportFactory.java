package edu.duke.cabig.c3pr.domain.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.dao.ICD9DiseaseSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.Summary3ReportDao;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Summary3Report;
import edu.duke.cabig.c3pr.tools.Configuration;

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

	public ICD9DiseaseSiteDao getICD9DiseaseSiteDao() {
		return icd9DiseaseSiteDao;
	}

	public void setICD9DiseaseSiteDao(ICD9DiseaseSiteDao icd9DiseaseSiteDao) {
		this.icd9DiseaseSiteDao = icd9DiseaseSiteDao;
	}

	private ICD9DiseaseSiteDao icd9DiseaseSiteDao;

	public Summary3ReportDao getSummary3ReportDao() {
		return summary3ReportDao;
	}

	public void setSummary3ReportDao(Summary3ReportDao summary3ReportDao) {
		this.summary3ReportDao = summary3ReportDao;
	}
	
	public void buildSummary3Report(Summary3Report summary3Report){
		
		if(summary3Report.getReportingSource()== null){
			String reportingSourceNCICode =	this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);;
			HealthcareSite reportingOrganization = healthcareSiteDao.getByPrimaryIdentifier(reportingSourceNCICode);
			summary3Report.setReportingSource(reportingOrganization.getName());
		}
		Date reportStartDate = summary3Report.getStartDate();
		Date reportEndDate = summary3Report.getEndDate();
		HealthcareSite hcs = summary3Report.getReportingOrganization();
		List<ICD9DiseaseSite> diseaseSites = new ArrayList<ICD9DiseaseSite>();
		diseaseSites = icd9DiseaseSiteDao.getAllOrderedByName();
		
		Integer therapeuticICD9DiseaseSiteRegistrationsCount =0;
		Integer icdDiseaseSiteRegistrationsCount =0;
		
		for(ICD9DiseaseSite icdDiseaseSite:diseaseSites){
			Map registrationsForDiseaseSite = new HashMap<String,Object>();
			Integer newlyEnrolledTherapeuticPatientsForGivenDiseaseSite = summary3ReportDao.getNewlyEnrolledTherapeuticStudySubjectCountForGivenICD9DiseaseSite(icdDiseaseSite, hcs, reportStartDate, reportEndDate);
			therapeuticICD9DiseaseSiteRegistrationsCount = therapeuticICD9DiseaseSiteRegistrationsCount+newlyEnrolledTherapeuticPatientsForGivenDiseaseSite;
			Integer newlyRegisteredPatientsForGivenICD9DiseaseSite = summary3ReportDao.getNewlyRegisteredSubjectCountForGivenICD9DiseaseSite(icdDiseaseSite, hcs, reportStartDate, reportEndDate);
			icdDiseaseSiteRegistrationsCount = icdDiseaseSiteRegistrationsCount + newlyRegisteredPatientsForGivenICD9DiseaseSite;
			registrationsForDiseaseSite.put("newlyEnrolledTherapeuticPatients", newlyEnrolledTherapeuticPatientsForGivenDiseaseSite);
			registrationsForDiseaseSite.put("newlyRegisteredPatients", " - ");
			summary3Report.getReportData().put(icdDiseaseSite,registrationsForDiseaseSite);
		}
		
		// creating a dummy anatomic site which has total as name for reporting purpose.
		//TODO find a better way to implement this		
		
		Map totalRegistrationCounts = new HashMap<String,Object>();
	
		ICD9DiseaseSite totalICD9DiseaseSite = new ICD9DiseaseSite();
		Integer newlyEnrolledTotalTherapeuticPatients = summary3ReportDao.getNewlyEnrolledTherapeuticStudySubjectCount( hcs, reportStartDate, reportEndDate);
		Integer newlyRegisteredTotalPatients = summary3ReportDao.getNewlyRegisteredSubjectCount(hcs, reportStartDate, reportEndDate);
		totalRegistrationCounts.put("newlyEnrolledTherapeuticPatients", newlyEnrolledTotalTherapeuticPatients);
		totalRegistrationCounts.put("newlyRegisteredPatients", " - ");
		
		// creating a dummy anatomic site which has UnknwonSites Sites as name for reporting purpose.
		//TODO find a better way to implement this		
	
		Map unknownICD9DiseaseSiteSiteRegistrationCounts = new HashMap<String,Object>();
		
		ICD9DiseaseSite unKnownICD9DiseaseSite = new ICD9DiseaseSite();
		Integer unKnownDiseaseSiteNewTherapeuticRegistrations = newlyEnrolledTotalTherapeuticPatients - therapeuticICD9DiseaseSiteRegistrationsCount;
		Integer unKnownDiseaseSiteNewRegistrations = newlyRegisteredTotalPatients - icdDiseaseSiteRegistrationsCount;
		unknownICD9DiseaseSiteSiteRegistrationCounts.put("newlyEnrolledTherapeuticPatients", unKnownDiseaseSiteNewTherapeuticRegistrations < 0 ? 0 :unKnownDiseaseSiteNewTherapeuticRegistrations);
		unknownICD9DiseaseSiteSiteRegistrationCounts.put("newlyRegisteredPatients", " - ");
		
		// creating a dummy anatomic site which has UnknwonSites Sites as name for reporting purpose.
		//TODO find a better way to implement this		
	
		unKnownICD9DiseaseSite.setName("Unknown Sites");
		summary3Report.getReportData().put(unKnownICD9DiseaseSite, unknownICD9DiseaseSiteSiteRegistrationCounts);
		
		// creating a dummy anatomic site which has total as name for reporting purpose.
		//TODO find a better way to implement this		
		
		totalICD9DiseaseSite.setName("TOTAL:");
		summary3Report.getReportData().put(totalICD9DiseaseSite, totalRegistrationCounts);
	
	}

}
