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

@Entity
@DiscriminatorValue(value = "CMR")
public class ContactMechanismBasedRecipient extends Recipient {

	private LazyListHelper lazyListHelper;
	
//    private List<ContactMechanism> contactMechanism = new ArrayList<ContactMechanism>();

	
	public ContactMechanismBasedRecipient() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(ContactMechanism.class, new InstantiateFactory<ContactMechanism>(
        		ContactMechanism.class));
	}
	
    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "recipients_id")
    @Where(clause = "retired_indicator  = 'false'")
    public List<ContactMechanism> getContactMechanismInternal() {
        return lazyListHelper.getInternalList(ContactMechanism.class);
    }

    public void setContactMechanismInternal(List<ContactMechanism> contactMechanism) {
        lazyListHelper.setInternalList(ContactMechanism.class, contactMechanism);
    }

    @Transient
    public List<ContactMechanism> getContactMechanism() {
        return lazyListHelper.getLazyList(ContactMechanism.class);
    }

    public void setContactMechanism(List<ContactMechanism> contactMechanism) {
    }
//    public List<ContactMechanism> getContactMechanism() {
//		return contactMechanism;
//	}
//
//	public void setContactMechanism(List<ContactMechanism> contactMechanism) {
//		this.contactMechanism = contactMechanism;
//	}

	@Override
    @Transient
    public void setRetiredIndicatorAsTrue() {
        super.setRetiredIndicatorAsTrue();
        this.setRetiredIndicatorAsTrue();
    }
}
