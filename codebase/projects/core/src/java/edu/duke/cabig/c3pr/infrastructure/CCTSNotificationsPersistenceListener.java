/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
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
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import edu.duke.cabig.c3pr.domain.CCTSBroadcastEnabledDomainObject;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.service.SchedulerService;
import edu.duke.cabig.c3pr.tools.Configuration;

/**
 * This class acts as a Hibernate event listener and reacts to INSERT/UPDATE
 * events related to {@link Participant}, {@link Study}, and
 * {@link StudySubject}. Upon such events, a notification message is sent to the
 * iHub instance.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class CCTSNotificationsPersistenceListener implements
		PostInsertEventListener, PostUpdateEventListener,
		ApplicationContextAware {

	private static final String SCHEDULER_SERVICE = "schedulerService";

	private static final Log log = LogFactory
			.getLog(CCTSNotificationsPersistenceListener.class);

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
		if (entity instanceof CCTSBroadcastEnabledDomainObject) {
			if (isESBEnabled()) {
				log.debug(entity.getClass().getSimpleName()
						+ " create/update detected, getting ready to broadcast notification to caTissue and others.");
				// only do the broadcast if the object has grid_id.
				CCTSBroadcastEnabledDomainObject p = (CCTSBroadcastEnabledDomainObject) entity;
				if (StringUtils.isNotBlank(p.getGridId())) {
					scheduleNotification(p);
				} else {
					log.warn(entity.getClass().getSimpleName()
							+ " with ID="
							+ p.getId()
							+ " has no Grid ID; hence, skipping the broadcast via iHub.");
				}
			} else {
				log.debug("A change to a CCTSBroadcastEnabledDomainObject won't be broadcasted, because we're running in local mode.");
			}
		}

	}

	/**
	 * @return
	 */
	private boolean isESBEnabled() {
		Configuration configuration = (Configuration) applicationContext
				.getBean("configuration");
		return "true".equalsIgnoreCase(configuration
				.get(Configuration.ESB_ENABLE));
	}

	private void scheduleNotification(CCTSBroadcastEnabledDomainObject s) {
		try {
			log.info("Getting ready to broadcast a notification for a "
					+ s.getClass().getSimpleName() + " with Grid Id of "
					+ s.getGridId());
			SchedulerService schedulerService = getSchedulerService();
			schedulerService.scheduleNotification(s);
		} catch (RuntimeException e) {
			log.error(
					"Unable to schedule message notification about "
							+ s.getClass().getSimpleName() + " with grid id="
							+ s.getGridId() + ". caTissue will miss the event!",
					e);
		}
	}

	/**
	 * @return
	 * @throws BeansException
	 */
	private SchedulerService getSchedulerService() throws BeansException {
		SchedulerService schedulerService = (SchedulerService) getApplicationContext()
				.getBean(SCHEDULER_SERVICE);
		return schedulerService;
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
