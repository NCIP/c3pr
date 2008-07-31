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
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.ScheduledEmailNotificationDeliveryStatusEnum;

@Entity
@Table(name = "rpt_schld_notfns")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "rpt_schld_notfns_ID_SEQ") })
public class RecipientScheduledNotification extends AbstractMutableDeletableDomainObject {
	
	private Date dateRead;
	
	private Boolean isRead;
	
	private ScheduledEmailNotificationDeliveryStatusEnum deliverystatus;
	
	private Recipient recipient;

	@ManyToOne
    @JoinColumn(name = "recipients_id")
    @Cascade(value = { CascadeType.LOCK })
	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}
	
	public Date getDateRead() {
		return dateRead;
	}

	public void setDateRead(Date dateRead) {
		this.dateRead = dateRead;
	}

	@Enumerated(EnumType.STRING)
	public ScheduledEmailNotificationDeliveryStatusEnum getDeliverystatus() {
		return deliverystatus;
	}

	public void setDeliverystatus(
			ScheduledEmailNotificationDeliveryStatusEnum deliverystatus) {
		this.deliverystatus = deliverystatus;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

}
