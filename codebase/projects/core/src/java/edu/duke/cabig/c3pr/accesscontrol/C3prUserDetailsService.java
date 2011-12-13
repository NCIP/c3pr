package edu.duke.cabig.c3pr.accesscontrol;


import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.PersonUserType;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.dao.RolePrivilegeDao;
import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.LocalPersonUser;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.RolePrivilege;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import gov.nih.nci.cabig.ctms.acegi.csm.authorization.CSMUserDetailsService;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSessionFactory;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acegisecurity.LockedException;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	
	private PersonUserDao personUserDao;
	
	private UserDao userDao;
	
	private static final Log logger = LogFactory.getLog(C3prUserDetailsService.class);
	
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.security.acegi.csm.authorization.CSMUserDetailsService#loadUserByUsername(java.lang.String)
	 * consider replacing UserDeactivatedException with AccountStatusException or something pre-existing in acegi.
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
					throws LockedException, UsernameNotFoundException, DataAccessException {

		AuthorizedUser authorizedUser = null;
		UserDetails userDetails = super.loadUserByUsername(username);
		
		gov.nih.nci.security.authorization.domainobjects.User csmUser = 
			getCsmUserProvisioningManager().getUser(userDetails.getUsername());
		if(SecurityUtils.isUserDeactivated(csmUser.getEndDate())){
			SimpleDateFormat sfd = new SimpleDateFormat("EEE, dd-MMM-yyyy");
			logger.error("User: "+userDetails.getUsername() + " has been deactivated as of " + sfd.format(csmUser.getEndDate()));
			//Throwing Exception for de-activated users
			throw new LockedException("User: "+userDetails.getUsername() + " has been deactivated as of " + sfd.format(csmUser.getEndDate()));
		} else {
			ProvisioningSession provisioningSession = provisioningSessionFactory.createSession(csmUser.getUserId());
			List<C3PRUserGroupType> c3prUserGroupTypes = SecurityUtils.getC3PRUserRoleTypes(userDetails.getAuthorities());
			
			authorizedUser= new AuthorizedUser(userDetails.getUsername(), userDetails.getPassword(), true, true, true, true, 
					userDetails.getAuthorities(), provisioningSession, getAllRolePrivileges(c3prUserGroupTypes), 
					getPersonUser(csmUser), buildRoleBasedOrganizationsMap(c3prUserGroupTypes, provisioningSession));
		}
		return authorizedUser;
	}
	
	/**
	 * Gets the research staff. 
	 * Creates one if the staff does not exist. This is for the suite use case of dynamic provisioning.
	 * NOTE: Throws a runtime exception if unable to provision.
	 * Since dynamic user provisioning failed, we show the error on the ui instead of working around it.
	 * @param userId the user id
	 * @return the research staff
	 */
	private PersonUser getPersonUser(gov.nih.nci.security.authorization.domainobjects.User csmUser) {
		edu.duke.cabig.c3pr.domain.C3PRUser c3prUser = userDao.getByLoginId(csmUser.getUserId());
		if(c3prUser == null){
			PersonUser researchStaff = populatePersonUser(csmUser);
			try {
				logger.debug("Attempting to dynamically provision the CSM user with user id: "+ csmUser.getUserId() +" in C3PR as staff.");
				setAuditInfo();
				personUserDao.createResearchStaff(researchStaff);
			} catch(Exception e){
				logger.error("Unable to proceed as dynamic provisioning failed for user: "+csmUser.getUserId());
				logger.error("Check user details in csm_user for invalid data.");
				logger.error(e.getMessage());
				throw new RuntimeException();
			}
			return researchStaff;
		}
		return (PersonUser) c3prUser;
	}

	
	/**
	 * Populate person user.
	 *
	 * @param csmUser the csm user
	 * @return the person user
	 */
	private PersonUser populatePersonUser(User csmUser) {
		PersonUser personUser = new LocalPersonUser(PersonUserType.USER);
		personUser.setFirstName(csmUser.getFirstName());
		personUser.setLastName(csmUser.getLastName());
		personUser.setLoginId(csmUser.getUserId().toString());
		personUser.setEmail(csmUser.getEmailId());
//		researchStaff.setPhone(csmUser.getPhoneNumber());
//		personUser.setAssignedIdentifier(UUID.randomUUID().toString());
		return personUser;
	}

	/**
	 * Gets the all role privileges.
	 *
	 * @param grantedAuthorities the granted authorities
	 * @return the all role privileges
	 */
	private RolePrivilege[] getAllRolePrivileges(List<C3PRUserGroupType> c3prUserGroupTypes) {
		List<RolePrivilege> rolePrivilegeList = new ArrayList<RolePrivilege>();
		String roleName;
		for(C3PRUserGroupType group: c3prUserGroupTypes){
			roleName = group.getCode();
			rolePrivilegeList.addAll(rolePrivilegeDao.getAllPrivilegesForRole(roleName));
		}
		return rolePrivilegeList.toArray(new RolePrivilege[rolePrivilegeList.size()]);
	}
	
	
	/**
	 * Builds the role based organizations map. Basically a map of all organizations which a user can access with a particular role.
	 *
	 * @param c3prUserGroupTypes the c3pr user group types
	 * @param provisioningSession the provisioning session
	 * @return the map
	 */
	private Map<String, List<String>> buildRoleBasedOrganizationsMap(List<C3PRUserGroupType> c3prUserGroupTypes, ProvisioningSession provisioningSession){
		Map<String, List<String>> roleBasedOrganizationsMap = new HashMap<String, List<String>>();
		List<String> organizationIdList = new ArrayList<String>();
	   
		for(C3PRUserGroupType c3prUserGroupType:c3prUserGroupTypes){
	    	if(!SecurityUtils.isGlobalRole(c3prUserGroupType)){
	        	
	            SuiteRole suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(c3prUserGroupType);
	            SuiteRoleMembership suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
	    	    //add all site identifiers to a set
	    		if(suiteRole.isSiteScoped() && !suiteRoleMembership.isAllSites()){
	    			organizationIdList.addAll(suiteRoleMembership.getSiteIdentifiers());
	    			roleBasedOrganizationsMap.put(c3prUserGroupType.getCode(), organizationIdList);
	    		}
	    	}
	    }
        return roleBasedOrganizationsMap;
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

	public PersonUserDao getPersonUserDao() {
		return personUserDao;
	}

	public void setPersonUserDao(PersonUserDao personUserDao) {
		this.personUserDao = personUserDao;
	}
}
