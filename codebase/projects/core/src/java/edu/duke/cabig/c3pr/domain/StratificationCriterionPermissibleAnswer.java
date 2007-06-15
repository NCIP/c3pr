package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

/**
 * @author Priyatam
 */
@Entity
@Table (name = "stratification_cri_per_ans")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="STRATIFICATION_CRI_ANS_ID_SEQ")
    }
)
public class StratificationCriterionPermissibleAnswer extends AbstractMutableDomainObject implements Comparable<StratificationCriterionPermissibleAnswer> {

    private String permissibleAnswer;
    private StratificationCriterion stratificationCriterion;
    
    /// LOGIC
    @Transient
    public int getAnswerNumber() {
    	List<StratificationCriterionPermissibleAnswer> answers = 
    		stratificationCriterion.getPermissibleAnswers();
    	for (int i = 0; i < answers.size(); i ++) {
    		StratificationCriterionPermissibleAnswer answer = answers.get(i);
			if (answer.equals(this))
				return i+1;
		}
    	return 0;
    }
    
	@Transient
	public String getTrimmedPermissibleAnswer() {		
		return StringUtils.getTrimmedText(permissibleAnswer, 40);
	}
	
	/// BEAN PROPERTIES

    @OneToOne
	@Cascade(value = { CascadeType.ALL})
    @JoinColumn(name="str_cri_id", nullable=true)
	public StratificationCriterion getStratificationCriterion() {
		return stratificationCriterion;
	}

	public void setStratificationCriterion(
			StratificationCriterion stratificationCriterion) {
		this.stratificationCriterion = stratificationCriterion;
	}

	public int compareTo(StratificationCriterionPermissibleAnswer o) {
		// TODO Auto-generated method stub
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
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final StratificationCriterionPermissibleAnswer other = (StratificationCriterionPermissibleAnswer) obj;
		if (permissibleAnswer == null) {
			if (other.permissibleAnswer != null)
				return false;
		} else if (!permissibleAnswer.equals(other.permissibleAnswer))
			return false;
		return true;
	}

	
}
