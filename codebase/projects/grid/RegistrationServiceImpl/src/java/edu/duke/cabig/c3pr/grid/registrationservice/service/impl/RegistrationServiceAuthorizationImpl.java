/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.grid.registrationservice.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.duke.cabig.c3pr.constants.RoleTypes;
import edu.duke.cabig.c3pr.utils.GridSecurityUtils;

/**
 * The Class StudyServiceAuthorizationImpl.
 */
public class RegistrationServiceAuthorizationImpl implements RegistrationAuthorizationI {

	private GridSecurityUtils gridSecurityUtils;
	
	public void authorizeEnroll(String callerIdentity) throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN) || roles
				.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.REGISTRAR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");
	}

	public void authorizeOffStudy(String callerIdentity) throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN) || roles
				.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.REGISTRAR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");		
	}

	public void authorizeTransfer(String callerIdentity) throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN) || roles
				.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.REGISTRAR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");		
	}
	
	public void authorizeGetRegistrations(String callerIdentity) throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN) || roles
				.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.REGISTRAR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");
	
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI#authorizeGetMultipleResourceProperties(java.lang.String)
	 */
	public void authorizeGetMultipleResourceProperties(String callerIdentity)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI#authorizeGetResourceProperty(java.lang.String)
	 */
	public void authorizeGetResourceProperty(String callerIdentity)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI#authorizeGetServiceSecurityMetadata(java.lang.String)
	 */
	public void authorizeGetServiceSecurityMetadata(String callerIdentity)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI#authorizeQueryResourceProperties(java.lang.String)
	 */
	public void authorizeQueryResourceProperties(String callerIdentity)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	public void setGridSecurityUtils(GridSecurityUtils gridSecurityUtils) {
		this.gridSecurityUtils = gridSecurityUtils;
	}

}
