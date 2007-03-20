package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Kruttik
 */
 @Entity
 @Table (name = "subject_eligibility_answers")
 @GenericGenerator(name="id-generator", strategy = "native",
     parameters = {
         @Parameter(name="sequence", value="prt_eligibility_ans_id_seq")
     }
 )
public class SubjectEligibilityAnswer extends AbstractGridIdentifiableDomainObject
{			
	private boolean answerText;
	private EligibilityCriteria eligibilityCriteria;
	
	public boolean isAnswerText() {
		return answerText;
	}
	public void setAnswerText(boolean answerText) {
		this.answerText = answerText;
	}

	@ManyToOne
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })        
    @JoinColumn(name = "study_id", nullable=false)    
	public EligibilityCriteria getEligibilityCriteria() {
		return eligibilityCriteria;
	}
	public void setEligibilityCriteria(EligibilityCriteria eligibilityCriteria) {
		this.eligibilityCriteria = eligibilityCriteria;
	}
	
	
}