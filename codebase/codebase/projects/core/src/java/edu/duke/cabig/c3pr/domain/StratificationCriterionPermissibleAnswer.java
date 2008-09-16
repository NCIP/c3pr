package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * @author Priyatam
 */
@Entity
@Table(name = "strat_cri_per_ans")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "strat_cri_per_ans_ID_SEQ") })
public class StratificationCriterionPermissibleAnswer extends AbstractMutableDeletableDomainObject
                implements Comparable<StratificationCriterionPermissibleAnswer> {

    private String permissibleAnswer;

    public StratificationCriterionPermissibleAnswer() {

    }

    @Transient
    public String getTrimmedPermissibleAnswer() {
        return StringUtils.getTrimmedText(permissibleAnswer, 40);
    }

    // / BEAN PROPERTIES

    public int compareTo(StratificationCriterionPermissibleAnswer o) {
        return 0;
    }

    public String getPermissibleAnswer() {
        return permissibleAnswer;
    }

    public void setPermissibleAnswer(String permissibleAnswer) {
        this.permissibleAnswer = permissibleAnswer;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + ((permissibleAnswer == null) ? 0 : permissibleAnswer.hashCode());
        return result;
    }

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
