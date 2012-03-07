package edu.duke.cabig.c3pr.domain;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * The Class StratificationCriterion.
 * 
 * @author Vinay Gangoli
 */
@Entity
@Table(name = "strat_criteria")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "strat_criteria_ID_SEQ") })
public class StratificationCriterion extends AbstractMutableDeletableDomainObject  {

    /** The lazy list helper. */
    private LazyListHelper lazyListHelper;

    /** The question number. */
    private Integer questionNumber = new Integer(0);

    /** The question text. */
    private String questionText;

    /**
     * Instantiates a new stratification criterion.
     */
    public StratificationCriterion() {
        lazyListHelper = new LazyListHelper();
        this.questionNumber = new Integer(0);
        lazyListHelper.add(StratificationCriterionPermissibleAnswer.class,
                        new InstantiateFactory<StratificationCriterionPermissibleAnswer>(
                                        StratificationCriterionPermissibleAnswer.class));
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#setRetiredIndicatorAsTrue()
     */
    @Override
    @Transient
    public void setRetiredIndicatorAsTrue() {
        super.setRetiredIndicatorAsTrue();
        List<StratificationCriterionPermissibleAnswer> scpaList = this.getPermissibleAnswers();
        StratificationCriterionPermissibleAnswer scpa;
        Iterator scpaIter = scpaList.iterator();
        while (scpaIter.hasNext()) {
            scpa = (StratificationCriterionPermissibleAnswer) scpaIter.next();
            scpa.setRetiredIndicatorAsTrue();
        }
    }

    /**
     * Adds the permissible answer.
     * 
     * @param answer the answer
     */
    public void addPermissibleAnswer(StratificationCriterionPermissibleAnswer answer) {
        getPermissibleAnswers().add(answer);
    }

    /**
     * Removes the permissible answer.
     * 
     * @param answer the answer
     */
    public void removePermissibleAnswer(StratificationCriterionPermissibleAnswer answer) {
        getPermissibleAnswers().remove(answer);
    }

    /**
     * Gets the trimmed question text.
     * 
     * @return the trimmed question text
     */
    @Transient
    public String getTrimmedQuestionText() {
        return StringUtils.getTrimmedText(questionText, 40);
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     
    public int compareTo(StratificationCriterion o) {
        return 0;
    }*/

    /**
     * Gets the question number.
     * 
     * @return the question number
     */
    public Integer getQuestionNumber() {
        return questionNumber;
    }

    /**
     * Sets the question number.
     * 
     * @param questionNumber the new question number
     */
    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    /**
     * Gets the question text.
     * 
     * @return the question text
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Sets the question text.
     * 
     * @param questionText the new question text
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * Gets the permissible answers internal.
     * 
     * @return the permissible answers internal
     */
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
    @JoinColumn(name = "str_cri_id", nullable = false)
    @Cascade(value = { CascadeType.ALL})
    @Where(clause = "retired_indicator = 'false'")
    public List<StratificationCriterionPermissibleAnswer> getPermissibleAnswersInternal() {
        return lazyListHelper.getInternalList(StratificationCriterionPermissibleAnswer.class);
    }

    /**
     * Sets the permissible answers.
     * 
     * @param permissibleAnswers the new permissible answers
     
    public void setPermissibleAnswers(
                    List<StratificationCriterionPermissibleAnswer> permissibleAnswers) {
        lazyListHelper.setInternalList(StratificationCriterionPermissibleAnswer.class,
                        permissibleAnswers);
    }*/

    /**
     * Gets the permissible answers.
     * 
     * @return the permissible answers
     */
    @Transient
    public List<StratificationCriterionPermissibleAnswer> getPermissibleAnswers() {
        return lazyListHelper.getLazyList(StratificationCriterionPermissibleAnswer.class);
    }

    /**
     * Sets the permissible answers internal.
     * 
     * @param permissibleAnswers the new permissible answers internal
     */
    public void setPermissibleAnswersInternal(
                    List<StratificationCriterionPermissibleAnswer> permissibleAnswers) {
        lazyListHelper.setInternalList(StratificationCriterionPermissibleAnswer.class,
                        permissibleAnswers);
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        for (StratificationCriterionPermissibleAnswer scpa : getPermissibleAnswers()) {
            result = PRIME * result + ((scpa == null) ? 0 : scpa.getPermissibleAnswer().hashCode());
        }
        result = PRIME * result + ((questionNumber == null) ? 0 : questionNumber.hashCode());
        result = PRIME * result + ((questionText == null) ? 0 : questionText.hashCode());
        return result;
    }

    /**
     * Two StratificationCriterion are considered equal if the permissible answer string text are equal.
     * and the question number and the question text are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        final StratificationCriterion other = (StratificationCriterion) obj;
        if (getPermissibleAnswers() == null) {
            if (other.getPermissibleAnswers() != null) return false;
        }
        else if (!getPermissibleAnswers().equals(other.getPermissibleAnswers())) return false;
        if (questionNumber == null) {
            if (other.questionText != null) return false;
        }
        else if (!questionNumber.equals(other.questionNumber)) return false;
        if (questionText == null) {
            if (other.questionText != null) return false;
        }
        else if (!questionText.equals(other.questionText)) return false;
        return true;
    }

}
