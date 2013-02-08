/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.grid.studyservice.service;

import java.rmi.RemoteException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.duke.cabig.c3pr.grid.studyservice.common.StudyServiceI;
import edu.duke.cabig.c3pr.grid.studyservice.service.impl.EchoStudyServiceImpl;
import edu.duke.cabig.c3pr.grid.studyservice.service.impl.SpringApplicationContextProvider;

/**
 * Grid Service APIs that manages study lifecycle within the scope of C3PR. These
 * APIs should be implemented appropriately by coordinating centers and participating sites
 * to facilitate study level communication (create, read, update etc). 
 * 
 * @created by Introduce Toolkit version 1.2
 */
public class StudyServiceImpl extends StudyServiceImplBase {

	/** The study service i. */
	private StudyServiceI studyServiceI;
    
    /**
     * Instantiates a new study service impl.
     * 
     * @throws RemoteException the remote exception
     */
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
	
  /**
   * Activates a study site at coordinating center. Coordinating Center should provide 
   * appropriate implementation to allow participating sites to activate their site. Site
   * should provide the study identifier, their local site unique identifier and a valid IRB approval date. 
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void activateStudySite(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.activateStudySite(message);
  }

  /**
   * Amends a study at a participating site. Participating sites should provide appropriate implementation
   * to allow coordinating center to amend the study at the local site. Coordinating center should provide
   * valid study identifier and the amended study version data.  
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void amendStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.amendStudy(message);
  }

  /**
   * Close study to accrual at a participating site. Participating sites should provide appropriate implementation
   * to allow coordinating center to close the study  to accrual at the local site. Coordinating center should provide
   * valid study identifier. The site will no longer be able to enroll subjects on the study.
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void closeStudyToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.closeStudyToAccrual(message);
  }

  /**
   * Close study site to accrual at the participating site. Participating sites should provide appropriate implementation
   * to allow coordinating center to close the study site to accrual. Coordinating center should provide
   * valid study identifier and the the valid site unique identifier. The site will no longer be able to enroll subjects on the study. 
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void closeStudySiteToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.closeStudySiteToAccrual(message);
  }

  /**
   * Creates the study definition at a participating site. Participating sites should provide appropriate implementation
   * to allow coordinating center to create the study definition at the local site. Coordinating center should provide valid & complete
   * study data. The sites will only be able to enroll subjects after the coordinating center activates the site. 
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void createStudyDefinition(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.createStudyDefinition(message);
  }

  /**
   * Opens study at a participating site. Participating sites should provide appropriate implementation
   * to allow coordinating center to set the study in open status at the local site. Coordinating center should provide valid
   * study identifier. The sites will only be able to enroll subjects after the coordinating center activates the site.
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void openStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.openStudy(message);
  }

  /**
   * Update study site protocol version. Coordinating center should provide appropriate implementation to
   * allow participating sites to update the study version they are on.Participating site should provide valid
   * study identifier, their local site unique identifier and a valid IRB approval date.
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void updateStudySiteProtocolVersion(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.updateStudySiteProtocolVersion(message);
  }

  /**
   * Update study at a participating site. Participating sites should provide appropriate implementation
   * to allow coordinating center to update the study at the local site. Coordinating center should provide valid
   * study identifier and the changed study data.
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void updateStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    studyServiceI.updateStudy(message);
  }

  /**
   * Gets the study. Participating sites and Coordinating centers should provide appropriate implementation to
   * allow interested sites to receive study details. Clients should provide valid study identifier. 
   * 
   * @param message the message
   * 
   * @return the study
   * 
   * @throws RemoteException the remote exception
   */
  public gov.nih.nci.cabig.ccts.domain.Message getStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  return studyServiceI.getStudy(message);
  }

  /**
   * Close study to accrual and treatment at a participating site. Participating sites should provide appropriate implementation
   * to allow coordinating center to close the study  to accrual and treatment at the local site. Coordinating center should provide
   * valid study identifier. The site will no longer be able to enroll subjects on the study.
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void closeStudyToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.closeStudyToAccrual(message);
  }

  /**
   * Temporarily close study to accrual at a participating site. Participating sites should provide appropriate implementation
   * to allow coordinating center to close the study to temporarily accrual at the local site. Coordinating center should provide
   * valid study identifier. The site will no longer be able to enroll subjects on the study
   * until the coordinating reactivates the site.
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void temporarilyCloseStudyToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.temporarilyCloseStudyToAccrual(message);
  }

  /**
   * Temporarily close study to accrual and treatment at a participating site. Participating sites should provide appropriate implementation
   * to allow coordinating center to close the study to temporarily accrual and treatment at the local site. Coordinating center should provide
   * valid study identifier. The site will no longer be able to enroll subjects on the study
   * until the coordinating reactivates the site.
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void temporarilyCloseStudyToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.temporarilyCloseStudyToAccrualAndTreatment(message);
  }

  /**
   * Close study site to accrual and treatment at the participating site. Participating sites should provide appropriate implementation
   * to allow coordinating center to close the study site to accrual. Coordinating center should provide
   * valid study identifier and the the valid site unique identifier. The site will no longer be able to enroll subjects on the study.
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void closeStudySiteToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.closeStudySiteToAccrual(message);
  }

  /**
   * Temporarily close study site to accrual at the participating site. Participating sites should provide appropriate implementation
   * to allow coordinating center to temporarily close the study site to accrual. Coordinating center should provide
   * valid study identifier and the the valid site unique identifier. The site will no longer be able to enroll subjects on the study
   * until the coordinating reactivates the site.
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void temporarilyCloseStudySiteToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.temporarilyCloseStudySiteToAccrualAndTreatment(message);
  }

  /**
   * Temporarily close study site to accrual at the participating site. Participating sites should provide appropriate implementation
   * to allow coordinating center to temporarily close the study site to accrual. Coordinating center should provide
   * valid study identifier and the the valid site unique identifier. The site will no longer be able to enroll subjects on the study
   * until the coordinating reactivates the site.
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void temporarilyCloseStudySiteToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  studyServiceI.temporarilyCloseStudySiteToAccrual(message);
  }

  /**
   * Creates the and open study at a participating site. Participating sites should provide appropriate implementation
   * to allow coordinating center to create the study definition and set the study status to open at the local site. Coordinating center 
   * should provide valid & complete study data. The sites will only be able to enroll subjects after the coordinating center activates the site.  
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void createAndOpenStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    studyServiceI.createAndOpenStudy(message);
  }

}

