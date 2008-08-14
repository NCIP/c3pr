package edu.duke.cabig.c3pr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.rules.exception.RuleException;
import edu.duke.cabig.c3pr.rules.runtime.BusinessRulesExecutionService;
import edu.duke.cabig.c3pr.service.RulesDelegationService;
import edu.duke.cabig.c3pr.service.SchedulerService;
import edu.duke.cabig.c3pr.utils.NotificationEmailService;

public class RulesDelegationServiceImpl implements RulesDelegationService{

	private static final Log log = LogFactory.getLog(RulesDelegationServiceImpl.class);
	
	public static final String NEW_STUDY_SAVED_EVENT = "NEW_STUDY_SAVED_EVENT";	
	public static final String NEW_STUDY_SITE_SAVED_EVENT = "NEW_STUDY_SITE_SAVED_EVENT";	
	public static final String STUDY_STATUS_CHANGE_EVENT = "STUDY_STATUS_CHANGE_EVENT";	
	public static final String STUDY_SITE_STATUS_CHANGE_EVENT = "STUDY_SITE_STATUS_CHANGE_EVENT";	
	public static final String REGISTRATION_EVENT = "REGISTRATION_EVENT";
	
	private BusinessRulesExecutionService businessRulesExecutionService;
	
	private NotificationEmailService notificationEmailService;
	
	private SchedulerService schedulerService;
	
	
	public void activateRules(String event, List<Object> objects){		
		
		log.debug(this.getClass().getName() + ": Entering activateRules()");
		ArrayList <Object>objList = new ArrayList<Object>();
		objList.addAll(objects);
		objList.add(schedulerService);
		objList.add(notificationEmailService);
		
		try{
			businessRulesExecutionService.fireRules("edu.duke.cabig.c3pr.rules.deploy.study_status_rules", objList);
		}catch(RuleException re){
			log.error(re.getMessage());
		}
		log.debug(this.getClass().getName() + ": Exiting activateRules()");
	}

	
	public void activateRules(String event, Object obj, Object oldValue, Object newValue){
		return;
	}

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


	public SchedulerService getSchedulerService() {
		return schedulerService;
	}


	public void setSchedulerService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}
}
