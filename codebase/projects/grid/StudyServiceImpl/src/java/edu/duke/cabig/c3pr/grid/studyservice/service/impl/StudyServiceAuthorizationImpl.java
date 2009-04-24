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
	
	public void authorizeCreateAndOpenStudy(String callerIdentity)
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

	public void authorizeCloseStudySiteToAccrual(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");
		
	}

	public void authorizeCloseStudySiteToAccrualAndTreatment(
			String callerIdentity) throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");
		
	}

	public void authorizeCloseStudyToAccrual(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");
		
	}

	public void authorizeCloseStudyToAccrualAndTreatment(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");
		
	}

	public void authorizeCreateStudyDefinition(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN) || roles
				.contains(RoleTypes.SITE_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");
		
	}

	public void authorizeGetStudy(String callerIdentity) throws RemoteException {
		
	}

	public void authorizeTemporarilyCloseStudySiteToAccrual(
			String callerIdentity) throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");
		
	}

	public void authorizeTemporarilyCloseStudySiteToAccrualAndTreatment(
			String callerIdentity) throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");
		
	}

	public void authorizeTemporarilyCloseStudyToAccrual(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");
		
	}

	public void authorizeTemporarilyCloseStudyToAccrualAndTreatment(
			String callerIdentity) throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");
		
	}

	public void authorizeUpdateStudy(String callerIdentity)
			throws RemoteException {
		ArrayList<RoleTypes> roles = gridSecurityUtils.getRoles(callerIdentity);
		if (!(roles.contains(RoleTypes.C3PR_ADMIN)
				|| roles.contains(RoleTypes.SITE_COORDINATOR) || roles
				.contains(RoleTypes.STUDY_COORDINATOR)))
			throw new RemoteException("Access Denied. User with role "
					+ gridSecurityUtils.getRolesAsString(roles)
					+ " cannot invoke this grid operation.");
		
	}
}
