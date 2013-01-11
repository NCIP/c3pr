/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

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
@Table(name = "subject_strat_ans")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "subject_strat_ans_id_seq") })
public class SubjectStratificationAnswer extends AbstractMutableDeletableDomainObject {
    private StratificationCriterion stratificationCriterion;

    private StratificationCriterionPermissibleAnswer stratificationCriterionAnswer;

    @ManyToOne
    @Cascade(value = { CascadeType.LOCK})
    @JoinColumn(name = "STR_CRI_ID", nullable = true)
    public StratificationCriterion getStratificationCriterion() {
        return stratificationCriterion;
    }

    public void setStratificationCriterion(StratificationCriterion stratificationCriterion) {
        this.stratificationCriterion = stratificationCriterion;
    }

    @OneToOne
    @Cascade(value = { CascadeType.LOCK})
    @JoinColumn(name = "STRAT_ANS_ID")
    public StratificationCriterionPermissibleAnswer getStratificationCriterionAnswer() {
        return stratificationCriterionAnswer;
    }

    public void setStratificationCriterionAnswer(
                    StratificationCriterionPermissibleAnswer stratificationCriterionAnswer) {
        this.stratificationCriterionAnswer = stratificationCriterionAnswer;
    }
}
