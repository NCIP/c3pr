package edu.duke.cabig.c3pr.setup;

import java.util.Arrays;
import java.util.HashSet;

import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.tools.Configuration;
import gov.nih.nci.security.authorization.domainobjects.User;

public class SetupStatusTest extends AbstractTestCase {

	private SetupStatus setupStatus;
	private CSMUserRepository csmUserRepository;
    private Configuration configuration;
    
    @Override
    protected void setUp() throws Exception {
    	setupStatus = new SetupStatus();
    	csmUserRepository = registerMockFor(CSMUserRepository.class);
    	configuration = registerMockFor(Configuration.class);
    	setupStatus.setConfiguration(configuration);
    	setupStatus.setCsmUserRepository(csmUserRepository);
    }
	
    public void testRecheckDefault(){
    	assertTrue(setupStatus.isPreAuthenticationSetupNeeded());
    	assertTrue(setupStatus.isPostAuthenticationSetupNeeded());
    }
    
    public void testRecheckPreAuthNeeded(){
    	EasyMock.expect(csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN)).andReturn(new HashSet<User>());
    	EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn(null);
    	replayMocks();
    	setupStatus.recheck();
    	assertTrue(setupStatus.isPreAuthenticationSetupNeeded());
    	verifyMocks();
    }
    
    public void testRecheckPreAuthNotNeeded(){
    	EasyMock.expect(csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN)).andReturn(new HashSet<User>(Arrays.asList(new User[]{new User()})));
    	EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn(null);
    	replayMocks();
    	setupStatus.recheck();
    	assertFalse(setupStatus.isPreAuthenticationSetupNeeded());
    	verifyMocks();
    }
    
    public void testRecheckPostAuthNeeded1(){
    	EasyMock.expect(csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN)).andReturn(new HashSet<User>());
    	EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn(null);
    	replayMocks();
    	setupStatus.recheck();
    	assertTrue(setupStatus.isPostAuthenticationSetupNeeded());
    	verifyMocks();
    }
    
    public void testRecheckPostAuthNeeded2(){
    	EasyMock.expect(csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN)).andReturn(new HashSet<User>());
    	EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn("SomeNCICode");
    	EasyMock.expect(configuration.get(Configuration.SITE_NAME)).andReturn(null);
    	replayMocks();
    	setupStatus.recheck();
    	assertTrue(setupStatus.isPostAuthenticationSetupNeeded());
    	verifyMocks();
    }
    
    public void testRecheckPostAuthNeeded3(){
    	EasyMock.expect(csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN)).andReturn(new HashSet<User>());
    	EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn("SomeNCICode");
    	EasyMock.expect(configuration.get(Configuration.SITE_NAME)).andReturn("SITE_NAME");
    	EasyMock.expect(configuration.get(Configuration.SMTP_PROTOCOL)).andReturn(null);
    	replayMocks();
    	setupStatus.recheck();
    	assertTrue(setupStatus.isPostAuthenticationSetupNeeded());
    	verifyMocks();
    }
    
    public void testRecheckPostAuthNeeded4(){
    	EasyMock.expect(csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN)).andReturn(new HashSet<User>());
    	EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn("SomeNCICode");
    	EasyMock.expect(configuration.get(Configuration.SITE_NAME)).andReturn("SITE_NAME");
    	EasyMock.expect(configuration.get(Configuration.SMTP_PROTOCOL)).andReturn("SMTP_PROTOCOL");
    	EasyMock.expect(configuration.get(Configuration.SMTP_SSL_AUTH)).andReturn("");
    	replayMocks();
    	setupStatus.recheck();
    	assertTrue(setupStatus.isPostAuthenticationSetupNeeded());
    	verifyMocks();
    }
    
    public void testRecheckPostAuthNeeded5(){
    	EasyMock.expect(csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN)).andReturn(new HashSet<User>());
    	EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn("SomeNCICode");
    	EasyMock.expect(configuration.get(Configuration.SITE_NAME)).andReturn("SITE_NAME");
    	EasyMock.expect(configuration.get(Configuration.SMTP_PROTOCOL)).andReturn("SMTP_PROTOCOL");
    	EasyMock.expect(configuration.get(Configuration.SMTP_SSL_AUTH)).andReturn("SMTP_SSL_AUTH");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_AUTH)).andReturn("");
    	replayMocks();
    	setupStatus.recheck();
    	assertTrue(setupStatus.isPostAuthenticationSetupNeeded());
    	verifyMocks();
    }
    
    public void testRecheckPostAuthNeeded6(){
    	EasyMock.expect(csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN)).andReturn(new HashSet<User>());
    	EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn("SomeNCICode");
    	EasyMock.expect(configuration.get(Configuration.SITE_NAME)).andReturn("SITE_NAME");
    	EasyMock.expect(configuration.get(Configuration.SMTP_PROTOCOL)).andReturn("SMTP_PROTOCOL");
    	EasyMock.expect(configuration.get(Configuration.SMTP_SSL_AUTH)).andReturn("SMTP_SSL_AUTH");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_AUTH)).andReturn("OUTGOING_MAIL_AUTH");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_PASSWORD)).andReturn("");
    	replayMocks();
    	setupStatus.recheck();
    	assertTrue(setupStatus.isPostAuthenticationSetupNeeded());
    	verifyMocks();
    }
    
    public void testRecheckPostAuthNeeded7(){
    	EasyMock.expect(csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN)).andReturn(new HashSet<User>());
    	EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn("SomeNCICode");
    	EasyMock.expect(configuration.get(Configuration.SITE_NAME)).andReturn("SITE_NAME");
    	EasyMock.expect(configuration.get(Configuration.SMTP_PROTOCOL)).andReturn("SMTP_PROTOCOL");
    	EasyMock.expect(configuration.get(Configuration.SMTP_SSL_AUTH)).andReturn("SMTP_SSL_AUTH");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_AUTH)).andReturn("OUTGOING_MAIL_AUTH");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_PASSWORD)).andReturn("OUTGOING_MAIL_PASSWORD");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_SERVER)).andReturn("");
    	replayMocks();
    	setupStatus.recheck();
    	assertTrue(setupStatus.isPostAuthenticationSetupNeeded());
    	verifyMocks();
    }
    
    public void testRecheckPostAuthNeeded8(){
    	EasyMock.expect(csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN)).andReturn(new HashSet<User>());
    	EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn("SomeNCICode");
    	EasyMock.expect(configuration.get(Configuration.SITE_NAME)).andReturn("SITE_NAME");
    	EasyMock.expect(configuration.get(Configuration.SMTP_PROTOCOL)).andReturn("SMTP_PROTOCOL");
    	EasyMock.expect(configuration.get(Configuration.SMTP_SSL_AUTH)).andReturn("SMTP_SSL_AUTH");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_AUTH)).andReturn("OUTGOING_MAIL_AUTH");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_PASSWORD)).andReturn("OUTGOING_MAIL_PASSWORD");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_SERVER)).andReturn("OUTGOING_MAIL_SERVER");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_SERVER_PORT)).andReturn("");
    	replayMocks();
    	setupStatus.recheck();
    	assertTrue(setupStatus.isPostAuthenticationSetupNeeded());
    	verifyMocks();
    }
    
    public void testRecheckPostAuthNeeded9(){
    	EasyMock.expect(csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN)).andReturn(new HashSet<User>());
    	EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn("SomeNCICode");
    	EasyMock.expect(configuration.get(Configuration.SITE_NAME)).andReturn("SITE_NAME");
    	EasyMock.expect(configuration.get(Configuration.SMTP_PROTOCOL)).andReturn("SMTP_PROTOCOL");
    	EasyMock.expect(configuration.get(Configuration.SMTP_SSL_AUTH)).andReturn("SMTP_SSL_AUTH");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_AUTH)).andReturn("OUTGOING_MAIL_AUTH");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_PASSWORD)).andReturn("OUTGOING_MAIL_PASSWORD");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_SERVER)).andReturn("OUTGOING_MAIL_SERVER");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_SERVER_PORT)).andReturn("OUTGOING_MAIL_SERVER_PORT");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_USERNAME)).andReturn("");
    	replayMocks();
    	setupStatus.recheck();
    	assertTrue(setupStatus.isPostAuthenticationSetupNeeded());
    	verifyMocks();
    }
    
    public void testRecheckPostAuthNotNeeded(){
    	EasyMock.expect(csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN)).andReturn(new HashSet<User>());
    	EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn("SomeNCICode");
    	EasyMock.expect(configuration.get(Configuration.SITE_NAME)).andReturn("SITE_NAME");
    	EasyMock.expect(configuration.get(Configuration.SMTP_PROTOCOL)).andReturn("SMTP_PROTOCOL");
    	EasyMock.expect(configuration.get(Configuration.SMTP_SSL_AUTH)).andReturn("SMTP_SSL_AUTH");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_AUTH)).andReturn("OUTGOING_MAIL_AUTH");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_PASSWORD)).andReturn("OUTGOING_MAIL_PASSWORD");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_SERVER)).andReturn("OUTGOING_MAIL_SERVER");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_SERVER_PORT)).andReturn("OUTGOING_MAIL_SERVER_PORT");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_USERNAME)).andReturn("OUTGOING_MAIL_USERNAME");
    	replayMocks();
    	setupStatus.recheck();
    	assertFalse(setupStatus.isPostAuthenticationSetupNeeded());
    	verifyMocks();
    }
    
    public void testAfterPropertiesSet(){
    	assertTrue(setupStatus.isPostAuthenticationSetupNeeded());
		assertTrue(setupStatus.isPreAuthenticationSetupNeeded());
		EasyMock.expect(csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN)).andReturn(new HashSet<User>(Arrays.asList(new User[]{new User()})));
    	EasyMock.expect(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).andReturn("SomeNCICode");
    	EasyMock.expect(configuration.get(Configuration.SITE_NAME)).andReturn("SITE_NAME");
    	EasyMock.expect(configuration.get(Configuration.SMTP_PROTOCOL)).andReturn("SMTP_PROTOCOL");
    	EasyMock.expect(configuration.get(Configuration.SMTP_SSL_AUTH)).andReturn("SMTP_SSL_AUTH");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_AUTH)).andReturn("OUTGOING_MAIL_AUTH");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_PASSWORD)).andReturn("OUTGOING_MAIL_PASSWORD");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_SERVER)).andReturn("OUTGOING_MAIL_SERVER");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_SERVER_PORT)).andReturn("OUTGOING_MAIL_SERVER_PORT");
    	EasyMock.expect(configuration.get(Configuration.OUTGOING_MAIL_USERNAME)).andReturn("OUTGOING_MAIL_USERNAME");
    	replayMocks();
    	try {
			setupStatus.afterPropertiesSet();
			assertFalse(setupStatus.isPostAuthenticationSetupNeeded());
			assertFalse(setupStatus.isPreAuthenticationSetupNeeded());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}finally{
			verifyMocks();
		}
    }
}
