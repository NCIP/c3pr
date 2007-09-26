package edu.duke.cabig.c3pr.domain;


import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;


/**
 * @author Vinay Gangoli
 */
@Entity
@Table (name = "strat_cri_ans_cmb")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="strat_cri_ans_cmb_id_seq")
    }
)
public class StratificationCriterionAnswerCombination extends AbstractMutableDeletableDomainObject {
	
	private StratificationCriterion stratificationCriterion;
	private StratificationCriterionPermissibleAnswer stratificationCriterionPermissibleAnswer;
	
	public StratificationCriterionAnswerCombination(){		
	}
	
	public StratificationCriterionAnswerCombination(StratificationCriterionAnswerCombination scac){		
		this.stratificationCriterion = scac.getStratificationCriterion();
		this.stratificationCriterionPermissibleAnswer = scac.getStratificationCriterionPermissibleAnswer();		
	}
	
	public StratificationCriterionAnswerCombination(SubjectStratificationAnswer ssa){
		this.stratificationCriterion = ssa.getStratificationCriterion();
		this.stratificationCriterionPermissibleAnswer = ssa.getStratificationCriterionAnswer();
	}
	
	
	@ManyToOne
    @JoinColumn(name = "sc_id")
    @Cascade(value = { CascadeType.SAVE_UPDATE, CascadeType.MERGE})
	public StratificationCriterion getStratificationCriterion() {
		return stratificationCriterion;
	}
	public void setStratificationCriterion(
			StratificationCriterion stratificationCriterion) {
		this.stratificationCriterion = stratificationCriterion;
	}
	
	@ManyToOne
    @JoinColumn(name = "scpa_id")
    @Cascade(value = { CascadeType.SAVE_UPDATE, CascadeType.MERGE})
	public StratificationCriterionPermissibleAnswer getStratificationCriterionPermissibleAnswer() {
		return stratificationCriterionPermissibleAnswer;
	}
	public void setStratificationCriterionPermissibleAnswer(
			StratificationCriterionPermissibleAnswer stratificationCriterionPermissibleAnswer) {
		this.stratificationCriterionPermissibleAnswer = stratificationCriterionPermissibleAnswer;
	}	
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME
				* result
				+ (stratificationCriterion.getQuestionText().hashCode())
				+ (stratificationCriterionPermissibleAnswer.getPermissibleAnswer().hashCode());
		return result;
	}

	/*
	 * NOTE: As per this method two Stratum Groups are considered equal if they have the same question/answer combination.
	 * In other words if they have the same stratification_cri_ans_combination.
	 */
	@Override	
	public boolean equals(Object obj){
		if (this == obj)
			return true;
//		if (!super.equals(obj))
//			return false;
		if (getClass() != obj.getClass())
			return false;
		if(obj instanceof StratificationCriterionAnswerCombination){
			StratificationCriterionAnswerCombination scac = (StratificationCriterionAnswerCombination)obj;
			if(scac.getStratificationCriterion().getQuestionText().equals(this.getStratificationCriterion().getQuestionText()) &&
			   scac.getStratificationCriterionPermissibleAnswer().getPermissibleAnswer().equals(this.getStratificationCriterionPermissibleAnswer().getPermissibleAnswer()) ){
				return true;
			}
			
//			if(this.getStratificationCriterion().equals(sg.getStratificationCriterion()) &&
//					this.getStratificationCriterionPermissibleAnswer().equals(sg.getStratificationCriterionPermissibleAnswer())){
//				return true;
//			}			
		} 
		return false;	
	}

}
