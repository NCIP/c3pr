/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import gov.nih.nci.cabig.ctms.domain.DomainObjectTools;

/**
 * @author Priyatam
 */
@Entity
@Table(name = "eligibility_criteria")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "ELIGIBILITY_CRITERIA_ID_SEQ") })
public abstract class EligibilityCriteria extends AbstractMutableDeletableDomainObject {
    private Boolean notApplicableIndicator;

    private Integer questionNumber;

    private String questionText;

    @Override
    @Transient
    public void setRetiredIndicatorAsTrue() {
        super.setRetiredIndicatorAsTrue();
    }

    public Boolean getNotApplicableIndicator() {
        return notApplicableIndicator;
    }

    public void setNotApplicableIndicator(Boolean notApplicableInidicator) {
        this.notApplicableIndicator = notApplicableInidicator;
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
    
    @Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final EligibilityCriteria that = (EligibilityCriteria) o;

		if(this.questionText == null || that.questionText == null)
			return false;
		
		return this.questionText.equals(that.questionText);
	}
}
