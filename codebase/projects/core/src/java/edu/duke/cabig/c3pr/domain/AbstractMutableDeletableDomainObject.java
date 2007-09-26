package edu.duke.cabig.c3pr.domain;

import javax.persistence.MappedSuperclass;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

@MappedSuperclass
public class AbstractMutableDeletableDomainObject extends
		AbstractMutableDomainObject {
	
	
	private String retiredIndicator = "false";

	public String getRetiredIndicator() {
		return retiredIndicator;
	}

	public void setRetiredIndicator(String retiredIndicator) {
		this.retiredIndicator = retiredIndicator;
	}

	
}
