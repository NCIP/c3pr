/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.scheduler.runtime.job;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import edu.duke.cabig.c3pr.dao.PlannedNotificationDao;
import edu.duke.cabig.c3pr.dao.RecipientScheduledNotificationDao;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;

/**
 * This class serves as the Email sending job class scheduled by <code>scheduler</code>
 * component. 
 * @author Vinay Gangoli
 */
public abstract class ScheduledJob implements Job, ApplicationContextAware {

    protected static final Log logger = LogFactory.getLog(ScheduledJob.class);

    protected Scheduler scheduler;

    protected JobDetail jobDetail;

    protected JobExecutionContext jobContext;

    protected ApplicationContext applicationContext;
    
    protected PlannedNotificationDao plannedNotificationDao;
    
    protected RecipientScheduledNotificationDao recipientScheduledNotificationDao;

    public ScheduledJob(){
    	super();
    }
    /**
     * @author Vinay Gangoli
     * All jobs must extend this class. this handles the hibernate session scope for the jobs.
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
       	logger.debug("Executing Scheduled Job");
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest()); 
    	OpenSessionInViewInterceptor interceptor = null;
    	
        try {
            // init the member variables
            scheduler = context.getScheduler();
            jobDetail = context.getJobDetail();
            applicationContext = (ApplicationContext) scheduler.getContext().get("applicationContext");

            plannedNotificationDao = (PlannedNotificationDao) applicationContext.getBean("plannedNotificationDao");
            recipientScheduledNotificationDao = (RecipientScheduledNotificationDao) applicationContext.getBean("recipientScheduledNotificationDao");
            
        	interceptor=(OpenSessionInViewInterceptor)applicationContext.getBean( "openSessionInViewInterceptor");
        	interceptor.preHandle(webRequest);
        	
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            Integer plannedNotificationId = jobDataMap.getInt("plannedNotificationId");            
            //PlannedNotification plannedNotification = plannedNotificationDao.getInitializedPlannedNotificationById(plannedNotificationId);
            PlannedNotification plannedNotification = plannedNotificationDao.getById(plannedNotificationId);
            
            setAuditInfo();
            RecipientScheduledNotification recipientScheduledNotification = null;
            if(jobDataMap.containsKey("recipientScheduledNotificationId")){
            	Integer recipientScheduledNotificationId = jobDataMap.getInt("recipientScheduledNotificationId");            
            	//recipientScheduledNotification = recipientScheduledNotificationDao.getInitializedRecipientScheduledNotificationById(recipientScheduledNotificationId);
            	recipientScheduledNotification = recipientScheduledNotificationDao.getById(recipientScheduledNotificationId);
            }
            
            if(plannedNotification.getEventName() != null){
            	try{
            		processJob(jobDataMap, recipientScheduledNotification, plannedNotification);
            	}catch(JobExecutionException jee){
            		logger.error(jee.getMessage());
            	}
            }
            //try commenting out the postHandle ...might not be necessary!
			interceptor.postHandle(webRequest, null); 
			
        } catch (RuntimeException exception) { 
			logger.error(exception.getMessage()); 
			// Get cause if present Throwable t = ((HibernateJdbcException) exception).getRootCause(); while (t != null) { 
			//t = t.getCause(); 
		} catch (Exception e) {
            logger.error("execution of job failed", e);
        }finally{ 
			interceptor.afterCompletion(webRequest, null); 
		}
    }
    
    /*
     * this is the method that must be overridden by the extending jobs.
     * this will contain the job content details.
     */
    public abstract void processJob(JobDataMap jobDataMap, RecipientScheduledNotification recipientScheduledNotification, 
    					PlannedNotification plannedNotification)  throws JobExecutionException ;
    	

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setJobContext(JobExecutionContext jobContext) {
		this.jobContext = jobContext;
	}

    public JobExecutionContext getJobContext() {
        return jobContext;
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public void setAuditInfo(){
    	gov.nih.nci.cabig.ctms.audit.DataAuditInfo.setLocal(new gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo(
        		"C3PR Admin", "C3PR Scheduled Notification Job", new Date(), "C3PR Scheduled Notification Job"));
    }

}
