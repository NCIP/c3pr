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

@Entity
@Table(name = "sub_con_que_ans")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SUB_CON_QUE_ANS_ID_SEQ") })
public class SubjectConsentQuestionAnswer extends AbstractMutableDeletableDomainObject {
	
	private Boolean agreementIndicator;

	public Boolean getAgreementIndicator() {
		return agreementIndicator;
	}

	public void setAgreementIndicator(Boolean agreementIndicator) {
		this.agreementIndicator = agreementIndicator;
	}

	private ConsentQuestion consentQuestion;

	@ManyToOne
	@Cascade(value = { CascadeType.LOCK })
    @JoinColumn(name = "con_que_id", nullable = false)
	public ConsentQuestion getConsentQuestion() {
		return consentQuestion;
	}

	public void setConsentQuestion(ConsentQuestion consentQuestion) {
		this.consentQuestion = consentQuestion;
	}

}
