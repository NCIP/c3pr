package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.StringUtils;

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
public class StratificationCriterion extends AbstractDomainObject implements Comparable<StratificationCriterion> {

	private int questionNumber;
    private String questionText;
    private List<StratificationCriterionPermissibleAnswer> permissibleAnswers = 
    	new ArrayList<StratificationCriterionPermissibleAnswer>();
    private Study study;
    
    /// LOGIC

	public void addPermissibleAnswer(StratificationCriterionPermissibleAnswer answer){
		permissibleAnswers.add(answer);
		answer.setStratificationCriterion(this);
	}
	
	public void removePermissibleAnswer(StratificationCriterionPermissibleAnswer answer){
		permissibleAnswers.remove(answer);
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

	@ManyToOne
	@JoinColumn(name="study_id", nullable=false)
	public Study getStudy() {
		return study;
	}
	
	public void setStudy(Study study) {
		this.study = study;
	}
	
	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
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
	public List<StratificationCriterionPermissibleAnswer> getPermissibleAnswers() {
		return permissibleAnswers;
	}

	public void setPermissibleAnswers(
			List<StratificationCriterionPermissibleAnswer> permissibleAnswers) {
		this.permissibleAnswers = permissibleAnswers;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((permissibleAnswers == null) ? 0 : permissibleAnswers.hashCode());
		result = PRIME * result + questionNumber;
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
		if (permissibleAnswers == null) {
			if (other.permissibleAnswers != null)
				return false;
		} else if (!permissibleAnswers.equals(other.permissibleAnswers))
			return false;
		if (questionNumber != other.questionNumber)
			return false;
		if (questionText == null) {
			if (other.questionText != null)
				return false;
		} else if (!questionText.equals(other.questionText))
			return false;
		return true;
	}
	
	
}
