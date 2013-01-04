/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.grid.registrationservice.service;

import java.rmi.RemoteException;

import org.springframework.context.ApplicationContext;

import edu.duke.cabig.c3pr.grid.registrationservice.common.RegistrationServiceI;
import edu.duke.cabig.c3pr.grid.registrationservice.service.impl.EchoRegistrationServiceImpl;
import edu.duke.cabig.c3pr.grid.registrationservice.service.impl.SpringApplicationContextProvider;

/**
 * Grid Service APIs that manages subject enrollment. These
 * APIs should be implemented appropriately by coordinating centers and participating sites
 * to facilitate subject enrollment, changes in epoch and subject off study on studies.
 */
public class RegistrationServiceImpl extends RegistrationServiceImplBase {

    /** The registration service i. */
    private RegistrationServiceI registrationServiceI;
    
    /**
     * Instantiates a new registration service impl.
     * 
     * @throws RemoteException the remote exception
     */
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
	
  /**
   * Enrolls a subject at coordinating center. Coordinating center should provide appropriate implementation to allow participating site
   * to send a subject enrollment request to the coordinating center. Upon successful enrollment, the coordinating center should assign a study subject
   * identifier which should be returned to the participating site. If study requires randomization, then coordinating center should assign an Arm 
   * depending upon the stratification criterion. Participating site should provide valid subject data, study identifier, local site unique identifier and
   * enrollment information like eligibility, stratification, randomization, consent signed date etc.
   * 
   * @param message the message
   * 
   * @return the gov.nih.nci.cabig.ccts.domain. message
   * 
   * @throws RemoteException the remote exception
   */
  public gov.nih.nci.cabig.ccts.domain.Message enroll(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
	  return registrationServiceI.enroll(message);
  }

  /**
   * Transfer subject to a different epoch at coordinating center. Coordinating center should provide appropriate implementation to allow participating site
   * to send a change epoch request for a subject to the coordinating center. Upon successful transfer, the coordinating center should assign an Arm 
   * depending upon the stratification criterion if the study is randomized.  Participating site should provide valid study subject identifier and
   * epoch information like eligibility, stratification, randomization etc.
   * 
   * @param message the message
   * 
   * @return the gov.nih.nci.cabig.ccts.domain. message
   * 
   * @throws RemoteException the remote exception
   */
  public gov.nih.nci.cabig.ccts.domain.Message transfer(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    return registrationServiceI.transfer(message);
  }

  /**
   * Puts a subject off study at the coordinatng center. Coordinating center should provide appropriate implementation to allow participating site
   * to send a subject off study to the coordinating center. Participating site should provide valid study subject identifier, off study date and off study reason.
   * 
   * @param message the message
   * 
   * @throws RemoteException the remote exception
   */
  public void offStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    registrationServiceI.offStudy(message);
  }

  /**
   * Gets the registrations at a site. Sites should provide appropriate implementation to allow clients to get registrations or a study. Clients should send a valid
   * study subject identifier. 
   * 
   * @param message the message
   * 
   * @return the registrations
   * 
   * @throws RemoteException the remote exception
   */
  public gov.nih.nci.cabig.ccts.domain.Message getRegistrations(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
   return registrationServiceI.getRegistrations(message);
  }

}

