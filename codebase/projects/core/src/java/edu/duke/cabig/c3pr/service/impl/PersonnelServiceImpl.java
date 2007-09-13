package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.OrganizationService;
import edu.duke.cabig.c3pr.service.PersonnelService;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;
import gov.nih.nci.security.exceptions.CSTransactionException;
import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Aug 24, 2007
 * Time: 9:35:57 AM
 * To change this template use File | Settings | File Templates.
 */

public class PersonnelServiceImpl implements PersonnelService {

    private ResearchStaffDao dao;
    private OrganizationService organizationService;
    private UserProvisioningManager userProvisioningManager;

    private MailSender mailSender;
    private SimpleMailMessage accountCreatedTemplateMessage;

    private Logger log = Logger.getLogger(PersonnelServiceImpl.class);

    private void save(C3PRUser c3prUser) throws C3PRBaseException, C3PRBaseRuntimeException {

        gov.nih.nci.security.authorization.domainobjects.User csmUser = new gov.nih.nci.security.authorization.domainobjects.User();
        String emailId = null;

        for (ContactMechanism cm : c3prUser.getContactMechanisms()) {
            if (cm.getType().equals(ContactMechanismType.EMAIL)) {
                emailId = cm.getValue();
                csmUser.setLoginName(emailId);
                csmUser.setEmailId(emailId);
                c3prUser.setLoginId(csmUser.getLoginName());
            } else if (cm.getType().equals(ContactMechanismType.PHONE))
                csmUser.setPhoneNumber(cm.getValue());
        }

        if (emailId == null)
            throw new C3PRBaseException("Email address is required");

        csmUser.setFirstName(c3prUser.getFirstName());
        csmUser.setLastName(c3prUser.getLastName());
        csmUser.setPassword(c3prUser.getLastName());


        c3prUser.setLoginId(emailId);
        log.debug("Saving c3pr user");
        dao.save(c3prUser);

        //do this last. Any exception will rollback the c3pr transaction
        try {
            userProvisioningManager.createUser(csmUser);

            for (C3PRUserGroupType group : c3prUser.getGroups()) {
                assignUserToGroup(c3prUser, group.getCode());
            }
        } catch (CSTransactionException e) {
            throw new C3PRBaseException("Could not create user", e);
        }

        try {
            SimpleMailMessage msg = new SimpleMailMessage(this.accountCreatedTemplateMessage);
            msg.setTo(emailId);
            msg.setText("An account has been created for you.\n" +
                    " Username:" + csmUser.getLoginName() + " Password:" + csmUser.getPassword() + "" +
                    "\n -c3pr admin");
            this.mailSender.send(msg);
        } catch (MailException e) {
            throw new C3PRBaseRuntimeException("Could not send confirmation email to user", e);
        }


    }

    public void save(Investigator inv) throws C3PRBaseException, C3PRBaseRuntimeException {
        log.debug("Saving Investigator");
        save((C3PRUser) inv);
    }


    public void save(ResearchStaff staff) throws C3PRBaseException, C3PRBaseRuntimeException {
        log.debug("Saving Research Staff");
        save((C3PRUser) staff);

        try {
            User csmUser = userProvisioningManager.getUserById(staff.getLoginId());
            csmUser.setOrganization(staff.getHealthcareSite().getNciInstituteCode());

        } catch (CSObjectNotFoundException e) {
            new C3PRBaseException("Could not assign user to organization group.");
        }
        log.debug("Successfully assigned user to organization");

        Group grp = organizationService.createGroupForOrganization(staff.getHealthcareSite());
        assignUserToGroup(staff, grp.getGroupName());
    }

    public void merge(Investigator user) throws C3PRBaseException, C3PRBaseRuntimeException{
    	 dao.save((C3PRUser)user);
    }
    
    public void merge(ResearchStaff user) throws C3PRBaseException, C3PRBaseRuntimeException{
   	 dao.save((C3PRUser)user);
   }
    
    private void assignUserToGroup(C3PRUser c3PRUser, String groupName) throws C3PRBaseException {
        try {
            userProvisioningManager.assignUserToGroup(c3PRUser.getLoginId(), groupName);
        } catch (CSTransactionException e) {
            throw new C3PRBaseException("Could not add user to group", e);
        }
    }


    //spring settters
    public UserProvisioningManager getUserProvisioningManager() {
        return userProvisioningManager;
    }

    public void setUserProvisioningManager(UserProvisioningManager userProvisioningManager) {
        this.userProvisioningManager = userProvisioningManager;
    }


    public ResearchStaffDao getDao() {
        return dao;
    }

    public void setDao(ResearchStaffDao dao) {
        this.dao = dao;
    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public SimpleMailMessage getAccountCreatedTemplateMessage() {
        return accountCreatedTemplateMessage;
    }

    public void setAccountCreatedTemplateMessage(SimpleMailMessage accountCreatedTemplateMessage) {
        this.accountCreatedTemplateMessage = accountCreatedTemplateMessage;
    }
}
