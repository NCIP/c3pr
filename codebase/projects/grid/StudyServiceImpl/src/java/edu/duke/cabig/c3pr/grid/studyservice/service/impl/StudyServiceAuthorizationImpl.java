package edu.duke.cabig.c3pr.grid.studyservice.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.duke.cabig.c3pr.utils.GridSecurityUtils;
import edu.duke.cabig.c3pr.utils.RoleTypes;

/**
 * The Class StudyServiceAuthorizationImpl.
 */
public class StudyServiceAuthorizationImpl implements StudyAuthorizationI {

	/** The user details service. */
	private GridSecurityUtils gridSecurityUtils;

	public void setGridSecurityUtils(GridSecurityUtils gridSecurityUtils) {
		this.gridSecurityUtils = gridSecurityUtils;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI#authorizeCreateStudy(java.lang.String)
	 */
	public void authorizeCreateStudy(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN) || roles
				.contains(RoleTypes.SITE_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI#authorizeOpenStudy(java.lang.String)
	 */
	public void authorizeOpenStudy(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI#authorizeApproveStudySiteForActivation(java.lang.String)
	 */
	public void authorizeApproveStudySiteForActivation(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI#authorizeActivateStudySite(java.lang.String)
	 */
	public void authorizeActivateStudySite(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI#authorizeAmendStudy(java.lang.String)
	 */
	public void authorizeAmendStudy(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI#authorizeUpdateStudySiteProtocolVersion(java.lang.String)
	 */
	public void authorizeUpdateStudySiteProtocolVersion(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI#authorizeCloseStudy(java.lang.String)
	 */
	public void authorizeCloseStudy(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI#authorizeUpdateStudyStatus(java.lang.String)
	 */
	public void authorizeUpdateStudyStatus(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI#authorizeCloseStudySite(java.lang.String)
	 */
	public void authorizeCloseStudySite(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
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
}
