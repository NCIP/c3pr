package edu.duke.cabig.c3pr.grid.registrationservice.service.impl;

import java.rmi.RemoteException;


public interface RegistrationAuthorizationI {

	public void authorizeGetMultipleResourceProperties(String callerIdentity) throws RemoteException;

	public void authorizeGetResourceProperty(String callerIdentity) throws RemoteException;

	public void authorizeQueryResourceProperties(String callerIdentity) throws RemoteException;

	public void authorizeGetServiceSecurityMetadata(String callerIdentity) throws RemoteException;

	public void authorizeEnroll(String callerIdentity) throws RemoteException;

	public void authorizeTransfer(String callerIdentity) throws RemoteException;

	public void authorizeOffStudy(String callerIdentity) throws RemoteException;
	
	public void authorizeGetRegistrations(String callerIdentity) throws RemoteException;

}
