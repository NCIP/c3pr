/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.grid.registrationservice.service.globus;

import edu.duke.cabig.c3pr.grid.registrationservice.service.RegistrationServiceImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the RegistrationServiceImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class RegistrationServiceProviderImpl{
	
	RegistrationServiceImpl impl;
	
	public RegistrationServiceProviderImpl() throws RemoteException {
		impl = new RegistrationServiceImpl();
	}
	

    public edu.duke.cabig.c3pr.grid.registrationservice.stubs.EnrollResponse enroll(edu.duke.cabig.c3pr.grid.registrationservice.stubs.EnrollRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.EnrollResponse boxedResult = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.EnrollResponse();
    boxedResult.setMessage(impl.enroll(params.getMessage().getMessage()));
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.registrationservice.stubs.TransferResponse transfer(edu.duke.cabig.c3pr.grid.registrationservice.stubs.TransferRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.TransferResponse boxedResult = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.TransferResponse();
    boxedResult.setMessage(impl.transfer(params.getMessage().getMessage()));
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.registrationservice.stubs.OffStudyResponse offStudy(edu.duke.cabig.c3pr.grid.registrationservice.stubs.OffStudyRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.OffStudyResponse boxedResult = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.OffStudyResponse();
    impl.offStudy(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.registrationservice.stubs.GetRegistrationsResponse getRegistrations(edu.duke.cabig.c3pr.grid.registrationservice.stubs.GetRegistrationsRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.GetRegistrationsResponse boxedResult = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.GetRegistrationsResponse();
    boxedResult.setMessage(impl.getRegistrations(params.getMessage().getMessage()));
    return boxedResult;
  }

}
