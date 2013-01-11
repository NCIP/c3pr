/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
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
import org.hibernate.annotations.Where;

@Entity
@Table(name = "recipients")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "RECIPIENTS_ID_SEQ") })
public abstract class Recipient extends AbstractMutableDeletableDomainObject {
	
	private List<RecipientScheduledNotification>  recipientScheduledNotifications;

    @OneToMany
    @Cascade(value = { CascadeType.LOCK})
    @JoinColumn(name = "recipients_id")
    @Where(clause = "retired_indicator  = 'false'")
	public List<RecipientScheduledNotification> getRecipientScheduledNotifications() {
		return recipientScheduledNotifications;
	}

	public void setRecipientScheduledNotifications(
			List<RecipientScheduledNotification> recipientScheduledNotifications) {
		this.recipientScheduledNotifications = recipientScheduledNotifications;
	}
	

}
