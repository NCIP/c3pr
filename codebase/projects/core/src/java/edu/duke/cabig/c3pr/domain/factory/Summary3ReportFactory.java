/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.Summary3ReportDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Summary3Report;
import edu.duke.cabig.c3pr.domain.Summary3ReportDiseaseSite;
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

	public Summary3ReportDao getSummary3ReportDao() {
		return summary3ReportDao;
	}

	public void setSummary3ReportDao(Summary3ReportDao summary3ReportDao) {
		this.summary3ReportDao = summary3ReportDao;
	}
	
	@SuppressWarnings("unchecked")
	public void buildSummary3Report(Summary3Report summary3Report){
		
		if(summary3Report.getReportingSource()== null){
			String reportingSourceNCICode =	this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);;
			HealthcareSite reportingOrganization = healthcareSiteDao.getByPrimaryIdentifier(reportingSourceNCICode);
			summary3Report.setReportingSource(reportingOrganization.getName());
		}
		Date reportStartDate = summary3Report.getStartDate();
		Date reportEndDate = summary3Report.getEndDate();
		HealthcareSite hcs = summary3Report.getReportingOrganization();
		List<Summary3ReportDiseaseSite> diseaseSites = new ArrayList<Summary3ReportDiseaseSite>();
		diseaseSites = summary3ReportDao.getAllOrderedByName();
		
		int newlyEnrolledTotalTherapeuticPatients = 0;
		
		for(Summary3ReportDiseaseSite summary3ReportDiseaseSite:diseaseSites){
			Map registrationsForDiseaseSite = new LinkedHashMap<String,Object>();
			int newlyEnrolledTherapeuticPatientsForGivenDiseaseSite = summary3ReportDao.getNewlyEnrolledTherapeuticStudySubjectCountForGivenSummary3ReportDiseaseSite(summary3ReportDiseaseSite, hcs, reportStartDate, reportEndDate);
			// update the newly enrolled therapeutic patients with those for the particular disease
			newlyEnrolledTotalTherapeuticPatients = newlyEnrolledTotalTherapeuticPatients + newlyEnrolledTherapeuticPatientsForGivenDiseaseSite;
			registrationsForDiseaseSite.put("newlyRegisteredPatients", " - ");
			registrationsForDiseaseSite.put("newlyEnrolledTherapeuticPatients", newlyEnrolledTherapeuticPatientsForGivenDiseaseSite);
			
			summary3Report.getReportData().put(summary3ReportDiseaseSite,(LinkedHashMap<String,Object>)registrationsForDiseaseSite);
		}
		
		// creating a dummy anatomic site which has total as name for reporting purpose.
		//TODO find a better way to implement this		
		
		Map totalRegistrationCounts = new LinkedHashMap<String,Object>();
	
		Summary3ReportDiseaseSite totalICD9DiseaseSite = new Summary3ReportDiseaseSite();
		totalRegistrationCounts.put("newlyRegisteredPatients", " - ");
		totalRegistrationCounts.put("newlyEnrolledTherapeuticPatients", newlyEnrolledTotalTherapeuticPatients);
		
		// creating a dummy anatomic site which has total as name for reporting purpose.
		//TODO find a better way to implement this		
		
		totalICD9DiseaseSite.setName("TOTAL:");
		summary3Report.getReportData().put(totalICD9DiseaseSite, (LinkedHashMap<String,Object>)totalRegistrationCounts);
	
	}

}
