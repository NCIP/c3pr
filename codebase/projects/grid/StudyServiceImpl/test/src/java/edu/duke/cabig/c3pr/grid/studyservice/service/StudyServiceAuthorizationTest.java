package edu.duke.cabig.c3pr.grid.studyservice.service;

import java.rmi.RemoteException;
import java.util.HashSet;

import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.grid.studyservice.service.impl.StudyAuthorizationI;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.dao.GroupSearchCriteria;

// TODO: Auto-generated Javadoc
/**
 * The Class StudyServiceAuthorizationTest. CPR-501
 */
public class StudyServiceAuthorizationTest extends DaoTestCase {

	private static ApplicationContext applicationContext = null;
	/** The study authorization. */
	private StudyAuthorizationI studyAuthorization;
	
	private UserProvisioningManager userProvisioningManager;
	
	gov.nih.nci.security.authorization.domainobjects.User adminUser = new gov.nih.nci.security.authorization.domainobjects.User();
	gov.nih.nci.security.authorization.domainobjects.User siteCoUser = new gov.nih.nci.security.authorization.domainobjects.User();
	gov.nih.nci.security.authorization.domainobjects.User studyCoUser = new gov.nih.nci.security.authorization.domainobjects.User();
	gov.nih.nci.security.authorization.domainobjects.User registrarUser = new gov.nih.nci.security.authorization.domainobjects.User();
	gov.nih.nci.security.authorization.domainobjects.User registrarAndSiteCoUser = new gov.nih.nci.security.authorization.domainobjects.User();
	
	@Override
	public ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(new String[] {"classpath*:applicationContext-grid-c3prStudyService.xml"});
        }
        return applicationContext;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.utils.DaoTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		getApplicationContext();
		studyAuthorization=(StudyAuthorizationI)applicationContext.getBean("studyAuthorization");
		userProvisioningManager=(UserProvisioningManager)applicationContext.getBean("csmUserProvisioningManager");
		
		Group search = new Group();
        search.setGroupName(C3PRUserGroupType.C3PR_ADMIN.getCode());
        GroupSearchCriteria sc = new GroupSearchCriteria(search);
        Group adminGroup=(Group) userProvisioningManager.getObjects(sc).get(0);
        
        search = new Group();
        search.setGroupName(C3PRUserGroupType.SITE_COORDINATOR.getCode());
        sc = new GroupSearchCriteria(search);
        Group siteCoGroup=(Group) userProvisioningManager.getObjects(sc).get(0);
        
        search = new Group();
        search.setGroupName(C3PRUserGroupType.STUDY_COORDINATOR.getCode());
        sc = new GroupSearchCriteria(search);
        Group studyCoGroup=(Group) userProvisioningManager.getObjects(sc).get(0);
        
        search = new Group();
        search.setGroupName(C3PRUserGroupType.REGISTRAR.getCode());
        sc = new GroupSearchCriteria(search);
        Group registrarGroup=(Group) userProvisioningManager.getObjects(sc).get(0);
        
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setPassword("test");
        adminUser.setLoginName("AdminUser");
        adminUser.setEmailId("AdminUser");
        adminUser.setGroups(new HashSet());
        adminUser.getGroups().add(adminGroup);
        userProvisioningManager.createUser(adminUser);
		//userProvisioningManager.assignGroupsToUser(csmUser.getUserId().toString(), new String[]{returnGroup.getGroupId().toString()});
        studyCoUser.setFirstName("Study");
        studyCoUser.setLastName("Coordinator");
        studyCoUser.setPassword("test");
        studyCoUser.setLoginName("StudyCoordinatorUser");
        studyCoUser.setEmailId("StudyCoordinatorUser");
        studyCoUser.setGroups(new HashSet());
        studyCoUser.getGroups().add(studyCoGroup);
        userProvisioningManager.createUser(studyCoUser);
        
        siteCoUser.setFirstName("Site");
        siteCoUser.setLastName("Coordinator");
        siteCoUser.setPassword("test");
        siteCoUser.setLoginName("SiteCoordinatorUser");
        siteCoUser.setEmailId("SiteCoordinatorUser");
        siteCoUser.setGroups(new HashSet());
        siteCoUser.getGroups().add(siteCoGroup);
        userProvisioningManager.createUser(siteCoUser);
        
		registrarUser.setFirstName("Registrar");
		registrarUser.setLastName("User");
		registrarUser.setPassword("test");
		registrarUser.setLoginName("RegistrarUser");
		registrarUser.setEmailId("RegistrarUser");
		registrarUser.setGroups(new HashSet());
		registrarUser.getGroups().add(registrarGroup);
        userProvisioningManager.createUser(registrarUser);
        
        registrarAndSiteCoUser.setFirstName("RegistrarAndSiteCo");
        registrarAndSiteCoUser.setLastName("User");
        registrarAndSiteCoUser.setPassword("test");
        registrarAndSiteCoUser.setLoginName("RegistrarAndSiteCoUser");
        registrarAndSiteCoUser.setEmailId("RegistrarAndSiteCoUser");
        registrarAndSiteCoUser.setGroups(new HashSet());
        registrarAndSiteCoUser.getGroups().add(registrarGroup);
        registrarAndSiteCoUser.getGroups().add(siteCoGroup);
        userProvisioningManager.createUser(registrarAndSiteCoUser);
	}
	
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		userProvisioningManager.removeUser(adminUser.getUserId()+"");
		userProvisioningManager.removeUser(studyCoUser.getUserId()+"");
		userProvisioningManager.removeUser(siteCoUser.getUserId()+"");
		userProvisioningManager.removeUser(registrarUser.getUserId()+"");
		userProvisioningManager.removeUser(registrarAndSiteCoUser.getUserId()+"");
		super.tearDown();
	}
	/**
	 * Test authorize create study admin.
	 */
	public void testAuthorizeCreateStudyAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=AdminUser";
		try {
			studyAuthorization.authorizeCreateStudy(callerIdentity);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize create study site coordinator.
	 */
	public void testAuthorizeCreateStudySiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SiteCoordinatorUser";
		try {
			studyAuthorization.authorizeCreateStudy(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize create study study coordinator.
	 */
	public void testAuthorizeCreateStudyStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=StudyCoordinatorUser";
		try {
			studyAuthorization.authorizeCreateStudy(callerIdentity);
			fail("Should have failed");
			
		} catch (RemoteException e) {
			
		}
	}
	
	/**
	 * Test authorize create study registrar.
	 */
	public void testAuthorizeCreateStudyRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarUser";
		try {
			studyAuthorization.authorizeCreateStudy(callerIdentity);
			fail("Should have failed");
			
		} catch (RemoteException e) {
			
		}
	}
	
	/**
	 * Test authorize create study registrar and site cordinator.
	 */
	public void testAuthorizeCreateStudyRegistrarAndSiteCordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarAndSiteCoUser";
		try {
			studyAuthorization.authorizeCreateStudy(callerIdentity);
			
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize open study admin.
	 */
	public void testAuthorizeOpenStudyAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=AdminUser";
		try {
			studyAuthorization.authorizeOpenStudy(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize open study site coordinator.
	 */
	public void testAuthorizeOpenStudySiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SiteCoordinatorUser";
		try {
			studyAuthorization.authorizeOpenStudy(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize open study study coordinator.
	 */
	public void testAuthorizeOpenStudyStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=StudyCoordinatorUser";
		try {
			studyAuthorization.authorizeOpenStudy(callerIdentity);
			
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize open study registrar.
	 */
	public void testAuthorizeOpenStudyRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarUser";
		try {
			studyAuthorization.authorizeOpenStudy(callerIdentity);
			fail("Should have failed");
			
		} catch (RemoteException e) {
			
		}
	}
	
	/**
	 * Test authorize approve study site for activation admin.
	 */
	public void testAuthorizeApproveStudySiteForActivationAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=AdminUser";
		try {
			studyAuthorization.authorizeApproveStudySiteForActivation(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize approve study site for activation site coordinator.
	 */
	public void testAuthorizeApproveStudySiteForActivationSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SiteCoordinatorUser";
		try {
			studyAuthorization.authorizeApproveStudySiteForActivation(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize approve study site for activation study coordinator.
	 */
	public void testAuthorizeApproveStudySiteForActivationStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=StudyCoordinatorUser";
		try {
			studyAuthorization.authorizeApproveStudySiteForActivation(callerIdentity);
			
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize approve study site for activation registrar.
	 */
	public void testAuthorizeApproveStudySiteForActivationRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarUser";
		try {
			studyAuthorization.authorizeApproveStudySiteForActivation(callerIdentity);
			fail("Should have failed");
			
		} catch (RemoteException e) {
			
		}
	}
	
	/**
	 * Test authorize activate study site admin.
	 */
	public void testAuthorizeActivateStudySiteAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=AdminUser";
		try {
			studyAuthorization.authorizeActivateStudySite(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize activate study site site coordinator.
	 */
	public void testAuthorizeActivateStudySiteSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SiteCoordinatorUser";
		try {
			studyAuthorization.authorizeActivateStudySite(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize activate study site study coordinator.
	 */
	public void testAuthorizeActivateStudySiteStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=StudyCoordinatorUser";
		try {
			studyAuthorization.authorizeActivateStudySite(callerIdentity);
			
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize activate study site registrar.
	 */
	public void testAuthorizeActivateStudySiteRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarUser";
		try {
			studyAuthorization.authorizeActivateStudySite(callerIdentity);
			fail("Should have failed");
			
		} catch (RemoteException e) {
			
		}
	}
	
	/**
	 * Test authorize amend study admin.
	 */
	public void testAuthorizeAmendStudyAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=AdminUser";
		try {
			studyAuthorization.authorizeAmendStudy(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize amend study site coordinator.
	 */
	public void testAuthorizeAmendStudySiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SiteCoordinatorUser";
		try {
			studyAuthorization.authorizeAmendStudy(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize amend study study coordinator.
	 */
	public void testAuthorizeAmendStudyStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=StudyCoordinatorUser";
		try {
			studyAuthorization.authorizeAmendStudy(callerIdentity);
			
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize amend study registrar.
	 */
	public void testAuthorizeAmendStudyRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarUser";
		try {
			studyAuthorization.authorizeAmendStudy(callerIdentity);
			fail("Should have failed");
			
		} catch (RemoteException e) {
			
		}
	}
	
	/**
	 * Test authorize update study site protocol version admin.
	 */
	public void testAuthorizeUpdateStudySiteProtocolVersionAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=AdminUser";
		try {
			studyAuthorization.authorizeUpdateStudySiteProtocolVersion(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize update study site protocol version site coordinator.
	 */
	public void testAuthorizeUpdateStudySiteProtocolVersionSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SiteCoordinatorUser";
		try {
			studyAuthorization.authorizeUpdateStudySiteProtocolVersion(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize update study site protocol version study coordinator.
	 */
	public void testAuthorizeUpdateStudySiteProtocolVersionStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=StudyCoordinatorUser";
		try {
			studyAuthorization.authorizeUpdateStudySiteProtocolVersion(callerIdentity);
			
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize update study site protocol version registrar.
	 */
	public void testAuthorizeUpdateStudySiteProtocolVersionRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarUser";
		try {
			studyAuthorization.authorizeUpdateStudySiteProtocolVersion(callerIdentity);
			fail("Should have failed");
			
		} catch (RemoteException e) {
			
		}
	}
	
	/**
	 * Test authorize close study admin.
	 */
	public void testAuthorizeCloseStudyAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=AdminUser";
		try {
			studyAuthorization.authorizeCloseStudy(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize close study site coordinator.
	 */
	public void testAuthorizeCloseStudySiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SiteCoordinatorUser";
		try {
			studyAuthorization.authorizeCloseStudy(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize close study study coordinator.
	 */
	public void testAuthorizeCloseStudyStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=StudyCoordinatorUser";
		try {
			studyAuthorization.authorizeCloseStudy(callerIdentity);
			
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize close study registrar.
	 */
	public void testAuthorizeCloseStudyRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarUser";
		try {
			studyAuthorization.authorizeCloseStudy(callerIdentity);
			fail("Should have failed");
			
		} catch (RemoteException e) {
			
		}
	}
	
	/**
	 * Test authorize update study status admin.
	 */
	public void testAuthorizeUpdateStudyStatusAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=AdminUser";
		try {
			studyAuthorization.authorizeUpdateStudyStatus(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize update study status site coordinator.
	 */
	public void testAuthorizeUpdateStudyStatusSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SiteCoordinatorUser";
		User user=new User("AdminUser", "password", true, true, true, true,new GrantedAuthorityImpl[]{new GrantedAuthorityImpl("ROLE_site_coordinator")});
		try {
			studyAuthorization.authorizeUpdateStudyStatus(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize update study status study coordinator.
	 */
	public void testAuthorizeUpdateStudyStatusStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=StudyCoordinatorUser";
		
		try {
			studyAuthorization.authorizeUpdateStudyStatus(callerIdentity);
			
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize update study status registrar.
	 */
	public void testAuthorizeUpdateStudyStatusRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarUser";
		try {
			studyAuthorization.authorizeUpdateStudyStatus(callerIdentity);
			fail("Should have failed");
			
		} catch (RemoteException e) {
			
		}
	}
	
	/**
	 * Test authorize close study site admin.
	 */
	public void testAuthorizeCloseStudySiteAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=AdminUser";
		try {
			studyAuthorization.authorizeCloseStudySite(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize close study site site coordinator.
	 */
	public void testAuthorizeCloseStudySiteSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SiteCoordinatorUser";
		
		try {
			studyAuthorization.authorizeCloseStudySite(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize close study site study coordinator.
	 */
	public void testAuthorizeCloseStudySiteStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=StudyCoordinatorUser";
		try {
			studyAuthorization.authorizeCloseStudySite(callerIdentity);
			
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize close study site registrar.
	 */
	public void testAuthorizeCloseStudySiteRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarUser";
		try {
			studyAuthorization.authorizeCloseStudySite(callerIdentity);
			fail("Should have failed");
			
		} catch (RemoteException e) {
			
		}
	}
}
