package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.exceptions.CSTransactionException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Aug 24, 2007
 * Time: 9:35:57 AM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class PersonnelServiceImpl implements PersonnelService {

    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
    private UserProvisioningManager userProvisioningManager;

    public void save(C3PRUser c3prUser) throws C3PRBaseException {
        healthcareSiteInvestigatorDao.save(c3prUser);

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
        //user.setOrganization();
        csmUser.setPassword(c3prUser.getLastName());

        try {
            userProvisioningManager.createUser(csmUser);
            c3prUser.setLoginId(emailId);
            healthcareSiteInvestigatorDao.save(c3prUser);
        } catch (CSTransactionException e) {
            throw new C3PRBaseException("Could not create user", e);
        }
    }


    public void assignUserToGroup(C3PRUser c3PRUser, C3PRUserGroupType groupName) throws C3PRBaseException {
        try {
            userProvisioningManager.assignUserToGroup(c3PRUser.getLoginId(), groupName.name());
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

    public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
        return healthcareSiteInvestigatorDao;
    }

    public void setHealthcareSiteInvestigatorDao(HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
        this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
    }
}
