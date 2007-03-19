package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Kruttik
 */
 @Entity
 @Table (name = "eligibility_criterias")
 @Inheritance(strategy=InheritanceType.SINGLE_TABLE) 
 @DiscriminatorColumn(name="expected_answer_indicator",
                discriminatorType=DiscriminatorType.STRING, length=1)
 @GenericGenerator(name="id-generator", strategy = "native",
     parameters = {
         @Parameter(name="sequence", value="ELIGIBILITY_CRITERIAS_ID_SEQ")
     }
 )
public abstract class EligibilityCriteria extends AbstractGridIdentifiableDomainObject
{			
	private boolean notApplicableInidicator;
	
	private int questionNumber;
	
	private String questionText;
	
	public boolean isNotApplicableInidicator() {
		return notApplicableInidicator;
	}

	public void setNotApplicableInidicator(boolean notApplicableInidicator) {
		this.notApplicableInidicator = notApplicableInidicator;
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