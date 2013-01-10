/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class StratificationCriterionPermissibleAnswer.
 * 
 * @author Vinay Gangoli
 */
@Entity
@Table(name = "strat_cri_per_ans")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "strat_cri_per_ans_ID_SEQ") })
public class StratificationCriterionPermissibleAnswer extends AbstractMutableDeletableDomainObject
                implements Comparable<StratificationCriterionPermissibleAnswer> {

    /** The permissible answer. */
    private String permissibleAnswer;

    /**
     * Instantiates a new stratification criterion permissible answer.
     */
    public StratificationCriterionPermissibleAnswer() {

    }

    /**
     * Gets the trimmed permissible answer.
     * 
     * @return the trimmed permissible answer
     */
    @Transient
    public String getTrimmedPermissibleAnswer() {
        return StringUtils.getTrimmedText(permissibleAnswer, 40);
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(StratificationCriterionPermissibleAnswer o) {
        return 0;
    }

    /**
     * Gets the permissible answer.
     * 
     * @return the permissible answer
     */
    public String getPermissibleAnswer() {
        return permissibleAnswer;
    }

    /**
     * Sets the permissible answer.
     * 
     * @param permissibleAnswer the new permissible answer
     */
    public void setPermissibleAnswer(String permissibleAnswer) {
        this.permissibleAnswer = permissibleAnswer;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + ((permissibleAnswer == null) ? 0 : permissibleAnswer.hashCode());
        return result;
    }

    /**
     * Two permisssible answers are considered equal if the permissible answer string text are equal.
     * called by the equals method in StratificationCriterion.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        final StratificationCriterionPermissibleAnswer other = (StratificationCriterionPermissibleAnswer) obj;
        if (permissibleAnswer == null) {
            if (other.permissibleAnswer != null) return false;
        }
        else if (!permissibleAnswer.equals(other.permissibleAnswer)) return false;
        return true;
    }

}
