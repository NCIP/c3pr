package edu.duke.cabig.c3pr.service.impl;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.rules.exception.RuleException;
import edu.duke.cabig.c3pr.rules.runtime.BusinessRulesExecutionService;
import edu.duke.cabig.c3pr.service.RulesDelegationService;
import edu.duke.cabig.c3pr.utils.NotificationEmailService;

public class RulesDelegationServiceImpl implements RulesDelegationService{

	private static final Log log = LogFactory.getLog(RulesDelegationServiceImpl.class);
	
	public static final String NEW_STUDY_SAVED_EVENT = "NEW_STUDY_SAVED_EVENT";	
	public static final String NEW_STUDY_SITE_SAVED_EVENT = "NEW_STUDY_SITE_SAVED_EVENT";	
	public static final String STUDY_STATUS_CHANGE_EVENT = "STUDY_STATUS_CHANGE_EVENT";	
	public static final String STUDY_SITE_STATUS_CHANGE_EVENT = "STUDY_SITE_STATUS_CHANGE_EVENT";	
	public static final String REGISTRATION_EVENT = "REGISTRATION_EVENT";
	
	BusinessRulesExecutionService businessRulesExecutionService;
	
	NotificationEmailService notificationEmailService;
	
	public void activateRules(String event, Object studyObj, Object oldVal, Object newVal){		
		/*
		RuleBase ruleBase = null;
		if(event.equalsIgnoreCase(STUDY_STATUS_CHANGE_EVENT) ){
			InputStreamReader r =  null;			
			try{
				r = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("edu/duke/cabig/c3pr/rules/study-status-rules.xml" ));
				ruleBase = RuleBaseLoader.getInstance().loadFromReader(r);
			}catch(CheckedDroolsException cde){
				log.error(cde.getMessage());
			}catch(IOException ioe){
				log.error(ioe.getMessage());
			}

			StatefulSession statefulSession = ruleBase.newStatefulSession();
        	statefulSession.fireAllRules();
		}*/
		
		ArrayList objList = new ArrayList();
		objList.add(studyObj);
		//objList.add(new RulesDelegationServiceImpl());
		objList.add(notificationEmailService);
		
		try{
			businessRulesExecutionService.fireRules("edu.duke.cabig.c3pr.rules.deploy.study_status_rules", objList);
		}catch(RuleException re){
			log.error(re.getMessage());
		}
		
	}
	
	/*public void sendMail(Study study){
		notificationEmailService.sendEmail(study);
	}*/

	public BusinessRulesExecutionService getBusinessRulesExecutionService() {
		return businessRulesExecutionService;
	}

	public void setBusinessRulesExecutionService(
			BusinessRulesExecutionService businessRulesExecutionService) {
		this.businessRulesExecutionService = businessRulesExecutionService;
	}

	public NotificationEmailService getNotificationEmailService() {
		return notificationEmailService;
	}

	public void setNotificationEmailService(
			NotificationEmailService notificationEmailService) {
		this.notificationEmailService = notificationEmailService;
	}
}
