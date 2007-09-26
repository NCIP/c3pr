package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Kruttik
 */
 @Entity
 @Table (name = "subject_strat_ans")
 @GenericGenerator(name="id-generator", strategy = "native",
     parameters = {
         @Parameter(name="sequence", value="subject_strat_ans_id_seq")
     }
 )
public class SubjectStratificationAnswer extends AbstractMutableDeletableDomainObject
{			
	private StratificationCriterion stratificationCriterion;
	private StratificationCriterionPermissibleAnswer stratificationCriterionAnswer;

	@ManyToOne
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN, CascadeType.SAVE_UPDATE, CascadeType.PERSIST })        
    @JoinColumn(name = "STR_CRI_ID", nullable=true)
	public StratificationCriterion getStratificationCriterion() {
		return stratificationCriterion;
	}
	public void setStratificationCriterion(
			StratificationCriterion stratificationCriterion) {
		this.stratificationCriterion = stratificationCriterion;
	}
	
	@OneToOne
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN, CascadeType.SAVE_UPDATE, CascadeType.PERSIST })        
    @JoinColumn(name = "STRAT_ANS_ID")
	public StratificationCriterionPermissibleAnswer getStratificationCriterionAnswer() {
		return stratificationCriterionAnswer;
	}
	public void setStratificationCriterionAnswer(
			StratificationCriterionPermissibleAnswer stratificationCriterionAnswer) {
		this.stratificationCriterionAnswer = stratificationCriterionAnswer;
	}
}