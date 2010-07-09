package edu.duke.cabig.c3pr.utils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetailsService;
import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.RoleTypes;

public class GridSecurityUtilsUnitTest extends AbstractTestCase {

	private UserDetailsService userDetailsService;
	private GridSecurityUtils gridSecurityUtils;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		userDetailsService= registerMockFor(UserDetailsService.class);
		gridSecurityUtils=new GridSecurityUtils();
		gridSecurityUtils.setUserDetailsService(userDetailsService);
		
	}
	
	public void testGetUserIdFromGridIdentity(){
		String userId=gridSecurityUtils.getUserIdFromGridIdentity("/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest");
		assertEquals("SmokeTest", userId);
	}
	
	public void testGetUserIdFromGridIdentityNULLIdentity(){
		String userId=gridSecurityUtils.getUserIdFromGridIdentity("");
		assertEquals("", userId);
	}
	
	public void testLoadUserAuthentication(){
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("Admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			User returnedUser=gridSecurityUtils.loadUserAuthentication("SmokeTest");
			assertEquals("SmokeTest", returnedUser.getUsername());
			assertEquals("password", returnedUser.getPassword());
			assertEquals("Admin", returnedUser.getAuthorities()[0].getAuthority());
			User secureUser =(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			assertEquals("SmokeTest", secureUser.getUsername());
			assertEquals("password", secureUser.getPassword());
			assertEquals("Admin", secureUser.getAuthorities()[0].getAuthority());
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have thrown exception");
		}
	}
	
	public void testLoadUserAuthenticationException(){
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(null);
		replayMocks();
		try {
			User returnedUser=gridSecurityUtils.loadUserAuthentication("SmokeTest");
			fail("Should have thrown exception");
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testGetRolesAdmin(){
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_system_administrator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			List<RoleTypes> roles=gridSecurityUtils.getRoles("SmokeTest");
			assertEquals(RoleTypes.SYSTEM_ADMINISTRATOR, roles.get(0));
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have thrown exception");
		}
	}
	
	public void testGetRolesAll(){
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_data_importer"),
				new GrantedAuthorityImpl("ROLE_person_and_organization_information_manager"),
				new GrantedAuthorityImpl("ROLE_registrar"),
				new GrantedAuthorityImpl("ROLE_user_administrator"),});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			List<RoleTypes> roles=gridSecurityUtils.getRoles("SmokeTest");
			assertEquals(RoleTypes.DATA_IMPORTER, roles.get(0));
			assertEquals(RoleTypes.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER, roles.get(1));
			assertEquals(RoleTypes.REGISTRAR, roles.get(2));
			assertEquals(RoleTypes.USER_ADMINISTRATOR, roles.get(3));
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have thrown exception");
		}
	}
	
	public void testGetRolesInvalidRole(){
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("wrong"),new GrantedAuthorityImpl("ROLE_user_administrator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			List<RoleTypes> roles=gridSecurityUtils.getRoles("SmokeTest");
			assertEquals("wrong size", 1, roles.size());
			assertEquals(RoleTypes.USER_ADMINISTRATOR, roles.get(0));
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Should have thrown exception");
			verifyMocks();
		}
	}
	
	public void testGetRolesAsString(){
		ArrayList<RoleTypes> roles=new ArrayList<RoleTypes>();
		roles.add(RoleTypes.DATA_ANALYST);
		roles.add(RoleTypes.REGISTRAR);
		roles.add(RoleTypes.STUDY_CREATOR);
		roles.add(RoleTypes.STUDY_QA_MANAGER);
		String roleString=gridSecurityUtils.getRolesAsString(roles);
		assertEquals("{"+RoleTypes.DATA_ANALYST.getDisplayName()+","+RoleTypes.REGISTRAR.getDisplayName()+","+RoleTypes.STUDY_CREATOR.getDisplayName()+","+RoleTypes.STUDY_QA_MANAGER.getDisplayName()+","+"}", roleString);
	}
	
}
