package edu.duke.cabig.c3pr.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import edu.duke.cabig.c3pr.constants.EmailNotificationDeliveryStatusEnum;
import edu.duke.cabig.c3pr.dao.PlannedNotificationDao;
import edu.duke.cabig.c3pr.dao.ScheduledNotificationDao;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.service.ScheduledNotificationService;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Author: vGangoli Date: Nov 30, 2007
 */

public class ScheduledNotificationServiceImpl implements ScheduledNotificationService, ApplicationContextAware {

    private Logger log = Logger.getLogger(ScheduledNotificationServiceImpl.class);

    private PlannedNotificationDao plannedNotificationDao;
    private ScheduledNotificationDao scheduledNotificationDao;
    ApplicationContext applicationContext;
    

    public Integer saveScheduledNotification(PlannedNotification plannedNotification, Study study) {
    	log.debug(this.getClass().getName() + ": Entering saveScheduledNotification()");
    	String composedMessage = applyRuntimeReplacementsForStudyStatusEmailMessage(plannedNotification.getMessage(), study);
    	return saveScheduledNotification(plannedNotification, composedMessage);
    }
    
    public Integer saveScheduledNotification(PlannedNotification plannedNotification, StudySite studySite) {
    	log.debug(this.getClass().getName() + ": Entering saveScheduledNotification()");
    	String composedMessage = applyRuntimeReplacementsForStudySiteStatusEmailMessage(plannedNotification.getMessage(), studySite);
    	return saveScheduledNotification(plannedNotification, composedMessage);
    }    
    
    public Integer saveScheduledNotification(PlannedNotification plannedNotification, String composedMessage){
    	log.debug(this.getClass().getName() + ": Entering saveScheduledNotification()");
    	ScheduledNotification scheduledNotification = null;
    	
    	//Creating a new session to save the scheduled notifications to avoid conflicts with the
    	//currentSession (whose flush initiated this interceptor call in the first place).
    	SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean("sessionFactory");
    	Session session = sessionFactory.openSession();
		session.setFlushMode(FlushMode.COMMIT);
    	try{
    		session.update(plannedNotification);
        	//plannedNotificationDao.reassociate(plannedNotification); 
        	//generating and saving the ScheduledNotification
        	scheduledNotification = addScheduledNotification(plannedNotification, composedMessage);
        	session.saveOrUpdate(plannedNotification);
        	session.flush();
        	//plannedNotificationDao.saveOrUpdate(plannedNotification);
    	}catch(Exception e){
    		log.error(e.getMessage());
    	}finally{
    		session.close();
    	}
		log.debug(this.getClass().getName() + ": Exiting saveScheduledNotification()");
		if(scheduledNotification != null){
		    return scheduledNotification.getId();
		}else{
			log.error("ScheduledNotificationServiceImpl.saveScheduledNotification(): ScheduledNotification was not saved successfully");
			return 0;
		}
    }
    
    public ScheduledNotification addScheduledNotification(PlannedNotification plannedNotification, String composedMessage){
    	
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
    		rsn.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.PENDING);
    		scheduledNotification.getRecipientScheduledNotification().add(rsn);
    	}
    	
    	for(UserBasedRecipient ubr: plannedNotification.getUserBasedRecipient()){
    		rsn = new RecipientScheduledNotification();
    		rsn.setRecipient(ubr);
    		rsn.setIsRead(Boolean.FALSE);
    		rsn.setScheduledNotification(scheduledNotification);
    		rsn.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.PENDING);
    		scheduledNotification.getRecipientScheduledNotification().add(rsn);
    	}
    	return scheduledNotification;
    }
    
    
    /* Using freemarker to compose the email message and replace the substitution vars
     */
    private String applyRuntimeReplacementsForStudyStatusEmailMessage(String rawText, Study study) {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration();
        Map<Object, Object> map = study.buildMapForNotification();
        try {
            Template t = new Template("message", new StringReader(rawText), cfg);
            StringWriter writer = new StringWriter();
            t.process(map, writer);
            return writer.toString();
        } catch (TemplateException e) {
            log.error("Error while applying freemarker template ", e);
        } catch (IOException e) {
            log.error("Error while applying freemarker template ", e);
        }
        return "";
    }
    
    /* Using freemarker to compose the email message and replace the substitution vars
     */
    private String applyRuntimeReplacementsForStudySiteStatusEmailMessage(String rawText, StudySite studySite) {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration();
        Map<Object, Object> map = studySite.buildMapForNotification();
        try {
            Template t = new Template("message", new StringReader(rawText), cfg);
            StringWriter writer = new StringWriter();
            t.process(map, writer);
            return writer.toString();
        } catch (TemplateException e) {
            log.error("Error while applying freemarker template", e);
        } catch (IOException e) {
            log.error("Error while applying freemarker template", e);
        }
        return "";
    }
    

	public PlannedNotificationDao getPlannedNotificationDao() {
		return plannedNotificationDao;
	}

	public void setPlannedNotificationDao(
			PlannedNotificationDao plannedNotificationDao) {
		this.plannedNotificationDao = plannedNotificationDao;
	}


	public ScheduledNotificationDao getScheduledNotificationDao() {
		return scheduledNotificationDao;
	}


	public void setScheduledNotificationDao(
			ScheduledNotificationDao scheduledNotificationDao) {
		this.scheduledNotificationDao = scheduledNotificationDao;
	}


	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}


	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
    
    /* This method generates the final email message by plugging in the right values for the 
    substitution variables. this was replaced by freemarker.

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
  }


	public static void main(String arsg[]){
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
