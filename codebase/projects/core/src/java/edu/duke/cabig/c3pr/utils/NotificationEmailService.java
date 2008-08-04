package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.impl.PersonnelServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;

/**
 * Author: vGangoli Date: Nov 30, 2007
 */

public class NotificationEmailService {

    private MailSender mailSender;

    private SimpleMailMessage accountCreatedTemplateMessage;

//    PersonnelServiceImpl personnelServiceImpl;

    private Logger log = Logger.getLogger(StudyTargetAccrualNotificationEmail.class);

    private HealthcareSiteDao healthcareSiteDao;
    
    private PersonnelServiceImpl personnelServiceImpl;
    
    private Configuration configuration;
    
    /**
     * This method is reponsible for figuring out the email address from notifications and sending
     * out the notification email using Javamail.
     * 
     * @param study
     */
    public void sendEmail(PlannedNotification plannedNotification) {
        List<String> emailList = null;
        emailList = generateEmailList(plannedNotification);
        for (String emailAddress : emailList) {
            try {
                SimpleMailMessage msg = new SimpleMailMessage(this.accountCreatedTemplateMessage);
                msg.setSubject(plannedNotification.getTitle());
                msg.setTo(emailAddress);
                msg.setText(plannedNotification.getMessage());
                log.debug("Trying to send study status change notification email");
                this.mailSender.send(msg);
            }
            catch (MailException e) {
                log.error("Could not send email due to  " + e.getMessage());
                // just log it for now
            }
        }
    }

    /**
     * This method generates a list of emails by getting the emailaddresses from all the email based
     * Recepients and Role based Recepients for a given Notification. The role based recepient only
     * has the role String...the corresponding personnel in that role for that study have to be
     * retrieved and their email addresses have to be addded to the finalList.
     */
    public List<String> generateEmailList(PlannedNotification plannedNotification) {
        List<String> emailList = new ArrayList<String>();
    	emailList.addAll(getEmailsFromRoleBasedRecipient(plannedNotification));
    	emailList.addAll(getEmailsFromUserBasedRecipient(plannedNotification));
//    	emailList.addAll(getEmailsFromContactMechanismBasedRecipient(plannedNotification));
    	
    	return emailList;
        
    }

    public List<String> getEmailsFromRoleBasedRecipient(PlannedNotification plannedNotification) {
        List<String> returnList = new ArrayList<String>();
        List<C3PRUserGroupType> groupList = null;
        List<ResearchStaff> rStaffList = null;
        rStaffList = getHostingHealthcareSite().getResearchStaffs();

        for (ResearchStaff rs : rStaffList) {
            try {
                groupList = personnelServiceImpl.getGroups(rs);
            }
            catch (C3PRBaseException e) {
                log.error("NotificationEmailService - personnelServiceImpl.getGroups():FAILED");
                log.error(e.getMessage());
            }
             //Handle the investigator code seperately 
            if (groupList != null) {
                for (C3PRUserGroupType group : groupList) {
                	for(RoleBasedRecipient rbr: plannedNotification.getRoleBasedRecipient()){
                		if(rbr.getRole().equalsIgnoreCase(group.getCode())){
                            returnList.addAll(getEmailAddressesFromResearchStaff(rs));
                        }
                	}
                }
            }
        }
        return returnList;
    }

    //merge this with getEmailAddressesFromInvestigator
    public List<String> getEmailAddressesFromResearchStaff(ResearchStaff rs) {
        List<String> returnList = new ArrayList<String>();
        for (ContactMechanism cm : rs.getContactMechanisms()) {
            if (cm.getType().equals(ContactMechanismType.EMAIL)) {
                returnList.add(cm.getValue());
            }
        }
        return returnList;
    }
    
    public List<String> getEmailAddressesFromInvestigator(Investigator inv) {
        List<String> returnList = new ArrayList<String>();
        for (ContactMechanism cm : inv.getContactMechanisms()) {
            if (cm.getType().equals(ContactMechanismType.EMAIL)) {
                returnList.add(cm.getValue());
            }
        }
        return returnList;
    }

    
    public List<String> getEmailsFromUserBasedRecipient(PlannedNotification plannedNotification){
    	List<String> returnList = new ArrayList<String>();
    	
    	for(UserBasedRecipient ubr: plannedNotification.getUserBasedRecipient()){
    		if(ubr.getResearchStaff() != null){
    			returnList.addAll(getEmailAddressesFromResearchStaff(ubr.getResearchStaff()));
    		}
            if(ubr.getInvestigator() != null){
            	returnList.addAll(getEmailAddressesFromInvestigator(ubr.getInvestigator()));
            }
    	}
    	return returnList;
    }
    
    
    public HealthcareSite getHostingHealthcareSite(){
    	return healthcareSiteDao.getByNciInstituteCode(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE));
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

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public PersonnelServiceImpl getPersonnelServiceImpl() {
		return personnelServiceImpl;
	}

	public void setPersonnelServiceImpl(PersonnelServiceImpl personnelServiceImpl) {
		this.personnelServiceImpl = personnelServiceImpl;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

/*    public PersonnelServiceImpl getPersonnelServiceImpl() {
        return personnelServiceImpl;
    }

    public void setPersonnelServiceImpl(PersonnelServiceImpl personnelServiceImpl) {
        this.personnelServiceImpl = personnelServiceImpl;
    }*/
}
