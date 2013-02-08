/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.StatusType;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.repository.PersonUserRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.CommonUtils;
import edu.duke.cabig.c3pr.utils.RoleBasedHealthcareSitesAndStudiesDTO;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;
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
	
	public User getCSMUser(PersonUser staff) {
		User user =  getCSMUserByUserName(staff.getLoginId());
		//set the user status in personUser before returning..consider moving this logic to better location.
		if(user != null){
			if(SecurityUtils.isUserDeactivated(user.getEndDate())){
				staff.setUserStatus(StatusType.IN.getName());
	    	} else {
	    		staff.setUserStatus(StatusType.AC.getName());
	    	}
		}
		return user;
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
	
	public List<C3PRUserGroupType> getGroupsForUser(User csmUser) {
		return personUserDao.getGroupsForUser(csmUser);
	}
	
	public List<String> getOrganizationIdsForUser(User csmUser, C3PRUserGroupType c3prUserGroupType){
		return personUserDao.getOrganizationIdsForUser(csmUser, c3prUserGroupType);
	}
	
	public List<String> getStudyIdsForUser(User csmUser, C3PRUserGroupType c3prUserGroupType){
		return personUserDao.getStudyIdsForUser(csmUser, c3prUserGroupType);
	}
	
	public List<String> getOrganizationIdsForUser(ProvisioningSession provisioningSession , C3PRUserGroupType c3prUserGroupType){
		return personUserDao.getOrganizationIdsForUser(provisioningSession, c3prUserGroupType);
	}
	
	public List<String> getStudyIdsForUser(ProvisioningSession provisioningSession , C3PRUserGroupType c3prUserGroupType){
		return personUserDao.getStudyIdsForUser(provisioningSession, c3prUserGroupType);
	}

	/**
	 * Creates the personUser without Assigned ID and csm user and assign roles.
	 *
	 * @param researchStaff the research staff
	 * @param username the username
	 * @param listAssociation the list association
	 * @return the research staff
	 * @throws C3PRBaseException the C3PR base exception
	 */
	public PersonUser createOrModifyUserWithoutResearchStaffAndAssignRoles(
			PersonUser researchStaff, String username,
			List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) throws C3PRBaseException {
		if(StringUtils.isBlank(researchStaff.getAssignedIdentifier())){
			researchStaff.setAssignedIdentifier(null);
		}
		return personUserDao.createOrModifyPersonUser(researchStaff, true, username, listAssociation);
	}

	/**
	 * Creates the or modify research staff.
	 *
	 * @param researchStaff the research staff
	 * @param listAssociation the list association
	 * @return the research staff
	 * @throws C3PRBaseException the c3pr base exception
	 */
	public PersonUser createOrModifyResearchStaffWithoutUser(
			PersonUser researchStaff, List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) throws C3PRBaseException {
		if(StringUtils.isBlank(researchStaff.getAssignedIdentifier())){
			log.error("Cannot create User without an Assigned Identifier");
			return researchStaff;
		}
		return personUserDao.createOrModifyPersonUser(researchStaff, false, null, listAssociation);
	}

	/**
	 * Creates the research staff with csm user and assign roles.
	 *
	 * @param researchStaff the research staff
	 * @param username the username
	 * @param listAssociation the list association
	 * @return the research staff
	 * @throws C3PRBaseException the C3PR base exception
	 */
	public PersonUser createOrModifyResearchStaffWithUserAndAssignRoles(
			PersonUser researchStaff, String username,
			List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) throws C3PRBaseException {
		if(StringUtils.isBlank(researchStaff.getAssignedIdentifier())){
			log.error("Cannot create User without an Assigned Identifier");
			return researchStaff;
		}
		return personUserDao.createOrModifyPersonUser(researchStaff, true, username, listAssociation);
	}
	

	/**
	 * Creates the super user.
	 *
	 * @param researchStaff the research staff
	 * @param username the username
	 * @param listAssociation the list association
	 * @return the research staff
	 * @throws C3PRBaseException the C3PR base exception
	 */
	public PersonUser createSuperUser(PersonUser researchStaff, String username,
			List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) throws C3PRBaseException {
		return personUserDao.createOrModifyPersonUser(researchStaff, true, username, listAssociation);
	}

	public boolean getHasAccessToAllSites(User csmUser, C3PRUserGroupType group) {
		return personUserDao.getHasAccessToAllSites(csmUser, group);
	}
	
	public boolean getHasAccessToAllStudies(User csmUser, C3PRUserGroupType group) {
		return personUserDao.getHasAccessToAllStudies(csmUser, group);
	}
	
	public boolean getHasAccessToAllSites(ProvisioningSession provisioningSession, C3PRUserGroupType group) {
		return personUserDao.getHasAccessToAllSites(provisioningSession, group);
	}
	
	public boolean getHasAccessToAllStudies(ProvisioningSession provisioningSession, C3PRUserGroupType group) {
		return personUserDao.getHasAccessToAllStudies(provisioningSession, group);
	}

}
