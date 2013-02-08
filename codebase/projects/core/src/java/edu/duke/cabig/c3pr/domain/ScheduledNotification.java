/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

/**
 * The Class ScheduledNotification.
 */
@Entity
@Table(name = "schld_notfns")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "schld_notfns_ID_SEQ") })
public class ScheduledNotification extends AbstractMutableDeletableDomainObject {
	
	/** The message. */
	private String message;
	
	/** The title. */
	private String title;
	
	/** The date sent. */
	private Date dateSent;
	
	/** The lazy list helper. */
	private LazyListHelper lazyListHelper;
	
	/** The study organization. */
	private StudyOrganization studyOrganization;
	
	private String eventId;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 * Instantiates a new scheduled notification.
	 */
	public ScheduledNotification() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(RecipientScheduledNotification.class, new InstantiateFactory<RecipientScheduledNotification>(
        		RecipientScheduledNotification.class));
	}
	
	/**
	 * Gets the html message.
	 * 
	 * @return the html message
	 */
	@Transient
	public String getHtmlMessage(){
		if(message == null){
			return  "";
		} else {
			if(message.startsWith("<html>")){
				return message.substring(12, message.length()-14 );
			} else {
				return message;
			}
		}
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
	 * Gets the date sent.
	 * 
	 * @return the date sent
	 */
	public Date getDateSent() {
		return dateSent;
	}

	/**
	 * Sets the date sent.
	 * 
	 * @param dateSent the new date sent
	 */
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

    /**
     * Gets the recipient scheduled notification internal.
     * 
     * @return the recipient scheduled notification internal
     */
    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.SAVE_UPDATE, CascadeType.MERGE})
    @JoinColumn(name = "schld_notfns_id")
    @Where(clause = "retired_indicator  = 'false'")
	public List<RecipientScheduledNotification> getRecipientScheduledNotificationInternal() {
        return lazyListHelper.getInternalList(RecipientScheduledNotification.class);
    }

    /**
     * Sets the recipient scheduled notification internal.
     * 
     * @param recipientScheduledNotification the new recipient scheduled notification internal
     */
    public void setRecipientScheduledNotificationInternal(List<RecipientScheduledNotification> recipientScheduledNotification) {
        lazyListHelper.setInternalList(RecipientScheduledNotification.class, recipientScheduledNotification);
    }

    /**
     * Gets the recipient scheduled notification.
     * 
     * @return the recipient scheduled notification
     */
    @Transient
    public List<RecipientScheduledNotification> getRecipientScheduledNotification() {
        return lazyListHelper.getLazyList(RecipientScheduledNotification.class);
    }


    /**
     * Gets the study organization.
     * 
     * @return the study organization
     */
    @OneToOne
    @Cascade(value = { CascadeType.LOCK})
    @JoinColumn(name = "study_org_id")
	public StudyOrganization getStudyOrganization() {
		return studyOrganization;
	}

	/**
	 * Sets the study organization.
	 * 
	 * @param studyOrganization the new study organization
	 */
	public void setStudyOrganization(StudyOrganization studyOrganization) {
		this.studyOrganization = studyOrganization;
	}
	
}
