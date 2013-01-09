/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * The Class StratificationCriterionAnswerCombination.
 * 
 * @author Vinay Gangoli
 */
@Entity
@Table(name = "strat_cri_ans_cmb")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "strat_cri_ans_cmb_id_seq") })
public class StratificationCriterionAnswerCombination extends AbstractMutableDeletableDomainObject {

    /** The stratification criterion. */
    private StratificationCriterion stratificationCriterion;

    /** The stratification criterion permissible answer. */
    private StratificationCriterionPermissibleAnswer stratificationCriterionPermissibleAnswer;

    /**
     * Instantiates a new stratification criterion answer combination.
     */
    public StratificationCriterionAnswerCombination() {
    }

    /**
     * Instantiates a new stratification criterion answer combination.
     * 
     * @param scac the scac
     */
    public StratificationCriterionAnswerCombination(StratificationCriterionAnswerCombination scac) {
        this.stratificationCriterion = scac.getStratificationCriterion();
        this.stratificationCriterionPermissibleAnswer = scac
                        .getStratificationCriterionPermissibleAnswer();
    }

    /**
     * Instantiates a new stratification criterion answer combination.
     * 
     * @param ssa the ssa
     */
    public StratificationCriterionAnswerCombination(SubjectStratificationAnswer ssa) {
        this.stratificationCriterion = ssa.getStratificationCriterion();
        this.stratificationCriterionPermissibleAnswer = ssa.getStratificationCriterionAnswer();
    }

    /**
     * Gets the stratification criterion.
     * 
     * @return the stratification criterion
     */
    @ManyToOne
    @JoinColumn(name = "sc_id")
    @Cascade(value = { CascadeType.SAVE_UPDATE, CascadeType.MERGE })
    public StratificationCriterion getStratificationCriterion() {
        return stratificationCriterion;
    }

    /**
     * Sets the stratification criterion.
     * 
     * @param stratificationCriterion the new stratification criterion
     */
    public void setStratificationCriterion(StratificationCriterion stratificationCriterion) {
        this.stratificationCriterion = stratificationCriterion;
    }

    /**
     * Gets the stratification criterion permissible answer.
     * 
     * @return the stratification criterion permissible answer
     */
    @ManyToOne
    @JoinColumn(name = "scpa_id")
    @Cascade(value = { CascadeType.SAVE_UPDATE, CascadeType.MERGE })
    public StratificationCriterionPermissibleAnswer getStratificationCriterionPermissibleAnswer() {
        return stratificationCriterionPermissibleAnswer;
    }

    /**
     * Sets the stratification criterion permissible answer.
     * 
     * @param stratificationCriterionPermissibleAnswer the new stratification criterion permissible answer
     */
    public void setStratificationCriterionPermissibleAnswer(
                    StratificationCriterionPermissibleAnswer stratificationCriterionPermissibleAnswer) {
        this.stratificationCriterionPermissibleAnswer = stratificationCriterionPermissibleAnswer;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME
                        * result
                        + (stratificationCriterion.getQuestionText().hashCode())
                        + (stratificationCriterionPermissibleAnswer.getPermissibleAnswer()
                                        .hashCode());
        return result;
    }

    /*
     * NOTE: As per this method two Stratum Groups are considered equal if they have the same
     * question/answer combination. In other words if they have the same
     * stratification_cri_ans_combination.
     */
    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        if (obj instanceof StratificationCriterionAnswerCombination) {
            StratificationCriterionAnswerCombination scac = (StratificationCriterionAnswerCombination) obj;
            if (scac.getStratificationCriterion().getQuestionText().equals(this.getStratificationCriterion().getQuestionText())
                        && scac.getStratificationCriterionPermissibleAnswer().getPermissibleAnswer().equals(
                                     this.getStratificationCriterionPermissibleAnswer().getPermissibleAnswer())) {
                return true;
            }
        }
        return false;
    }

}
