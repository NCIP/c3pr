package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.Study;

/**
 * @author Gangoli, Vinay
 * @Date 08/12/2008
 * @version 1.0
 */
public interface SchedulerService {

    /**
     * Schedule Notifications by using quartz scheduler
     */
	public void scheduleNotification(PlannedNotification plannedNotification, Study study);

}
