package edu.duke.cabig.c3pr.domain.scheduler.runtime.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;

import edu.duke.cabig.c3pr.constants.EmailNotificationDeliveryStatusEnum;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.utils.NotificationEmailService;

/**
 * This class serves as the Email sending job class scheduled by <code>scheduler</code>
 * component. 
 * @author Vinay Gangoli
 */
public class ScheduledNotificationJob extends ScheduledJob {

    protected static final Log logger = LogFactory.getLog(ScheduledNotificationJob.class);
    
    private NotificationEmailService notificationEmailService;

    public ScheduledNotificationJob(){
    	super();
    }

    /*
     * @see edu.duke.cabig.c3pr.domain.scheduler.runtime.job.ScheduledJob#processJob(org.quartz.JobDataMap, org.springframework.context.ApplicationContext,
     * 									 edu.duke.cabig.c3pr.domain.RecipientScheduledNotification)
     * This is the job for the Study status change notification email delivery. 
     */
    @Override
    public void processJob(JobDataMap jobDataMap, ApplicationContext applicationContext, 
    						RecipientScheduledNotification recipientScheduledNotification) throws JobExecutionException {
       	logger.debug("Executing ScheduledNotification Job");
       	
    	assert applicationContext != null: "applicationContext cannot be null";
    	assert recipientScheduledNotification != null: "plannedNotification cannot be null";
    	
        try {
            // init the member variables
        	if(recipientScheduledNotification.getDeliveryStatus().equals(EmailNotificationDeliveryStatusEnum.PENDING) ||
        			recipientScheduledNotification.getDeliveryStatus().equals(EmailNotificationDeliveryStatusEnum.RETRY) ){
        		notificationEmailService = (NotificationEmailService) applicationContext.getBean("notificationEmailService");
                notificationEmailService.sendEmail(recipientScheduledNotification);
                recipientScheduledNotification.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.COMPLETE);
        	}
        } catch(MailException me){
        	logger.error("execution of sendMail failed", me);
        	recipientScheduledNotification.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.RETRY);
        }catch (Exception e){
            logger.error("execution of job failed", e);
            recipientScheduledNotification.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.ERROR);
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