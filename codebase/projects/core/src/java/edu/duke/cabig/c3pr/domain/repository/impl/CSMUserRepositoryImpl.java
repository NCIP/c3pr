/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.repository.impl;


import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.dao.GroupSearchCriteria;
import gov.nih.nci.security.dao.UserSearchCriteria;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;
import gov.nih.nci.security.exceptions.CSTransactionException;
import gov.nih.nci.security.util.StringEncrypter;

public class CSMUserRepositoryImpl implements CSMUserRepository {

	private UserProvisioningManager userProvisioningManager;
	private UserDao userDao;
	private MailSender mailSender;
	private Logger log = Logger.getLogger(CSMUserRepositoryImpl.class);

	public gov.nih.nci.security.authorization.domainobjects.User getCSMUserByName(String userName) {
		gov.nih.nci.security.authorization.domainobjects.User csmUser = userProvisioningManager.getUser(userName);
		if (csmUser == null) throw new C3PRNoSuchUserException("No such CSM user.");
		return csmUser;
	}
	
	public Set<gov.nih.nci.security.authorization.domainobjects.User> getCSMUsersByGroup(C3PRUserGroupType group) {
		try {
			return userProvisioningManager.getUsers(getGroupIdByName(group.getCode()));
		} catch (Exception e) {
			return new HashSet<gov.nih.nci.security.authorization.domainobjects.User>();
		}
	}
	
	/**
	 * @param groupName
	 * @return
	 */
	private String getGroupIdByName(String groupName) {
		Group search = new Group();
		search.setGroupName(groupName);
		GroupSearchCriteria sc = new GroupSearchCriteria(search);
		Group returnGroup = (Group) userProvisioningManager.getObjects(sc).get(0);
		return returnGroup.getGroupId().toString();
	}

	public User getUserByName(String userName) {
		gov.nih.nci.security.authorization.domainobjects.User csmUser= getCSMUserByName(userName);
		User user = userDao.getByLoginId(csmUser.getUserId().longValue());
		if (user == null) throw new C3PRNoSuchUserException("No such user.");
		return user;
	}
	
	public String getUsernameById(String loginId) {
		gov.nih.nci.security.authorization.domainobjects.User csmUser=getCSMUserById(loginId);
		return csmUser.getLoginName();
	}
	
	/** Gets all the users in CSM who match any of the passed in criteria. Like a searchByExample.
 	 * 
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param loginName
	 * @return
	 */
	public List<gov.nih.nci.security.authorization.domainobjects.User> searchCSMUsers(String firstName, 
			String lastName, String emailAddress, String loginName) {
		gov.nih.nci.security.authorization.domainobjects.User csmUser = new gov.nih.nci.security.authorization.domainobjects.User();
		
		if(StringUtils.isNotEmpty(firstName)){
			csmUser.setFirstName(firstName);
		}
		if(StringUtils.isNotEmpty(lastName)){
			csmUser.setLastName(lastName);
		}
		if(StringUtils.isNotEmpty(emailAddress)){
			csmUser.setEmailId(emailAddress);
		}
		if(StringUtils.isNotEmpty(loginName)){
			csmUser.setLoginName(loginName);
		}
		
		UserSearchCriteria usc = new UserSearchCriteria(csmUser);
		List<gov.nih.nci.security.authorization.domainobjects.User> userList = userProvisioningManager.getObjects(usc);
		return userList;
	}
	
	
//	public List<gov.nih.nci.security.authorization.domainobjects.User> searchCSMUsersByOrganization(String firstName, String lastName, String emailAddress, String loginName, String orgId) {
//	}
	
	private gov.nih.nci.security.authorization.domainobjects.User getCSMUserById(String loginId){
		try {
			return userProvisioningManager.getUserById(loginId);
		} catch (CSObjectNotFoundException e) {
			throw new C3PRNoSuchUserException("No such CSM user.");
		}
	}
	
	private void saveCSMUser(gov.nih.nci.security.authorization.domainobjects.User csmUser) {
		try {
			userProvisioningManager.modifyUser(csmUser);
		} catch (CSTransactionException e) {
			throw new C3PRBaseRuntimeException("Couldn't save CSM user: ", e);
		}
	}

	public void userChangePassword(User user, String password, int maxHistorySize) {
		gov.nih.nci.security.authorization.domainobjects.User csmUser = getCSMUserById(user.getLoginId());
		user.resetToken();
		user.setPasswordLastSet(new Timestamp(new Date().getTime()));
		user.addPasswordToHistory(csmUser.getPassword(), maxHistorySize);
		csmUser.setPassword((StringUtils.isEmpty(user.getSalt()) ? "" : user.getSalt() ) + password);
		userDao.save(user);
		saveCSMUser(csmUser);
	}

	public boolean userHasPassword(User user, String password) {
		String loginId = user.getLoginId();
		return encryptString(user.getSalt()
				+ password).equals(getCSMUserById(loginId).getPassword());
	}

	public boolean userHadPassword(User user, String password) {
		return user.getPasswordHistory().contains(encryptString(password));
	}

	public void sendUserEmail(String userName, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(getUserByName(userName).getEmail());
			message.setSubject(subject);
			message.setText(text);
			mailSender.send(message);
		} catch (MailException e) {
			throw new C3PRBaseRuntimeException("Could not send email to user.", e);
		}
	}

	private String encryptString(String string) {
		try {
			return new StringEncrypter().encrypt(string);
		} catch (StringEncrypter.EncryptionException e) {
			throw new C3PRBaseRuntimeException("not able to encrypt string");
		}
	}
	
	@Required
	public void setUserProvisioningManager(final UserProvisioningManager userProvisioningManager) {
		this.userProvisioningManager = userProvisioningManager;
	}

	@Required
	public void setMailSender(final MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Required
	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}

	public class C3PRNoSuchUserException extends C3PRBaseRuntimeException{
		public C3PRNoSuchUserException(String message) {
			super(message);
		}
	}
}
