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
import edu.duke.cabig.c3pr.constants.RoleTypes;
import edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyServiceAuthorizationImpl;
import edu.duke.cabig.c3pr.utils.GridSecurityUtils;

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
			studyAuthorization.authorizeCreateStudyDefinition(callerIdentity);
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
			studyAuthorization.authorizeCreateStudyDefinition(callerIdentity);
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
			studyAuthorization.authorizeCreateStudyDefinition(callerIdentity);
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
			studyAuthorization.authorizeCreateStudyDefinition(callerIdentity);
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
			studyAuthorization.authorizeCreateStudyDefinition(callerIdentity);
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
	
	public void testAuthorizeGetStudyAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		try {
			studyAuthorization.authorizeGetStudy(callerIdentity);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeGetStudySiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		try {
			studyAuthorization.authorizeGetStudy(callerIdentity);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeGetStudyStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		try {
			studyAuthorization.authorizeGetStudy(callerIdentity);
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeGetStudyRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		try {
			studyAuthorization.authorizeGetStudy(callerIdentity);
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
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
	
	public void testAuthorizeCloseStudyToAccrualAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudyToAccrual(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudyToAccrualCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudyToAccrual(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudyToAccrualStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudyToAccrual(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudyToAccrualRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudyToAccrual(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeCloseStudyToAccrualAndTreatmentAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudyToAccrualAndTreatment(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudyToAccrualAndTreatmentCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudyToAccrualAndTreatment(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudyToAccrualAndTreatmentStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudyToAccrualAndTreatment(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudyToAccrualAndTreatmentRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudyToAccrualAndTreatment(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudyToAccrualAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudyToAccrual(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudyToAccrualCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudyToAccrual(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudyToAccrualStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudyToAccrual(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudyToAccrualRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudyToAccrual(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudyToAccrualAndTreatmentAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudyToAccrualAndTreatment(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudyToAccrualAndTreatmentCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudyToAccrualAndTreatment(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudyToAccrualAndTreatmentStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudyToAccrualAndTreatment(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudyToAccrualAndTreatmentRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudyToAccrualAndTreatment(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeUpdateStudyAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeUpdateStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeUpdateStudySiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeUpdateStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeUpdateStudyStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeUpdateStudy(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeUpdateStudyRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeUpdateStudy(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeCloseStudySiteToAccrualAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudySiteToAccrual(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudySiteToAccrualSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudySiteToAccrual(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudySiteToAccrualStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudySiteToAccrual(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudySiteToAccrualRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudySiteToAccrual(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeCloseStudySiteToAccrualAndTreatmentAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudySiteToAccrualAndTreatment(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudySiteToAccrualAndTreatmentSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudySiteToAccrualAndTreatment(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudySiteToAccrualAndTreatmentStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudySiteToAccrualAndTreatment(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeCloseStudySiteToAccrualAndTreatmentRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeCloseStudySiteToAccrualAndTreatment(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudySiteToAccrualAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudySiteToAccrual(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudySiteToAccrualSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudySiteToAccrual(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudySiteToAccrualStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudySiteToAccrual(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudySiteToAccrualRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudySiteToAccrual(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudySiteToAccrualAndTreatmentAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_c3pr_admin")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudySiteToAccrualAndTreatment(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudySiteToAccrualAndTreatmentSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudySiteToAccrualAndTreatment(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudySiteToAccrualAndTreatmentStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_study_coordinator")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudySiteToAccrualAndTreatment(callerIdentity);
			verifyMocks();
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	public void testAuthorizeTemporarilyCloseStudySiteToAccrualAndTreatmentRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest";
		User user=new User("SmokeTest", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_registrar")});
		EasyMock.expect(userDetailsService.loadUserByUsername("SmokeTest")).andReturn(user);
		replayMocks();
		try {
			studyAuthorization.authorizeTemporarilyCloseStudySiteToAccrualAndTreatment(callerIdentity);
			fail("Should have failed");
			verifyMocks();
		} catch (RemoteException e) {
			verifyMocks();
		}
	}
}
