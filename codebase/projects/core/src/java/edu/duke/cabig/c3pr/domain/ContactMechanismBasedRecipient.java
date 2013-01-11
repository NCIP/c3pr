/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * The Class ContactMechanismBasedRecipient.
 * Primarily used by the system notifications use case.
 * This is used to add an email which is not in the database to the notification recipient list
 * 
 * @author Vinay Gangoli
 */
@Entity
@DiscriminatorValue(value = "CMR")
public class ContactMechanismBasedRecipient extends Recipient {

	/** The lazy list helper. */
	private LazyListHelper lazyListHelper;
	
	/**
	 * Instantiates a new contact mechanism based recipient.
	 */
	public ContactMechanismBasedRecipient() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(ContactMechanism.class, new InstantiateFactory<ContactMechanism>(
        		ContactMechanism.class));
	}
	
    /**
     * Gets the contact mechanisms internal.
     * 
     * @return the contact mechanisms internal
     */
    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "recipients_id")
    @Where(clause = "retired_indicator  = 'false'")
    public List<ContactMechanism> getContactMechanismsInternal() {
        return lazyListHelper.getInternalList(ContactMechanism.class);
    }

    /**
     * Sets the contact mechanisms internal.
     * 
     * @param contactMechanisms the new contact mechanisms internal
     */
    public void setContactMechanismsInternal(List<ContactMechanism> contactMechanisms) {
        lazyListHelper.setInternalList(ContactMechanism.class, contactMechanisms);
    }

    /**
     * Gets the contact mechanisms.
     * 
     * @return the contact mechanisms
     */
    @Transient
    public List<ContactMechanism> getContactMechanisms() {
        return lazyListHelper.getLazyList(ContactMechanism.class);
    }

}
