package edu.duke.cabig.c3pr.grid.studyservice.service;

import java.rmi.RemoteException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.duke.cabig.c3pr.grid.studyservice.common.StudyServiceI;
import edu.duke.cabig.c3pr.grid.studyservice.service.impl.EchoStudyServiceImpl;
import edu.duke.cabig.c3pr.grid.studyservice.service.impl.SpringApplicationContextProvider;

/**
 * TODO:I am the service side implementation class. IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class StudyServiceImpl extends StudyServiceImplBase {

	private StudyServiceI studyServiceI;
    
    public StudyServiceImpl() throws RemoteException {
        super();
        ApplicationContext appContext=SpringApplicationContextProvider.getApplicationContext();
        if(appContext==null){
            System.out.println("no applicationContext-grid-c3prStudyService.xml in classpath. Loading echo study service impl...");
            studyServiceI=new EchoStudyServiceImpl();
        }else{
        	System.out.println("applicationContext-grid-c3prStudyService.xml found in classpath. Loading echo study service impl...");
            studyServiceI=(StudyServiceI)appContext.getBean("gridStudyService");
        }
//        if(this.getClass().getClassLoader().getResourceAsStream("applicationContext-grid-c3prStudyService.xml")==null){
//            System.out.println("no applicationContext-grid-c3prStudyService.xml in calsspath. Loading echo study service impl...");
//            studyServiceI=new EchoStudyServiceImpl();
//            
//        }else{
//            ApplicationContext appContext=new ClassPathXmlApplicationContext("applicationContext-grid-c3prStudyService.xml");
//            studyServiceI=(StudyServiceI)appContext.getBean("c3prStudyService");
//        }
    }
	
  public void activateStudySite(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.activateStudySite(message);
  }

  public void amendStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.amendStudy(message);
  }

  public void closeStudyToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.closeStudyToAccrual(message);
  }

  public void closeStudySiteToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.closeStudySiteToAccrual(message);
  }

  public void createStudyDefinition(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.createStudyDefinition(message);
  }

  public void openStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.openStudy(message);
  }

  public void updateStudySiteProtocolVersion(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.updateStudySiteProtocolVersion(message);
  }

  public void updateStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    studyServiceI.updateStudy(message);
  }

  public gov.nih.nci.cabig.ccts.domain.Message getStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  return studyServiceI.getStudy(message);
  }

  public void closeStudyToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.closeStudyToAccrual(message);
  }

  public void temporarilyCloseStudyToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.temporarilyCloseStudyToAccrual(message);
  }

  public void temporarilyCloseStudyToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.temporarilyCloseStudyToAccrualAndTreatment(message);
  }

  public void closeStudySiteToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.closeStudySiteToAccrual(message);
  }

  public void temporarilyCloseStudySiteToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.temporarilyCloseStudySiteToAccrualAndTreatment(message);
  }

  public void temporarilyCloseStudySiteToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.temporarilyCloseStudySiteToAccrual(message);
  }

  public void createAndOpenStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    studyServiceI.createAndOpenStudy(message);
  }

}

