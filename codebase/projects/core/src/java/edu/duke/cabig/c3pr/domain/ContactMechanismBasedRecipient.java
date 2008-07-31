package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;

@Entity
@DiscriminatorValue(value = "CMR")
public class ContactMechanismBasedRecipient extends Recipient {

    private List<ContactMechanism> contactMechanism;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "recipients_id")
    @Where(clause = "retired_indicator  = 'false'")
    public List<ContactMechanism> getContactMechanism() {
		return contactMechanism;
	}

	public void setContactMechanism(List<ContactMechanism> contactMechanism) {
		this.contactMechanism = contactMechanism;
	}

	@Override
    @Transient
    public void setRetiredIndicatorAsTrue() {
        super.setRetiredIndicatorAsTrue();
        this.setRetiredIndicatorAsTrue();
    }
}
