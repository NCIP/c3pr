package edu.duke.cabig.c3pr.domain.customfield;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "STUDYSUBJECT")
public class StudySubjectCustomFieldDefinition extends CustomFieldDefinition {

}
