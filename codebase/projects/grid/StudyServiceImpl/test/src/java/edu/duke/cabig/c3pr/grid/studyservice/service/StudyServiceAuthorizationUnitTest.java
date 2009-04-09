package edu.duke.cabig.c3pr.grid.studyservice.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetailsService;
import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyServiceAuthorizationImpl;
import edu.duke.cabig.c3pr.utils.GridSecurityUtils;
import edu.duke.cabig.c3pr.utils.RoleTypes;

public class StudyServiceAuthorizationUnitTest extends AbstractTestCase {

	private UserDetailsService userDetailsService;
	private StudyServiceAuthorizationImpl studyAuthorization;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		userDetailsService= registerMockFor(UserDetailsService.class);
		GridSecurityUtils gridSecurityUtils= new GridSecurityUtils();
		gridSecurityUtils.setUserDetailsService(userDetailsService);
		studyAuthorization=new StudyServiceAuthorizationImpl();
		studyAuthorization.setGridSecurityUtils(gridSecurityUtils);
		
	}
	
	public void testAuthorizeCreateStudyAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCreateStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCreateStudySiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCreateStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCreateStudyStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCreateStudy(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeCreateStudyRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCreateStudy(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeCreateStudyRegistrarAndSiteCordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar"),new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCreateStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeOpenStudyAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeOpenStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeOpenStudySiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeOpenStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeOpenStudyStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeOpenStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeOpenStudyRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeOpenStudy(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeApproveStudySiteForActivationAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeApproveStudySiteForActivation(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeApproveStudySiteForActivationSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeApproveStudySiteForActivation(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeApproveStudySiteForActivationStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeApproveStudySiteForActivation(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeApproveStudySiteForActivationRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeApproveStudySiteForActivation(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeActivateStudySiteAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeActivateStudySite(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeActivateStudySiteSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeActivateStudySite(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeActivateStudySiteStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeActivateStudySite(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeActivateStudySiteRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeActivateStudySite(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeAmendStudyAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeAmendStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeAmendStudySiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeAmendStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeAmendStudyStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeAmendStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeAmendStudyRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeAmendStudy(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeUpdateStudySiteProtocolVersionAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeUpdateStudySiteProtocolVersion(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeUpdateStudySiteProtocolVersionSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeUpdateStudySiteProtocolVersion(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeUpdateStudySiteProtocolVersionStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeUpdateStudySiteProtocolVersion(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeUpdateStudySiteProtocolVersionRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeUpdateStudySiteProtocolVersion(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeCloseStudyAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudySiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudyStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudyRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudy(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeUpdateStudyStatusAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeUpdateStudyStatus(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeUpdateStudyStatusSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeUpdateStudyStatus(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeUpdateStudyStatusStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeUpdateStudyStatus(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeUpdateStudyStatusRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeUpdateStudyStatus(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeCloseStudySiteAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudySite(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudySiteSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudySite(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudySiteStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudySite(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudySiteRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudySite(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
}
