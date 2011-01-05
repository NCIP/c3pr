package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.CCTSBroadcastEnabledDomainObject;
import edu.duke.cabig.c3pr.domain.PlannedNotification;

/**
 * @author Gangoli, Vinay
 * @author Denis G. Krylov
 * @Date 08/12/2008
 * @version 1.0
 */
public interface SchedulerService {

	/**
	 * Schedule Notifications by using quartz scheduler
	 */
	public void scheduleStudyNotification(
			PlannedNotification plannedNotification,
			Integer scheduledNotificationId);

	/**
	 * Schedules a notification message broadcast via iHub about a
	 * created/updated {@link CCTSBroadcastEnabledDomainObject}.
	 * 
	 * @param broadcastEnabledDomainObject
	 * @throws RuntimeException
	 */
	public void scheduleNotification(
			CCTSBroadcastEnabledDomainObject broadcastEnabledDomainObject)
			throws RuntimeException;

}
