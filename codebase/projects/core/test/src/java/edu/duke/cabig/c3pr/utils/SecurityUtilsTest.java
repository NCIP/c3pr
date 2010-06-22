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
	
	public void testGetUsername(){
		User user = registerMockFor(User.class);
		EasyMock.expect(authentication.getPrincipal()).andReturn(user);
		EasyMock.expect(user.getUsername()).andReturn("username");
		replayMocks();
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
		verifyMocks();
	}
	
	public void testHasRoleFalse(){
		GrantedAuthority[] grantedAuthorities = getGrantedAuthorities(1);
		EasyMock.expect(authentication.getAuthorities()).andReturn(grantedAuthorities);
		EasyMock.expect(grantedAuthorities[0].getAuthority()).andReturn(RoleTypes.REGISTRAR.getCode());
		roleTypes.add(RoleTypes.SITE_COORDINATOR);
		roleTypes.add(RoleTypes.STUDY_COORDINATOR);
		replayMocks();
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
