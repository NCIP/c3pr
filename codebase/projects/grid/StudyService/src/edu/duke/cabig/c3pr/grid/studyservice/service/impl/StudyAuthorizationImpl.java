/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.grid.studyservice.service.impl;

import java.rmi.RemoteException;

public class StudyAuthorizationImpl implements StudyAuthorizationI {

	public void authorizeActivateStudySite(String callerIdentity) {
		System.out.println("Default Authorized");

	}

	public void authorizeAmendStudy(String callerIdentity) {
		System.out.println("Default Authorized");

	}

	public void authorizeGetMultipleResourceProperties(String callerIdentity) {
		System.out.println("Default Authorized");

	}

	public void authorizeGetResourceProperty(String callerIdentity) {
		System.out.println("Default Authorized");

	}

	public void authorizeGetServiceSecurityMetadata(String callerIdentity) {
		System.out.println("Default Authorized");

	}

	public void authorizeOpenStudy(String callerIdentity) {
		System.out.println("Default Authorized");

	}
	
	public void authorizeCreateAndOpenStudy(String callerIdentity) {
		System.out.println("Default Authorized");

	}

	public void authorizeQueryResourceProperties(String callerIdentity) {
		System.out.println("Default Authorized");

	}

	public void authorizeUpdateStudySiteProtocolVersion(String callerIdentity) {
		System.out.println("Default Authorized");

	}

	public void authorizeCloseStudySiteToAccrual(String callerIdentity)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void authorizeCloseStudySiteToAccrualAndTreatment(
			String callerIdentity) throws RemoteException {
		System.out.println("Default Authorized");		
	}

	public void authorizeCloseStudyToAccrual(String callerIdentity)
			throws RemoteException {
		System.out.println("Default Authorized");		
	}

	public void authorizeCloseStudyToAccrualAndTreatment(String callerIdentity)
			throws RemoteException {
		System.out.println("Default Authorized");		
	}

	public void authorizeCreateStudyDefinition(String callerIdentity)
			throws RemoteException {
		System.out.println("Default Authorized");		
	}

	public void authorizeGetStudy(String callerIdentity) throws RemoteException {
		System.out.println("Default Authorized");		
	}

	public void authorizeTemporarilyCloseStudySiteToAccrual(
			String callerIdentity) throws RemoteException {
		System.out.println("Default Authorized");		
	}

	public void authorizeTemporarilyCloseStudySiteToAccrualAndTreatment(
			String callerIdentity) throws RemoteException {
		System.out.println("Default Authorized");		
	}

	public void authorizeTemporarilyCloseStudyToAccrual(String callerIdentity)
			throws RemoteException {
		System.out.println("Default Authorized");		
	}

	public void authorizeTemporarilyCloseStudyToAccrualAndTreatment(
			String callerIdentity) throws RemoteException {
		System.out.println("Default Authorized");		
	}

	public void authorizeUpdateStudy(String callerIdentity)
			throws RemoteException {
		System.out.println("Default Authorized");		
	}

}
