package edu.duke.cabig.c3pr.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.dao.InvestigatorConverterDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.dao.PlannedNotificationDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffConverterDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.ConverterInvestigator;
import edu.duke.cabig.c3pr.domain.ConverterResearchStaff;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.LocalInvestigator;
import edu.duke.cabig.c3pr.domain.LocalResearchStaff;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.domain.repository.impl.CSMUserRepositoryImpl.C3PRNoSuchUserException;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.utils.RecipientScheduledNotificationComparator;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;
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

    private ResearchStaffDao researchStaffDao;
    
    private InvestigatorDao investigatorDao;

    private UserProvisioningManager userProvisioningManager;

    private CSMObjectIdGenerator siteObjectIdGenerator;
    
    private PlannedNotificationDao plannedNotificationDao;
    
    private ResearchStaffConverterDao researchStaffConverterDao;
    
    private InvestigatorConverterDao investigatorConverterDao;
    
    private UserDao userDao;
    
    private Logger log = Logger.getLogger(PersonnelServiceImpl.class);
    
    public InvestigatorConverterDao getInvestigatorConverterDao() {
		return investigatorConverterDao;
	}

	public void setInvestigatorConverterDao(
			InvestigatorConverterDao investigatorConverterDao) {
		this.investigatorConverterDao = investigatorConverterDao;
	}

	public ResearchStaffConverterDao getResearchStaffConverterDao() {
		return researchStaffConverterDao;
	}

	public void setResearchStaffConverterDao(
			ResearchStaffConverterDao researchStaffConverterDao) {
		this.researchStaffConverterDao = researchStaffConverterDao;
	}

    public void save(Investigator inv) throws C3PRBaseException {
        log.debug("Saving Investigator");
        investigatorDao.save(inv);
    }

    public void save(ResearchStaff staff) throws C3PRBaseException {
        researchStaffDao.saveResearchStaff(staff);
    }

    public Investigator merge(Investigator user) throws C3PRBaseException {
        return investigatorDao.merge(user);
    }

    public void merge(ResearchStaff staff) throws C3PRBaseException {
        researchStaffDao.mergeResearchStaffAndCsmData(staff);
    }

    public User getCSMUserByUsername(String userName) {
        return userProvisioningManager.getUser(userName);
    }

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
        ResearchStaff researchStaff = null;
    	try {
			researchStaff = (ResearchStaff)userDao.getByLoginId(user.getUserId().longValue());
			// getting notifications set up as userBasedNotifications
            for (UserBasedRecipient ubr : researchStaff.getUserBasedRecipient()) {
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
    
    
    public HealthcareSite getLoggedInUsersOrganization(HttpServletRequest request){
    	gov.nih.nci.security.authorization.domainobjects.User user = (gov.nih.nci.security.authorization.domainobjects.User) request
        																					.getSession().getAttribute("userObject");
    	
    	ResearchStaff researchStaff = null;
    	try {
			researchStaff = (ResearchStaff)userDao.getByLoginId(user.getUserId().longValue());
		} catch (C3PRNoSuchUserException e) {
			log.debug(e.getMessage());
			return null;
		}
    	return researchStaff.getHealthcareSite();
    }

    // spring settters
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

	public InvestigatorDao getInvestigatorDao() {
		return investigatorDao;
	}

	public void setInvestigatorDao(InvestigatorDao investigatorDao) {
		this.investigatorDao = investigatorDao;
	}

	public CSMObjectIdGenerator getSiteObjectIdGenerator() {
        return siteObjectIdGenerator;
    }

    public void setSiteObjectIdGenerator(CSMObjectIdGenerator siteObjectIdGenerator) {
        this.siteObjectIdGenerator = siteObjectIdGenerator;
    }

	public PlannedNotificationDao getPlannedNotificationDao() {
		return plannedNotificationDao;
	}

	public void setPlannedNotificationDao(
			PlannedNotificationDao plannedNotificationDao) {
		this.plannedNotificationDao = plannedNotificationDao;
	}

	public ConverterResearchStaff convertLocalResearchStaffToRemoteResearchStaff(
			LocalResearchStaff localResearchStaff,
			RemoteResearchStaff remoteResearchStaff) {
		ConverterResearchStaff converterResearchStaff = researchStaffConverterDao.getById(localResearchStaff.getId());
		converterResearchStaff.setDtype("Remote");
		converterResearchStaff.setFirstName(remoteResearchStaff.getFirstName());
		converterResearchStaff.setLastName(remoteResearchStaff.getLastName());
		converterResearchStaff.setMiddleName(remoteResearchStaff.getMiddleName());
		converterResearchStaff.setMaidenName(remoteResearchStaff.getMaidenName());
		converterResearchStaff.setUniqueIdentifier(remoteResearchStaff.getExternalId());
		researchStaffConverterDao.save(converterResearchStaff);
		return converterResearchStaff;
	}
	
	public ConverterInvestigator convertLocalInvestigatorToRemoteInvestigator(
			LocalInvestigator localInvestigator,
			RemoteInvestigator remoteInvestigator) {
		ConverterInvestigator converterInvestigator = investigatorConverterDao.getById(localInvestigator.getId());
		converterInvestigator.setDtype("Remote");
		converterInvestigator.setFirstName(remoteInvestigator.getFirstName());
		converterInvestigator.setLastName(remoteInvestigator.getLastName());
		converterInvestigator.setMiddleName(remoteInvestigator.getMiddleName());
		converterInvestigator.setMaidenName(remoteInvestigator.getMaidenName());
		converterInvestigator.setUniqueIdentifier(remoteInvestigator.getExternalId());
		investigatorConverterDao.save(converterInvestigator);
		return converterInvestigator;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}
