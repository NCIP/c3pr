package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.User;
import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.accesscontrol.AuthorizedUser;
import edu.duke.cabig.c3pr.accesscontrol.UserPrivilege;
import edu.duke.cabig.c3pr.constants.RoleTypes;
import edu.duke.cabig.c3pr.constants.UserPrivilegeType;

public class SecurityUtilsTest extends AbstractTestCase {

	Authentication authentication;
	
	List<RoleTypes> roleTypes;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		authentication = registerMockFor(Authentication.class);
		roleTypes = new ArrayList<RoleTypes>();
	}
	
	public void testIsSuperUserTrue(){
		GrantedAuthority[] grantedAuthorities = getGrantedAuthorities(23);
		EasyMock.expect(authentication.getAuthorities()).andReturn(grantedAuthorities);
		EasyMock.expect(grantedAuthorities[0].getAuthority()).andReturn(RoleTypes.REGISTRAR.getCode());
		EasyMock.expect(grantedAuthorities[1].getAuthority()).andReturn(RoleTypes.BUSINESS_ADMINISTRATOR.getCode());
		EasyMock.expect(grantedAuthorities[2].getAuthority()).andReturn(RoleTypes.DATA_ANALYST.getCode());
		EasyMock.expect(grantedAuthorities[3].getAuthority()).andReturn(RoleTypes.DATA_IMPORTER.getCode());
		EasyMock.expect(grantedAuthorities[4].getAuthority()).andReturn(RoleTypes.DATA_READER.getCode());
		EasyMock.expect(grantedAuthorities[5].getAuthority()).andReturn(RoleTypes.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER.getCode());
		EasyMock.expect(grantedAuthorities[6].getAuthority()).andReturn(RoleTypes.REGISTRATION_QA_MANAGER.getCode());
		EasyMock.expect(grantedAuthorities[7].getAuthority()).andReturn(RoleTypes.STUDY_CREATOR.getCode());
		EasyMock.expect(grantedAuthorities[8].getAuthority()).andReturn(RoleTypes.STUDY_QA_MANAGER.getCode());
		EasyMock.expect(grantedAuthorities[9].getAuthority()).andReturn(RoleTypes.STUDY_SITE_PARTICIPATION_ADMINISTRATOR.getCode());
		EasyMock.expect(grantedAuthorities[10].getAuthority()).andReturn(RoleTypes.STUDY_TEAM_ADMINISTRATOR.getCode());
		EasyMock.expect(grantedAuthorities[11].getAuthority()).andReturn(RoleTypes.SUBJECT_MANAGER.getCode());
		EasyMock.expect(grantedAuthorities[12].getAuthority()).andReturn(RoleTypes.SYSTEM_ADMINISTRATOR.getCode());
		EasyMock.expect(grantedAuthorities[13].getAuthority()).andReturn(RoleTypes.SUPPLEMENTAL_STUDY_INFORMATION_MANAGER.getCode());
		EasyMock.expect(grantedAuthorities[14].getAuthority()).andReturn(RoleTypes.USER_ADMINISTRATOR.getCode());
		EasyMock.expect(grantedAuthorities[15].getAuthority()).andReturn(RoleTypes.AE_RULE_AND_REPORT_MANAGER.getCode());
		EasyMock.expect(grantedAuthorities[16].getAuthority()).andReturn(RoleTypes.STUDY_CALENDAR_TEMPLATE_BUILDER.getCode());
		EasyMock.expect(grantedAuthorities[17].getAuthority()).andReturn(RoleTypes.STUDY_SUBJECT_CALENDAR_MANAGER.getCode());
		EasyMock.expect(grantedAuthorities[18].getAuthority()).andReturn(RoleTypes.AE_REPORTER.getCode());
		EasyMock.expect(grantedAuthorities[19].getAuthority()).andReturn(RoleTypes.AE_EXPEDITED_REPORT_REVIEWER.getCode());
		EasyMock.expect(grantedAuthorities[20].getAuthority()).andReturn(RoleTypes.AE_STUDY_DATA_REVIEWER.getCode());
		EasyMock.expect(grantedAuthorities[21].getAuthority()).andReturn(RoleTypes.LAB_IMPACT_CALENDAR_NOTIFIER.getCode());
		EasyMock.expect(grantedAuthorities[22].getAuthority()).andReturn(RoleTypes.LAB_DATA_USER.getCode());
		replayMocks();
		assertTrue(SecurityUtils.isSuperUser(authentication));
		verifyMocks();
	}
	
	public void testIsSuperUserFalse(){
		GrantedAuthority[] grantedAuthorities = getGrantedAuthorities(14);
		EasyMock.expect(authentication.getAuthorities()).andReturn(grantedAuthorities);
		EasyMock.expect(grantedAuthorities[0].getAuthority()).andReturn(RoleTypes.REGISTRAR.getCode());
		EasyMock.expect(grantedAuthorities[1].getAuthority()).andReturn(RoleTypes.BUSINESS_ADMINISTRATOR.getCode());
		EasyMock.expect(grantedAuthorities[2].getAuthority()).andReturn(RoleTypes.DATA_ANALYST.getCode());
		EasyMock.expect(grantedAuthorities[3].getAuthority()).andReturn(RoleTypes.DATA_IMPORTER.getCode());
		EasyMock.expect(grantedAuthorities[4].getAuthority()).andReturn(RoleTypes.DATA_READER.getCode());
		EasyMock.expect(grantedAuthorities[5].getAuthority()).andReturn(RoleTypes.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER.getCode());
		EasyMock.expect(grantedAuthorities[6].getAuthority()).andReturn(RoleTypes.REGISTRATION_QA_MANAGER.getCode());
		EasyMock.expect(grantedAuthorities[7].getAuthority()).andReturn(RoleTypes.STUDY_CREATOR.getCode());
		EasyMock.expect(grantedAuthorities[8].getAuthority()).andReturn(RoleTypes.STUDY_QA_MANAGER.getCode());
		EasyMock.expect(grantedAuthorities[9].getAuthority()).andReturn(RoleTypes.STUDY_SITE_PARTICIPATION_ADMINISTRATOR.getCode());
		EasyMock.expect(grantedAuthorities[10].getAuthority()).andReturn(RoleTypes.STUDY_TEAM_ADMINISTRATOR.getCode());
		EasyMock.expect(grantedAuthorities[11].getAuthority()).andReturn(RoleTypes.SUBJECT_MANAGER.getCode());
		EasyMock.expect(grantedAuthorities[12].getAuthority()).andReturn(RoleTypes.SYSTEM_ADMINISTRATOR.getCode());
		EasyMock.expect(grantedAuthorities[13].getAuthority()).andReturn(RoleTypes.SUPPLEMENTAL_STUDY_INFORMATION_MANAGER.getCode());
		replayMocks();
		assertFalse(SecurityUtils.isSuperUser(authentication));
		verifyMocks();
	}
	
	public void testGetUsername(){
		User user = registerMockFor(User.class);
		EasyMock.expect(authentication.getPrincipal()).andReturn(user);
		EasyMock.expect(user.getUsername()).andReturn("username");
		replayMocks();
		assertEquals("username", SecurityUtils.getUserName(authentication));
		verifyMocks();
	}
	
	public void testHasRoleTrue(){
		GrantedAuthority[] grantedAuthorities = getGrantedAuthorities(2);
		EasyMock.expect(authentication.getAuthorities()).andReturn(grantedAuthorities);
		EasyMock.expect(grantedAuthorities[0].getAuthority()).andReturn(RoleTypes.REGISTRAR.getCode());
		EasyMock.expect(grantedAuthorities[1].getAuthority()).andReturn(RoleTypes.BUSINESS_ADMINISTRATOR.getCode());
		roleTypes.add(RoleTypes.DATA_ANALYST);
		roleTypes.add(RoleTypes.REGISTRAR);
		replayMocks();
		assertTrue(SecurityUtils.hasRole(authentication, roleTypes));
		verifyMocks();
	}
	
	public void testHasRoleFalse(){
		GrantedAuthority[] grantedAuthorities = getGrantedAuthorities(1);
		EasyMock.expect(authentication.getAuthorities()).andReturn(grantedAuthorities);
		EasyMock.expect(grantedAuthorities[0].getAuthority()).andReturn(RoleTypes.REGISTRAR.getCode());
		roleTypes.add(RoleTypes.BUSINESS_ADMINISTRATOR);
		roleTypes.add(RoleTypes.DATA_ANALYST);
		replayMocks();
		assertFalse(SecurityUtils.hasRole(authentication, roleTypes));
		verifyMocks();
	}
	
	public void testHasAllPrivilegeTrue(){
		AuthorizedUser authorizedUser = registerMockFor(AuthorizedUser.class);
		SecurityContext securityContext = registerMockFor(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		List<UserPrivilege> userPrivileges = new ArrayList<UserPrivilege>();
		userPrivileges.add(new UserPrivilege(UserPrivilegeType.HEALTHCARESITE_CREATE.getCode()));
		userPrivileges.add(new UserPrivilege(UserPrivilegeType.HEALTHCARESITE_READ.getCode()));
		EasyMock.expect(securityContext.getAuthentication()).andReturn(authentication);
		EasyMock.expect(authentication.getPrincipal()).andReturn(authorizedUser);
		EasyMock.expect(authorizedUser.getUserPrivileges()).andReturn(userPrivileges);
		replayMocks();
		List<UserPrivilegeType> checkPrivileges = new ArrayList<UserPrivilegeType>();
		checkPrivileges.add(UserPrivilegeType.HEALTHCARESITE_READ);
		checkPrivileges.add(UserPrivilegeType.HEALTHCARESITE_CREATE);
		assertTrue(SecurityUtils.hasAllPrivilege(checkPrivileges));
		verifyMocks();
	}
	
	public void testHasAllPrivilegeFalse1(){
		AuthorizedUser authorizedUser = registerMockFor(AuthorizedUser.class);
		SecurityContext securityContext = registerMockFor(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		List<UserPrivilege> userPrivileges = new ArrayList<UserPrivilege>();
		userPrivileges.add(new UserPrivilege(UserPrivilegeType.STUDY_READ.getCode()));
		userPrivileges.add(new UserPrivilege(UserPrivilegeType.HEALTHCARESITE_READ.getCode()));
		EasyMock.expect(securityContext.getAuthentication()).andReturn(authentication);
		EasyMock.expect(authentication.getPrincipal()).andReturn(authorizedUser);
		EasyMock.expect(authorizedUser.getUserPrivileges()).andReturn(userPrivileges);
		replayMocks();
		List<UserPrivilegeType> checkPrivileges = new ArrayList<UserPrivilegeType>();
		checkPrivileges.add(UserPrivilegeType.HEALTHCARESITE_READ);
		checkPrivileges.add(UserPrivilegeType.HEALTHCARESITE_CREATE);
		assertFalse(SecurityUtils.hasAllPrivilege(checkPrivileges));
		verifyMocks();
	}
	
	public void testHasAllPrivilegeFalse2(){
		AuthorizedUser authorizedUser = registerMockFor(AuthorizedUser.class);
		SecurityContext securityContext = registerMockFor(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		List<UserPrivilege> userPrivileges = new ArrayList<UserPrivilege>();
		userPrivileges.add(new UserPrivilege(UserPrivilegeType.HEALTHCARESITE_CREATE.getCode()));
		userPrivileges.add(new UserPrivilege(UserPrivilegeType.HEALTHCARESITE_READ.getCode()));
		EasyMock.expect(securityContext.getAuthentication()).andReturn(authentication);
		EasyMock.expect(authentication.getPrincipal()).andReturn(authorizedUser);
		EasyMock.expect(authorizedUser.getUserPrivileges()).andReturn(userPrivileges);
		replayMocks();
		List<UserPrivilegeType> checkPrivileges = new ArrayList<UserPrivilegeType>();
		checkPrivileges.add(UserPrivilegeType.STUDY_CREATE);
		assertFalse(SecurityUtils.hasAllPrivilege(checkPrivileges));
		verifyMocks();
	}
	
	public void testHasAnyPrivilegeTrue(){
		AuthorizedUser authorizedUser = registerMockFor(AuthorizedUser.class);
		SecurityContext securityContext = registerMockFor(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		List<UserPrivilege> userPrivileges = new ArrayList<UserPrivilege>();
		userPrivileges.add(new UserPrivilege(UserPrivilegeType.HEALTHCARESITE_CREATE.getCode()));
		userPrivileges.add(new UserPrivilege(UserPrivilegeType.HEALTHCARESITE_READ.getCode()));
		EasyMock.expect(securityContext.getAuthentication()).andReturn(authentication);
		EasyMock.expect(authentication.getPrincipal()).andReturn(authorizedUser);
		EasyMock.expect(authorizedUser.getUserPrivileges()).andReturn(userPrivileges);
		replayMocks();
		List<UserPrivilegeType> checkPrivileges = new ArrayList<UserPrivilegeType>();
		checkPrivileges.add(UserPrivilegeType.HEALTHCARESITE_READ);
		checkPrivileges.add(UserPrivilegeType.HEALTHCARESITE_CREATE);
		assertTrue(SecurityUtils.hasAnyPrivilege(checkPrivileges));
		verifyMocks();
	}
	
	public void testHasAnyPrivilegeTrue1(){
		AuthorizedUser authorizedUser = registerMockFor(AuthorizedUser.class);
		SecurityContext securityContext = registerMockFor(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		List<UserPrivilege> userPrivileges = new ArrayList<UserPrivilege>();
		userPrivileges.add(new UserPrivilege(UserPrivilegeType.STUDY_READ.getCode()));
		userPrivileges.add(new UserPrivilege(UserPrivilegeType.HEALTHCARESITE_READ.getCode()));
		EasyMock.expect(securityContext.getAuthentication()).andReturn(authentication);
		EasyMock.expect(authentication.getPrincipal()).andReturn(authorizedUser);
		EasyMock.expect(authorizedUser.getUserPrivileges()).andReturn(userPrivileges);
		replayMocks();
		List<UserPrivilegeType> checkPrivileges = new ArrayList<UserPrivilegeType>();
		checkPrivileges.add(UserPrivilegeType.HEALTHCARESITE_READ);
		checkPrivileges.add(UserPrivilegeType.HEALTHCARESITE_CREATE);
		assertTrue(SecurityUtils.hasAnyPrivilege(checkPrivileges));
		verifyMocks();
	}
	
	public void testHasAnyPrivilegeFalse1(){
		AuthorizedUser authorizedUser = registerMockFor(AuthorizedUser.class);
		SecurityContext securityContext = registerMockFor(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		List<UserPrivilege> userPrivileges = new ArrayList<UserPrivilege>();
		userPrivileges.add(new UserPrivilege(UserPrivilegeType.HEALTHCARESITE_CREATE.getCode()));
		userPrivileges.add(new UserPrivilege(UserPrivilegeType.HEALTHCARESITE_READ.getCode()));
		EasyMock.expect(securityContext.getAuthentication()).andReturn(authentication);
		EasyMock.expect(authentication.getPrincipal()).andReturn(authorizedUser);
		EasyMock.expect(authorizedUser.getUserPrivileges()).andReturn(userPrivileges);
		replayMocks();
		List<UserPrivilegeType> checkPrivileges = new ArrayList<UserPrivilegeType>();
		checkPrivileges.add(UserPrivilegeType.STUDY_CREATE);
		assertFalse(SecurityUtils.hasAnyPrivilege(checkPrivileges));
		verifyMocks();
	}
	
	private GrantedAuthority[] getGrantedAuthorities(int size){
		GrantedAuthority[] grantedAuthorities = new GrantedAuthority[size];
		for(int i=0 ; i<size ; i++){
			grantedAuthorities[i] = registerMockFor(GrantedAuthority.class);
		}
		return grantedAuthorities;
	}
}
