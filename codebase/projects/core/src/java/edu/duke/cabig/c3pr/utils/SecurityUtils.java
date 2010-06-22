package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.User;
import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.accesscontrol.AuthorizedUser;
import edu.duke.cabig.c3pr.accesscontrol.UserPrivilege;
import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.RoleTypes;
import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.dao.RolePrivilegeDao;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;

/**
 * The Class SecurityUtils.
 */
public class SecurityUtils {

	public static final String ACEGI_PREFIX = "ROLE_";
	
	/** The log. */
	private static Log log = LogFactory.getLog(SecurityUtils.class);
	
	public static boolean isSuperUser(Authentication authentication){
    	List<RoleTypes> allRoles = Arrays.asList(RoleTypes.values());
    	List<RoleTypes> roles = SecurityUtils.getRoleTypes();
    	if(allRoles.equals(roles)){
    		return true;
    	}
    	return false;
	}
	
	public static boolean isSuperUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	return isSuperUser(authentication);
	}
	
	/**
	 * Gets the user name from the authentication object.
	 * 
	 * 
	 * @return the user name
	 */
	public static String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return getUserName(authentication);
	}
	
	/**
	 * Gets the user name from the authentication object.
	 * 
	 * @param authentication the authentication
	 * 
	 * @return the user name
	 */
	public static String getUserName(Authentication authentication) {
		return ((User)authentication.getPrincipal()).getUsername();
	}
	
	/**
	 * Checks if user has any of the provided roles.
	 * 
	 * @param roleTypes the role types
	 * 
	 * @return true, if successful
	 */
	public static boolean hasRole(List<RoleTypes> roleTypes){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return hasRole(authentication, roleTypes);
	}
	
	/**
	 * Checks if user has any of the provided roles.
	 * 
	 * @param authentication the authentication
	 * @param roleTypes the role types
	 * 
	 * @return true, if successful
	 */
	public static boolean hasRole(Authentication authentication, List<RoleTypes> roleTypes){
		if(roleTypes == null){
			return false;
		}
		return CollectionUtils.containsAny(roleTypes, getRoleTypes(authentication));
	}
	
	public static List<RoleTypes> getRoleTypes(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return getRoleTypes(authentication);
	}
	
	public static List<RoleTypes> getRoleTypes(Authentication authentication){
		List<RoleTypes> roleTypes = new ArrayList<RoleTypes>();
		GrantedAuthority[] grantedAuthorities = authentication.getAuthorities();
		for(GrantedAuthority grantedAuthority : grantedAuthorities){
			roleTypes.add(RoleTypes.getByCode(grantedAuthority.getAuthority()));
		}
		return roleTypes;
	}
	
	/**
	 * Checks if user has any of the provided privilege.
	 * 
	 * @param authentication the authentication
	 * @param roleTypes the role types
	 * 
	 * @return true, if successful
	 */
	public static boolean hasPrivilege(List<UserPrivilegeType> privilegeTypes){
		if(privilegeTypes == null){
			return false;
		}
		List<UserPrivilege> privileges = new ArrayList<UserPrivilege>();
		for(UserPrivilegeType userPrivilegeType : privilegeTypes){
			privileges.add(new UserPrivilege(userPrivilegeType.getCode()));
		}
		
		return CollectionUtils.containsAny(privileges, getUserPrivileges());
	}
	
	/**
	 * Checks if user has any of the provided privilege.
	 * 
	 * @param authentication the authentication
	 * @param roleTypes the role types
	 * 
	 * @return true, if successful
	 */
	public static boolean hasPrivilege(UserPrivilegeType privilegeType){
		if(privilegeType == null){
			return false;
		}
		List<UserPrivilegeType> privilegeTypes = new ArrayList<UserPrivilegeType>();
		privilegeTypes.add(privilegeType);
		return hasPrivilege(privilegeTypes);
	}
	
	public static List<UserPrivilege> getUserPrivileges(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AuthorizedUser authorizedUser = (AuthorizedUser)authentication.getPrincipal();
		return authorizedUser.getUserPrivileges();
	}

	/**
	 * Gets the c3 pr user role types.
	 *
	 * @param authentication the authentication
	 * @return the c3 pr user role types
	 */
	public static List<C3PRUserGroupType> getC3PRUserRoleTypes(Authentication authentication){
		return getC3PRUserRoleTypes(authentication.getAuthorities());
	}
	
	/**
	 * Gets the c3 pr user role types.
	 *
	 * @param authentication the authentication
	 * @return the c3 pr user role types
	 */
	public static List<C3PRUserGroupType> getC3PRUserRoleTypes(GrantedAuthority[] grantedAuthorities){
		List<C3PRUserGroupType> c3prUserGroupTypes = new ArrayList<C3PRUserGroupType>();
		for(GrantedAuthority grantedAuthority : grantedAuthorities){
			c3prUserGroupTypes.add(C3PRUserGroupType.getByCode(grantedAuthority.getAuthority().substring(ACEGI_PREFIX.length())));
		}
		return c3prUserGroupTypes;
	}
	
	
	/**
	 * Checks for all site acsess.
	 * Get the provisioningSession from the user and the roles from the authentication object to 
	 * determine hasAllSiteAccess
	 *  User has all Site access if he isnt scoped by site or suiteRoleMembership.isAllSites() is true
	 *
	 * @param authentication the authentication
	 * @return true, if successful
	 */
	public static boolean hasAllSiteAccess(Authentication authentication){
		ProvisioningSession provisioningSession = ((AuthorizedUser)authentication.getPrincipal()).getProvisioningSession();
		List<C3PRUserGroupType> rolesList = SecurityUtils.getC3PRUserRoleTypes(authentication);
		SuiteRoleMembership suiteRoleMembership; 
		for(C3PRUserGroupType userRole: rolesList){
			suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(C3PRUserGroupType.getUnifiedSuiteRole(userRole));
			if(suiteRoleMembership.isAllSites() || !suiteRoleMembership.hasSiteScope()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks for all study acsess.
	 * Get the provisioningSession from the user and the roles from the authentication object to 
	 * determine hasAllSiteAccess
	 * User has all Study access if he isnt scoped by study or suiteRoleMembership.isAllStudies() is true
	 *
	 * @param authentication the authentication
	 * @return true, if successful
	 */
	public static boolean hasAllStudyAccess(Authentication authentication){
		ProvisioningSession provisioningSession = ((AuthorizedUser)authentication.getPrincipal()).getProvisioningSession();
		List<C3PRUserGroupType> rolesList = SecurityUtils.getC3PRUserRoleTypes(authentication);
		SuiteRoleMembership suiteRoleMembership; 
		for(C3PRUserGroupType userRole: rolesList){
			suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(C3PRUserGroupType.getUnifiedSuiteRole(userRole));
			if(suiteRoleMembership.isAllStudies() || !suiteRoleMembership.hasStudyScope()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Builds the user accessible organization ids list.
	 * Get the provisioningSession from the user and the roles from the authentication object to 
	 * build userAccessibleOrganizationIdsList(provided user doesn't have hasAllSiteAccess).
	 *
	 * @param authentication the authentication
	 * @return the list
	 */
	public static List<String> buildUserAccessibleOrganizationIdsList(Authentication authentication){
		ProvisioningSession provisioningSession = ((AuthorizedUser)authentication.getPrincipal()).getProvisioningSession();
		List<C3PRUserGroupType> rolesList = SecurityUtils.getC3PRUserRoleTypes(authentication);
		List<String> userAccessibleOrganizationIdsList = new ArrayList<String>();
		SuiteRoleMembership suiteRoleMembership; 
		for(C3PRUserGroupType userRole: rolesList){
			suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(C3PRUserGroupType.getUnifiedSuiteRole(userRole));
			if(suiteRoleMembership.isAllSites()){
				log.error("User has access to all sites. No point in building list");
			} else {
				//add NC010 from "HealthcareSite.NC010" to the userAccessibleOrganizationIdsList
				for(String siteId:suiteRoleMembership.getSiteIdentifiers()){
					userAccessibleOrganizationIdsList.add(siteId.substring(siteId.lastIndexOf(".") + 1));
				}
			}
		}
		return userAccessibleOrganizationIdsList;
	}

	/**
	 * Builds the user accessible organization ids list.
	 * Get the provisioningSession from the user and the roles from the authentication object to 
	 * build userAccessibleOrganizationIdsList(provided user doesn't have hasAllSiteAccess).
	 *
	 * @param authentication the authentication
	 * @return the list
	 */
	public static List<String> buildUserAccessibleStudyIdsList(Authentication authentication){
		ProvisioningSession provisioningSession = ((AuthorizedUser)authentication.getPrincipal()).getProvisioningSession();
		List<C3PRUserGroupType> rolesList = SecurityUtils.getC3PRUserRoleTypes(authentication);
		List<String> userAccessibleStudyIdsList = new ArrayList<String>();
		SuiteRoleMembership suiteRoleMembership;
		for(C3PRUserGroupType userRole: rolesList){
			suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(C3PRUserGroupType.getUnifiedSuiteRole(userRole));
			if(suiteRoleMembership.isAllSites()){
				log.error("User has access to all sites. No point in building list");
			} else {
				//add NC010 from "HealthcareSite.NC010" to the userAccessibleOrganizationIdsList
				for(String studyId:suiteRoleMembership.getStudyIdentifiers()){
					userAccessibleStudyIdsList.add(studyId.substring(studyId.lastIndexOf(".") + 1));
				}
			}
		}
		return userAccessibleStudyIdsList;
	}
	
	
	/**
	 * Check privilege, given roles set.
	 *
	 * @param rolesSet the roles set
	 * @param privilege the privilege
	 * @return true, if successful
	 */
	public static boolean checkPrivilegeGivenRoles(RolePrivilegeDao rolePrivilegeDao, List<C3PRUserGroupType> rolesList, String objectId, String privilege) {
		for(C3PRUserGroupType role: rolesList){
			if(rolePrivilegeDao.isValidRolePrivilege(objectId, privilege, role.getCode())){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Gets the role types that can access staff.
	 *
	 * @return the role types that can access staff
	 */
	public static List<RoleTypes> getRoleTypesFromCodeList(List<String> codeList) {
		List<RoleTypes> roleTypes = new ArrayList<RoleTypes>();
		for(String role: codeList){
			roleTypes.add(RoleTypes.getByCode(role));
		}
		return roleTypes;
	}

}
