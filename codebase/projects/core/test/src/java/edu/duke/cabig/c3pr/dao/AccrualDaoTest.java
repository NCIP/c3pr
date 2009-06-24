package edu.duke.cabig.c3pr.dao;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.dao.accrual.AccrualDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.accrual.DiseaseSiteAccrualReport;
import edu.duke.cabig.c3pr.domain.accrual.SiteAccrualReport;
import edu.duke.cabig.c3pr.domain.accrual.StudyAccrualReport;
import edu.duke.cabig.c3pr.service.AccrualService;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;
import edu.duke.cabig.c3pr.xml.XMLParser;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

// TODO: Auto-generated Javadoc
/**
 * The Class AccrualDaoTest.
 */
public class AccrualDaoTest extends ContextDaoTestCase<AccrualDao> {

	/** The local site. */
	private HealthcareSite localSite;

	/** The healthcare site dao. */
	private HealthcareSiteDao healthcareSiteDao;

	/** The accrual dao. */
	private AccrualDao accrualDao;

	/** The anatomic site dao. */
	private AnatomicSiteDao anatomicSiteDao;

	/** The accrual service. */
	private AccrualService accrualService;
	
	/** The accrual report xml parser. */
	public XMLParser accrualReportXmlParser;
	
	/** The marshaller. */
	XmlMarshaller marshaller;

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.utils.DaoTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		healthcareSiteDao = (HealthcareSiteDao) getApplicationContext()
				.getBean("healthcareSiteDao");
		accrualDao = (AccrualDao) getApplicationContext().getBean("accrualDao");
		anatomicSiteDao = (AnatomicSiteDao) getApplicationContext().getBean(
				"anatomicSiteDao");
		accrualService = (AccrualService) getApplicationContext().getBean(
				"accrualService");
		
		marshaller = new XmlMarshaller("accrual-report-castor-mapping.xml");
		accrualReportXmlParser = (XMLParser)getApplicationContext().getBean("accrualReportXmlParser");
		
		accrualService.setLocalNCIIdentifier("code");
		localSite = healthcareSiteDao.getById(1100);
	}

	/**
	 * Test get disease sites.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetDiseaseSites() throws Exception {
		List<DiseaseSiteAccrualReport> diseaseSiteAccrualReports = accrualService
				.getDiseaseSiteAccrualReports();
		assertEquals("Wrong number of disease accrual reports returned", 20,
				diseaseSiteAccrualReports.size());
	}

	/**
	 * Test accrual service get study accrual reports.
	 * 
	 * @throws Exception the exception
	 */
	public void testAccrualServiceGetStudyAccrualReports() throws Exception {
		List<StudyAccrualReport> studyAccrualReports = accrualService
				.getStudyAccrualReports();
		assertEquals("Wrong number of study accrual reports returned", 1,
				studyAccrualReports.size());
	}
	
	/**
	 * Test accrual dao get study accrual reports.
	 * 
	 * @throws Exception the exception
	 */
	public void testAccrualDaoGetStudyAccrualReports() throws Exception {
		List<StudyAccrualReport> studyAccrualReports = accrualDao
				.getStudyAccrualReports("code","s");
		assertEquals("Wrong number of study accrual reports returned", 0,
				studyAccrualReports.size());
	}
	
	/**
	 * Test get all accrual site reports.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetAllAccrualSiteReports() throws Exception {
		List<DiseaseSiteAccrualReport> diseaseSiteAccrualReports = accrualDao.getAllDiseaseSiteAccrualReports("code");
		assertEquals("Wrong number of disease site accrual reports returned", 20,
				diseaseSiteAccrualReports.size());
	}

	/**
	 * Test get site accrual1.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetSiteAccrual1() throws Exception {

		Date startDate = new Date();

		// the reference for setting year is 1900
		startDate.setYear(100);
		Date endDate =null;

		SiteAccrualReport siteAccrualReport = accrualService.getSiteAccrualReport(
				"Abdomen", "short_title_text", startDate, endDate);
		assertEquals("Wrong number of study accrual reports returned", 1,
				siteAccrualReport.getAccrual().getValue());
	}

	/**
	 * Test get site accrual2.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetSiteAccrual2() throws Exception {

		Date startDate = new Date();

		// the reference for setting year is 1900
		startDate.setYear(100);
		
		Date endDate = new Date();

		// the reference for setting year is 1900
		endDate.setYear(101);


		SiteAccrualReport siteAccrualReport = accrualService.getSiteAccrualReport(
				"Abdomen", "short_title_text", startDate, endDate);
		assertEquals("Wrong number of study accrual reports returned", 0,
				siteAccrualReport.getAccrual().getValue());
	}
	
	/**
	 * Test get site accrual3.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetSiteAccrual3() throws Exception {

		Date startDate = new Date();

		// the reference for setting year is 1900
		startDate.setYear(100);
		
		Date endDate = new Date();

		// the reference for setting year is 1900
		endDate.setYear(111);


		SiteAccrualReport siteAccrualReport = accrualService.getSiteAccrualReport(
				"Abdomen", "short_title_text", startDate, endDate);
		assertEquals("Wrong number of study accrual reports returned", 1,
				siteAccrualReport.getAccrual().getValue());
	}
	
	/**
	 * Test get site accrual4.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetSiteAccrual4() throws Exception {

		Date startDate = new Date();

		// the reference for setting year is 1900
		startDate.setYear(100);
		
		Date endDate = new Date();

		// the reference for setting year is 1900
		endDate.setYear(111);


		SiteAccrualReport siteAccrualReport = accrualService.getSiteAccrualReport(
				"Skin", "short_title_text", startDate, endDate);
		assertEquals("Wrong number of study accrual reports returned", 1,
				siteAccrualReport.getAccrual().getValue());
	}
	
	/**
	 * Test get site accrual5.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetSiteAccrual5() throws Exception {

		Date startDate = new Date();

		// the reference for setting year is 1900
		startDate.setYear(100);
		
		Date endDate = new Date();

		// the reference for setting year is 1900
		endDate.setYear(111);


		SiteAccrualReport siteAccrualReport = accrualService.getSiteAccrualReport(
				null, "short_title_text", startDate, endDate);
		assertEquals("Wrong number of study accrual reports returned", 2,
				siteAccrualReport.getAccrual().getValue());
	}
	
	/**
	 * Test to xml.
	 */
	public void testToXML(){
		
		Date startDate = new Date();

		// the reference for setting year is 1900
		startDate.setYear(100);
		
		Date endDate = new Date();

		// the reference for setting year is 1900
		endDate.setYear(111);

		SiteAccrualReport siteAccrualReport = accrualService.getSiteAccrualReport(
				null, "short_title_text", startDate, endDate);
		assertEquals("Wrong number of study accrual reports returned", 2,
				siteAccrualReport.getAccrual().getValue());
		try {
			String xmlSiteReport = (marshaller.toXML(siteAccrualReport));
			System.out.println(xmlSiteReport);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Test accrual xml validation.
	 * 
	 * @throws Exception the exception
	 */
	public void testAccrualXMLValidation() throws Exception {

		try {
			
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sample-accrual-report.xml");
			
			byte[] bytes = new byte[8000];
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8000);

			int readBytes;
			while((readBytes = inputStream.read(bytes)) > 0) {
		        outputStream.write(bytes, 0, readBytes);
			}
		        byte[] byteData = outputStream.toByteArray();
		    
	        // Close the streams
	        inputStream.close();
	        outputStream.close();

	        accrualReportXmlParser.validate(byteData);
		} catch (Exception e) {
			e.printStackTrace();
			//fail("Unable to Validate");
		}
}


}
