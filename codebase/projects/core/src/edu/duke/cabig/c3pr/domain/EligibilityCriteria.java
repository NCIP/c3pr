package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

/**
 * @author Priyatam
 */
 @Entity
 @Table (name = "eligibility_criterias")
 @Inheritance(strategy=InheritanceType.SINGLE_TABLE) 
 @GenericGenerator(name="id-generator", strategy = "native",
     parameters = {
         @Parameter(name="sequence", value="ELIGIBILITY_CRITERIAS_ID_SEQ")
     }
 )
public abstract class EligibilityCriteria extends AbstractGridIdentifiableDomainObject
{			
	private Boolean notApplicableIndicator;
	
	private int questionNumber;
	
	private String questionText;
	
	private Study study;
	
	@ManyToOne
	@JoinColumn(name="stu_id", updatable = false, insertable = false) 
	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	public Boolean getNotApplicableIndicator() {
		return notApplicableIndicator;
	}

	public void setNotApplicableIndicator(Boolean notApplicableInidicator) {
		this.notApplicableIndicator = notApplicableInidicator;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
		
	
}