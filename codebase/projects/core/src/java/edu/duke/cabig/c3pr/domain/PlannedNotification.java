/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.constants.DeliveryMechanismEnum;
import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.constants.NotificationFrequencyEnum;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * The Class PlannedNotification.
 * The heart of the notification model. This class maintains notification related details like
 * the freq , type, message content, deliveryMechanism etc. 
 * Every org can have a bunch of these configured.
 * 
 * Also used by study level notifications use case.
 * 
 */
@Entity
@Table(name = "planned_notfns")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "planned_notfns_ID_SEQ") })
@Where(clause = "retired_indicator  = 'false'")
public class PlannedNotification extends AbstractMutableDeletableDomainObject {

    /** The study threshold. */
    private Integer studyThreshold;
    
    /** The study site threshold. */
    private Integer studySiteThreshold;

    /** The frequency. */
    private NotificationFrequencyEnum frequency;
    
    /** The message. */
    private String message;
    
    /** The title. */
    private String title;
    
    /** The delivery mechanism. */
    private DeliveryMechanismEnum deliveryMechanism;
    
    /** The event name. */
    private NotificationEventTypeEnum eventName;
    
    /** The lazy list helper. */
    private LazyListHelper lazyListHelper;
    
    /** The healthcare site. */
    private HealthcareSite healthcareSite;

    /**
     * Instantiates a new planned notification.
     */
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

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#setRetiredIndicatorAsTrue()
     */
    @Override
    @Transient
    public void setRetiredIndicatorAsTrue() {
        super.setRetiredIndicatorAsTrue();
        Iterator iter = getUserBasedRecipient().iterator();
        while(iter.hasNext()){
        	((UserBasedRecipient)iter.next()).setRetiredIndicatorAsTrue();
        }
        iter = getRoleBasedRecipient().iterator();
        while(iter.hasNext()){
        	((RoleBasedRecipient)iter.next()).setRetiredIndicatorAsTrue();
        }
        //REINTRODUCE this when we start using Contact mechanism based Recpt
//        iter = getContactMechanismBasedRecipient().iterator();
//        while(iter.hasNext()){
//        	((ContactMechanismBasedRecipient)iter.next()).setRetiredIndicatorAsTrue();
//        }
        
//        iter = getScheduledNotifications().iterator();
//        while(iter.hasNext()){
//        	((ScheduledNotification)iter.next()).setRetiredIndicatorAsTrue();
//        }
    }

    /**
     * Gets the user based recipient internal.
     * 
     * @return the user based recipient internal
     */
    @OneToMany(fetch = FetchType.LAZY,orphanRemoval=true)
    @Cascade(value = { CascadeType.ALL})
    @JoinColumn(name = "planned_notfns_id", nullable = false)
    @Where(clause = "DTYPE = 'ER' and retired_indicator  = 'false'")
    public List<UserBasedRecipient> getUserBasedRecipientInternal() {
        return lazyListHelper.getInternalList(UserBasedRecipient.class);
    }

    /**
     * Sets the user based recipient internal.
     * 
     * @param userBasedRecipient the new user based recipient internal
     */
    public void setUserBasedRecipientInternal(List<UserBasedRecipient> userBasedRecipient) {
        lazyListHelper.setInternalList(UserBasedRecipient.class, userBasedRecipient);
    }

    /**
     * Gets the user based recipient.
     * 
     * @return the user based recipient
     */
    @Transient
    public List<UserBasedRecipient> getUserBasedRecipient() {
        return lazyListHelper.getLazyList(UserBasedRecipient.class);
    }

    /**
     * Sets the user based recipient.
     * 
     * @param userBasedRecipient the new user based recipient
     */
    public void setUserBasedRecipient(List<UserBasedRecipient> userBasedRecipient) {
    }

    /**
     * Gets the role based recipient internal.
     * 
     * @return the role based recipient internal
     */
    @OneToMany(fetch = FetchType.LAZY,orphanRemoval=true)
    @Cascade(value = { CascadeType.ALL})
    @JoinColumn(name = "planned_notfns_id", nullable = false)
    @Where(clause = "DTYPE = 'RR' and retired_indicator  = 'false'")
    public List<RoleBasedRecipient> getRoleBasedRecipientInternal() {
        return lazyListHelper.getInternalList(RoleBasedRecipient.class);
    }

    /**
     * Sets the role based recipient internal.
     * 
     * @param roleBasedRecipient the new role based recipient internal
     */
    public void setRoleBasedRecipientInternal(List<RoleBasedRecipient> roleBasedRecipient) {
        lazyListHelper.setInternalList(RoleBasedRecipient.class, roleBasedRecipient);
    }

    /**
     * Gets the role based recipient.
     * 
     * @return the role based recipient
     */
    @Transient
    public List<RoleBasedRecipient> getRoleBasedRecipient() {
        return lazyListHelper.getLazyList(RoleBasedRecipient.class);
    }

    /**
     * Sets the role based recipient.
     * 
     * @param roleBasedRecipient the new role based recipient
     */
    public void setRoleBasedRecipient(List<RoleBasedRecipient> roleBasedRecipient) {
    }
    
    /**
     * Gets the contact mechanism based recipient internal.
     * 
     * @return the contact mechanism based recipient internal
     */
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
    @Cascade(value = { CascadeType.ALL})
    @JoinColumn(name = "planned_notfns_id", nullable = false)
    @Where(clause = "DTYPE = 'CMR' and retired_indicator  = 'false'")
    public List<ContactMechanismBasedRecipient> getContactMechanismBasedRecipientInternal() {
        return lazyListHelper.getInternalList(ContactMechanismBasedRecipient.class);
    }

    /**
     * Sets the contact mechanism based recipient internal.
     * 
     * @param contactMechanismBasedRecipient the new contact mechanism based recipient internal
     */
    public void setContactMechanismBasedRecipientInternal(List<ContactMechanismBasedRecipient> contactMechanismBasedRecipient) {
        lazyListHelper.setInternalList(ContactMechanismBasedRecipient.class, contactMechanismBasedRecipient);
    }

    /**
     * Gets the contact mechanism based recipient.
     * 
     * @return the contact mechanism based recipient
     */
    @Transient
    public List<ContactMechanismBasedRecipient> getContactMechanismBasedRecipient() {
        return lazyListHelper.getLazyList(ContactMechanismBasedRecipient.class);
    }

    /**
     * Sets the contact mechanism based recipient.
     * 
     * @param contactMechanismBasedRecipient the new contact mechanism based recipient
     */
    public void setContactMechanismBasedRecipient(List<ContactMechanismBasedRecipient> contactMechanismBasedRecipient) {
    }
    
    /**
     * Gets the scheduled notifications internal.
     * 
     * @return the scheduled notifications internal
     */
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.MERGE})
    @JoinColumn(name = "planned_notfns_id", nullable = false)
    @Where(clause = "retired_indicator  = 'false'")
//    @OrderBy(clause="date_sent desc")
    public List<ScheduledNotification> getScheduledNotificationsInternal() {
        return lazyListHelper.getInternalList(ScheduledNotification.class);
    }

    /**
     * Sets the scheduled notifications internal.
     * 
     * @param scheduledNotifications the new scheduled notifications internal
     */
    public void setScheduledNotificationsInternal(List<ScheduledNotification> scheduledNotifications) {
        lazyListHelper.setInternalList(ScheduledNotification.class, scheduledNotifications);
    }

    /**
     * Gets the scheduled notifications.
     * 
     * @return the scheduled notifications
     */
    @Transient
    public List<ScheduledNotification> getScheduledNotifications() {
        return lazyListHelper.getLazyList(ScheduledNotification.class);
    }

    /**
     * Sets the scheduled notifications.
     * 
     * @param scheduledNotifications the new scheduled notifications
     */
    public void setScheduledNotifications(List<ScheduledNotification> scheduledNotifications) {
    }

    /**
     * Gets the email addresses.
     * 
     * @return the email addresses
     */
    @Transient
    public String getEmailAddresses() {
        String emailAddresses = "";
        for (ContactMechanismBasedRecipient cmbr : getContactMechanismBasedRecipient()) {
        	for (ContactMechanism cm : cmbr.getContactMechanisms()) {
        		if(cm.getType().getCode().equalsIgnoreCase(ContactMechanismType.EMAIL.getCode())){
        			emailAddresses += cm.getValue() + " <br/> ";
        		}
            }
        }
        for (UserBasedRecipient eRec : getUserBasedRecipient()) {
            emailAddresses += eRec.getEmailAddress() + " <br/> ";
        }
        return emailAddresses;
    }

    /**
     * Gets the roles.
     * 
     * @return the roles
     */
    @Transient
    public String getRoles() {
        String roles = "";
        for (RoleBasedRecipient rRec : getRoleBasedRecipient()) {
            roles += rRec.getRole() + " <br/> ";
        }
        return roles;
    }

    /**
     * Gets the frequency.
     * 
     * @return the frequency
     */
    @Enumerated(EnumType.STRING)
	public NotificationFrequencyEnum getFrequency() {
		return frequency;
	}

	/**
	 * Sets the frequency.
	 * 
	 * @param frequency the new frequency
	 */
	public void setFrequency(NotificationFrequencyEnum frequency) {
		this.frequency = frequency;
	}

	/**
	 * Gets the delivery mechanism.
	 * 
	 * @return the delivery mechanism
	 */
	@Enumerated(EnumType.STRING)
	public DeliveryMechanismEnum getDeliveryMechanism() {
		return deliveryMechanism;
	}

	/**
	 * Sets the delivery mechanism.
	 * 
	 * @param deliveryMechanism the new delivery mechanism
	 */
	public void setDeliveryMechanism(DeliveryMechanismEnum deliveryMechanism) {
		this.deliveryMechanism = deliveryMechanism;
	}

	/**
	 * Gets the event name.
	 * 
	 * @return the event name
	 */
	@Enumerated(EnumType.STRING)
	public NotificationEventTypeEnum getEventName() {
		return eventName;
	}

	/**
	 * Sets the event name.
	 * 
	 * @param eventName the new event name
	 */
	public void setEventName(NotificationEventTypeEnum eventName) {
		this.eventName = eventName;
	}

	/**
	 * Gets the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 * 
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the study site threshold.
	 * 
	 * @return the study site threshold
	 */
	public Integer getStudySiteThreshold() {
		return studySiteThreshold;
	}

	/**
	 * Sets the study site threshold.
	 * 
	 * @param studySiteThreshold the new study site threshold
	 */
	public void setStudySiteThreshold(Integer studySiteThreshold) {
		this.studySiteThreshold = studySiteThreshold;
	}

	/**
	 * Gets the study threshold.
	 * 
	 * @return the study threshold
	 */
	public Integer getStudyThreshold() {
		return studyThreshold;
	}

	/**
	 * Sets the study threshold.
	 * 
	 * @param studyThreshold the new study threshold
	 */
	public void setStudyThreshold(Integer studyThreshold) {
		this.studyThreshold = studyThreshold;
	}

	/**
	 * Gets the healthcare site.
	 * 
	 * @return the healthcare site
	 */
	@ManyToOne
	@JoinColumn(name = "organizations_id")
	@Cascade(value = CascadeType.LOCK)
    @Where(clause = "retired_indicator  = 'false'")
	public HealthcareSite getHealthcareSite() {
		return healthcareSite;
	}

	/**
	 * Sets the healthcare site.
	 * 
	 * @param healthcareSite the new healthcare site
	 */
	public void setHealthcareSite(HealthcareSite healthcareSite) {
		this.healthcareSite = healthcareSite;
	}

}
