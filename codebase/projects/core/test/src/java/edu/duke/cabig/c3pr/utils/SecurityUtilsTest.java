package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.User;
import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.RoleTypes;

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
		GrantedAuthority[] grantedAuthorities = getGrantedAuthorities(3);
		EasyMock.expect(authentication.getAuthorities()).andReturn(grantedAuthorities);
		EasyMock.expect(grantedAuthorities[0].getAuthority()).andReturn(RoleTypes.REGISTRAR.getCode());
		EasyMock.expect(grantedAuthorities[1].getAuthority()).andReturn(RoleTypes.STUDY_COORDINATOR.getCode());
		EasyMock.expect(grantedAuthorities[2].getAuthority()).andReturn(RoleTypes.C3PR_ADMIN.getCode());
		replayMocks();
		assertTrue(SecurityUtils.isSuperUser(authentication));
		verifyMocks();
	}
	
	public void testIsSuperUserFalse(){
		GrantedAuthority[] grantedAuthorities = getGrantedAuthorities(2);
		EasyMock.expect(authentication.getAuthorities()).andReturn(grantedAuthorities);
		EasyMock.expect(grantedAuthorities[0].getAuthority()).andReturn(RoleTypes.REGISTRAR.getCode());
		EasyMock.expect(grantedAuthorities[1].getAuthority()).andReturn(RoleTypes.STUDY_COORDINATOR.getCode());
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
		EasyMock.expect(grantedAuthorities[1].getAuthority()).andReturn(RoleTypes.STUDY_COORDINATOR.getCode());
		roleTypes.add(RoleTypes.SITE_COORDINATOR);
		roleTypes.add(RoleTypes.STUDY_COORDINATOR);
		replayMocks();
		assertTrue(SecurityUtils.hasRole(authentication, roleTypes));
		verifyMocks();
	}
	
	public void testHasRoleFalse(){
		GrantedAuthority[] grantedAuthorities = getGrantedAuthorities(1);
		EasyMock.expect(authentication.getAuthorities()).andReturn(grantedAuthorities);
		EasyMock.expect(grantedAuthorities[0].getAuthority()).andReturn(RoleTypes.REGISTRAR.getCode());
		roleTypes.add(RoleTypes.SITE_COORDINATOR);
		roleTypes.add(RoleTypes.STUDY_COORDINATOR);
		replayMocks();
		assertFalse(SecurityUtils.hasRole(authentication, roleTypes));
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
