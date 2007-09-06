package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.exceptions.CSTransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.MailException;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Aug 24, 2007
 * Time: 9:35:57 AM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class PersonnelServiceImpl implements PersonnelService {

    private ResearchStaffDao researchStaffDao;
    private UserProvisioningManager userProvisioningManager;

    private MailSender mailSender;
    private SimpleMailMessage accountCreatedTemplateMessage;


    public void save(C3PRUser c3prUser) throws C3PRBaseException, C3PRBaseRuntimeException {

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
        //csmUser.setOrganization(c3prUser.get);
        csmUser.setPassword(c3prUser.getLastName());

        try {
            userProvisioningManager.createUser(csmUser);

            for(C3PRUserGroupType group : c3prUser.getGroups()){
                assignUserToGroup(c3prUser,group);
            }

            c3prUser.setLoginId(emailId);
            researchStaffDao.save(c3prUser);

            try {
                SimpleMailMessage msg = new SimpleMailMessage(this.accountCreatedTemplateMessage);
                msg.setTo(emailId);
                msg.setText("An account has been created for you.\n" +
                        " Username:" + csmUser.getLoginName() + " Password:" + csmUser.getPassword() + "" +
                        "\n -c3pr admin");
                this.mailSender.send(msg);
            } catch (MailException e) {
                throw new C3PRBaseRuntimeException("Could not send confirmation email to user",e);
            }


        } catch (CSTransactionException e) {
            throw new C3PRBaseException("Could not create user", e);
        }
    }


    public void assignUserToGroup(C3PRUser c3PRUser, C3PRUserGroupType groupName) throws C3PRBaseException {
        try {
            userProvisioningManager.assignUserToGroup(c3PRUser.getLoginId(), groupName.getCode());
        } catch (CSTransactionException e) {
            throw new C3PRBaseException("Could not add user to group", e);
        }
    }

    public UserProvisioningManager getUserProvisioningManager() {
        return userProvisioningManager;
    }

    public void setUserProvisioningManager(UserProvisioningManager userProvisioningManager) {
        this.userProvisioningManager = userProvisioningManager;
    }


    public ResearchStaffDao getResearchStaffDao() {
        return researchStaffDao;
    }

    public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
        this.researchStaffDao = researchStaffDao;
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
