package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Kruttik
 */
 @Entity
 @Table (name = "subject_eligibility_ans")
 @GenericGenerator(name="id-generator", strategy = "native",
     parameters = {
         @Parameter(name="sequence", value="subject_eligibility_ans_id_seq")
     }
 )
public class SubjectEligibilityAnswer extends AbstractMutableDeletableDomainObject
{			
	private String answerText;
	private EligibilityCriteria eligibilityCriteria;
	
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	@ManyToOne
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN, CascadeType.SAVE_UPDATE, CascadeType.PERSIST })        
    @JoinColumn(name = "elgct_id", nullable=false)    
	public EligibilityCriteria getEligibilityCriteria() {
		return eligibilityCriteria;
	}
	public void setEligibilityCriteria(EligibilityCriteria eligibilityCriteria) {
		this.eligibilityCriteria = eligibilityCriteria;
	}
	
	
}