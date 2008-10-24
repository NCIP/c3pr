package edu.duke.cabig.c3pr.grid.service;

import java.rmi.RemoteException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sun.rmi.runtime.GetThreadPoolAction;

import edu.duke.cabig.c3pr.grid.common.StudyServiceI;
import edu.duke.cabig.c3pr.grid.service.impl.EchoStudyServiceImpl;

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
        if(this.getClass().getClassLoader().getResourceAsStream("applicationContext-grid-c3prStudyService.xml")==null){
            System.out.println("no applicationContext-grid-c3prStudyService.xml in calsspath. Loading echo study service impl...");
            studyServiceI=new EchoStudyServiceImpl();
            
        }else{
            ApplicationContext appContext=new ClassPathXmlApplicationContext("applicationContext-grid-c3prStudyService.xml");
            studyServiceI=(StudyServiceI)appContext.getBean("c3prStudyService");
        }
    }

    public void createStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
        studyServiceI.createStudy(message);
    }

    public void openStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
        studyServiceI.openStudy(message);
    }

    public void approveStudySiteForActivation(gov.nih.nci.cabig.ccts.domain.Message message)
                    throws RemoteException {
        studyServiceI.approveStudySiteForActivation(message);
    }

    public void activateStudySite(gov.nih.nci.cabig.ccts.domain.Message message)
                    throws RemoteException {
        studyServiceI.activateStudySite(message);
    }

    public void amendStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
        studyServiceI.amendStudy(message);
    }

    public void updateStudySiteProtocolVersion(gov.nih.nci.cabig.ccts.domain.Message message)
                    throws RemoteException {
        studyServiceI.updateStudySiteProtocolVersion(message);
    }

    public void closeStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
        studyServiceI.closeStudy(message);
    }

    public void updateStudyStatus(gov.nih.nci.cabig.ccts.domain.Message message)
                    throws RemoteException {
        studyServiceI.updateStudyStatus(message);
    }

    public void closeStudySite(gov.nih.nci.cabig.ccts.domain.Message message)
                    throws RemoteException {
        studyServiceI.closeStudySite(message);
    }

    public void closeStudySites(gov.nih.nci.cabig.ccts.domain.Message message)
                    throws RemoteException {
        studyServiceI.closeStudySites(message);
    }
}
