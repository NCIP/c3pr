package edu.duke.cabig.c3pr.grid.registrationservice.service;

import java.rmi.RemoteException;
import java.util.HashSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.esb.infrastructure.MultisiteDelegatedCredentialProvider;
import edu.duke.cabig.c3pr.grid.registrationservice.client.RegistrationServiceClient;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.dao.GroupSearchCriteria;

// TODO: Auto-generated Javadoc
/**
 * The Class StudyServiceAuthorizationTest. CPR-501
 */
public class RegistrationServiceAuthorizationDeploymentTest extends DaoTestCase {

    private String url="https://localhost:38443/wsrf/services/cagrid/RegistrationService";
    private String idpURL="https://ccis-c3pr-grid.duhs.duke.edu:8080/wsrf/services/cagrid/Dorian";
    private String ifsURL="https://ccis-c3pr-grid.duhs.duke.edu:8080/wsrf/services/cagrid/Dorian";
	
	private static ApplicationContext applicationContext = null;
	
	private UserProvisioningManager userProvisioningManager;
	
	private gov.nih.nci.security.authorization.domainobjects.User adminUser = new gov.nih.nci.security.authorization.domainobjects.User();
	private gov.nih.nci.security.authorization.domainobjects.User siteCoUser = new gov.nih.nci.security.authorization.domainobjects.User();
	private gov.nih.nci.security.authorization.domainobjects.User studyCoUser = new gov.nih.nci.security.authorization.domainobjects.User();
	private gov.nih.nci.security.authorization.domainobjects.User registrarUser = new gov.nih.nci.security.authorization.domainobjects.User();
	private gov.nih.nci.security.authorization.domainobjects.User registrarAndSiteCoUser = new gov.nih.nci.security.authorization.domainobjects.User();
	
	private String dorianAdminUsername="AdminUser";
	private String dorianAdminPassword="Admi1!";
	private String dorianSiteCoUsername="SiteCoordinatorUser";
	private String dorianSiteCoPassword="SiCo1!";
	private String dorianStudyCoUsername="StudyCoordinatorUser";
	private String dorianStudyCoPassword="StCo1!";
	private String dorianRegistrarUsername="RegistrarUser";
	private String dorianRegistrarPassword="Reg1!";
	private String dorianRegAndSiteCoUsername="RegistrarAndSiteCoUser";
	private String dorianRegAndSiteCoPassword="ReSiCo1!";
	
	
	private RegistrationServiceClient getRegistrationServiceClient(String username, String password){
		MultisiteDelegatedCredentialProvider multisiteDelegatedCredentialProvider=new MultisiteDelegatedCredentialProvider();
		multisiteDelegatedCredentialProvider.setIdpUrl(idpURL);
		multisiteDelegatedCredentialProvider.setIfsUrl(ifsURL);
		try {
			return new RegistrationServiceClient(url,multisiteDelegatedCredentialProvider.getCredential(username, password));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
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
        adminUser.setPassword(dorianAdminPassword);
        adminUser.setLoginName(dorianAdminUsername);
        adminUser.setEmailId(dorianAdminUsername);
        adminUser.setGroups(new HashSet());
        adminUser.getGroups().add(adminGroup);
        userProvisioningManager.createUser(adminUser);
		//userProvisioningManager.assignGroupsToUser(csmUser.getUserId().toString(), new String[]{returnGroup.getGroupId().toString()});
        studyCoUser.setFirstName("Study");
        studyCoUser.setLastName("Coordinator");
        studyCoUser.setPassword(dorianStudyCoPassword);
        studyCoUser.setLoginName(dorianStudyCoUsername);
        studyCoUser.setEmailId(dorianStudyCoUsername);
        studyCoUser.setGroups(new HashSet());
        studyCoUser.getGroups().add(studyCoGroup);
        userProvisioningManager.createUser(studyCoUser);
        
        siteCoUser.setFirstName("Site");
        siteCoUser.setLastName("Coordinator");
        siteCoUser.setPassword(dorianSiteCoPassword);
        siteCoUser.setLoginName(dorianSiteCoUsername);
        siteCoUser.setEmailId(dorianSiteCoUsername);
        siteCoUser.setGroups(new HashSet());
        siteCoUser.getGroups().add(siteCoGroup);
        userProvisioningManager.createUser(siteCoUser);
        
		registrarUser.setFirstName("Registrar");
		registrarUser.setLastName("User");
		registrarUser.setPassword(dorianRegistrarPassword);
		registrarUser.setLoginName(dorianRegistrarUsername);
		registrarUser.setEmailId(dorianRegistrarUsername);
		registrarUser.setGroups(new HashSet());
		registrarUser.getGroups().add(registrarGroup);
        userProvisioningManager.createUser(registrarUser);
        
        registrarAndSiteCoUser.setFirstName("RegistrarAndSiteCo");
        registrarAndSiteCoUser.setLastName("User");
        registrarAndSiteCoUser.setPassword(dorianRegAndSiteCoPassword);
        registrarAndSiteCoUser.setLoginName(dorianRegAndSiteCoUsername);
        registrarAndSiteCoUser.setEmailId(dorianRegAndSiteCoUsername);
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
		try {
			getRegistrationServiceClient(dorianAdminUsername, dorianAdminPassword).enroll(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize create study site coordinator.
	 */
	public void testAuthorizeEnrollSiteCoordinator(){
		try {
			getRegistrationServiceClient(dorianSiteCoUsername, dorianSiteCoPassword).enroll(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize create study study coordinator.
	 */
	public void testAuthorizeEnrollStudyCoordinator(){
		try {
			getRegistrationServiceClient(dorianStudyCoUsername, dorianStudyCoPassword).enroll(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException:"));
		}
	}
	
	/**
	 * Test authorize create study registrar.
	 */
	public void testAuthorizeEnrollRegistrar(){
		try {
			getRegistrationServiceClient(dorianRegistrarUsername, dorianRegistrarPassword).enroll(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize create study registrar and site cordinator.
	 */
	public void testAuthorizeEnrollRegistrarAndSiteCordinator(){
		try {
			getRegistrationServiceClient(dorianRegAndSiteCoUsername, dorianRegAndSiteCoPassword).enroll(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
			
		}
	}
	
	/**
	 * Test authorize open study admin.
	 */
	public void testAuthorizeTransferAdmin(){
		try {
			getRegistrationServiceClient(dorianAdminUsername, dorianAdminPassword).transfer(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
			
		}
	}
	
	/**
	 * Test authorize open study site coordinator.
	 */
	public void testAuthorizeTransferSiteCoordinator(){
		try {
			getRegistrationServiceClient(dorianSiteCoUsername, dorianSiteCoPassword).transfer(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
			
		}
	}
	
	/**
	 * Test authorize open study study coordinator.
	 */
	public void testAuthorizeTransferStudyCoordinator(){
		try {
			getRegistrationServiceClient(dorianStudyCoUsername, dorianStudyCoPassword).transfer(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException:"));
			
		}
	}
	
	/**
	 * Test authorize open study registrar.
	 */
	public void testAuthorizeTransferRegistrar(){
		try {
			getRegistrationServiceClient(dorianRegistrarUsername, dorianRegistrarPassword).transfer(new Message());
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize approve study site for activation admin.
	 */
	public void testAuthorizeOffStudyAdmin(){
		try {
			getRegistrationServiceClient(dorianAdminUsername, dorianAdminPassword).offStudy(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize approve study site for activation site coordinator.
	 */
	public void testAuthorizeOffStudySiteCoordinator(){
		try {
			getRegistrationServiceClient(dorianSiteCoUsername, dorianSiteCoPassword).offStudy(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize approve study site for activation study coordinator.
	 */
	public void testAuthorizeOffStudyStudyCoordinator(){
		try {
			getRegistrationServiceClient(dorianStudyCoUsername, dorianStudyCoPassword).offStudy(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException:"));
		}
	}
	
	/**
	 * Test authorize approve study site for activation registrar.
	 */
	public void testAuthorizeOffStudyRegistrar(){
		try {
			getRegistrationServiceClient(dorianRegistrarUsername, dorianRegistrarPassword).offStudy(new Message());
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
}
