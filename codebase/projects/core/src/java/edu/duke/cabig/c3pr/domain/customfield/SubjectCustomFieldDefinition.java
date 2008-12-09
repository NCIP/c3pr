package edu.duke.cabig.c3pr.domain.customfield;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "SUBJECT")
public class SubjectCustomFieldDefinition extends CustomFieldDefinition {

}
