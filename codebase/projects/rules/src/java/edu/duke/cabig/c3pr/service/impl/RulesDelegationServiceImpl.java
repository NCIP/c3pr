/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.rules.exception.RuleException;
import edu.duke.cabig.c3pr.rules.runtime.BusinessRulesExecutionService;
import edu.duke.cabig.c3pr.service.RulesDelegationService;
import edu.duke.cabig.c3pr.service.ScheduledNotificationService;
import edu.duke.cabig.c3pr.service.SchedulerService;
import edu.duke.cabig.c3pr.tools.PropertiesGetterFactoryBean;

public class RulesDelegationServiceImpl implements RulesDelegationService{

	private static final Log log = LogFactory.getLog(RulesDelegationServiceImpl.class);
	
	public static final String NEW_STUDY_SAVED_EVENT = "NEW_STUDY_SAVED_EVENT";	
	public static final String NEW_STUDY_SITE_SAVED_EVENT = "NEW_STUDY_SITE_SAVED_EVENT";	
	public static final String STUDY_STATUS_CHANGE_EVENT = "STUDY_STATUS_CHANGE_EVENT";	
	public static final String STUDY_SITE_STATUS_CHANGE_EVENT = "STUDY_SITE_STATUS_CHANGE_EVENT";	
	public static final String REGISTRATION_EVENT = "REGISTRATION_EVENT";
	public static final String MASTER_SUBJECT_UPDATED_EVENT = "MASTER_SUBJECT_UPDATED_EVENT";
	
	private BusinessRulesExecutionService businessRulesExecutionService;
	
	private ScheduledNotificationService scheduledNotificationService;
	
	private SchedulerService schedulerService;
	
	
	public void activateRules(NotificationEventTypeEnum event, List<Object> objects){		
		
		log.debug(this.getClass().getName() + ": Entering activateRules()");
		ArrayList <Object>objList = new ArrayList<Object>();
		objList.addAll(objects);
		objList.add(schedulerService);
		objList.add(scheduledNotificationService);
		objList.add(event);
		
		try{
			if(event != null){
				businessRulesExecutionService.fireRules("edu.duke.cabig.c3pr.rules.deploy.study_status_rules", objList);
			}
		}catch(RuleException re){
			log.error(re.getMessage());
		}
		log.debug(this.getClass().getName() + ": Exiting activateRules()");
	}
	
	public static void loadConfiguration(Properties props) {
    	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:edu/duke/cabig/c3pr/applicationContext-config.xml");
    	
    	String url = applicationContext.getBean("c3prPropertyConfigurerFromFile[datasource.url]").toString();
    	String driver = applicationContext.getBean("c3prPropertyConfigurerFromFile[datasource.driver]").toString();
    	String user = applicationContext.getBean("c3prPropertyConfigurerFromFile[datasource.username]").toString();
    	String pwd = applicationContext.getBean("c3prPropertyConfigurerFromFile[datasource.password]").toString();
        
		props.setProperty("datasource.driver", driver);
        props.setProperty("datasource.password", pwd);
        props.setProperty("datasource.username", user);
        props.setProperty("datasource.url", url);

	}
	

	public BusinessRulesExecutionService getBusinessRulesExecutionService() {
		return businessRulesExecutionService;
	}

	public void setBusinessRulesExecutionService(
			BusinessRulesExecutionService businessRulesExecutionService) {
		this.businessRulesExecutionService = businessRulesExecutionService;
	}

	public SchedulerService getSchedulerService() {
		return schedulerService;
	}


	public void setSchedulerService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}


	public ScheduledNotificationService getScheduledNotificationService() {
		return scheduledNotificationService;
	}


	public void setScheduledNotificationService(
			ScheduledNotificationService scheduledNotificationService) {
		this.scheduledNotificationService = scheduledNotificationService;
	}
}
