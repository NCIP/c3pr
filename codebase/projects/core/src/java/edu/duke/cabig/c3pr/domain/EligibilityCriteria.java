package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Priyatam
 */
@Entity
@Table(name = "eligibility_criterias")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "ELIGIBILITY_CRITERIAS_ID_SEQ") })
public abstract class EligibilityCriteria extends AbstractMutableDomainObject {
	private Boolean notApplicableIndicator;

	private int questionNumber;

	private String questionText;

	private String name;

	
	public Boolean getNotApplicableIndicator() {
		return notApplicableIndicator;
	}

	public void setNotApplicableIndicator(Boolean notApplicableInidicator) {
		this.notApplicableIndicator = notApplicableInidicator;
	}

	

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	@Transient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}
}