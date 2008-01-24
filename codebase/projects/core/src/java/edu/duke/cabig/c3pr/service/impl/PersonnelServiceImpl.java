package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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


    private Logger log = Logger.getLogger(PersonnelServiceImpl.class);

    private void save(C3PRUser c3prUser, gov.nih.nci.security.authorization.domainobjects.User csmUser)
            throws C3PRBaseException, MailException {
        try {
            if (csmUser == null) {
                csmUser = new gov.nih.nci.security.authorization.domainobjects.User();
                populateCSMUser(c3prUser, csmUser);
                userProvisioningManager.createUser(csmUser);
            } else {
                populateCSMUser(c3prUser, csmUser);
                userProvisioningManager.modifyUser(csmUser);
            }

            log.debug("Saving c3pr user");
            dao.save(c3prUser);
            c3prUser.setLoginId(csmUser.getUserId().toString());

            for (C3PRUserGroupType group : c3prUser.getGroups()) {
                assignUserToGroup(csmUser, group.getCode());
            }
        } catch (CSTransactionException e) {
            throw new C3PRBaseException("Could not create user", e);
        }
    }


    public void save(Investigator inv) throws C3PRBaseException {
        log.debug("Saving Investigator");
        dao.save(inv);
    }


    public void save(ResearchStaff staff) throws C3PRBaseException {
        save(staff, null);

        try {
            User csmUser = getCSMUser(staff);
            csmUser.setOrganization(staff.getHealthcareSite().getNciInstituteCode());
            assignUserToGroup(csmUser, siteObjectIdGenerator.generateId(staff.getHealthcareSite()));
            log.debug("Successfully assigned user to organization group" + siteObjectIdGenerator.generateId(staff.getHealthcareSite()));
        } catch (CSObjectNotFoundException e) {
            new C3PRBaseException("Could not assign user to organization group.");
        }
    }

    public void merge(Investigator user) throws C3PRBaseException {
        dao.save((C3PRUser) user);
    }

    public void merge(ResearchStaff staff) throws C3PRBaseException {
        try {
            User csmUser = getCSMUser(staff);
            save(staff, csmUser);
        } catch (CSObjectNotFoundException e) {
            new C3PRBaseException("Could not save Research staff" + e.getMessage());
        }

    }

    private void assignUserToGroup(User csmUser, String groupName) throws C3PRBaseException {
        Set<String> groups = new HashSet<String>();
        try {
            Set<Group> existingSet = userProvisioningManager.getGroups(csmUser.getUserId().toString());
            for (Group existingGroup : existingSet) {
                groups.add(existingGroup.getGroupId().toString());
            }
            groups.add(getGroupIdByName(groupName));

            userProvisioningManager.assignGroupsToUser(csmUser.getUserId().toString(), groups.toArray(new String[groups.size()]));
        } catch (Exception e) {
            throw new C3PRBaseException("Could not add user to group", e);
        }
    }

    private User getCSMUser(C3PRUser user) throws CSObjectNotFoundException {
        return userProvisioningManager.getUserById(user.getLoginId());
    }

    public List<C3PRUserGroupType> getGroups(C3PRUser user) throws C3PRBaseException {
        List<C3PRUserGroupType> groups = new ArrayList<C3PRUserGroupType>();

        try {
            Set<Group> csmGroups = userProvisioningManager.getGroups(user.getLoginId().toString());
            for (Group csmGroup : csmGroups) {
            	if(C3PRUserGroupType.getByCode(csmGroup.getGroupName()) != null){
            		groups.add(C3PRUserGroupType.getByCode(csmGroup.getGroupName()));
            	}                
            }
        } catch (Exception e) {
            log.warn("Error getting groups from CSM for user " + user.getFullName());
        }

        return groups;

    }

    private String getGroupIdByName(String groupName) {
        Group search = new Group();
        search.setGroupName(groupName);
        GroupSearchCriteria sc = new GroupSearchCriteria(search);
        Group returnGroup = (Group) userProvisioningManager.getObjects(sc).get(0);
        return returnGroup.getGroupId().toString();
    }


    private void populateCSMUser(C3PRUser c3prUser, gov.nih.nci.security.authorization.domainobjects.User csmUser) {
        csmUser.setFirstName(c3prUser.getFirstName());
        csmUser.setLastName(c3prUser.getLastName());
        csmUser.setPassword(c3prUser.getLastName());


        for (ContactMechanism cm : c3prUser.getContactMechanisms()) {
            if (cm.getType().equals(ContactMechanismType.EMAIL)) {
                csmUser.setLoginName(cm.getValue());
                csmUser.setEmailId(cm.getValue());
            }
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


    public CSMObjectIdGenerator getSiteObjectIdGenerator() {
        return siteObjectIdGenerator;
    }

    public void setSiteObjectIdGenerator(CSMObjectIdGenerator siteObjectIdGenerator) {
        this.siteObjectIdGenerator = siteObjectIdGenerator;
    }


}
