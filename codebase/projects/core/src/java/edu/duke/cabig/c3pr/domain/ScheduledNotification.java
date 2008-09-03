package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "schld_notfns")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "schld_notfns_ID_SEQ") })
public class ScheduledNotification extends AbstractMutableDeletableDomainObject {
	
	private String message;
	
	private String title;
	
	private Date dateSent;
	
	private LazyListHelper lazyListHelper;

	public ScheduledNotification() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(RecipientScheduledNotification.class, new InstantiateFactory<RecipientScheduledNotification>(
        		RecipientScheduledNotification.class));
	}
	
	@Transient
	public String getHtmlMessage(){
		if(message.startsWith("<html>")){
			return message.substring(12, message.length()-14 );
		} else {
			return message;
		}
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "schld_notfns_id")
    @Where(clause = "retired_indicator  = 'false'")
	public List<RecipientScheduledNotification> getRecipientScheduledNotificationInternal() {
        return lazyListHelper.getInternalList(RecipientScheduledNotification.class);
    }

    public void setRecipientScheduledNotificationInternal(List<RecipientScheduledNotification> recipientScheduledNotification) {
        lazyListHelper.setInternalList(RecipientScheduledNotification.class, recipientScheduledNotification);
    }

    @Transient
    public List<RecipientScheduledNotification> getRecipientScheduledNotification() {
        return lazyListHelper.getLazyList(RecipientScheduledNotification.class);
    }

    public void setRecipientScheduledNotification(List<RecipientScheduledNotification> recipientScheduledNotification) {
    }
    
    @Override
    @Transient
    public void setRetiredIndicatorAsTrue() {
        super.setRetiredIndicatorAsTrue();
        this.setRetiredIndicatorAsTrue();
    }
	
}
