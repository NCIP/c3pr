/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.dao.BaseInvestigatorDataContainerDao;
import edu.duke.cabig.c3pr.dao.BaseResearchStaffDataContainerDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.dao.PlannedNotificationDao;
import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.BaseContactMechanismDataContainer;
import edu.duke.cabig.c3pr.domain.BaseInvestigatorDataContainer;
import edu.duke.cabig.c3pr.domain.BaseResearchStaffDataContainer;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.LocalInvestigator;
import edu.duke.cabig.c3pr.domain.LocalPersonUser;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.domain.RemotePersonUser;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.domain.repository.impl.CSMUserRepositoryImpl.C3PRNoSuchUserException;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.utils.RecipientScheduledNotificationComparator;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.User;

/**
 * Created by IntelliJ IDEA. 
 * @author Vinay Gangoli, kherm 
 * 
 * Date: Aug 24, 2007 Time: 9:35:57 AM To change this template
 * use File | Settings | File Templates.
 */

public class PersonnelServiceImpl implements PersonnelService {

    private PersonUserDao personUserDao;
    
    private InvestigatorDao investigatorDao;

    private UserProvisioningManager userProvisioningManager;

    private PlannedNotificationDao plannedNotificationDao;
    
    private BaseResearchStaffDataContainerDao baseResearchStaffDataContainerDao;
    
    private BaseInvestigatorDataContainerDao baseInvestigatorDataContainerDao;
    
    private UserDao userDao;
    
    private Logger log = Logger.getLogger(PersonnelServiceImpl.class);
    

    public void setBaseResearchStaffDataContainerDao(
			BaseResearchStaffDataContainerDao baseResearchStaffDataContainerDao) {
		this.baseResearchStaffDataContainerDao = baseResearchStaffDataContainerDao;
	}

	public void setBaseInvestigatorDataContainerDao(
			BaseInvestigatorDataContainerDao baseInvestigatorDataContainerDao) {
		this.baseInvestigatorDataContainerDao = baseInvestigatorDataContainerDao;
	}

	public void save(Investigator inv) throws C3PRBaseException {
        log.debug("Saving Investigator");
        investigatorDao.save(inv);
    }

	/**
     * Will save Research Staff without CSM
     * 
     * @param staff
     * @throws C3PRBaseException
     * @throws C3PRBaseRuntimeException
     */
    public void save(PersonUser staff) throws C3PRBaseException {
        personUserDao.createResearchStaff(staff);
    }
    
    /**
     * Will save Research Staff and add appropriate data into CSM
     * 
     * @param staff
     * @throws C3PRBaseException
     * @throws C3PRBaseRuntimeException
     */
    public void saveUser(PersonUser staff) throws C3PRBaseException {
		personUserDao.createOrModifyPersonUser(staff, true, staff.getLoginId(), null);
    }    
    

    public Investigator merge(Investigator user) throws C3PRBaseException {
        return investigatorDao.merge(user);
    }

    public User getCSMUserByUsername(String userName) {
        return userProvisioningManager.getUser(userName);
    }

    /**
     * Get a list of csm groups for the user
     * 
     * @param user
     * @return
     * @throws C3PRBaseException
     */
    public List<C3PRUserGroupType> getGroups(C3PRUser user) throws C3PRBaseException {
        List<C3PRUserGroupType> groups = new ArrayList<C3PRUserGroupType>();

        try {
            Set<Group> csmGroups = userProvisioningManager.getGroups(user.getLoginId().toString());
            for (Group csmGroup : csmGroups) {
                if (C3PRUserGroupType.getByCode(csmGroup.getGroupName()) != null) {
                    groups.add(C3PRUserGroupType.getByCode(csmGroup.getGroupName()));
                }
            }
        }
        catch (Exception e) {
            log.warn("Error getting groups from CSM for user " + user.getFullName());
        }
        return groups;
    }

    /**
     * Get a list of csm groups for the user.
     * Used by the dashboardController
     * @param emailId
     * @return
     * @throws C3PRBaseException
     */
    public List<C3PRUserGroupType> getGroups(String emailId) throws C3PRBaseException {
        List<C3PRUserGroupType> groups = new ArrayList<C3PRUserGroupType>();

        try {
            Set<Group> csmGroups = userProvisioningManager.getGroups(emailId);
            for (Group csmGroup : csmGroups) {
                if (C3PRUserGroupType.getByCode(csmGroup.getGroupName()) != null) {
                    groups.add(C3PRUserGroupType.getByCode(csmGroup.getGroupName()));
                }
            }
        }
        catch (Exception e) {
            log.warn("Error getting groups from CSM for loginId: " + emailId);
        }
        return groups;
    }
    
    
    /*
     * used exclusively for the dashboard and inbox view of notifications
     */
    public List<RecipientScheduledNotification> getRecentNotifications(HttpServletRequest request) {
        gov.nih.nci.security.authorization.domainobjects.User user = (gov.nih.nci.security.authorization.domainobjects.User) request
                        .getSession().getAttribute("userObject");
        List<RecipientScheduledNotification> recipientScheduledNotificationsList = new ArrayList<RecipientScheduledNotification>();
        List<ScheduledNotification> scheduledNotificationsList = new ArrayList<ScheduledNotification>();
        PersonUser researchStaff = null;
    	try {
			researchStaff = (PersonUser)userDao.getByLoginId(user.getUserId().longValue());
			if(researchStaff == null){
				return recipientScheduledNotificationsList;
			}
			// getting notifications set up as userBasedNotifications
            for (UserBasedRecipient ubr : researchStaff.getUserBasedRecipients()) {
                recipientScheduledNotificationsList.addAll(ubr.getRecipientScheduledNotifications());
            }

            // getting notifications set up as roleBasedNotifications
            Iterator<C3PRUserGroupType> groupIterator = null;
            List<String> groupRoles = new ArrayList<String>();
            try {
                groupIterator = getGroups(user.getUserId().toString()).iterator();
            }
            catch (C3PRBaseException cbe) {
                log.error(cbe.getMessage());
            }
            while (groupIterator.hasNext()) {
                groupRoles.add(((C3PRUserGroupType) groupIterator.next()).name());
            }
            // groupRoles now contains all the roles of the logged in user
            for (PlannedNotification pn : plannedNotificationDao.getAll()) {
                for (RoleBasedRecipient rbr : pn.getRoleBasedRecipient()) {
                    if (groupRoles.contains(rbr.getRole())) {
                        recipientScheduledNotificationsList.addAll(rbr
                                        .getRecipientScheduledNotifications());
                    }
                }
            }
		} catch (C3PRNoSuchUserException e) {
			log.debug(e.getMessage());
			// for the admin case
            for (PlannedNotification pn : plannedNotificationDao.getAll()) {
                scheduledNotificationsList.addAll(pn.getScheduledNotifications());
            }
		}
        Collections.sort(recipientScheduledNotificationsList, new RecipientScheduledNotificationComparator());
        return recipientScheduledNotificationsList;
    }
    
    /** Returns a list of all the organizations associated with the staff.
     * 
     * @param User
     * @return List of HealthcareSites
     */
    public List<HealthcareSite> getUserOrganizations(User user){
    	PersonUser researchStaff = null;
    	try {
			researchStaff = (PersonUser)userDao.getByLoginId(user.getUserId().longValue());
		} catch (C3PRNoSuchUserException e) {
			log.debug(e.getMessage());
			return null;
		}
    	return researchStaff.getHealthcareSites();
    }

    // spring setters
    public UserProvisioningManager getUserProvisioningManager() {
        return userProvisioningManager;
    }

    public void setUserProvisioningManager(UserProvisioningManager userProvisioningManager) {
        this.userProvisioningManager = userProvisioningManager;
    }

    public PersonUserDao getPersonUserDao() {
		return personUserDao;
	}

	public void setPersonUserDao(PersonUserDao personUserDao) {
		this.personUserDao = personUserDao;
	}

	public InvestigatorDao getInvestigatorDao() {
		return investigatorDao;
	}

	public void setInvestigatorDao(InvestigatorDao investigatorDao) {
		this.investigatorDao = investigatorDao;
	}

	public PlannedNotificationDao getPlannedNotificationDao() {
		return plannedNotificationDao;
	}

	public void setPlannedNotificationDao(
			PlannedNotificationDao plannedNotificationDao) {
		this.plannedNotificationDao = plannedNotificationDao;
	}

	public BaseResearchStaffDataContainer convertLocalPersonUserToRemotePersonUser(
			LocalPersonUser localPersonUser,
			RemotePersonUser remotePersonUser) {
		BaseResearchStaffDataContainer baseResearchStaffDataContainer = baseResearchStaffDataContainerDao.getById(localPersonUser.getId());
		baseResearchStaffDataContainer.setDtype("Remote");
		baseResearchStaffDataContainer.setFirstName(remotePersonUser.getFirstName());
		baseResearchStaffDataContainer.setLastName(remotePersonUser.getLastName());
		baseResearchStaffDataContainer.setMiddleName(remotePersonUser.getMiddleName());
		baseResearchStaffDataContainer.setMaidenName(remotePersonUser.getMaidenName());
		baseResearchStaffDataContainer.setExternalId(remotePersonUser.getExternalId()); 
		// delete all the existing contact mechanisms and add new ones from the remote Research Staff
		baseResearchStaffDataContainer.removeContactMechanisms();
		for(ContactMechanism cm : remotePersonUser.getContactMechanisms()){
			BaseContactMechanismDataContainer baseContactMechanismDataContainer = new BaseContactMechanismDataContainer();
			baseContactMechanismDataContainer.setDtype("Remote");
			baseContactMechanismDataContainer.setType(cm.getType());
			baseContactMechanismDataContainer.setValue(cm.getValue());
			baseResearchStaffDataContainer.addContactMechanism(baseContactMechanismDataContainer);
		}
		baseResearchStaffDataContainerDao.save(baseResearchStaffDataContainer);
		return baseResearchStaffDataContainer;
	}
	
	public BaseInvestigatorDataContainer convertLocalInvestigatorToRemoteInvestigator(
			LocalInvestigator localInvestigator,
			RemoteInvestigator remoteInvestigator) {
		BaseInvestigatorDataContainer baseInvestigatorDataContainer = baseInvestigatorDataContainerDao.getById(localInvestigator.getId());
		baseInvestigatorDataContainer.setDtype("Remote");
		baseInvestigatorDataContainer.setFirstName(remoteInvestigator.getFirstName());
		baseInvestigatorDataContainer.setLastName(remoteInvestigator.getLastName());
		baseInvestigatorDataContainer.setMiddleName(remoteInvestigator.getMiddleName());
		baseInvestigatorDataContainer.setMaidenName(remoteInvestigator.getMaidenName());
		baseInvestigatorDataContainer.setExternalId(remoteInvestigator.getExternalId());
		// first remove all the existing contact mechanism of the investigator and add those from the
		// remove investigator
		baseInvestigatorDataContainer.removeContactMechanisms();
		for(ContactMechanism cm : remoteInvestigator.getContactMechanisms()){
			BaseContactMechanismDataContainer baseContactMechanismDataContainer = new BaseContactMechanismDataContainer();
			baseContactMechanismDataContainer.setDtype("Remote");
			baseContactMechanismDataContainer.setType(cm.getType());
			baseContactMechanismDataContainer.setValue(cm.getValue());
			baseInvestigatorDataContainer.addContactMechanism(baseContactMechanismDataContainer);
		}
		baseInvestigatorDataContainerDao.save(baseInvestigatorDataContainer);
		return baseInvestigatorDataContainer;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}
