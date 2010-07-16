package edu.duke.cabig.c3pr.accesscontrol;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.dao.RolePrivilegeDao;
import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.LocalResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RolePrivilege;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSessionFactory;
import gov.nih.nci.security.acegi.csm.authorization.CSMUserDetailsService;
import gov.nih.nci.security.authorization.domainobjects.User;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.uuid.UUID;
import org.springframework.dao.DataAccessException;

/**
 * The Class C3prUserDetailsService.
 * Overrides the loadUserByUsername() from CSMUserDetailsService so as to return a AuthorizedUser object.
 * Populates the protectionGroupRoleContextList in the AuthorizedUser so that all filterers and checkers can
 * utilize it instead of going to CSM.
 * 
 * @author Vinay G
 */
public class C3prUserDetailsService extends CSMUserDetailsService{
	
	private ProvisioningSessionFactory provisioningSessionFactory;
	
	private RolePrivilegeDao rolePrivilegeDao;
	
	private ResearchStaffDao researchStaffDao;
	
	private UserDao userDao;
	
	private static final Log logger = LogFactory.getLog(C3prUserDetailsService.class);
	
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.security.acegi.csm.authorization.CSMUserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
					throws UsernameNotFoundException, DataAccessException {

		UserDetails userDetails = super.loadUserByUsername(username);
		
		gov.nih.nci.security.authorization.domainobjects.User csmUser = 
			getCsmUserProvisioningManager().getUser(userDetails.getUsername());
		AuthorizedUser authorizedUser= new AuthorizedUser(userDetails.getUsername(), userDetails.getPassword(), true, true, true, true, 
						userDetails.getAuthorities(), provisioningSessionFactory. createSession(csmUser.getUserId()), getAllRolePrivileges(userDetails.getAuthorities()), 
						getResearchStaff(csmUser));
		
		return authorizedUser;
	}
	
	/**
	 * Gets the research staff. 
	 * Creates one if the staff does not exist. This is for the suite use case of dynamic provisioning.
	 *
	 * @param userId the user id
	 * @return the research staff
	 */
	private ResearchStaff getResearchStaff(gov.nih.nci.security.authorization.domainobjects.User csmUser) {
		edu.duke.cabig.c3pr.domain.User c3prUser = userDao.getByLoginId(csmUser.getUserId());
		if(c3prUser == null){
			ResearchStaff researchStaff = populateResearchStaff(csmUser);
			try {
				logger.debug("Attempting to dynamically provision the CSM user with user id: "+ csmUser.getUserId() +" in C3PR as staff.");
				setAuditInfo();
				researchStaffDao.createResearchStaff(researchStaff);
			} catch (C3PRBaseException e) {
				logger.error("Unable to dynamically provision staff for user: "+csmUser.getUserId());
				logger.error(e.getMessage());
			} catch(Exception e){
				logger.error(e.getMessage());
			}
			return researchStaff;
		}
		return (ResearchStaff) c3prUser;
	}

	/**
	 * Populate research staff.
	 *
	 * @param csmUser the csm user
	 * @return the research staff
	 */
	private ResearchStaff populateResearchStaff(User csmUser) {
		ResearchStaff researchStaff = new LocalResearchStaff();
		researchStaff.setFirstName(csmUser.getFirstName());
		researchStaff.setLastName(csmUser.getLastName());
		researchStaff.setLoginId(csmUser.getUserId().toString());
//		researchStaff.setEmail(csmUser.getEmailId());
//		researchStaff.setPhone(csmUser.getPhoneNumber());
		researchStaff.setAssignedIdentifier(UUID.randomUUID().toString());
		return researchStaff;
	}

	/**
	 * Gets the all role privileges.
	 *
	 * @param grantedAuthorities the granted authorities
	 * @return the all role privileges
	 */
	private RolePrivilege[] getAllRolePrivileges(GrantedAuthority[] grantedAuthorities) {
		List<C3PRUserGroupType> c3prUserGroupTypes = SecurityUtils.getC3PRUserRoleTypes(grantedAuthorities);
		List<RolePrivilege> rolePrivilegeList = new ArrayList<RolePrivilege>();
		String roleName;
		for(C3PRUserGroupType group: c3prUserGroupTypes){
			roleName = group.getCode();
			rolePrivilegeList.addAll(rolePrivilegeDao.getAllPrivilegesForRole(roleName));
		}
		return rolePrivilegeList.toArray(new RolePrivilege[rolePrivilegeList.size()]);
	}

	public ProvisioningSessionFactory getProvisioningSessionFactory() {
		return provisioningSessionFactory;
	}

	public void setProvisioningSessionFactory(
			ProvisioningSessionFactory provisioningSessionFactory) {
		this.provisioningSessionFactory = provisioningSessionFactory;
	}

	public RolePrivilegeDao getRolePrivilegeDao() {
		return rolePrivilegeDao;
	}

	public void setRolePrivilegeDao(RolePrivilegeDao rolePrivilegeDao) {
		this.rolePrivilegeDao = rolePrivilegeDao;
	}

	public ResearchStaffDao getResearchStaffDao() {
		return researchStaffDao;
	}

	public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
		this.researchStaffDao = researchStaffDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setAuditInfo(){
    	gov.nih.nci.cabig.ctms.audit.DataAuditInfo.setLocal(new gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo(
        		"C3PR Admin", "C3PR dynamic provisioning of suite user", new Date(), "C3PR dynamic provisioning of suite user"));
    }
}
