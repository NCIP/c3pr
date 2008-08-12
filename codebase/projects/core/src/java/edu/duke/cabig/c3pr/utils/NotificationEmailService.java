package edu.duke.cabig.c3pr.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.PlannedNotificationDao;
import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.impl.PersonnelServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Author: vGangoli Date: Nov 30, 2007
 */

public class NotificationEmailService {

    private MailSender mailSender;

    private SimpleMailMessage accountCreatedTemplateMessage;

    private Logger log = Logger.getLogger(StudyTargetAccrualNotificationEmail.class);

    private HealthcareSiteDao healthcareSiteDao;
    
    private PersonnelServiceImpl personnelServiceImpl;
    
    private Configuration configuration;
    
    private PlannedNotificationDao plannedNotificationDao;
    
    /**
     * This method is reponsible for figuring out the email address from notifications and sending
     * out the notification email using Javamail.
     * 
     * @param study
     */
    public void sendEmail(PlannedNotification plannedNotification, Study study) {
    	log.debug(this.getClass().getName() + ": Entering sendEmail()");
        List<String> emailList = null;
        //composing the message to be sent out
        String composedMessage = applyRuntimeReplacementsForStudyStatusEmailMessage(plannedNotification.getMessage(), study);
        //saving the ScheduledNotification
        addScheduledNotification(plannedNotification, composedMessage);
        plannedNotificationDao.merge(plannedNotification);
        emailList = generateEmailList(plannedNotification);
        for (String emailAddress : emailList) {
            try {
                SimpleMailMessage msg = new SimpleMailMessage(this.accountCreatedTemplateMessage);
                msg.setSubject(plannedNotification.getTitle());
                msg.setTo(emailAddress);
                msg.setText(composedMessage);
                log.debug("Trying to send study status change notification email");
                
                this.mailSender.send(msg);
                
            }
            catch (MailException e) {
                log.error("Could not send email due to  " + e.getMessage());
                // just log it for now
            }
        }
        log.debug(this.getClass().getName() + ": Exiting sendEmail()");
    }

    /**
     * This method generates a list of emails by getting the emailaddresses from all the email based
     * Recepients and Role based Recepients for a given Notification. The role based recepient only
     * has the role String...the corresponding personnel in that role for that study have to be
     * retrieved and their email addresses have to be addded to the finalList.
     */
    public List<String> generateEmailList(PlannedNotification plannedNotification) {
    	log.debug(this.getClass().getName() + ": Entering generateEmailList()");
        List<String> emailList = new ArrayList<String>();
    	emailList.addAll(getEmailsFromRoleBasedRecipient(plannedNotification));
    	emailList.addAll(getEmailsFromUserBasedRecipient(plannedNotification));
//    	emailList.addAll(getEmailsFromContactMechanismBasedRecipient(plannedNotification));
    	log.debug(this.getClass().getName() + ": Exiting generateEmailList()");
    	return emailList;
        
    }

    public List<String> getEmailsFromRoleBasedRecipient(PlannedNotification plannedNotification) {
    	log.debug(this.getClass().getName() + ": Entering getEmailsFromRoleBasedRecipient()");
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
        log.debug(this.getClass().getName() + ": exiting getEmailsFromRoleBasedRecipient()");
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
    	log.debug(this.getClass().getName() + ": Entering getEmailsFromUserBasedRecipient()");
    	List<String> returnList = new ArrayList<String>();
    	
    	for(UserBasedRecipient ubr: plannedNotification.getUserBasedRecipient()){
    		if(ubr.getResearchStaff() != null){
    			returnList.addAll(getEmailAddressesFromResearchStaff(ubr.getResearchStaff()));
    		}
            if(ubr.getInvestigator() != null){
            	returnList.addAll(getEmailAddressesFromInvestigator(ubr.getInvestigator()));
            }
    	}
    	log.debug(this.getClass().getName() + ": exiting getEmailsFromUserBasedRecipient()");
    	return returnList;
    }
    
    
    public HealthcareSite getHostingHealthcareSite(){
    	return healthcareSiteDao.getByNciInstituteCode(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE));
    }
    
    
    public void addScheduledNotification(PlannedNotification plannedNotification, String composedMessage){
    	
    	ScheduledNotification scheduledNotification = new ScheduledNotification();
    	plannedNotification.getScheduledNotification().add(scheduledNotification);
    	scheduledNotification.setDateSent(new Date());
		scheduledNotification.setMessage(composedMessage);
		scheduledNotification.setTitle(plannedNotification.getTitle());
    	RecipientScheduledNotification rsn; 
    	for(RoleBasedRecipient rbr: plannedNotification.getRoleBasedRecipient()){
    		rsn = new RecipientScheduledNotification();
    		rsn.setRecipient(rbr);
    		rsn.setIsRead(Boolean.FALSE);
    		rsn.setScheduledNotification(scheduledNotification);
    		scheduledNotification.getRecipientScheduledNotification().add(rsn);
    	}
    	
    	for(UserBasedRecipient ubr: plannedNotification.getUserBasedRecipient()){
    		rsn = new RecipientScheduledNotification();
    		rsn.setRecipient(ubr);
    		rsn.setIsRead(Boolean.FALSE);
    		rsn.setScheduledNotification(scheduledNotification);
    		scheduledNotification.getRecipientScheduledNotification().add(rsn);
    	}
    	return;
    }
    
    
    private String applyRuntimeReplacementsForStudyStatusEmailMessage(String rawText, Study study) {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration();
        Map<Object, Object> map = study.buildMapForNotification();
        try {
            Template t = new Template("message", new StringReader(rawText), cfg);
            StringWriter writer = new StringWriter();
            t.process(map, writer);
            return writer.toString();
        } catch (TemplateException e) {
            log.error("Error while applying freemarker template [PlannedNotificatiton.body]", e);
        } catch (IOException e) {
            log.error("Error while applying freemarker template [PlannedNotificatiton.body]", e);
        }
        return "";
    }
    
    
    /*This method generates the final email message by plugging in the right values for the 
      substitution variables.

      private String composeMessage(String messageWithSubVars, Study study){
    	StringBuffer generatedMessage = new StringBuffer();
    	int startIndex = 0;
    	int dollarIndex = -1;
    	int endDollarIndex = -1;
    	String variableValue = "";
    	
    	//loops over the messageWithSubVars until it runs out of subvars.
    	while(true){
	    	dollarIndex = messageWithSubVars.indexOf("$", startIndex);
	    	if(dollarIndex == -1){
	    		//appending the string after the last '$' or the whole string if no '$' is found.
	    		generatedMessage.append(messageWithSubVars.substring(startIndex));
	    		//no more subvars hence exit
	    		break;
	    	} else {
		    	
		    	endDollarIndex = messageWithSubVars.indexOf("}", dollarIndex);
		    	if(endDollarIndex == -1){
		    		//this means closing brace wasnt found...hence return msg as is.
		    		//admin needs to correct the message format.
		    		return messageWithSubVars;
		    	}
		    	variableValue = getVariableValue(messageWithSubVars.substring(dollarIndex + 2, endDollarIndex), study);
		    	
		    	generatedMessage.append(messageWithSubVars.substring(startIndex, dollarIndex)+ " " + variableValue + " ");
		    	startIndex = endDollarIndex + 1;
	    	}
    	}
    	return generatedMessage.toString();
    }
    
    private String getVariableValue(String variable, Study study){
    	if(variable.equals(NotificationEmailSubstitutionVariablesEnum.COORDINATING_CENTER_STUDY_STATUS.toString())){
    		return study.getCoordinatingCenterStudyStatus().getDisplayName();
    	}
		if(variable.equals(NotificationEmailSubstitutionVariablesEnum.REGISTRATION_STATUS.toString())){
			return "rs";		
		}
		if(variable.equals(NotificationEmailSubstitutionVariablesEnum.STUDY_ID.toString())){
			return study.getId().toString();
		}
		if(variable.equals(NotificationEmailSubstitutionVariablesEnum.STUDY_SHORT_TITLE.toString())){
			return study.getShortTitleText();
		}
		return "";
    }*/
    
    
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

	public PlannedNotificationDao getPlannedNotificationDao() {
		return plannedNotificationDao;
	}

	public void setPlannedNotificationDao(
			PlannedNotificationDao plannedNotificationDao) {
		this.plannedNotificationDao = plannedNotificationDao;
	}

	/*public static void main(String arsg[]){
		NotificationEmailService nes = new NotificationEmailService();
		String messageWithSubVars = "The study ${" +NotificationEmailSubstitutionVariablesEnum.STUDY_ID +"} is now in ${" +
		NotificationEmailSubstitutionVariablesEnum.COORDINATING_CENTER_STUDY_STATUS + "} status.";
		Study study = new Study();
		study.setShortTitleText("shortTitleText");
		study.setId(10);
		study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
		
		System.out.println(nes.composeMessage(messageWithSubVars, study));
	}*/
}
