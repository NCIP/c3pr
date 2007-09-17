package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.dao.GroupSearchCriteria;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;
import gov.nih.nci.security.exceptions.CSTransactionException;
import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Aug 24, 2007
 * Time: 9:35:57 AM
 * To change this template use File | Settings | File Templates.
 */

public class PersonnelServiceImpl implements PersonnelService {

    private ResearchStaffDao dao;

    private UserProvisioningManager userProvisioningManager;
    private CSMObjectIdGenerator siteObjectIdGenerator;

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

        csmUser.setFirstName(c3prUser.getFirstName());
        csmUser.setLastName(c3prUser.getLastName());
        csmUser.setPassword(c3prUser.getLastName());

        //do this last. Any exception will rollback the c3pr transaction
        try {
            userProvisioningManager.createUser(csmUser);
            c3prUser.setLoginId(csmUser.getUserId().toString());
            log.debug("Saving c3pr user");
            dao.save(c3prUser);

            for (C3PRUserGroupType group : c3prUser.getGroups()) {
                assignUserToGroup(csmUser, group.getCode());
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
        try {
            save((C3PRUser) staff);
        } catch (C3PRBaseRuntimeException e) {
            //ignore because its a mail send exception
        }

        try {
            User csmUser = getCSMUser(staff);
            csmUser.setOrganization(staff.getHealthcareSite().getNciInstituteCode());
            assignUserToGroup(csmUser,siteObjectIdGenerator.generateId(staff.getHealthcareSite()) );
            log.debug("Successfully assigned user to organization group" + siteObjectIdGenerator.generateId(staff.getHealthcareSite()) );
        } catch (CSObjectNotFoundException e) {
            new C3PRBaseException("Could not assign user to organization group.");
        }

    }

    public void merge(Investigator user) throws C3PRBaseException, C3PRBaseRuntimeException{
        dao.save((C3PRUser)user);
    }

    public void merge(ResearchStaff user) throws C3PRBaseException, C3PRBaseRuntimeException{
        dao.save((C3PRUser)user);
    }

    private void assignUserToGroup(User csmUser, String groupName) throws C3PRBaseException {
        Set<String> groups = new HashSet<String>();
        try {
            Set<Group> existingSet = userProvisioningManager.getGroups(csmUser.getUserId().toString());
            for(Group existingGroup: existingSet){
                groups.add(existingGroup.getGroupId().toString());
            }
            groups.add(getGroupIdByName(groupName));

            userProvisioningManager.assignGroupsToUser(csmUser.getUserId().toString(),groups.toArray(new String[groups.size()]));
        } catch (Exception e) {
            throw new C3PRBaseException("Could not add user to group", e);
        }
    }

    private User getCSMUser(C3PRUser user) throws CSObjectNotFoundException{
        return userProvisioningManager.getUserById(user.getLoginId());
    }

    private String getGroupIdByName(String groupName){
        Group search = new Group();
        search.setGroupName(groupName);
        GroupSearchCriteria sc  = new GroupSearchCriteria(search);
        Group returnGroup = (Group)userProvisioningManager.getObjects(sc).get(0);
        return returnGroup.getGroupId().toString();
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


    public CSMObjectIdGenerator getSiteObjectIdGenerator() {
        return siteObjectIdGenerator;
    }

    public void setSiteObjectIdGenerator(CSMObjectIdGenerator siteObjectIdGenerator) {
        this.siteObjectIdGenerator = siteObjectIdGenerator;
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
