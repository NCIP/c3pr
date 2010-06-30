package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.repository.ResearchStaffRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.CommonUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;


public class ResearchStaffRepositoryImpl implements ResearchStaffRepository {
	
	public ResearchStaffDao researchStaffDao ; 
	
	public ResearchStaffDao getResearchStaffDao() {
		return researchStaffDao;
	}
	public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
		this.researchStaffDao = researchStaffDao;
	}
	
	private UserProvisioningManager userProvisioningManager;
	public UserProvisioningManager getUserProvisioningManager() {
		return userProvisioningManager;
	}
	public void setUserProvisioningManager(
			UserProvisioningManager userProvisioningManager) {
		this.userProvisioningManager = userProvisioningManager;
	}
	
	public ResearchStaff merge(ResearchStaff staff) {
		return (ResearchStaff)researchStaffDao.merge(staff);
	}
	public ResearchStaff getByAssignedIdentifier(String assignedIdentifier) {
		return researchStaffDao.getByAssignedIdentifier(assignedIdentifier);
	}
	public void initialize(ResearchStaff staff) {
		 researchStaffDao.initialize(staff);
	}
	
	public boolean isLoggedInUser(ResearchStaff staff){
		if(StringUtils.isNotBlank(staff.getLoginId())){
			User csmUser;
			csmUser = getCSMUser(staff);
			if(csmUser != null){
				return StringUtils.equals(csmUser.getLoginName(), CommonUtils.getLoggedInUsername());
			}
		}
		return false ;
	}
	
	public User getCSMUser(C3PRUser user) {
		try{
			return userProvisioningManager.getUserById(user.getLoginId());
		}catch (CSObjectNotFoundException e) {
		}
		return null ;
		
	}
	public ResearchStaff getByAssignedIdentifierFromLocal(String assignedIdentifier) {
		return researchStaffDao.getByAssignedIdentifierFromLocal(assignedIdentifier);
	}
	
	public List<ResearchStaff> getRemoteResearchStaff(ResearchStaff staff) {
		return researchStaffDao.getRemoteResearchStaff(staff);
	}
	public void evict(ResearchStaff researchStaff) {
		researchStaffDao.evict(researchStaff);
	}
	
	public List<C3PRUserGroupType> getGroups(User csmUser, HealthcareSite healthcareSite) {
		return researchStaffDao.getUserGroupsForOrganization(csmUser, healthcareSite);
	}
	
	/**
	 * Creates the csm user.
	 *
	 * @param researchStaff the research staff
	 * @param username the username
	 * @param hasAccessToAllSites the has access to all sites
	 * @return the research staff
	 * @throws C3PRBaseException the C3PR base exception
	 */
	public ResearchStaff createCSMUser(ResearchStaff researchStaff, String username, boolean hasAccessToAllSites) throws C3PRBaseException{
		return researchStaffDao.createOrModifyResearchStaff(researchStaff, true, username, null , hasAccessToAllSites);
	}

	/**
	 * Creates the csm user and assign roles.
	 *
	 * @param researchStaff the research staff
	 * @param username the username
	 * @param associationMap the association map
	 * @param hasAccessToAllSites the has access to all sites
	 * @return the research staff
	 * @throws C3PRBaseException the C3PR base exception
	 */
	public ResearchStaff createCSMUserAndAssignRoles(
			ResearchStaff researchStaff, String username,
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap,
			boolean hasAccessToAllSites) throws C3PRBaseException {
		return researchStaffDao.createOrModifyResearchStaff(researchStaff, true, username, associationMap , hasAccessToAllSites);
	}

	/**
	 * Creates the or modify research staff.
	 *
	 * @param researchStaff the research staff
	 * @param associationMap the association map
	 * @param hasAccessToAllSites the has access to all sites
	 * @return the research staff
	 * @throws C3PRBaseException the c3pr base exception
	 */
	public ResearchStaff createOrModifyResearchStaff(
			ResearchStaff researchStaff,
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap,
			boolean hasAccessToAllSites) throws C3PRBaseException {
		return researchStaffDao.createOrModifyResearchStaff(researchStaff, false, null, associationMap , hasAccessToAllSites);
	}

	/**
	 * Creates only the research staff.
	 *
	 * @param researchStaff the research staff
	 * @return the research staff
	 * @throws C3PRBaseException the C3PR base exception
	 */
	public ResearchStaff createResearchStaff(ResearchStaff researchStaff) throws C3PRBaseException {
		return researchStaffDao.createResearchStaff(researchStaff);
	}

	/**
	 * Creates the research staff with csm user.
	 *
	 * @param researchStaff the research staff
	 * @param username the username
	 * @param hasAccessToAllSites the has access to all sites
	 * @return the research staff
	 * @throws C3PRBaseException the c3pr base exception
	 */
	public ResearchStaff createResearchStaffWithCSMUser(
			ResearchStaff researchStaff, String username,
			boolean hasAccessToAllSites) throws C3PRBaseException {
		return researchStaffDao.createOrModifyResearchStaff(researchStaff, true, username, null , hasAccessToAllSites);
	}

	/**
	 * Creates the research staff with csm user and assign roles.
	 *
	 * @param researchStaff the research staff
	 * @param username the username
	 * @param associationMap the association map
	 * @param hasAccessToAllSites the has access to all sites
	 * @return the research staff
	 * @throws C3PRBaseException the C3PR base exception
	 */
	public ResearchStaff createResearchStaffWithCSMUserAndAssignRoles(
			ResearchStaff researchStaff, String username,
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap,
			boolean hasAccessToAllSites) throws C3PRBaseException {
		return researchStaffDao.createOrModifyResearchStaff(researchStaff, true, username, associationMap , hasAccessToAllSites);
	}

	/**
	 * Creates the super user.
	 *
	 * @param researchStaff the research staff
	 * @param username the username
	 * @param associationMap the association map
	 * @return the research staff
	 * @throws C3PRBaseException the C3PR base exception
	 */
	public ResearchStaff createSuperUser(ResearchStaff researchStaff, String username,
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap) throws C3PRBaseException {
		return researchStaffDao.createOrModifyResearchStaff(researchStaff, true, username, associationMap , true);
	}

	public ResearchStaff createResearchStaff(ResearchStaff researchStaff,
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap)  throws C3PRBaseException {
		return researchStaffDao.createOrModifyResearchStaff(researchStaff, true, null, associationMap, false);
	}
	
	public boolean getHasAccessToAllSites(User csmUser) {
		return researchStaffDao.getHasAccessToAllSites(csmUser);
	}
	

}
