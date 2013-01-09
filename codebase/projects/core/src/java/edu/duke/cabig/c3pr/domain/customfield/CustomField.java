/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.customfield;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;

@Entity
@Table(name = "custom_fields")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CUSTOM_FIELDS_ID_SEQ") })
public abstract class CustomField extends AbstractMutableDeletableDomainObject {
	private Study study ;
	private StudySubject studySubject;
	private Participant participant ;
	private StudySubjectDemographics studySubjectDemographics;

	private CustomFieldDefinition customFieldDefinition ;
	
	@ManyToOne
    @JoinColumn(name = "stu_id")
	public Study getStudy() {
		return study;
	}
	
	public void setStudy(Study study) {
		this.study = study;
	}
	
	@ManyToOne
    @JoinColumn(name = "stu_sub_id")
	public StudySubject getStudySubject() {
		return studySubject;
	}
	
	public void setStudySubject(StudySubject studySubject) {
		this.studySubject = studySubject;
	}
	
	@ManyToOne
    @JoinColumn(name = "sub_id")
	public Participant getParticipant() {
		return participant;
	}
	
	public void setParticipant(Participant participant) {
		this.participant = participant;
	}
	
	@ManyToOne
    @JoinColumn(name = "stu_sub_dmgphcs_id")	
	public StudySubjectDemographics getStudySubjectDemographics() {
		return studySubjectDemographics;
	}

	public void setStudySubjectDemographics(
			StudySubjectDemographics studySubjectDemographics) {
		this.studySubjectDemographics = studySubjectDemographics;
	}

	@ManyToOne
    @JoinColumn(name = "cust_field_def_id")
	public CustomFieldDefinition getCustomFieldDefinition() {
		return customFieldDefinition;
	}
	
	public void setCustomFieldDefinition(CustomFieldDefinition customFieldDefinition) {
		this.customFieldDefinition = customFieldDefinition;
	}
}


