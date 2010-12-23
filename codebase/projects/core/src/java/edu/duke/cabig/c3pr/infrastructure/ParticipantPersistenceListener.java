/**
 * 
 */
package edu.duke.cabig.c3pr.infrastructure;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.service.SchedulerService;

/**
 * This class acts as a Hibernate event listener and reacts to INSERT/UPDATE
 * events related to {@link Participant}. Upon such events, a notification
 * message is sent to the iHub instance.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class ParticipantPersistenceListener implements
		PostInsertEventListener, PostUpdateEventListener,
		ApplicationContextAware {

	private static final String SCHEDULER_SERVICE = "schedulerService";

	private static final Log log = LogFactory
			.getLog(ParticipantPersistenceListener.class);

	/**
	 * No dependency injection here, because of circular dependency in spring
	 * config XML.
	 */
	private ApplicationContext applicationContext;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hibernate.event.PostUpdateEventListener#onPostUpdate(org.hibernate
	 * .event.PostUpdateEvent)
	 */
	public void onPostUpdate(PostUpdateEvent event) {
		process(event.getEntity());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hibernate.event.PostInsertEventListener#onPostInsert(org.hibernate
	 * .event.PostInsertEvent)
	 */
	public void onPostInsert(PostInsertEvent event) {
		process(event.getEntity());
	}

	private void process(Object entity) {
		if (entity instanceof Participant) {
			log.debug("Participant create/update detected, getting ready to broadcast notification to caTissue and others.");
			// only do the broadcast if the participant has grid_id.
			Participant p = (Participant) entity;
			if (StringUtils.isNotBlank(p.getGridId())) {
				scheduleNotification(p);
			} else {
				log.warn("Participant with ID="
						+ p.getId()
						+ " has no Grid ID; hence, skipping the broadcast via iHub.");
			}
		}
	}

	private void scheduleNotification(Participant p) {
		try {
			log.info("Getting ready to broadcast a notification for participant with Grid Id of "
					+ p.getGridId());
			SchedulerService schedulerService = (SchedulerService) getApplicationContext()
					.getBean(SCHEDULER_SERVICE);
			schedulerService.scheduleParticipantNotification(p);
		} catch (RuntimeException e) {
			log.error(
					"Unable to schedule message notification about participant with grid id="
							+ p.getGridId() + ". caTissue will miss the event!",
					e);
		}
	}

	/**
	 * @return the applicationContext
	 */
	public final ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext
	 *            the applicationContext to set
	 */
	public final void setApplicationContext(
			ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
