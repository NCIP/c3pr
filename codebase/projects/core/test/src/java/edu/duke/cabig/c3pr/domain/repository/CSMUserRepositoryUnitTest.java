package edu.duke.cabig.c3pr.domain.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.easymock.classextension.EasyMock;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.repository.impl.CSMUserRepositoryImpl;
import edu.duke.cabig.c3pr.domain.repository.impl.CSMUserRepositoryImpl.C3PRNoSuchUserException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.dao.GroupSearchCriteria;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;
import gov.nih.nci.security.exceptions.CSTransactionException;
import gov.nih.nci.security.util.StringEncrypter;
import gov.nih.nci.security.util.StringEncrypter.EncryptionException;

public class CSMUserRepositoryUnitTest extends AbstractTestCase {

	private UserProvisioningManager userProvisioningManager;
	private UserDao userDao;
	private MailSender mailSender;
	private CSMUserRepository csmUserRepository;
	private User csmUser;
	private edu.duke.cabig.c3pr.domain.User c3prUser;
	private String invalidUsername = "Invalid";
	private String validUsername = "Valid";
	private String invalidEmail = "Invalid@email.com";
	private String validEmail = "Valid@email.com";
	private String invalidLoginId = "InvalidLoginId";
	private String validLoginId = "ValidLoginId";
	private String oldPassword="old_password";
	private String newPassword="new_password";
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		csmUser = registerMockFor(User.class);
		userProvisioningManager = registerMockFor(UserProvisioningManager.class);
		userDao = registerMockFor(UserDao.class);
		mailSender = registerMockFor(MailSender.class);
		CSMUserRepositoryImpl csmUserRepositoryImpl = new CSMUserRepositoryImpl();
		csmUserRepository = csmUserRepositoryImpl;
		csmUserRepositoryImpl.setMailSender(mailSender);
		csmUserRepositoryImpl.setUserDao(userDao);
		csmUserRepositoryImpl.setUserProvisioningManager(userProvisioningManager);
		c3prUser = registerMockFor(edu.duke.cabig.c3pr.domain.User.class);
	}
	
	public void testGetUserByNameInvalidUsername(){
		EasyMock.expect(userProvisioningManager.getUser(invalidUsername)).andReturn(null);		
		replayMocks();
		try {
			csmUserRepository.getUserByName(invalidUsername);
			fail();
		} catch (C3PRNoSuchUserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			fail();
		}finally{
			verifyMocks();
		}
	}
	
	public void testGetUserByNameValidUsernameInvalidResearchStaff(){
		EasyMock.expect(userProvisioningManager.getUser(validUsername)).andReturn(csmUser);
		EasyMock.expect(csmUser.getEmailId()).andReturn(invalidEmail);
		EasyMock.expect(userDao.getByEmailAddress(invalidEmail)).andReturn(null);
		replayMocks();
		try {
			csmUserRepository.getUserByName(validUsername);
			fail();
		} catch (C3PRNoSuchUserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			fail();
		}finally{
			verifyMocks();
		}
	}
	
	public void testGetUserByName(){
		EasyMock.expect(userProvisioningManager.getUser(validUsername)).andReturn(csmUser);
		EasyMock.expect(csmUser.getEmailId()).andReturn(validEmail);
		EasyMock.expect(userDao.getByEmailAddress(validEmail)).andReturn(c3prUser);
		replayMocks();
		try {
			csmUserRepository.getUserByName(validUsername);
		}catch (Exception e) {
			fail();
		}finally{
			verifyMocks();
		}
	}
	
	public void testGetUsernameByIdInvalidLoginId(){
		try {
			EasyMock.expect(userProvisioningManager.getUserById(invalidLoginId)).andThrow(new CSObjectNotFoundException());
		} catch (CSObjectNotFoundException e1) {
			fail();
		}
		replayMocks();
		try {
			csmUserRepository.getUsernameById(invalidLoginId);
			fail();
		} catch (C3PRNoSuchUserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			fail();
		}finally{
			verifyMocks();
		}
	}
	
	public void testGetUsernameById(){
		try {
			EasyMock.expect(userProvisioningManager.getUserById(validLoginId)).andReturn(csmUser);
		} catch (CSObjectNotFoundException e1) {
			fail();
		}
		EasyMock.expect(csmUser.getLoginName()).andReturn(validUsername);
		replayMocks();
		try {
			assertEquals(validUsername, csmUserRepository.getUsernameById(validLoginId));
		}catch (Exception e) {
			fail();
		}finally{
			verifyMocks();
		}
	}
	
	public void testUserChangePasswordException(){
		EasyMock.expect(c3prUser.getLoginId()).andReturn(validLoginId);
		try {
			EasyMock.expect(userProvisioningManager.getUserById(validLoginId)).andReturn(csmUser);
		} catch (CSObjectNotFoundException e1) {
			fail();
		}
		c3prUser.resetToken();
		c3prUser.setPasswordLastSet(EasyMock.isA(Timestamp.class));
		EasyMock.expect(csmUser.getPassword()).andReturn(oldPassword);
		c3prUser.addPasswordToHistory(oldPassword, 1);
		EasyMock.expect(c3prUser.getSalt()).andReturn("salt_").times(2);
		csmUser.setPassword("salt_"+newPassword);
		userDao.save(c3prUser);
		try {
			userProvisioningManager.modifyUser(csmUser);
			EasyMock.expectLastCall().andThrow(new CSTransactionException());
		} catch (CSTransactionException e) {
			fail();
		}
		replayMocks();
		try {
			csmUserRepository.userChangePassword(c3prUser, newPassword, 1);
			fail();
		} catch (C3PRBaseRuntimeException e) {
			e.printStackTrace();
		}finally{
			verifyMocks();
		}
	}
	
	public void testUserChangePassword(){
		EasyMock.expect(c3prUser.getLoginId()).andReturn(validLoginId);
		try {
			EasyMock.expect(userProvisioningManager.getUserById(validLoginId)).andReturn(csmUser);
		} catch (CSObjectNotFoundException e1) {
			fail();
		}
		c3prUser.resetToken();
		c3prUser.setPasswordLastSet(EasyMock.isA(Timestamp.class));
		EasyMock.expect(csmUser.getPassword()).andReturn(oldPassword);
		c3prUser.addPasswordToHistory(oldPassword, 1);
		EasyMock.expect(c3prUser.getSalt()).andReturn("salt_").times(2);
		csmUser.setPassword("salt_"+newPassword);
		userDao.save(c3prUser);
		try {
			userProvisioningManager.modifyUser(csmUser);
		} catch (CSTransactionException e) {
			fail();
		}
		replayMocks();
		try {
			csmUserRepository.userChangePassword(c3prUser, newPassword, 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}finally{
			verifyMocks();
		}
	}
	
	public void testUserHasPasswordTrue(){
		EasyMock.expect(c3prUser.getLoginId()).andReturn(validLoginId);
		try {
			EasyMock.expect(userProvisioningManager.getUserById(validLoginId)).andReturn(csmUser);
		} catch (CSObjectNotFoundException e1) {
			fail();
		}
		try {
			EasyMock.expect(csmUser.getPassword()).andReturn(new StringEncrypter().encrypt(newPassword));
		} catch (EncryptionException e) {
			e.printStackTrace();
			fail();
		}
		EasyMock.expect(c3prUser.getSalt()).andReturn("");
		replayMocks();
		assertTrue(csmUserRepository.userHasPassword(c3prUser, newPassword));
		verifyMocks();
	}
	
	public void testUserHasPasswordFalse(){
		EasyMock.expect(c3prUser.getLoginId()).andReturn(validLoginId);
		try {
			EasyMock.expect(userProvisioningManager.getUserById(validLoginId)).andReturn(csmUser);
		} catch (CSObjectNotFoundException e1) {
			fail();
		}
		try {
			EasyMock.expect(csmUser.getPassword()).andReturn(new StringEncrypter().encrypt(oldPassword));
		} catch (EncryptionException e) {
			e.printStackTrace();
			fail();
		}
		EasyMock.expect(c3prUser.getSalt()).andReturn("");
		replayMocks();
		assertFalse(csmUserRepository.userHasPassword(c3prUser, newPassword));
		verifyMocks();
	}
	
	public void testUserHadPasswordTrue(){
		List<String> passwordHistory = new ArrayList<String>();
		try {
			passwordHistory.add(new StringEncrypter().encrypt(oldPassword));
			passwordHistory.add(new StringEncrypter().encrypt(newPassword));
		} catch (EncryptionException e) {
			e.printStackTrace();
			fail();
		}
		EasyMock.expect(c3prUser.getPasswordHistory()).andReturn(passwordHistory);
		replayMocks();
		assertTrue(csmUserRepository.userHadPassword(c3prUser, newPassword));
		verifyMocks();
	}
	
	public void testUserHadPasswordFalse(){
		List<String> passwordHistory = new ArrayList<String>();
		try {
			passwordHistory.add(new StringEncrypter().encrypt(oldPassword));
		} catch (EncryptionException e) {
			e.printStackTrace();
			fail();
		}
		EasyMock.expect(c3prUser.getPasswordHistory()).andReturn(passwordHistory);
		replayMocks();
		assertFalse(csmUserRepository.userHadPassword(c3prUser, newPassword));
		verifyMocks();
	}
	
	public void testSendUserMailException(){
		EasyMock.expect(userProvisioningManager.getUser(validUsername)).andReturn(csmUser);
		EasyMock.expect(csmUser.getEmailId()).andReturn(validEmail);
		EasyMock.expect(userDao.getByEmailAddress(validEmail)).andReturn(c3prUser);
		EasyMock.expect(c3prUser.getEmail()).andReturn(validEmail);
		mailSender.send(EasyMock.isA(SimpleMailMessage.class));
		EasyMock.expectLastCall().andThrow(new MailAuthenticationException(""));
		replayMocks();
		try {
			csmUserRepository.sendUserEmail(validUsername, "", "");
			fail();
		} catch (C3PRBaseRuntimeException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
			fail();
		}finally{
			verifyMocks();
		}
		
	}
	
	public void testSendUserMail(){
		EasyMock.expect(userProvisioningManager.getUser(validUsername)).andReturn(csmUser);
		EasyMock.expect(csmUser.getEmailId()).andReturn(validEmail);
		EasyMock.expect(userDao.getByEmailAddress(validEmail)).andReturn(c3prUser);
		mailSender.send(EasyMock.isA(SimpleMailMessage.class));
		EasyMock.expect(c3prUser.getEmail()).andReturn(validEmail);
		replayMocks();
		try {
			csmUserRepository.sendUserEmail(validUsername, "", "");
		}catch (RuntimeException e) {
			e.printStackTrace();
			fail();
		}finally{
			verifyMocks();
		}
	}
	
	public void testGetCSMUsersByGroupException1(){
		try {
			assertEquals(0, csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN).size());
		} catch (NullPointerException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	public void testGetCSMUsersByGroupException2(){
		EasyMock.expect(userProvisioningManager.getObjects(EasyMock.isA(GroupSearchCriteria.class))).andReturn(null);
		replayMocks();
		try {
			assertEquals(0, csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN).size());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}finally{
			verifyMocks();
		}
	}
	
	public void testGetCSMUsersByGroupNoUsersFound(){
		Group group = new Group();
		group.setGroupId(Long.parseLong("1"));
		EasyMock.expect(userProvisioningManager.getObjects(EasyMock.isA(GroupSearchCriteria.class))).andReturn(Arrays.asList(new Group[]{group}));
		try {
			EasyMock.expect(userProvisioningManager.getUsers("1")).andThrow(new CSObjectNotFoundException());
		} catch (CSObjectNotFoundException e1) {
			e1.printStackTrace();
			fail();
		}
		replayMocks();
		assertEquals(0, csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN).size());
		verifyMocks();
	}
	
	public void testGetCSMUsersByGroupUsersFound(){
		Group group = new Group();
		group.setGroupId(Long.parseLong("1"));
		EasyMock.expect(userProvisioningManager.getObjects(EasyMock.isA(GroupSearchCriteria.class))).andReturn(Arrays.asList(new Group[]{group}));
		try {
			EasyMock.expect(userProvisioningManager.getUsers("1")).andReturn(new HashSet<User>(Arrays.asList(new User[]{csmUser})));
		} catch (CSObjectNotFoundException e1) {
			e1.printStackTrace();
			fail();
		}
		replayMocks();
		assertEquals(1, csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN).size());
		verifyMocks();
	}
}
