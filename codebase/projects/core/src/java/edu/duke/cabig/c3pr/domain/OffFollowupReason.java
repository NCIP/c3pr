package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class OrganizationAssignedIdentifier.
 */
@Entity
@DiscriminatorValue("OFF_FOLLOWUP")
public class OffFollowupReason extends Reason {
}
