package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;
import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

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

/**
 * @author Priyatam
 */
@Entity
@Table (name = "strat_criteria")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="strat_criteria_ID_SEQ")
    }
)
public class StratificationCriterion extends AbstractMutableDeletableDomainObject implements Comparable<StratificationCriterion> {

	private LazyListHelper lazyListHelper;
	private Integer questionNumber=new Integer(0);
    private String questionText;
       
    public StratificationCriterion() {
    	lazyListHelper=new LazyListHelper();
    	this.questionNumber=new Integer(0);
    	lazyListHelper.add(StratificationCriterionPermissibleAnswer.class,new InstantiateFactory<StratificationCriterionPermissibleAnswer>(
    			StratificationCriterionPermissibleAnswer.class));
	}
    /// LOGIC

    @Override
	@Transient
	public void setRetiredIndicatorAsTrue(){
		super.setRetiredIndicatorAsTrue();
		List<StratificationCriterionPermissibleAnswer> scpaList = this.getPermissibleAnswers();
		StratificationCriterionPermissibleAnswer scpa;
		Iterator scpaIter = scpaList.iterator();
		while(scpaIter.hasNext()){
			scpa = (StratificationCriterionPermissibleAnswer)scpaIter.next();
			scpa.setRetiredIndicatorAsTrue();
		}
	}
    
	public void addPermissibleAnswer(StratificationCriterionPermissibleAnswer answer){
		getPermissibleAnswers().add(answer);
//		answer.setStratificationCriterion(this);
	}
	
	public void removePermissibleAnswer(StratificationCriterionPermissibleAnswer answer){
		getPermissibleAnswers().remove(answer);
	}
	

	@Transient
	public String getTrimmedQuestionText() {		
		return StringUtils.getTrimmedText(questionText, 40);
	}
    
    /// BEAN PROPERTIES
	public int compareTo(StratificationCriterion o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Integer getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	@OneToMany (fetch=FetchType.LAZY)
	@JoinColumn(name="str_cri_id", nullable=false)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    @Where(clause = "retired_indicator = 'false'")
	public List<StratificationCriterionPermissibleAnswer> getPermissibleAnswersInternal() {
		return lazyListHelper.getInternalList(StratificationCriterionPermissibleAnswer.class);
	}

	public void setPermissibleAnswers(
			List<StratificationCriterionPermissibleAnswer> permissibleAnswers) {
		lazyListHelper.setInternalList(StratificationCriterionPermissibleAnswer.class, permissibleAnswers);
	}

	@Transient
	public List<StratificationCriterionPermissibleAnswer> getPermissibleAnswers() {
		return lazyListHelper.getLazyList(StratificationCriterionPermissibleAnswer.class);
	}

	public void setPermissibleAnswersInternal(
			List<StratificationCriterionPermissibleAnswer> permissibleAnswers) {
		lazyListHelper.setInternalList(StratificationCriterionPermissibleAnswer.class, permissibleAnswers);
	}
	
/*	@OneToMany (fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})	
    @JoinColumn(name = "sc_id")
	public List<StratificationCriterionAnswerCombination> getStratificationCriterionAnswerCombinationInternal() {
		return lazyListHelper.getInternalList(StratificationCriterionAnswerCombination.class);
	}

	public void setStratificationCriterionAnswerCombinationInternal(
			List<StratificationCriterionAnswerCombination> combinationAnswers) {
		lazyListHelper.setInternalList(StratificationCriterionAnswerCombination.class, combinationAnswers);
	}
	
	@Transient
	public List<StratificationCriterionAnswerCombination> getStratificationCriterionAnswerCombination() {
		return lazyListHelper.getLazyList(StratificationCriterionAnswerCombination.class);
	}

	public void setStratificationCriterionAnswerCombination(
			List<StratificationCriterionAnswerCombination> combinationAnswers) {
		
	}*/

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		for(StratificationCriterionPermissibleAnswer scpa: getPermissibleAnswers()){
			result = PRIME * result + ((scpa == null) ? 0 : scpa.getPermissibleAnswer().hashCode());
		}		
		result = PRIME * result + ((questionNumber == null) ? 0 : questionNumber.hashCode());
		result = PRIME * result + ((questionText == null) ? 0 : questionText.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final StratificationCriterion other = (StratificationCriterion) obj;
		if (getPermissibleAnswers() == null) {
			if (other.getPermissibleAnswers() != null)
				return false;
		} else if (!getPermissibleAnswers().equals(other.getPermissibleAnswers()))
			return false;
        if (questionNumber == null) {
            if (other.questionText != null)
				return false;
       } else if (!questionNumber.equals(other.questionNumber))
        	return false;
		if (questionText == null) {
			if (other.questionText != null)
				return false;
		} else if (!questionText.equals(other.questionText))
			return false;
		return true;
	}
		
}
