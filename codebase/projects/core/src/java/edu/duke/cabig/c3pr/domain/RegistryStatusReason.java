package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class OrganizationAssignedIdentifier.
 */
@Entity
@DiscriminatorValue("REGISTRY_STATUS")
public class RegistryStatusReason extends Reason {
	public RegistryStatusReason() {
		super();
	}

	public RegistryStatusReason(String code, String description,
			Reason primaryReason, Boolean primaryIndicator) {
		super(code, description, primaryReason, primaryIndicator);
	}
	
}
