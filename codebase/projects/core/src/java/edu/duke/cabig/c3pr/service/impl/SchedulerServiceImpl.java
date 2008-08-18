package edu.duke.cabig.c3pr.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

import edu.duke.cabig.c3pr.constants.NotificationFrequencyEnum;
import edu.duke.cabig.c3pr.dao.ScheduledNotificationDao;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.scheduler.runtime.job.ScheduledNotificationJob;
import edu.duke.cabig.c3pr.service.SchedulerService;
import edu.duke.cabig.c3pr.utils.StudyTargetAccrualNotificationEmail;

/**
 * @author Gangoli, Vinay
 */
public class SchedulerServiceImpl implements SchedulerService {
	
	public static final String WEEKLY = "0 10 18 ? * THU";
	public static final String MONTHLY ="0 0 12 L * ?";
	public static final String ANNUAL ="0 0 12 L DEC ?";
	
	public static final Long REPEAT_INTERVAL_IN_MILLI_SECONDS= 10*60*1000L;
	public static final Integer REPEAT_COUNT= 3;
	
	private Logger log = Logger.getLogger(StudyTargetAccrualNotificationEmail.class);
	
	private Scheduler scheduler;
	private ScheduledNotificationJob scheduledNotificationJob;
	private ScheduledNotificationDao scheduledNotificationDao;
	
	//called from the rules xml.
	public void scheduleStudyNotification(PlannedNotification plannedNotification, Integer scheduledNotificationId){
		
        assert scheduledNotificationId != null: "scheduledNotificationId cannot be null";

       	log.debug("Entering ScheduleNotification of the SchedulerServiceImpl: " + scheduledNotificationId);

        try {
            	// for each rcptSchldNotification create job detail, and associate with the scheduler
        		ScheduledNotification scheduledNotification = scheduledNotificationDao.getById(scheduledNotificationId);
        		List<RecipientScheduledNotification> rsnList = scheduledNotification.getRecipientScheduledNotification();
        		
        		for(RecipientScheduledNotification rsn: rsnList){
                    // create a trigger
                    Trigger trigger = makeTrigger(plannedNotification, rsn.getId());
                    if(trigger == null){
                    	log.error("Trigger cannot be null");
                    	return;
                    }
                    
                    // create job detail and set the map values
                    String jobName = "J:recipientScheduledNotificationId:" + rsn.getId().toString();
                    String jobGroupName = "JG:recipientScheduledNotificationId:" + rsn.getId().toString();
                    JobDetail jobDetail = new JobDetail(jobName, jobGroupName, scheduledNotificationJob.getClass());
                                    
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    jobDataMap.put("plannedNotificationId", plannedNotification.getId());
                    jobDataMap.put("recipientScheduledNotificationId", rsn.getId());

                    // schedule the jobs
                    log.info("Scheduling the job (jobFullName : " + jobDetail.getFullName() + ")");
                    scheduler.scheduleJob(jobDetail, trigger);
        		}
        	
        } catch (SchedulerException e) {
            log.error("Exception while scheduling ", e);
        }
	}
	
	
	/* Cron-Expressions are used to configure instances of CronTrigger. Cron-Expressions are strings that are actually made up of seven sub-expressions, 
	 * that describe individual details of the schedule. These sub-expression are separated with white-space, and represent:
	   1. Seconds
	   2. Minutes
	   3. Hours
	   4. Day-of-Month
	   5. Month
	   6. Day-of-Week
	   7. Year (optional field)
	   An example of a complete cron-expression is the string "0 0 12 ? * WED" - which means "every Wednesday at 12:00 pm".
	   Refer: http://wiki.opensymphony.com/display/QRTZ1/TutorialLesson6
	*/
	private Trigger makeTrigger(PlannedNotification plannedNotification, Integer recipientScheduledNotificationId) {
        Trigger t = null;
        
		NotificationFrequencyEnum frequency = plannedNotification.getFrequency();
		try{
			if(frequency.equals(NotificationFrequencyEnum.WEEKLY)){
	        	//every Friday at 12:00pm
	        	t = new CronTrigger("TW:scheduledNotificationId:" + recipientScheduledNotificationId, "TGW:PlannedNotificationId:" + recipientScheduledNotificationId, WEEKLY);
			}
			if(frequency.equals(NotificationFrequencyEnum.MONTHLY)){
		        	//every last day of month at 12:00pm
		        	t = new CronTrigger("TM:scheduledNotificationId:" + recipientScheduledNotificationId, "TGM:PlannedNotificationId:" + recipientScheduledNotificationId , MONTHLY);
			}
			if(frequency.equals(NotificationFrequencyEnum.ANNUAL)){
		        	//every last day December at 12:00pm
		        	t = new CronTrigger("TA:scheduledNotificationId:" + recipientScheduledNotificationId , "TGA:PlannedNotificationId:" + recipientScheduledNotificationId , ANNUAL);
			}
			if(frequency.equals(NotificationFrequencyEnum.IMMEDIATE) || t == null){
		        t = TriggerUtils.makeImmediateTrigger("T:recipientScheduledNotificationId:" + recipientScheduledNotificationId,  REPEAT_COUNT, REPEAT_INTERVAL_IN_MILLI_SECONDS);
		        t.setGroup("TG:scheduledNotificationId:" + recipientScheduledNotificationId);
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
        return t;
    }	
	

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public ScheduledNotificationJob getScheduledNotificationJob() {
		return scheduledNotificationJob;
	}

	public void setScheduledNotificationJob(
			ScheduledNotificationJob scheduledNotificationJob) {
		this.scheduledNotificationJob = scheduledNotificationJob;
	}


	public ScheduledNotificationDao getScheduledNotificationDao() {
		return scheduledNotificationDao;
	}


	public void setScheduledNotificationDao(
			ScheduledNotificationDao scheduledNotificationDao) {
		this.scheduledNotificationDao = scheduledNotificationDao;
	}

}
