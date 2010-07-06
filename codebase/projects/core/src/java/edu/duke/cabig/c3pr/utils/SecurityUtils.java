package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RolePrivilege;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;

/**
 * The Class SecurityUtils.
 */
public class SecurityUtils {

	/** The Constant ACEGI_PREFIX. */
	public static final String ACEGI_PREFIX = "ROLE_";
	
	/** The log. */
	private static Log log = LogFactory.getLog(SecurityUtils.class);
	
	public static final SuiteRole GLOBAL_ROLE = SuiteRole.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER;
	
	/**
	 * Checks if is super user.
	 * 
	 * @param authentication the authentication
	 * 
	 * @return true, if is super user
	 */
	public static boolean isSuperUser(Authentication authentication){
    	List<RoleTypes> allRoles = Arrays.asList(RoleTypes.values());
    	List<RoleTypes> roles = SecurityUtils.getRoleTypes(authentication);
    	for(RoleTypes role : allRoles){
	    	if(!roles.contains(role)){
	    		return false;
	    	}
		}
    	return true;
	}
	
	/**
	 * Checks if is super user.
	 * 
	 * @return true, if is super user
	 */
	public static boolean isSuperUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	return isSuperUser(authentication);
	}
	
	/**
	 * Gets the user name from the authentication object.
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
		if(authentication != null){
			return ((User)authentication.getPrincipal()).getUsername();
		}
		return null ;
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
	
	/**
	 * Gets the role types.
	 * 
	 * @return the role types
	 */
	public static List<RoleTypes> getRoleTypes(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return getRoleTypes(authentication);
	}
	
	/**
	 * Gets the role types.
	 * 
	 * @param authentication the authentication
	 * 
	 * @return the role types
	 */
	public static List<RoleTypes> getRoleTypes(Authentication authentication){
		List<RoleTypes> roleTypes = new ArrayList<RoleTypes>();
		if(authentication != null){
			GrantedAuthority[] grantedAuthorities = authentication.getAuthorities();
			for(GrantedAuthority grantedAuthority : grantedAuthorities){
				RoleTypes role = RoleTypes.getByCode(grantedAuthority.getAuthority());
				if(role!=null){
					roleTypes.add(role);
				}
			}
		}
		return roleTypes;
	}
	
	/**
	 * Checks for privilege.
	 * 
	 * @param userPrivilege the user privilege
	 * 
	 * @return true, if successful
	 */
	public static boolean hasPrivilege(Authentication authentication, UserPrivilege userPrivilege){
		return getUserPrivileges(authentication).contains(userPrivilege);
	}
	
	/**
	 * Checks if user has any of the provided privileges.
	 * 
	 * @param privilegeTypes the privilege types
	 * 
	 * @return true, if successful
	 */
	public static boolean hasAnyPrivilege(List<UserPrivilegeType> privilegeTypes){
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
	 * Checks if user has all of the provided privileges.
	 * 
	 * @param privilegeTypes the privilege types
	 * 
	 * @return true, if successful
	 */
	public static boolean hasAllPrivilege(List<UserPrivilegeType> privilegeTypes){
		if(privilegeTypes == null){
			return false;
		}
		List<UserPrivilege> privileges = new ArrayList<UserPrivilege>();
		for(UserPrivilegeType userPrivilegeType : privilegeTypes){
			privileges.add(new UserPrivilege(userPrivilegeType.getCode()));
		}
		
		return getUserPrivileges().containsAll(privileges);
	}
	
	/**
	 * Checks if user has the provided privilege.
	 * 
	 * @param privilegeType the privilege type
	 * 
	 * @return true, if successful
	 */
	public static boolean hasPrivilege(UserPrivilegeType privilegeType){
		if(privilegeType == null){
			return false;
		}
		List<UserPrivilegeType> privilegeTypes = new ArrayList<UserPrivilegeType>();
		privilegeTypes.add(privilegeType);
		return hasAnyPrivilege(privilegeTypes);
	}
	
	/**
	 * Gets the user privileges.
	 * 
	 * @return the user privileges
	 */
	public static List<UserPrivilege> getUserPrivileges(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return getUserPrivileges(authentication);
	}
	
	/**
	 * Gets the user privileges.
	 * 
	 * @return the user privileges
	 */
	public static List<UserPrivilege> getUserPrivileges(Authentication authentication){
		if(authentication != null){
			AuthorizedUser authorizedUser = (AuthorizedUser)authentication.getPrincipal();
			return authorizedUser.getUserPrivileges();
		}else {
			return new ArrayList<UserPrivilege>();
		}
	}

	
	/**
	 * Gets the c3 pr user role types.
	 * 
	 * @param grantedAuthorities the granted authorities
	 * 
	 * @return the c3 pr user role types
	 */
	public static List<C3PRUserGroupType> getC3PRUserRoleTypes(GrantedAuthority[] grantedAuthorities){
		List<C3PRUserGroupType> c3prUserGroupTypes = new ArrayList<C3PRUserGroupType>();
		for(GrantedAuthority grantedAuthority : grantedAuthorities){
			final String code = grantedAuthority.getAuthority().substring(ACEGI_PREFIX.length());
			final C3PRUserGroupType groupType = C3PRUserGroupType.getByCode(code);
			if (groupType!=null) {
				c3prUserGroupTypes.add(groupType);
			} else {
				log.warn("Unable to resolve C3PRUserGroupType by code: "+code);
			}
		}
		return c3prUserGroupTypes;
	}
	
	
	/**
	 * Checks for all site access.
	 * Get the provisioningSession from the user and the roles from the authentication object to
	 * determine hasAllSiteAccess
	 * User has all Site access if he isnt scoped by site or suiteRoleMembership.isAllSites() is true
	 * 
	 * @return true, if successful
	 */
	public static boolean hasAllSiteAccess(C3PRUserGroupType userRole){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null){
			ProvisioningSession provisioningSession = ((AuthorizedUser)authentication.getPrincipal()).getProvisioningSession();
			SuiteRoleMembership suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(C3PRUserGroupType.getUnifiedSuiteRole(userRole));
			if(suiteRoleMembership.isAllSites() || !suiteRoleMembership.hasSiteScope()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks for all site access.
	 * Get the provisioningSession from the user and the roles from the authentication object to
	 * determine hasAllSiteAccess
	 * User has all Site access if he isnt scoped by site or suiteRoleMembership.isAllSites() is true
	 * 
	 * @return true, if successful
	 */
	public static boolean hasAllSiteAccess(String userPrivilegeString){
		Set<C3PRUserGroupType> userGroupTypes = getUserRoles(UserPrivilegeType.valueOf(userPrivilegeString));
		for(C3PRUserGroupType userGroupType : userGroupTypes){
			if(hasAllSiteAccess(userGroupType)){
				return true;
			}
		}
		return false;
	}

	
	/**
	 * Checks for all study access.
	 * Get the provisioningSession from the user and the roles from the authentication object to
	 * determine hasAllSiteAccess
	 * User has all Study access if he isnt scoped by study or suiteRoleMembership.isAllStudies() is true
	 * 
	 * @return true, if successful
	 */
	public static boolean hasAllStudyAccess(C3PRUserGroupType userRole){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null){	
			ProvisioningSession provisioningSession = ((AuthorizedUser)authentication.getPrincipal()).getProvisioningSession();
			SuiteRoleMembership suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(C3PRUserGroupType.getUnifiedSuiteRole(userRole));
			SuiteRole suiteRole = suiteRoleMembership.getRole();
			if(suiteRole.isStudyScoped() && !suiteRoleMembership.isAllStudies()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Builds the user accessible organization ids list.
	 * Get the provisioningSession from the user and the roles from the authentication object to
	 * build userAccessibleOrganizationIdsList(provided user doesn't have hasAllSiteAccess).
	 * 
	 * @return the list
	 */
	public static List<String> buildUserAccessibleOrganizationIdsList(C3PRUserGroupType userRole){
		List<String> userAccessibleOrganizationIdsList = new ArrayList<String>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null){
			ProvisioningSession provisioningSession = ((AuthorizedUser)authentication.getPrincipal()).getProvisioningSession();
			SuiteRoleMembership suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(C3PRUserGroupType.getUnifiedSuiteRole(userRole));
			if(suiteRoleMembership.isAllSites()){
				log.error("User has access to all sites. No point in building list");
			} else {
				//e.g: add NC010 from "HealthcareSite.NC010" to the userAccessibleOrganizationIdsList
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
	 * @return the list
	 */
	public static List<String> buildUserAccessibleStudyIdsList(C3PRUserGroupType userRole){
		List<String> userAccessibleStudyIdsList = new ArrayList<String>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null){
			ProvisioningSession provisioningSession = ((AuthorizedUser)authentication.getPrincipal()).getProvisioningSession();
			SuiteRoleMembership suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(C3PRUserGroupType.getUnifiedSuiteRole(userRole));
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
	 * Gets all the user's roles that have the privilege that is passed in.
	 *
	 * @param privlegeType the privlege type
	 * @return the user roles
	 */
	public static Set<C3PRUserGroupType> getUserRoles(UserPrivilegeType privlegeType){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Set<C3PRUserGroupType> rolesList = new HashSet<C3PRUserGroupType>();
		if(authentication != null){
			List<RolePrivilege> rpList = ((AuthorizedUser)authentication.getPrincipal()).getRolePrivileges(privlegeType);
			for(RolePrivilege rp: rpList){
				rolesList.add(C3PRUserGroupType.getByCode(rp.getRoleName()));
			}
		}
		return rolesList;
	}
	
	
	/**
	 * Checks if is global role.
	 *
	 * @param groupType the group type
	 * @return true, if is global role
	 */
	public static boolean isGlobalRole(C3PRUserGroupType groupType){
		SuiteRole suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(groupType);
		if(suiteRole.isScoped() && !suiteRole.equals(GLOBAL_ROLE)){
			return false;
		}
		return true;
	}
	
	
	/**
	 * Gets the logged in research staff.
	 * 
	 * @return the logged in research staff
	 */
	public static ResearchStaff getLoggedInResearchStaff(){
		return ((AuthorizedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getResearchStaff();
	}

}


