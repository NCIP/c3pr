package edu.duke.cabig.c3pr.grid.accrualservice.service;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;
import org.springframework.web.context.request.WebRequest;

import edu.duke.cabig.c3pr.domain.accrual.Accrual;
import edu.duke.cabig.c3pr.domain.accrual.DiseaseSiteAccrualReport;
import edu.duke.cabig.c3pr.domain.accrual.SiteAccrualReport;
import edu.duke.cabig.c3pr.domain.accrual.StudyAccrualReport;
import edu.duke.cabig.c3pr.service.AccrualService;
import edu.duke.cabig.c3pr.utils.SessionAndAuditHelper;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ccts.domain.AccrualType;
import gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType;
import gov.nih.nci.cabig.ccts.domain.SiteAcccrualReportType;
import gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType;
import gov.nih.nci.cagrid.common.Utils;

/**
 * TODO:I am the service side implementation class. IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class AccrualServiceImpl extends AccrualServiceImplBase {

	private AccrualService accrualService;

	private XmlMarshaller marshaller;
	
	private OpenSessionInViewInterceptor interceptor;

	public AccrualServiceImpl() throws RemoteException {
		super();
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				(new String[] {
						"classpath*:edu/duke/cabig/c3pr/applicationContext-*.xml"}));
		accrualService = (AccrualService) applicationContext
				.getBean("accrualService");

		marshaller = new XmlMarshaller("accrual-report-castor-mapping.xml");
		interceptor = (OpenSessionInViewInterceptor) applicationContext.getBean("openSessionInViewInterceptor");
		
	}

  public gov.nih.nci.cabig.ccts.domain.SiteAcccrualReportType getSiteAccrualReport(java.lang.String diseaseSiteName,java.lang.String studyShortTitleText,java.util.Date startDate,java.util.Date endDate) throws RemoteException {
		WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
		SiteAccrualReport siteAccrualReport = accrualService
				.getSiteAccrualReport(diseaseSiteName, studyShortTitleText,
						startDate, endDate);
		InputStream is = getClass().getClassLoader().getResourceAsStream("edu/duke/cabig/c3pr/grid/accrualservice/client/client-config.wsdd");
		String xmlSiteReport = "";
		
		try {
			xmlSiteReport = (marshaller.toXML(siteAccrualReport));
			System.out.println("---------------------- XML--------------------------------------------------" +"\n");
			System.out.println(xmlSiteReport);
			
			System.out.println("\n" + "---------------------- XML--------------------------------------------------" +"\n");
			Reader stringReader = new StringReader(xmlSiteReport);
			return (SiteAcccrualReportType)Utils.deserializeObject(stringReader, SiteAcccrualReportType.class);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
		}
		
		return null;
	}

  public gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType[] getDiseaseSiteAccrualReports() throws RemoteException {
	  WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
		try {
			List<DiseaseSiteAccrualReport> diseaseSiteAccrualReports = accrualService
					.getDiseaseSiteAccrualReports();
			List<DiseaseSiteAccrualReportType> diseaseSiteAccrualReportTypeList = new ArrayList<DiseaseSiteAccrualReportType>();
			for (DiseaseSiteAccrualReport diseaseSiteAccrualReport : diseaseSiteAccrualReports) {
				DiseaseSiteAccrualReportType diseaseAccrualReportType = new DiseaseSiteAccrualReportType();
				diseaseAccrualReportType.setName(diseaseSiteAccrualReport
						.getName());
				Accrual accrual = diseaseSiteAccrualReport.getAccrual();
				AccrualType accrualType = new AccrualType();
				accrualType.setValue(accrual.getValue());
				diseaseAccrualReportType.setAccrual(accrualType);
				diseaseSiteAccrualReportTypeList.add(diseaseAccrualReportType);
			}
			int size = diseaseSiteAccrualReportTypeList.size();
			DiseaseSiteAccrualReportType[] diseaseSiteAccrualReportArray = new DiseaseSiteAccrualReportType[size];
			DiseaseSiteAccrualReportType diseaseSiteAccrualReportTypeTemp = null;
			for(int i =0; i<size;i++ ){
				diseaseSiteAccrualReportArray[i]= (DiseaseSiteAccrualReportType)(diseaseSiteAccrualReportTypeList.get(i));
			}
			return  diseaseSiteAccrualReportArray;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
		}
		
		return null;
		
	}

  public java.lang.String getSiteCtepId() throws RemoteException {
		return accrualService.getSiteCtepId();
	}

  public gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType[] getStudyAccrualReports() throws RemoteException {
	  WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
		try {
			List<StudyAccrualReport> studyAccrualReports = accrualService.getStudyAccrualReports();
			List<StudyAccrualReportType> studyAccrualReportTypeList = new ArrayList<StudyAccrualReportType>();
			for (StudyAccrualReport studyAccrualReport : studyAccrualReports) {
				StudyAccrualReportType studyAccrualReportType = new StudyAccrualReportType();
				studyAccrualReportType.setShortTitle(studyAccrualReport.getShortTitle());
				studyAccrualReportType.setIdentifier(studyAccrualReport.getIdentifier());
				Accrual accrual = studyAccrualReport.getAccrual();
				AccrualType accrualType = new AccrualType();
				accrualType.setValue(accrual.getValue());
				studyAccrualReportType.setAccrual(accrualType);
				List<DiseaseSiteAccrualReport> diseaseSiteAccrualReports = studyAccrualReport.getDiseaseSiteAccrualReports();
				List<DiseaseSiteAccrualReportType> diseaseSiteAccrualReportTypeList = new ArrayList<DiseaseSiteAccrualReportType>();
				for (DiseaseSiteAccrualReport diseaseSiteAccrualReport : diseaseSiteAccrualReports) {
					DiseaseSiteAccrualReportType diseaseAccrualReportType = new DiseaseSiteAccrualReportType();
					diseaseAccrualReportType.setName(diseaseSiteAccrualReport
					.getName());
					Accrual accrualDisease = diseaseSiteAccrualReport.getAccrual();
					AccrualType accrualDiseaseType = new AccrualType();
					accrualDiseaseType.setValue(accrualDisease.getValue());
					diseaseAccrualReportType.setAccrual(accrualDiseaseType);
					diseaseSiteAccrualReportTypeList.add(diseaseAccrualReportType);
				}
				
				int size = diseaseSiteAccrualReportTypeList.size();
				DiseaseSiteAccrualReportType[] diseaseSiteAccrualReportArray = new DiseaseSiteAccrualReportType[size];
				DiseaseSiteAccrualReportType diseaseSiteAccrualReportTypeTemp = null;
				for(int i =0; i<size;i++ ){
					diseaseSiteAccrualReportArray[i]= (DiseaseSiteAccrualReportType)(diseaseSiteAccrualReportTypeList.get(i));
				}
				studyAccrualReportType.setDiseaseSiteAccrualReport(diseaseSiteAccrualReportArray);
			}
			int size = studyAccrualReportTypeList.size();
			StudyAccrualReportType[] studyAccrualReportArray = new StudyAccrualReportType[size];
			for(int i =0; i<size;i++ ){
				studyAccrualReportArray[i]= (StudyAccrualReportType)(studyAccrualReportTypeList.get(i));
			}
			return  studyAccrualReportArray;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
		}
		
		return null;
		
	}
	
}
