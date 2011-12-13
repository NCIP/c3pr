/**
 * 
 */
package edu.duke.cabig.c3pr.domain.scheduler.runtime.job;

import java.io.CharArrayWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.StatefulJob;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.esb.ESBCommunicationException;
import edu.duke.cabig.c3pr.esb.Metadata;
import edu.duke.cabig.c3pr.esb.OperationNameEnum;
import gme.ccts_cabig._1_0.gov_nih_nci_cabig_ccts_domain.notifications.Application;
import gme.ccts_cabig._1_0.gov_nih_nci_cabig_ccts_domain.notifications.BusinessObjectID;
import gme.ccts_cabig._1_0.gov_nih_nci_cabig_ccts_domain.notifications.EventType;
import gme.ccts_cabig._1_0.gov_nih_nci_cabig_ccts_domain.notifications.Notification;

/**
 * We want this job to persist {@link JobDataMap} after each invocation (because
 * the map contains message queue) and to not run concurrently. Hence,
 * {@link StatefulJob}. <br>
 * <br>
 * The code in this class is written under the assumption that this job won't
 * run concurrently, since it's a {@link StatefulJob}, a singleton, and there is
 * only one {@link Scheduler}. However, if this assumption is somehow becomes
 * invalid, duplicate notification messages may be sent to iHub. <br>
 * <br>
 * The reason for not running this job concurrently is that we need ordered
 * delivery of messages to iHub.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class CCTSNotificationMessageJob implements StatefulJob {

	public static final String IHUB_SERVICE_NAME = "NOTIFICATION_BROADCAST";
	public static final String CCTS_NOTIFICATIONS_NS = "gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain/Notifications";
	private static final String CCTS_NOTIFICATIONS_QUEUE_KEY = edu.duke.cabig.c3pr.service.impl.SchedulerServiceImpl.CCTS_NOTIFICATIONS_QUEUE_KEY;
	private static final Logger log = Logger
			.getLogger(CCTSNotificationMessageJob.class);
	private static final long NOTIFICATION_TTL = DateUtils.MILLIS_PER_DAY * 7;

	/**
	 * CCTSMessageBroadcaster. This cannot be DI-ed by Spring, unfortunately.
	 */
	private CCTSMessageBroadcaster cctsMessageBroadcaster;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		log.debug("Starting execution of the CCTSNotificationMessageJob");
		setBroadcasterInstance(context);

		final JobDetail jobDetail = context.getJobDetail();
		Vector<CCTSNotification> queue;
		try {
			queue = (Vector<CCTSNotification>) jobDetail.getJobDataMap().get(
					CCTS_NOTIFICATIONS_QUEUE_KEY);
		} catch (ClassCastException e) {
			log.error("Queue implementation has changed. Indicates a programming error. Job cancelled. An attempt to re-create the job will be made again once a ccts event occurs.");
			JobExecutionException ex = new JobExecutionException(false);
			ex.setUnscheduleAllTriggers(true);
			ex.setUnscheduleFiringTrigger(true);
			throw ex;
		}

		if (queue != null) {
			try {
				process(queue);
			} finally {
				jobDetail.getJobDataMap().put(CCTS_NOTIFICATIONS_QUEUE_KEY,
						queue);
			}
		} else {
			log.error("Unexpected null queue: CCTS_NOTIFICATIONS_QUEUE_KEY. Indicates a programming error. Job cancelled. An attempt to re-create the job will be made again once a ccts event occurs.");
			JobExecutionException ex = new JobExecutionException(false);
			ex.setUnscheduleAllTriggers(true);
			ex.setUnscheduleFiringTrigger(true);
			throw ex;
		}
		log.debug("Ended execution of the CCTSNotificationMessageJob");
	}

	/**
	 * @param context
	 * @throws JobExecutionException
	 * @throws SchedulerException
	 * @throws BeansException
	 */
	private void setBroadcasterInstance(JobExecutionContext context)
			throws JobExecutionException {
		try {
			final Scheduler scheduler = context.getScheduler();
			ApplicationContext applicationContext = (ApplicationContext) scheduler
					.getContext().get("applicationContext");
			this.cctsMessageBroadcaster = (CCTSMessageBroadcaster) applicationContext
					.getBean("cctsNotificationsBroadcaster");
		} catch (Exception e) {
			log.error("Unable to retrieve cctsNotificationsBroadcaster instance. Indicates a configuration error. CCTS notifications are disabled!");
			JobExecutionException ex = new JobExecutionException(false);
			throw ex;
		}
	}

	/**
	 * @param queue
	 */
	private void process(final Vector<CCTSNotification> queue) {
		boolean done;
		do {
			done = true;
			CCTSNotification notification = null;
			try {
				notification = queue.firstElement();
			} catch (NoSuchElementException e) {
			}
			if (notification == null) {
				log.debug("CCTS notification queue is empty; messages, if any, have been processed.");
			} else
				try {
					process(notification);
					queue.remove(notification);
					done = false;
				} catch (ESBCommunicationException e) {
					log.error(ExceptionUtils.getFullStackTrace(e));
					log.error("Processing of a CCTS notification failed: "
							+ notification);
					log.error("Failed notification will be put back into the queue for another re-try later.");
				} catch (Exception e) {
					log.error(ExceptionUtils.getFullStackTrace(e));
					log.error("Processing of a CCTS notification failed: "
							+ notification);
					log.error("This error indicates a problem with the message itself. It will be removed from the queue. No further delivery attempts will be made.");
					queue.remove(notification);
					done = false;
				}
		} while (!done);
	}

	private void process(CCTSNotification notification)
			throws BroadcastException {
		if (notification.getTimestamp() != null
				&& new Date().getTime() - notification.getTimestamp().getTime() > NOTIFICATION_TTL) {
			log.error("CCTS notification has been in queue for too long and has expired: "
					+ notification);
			return;
		}
		Notification jaxbObj = convert(notification);
		String xml = serialize(jaxbObj);
		log.debug("XML message to be sent to iHub: " + xml);
		Metadata metadata = new Metadata();
		metadata.setOperationName(OperationNameEnum.NA.getName());
		metadata.setServiceType(IHUB_SERVICE_NAME);
		metadata.setExternalIdentifier(notification.getExternalId());
		cctsMessageBroadcaster.broadcast(xml, metadata);
	}

	private String serialize(Notification notification) {
		try {
			JAXBContext context = JAXBContext.newInstance(Notification.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			JAXBElement<Notification> jaxbElement = new JAXBElement<Notification>(
					new QName(CCTS_NOTIFICATIONS_NS, "notification"),
					Notification.class, notification);
			CharArrayWriter writer = new CharArrayWriter();
			m.marshal(jaxbElement, writer);
			return String.valueOf(writer.toCharArray());
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

	}

	private Notification convert(CCTSNotification notification) {
		try {
			Notification jaxbNotification = new Notification();
			jaxbNotification.setApplication(Application.C_3_PR);
			jaxbNotification.setEventType(EventType.fromValue(notification
					.getEventType()));
			if (notification.getTimestamp() != null) {
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(notification.getTimestamp());
				DatatypeFactory df = DatatypeFactory.newInstance();
				XMLGregorianCalendar xmlDate = df.newXMLGregorianCalendar(gc);
				jaxbNotification.setTimestamp(xmlDate);
			}
			BusinessObjectID businessObjectID = new BusinessObjectID();
			businessObjectID.setValue(notification.getIdentifierValue());
			businessObjectID.setType(notification.getIdentifierType());
			jaxbNotification.setObjectId(businessObjectID);

			return jaxbNotification;
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Lightweight class that stores CCTS notification details. The reason for
	 * using this class, and not using {@link Notification} directly, is because
	 * this class is lighter (it does need to serialize into database), less
	 * volatile, and serializable ({@link Notification} is not serializable by
	 * default).
	 * 
	 * @author Denis G. Krylov
	 * 
	 */
	public static final class CCTSNotification implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3180003573480810064L;

		public CCTSNotification() {
		}

		private String identifierValue;
		private String eventType;
		private String identifierType;
		private Date timestamp = new Date();
		private final String externalId = UUID.randomUUID().toString();

		/**
		 * @return the identifierValue
		 */
		public final String getIdentifierValue() {
			return identifierValue;
		}

		/**
		 * @param identifierValue
		 *            the identifierValue to set
		 */
		public final void setIdentifierValue(String identifierValue) {
			this.identifierValue = identifierValue;
		}

		/**
		 * @return the eventType
		 */
		public final String getEventType() {
			return eventType;
		}

		/**
		 * @param eventType
		 *            the eventType to set
		 */
		public final void setEventType(String eventType) {
			this.eventType = eventType;
		}

		/**
		 * @return the identifierType
		 */
		public final String getIdentifierType() {
			return identifierType;
		}

		/**
		 * @param identifierType
		 *            the identifierType to set
		 */
		public final void setIdentifierType(String identifierType) {
			this.identifierType = identifierType;
		}

		/**
		 * @return the timestamp
		 */
		public final Date getTimestamp() {
			return timestamp;
		}

		/**
		 * @param timestamp
		 *            the timestamp to set
		 */
		public final void setTimestamp(Date timestamp) {
			this.timestamp = timestamp;
		}

		public String getExternalId() {
			return externalId;
		};

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "CCTSNotification [identifierValue=" + identifierValue
					+ ", eventType=" + eventType + ", identifierType="
					+ identifierType + ", timestamp=" + timestamp
					+ ", externalId=" + externalId + "]";
		}

	}

	/**
	 * @return the cctsMessageBroadcaster
	 */
	public final CCTSMessageBroadcaster getCctsMessageBroadcaster() {
		return cctsMessageBroadcaster;
	}

	public static void main(String[] args) {
		Vector v = new Vector();
		v.add(new CCTSNotification());
		System.out.println(v);
		byte[] b = SerializationUtils.serialize(v);
		Vector v2 = (Vector) SerializationUtils.deserialize(b);
		System.out.println(v2);
	}

}
