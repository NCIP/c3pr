package edu.duke.cabig.c3pr.grid.service.globus;

import edu.duke.cabig.c3pr.grid.service.C3PRStudyConsumerImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the C3PRStudyConsumerImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.0
 * 
 */
public class C3PRStudyConsumerProviderImpl{
	
	C3PRStudyConsumerImpl impl;
	
	public C3PRStudyConsumerProviderImpl() throws RemoteException {
		impl = new C3PRStudyConsumerImpl();
	}
	

	public edu.duke.cabig.c3pr.grid.stubs.CreateOrUpdateResponse createOrUpdate(edu.duke.cabig.c3pr.grid.stubs.CreateOrUpdateRequest params) throws RemoteException, edu.duke.cabig.c3pr.grid.stubs.types.StudyValidationException, edu.duke.cabig.c3pr.grid.stubs.types.StudyCreateOrUpdateException {
		C3PRStudyConsumerAuthorization.authorizeCreateOrUpdate();
		edu.duke.cabig.c3pr.grid.stubs.CreateOrUpdateResponse boxedResult = new edu.duke.cabig.c3pr.grid.stubs.CreateOrUpdateResponse();
		boxedResult.setStudy(impl.createOrUpdate(params.getStudy().getStudy()));
		return boxedResult;
	}

}
