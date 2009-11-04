package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class AbstractMutableDeletableDomainObject extends AbstractMutableDomainObject {

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((retiredIndicator == null) ? 0 : retiredIndicator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractMutableDeletableDomainObject other = (AbstractMutableDeletableDomainObject) obj;
		if (retiredIndicator == null) {
			if (other.retiredIndicator != null)
				return false;
		} else if (!retiredIndicator.equals(other.retiredIndicator))
			return false;
		return true;
	}

	private String retiredIndicator = "false";

    public String getRetiredIndicator() {
        return retiredIndicator;
    }

    private void setRetiredIndicator(String retiredIndicator) {
        this.retiredIndicator = retiredIndicator;
    }

    public void setRetiredIndicatorAsTrue() {
        this.setRetiredIndicator("true");
    }

    public void setRetiredIndicatorAsFalse() {
        this.setRetiredIndicator("false");
    }

    @Transient
    public int getHashCode() {
        return this.hashCode();
    }
}
