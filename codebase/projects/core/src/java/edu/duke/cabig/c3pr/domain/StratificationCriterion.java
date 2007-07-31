package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Priyatam
 */
@Entity
@Table (name = "stratification_criterion")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="STRATIFICATION_CRI_ID_SEQ")
    }
)
public class StratificationCriterion extends AbstractMutableDomainObject implements Comparable<StratificationCriterion> {

	private LazyListHelper lazyListHelper;
	private Integer questionNumber=new Integer(0);
    private String questionText;
       
    public StratificationCriterion() {
    	lazyListHelper=new LazyListHelper();
    	this.questionNumber=new Integer(0);
    	lazyListHelper.add(StratificationCriterionPermissibleAnswer.class,new BiDirectionalInstantiateFactory<StratificationCriterionPermissibleAnswer>(
    			StratificationCriterionPermissibleAnswer.class,this));
	}
    /// LOGIC

	public void addPermissibleAnswer(StratificationCriterionPermissibleAnswer answer){
		getPermissibleAnswers().add(answer);
		answer.setStratificationCriterion(this);
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

	@OneToMany (mappedBy="stratificationCriterion", fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})	
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

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((getPermissibleAnswers() == null) ? 0 : getPermissibleAnswers().hashCode());
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
