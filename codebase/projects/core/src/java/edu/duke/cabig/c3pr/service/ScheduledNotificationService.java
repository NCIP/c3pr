package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;

/**
 * @author Kulasekaran,Ramakrishna
 * @version 1.0
 */
public interface ScheduledNotificationService {

    /**
     * Saves a ScheduledNotification and the respective RcptBasedNotification data
     * 
     * @param Participant
     *                object
     * @return List of Participant objects based on the sample participant object
     * @throws Runtime
     *                 exception
     */
	
	/*
	 * Accepts the changed study so it can compose the message by replacing the sub vars.
	 */
	public Integer saveScheduledNotification(PlannedNotification plannedNotification, Study study);
	
	public Integer saveScheduledNotification(PlannedNotification plannedNotification, StudySite studySite);

}
