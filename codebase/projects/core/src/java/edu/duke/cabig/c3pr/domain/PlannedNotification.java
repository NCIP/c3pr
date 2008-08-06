package edu.duke.cabig.c3pr.domain;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

import edu.duke.cabig.c3pr.constants.DeliveryMechanismType;
import edu.duke.cabig.c3pr.constants.NotificationEventType;
import edu.duke.cabig.c3pr.constants.NotificationFrequencyEnum;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@Table(name = "planned_notfns")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "planned_notfns_ID_SEQ") })
public class PlannedNotification extends AbstractMutableDeletableDomainObject {

    private Integer threshold;

    private NotificationFrequencyEnum frequency;
    
    private String message;
    
    private String title;
    
    private DeliveryMechanismType deliveryMechanism;
    
    private NotificationEventType eventName;
    
    private LazyListHelper lazyListHelper;

    public PlannedNotification() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(UserBasedRecipient.class, new InstantiateFactory<UserBasedRecipient>(
        		UserBasedRecipient.class));
        lazyListHelper.add(RoleBasedRecipient.class, new InstantiateFactory<RoleBasedRecipient>(
                        RoleBasedRecipient.class));
        lazyListHelper.add(ContactMechanismBasedRecipient.class, new InstantiateFactory<ContactMechanismBasedRecipient>(
        		ContactMechanismBasedRecipient.class));
        lazyListHelper.add(ScheduledNotification.class, new InstantiateFactory<ScheduledNotification>(
        		ScheduledNotification.class));
    }

    @Override
    @Transient
    public void setRetiredIndicatorAsTrue() {
        super.setRetiredIndicatorAsTrue();
        //this.setRetiredIndicatorAsTrue();
        Iterator iter = getUserBasedRecipient().iterator();
        while(iter.hasNext()){
        	((UserBasedRecipient)iter.next()).setRetiredIndicatorAsTrue();
        }
        iter = getRoleBasedRecipient().iterator();
        while(iter.hasNext()){
        	((RoleBasedRecipient)iter.next()).setRetiredIndicatorAsTrue();
        }
        iter = getContactMechanismBasedRecipient().iterator();
        while(iter.hasNext()){
        	((ContactMechanismBasedRecipient)iter.next()).setRetiredIndicatorAsTrue();
        }
        iter = getScheduledNotification().iterator();
        while(iter.hasNext()){
        	((ScheduledNotification)iter.next()).setRetiredIndicatorAsTrue();
        }
    }

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "planned_notfns_id", nullable = false)
    @Where(clause = "DTYPE = 'ER' and retired_indicator  = 'false'")
    public List<UserBasedRecipient> getUserBasedRecipientInternal() {
        return lazyListHelper.getInternalList(UserBasedRecipient.class);
    }

    public void setUserBasedRecipientInternal(List<UserBasedRecipient> userBasedRecipient) {
        lazyListHelper.setInternalList(UserBasedRecipient.class, userBasedRecipient);
    }

    @Transient
    public List<UserBasedRecipient> getUserBasedRecipient() {
        return lazyListHelper.getLazyList(UserBasedRecipient.class);
    }

    public void setUserBasedRecipient(List<UserBasedRecipient> userBasedRecipient) {
    }

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "planned_notfns_id", nullable = false)
    @Where(clause = "DTYPE = 'RR' and retired_indicator  = 'false'")
    public List<RoleBasedRecipient> getRoleBasedRecipientInternal() {
        return lazyListHelper.getInternalList(RoleBasedRecipient.class);
    }

    public void setRoleBasedRecipientInternal(List<RoleBasedRecipient> roleBasedRecipient) {
        lazyListHelper.setInternalList(RoleBasedRecipient.class, roleBasedRecipient);
    }

    @Transient
    public List<RoleBasedRecipient> getRoleBasedRecipient() {
        return lazyListHelper.getLazyList(RoleBasedRecipient.class);
    }

    public void setRoleBasedRecipient(List<RoleBasedRecipient> roleBasedRecipient) {
    }
    
    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "planned_notfns_id", nullable = false)
    @Where(clause = "DTYPE = 'CMR' and retired_indicator  = 'false'")
    public List<ContactMechanismBasedRecipient> getContactMechanismBasedRecipientInternal() {
        return lazyListHelper.getInternalList(ContactMechanismBasedRecipient.class);
    }

    public void setContactMechanismBasedRecipientInternal(List<ContactMechanismBasedRecipient> contactMechanismBasedRecipient) {
        lazyListHelper.setInternalList(ContactMechanismBasedRecipient.class, contactMechanismBasedRecipient);
    }

    @Transient
    public List<ContactMechanismBasedRecipient> getContactMechanismBasedRecipient() {
        return lazyListHelper.getLazyList(ContactMechanismBasedRecipient.class);
    }

    public void setContactMechanismBasedRecipient(List<ContactMechanismBasedRecipient> contactMechanismBasedRecipient) {
    }
    
    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "planned_notfns_id", nullable = false)
    @Where(clause = "retired_indicator  = 'false'")
    @OrderBy(clause="date_sent desc")
    public List<ScheduledNotification> getScheduledNotificationInternal() {
        return lazyListHelper.getInternalList(ScheduledNotification.class);
    }

    public void setScheduledNotificationInternal(List<ScheduledNotification> scheduledNotification) {
        lazyListHelper.setInternalList(ScheduledNotification.class, scheduledNotification);
    }

    @Transient
    public List<ScheduledNotification> getScheduledNotification() {
        return lazyListHelper.getLazyList(ScheduledNotification.class);
    }

    public void setScheduledNotification(List<ScheduledNotification> scheduledNotification) {
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    @Transient
    public String getEmailAddresses() {
        String emailAddresses = "";
        for (UserBasedRecipient eRec : getUserBasedRecipient()) {
            emailAddresses += eRec.getEmailAddress() + " <br/> ";
        }
        return emailAddresses;
    }

    @Transient
    public String getRoles() {
        String roles = "";
        for (RoleBasedRecipient rRec : getRoleBasedRecipient()) {
            roles += rRec.getRole() + " <br/> ";
        }
        return roles;
    }

    @Enumerated(EnumType.STRING)
	public NotificationFrequencyEnum getFrequency() {
		return frequency;
	}

	public void setFrequency(NotificationFrequencyEnum frequency) {
		this.frequency = frequency;
	}

	@Enumerated(EnumType.STRING)
	public DeliveryMechanismType getDeliveryMechanism() {
		return deliveryMechanism;
	}

	public void setDeliveryMechanism(DeliveryMechanismType deliveryMechanism) {
		this.deliveryMechanism = deliveryMechanism;
	}

	@Enumerated(EnumType.STRING)
	public NotificationEventType getEventName() {
		return eventName;
	}

	public void setEventName(NotificationEventType eventName) {
		this.eventName = eventName;
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

}
