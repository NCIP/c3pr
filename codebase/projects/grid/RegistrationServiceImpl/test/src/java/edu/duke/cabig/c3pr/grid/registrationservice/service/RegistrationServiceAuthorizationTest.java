package edu.duke.cabig.c3pr.grid.registrationservice.service;

import java.rmi.RemoteException;
import java.util.HashSet;

import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.grid.registrationservice.service.impl.RegistrationAuthorizationI;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.dao.GroupSearchCriteria;

// TODO: Auto-generated Javadoc
/**
 * The Class StudyServiceAuthorizationTest. CPR-501
 */
public class RegistrationServiceAuthorizationTest extends DaoTestCase {

	private static ApplicationContext applicationContext = null;
	/** The study authorization. */
	private RegistrationAuthorizationI registrationAuthorizationI;
	
	private UserProvisioningManager userProvisioningManager;
	
	gov.nih.nci.security.authorization.domainobjects.User adminUser = new gov.nih.nci.security.authorization.domainobjects.User();
	gov.nih.nci.security.authorization.domainobjects.User siteCoUser = new gov.nih.nci.security.authorization.domainobjects.User();
	gov.nih.nci.security.authorization.domainobjects.User studyCoUser = new gov.nih.nci.security.authorization.domainobjects.User();
	gov.nih.nci.security.authorization.domainobjects.User registrarUser = new gov.nih.nci.security.authorization.domainobjects.User();
	gov.nih.nci.security.authorization.domainobjects.User registrarAndSiteCoUser = new gov.nih.nci.security.authorization.domainobjects.User();
	
	@Override
	public ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(new String[] {"classpath*:applicationContext-grid-c3prRegistrationService.xml"});
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
		registrationAuthorizationI=(RegistrationAuthorizationI)applicationContext.getBean("registrationAuthorization");
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
	public void testAuthorizeEnrollAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=AdminUser";
		try {
			registrationAuthorizationI.authorizeEnroll(callerIdentity);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize create study site coordinator.
	 */
	public void testAuthorizeEnrollSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SiteCoordinatorUser";
		try {
			registrationAuthorizationI.authorizeEnroll(callerIdentity);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize create study study coordinator.
	 */
	public void testAuthorizeEnrollStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=StudyCoordinatorUser";
		try {
			registrationAuthorizationI.authorizeEnroll(callerIdentity);
			fail("Should have failed");
		} catch (RemoteException e) {
			
		}
	}
	
	/**
	 * Test authorize create study registrar.
	 */
	public void testAuthorizeEnrollRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarUser";
		try {
			registrationAuthorizationI.authorizeEnroll(callerIdentity);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize create study registrar and site cordinator.
	 */
	public void testAuthorizeEnrollRegistrarAndSiteCordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarAndSiteCoUser";
		try {
			registrationAuthorizationI.authorizeEnroll(callerIdentity);
		} catch (RemoteException e) {
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize open study admin.
	 */
	public void testAuthorizeTransferAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=AdminUser";
		try {
			registrationAuthorizationI.authorizeTransfer(callerIdentity);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize open study site coordinator.
	 */
	public void testAuthorizeTransferSiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SiteCoordinatorUser";
		try {
			registrationAuthorizationI.authorizeTransfer(callerIdentity);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize open study study coordinator.
	 */
	public void testAuthorizeTransferStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=StudyCoordinatorUser";
		try {
			registrationAuthorizationI.authorizeTransfer(callerIdentity);
			fail("Should have failed");
		} catch (RemoteException e) {
		}
	}
	
	/**
	 * Test authorize open study registrar.
	 */
	public void testAuthorizeTransferRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarUser";
		try {
			registrationAuthorizationI.authorizeTransfer(callerIdentity);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize approve study site for activation admin.
	 */
	public void testAuthorizeOffStudyAdmin(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=AdminUser";
		try {
			registrationAuthorizationI.authorizeOffStudy(callerIdentity);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize approve study site for activation site coordinator.
	 */
	public void testAuthorizeOffStudySiteCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SiteCoordinatorUser";
		try {
			registrationAuthorizationI.authorizeOffStudy(callerIdentity);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
	/**
	 * Test authorize approve study site for activation study coordinator.
	 */
	public void testAuthorizeOffStudyStudyCoordinator(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=StudyCoordinatorUser";
		try {
			registrationAuthorizationI.authorizeOffStudy(callerIdentity);
			fail("Should have failed");
		} catch (RemoteException e) {
		}
	}
	
	/**
	 * Test authorize approve study site for activation registrar.
	 */
	public void testAuthorizeOffStudyRegistrar(){
		String callerIdentity="/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=RegistrarUser";
		try {
			registrationAuthorizationI.authorizeOffStudy(callerIdentity);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
}
