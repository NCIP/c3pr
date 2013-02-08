/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * @author Kruttik
 */
@Entity
@Table(name = "subject_eligibility_ans")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "subject_eligibility_ans_id_seq") })
public class SubjectEligibilityAnswer extends AbstractMutableDeletableDomainObject {
    private String answerText;

    private EligibilityCriteria eligibilityCriteria;
    
    private boolean allowWaiver = false;
    
    private String waiverId;
    
    private String waiverReason;
    
    private PersonUser waivedBy;
    
	public boolean getAllowWaiver() {
		return allowWaiver;
	}

	public void setAllowWaiver(boolean allowWaiver) {
		this.allowWaiver = allowWaiver;
	}

	public String getWaiverId() {
		return waiverId;
	}

	public void setWaiverId(String waiverId) {
		this.waiverId = waiverId;
	}

	public String getWaiverReason() {
		return waiverReason;
	}

	public void setWaiverReason(String waiverReason) {
		this.waiverReason = waiverReason;
	}

	public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @ManyToOne
    @Cascade(value = { CascadeType.LOCK })
    @JoinColumn(name = "elgct_id", nullable = false)
    public EligibilityCriteria getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilityCriteria(EligibilityCriteria eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    @ManyToOne
    @JoinColumn(name = "rs_id")
    @Cascade( { CascadeType.LOCK})
	public PersonUser getWaivedBy() {
		return waivedBy;
	}

	public void setWaivedBy(PersonUser waivedBy) {
		this.waivedBy = waivedBy;
	}

	public boolean canAllowWaiver(){
		if (!allowWaiver && !StringUtils.isBlank(answerText) && (
				(eligibilityCriteria instanceof InclusionEligibilityCriteria && answerText.equalsIgnoreCase("no")) || 
				(eligibilityCriteria instanceof ExclusionEligibilityCriteria && answerText.equalsIgnoreCase("yes"))
				)) {
			return true;
		}
		return false;
	}
}
