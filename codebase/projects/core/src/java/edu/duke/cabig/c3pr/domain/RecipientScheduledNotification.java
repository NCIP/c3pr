package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.EmailNotificationDeliveryStatusEnum;

/**
 * The Class RecipientScheduledNotification.
 * This is a part of the notification framework. This maintains a copy of the notification received
 * by the recipient and the details related this association. 
 */
@Entity
@Table(name = "rpt_schld_notfns")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "rpt_schld_notfns_ID_SEQ") })
public class RecipientScheduledNotification extends AbstractMutableDeletableDomainObject {
	
	/** The date the message is read by the user. */
	private Date dateRead;
	
	/** The flag which indicates if the mail is read or not. */
	private Boolean isRead;
	
	/** The delivery status of the notification. */
	private EmailNotificationDeliveryStatusEnum deliveryStatus;
	
	/** The recipient. */
	private Recipient recipient;
	
	/** The scheduled notification (template of notification to be sent). */
	private ScheduledNotification scheduledNotification;

	/**
	 * Gets the recipient.
	 * 
	 * @return the recipient
	 */
	@ManyToOne
    @JoinColumn(name = "recipients_id")
    @Cascade(value = { CascadeType.LOCK })
	public Recipient getRecipient() {
		return recipient;
	}

	/**
	 * Sets the recipient.
	 * 
	 * @param recipient the new recipient
	 */
	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}
	
	/**
	 * Gets the date read.
	 * 
	 * @return the date read
	 */
	public Date getDateRead() {
		return dateRead;
	}

	/**
	 * Sets the date read.
	 * 
	 * @param dateRead the new date read
	 */
	public void setDateRead(Date dateRead) {
		this.dateRead = dateRead;
	}

	/**
	 * Gets the delivery status.
	 * 
	 * @return the delivery status
	 */
	@Enumerated(EnumType.STRING)
	public EmailNotificationDeliveryStatusEnum getDeliveryStatus() {
		return deliveryStatus;
	}

	/**
	 * Sets the delivery status.
	 * 
	 * @param deliveryStatus the new delivery status
	 */
	public void setDeliveryStatus(
			EmailNotificationDeliveryStatusEnum deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	/**
	 * Gets the checks if is read.
	 * 
	 * @return the checks if is read
	 */
	public Boolean getIsRead() {
		return isRead;
	}

	/**
	 * Sets the checks if is read.
	 * 
	 * @param isRead the new checks if is read
	 */
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

    /**
     * Gets the scheduled notification.
     * 
     * @return the scheduled notification
     */
    @ManyToOne
    @Cascade(value = { CascadeType.LOCK})
    @JoinColumn(name = "schld_notfns_id", nullable = false)
    @OrderBy(clause="date_sent desc")
	public ScheduledNotification getScheduledNotification() {
		return scheduledNotification;
	}

	/**
	 * Sets the scheduled notification.
	 * 
	 * @param scheduledNotification the new scheduled notification
	 */
	public void setScheduledNotification(ScheduledNotification scheduledNotification) {
		this.scheduledNotification = scheduledNotification;
	}

}
