package edu.duke.cabig.c3pr.dao;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.dao.accrual.AccrualDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.accrual.DiseaseSiteAccrualReport;
import edu.duke.cabig.c3pr.domain.accrual.StudyAccrualReport;
import edu.duke.cabig.c3pr.service.AccrualService;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;
import edu.duke.cabig.c3pr.xml.XMLParser;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

public class AccrualDaoTest extends ContextDaoTestCase<AccrualDao> {

	private HealthcareSite localSite;

	private HealthcareSiteDao healthcareSiteDao;

	private AccrualDao accrualDao;

	private AnatomicSiteDao anatomicSiteDao;

	private AccrualService accrualService;
	
	public XMLParser accrualReportXmlParser;
	
	XmlMarshaller marshaller;

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

	public void testGetDiseaseSites() throws Exception {
		List<DiseaseSiteAccrualReport> diseaseSiteAccrualReports = accrualService
				.getDiseaseSiteAccrualReports();
		assertEquals("Wrong number of disease accrual reports returned", 3,
				diseaseSiteAccrualReports.size());
	}

	public void testGetStudyAccrualReports() throws Exception {
		List<StudyAccrualReport> studyAccrualReports = accrualService
				.getStudyAccrualReports();
		assertEquals("Wrong number of study accrual reports returned", 2,
				studyAccrualReports.size());
	}

	public void testGetSiteAccrual1() throws Exception {

		DiseaseSiteAccrualReport diseaseSiteAccrualReport = new DiseaseSiteAccrualReport();
		diseaseSiteAccrualReport.setName("Abdomen");
		StudyAccrualReport studyAccrualReport = new StudyAccrualReport();
		studyAccrualReport.setShortTitle("short_title_text");
		Date startDate = new Date();

		// the reference for setting year is 1900
		startDate.setYear(100);
		Date endDate =null;

		/*SiteAccrualReport siteAccrualReport = accrualService.getAccrual(
				diseaseSiteAccrualReport, studyAccrualReport, startDate, endDate);
		assertEquals("Wrong number of study accrual reports returned", 1,
				siteAccrualReport.getAccrual().getValue());*/
	}

	public void testGetSiteAccrual2() throws Exception {

		DiseaseSiteAccrualReport diseaseSiteAccrualReport = new DiseaseSiteAccrualReport();
		diseaseSiteAccrualReport.setName("Abdomen");
		StudyAccrualReport studyAccrualReport = new StudyAccrualReport();
		studyAccrualReport.setShortTitle("short_title_text");
		Date startDate = new Date();

		// the reference for setting year is 1900
		startDate.setYear(100);
		
		Date endDate = new Date();

		// the reference for setting year is 1900
		endDate.setYear(101);


		/*SiteAccrualReport siteAccrualReport = accrualService.getAccrual(
				diseaseSiteAccrualReport, studyAccrualReport, startDate, endDate);
		assertEquals("Wrong number of study accrual reports returned", 0,
				siteAccrualReport.getAccrual().getValue());*/
	}
	
	public void testGetSiteAccrual3() throws Exception {

		DiseaseSiteAccrualReport diseaseSiteAccrualReport = new DiseaseSiteAccrualReport();
		diseaseSiteAccrualReport.setName("Abdomen");
		StudyAccrualReport studyAccrualReport = new StudyAccrualReport();
		studyAccrualReport.setShortTitle("short_title_text");
		Date startDate = new Date();

		// the reference for setting year is 1900
		startDate.setYear(100);
		
		Date endDate = new Date();

		// the reference for setting year is 1900
		endDate.setYear(111);


		/*SiteAccrualReport siteAccrualReport = accrualService.getAccrual(
				diseaseSiteAccrualReport, studyAccrualReport, startDate, endDate);
		assertEquals("Wrong number of study accrual reports returned", 1,
				siteAccrualReport.getAccrual().getValue());*/
	}
	
	public void testGetSiteAccrual4() throws Exception {

		DiseaseSiteAccrualReport diseaseSiteAccrualReport = new DiseaseSiteAccrualReport();
		diseaseSiteAccrualReport.setName("Skin");
		StudyAccrualReport studyAccrualReport = new StudyAccrualReport();
		studyAccrualReport.setShortTitle("short_title_text");
		Date startDate = new Date();

		// the reference for setting year is 1900
		startDate.setYear(100);
		
		Date endDate = new Date();

		// the reference for setting year is 1900
		endDate.setYear(111);


		/*SiteAccrualReport siteAccrualReport = accrualService.getAccrual(
				diseaseSiteAccrualReport, studyAccrualReport, startDate, endDate);
		assertEquals("Wrong number of study accrual reports returned", 1,
				siteAccrualReport.getAccrual().getValue());*/
	}
	
	public void testGetSiteAccrual5() throws Exception {

		DiseaseSiteAccrualReport diseaseSiteAccrualReport = null;
		StudyAccrualReport studyAccrualReport = new StudyAccrualReport();
		studyAccrualReport.setShortTitle("short_title_text");
		Date startDate = new Date();

		// the reference for setting year is 1900
		startDate.setYear(100);
		
		Date endDate = new Date();

		// the reference for setting year is 1900
		endDate.setYear(111);

/*
		SiteAccrualReport siteAccrualReport = accrualService.getAccrual(
				diseaseSiteAccrualReport, studyAccrualReport, startDate, endDate);
		assertEquals("Wrong number of study accrual reports returned", 2,
				siteAccrualReport.getAccrual().getValue());*/
	}
	
	public void testToXML(){
		
		DiseaseSiteAccrualReport diseaseSiteAccrualReport = null;
		StudyAccrualReport studyAccrualReport = new StudyAccrualReport();
		studyAccrualReport.setShortTitle("short_title_text");
		Date startDate = new Date();

		// the reference for setting year is 1900
		startDate.setYear(100);
		
		Date endDate = new Date();

		// the reference for setting year is 1900
		endDate.setYear(111);


		/*SiteAccrualReport siteAccrualReport = accrualService.getAccrual(
				diseaseSiteAccrualReport, studyAccrualReport, startDate, endDate);
		assertEquals("Wrong number of study accrual reports returned", 2,
				siteAccrualReport.getAccrual().getValue());
		try {
			String xmlSiteReport = (marshaller.toXML(siteAccrualReport));
			System.out.println(xmlSiteReport);
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		
	}


}
