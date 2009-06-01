package edu.duke.cabig.c3pr.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Summary3Report;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.XsltTransformer;
import edu.duke.cabig.c3pr.xml.Summary3ReportGenerator;

public class Summary3ReportServiceTest extends DaoTestCase {

	private SummaryReportService summaryReportService;

	private HealthcareSiteDao healthcareSiteDao;

	private String xslFOXsltFile = "summary3Report.xslt";

	public void setSummary3ReportGenerator(
			Summary3ReportGenerator summary3ReportGenerator) {
		this.summary3ReportGenerator = summary3ReportGenerator;
	}

	private Summary3ReportGenerator summary3ReportGenerator;

	public void setSummaryReportSerivice(
			SummaryReportService summaryReportService) {
		this.summaryReportService = summaryReportService;
	}

	@Override
	protected void setUp() throws Exception {

		summaryReportService = (SummaryReportService) getApplicationContext()
				.getBean("summaryReportService");

		healthcareSiteDao = (HealthcareSiteDao) getApplicationContext()
				.getBean("healthcareSiteDao");

		super.setUp();
	}

	public void testGenerateXML() throws Exception {
		HealthcareSite hcs = healthcareSiteDao.getById(1100);

		 DateFormat format = 
			 new SimpleDateFormat("MM/dd/yyyy");
			       Date startDate = 
			 (Date)format.parse("01/11/1990");
			       Date endDate = 
			 (Date)format.parse("01/11/2010");

		Summary3Report summary3Report = new Summary3Report(hcs, startDate,
				endDate);
		
		summaryReportService.buildSummary3Report(summary3Report);

		String xmlString = summaryReportService.generateXML(summary3Report);
		 System.out.println(xmlString);

		/*String newXMLString = "";
		FileReader input = new FileReader(
				"C:/users/iris/Reports/testReportXML.xml");
		BufferedReader bufRead = new BufferedReader(input);
		String line = bufRead.readLine();

		while (line != null) {
			newXMLString = newXMLString + line;
			line = bufRead.readLine();
		}
*/
		// generate report and send ...
		XsltTransformer xsltTrans = new XsltTransformer();

		try {
			xsltTrans.toPdf(xmlString, "C:/users/iris/Reports/testReport",
					xslFOXsltFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
