package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class OrganizationAssignedIdentifier.
 */
@Entity
@DiscriminatorValue("OFF_TREATMENT")
public class OffTreatmentReason extends Reason {
}
