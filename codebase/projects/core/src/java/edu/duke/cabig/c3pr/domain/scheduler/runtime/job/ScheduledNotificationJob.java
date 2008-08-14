package edu.duke.cabig.c3pr.domain.scheduler.runtime.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.NotificationEmailService;

/**
 * This class serves as the Email sending job class scheduled by <code>scheduler</code>
 * component. 
 * @author Vinay Gangoli
 */
public class ScheduledNotificationJob extends ScheduledJob {

    protected static final Log logger = LogFactory.getLog(ScheduledNotificationJob.class);

    private StudyDao studyDao;
    
    private NotificationEmailService notificationEmailService;

    public ScheduledNotificationJob(){
    	super();
    }

    /*
     * @see edu.duke.cabig.c3pr.domain.scheduler.runtime.job.ScheduledJob#processJob(org.quartz.JobDataMap, org.springframework.context.ApplicationContext, edu.duke.cabig.c3pr.domain.PlannedNotification)
     * This is the job for the Study status change notification email delivery. 
     */
    @Override
    public void processJob(JobDataMap jobDataMap, ApplicationContext applicationContext, PlannedNotification plannedNotification) throws JobExecutionException {
       	logger.debug("Executing ScheduledNotification Job");
       	
    	assert applicationContext != null: "applicationContext cannot be null";
    	assert jobDataMap != null: "jobDataMap cannot be null";
    	assert plannedNotification != null: "plannedNotification cannot be null";
    	
        try {
            // init the member variables
            notificationEmailService = (NotificationEmailService) applicationContext.getBean("notificationEmailService");
            
            studyDao = (StudyDao) applicationContext.getBean("studyDao");
            Integer studyId = jobDataMap.getInt("studyId");
            Study study = studyDao.getById(studyId);
            
            notificationEmailService.sendEmail(plannedNotification, study);
        } catch (Exception e) {
            logger.error("execution of job failed", e);
        }
        logger.debug("Exiting ScheduledNotification Job");
    }
    
	public NotificationEmailService getNotificationEmailService() {
		return notificationEmailService;
	}

	public void setNotificationEmailService(NotificationEmailService notificationEmailService) {
		this.notificationEmailService = notificationEmailService;
	}

}