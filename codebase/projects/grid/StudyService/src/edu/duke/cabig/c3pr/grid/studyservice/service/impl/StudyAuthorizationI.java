/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.grid.studyservice.service.impl;

import java.rmi.RemoteException;

public interface StudyAuthorizationI {

	public void authorizeGetMultipleResourceProperties(String callerIdentity) throws RemoteException;

	public void authorizeGetResourceProperty(String callerIdentity) throws RemoteException;

	public void authorizeQueryResourceProperties(String callerIdentity) throws RemoteException;

	public void authorizeGetServiceSecurityMetadata(String callerIdentity) throws RemoteException;

	public void authorizeActivateStudySite(String callerIdentity) throws RemoteException;

	public void authorizeAmendStudy(String callerIdentity) throws RemoteException;

	public void authorizeCloseStudyToAccrual(String callerIdentity) throws RemoteException ;

	public void authorizeCloseStudySiteToAccrual(String callerIdentity) throws RemoteException;

	public void authorizeCreateStudyDefinition(String callerIdentity) throws RemoteException;
	
	public void authorizeOpenStudy(String callerIdentity) throws RemoteException;
	
	public void authorizeCreateAndOpenStudy(String callerIdentity) throws RemoteException;

	public void authorizeUpdateStudySiteProtocolVersion(String callerIdentity) throws RemoteException;

	public void authorizeUpdateStudy(String callerIdentity) throws RemoteException;

	public void authorizeGetStudy(String callerIdentity) throws RemoteException;

	public void authorizeCloseStudyToAccrualAndTreatment(String callerIdentity) throws RemoteException;

	public void authorizeTemporarilyCloseStudyToAccrual(String callerIdentity) throws RemoteException;

	public void authorizeTemporarilyCloseStudyToAccrualAndTreatment(String callerIdentity) throws RemoteException;

	public void authorizeCloseStudySiteToAccrualAndTreatment(String callerIdentity) throws RemoteException;

	public void authorizeTemporarilyCloseStudySiteToAccrualAndTreatment(String callerIdentity) throws RemoteException;

	public void authorizeTemporarilyCloseStudySiteToAccrual(String callerIdentity) throws RemoteException;
}
