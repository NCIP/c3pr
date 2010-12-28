package edu.duke.cabig.c3pr.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

import edu.duke.cabig.c3pr.constants.NotificationFrequencyEnum;
import edu.duke.cabig.c3pr.dao.ScheduledNotificationDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.scheduler.runtime.job.CCTSNotificationMessageJob.CCTSNotification;
import edu.duke.cabig.c3pr.domain.scheduler.runtime.job.ScheduledNotificationJob;
import edu.duke.cabig.c3pr.service.SchedulerService;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gme.ccts_cabig._1_0.gov_nih_nci_cabig_ccts_domain.notifications.EventType;

/**
 * @author Gangoli, Vinay
 * @author Denis G. Krylov
 */
public class SchedulerServiceImpl implements SchedulerService {

	public static final String WEEKLY = "0 35 10 ? * FRI";
	public static final String MONTHLY = "0 0 12 L * ?";
	public static final String ANNUAL = "0 0 12 L DEC ?";

	public static final Long REPEAT_INTERVAL_IN_MILLI_SECONDS = 10 * 60 * 1000L;
	public static final Integer REPEAT_COUNT = 3;

	public static final String CCTS_NOTIFICATIONS_QUEUE_KEY = "CCTS_NOTIFICATIONS_QUEUE_KEY";
	private static final String CCTS_NOTIFICATIONS_JOB_GROUP = "CCTS_NOTIFICATIONS_JOB_GROUP";
	private static final String CCTS_NOTIFICATIONS_JOB_NAME = "CCTS_NOTIFICATIONS_JOB_NAME";
	private static final String CCTS_NOTIFICATIONS_TRIGGER_GROUP = "CCTS_NOTIFICATIONS_TRIGGER_GROUP";
	private static final String CCTS_NOTIFICATIONS_TRIGGER_NAME = "CCTS_NOTIFICATIONS_TRIGGER_NAME";
	private static final int CCTS_NOTIFICATIONS_TRIGGER_REPEAT_COUNT = SimpleTrigger.REPEAT_INDEFINITELY;
	private static final long CCTS_NOTIFICATIONS_TRIGGER_REPEAT_INTERVAL = 5 * 60 * 1000L;

	private Logger log = Logger.getLogger(SchedulerServiceImpl.class);

	private Scheduler scheduler;
	private ScheduledNotificationJob scheduledNotificationJob;
	private Job cctsNotificationMessageJob;
	private ScheduledNotificationDao scheduledNotificationDao;

	/*
	 * Called from the rules xml for the immediate frequency event based
	 * notifications.
	 */
	public void scheduleStudyNotification(
			PlannedNotification plannedNotification,
			Integer scheduledNotificationId) {

		// do nothing if scheduled notification Id is null. This currently
		// happens only for master_subject_updated event
		// when there is already a notification scheduled for the day. Then
		// another one is not created
		if (scheduledNotificationId == null) {
			log.debug("Exiting ScheduleNotification of the SchedulerServiceImpl because scheduledNotificationId is null");
			return;
		}

		log.debug("Entering ScheduleNotification of the SchedulerServiceImpl: "
				+ scheduledNotificationId);

		try {
			// for each rcptSchldNotification create job detail, and associate
			// with the scheduler
			ScheduledNotification scheduledNotification = scheduledNotificationDao
					.getById(scheduledNotificationId);
			List<RecipientScheduledNotification> rsnList = scheduledNotification
					.getRecipientScheduledNotification();

			for (RecipientScheduledNotification rsn : rsnList) {
				// create a trigger
				Trigger trigger = makeTrigger(plannedNotification, rsn.getId());
				if (trigger == null) {
					log.error("Trigger cannot be null");
					return;
				}

				// create job detail and set the map values
				String jobName = "J:recipientScheduledNotificationId:"
						+ rsn.getId().toString();
				String jobGroupName = "JG:recipientScheduledNotificationId:"
						+ rsn.getId().toString();
				JobDetail jobDetail = new JobDetail(jobName, jobGroupName,
						scheduledNotificationJob.getClass());

				JobDataMap jobDataMap = jobDetail.getJobDataMap();
				jobDataMap.put("plannedNotificationId",
						plannedNotification.getId());
				jobDataMap.put("recipientScheduledNotificationId", rsn.getId());

				// schedule the jobs
				log.info("Scheduling the job (jobFullName : "
						+ jobDetail.getFullName() + ")");
				scheduler.scheduleJob(jobDetail, trigger);
			}

		} catch (SchedulerException e) {
			log.error("Exception while scheduling ", e);
		}
	}

	/*
	 * Cron-Expressions are used to configure instances of CronTrigger.
	 * Cron-Expressions are strings that are actually made up of seven
	 * sub-expressions, that describe individual details of the schedule. These
	 * sub-expression are separated with white-space, and represent: 1. Seconds
	 * 2. Minutes 3. Hours 4. Day-of-Month 5. Month 6. Day-of-Week 7. Year
	 * (optional field) An example of a complete cron-expression is the string
	 * "0 0 12 ? * WED" - which means "every Wednesday at 12:00 pm". Refer:
	 * http://wiki.opensymphony.com/display/QRTZ1/TutorialLesson6
	 */
	private Trigger makeTrigger(PlannedNotification plannedNotification,
			Integer recipientScheduledNotificationId) {
		Trigger t = null;

		NotificationFrequencyEnum frequency = plannedNotification
				.getFrequency();
		try {
			if (frequency.equals(NotificationFrequencyEnum.WEEKLY)) {
				// every Friday at 12:00pm
				t = new CronTrigger("TW:scheduledNotificationId:"
						+ recipientScheduledNotificationId,
						"TGW:PlannedNotificationId:"
								+ recipientScheduledNotificationId, WEEKLY);
			}
			if (frequency.equals(NotificationFrequencyEnum.MONTHLY)) {
				// every last day of month at 12:00pm
				t = new CronTrigger("TM:scheduledNotificationId:"
						+ recipientScheduledNotificationId,
						"TGM:PlannedNotificationId:"
								+ recipientScheduledNotificationId, MONTHLY);
			}
			if (frequency.equals(NotificationFrequencyEnum.ANNUAL)) {
				// every last day December at 12:00pm
				t = new CronTrigger("TA:scheduledNotificationId:"
						+ recipientScheduledNotificationId,
						"TGA:PlannedNotificationId:"
								+ recipientScheduledNotificationId, ANNUAL);
			}
			if (frequency.equals(NotificationFrequencyEnum.END_OF_THE_DAY)) {

				Date endOfDayTime = DateUtil.getMidNightTime();
				t = new SimpleTrigger("TG:scheduledNotificationId:"
						+ recipientScheduledNotificationId,
						"TG:scheduledNotificationId:"
								+ recipientScheduledNotificationId,
						endOfDayTime);
			}
			// This is the only one that will be used from here...the cron
			// triggers will be created in createNotificationController
			// at the time of creation of Planned Notifications.
			if (frequency.equals(NotificationFrequencyEnum.IMMEDIATE)
					|| t == null) {
				t = TriggerUtils.makeImmediateTrigger(
						"T:recipientScheduledNotificationId:"
								+ recipientScheduledNotificationId,
						REPEAT_COUNT, REPEAT_INTERVAL_IN_MILLI_SECONDS);
				t.setGroup("TG:scheduledNotificationId:"
						+ recipientScheduledNotificationId);
			}
		} catch (Exception e) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.service.SchedulerService#scheduleParticipantNotification
	 * (edu.duke.cabig.c3pr.domain.Participant)
	 */
	public void scheduleParticipantNotification(Participant participant) {
		try {
			if (participant == null
					|| StringUtils.isBlank(participant.getGridId())) {
				return;
			}

			CCTSNotification notification = new CCTSNotification();
			notification.setEventType(EventType.SUBJECT_CREATION.name());
			notification.setIdentifierType("GRID_ID");
			notification.setIdentifierValue(participant.getGridId());
			notification.setTimestamp(new Date());

			queueNotification(notification);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * @return
	 * @throws SchedulerException
	 */
	private synchronized JobDetail queueNotification(
			CCTSNotification notification) throws SchedulerException {
		JobDetail jobDetail = scheduler.getJobDetail(
				CCTS_NOTIFICATIONS_JOB_NAME, CCTS_NOTIFICATIONS_JOB_GROUP);
		if (jobDetail == null) {
			jobDetail = new JobDetail(CCTS_NOTIFICATIONS_JOB_NAME,
					CCTS_NOTIFICATIONS_JOB_GROUP,
					cctsNotificationMessageJob.getClass());
			jobDetail.setRequestsRecovery(true);
			jobDetail.setDurability(true);
		} else {
			scheduler.unscheduleJob(CCTS_NOTIFICATIONS_TRIGGER_NAME,
					CCTS_NOTIFICATIONS_TRIGGER_GROUP);
		}

		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		Vector<CCTSNotification> queue = (Vector<CCTSNotification>) jobDataMap
				.get(CCTS_NOTIFICATIONS_QUEUE_KEY);
		if (queue == null) {
			// Selection of Vector is explained by the fact this List
			// implementation
			// is "really" serializable, while others from java.util.collections
			// and java.util.concurrent are not.
			queue = new Vector<edu.duke.cabig.c3pr.domain.scheduler.runtime.job.CCTSNotificationMessageJob.CCTSNotification>();
		}
		queue.add(notification);
		jobDataMap.put(CCTS_NOTIFICATIONS_QUEUE_KEY, queue);

		log.info("Scheduling the job (jobFullName : " + jobDetail.getFullName()
				+ ")");
		scheduler.addJob(jobDetail, true);
		Trigger trigger = makeCctsNotificationsJobTrigger();
		scheduler.scheduleJob(trigger);
		return jobDetail;
	}

	private Trigger makeCctsNotificationsJobTrigger() {
		Trigger trigger = TriggerUtils.makeImmediateTrigger(
				CCTS_NOTIFICATIONS_TRIGGER_NAME,
				CCTS_NOTIFICATIONS_TRIGGER_REPEAT_COUNT,
				CCTS_NOTIFICATIONS_TRIGGER_REPEAT_INTERVAL);
		trigger.setGroup(CCTS_NOTIFICATIONS_TRIGGER_GROUP);
		trigger.setJobName(CCTS_NOTIFICATIONS_JOB_NAME);
		trigger.setJobGroup(CCTS_NOTIFICATIONS_JOB_GROUP);
		return trigger;
	}

	/**
	 * @return the cctsNotificationMessageJob
	 */
	public final Job getCctsNotificationMessageJob() {
		return cctsNotificationMessageJob;
	}

	/**
	 * @param cctsNotificationMessageJob
	 *            the cctsNotificationMessageJob to set
	 */
	public final void setCctsNotificationMessageJob(
			Job cctsNotificationMessageJob) {
		this.cctsNotificationMessageJob = cctsNotificationMessageJob;
	}

}
