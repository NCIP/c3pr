package edu.duke.cabig.c3pr.grid.accrualservice.service;

import java.rmi.RemoteException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.XMLReader;

import edu.duke.cabig.c3pr.domain.accrual.SiteAccrualReport;
import edu.duke.cabig.c3pr.service.AccrualService;
import gov.nih.nci.cagrid.common.Utils;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class AccrualServiceImpl extends AccrualServiceImplBase {

	private AccrualService accrualService;
	
	public AccrualServiceImpl() throws RemoteException {
		super();
		ApplicationContext applicationContext= new ClassPathXmlApplicationContext((new String[] {
                "classpath*:edu/duke/cabig/c3pr/applicationContext-core.xml",
                "classpath*:edu/duke/cabig/c3pr/applicationContext-core-db.xml",
                "classpath*:edu/duke/cabig/c3pr/applicationContext-core-aspects.xml",
                "classpath*:edu/duke/cabig/c3pr/applicationContext-csm.xml",
                "classpath*:edu/duke/cabig/c3pr/applicationContext-config.xml",

        }));
		accrualService= (AccrualService)applicationContext.getBean("accrualService");
	}
	
  public gov.nih.nci.cabig.ccts.domain.SiteAcccrualReportType getSiteAccrualReport(java.lang.String diseaseSiteName,java.lang.String studyShortTitleText,java.util.Date startDate,java.util.Date endDate) throws RemoteException {
    //TODO: Implement this autogenerated method
	//   SiteAccrualReport siteAccrualReport= accrualService.getAccrual(diseaseSiteName, studyShortTitleText, startDate, endDate);
	 // Utils.deserializeObject(xmlReader, SiteAccrualReportType);
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType[] getDiseaseSiteAccrualReports() throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public java.lang.String getSiteCtepId() throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType[] getStudyAccrualReports() throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

}
