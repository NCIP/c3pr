package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.Correspondence;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;

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
	
	public Integer saveScheduledNotification(PlannedNotification plannedNotification, StudySubject studySubject);
	
	public Integer saveScheduledNotification(PlannedNotification plannedNotification, Participant participant);
	
	public Integer saveScheduledNotification(PlannedNotification plannedNotification, Correspondence correspondence);

}
