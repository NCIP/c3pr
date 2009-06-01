package edu.duke.cabig.c3pr.service;

import java.util.Date;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Summary3Report;

public interface SummaryReportService {
	
	public String generateXML(Summary3Report summary3Report);
	
	public void buildSummary3Report(Summary3Report summary3Report);

}
