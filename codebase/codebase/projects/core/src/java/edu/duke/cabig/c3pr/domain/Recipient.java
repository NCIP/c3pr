package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "recipients")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "RECIPIENTS_ID_SEQ") })
public abstract class Recipient extends AbstractMutableDeletableDomainObject {
	
	private List<RecipientScheduledNotification>  recipientScheduledNotification;

    @OneToMany
    @Cascade(value = { CascadeType.LOCK})
    @JoinColumn(name = "recipients_id")
	public List<RecipientScheduledNotification> getRecipientScheduledNotification() {
		return recipientScheduledNotification;
	}

	public void setRecipientScheduledNotification(
			List<RecipientScheduledNotification> recipientScheduledNotification) {
		this.recipientScheduledNotification = recipientScheduledNotification;
	}
	

}
