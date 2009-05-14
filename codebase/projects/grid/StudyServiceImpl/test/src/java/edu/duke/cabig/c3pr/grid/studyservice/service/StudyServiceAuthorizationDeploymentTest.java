package edu.duke.cabig.c3pr.grid.studyservice.service;

import java.rmi.RemoteException;
import java.util.HashSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.esb.infrastructure.MultisiteDelegatedCredentialProvider;
import edu.duke.cabig.c3pr.grid.studyservice.client.StudyServiceClient;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.dao.GroupSearchCriteria;

// TODO: Auto-generated Javadoc
/**
 * The Class StudyServiceAuthorizationTest. CPR-501
 */
public class StudyServiceAuthorizationDeploymentTest extends DaoTestCase {

    //private String url="https://localhost:38443/wsrf/services/cagrid/StudyService";
	private String url="https://ccis-c3pr-app.duhs.duke.edu:88/wsrf/services/cagrid/StudyService";
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
	
	
	private StudyServiceClient getStudyServiceClient(String username, String password){
		MultisiteDelegatedCredentialProvider multisiteDelegatedCredentialProvider=new MultisiteDelegatedCredentialProvider();
		multisiteDelegatedCredentialProvider.setIdpUrl(idpURL);
		multisiteDelegatedCredentialProvider.setIfsUrl(ifsURL);
		try {
			return new StudyServiceClient(url,multisiteDelegatedCredentialProvider.getCredential(username, password));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
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
	public void testAuthorizeCreateStudyDefinitionAdmin(){
		try {
			getStudyServiceClient(dorianAdminUsername, dorianAdminPassword).createStudyDefinition(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize create study site coordinator.
	 */
	public void testAuthorizeCreateStudyDefinitionSiteCoordinator(){
		try {
			getStudyServiceClient(dorianSiteCoUsername, dorianSiteCoPassword).createStudyDefinition(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize create study study coordinator.
	 */
	public void testAuthorizeCreateStudyDefinitionStudyCoordinator(){
		try {
			getStudyServiceClient(dorianStudyCoUsername, dorianStudyCoPassword).createStudyDefinition(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException:"));
		}
	}
	
	/**
	 * Test authorize create study registrar.
	 */
	public void testAuthorizeCreateStudyDefinitionRegistrar(){
		try {
			getStudyServiceClient(dorianRegistrarUsername, dorianRegistrarPassword).createStudyDefinition(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException:"));
		}
	}
	
	/**
	 * Test authorize create study registrar and site cordinator.
	 */
	public void testAuthorizeCreateStudyDefinitionRegistrarAndSiteCordinator(){
		try {
			getStudyServiceClient(dorianRegAndSiteCoUsername, dorianRegAndSiteCoPassword).createStudyDefinition(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
			
		}
	}
	
	/**
	 * Test authorize open study admin.
	 */
	public void testAuthorizeOpenStudyAdmin(){
		try {
			getStudyServiceClient(dorianAdminUsername, dorianAdminPassword).openStudy(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
			
		}
	}
	
	/**
	 * Test authorize open study site coordinator.
	 */
	public void testAuthorizeOpenStudySiteCoordinator(){
		try {
			getStudyServiceClient(dorianSiteCoUsername, dorianSiteCoPassword).openStudy(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
			
		}
	}
	
	/**
	 * Test authorize open study study coordinator.
	 */
	public void testAuthorizeOpenStudyStudyCoordinator(){
		try {
			getStudyServiceClient(dorianStudyCoUsername, dorianStudyCoPassword).openStudy(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
			
		}
	}
	
	/**
	 * Test authorize open study registrar.
	 */
	public void testAuthorizeOpenStudyRegistrar(){
		try {
			getStudyServiceClient(dorianRegistrarUsername, dorianRegistrarPassword).openStudy(new Message());
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException:"));
		}
	}
	
	/**
	 * Test authorize activate study site admin.
	 */
	public void testAuthorizeActivateStudySiteAdmin(){
		try {
			getStudyServiceClient(dorianAdminUsername, dorianAdminPassword).activateStudySite(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize activate study site site coordinator.
	 */
	public void testAuthorizeActivateStudySiteSiteCoordinator(){
		try {
			getStudyServiceClient(dorianSiteCoUsername, dorianSiteCoPassword).activateStudySite(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize activate study site study coordinator.
	 */
	public void testAuthorizeActivateStudySiteStudyCoordinator(){
		try {
			getStudyServiceClient(dorianStudyCoUsername, dorianStudyCoPassword).activateStudySite(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize activate study site registrar.
	 */
	public void testAuthorizeActivateStudySiteRegistrar(){
		try {
			getStudyServiceClient(dorianRegistrarUsername, dorianRegistrarPassword).activateStudySite(new Message());
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException:"));
		}
	}
	
	/**
	 * Test authorize amend study admin.
	 */
	public void testAuthorizeAmendStudyAdmin(){
		try {
			getStudyServiceClient(dorianAdminUsername, dorianAdminPassword).amendStudy(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.rmi.RemoteException: Not yet implemented"));
		}
	}
	
	/**
	 * Test authorize amend study site coordinator.
	 */
	public void testAuthorizeAmendStudySiteCoordinator(){
		try {
			getStudyServiceClient(dorianSiteCoUsername, dorianSiteCoPassword).amendStudy(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.rmi.RemoteException: Not yet implemented"));
		}
	}
	
	/**
	 * Test authorize amend study study coordinator.
	 */
	public void testAuthorizeAmendStudyStudyCoordinator(){
		try {
			getStudyServiceClient(dorianStudyCoUsername, dorianStudyCoPassword).amendStudy(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.rmi.RemoteException: Not yet implemented"));
		}
	}
	
	/**
	 * Test authorize amend study registrar.
	 */
	public void testAuthorizeAmendStudyRegistrar(){
		try {
			getStudyServiceClient(dorianRegistrarUsername, dorianRegistrarPassword).amendStudy(new Message());
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException:"));
		}
	}
	
	/**
	 * Test authorize update study site protocol version admin.
	 */
	public void testAuthorizeUpdateStudySiteProtocolVersionAdmin(){
		try {
			getStudyServiceClient(dorianAdminUsername, dorianAdminPassword).updateStudySiteProtocolVersion(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.rmi.RemoteException: Not yet implemented"));
		}
	}
	
	/**
	 * Test authorize update study site protocol version site coordinator.
	 */
	public void testAuthorizeUpdateStudySiteProtocolVersionSiteCoordinator(){
		try {
			getStudyServiceClient(dorianSiteCoUsername, dorianSiteCoPassword).updateStudySiteProtocolVersion(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.rmi.RemoteException: Not yet implemented"));
		}
	}
	
	/**
	 * Test authorize update study site protocol version study coordinator.
	 */
	public void testAuthorizeUpdateStudySiteProtocolVersionStudyCoordinator(){
		try {
			getStudyServiceClient(dorianStudyCoUsername, dorianStudyCoPassword).updateStudySiteProtocolVersion(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.rmi.RemoteException: Not yet implemented"));
		}
	}
	
	/**
	 * Test authorize update study site protocol version registrar.
	 */
	public void testAuthorizeUpdateStudySiteProtocolVersionRegistrar(){
		try {
			getStudyServiceClient(dorianRegistrarUsername, dorianRegistrarPassword).updateStudySiteProtocolVersion(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException:"));		}
	}
	
	/**
	 * Test authorize close study admin.
	 */
	public void testAuthorizeCloseStudyToAccrualAdmin(){
		try {
			getStudyServiceClient(dorianAdminUsername, dorianAdminPassword).closeStudyToAccrual(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize close study site coordinator.
	 */
	public void testAuthorizeCloseStudyToAccrualSiteCoordinator(){
		try {
			getStudyServiceClient(dorianSiteCoUsername, dorianSiteCoPassword).closeStudyToAccrual(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize close study study coordinator.
	 */
	public void testAuthorizeCloseStudyToAccrualStudyCoordinator(){
		try {
			getStudyServiceClient(dorianStudyCoUsername, dorianStudyCoPassword).closeStudyToAccrual(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize close study registrar.
	 */
	public void testAuthorizeCloseStudyToAccrualRegistrar(){
		try {
			getStudyServiceClient(dorianRegistrarUsername, dorianRegistrarPassword).closeStudyToAccrual(new Message());
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException:"));
		}
	}
	
	/**
	 * Test authorize update study status admin.
	 */
	public void testAuthorizeUpdateStudyAdmin(){
		try {
			getStudyServiceClient(dorianAdminUsername, dorianAdminPassword).updateStudy(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.rmi.RemoteException: Not yet implemented"));
		}
	}
	
	/**
	 * Test authorize update study status site coordinator.
	 */
	public void testAuthorizeUpdateStudySiteCoordinator(){
		try {
			getStudyServiceClient(dorianSiteCoUsername, dorianSiteCoPassword).updateStudy(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.rmi.RemoteException: Not yet implemented"));
		}
	}
	
	/**
	 * Test authorize update study status study coordinator.
	 */
	public void testAuthorizeUpdateStudyStudyCoordinator(){
		try {
			getStudyServiceClient(dorianStudyCoUsername, dorianStudyCoPassword).updateStudy(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.rmi.RemoteException: Not yet implemented"));
		}
	}
	
	/**
	 * Test authorize update study status registrar.
	 */
	public void testAuthorizeUpdateStudyRegistrar(){
		try {
			getStudyServiceClient(dorianRegistrarUsername, dorianRegistrarPassword).updateStudy(new Message());
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException:"));
		}
	}
	
	/**
	 * Test authorize close study site admin.
	 */
	public void testAuthorizeCloseStudySiteToAccrualAdmin(){
		try {
			getStudyServiceClient(dorianAdminUsername, dorianAdminPassword).closeStudySiteToAccrual(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize close study site site coordinator.
	 */
	public void testAuthorizeCloseStudyToAccrualSiteSiteCoordinator(){
		try {
			getStudyServiceClient(dorianSiteCoUsername, dorianSiteCoPassword).closeStudySiteToAccrual(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize close study site study coordinator.
	 */
	public void testAuthorizeCloseStudyToAccrualSiteStudyCoordinator(){
		try {
			getStudyServiceClient(dorianStudyCoUsername, dorianStudyCoPassword).closeStudySiteToAccrual(new Message());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("java.lang.StringIndexOutOfBoundsException: String index out of range"));
		}
	}
	
	/**
	 * Test authorize close study site registrar.
	 */
	public void testAuthorizeCloseStudyToAccrualSiteRegistrar(){
		try {
			getStudyServiceClient(dorianRegistrarUsername, dorianRegistrarPassword).closeStudySiteToAccrual(new Message());
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue("wrong error", e.getMessage().startsWith("org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException:"));
		}
	}
}
