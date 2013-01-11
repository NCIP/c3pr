/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.grid.registrationservice.service.impl;

import java.rmi.RemoteException;

public class RegistrationAuthorizationImpl implements RegistrationAuthorizationI {

	public void authorizeGetMultipleResourceProperties(String callerIdentity) {
		System.out.println("Default Authorized");

	}

	public void authorizeGetResourceProperty(String callerIdentity) {
		System.out.println("Default Authorized");

	}

	public void authorizeGetServiceSecurityMetadata(String callerIdentity) {
		System.out.println("Default Authorized");

	}

	public void authorizeEnroll(String callerIdentity) throws RemoteException {
		System.out.println("Default Authorized");		
	}

	public void authorizeOffStudy(String callerIdentity) throws RemoteException {
		System.out.println("Default Authorized");		
	}

	public void authorizeQueryResourceProperties(String callerIdentity)
			throws RemoteException {
		System.out.println("Default Authorized");		
	}

	public void authorizeTransfer(String callerIdentity) throws RemoteException {
		System.out.println("Default Authorized");		
	}

	public void authorizeGetRegistrations(String callerIdentity)
			throws RemoteException {
		System.out.println("Default Authorized");			
	}

}
