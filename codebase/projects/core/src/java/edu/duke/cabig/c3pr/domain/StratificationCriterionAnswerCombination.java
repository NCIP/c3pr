package edu.duke.cabig.c3pr.domain;


import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * @author Vinay Gangoli
 */
@Entity
@Table (name = "stratification_criterion_answer_combination")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="STRATIFICATION_CRITERION_ANSWER_COMBINATION_ID_SEQ")
    }
)
public class StratificationCriterionAnswerCombination extends AbstractMutableDomainObject {
	
	private StratificationCriterion stratificationCriterion;
	private StratificationCriterionPermissibleAnswer stratificationCriterionPermissibleAnswer;
	
	@ManyToOne (fetch=FetchType.LAZY)	
    @JoinColumn(name = "sc_id")
    @Cascade(value = { CascadeType.ALL})
	public StratificationCriterion getStratificationCriterion() {
		return stratificationCriterion;
	}
	public void setStratificationCriterion(
			StratificationCriterion stratificationCriterion) {
		this.stratificationCriterion = stratificationCriterion;
	}
	
	@ManyToOne (fetch=FetchType.LAZY)	
    @JoinColumn(name = "scpa_id")
    @Cascade(value = { CascadeType.ALL})
	public StratificationCriterionPermissibleAnswer getStratificationCriterionPermissibleAnswer() {
		return stratificationCriterionPermissibleAnswer;
	}
	public void setStratificationCriterionPermissibleAnswer(
			StratificationCriterionPermissibleAnswer stratificationCriterionPermissibleAnswer) {
		this.stratificationCriterionPermissibleAnswer = stratificationCriterionPermissibleAnswer;
	}	

}
