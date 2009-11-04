package edu.duke.cabig.c3pr.domain.customfield;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "STUDY")
public class StudyCustomFieldDefinition extends CustomFieldDefinition {
	
}
