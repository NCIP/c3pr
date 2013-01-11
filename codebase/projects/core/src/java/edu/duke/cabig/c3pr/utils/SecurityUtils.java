/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.User;
import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.duke.cabig.c3pr.accesscontrol.AuthorizedUser;
import edu.duke.cabig.c3pr.accesscontrol.UserPrivilege;
import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.RoleTypes;
import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.RolePrivilege;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSessionFactory;
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
	
	public static final SuiteRole PERSON_AND_ORGANIZATION_INFORMATION_MANAGER = SuiteRole.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER;
	
	private ApplicationContext applicationContext;
	
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
    	List<RoleTypes> nonC3PRRoles = SecurityUtils.getNonC3PRRoles();
    	for(RoleTypes role : allRoles){
    		if(nonC3PRRoles.contains(role)){
    			continue;
    		}
	    	if(!roles.contains(role)){
	    		return false;
	    	}else if(!hasAllSiteAccess(role)){
	    		return false ;
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
			SuiteRole suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(userRole);
			SuiteRoleMembership suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
			if(suiteRole.isSiteScoped() && !suiteRoleMembership.isAllSites()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks for all site access.
	 *
	 * @param roleType the role type
	 * @return true, if successful
	 */
	public static boolean hasAllSiteAccess(RoleTypes roleType){
		return hasAllSiteAccess(C3PRUserGroupType.valueOf(roleType.getName()));
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
		Set<C3PRUserGroupType> userGroupTypes = getRolesForLoggedInUser(UserPrivilegeType.valueOf(userPrivilegeString));
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
	 * Checks for all study access.
	 * Get the provisioningSession from the user and the roles from the authentication object to
	 * determine hasAllSiteAccess
	 * User has all Site access if he isnt scoped by site or suiteRoleMembership.isAllSites() is true
	 * 
	 * @return true, if successful
	 */
	public static boolean hasAllStudyAccess(String userPrivilegeString){
		Set<C3PRUserGroupType> userGroupTypes = getRolesForLoggedInUser(UserPrivilegeType.valueOf(userPrivilegeString));
		for(C3PRUserGroupType userGroupType : userGroupTypes){
			if(hasAllStudyAccess(userGroupType)){
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
	 * @return the list
	 */
	public static List<String> buildAccessibleOrganizationIdsListForLoggedInUser(C3PRUserGroupType userRole){
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
					userAccessibleOrganizationIdsList.add(siteId.substring(siteId.indexOf(".") + 1));
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
	public static List<String> buildAccessibleStudyIdsListForLoggedInUser(C3PRUserGroupType userRole){
		List<String> userAccessibleStudyIdsList = new ArrayList<String>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null){
			ProvisioningSession provisioningSession = ((AuthorizedUser)authentication.getPrincipal()).getProvisioningSession();
			SuiteRoleMembership suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(C3PRUserGroupType.getUnifiedSuiteRole(userRole));
			
			//add NC010 from "HealthcareSite.NC010" to the userAccessibleOrganizationIdsList
			for(String studyId:suiteRoleMembership.getStudyIdentifiers()){
				userAccessibleStudyIdsList.add(studyId.substring(studyId.indexOf(".") + 1));
			}
		}
		return userAccessibleStudyIdsList;
	}
	
	
	/**
	 * Gets all the user's roles that have the privilege that is passed in.
	 *
	 * @param privlegeType the privilege type
	 * @return the user roles
	 */
	public static Set<C3PRUserGroupType> getRolesForLoggedInUser(UserPrivilegeType privlegeType){
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
		if(suiteRole.isScoped() && !suiteRole.equals(PERSON_AND_ORGANIZATION_INFORMATION_MANAGER)){
			return false;
		}
		return true;
	}
	
	
	/**
	 * Gets the logged in research staff.
	 * 
	 * @return the logged in research staff
	 */
	public static PersonUser getLoggedInResearchStaff(){
		return ((AuthorizedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPersonUser();
	}
	
	/**
	 * Gets the Suite roles which have no privileges in C3PR..
	 *
	 * @return the non c3 pr roles
	 */
	public static List<RoleTypes> getNonC3PRRoles(){
		List<RoleTypes> nonC3PRRoles = new ArrayList<RoleTypes>();

		nonC3PRRoles.add(RoleTypes.AE_EXPEDITED_REPORT_REVIEWER);
		nonC3PRRoles.add(RoleTypes.AE_REPORTER);
		nonC3PRRoles.add(RoleTypes.AE_RULE_AND_REPORT_MANAGER);
		nonC3PRRoles.add(RoleTypes.AE_STUDY_DATA_REVIEWER);
		nonC3PRRoles.add(RoleTypes.LAB_DATA_USER);
		nonC3PRRoles.add(RoleTypes.LAB_IMPACT_CALENDAR_NOTIFIER);
		nonC3PRRoles.add(RoleTypes.STUDY_CALENDAR_TEMPLATE_BUILDER);
		nonC3PRRoles.add(RoleTypes.STUDY_SUBJECT_CALENDAR_MANAGER);
		
		return nonC3PRRoles;
	}
	
	/**
	 * Gets the study scoped roles.
	 *
	 * @return the study scoped roles
	 */
	public static List<String> getStudyScopedRoles(){
		List<String> studyScopedRoles = new ArrayList<String>();

		studyScopedRoles.add(C3PRUserGroupType.REGISTRAR.getCode());
		studyScopedRoles.add(C3PRUserGroupType.DATA_ANALYST.getCode());
		studyScopedRoles.add(C3PRUserGroupType.DATA_READER.getCode());
		
		studyScopedRoles.add(C3PRUserGroupType.AE_STUDY_DATA_REVIEWER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.AE_EXPEDITED_REPORT_REVIEWER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.AE_REPORTER.getCode());
		
		studyScopedRoles.add(C3PRUserGroupType.LAB_DATA_USER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.LAB_IMPACT_CALENDAR_NOTIFIER.getCode());
		
		studyScopedRoles.add(C3PRUserGroupType.STUDY_CALENDAR_TEMPLATE_BUILDER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.STUDY_SUBJECT_CALENDAR_MANAGER.getCode());
		
		return studyScopedRoles;
	}
	
	
	/**
	 * Gets the site scoped roles.
	 *
	 * @return the site scoped roles code as string and not the C3PRUserGroupType as it is used from the UI
	 */
	public static List<String> getSiteScopedRoles(){
		List<String> studyScopedRoles = new ArrayList<String>();

		//studyScopedRoles.add(C3PRUserGroupType.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.USER_ADMINISTRATOR.getCode());
		
		studyScopedRoles.add(C3PRUserGroupType.STUDY_CREATOR.getCode());
		studyScopedRoles.add(C3PRUserGroupType.SUPPLEMENTAL_STUDY_INFORMATION_MANAGER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.STUDY_TEAM_ADMINISTRATOR.getCode());
		studyScopedRoles.add(C3PRUserGroupType.STUDY_SITE_PARTICIPATION_ADMINISTRATOR.getCode());
		
		studyScopedRoles.add(C3PRUserGroupType.STUDY_CALENDAR_TEMPLATE_BUILDER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.STUDY_QA_MANAGER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.REGISTRATION_QA_MANAGER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.SUBJECT_MANAGER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.STUDY_SUBJECT_CALENDAR_MANAGER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.REGISTRAR.getCode());
		studyScopedRoles.add(C3PRUserGroupType.AE_REPORTER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.AE_EXPEDITED_REPORT_REVIEWER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.AE_STUDY_DATA_REVIEWER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.LAB_IMPACT_CALENDAR_NOTIFIER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.LAB_DATA_USER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.DATA_READER.getCode());
		studyScopedRoles.add(C3PRUserGroupType.DATA_ANALYST.getCode());
		
		return studyScopedRoles;
	}
	
	/**
	 * Gets the list of organizations, the logged in user has access to with the privilege passed in.
	 * This list is populated during login and saved in the AuthorizedUSer object.
	 *
	 * @param privlegeType the privlege type
	 * @param servletContext the servlet context
	 * @return the logged in users organizations
	 */
	public static List<HealthcareSite> getLoggedInUsersOrganizations(String privlegeType, ServletContext servletContext){
		List hcsList = new ArrayList<HealthcareSite>();
		if(StringUtils.isBlank(privlegeType)){
			return hcsList;
		}
		Set<C3PRUserGroupType> groupSet = getRolesForLoggedInUser(UserPrivilegeType.valueOf(privlegeType.trim()));
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		HealthcareSiteDao healthcareSiteDao = (HealthcareSiteDao)context.getBean("healthcareSiteDao");
		
		Map<String, List<String>> roleBasedOrganizationsMap = ((AuthorizedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRoleBasedOrganizationsMap();
		List<String> organizationIdList = new ArrayList<String>();
	    for(C3PRUserGroupType c3prUserGroupType:groupSet){
	    	if(roleBasedOrganizationsMap.containsKey(c3prUserGroupType.getCode())){
	    		organizationIdList = roleBasedOrganizationsMap.get(c3prUserGroupType.getCode());
	    		HealthcareSite healthcareSite;
	    		for(String hcsId: organizationIdList){
	     			healthcareSite = healthcareSiteDao.getByCtepCodeFromLocal(hcsId);
	     			if(healthcareSite != null && ! hcsList.contains(healthcareSite)){
	     				hcsList.add(healthcareSite);
	     			}
	     		}
	    	}
	    }
        return hcsList;
	}
	
	
	/**
	 * Checks if the user has been deactivated based on the users endDate in csm_user.
	 *
	 * @param userEndDate the user end date from csm_user
	 * @return true, if is user deactivated
	 */
	public static boolean isUserDeactivated(Date userEndDate){
		if(userEndDate != null && DateUtil.compareDate(userEndDate,Calendar.getInstance().getTime()) <= 0){
			return true;
		}
		return false;
	}
	

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}


