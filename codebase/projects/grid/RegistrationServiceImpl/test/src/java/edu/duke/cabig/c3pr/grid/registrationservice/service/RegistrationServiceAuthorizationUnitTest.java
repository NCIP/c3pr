/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.grid.registrationservice.service;

import java.rmi.RemoteException;

import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetailsService;
import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.grid.registrationservice.service.impl.RegistrationServiceAuthorizationImpl;
import edu.duke.cabig.c3pr.utils.GridSecurityUtils;

public class RegistrationServiceAuthorizationUnitTest extends AbstractTestCase {

	private UserDetailsService userDetailsService;
	private RegistrationServiceAuthorizationImpl registrationAuthorization;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		userDetailsService= registerMockFor(UserDetailsService.class);
		GridSecurityUtils gridSecurityUtils= new GridSecurityUtils();
		gridSecurityUtils.setUserDetailsService(userDetailsService);
		registrationAuthorization=new RegistrationServiceAuthorizationImpl();
		registrationAuthorization.setGridSecurityUtils(gridSecurityUtils);
		
	}
	
	public void testAuthorizeEnrollAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			registrationAuthorization.authorizeEnroll(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeEnrollSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			registrationAuthorization.authorizeEnroll(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeEnrollStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			registrationAuthorization.authorizeEnroll(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeEnrollRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			registrationAuthorization.authorizeEnroll(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeEnrollRegistrarAndSiteCordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar"),new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			registrationAuthorization.authorizeEnroll(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTransferAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			registrationAuthorization.authorizeTransfer(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTransferSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			registrationAuthorization.authorizeTransfer(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTransferStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			registrationAuthorization.authorizeTransfer(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void testAuthorizeTransferRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			registrationAuthorization.authorizeTransfer(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
			verifyMocks();
		}
	}
	
	public void testAuthorizeOffStudyAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			registrationAuthorization.authorizeOffStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeOffStudySiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			registrationAuthorization.authorizeOffStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeOffStudyStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			registrationAuthorization.authorizeOffStudy(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			verifyMocks();
		}
	}
	
	public void testAuthorizeOffStudyRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			registrationAuthorization.authorizeOffStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
			verifyMocks();
		}
	}
}
