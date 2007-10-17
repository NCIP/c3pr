package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

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
@Table(name = "eligibility_criteria")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "ELIGIBILITY_CRITERIA_ID_SEQ") })
public abstract class EligibilityCriteria extends AbstractMutableDeletableDomainObject {
	private Boolean notApplicableIndicator;

	private Integer questionNumber;

	private String questionText;

	private String name;

	
	@Override
	@Transient
	public void setRetiredIndicatorAsTrue(){
		super.setRetiredIndicatorAsTrue();		
	}
	
	public Boolean getNotApplicableIndicator() {
		return notApplicableIndicator;
	}

	public void setNotApplicableIndicator(Boolean notApplicableInidicator) {
		this.notApplicableIndicator = notApplicableInidicator;
	}

	public Integer getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
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
}