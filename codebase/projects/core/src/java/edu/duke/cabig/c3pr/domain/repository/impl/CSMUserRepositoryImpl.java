package edu.duke.cabig.c3pr.domain.repository.impl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;

import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.dao.GroupSearchCriteria;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;
import gov.nih.nci.security.exceptions.CSTransactionException;
import gov.nih.nci.security.util.StringEncrypter;

public class CSMUserRepositoryImpl implements CSMUserRepository {

	private UserProvisioningManager userProvisioningManager;
	private UserDao userDao;
	private ResearchStaffDao researchStaffDao;
	private CSMObjectIdGenerator siteObjectIdGenerator;
	private MailSender mailSender;
	private SimpleMailMessage accountCreatedTemplateMessage;
	private Logger log = Logger.getLogger(CSMUserRepositoryImpl.class);

	public void createOrUpdateCSMUserAndGroupsForResearchStaff(final ResearchStaff researchStaff, String changeURL) {
		gov.nih.nci.security.authorization.domainobjects.User csmUser;
		/* this should be done by a validator */
		if (researchStaff.getEmailAddress() == null) {
			throw new C3PRBaseRuntimeException("Email address is required");
		} else if (researchStaff.getId() == null) {
			csmUser = createCSMUserForResearchStaff(researchStaff, changeURL);
		} else {
			csmUser = updateCSMUserForResearchStaff(researchStaff);
		}
		/* not sure why this gets done here */
		researchStaff.setLoginId(csmUser.getUserId().toString());
		createCSMUserGroupsForResearchStaff(researchStaff);
	}

	private void createCSMUserGroupsForResearchStaff(final ResearchStaff researchStaff) throws C3PRBaseRuntimeException{
		List<String> groupIds = new ArrayList<String>();
		for (C3PRUserGroupType group : researchStaff.getGroups()) {
			groupIds.add(group.getCode().toString());
		}
		assignUserToGroup(researchStaff.getLoginId(), groupIds.toArray(new String[groupIds.size()]));
		log.debug("Successfully assigned user to organization");
	}

	private void copyUserToCSMUser(User user, gov.nih.nci.security.authorization.domainobjects.User csmUser) {
		String emailId = user.getEmailAddress();
		csmUser.setLoginName(emailId);
		csmUser.setEmailId(emailId);
		csmUser.setPhoneNumber(user.getPhoneNumber());
		csmUser.setFirstName(user.getFirstName());
		csmUser.setLastName(user.getLastName());
		// psc does not use these
		// do we really need this? csmUser.setOrganization(researchStaff.getOrganization().getName());
		// or this? csmUser.setOrganization(researchStaff.getOrganization().getNciInstituteCode());
	}

	private gov.nih.nci.security.authorization.domainobjects.User createCSMUserForResearchStaff(final ResearchStaff researchStaff, String changeURL) {
		// assumes research staff id is null
		String emailId = researchStaff.getEmailAddress();
		gov.nih.nci.security.authorization.domainobjects.User csmUser;
		try {
			getCSMUserByName(emailId);
			throw new C3PRBaseRuntimeException("Couldn't add user: " + emailId + ": already exists.");
		} catch (C3PRNoSuchUserException e) {
			csmUser = new gov.nih.nci.security.authorization.domainobjects.User();
			copyUserToCSMUser(researchStaff, csmUser);
			csmUser.setPassword(encryptString(researchStaff.getSalt() + "obscurity"));
			createCSMUser(csmUser);
			researchStaffDao.save(researchStaff);
			sendUserEmail(emailId, "Your new caAERS account", "A new caAERS account has been created for you.\n"
					+ "\n"
					+ "You must change your password before you can login. In order to do so please visit this URL:\n"
					+ "\n"
					+ changeURL + "&token=" + userCreateToken(emailId) + "\n"
					+ "\n"
					+ "Regards\n"
					+ "The caAERS Notification System.\n");
			return csmUser;
		}
	}

	private gov.nih.nci.security.authorization.domainobjects.User updateCSMUserForResearchStaff(final ResearchStaff researchStaff) {
		String emailId = researchStaff.getEmailAddress();
		gov.nih.nci.security.authorization.domainobjects.User csmUser = getCSMUserByName(emailId);
		copyUserToCSMUser(researchStaff, csmUser);
		saveCSMUser(csmUser);
		researchStaffDao.save(researchStaff);
		return csmUser;
	}

	private void assignUserToGroup(final String userId, final String[] groupIds) throws C3PRBaseRuntimeException {
		try {
			userProvisioningManager.assignGroupsToUser(userId, groupIds);
		} catch (CSTransactionException e) {
			throw new C3PRBaseRuntimeException("Could not add user to group", e);
		}
	}

	private String getGroupIdByName(final String groupName){
		Group search = new Group();
		search.setGroupName(groupName);
		GroupSearchCriteria sc = new GroupSearchCriteria(search);
		Group returnGroup = (Group) userProvisioningManager.getObjects(sc).get(0);
		return returnGroup.getGroupId().toString();
	}

	private void createCSMUser(gov.nih.nci.security.authorization.domainobjects.User csmUser) {
		try {
			userProvisioningManager.createUser(csmUser);
		} catch (CSTransactionException e) {
			throw new C3PRBaseRuntimeException("Could not create user", e);
		}
	}

	private gov.nih.nci.security.authorization.domainobjects.User getCSMUserByName(String userName) {
		gov.nih.nci.security.authorization.domainobjects.User csmUser = userProvisioningManager.getUser(userName);
		if (csmUser == null) throw new C3PRNoSuchUserException("No such CSM user.");
		return csmUser;
	}

	private void saveCSMUser(gov.nih.nci.security.authorization.domainobjects.User csmUser) {
		try {
			userProvisioningManager.modifyUser(csmUser);
		} catch (CSTransactionException e) {
			throw new C3PRBaseRuntimeException("Couldn't save CSM user: ", e);
		}
	}

	public User getUserByName(String userName) {
		User user = userDao.getByEmailAddress(userName);
		if (user == null) throw new C3PRNoSuchUserException("No such user.");
		return user;
	}

	public void saveUser(User user) {
		// this should be the way its done, but its not
		userDao.save(user);
		// get the csm user, save or create
	}

	public String userCreateToken(String userName) {
		User user = getUserByName(userName);
		user.setTokenTime(new Timestamp(new Date().getTime()));
		user.setToken(encryptString(user.getSalt() + user.getTokenTime().toString()
				+ "random_string").replaceAll("\\W", "Q"));
		userDao.save(user);
		return user.getToken();
	}

	public void userChangePassword(String userName, String password, int maxHistorySize) {
		User user = getUserByName(userName);
		gov.nih.nci.security.authorization.domainobjects.User csmUser = getCSMUserByName(userName);
		user.resetToken();
		user.setPasswordLastSet(new Timestamp(new Date().getTime()));
		user.addPasswordToHistory(csmUser.getPassword(), maxHistorySize);
		csmUser.setPassword(user.getSalt() + password);
		userDao.save(user);
		saveCSMUser(csmUser);
	}

	public boolean userHasPassword(String userName, String password) {
		return encryptString(getUserByName(userName).getSalt()
				+ password).equals(getCSMUserByName(userName).getPassword());
	}

	public boolean userHadPassword(String userName, String password) {
		return getUserByName(userName).getPasswordHistory().contains(encryptString(password));
	}

	public void sendUserEmail(String userName, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(getUserByName(userName).getEmailAddress());
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

	// end

	@Required
	public void setUserProvisioningManager(final UserProvisioningManager userProvisioningManager) {
		this.userProvisioningManager = userProvisioningManager;
	}

	@Required
	public void setMailSender(final MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Required
	public void setAccountCreatedTemplateMessage(final SimpleMailMessage accountCreatedTemplateMessage) {
		this.accountCreatedTemplateMessage = accountCreatedTemplateMessage;
	}

	@Required
	public void setSiteObjectIdGenerator(final CSMObjectIdGenerator siteObjectIdGenerator) {
		this.siteObjectIdGenerator = siteObjectIdGenerator;
	}

	@Required
	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}

	@Required
	public void setResearchStaffDao(final ResearchStaffDao researchStaffDao) {
		this.researchStaffDao = researchStaffDao;
	}

	public class C3PRNoSuchUserException extends C3PRBaseRuntimeException{
		public C3PRNoSuchUserException(String message) {
			super(message);
		}
	}
}
