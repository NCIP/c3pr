package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Priyatam
 */

@Entity
@Table (name = "Eligibility_Criteria")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="seq_arms_id")
    }
)
public class EligibilityCriteria extends AbstractDomainObject implements Comparable<EligibilityCriteria>, Serializable{

	/**
	 * Values include: Yes, No.
	 */
	private boolean expectedAnswerIndicator;
	private int id;
	private int questionNumber;
	private java.lang.String questionText;
	private Study study;

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(EligibilityCriteria o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + (expectedAnswerIndicator ? 1231 : 1237);
		result = PRIME * result + id;
		result = PRIME * result + questionNumber;
		result = PRIME * result + ((questionText == null) ? 0 : questionText.hashCode());
		result = PRIME * result + ((study == null) ? 0 : study.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final EligibilityCriteria other = (EligibilityCriteria) obj;
		if (expectedAnswerIndicator != other.expectedAnswerIndicator)
			return false;
		if (id != other.id)
			return false;
		if (questionNumber != other.questionNumber)
			return false;
		if (questionText == null) {
			if (other.questionText != null)
				return false;
		} else if (!questionText.equals(other.questionText))
			return false;
		if (study == null) {
			if (other.study != null)
				return false;
		} else if (!study.equals(other.study))
			return false;
		return true;
	}

}