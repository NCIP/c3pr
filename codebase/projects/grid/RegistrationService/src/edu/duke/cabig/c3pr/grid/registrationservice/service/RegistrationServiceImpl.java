package edu.duke.cabig.c3pr.grid.registrationservice.service;

import java.rmi.RemoteException;

import org.springframework.context.ApplicationContext;

import edu.duke.cabig.c3pr.grid.registrationservice.common.RegistrationServiceI;
import edu.duke.cabig.c3pr.grid.registrationservice.service.impl.EchoRegistrationServiceImpl;
import edu.duke.cabig.c3pr.grid.registrationservice.service.impl.SpringApplicationContextProvider;

/**
 * TODO:I am the service side implementation class. IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class RegistrationServiceImpl extends RegistrationServiceImplBase {

    private RegistrationServiceI registrationServiceI;
    
    public RegistrationServiceImpl() throws RemoteException {
    	super();
        ApplicationContext appContext=SpringApplicationContextProvider.getApplicationContext();
        if(appContext==null){
            System.out
                            .println("no applicationContext-grid-c3prRegistrationService.xml in calsspath. Loading echo registration service impl...");
            registrationServiceI = new EchoRegistrationServiceImpl();

        }
        else {
            registrationServiceI = (RegistrationServiceI) appContext
                            .getBean("c3prRegistrationService");
        }
    }
	
  public gov.nih.nci.cabig.ccts.domain.Message enroll(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  return registrationServiceI.enroll(message);
  }

  public gov.nih.nci.cabig.ccts.domain.Message transfer(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    return registrationServiceI.transfer(message);
  }

  public void offStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    registrationServiceI.offStudy(message);
  }

  public gov.nih.nci.cabig.ccts.domain.Message getRegistrations(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
   return registrationServiceI.getRegistrations(message);
  }

}

