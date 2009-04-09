package edu.duke.cabig.c3pr.grid.studyservice.service.impl;

import java.rmi.RemoteException;


public interface StudyAuthorizationI {

	public void authorizeGetMultipleResourceProperties(String callerIdentity) throws RemoteException;

	public void authorizeGetResourceProperty(String callerIdentity) throws RemoteException;

	public void authorizeQueryResourceProperties(String callerIdentity) throws RemoteException;

	public void authorizeGetServiceSecurityMetadata(String callerIdentity) throws RemoteException;

	public void authorizeCreateStudy(String callerIdentity) throws RemoteException;

	public void authorizeOpenStudy(String callerIdentity) throws RemoteException;

	public void authorizeApproveStudySiteForActivation(String callerIdentity) throws RemoteException;

	public void authorizeActivateStudySite(String callerIdentity) throws RemoteException;

	public void authorizeAmendStudy(String callerIdentity) throws RemoteException;

	public void authorizeUpdateStudySiteProtocolVersion(String callerIdentity) throws RemoteException;

	public void authorizeCloseStudy(String callerIdentity) throws RemoteException;

	public void authorizeUpdateStudyStatus(String callerIdentity) throws RemoteException;

	public void authorizeCloseStudySite(String callerIdentity) throws RemoteException;
}
