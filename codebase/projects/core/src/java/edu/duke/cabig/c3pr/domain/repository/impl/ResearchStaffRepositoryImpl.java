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
	
	public ResearchStaff createCSMUser(ResearchStaff researchStaff, String username, boolean hasAccessToAllSites) throws C3PRBaseException {
		return researchStaffDao.createCSMUser(researchStaff, username, hasAccessToAllSites);
	}
	
	public ResearchStaff createCSMUserAndAssignRoles(ResearchStaff researchStaff, String username, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap,
			boolean hasAccessToAllSites) throws C3PRBaseException {
		return researchStaffDao.createCSMUserAndAssignRoles(researchStaff, username, associationMap, hasAccessToAllSites);
	}
	
	public ResearchStaff createOrModifyResearchStaff(ResearchStaff researchStaff, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap,
			boolean hasAccessToAllSites) throws C3PRBaseException {
		return researchStaffDao.createOrModifyResearchStaff(researchStaff, associationMap, hasAccessToAllSites);
	}
	public ResearchStaff createResearchStaff(ResearchStaff researchStaff) throws C3PRBaseException{
		return researchStaffDao.createResearchStaff(researchStaff);
	}
	public ResearchStaff createResearchStaffWithCSMUser(ResearchStaff researchStaff, String username, boolean hasAccessToAllSites) throws C3PRBaseException{
		return researchStaffDao.createResearchStaffWithCSMUser(researchStaff , username, hasAccessToAllSites);
	}
	public ResearchStaff createResearchStaffWithCSMUserAndAssignRoles(ResearchStaff researchStaff, String username, 
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap, boolean hasAccessToAllSites) throws C3PRBaseException{
		return researchStaffDao.createResearchStaffWithCSMUserAndAssignRoles(researchStaff, username , associationMap, hasAccessToAllSites);
	}
	public ResearchStaff createSuperUser(ResearchStaff researchStaff, String username, 
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap) throws C3PRBaseException{
		return researchStaffDao.createSuperUser(researchStaff,  username , associationMap);
	}
	
	public boolean getHasAccessToAllSites(User csmUser) {
		return false;
	}
	public ResearchStaff createResearchStaff(ResearchStaff researchStaff, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap)  throws C3PRBaseException {
		return researchStaffDao.createResearchStaff(researchStaff, associationMap);
	}
	

}
