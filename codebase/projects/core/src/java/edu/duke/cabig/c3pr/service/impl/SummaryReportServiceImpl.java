/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.service.impl;

import java.util.Date;

import edu.duke.cabig.c3pr.dao.Summary3ReportDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Summary3Report;
import edu.duke.cabig.c3pr.domain.factory.Summary3ReportFactory;
import edu.duke.cabig.c3pr.service.SummaryReportService;
import edu.duke.cabig.c3pr.xml.Summary3ReportXmlMarshaller;
import edu.duke.cabig.c3pr.xml.XMLParser;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.common.exception.XMLUtilityException;

public class SummaryReportServiceImpl implements SummaryReportService{
	
	private Summary3ReportDao summary3ReportDao;
	
	private Summary3ReportFactory summary3ReportFactory;
	
	public Summary3ReportFactory getSummary3ReportFactory() {
		return summary3ReportFactory;
	}

	public void setSummary3ReportFactory(Summary3ReportFactory summary3ReportFactory) {
		this.summary3ReportFactory = summary3ReportFactory;
	}

	private XMLParser summaryReportXmlParser;
	
	private Summary3ReportXmlMarshaller marshaller;


	public void setSummaryReportXmlParser(XMLParser summaryReportXmlParser) {
		this.summaryReportXmlParser = summaryReportXmlParser;
	}

	public void setSummary3ReportDao(Summary3ReportDao summary3ReportDao) {
		this.summary3ReportDao = summary3ReportDao;
	}

	public void buildSummary3Report(Summary3Report summary3Report){
		summary3ReportFactory.buildSummary3Report(summary3Report);
	}

	public String generateXML(Summary3Report summary3Report) {
		
		String reportXML = null;
		
		marshaller = new Summary3ReportXmlMarshaller("summary-3-report-castor-mapping.xml");
		try {
			reportXML = marshaller.toXML(summary3Report);
		} catch (XMLUtilityException e) {
			e.printStackTrace();
		}
		
		return reportXML;
	}

	public Summary3ReportXmlMarshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(Summary3ReportXmlMarshaller marshaller) {
		this.marshaller = marshaller;
	}

	public void buildSummary3Report(HealthcareSite healthcareSite,
			String grantNumber, Date startDate, Date endDate) {
			Summary3Report summary3Report = new Summary3Report(healthcareSite,grantNumber,startDate,endDate);
			this.buildSummary3Report(summary3Report);
	}

	public String generateXML(HealthcareSite healthcareSite,
			String grantNumber, Date startDate, Date endDate) {
			Summary3Report summary3Report = new Summary3Report(healthcareSite,grantNumber,startDate,endDate);
		return this.generateXML(summary3Report);
	}

}
