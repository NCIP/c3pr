package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.repository.PersonUserRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.CommonUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;


public class PersonUserRepositoryImpl implements PersonUserRepository {
	
	public PersonUserDao personUserDao ; 
	
	/** The log. */
	private static Log log = LogFactory.getLog(PersonUserRepositoryImpl.class);
	
	public PersonUserDao getPersonUserDao() {
		return personUserDao;
	}
	public void setPersonUserDao(PersonUserDao personUserDao) {
		this.personUserDao = personUserDao;
	}
	
	private UserProvisioningManager userProvisioningManager;
	public UserProvisioningManager getUserProvisioningManager() {
		return userProvisioningManager;
	}
	public void setUserProvisioningManager(
			UserProvisioningManager userProvisioningManager) {
		this.userProvisioningManager = userProvisioningManager;
	}
	
	public PersonUser merge(PersonUser staff) {
		return (PersonUser)personUserDao.merge(staff);
	}
	public PersonUser getByAssignedIdentifier(String assignedIdentifier) {
		return personUserDao.getByAssignedIdentifier(assignedIdentifier);
	}
	public void initialize(PersonUser staff) {
		 personUserDao.initialize(staff);
	}
	
	public boolean isLoggedInUser(PersonUser staff){
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
		return getCSMUserByUserName(user.getLoginId());
	}
	
	public User getCSMUserByUserName(String userName) {
		try{
			return userProvisioningManager.getUserById(userName);
		}catch (CSObjectNotFoundException e) {
			log.error(e.getMessage());
		}
		return null ;
	}
	
	public PersonUser getByAssignedIdentifierFromLocal(String assignedIdentifier) {
		return personUserDao.getByAssignedIdentifierFromLocal(assignedIdentifier);
	}
	
	public List<PersonUser> getRemoteResearchStaff(PersonUser staff) {
		return personUserDao.getRemoteResearchStaff(staff);
	}
	public void evict(PersonUser researchStaff) {
		personUserDao.evict(researchStaff);
	}
	
	public List<C3PRUserGroupType> getGroups(User csmUser, HealthcareSite healthcareSite) {
		return personUserDao.getUserGroupsForOrganization(csmUser, healthcareSite);
	}
	

	/**
	 * Creates the personUser without Assigned ID and csm user and assign roles.
	 *
	 * @param researchStaff the research staff
	 * @param username the username
	 * @param associationMap the association map
	 * @param hasAccessToAllSites the has access to all sites
	 * @return the research staff
	 * @throws C3PRBaseException the C3PR base exception
	 */
	public PersonUser createOrModifyUserWithoutResearchStaffAndAssignRoles(
			PersonUser researchStaff, String username,
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap,
			boolean hasAccessToAllSites) throws C3PRBaseException {
		if(StringUtils.isBlank(username)){
			log.error("Cannot create User without a username");
			return researchStaff;
		}
		if(StringUtils.isBlank(researchStaff.getAssignedIdentifier())){
			researchStaff.setAssignedIdentifier(null);
		}
		return personUserDao.createOrModifyPersonUser(researchStaff, true, username, associationMap , hasAccessToAllSites);
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
	public PersonUser createOrModifyResearchStaffWithoutUser(
			PersonUser researchStaff, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap,
			boolean hasAccessToAllSites) throws C3PRBaseException {
		if(StringUtils.isBlank(researchStaff.getAssignedIdentifier())){
			log.error("Cannot create User without an Assigned Identifier");
			return researchStaff;
		}
		return personUserDao.createOrModifyPersonUser(researchStaff, false, null, associationMap , hasAccessToAllSites);
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
	public PersonUser createOrModifyResearchStaffWithUserAndAssignRoles(
			PersonUser researchStaff, String username,
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap,
			boolean hasAccessToAllSites) throws C3PRBaseException {
		if(StringUtils.isBlank(researchStaff.getAssignedIdentifier())){
			log.error("Cannot create User without an Assigned Identifier");
			return researchStaff;
		}
		if(StringUtils.isBlank(username)){
			log.error("Cannot create User without a username");
			return researchStaff;
		}
		return personUserDao.createOrModifyPersonUser(researchStaff, true, username, associationMap , hasAccessToAllSites);
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
	public PersonUser createSuperUser(PersonUser researchStaff, String username,
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap) throws C3PRBaseException {
		return personUserDao.createOrModifyPersonUser(researchStaff, true, username, associationMap , true);
	}

	public boolean getHasAccessToAllSites(User csmUser) {
		return personUserDao.getHasAccessToAllSites(csmUser);
	}

}
