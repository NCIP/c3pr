package edu.duke.cabig.c3pr.service.impl;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

import edu.duke.cabig.c3pr.constants.NotificationFrequencyEnum;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.scheduler.runtime.job.ScheduledNotificationJob;
import edu.duke.cabig.c3pr.service.SchedulerService;
import edu.duke.cabig.c3pr.utils.StudyTargetAccrualNotificationEmail;

/**
 * @author Gangoli, Vinay
 */
public class SchedulerServiceImpl implements SchedulerService {
	
	private Logger log = Logger.getLogger(StudyTargetAccrualNotificationEmail.class);
	
	private Scheduler scheduler;
	
	private ScheduledNotificationJob scheduledNotificationJob;
	//called from the rules xml.
	public void scheduleNotification(PlannedNotification plannedNotification, Study study){
		
        assert study != null : "study should not be null";
        assert study.getId() != null : "study must have a valid id";
        assert plannedNotification != null: "plannedNotification cannot be null";

        if (log.isDebugEnabled()) {
        	log.debug("Entering ScheduleNotification of the SchedulerServiceImpl: " + String.valueOf(study.getId()));
        }

        try {
            	// for each notification create job detail, and associate with the scheduler
                // create a trigger
                Trigger trigger = makeTrigger(plannedNotification);
                if(trigger == null){
                	log.debug("This is a cronTrigger not a simpleTrigger");
                }
                
                // create job detail and set the map values
                String jobName = "J:PlannedNotificationId:" + plannedNotification.getId().toString() + "-" + Math.random();
                String jobGroupName = "JG:PlannedNotificationId:" + plannedNotification.getId().toString()  + "-" + Math.random();
                JobDetail jobDetail = new JobDetail(jobName, jobGroupName, scheduledNotificationJob.getClass());
                                
                JobDataMap jobDataMap = jobDetail.getJobDataMap();
                jobDataMap.put("plannedNotificationId", plannedNotification.getId());
                jobDataMap.put("studyId", study.getId());

                // schedule the jobs
                log.info("Scheduling the job (jobFullName : " + jobDetail.getFullName() + ")");
                scheduler.scheduleJob(jobDetail, trigger);

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
	private Trigger makeTrigger(PlannedNotification plannedNotification) {
        Trigger t = null;
        String strId = plannedNotification.getId().toString();
        long repeatIntevalInMilliSecondss = 10*60*1000; //10 mins
        int repeatCount = 0;
        
		NotificationFrequencyEnum frequency = plannedNotification.getFrequency();
		if(frequency.equals(NotificationFrequencyEnum.IMMEDIATE)){
	        t = TriggerUtils.makeImmediateTrigger("T:PlannedNotificationId:" + strId  + "-" + Math.random(),  repeatCount, repeatIntevalInMilliSecondss);
	        t.setGroup("TG:PlannedNotificationId:" + strId  + "-" + Math.random());
		}
		if(frequency.equals(NotificationFrequencyEnum.WEEKLY)){
	        try{
	        	//every Friday at 12:00pm
	        	t = new CronTrigger("CT:PlannedNotificationId:" + strId  + "-" + Math.random(),"CTG:PlannedNotificationId:" + strId  + "-" + Math.random(),"0 10 18 ? * THU");
	        }catch(Exception e){
	        	log.error(e.getMessage());
	        }
		}
		if(frequency.equals(NotificationFrequencyEnum.MONTHLY)){
	        try{
	        	//every last day of month at 12:00pm
	        	t = new CronTrigger("CT:PlannedNotificationId:" + strId  + "-" + Math.random(),"CTG:PlannedNotificationId:" + strId  + "-" + Math.random(),"0 0 12 L * ?");
	        }catch(Exception e){
	        	log.error(e.getMessage());
	        }
		}
		if(frequency.equals(NotificationFrequencyEnum.ANNUAL)){
	        try{
	        	//every last day December at 12:00pm
	        	t = new CronTrigger("CT:PlannedNotificationId:" + strId  + "-" + Math.random(),"CTG:PlannedNotificationId:" + strId  + "-" + Math.random(),"0 0 12 L DEC ?");
	        }catch(Exception e){
	        	log.error(e.getMessage());
	        }
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

}
